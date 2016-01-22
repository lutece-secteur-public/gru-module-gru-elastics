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


public class UserSMS
{
    private int _nPhoneNumber;
    private String _strMessage;

    // Constructor
    public UserSMS(  )
    {
        super(  );
    }

    public UserSMS( int _nPhoneNumber, String _strMessage )
    {
        super(  );
        this._nPhoneNumber = _nPhoneNumber;
        this._strMessage = _strMessage;
    }

    public UserSMS( JSONObject json ) throws JSONException
    {
        super(  );
        this._nPhoneNumber = json.getInt( "phone_number" );
        this._strMessage = json.getString( "message" );
    }

    // Getters & Setters
    public int getPhoneNumber(  )
    {
        return _nPhoneNumber;
    }

    public void setPhoneNumber( int _nPhoneNumber )
    {
        this._nPhoneNumber = _nPhoneNumber;
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
        return "\"user_sms\": {\"phone_number\": \"" + _nPhoneNumber + "\",\"message\": \"" + _strMessage + "\"}";
    }
}
