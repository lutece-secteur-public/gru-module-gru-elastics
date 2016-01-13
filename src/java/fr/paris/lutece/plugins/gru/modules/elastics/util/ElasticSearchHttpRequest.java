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

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import fr.paris.lutece.plugins.gru.modules.elastics.util.constants.GRUElasticsConstants;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 
 * 
 *
 */
public class ElasticSearchHttpRequest
{
    //Client Http
    private static Client _client = Client.create(  );
    private static ObjectMapper _mapper = new ObjectMapper(  );

    /**
     * 
     */
    private ElasticSearchHttpRequest( )
    {
    	
    }
    
    ////////////////////////////////////////////////////////////////////////////////CREATE ///////////////////////////////////////////////////////////////////////////////////////////
    /**
     * method which permit to insert a Demand
     * @param demand json string which represent the demand
     * @return elasticsearch response
     */
    public static String insertDemand( String demand )
    {
        String output = "";

        try
        {
            Map<String, Object> jsonDemand = _mapper.readValue( demand, Map.class );
            WebResource resource = _client.resource( AppPropertiesService.getProperty( 
                        GRUElasticsConstants.PATH_ELK_SERVER ) +
                    AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_TYPE_DEMAND ) +
                    jsonDemand.get( GRUElasticsConstants.FIELD_NOTIFICATION_REFERENCE ) );
            ClientResponse response = resource.put( ClientResponse.class, demand );
            output = response.getEntity( String.class );
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.getMessage();
        }

        return output;
    }

    /**
     * method which permit to insert a User
     * @param user json string which represent the user
     * @return elasticsearch response
     */
    public static String insertUser( String user )
    {
        String output = "";

        try
        {
            Map<String, Object> jsonUser = _mapper.readValue( user, Map.class );
            WebResource resource = _client.resource( AppPropertiesService.getProperty( 
                        GRUElasticsConstants.PATH_ELK_SERVER ) +
                    AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_TYPE_USER ) +
                    jsonUser.get( GRUElasticsConstants.FIELD_NOTIFICATION_USER_GUID) );
            ClientResponse response = resource.post( ClientResponse.class, user );
            output = response.getEntity( String.class );
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.getMessage();
        }

        return output;
    }

    /**
     * method which permit to insert a Notification
     * @param notification json string which represent the notification
     * @return elasticsearch response
     */
    public static String insertNotification( String notification )
    {
        String output = "";

        try
        {
            Map<String, Object> jsonNotification = _mapper.readValue( notification, Map.class );
            Map<String, String> jsonSollicitation = (Map<String, String>) jsonNotification.get( GRUElasticsConstants.FIELD_NOTIFICATION_SOLLICITATION );
            WebResource resource = _client.resource( AppPropertiesService.getProperty( 
                        GRUElasticsConstants.PATH_ELK_SERVER ) +
                    AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_TYPE_NOTIFICATION ) + jsonSollicitation.get(GRUElasticsConstants.FIELD_NOTIFICATION_DEMAND_ID));
            ClientResponse response = resource.post( ClientResponse.class, notification );
            output = response.getEntity( String.class );
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.getMessage();
        }

        return output;
    }

    ////////////////////////////////////////////////////////////////////////////////////////GETTERS////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * method which permit to get a demand by its id
     * @param strIdDemand the Id of the demand
     * @return the demand from elasticsearch
     */
    public static String getDemandbyId( String strIdDemand )
    {
        WebResource resource = _client.resource( AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_SERVER ) +
                AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_TYPE_DEMAND ) + strIdDemand +
                GRUElasticsConstants.PATH_ELK_SOURCE );
        ClientResponse response = resource.get( ClientResponse.class );
        String output = response.getEntity( String.class );

        return output;
    }

    /**
     * method which permit to get a user by its Id
     * @param strGuId the Id of the user
     * @return the user from elasticsearch
     */
    public static String getUserbyGuId( String strGuId )
    {
        WebResource resource = _client.resource( AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_SERVER ) +
                AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_TYPE_USER ) + strGuId +
                GRUElasticsConstants.PATH_ELK_SOURCE );
        ClientResponse response = resource.get( ClientResponse.class );
        String output = response.getEntity( String.class );

        return output;
    }

    /**
     * method which permit to get a notification by its id
     * @param strIdNotification the Id of the notification
     * @return the notification from elasticsearch
     */
    public static String getNotificationById( String strIdNotification )
    {
        WebResource resource = _client.resource( AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_SERVER ) +
                AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_TYPE_NOTIFICATION ) +
                strIdNotification + GRUElasticsConstants.PATH_ELK_SOURCE );
        ClientResponse response = resource.get( ClientResponse.class );
        String output = response.getEntity( String.class );

        return output;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////DELETE//////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * method which permit to delete a demand by its id
     * @param strIdDemand the Id of a demand
     * @return the response of Elasticsearch
     */
    public static String deleteDemandbyId( String strIdDemand )
    {
        WebResource resource = _client.resource( AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_SERVER ) +
                AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_TYPE_DEMAND ) + strIdDemand );
        ClientResponse response = resource.delete( ClientResponse.class );
        String output = response.getEntity( String.class );

        return output;
    }

    /**
     * method which permit to delete a User by its id
     * @param strGuId the Id of a User
     * @return the response of elasticsearch
     */
    public static String deleteUserbyGuId( String strGuId )
    {
        WebResource resource = _client.resource( AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_SERVER ) +
                AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_TYPE_USER ) + strGuId );
        ClientResponse response = resource.delete( ClientResponse.class );
        String output = response.getEntity( String.class );

        return output;
    }

    /**
     * method which permit to delete a Notification by its id
     * @param strIdNotification the Id of a Notification
     * @return the response of elasticsearch
     */
    public static String deleteNotificationbyId( String strIdNotification )
    {
        WebResource resource = _client.resource( AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_SERVER ) +
                AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_TYPE_NOTIFICATION ) +
                strIdNotification );
        ClientResponse response = resource.delete( ClientResponse.class );
        String output = response.getEntity( String.class );

        return output;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////UPDATE///////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * method which permit to update a Demand by its id
     * @param demand json string which represents the demand
     * @param strIdDemand the Id of the demand
     * @return response of elasticsearch
     */
    public static String updateDemandById( String demand, String strIdDemand )
    {
        WebResource resource = _client.resource( AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_SERVER ) +
                AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_TYPE_DEMAND ) + strIdDemand +
                GRUElasticsConstants.PATH_ELK_UPDATE );
        ClientResponse response = resource.post( ClientResponse.class, demand );
        String output = response.getEntity( String.class );

        return output;
    }

    /**
     * method which permit to update a User by its id
     * @param user json string which represents the user
     * @param strIdUser the Id of the user
     * @return response of elasticsearch
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static String updateUserByGuId( String user, String strIdUser )
    {
        WebResource resource = _client.resource( AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_SERVER ) +
                AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_TYPE_USER ) + strIdUser +
                GRUElasticsConstants.PATH_ELK_UPDATE );
        ClientResponse response = resource.post( ClientResponse.class, user );
        String output = response.getEntity( String.class );

        return output;
    }

    /**
     * method which permit to update a Notification by its id
     * @param notification json string which represents the notification
     * @param strIdDemand the id of the notification
     * @return response of elasticsearch
     */
    public static String updateNotificationByIdDemand( String notification, String strIdDemand )
    {
        WebResource resource = _client.resource( AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_SERVER ) +
                AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_TYPE_NOTIFICATION ) + strIdDemand +
                GRUElasticsConstants.PATH_ELK_UPDATE );
        ClientResponse response = resource.post( ClientResponse.class, notification );
        String output = response.getEntity( String.class );

        return output;
    }

    //////////////////////////////////////////////////////////////////AUTOCOMPLETION//////////////////////////////////////////////////////////////////////
    /**
     * method which permit to send an autocomplete request to elasticsearch
     * @param autocompleteRequest  the autocomplete request
     * @return response of elasticsearch
     */
    public static String autocomplete( String autocompleteRequest )
    {
        WebResource resource = _client.resource( AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_SERVER ) +
                AppPropertiesService.getProperty( GRUElasticsConstants.PATH_ELK_SUGGEST ) );
        ClientResponse response = resource.post( ClientResponse.class, autocompleteRequest );
        String output = response.getEntity( String.class );

        return output;
    }
}
