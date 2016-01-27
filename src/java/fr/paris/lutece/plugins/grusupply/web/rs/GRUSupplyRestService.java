/*
 * Copyright (c) 2002-2015, Mairie de Paris
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

import com.mysql.jdbc.StringUtils;

import fr.paris.lutece.plugins.grusupply.business.Customer;
import fr.paris.lutece.plugins.grusupply.business.Demand;
import fr.paris.lutece.plugins.grusupply.business.Notification;
import fr.paris.lutece.plugins.grusupply.business.dto.NotificationDTO;
import fr.paris.lutece.plugins.grusupply.business.dto.UserDTO;
import fr.paris.lutece.plugins.grusupply.constant.GruSupplyConstants;
import fr.paris.lutece.plugins.grusupply.service.GRUService;
import fr.paris.lutece.plugins.rest.service.RestConstants;
import fr.paris.lutece.portal.service.util.AppLogService;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path( RestConstants.BASE_PATH + GruSupplyConstants.PLUGIN_NAME )
public class GRUSupplyRestService
{
    private static final String STATUS_RECEIVED = "{ \"status\": \"received\" }";
    
    /**
     * Web Service methode which permit to store notification in elasticsearch
     *
     * @param strJson
     * @return
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

            NotificationDTO notif = mapper.readValue( strJson, NotificationDTO.class );
            AppLogService.info( "grusupply - Received strJson : " + strJson );

            // Find CID in GRU Database
            fr.paris.lutece.plugins.gru.business.customer.Customer gruCustomer;

            // CASE 1 NOT CID
            if ( StringUtils.isNullOrEmpty( notif.getCustomerid(  ) ) )
            {
                // CASE 1.1 : no cid and no guid:  break the flux and wait for a new flux with one of them
                if ( StringUtils.isNullOrEmpty( notif.getUserGuid(  ) ) )
                {
                    return error( "grusupply - Error : JSON doesnit contains any GUID nor Customer ID" );
                } // CASE 1.2  : no cid and guid:  look for a mapping beween an existing guid
                else
                {
                    gruCustomer = fr.paris.lutece.plugins.gru.business.customer.CustomerHome.findByGuid( notif.getUserGuid(  ) );

                    if ( gruCustomer == null )
                    {
                        String strGuid = notif.getUserGuid(  );

                        UserDTO userDto = GRUService.instance(  ).getUserInfo( strGuid );

                        gruCustomer = new fr.paris.lutece.plugins.gru.business.customer.Customer(  );
                        gruCustomer.setFirstname( userDto.getFirstname(  ) );
                        gruCustomer.setLastname( userDto.getLastname(  ) );
                        gruCustomer.setEmail( userDto.getEmail(  ) );
                        gruCustomer.setAccountGuid( strGuid );
                        gruCustomer = fr.paris.lutece.plugins.gru.business.customer.CustomerHome.create( gruCustomer );
                        AppLogService.info( "New user created into the GRU for the guid : " + strGuid +
                            " its customer id is : " + gruCustomer.getId(  ) );
                    }
                }
            } // CASE 2 : cid and (guid or no guid):  find customer info in GRU database
            else
            {
                gruCustomer = fr.paris.lutece.plugins.gru.business.customer.CustomerHome.findByPrimaryKey( Integer.parseInt( 
                            notif.getCustomerid(  ) ) );

                if ( gruCustomer == null )
                {
                    return error( "grusupply - Error : No user found with the customer ID : " +
                        notif.getCustomerid(  ) );
                }
            }

            // Parse to Customer (TODO HAVE TO ADD WITH OPENAM)
            GRUService.instance(  ).store( parseCustomer( gruCustomer ) );

            // Parse to Demand
            GRUService.instance(  ).store( parseDemand( notif, gruCustomer.getId(  ) ) );

            // Parse to Notification
            GRUService.instance(  ).store( parseNotif( notif, strJson ) );
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

        return Response.status( Response.Status.CREATED ).entity( STATUS_RECEIVED ).build();
    }

    /**
     * Method which create a demand from OpenAm, a flux and GRU database
     *
     * @param gruCustomer
     * @return
     */
    private static Customer parseCustomer( fr.paris.lutece.plugins.gru.business.customer.Customer gruCustomer )
    {
        Customer grusupplyCustomer = new Customer(  );
        grusupplyCustomer.setCustomerId( gruCustomer.getId(  ) );
        grusupplyCustomer.setName( gruCustomer.getLastname(  ) );
        grusupplyCustomer.setFirstName( gruCustomer.getFirstname(  ) );
        /*        grusupplyCustomer.setBirthday( gruCustomer.getBirthday(  ) );
         grusupplyCustomer.setCivility( gruCustomer.getCivility(  ) );
         grusupplyCustomer.setStreet( gruCustomer.getStreet(  ) );
         grusupplyCustomer.setCityOfBirth( gruCustomer.getCityOfBirth(  ) );
         grusupplyCustomer.setCity( gruCustomer.getCity(  ) );
         grusupplyCustomer.setPostalCode( gruCustomer.getPostalCode(  ) );
         grusupplyCustomer.setTelephoneNumber( gruCustomer.getTelephoneNumber(  ) );*/
        grusupplyCustomer.setEmail( gruCustomer.getEmail(  ) );
        grusupplyCustomer.setStayConnected( true );

        return grusupplyCustomer;
    }

    /**
     * Method which create a demand from an flux
     *
     * @param notifDTO
     * @param nCustomerId
     * @return
     */
    private static Demand parseDemand( NotificationDTO notifDTO, int nCustomerId )
    {
        Demand demand = new Demand(  );
        demand.setUserCid( nCustomerId );
        demand.setDemandId( notifDTO.getDemandeId(  ) );
        demand.setDemandIdType( notifDTO.getDemandIdType(  ) );
        demand.setDemandMaxStep( -1 );
        demand.setDemandUserCurrentStep( -1 );
        demand.setDemandState( notifDTO.getDemandState(  ) );
        demand.setNotifType( notifDTO.getNotificationType(  ) );
        demand.setDateDemand( "NON RENSEIGNE" );
        demand.setCRMStatus( notifDTO.getCrmStatusId(  ) );
        demand.setReference( "NON RENSEIGNE" );

        return demand;
    }

    /**
     * Method which create a notification from a flux
     *
     * @param notifDTO
     * @return
     */
    private static Notification parseNotif( NotificationDTO notifDTO, String strJson )
    {
        Notification notification = new Notification(  );
        notification.setDemandeId( notifDTO.getDemandeId(  ) );
        notification.setDemandIdType( notifDTO.getDemandIdType(  ) );
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
     * @return The response
     */
    private Response error( String strMessage )
    {
        return error( strMessage, null );
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
        return Response.ok().entity( strError ).build();
    }
}
