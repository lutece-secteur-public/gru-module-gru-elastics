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


public class UserBackoffice
{
    private String _strMessage;
    private String _strStatusText;
    private int _nNotifiedDashboard;
    private int _nDisplayDashboard;
    private String _strViewDashboard;
    private int _nNotifiedEmail;
    private int _nDisplayEmail;
    private String _strViewEmail;
    private int _nNotifiedSms;
    private int _nDisplaySms;
    private String _strViewSms;

    // Contructors
    public UserBackoffice(  )
    {
        super(  );
    }

    public UserBackoffice( String _strMessage, String _strStatusText, int _nNotifiedDashboard, int _nDisplayDashboard,
        String _strViewDashboard, int _nNotifiedEmail, int _nDisplayEmail, String _strViewEmail, int _nNotifiedSms,
        int _nDisplaySms, String _strViewSms )
    {
        this(  );
        this._strMessage = _strMessage;
        this._strStatusText = _strStatusText;
        this._nNotifiedDashboard = _nNotifiedDashboard;
        this._nDisplayDashboard = _nDisplayDashboard;
        this._strViewDashboard = _strViewDashboard;
        this._nNotifiedEmail = _nNotifiedEmail;
        this._nDisplayEmail = _nDisplayEmail;
        this._strViewEmail = _strViewEmail;
        this._nNotifiedSms = _nNotifiedSms;
        this._nDisplaySms = _nDisplaySms;
        this._strViewSms = _strViewSms;
    }

    public UserBackoffice( JSONObject _json ) throws JSONException
    {
        this(  );
        this._strMessage = _json.getString( "message" );
        this._strStatusText = _json.getString( "status_text" );
        this._nNotifiedDashboard = _json.getInt( "notified_on_dashboard" );
        this._nDisplayDashboard = _json.getInt( "display_level_dashboard_notification" );
        this._strViewDashboard = _json.getString( "view_dashboard_notification" );
        this._nNotifiedEmail = _json.getInt( "notified_by_email" );
        this._nDisplayEmail = _json.getInt( "display_level_email_notification" );
        this._strViewEmail = _json.getString( "view_email_notification" );
        this._nNotifiedSms = _json.getInt( "notified_by_sms" );
        this._nDisplaySms = _json.getInt( "display_level_sms_notification" );
        this._strViewSms = _json.getString( "view_sms_notification" );
    }

    // Getters & Setters
    public String getMessage(  )
    {
        return _strMessage;
    }

    public void setMessage( String _strMessage )
    {
        this._strMessage = _strMessage;
    }

    public String getStatusText(  )
    {
        return _strStatusText;
    }

    public void setStatusText( String _strStatusText )
    {
        this._strStatusText = _strStatusText;
    }

    public int getNotifiedDashboard(  )
    {
        return _nNotifiedDashboard;
    }

    public void setNotifiedDashboard( int _nNotifiedDashboard )
    {
        this._nNotifiedDashboard = _nNotifiedDashboard;
    }

    public int getDisplayDashboard(  )
    {
        return _nDisplayDashboard;
    }

    public void setDisplayDashboard( int _nDisplayDashboard )
    {
        this._nDisplayDashboard = _nDisplayDashboard;
    }

    public String getViewDashboard(  )
    {
        return _strViewDashboard;
    }

    public void setViewDashboard( String _strViewDashboard )
    {
        this._strViewDashboard = _strViewDashboard;
    }

    public int getNotifiedEmail(  )
    {
        return _nNotifiedEmail;
    }

    public void setNotifiedEmail( int _nNotifiedEmail )
    {
        this._nNotifiedEmail = _nNotifiedEmail;
    }

    public int getDisplayEmail(  )
    {
        return _nDisplayEmail;
    }

    public void setDisplayEmail( int _nDisplayEmail )
    {
        this._nDisplayEmail = _nDisplayEmail;
    }

    public String getViewEmail(  )
    {
        return _strViewEmail;
    }

    public void setViewEmail( String _strViewEmail )
    {
        this._strViewEmail = _strViewEmail;
    }

    public int getNotifiedSms(  )
    {
        return _nNotifiedSms;
    }

    public void setNotifiedSms( int _nNotifiedSms )
    {
        this._nNotifiedSms = _nNotifiedSms;
    }

    public int getDisplaySms(  )
    {
        return _nDisplaySms;
    }

    public void setDisplaySms( int _nDisplaySms )
    {
        this._nDisplaySms = _nDisplaySms;
    }

    public String getViewSms(  )
    {
        return _strViewSms;
    }

    public void setViewSms( String _strViewSms )
    {
        this._strViewSms = _strViewSms;
    }

    public String toJSON(  )
    {
        return "\"backoffice_logging\":{\"message\": \"" + _strMessage + "\",\"status_text\": \"" + _strStatusText +
        "\"," + "\"notified_on_dashboard\": " + _nNotifiedDashboard + ",\"display_level_dashboard_notification\": " +
        _nDisplayDashboard + ",\"view_dashboard_notification\": \"" + _strViewDashboard + "\"," +
        "\"notified_by_email\": " + _nNotifiedEmail + ",\"display_level_email_notification\": " + _nDisplayEmail +
        ",\"view_email_notification\": \"" + _strViewEmail + "\"," + "\"notified_by_sms\": " + _nNotifiedSms +
        ",\"display_level_sms_notification\": " + _nDisplaySms + ",\"view_sms_notification\": \"" + _strViewSms +
        "\"}";
    }
}
