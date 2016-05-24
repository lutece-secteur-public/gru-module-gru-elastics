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
import fr.paris.lutece.plugins.grusupply.business.dto.NotificationDTO;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.httpaccess.HttpAccess;
import fr.paris.lutece.util.httpaccess.HttpAccessException;

import org.apache.commons.lang.StringUtils;


public class NotifyCrmService
{
    private static String CRM_REMOTE_ID = "remote_id";
    public static final String URL_WS_CREATE_DEMAND = "grusupply.url.ws.createDemandByUserGuid";
    public static final String URL_WS_NOTIFY_DEMAND = "grusupply.url.ws.notifyDemand";

    /**  constructor */
    public NotifyCrmService(  )
    {
    }

    /**
     * Create CRM Demand
     * @param notif
     * @throws CRMException
     */
    public void sendCreateDemand( NotificationDTO notif )
        throws CRMException
    {
        ICRMItem crmItem = SpringContextService.getBean( CRMItemTypeEnum.DEMAND_CREATE_BY_USER_GUID.toString(  ) );

        crmItem.putParameter( ICRMItem.ID_DEMAND_TYPE,
            ( notif.getDemandTypeId(  ) != 0 ) ? new Integer( notif.getDemandTypeId(  ) ).toString(  ) : StringUtils.EMPTY );

        crmItem.putParameter( ICRMItem.USER_GUID,
            StringUtils.isNotBlank( notif.getUserGuid(  ) ) ? notif.getUserGuid(  ) : StringUtils.EMPTY );

        crmItem.putParameter( ICRMItem.ID_STATUS_CRM, new Integer( notif.getCrmStatusId(  ) ).toString(  ) );

        crmItem.putParameter( ICRMItem.STATUS_TEXT,
            StringUtils.isNotBlank( notif.getUserDashBoard(  ).getStatusText(  ) )
            ? notif.getUserDashBoard(  ).getStatusText(  ) : StringUtils.EMPTY );

        crmItem.putParameter( ICRMItem.DEMAND_DATA,
            StringUtils.isNotBlank( notif.getUserDashBoard(  ).getData(  ) ) ? notif.getUserDashBoard(  ).getData(  )
                                                                             : StringUtils.EMPTY );

        crmItem.putParameter( CRM_REMOTE_ID, new Integer( notif.getRemoteDemandeId(  ) ).toString(  ) );

        doProcess( crmItem, AppPropertiesService.getProperty( URL_WS_CREATE_DEMAND ) );
    }

    /**
     * Notify CRM Demand
     * @param notif
     * @throws CRMException
     */
    public void notify( NotificationDTO notif ) throws CRMException
    {
        ICRMItem crmItem = SpringContextService.getBean( CRMItemTypeEnum.NOTIFICATION.toString(  ) );

        crmItem.putParameter( CRM_REMOTE_ID, new Integer( notif.getRemoteDemandeId(  ) ).toString(  ) );

        crmItem.putParameter( ICRMItem.ID_DEMAND_TYPE,
            ( notif.getDemandTypeId(  ) != 0 ) ? new Integer( notif.getDemandTypeId(  ) ).toString(  ) : StringUtils.EMPTY );

        crmItem.putParameter( ICRMItem.NOTIFICATION_OBJECT,
            StringUtils.isNotBlank( notif.getUserDashBoard(  ).getSubject(  ) )
            ? notif.getUserDashBoard(  ).getSubject(  ) : StringUtils.EMPTY );

        crmItem.putParameter( ICRMItem.NOTIFICATION_MESSAGE,
            StringUtils.isNotBlank( notif.getUserDashBoard(  ).getMessage(  ) )
            ? notif.getUserDashBoard(  ).getMessage(  ) : StringUtils.EMPTY );

        crmItem.putParameter( ICRMItem.NOTIFICATION_SENDER,
            StringUtils.isNotBlank( notif.getUserDashBoard(  ).getSenderName(  ) )
            ? notif.getUserDashBoard(  ).getSenderName(  ) : StringUtils.EMPTY );

        doProcess( crmItem, AppPropertiesService.getProperty( URL_WS_NOTIFY_DEMAND ) );
    }

    /**
     * call web service rest
     * @param crmItem the parameters
     * @param strWsUrl the web service URL
     * @return the response
     * @throws CRMException
     */
    public String doProcess( ICRMItem crmItem, String strWsUrl )
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
}
