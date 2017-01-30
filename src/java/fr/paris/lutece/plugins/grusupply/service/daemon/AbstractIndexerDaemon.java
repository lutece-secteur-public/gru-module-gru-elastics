package fr.paris.lutece.plugins.grusupply.service.daemon;

import fr.paris.lutece.plugins.grusupply.business.daemon.IndexerTask;
import fr.paris.lutece.portal.service.daemon.Daemon;

/**
 * Abstract class for IndexerAction
 */
public abstract class AbstractIndexerDaemon extends Daemon
{
    protected static final String LOG_INDEX_CREATED = "Number of created indexes : ";
    protected static final String LOG_INDEX_UPDATED = "Number of updated indexes : ";
    protected static final String LOG_INDEX_DELETED = "Number of deleted indexes : ";
    protected static final String LOG_END_OF_SENTENCE = ". ";

    /**
     * Constructor
     */
    public AbstractIndexerDaemon( )
    {
        super( );
    }

    /**
     * {@inheritDoc}
     */
    public void run( )
    {
        StringBuilder sbLogs = new StringBuilder( );

        indexCreated( sbLogs );
        indexUpdated( sbLogs );
        indexDeleted( sbLogs );

        setLastRunLogs( sbLogs.toString( ) );
    }

    /**
     * Indexes created resources. Logs the action in the specified StringBuilder
     * 
     * @param sbLogs
     *            the StringBuilder used to log the action
     */
    private void indexCreated( StringBuilder sbLogs )
    {
        sbLogs.append( LOG_INDEX_CREATED );
        sbLogs.append( indexUpdated( IndexerTask.CREATE ) );
        sbLogs.append( LOG_END_OF_SENTENCE );
    }

    /**
     * Indexes updated resources. Logs the action in the specified StringBuilder
     * 
     * @param sbLogs
     *            the StringBuilder used to log the action
     */
    private void indexUpdated( StringBuilder sbLogs )
    {
        sbLogs.append( LOG_INDEX_UPDATED );
        sbLogs.append( indexUpdated( IndexerTask.UPDATE ) );
        sbLogs.append( LOG_END_OF_SENTENCE );
    }

    /**
     * Indexes deleted customers. Logs the action in the specified StringBuilder
     * 
     * @param sbLogs
     *            the StringBuilder used to log the action
     */
    private void indexDeleted( StringBuilder sbLogs )
    {
        sbLogs.append( LOG_INDEX_DELETED );
        sbLogs.append( 0 );
        sbLogs.append( LOG_END_OF_SENTENCE );
    }

    /**
     * Indexes updated resources and remove them from table
     * 
     * @param indexerTask
     *            the indexer task
     * @return the number of indexed resources
     */
    protected abstract int indexUpdated( IndexerTask indexerTask );

}
