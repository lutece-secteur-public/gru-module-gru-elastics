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


/**
 *
 * This class represents an action for the indexer
 *
 */
public class IndexerAction
{
    private int _nIdAction;
    private IndexerTask _indexerTask;
    private String _strResourceId;
    private String _strResourceType;

    /**
     * Gets the action id
     * @return the action id
     */
    public int getIdAction(  )
    {
        return _nIdAction;
    }

    /**
     * Sets the action id
     * @param nIdAction the action id
     */
    public void setIdAction( int nIdAction )
    {
        _nIdAction = nIdAction;
    }

    /**
     * Gets the task
     * @return the task
     */
    public IndexerTask getTask(  )
    {
        return _indexerTask;
    }

    /**
     * Sets the task
     * @param indexerTask the task
     */
    public void setTask( IndexerTask indexerTask )
    {
        _indexerTask = indexerTask;
    }

    /**
     * Get the resource id
     * @return the _strResourceId
     */
    public String getResourceId( )
    {
        return _strResourceId;
    }

    /**
     * Set the resource id
     * @param _strResourceId the _strResourceId to set
     */
    public void setResourceId( String _strResourceId )
    {
        this._strResourceId = _strResourceId;
    }

    /**
     * Get the resource type
     * @return the _strResourceType
     */
    public String getResourceType( )
    {
        return _strResourceType;
    }

    /**
     * Set the resource type
     * @param _strResourceType the _strResourceType to set
     */
    public void setResourceType( String _strResourceType )
    {
        this._strResourceType = _strResourceType;
    }
    
}
