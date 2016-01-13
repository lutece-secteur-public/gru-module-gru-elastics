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
import fr.paris.lutece.util.sql.DAOUtil;

/**
 * 
 * 
 *
 */
public class ElasticMappingDAO implements IElasticMappingDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_mapping ) FROM elastic_mapping";
    private static final String SQL_QUERY_SELECT = "SELECT id_mapping,id_customer,id_user, ref_user FROM elastic_mapping WHERE id_customer = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO elastic_mapping ( id_mapping, id_customer, id_user, ref_user ) VALUES (  ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM elastic_mapping WHERE id_mapping = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE elastic_mapping SET  id_customer = ?, id_user = ?, ref_user = ? WHERE id_mapping = ?";
    private static final String SQL_QUERY_SELECT_BY_ID_USER = "SELECT id_mapping, id_customer, ref_user FROM elastic_mapping WHERE id_user = ?";

    @Override
    public int newPrimaryKey( Plugin plugin )
    {
        // TODO Auto-generated method stub
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery(  );

        int nKey = 1;

        if ( daoUtil.next(  ) )
        {
            nKey = daoUtil.getInt( 1 ) + 1;
        }

        daoUtil.free(  );

        return nKey;
    }

    @Override
    public void insert( ElasticMapping mapping, Plugin plugin )
    {
        // TODO Auto-generated method stub
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        mapping.setIdMapping( newPrimaryKey( plugin ) );

        daoUtil.setInt( 1, mapping.getIdMapping(  ) );
        daoUtil.setInt( 2, mapping.getIdCustomer(  ) );
        daoUtil.setInt( 3, mapping.getIdUser(  ) );
        daoUtil.setString( 4, mapping.getStrRefUser(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    @Override
    public void store( ElasticMapping mapping, Plugin plugin )
    {
        // TODO Auto-generated method stub
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( 1, mapping.getIdCustomer(  ) );
        daoUtil.setInt( 2, mapping.getIdUser(  ) );
        daoUtil.setString( 3, mapping.getStrRefUser(  ) );
        daoUtil.setInt( 4, mapping.getIdMapping(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    @Override
    public void delete( int nKey, Plugin plugin )
    {
        // TODO Auto-generated method stub
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    @Override
    public ElasticMapping load( int nKey, Plugin plugin )
    {
        // TODO Auto-generated method stub
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery(  );

        ElasticMapping mapping = null;

        if ( daoUtil.next(  ) )
        {
            mapping = new ElasticMapping(  );
            mapping.setIdMapping( daoUtil.getInt( 1 ) );
            mapping.setIdCustomer( daoUtil.getInt( 2 ) );
            mapping.setIdUser( daoUtil.getInt( 3 ) );
            mapping.setStrRefUser( daoUtil.getString( 4 ) );
        }

        daoUtil.free(  );

        return mapping;
    }

    @Override
    public ElasticMapping loadByUserId( int nKey, Plugin plugin )
    {
        // TODO Auto-generated method stub
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ID_USER, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery(  );

        ElasticMapping mapping = null;

        if ( daoUtil.next(  ) )
        {
            mapping = new ElasticMapping(  );
            mapping.setIdMapping( daoUtil.getInt( 1 ) );
            mapping.setIdCustomer( daoUtil.getInt( 2 ) );
            mapping.setIdUser( nKey );
            mapping.setStrRefUser( daoUtil.getString( 3 ) );
        }

        daoUtil.free(  );

        return mapping;
    }
}
