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
package fr.paris.lutece.plugins.grusupply.business.daemon.demand;

import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.plugins.grusupply.business.daemon.IndexerTask;
import fr.paris.lutece.plugins.grusupply.service.GruSupplyPlugin;
import fr.paris.lutece.util.sql.DAOUtil;

/**
 * This class provides Data Access methods for Demand Indexer Action objects
 */
public class DemandIndexerActionDAO implements IDemandIndexerActionDAO
{
    // Constants key words
    public static final String CONSTANT_WHERE = " WHERE ";
    public static final String CONSTANT_AND = " AND ";

    // Constants filters
    private static final String SQL_FILTER_ID_ACTION = " id_action = ? ";
    private static final String SQL_FILTER_ID_TASK = " id_task = ? ";
    private static final String SQL_FILTER_ID_DEMAND = " id_demand = ? ";
    private static final String SQL_FILTER_DEMAND_TYPE_ID = " demand_type_id = ? ";

    // Constants requests
    private static final String SQL_QUERY_NEW_PK = "SELECT max(id_action) FROM grusupply_demand_indexer_action";
    private static final String SQL_QUERY_INSERT = "INSERT INTO grusupply_demand_indexer_action(id_action,id_demand,demand_type_id,id_task)"
            + " VALUES(?,?,?,?)";
    private static final String SQL_QUERY_SELECT = "SELECT id_action,id_demand,demand_type_id,id_task" + " FROM grusupply_demand_indexer_action  ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM grusupply_demand_indexer_action WHERE id_action = ? ";
    private static final String SQL_QUERY_SELECT_BY_ID = SQL_QUERY_SELECT + CONSTANT_WHERE + SQL_FILTER_ID_ACTION;

    /**
     * Generates a new primary key
     *
     * @param plugin
     *            the plugin
     * @return The new primary key
     */
    private synchronized int newPrimaryKey( )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, GruSupplyPlugin.getPlugin( ) );
        daoUtil.executeQuery( );

        int nKey = 1;

        if ( daoUtil.next( ) )
        {
            nKey = daoUtil.getInt( 1 ) + 1;
        }

        daoUtil.free( );

        return nKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert( DemandIndexerAction demandIndexerAction )
    {
        int nKey = newPrimaryKey( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, GruSupplyPlugin.getPlugin( ) );
        daoUtil.setString( 2, demandIndexerAction.getDemandId( ) );
        daoUtil.setString( 3, demandIndexerAction.getDemandTypeId( ) );
        daoUtil.setInt( 4, demandIndexerAction.getTask( ).getValue( ) );

        demandIndexerAction.setIdAction( nKey );
        daoUtil.setInt( 1, demandIndexerAction.getIdAction( ) );

        daoUtil.executeUpdate( );

        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( int nId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, GruSupplyPlugin.getPlugin( ) );
        daoUtil.setInt( 1, nId );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DemandIndexerAction> selectList( DemandIndexerActionFilter demandIndexerActionFilter )
    {
        List<DemandIndexerAction> demandIndexerActionList = new ArrayList<DemandIndexerAction>( );
        DemandIndexerAction demandIndexerAction = null;
        List<String> listStrFilter = new ArrayList<String>( );

        if ( demandIndexerActionFilter.containsIndexerTask( ) )
        {
            listStrFilter.add( SQL_FILTER_ID_TASK );
        }

        if ( demandIndexerActionFilter.containsDemandId( ) )
        {
            listStrFilter.add( SQL_FILTER_ID_DEMAND );
        }

        if ( demandIndexerActionFilter.containsDemandTypeId( ) )
        {
            listStrFilter.add( SQL_FILTER_DEMAND_TYPE_ID );
        }

        String strSQL = buildRequestWithFilter( SQL_QUERY_SELECT, listStrFilter, null );

        DAOUtil daoUtil = new DAOUtil( strSQL, GruSupplyPlugin.getPlugin( ) );

        int nIndex = 1;

        if ( demandIndexerActionFilter.containsIndexerTask( ) )
        {
            daoUtil.setInt( nIndex++, demandIndexerActionFilter.getIndexerTask( ).getValue( ) );
        }

        if ( demandIndexerActionFilter.containsDemandId( ) )
        {
            daoUtil.setString( nIndex++, demandIndexerActionFilter.getDemandId( ) );
        }

        if ( demandIndexerActionFilter.containsDemandTypeId( ) )
        {
            daoUtil.setString( nIndex, demandIndexerActionFilter.getDemandTypeId( ) );
        }

        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            demandIndexerAction = new DemandIndexerAction( );
            demandIndexerAction.setIdAction( daoUtil.getInt( 1 ) );
            demandIndexerAction.setDemandId( daoUtil.getString( 2 ) );
            demandIndexerAction.setDemandTypeId( daoUtil.getString( 3 ) );
            demandIndexerAction.setTask( IndexerTask.valueOf( daoUtil.getInt( 4 ) ) );

            demandIndexerActionList.add( demandIndexerAction );
        }

        daoUtil.free( );

        return demandIndexerActionList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DemandIndexerAction findByPrimaryKey( int nIdDemandIndexerAction )
    {
        DemandIndexerAction demandIndexerAction = null;

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ID, GruSupplyPlugin.getPlugin( ) );
        daoUtil.setInt( 1, nIdDemandIndexerAction );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            demandIndexerAction = new DemandIndexerAction( );
            demandIndexerAction.setIdAction( daoUtil.getInt( 1 ) );
            demandIndexerAction.setDemandId( daoUtil.getString( 2 ) );
            demandIndexerAction.setDemandTypeId( daoUtil.getString( 3 ) );
            demandIndexerAction.setTask( IndexerTask.valueOf( daoUtil.getInt( 4 ) ) );
        }

        daoUtil.free( );

        return demandIndexerAction;
    }

    /**
     * Builds a query with filters placed in parameters
     * 
     * @param strSelect
     *            the select of the query
     * @param listStrFilter
     *            the list of filter to add in the query
     * @param strOrder
     *            the order by of the query
     * @return a query
     */
    private static String buildRequestWithFilter( String strSelect, List<String> listStrFilter, String strOrder )
    {
        StringBuffer strBuffer = new StringBuffer( );
        strBuffer.append( strSelect );

        int nCount = 0;

        for ( String strFilter : listStrFilter )
        {
            if ( ++nCount == 1 )
            {
                strBuffer.append( CONSTANT_WHERE );
            }

            strBuffer.append( strFilter );

            if ( nCount != listStrFilter.size( ) )
            {
                strBuffer.append( CONSTANT_AND );
            }
        }

        if ( strOrder != null )
        {
            strBuffer.append( strOrder );
        }

        return strBuffer.toString( );
    }

}
