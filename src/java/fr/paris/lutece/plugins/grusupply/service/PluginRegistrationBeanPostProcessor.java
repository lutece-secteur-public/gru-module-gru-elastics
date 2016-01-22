package fr.paris.lutece.plugins.grusupply.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;


public class PluginRegistrationBeanPostProcessor implements BeanPostProcessor {

	private GRUService manager;
	 
	public PluginRegistrationBeanPostProcessor(GRUService manager)
	{	
		this.manager = manager;
	}
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String strBeanName)
			throws BeansException {
		
		if (bean instanceof AbsractStorageService)
		{	
			manager.addNotificationStorageBean( (INotificationStorageService) bean);
		
		}else if(bean instanceof IUserInfoProvider){
		
			manager.addUserInfoProviderBean((IUserInfoProvider) bean);
		
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String strBeanName)
			throws BeansException {
		
		return bean;
	}
	
	

}
