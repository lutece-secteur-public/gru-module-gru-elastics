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
package fr.paris.lutece.plugins.grusupply.model;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import fr.paris.lutece.plugins.grusupply.model.gru.UserBackoffice;
import fr.paris.lutece.plugins.grusupply.model.gru.UserDashboard;
import fr.paris.lutece.plugins.grusupply.model.gru.UserEmail;
import fr.paris.lutece.plugins.grusupply.model.gru.UserSMS;

public class ESBNotification 
{
	private String _strUserGuid; 
	private String _strEmail;
	private int _nCrmStatusId;
	private String _strNotificationType;
	private int _nDemandeId;
	private int _nDemandIdType;
	private int _nMaxStep;
	private int _nUserCurrentStep;
	private int _nDemandState;
	
	private UserEmail _userEmail;
	private UserDashboard _userDashBoard;
	private UserSMS _userSms;
	private UserBackoffice _userBackOffice;
	
	// Constructors
	public ESBNotification( )
	{
		super( );
	}
	public ESBNotification( String _strUserGuid, String _strEmail, int _nCrmStatusId, String _strNotificationType,
			int _nDemandeId, int _nDemandIdType, int _nMaxStep, int _nUserCurrentStep, int _nDemandState,
			UserEmail _userEmail, UserDashboard _userDashBoard, UserSMS _userSms, UserBackoffice _userBackOffice )
	{
		this( );
		this._strUserGuid = _strUserGuid;
		this._strEmail = _strEmail;
		this._nCrmStatusId = _nCrmStatusId;
		this._strNotificationType = _strNotificationType;
		this._nDemandeId = _nDemandeId;
		this._nDemandIdType = _nDemandIdType;
		this._nMaxStep = _nMaxStep;
		this._nUserCurrentStep = _nUserCurrentStep;
		this._nDemandState = _nDemandState;
		this._userEmail = _userEmail;
		this._userDashBoard = _userDashBoard;
		this._userSms = _userSms;
		this._userBackOffice = _userBackOffice;
	}
	public ESBNotification( JSONObject _json ) throws JSONException
	{
		this( );
		this._strUserGuid = _json.getString("user_guid");
		this._strEmail = _json.getString("email");
		this._nCrmStatusId = _json.getInt("email");
		this._strNotificationType = _json.getString("notification_type");
		this._nDemandeId = _json.getInt("demand_id");
		this._nDemandIdType = _json.getInt("demand_id_type");
		this._nMaxStep = _json.getInt("demand_max_step");
		this._nUserCurrentStep = _json.getInt("demand_user_current_step");
		this._nDemandState = _json.getInt("demand_state");
		this._userEmail = new UserEmail(_json.getJSONObject("user_email"));
		this._userDashBoard = new UserDashboard(_json.getJSONObject("user_dashboard"));
		this._userSms = new UserSMS(_json.getJSONObject("user_sms"));
		this._userBackOffice = new UserBackoffice(_json.getJSONObject("backoffice_logging"));
	}
	// GETTERS & SETTERS
	public String getUserGuid( )
	{
		return _strUserGuid;
	}
	public void setUserGuid( String _strUserGuid ) 
	{
		this._strUserGuid = _strUserGuid;
	}
	public String getEmail()
	{
		return _strEmail;
	}
	public void setEmail(String _strEmail)
	{
		this._strEmail = _strEmail;
	}
	public int getCrmStatusId( )
	{
		return _nCrmStatusId;
	}
	public void setCrmStatusId( int _nCrmStatusId )
	{
		this._nCrmStatusId = _nCrmStatusId;
	}
	public String getNotificationType( )
	{
		return _strNotificationType;
	}
	public void setNotificationType( String _strNotificationType )
	{
		this._strNotificationType = _strNotificationType;
	}
	public int getDemandeId( ) 
	{
		return _nDemandeId;
	}
	public void setDemandeId( int _nDemandeId ) 
	{
		this._nDemandeId = _nDemandeId;
	}
	public int getDemandIdType( )
	{
		return _nDemandIdType;
	}
	public void setDemandIdType( int _nDemandIdType )
	{
		this._nDemandIdType = _nDemandIdType;
	}
	public int getMaxStep( )
	{
		return _nMaxStep;
	}
	public void setMaxStep( int _nMaxStep ) 
	{
		this._nMaxStep = _nMaxStep;
	}
	public int getUserCurrentStep( ) 
	{
		return _nUserCurrentStep;
	}
	public void setUserCurrentStep( int _nUserCurrentStep ) 
	{
		this._nUserCurrentStep = _nUserCurrentStep;
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
	public int getDemandState( )
	{
		return _nDemandState;
	}
	public void setDemandState( int _nDemandState) 
	{
		this._nDemandState = _nDemandState;
	}
}
