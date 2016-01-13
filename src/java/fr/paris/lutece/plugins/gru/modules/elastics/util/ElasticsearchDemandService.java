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
package fr.paris.lutece.plugins.gru.modules.elastics.util;

import fr.paris.lutece.plugins.gru.business.customer.Customer;
import fr.paris.lutece.plugins.gru.business.customer.CustomerHome;
import fr.paris.lutece.plugins.gru.business.demand.BaseDemand;
import fr.paris.lutece.plugins.gru.business.demand.Demand;
import fr.paris.lutece.plugins.gru.business.demand.Notification;
import fr.paris.lutece.plugins.gru.modules.elastics.business.DemandMapping;
import fr.paris.lutece.plugins.gru.modules.elastics.business.DemandMappingHome;
import fr.paris.lutece.plugins.gru.modules.elastics.util.constants.GRUElasticsConstants;
import fr.paris.lutece.plugins.gru.service.demand.IDemandService;
import fr.paris.lutece.plugins.gru.service.demand.NotificationService;
import fr.paris.lutece.plugins.gru.service.demandtype.DemandTypeService;
import fr.paris.lutece.portal.business.user.AdminUser;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 *
 */
public class ElasticsearchDemandService implements IDemandService
{
    ObjectMapper _mapper = new ObjectMapper(  );

    @Override
    public Demand getDemand( String strDemandId, String strDemandTypeId, AdminUser user )
    {
        // TODO Auto-generated method stub
        BaseDemand base = new BaseDemand(  );
        Demand demand = null;
        Notification notification = new Notification(  );
        DemandMapping mapping = DemandMappingHome.findByDemandId( strDemandId, Integer.parseInt( strDemandTypeId ) );
        String demandElastic = ElasticSearchHttpRequest.getDemandbyId( mapping.getStrElasticsearchId(  ) );
        String notificationElastic = ElasticSearchHttpRequest.getNotificationById( mapping.getRefNotification(  ) );
        Customer customer = CustomerHome.findByPrimaryKey( mapping.getCustomerId(  ) );
        Map<String, Object> jsonDemand;

        try
        {
            jsonDemand = _mapper.readValue( demandElastic, Map.class );
            base.setDemandTypeId( jsonDemand.get( GRUElasticsConstants.FIELD_NOTIFICATION_DEMAND_TYPE_ID ).toString(  ) );
            base.setId( jsonDemand.get( GRUElasticsConstants.FIELD_NOTIFICATION_DEMAND_ID ).toString(  ) );
            base.setReference( jsonDemand.get( GRUElasticsConstants.FIELD_NOTIFICATION_REFERENCE ).toString(  ) );
            base.setStatus( Integer.parseInt( jsonDemand.get( GRUElasticsConstants.FIELD_NOTIFICATION_CRM_STATUS_ID )
                                                        .toString(  ) ) );

            demand = DemandTypeService.buildDemand( base, customer, user );

            notification.setTimestamp( ( new Date(  ) ).getTime(  ) );
            notification.setTitle( "Prise en compte de la demande" );
            NotificationService.parseJSON( notification, notificationElastic );
            demand.addNotification( notification );
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.getMessage();
        }

        return demand;
    }

    @Override
    public List<BaseDemand> getDemands( String strCustomerId, AdminUser user )
    {
        // TODO Auto-generated method stub
        List<BaseDemand> list = new ArrayList<BaseDemand>(  );
        List<String> idList = DemandMappingHome.getIdDemandList( Integer.parseInt( strCustomerId ) );

        for ( String id : idList )
        {
            String demand = ElasticSearchHttpRequest.getDemandbyId( id );

            try
            {
                Map<String, Object> jsonDemand = _mapper.readValue( demand, Map.class );
                BaseDemand base = new BaseDemand(  );
                base.setDemandTypeId( jsonDemand.get( GRUElasticsConstants.FIELD_NOTIFICATION_DEMAND_TYPE_ID ).toString(  ) );
                base.setId( jsonDemand.get( GRUElasticsConstants.FIELD_NOTIFICATION_DEMAND_ID ).toString(  ) );
                base.setReference( jsonDemand.get( GRUElasticsConstants.FIELD_NOTIFICATION_REFERENCE ).toString(  ) );
                base.setStatus( (Integer) jsonDemand.get( GRUElasticsConstants.FIELD_NOTIFICATION_CRM_STATUS_ID ) );
                list.add( base );
            }
            catch ( IOException e )
            {
                // TODO Auto-generated catch block
                e.getMessage();
            }
        }

        return list;
    }
}
