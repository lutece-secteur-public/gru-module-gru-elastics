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

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import fr.paris.lutece.plugins.crmclient.util.CRMException;
import fr.paris.lutece.plugins.grusupply.business.Customer;
import fr.paris.lutece.plugins.grusupply.business.Demand;
import fr.paris.lutece.plugins.grusupply.business.Notification;
import fr.paris.lutece.plugins.grusupply.business.dto.NotificationDTO;
import fr.paris.lutece.plugins.grusupply.constant.GruSupplyConstants;
import fr.paris.lutece.plugins.grusupply.service.CustomerProvider;
import fr.paris.lutece.plugins.grusupply.service.NotificationService;
import fr.paris.lutece.plugins.grusupply.service.StorageService;
import fr.paris.lutece.plugins.rest.service.RestConstants;
import fr.paris.lutece.portal.service.util.AppLogService;


@Path( RestConstants.BASE_PATH + GruSupplyConstants.PLUGIN_NAME )
public class GRUSupplyRestService
{
    private static final String STATUS_RECEIVED = "{ \"acknowledge\" : { \"status\": \"received\" } }";

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
            mapper.configure( Feature.UNWRAP_ROOT_VALUE, true );
            mapper.configure( Feature.FAIL_ON_UNKNOWN_PROPERTIES, false );

            NotificationDTO notif = mapper.readValue( strJson, NotificationDTO.class );
            AppLogService.info( "grusupply - Received strJson : " + strJson );

            Customer user = CustomerProvider.instance(  ).get( notif.getUserGuid(  ), notif.getCustomerid(  ) );
            Demand demand = buildDemand( notif, user );
            Notification notification = buildNotif( notif, demand, strJson );

            // Parse to Demand
            StorageService.instance(  ).store( demand );

            // Parse to Notifications
            StorageService.instance(  ).store( notification );

            // Notify user and crm if a bean NotificationService is instantiated
            NotificationService notificationService = NotificationService.instance(  );

            if ( notificationService != null )
            {
                AppLogService.info( " \n \n GRUSUPPLY - Bean Notifcation not null \n \n" );

                if ( notif.getUserEmail(  ) != null )
                {
                    notificationService.sendEmail( notif, user );
                }

                if ( notif.getUserSms(  ) != null )
                {
                    notificationService.sendSms( notif, user );
                }

                try
                {
                    if ( notif.getUserDashBoard(  ) != null )
                    {
                        notificationService.notifyCrm( notif );
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
     * Method which create a demand from an flux
     * @param notifDTO
     * @param customer
     * @return
     */
    private static Demand buildDemand( NotificationDTO notifDTO, Customer user )
    {
        if ( ( notifDTO == null ) || ( user == null ) )
        {
            throw new NullPointerException(  );
        }

        Demand demand = new Demand(  );
        demand.setCustomer( user );
        demand.setDemandId( notifDTO.getDemandeId(  ) );
        demand.setDemandTypeId( notifDTO.getDemandTypeId(  ) );
        demand.setDemandMaxStep( notifDTO.getMaxStep(  ) );
        demand.setDemandUserCurrentStep( notifDTO.getUserCurrentStep(  ) );
        demand.setNotifType( notifDTO.getNotificationType(  ) );
        demand.setCRMStatus( notifDTO.getCrmStatusId(  ) );
        demand.setReference( notifDTO.getReference(  ) );
        demand.setDemandStatus( notifDTO.getDemandStatus(  ) );

        return demand;
    }

    /**
     * Method which create a notification from a flux
     * @param notifDTO
     * @return
     */
    private static Notification buildNotif( NotificationDTO notifDTO, Demand demand, String strJson )
    {
        if ( notifDTO == null )
        {
            throw new NullPointerException(  );
        }

        Notification notification = new Notification(  );
        notification.setDemand( demand );
        notification.setDateNotification( notifDTO.getNotificationDate(  ) );
        notification.setUserEmail( notifDTO.getUserEmail(  ) );
        notification.setUserDashBoard( notifDTO.getUserDashBoard(  ) );
        notification.setUserSms( notifDTO.getUserSms(  ) );
        notification.setUserBackOffice( notifDTO.getUserBackOffice(  ) );
        notification.setJson( strJson );

        return notification;
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
