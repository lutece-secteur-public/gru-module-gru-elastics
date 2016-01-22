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

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class UserDashboard
{
	private String _strStatusText;
	private String _strMessage;
	private String _strSubject;
	private String _strSenderName;
	private String _strData;

	public UserDashboard( ) 
	{
		super( );
	}
	public UserDashboard( String _strStatusText, String _strMessage, String _strSubject, String _strSenderName,
			String _strData ) 
	{
		super( );
		this._strStatusText = _strStatusText;
		this._strMessage = _strMessage;
		this._strSubject = _strSubject;
		this._strSenderName = _strSenderName;
		this._strData = _strData;
	}	
	public UserDashboard( JSONObject json ) throws JSONException
	{
		super( );
		this._strStatusText = json.getString("status_text");
		this._strMessage = json.getString("message");
		this._strSubject = json.getString("subject");
		this._strSenderName = json.getString("sender_name");
		this._strData = json.getString("data");
	}
	// Getters & Setters
	public String getStatusText( ) 
	{
		return _strStatusText;
	}
	public void setStatusText( String _strStatusText )
	{
		this._strStatusText = _strStatusText;
	}
	public String getMessage( )
	{
		return _strMessage;
	}
	public void setMessage( String _strMessage )
	{
		this._strMessage = _strMessage;
	}
	public String getSubject( ) 
	{
		return _strSubject;
	}
	public void setSubject( String _strSubject ) 
	{
		this._strSubject = _strSubject;
	}
	public String getSenderName( )
	{
		return _strSenderName;
	}
	public void setSenderName( String _strSenderName )
	{
		this._strSenderName = _strSenderName;
	}
	public String getData( ) 
	{
		return _strData;
	}
	public void setData( String _strData )
	{
		this._strData = _strData;
	}
	public String toJSON( )
	{
		return "\"user_dashboard\": {\"status_text\": \""+_strStatusText+"\",\"data\": \""+_strData+"\"}";
	}
}
