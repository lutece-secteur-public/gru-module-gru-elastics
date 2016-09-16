/*
 * Copyright (c) 2002-2016, Mairie de Paris
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

import fr.paris.lutece.plugins.grusupply.business.Customer;
import fr.paris.lutece.plugins.identitystore.web.rs.dto.AttributeDto;
import fr.paris.lutece.plugins.identitystore.web.rs.dto.IdentityDto;
import fr.paris.lutece.plugins.identitystore.web.service.IdentityService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;


/**
 * This class provides customers
 *
 */
public class CustomerProvider
{
    //FIXME ? full recopie de IdentityStoreCustomerInfoService
    private static final String ATTRIBUTE_USER_NAME_GIVEN = "customerprovisioning.identity.attribute.user.name.given";
    private static final String ATTRIBUTE_USER_NAME_FAMILLY = "customerprovisioning.identity.attribute.user.name.family";
    private static final String ATTRIBUTE_USER_HOMEINFO_ONLINE_EMAIL = "customerprovisioning.identity.attribute.user.home-info.online.email";
    private static final String ATTRIBUTE_USER_HOMEINFO_TELECOM_TELEPHONE_NUMBER = "customerprovisioning.identity.attribute.user.home-info.telecom.telephone.number";
    private static final String ATTRIBUTE_USER_HOMEINFO_TELECOM_MOBILE_NUMBER = "customerprovisioning.identity.attribute.user.home-info.telecom.mobile.number";
    private static final String ATTRIBUTE_IDENTITY_NAME_GIVEN = AppPropertiesService.getProperty( ATTRIBUTE_USER_NAME_GIVEN );
    private static final String ATTRIBUTE_IDENTITY_NAME_FAMILLY = AppPropertiesService.getProperty( ATTRIBUTE_USER_NAME_FAMILLY );
    private static final String ATTRIBUTE_IDENTITY_HOMEINFO_ONLINE_EMAIL = AppPropertiesService.getProperty( ATTRIBUTE_USER_HOMEINFO_ONLINE_EMAIL );
    private static final String ATTRIBUTE_IDENTITY_HOMEINFO_TELECOM_TELEPHONE_NUMBER = AppPropertiesService.getProperty( ATTRIBUTE_USER_HOMEINFO_TELECOM_TELEPHONE_NUMBER );
    private static final String ATTRIBUTE_IDENTITY_HOMEINFO_TELECOM_MOBILE_NUMBER = AppPropertiesService.getProperty( ATTRIBUTE_USER_HOMEINFO_TELECOM_MOBILE_NUMBER );
    private static final String DEFAULT_CUSTOMER_ID = "0";

    //Service identityStore
    private static final String BEAN_IDENTITYSTORE_SERVICE = "grusupply.identitystore.service";
    private static CustomerProvider _singleton;
    private static boolean bIsInitialized = false;
    private static final String APPLICATION_CODE = "GruSupply";
    private IdentityService _identityService;

    /**
     * retrieve singleton
     */
    public static CustomerProvider instance(  )
    {
        if ( !bIsInitialized )
        {
            try
            {
                _singleton = new CustomerProvider(  );
                _singleton.setIdentityService( (IdentityService) SpringContextService.getBean( 
                        BEAN_IDENTITYSTORE_SERVICE ) );
            }
            catch ( NoSuchBeanDefinitionException e )
            {
                // The notification bean has not been found, the application must use the ESB
                AppLogService.info( "No notification bean found, the application must use the ESB" );
            }
            finally
            {
                bIsInitialized = true;
            }
        }

        return _singleton;
    }

    private void setIdentityService( IdentityService identityService )
    {
        this._identityService = identityService;
    }

    /**
     * Provides a customer with the specified GUID / CID
     * @param strGuid the GUID
     * @param strCid the customer id
     * @return the customer
     */
    public Customer get( String strGuid, String strCid )
    {
        if ( StringUtils.isBlank( strGuid ) )
        {
            strGuid = StringUtils.EMPTY;
        }

        if ( StringUtils.isBlank( strCid ) || !StringUtils.isNumeric( strCid ) )
        {
            strCid = DEFAULT_CUSTOMER_ID;
        }

        IdentityDto identityDto = _identityService.getIdentity( strGuid, Integer.parseInt( strCid ), APPLICATION_CODE,
                StringUtils.EMPTY );

        return convert( identityDto );
    }

    /**
     * Converts a GRU customer to a GRU supply customer
     *
     * @param customerGru the GRU customer
     * @return the GRU supply customer
     */
    private static Customer convert( IdentityDto identityDto )
    {
        Customer customerGruSupply = new Customer(  );

        customerGruSupply.setCustomerId( identityDto.getCustomerId(  ) );
        customerGruSupply.setName( getAttribute( identityDto, ATTRIBUTE_IDENTITY_NAME_FAMILLY ) );
        customerGruSupply.setFirstName( getAttribute( identityDto, ATTRIBUTE_IDENTITY_NAME_GIVEN ) );
        customerGruSupply.setEmail( getAttribute( identityDto, ATTRIBUTE_IDENTITY_HOMEINFO_ONLINE_EMAIL ) );
        customerGruSupply.setTelephoneNumber( getAttribute( identityDto,
                ATTRIBUTE_IDENTITY_HOMEINFO_TELECOM_MOBILE_NUMBER ) );
        customerGruSupply.setFixedTelephoneNumber( getAttribute( identityDto,
                ATTRIBUTE_IDENTITY_HOMEINFO_TELECOM_TELEPHONE_NUMBER ) );
        customerGruSupply.setStayConnected( true );

        return customerGruSupply;
    }

    /**
     * Gets the attribute value from the specified identity
     * @param identityDto the identity
     * @param strCode the attribute code
     * @return {@code null} if the attribute does not exist, the attribute value otherwise
     */
    private static String getAttribute( IdentityDto identityDto, String strCode )
    {
        AttributeDto attribute = identityDto.getAttributes(  ).get( strCode );

        return ( attribute == null ) ? null : attribute.getValue(  );
    }
}
