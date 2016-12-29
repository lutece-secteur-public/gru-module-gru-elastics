/*
 * Copyright (c) 2002-2016, Mairie de Paris
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.paris.lutece.plugins.crmclient.util.CRMException;
import fr.paris.lutece.plugins.grubusiness.business.demand.DemandService;
import fr.paris.lutece.plugins.grubusiness.business.notification.NotifyGruGlobalNotification;
import fr.paris.lutece.plugins.grusupply.business.Customer;
import fr.paris.lutece.plugins.grusupply.business.Demand;
import fr.paris.lutece.plugins.grusupply.constant.GruSupplyConstants;
import fr.paris.lutece.plugins.grusupply.service.CustomerProvider;
import fr.paris.lutece.plugins.grusupply.service.NotificationService;
import fr.paris.lutece.plugins.grusupply.service.IndexService;
import fr.paris.lutece.plugins.rest.service.RestConstants;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.util.AppLogService;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path( RestConstants.BASE_PATH + GruSupplyConstants.PLUGIN_NAME )
public class GRUSupplyRestService
{
    // Bean names
    private static final String BEAN_STORAGE_SERVICE = "grusupply.storageService";
    
    // Other constants
    private static final String STATUS_RECEIVED = "{ \"acknowledge\" : { \"status\": \"received\" } }";
    
    @Inject
    @Named( BEAN_STORAGE_SERVICE )
    private DemandService _demandService;

    /**
     * Web Service methode which permit to store the notification flow into a data store
     * @param strJson The JSON flow
     * @return The response
     */
    @POST
    @Path( "notification" )
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.APPLICATION_JSON )
    public Response notification( String strJson )
    {
        try
        {
            // Format from JSON
            ObjectMapper mapper = new ObjectMapper(  );
            mapper.configure( DeserializationFeature.UNWRAP_ROOT_VALUE, true );
            mapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );

            NotifyGruGlobalNotification notification = mapper.readValue( strJson, NotifyGruGlobalNotification.class );
            AppLogService.info( "grusupply - Received strJson : " + strJson );
            
            store( notification );

            //STORE FOR AGENT
            try
            {
                Customer user = null;

                if ( ( notification.getCustomerId(  ) != null ) &&
                        StringUtils.isNotBlank( notification.getCustomerId(  ) ) )
                {
                    user = CustomerProvider.instance(  ).get( notification.getGuid(  ), notification.getCustomerId(  ) );
                }

                Demand demandGruSupply = buildDemand( notification, user );

                // Parse to Demand
                IndexService.instance(  ).index( demandGruSupply );

                // Parse to Notifications
                IndexService.instance(  ).index( notification );
            }
            catch ( AppException e )
            {
                //NOTHING TO DO
            }

            // Notify user and crm if a bean NotificationService is instantiated
            NotificationService notificationService = NotificationService.instance(  );

            if ( notificationService != null )
            {
                AppLogService.info( " \n \n GRUSUPPLY - Bean Notifcation not null \n \n" );

                if ( notification.getUserEmail(  ) != null )
                {
                    notificationService.sendEmail( notification );
                }

                if ( notification.getUserSMS(  ) != null )
                {
                    notificationService.sendSms( notification );
                }

                if ( ( notification.getBroadcastEmail(  ) != null ) && !notification.getBroadcastEmail(  ).isEmpty(  ) )
                {
                    notificationService.sendBroadcastEmail( notification );
                }

                try
                {
                    if ( notification.getUserDashboard(  ) != null )
                    {
                        notificationService.notifyCrm( notification );
                    }
                }
                catch ( CRMException ex )
                {
                    return error( ex + " :" + ex.getMessage(  ), ex );
                }
            }
        }
        catch ( JsonParseException ex )
        {
            return error( ex + " :" + ex.getMessage(  ), ex );
        }
        catch ( JsonMappingException ex )
        {
            return error( ex + " :" + ex.getMessage(  ), ex );
        }
        catch ( IOException ex )
        {
            return error( ex + " :" + ex.getMessage(  ), ex );
        }
        catch ( NullPointerException ex )
        {
            return error( ex + " :" + ex.getMessage(  ), ex );
        }

        return Response.status( Response.Status.CREATED ).entity( STATUS_RECEIVED ).build(  );
    }
    
    /**
     * Stores a notification and the associated demand
     * @param notification the notification to store
     */
    private void store( NotifyGruGlobalNotification notification )
    {
        fr.paris.lutece.plugins.grubusiness.business.demand.Demand demand = _demandService.findByPrimaryKey( String.valueOf( notification.getDemandId(  ) ), String.valueOf( notification.getDemandTypeId(  ) ) );
        
        if ( demand == null )
        {
            demand = new fr.paris.lutece.plugins.grubusiness.business.demand.Demand(  );

            demand.setId( String.valueOf( notification.getDemandId(  ) ) );
            demand.setTypeId( String.valueOf( notification.getDemandTypeId(  ) ) );
            demand.setReference( notification.getDemandReference(  ) );
            demand.setCustomerId( notification.getCustomerId(  ) );
            demand.setCreationDate( notification.getNotificationDate(  ) );
            demand.setMaxSteps( notification.getDemandMaxStep(  ) );
            demand.setCurrentStep( notification.getDemandUserCurrentStep(  ) );
            demand.setStatusId( notification.getDemandStatus(  ) );
            
            _demandService.create( demand );
        }
        else
        {
            demand.setCustomerId( notification.getCustomerId(  ) );
            demand.setCurrentStep( notification.getDemandUserCurrentStep(  ) );

            // Demand opened to closed
            if ( ( demand.getStatusId(  ) != fr.paris.lutece.plugins.grubusiness.business.demand.Demand.STATUS_CLOSED ) &&
                    ( notification.getDemandStatus(  ) == fr.paris.lutece.plugins.grubusiness.business.demand.Demand.STATUS_CLOSED ) )
            {
                demand.setStatusId( notification.getDemandStatus(  ) );
                demand.setClosureDate( notification.getNotificationDate(  ) );
            }

            // Demand closed to opened
            if ( ( demand.getStatusId(  ) == fr.paris.lutece.plugins.grubusiness.business.demand.Demand.STATUS_CLOSED ) &&
                    ( notification.getDemandStatus(  ) != fr.paris.lutece.plugins.grubusiness.business.demand.Demand.STATUS_CLOSED ) )
            {
                demand.setStatusId( notification.getDemandStatus(  ) );
                demand.setClosureDate( 0 );
            }

            _demandService.update( demand );
        }
        
        _demandService.create( notification );
    }

    /**
     * Method which create a demand from an flux
     * @param notifDTO
     * @param customer
     * @return
     */
    private static Demand buildDemand( NotifyGruGlobalNotification notification, Customer user )
    {
        if ( ( notification == null ) )
        {
            throw new NullPointerException(  );
        }

        Demand demand = new Demand(  );
        demand.setCustomer( user );
        demand.setDemandId( notification.getDemandId(  ) );
        demand.setDemandTypeId( notification.getDemandTypeId(  ) );
        demand.setDemandMaxStep( notification.getDemandMaxStep(  ) );
        demand.setDemandUserCurrentStep( notification.getDemandUserCurrentStep(  ) );
        demand.setNotifType( notification.getNotificationType(  ) );
        demand.setCRMStatus( notification.getCrmStatusId(  ) );
        demand.setReference( notification.getDemandReference(  ) );
        demand.setDemandStatus( notification.getDemandStatus(  ) );

        return demand;
    }

    /**
     * Build an error response
     * @param strMessage The error message
     * @param ex An exception
     * @return The response
     */
    private Response error( String strMessage, Throwable ex )
    {
        if ( ex != null )
        {
            AppLogService.error( strMessage, ex );
        }
        else
        {
            AppLogService.error( strMessage );
        }

        String strError = "{ \"status\": \"Error : " + strMessage + "\" }";

        return Response.status( Response.Status.INTERNAL_SERVER_ERROR ).entity( strError ).build(  );
    }
}
