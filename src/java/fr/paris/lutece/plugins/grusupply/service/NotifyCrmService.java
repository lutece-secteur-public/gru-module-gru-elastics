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
package fr.paris.lutece.plugins.grusupply.service;

import fr.paris.lutece.plugins.crmclient.business.CRMItemTypeEnum;
import fr.paris.lutece.plugins.crmclient.business.ICRMItem;
import fr.paris.lutece.plugins.crmclient.util.CRMException;
import fr.paris.lutece.plugins.grubusiness.business.notification.NotifyGruGlobalNotification;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.httpaccess.HttpAccess;
import fr.paris.lutece.util.httpaccess.HttpAccessException;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;


public class NotifyCrmService
{
    private static final String CRM_REMOTE_ID = "remote_id";
    private static final String KEY_DEMAND = "demand";
    private static final String SLASH = "/";
    private static final String URL_WS_GET_DEMAND = "grusupply.url.ws.getDemand";
    private static final String URL_WS_CREATE_DEMAND = "grusupply.url.ws.createDemandByUserGuid";
    private static final String URL_WS_UPDATE_DEMAND = "grusupply.url.ws.updateDemand";
    private static final String URL_WS_NOTIFY_DEMAND = "grusupply.url.ws.notifyDemand";

    /**  constructor */
    public NotifyCrmService(  )
    {
    }

    /**
     * Tests whether the demand already exists or not
     * @param notif the notification
     * @return {@code true} if the demand already exists, {@code false} otherwise
     * @throws CRMException if there is an exception during the treatment
     */
    public boolean isExistDemand( NotifyGruGlobalNotification notif )
        throws CRMException
    {
        boolean bIsExistDemand = false;

        AppLogService.info( " \n \n GRUSUPPLY - isExistDemand( NotificationDTO notif ) \n \n" );

        String strIdDemandType = String.valueOf( notif.getDemandTypeId(  ) );
        String strIdRemoteDemand = String.valueOf( notif.getRemoteDemandId(  ) );

        String strResponse = doProcess( AppPropertiesService.getProperty( URL_WS_GET_DEMAND ) + SLASH +
                strIdDemandType + SLASH + strIdRemoteDemand );

        if ( JSONUtils.mayBeJSON( strResponse ) )
        {
            JSONObject jsonResponse = (JSONObject) JSONSerializer.toJSON( strResponse );

            if ( jsonResponse.has( KEY_DEMAND ) )
            {
                bIsExistDemand = true;
            }
        }

        return bIsExistDemand;
    }

    /**
     * Create CRM Demand
     * @param notif
     * @throws CRMException
     */
    public void createDemand( NotifyGruGlobalNotification notif )
        throws CRMException
    {
        AppLogService.info( " \n \n GRUSUPPLY - sendCreateDemand( NotificationDTO notif ) \n \n" );

        ICRMItem crmItem = buildCrmItemForDemand( notif, CRMItemTypeEnum.DEMAND_CREATE_BY_USER_GUID );

        doProcess( crmItem, AppPropertiesService.getProperty( URL_WS_CREATE_DEMAND ) );
    }

    /**
     * Updates a CRM Demand
     * @param notif the notification
     * @throws CRMException if there is an exception during the treatment
     */
    public void updateDemand( NotifyGruGlobalNotification notif )
        throws CRMException
    {
        AppLogService.info( " \n \n GRUSUPPLY - updateDemand( NotificationDTO notif ) \n \n" );

        ICRMItem crmItem = buildCrmItemForDemand( notif, CRMItemTypeEnum.DEMAND_UPDATE );

        doProcess( crmItem, AppPropertiesService.getProperty( URL_WS_UPDATE_DEMAND ) );
    }

    /**
     * Notify CRM Demand
     * @param notif
     * @throws CRMException
     */
    public void notify( NotifyGruGlobalNotification notif )
        throws CRMException
    {
        AppLogService.info( " \n \n GRUSUPPLY - notify( NotificationDTO notif ) \n \n" );

        ICRMItem crmItem = buildCrmItemForNotification( notif, CRMItemTypeEnum.NOTIFICATION );

        doProcess( crmItem, AppPropertiesService.getProperty( URL_WS_NOTIFY_DEMAND ) );
    }

    /**
     * Call web service rest using POST method
     * @param crmItem the parameters
     * @param strWsUrl the web service URL
     * @return the response
     * @throws CRMException
     */
    private String doProcess( ICRMItem crmItem, String strWsUrl )
        throws CRMException
    {
        String strResponse = StringUtils.EMPTY;

        try
        {
            HttpAccess httpAccess = new HttpAccess(  );
            strResponse = httpAccess.doPost( strWsUrl, crmItem.getParameters(  ) );
        }
        catch ( HttpAccessException e )
        {
            String strError = "Error connecting to '" + strWsUrl + "' : ";
            AppLogService.error( strError + e.getMessage(  ), e );
            throw new CRMException( strError, e );
        }

        return strResponse;
    }

    /**
     * Call web service rest using GET method
     * @param crmItem the parameters
     * @param strWsUrl the web service URL
     * @return the response
     * @throws CRMException
     */
    private String doProcess( String strWsUrl ) throws CRMException
    {
        String strResponse = StringUtils.EMPTY;

        try
        {
            HttpAccess httpAccess = new HttpAccess(  );
            Map<String, String> mapHeaders = new HashMap<String, String>(  );
            mapHeaders.put( HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON );

            strResponse = httpAccess.doGet( strWsUrl, null, null, mapHeaders );
        }
        catch ( HttpAccessException e )
        {
            String strError = "Error connecting to '" + strWsUrl + "' : ";
            AppLogService.error( strError + e.getMessage(  ), e );
            throw new CRMException( strError, e );
        }

        return strResponse;
    }

    /**
     * Builds a CrmItem object for a demand
     * @param notif the notification
     * @param crmItemType the CrmItemType
     * @return the CrmItem
     */
    private static ICRMItem buildCrmItemForDemand( NotifyGruGlobalNotification notif, CRMItemTypeEnum crmItemType )
    {
        ICRMItem crmItem = SpringContextService.getBean( crmItemType.toString(  ) );

        crmItem.putParameter( ICRMItem.ID_DEMAND_TYPE,
            ( notif.getDemandTypeId(  ) != 0 ) ? String.valueOf( notif.getDemandTypeId(  ) ) : StringUtils.EMPTY );

        crmItem.putParameter( ICRMItem.USER_GUID,
            StringUtils.isNotBlank( notif.getGuid(  ) ) ? notif.getGuid(  ) : StringUtils.EMPTY );

        crmItem.putParameter( ICRMItem.ID_STATUS_CRM, String.valueOf( notif.getCrmStatusId(  ) ) );

        crmItem.putParameter( ICRMItem.STATUS_TEXT,
            StringUtils.isNotBlank( notif.getUserDashboard(  ).getStatusText(  ) )
            ? notif.getUserDashboard(  ).getStatusText(  ) : StringUtils.EMPTY );

        crmItem.putParameter( ICRMItem.DEMAND_DATA,
            StringUtils.isNotBlank( notif.getUserDashboard(  ).getData(  ) ) ? notif.getUserDashboard(  ).getData(  )
                                                                             : StringUtils.EMPTY );

        crmItem.putParameter( CRM_REMOTE_ID, String.valueOf( notif.getRemoteDemandId(  ) ) );

        return crmItem;
    }

    /**
     * Builds a CrmItem object for a notification
     * @param notif the notification
     * @param crmItemType the CrmItemType
     * @return the CrmItem
     */
    private static ICRMItem buildCrmItemForNotification( NotifyGruGlobalNotification notif, CRMItemTypeEnum crmItemType )
    {
        ICRMItem crmItem = SpringContextService.getBean( crmItemType.toString(  ) );

        crmItem.putParameter( CRM_REMOTE_ID, String.valueOf( notif.getRemoteDemandId(  ) ) );

        crmItem.putParameter( ICRMItem.ID_DEMAND_TYPE,
            ( notif.getDemandTypeId(  ) != 0 ) ? String.valueOf( notif.getDemandTypeId(  ) ) : StringUtils.EMPTY );

        crmItem.putParameter( ICRMItem.NOTIFICATION_OBJECT,
            StringUtils.isNotBlank( notif.getUserDashboard(  ).getSubject(  ) )
            ? notif.getUserDashboard(  ).getSubject(  ) : StringUtils.EMPTY );

        crmItem.putParameter( ICRMItem.NOTIFICATION_MESSAGE,
            StringUtils.isNotBlank( notif.getUserDashboard(  ).getMessage(  ) )
            ? notif.getUserDashboard(  ).getMessage(  ) : StringUtils.EMPTY );

        crmItem.putParameter( ICRMItem.NOTIFICATION_SENDER,
            StringUtils.isNotBlank( notif.getUserDashboard(  ).getSenderName(  ) )
            ? notif.getUserDashboard(  ).getSenderName(  ) : StringUtils.EMPTY );

        return crmItem;
    }
}
