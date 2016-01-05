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
package fr.paris.lutece.plugins.gru.modules.elastics.rs;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;

import fr.paris.lutece.plugins.gru.business.customer.Customer;
import fr.paris.lutece.plugins.gru.business.customer.CustomerHome;
import fr.paris.lutece.plugins.gru.business.demand.Demand;
import fr.paris.lutece.plugins.gru.modules.elastics.business.DemandMapping;
import fr.paris.lutece.plugins.gru.modules.elastics.business.DemandMappingHome;
import fr.paris.lutece.plugins.gru.modules.elastics.business.ElasticMapping;
import fr.paris.lutece.plugins.gru.modules.elastics.business.ElasticMappingHome;
import fr.paris.lutece.plugins.gru.modules.elastics.util.ElasticSearchHttpRequest;
import fr.paris.lutece.plugins.gru.modules.elastics.util.ElasticsearchDemandService;
import fr.paris.lutece.plugins.gru.modules.elastics.util.constants.GRUElasticsConstants;
import fr.paris.lutece.plugins.rest.service.RestConstants;
import fr.paris.lutece.portal.business.user.AdminUser;


/**
 *
 * GRUElasticsRest
 *
 */
@Path( RestConstants.BASE_PATH + GRUElasticsConstants.PLUGIN_NAME )
public class GRUElasticsRest
{  
	ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 
	 * @return
	 * @throws JSONException
	 */
	
	@POST
    @Path( GRUElasticsConstants.PATH_NOTIFICATION )
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes( MediaType.APPLICATION_JSON )
    public String notification ( String strJson) throws JSONException
    {
    	
    	try {
    		
    		ElasticMapping mapping;
    		DemandMapping demandMapping = new DemandMapping();
    		Map<String, Object> resultDemand = null;
    		Map<String,Object> resultUser = null;
    		Map<String, Object> resultNotification = null;
			Map<String,Object> flux = mapper.readValue(strJson, Map.class);
			Map<String,Object> notification =(Map<String,Object>) flux.get("notification");
			Map<String,String> userEmail = (Map<String,String>)notification.get("user_email");
			Map<String,String> userDashboard = (Map<String,String>)notification.get("user_dashboard");
			Map<String,String> userSMS = (Map<String,String>)notification.get("user_sms");
			Map<String,Object> backofficeLogging = (Map<String,Object>)notification.get("backoffice_logging");
			
			if(StringUtils.isBlank(notification.get("user_guid").toString()) || ElasticMappingHome.findByUserId((Integer)notification.get("user_guid")) == null )
			{	
					Customer customer = new Customer();
					customer.setAccountGuid("100");
					customer.setAccountLogin("john");
					customer.setEmail("john.doe@somewhere.com");
					customer.setExtrasAttributes("");
					customer.setFirstname("John");
					customer.setHasAccount(true);
					customer.setIdTitle(12);
					customer.setIsEmailVerified(true);
					customer.setIsMobilePhoneVerified(true);
					customer.setLastname("Doe");
					customer.setMobilePhone("0681795994");
					
					customer= CustomerHome.create(customer);
					
					mapping = new ElasticMapping();
					mapping.setId_customer(customer.getId());
					mapping.setId_user((Integer)notification.get("user_guid"));
					demandMapping.setCustomerId(customer.getId());
					ElasticMappingHome.create(mapping);
			}
			else
			{
				mapping=ElasticMappingHome.findByUserId((Integer)notification.get("user_guid"));
				demandMapping.setCustomerId(mapping.getId_customer());
			}
			
			resultDemand = doCreateDemand(notification, userSMS);
			resultUser = doCreateUser(notification, userSMS);
			resultNotification = doCreateNotification(notification, backofficeLogging, userEmail, userDashboard, userSMS);
			
			if((Boolean)resultUser.get("created") && (Boolean)resultDemand.get("created") && (Boolean)resultNotification.get("created"))
			{	
				// mapping des users
				mapping.setStrRefUser(resultUser.get("_id").toString());
				ElasticMappingHome.update(mapping);
				
				//mapping des 
				demandMapping.setDemandTypeId((Integer)notification.get("demand_id_type"));
				demandMapping.setStrDemand_id(notification.get("demand_id").toString());
				demandMapping.setStrElasticsearch_id(resultDemand.get("_id").toString());
				demandMapping.setRefNotification(resultNotification.get("_id").toString());
				DemandMappingHome.create(demandMapping);
				
				return "{"+"\"status\":"+"\"201\""+"}";		
			}
			else
			{
				return "{"+"\"status\":"+"\"404\""+"}";
			}
			
    	} 
			catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return null;

    }
    /**
     * 
     * @param notification
     * @param userSMS
     * @return
     */
    @POST
    @Path( "demande" )
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes( MediaType.APPLICATION_JSON )
    public Map<String,Object> doCreateDemand(Map<String,Object> notification, Map<String,String> userSMS){
    	Map<String, Object> mapResult = null;
    	String demand= "{\n" + 
    						"\"utilisateur\":\n" +	"{\n" + 
    									"\"user_guid\":"+ "\""+notification.get("user_guid")+"\"" + "\n" + 
    								"},\n" + 
    						"\"demand_id\":" + "\""+notification.get("demand_id")+"\"" + ",\n" +
    						"\"demand_id_type\":" + notification.get("demand_id_type")  + ",\n" +
    						"\"demand_max_step\":"+ notification.get("demand_max_step") + ",\n" + 
    						"\"demand_user_current_step\":"+ notification.get("demand_user_current_step") + ",\n" +
    						"\"demande_state\":"+ notification.get("demand_state") + ",\n" +
    						"\"notification_type\":"+ "\""+notification.get("notification_type")+"\"" + ",\n" +
    						"\"date_demande\":\"2015-03-31\",\n" + 
    						"\"crm_status_id\":" + notification.get("crm_status_id") + ",\n" + 
    						"\"reference\": \"PZQu4rocRy60hO2seUEziQ\",\n" +
    						"\"suggest\":\n"+"{\n"+
    								"\"input\":"+"["+"\"PZQu4rocRy60hO2seUEziQ\""+"],\n"+
    								"\"output\": \"John Doe\",\n" +
    								"\"payload\":\n"+"{\n" + 
    									"\"user_guid\":"+ "\""+notification.get("user_guid")+"\"" + ",\n" + 
    									"\"birthday\":\"20/03/1980\",\n"+
    									"\"telephoneNumber\":"+ "\""+userSMS.get("phone_number")+"\"" + ",\n" +
    									"\" email\":"+ "\""+notification.get("email")+"\"" + ",\n" +
    									"\"reference\":\"PZQu4rocRy60hO2seUEziQ\"\n"+
    								"}\n"+
    							"}\n"+
    						"}";
    	
    	String result =ElasticSearchHttpRequest.insertDemand(demand);
    	try {
			 mapResult = mapper.readValue(result, Map.class);
		} 
    	catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return mapResult;
    }
    /**
     * 
     * @param notification
     * @param userSMS
     * @return
     */
    @POST
    @Path( "utilisateur" )
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes( MediaType.APPLICATION_JSON )
    public Map<String, Object> doCreateUser(Map<String, Object> notification, Map<String, String> userSMS){
    	Map<String,Object> response = null;
    	String user = "{\n"+ 
    			 			"\"user_guid\":"+ "\""+notification.get("user_guid")+"\"" + ",\n" +
    			 			"\"email\":" + "\""+notification.get("email")+"\"" + ",\n" +
    			 			"\"name\": \"John Doe\",\n" + 
    			 			"\"stayConnected\" : \"true\",\n"+
    			 			"\"street\" : \" rue test\",\n"+
    			 			"\"telephoneNumber\" :"+ "\""+userSMS.get("phone_number")+"\"" + ",\n" +
    			 			"\"city\" : \"Paris\",\n"+
    			 			"\"cityOfBirth\" : \"london\",\n"+
    			 			"\"birthday\" : \"20/03/1980\",\n"+
    			 			"\"civility\":\"Mr\",\n" +
    			 			"\"postalCode\" : \"75019\",\n"+
    			 			"\"suggest\":"+"{\n"+
    			 				"\"input\":" + "[" + "\"Ndiambe\","+ "\"Darou\"," + "\""+userSMS.get("phone_number")+"\"" + "," + "\""+notification.get("email")+"\"" + "],\n" + 
    			 				"\"output\": \"Ndiambe Darou\",\n" + 
    			 				"\"payload\":"+ "{" + "\"user_guid\":"+ "\""+notification.get("user_guid")+"\"" + ",\n" +
    			 					"\"birthday\": \"20/03/1980\",\n"+
    			 					"\"telephoneNumber\":" + "\""+userSMS.get("phone_number")+"\"" + ",\n" +
    			 					"\"email\":" + "\""+notification.get("email")+"\"" + "\n" + 
    			 					"}\n" + 
    			 				"}\n"+
    			 		"}";
    	 
    	 String result = ElasticSearchHttpRequest.insertUser(user);
    	 try {
			response = mapper.readValue(result, Map.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
    /**
     * 
     * @param notification
     * @param backofficeLogging
     * @param userEmail
     * @param userDashboard
     * @param userSMS
     * @return
     */
    @POST
    @Path( "creer_notif" )
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes( MediaType.APPLICATION_JSON )
    public Map<String, Object> doCreateNotification(Map<String, Object> notification, Map<String,Object> backofficeLogging, Map<String,String> userEmail,
    												Map<String, String> userDashboard, Map<String,String> userSMS){
    	Map<String,Object> response = null;
    	 String notif = "{\n" + 
    			 			"\"sollicitation\":" + "{\n" +
    			 							"\"demand_id\":" + "\""+notification.get("demand_id")+"\"" + "\n" +
    			 						"},\n" + 
    			 				"\"status_text\":" + "\""+backofficeLogging.get("status_text")+"\"" + ",\n" +
    			 				"\"message\":" + "\"" +backofficeLogging.get("message")+"\"" + ",\n" +
    			 				"\"notified_on_dashboard\":"+ backofficeLogging.get("notified_on_dashboard")+ ",\n" + 
    			 				"\"notified_by_email\":" + backofficeLogging.get("notified_by_email") + ",\n" +
    			 				"\"notified_by_sms\":" + backofficeLogging.get("notified_by_sms") + ",\n" +
    			 				"\"display_level_dashboard_notification\":" +  backofficeLogging.get("display_level_dashboard_notification") + ",\n" +
    			 				"\"view_dashboard_notification\":" + "\""+backofficeLogging.get("view_dashboard_notification")+"\"" + ",\n" +
    			 				"\"display_level_email_notification\":" + backofficeLogging.get("display_level_email_notification") + ",\n" +
    			 				"\"view_email_notification\":" + "\""+backofficeLogging.get("view_email_notification")+"\"" + ",\n" +
    			 				"\"display_level_sms_notification\":" + backofficeLogging.get("display_level_sms_notification") + ",\n" +
    			 				"\"view_sms_notification\":" +  "\""+backofficeLogging.get("view_sms_notification")+"\"" + ",\n" +
    			 				"\"date_sollicitation\":\"2015-03-31\",\n" +
    			 				"\"user_email\": {\n" +	
    			 					"\"sender_name\":" + "\""+userEmail.get("sender_name")+"\"" + ",\n" +	
    			 					"\"sender_email\":" + "\""+ userEmail.get("sender_email")+"\"" + ",\n" +
    			 					"\"recipient\":" + "\""+userEmail.get("recipient")+"\"" + ",\n" +
    			 					"\"subject\":"+ "\""+userEmail.get("subject")+"\"" + ",\n" +
    			 					"\"message\":" + "\""+userEmail.get("message")+"\"" + "\n" + 
    			 				"},\n" + 
    			 				"\"user_dashboard\":{\n" +
    			 					"\"status_text\":" + "\""+userDashboard.get("status_text")+"\"" +  ",\n" +
    			 					"\"sender_name\":" + "\""+userDashboard.get("sender_name")+"\"" +  ",\n" +
    			 					"\"subject\":" + "\""+userDashboard.get("subject")+"\"" + ",\n" +
    			 					"\"message\":" + "\""+userDashboard.get("message")+"\"" + ",\n" + 
    			 					"\"data\":" + "\""+userDashboard.get("data")+"\"" + "\n" + 
    			 				"},\n" + 
    			 				"\"user_sms\": {\n" + 
    			 					"\"phone_number\":" + "\""+userSMS.get("phone_number")+"\"" + ",\n" + 
    			 					"\"message\":" + "\""+userSMS.get("message")+"\"" +
    			 				"}\n" +
    			 			"}";
    	 
    	 String result =ElasticSearchHttpRequest.insertNotification(notif);
    	 try {
			response = mapper.readValue(result, Map.class);
		} 
    	 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
}
