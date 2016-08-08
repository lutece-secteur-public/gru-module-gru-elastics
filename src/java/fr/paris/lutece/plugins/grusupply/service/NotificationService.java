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

import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import fr.paris.lutece.plugins.crmclient.util.CRMException;
import fr.paris.lutece.plugins.grusupply.business.Customer;
import fr.paris.lutece.plugins.grusupply.business.dto.NotificationDTO;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;


public class NotificationService
{
    private static final String BEAN_NOTIFICATION_SERVICE = "grusupply.notificationService";
    private static NotificationService _singleton;
    private static SendEmailService _sendEmailService;
    private static SendSmsService _sendSmsService;
    private static NotifyCrmService _notifyCrmService;
    private static boolean bIsInitialized = false;

    /**
     * Returns the unique instance
     * @return The unique instance
     */
    public static NotificationService instance(  )
    {
        if ( !bIsInitialized )
        {
            try
            {
                _singleton = SpringContextService.getBean( BEAN_NOTIFICATION_SERVICE );
                _sendEmailService = new SendEmailService(  );
                _sendSmsService = new SendSmsService(  );
                _notifyCrmService = new NotifyCrmService(  );
            }
            catch ( NoSuchBeanDefinitionException e )
            {
                // The notification bean has not been found, the application must use the ESB
                AppLogService.info( "No notification bean found, the application must use the ESB" );
            }
            finally
            {
                bIsInitialized = true;
            }
        }

        return _singleton;
    }

    /**
     * send Email
     * @param notification
     */
    public void sendEmail( NotificationDTO notification, Customer customer )
    {
        if ( notification != null )
        {
            _sendEmailService.sendEmail( customer, notification );
        }
    }

    /**
     * send Sms
     * @param notification
     */
    public void sendSms( NotificationDTO notification, Customer customer )
    {
        if ( notification != null )
        {
            _sendSmsService.sendSms( customer, notification );
        }
    }

    /**
     * Notify CRM
     * @param notification
     * @throws CRMException
     */
    public void notifyCrm( NotificationDTO notification )
        throws CRMException
    {
        if ( notification != null )
        {
            if ( !_notifyCrmService.isExistDemand( notification ) )
            {
                _notifyCrmService.createDemand( notification );
            }
            else
            {
                _notifyCrmService.updateDemand( notification );
            }

            _notifyCrmService.notify( notification );
        }
    }
}
