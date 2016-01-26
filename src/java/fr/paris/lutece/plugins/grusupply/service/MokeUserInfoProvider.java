package fr.paris.lutece.plugins.grusupply.service;

import fr.paris.lutece.plugins.grusupply.business.dto.UserDTO;

public class MokeUserInfoProvider implements IUserInfoProvider {

	@Override
	public UserDTO getUserInfo( String guid ) {
		UserDTO user = new UserDTO();
		user.setFirstname("JOHN");
		user.setLastname("DOE");
		user.setCity("Paris");
		user.setEmail("john.doe@email.com");
		
		return user;
	}

}
