package fr.paris.lutece.plugins.grusupply.service;

import fr.paris.lutece.plugins.gru.business.customer.Customer;
import fr.paris.lutece.portal.service.spring.SpringContextService;

/**
 * CustomerService
 */
public class CustomerService
{

    private static final String BEAN_CUSTOMER_INFO_SERVICE = "grusupply.customerinfoService";
    private static ICustomerInfoService _customerInfoService;
    private static CustomerService _singleton;

    /** private constructor */
    private CustomerService()
    {
    }

    /**
     * Return the unique instance
     * @return The instance
     */
    public static CustomerService instance()
    {
        if( _singleton == null )
        {
            _singleton = new CustomerService();
            _customerInfoService = SpringContextService.getBean( BEAN_CUSTOMER_INFO_SERVICE );
        }

        return _singleton;
    }

    /**
     * Retrieve a customer by its guid
     * @param strGuid The GUID
     * @return The customer
     */
    public Customer getCustomerByGuid( String strGuid )
    {
        return _customerInfoService.getCustomerByGuid( strGuid );
    }

    /**
     * Retrieve the customer by its ID
     * @param strCid The customer ID
     * @return The customer
     */
    public Customer getCustomerByCid( String strCid )
    {
        return _customerInfoService.getCustomerByCid( strCid );
    }

    /**
     * Create a new customer
     * @param customer The customer
     * @return  The created customer
     */
    public Customer createCustomer( Customer customer )
    {
        return _customerInfoService.createCustomer( customer );
    }
}
