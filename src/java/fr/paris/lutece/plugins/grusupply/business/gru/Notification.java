   
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
package fr.paris.lutece.plugins.grusupply.business.gru;

/**
 * This is the business class for the object Notification
 */ 
public class Notification
{
    // Variables declarations 
    private int _nDemandeId;
    private int _nDemandIdType;
    private UserEmail _UserEmail;
    private UserDashboard _userDashBoard;
    private UserSMS _userSms;
    private UserBackoffice _userBackOffice;
    
    
       /**
        * Returns the DemandeId
        * @return The DemandeId
        */ 
    public int getDemandeId()
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
    public int getDemandIdType()
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
        * Returns the UserEmail
        * @return The UserEmail
        */ 
    public UserEmail getUserEmail()
    {
        return _UserEmail;
    }
    
       /**
        * Sets the UserEmail
        * @param UserEmail The UserEmail
        */ 
    public void setUserEmail( UserEmail UserEmail )
    {
        _UserEmail = UserEmail;
    }
    
       /**
        * Returns the UserDashBoard
        * @return The UserDashBoard
        */ 
    public UserDashboard getUserDashBoard()
    {
        return _userDashBoard;
    }
    
       /**
        * Sets the UserDashBoard
        * @param UserDashBoard The UserDashBoard
        */ 
    public void setUserDashBoard( UserDashboard UserDashBoard )
    {
        _userDashBoard = UserDashBoard;
    }
    
       /**
        * Returns the UserSms
        * @return The UserSms
        */ 
    public UserSMS getUserSms()
    {
        return _userSms;
    }
    
       /**
        * Sets the UserSms
        * @param UserSms The UserSms
        */ 
    public void setUserSms( UserSMS UserSms )
    {
        _userSms = UserSms;
    }
    
       /**
        * Returns the UserBackOffice
        * @return The UserBackOffice
        */ 
    public UserBackoffice getUserBackOffice()
    {
        return _userBackOffice;
    }
    
       /**
        * Sets the UserBackOffice
        * @param UserBackOffice The UserBackOffice
        */ 
    public void setUserBackOffice( UserBackoffice UserBackOffice )
    {
        _userBackOffice = UserBackOffice;
    }
 }