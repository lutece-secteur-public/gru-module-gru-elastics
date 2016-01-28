package fr.paris.lutece.plugins.grusupply.service;

import fr.paris.lutece.plugins.gru.business.customer.Customer;

public interface ICustomerInfoService {

	
	/**
	 * Methode which find a user in gru database
	 * @param guid
	 * @return
	 */
	public Customer getCustomerByGuid( String strGid );
	
	/**
	 * Methode which find a customer by cid
	 * @param cid
	 * @return
	 */
	public Customer getCustomerByCid( String strCid );
	
	/**
	 * Method which create a new customer in gru
	 * @param c
	 * @return
	 */
	public Customer createCustomer( Customer c);
}
