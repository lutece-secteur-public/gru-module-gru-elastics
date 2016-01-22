package fr.paris.lutece.plugins.grusupply.model;

public class OpenAMUser 
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

	    public OpenAMUser(  )
	    {
	    }

	    public OpenAMUser( String strGuid, String strFirstName, String strLastName, String strCivility, String strBirthDay,
	        String strTelephonNumber, String strCity, String strPostalCode, String strCityOfBirth, String strStayConnected,
	        String strStreet )
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
