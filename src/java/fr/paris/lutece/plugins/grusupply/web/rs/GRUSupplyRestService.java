/*
 * Copyright (c) 2002-2017, Mairie de Paris
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

import fr.paris.lutece.plugins.grubusiness.business.customer.Customer;
import fr.paris.lutece.plugins.grubusiness.business.demand.Demand;
import fr.paris.lutece.plugins.grubusiness.business.demand.DemandService;
import fr.paris.lutece.plugins.grubusiness.business.notification.Event;
import fr.paris.lutece.plugins.grubusiness.business.notification.Notification;
import fr.paris.lutece.plugins.grubusiness.business.notification.NotificationEvent;
import fr.paris.lutece.plugins.grubusiness.service.notification.NotificationException;
import fr.paris.lutece.plugins.grustoragedb.business.DemandTypeHome;
import fr.paris.lutece.plugins.grustoragedb.business.EnumGenericStatus;
import fr.paris.lutece.plugins.grustoragedb.business.NotificationContentHome;
import fr.paris.lutece.plugins.grusupply.business.StatusMessage;
import fr.paris.lutece.plugins.grusupply.constant.GruSupplyConstants;
import fr.paris.lutece.plugins.grusupply.service.CustomerProvider;
import fr.paris.lutece.plugins.grusupply.service.NotificationService;
import fr.paris.lutece.plugins.identitystore.web.exception.IdentityNotFoundException;
import fr.paris.lutece.plugins.rest.service.RestConstants;
import fr.paris.lutece.portal.service.util.AppLogService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;

@Path( RestConstants.BASE_PATH + GruSupplyConstants.PLUGIN_NAME )
public class GRUSupplyRestService
{
    // Bean names
    private static final String BEAN_STORAGE_SERVICE = "grusupply.storageService";

    // Messages
    private static final String WARNING_DEMAND_ID_MANDATORY = "Notification Demand_id field is mandatory";
    private static final String WARNING_DEMAND_TYPE_ID_MANDATORY = "Notification Demand_type_id field is mandatory";
    private static final String WARNING_CUSTOMER_ID_MANDATORY = "Notification connection_id field is mandatory";
    private static final String MESSAGE_MISSING_MANDATORY_FIELD = "Missing value";
    private static final String MESSAGE_MISSING_DEMAND_ID = "Demand Id and Demand type Id are mandatory";
    private static final String MESSAGE_MISSING_USER_ID = "User connection id is mandatory";
    private static final String MESSAGE_INCORRECT_DEMAND_ID = "Demand Type Id not found";
    
    // Other constants
    private static final String RESPONSE_OK = "{ \"acknowledge\" : { \"status\": \"received\" } }";
    
    private static final String TYPE_DEMAND = "DEMAND";
    private static final String TYPE_NOTIFICATION = "DEMAND";
    private static final String STATUS_WARNING = "WARNING";
    private static final String STATUS_ERROR = "ERROR";
    private static final String STATUS_FAILED = "FAILED";
    private static final String TYPE_GUICHET = "GUICHET";
    
    // instance variable
    @Inject
    @Named( BEAN_STORAGE_SERVICE )
    private DemandService _demandService;

    /**
     * Web Service methode which permit to store the notification flow into a data store
     * 
     * @param strJson
     *            The JSON flow
     * @return The response
     */
    @POST
    @Path( "notification" )
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.APPLICATION_JSON )
    public Response notification( String strJson )
    {
        
        List<StatusMessage> warnings = new ArrayList<>( );
        
        try
        {
            // Format from JSON
            ObjectMapper mapper = new ObjectMapper( );
            mapper.configure( DeserializationFeature.UNWRAP_ROOT_VALUE, true );
            mapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );

            Notification notification = mapper.readValue( strJson, Notification.class );
            AppLogService.debug( "grusupply / notification - Received strJson : " + strJson );

            Customer customerEncrypted = notification.getDemand( ).getCustomer( );
            
            if ( CustomerProvider.instance( ).hasIdentityService( ) )
            {
                Customer customerDecrypted = CustomerProvider.instance( ).decrypt( customerEncrypted, notification.getDemand( ) );

                if ( customerDecrypted != null && StringUtils.isNotEmpty( customerDecrypted.getConnectionId( ) )
                        && StringUtils.isEmpty( customerDecrypted.getId( ) ) )
                {
                	try 
                	{
                		Customer customerTmp = CustomerProvider.instance( ).get( customerDecrypted.getConnectionId( ), StringUtils.EMPTY );
                		customerDecrypted.setId( customerTmp.getId( ) );
                	}
            		catch ( IdentityNotFoundException e )
                    {
            			// customer not found in IDS
            			AppLogService.debug( "Customer not found with connection_id : " + customerDecrypted.getConnectionId( ) );
            			customerDecrypted = null;
                    }
                }
                	
                if ( customerDecrypted == null )
                {
                    customerDecrypted = new Customer( );
                    customerDecrypted.setConnectionId( StringUtils.EMPTY );
                    customerDecrypted.setId( StringUtils.EMPTY );
                    notification.getDemand().setCustomer( customerDecrypted );
                }

                notification.getDemand( ).setCustomer( customerDecrypted );
            }
            else
            {
                notification.getDemand( ).setCustomer( customerEncrypted );
            }

            // store any notification whatever its content
            store( notification );
            
            
            // notification should be associated to a demand id
            if ( StringUtils.isBlank( notification.getDemand( ).getId( ) ) )
            {
                StatusMessage msg = new StatusMessage( TYPE_DEMAND, STATUS_WARNING, MESSAGE_MISSING_MANDATORY_FIELD, WARNING_DEMAND_ID_MANDATORY );
                warnings.add( msg );
            }
            
            // notification should be associated to a demand type id
            if ( StringUtils.isBlank( notification.getDemand( ).getTypeId( ) ) )
            {
                StatusMessage msg = new StatusMessage( TYPE_DEMAND, STATUS_WARNING, MESSAGE_MISSING_MANDATORY_FIELD, WARNING_DEMAND_TYPE_ID_MANDATORY );
                warnings.add( msg );
            }
            
            // notification should be associated to a customer id
            if ( notification.getDemand( ).getCustomer( ) != null 
                    && StringUtils.isBlank( notification.getDemand( ).getCustomer( ).getConnectionId( ) ) )
            {
                StatusMessage msg = new StatusMessage( TYPE_DEMAND, STATUS_WARNING, MESSAGE_MISSING_MANDATORY_FIELD, WARNING_CUSTOMER_ID_MANDATORY );
                warnings.add( msg );
            }
            
            
            // Notify user and crm if a bean NotificationService is instantiated
            NotificationService notificationService = NotificationService.instance( );

            if ( notificationService != null )
            {
                AppLogService.info( "GRUSUPPLY - Process Notification" + notification.getId( ) );

                NotificationService.instance().process( notification );                
                
            }
            
            //Create NotificationContent
            NotificationContentHome.create( notification );
            
            //Notification Event
            controlNotificationEvent( notification );
            
        }
        catch( JsonParseException ex )
        {
            return fail( ex, Response.Status.BAD_REQUEST );
        }
        catch( JsonMappingException | NullPointerException | NotificationException ex )
        {
            return fail( ex, Response.Status.BAD_REQUEST  );
        }
        catch( IOException ex )
        {
            return fail( ex, Response.Status.INTERNAL_SERVER_ERROR  );
        }
        catch( Exception ex )
        {
            return fail( ex, Response.Status.INTERNAL_SERVER_ERROR  );
        }

        if ( warnings.isEmpty( ) )
        {
            return success( );
        }
        else
        {
            return successWithWarnings( warnings );
        }
        
    }
    
    /**
     * Values and store the NotificationEvent object if failure
     * @param notification
     * @param strMessage
     */
    private void controlNotificationEvent ( Notification notification  )
    {
        if ( notification.getMyDashboardNotification( ) != null )
        {
            String strMessage = checkNotification( notification ); 
            
            if( StringUtils.isNotEmpty( strMessage ))
            {
                NotificationEvent notificationEvent = new NotificationEvent( );
                        
                Event event = new Event( );
                event.setMessage( strMessage );
                event.setStatus( STATUS_FAILED );
                event.setType( TYPE_GUICHET );
                event.setEventDate( notification.getDate( ) );
                        
                notificationEvent.setEvent( event );
                notificationEvent.setMsgId( "" );   
                notificationEvent.setDemand( notification.getDemand( ) );
                notificationEvent.setNotificationDate( notification.getDate( ) );
                
                store( notificationEvent );
            }
        }
    }

    /**
     * Check notification
     * @param notif
     * @return The message error
     */
    private String checkNotification( Notification notif )
    {        
        // check if connection id is present
        if ( notif.getDemand( ) == null || notif.getDemand( ).getCustomer( ) == null || StringUtils.isBlank( notif.getDemand( ).getCustomer( ).getConnectionId( ) ) )
        {
            return generateErrorMessage( notif, Response.Status.PRECONDITION_FAILED, MESSAGE_MISSING_USER_ID );
        }

        // check if Demand remote id and demand type id are present
        if ( StringUtils.isBlank( notif.getDemand( ).getId( ) ) || StringUtils.isBlank( notif.getDemand( ).getTypeId( ) ) )
        {
            return generateErrorMessage( notif, Response.Status.PRECONDITION_FAILED, MESSAGE_MISSING_DEMAND_ID );
        }

        // check id demand_type_id is numeric
        if ( !StringUtils.isNumeric( notif.getDemand( ).getTypeId( ) ) )
        {
            return generateErrorMessage( notif, Response.Status.PRECONDITION_FAILED, MESSAGE_INCORRECT_DEMAND_ID );
        }

        // check if demand type id exists
        if ( !DemandTypeHome.findByPrimaryKey( Integer.parseInt( notif.getDemand( ).getTypeId( ) ) ).isPresent( ) )
        {
            return generateErrorMessage( notif, Response.Status.PRECONDITION_FAILED, MESSAGE_INCORRECT_DEMAND_ID );
        }

        return StringUtils.EMPTY;
    }

    /**
     * Generates the error message
     * @param notification
     * @param strResponseStatus
     * @param strErrorMessage
     * @return
     */
    private String generateErrorMessage ( Notification notification, Status strResponseStatus,  String strErrorMessage)
    {
        StringBuilder message = new StringBuilder();
        message.append( "\n" );
        message.append( "Demande id " + notification.getDemand( ).getDemandId( ) + "\n" );
        message.append( "Notification id " + notification.getId( ) + "\n" );
        message.append( "Status: " + strResponseStatus.getStatusCode( ) + " " + strResponseStatus.getReasonPhrase( ) + "\n" );
        message.append( "Error: " + strErrorMessage  + "\n" );
        
        return message.toString( );
    }
    
    
        /**
     * Web Service methode which permit to store the notification flow into a data store
     * 
     * @param strJson
     *            The JSON flow
     * @return The response
     */
    @POST
    @Path( "notificationEvent" )
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.APPLICATION_JSON )
    public Response notificationEvent( String strJson )
    {
        try
        {
            // Format from JSON
            ObjectMapper mapper = new ObjectMapper( );
            mapper.configure( DeserializationFeature.UNWRAP_ROOT_VALUE, true );
            mapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );

            NotificationEvent notificationEvent = mapper.readValue( strJson, NotificationEvent.class );
            AppLogService.debug( "grusupply / notificationEvent - Received strJson : " + strJson );

            store( notificationEvent );

        }
        catch( JsonParseException ex )
        {
            return fail( ex, Response.Status.BAD_REQUEST );
        }
        catch( JsonMappingException | NullPointerException ex )
        {
            return fail( ex, Response.Status.BAD_REQUEST );
        }
        catch( IOException ex )
        {
            return fail( ex, Response.Status.INTERNAL_SERVER_ERROR );
        }
        catch( Exception ex )
        {
            return fail( ex, Response.Status.INTERNAL_SERVER_ERROR  );
        }

        return success( );
    }

    /**
     * Stores a notification and the associated demand
     * 
     * @param notification
     *            the notification to store
     */
    private void store( Notification notification )
    {
        Demand demand = _demandService.findByPrimaryKey( notification.getDemand( ).getId( ), notification.getDemand( ).getTypeId( ) );
                
        if ( demand == null || !demand.getCustomer( ).getId( ).equals( notification.getDemand( ).getCustomer( ).getConnectionId( ) ) )
        {
            demand = new Demand( );

            demand.setId( notification.getDemand( ).getId( ) );
            demand.setTypeId( notification.getDemand( ).getTypeId( ) );
            demand.setSubtypeId( notification.getDemand( ).getSubtypeId( ) );
            demand.setReference( notification.getDemand( ).getReference( ) );
            demand.setCreationDate( notification.getDate( ) );
            demand.setMaxSteps( notification.getDemand( ).getMaxSteps( ) );
            demand.setCurrentStep( notification.getDemand( ).getCurrentStep( ) );
            demand.setStatusId( notification.getDemand( ).getStatusId( ) );

            Customer customerDemand = new Customer( );
            customerDemand.setId( notification.getDemand( ).getCustomer( ).getId( ) );
            customerDemand.setConnectionId( notification.getDemand( ).getCustomer( ).getConnectionId( ) );
            demand.setCustomer( customerDemand );
            _demandService.create( demand );
        }
        else
        {
            demand.getCustomer( ).setId( notification.getDemand( ).getCustomer( ).getId( ) );
            demand.setCurrentStep( notification.getDemand( ).getCurrentStep( ) );

            // Demand opened to closed
            if ( !EnumGenericStatus.isClosedStatus( demand.getStatusId( ) ) 
                    && EnumGenericStatus.isClosedStatus( notification.getDemand( ).getStatusId( ) ) )                 
            {
                demand.setClosureDate( notification.getDate( ) );
            }

            // Demand closed to opened
            if ( EnumGenericStatus.isClosedStatus( demand.getStatusId( ) ) && !EnumGenericStatus.isClosedStatus( notification.getDemand( ).getStatusId( ) ) )
            {                
                demand.setClosureDate( 0 );
            }

            _demandService.update( demand );
        }
        notification.setDemand( demand );
        _demandService.create( notification );
    }

        /**
     * Stores a notification and the associated demand
     * 
     * @param notification
     *            the notification to store
     */
    private void store( NotificationEvent notificationEvent )
    {

        _demandService.create( notificationEvent );
    }

 
    /**
     * Build an error response
     * 
     * @param strMessage
     *            The error message
     * @param ex
     *            An exception
     * @return The response
     */
    private Response fail( Throwable ex, Status httpStatus )
    {
        StringBuilder strMsg = new StringBuilder( "[" );
        
        if ( ex != null )
        {
            AppLogService.error( ex.getMessage( ), ex );
            strMsg.append( new StatusMessage( TYPE_NOTIFICATION, STATUS_ERROR, ex.toString( ) , ex.getMessage( ) ).asJson( ) );
        }

        strMsg.append( "]" );                
        String strError = "{ \"acknowledge\" : { \"status\": \"error\", \"errors\" : " + strMsg + " } }";

        return Response.status( httpStatus ).entity( strError ).build( );
    }
    
     
    /**
     * Build an error response
     * 
     * @param strMessage
     *            The error message
     * @param ex
     *            An exception
     * @return The response
     */
    private Response successWithWarnings( List<StatusMessage> warnings )
    {
        StringBuilder strWarnings = new StringBuilder( "[" );
        
        if (warnings != null)
        {
            for ( StatusMessage msg : warnings )
            {
                strWarnings.append( msg.asJson( ) ).append( "," );
            }
            
            // remove last ","
            strWarnings.setLength( strWarnings.length( ) - 1);
        }
        
        strWarnings.append( "]" );
            
        String strResponse = "{ \"acknowledge\" : { \"status\": \"warning\", \"warnings\" : " + strWarnings.toString( ) + " } }";
        
        return Response.status( Response.Status.CREATED ).entity( strResponse ).build( );
    }
    
    /**
     * success case
     * 
     * @return a successful response
     */
    private Response success( )
    {
        return Response.status( Response.Status.CREATED ).entity( RESPONSE_OK ).build( );
    }
}
