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
package fr.paris.lutece.plugins.grusupply.model.gru;

public class Notification
{
	private int _nDemandeId;
	private int _nDemandIdType;
	
	private UserEmail _userEmail;
	private UserDashboard _userDashBoard;
	private UserSMS _userSms;
	private UserBackoffice _userBackOffice;
	
	// GETTERS & SETTERS
	public int getDemandeId( )
	{
		return _nDemandeId;
	}
	public void setDemandeId( int _nDemandeId )
	{
		this._nDemandeId = _nDemandeId;
	}
	public int getDemandIdType() {
		return _nDemandIdType;
	}
	public void setDemandIdType( int _nDemandIdType ) 
	{
		this._nDemandIdType = _nDemandIdType;
	}
	public UserEmail getUserEmail( )
	{
		return _userEmail;
	}
	public void setUserEmail( UserEmail _userEmail ) 
	{
		this._userEmail = _userEmail;
	}
	public UserDashboard getUserDashBoard( )
	{
		return _userDashBoard;
	}
	public void setUserDashBoard( UserDashboard _userDashBoard )
	{
		this._userDashBoard = _userDashBoard;
	}
	public UserSMS getUserSms( )
	{
		return _userSms;
	}
	public void setUserSms( UserSMS _userSms ) 
	{
		this._userSms = _userSms;
	}
	public UserBackoffice getUserBackOffice( ) 
	{
		return _userBackOffice;
	}
	public void setUserBackOffice( UserBackoffice _userBackOffice )
	{
		this._userBackOffice = _userBackOffice;
	}
	public String toJSON( )
	{
		String retour = "\"demande\":{\"demand_id\": \""+_nDemandeId+"\",\"demand_id_type\": "+_nDemandIdType+"},\"status_text\": \""+_userBackOffice.getStatusText()+"\","
				+ "\"message\": \""+_userBackOffice.getMessage()+"\",\"notified_on_dashboard\": "+_userBackOffice.getNotifiedDashboard()+",\"notified_by_email\": "+_userBackOffice.getNotifiedEmail()+","
				+ "\"notified_by_sms\": "+_userBackOffice.getNotifiedSms()+",\"display_level_dashboard_notification\": "+_userBackOffice.getDisplayDashboard()+","
				+ "\"view_dashboard_notification\": "+_userBackOffice.getViewDashboard()+",\"display_level_email_notification\": "+_userBackOffice.getDisplayEmail()+","
				+ "\"view_email_notification\": \""+_userBackOffice.getViewEmail()+"\",\"display_level_sms_notification\": "+_userBackOffice.getDisplaySms()+","
				+ "\"view_sms_notification\": \""+_userBackOffice.getViewSms()+"\",\"date_sollicitation\":\""+"TEST"+"\"";
		retour += ","+_userEmail.toJSON();
		retour += ","+_userDashBoard.toJSON();
		retour += ","+_userSms.toJSON();
		retour += "}";
		return retour;
	}
}
