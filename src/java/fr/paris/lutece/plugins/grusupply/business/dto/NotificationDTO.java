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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;


/**
 * This is the business class for the object ESBNotificationDTO
 */
@JsonRootName( value = "notification" )
@JsonIgnoreProperties( ignoreUnknown = true )
public class NotificationDTO
{
    // Variables declarations 
    private String _strUserGuid;
    private String _strCustomerid;
    private String _strEmail;
    private int _nCrmStatusId;
    private String _strNotificationType;
    private int _nDemandeId;
    private int _nDemandIdType;
    private int _nMaxStep;
    private int _nUserCurrentStep;
    private int _nDemandState;
    private int _strDemandDate;
    private String _strReference;
    private EmailNotification _userEmail;
    private DashboardNotification _userDashBoard;
    private SMSNotification _userSms;
    private BackofficeNotification _userBackOffice;

    public NotificationDTO(  )
    {
    }

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
    @JsonProperty( "user_guid" )
    public void setUserGuid( String strUserGuid )
    {
        _strUserGuid = strUserGuid;
    }

    /**
     * Return the customer id
     * @return
     */
    public String getCustomerid(  )
    {
        return _strCustomerid;
    }

    /**
     * Sets the customer id
     * @param _strCustomerid
     */
    @JsonProperty( "customer_id" )
    public void setCustomerid( String _strCustomerid )
    {
        this._strCustomerid = _strCustomerid;
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
    @JsonProperty( "email" )
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
    @JsonProperty( "crm_status_id" )
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
    @JsonProperty( "notification_type" )
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
    @JsonProperty( "demand_id" )
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
    @JsonProperty( "demand_id_type" )
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
    @JsonProperty( "demand_max_step" )
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
    @JsonProperty( "demand_user_current_step" )
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
    @JsonProperty( "demand_state" )
    public void setDemandState( int nDemandState )
    {
        _nDemandState = nDemandState;
    }

    /**
     * Return the date of the demand
     * @return
     */
    public int getDemandDate(  )
    {
        return _strDemandDate;
    }

    /**
     * Sets the Date of the Demand
     * @param nDemandState The DemandState
     */
    @JsonProperty( "demand_date" )
    public void setDemandDate( int strDemandDate )
    {
        this._strDemandDate = strDemandDate;
    }

    /**
     * Returns the Reference of a Demand
     * @return
     */
    public String getReference(  )
    {
        return _strReference;
    }

    /**
     * Gets the Reference of a demand
     * @param _strReference
     */
    @JsonProperty( "reference" )
    public void setReference( String _strReference )
    {
        this._strReference = _strReference;
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
    @JsonProperty( "user_email" )
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
     * @param userDashBoard The UserDashBoard
     */
    @JsonProperty( "user_dashboard" )
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
     * @param userSms The UserSms
     */
    @JsonProperty( "user_sms" )
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
    @JsonProperty( "backoffice_logging" )
    public void setUserBackOffice( BackofficeNotification UserBackOffice )
    {
        _userBackOffice = UserBackOffice;
    }
}
