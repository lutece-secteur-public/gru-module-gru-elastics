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
 * This is the business class for the object Customer
 */
public class Customer
{
    // Variables declarations 
    private String _strCustomerId;
    private String _strName;
    private String _strFirstName;
    private String _strEmail;
    private String _strBirthday;
    private String _strCivility;
    private String _strStreet;
    private String _strCityOfBirth;
    private boolean _bStayConnected;
    private String _strCity;
    private String _strPostalCode;
    private String _strTelephoneNumber;
    private String _strFixedTelephoneNumber;

    /**
     * Gets the fixe telephone number.
     *
     * @return the fixe telephone number
     */
    public String getFixedTelephoneNumber(  )
    {
        return _strFixedTelephoneNumber;
    }

    /**
     * Sets the fixe telephone number.
     *
     * @param strFixedTelephoneNumber the new fixe telephone number
     */
    public void setFixedTelephoneNumber( String strFixedTelephoneNumber )
    {
        _strFixedTelephoneNumber = strFixedTelephoneNumber;
    }

    /**
     * Returns the CustomerId
     * @return The CustomerId
     */
    public String getCustomerId(  )
    {
        return _strCustomerId;
    }

    /**
     * Sets the CustomerId
     * @param CustomerId The CustomerId
     */
    public void setCustomerId( String strCustomerId )
    {
        _strCustomerId = strCustomerId;
    }

    /**
     * Returns the Name
     * @return The Name
     */
    public String getName(  )
    {
        return _strName;
    }

    /**
     * Sets the Name
     * @param strName The Name
     */
    public void setName( String strName )
    {
        _strName = strName;
    }

    /**
     * Returns the FirstName
     * @return The FirstName
     */
    public String getFirstName(  )
    {
        return _strFirstName;
    }

    /**
     * Sets the FirstName
     * @param strFirstName The FirstName
     */
    public void setFirstName( String strFirstName )
    {
        _strFirstName = strFirstName;
    }

    /**
     * Returns the Email
     * @return The Email
     */
    public String getEmail(  )
    {
        return _strEmail;
    }

    /**
     * Sets the Email
     * @param strEmail The Email
     */
    public void setEmail( String strEmail )
    {
        _strEmail = strEmail;
    }

    /**
     * Returns the Birthday
     * @return The Birthday
     */
    public String getBirthday(  )
    {
        return _strBirthday;
    }

    /**
     * Sets the Birthday
     * @param strBirthday The Birthday
     */
    public void setBirthday( String strBirthday )
    {
        _strBirthday = strBirthday;
    }

    /**
     * Returns the Civility
     * @return The Civility
     */
    public String getCivility(  )
    {
        return _strCivility;
    }

    /**
     * Sets the Civility
     * @param strCivility The Civility
     */
    public void setCivility( String strCivility )
    {
        _strCivility = strCivility;
    }

    /**
     * Returns the Street
     * @return The Street
     */
    public String getStreet(  )
    {
        return _strStreet;
    }

    /**
     * Sets the Street
     * @param strStreet The Street
     */
    public void setStreet( String strStreet )
    {
        _strStreet = strStreet;
    }

    /**
     * Returns the CityOfBirth
     * @return The CityOfBirth
     */
    public String getCityOfBirth(  )
    {
        return _strCityOfBirth;
    }

    /**
     * Sets the CityOfBirth
     * @param strCityOfBirth The CityOfBirth
     */
    public void setCityOfBirth( String strCityOfBirth )
    {
        _strCityOfBirth = strCityOfBirth;
    }

    /**
     * Returns the StayConnected
     * @return The StayConnected
     */
    public boolean getStayConnected(  )
    {
        return _bStayConnected;
    }

    /**
     * Sets the StayConnected
     * @param StayConnected The StayConnected
     */
    public void setStayConnected( boolean stayConnected )
    {
        _bStayConnected = stayConnected;
    }

    /**
     * Returns the City
     * @return The City
     */
    public String getCity(  )
    {
        return _strCity;
    }

    /**
     * Sets the City
     * @param strCity The City
     */
    public void setCity( String strCity )
    {
        _strCity = strCity;
    }

    /**
     * Returns the PostalCode
     * @return The PostalCode
     */
    public String getPostalCode(  )
    {
        return _strPostalCode;
    }

    /**
     * Sets the PostalCode
     * @param strPostalCode The PostalCode
     */
    public void setPostalCode( String strPostalCode )
    {
        _strPostalCode = strPostalCode;
    }

    /**
     * Returns the TelephoneNumber
     * @return The TelephoneNumber
     */
    public String getTelephoneNumber(  )
    {
        return _strTelephoneNumber;
    }

    /**
     * Sets the TelephoneNumber
     * @param strTelephoneNumber The TelephoneNumber
     */
    public void setTelephoneNumber( String strTelephoneNumber )
    {
        _strTelephoneNumber = strTelephoneNumber;
    }
}
