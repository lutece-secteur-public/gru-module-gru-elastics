package fr.paris.lutece.plugins.gru.modules.elastics.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

public class DemandMappingHome {

	// Static variable pointed at the DAO instance
    private static IDemandMappingDAO _dao = SpringContextService.getBean( "gru-elastics.demandMappingDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "gru-elastics" );
    
    /**
     * Private constructor - this class need not be instantiated
     */
    private DemandMappingHome(  )
    {
    }
    
    /**
     * Create an instance of the DemandMapping class
     * @param mapping The instance of the DemandMapping which contains the informations to store
     * @return The  instance of DemandMapping which has been created with its primary key.
     */
    public static void create(DemandMapping mapping )
    {
        _dao.insert( mapping, _plugin );
    }
    /**
     * Update of the DemandMapping which is specified in parameter
     * @param mapping The instance of the DemandMapping which contains the data to store
     * @return The instance of the DemandMapping which has been updated
     */
    public static void update( DemandMapping mapping )
    {
        _dao.store( mapping, _plugin );
    }
///////////////////////////////////////////////////////////////////////////
//Finders
    /**
     * Returns an instance of a mapping whose identifier is specified in parameter
     * @param nKey The mapping primary key which is the elasticsearch id
     * @return an instance of DemandMapping
     */
    public static DemandMapping findByPrimaryKey( int nKey )
    {
    	return _dao.load( nKey, _plugin );
    }
    /**
     * Returns an instance of a mapping whose user identifier is specified in parameter
     * @param nKey The user identifier
     * @return an instance of ElasticMapping
     */
    public static DemandMapping findByUserId( int nKey )
    {
    	return _dao.loadByCustomerId( nKey, _plugin );
    }
    /**
    * Returns an instance of a mapping whose user identifier is specified in parameter
    * @param nKey The user identifier
    * @return an instance of ElasticMapping
    */
    public static DemandMapping findByDemandId( String strDemandId, int demandIdType )
    {
    	return _dao.loadbyIdDemand(strDemandId, demandIdType, _plugin );
    }
}
