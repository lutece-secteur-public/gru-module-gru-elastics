package fr.paris.lutece.plugins.grusupply.service;

import fr.paris.lutece.plugins.gru.business.customer.Customer;
import fr.paris.lutece.portal.service.spring.SpringContextService;

public class CustomerService {

    private static final String BEAN_CUSTOMER_INFO_SERVICE = "grusupply.customerinfoService";
    private static ICustomerInfoService _customerInfoService;
    private static CustomerService _singleton;
    
    private CustomerService( )
    {
    	
    }
    
    public static CustomerService instance( )
    {
        if ( _singleton == null )
        {
            _singleton = new CustomerService(  );
            _customerInfoService = SpringContextService.getBean( BEAN_CUSTOMER_INFO_SERVICE );
        }

        return _singleton;  	
    }
    
    public Customer getCustomerByGuid( String strGid )
   	{
   		return _customerInfoService.getCustomerByGuid(strGid);
   	}

    public Customer getCustomerByCid( String strCid )
   	{
   		return _customerInfoService.getCustomerByCid(strCid);
   	}	

    public Customer createCustomer( Customer c)
   	{
   		return _customerInfoService.createCustomer(c);
   	}   
}
