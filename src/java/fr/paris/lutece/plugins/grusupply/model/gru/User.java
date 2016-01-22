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

public class User 
{
	private long _lUserGuid;
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
	
	//Getters & Setters
	public long getUserGuid() 
	{
		return _lUserGuid;
	}
	public void setUserGuid(long _lUserGuid) 
	{
		this._lUserGuid = _lUserGuid;
	}
	public String getName()
{
		return _strName;
	}
	public void setName(String _strName)
	{
		this._strName = _strName;
	}
	public String getFirstName()
	{
		return _strFirstName;
	}
	public void setFirstName(String _strFirstName) 
	{
		this._strFirstName = _strFirstName;
	}
	public String getEmail() 
	{
		return _strEmail;
	}
	public void setEmail(String _strEmail) 
	{
		this._strEmail = _strEmail;
	}
	public String getBirthday() 
	{
		return _strBirthday;
	}
	public void setBirthday(String _strBirthday)
	{
		this._strBirthday = _strBirthday;
	}
	public String getCivility() 
	{
		return _strCivility;
	}
	public void setCivility(String _strCivility) 
	{
		this._strCivility = _strCivility;
	}
	public String getStreet() 
	{
		return _strStreet;
	}
	public void setStreet(String _strStreet)
	{
		this._strStreet = _strStreet;
	}
	public String getCityOfBirth()
	{
		return _strCityOfBirth;
	}
	public void setCityOfBirth(String _strCityOfBirth)
	{
		this._strCityOfBirth = _strCityOfBirth;
	}
	public boolean isStayConnected() 
	{
		return _bStayConnected;
	}
	public void setStayConnected(boolean _strStayConnected)
	{
		this._bStayConnected = _strStayConnected;
	}
	public String getCity() 
	{
		return _strCity;
	}
	public void setCity(String _strCity)
	{
		this._strCity = _strCity;
	}
	public String getPostalCode()
	{
		return _strPostalCode;
	}
	public void setPostalCode(String _strPostalCode)
	{
		this._strPostalCode = _strPostalCode;
	}
	public String getTelephoneNumber() 
	{
		return _strTelephoneNumber;
	}
	public void setTelephoneNumber(String _strTelephoneNumber)
	{
		this._strTelephoneNumber = _strTelephoneNumber;
	}
	public String toJSON()
	{
		return "\"user_guid\": \""+_lUserGuid+"\",\"email\": \""+_strEmail+"\",\"name\": \""+_strName+" "+_strFirstName+"\",\"stayConnected\" : \""+((_bStayConnected)?"true":"false")+"\","
				+ "\"street\" : \""+_strStreet+"\",\"telephoneNumber\" : \""+_strTelephoneNumber+"\",\"city\" : \""+_strCity+"\",\"cityOfBirth\" : \""+_strCityOfBirth+"\",\"birthday\" : \""+_strBirthday+"\",\"civility\" : \""+_strCivility+"\","
				+ "\"postalCode\" : \""+_strPostalCode+"\",\"suggest\" : { \"input\": [ \""+_strName+"\", \""+_strFirstName+"\", \""+_strTelephoneNumber+"\", \""+_strEmail+"\" ],\"output\": \""+_strName+" "+_strFirstName+"\",\"payload\" : { \"user_guid\" : \""+_lUserGuid+"\","
				+ "\"birthday\" : \""+_strBirthday+"\",\"telephoneNumber\" : \""+_strTelephoneNumber+"\",\"email\": \""+_strEmail+"\"}}";
	}
}
