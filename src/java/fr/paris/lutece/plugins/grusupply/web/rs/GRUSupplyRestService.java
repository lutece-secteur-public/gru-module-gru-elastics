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
            // Format to JSON
        	ObjectMapper esbMapper = new ObjectMapper( );
        	esbMapper.configure( Feature.UNWRAP_ROOT_VALUE, true );
            NotificationDTO _notif = esbMapper.readValue( strJson, NotificationDTO.class );

            // Find CID in GRU Database
            fr.paris.lutece.plugins.gru.business.customer.Customer gruCustomer = null;
            Customer grusupplyCustomer = new Customer(  );
            
            	// NOT Customerid IN ESB FLUX
            if(StringUtils.isNullOrEmpty(_notif.getCustomerid()))
            {
            	if(StringUtils.isNullOrEmpty(_notif.getUserGuid()))
    			{
            		AppLogService.error("Need for Guid and Cid");
    			}
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
            else
            {
            	gruCustomer = fr.paris.lutece.plugins.gru.business.customer.CustomerHome.findByPrimaryKey(Integer.parseInt(_notif.getCustomerid()));
            }
          
            // Parse to Customer (TODO HAVE TO ADD WITH OPENAM)

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

            GRUService.instance(  ).store( grusupplyCustomer );

            // Parse to Demand
            Demand demand = new Demand(  );
            demand.setUserCid( grusupplyCustomer.getCustomerId( ) );
            demand.setDemandId( _notif.getDemandeId(  ) );
            demand.setDemandIdType( _notif.getDemandIdType(  ) );
            demand.setDemandMaxStep( -1 );
            demand.setDemandUserCurrentStep( -1 );
            demand.setDemandState( _notif.getDemandState(  ) );
            demand.setNotifType( _notif.getNotificationType(  ) );
            demand.setDateDemand( "NON RENSEIGNE" );
            demand.setCRMStatus( _notif.getCrmStatusId(  ) );
            demand.setReference( "NON RENSEIGNE" );

            GRUService.instance(  ).store( demand );

            // Parse to Notification
            Notification notification = new Notification(  );
            notification.setDemandeId( _notif.getDemandeId(  ) );
            notification.setDemandIdType( _notif.getDemandIdType(  ) );
            notification.setUserEmail( _notif.getUserEmail(  ) );
            notification.setUserDashBoard( _notif.getUserDashBoard(  ) );
            notification.setUserSms( _notif.getUserSms(  ) );
            notification.setUserBackOffice( _notif.getUserBackOffice(  ) );

            GRUService.instance(  ).store( notification );
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
}
