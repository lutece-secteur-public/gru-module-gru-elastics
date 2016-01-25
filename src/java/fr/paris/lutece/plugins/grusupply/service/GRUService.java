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

import fr.paris.lutece.plugins.grusupply.business.dto.OpenAMUserDTO;
import fr.paris.lutece.plugins.grusupply.business.gru.Demand;
import fr.paris.lutece.plugins.grusupply.business.gru.Notification;
import fr.paris.lutece.plugins.grusupply.business.gru.User;
import fr.paris.lutece.portal.service.spring.SpringContextService;



public class GRUService
{
    private static final String BEAN_GRUSUPPLY_SERVICE = "grusupply.gruService";
    private INotificationStorageService _notificationStorageService;
    private IUserInfoProvider _userInfoProvider;
    private static GRUService _singleton;

    public static GRUService getService(  )
    {
        if ( _singleton == null )
        {
            _singleton = SpringContextService.getBean( BEAN_GRUSUPPLY_SERVICE );
        }

        return _singleton;
    }

   
    /**
     * Store notification
     * @param notification
     */
    public void store( Notification notification )
    {
    	
        	_notificationStorageService.store( notification );
        
    }

    /**
     * Store user
     * @param user
     */
    public void store( User user )
    {
      
    	     _notificationStorageService.store( user );
        
    }
    /**
     * Store demand 
     * @param demand
     */
    public void store( Demand demand )
    {
      
    		_notificationStorageService.store( demand );
        
    }

    /**
     * Store guid
     * @param guid
     * @return
     */
    public OpenAMUserDTO getUserInfo( String guid )
    {
   
            return (OpenAMUserDTO) _userInfoProvider.getUserInfo( guid );
      
    }
}
