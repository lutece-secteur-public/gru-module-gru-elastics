package fr.paris.lutece.plugins.gru.modules.elastics.util;



import java.util.concurrent.ExecutionException;

import org.codehaus.jettison.json.JSONObject;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.index.query.QueryBuilders;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import fr.paris.lutece.plugins.gru.modules.elastics.util.constants.GRUElasticsConstants;


public class ElasticSearchHttpRequest {

	
	private static Client client = Client.create();
	
	////////////////////////////////////////////////////////////////////////////////CREATE INDEX///////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param demand
	 * @return
	 */
	public static String insertDemand( String demand ) 
	{
		WebResource resource = client.resource(GRUElasticsConstants.PATH_ELK_SERVER_DEMAND);
		ClientResponse response = resource.post(ClientResponse.class,demand);
		String output = response.getEntity(String.class);
		return output;
		
	}
	/**
	 * 
	 * @param user
	 * @return
	 */
	public static String insertUser( String user )
	{
		WebResource resource = client.resource(GRUElasticsConstants.PATH_ELK_SERVER_USER);
		ClientResponse response = resource.post(ClientResponse.class,user);
		String output = response.getEntity(String.class);
		return output;
	}
	/**
	 * 
	 * @param notification
	 * @return
	 */
	public static String insertNotification( String notification )
	{
		WebResource resource = client.resource(GRUElasticsConstants.PATH_ELK_SERVER_NOTIFICATION);
		ClientResponse response = resource.post(ClientResponse.class,notification);
		String output = response.getEntity(String.class);
		return output;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////GETTERS////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param strIdDemand
	 * @return
	 */
	public static String getDemandbyId(String strIdDemand){
		WebResource resource = client.resource(GRUElasticsConstants.PATH_ELK_SERVER_DEMAND+strIdDemand+GRUElasticsConstants.PATH_ELK_SOURCE);
		ClientResponse response = resource.get(ClientResponse.class);
		String output = response.getEntity(String.class);
		return output;
	}
	/**
	 * 
	 * @param strGuId
	 * @return
	 */
	public static String getUserbyGuId(String strGuId){
		WebResource resource = client.resource(GRUElasticsConstants.PATH_ELK_SERVER_USER+strGuId+GRUElasticsConstants.PATH_ELK_SOURCE);
		ClientResponse response = resource.get(ClientResponse.class);
		String output = response.getEntity(String.class);
		return output;
	}
	/**
	 * 
	 * @param strIdNotification
	 * @return
	 */
	public static String getNotificationById(String strIdNotification){
		WebResource resource = client.resource(GRUElasticsConstants.PATH_ELK_SERVER_NOTIFICATION+strIdNotification+GRUElasticsConstants.PATH_ELK_SOURCE);
		ClientResponse response = resource.get(ClientResponse.class);
		String output = response.getEntity(String.class);
		return output;
	
	}

////////////////////////////////////////////////////////////////////////////////////////////DELETE//////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param strIdDemand
	 * @return
	 */
	public static String deleteDemandbyId(String strIdDemand){
		WebResource resource = client.resource(GRUElasticsConstants.PATH_ELK_SERVER_DEMAND+strIdDemand);
		ClientResponse response = resource.delete(ClientResponse.class);
		String output = response.getEntity(String.class);
		return output;
	
	}
	/**
	 * 
	 * @param strGuId
	 * @return
	 */
	public static String deleteUserbyGuId(String strGuId){
		WebResource resource = client.resource(GRUElasticsConstants.PATH_ELK_SERVER_USER+strGuId);
		ClientResponse response = resource.delete(ClientResponse.class);
		String output = response.getEntity(String.class);
		return output;
	}
	/**
	 * 
	 * @param strIdDemand
	 * @return
	 */
	public static String deleteNotificationbyId(String strIdNotification){
		WebResource resource = client.resource(GRUElasticsConstants.PATH_ELK_SERVER_NOTIFICATION+strIdNotification);
		ClientResponse response = resource.delete(ClientResponse.class);
		String output = response.getEntity(String.class);
		return output;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////UPDATE///////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param demand
	 * @param strIdDemand
	 * @return
	 */
	public static String updateDemandById(String demand, String strIdDemand) {
		WebResource resource = client.resource(GRUElasticsConstants.PATH_ELK_SERVER_DEMAND+strIdDemand+GRUElasticsConstants.PATH_ELK_UPDATE);
		ClientResponse response = resource.post(ClientResponse.class,demand);
		String output = response.getEntity(String.class);
		return output;
	}
	/**
	 * 
	 * @param user
	 * @param strIdUser
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static String updateUserByGuId(String user, String strIdUser) {
		WebResource resource = client.resource(GRUElasticsConstants.PATH_ELK_SERVER_USER+strIdUser+GRUElasticsConstants.PATH_ELK_UPDATE);
		ClientResponse response = resource.post(ClientResponse.class,user);
		String output = response.getEntity(String.class);
		return output;
	}
	/**
	 * 
	 * @param notification
	 * @param strIdDemand
	 * @return
	 */
	public static String UpdateNotificationByIdDemand(String notification, String strIdDemand) {
		WebResource resource = client.resource(GRUElasticsConstants.PATH_ELK_SERVER_NOTIFICATION+strIdDemand+GRUElasticsConstants.PATH_ELK_UPDATE);
		ClientResponse response = resource.post(ClientResponse.class,notification);
		String output = response.getEntity(String.class);
		return output;	
	}
}
