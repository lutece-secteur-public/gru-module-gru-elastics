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

import org.codehaus.jettison.json.JSONObject;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;

import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import org.elasticsearch.index.query.QueryBuilders;

import java.net.InetSocketAddress;

import java.util.concurrent.ExecutionException;

/**
 * 
 * 
 *
 */
public class ElasticSearchRequest
{
    Settings _settings = ImmutableSettings.settingsBuilder(  ).put( "client.transport.sniff", true ).build(  );
    private Client _client = new TransportClient(  ).addTransportAddress( new InetSocketTransportAddress( 
                new InetSocketAddress( "localhost", 9300 ) ) );

    /**
     *
     * @param demand
     * @return
     */
    public String insertDemand( String demand )
    {
        //BulkRequestBuilder bulkRequest = client.prepareBulk();
        IndexResponse response = _client.prepareIndex( "teleservice", "demand" ).setSource( demand ).get(  );

        return response.toString(  );
    }

    /**
     *
     * @param user
     * @return
     */
    public String insertUser( String user )
    {
        //BulkRequestBuilder bulkRequest = client.prepareBulk();
        IndexResponse response = _client.prepareIndex( "teleservice", "user" ).setSource( user ).get(  );

        return response.toString(  );
    }

    /**
     *
     * @param notification
     * @return
     */
    public String insertNotification( String notification )
    {
        //BulkRequestBuilder bulkRequest = client.prepareBulk();
        IndexResponse response = _client.prepareIndex( "teleservice", "notification" ).setSource( notification ).get(  );

        return response.toString(  );
    }

    /**
     *
     * @param strIdDemand
     * @return
     */
    public String getDemandbyId( String strIdDemand )
    {
        GetResponse response = _client.prepareGet( "teleservice", "demand", strIdDemand ).get(  );

        return response.toString(  );
    }

    /**
     *
     * @param strGuId
     * @return
     */
    public String getUserbyGuId( String strGuId )
    {
        GetResponse response = _client.prepareGet( "teleservice", "user", strGuId ).get(  );

        return response.toString(  );
    }

    /**
     *
     * @param strGuId
     * @return
     */
    public GetResponse getNotificationByDemand( String strIdDemand )
    {
        GetResponse response = _client.prepareGet( "teleservice", "notification.sollicitation", strIdDemand ).get(  );

        return response;
    }

    /**
     *
     * @param strIdDemand
     * @return
     */
    public String DeleteDemandbyId( String strIdDemand )
    {
        DeleteResponse response = _client.prepareDelete( "teleservice", "demand", strIdDemand ).get(  );

        return response.toString(  );
    }

    /**
     *
     * @param strGuId
     * @return
     */
    public String DeleteUserbyGuId( String strGuId )
    {
        DeleteResponse response = _client.prepareDelete( "teleservice", "user", strGuId ).get(  );

        return response.toString(  );
    }

    /**
     *
     * @param strIdDemand
     * @return
     */
    public String DeleteNotificationbyIdDemand( String strIdDemand )
    {
        DeleteResponse response = _client.prepareDelete( "teleservice", "notification", strIdDemand ).get(  );

        return response.toString(  );
    }

    /**
     *
     * @param demand
     * @param strIdDemand
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public String UpdateDemandById( JSONObject demand, String strIdDemand )
        throws InterruptedException, ExecutionException
    {
        UpdateRequest updateRequest = new UpdateRequest( "teleservice", "demand", strIdDemand ).doc( demand );
        UpdateResponse response = _client.update( updateRequest ).get(  );

        return response.toString(  );
    }

    /**
     *
     * @param user
     * @param strIdUser
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public String UpdateUserByGuId( JSONObject user, String strIdUser )
        throws InterruptedException, ExecutionException
    {
        UpdateRequest updateRequest = new UpdateRequest( "teleservice", "user", strIdUser ).doc( user );
        UpdateResponse response = _client.update( updateRequest ).get(  );

        return response.toString(  );
    }

    /**
     *
     * @param notification
     * @param strIdDemand
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public String UpdateNotificationByIdDemand( JSONObject notification, String strIdDemand )
        throws InterruptedException, ExecutionException
    {
        UpdateRequest updateRequest = new UpdateRequest( "teleservice", "user", strIdDemand ).doc( notification );
        UpdateResponse response = _client.update( updateRequest ).get(  );

        return response.toString(  );
    }

    ///////////////////////////////////////////////////SEARCH REQUESTS//////////////////////////////////////////////////////////////////
    /**
     *
     * @param strIdDemand
     * @param strIdDemandType
     * @return
     */
    public SearchResponse getDemandFromSearch( String strIdDemand, String strIdDemandType )
    {
        SearchResponse response = _client.prepareSearch( "teleservice" ).setTypes( "demand" )
                                        .setQuery( QueryBuilders.termQuery( "demand_id", strIdDemand ) ).execute(  )
                                        .actionGet(  );

        return response;
    }

    /**
     *
     * @param strGuId
     * @return
     */
    public SearchResponse getListDemandFromSearch( String strGuId )
    {
        SearchResponse response = _client.prepareSearch( "teleservice" ).setTypes( "demand" )
                                        .setQuery( QueryBuilders.matchAllQuery(  ) ).execute(  ).actionGet(  );

        return response;
    }

    /**
     *
     * @param strPhone
     * @return
     */
    public SearchResponse getUserbyPhoneNumber( String strPhone )
    {
        SearchResponse response = _client.prepareSearch( "teleservice" ).setTypes( "user" )
                                        .setQuery( QueryBuilders.termQuery( "telephoneNumber", strPhone ) ).execute(  )
                                        .actionGet(  );

        return response;
    }

    /**
     *
     * @param strName
     * @return
     */
    public SearchResponse getUserbyName( String strName )
    {
        SearchResponse response = _client.prepareSearch( "teleservice" ).setTypes( "teleservice" )
                                        .setQuery( QueryBuilders.termQuery( "name", strName ) ).execute(  ).actionGet(  );

        return response;
    }
}
