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
package fr.paris.lutece.plugins.grusupply.business;


/**
 * This is the business class for the object Notification
 */
public class Notification
{
    // Variables declarations 
    private int _nDemandeId;
    private int _nDemandIdType;
    private EmailNotification _emailNotification;
    private DashboardNotification _dashBoardNotification;
    private SMSNotification _smsNotification;
    private BackofficeNotification _backOfficeNotification;
    private String _strJson;

    /**
     * Returns the DemandeId
     * @return The DemandeId
     */
    public int getDemandId(  )
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
     * Returns the EmailNotification
     * @return The EmailNotification
     */
    public EmailNotification getUserEmail(  )
    {
        return _emailNotification;
    }

    /**
     * Sets the EmailNotification
     * @param UserEmail The EmailNotification
     */
    public void setUserEmail( EmailNotification UserEmail )
    {
        _emailNotification = UserEmail;
    }

    /**
     * Returns the UserDashBoard
     * @return The UserDashBoard
     */
    public DashboardNotification getUserDashBoard(  )
    {
        return _dashBoardNotification;
    }

    /**
     * Sets the UserDashBoard
     * @param UserDashBoard The UserDashBoard
     */
    public void setUserDashBoard( DashboardNotification UserDashBoard )
    {
        _dashBoardNotification = UserDashBoard;
    }

    /**
     * Returns the UserSms
     * @return The UserSms
     */
    public SMSNotification getUserSms(  )
    {
        return _smsNotification;
    }

    /**
     * Sets the UserSms
     * @param UserSms The UserSms
     */
    public void setUserSms( SMSNotification UserSms )
    {
        _smsNotification = UserSms;
    }

    /**
     * Returns the UserBackOffice
     * @return The UserBackOffice
     */
    public BackofficeNotification getUserBackOffice(  )
    {
        return _backOfficeNotification;
    }

    /**
     * Sets the UserBackOffice
     * @param UserBackOffice The UserBackOffice
     */
    public void setUserBackOffice( BackofficeNotification UserBackOffice )
    {
        _backOfficeNotification = UserBackOffice;
    }
    
    /**
     * Returns the Json
     * @return The Json
     */
    public String getJson(  )
    {
        return _strJson;
    }

    /**
     * Sets the Json
     * @param strJson The Json
     */
    public void setJson( String strJson )
    {
        _strJson = strJson;
    }

    
}
