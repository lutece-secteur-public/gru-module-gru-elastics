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
package fr.paris.lutece.plugins.grusupply.service.daemon.demand;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import fr.paris.lutece.plugins.grusupply.business.daemon.IndexerTask;
import fr.paris.lutece.plugins.grusupply.business.daemon.demand.DemandIndexerAction;
import fr.paris.lutece.plugins.grusupply.business.daemon.demand.DemandIndexerActionDAO;
import fr.paris.lutece.plugins.grusupply.business.daemon.demand.DemandIndexerActionFilter;
import fr.paris.lutece.plugins.grusupply.business.daemon.demand.IDemandIndexerActionDAO;
import fr.paris.lutece.test.LuteceTestCase;

/**
 * Test class for the DemandIndexerActionDAO
 */
public class DemandIndexerActionDAOTest extends LuteceTestCase
{
    // Indexer Action constants
    private static final int INDEXERACTION_ID_1 = 1;
    private static final int INDEXERACTION_ID_2 = 2;
    private static final int INDEXERACTION_ID_3 = 3;

    // Demand Indexer Action constants
    private static final String DEMAND_INDEXERACTION_DEMAND_ID_1 = "1";
    private static final String DEMAND_INDEXERACTION_DEMAND_ID_2 = "2";
    private static final String DEMAND_INDEXERACTION_DEMAND_ID_3 = "3";
    private static final String DEMAND_INDEXERACTION_DEMAND_TYPE_ID_1 = "1";
    private static final String DEMAND_INDEXERACTION_DEMAND_TYPE_ID_2 = "2";
    private static final String DEMAND_INDEXERACTION_DEMAND_TYPE_ID_3 = "3";
    
    // DAO
    private final IDemandIndexerActionDAO _demandIndexerActionDao;

    /**
     * Constructor
     */
    public DemandIndexerActionDAOTest(  )
    {
        _demandIndexerActionDao = new DemandIndexerActionDAO(  );
    }
    
    /**
     * Test case for Demand Indexer Action
     */
    public void testDemandIndexerActionDAO(  )
    {
        // Test create and retrieve data
        DemandIndexerAction demandIndexerAction = new DemandIndexerAction(  );
        demandIndexerAction.setIdAction( INDEXERACTION_ID_1 );
        demandIndexerAction.setDemandId( DEMAND_INDEXERACTION_DEMAND_ID_1 );
        demandIndexerAction.setDemandTypeId( DEMAND_INDEXERACTION_DEMAND_TYPE_ID_1 );
        demandIndexerAction.setTask( IndexerTask.CREATE );        
        _demandIndexerActionDao.insert( demandIndexerAction );
        
        DemandIndexerAction demandIndexerActionStored = _demandIndexerActionDao.findByPrimaryKey( demandIndexerAction.getIdAction(  ) );
        assertThat( demandIndexerAction.getIdAction(  ), is( demandIndexerActionStored.getIdAction(  ) ) );
        assertThat( demandIndexerAction.getDemandId(  ), is( demandIndexerActionStored.getDemandId(  ) ) );
        assertThat( demandIndexerAction.getDemandTypeId(  ), is( demandIndexerActionStored.getDemandTypeId(  ) ) );
        assertThat( demandIndexerAction.getTask( ).getValue( ), is( demandIndexerActionStored.getTask(  ).getValue(  ) ) );
        
        // Test selectList
        demandIndexerAction = new DemandIndexerAction(  );
        demandIndexerAction.setIdAction( INDEXERACTION_ID_2 );
        demandIndexerAction.setDemandId( DEMAND_INDEXERACTION_DEMAND_ID_2 );
        demandIndexerAction.setDemandTypeId( DEMAND_INDEXERACTION_DEMAND_TYPE_ID_2 );
        demandIndexerAction.setTask( IndexerTask.CREATE );        
        _demandIndexerActionDao.insert( demandIndexerAction );
        
        demandIndexerAction = new DemandIndexerAction(  );
        demandIndexerAction.setIdAction( INDEXERACTION_ID_3 );
        demandIndexerAction.setDemandId( DEMAND_INDEXERACTION_DEMAND_ID_3 );
        demandIndexerAction.setDemandTypeId( DEMAND_INDEXERACTION_DEMAND_TYPE_ID_3 );
        demandIndexerAction.setTask( IndexerTask.UPDATE );        
        _demandIndexerActionDao.insert( demandIndexerAction );
        
        // --- Get all Demand indexer with a create task 
        DemandIndexerActionFilter demandIndexerActionFilter = new DemandIndexerActionFilter(  );
        demandIndexerActionFilter.setIndexerTask( IndexerTask.CREATE );
        
        List<DemandIndexerAction> listDemandIndexerAction = _demandIndexerActionDao.selectList( demandIndexerActionFilter );
        assertThat( listDemandIndexerAction.size(  ), is( 2 ) );
        
        // --- Get all demand indexer with an update task
        demandIndexerActionFilter = new DemandIndexerActionFilter(  );
        demandIndexerActionFilter.setIndexerTask( IndexerTask.CREATE );
        
        listDemandIndexerAction = _demandIndexerActionDao.selectList( demandIndexerActionFilter );
        assertThat( listDemandIndexerAction.size(  ), is( 2 ) );
        
        // Test delete
        _demandIndexerActionDao.delete( INDEXERACTION_ID_1 );
        demandIndexerActionStored = _demandIndexerActionDao.findByPrimaryKey( INDEXERACTION_ID_1 );
        assertThat( demandIndexerActionStored, is( nullValue(  ) ) );
    }
}
