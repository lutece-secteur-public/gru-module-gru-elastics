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

import com.drew.lang.StringUtil;
import com.mysql.jdbc.StringUtils;

import java.io.IOException;
import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path( RestConstants.BASE_PATH + GruSupplyConstants.PLUGIN_NAME )
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
            // Format from JSON
        	ObjectMapper esbMapper = new ObjectMapper( );
        	esbMapper.configure( Feature.UNWRAP_ROOT_VALUE, true );
            NotificationDTO _notif = esbMapper.readValue( strJson, NotificationDTO.class );

            // Find CID in GRU Database
            fr.paris.lutece.plugins.gru.business.customer.Customer gruCustomer = null;
            
            	// CASE 1 NOT CID
            if(StringUtils.isNullOrEmpty(_notif.getCustomerid()))
            {
            	// CASE 1.1 : no cid and no guid:  break the flux and wait for a new flux with one of them
            	if(StringUtils.isNullOrEmpty(_notif.getUserGuid()))
    			{
            		AppLogService.error("Need for Guid and Cid");
    			}
            	// CASE 1.2  : no cid and guid:  look for a mapping beween an existing guid
            	else
            	{
                	gruCustomer = fr.paris.lutece.plugins.gru.business.customer.CustomerHome.findByGuid(_notif.getUserGuid());
                	if(gruCustomer == null)
                	{
                		String strGuid = _notif.getUserGuid(  );
                		
                		// TODO recup info openAM
                		UserDTO userDto = GRUService.instance(  ).getUserInfo( strGuid );
                		
                		// insertion gru
                		gruCustomer = new fr.paris.lutece.plugins.gru.business.customer.Customer();
                		gruCustomer.setFirstname(userDto.getFirstname());
                		gruCustomer.setLastname(userDto.getLastname());
                		gruCustomer.setEmail(userDto.getEmail());
                		gruCustomer.setAccountGuid(strGuid);
                		gruCustomer = fr.paris.lutece.plugins.gru.business.customer.CustomerHome.create(gruCustomer);
                	}
            	}
            }
        	// CASE 2 : cid and (guid or no guid):  find customer info in GRU database
            else
            {
            	gruCustomer = fr.paris.lutece.plugins.gru.business.customer.CustomerHome.findByPrimaryKey(Integer.parseInt(_notif.getCustomerid()));
            }
          
            // Parse to Customer (TODO HAVE TO ADD WITH OPENAM)
            GRUService.instance(  ).store( parseCustomer( gruCustomer ) );

            // Parse to Demand
            GRUService.instance(  ).store( parseDemand( _notif, gruCustomer.getId( ) ) );

            // Parse to Notification
            GRUService.instance(  ).store( parseNotif( _notif ) );
            
            
        }catch (JsonParseException ex) {
        	AppLogService.error( ex + " :" + ex.getMessage(  ), ex );
            return GruSupplyConstants.STATUS_404;			
		} catch (JsonMappingException ex) {
			AppLogService.error( ex + " :" + ex.getMessage(  ), ex );
	        return GruSupplyConstants.STATUS_404;
		} catch (IOException ex) {
			AppLogService.error( ex + " :" + ex.getMessage(  ), ex );
	        return GruSupplyConstants.STATUS_404;
		}

        return GruSupplyConstants.STATUS_201;
    }
    /**
     * Method which create a demand from OpenAm, a flux and GRU database
     * @param gruCustomer
     * @return
     */
    private static Customer parseCustomer( fr.paris.lutece.plugins.gru.business.customer.Customer gruCustomer)
    {
    	Customer grusupplyCustomer = new Customer(  );
        if ( gruCustomer != null )
        {
        	grusupplyCustomer.setCustomerId( gruCustomer.getId( ) );
        	grusupplyCustomer.setName( gruCustomer.getLastname(  ) );
        	grusupplyCustomer.setFirstName( gruCustomer.getFirstname(  ) );
        /*	grusupplyCustomer.setBirthday( gruCustomer.getBirthday(  ) );
        	grusupplyCustomer.setCivility( gruCustomer.getCivility(  ) );
        	grusupplyCustomer.setStreet( gruCustomer.getStreet(  ) );
        	grusupplyCustomer.setCityOfBirth( gruCustomer.getCityOfBirth(  ) );
        	grusupplyCustomer.setCity( gruCustomer.getCity(  ) );
        	grusupplyCustomer.setPostalCode( gruCustomer.getPostalCode(  ) );
        	grusupplyCustomer.setTelephoneNumber( gruCustomer.getTelephoneNumber(  ) );*/
        	grusupplyCustomer.setEmail( gruCustomer.getEmail( ) );
        }
        grusupplyCustomer.setStayConnected( true );
        return grusupplyCustomer;
    }
    /**
     * Method which create a demand from an flux
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
     * @param notifDTO
     * @return
     */
    private static Notification parseNotif( NotificationDTO notifDTO)
    {
        Notification notification = new Notification(  );
        notification.setDemandeId( notifDTO.getDemandeId(  ) );
        notification.setDemandIdType( notifDTO.getDemandIdType(  ) );
        notification.setUserEmail( notifDTO.getUserEmail(  ) );
        notification.setUserDashBoard( notifDTO.getUserDashBoard(  ) );
        notification.setUserSms( notifDTO.getUserSms(  ) );
        notification.setUserBackOffice( notifDTO.getUserBackOffice(  ) );
        return notification;
    }
}
