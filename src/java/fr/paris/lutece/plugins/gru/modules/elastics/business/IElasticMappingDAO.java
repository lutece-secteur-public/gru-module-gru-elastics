package fr.paris.lutece.plugins.gru.modules.elastics.business;

import fr.paris.lutece.portal.service.plugin.Plugin;

public interface IElasticMappingDAO {
	/**
	 * 
	 * @param plugin
	 * @return the new primary key
	 */
	public int newPrimaryKey( Plugin plugin );
	    /**
	     * Insert a new mapping in the table.
	     * @param mapping instance of the Customer object to insert
	     * @param plugin the Plugin
	     */
	    void insert( ElasticMapping mapping, Plugin plugin );

	    /**
	     * Update the mapping in the table
	     * @param  mapping the reference of the Mapping
	     * @param plugin the Plugin
	     */
	    void store( ElasticMapping mapping, Plugin plugin );

	    /**
	     * Delete a mapping from the table
	     * @param nKey The identifier of the Mapping to delete
	     * @param plugin the Plugin
	     */
	    void delete( int nKey, Plugin plugin );

	    ///////////////////////////////////////////////////////////////////////////
	    // Finders

	    /**
	     * Load the mapping from the table
	     * @param nKey The identifier of the mapping
	     * @param plugin the Plugin
	     * @return The instance of the mapping
	     */
	    ElasticMapping load( int nKey, Plugin plugin );

	
	    /**
	     * Load the mapping from the table 
	     * @param nKey the  identifier of the mapping
	     * @param plugin
	     * @return
	     */
	    ElasticMapping loadByUserId(int nKey, Plugin plugin);
}   
