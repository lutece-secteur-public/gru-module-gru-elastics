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
package fr.paris.lutece.plugins.gru.modules.elastics.rs;

import fr.paris.lutece.plugins.gru.business.customer.Customer;
import fr.paris.lutece.plugins.gru.business.customer.CustomerHome;
import fr.paris.lutece.plugins.gru.modules.elastics.business.DemandMapping;
import fr.paris.lutece.plugins.gru.modules.elastics.business.DemandMappingHome;
import fr.paris.lutece.plugins.gru.modules.elastics.business.ElasticMapping;
import fr.paris.lutece.plugins.gru.modules.elastics.business.ElasticMappingHome;
import fr.paris.lutece.plugins.gru.modules.elastics.util.ElasticSearchHttpRequest;
import fr.paris.lutece.plugins.gru.modules.elastics.util.constants.GRUElasticsConstants;
import fr.paris.lutece.plugins.rest.service.RestConstants;

import org.apache.commons.lang.StringUtils;

import org.codehaus.jackson.map.ObjectMapper;

import org.codehaus.jettison.json.JSONException;

import java.io.IOException;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 *
 * GRUElasticsRest
 *
 */
@Path( RestConstants.BASE_PATH + GRUElasticsConstants.PLUGIN_NAME )
public class GRUElasticsRest
{
    ObjectMapper _mapper = new ObjectMapper(  );

    /**
     * Web service method which permit to store data in elasticsearch
     * @return the status of the elasticsearch response
     * @param strJson 
     * @throws JSONException 
     */
    @POST
    @Path( GRUElasticsConstants.PATH_NOTIFICATION )
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes( MediaType.APPLICATION_JSON )
    public String notification( String strJson ) throws JSONException
    {
        try
        {
            //Variables
            ElasticMapping mapping;
            DemandMapping demandMapping = new DemandMapping(  );
            Map<String, Object> resultDemand = null;
            Map<String, Integer> successDemand = null;
            Map<String, Object> resultUser = null;
            Map<String, Integer> successUser = null;
            Map<String, Object> resultNotification = null;
            Map<String, Integer> successNotification = null;
            Map<String, Object> flux = _mapper.readValue( strJson, Map.class );
            Map<String, Object> notification = (Map<String, Object>) flux.get( GRUElasticsConstants.FIELD_JSON_BLOC_NOTIFICATION );
            Map<String, String> userEmail = (Map<String, String>) notification.get( GRUElasticsConstants.FIELD_JSON_BLOC_USER_EMAIL );
            Map<String, String> userDashboard = (Map<String, String>) notification.get( GRUElasticsConstants.FIELD_JSON_BLOC_USER_DASHBOARD );
            Map<String, String> userSMS = (Map<String, String>) notification.get( GRUElasticsConstants.FIELD_JSON_BLOC_USER_SMS );
            Map<String, Object> backofficeLogging = (Map<String, Object>) notification.get( GRUElasticsConstants.FIELD_JSON_BLOC_BACKOFFICE_LOGGING );

            if ( StringUtils.isBlank( notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_USER_GUID ).toString(  ) ) ||
                    ( ElasticMappingHome.findByUserId( 
                        (Integer) notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_USER_GUID ) ) == null ) )
            {
                Customer customer = new Customer(  );
                customer.setAccountGuid( "100" );
                customer.setAccountLogin( "john" );
                customer.setEmail( "john.doe@somewhere.com" );
                customer.setExtrasAttributes( "" );
                customer.setFirstname( "John" );
                customer.setHasAccount( true );
                customer.setIdTitle( 12 );
                customer.setIsEmailVerified( true );
                customer.setIsMobilePhoneVerified( true );
                customer.setLastname( "Doe" );
                customer.setMobilePhone( "0681795994" );

                customer = CustomerHome.create( customer );

                mapping = new ElasticMapping(  );
                mapping.setIdCustomer( customer.getId(  ) );
                mapping.setIdUser( (Integer) notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_USER_GUID ) );
                demandMapping.setCustomerId( customer.getId(  ) );
                ElasticMappingHome.create( mapping );
            }
            else
            {
                mapping = ElasticMappingHome.findByUserId( (Integer) notification.get( 
                            GRUElasticsConstants.FIELD_NOTIFICATION_USER_GUID ) );
                demandMapping.setCustomerId( mapping.getIdCustomer(  ) );
            }

            resultDemand = doCreateDemand( notification, userSMS );
            resultUser = doCreateUser( notification, userSMS );
            resultNotification = doCreateNotification( notification, backofficeLogging, userEmail, userDashboard,
                    userSMS );
            successDemand = (Map<String, Integer>) resultDemand.get( GRUElasticsConstants.FIEL_RESULT_SHARD );
            successUser = (Map<String, Integer>) resultUser.get( GRUElasticsConstants.FIEL_RESULT_SHARD );
            successNotification = (Map<String, Integer>) resultNotification.get( GRUElasticsConstants.FIEL_RESULT_SHARD );

            if ( ( successDemand.get( GRUElasticsConstants.FIELD_RESULT_CREATED ) >= 1 ) &&
                    ( successUser.get( GRUElasticsConstants.FIELD_RESULT_CREATED ) >= 1 ) &&
                    ( successNotification.get( GRUElasticsConstants.FIELD_RESULT_CREATED ) >= 1 ) )
            {
                // Users' mapping
                mapping.setStrRefUser( resultUser.get( GRUElasticsConstants.FIELD_RESULT_ID ).toString(  ) );
                ElasticMappingHome.update( mapping );

                if ( DemandMappingHome.findByDemandId( 
                            notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_DEMAND_ID ).toString(  ),
                            (Integer) notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_DEMAND_TYPE_ID ) ) == null )
                {
                    //Demand's mapping 
                    demandMapping.setDemandTypeId( (Integer) notification.get( 
                            GRUElasticsConstants.FIELD_NOTIFICATION_DEMAND_TYPE_ID ) );
                    demandMapping.setStrDemandId( notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_DEMAND_ID )
                                                              .toString(  ) );
                    demandMapping.setStrElasticsearchId( resultDemand.get( GRUElasticsConstants.FIELD_RESULT_ID )
                                                                     .toString(  ) );
                    demandMapping.setRefNotification( resultNotification.get( GRUElasticsConstants.FIELD_RESULT_ID )
                                                                        .toString(  ) );
                    DemandMappingHome.create( demandMapping );
                }

                return GRUElasticsConstants.STATUS_201;
            }
            else
            {
                return GRUElasticsConstants.STATUS_404;
            }
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.getMessage();
        }

        return null;
    }

    /**
     * Web service method which permit to store a Demand in elasticseach
     * @param notification json string which contains data about notificattion
     * @param userSMS json string which contains data SMS
     * @return the response of Elasticsearch as a Map
     */
    @POST
    @Path( GRUElasticsConstants.PARAMETER_NOTIFICATION_DEMAND )
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes( MediaType.APPLICATION_JSON )
    public Map<String, Object> doCreateDemand( Map<String, Object> notification, Map<String, String> userSMS )
    {
        Map<String, Object> mapResult = null;
        String demandUser = GRUElasticsConstants.OPEN_BRACE + GRUElasticsConstants.FIELD_DEMAND_USER +
            GRUElasticsConstants.OPEN_BRACE + GRUElasticsConstants.FIELD_DEMAND_USER_GUID + "\"" +
            notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_USER_GUID ) + "\"" +
            GRUElasticsConstants.CLOSE_BRACE + ",";

        String demandDetail = GRUElasticsConstants.FIELD_DEMAND_ID + "\"" +
            notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_DEMAND_ID ) + "\"," +
            GRUElasticsConstants.FIELD_DEMAND_ID_TYPE +
            notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_DEMAND_TYPE_ID ) + "," +
            GRUElasticsConstants.FIELD_DEMAND_MAX_STEP +
            notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_DEMAND_MAX_STEP ) + "," +
            GRUElasticsConstants.FIELD_DEMAND_CURRENT_STEP +
            notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_DEMAND_CURRENT_STEP ) + "," +
            GRUElasticsConstants.FIELD_DEMAND_STATE +
            notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_DEMAND_STATE ) + "," +
            GRUElasticsConstants.FIELD_DEMAND_NOTIFICATION_TYPE + "\"" +
            notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_TYPE ) + "\"" + "," +
            GRUElasticsConstants.FIELD_DEMAND_DATE + "\"2015-03-31\"," + GRUElasticsConstants.FIELD_CRM_STATUS_ID +
            notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_CRM_STATUS_ID ) + "," +
            GRUElasticsConstants.FIELD_DEMAND_REFERENCE + "\"PZQu4rocRy60hO2seUEziQ\",";

        String suggest = GRUElasticsConstants.FIELD_SUGGEST + GRUElasticsConstants.OPEN_BRACE +
            GRUElasticsConstants.FIELD_SUGGEST_INPUT + GRUElasticsConstants.OPEN_SQUARE_BRACKET +
            "\"PZQu4rocRy60hO2seUEziQ\"" + GRUElasticsConstants.CLOSE_SQUARE_BRACKET + "," +
            GRUElasticsConstants.FIELD_SUGGEST_OUTPUT + "\"John Doe\"," + GRUElasticsConstants.FIELD_SUGGEST_PAYLOAD +
            GRUElasticsConstants.OPEN_BRACE + GRUElasticsConstants.FIELD_DEMAND_USER_GUID + "\"" +
            notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_USER_GUID ) + "\"" + "," +
            GRUElasticsConstants.FIELD_SUGGEST_BIRTHDAY + "\"20/03/1980\"," +
            GRUElasticsConstants.FIELD_SUGGEST_PHONE_NUMBER + "\"" +
            userSMS.get( GRUElasticsConstants.FIELD_USER_SMS_PHONE_NUMBER ) + "\"" + "," +
            GRUElasticsConstants.FIELD_SUGGEST_EMAIL + "\"" +
            notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_EMAIL ) + "\"" + "," +
            GRUElasticsConstants.FIELD_DEMAND_REFERENCE + "\"PZQu4rocRy60hO2seUEziQ\"" +
            GRUElasticsConstants.CLOSE_BRACE + GRUElasticsConstants.CLOSE_BRACE + GRUElasticsConstants.CLOSE_BRACE;

        String demand = demandUser + demandDetail + suggest;
        String result = ElasticSearchHttpRequest.insertDemand( demand );

        try
        {
            mapResult = _mapper.readValue( result, Map.class );
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.getMessage();
        }

        return mapResult;
    }

    /**
     * Web service method which permit to store a User in elasticseach
     * @param notification json string which contains data about notificattion
     * @param userSMS json string which contains data SMS
     * @return the response of Elasticsearch as a Map
     */
    @POST
    @Path( GRUElasticsConstants.PARAMETER_NOTIFICATION_USER )
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes( MediaType.APPLICATION_JSON )
    public Map<String, Object> doCreateUser( Map<String, Object> notification, Map<String, String> userSMS )
    {
        Map<String, Object> response = null;
        String userDetail = GRUElasticsConstants.OPEN_BRACE + GRUElasticsConstants.FIELD_USER_GUID + "\"" +
            notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_USER_GUID ) + "\"" + "," +
            GRUElasticsConstants.FIELD_USER_EMAIL + "\"" +
            notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_EMAIL ) + "\"" + "," +
            GRUElasticsConstants.FIELD_USER_NAME + " \"John Doe\",\n" + GRUElasticsConstants.FIELD_USER_STAYCONNECTED +
            " \"true\"," + GRUElasticsConstants.FIELD_USER_STREET + "\"rue test\"," +
            GRUElasticsConstants.FIELD_USER_PHONE_NUMBER + "\"" +
            userSMS.get( GRUElasticsConstants.FIELD_USER_SMS_PHONE_NUMBER ) + "\"" + "," +
            GRUElasticsConstants.FIELD_USER_CITY + " \"Paris\"," + GRUElasticsConstants.FIELD_USER_CITYOFBIRTH +
            "\"london\"," + GRUElasticsConstants.FIELD_USER_BIRTHDAY + "\"20/03/1980\"," +
            GRUElasticsConstants.FIELD_USER_CIVILITY + "\"Mr\"," + GRUElasticsConstants.FIELD_USER_POSTAL_CODE +
            "\"75019\",";

        String suggestUser = GRUElasticsConstants.FIELD_SUGGEST + GRUElasticsConstants.OPEN_BRACE +
            GRUElasticsConstants.FIELD_SUGGEST_INPUT + GRUElasticsConstants.OPEN_SQUARE_BRACKET + "\"John\"," +
            "\"Doe\"," + "\"" + userSMS.get( GRUElasticsConstants.FIELD_USER_SMS_PHONE_NUMBER ) + "\"" + "," + "\"" +
            notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_EMAIL ) + "\"" +
            GRUElasticsConstants.CLOSE_SQUARE_BRACKET + "," + GRUElasticsConstants.FIELD_SUGGEST_OUTPUT +
            "\"Ndiambe Darou\"," + GRUElasticsConstants.FIELD_SUGGEST_PAYLOAD + GRUElasticsConstants.OPEN_BRACE +
            GRUElasticsConstants.FIELD_USER_GUID + "\"" +
            notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_USER_GUID ) + "\"" + "," +
            GRUElasticsConstants.FIELD_USER_BIRTHDAY + "\"20/03/1980\",\n" +
            GRUElasticsConstants.FIELD_USER_PHONE_NUMBER + "\"" +
            userSMS.get( GRUElasticsConstants.FIELD_USER_SMS_PHONE_NUMBER ) + "\"" + "," +
            GRUElasticsConstants.FIELD_USER_EMAIL + "\"" +
            notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_EMAIL ) + "\"" +
            GRUElasticsConstants.CLOSE_BRACE + GRUElasticsConstants.CLOSE_BRACE + GRUElasticsConstants.CLOSE_BRACE;

        String user = userDetail + suggestUser;
        String result = ElasticSearchHttpRequest.insertUser( user );

        try
        {
            response = _mapper.readValue( result, Map.class );
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.getMessage();
        }

        return response;
    }

    /**
     *  Web service method which permit to store a Notification in elasticseach
     * @param notification  json string which contains data about notificattion
     * @param backofficeLogging json string which contains data about backofficeLogging
     * @param userEmail json string which contains data about Email
     * @param userDashboard json string which contains data about Userdashboard
     * @param userSMS json string which contains data about SMS
     * @return the response of Elasticsearch as a Map
     */
    @POST
    @Path( GRUElasticsConstants.PARAMETER_NOTIFICATION_NOTIFICATION )
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes( MediaType.APPLICATION_JSON )
    public Map<String, Object> doCreateNotification( Map<String, Object> notification,
        Map<String, Object> backofficeLogging, Map<String, String> userEmail, Map<String, String> userDashboard,
        Map<String, String> userSMS )
    {
        Map<String, Object> response = null;
        String sollicitation = GRUElasticsConstants.OPEN_BRACE + GRUElasticsConstants.FIELD_NOTIF_SOLLICITATION +
            GRUElasticsConstants.OPEN_BRACE + GRUElasticsConstants.FIELD_NOTIF_DEMAND_ID + "\"" +
            notification.get( GRUElasticsConstants.FIELD_NOTIFICATION_DEMAND_ID ) + "\"" +
            GRUElasticsConstants.CLOSE_BRACE + ",";

        String backofficeLog = GRUElasticsConstants.FIELD_NOTIF_STATUS_TEXT + "\"" +
            backofficeLogging.get( GRUElasticsConstants.FIELD_BACKOFFICE_STATUS_TEXT ) + "\"" + "," +
            GRUElasticsConstants.FIELD_NOTIF_MESSAGE + "\"" +
            backofficeLogging.get( GRUElasticsConstants.FIELD_BACKOFFICE_MESSAGE ) + "\"" + "," +
            GRUElasticsConstants.FIELD_NOTIFIED_ON_DASHBOARD +
            backofficeLogging.get( GRUElasticsConstants.FIELD_BACKOFFICE_NOTIFIED_ON_DASHBOARD ) + "," +
            GRUElasticsConstants.FIELD_NOTIFIED_BY_EMAIL +
            backofficeLogging.get( GRUElasticsConstants.FIELD_BACKOFFICE_NOTIFIED_BY_EMAIL ) + "," +
            GRUElasticsConstants.FIELD_NOTIFIED_BY_SMS +
            backofficeLogging.get( GRUElasticsConstants.FIELD_BACKOFFICE_NOTIFIED_BY_SMS ) + "," +
            GRUElasticsConstants.FIELD_NOTIF_LEVEL_DASHBOARD_NOTIF +
            backofficeLogging.get( GRUElasticsConstants.FIELD_BACKOFFICE_LEVEL_DASHBOARD_NOTIF ) + "," +
            GRUElasticsConstants.FIELD_NOTIF_VIEW_DASHBOARD_NOTIF + "\"" +
            backofficeLogging.get( GRUElasticsConstants.FIELD_BACKOFFICE_VIEW_DASHBOARD_NOTIF ) + "\"" + "," +
            GRUElasticsConstants.FIELD_NOTIF_LEVEL_EMAIL_NOTIF +
            backofficeLogging.get( GRUElasticsConstants.FIELD_BACKOFFICE_LEVEL_EMAIL_NOTIF ) + "," +
            GRUElasticsConstants.FIELD_NOTIF_VIEW_EMAIL_NOTIF + "\"" +
            backofficeLogging.get( GRUElasticsConstants.FIELD_BACKOFFICE_VIEW_EMAIL_NOTIF ) + "\"" + "," +
            GRUElasticsConstants.FIELD_NOTIF_LEVEL_SMS_NOTIF +
            backofficeLogging.get( GRUElasticsConstants.FIELD_BACKOFFICE_LEVEL_SMS_NOTIF ) + "," +
            GRUElasticsConstants.FIELD_NOTIF_VIEW_SMS_NOTIF + "\"" +
            backofficeLogging.get( GRUElasticsConstants.FIELD_NOTIF_VIEW_SMS_NOTIF ) + "\"" + "," +
            GRUElasticsConstants.FIELD_NOTIF_DATE_SOLLICITATION + "\"2015-03-31\",";

        String userMail = GRUElasticsConstants.FIELD_NOTIF_USER_MAIL + GRUElasticsConstants.OPEN_BRACE +
            GRUElasticsConstants.FIELD_NOTIF_SENDER_NAME + "\"" +
            userEmail.get( GRUElasticsConstants.FIELD_USER_EMAIL_SENDER_NAME ) + "\"" + "," +
            GRUElasticsConstants.FIELD_NOTIF_SENDER_EMAIL + "\"" +
            userEmail.get( GRUElasticsConstants.FIELD_USER_EMAIL_SENDER_EMAIL ) + "\"" + "," +
            GRUElasticsConstants.FIELD_NOTIF_RECIPIENT + "\"" +
            userEmail.get( GRUElasticsConstants.FIELD_USER_EMAIL_RECIPIENT ) + "\"" + "," +
            GRUElasticsConstants.FIELD_NOTIF_SUBJECT + "\"" +
            userEmail.get( GRUElasticsConstants.FIELD_USER_EMAIL_SUBJECT ) + "\"" + "," +
            GRUElasticsConstants.FIELD_NOTIF_USER_EMAIL_MESSAGE + "\"" +
            userEmail.get( GRUElasticsConstants.FIELD_USER_EMAIL_MESSAGE ) + "\"" + GRUElasticsConstants.CLOSE_BRACE +
            ",";

        String dashboard = GRUElasticsConstants.FIELD_NOTIF_USER_DASHBOARD + GRUElasticsConstants.OPEN_BRACE +
            GRUElasticsConstants.FIELD_NOTIF_DASHBOARD_STATUS_TEXT + "\"" +
            userDashboard.get( GRUElasticsConstants.FIELD_DASHBOARD_STATUS_TEXT ) + "\"" + "," +
            GRUElasticsConstants.FIELD_NOTIF_DASHBOARD_SENDER_NAME + "\"" +
            userDashboard.get( GRUElasticsConstants.FIELD_DASHBORD_SENDER_NAME ) + "\"" + "," +
            GRUElasticsConstants.FIELD_NOTIF_DASHBOARD_SUBJECT + "\"" +
            userDashboard.get( GRUElasticsConstants.FIELD_DASHBORD_SUBJECT ) + "\"" + "," +
            GRUElasticsConstants.FIELD_NOTIF_DASHBOARD_MESSAGE + "\"" +
            userDashboard.get( GRUElasticsConstants.FIELD_DASHBORD_MESSAGE ) + "\"" + "," +
            GRUElasticsConstants.FIELD_NOTIF_DASHBOARD_DATA + "\"" +
            userDashboard.get( GRUElasticsConstants.FIELD_DASHBORD_DATA ) + "\"" + GRUElasticsConstants.CLOSE_BRACE +
            ",";

        String sms = GRUElasticsConstants.FIELD_NOTIF_USER_SMS + GRUElasticsConstants.OPEN_BRACE +
            GRUElasticsConstants.FIELD_NOTIF_USER_SMS_PHONE_NUMBER + "\"" +
            userSMS.get( GRUElasticsConstants.FIELD_USER_SMS_PHONE ) + "\"" + "," +
            GRUElasticsConstants.FIELD_NOTIF_USER_SMS_MESSAGE + "\"" +
            userSMS.get( GRUElasticsConstants.FIELD_USER_SMS_MESSAGE ) + "\"" + GRUElasticsConstants.CLOSE_BRACE +
            GRUElasticsConstants.CLOSE_BRACE;

        String notif = sollicitation + backofficeLog + userMail + dashboard + sms;

        String result = ElasticSearchHttpRequest.insertNotification( notif );

        try
        {
            response = _mapper.readValue( result, Map.class );
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.getMessage();
        }

        return response;
    }

    /**
     *  Web service method which permit to send autocompletion request to elasticsearch
     * @param strQuery autocompletion request for elasticsearch
     * @return the response of the
     */
    @GET
    @Path( GRUElasticsConstants.PARAMETER_NOTIFICATION_AUTOCOMPLETE )
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes( MediaType.APPLICATION_JSON )
    public String autocomplete( @PathParam( GRUElasticsConstants.PARAMETER_NOTIFICATION_QUERY )
    String strQuery )
    {
        //String autocompleteRequest = GRUElasticsConstants.FIELD_USER_SUGGEST + strQuery +
          //  GRUElasticsConstants.FIELD__COMPLETION;

        return ElasticSearchHttpRequest.autocomplete(strQuery);
    }
}
