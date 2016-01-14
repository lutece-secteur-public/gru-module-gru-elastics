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
package fr.paris.lutece.plugins.gru.modules.supply.business;

/**
 *
 * @author
 *
 */
public class ElasticMapping
{
    private int _nIdCustomer;
    private int _nIdUser;
    private int _nIdMapping;
    private String _strRefUser;

    /**
     *
     * @return
     */
    public String getStrRefUser(  )
    {
        return _strRefUser;
    }

    /**
     *
     * @param strRefUser
     */
    public void setStrRefUser( String strRefUser )
    {
        this._strRefUser = strRefUser;
    }

    /**
     *
     * @return _nIdMapping 
     */
    public int getIdMapping(  )
    {
        return _nIdMapping;
    }

    /**
     *
     * @param idMapping 
     */
    public void setIdMapping( int idMapping )
    {
        this._nIdMapping = idMapping;
    }

    /**
     *
     * @return _nIdCustomer
     */
    public int getIdCustomer(  )
    {
        return _nIdCustomer;
    }

    /**
     *
     * @param idCustomer 
     */
    public void setIdCustomer( int idCustomer )
    {
        this._nIdCustomer = idCustomer;
    }

    /**
     *
     * @return _nIdUser
     */
    public int getIdUser(  )
    {
        return _nIdUser;
    }

    /**
     *
     * @param idUser 
     */
    public void setIdUser( int idUser )
    {
        this._nIdUser = idUser;
    }
}
