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

import fr.paris.lutece.plugins.crmclient.util.CRMException;
import fr.paris.lutece.plugins.grubusiness.business.notification.BroadcastNotification;
import fr.paris.lutece.plugins.grubusiness.business.notification.EmailAddress;
import fr.paris.lutece.plugins.grubusiness.business.notification.EmailNotification;
import fr.paris.lutece.plugins.grubusiness.business.notification.Notification;
import fr.paris.lutece.plugins.grusupply.business.GruSupplyEmail;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import java.util.List;


public class NotificationService
{
    private static final String BEAN_NOTIFICATION_SERVICE = "grusupply.notificationService";
    private static final String ADRESS_SEPARATOR = ";";
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
    public void sendEmail( Notification notification )
    {
        if ( ( notification != null ) && ( notification.getUserEmail(  ) != null ) )
        {
            GruSupplyEmail gruEmail = new GruSupplyEmail(  );
            EmailNotification notifEmail = notification.getUserEmail(  );
            gruEmail.setRecipient( notifEmail.getRecipient(  ) );
            gruEmail.setCc( notifEmail.getCc(  ) );
            gruEmail.setBcc( notifEmail.getCci(  ) );
            gruEmail.setSenderEmail( notifEmail.getSenderEmail(  ) );
            gruEmail.setSenderName( notifEmail.getSenderName(  ) );
            gruEmail.setSubject( notifEmail.getSubject(  ) );
            gruEmail.setMessage( notifEmail.getMessage(  ) );

            _sendEmailService.sendEmail( gruEmail );
        }
    }

    /**
     * send Broadcast email
     * @param notification
     */
    public void sendBroadcastEmail( Notification notification )
    {
        if ( ( notification != null ) && ( notification.getBroadcastEmail(  ) != null ) )
        {
            GruSupplyEmail gruEmail = null;

            for ( BroadcastNotification notifBroadcast : notification.getBroadcastEmail(  ) )
            {
                gruEmail = new GruSupplyEmail(  );
                gruEmail.setRecipient( buildEmailAdresses( notifBroadcast.getRecipient(  ) ) );
                gruEmail.setCc( buildEmailAdresses( notifBroadcast.getCc(  ) ) );
                gruEmail.setBcc( buildEmailAdresses( notifBroadcast.getBcc(  ) ) );
                gruEmail.setSenderEmail( notifBroadcast.getSenderEmail(  ) );
                gruEmail.setSenderName( notifBroadcast.getSenderName(  ) );
                gruEmail.setSubject( notifBroadcast.getSubject(  ) );
                gruEmail.setMessage( notifBroadcast.getMessage(  ) );
                _sendEmailService.sendEmail( gruEmail );
            }
        }
    }

    private String buildEmailAdresses( List<EmailAddress> lstEmailAdress )
    {
        StringBuilder strEmailAdresses = new StringBuilder(  );

        if ( ( lstEmailAdress != null ) && !lstEmailAdress.isEmpty(  ) )
        {
            for ( EmailAddress emailAddress : lstEmailAdress )
            {
                if ( strEmailAdresses.length(  ) > 0 )
                {
                    strEmailAdresses.append( ADRESS_SEPARATOR );
                }

                strEmailAdresses.append( emailAddress.getAddress(  ) );
            }
        }

        return strEmailAdresses.toString(  );
    }

    /**
     * send Sms
     * @param notification
     */
    public void sendSms( Notification notification )
    {
        if ( notification != null )
        {
            _sendSmsService.sendSms( notification );
        }
    }

    /**
     * Notify CRM
     * @param notification
     * @throws CRMException
     */
    public void notifyCrm( Notification notification )
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
