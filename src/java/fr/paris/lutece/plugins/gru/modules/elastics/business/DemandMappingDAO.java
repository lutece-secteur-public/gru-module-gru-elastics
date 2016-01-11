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

import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.plugins.gru.business.customer.Customer;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

public class DemandMappingDAO implements IDemandMappingDAO {
	
	private static final String SQL_QUERY_SELECT = "SELECT id_elasticsearch, id_demand, id_demand_type, id_customer, ref_notification FROM demand_mapping WHERE id_elasticsearch = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO demand_mapping ( id_elasticsearch, id_demand, id_demand_type, id_customer, ref_notification ) VALUES (  ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM demand_mapping WHERE id_elasticsearch = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE demand_mapping SET id_elasticsearch = ?, id_demand = ?, id_demand_type = ?, id_customer = ?, ref_notification = ?";
    private static final String SQL_QUERY_SELECT_BY_DEMAND  = "SELECT id_elasticsearch, id_demand, id_demand_type, id_customer, ref_notification FROM demand_mapping WHERE id_demand = ? AND id_demand_type = ?";
    private static final String SQL_QUERY_SELECT_BY_ID_CUSTOMER = "SELECT id_elasticsearch, id_demand, id_demand_type ,id_customer FROM demand_mapping WHERE id_customer = ?";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_elasticsearch FROM demand_mapping WHERE id_customer = ? ";
    
	@Override
	public void insert(DemandMapping mapping, Plugin plugin) {
		// TODO Auto-generated method stub
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        daoUtil.setString( 1, mapping.getStrElasticsearch_id() );
        daoUtil.setString(2, mapping.getStrDemand_id());
        daoUtil.setInt(3, mapping.getDemandTypeId());
        daoUtil.setInt(4, mapping.getCustomerId());
        daoUtil.setString(5, mapping.getRefNotification());
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
	}

	@Override
	public void store(DemandMapping mapping, Plugin plugin) {
		// TODO Auto-generated method stub
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setString( 1, mapping.getStrElasticsearch_id() );
        daoUtil.setString( 2, mapping.getStrDemand_id() );
        daoUtil.setInt( 3, mapping.getDemandTypeId() );
        daoUtil.setInt( 4, mapping.getCustomerId() );
        daoUtil.setString(5, mapping.getRefNotification());
        daoUtil.executeUpdate(  );
        daoUtil.free(  );

	}

	@Override
	public void delete(int nKey, Plugin plugin) {
		// TODO Auto-generated method stub
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
	}

	@Override
	public DemandMapping load(int nKey, Plugin plugin) {
		// TODO Auto-generated method stub
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery(  );
        
        DemandMapping mapping =null;
        if ( daoUtil.next(  ) )
        {
            mapping = new DemandMapping(  );
            mapping.setStrElasticsearch_id(daoUtil.getString( 1 ));
            mapping.setStrDemand_id( daoUtil.getString( 2 ));
            mapping.setDemandTypeId(daoUtil.getInt( 3 ));
            mapping.setCustomerId( daoUtil.getInt( 4 ) );
            mapping.setRefNotification(daoUtil.getString(5));
        }
        daoUtil.free(  );
		return mapping;
	}

	@Override
	public DemandMapping loadByCustomerId(int id_customer, Plugin plugin) {
		// TODO Auto-generated method stub
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ID_CUSTOMER, plugin );
        daoUtil.setInt( 1, id_customer );
        daoUtil.executeQuery(  );
        
        DemandMapping mapping =null;
        if ( daoUtil.next(  ) )
        {
            mapping = new DemandMapping(  );
            mapping.setStrElasticsearch_id(daoUtil.getString( 1 ));
            mapping.setStrDemand_id( daoUtil.getString( 2 ));
            mapping.setDemandTypeId(daoUtil.getInt(3));
            mapping.setCustomerId(daoUtil.getInt(4));
            mapping.setRefNotification(daoUtil.getString(5));
        }
        daoUtil.free(  );
		return mapping;
	}

	@Override
	public DemandMapping loadByIdDemand(String strIdDemand, int demandIdType, Plugin plugin) {
		// TODO Auto-generated method stub
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_DEMAND, plugin );
        daoUtil.setString( 1, strIdDemand );
        daoUtil.setInt(2, demandIdType);
        daoUtil.executeQuery(  );
        DemandMapping mapping =null;
        if ( daoUtil.next(  ) )
        {
            mapping = new DemandMapping(  );
            mapping.setStrElasticsearch_id(daoUtil.getString( 1 ));
            mapping.setStrDemand_id( daoUtil.getString( 2 ));
            mapping.setDemandTypeId(daoUtil.getInt(3));
            mapping.setCustomerId(daoUtil.getInt(4));
            mapping.setRefNotification(daoUtil.getString(5));
            
        }
        daoUtil.free(  );
		return mapping;
	}


	@Override
	public List<String> selectIdElasticsearchList(int idCustomer, Plugin plugin) {
		// TODO Auto-generated method stub
		List<String> idList = new ArrayList<String>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            String strIdDemand = daoUtil.getString(1);
            idList.add(strIdDemand);
        }

        daoUtil.free(  );

		return idList;
	}

}
