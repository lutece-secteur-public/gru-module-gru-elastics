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
package fr.paris.lutece.plugins.grusupply.business.daemon;

import fr.paris.lutece.plugins.grusupply.service.GruSupplyPlugin;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * This class provides Data Access methods for Indexer Action objects
 */
public final class IndexerActionDAO implements IIndexerActionDAO
{
    // Constants key words
    public static final String CONSTANT_WHERE = " WHERE ";
    public static final String CONSTANT_AND = " AND ";
    
    // Constants filters
    private static final String SQL_FILTER_ID_ACTION = " id_action = ? ";
    private static final String SQL_FILTER_ID_TASK = " id_task = ? ";
    private static final String SQL_FILTER_ID_RESOURCE = " id_resource = ? ";
    private static final String SQL_FILTER_RESOURCE_TYPE = " resource_type = ? ";

    // Constants requests
    private static final String SQL_QUERY_NEW_PK = "SELECT max(id_action) FROM grusupply_indexer_action";
    private static final String SQL_QUERY_INSERT = "INSERT INTO grusupply_indexer_action(id_action,id_resource,id_task,resource_type)" +
            " VALUES(?,?,?,?)";
    private static final String SQL_QUERY_SELECT = "SELECT id_action,id_resource,id_task,resource_type" +
        " FROM grusupply_indexer_action  ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM grusupply_indexer_action WHERE id_action = ? ";
    private static final String SQL_QUERY_SELECT_BY_ID = SQL_QUERY_SELECT + CONSTANT_WHERE + SQL_FILTER_ID_ACTION;

    /**
     * Generates a new primary key
     *
     * @param plugin the plugin
     * @return The new primary key
     */
    private synchronized int newPrimaryKey(  )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, GruSupplyPlugin.getPlugin(  ) );
        daoUtil.executeQuery(  );

        int nKey = 1;

        if ( daoUtil.next(  ) )
        {
            nKey = daoUtil.getInt( 1 ) + 1;
        }

        daoUtil.free(  );

        return nKey;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void insert( IndexerAction indexerAction )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, GruSupplyPlugin.getPlugin(  ) );
        daoUtil.setString( 2, indexerAction.getResourceId(  ) );
        daoUtil.setInt( 3, indexerAction.getTask(  ).getValue(  ) );
        daoUtil.setString( 4, indexerAction.getResourceType(  ) );

        indexerAction.setIdAction( newPrimaryKey(  ) );
        daoUtil.setInt( 1, indexerAction.getIdAction(  ) );

        daoUtil.executeUpdate(  );

        daoUtil.free(  );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( int nId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, GruSupplyPlugin.getPlugin(  ) );
        daoUtil.setInt( 1, nId );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IndexerAction> selectList( IndexerActionFilter filter )
    {
        List<IndexerAction> indexerActionList = new ArrayList<IndexerAction>(  );
        IndexerAction indexerAction = null;
        List<String> listStrFilter = new ArrayList<String>(  );

        if ( filter.containsTask(  ) )
        {
            listStrFilter.add( SQL_FILTER_ID_TASK );
        }

        if ( filter.containsResourceId( ) )
        {
            listStrFilter.add( SQL_FILTER_ID_RESOURCE );
        }
        
        if ( filter.containsResourceType( ) )
        {
            listStrFilter.add( SQL_FILTER_RESOURCE_TYPE );
        }

        String strSQL = buildRequestWithFilter( SQL_QUERY_SELECT, listStrFilter, null );

        DAOUtil daoUtil = new DAOUtil( strSQL );

        int nIndex = 1;

        if ( filter.containsTask(  ) )
        {
            daoUtil.setInt( nIndex++, filter.getIndexerTask(  ).getValue(  ) );
        }

        if ( filter.containsResourceId( ) )
        {
            daoUtil.setString( nIndex++, filter.getResourceId( ) );
        }
        
        if ( filter.containsResourceType( ) )
        {
            daoUtil.setString( nIndex, filter.getResourceType( ) );
        }

        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            indexerAction = new IndexerAction(  );
            indexerAction.setIdAction( daoUtil.getInt( 1 ) );
            indexerAction.setResourceId( daoUtil.getString( 2 ) );
            indexerAction.setTask( IndexerTask.valueOf( daoUtil.getInt( 3 ) ) );
            indexerAction.setResourceType( daoUtil.getString( 4 ) );

            indexerActionList.add( indexerAction );
        }

        daoUtil.free(  );

        return indexerActionList;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public IndexerAction findByPrimaryKey( int idIndexerAction )
    {
        IndexerAction indexerAction = null;
        
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ID, GruSupplyPlugin.getPlugin(  ) );
        daoUtil.setInt( 1, idIndexerAction );
        daoUtil.executeQuery( );
        
        while ( daoUtil.next( ) )
        {
            indexerAction = new IndexerAction(  );
            indexerAction.setIdAction( daoUtil.getInt( 1 ) );
            indexerAction.setResourceId( daoUtil.getString( 2 ) );
            indexerAction.setTask( IndexerTask.valueOf( daoUtil.getInt( 3 ) ) );
            indexerAction.setResourceType( daoUtil.getString( 4 ) );
        }
        
        daoUtil.free(  );
        
        return indexerAction;
    }

    /**
     * Builds a query with filters placed in parameters
     * @param strSelect the select of the query
     * @param listStrFilter the list of filter to add in the query
     * @param strOrder the order by of the query
     * @return a query
     */
    private static String buildRequestWithFilter( String strSelect, List<String> listStrFilter, String strOrder )
    {
        StringBuffer strBuffer = new StringBuffer(  );
        strBuffer.append( strSelect );

        int nCount = 0;

        for ( String strFilter : listStrFilter )
        {
            if ( ++nCount == 1 )
            {
                strBuffer.append( CONSTANT_WHERE );
            }

            strBuffer.append( strFilter );

            if ( nCount != listStrFilter.size(  ) )
            {
                strBuffer.append( CONSTANT_AND );
            }
        }

        if ( strOrder != null )
        {
            strBuffer.append( strOrder );
        }

        return strBuffer.toString(  );
    }

}
