package fr.paris.lutece.plugins.gru.modules.elastics.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

public final class ElasticMappingHome {

	// Static variable pointed at the DAO instance
    private static IElasticMappingDAO _dao = SpringContextService.getBean( "gru-elastics.elasticMappingDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "gru-elastics" );
    
    /**
     * Private constructor - this class need not be instantiated
     */
    private ElasticMappingHome(  )
    {
    }
    /**
     * Create an instance of the ElasticMapping class
     * @param mapping The instance of the ElasticMapping which contains the informations to store
     * @return The  instance of ElasticMapping which has been created with its primary key.
     */
    public static void create(ElasticMapping mapping )
    {
        _dao.insert( mapping, _plugin );
    }
    /**
     * Update of the elasticMapping which is specified in parameter
     * @param mapping The instance of the ElasticMapping which contains the data to store
     * @return The instance of the ElasticMapping which has been updated
     */
    public static void update( ElasticMapping mapping )
    {
        _dao.store( mapping, _plugin );
    }
    /**
     * Remove the mapping whose identifier is specified in parameter
     * @param nKey The mapping Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }
    
///////////////////////////////////////////////////////////////////////////
// Finders
    /**
     * Returns an instance of a mapping whose identifier is specified in parameter
     * @param nKey The mapping primary key
     * @return an instance of ElasticMapping
     */
    public static ElasticMapping findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }
    /**
     * Returns an instance of a mapping whose user identifier is specified in parameter
     * @param nKey The user identifier
     * @return an instance of ElasticMapping
     */
    public static ElasticMapping findByUserId( int nKey )
    {
        return _dao.loadByUserId( nKey, _plugin );
    }
    
}
