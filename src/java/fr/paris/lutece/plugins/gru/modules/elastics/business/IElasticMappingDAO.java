package fr.paris.lutece.plugins.gru.modules.elastics.business;

import fr.paris.lutece.portal.service.plugin.Plugin;

public interface IElasticMappingDAO {
	/**
	 * 
	 * @param plugin
	 * @return
	 */
	public int newPrimaryKey( Plugin plugin );
	    /**
	     * Insert a new record in the table.
	     * @param mapping instance of the Customer object to insert
	     * @param plugin the Plugin
	     */
	    void insert( ElasticMapping mapping, Plugin plugin );

	    /**
	     * Update the record in the table
	     * @param  mapping the reference of the Customer
	     * @param plugin the Plugin
	     */
	    void store( ElasticMapping mapping, Plugin plugin );

	    /**
	     * Delete a record from the table
	     * @param nKey The identifier of the Customer to delete
	     * @param plugin the Plugin
	     */
	    void delete( int nKey, Plugin plugin );

	    ///////////////////////////////////////////////////////////////////////////
	    // Finders

	    /**
	     * Load the data from the table
	     * @param nKey The identifier of the customer
	     * @param plugin the Plugin
	     * @return The instance of the customer
	     */
	    ElasticMapping load( int nKey, Plugin plugin );

	
	    /**
	     * Load the data from the table 
	     * @param nKey the  identifier of the customer
	     * @param plugin
	     * @return
	     */
	    ElasticMapping loadByUserId(int nKey, Plugin plugin);
}   
