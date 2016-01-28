package fr.paris.lutece.plugins.grusupply.service;

import fr.paris.lutece.plugins.grusupply.business.Customer;
import fr.paris.lutece.plugins.grusupply.business.Demand;
import fr.paris.lutece.plugins.grusupply.business.Notification;
import fr.paris.lutece.portal.service.spring.SpringContextService;

public class StorageService {
    private static final String BEAN_STORAGE_SERVICE = "grusupply.storageService";
    private static StorageService _singleton;
    private static INotificationStorageService _notificationStorageService;

    private StorageService( )
    {
    	
    }
    
    public static StorageService instance(  )
    {
        if ( _singleton == null )
        {
            _singleton = new StorageService(  );
            _notificationStorageService = SpringContextService.getBean( BEAN_STORAGE_SERVICE );
        }

        return _singleton;
    }
    
    /**
     * Store notification
     * @param notification
     */
    public void store( Notification notification )
    {
        _notificationStorageService.store( notification );
    }

    /**
     * Store user
     * @param user
     */
    public void store( Customer user )
    {
        _notificationStorageService.store( user );
    }

    /**
     * Store demand
     * @param demand
     */
    public void store( Demand demand )
    {
        _notificationStorageService.store( demand );
    }
}
