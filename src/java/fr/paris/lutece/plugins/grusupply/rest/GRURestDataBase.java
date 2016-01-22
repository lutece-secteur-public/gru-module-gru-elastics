package fr.paris.lutece.plugins.grusupply.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import fr.paris.lutece.plugins.grusupply.constant.GruSupplyConstants;
import fr.paris.lutece.plugins.grusupply.model.ESBNotification;
import fr.paris.lutece.plugins.grusupply.model.OpenAMUser;
import fr.paris.lutece.plugins.grusupply.model.gru.Demand;
import fr.paris.lutece.plugins.grusupply.model.gru.Notification;
import fr.paris.lutece.plugins.grusupply.model.gru.User;
import fr.paris.lutece.plugins.grusupply.model.gru.UserBackoffice;
import fr.paris.lutece.plugins.grusupply.model.gru.UserDashboard;
import fr.paris.lutece.plugins.grusupply.model.gru.UserEmail;
import fr.paris.lutece.plugins.grusupply.model.gru.UserSMS;
import fr.paris.lutece.plugins.grusupply.service.GRUService;
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
			OpenAMUser userOpenam=GRUService.getService( ).getUserInfo(_esbNotif.getUserGuid());
						
			// Parse to User (TODO HAVE TO ADD WITH OPENAM)
			User _user = new User();
			if(userOpenam != null){
				
				_user.setName(userOpenam.getLastname( ));
				_user.setFirstName(userOpenam.getFirstname( ));
				_user.setBirthday(userOpenam.getBirthday( ));
				_user.setCivility(userOpenam.getCivility( ));
				_user.setStreet(userOpenam.getStreet( ));
				_user.setCityOfBirth(userOpenam.getCityOfBirth( ));
				_user.setCity(userOpenam.getCity( ));
			    _user.setPostalCode(userOpenam.getPostalCode( ));
				_user.setTelephoneNumber(userOpenam.getTelephoneNumber( ));
							
			}
						
			_user.setEmail(_esbNotif.getEmail());
			_user.setUserGuid(Long.parseLong(_esbNotif.getUserGuid()));
			_user.setStayConnected(true);
					
						
			GRUService.getService( ).store(_user);
				
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
					
			GRUService.getService( ).store(_demand);
						
			// Parse to Notification
			Notification _notification = new Notification();
			_notification.setDemandeId(_esbNotif.getDemandeId());
			_notification.setDemandIdType(_esbNotif.getDemandIdType());
			_notification.setUserEmail(_esbNotif.getUserEmail());
			_notification.setUserDashBoard(_esbNotif.getUserDashBoard());
			_notification.setUserSms(_esbNotif.getUserSms());
			_notification.setUserBackOffice(_esbNotif.getUserBackOffice());
				
			GRUService.getService( ).store(_notification);
		}
		catch(JSONException e)
		{
			e.getMessage();
			return GruSupplyConstants.STATUS_404;
		}

		return GruSupplyConstants.STATUS_201;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	



	
}
