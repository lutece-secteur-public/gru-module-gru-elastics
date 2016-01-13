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

/**
 *
 * @author
 *
 */
public class DemandMapping
{
    private String _strElasticsearch_id;
    private String _strDemand_id;
    private int _nDemandTypeId;
    private int _nCustomerId;
    private String _strRefNotification;

    /**
     *
     * @return _strRefNotification
     */
    public String getRefNotification(  )
    {
        return _strRefNotification;
    }

    /**
     *
     * @param refNotification
     */
    public void setRefNotification( String refNotification )
    {
        this._strRefNotification = refNotification;
    }

    /**
     *
     * @return _strElasticsearch_id
     */
    public String getStrElasticsearchId(  )
    {
        return _strElasticsearch_id;
    }

    /**
     *
     * @param strElasticsearchId
     */
    public void setStrElasticsearchId( String strElasticsearchId )
    {
        this._strElasticsearch_id = strElasticsearchId;
    }

    /**
     *
     * @return _strDemand_id
     */
    public String getStrDemandId(  )
    {
        return _strDemand_id;
    }

    /**
     *
     * @param strDemandId
     */
    public void setStrDemandId( String strDemandId )
    {
        this._strDemand_id = strDemandId;
    }

    /**
     *
     * @return _nDemandTypeId
     */
    public int getDemandTypeId(  )
    {
        return _nDemandTypeId;
    }

    /**
     *
     * @param demandTypeId
     */
    public void setDemandTypeId( int demandTypeId )
    {
        this._nDemandTypeId = demandTypeId;
    }

    /**
     *
     * @return _nCustomerId
     */
    public int getCustomerId(  )
    {
        return _nCustomerId;
    }

    /**
     *
     * @param customerId
     */
    public void setCustomerId( int customerId )
    {
        this._nCustomerId = customerId;
    }
}
