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


public class UserEmail
{
    private String _strSenderName;
    private String _strSenderEmail;
    private String _strRecipient;
    private String _strSubject;
    private String _strMessage;

    // Constructors
    public UserEmail(  )
    {
        super(  );
    }

    public UserEmail( String _strSenderName, String _strSenderEmail, String _strRecipient, String _strSubject,
        String _strMessage )
    {
        super(  );
        this._strSenderName = _strSenderName;
        this._strSenderEmail = _strSenderEmail;
        this._strRecipient = _strRecipient;
        this._strSubject = _strSubject;
        this._strMessage = _strMessage;
    }

    public UserEmail( JSONObject json ) throws JSONException
    {
        super(  );
        this._strSenderName = json.getString( "sender_name" );
        this._strSenderEmail = json.getString( "sender_email" );
        this._strRecipient = json.getString( "recipient" );
        this._strSubject = json.getString( "subject" );
        this._strMessage = json.getString( "message" );
    }

    // Getters & Setters
    public String getSenderName(  )
    {
        return _strSenderName;
    }

    public void setSenderName( String _strSenderName )
    {
        this._strSenderName = _strSenderName;
    }

    public String getSenderEmail(  )
    {
        return _strSenderEmail;
    }

    public void setSenderEmail( String _strSenderEmail )
    {
        this._strSenderEmail = _strSenderEmail;
    }

    public String getRecipient(  )
    {
        return _strRecipient;
    }

    public void setRecipient( String _strRecipient )
    {
        this._strRecipient = _strRecipient;
    }

    public String getSubject(  )
    {
        return _strSubject;
    }

    public void setSubject( String _strSubject )
    {
        this._strSubject = _strSubject;
    }

    public String getMessage(  )
    {
        return _strMessage;
    }

    public void setMessage( String _strMessage )
    {
        this._strMessage = _strMessage;
    }

    public String toJSON(  )
    {
        return "\"user_email\": {\"sender_name\": \"" + _strSenderName + "\",\"sender_email\": \"" + _strSenderEmail +
        "\"," + "\"recipient\": \"" + _strRecipient + "\",\"subject\": \"" + _strSubject + "\",\"message\": \"" +
        _strMessage + "\"}";
    }
}
