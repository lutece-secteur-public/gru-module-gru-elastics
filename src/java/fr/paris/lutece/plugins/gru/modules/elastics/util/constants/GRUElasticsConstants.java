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
package fr.paris.lutece.plugins.gru.modules.elastics.util.constants;

/**
 *
 * GRUElasticsConstants
 *
 */
public final class GRUElasticsConstants
{
    // CONSTANTS
    public static final int INVALID_ID_INT = -1;
    public static final int TRUE = 1;
    public static final String SLASH = "/";
    public static final String INVALID_ID = "-1";
    public static final String OPEN_BRACE = "{";
    public static final String CLOSE_BRACE = "}";
    public static final String OPEN_SQUARE_BRACKET = "[";
    public static final String CLOSE_SQUARE_BRACKET = "]";
    public static final String MEDIA_TYPE_JSON = "application/json";
    public static final String MEDIA_TYPE_XML = "application/xml";
    public static final String PLUGIN_NAME = "elasticsearch/";
    public static final String MODULE_NAME = "gru-elastics" ;
    public static final String BEAN_DEMAND_MAPPING = "gru-elastics.demandMappingDAO" ;
    
    // PATHS
    public static final String PATH_WADL = "wadl";
    public static final String PATH_DATA_FROM_FLUX = "send/data";
    public static final String PATH_ELK_SERVER = "gru-elastics.urlElk";
    public static final String PATH_ELK_TYPE_DEMAND = "gru-elastics.pathDemand";
    public static final String PATH_ELK_TYPE_USER = "gru-elastics.pathUser";
    public static final String PATH_ELK_TYPE_NOTIFICATION = "gru-elastics.pathNotification";
    public static final String PATH_ELK_SOURCE = "/_source";
    public static final String PATH_ELK_UPDATE = "/_update";

    // PROPERTIES
    public static final String PROPERTY_ENCODING_ENABLE = "crm-rest.encoding.enable";
    public static final String PROPERTY_ENCODING_FROM = "crm-rest.encoding.from";
    public static final String PROPERTY_ENCODING_TO = "crm-rest.encoding.to";
    // Paths
    public static final String PATH_NOTIFICATION = "notification";
    
    //PARAMETERS
    public static final String PARAMETER_NOTIFICATION_DEMAND = "strJson";
    
    //BLOCS
    public static final String FIELD_JSON_BLOC_NOTIFICATION = "notification";
    public static final String FIELD_JSON_BLOC_USER_EMAIL = "user_email";
    public static final String FIELD_JSON_BLOC_USER_DASHBOARD = "user_dashboard";
    public static final String FIELD_JSON_BLOC_USER_SMS = "user_sms";
    public static final String FIELD_JSON_BLOC_BACKOFFICE_LOGGING = "backoffice_logging";
    
    
    //FLUX FIELDS
    public static final String FIELD_NOTIFICATION_USER_GUID = "user_guid";
    public static final String FIELD_NOTIFICATION_DEMAND_TYPE_ID = "demand_id_type";
    public static final String FIELD_NOTIFICATION_DEMAND_ID = "demand_id";
    public static final String FIELD_NOTIFICATION_REFERENCE = "reference";
    public static final String FIELD_NOTIFICATION_DEMAND_MAX_STEP = "demand_max_step";
    public static final String FIELD_NOTIFICATION_DEMAND_CURRENT_STEP = "demand_user_current_step";
    public static final String FIELD_NOTIFICATION_DEMAND_STATE = "demand_state";
    public static final String FIELD_NOTIFICATION_TYPE = "notification_type";
    public static final String FIELD_NOTIFICATION_CRM_STATUS_ID = "crm_status_id";
    public static final String FIELD_NOTIFICATION_EMAIL = "email";
    public static final String FIELD_BACKOFFICE_STATUS_TEXT = "status_text";
    public static final String FIELD_BACKOFFICE_MESSAGE = "message";
    public static final String FIELD_BACKOFFICE_NOTIFIED_ON_DASHBOARD = "notified_on_dashboard";
    public static final String FIELD_BACKOFFICE_NOTIFIED_BY_EMAIL = "notified_by_email";
    public static final String FIELD_BACKOFFICE_NOTIFIED_BY_SMS = "notified_by_sms";
    public static final String FIELD_BACKOFFICE_LEVEL_DASHBOARD_NOTIF ="display_level_dashboard_notification";
    public static final String FIELD_BACKOFFICE_VIEW_DASHBOARD_NOTIF = "view_dashboard_notification";
    public static final String FIELD_BACKOFFICE_LEVEL_EMAIL_NOTIF ="display_level_email_notification";
    public static final String FIELD_BACKOFFICE_VIEW_EMAIL_NOTIF ="view_email_notification";
    public static final String FIELD_BACKOFFICE_LEVEL_SMS_NOTIF = "display_level_sms_notification";
    public static final String FIELD_USER_EMAIL_SENDER_NAME = "sender_name";
    public static final String FIELD_USER_EMAIL_SENDER_EMAIL = "sender_email";
    public static final String FIELD_USER_EMAIL_RECIPIENT = "recipient";
    public static final String FIELD_USER_EMAIL_SUBJECT ="subject";
    public static final String FIELD_USER_EMAIL_MESSAGE = "message";
    public static final String FIELD_DASHBOARD_STATUS_TEXT = "status_text";
    public static final String FIELD_DASHBORD_SENDER_NAME = "sender_name";
    public static final String FIELD_DASHBORD_SUBJECT ="subject";
    public static final String FIELD_DASHBORD_MESSAGE ="message";
    public static final String FIELD_DASHBORD_DATA = "data";
    public static final String FIELD_USER_SMS_PHONE = "phone_number";
    public static final String FIELD_USER_SMS_MESSAGE = "message";
    
    
    
    
    // DEMAND FIELDS
    public static final String FIELD_DEMAND_USER = "\"utilisateur\":";
    public static final String FIELD_DEMAND_USER_GUID = "\"user_guid\":";
    public static final String FIELD_DEMAND_ID = "\"demand_id\":"; 
    public static final String FIELD_DEMAND_ID_TYPE = "\"demand_id_type\":";
    public static final String FIELD_DEMAND_MAX_STEP = "\"demand_max_step\":";
    public static final String FIELD_DEMAND_CURRENT_STEP = "\"demand_user_current_step\":";
    public static final String FIELD_DEMAND_STATE = "\"demand_state\":";
    public static final String FIELD_DEMAND_NOTIFICATION_TYPE = "\"notification_type\":";
    public static final String FIELD_DEMAND_DATE = "\"date_demande\":";
    public static final String FIELD_CRM_STATUS_ID = "\"crm_status_id\":";
    public static final String FIELD_DEMAND_REFERENCE = "\"reference\":";
    
    //USER FIELDS
    public static final String FIELD_USER_GUID = "\"user_guid\":";
    public static final String FIELD_USER_EMAIL = "\"email\":";
    public static final String FIELD_USER_NAME = "\"name\":";
    public static final String FIELD_USER_STAYCONNECTED = "\"stayConnected\":";
    public static final String FIELD_USER_STREET = "\"street\":";
    public static final String FIELD_USER_PHONE_NUMBER = "\"telephoneNumber\" :";
    public static final String FIELD_USER_CITY = "\"city\" :";
    public static final String FIELD_USER_CITYOFBIRTH = "\"cityOfBirth\" :";
    public static final String FIELD_USER_BIRTHDAY = "\"birthday\" :";
    public static final String FIELD_USER_CIVILITY = "\"civility\":";
    public static final String FIELD_USER_POSTAL_CODE = "\"postalCode\":";
    
    
    //NOTIFICATION FIELDS 
    public static final String FIELD_NOTIF_SOLLICITATION = "\"sollicitation\":";
    public static final String FIELD_NOTIF_DEMAND_ID = "\"demand_id\":";
    public static final String FIELD_NOTIF_STATUS_TEXT = "\"status_text\":";
    public static final String FIELD_NOTIF_MESSAGE = "\"message\":";
    public static final String FIELD_NOTIFIED_ON_DASHBOARD = "\"notified_on_dashboard\":";
    public final static String FIELD_NOTIFIED_BY_EMAIL = "\"notified_by_email\":";
    public static final String FIELD_NOTIFIED_BY_SMS = "\"notified_by_sms\":";
    public static final String FIELD_NOTIF_LEVEL_DASHBOARD_NOTIF = "\"display_level_dashboard_notification\":";
    public static final String FIELD_NOTIF_VIEW_DASHBOARD_NOTIF = "\"view_dashboard_notification\":";
    public static final String FIELD_NOTIF_LEVEL_EMAIL_NOTIF ="\"display_level_email_notification\":";
    public static final String FIELD_NOTIF_VIEW_EMAIL_NOTIF = "\"view_email_notification\":";
    public static final String FIELD_NOTIF_LEVEL_SMS_NOTIF = "\"display_level_sms_notification\":";
    public static final String FIELD_NOTIF_VIEW_SMS_NOTIF = "\"view_sms_notification\":";
    public static final String FIELD_NOTIF_DATE_SOLLICITATION = "\"date_sollicitation\":";
    public static final String FIELD_NOTIF_USER_MAIL = "\"user_email\":";
    public static final String FIELD_NOTIF_SENDER_NAME = "\"sender_name\":";
    public static final String FIELD_NOTIF_SENDER_EMAIL = "\"sender_email\":";
    public static final String FIELD_NOTIF_RECIPIENT = "\"recipient\":";
    public static final String FIELD_NOTIF_SUBJECT = "\"subject\":";
    public static final String FIELD_NOTIF_USER_EMAIL_MESSAGE = "\"message\":";
    public static final String FIELD_NOTIF_USER_DASHBOARD ="\"user_dashboard\":";
    public static final String FIELD_NOTIF_DASHBOARD_STATUS_TEXT = "\"status_text\":";
    public static final String FIELD_NOTIF_DASHBOARD_SENDER_NAME = "\"sender_name\":";
    public static final String FIELD_NOTIF_DASHBOARD_SUBJECT = "\"subject\":";
    public static final String FIELD_NOTIF_DASHBOARD_MESSAGE = "\"message\":";
    public static final String FIELD_NOTIF_DASHBOARD_DATA = "\"data\":";
    public static final String FIELD_NOTIF_USER_SMS = "\"user_sms\":";
    public static final String FIELD_NOTIF_USER_SMS_PHONE_NUMBER = "\"phone_number\":";
    public static final String FIELD_NOTIF_USER_SMS_MESSAGE = "\"message\":";
    
    
    //USER_SMS FIELDS
    public static final String FIELD_USER_SMS_PHONE_NUMBER = "phone_number"; 
    
    //SUGGEST FIELDS
    public static final String FIELD_SUGGEST = "\"suggest\":";
    public static final String FIELD_SUGGEST_INPUT = "\"input\":";
    public static final String FIELD_SUGGEST_OUTPUT = "\"output\":";
    public static final String FIELD_SUGGEST_PAYLOAD = "\"payload\":";
    public static final String FIELD_SUGGEST_BIRTHDAY = "\"birthday\":";
    public static final String FIELD_SUGGEST_PHONE_NUMBER = "\"telephoneNumber\":";
    public static final String FIELD_SUGGEST_EMAIL = "\" email\":" ;
    
    
    //RESULT FIELDS
    public static final String FIELD_RESULT_CREATED  = "successful" ; 
    public static final String FIELD_RESULT_ID  = "_id" ;
    /**
     * Private constructor
     */
    private GRUElasticsConstants(  )
    {
    }
}
