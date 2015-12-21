/*
 * Copyright (c) 2002-2014, Mairie de Paris
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

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.mapper.object.ObjectMapper;

import fr.paris.lutece.plugins.gru.modules.elastics.util.constants.GRUElasticsConstants;


/**
 *
 * GRUElasticsRest
 *
 */
@Path( GRUElasticsConstants.BASE_PATH + GRUElasticsConstants.PLUGIN_NAME )
public class GRUElasticsRest
{
    
    /**
     * Index data
     * @param strJson The JSon to store
     * @return the status of insert or modify
     */
    @POST
    @Path( GRUElasticsConstants.PATH_NOTIFICATION )
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes( MediaType.APPLICATION_JSON )
    public String Notification ( String strJson )
    {
        Client client = new TransportClient( ).addTransportAddress( new InetSocketTransportAddress( "localhost", 8900 ) ); 
        ObjectMapper objectMapper = new ObjectMapper();
        IndexRequestBuilder index = client.prepareIndex( config.getIndex( ), "simple" )
                .setSource( strJson );
        index.execute( ).actionGet( );
    }

    /**
     * Create a new demand
     * @param strIdDemandType the demand type id
     * @param strIdCRMUser the ID CRM user
     * @param strIdStatusCRM the id status crm
     * @param strStatusText the status text
     * @param strData the data
     * @param request {@link HttpServletRequest}
     * @return the id of the newly created demand
     */
    @POST
    @Path( GRUElasticsConstants.PATH_CREATE_DEMAND_BY_ID_CRM_USER )
    @Produces( MediaType.TEXT_PLAIN )
    @Consumes( MediaType.APPLICATION_FORM_URLENCODED )
    public String doCreateDemandByIdCRMUser( 
        @FormParam( GRUElasticsConstants.PARAMETER_ID_DEMAND_TYPE )
    String strIdDemandType, @FormParam( GRUElasticsConstants.PARAMETER_ID_CRM_USER )
    String strIdCRMUser, @FormParam( GRUElasticsConstants.PARAMETER_ID_STATUS_CRM )
    String strIdStatusCRM, @FormParam( GRUElasticsConstants.PARAMETER_STATUS_TEXT )
    String strStatusText, @FormParam( GRUElasticsConstants.PARAMETER_DEMAND_DATA )
    String strData, @Context
    HttpServletRequest request )
    {
        String strIdDemand = GRUElasticsConstants.INVALID_ID;

        if ( StringUtils.isNotBlank( strIdDemandType ) && StringUtils.isNumeric( strIdDemandType ) &&
                StringUtils.isNotBlank( strIdStatusCRM ) && StringUtils.isNumeric( strIdStatusCRM ) &&
                StringUtils.isNotBlank( strIdCRMUser ) && StringUtils.isNumeric( strIdCRMUser ) )
        {
            int nIdCRMUser = Integer.parseInt( strIdCRMUser );
            CRMUser crmUser = CRMUserService.getService(  ).findByPrimaryKey( nIdCRMUser );

            if ( crmUser != null )
            {
                int nIdDemandType = Integer.parseInt( strIdDemandType );
                DemandType demandType = DemandTypeService.getService(  ).findByPrimaryKey( nIdDemandType );

                if ( demandType != null )
                {
                    int nIdStatusCRM = Integer.parseInt( strIdStatusCRM );
                    DemandStatusCRM statusCRM = DemandStatusCRMService.getService(  )
                                                                      .getStatusCRM( nIdStatusCRM, request.getLocale(  ) );

                    if ( statusCRM != null )
                    {
                        String strConvertedStatusText = StringUtil.convertString( strStatusText );
                        String strConvertedData = StringUtil.convertString( strData );
                        strIdDemand = Integer.toString( CRMService.getService(  )
                                                                  .registerDemand( nIdDemandType, nIdCRMUser,
                                    strConvertedData, strConvertedStatusText, nIdStatusCRM ) );
                    }
                    else
                    {
                        AppLogService.error( GRUElasticsConstants.MESSAGE_CRM_REST +
                            GRUElasticsConstants.MESSAGE_INVALID_ID_STATUS_CRM );
                    }
                }
                else
                {
                    AppLogService.error( GRUElasticsConstants.MESSAGE_CRM_REST +
                        GRUElasticsConstants.MESSAGE_INVALID_DEMAND_TYPE );
                }
            }
            else
            {
                AppLogService.error( GRUElasticsConstants.MESSAGE_CRM_REST + GRUElasticsConstants.MESSAGE_INVALID_USER );
            }
        }
        else
        {
            AppLogService.error( GRUElasticsConstants.MESSAGE_MANDATORY_FIELDS );
        }

        return strIdDemand;
    }

    /**
     * Update a demand
     * @param strIdDemand the id demand
     * @param strIdStatusCRM the id status crm
     * @param strStatusText the status text
     * @param strData the data
     * @param request {@link HttpServletRequest}
     * @return the id of the demand
     */
    @POST
    @Path( GRUElasticsConstants.PATH_UPDATE_DEMAND )
    @Produces( MediaType.TEXT_PLAIN )
    @Consumes( MediaType.APPLICATION_FORM_URLENCODED )
    public String doUpdateDemand( @FormParam( GRUElasticsConstants.PARAMETER_ID_DEMAND )
    String strIdDemand, @FormParam( GRUElasticsConstants.PARAMETER_ID_STATUS_CRM )
    String strIdStatusCRM, @FormParam( GRUElasticsConstants.PARAMETER_STATUS_TEXT )
    String strStatusText, @FormParam( GRUElasticsConstants.PARAMETER_DEMAND_DATA )
    String strData, @Context
    HttpServletRequest request )
    {
        if ( StringUtils.isNotBlank( strIdDemand ) && StringUtils.isNumeric( strIdDemand ) )
        {
            int nIdDemand = Integer.parseInt( strIdDemand );
            Demand demand = DemandService.getService(  ).findByPrimaryKey( nIdDemand );

            if ( demand != null )
            {
                int nIdStatusCRM = GRUElasticsConstants.INVALID_ID_INT;
                DemandStatusCRM statusCRM = null;

                if ( StringUtils.isNotBlank( strIdStatusCRM ) && StringUtils.isNumeric( strIdStatusCRM ) )
                {
                    nIdStatusCRM = Integer.parseInt( strIdStatusCRM );
                    statusCRM = DemandStatusCRMService.getService(  ).getStatusCRM( nIdStatusCRM, request.getLocale(  ) );
                }

                if ( ( statusCRM != null ) || ( nIdStatusCRM == GRUElasticsConstants.INVALID_ID_INT ) )
                {
                    String strConvertedStatusText = StringUtil.convertString( strStatusText );
                    String strConvertedData = StringUtil.convertString( strData );
                    CRMService.getService(  )
                              .setStatus( nIdDemand, strConvertedData, strConvertedStatusText, nIdStatusCRM );
                }
                else
                {
                    AppLogService.error( GRUElasticsConstants.MESSAGE_CRM_REST +
                        GRUElasticsConstants.MESSAGE_INVALID_ID_STATUS_CRM );
                }
            }
            else
            {
                AppLogService.error( GRUElasticsConstants.MESSAGE_CRM_REST + GRUElasticsConstants.MESSAGE_INVALID_DEMAND );
            }
        }
        else
        {
            AppLogService.error( GRUElasticsConstants.MESSAGE_CRM_REST + GRUElasticsConstants.MESSAGE_MANDATORY_FIELDS );
        }

        return strIdDemand;
    }

    /**
     * Delete a demand
     * @param strIdDemand the id demand
     * @return the id of the demand
     */
    @POST
    @Path( GRUElasticsConstants.PATH_DELETE_DEMAND )
    @Produces( MediaType.TEXT_PLAIN )
    @Consumes( MediaType.APPLICATION_FORM_URLENCODED )
    public String doDeleteDemand( @FormParam( GRUElasticsConstants.PARAMETER_ID_DEMAND )
    String strIdDemand )
    {
        if ( StringUtils.isNotBlank( strIdDemand ) && StringUtils.isNumeric( strIdDemand ) )
        {
            int nIdDemand = Integer.parseInt( strIdDemand );
            Demand demand = DemandService.getService(  ).findByPrimaryKey( nIdDemand );

            if ( demand != null )
            {
                CRMService.getService(  ).deleteDemand( nIdDemand );
            }
            else
            {
                AppLogService.error( GRUElasticsConstants.MESSAGE_CRM_REST + GRUElasticsConstants.MESSAGE_INVALID_DEMAND );
            }
        }
        else
        {
            AppLogService.error( GRUElasticsConstants.MESSAGE_CRM_REST + GRUElasticsConstants.MESSAGE_MANDATORY_FIELDS );
        }

        return strIdDemand;
    }
    
    /**
     * Get the demand in XML or demand JSON depending the value of strMediaType
     * @param strIdDemand the id demand
     * @param strMediaType the media type selected
     * @return the demand
     */
    @GET
    @Path( GRUElasticsConstants.PATH_VIEW_DEMAND )
    public String getDemand( @PathParam( GRUElasticsConstants.PARAMETER_ID_DEMAND )
    String strIdDemand, @QueryParam( GRUElasticsConstants.PARAMETER_MEDIA_TYPE) String strMediaType )
    {
      if ( StringUtils.isNotBlank( strMediaType ) && strMediaType.equals(GRUElasticsConstants.MEDIA_TYPE_JSON) )
        {
        	return getDemandJson(strIdDemand);
        	
        }
       return getDemandXML(strIdDemand);
    }
    
    

    /**
     * Get the XML of the demand
     * @param strIdDemand the id demand
     * @return the XML of the demand
     */
    @GET
    @Path( GRUElasticsConstants.PATH_VIEW_DEMAND )
    @Produces( MediaType.APPLICATION_XML )
    public String getDemandXML( @PathParam( GRUElasticsConstants.PARAMETER_ID_DEMAND )
    String strIdDemand )
    {
        StringBuffer sbXML = new StringBuffer(  );

        if ( StringUtils.isNotBlank( strIdDemand ) && StringUtils.isNumeric( strIdDemand ) )
        {
            int nIdDemand = Integer.parseInt( strIdDemand );
            Demand demand = DemandService.getService(  ).findByPrimaryKey( nIdDemand );

            if ( demand != null )
            {
                // sbXML.append( XmlUtil.getXmlHeader(  ) );
                sbXML.append( GRUElasticsConstants.XML_HEADER );
                XmlUtil.beginElement( sbXML, GRUElasticsConstants.TAG_DEMAND );

                XmlUtil.addElement( sbXML, GRUElasticsConstants.TAG_ID_DEMAND, demand.getIdDemand(  ) );
                XmlUtil.addElement( sbXML, GRUElasticsConstants.TAG_ID_DEMAND_TYPE, demand.getIdDemandType(  ) );
                XmlUtil.addElement( sbXML, GRUElasticsConstants.TAG_STATUS_TEXT, demand.getStatusText(  ) );
                XmlUtil.addElement( sbXML, GRUElasticsConstants.TAG_ID_STATUS_CRM, demand.getIdStatusCRM(  ) );
                XmlUtil.addElement( sbXML, GRUElasticsConstants.TAG_DATA, demand.getData(  ) );
                XmlUtil.addElement( sbXML, GRUElasticsConstants.TAG_USER_GUID, demand.getIdCRMUser(  ) );

                String strDateModification = DateUtil.getDateString( demand.getDateModification(  ), null );
                XmlUtil.addElement( sbXML, GRUElasticsConstants.TAG_DATE_MODIFICATION, strDateModification );
                XmlUtil.addElement( sbXML, GRUElasticsConstants.TAG_NB_NOTIFICATIONS, demand.getNumberNotifications(  ) );

                XmlUtil.endElement( sbXML, GRUElasticsConstants.TAG_DEMAND );
            }
            else
            {
                AppLogService.error( GRUElasticsConstants.MESSAGE_CRM_REST + GRUElasticsConstants.MESSAGE_DEMAND_NOT_FOUND );
                sbXML.append( XMLUtil.formatError( GRUElasticsConstants.MESSAGE_DEMAND_NOT_FOUND, 3 ) );
            }
        }
        else
        {
            AppLogService.error( GRUElasticsConstants.MESSAGE_CRM_REST + GRUElasticsConstants.MESSAGE_INVALID_DEMAND );
            sbXML.append( XMLUtil.formatError( GRUElasticsConstants.MESSAGE_INVALID_DEMAND, 3 ) );
        }

        return sbXML.toString(  );
    }

    /**
     * Get the Json of the demand
     * @param strIdDemand the id of the demand
     * @return the Json of the demand
     */
    @GET
    @Path( GRUElasticsConstants.PATH_VIEW_DEMAND )
    @Produces( MediaType.APPLICATION_JSON )
    public String getDemandJson( @PathParam( GRUElasticsConstants.PARAMETER_ID_DEMAND )
    String strIdDemand )
    {
        String strJSON = StringUtils.EMPTY;

        if ( StringUtils.isNotBlank( strIdDemand ) && StringUtils.isNumeric( strIdDemand ) )
        {
            int nIdDemand = Integer.parseInt( strIdDemand );
            Demand demand = DemandService.getService(  ).findByPrimaryKey( nIdDemand );

            if ( demand != null )
            {
                JSONObject json = new JSONObject(  );
                json.accumulate( GRUElasticsConstants.TAG_ID_DEMAND, demand.getIdDemand(  ) );
                json.accumulate( GRUElasticsConstants.TAG_ID_DEMAND_TYPE, demand.getIdDemandType(  ) );
                json.accumulate( GRUElasticsConstants.TAG_STATUS_TEXT, demand.getStatusText(  ) );
                json.accumulate( GRUElasticsConstants.TAG_ID_STATUS_CRM, demand.getIdStatusCRM(  ) );
                json.accumulate( GRUElasticsConstants.TAG_DATA, demand.getData(  ) );
                json.accumulate( GRUElasticsConstants.TAG_USER_GUID, demand.getIdCRMUser(  ) );

                String strDateModification = DateUtil.getDateString( demand.getDateModification(  ), null );
                json.accumulate( GRUElasticsConstants.TAG_DATE_MODIFICATION, strDateModification );
                json.accumulate( GRUElasticsConstants.TAG_NB_NOTIFICATIONS, demand.getNumberNotifications(  ) );

                JSONObject jsonDemand = new JSONObject(  );
                jsonDemand.accumulate( GRUElasticsConstants.TAG_DEMAND, json );
                strJSON = jsonDemand.toString( 4 );
            }
            else
            {
                AppLogService.error( GRUElasticsConstants.MESSAGE_CRM_REST + GRUElasticsConstants.MESSAGE_DEMAND_NOT_FOUND );
                strJSON = JSONUtil.formatError( GRUElasticsConstants.MESSAGE_DEMAND_NOT_FOUND, 3 );
            }
        }
        else
        {
            AppLogService.error( GRUElasticsConstants.MESSAGE_CRM_REST + GRUElasticsConstants.MESSAGE_INVALID_DEMAND );
            strJSON = JSONUtil.formatError( GRUElasticsConstants.MESSAGE_INVALID_DEMAND, 3 );
        }

        return strJSON;
    }

    /**
     * Get the user guid from a given id demand
     * @param strIdDemand the id demand
     * @return the user guid
     */
    @GET
    @Path( GRUElasticsConstants.PATH_GET_USER_GUID_FROM_ID_DEMAND )
    @Produces( MediaType.TEXT_PLAIN )
    public String getUserGuidFromIdDemand( @PathParam( GRUElasticsConstants.PARAMETER_ID_DEMAND )
    String strIdDemand )
    {
        String strUserGuid = StringUtils.EMPTY;

        if ( StringUtils.isNotBlank( strIdDemand ) && StringUtils.isNumeric( strIdDemand ) )
        {
            int nIdDemand = Integer.parseInt( strIdDemand );
            Demand demand = DemandService.getService(  ).findByPrimaryKey( nIdDemand );

            if ( demand != null )
            {
                int nIdCRMUser = demand.getIdCRMUser(  );
                CRMUser user = CRMUserService.getService(  ).findByPrimaryKey( nIdCRMUser );

                if ( user != null )
                {
                    strUserGuid = user.getUserGuid(  );
                }
            }
        }
        else
        {
            AppLogService.error( GRUElasticsConstants.MESSAGE_CRM_REST + GRUElasticsConstants.MESSAGE_INVALID_USER );
        }

        return strUserGuid;
    }
}
