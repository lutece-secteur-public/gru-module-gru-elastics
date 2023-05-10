/*
 * Copyright (c) 2002-2023, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.grusupply.web.rs;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.grubusiness.business.demand.Demand;
import fr.paris.lutece.plugins.grubusiness.business.notification.EnumNotificationType;
import fr.paris.lutece.plugins.grubusiness.business.notification.Notification;
import fr.paris.lutece.plugins.grubusiness.business.notification.NotificationFilter;
import fr.paris.lutece.plugins.grustoragedb.business.DemandHome;
import fr.paris.lutece.plugins.grustoragedb.business.NotificationHome;
import fr.paris.lutece.plugins.grustoragedb.business.DemandType;
import fr.paris.lutece.plugins.grustoragedb.business.DemandTypeHome;
import fr.paris.lutece.plugins.grusupply.constant.GruSupplyConstants;
import fr.paris.lutece.plugins.grusupply.utils.GrusupplyUtils;
import fr.paris.lutece.plugins.rest.service.RestConstants;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.html.Paginator;

/**
 * 
 * Service Rest DemandNotificationRestService
 *
 */
@Path( RestConstants.BASE_PATH + GruSupplyConstants.PLUGIN_NAME + GruSupplyConstants.PATH_DEMAND )
public class DemandNotificationRestService
{

    /**
     * Return list of demand
     * 
     * @param strDemandType
     * @param strPage
     */
    @GET
    @Path( GruSupplyConstants.PATH_DEMAND_LIST )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getListDemand( @HeaderParam( GruSupplyConstants.QUERY_PARAM_ID_DEMAND_TYPE ) String strIdDemandType, 
            @HeaderParam( GruSupplyConstants.QUERY_PARAM_INDEX ) String strIndex,
            @HeaderParam( GruSupplyConstants.QUERY_PARAM_CUSTOMER_ID ) String strCustomerId,
            @HeaderParam( GruSupplyConstants.QUERY_PARAM_NOTIFICATION_TYPE ) String strNotificationType )
    {
        int nIndex = StringUtils.isEmpty( strIndex ) ? 1 : Integer.parseInt( strIndex );
        int nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( GruSupplyConstants.LIMIT_DEMAND_API_REST, 10 );

        if ( StringUtils.isEmpty( strNotificationType ) )
        {
            strNotificationType = EnumNotificationType.MYDASHBOARD.name( );
        }

        List<Integer> listIds = DemandHome.getIdsByCustomerIdAndDemandTypeId( strCustomerId, strNotificationType, strIdDemandType );

        return getResponse( nIndex, nDefaultItemsPerPage, listIds );
    }

    /**
     * Get list by status
     * 
     * @param strIdDemandType
     * @param strIndex
     * @param strCustomerId
     * @return list of active demand
     */
    @GET
    @Path( GruSupplyConstants.PATH_DEMAND_STATUS )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getListOfDemandActive( @HeaderParam( GruSupplyConstants.QUERY_PARAM_ID_DEMAND_TYPE ) String strIdDemandType,
            @HeaderParam( GruSupplyConstants.QUERY_PARAM_INDEX ) String strIndex, 
            @HeaderParam( GruSupplyConstants.QUERY_PARAM_CUSTOMER_ID ) String strCustomerId,
            @HeaderParam( GruSupplyConstants.QUERY_PARAM_LIST_STATUS ) String strListStatus,
            @HeaderParam( GruSupplyConstants.QUERY_PARAM_NOTIFICATION_TYPE ) String strNotificationType )
    {
        int nIndex = StringUtils.isEmpty( strIndex ) ? 1 : Integer.parseInt( strIndex );
        int nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( GruSupplyConstants.LIMIT_DEMAND_API_REST, 10 );

        List<String> listStatus = Arrays.asList( strListStatus.split( "," ) );
        
        if ( StringUtils.isEmpty( strNotificationType ) )
        {
            strNotificationType = EnumNotificationType.MYDASHBOARD.name( );
        }
        List<Integer> listIds = DemandHome.getIdsByStatus( strCustomerId, listStatus, strNotificationType, strIdDemandType );

        return getResponse( nIndex, nDefaultItemsPerPage, listIds );
    }

    /**
     * Get response
     * @param nIndex
     * @param nDefaultItemsPerPage
     * @param listIds
     * @return
     */
    private Response getResponse( int nIndex, int nDefaultItemsPerPage, List<Integer> listIds )
    {
        DemandResult result = new DemandResult( );
        
        if ( !listIds.isEmpty( ) )
        {
            Paginator<Integer> paginator = new Paginator<>( listIds, nDefaultItemsPerPage, StringUtils.EMPTY, StringUtils.EMPTY, String.valueOf( nIndex ) );

            result.setDemands( DemandHome.getByIdsWithLastStatus( paginator.getPageItems( ) ) );
            result.setIndex( String.valueOf( nIndex ) );
            result.setPaginator( nIndex + "/" + paginator.getPagesCount( ) );
            result.setStatus( Response.Status.OK.name( ) );
            result.setNumberResult( listIds.size( ) );
        }
        
        return Response.status( Response.Status.OK ).entity( GrusupplyUtils.convertToJsonString( result ) ).build( );
    }

    /**
     * Gets list of notification
     * 
     * @param strIdDemand
     */
    @GET
    @Path( GruSupplyConstants.PATH_NOTIFICATION_LIST )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getListNotification( @HeaderParam( GruSupplyConstants.QUERY_PARAM_ID_DEMAND ) String strIdDemand,
            @HeaderParam( GruSupplyConstants.QUERY_PARAM_ID_DEMAND_TYPE ) String strIdDemandType,
            @HeaderParam( GruSupplyConstants.QUERY_PARAM_READED ) String strReaded)
    {
        NotificationResult result = new NotificationResult( );
        
        if ( StringUtils.isNotEmpty( strIdDemand ) && StringUtils.isNotEmpty( strIdDemandType )  )
        {
            NotificationFilter filter = new NotificationFilter( );
            filter.setDemandId( strIdDemand );
            filter.setDemandTypeId( strIdDemandType );
            List<Notification> notifications = NotificationHome.findByFilter( filter );

            result.setNotifications( notifications );
            result.setStatus( Response.Status.OK.name( ) );
            result.setNumberResult( notifications.size( ) );
            
            //Set demand
            if( StringUtils.isNotEmpty( strReaded ) )
            {
                Demand demand = DemandHome.findByPrimaryKey( strIdDemand, strIdDemandType );
                demand.setRead( Boolean.parseBoolean( strReaded ) );
                
                DemandHome.update( demand );
            }
            
        }

        return Response.status( Response.Status.OK ).entity( GrusupplyUtils.convertToJsonString( result ) ).build( );
    }

    /**
     * Gets list of demand types
     * 
     * @return list of demand types
     */
    @GET
    @Path( GruSupplyConstants.PATH_TYPE_DEMAND )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getDemandTypes( )
    {
        List<DemandType> listDemandTypes = DemandTypeHome.getDemandTypesList( );

        String strResult = GrusupplyUtils.convertToJsonString( listDemandTypes );
        return Response.ok( strResult ).build( );
    }

    /**
     * Gets list of notification types
     * 
     * @return list of demand types
     */
    @GET
    @Path( GruSupplyConstants.PATH_TYPE_NOTIFICATION )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getNotificationTypes( )
    {
        String strResult = GrusupplyUtils.convertToJsonString( EnumNotificationType.values( ) );
        return Response.ok( strResult ).build( );
    }
}
