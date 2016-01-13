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

import fr.paris.lutece.portal.service.plugin.Plugin;


public interface IElasticMappingDAO
{
    /**
     *
     * @param plugin
     * @return the new primary key
     */
    public int newPrimaryKey( Plugin plugin );

    /**
     * Insert a new mapping in the table.
     * @param mapping instance of the Customer object to insert
     * @param plugin the Plugin
     */
    void insert( ElasticMapping mapping, Plugin plugin );

    /**
     * Update the mapping in the table
     * @param  mapping the reference of the Mapping
     * @param plugin the Plugin
     */
    void store( ElasticMapping mapping, Plugin plugin );

    /**
     * Delete a mapping from the table
     * @param nKey The identifier of the Mapping to delete
     * @param plugin the Plugin
     */
    void delete( int nKey, Plugin plugin );

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Load the mapping from the table
     * @param nKey The identifier of the mapping
     * @param plugin the Plugin
     * @return The instance of the mapping
     */
    ElasticMapping load( int nKey, Plugin plugin );

    /**
     * Load the mapping from the table
     * @param nKey the  identifier of the mapping
     * @param plugin
     * @return
     */
    ElasticMapping loadByUserId( int nKey, Plugin plugin );
}
