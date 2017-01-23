package fr.paris.lutece.plugins.grusupply.service.daemon;

import java.util.List;

import fr.paris.lutece.plugins.grubusiness.business.demand.Demand;
import fr.paris.lutece.plugins.grubusiness.business.demand.DemandService;
import fr.paris.lutece.plugins.grubusiness.business.indexing.IndexingException;
import fr.paris.lutece.plugins.grusupply.business.daemon.IndexerTask;
import fr.paris.lutece.plugins.grusupply.business.daemon.demand.DemandIndexerAction;
import fr.paris.lutece.plugins.grusupply.business.daemon.demand.DemandIndexerActionFilter;
import fr.paris.lutece.plugins.grusupply.business.daemon.demand.DemandIndexerActionHome;
import fr.paris.lutece.plugins.grusupply.service.IndexService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;

/**
 * Daemon used to index demands in incremental mode
 */
public class DemandIndexerDaemon extends AbstractIndexerDaemon
{
    private static final String BEAN_DEMAND_SERVICE = "grusupply.storageService";
    private static DemandService _demandService;

    /**
     * {@inheritDoc}
     */
    @Override
    protected int indexUpdated( IndexerTask indexerTask )
    {
        _demandService = SpringContextService.getBean( BEAN_DEMAND_SERVICE );
        int nNbIndexedResources = 0;

        DemandIndexerActionFilter demandIndexerActionFilter = new DemandIndexerActionFilter( );
        demandIndexerActionFilter.setIndexerTask( indexerTask );

        List<DemandIndexerAction> listDemandIndexerActions = DemandIndexerActionHome.getList( demandIndexerActionFilter );

        for ( DemandIndexerAction demandIndexerAction : listDemandIndexerActions )
        {
            try
            {
                indexObject( demandIndexerAction, nNbIndexedResources );
            }
            catch( Exception e )
            {
                AppLogService.error( "Unable to get the demand with id " + demandIndexerAction.getDemandId( ) + " and with type id "
                        + demandIndexerAction.getDemandTypeId( ) + " : " + e.getMessage( ) );
            }
        }

        return nNbIndexedResources;
    }

    /**
     * Method use to index an object of type and id specify in the demandIndexerAction
     * 
     * @param demandIndexerAction
     * @param nNbIndexedResources
     */
    private void indexObject( DemandIndexerAction demandIndexerAction, int nNbIndexedResources )
    {
        Demand demand = _demandService.findByPrimaryKey( demandIndexerAction.getDemandId( ), demandIndexerAction.getDemandTypeId( ) );

        if ( demand != null )
        {
            try
            {
                IndexService.instance( ).index( demand );

                nNbIndexedResources++;

                DemandIndexerActionHome.remove( demandIndexerAction.getIdAction( ) );
            }

            catch( IndexingException e )
            {
                AppLogService.error( "Unable to index the demand id " + demandIndexerAction.getDemandId( ) + " : " + e.getMessage( ) );
            }

        }
    }

}
