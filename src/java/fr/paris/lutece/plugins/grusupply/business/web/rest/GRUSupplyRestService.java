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
package fr.paris.lutece.plugins.grusupply.business.web.rest;

import fr.paris.lutece.plugins.grusupply.business.dto.ESBNotificationDTO;
import fr.paris.lutece.plugins.grusupply.business.dto.OpenAMUserDTO;
import fr.paris.lutece.plugins.grusupply.business.gru.Demand;
import fr.paris.lutece.plugins.grusupply.business.gru.Notification;
import fr.paris.lutece.plugins.grusupply.business.gru.User;
import fr.paris.lutece.plugins.grusupply.constant.GruSupplyConstants;
import fr.paris.lutece.plugins.grusupply.service.GRUService;
import fr.paris.lutece.plugins.rest.service.RestConstants;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path( RestConstants.BASE_PATH + GruSupplyConstants.PLUGIN_NAME + GruSupplyConstants.MODULE_NAME_ES )
public class GRUSupplyRestService
{
    /**
     * Web Service methode which permit to store notification in elasticsearch
     * @param strJson
     * @return
     */
    @POST
    @Path( "notification" )
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.APPLICATION_JSON )
    public String notification( String strJson )
    {
        try
        {
            // Format to JSON
            JSONObject jsonNotif = new JSONObject( strJson );

            // Create JSON flux to Object
            ESBNotificationDTO _esbNotif = new ESBNotificationDTO( jsonNotif );

            // Search in OpenAM
            OpenAMUserDTO userOpenam = GRUService.getService(  ).getUserInfo( _esbNotif.getUserGuid(  ) );

            // Parse to User (TODO HAVE TO ADD WITH OPENAM)
            User _user = new User(  );

            if ( userOpenam != null )
            {
                _user.setName( userOpenam.getLastname(  ) );
                _user.setFirstName( userOpenam.getFirstname(  ) );
                _user.setBirthday( userOpenam.getBirthday(  ) );
                _user.setCivility( userOpenam.getCivility(  ) );
                _user.setStreet( userOpenam.getStreet(  ) );
                _user.setCityOfBirth( userOpenam.getCityOfBirth(  ) );
                _user.setCity( userOpenam.getCity(  ) );
                _user.setPostalCode( userOpenam.getPostalCode(  ) );
                _user.setTelephoneNumber( userOpenam.getTelephoneNumber(  ) );
            }

            _user.setEmail( _esbNotif.getEmail(  ) );
            _user.setUserGuid( Long.parseLong( _esbNotif.getUserGuid(  ) ) );
            _user.setStayConnected( true );

            GRUService.getService(  ).store( _user );

            // Parse to Demand
             Demand _demand = new Demand(  );
            _demand.setUserGuid( Long.parseLong( _esbNotif.getUserGuid(  ) ) );
            _demand.setDemandId( _esbNotif.getDemandeId(  ) );
            _demand.setDemandIdType( _esbNotif.getDemandIdType(  ) );
            _demand.setDemandMaxStep( -1 );
            _demand.setDemandUserCurrentStep( -1 );
            _demand.setDemandState( _esbNotif.getDemandState(  ) );
            _demand.setNotifType( _esbNotif.getNotificationType(  ) );
            _demand.setDateDemand( "NON RENSEIGNE" );
            _demand.setCRMStatus( _esbNotif.getCrmStatusId(  ) );
            _demand.setReference( "NON RENSEIGNE" );

            GRUService.getService(  ).store( _demand );

            // Parse to Notification
            Notification _notification = new Notification(  );
            _notification.setDemandeId( _esbNotif.getDemandeId(  ) );
            _notification.setDemandIdType( _esbNotif.getDemandIdType(  ) );
            _notification.setUserEmail( _esbNotif.getUserEmail(  ) );
            _notification.setUserDashBoard( _esbNotif.getUserDashBoard(  ) );
            _notification.setUserSms( _esbNotif.getUserSms(  ) );
            _notification.setUserBackOffice( _esbNotif.getUserBackOffice(  ) );

            GRUService.getService(  ).store( _notification );
        }
        catch ( JSONException e )
        {
            e.getMessage(  );

            return GruSupplyConstants.STATUS_404;
        }

        return GruSupplyConstants.STATUS_201;
    }
}
