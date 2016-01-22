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
package fr.paris.lutece.plugins.grusupply.service;

import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.plugins.grusupply.model.OpenAMUser;
import fr.paris.lutece.plugins.grusupply.model.gru.Demand;
import fr.paris.lutece.plugins.grusupply.model.gru.Notification;
import fr.paris.lutece.plugins.grusupply.model.gru.User;
import fr.paris.lutece.portal.service.spring.SpringContextService;

public class GRUService  {
	
	private static final String BEAN_GRUSUPPLY_SERVICE="grusupply.gruService";
	
	private static List<INotificationStorageService> _notificationStorageService = new ArrayList<INotificationStorageService>( );
	private static List<IUserInfoProvider> _userInfoProvider = new ArrayList<IUserInfoProvider>( );
	private static GRUService _singleton;
	

    public static GRUService  getService( )
    {
    	if(_singleton == null){
    		
    		_singleton= SpringContextService.getBean( BEAN_GRUSUPPLY_SERVICE );
    	}
        return _singleton;
    }
    
    public void addNotificationStorageBean(INotificationStorageService nStorageService){
    	
    	_notificationStorageService.add(nStorageService);
    }
    
    public void addUserInfoProviderBean(IUserInfoProvider userInfoProvider){
    	
    	_userInfoProvider.add(userInfoProvider);
    }
    /**
     * 
     * @param notification
     */
    
	public  void store(Notification _notification)
	{
        
       for ( INotificationStorageService storageService : _notificationStorageService ){
           	
           	storageService.store(_notification);
       }
	}
	
	
	public  void store(User _user)
	{
       
        for ( INotificationStorageService storageService : _notificationStorageService ){
        
        	storageService.store(_user);
        }
	}
	
	
	public  void store(Demand _demand)
	{

            for ( INotificationStorageService storageService : _notificationStorageService ){
            	
            	storageService.store(_demand);
            }
	}
	
	
	public OpenAMUser getUserInfo(String guid) {
		
		for ( IUserInfoProvider user : _userInfoProvider ){
        	// A REVOIRE COMMENT FAIRE POUR LE MAPING
			return (OpenAMUser) user.getUserInfo(guid);
        }
		
		return null;
		
	}
}
