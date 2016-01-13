/*
 * Copyright (c) 2002-2015, Mairie de Paris
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
package fr.paris.lutece.plugins.gru.modules.elastics.business;

import fr.paris.lutece.plugins.gru.modules.elastics.util.constants.GRUElasticsConstants;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.List;


/**
 * 
 * 
 *
 */
public class DemandMappingHome
{
    // Static variable pointed at the DAO instance
    private static IDemandMappingDAO _dao = SpringContextService.getBean( GRUElasticsConstants.BEAN_DEMAND_MAPPING );
    private static Plugin _plugin = PluginService.getPlugin( GRUElasticsConstants.MODULE_NAME );

    /**
     * Private constructor - this class need not be instantiated
     */
    private DemandMappingHome(  )
    {
    }

    /**
     * Create an instance of the DemandMapping class
     * @param mapping The instance of the DemandMapping which contains the informations to store
     * @return The  instance of DemandMapping which has been created with its primary key. 
     */
    public static void create( DemandMapping mapping )
    {
        _dao.insert( mapping, _plugin );
    }

    /**
     * Update of the DemandMapping which is specified in parameter
     * @param mapping The instance of the DemandMapping which contains the data to store
     * @return The instance of the DemandMapping which has been updated
     */
    public static void update( DemandMapping mapping )
    {
        _dao.store( mapping, _plugin );
    }

    ///////////////////////////////////////////////////////////////////////////
    //Finders
    /**
     * Returns an instance of a mapping whose identifier is specified in parameter
     * @param nKey The mapping primary key which is the elasticsearch id
     * @return an instance of DemandMapping
     */
    public static DemandMapping findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Returns an instance of a mapping whose user identifier is specified in parameter
     * @param nKey The user identifier
     * @return an instance of ElasticMapping
     */
    public static DemandMapping findByUserId( int nKey )
    {
        return _dao.loadByCustomerId( nKey, _plugin );
    }

    /**
    * Returns an instance of a mapping whose user identifier is specified in parameter
    * @param strDemandId The demand identifier 
    * @param demandIdType The demand type identifier
    * @return an instance of ElasticMapping
    */
    public static DemandMapping findByDemandId( String strDemandId, int demandIdType )
    {
        return _dao.loadByIdDemand( strDemandId, demandIdType, _plugin );
    }

    /**
     * Returns a list of demand's id
     * @param nkey 
     * @return  the list of Ids 
     */
    public static List<String> getIdDemandList( int nkey )
    {
        return _dao.selectIdElasticsearchList( nkey, _plugin );
    }
}
