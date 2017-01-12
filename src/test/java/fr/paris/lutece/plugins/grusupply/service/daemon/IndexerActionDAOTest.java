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

import fr.paris.lutece.plugins.grusupply.business.daemon.IIndexerActionDAO;
import fr.paris.lutece.plugins.grusupply.business.daemon.IndexerAction;
import fr.paris.lutece.plugins.grusupply.business.daemon.IndexerActionDAO;
import fr.paris.lutece.plugins.grusupply.business.daemon.IndexerActionFilter;
import fr.paris.lutece.plugins.grusupply.business.daemon.IndexerTask;
import fr.paris.lutece.plugins.grusupply.service.GruSupplyPlugin;
import fr.paris.lutece.test.LuteceTestCase;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

/**
 * Test class for the IndexerActionDAO
 */
public class IndexerActionDAOTest extends LuteceTestCase
{
    
    private static final int INDEXERACTION_ID_1 = 1;
    private static final int INDEXERACTION_ID_2 = 2;
    private static final int INDEXERACTION_ID_3 = 3;
    private static final String INDEXERACTION_RESOURCE_ID_1 = "1";
    private static final String INDEXERACTION_RESOURCE_ID_2 = "2";
    private static final String INDEXERACTION_RESOURCE_ID_3 = "3";
    private static final String INDEXERACTION_RESOURCE_TYPE_DEMAND = "demand";
    private static final String INDEXERACTION_RESOURCE_TYPE_CUSTOMER = "customer";
    
    private final IIndexerActionDAO _indexerActionDao;

    /**
     * Constructor
     */
    public IndexerActionDAOTest(  )
    {
        _indexerActionDao = new IndexerActionDAO(  );
    }

    /**
     * Test case
     */
    public void testBusiness(  )
    {
        // Test create and retrieve data
        IndexerAction indexerAction = new IndexerAction(  );
        indexerAction.setIdAction( INDEXERACTION_ID_1 );
        indexerAction.setResourceId( INDEXERACTION_RESOURCE_ID_1 );
        indexerAction.setResourceType( INDEXERACTION_RESOURCE_TYPE_DEMAND );
        indexerAction.setTask( IndexerTask.CREATE );        
        _indexerActionDao.insert( indexerAction );
        
        IndexerAction indexerActionStored = _indexerActionDao.findByPrimaryKey( indexerAction.getIdAction(  ) );
        assertThat( indexerAction.getIdAction(  ), is( indexerActionStored.getIdAction(  ) ) );
        assertThat( indexerAction.getResourceId(  ), is( indexerActionStored.getResourceId(  ) ) );
        assertThat( indexerAction.getResourceType(  ), is( indexerActionStored.getResourceType(  ) ) );
        assertThat( indexerAction.getTask( ).getValue( ), is( indexerActionStored.getTask(  ).getValue(  ) ) );
        
        // Test selectList
        indexerAction = new IndexerAction(  );
        indexerAction.setIdAction( INDEXERACTION_ID_2 );
        indexerAction.setResourceId( INDEXERACTION_RESOURCE_ID_2 );
        indexerAction.setResourceType( INDEXERACTION_RESOURCE_TYPE_CUSTOMER );
        indexerAction.setTask( IndexerTask.CREATE );
        _indexerActionDao.insert( indexerAction );
        
        indexerAction = new IndexerAction(  );
        indexerAction.setIdAction( INDEXERACTION_ID_3 );
        indexerAction.setResourceId( INDEXERACTION_RESOURCE_ID_3 );
        indexerAction.setResourceType( INDEXERACTION_RESOURCE_TYPE_DEMAND );
        indexerAction.setTask( IndexerTask.CREATE );
        _indexerActionDao.insert( indexerAction );
        
        // --- Select all IndexerAction of "demand" resource type
        IndexerActionFilter indexerActionFilter = new IndexerActionFilter(  );
        indexerActionFilter.setResourceType( INDEXERACTION_RESOURCE_TYPE_DEMAND );
        
        List<IndexerAction> listIndexerAction = _indexerActionDao.selectList( indexerActionFilter );
        assertThat( listIndexerAction.size(  ), is( 2 ) );
        
        // --- Select all IndexerAction of "customer" resource type
        indexerActionFilter = new IndexerActionFilter(  );
        indexerActionFilter.setResourceType( INDEXERACTION_RESOURCE_TYPE_CUSTOMER );
        
        listIndexerAction = _indexerActionDao.selectList( indexerActionFilter );
        assertThat( listIndexerAction.size(  ), is( 1 ) );
        
        // Test delete
        _indexerActionDao.delete( INDEXERACTION_ID_1 );
        indexerActionStored = _indexerActionDao.findByPrimaryKey( INDEXERACTION_ID_1 );
        assertThat( indexerActionStored, is( nullValue(  ) ) );
    }
    
}
