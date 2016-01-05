package fr.paris.lutece.plugins.gru.modules.elastics.business;

public class DemandMapping {

	private String strElasticsearch_id;
	private String strDemand_id;
	private int demandTypeId;
	private int customerId;
	private String strRefNotification;
	/**
	 * 
	 * @return
	 */
	public String getRefNotification() {
		return strRefNotification;
	}
	/**
	 * 
	 * @param refNotification
	 */
	public void setRefNotification(String refNotification) {
		this.strRefNotification = refNotification;
	}
	/**
	 * 
	 * @return
	 */
	public String getStrElasticsearch_id() {
		return strElasticsearch_id;
	}
	/**
	 * 
	 * @param strElasticsearch_id
	 */
	public void setStrElasticsearch_id(String strElasticsearch_id) {
		this.strElasticsearch_id = strElasticsearch_id;
	}
	/**
	 * 
	 * @return
	 */
	public String getStrDemand_id() {
		return strDemand_id;
	}
	/**
	 * 
	 * @param strDemand_id
	 */
	public void setStrDemand_id(String strDemand_id) {
		this.strDemand_id = strDemand_id;
	}
	/**
	 * 
	 * @return
	 */
	public int getDemandTypeId() {
		return demandTypeId;
	}
	/**
	 * 
	 * @param demandTypeId
	 */
	public void setDemandTypeId(int demandTypeId) {
		this.demandTypeId = demandTypeId;
	}
	/**
	 * 
	 * @return
	 */
	public int getCustomerId() {
		return customerId;
	}
	/**
	 * 
	 * @param userId
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
}
