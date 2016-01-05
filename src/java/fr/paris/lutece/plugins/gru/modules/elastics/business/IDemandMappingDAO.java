package fr.paris.lutece.plugins.gru.modules.elastics.business;

import fr.paris.lutece.portal.service.plugin.Plugin;

public interface IDemandMappingDAO {
		/**
		 * 
		 * @param mapping
		 * @param plugin
		 */
	    void insert( DemandMapping mapping, Plugin plugin );

	    /**
	     * Update the record in the table
	     * @param  mapping the reference of the Customer
	     * @param plugin the Plugin
	     */
	    void store( DemandMapping mapping, Plugin plugin );

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
	    DemandMapping load( int nKey, Plugin plugin );

	
	    /**
	     * Load the data from the table 
	     * @param nKey the  identifier of the customer
	     * @param plugin
	     * @return
	     */
	    DemandMapping loadByCustomerId(int nKey, Plugin plugin);
	    /**
	     * 
	     * @param strIdDemand
	     * @param demandIdType
	     * @param plugin
	     * @return
	     */
	    public DemandMapping loadbyIdDemand(String strIdDemand,int demandIdType, Plugin plugin);
}   

