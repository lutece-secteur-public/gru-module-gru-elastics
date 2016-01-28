package fr.paris.lutece.plugins.grusupply.service;

import fr.paris.lutece.plugins.grusupply.business.dto.UserDTO;
import fr.paris.lutece.portal.service.spring.SpringContextService;

public class UserInfoService {

	private static final String BEAN_USER_INFO_SERVICE = "grusupply.userinfoService";
    private static IUserInfoProvider _userInfoProvider;
    private static UserInfoService _singleton;
    
    private UserInfoService( )
    {
    	
    }
    
    public static UserInfoService instance( )
    {
        if ( _singleton == null )
        {
            _singleton = new UserInfoService(  );
            _userInfoProvider = SpringContextService.getBean( BEAN_USER_INFO_SERVICE );
        }

        return _singleton;  	
    }
    /**
     * Store guid
     * @param guid
     * @return
     */
    public UserDTO getUserInfo( String guid )
    {
        return (UserDTO) _userInfoProvider.getUserInfo( guid );
    }    

}
