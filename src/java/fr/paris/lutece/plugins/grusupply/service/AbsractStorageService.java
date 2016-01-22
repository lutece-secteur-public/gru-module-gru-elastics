package fr.paris.lutece.plugins.grusupply.service;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.grusupply.model.gru.Demand;
import fr.paris.lutece.plugins.grusupply.model.gru.Notification;
import fr.paris.lutece.plugins.grusupply.model.gru.User;
import fr.paris.lutece.portal.service.i18n.I18nService;

public abstract class AbsractStorageService implements INotificationStorageService {

	    private String _strKey;
	    private String _strtitleI18nKey;
	    private String _strbeanName;
	/**
	 * 
	 * @return _strKey
	 */
	    public String getKey(  )
	    {
	        return _strKey;
	    }
	/**
	 * 
	 * @return _strbeanName
	 */
	    public String getBeanName(  )
	    {
	        return _strbeanName;
	    }
	/**
	 * 
	 * @param strKey to set _strKey
	 */
	    public void setKey( String strKey )
	    {
	        _strKey = strKey;
	    }
	/**
	 * 
	 * @param strbeanName to set _strbeanName
	 */
	    public void setBeanName( String strbeanName )
	    {
	        _strbeanName = strbeanName;
	    }
	/**
	 * 
	 * @param locale to localize the title
	 * @return the title
	 */
	    public String getTitle( Locale locale )
	    {
	        return I18nService.getLocalizedString( _strtitleI18nKey, locale );
	    }
	/**
	 * 
	 * @return _strtitleI18nKey
	 */
	    public String gettitleI18nKey(  )
	    {
	        return _strtitleI18nKey;
	    }
	/**
	 * 
	 * @param strtitleI18nKey to set _strtitleI18nKey
	 */
	    public void settitleI18nKey( String strtitleI18nKey )
	    {
	        _strtitleI18nKey = strtitleI18nKey;
	    }
	/**
	 * 
	 * @param strType 
	 * @return true if the provider is invoked
	 */
	    public boolean isInvoked( String strType )
	    {
	        if ( StringUtils.isNotBlank( strType ) )
	        {
	            return getKey(  ).equals( strType );
	        }

	        return false;
	    }

}
