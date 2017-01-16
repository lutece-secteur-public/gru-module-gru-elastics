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

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.grusupply.business.daemon.IndexerActionFilter;

/**
*
* This class is a filter for DemandIndexerActionFilter
*
*/
public class DemandIndexerActionFilter extends IndexerActionFilter
{

    private String _strDemandId;
    private String _strDemandTypeId;
    
    /**
     * Get the demand id
     * @return the _strDemandId
     */
    public String getDemandId( )
    {
        return _strDemandId;
    }
    
    /**
     * Set the demand id
     * @param _strDemandId the _strDemandId to set
     */
    public void setDemandId( String _strDemandId )
    {
        this._strDemandId = _strDemandId;
    }
    
    /**
     * Tests if the filter contains a demand id to filter or not
     * @return {@code true} if the filter contains a demand to filter, {@code false} otherwise
     */
    public boolean containsDemandId(  )
    {
        return StringUtils.isNotBlank( _strDemandId );
    }
    
    /**
     * Get the demand type id
     * @return the _strDemandTypeId
     */
    public String getDemandTypeId( )
    {
        return _strDemandTypeId;
    }
    
    /**
     * Set the demand type id
     * @param _strDemandTypeId the _strDemandTypeId to set
     */
    public void setDemandTypeId( String _strDemandTypeId )
    {
        this._strDemandTypeId = _strDemandTypeId;
    }

    /**
     * Tests if the filter contains a demand type id to filter or not
     * @return {@code true} if the filter contains a demand type to filter, {@code false} otherwise
     */
    public boolean containsDemandTypeId(  )
    {
        return StringUtils.isNotBlank( _strDemandTypeId );
    }
    
}
