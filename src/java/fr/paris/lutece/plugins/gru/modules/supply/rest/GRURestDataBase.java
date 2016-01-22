package fr.paris.lutece.plugins.gru.modules.supply.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import fr.paris.lutece.plugins.gru.modules.supply.constants.GruSupplyConstants;
import fr.paris.lutece.plugins.gru.modules.supply.model.ESBNotification;
import fr.paris.lutece.plugins.gru.modules.supply.model.gru.Demand;
import fr.paris.lutece.plugins.gru.modules.supply.model.gru.Notification;
import fr.paris.lutece.plugins.gru.modules.supply.model.gru.User;
import fr.paris.lutece.plugins.gru.modules.supply.model.gru.UserBackoffice;
import fr.paris.lutece.plugins.gru.modules.supply.model.gru.UserDashboard;
import fr.paris.lutece.plugins.gru.modules.supply.model.gru.UserEmail;
import fr.paris.lutece.plugins.gru.modules.supply.model.gru.UserSMS;
import fr.paris.lutece.plugins.gru.modules.supply.service.GRUService;
import fr.paris.lutece.plugins.rest.service.RestConstants;

@Path( RestConstants.BASE_PATH + GruSupplyConstants.PLUGIN_NAME + GruSupplyConstants.MODULE_NAME_DB )
public class GRURestDataBase {
	
	/**
	 * Web Service methode which permit to store notification in a database
	 * @param strJson
	 * @return
	 */
	@POST
    @Path( "notification" )
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.APPLICATION_JSON )
    public String notification( String strJson )
	{
		try 
		{
			// Format to JSON
			JSONObject jsonNotif = new JSONObject(strJson);
			
			// Create JSON flux to Object
			ESBNotification _esbNotif = new ESBNotification(jsonNotif);
			
			// Search in OpenAM
			// TODO OpenAM search user
			
			// Parse to User (TODO HAVE TO ADD WITH OPENAM)
			User _user = new User();
			_user.setUserGuid(Long.parseLong(_esbNotif.getUserGuid()));
			_user.setName("DOE");
			_user.setFirstName("JOHN");
			_user.setEmail(_esbNotif.getEmail());
			_user.setBirthday("01/01/1970");
			_user.setCivility("CIV");
			_user.setStreet("3 rue de troie");
			_user.setCityOfBirth("DEEP WEB");
			_user.setStayConnected(true);
			_user.setCity("DARK NET");
			_user.setPostalCode("74000");
			_user.setTelephoneNumber("0606060606");
			
			GRUService.store(_user);
			
			// Parse to Demand
			Demand _demand = new Demand();
			_demand.setUserGuid(Long.parseLong(_esbNotif.getUserGuid()));
			_demand.setDemandId(_esbNotif.getDemandeId());
			_demand.setDemandIdType(_esbNotif.getDemandIdType());
			_demand.setDemandMaxStep(-1);
			_demand.setDemandUserCurrentStep(-1);
			_demand.setDemandState(_esbNotif.getDemandState());
			_demand.setNotifType(_esbNotif.getNotificationType());
			_demand.setDateDemand("NON RENSEIGNE");
			_demand.setCRMStatus(_esbNotif.getCrmStatusId());
			_demand.setReference("NON RENSEIGNE");
			
			GRUService.store(_demand);
			
			// Parse to Notification
			Notification _notification = new Notification();
			_notification.setDemandeId(_esbNotif.getDemandeId());
			_notification.setDemandIdType(_esbNotif.getDemandIdType());
			_notification.setUserEmail(_esbNotif.getUserEmail());
			_notification.setUserDashBoard(_esbNotif.getUserDashBoard());
			_notification.setUserSms(_esbNotif.getUserSms());
			_notification.setUserBackOffice(_esbNotif.getUserBackOffice());
			
			GRUService.store(_notification);
		}
		catch(JSONException e)
		{
			e.getMessage();
			return GruSupplyConstants.STATUS_404;
		}
		
		
		
		
		
		
		
		
		

		return GruSupplyConstants.STATUS_201;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	



	
}
