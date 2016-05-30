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

import fr.paris.lutece.plugins.grusupply.business.Customer;
import fr.paris.lutece.plugins.grusupply.business.EmailNotification;
import fr.paris.lutece.plugins.grusupply.business.SMSNotification;
import fr.paris.lutece.plugins.grusupply.business.dto.NotificationDTO;
import fr.paris.lutece.portal.service.mail.MailService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import org.apache.commons.lang.StringUtils;


public class SendSmsService
{
    private static final String PROPERTY_SMS_SERVER = "grusupply.sms.server";

    /** private constructor */
    SendSmsService(  )
    {
    }

    /**
     * Send SMS
     * @param smsNotification
     */
    public void sendSms( Customer customer, NotificationDTO notification )
    {
        SMSNotification smsNotification = notification.getUserSms(  );
        String strMessage = smsNotification.getMessage(  );
        String strPhoneNumber = getEmailForSms( smsNotification );
        EmailNotification emailNotification = notification.getUserEmail(  );

        if ( StringUtils.isNotBlank( strPhoneNumber ) )
        {
            if ( emailNotification != null )
            {
                MailService.sendMailText( strPhoneNumber, emailNotification.getSenderName(  ),
                    emailNotification.getSenderEmail(  ), emailNotification.getSubject(  ), strMessage );
            }
            else if ( customer != null )
            {
                MailService.sendMailText( strPhoneNumber, customer.getName(  ), customer.getEmail(  ), strMessage,
                    strMessage );
            }
            else
            {
                AppLogService.info( "SMS STUB : phone number=" + notification.getUserSms(  ).getPhoneNumber(  ) +
                    " message=" + notification.getUserSms(  ).getMessage(  ) );
            }
        }
    }

    /**
     * Return email for SMS
     * @param smsNotification
     * @return
     */
    private String getEmailForSms( SMSNotification smsNotification )
    {
        String strPhoneNumber = null;

        if ( ( smsNotification != null ) && ( smsNotification.getPhoneNumber(  ) != null ) &&
                StringUtils.isNotBlank( smsNotification.getPhoneNumber(  ) ) )
        {
            strPhoneNumber = smsNotification.getPhoneNumber(  ) +
                AppPropertiesService.getProperty( PROPERTY_SMS_SERVER );
        }

        return strPhoneNumber;
    }
}
