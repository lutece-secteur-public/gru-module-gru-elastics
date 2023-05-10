/*
 * Copyright (c) 2002-2023, Mairie de Paris
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
package fr.paris.lutece.plugins.grusupply.web.rs;

/**
 * 
 * SearchResult
 *
 */
public abstract class SearchResult
{
    private String _status;
    private String _paginator;
    private String _index;
    private Integer _nNumberResult;
    /**
     * @return the _status
     */
    public String getStatus( )
    {
        return _status;
    }
    /**
     * @param status the _status to set
     */
    public void setStatus( String status )
    {
        this._status = status;
    }
    
    /**
     * @return the _index
     */
    public String getIndex( )
    {
        return _index;
    }
    /**
     * @param index the _index to set
     */
    public void setIndex( String index )
    {
        this._index = index;
    }
    /**
     * @return the _paginator
     */
    public String getPaginator( )
    {
        return _paginator;
    }
    /**
     * @param paginator the _paginator to set
     */
    public void setPaginator( String paginator )
    {
        this._paginator = paginator;
    }
    /**
     * @return the _nNumberResult
     */
    public Integer getNumberResult( )
    {
        return _nNumberResult;
    }
    /**
     * @param nNumberResult the _nNumberResult to set
     */
    public void setNumberResult( Integer nNumberResult )
    {
        this._nNumberResult = nNumberResult;
    }
    
    
}
