package fr.paris.lutece.plugins.gru.modules.elastics.util;


import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;

import org.codehaus.jettison.json.JSONObject;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;



public class ElasticSearchRequest
{
	Settings settings = ImmutableSettings.settingsBuilder()
		    .put("client.transport.sniff", true)
		    /*.put("cluster.name", "Jean Grey-Summers")*/.build();
	
	private	Client client = new TransportClient()
		.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("localhost",9300)));
	
	/**
	 * 
	 * @param demand
	 * @return
	 */
	public String insertDemand( String demand )
	{
		//BulkRequestBuilder bulkRequest = client.prepareBulk();
			IndexResponse response= client.prepareIndex("teleservice", "demand")
					.setSource(demand).get();
			
		return response.toString();
	}
	/**
	 * 
	 * @param user
	 * @return
	 */
	public String insertUser( String user )
	{
		//BulkRequestBuilder bulkRequest = client.prepareBulk();
			IndexResponse response= client.prepareIndex("teleservice", "user").
					setSource(user).get();
			
		return response.toString();
	}
	/**
	 * 
	 * @param notification
	 * @return
	 */
	public String insertNotification( String notification )
	{
		//BulkRequestBuilder bulkRequest = client.prepareBulk();
			IndexResponse response= client.prepareIndex("teleservice", "notification")
					.setSource(notification).get();
			
		return response.toString();
	}
	/**
	 * 
	 * @param strIdDemand
	 * @return
	 */
	public String getDemandbyId(String strIdDemand){
		GetResponse response = client.prepareGet("teleservice", "demand", strIdDemand).get();
		return response.toString();
	}
	
	/**
	 * 
	 * @param strGuId
	 * @return
	 */
	public String getUserbyGuId(String strGuId){
		GetResponse response = client.prepareGet("teleservice", "user", strGuId).get();
		return response.toString();
	}
	/**
	 * 
	 * @param strGuId
	 * @return
	 */
	public GetResponse getNotificationByDemand(String strIdDemand){
		GetResponse response = client.prepareGet("teleservice", "notification.sollicitation", strIdDemand).get();
		return response;
	}
	/**
	 * 
	 * @param strIdDemand
	 * @return
	 */
	public String DeleteDemandbyId(String strIdDemand){
		DeleteResponse response = client.prepareDelete("teleservice", "demand",strIdDemand).get();
		return response.toString();
	}
	/**
	 * 
	 * @param strGuId
	 * @return
	 */
	public String DeleteUserbyGuId(String strGuId){
		DeleteResponse response = client.prepareDelete("teleservice", "user",strGuId).get();
		return response.toString();
	}
	
	/**
	 * 
	 * @param strIdDemand
	 * @return
	 */
	public String DeleteNotificationbyIdDemand(String strIdDemand){
		DeleteResponse response = client.prepareDelete("teleservice", "notification",strIdDemand).get();
	return response.toString();	
	}
	/**
	 * 
	 * @param demand
	 * @param strIdDemand
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String UpdateDemandById(JSONObject demand, String strIdDemand) throws InterruptedException, ExecutionException{
		UpdateRequest updateRequest= new UpdateRequest("teleservice", "demand", strIdDemand)
				.doc(demand);
			UpdateResponse response=client.update(updateRequest).get();
	
		return response.toString();
	}
	/**
	 * 
	 * @param user
	 * @param strIdUser
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String UpdateUserByGuId(JSONObject user, String strIdUser) throws InterruptedException, ExecutionException{
		UpdateRequest updateRequest= new UpdateRequest("teleservice", "user", strIdUser).doc(user);
			UpdateResponse response=client.update(updateRequest).get();
	
		return response.toString();
	}
	/**
	 * 
	 * @param notification
	 * @param strIdDemand
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String UpdateNotificationByIdDemand(JSONObject notification, String strIdDemand) throws InterruptedException, ExecutionException{
		UpdateRequest updateRequest= new UpdateRequest("teleservice", "user", strIdDemand).doc(notification);
		UpdateResponse response=client.update(updateRequest).get();
		return response.toString();
		
	}
	
	
///////////////////////////////////////////////////SEARCH REQUESTS//////////////////////////////////////////////////////////////////
/**
 * 
 * @param strIdDemand
 * @param strIdDemandType
 * @return
 */
	public SearchResponse getDemandFromSearch(String strIdDemand, String strIdDemandType){
		SearchResponse response = client.prepareSearch("teleservice")
				.setTypes("demand")
				.setQuery(QueryBuilders.termQuery("demand_id", strIdDemand))
				//.setPostFilter(FilterBuilders.termFilter("demand_id_type", strIdDemandType))
				//.setPostFilter(QueryBuilders.termQuery("demand_id", strIdDemand))
				//.addAggregation(AggregationBuilders.terms(strIdDemandType).field("demand_id_type"))
				.execute()
				.actionGet();
		return response;
	}
	/**
	 * 
	 * @param strGuId
	 * @return
	 */
	public SearchResponse getListDemandFromSearch(String strGuId){
		SearchResponse response = client.prepareSearch("teleservice")
				.setTypes("demand")
				.setQuery(QueryBuilders.matchAllQuery())
				//.setPostFilter(FilterBuilders.termFilter("utilisateur.user_guid", strGuId))
				//.setPostFilter(QueryBuilders.termQuery("utilisateur.user_guid", strGuId))
				.execute()
				.actionGet();
		return response;
	}
	/**
	 * 
	 * @param strPhone
	 * @return
	 */
	public SearchResponse getUserbyPhoneNumber(String strPhone){
		SearchResponse response = client.prepareSearch("teleservice")
				.setTypes("user")
				.setQuery(QueryBuilders.termQuery("telephoneNumber", strPhone))
				.execute()
				.actionGet();
		return response;
	}
	/**
	 * 
	 * @param strName
	 * @return
	 */
	public SearchResponse getUserbyName(String strName)
	{
		SearchResponse response = client.prepareSearch("teleservice")
				.setTypes("teleservice")
				.setQuery(QueryBuilders.termQuery("name", strName))
				.execute()
				.actionGet();
		return response;
	}
}