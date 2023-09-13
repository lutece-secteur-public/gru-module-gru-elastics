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
package fr.paris.lutece.plugins.grusupply.constant;

public class GruSupplyConstants
{
    // REST CONSTANTS
    public static final String PLUGIN_NAME = "grusupply/";
    public static final String PATH_DEMAND = "demand/";
    public static final String PATH_DEMAND_LIST = "list";
    public static final String PATH_DEMAND_STATUS = "status";
    public static final String PATH_TYPE_DEMAND = "type";
    public static final String PATH_TYPE_NOTIFICATION = "notification_type";
    public static final String PATH_NOTIFICATION_LIST = "notification/list";
    
    
    public static final String QUERY_PARAM_INDEX = "index";
    public static final String QUERY_PARAM_ID_DEMAND_TYPE = "idDemandType";
    public static final String QUERY_PARAM_CUSTOMER_ID = "customerId";
    public static final String QUERY_PARAM_NOTIFICATION_TYPE = "notificationType";
    public static final String QUERY_PARAM_ID_DEMAND = "idDemand";
    public static final String QUERY_PARAM_LIST_STATUS = "listStatus";
    
    // EXIT STATUS
    public static final String STATUS_201 = "{" + "\"status\":" + "\"201\"" + "}";
    public static final String STATUS_404 = "{" + "\"status\":" + "\"404\"" + "}";

    /** The Constant DEFAULT_INT. */
    public static final int DEFAULT_INT = -1;
    
    // PROPERTIES
    /** The Constant LIMIT_DEMAND_API_REST. */
    public static final String LIMIT_DEMAND_API_REST = "grusupply.api.rest.limit.demand";
    
    //MESSAGE
    public static final String MESSAGE_ERROR_DEMAND = "Parameter customerId is mandatory";
    public static final String MESSAGE_ERROR_STATUS = "Parameters customerId and listStatus are mandatory ( can separated status by ,)";
    public static final String MESSAGE_ERROR_NOTIF  = "Parameters idDemand, customerId and idDemandType are mandatory " ;
}