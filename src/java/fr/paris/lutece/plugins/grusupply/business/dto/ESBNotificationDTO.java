/*
 * Copyright (c) 2002-2013, Mairie de Paris
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
package fr.paris.lutece.plugins.grusupply.business.dto;

import fr.paris.lutece.plugins.grusupply.business.BackofficeNotification;
import fr.paris.lutece.plugins.grusupply.business.DashboardNotification;
import fr.paris.lutece.plugins.grusupply.business.EmailNotification;
import fr.paris.lutece.plugins.grusupply.business.SMSNotification;


/**
 * This is the business class for the object ESBNotificationDTO
 */
public class ESBNotificationDTO
{
    // Variables declarations 
    private String _strUserGuid;
    private String _strEmail;
    private int _nCrmStatusId;
    private String _strNotificationType;
    private int _nDemandeId;
    private int _nDemandIdType;
    private int _nMaxStep;
    private int _nUserCurrentStep;
    private int _nDemandState;
    private EmailNotification _userEmail;
    private DashboardNotification _userDashBoard;
    private SMSNotification _userSms;
    private BackofficeNotification _userBackOffice;

    /**
     * Returns the UserGuid
     * @return The UserGuid
     */
    public String getUserGuid(  )
    {
        return _strUserGuid;
    }

    /**
     * Sets the UserGuid
     * @param strUserGuid The UserGuid
     */
    public void setUserGuid( String strUserGuid )
    {
        _strUserGuid = strUserGuid;
    }

    /**
     * Returns the Email
     * @return The Email
     */
    public String getEmail(  )
    {
        return _strEmail;
    }

    /**
     * Sets the Email
     * @param strEmail The Email
     */
    public void setEmail( String strEmail )
    {
        _strEmail = strEmail;
    }

    /**
     * Returns the CrmStatusId
     * @return The CrmStatusId
     */
    public int getCrmStatusId(  )
    {
        return _nCrmStatusId;
    }

    /**
     * Sets the CrmStatusId
     * @param nCrmStatusId The CrmStatusId
     */
    public void setCrmStatusId( int nCrmStatusId )
    {
        _nCrmStatusId = nCrmStatusId;
    }

    /**
     * Returns the NotificationType
     * @return The NotificationType
     */
    public String getNotificationType(  )
    {
        return _strNotificationType;
    }

    /**
     * Sets the NotificationType
     * @param strNotificationType The NotificationType
     */
    public void setNotificationType( String strNotificationType )
    {
        _strNotificationType = strNotificationType;
    }

    /**
     * Returns the DemandeId
     * @return The DemandeId
     */
    public int getDemandeId(  )
    {
        return _nDemandeId;
    }

    /**
     * Sets the DemandeId
     * @param nDemandeId The DemandeId
     */
    public void setDemandeId( int nDemandeId )
    {
        _nDemandeId = nDemandeId;
    }

    /**
     * Returns the DemandIdType
     * @return The DemandIdType
     */
    public int getDemandIdType(  )
    {
        return _nDemandIdType;
    }

    /**
     * Sets the DemandIdType
     * @param nDemandIdType The DemandIdType
     */
    public void setDemandIdType( int nDemandIdType )
    {
        _nDemandIdType = nDemandIdType;
    }

    /**
     * Returns the MaxStep
     * @return The MaxStep
     */
    public int getMaxStep(  )
    {
        return _nMaxStep;
    }

    /**
     * Sets the MaxStep
     * @param nMaxStep The MaxStep
     */
    public void setMaxStep( int nMaxStep )
    {
        _nMaxStep = nMaxStep;
    }

    /**
     * Returns the UserCurrentStep
     * @return The UserCurrentStep
     */
    public int getUserCurrentStep(  )
    {
        return _nUserCurrentStep;
    }

    /**
     * Sets the UserCurrentStep
     * @param nUserCurrentStep The UserCurrentStep
     */
    public void setUserCurrentStep( int nUserCurrentStep )
    {
        _nUserCurrentStep = nUserCurrentStep;
    }

    /**
     * Returns the DemandState
     * @return The DemandState
     */
    public int getDemandState(  )
    {
        return _nDemandState;
    }

    /**
     * Sets the DemandState
     * @param nDemandState The DemandState
     */
    public void setDemandState( int nDemandState )
    {
        _nDemandState = nDemandState;
    }

    /**
     * Returns the EmailNotification
     * @return The EmailNotification
     */
    public EmailNotification getUserEmail(  )
    {
        return _userEmail;
    }

    /**
     * Sets the userEmail
     * @param userEmail The EmailNotification
     */
    public void setUserEmail( EmailNotification userEmail )
    {
        _userEmail = userEmail;
    }

    /**
     * Returns the UserDashBoard
     * @return The UserDashBoard
     */
    public DashboardNotification getUserDashBoard(  )
    {
        return _userDashBoard;
    }

    /**
     * Sets the UserDashBoard
     * @param UserDashBoard The UserDashBoard
     */
    public void setUserDashBoard( DashboardNotification userDashBoard )
    {
        _userDashBoard = userDashBoard;
    }

    /**
     * Returns the UserSms
     * @return The UserSms
     */
    public SMSNotification getUserSms(  )
    {
        return _userSms;
    }

    /**
     * Sets the UserSms
     * @param UserSms The UserSms
     */
    public void setUserSms( SMSNotification userSms )
    {
        _userSms = userSms;
    }

    /**
     * Returns the UserBackOffice
     * @return The UserBackOffice
     */
    public BackofficeNotification getUserBackOffice(  )
    {
        return _userBackOffice;
    }

    /**
     * Sets the UserBackOffice
     * @param UserBackOffice The UserBackOffice
     */
    public void setUserBackOffice( BackofficeNotification UserBackOffice )
    {
        _userBackOffice = UserBackOffice;
    }
}
