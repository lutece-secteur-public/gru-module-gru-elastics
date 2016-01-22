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

public class Demand
{
    private long _lUserGuid;
    private int _nDemandId;
    private int _nDemandIdType;
    private int _nDemandMaxStep;
    private int _nDemandUserCurrentStep;
    private int _nDemandState;
    private String _strNotifType;
    private String _strDateDemand;
    private int _nCRMStatus;
    private String _strReference;

    // Getters & Setters 
    public long getUserGuid(  )
    {
        return _lUserGuid;
    }

    public void setUserGuid( long _lUserGuid )
    {
        this._lUserGuid = _lUserGuid;
    }

    public int getDemandId(  )
    {
        return _nDemandId;
    }

    public void setDemandId( int _nDemandId )
    {
        this._nDemandId = _nDemandId;
    }

    public int getDemandIdType(  )
    {
        return _nDemandIdType;
    }

    public void setDemandIdType( int _nDemandIdType )
    {
        this._nDemandIdType = _nDemandIdType;
    }

    public int getDemandMaxStep(  )
    {
        return _nDemandMaxStep;
    }

    public void setDemandMaxStep( int _nDemandMaxStep )
    {
        this._nDemandMaxStep = _nDemandMaxStep;
    }

    public int getDemandUserCurrentStep(  )
    {
        return _nDemandUserCurrentStep;
    }

    public void setDemandUserCurrentStep( int _nDemandUserCurrentStep )
    {
        this._nDemandUserCurrentStep = _nDemandUserCurrentStep;
    }

    public int getDemandState(  )
    {
        return _nDemandState;
    }

    public void setDemandState( int _nDemandState )
    {
        this._nDemandState = _nDemandState;
    }

    public String getNotifType(  )
    {
        return _strNotifType;
    }

    public void setNotifType( String _strNotifType )
    {
        this._strNotifType = _strNotifType;
    }

    public String getDateDemand(  )
    {
        return _strDateDemand;
    }

    public void setDateDemand( String _strDateDemand )
    {
        this._strDateDemand = _strDateDemand;
    }

    public int getCRMStatus(  )
    {
        return _nCRMStatus;
    }

    public void setCRMStatus( int _nCRMStatus )
    {
        this._nCRMStatus = _nCRMStatus;
    }

    public String getReference(  )
    {
        return _strReference;
    }

    public void setReference( String _strReference )
    {
        this._strReference = _strReference;
    }

    public String toJSON( String nom, String prenom, String birthday, String telephoneNumber, String email )
    {
        return "\"utilisateur\":{\"user_guid\": \"" + _lUserGuid + "\"},\"demand_id\": \"" + _nDemandId +
        "\",\"demand_id_type\": " + _nDemandIdType + ",\"demand_max_step\": " + _nDemandMaxStep +
        ",\"demand_user_current_step\": " + _nDemandUserCurrentStep + ",\"demand_state\": " + _nDemandState + "," +
        "\"notification_type\": \"" + _strNotifType + "\",\"date_demande\":\"" + _strDateDemand +
        "\",\"crm_status_id\": " + _nCRMStatus + ",\"reference\":\"" + _strReference +
        "\",\"suggest\" : {\"input\": [ \"" + _strReference + "\" ],\"output\": " + "\"" + nom + " " + prenom +
        "\",\"payload\" : { \"user_guid\" : \"" + _lUserGuid + "\",\"birthday\" : \"" + birthday +
        "\",\"telephoneNumber\" : \"" + telephoneNumber + "\",\"email\": \"" + email + "\",\"reference\":\"" +
        _strReference + "\"}}";
    }
}
