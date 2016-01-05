package fr.paris.lutece.plugins.gru.modules.elastics.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import fr.paris.lutece.plugins.gru.business.customer.Customer;
import fr.paris.lutece.plugins.gru.business.customer.CustomerHome;
import fr.paris.lutece.plugins.gru.business.demand.BaseDemand;
import fr.paris.lutece.plugins.gru.business.demand.Demand;
import fr.paris.lutece.plugins.gru.business.demand.Notification;
import fr.paris.lutece.plugins.gru.modules.elastics.business.DemandMapping;
import fr.paris.lutece.plugins.gru.modules.elastics.business.DemandMappingHome;
import fr.paris.lutece.plugins.gru.service.demand.IDemandService;
import fr.paris.lutece.plugins.gru.service.demand.NotificationService;
import fr.paris.lutece.plugins.gru.service.demandtype.DemandTypeService;
import fr.paris.lutece.portal.business.user.AdminUser;

public class ElasticsearchDemandService implements IDemandService {

	ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public Demand getDemand(String strDemandId, String strDemandTypeId, AdminUser user) {
		// TODO Auto-generated method stub
		
		BaseDemand base = new BaseDemand();
		Demand demand =null;
		Notification notification = new Notification(  );
		DemandMapping mapping=DemandMappingHome.findByDemandId(strDemandId, Integer.parseInt(strDemandTypeId));
		String demandElastic = ElasticSearchHttpRequest.getDemandbyId(mapping.getStrElasticsearch_id());
		String notificationElastic = ElasticSearchHttpRequest.getNotificationById(mapping.getRefNotification());
		Customer customer = CustomerHome.findByPrimaryKey(mapping.getCustomerId());
		Map<String, Object> jsonDemand;
		
		try {
			jsonDemand = mapper.readValue(demandElastic, Map.class);
			base.setDemandTypeId(jsonDemand.get("demand_id_type").toString());
			base.setId(jsonDemand.get("demand_id").toString());
			base.setReference(jsonDemand.get("reference").toString());
			base.setStatus(Integer.parseInt(jsonDemand.get("crm_status_id").toString()));
			
			demand=DemandTypeService.buildDemand( base, customer, user );
			
			notification.setTimestamp( ( new Date(  ) ).getTime(  ) );
			notification.setTitle( "Prise en compte de la demande" );
			NotificationService.parseJSON( notification, notificationElastic );
			demand.addNotification(notification);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return demand;
	}


	@Override
	public List<BaseDemand> getDemands(String strCustomerId, AdminUser user) {
		// TODO Auto-generated method stub
		List<BaseDemand> list = new ArrayList<BaseDemand>();
			List<String> idList = DemandMappingHome.getiddemandList(Integer.parseInt(strCustomerId));
			for(String id: idList ){
				String demand=ElasticSearchHttpRequest.getDemandbyId(id);
					try {
						Map<String, Object> jsonDemand = mapper.readValue(demand, Map.class);
						BaseDemand base = new BaseDemand();
						base.setDemandTypeId(jsonDemand.get("demand_id_type").toString());
						base.setId(jsonDemand.get("demand_id").toString());
						base.setReference(jsonDemand.get("reference").toString());
						base.setStatus((Integer)jsonDemand.get("demand_state"));
						list.add(base);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
		
		
		return list;
	}

}
