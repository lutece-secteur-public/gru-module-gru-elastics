/*
 * Copyright (c) 2002-2016, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.grusupply.service.daemon;

import fr.paris.lutece.plugins.grusupply.business.Customer;
import fr.paris.lutece.plugins.grusupply.business.daemon.IndexerAction;
import fr.paris.lutece.plugins.grusupply.business.daemon.IndexerActionFilter;
import fr.paris.lutece.plugins.grusupply.business.daemon.IndexerActionHome;
import fr.paris.lutece.plugins.grusupply.business.daemon.IndexerTask;
import fr.paris.lutece.plugins.grusupply.service.CustomerProvider;
import fr.paris.lutece.plugins.grusupply.service.IndexService;
import fr.paris.lutece.portal.service.daemon.Daemon;
import fr.paris.lutece.portal.service.util.AppLogService;

import java.util.List;


/**
 *
 *  Daemon used to index customers in incremental mode
 */
public class CustomerIndexerDaemon extends Daemon
{
    private static final String LOG_INDEX_CREATED = "Number of created indexes : ";
    private static final String LOG_INDEX_UPDATED = "Number of updated indexes : ";
    private static final String LOG_INDEX_DELETED = "Number of deleted indexes : ";
    private static final String LOG_END_OF_SENTENCE = ". ";

    /**
     * Constructor
     */
    public CustomerIndexerDaemon(  )
    {
        super(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(  )
    {
        StringBuilder sbLogs = new StringBuilder(  );

        indexCreatedCustomers( sbLogs );
        indexUpdatedCustomers( sbLogs );
        indexDeletedCustomers( sbLogs );

        setLastRunLogs( sbLogs.toString(  ) );
    }

    /**
     * Indexes created customers.
     * Logs the action in the specified StringBuilder
     * @param sbLogs the StringBuilder used to log the action
     */
    private void indexCreatedCustomers( StringBuilder sbLogs )
    {
        sbLogs.append( LOG_INDEX_CREATED );
        sbLogs.append( indexUpdatedCustomers( IndexerTask.CREATE ) );
        sbLogs.append( LOG_END_OF_SENTENCE );
    }

    /**
     * Indexes updated customers.
     * Logs the action in the specified StringBuilder
     * @param sbLogs the StringBuilder used to log the action
     */
    private void indexUpdatedCustomers( StringBuilder sbLogs )
    {
        sbLogs.append( LOG_INDEX_UPDATED );
        sbLogs.append( indexUpdatedCustomers( IndexerTask.UPDATE ) );
        sbLogs.append( LOG_END_OF_SENTENCE );
    }

    /**
     * Indexes deleted customers.
     * Logs the action in the specified StringBuilder
     * @param sbLogs the StringBuilder used to log the action
     */
    private void indexDeletedCustomers( StringBuilder sbLogs )
    {
        sbLogs.append( LOG_INDEX_DELETED );
        sbLogs.append( 0 );
        sbLogs.append( LOG_END_OF_SENTENCE );
    }

    /**
     * Indexes updated customers
     * @param indexerTask the indexer task
     * @return the number of indexed customers
     */
    private int indexUpdatedCustomers( IndexerTask indexerTask )
    {
        int nNbIndexedCustomers = 0;

        IndexerActionFilter indexerActionFilter = new IndexerActionFilter(  );
        indexerActionFilter.setTask( indexerTask );

        List<IndexerAction> listIndexerActions = IndexerActionHome.getList( indexerActionFilter );

        for ( IndexerAction indexerAction : listIndexerActions )
        {
            try
            {
                Customer customer = CustomerProvider.instance(  ).get( null, indexerAction.getCustomerId(  ) );

                if ( customer != null )
                {
//                    IndexService.instance(  ).index( customer );

                    nNbIndexedCustomers++;

                    IndexerActionHome.remove( indexerAction.getIdAction(  ) );
                }
            }
            catch ( Exception e )
            {
                AppLogService.error( "Unable to get the customer with id " + indexerAction.getCustomerId(  ) + " : " +
                    e.getMessage(  ) );
            }
        }

        return nNbIndexedCustomers;
    }
}
