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

public class OpenAMUserDTO
{
    private String _strUid;
    private String _strCivility;
    private String _strFirstname;
    private String _strLastname;
    private String _strTelephoneNumber;
    private String _strBirthday;
    private String _strStreet;
    private String _strPostalCode;
    private String _strCity;
    private String _strCityOfBirth;
    private String _strStayConnected;

    public OpenAMUserDTO(  )
    {
    }

    public OpenAMUserDTO( String strGuid, String strFirstName, String strLastName, String strCivility,
        String strBirthDay, String strTelephonNumber, String strCity, String strPostalCode, String strCityOfBirth,
        String strStayConnected, String strStreet )
    {
        this.setUid( strGuid );
        this.setFirstname( strFirstName );
        this.setLastname( strLastName );
        this.setCivility( strCivility );
        this.setBirthday( strBirthDay );
        this.setTelephoneNumber( strTelephonNumber );
        this.setCity( strCity );
        this.setPostalCode( strPostalCode );
        this.setCityOfBirth( strCityOfBirth );
        this.setStreet( strStreet );
        this.setStayConnected( strStayConnected );
    }

    public String getCivility(  )
    {
        return _strCivility;
    }

    public void setCivility( String _strCivility )
    {
        this._strCivility = _strCivility;
    }

    public String getUid(  )
    {
        return _strUid;
    }

    public void setUid( String _strGuid )
    {
        this._strUid = _strGuid;
    }

    public String getFirstname(  )
    {
        return _strFirstname;
    }

    public void setFirstname( String _strFirstName )
    {
        this._strFirstname = _strFirstName;
    }

    public String getLastname(  )
    {
        return _strLastname;
    }

    public void setLastname( String _strLastName )
    {
        this._strLastname = _strLastName;
    }

    public String getTelephoneNumber(  )
    {
        return _strTelephoneNumber;
    }

    public void setTelephoneNumber( String _strTelephonNumber )
    {
        this._strTelephoneNumber = _strTelephonNumber;
    }

    public String getBirthday(  )
    {
        return _strBirthday;
    }

    public void setBirthday( String _strBirthday )
    {
        this._strBirthday = _strBirthday;
    }

    public String getStreet(  )
    {
        return _strStreet;
    }

    public void setStreet( String _strStreet )
    {
        this._strStreet = _strStreet;
    }

    public String getPostalCode(  )
    {
        return _strPostalCode;
    }

    public void setPostalCode( String _strPostalCode )
    {
        this._strPostalCode = _strPostalCode;
    }

    public String getCity(  )
    {
        return _strCity;
    }

    public void setCity( String _strCity )
    {
        this._strCity = _strCity;
    }

    public String getCityOfBirth(  )
    {
        return _strCityOfBirth;
    }

    public void setCityOfBirth( String _strCityOfBirth )
    {
        this._strCityOfBirth = _strCityOfBirth;
    }

    public String getStayConnected(  )
    {
        return _strStayConnected;
    }

    public void setStayConnected( String strStayConnected )
    {
        this._strStayConnected = strStayConnected;
    }
}
