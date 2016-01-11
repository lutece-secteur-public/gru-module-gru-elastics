package fr.paris.lutece.plugins.gru.modules.elastics.util;



import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import fr.paris.lutece.plugins.gru.modules.elastics.util.constants.GRUElasticsConstants;
import fr.paris.lutece.portal.service.util.AppPropertiesService;



public class ElasticSearchHttpRequest {

	//Client Http
	private static Client client = Client.create();
	private static ObjectMapper mapper = new ObjectMapper();
	////////////////////////////////////////////////////////////////////////////////CREATE ///////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * method which permit to insert a Demand 
	 * @param demand json string which represent the demand
	 * @return elasticsearch response
	 */
	public static String insertDemand( String demand ) 
	{
		String output = "";
		try {
			Map<String, Object> jsonDemand = mapper.readValue(demand, Map.class);
			WebResource resource = client.resource(AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_SERVER) + AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_TYPE_DEMAND) + jsonDemand.get("reference") );
			ClientResponse response = resource.put(ClientResponse.class,demand);
			output = response.getEntity(String.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;
	}
	/**
	 * method which permit to insert a User
	 * @param user json string which represent the user
	 * @return elasticsearch response
	 */
	public static String insertUser( String user )
	{
		String output = "";
		try {
			Map<String, Object> jsonUser = mapper.readValue(user, Map.class);
			WebResource resource = client.resource(AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_SERVER) + AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_TYPE_USER) + jsonUser.get("user_guid"));
			ClientResponse response = resource.post(ClientResponse.class,user);
			output = response.getEntity(String.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return output;
	}
	/**
	 * method which permit to insert a Notification
	 * @param notification json string which represent the notification
	 * @return elasticsearch response
	 */
	public static String insertNotification( String notification )
	{
		String output = "";
		try {
			Map<String,Object> jsonNotification = mapper.readValue(notification, Map.class);
			Map<String,String> jsonSollicitation = (Map<String,String>) jsonNotification.get("sollicitation");
			WebResource resource = client.resource(AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_SERVER) + AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_TYPE_NOTIFICATION) + "1108");
			ClientResponse response = resource.post(ClientResponse.class,notification);
			output = response.getEntity(String.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////GETTERS////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * method which permit to get a demand by its id
	 * @param strIdDemand the Id of the demand
	 * @return the demand from elasticsearch
	 */
	public static String getDemandbyId(String strIdDemand){
		WebResource resource = client.resource(AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_SERVER) + AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_TYPE_DEMAND) + strIdDemand+GRUElasticsConstants.PATH_ELK_SOURCE);
		ClientResponse response = resource.get(ClientResponse.class);
		String output = response.getEntity(String.class);
		return output;
	}
	/**
	 * method which permit to get a user by its Id
	 * @param strGuId the Id of the user
	 * @return the user from elasticsearch
	 */
	public static String getUserbyGuId(String strGuId){
		WebResource resource = client.resource(AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_SERVER) + AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_TYPE_USER)+strGuId+GRUElasticsConstants.PATH_ELK_SOURCE);
		ClientResponse response = resource.get(ClientResponse.class);
		String output = response.getEntity(String.class);
		return output;
	}
	/**
	 * method which permit to get a notification by its id
	 * @param strIdNotification the Id of the notification
	 * @return the notification from elasticsearch
	 */
	public static String getNotificationById(String strIdNotification){
		WebResource resource = client.resource(AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_SERVER)+ AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_TYPE_NOTIFICATION) + strIdNotification+GRUElasticsConstants.PATH_ELK_SOURCE);
		ClientResponse response = resource.get(ClientResponse.class);
		String output = response.getEntity(String.class);
		return output;
	
	}

////////////////////////////////////////////////////////////////////////////////////////////DELETE//////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * method which permit to delete a demand by its id
	 * @param strIdDemand the Id of a demand
	 * @return the response of Elasticsearch
	 */
	public static String deleteDemandbyId(String strIdDemand){
		WebResource resource = client.resource(AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_SERVER)+AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_TYPE_DEMAND) + strIdDemand);
		ClientResponse response = resource.delete(ClientResponse.class);
		String output = response.getEntity(String.class);
		return output;
	
	}
	/**
	 * method which permit to delete a User by its id
	 * @param strGuId the Id of a User
	 * @return the response of elasticsearch
	 */
	public static String deleteUserbyGuId(String strGuId){
		WebResource resource = client.resource(AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_SERVER) + AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_TYPE_USER) + strGuId);
		ClientResponse response = resource.delete(ClientResponse.class);
		String output = response.getEntity(String.class);
		return output;
	}
	/**
	 * method which permit to delete a Notification by its id
	 * @param strIdNotification the Id of a Notification
	 * @return the response of elasticsearch
	 */
	public static String deleteNotificationbyId(String strIdNotification){
		WebResource resource = client.resource(AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_SERVER) + AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_TYPE_NOTIFICATION)+ strIdNotification);
		ClientResponse response = resource.delete(ClientResponse.class);
		String output = response.getEntity(String.class);
		return output;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////UPDATE///////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * method which permit to update a Demand by its id
	 * @param demand json string which represents the demand
	 * @param strIdDemand the Id of the demand
	 * @return response of elasticsearch
	 */
	public static String updateDemandById(String demand, String strIdDemand) {
		WebResource resource = client.resource(AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_SERVER) + AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_TYPE_DEMAND) + strIdDemand + GRUElasticsConstants.PATH_ELK_UPDATE);
		ClientResponse response = resource.post(ClientResponse.class,demand);
		String output = response.getEntity(String.class);
		return output;
	}
	/**
	 * method which permit to update a User by its id
	 * @param user json string which represents the user
	 * @param strIdUser the Id of the user
	 * @return response of elasticsearch
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static String updateUserByGuId(String user, String strIdUser) {
		WebResource resource = client.resource(AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_SERVER)+ AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_TYPE_USER) + strIdUser+GRUElasticsConstants.PATH_ELK_UPDATE);
		ClientResponse response = resource.post(ClientResponse.class,user);
		String output = response.getEntity(String.class);
		return output;
	}
	/**
	 * method which permit to update a Notification by its id
	 * @param notification json string which represents the notification
	 * @param strIdDemand the id of the notification
	 * @return response of elasticsearch
	 */
	public static String UpdateNotificationByIdDemand(String notification, String strIdDemand) {
		WebResource resource = client.resource(AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_SERVER) + AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_TYPE_NOTIFICATION) + strIdDemand + GRUElasticsConstants.PATH_ELK_UPDATE);
		ClientResponse response = resource.post(ClientResponse.class,notification);
		String output = response.getEntity(String.class);
		return output;	
	}
	
//////////////////////////////////////////////////////////////////AUTOCOMPLETION//////////////////////////////////////////////////////////////////////
	/**
	 * method which permit to send an autocomplete request to elasticsearch
	 * @param autocompleteRequest  the autocomplete request
	 * @return response of elasticsearch
	 */
	public static String autocomplete(String autocompleteRequest) {
		WebResource resource = client.resource(AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_SERVER) + AppPropertiesService.getProperty(GRUElasticsConstants.PATH_ELK_SUGGEST));
		ClientResponse response = resource.post(ClientResponse.class,autocompleteRequest);
		String output = response.getEntity(String.class);
		return output;	
	}
	
}
