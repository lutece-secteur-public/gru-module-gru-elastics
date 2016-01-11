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
/**
 * 
 *
 *
 */
public final class ElasticMappingHome {

	// Static variable pointed at the DAO instance
    private static IElasticMappingDAO _dao = SpringContextService.getBean( GRUElasticsConstants.BEAN_ELASTIC_MAPPING );
    private static Plugin _plugin = PluginService.getPlugin( GRUElasticsConstants.MODULE_NAME);
    
    /**
     * Private constructor - this class need not be instantiated
     */
    private ElasticMappingHome(  )
    {
    }
    /**
     * Create an instance of the ElasticMapping class
     * @param mapping The instance of the ElasticMapping which contains the informations to store
     */
    public static void create(ElasticMapping mapping )
    {
        _dao.insert( mapping, _plugin );
    }
    /**
     * Update of the elasticMapping which is specified in parameter
     * @param mapping The instance of the ElasticMapping which contains the data to store 
     */
    public static void update( ElasticMapping mapping )
    {
        _dao.store( mapping, _plugin );
    }
    /**
     * Remove the mapping whose identifier is specified in parameter
     * @param nKey The mapping Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }
    
///////////////////////////////////////////////////////////////////////////
// Finders
    /**
     * Returns an instance of a mapping whose identifier is specified in parameter
     * @param nKey The mapping primary key
     * @return an instance of ElasticMapping
     */
    public static ElasticMapping findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }
    /**
     * Returns an instance of a mapping whose user identifier is specified in parameter
     * @param nKey The user identifier
     * @return an instance of ElasticMapping
     */
    public static ElasticMapping findByUserId( int nKey )
    {
        return _dao.loadByUserId( nKey, _plugin );
    }
    
}
