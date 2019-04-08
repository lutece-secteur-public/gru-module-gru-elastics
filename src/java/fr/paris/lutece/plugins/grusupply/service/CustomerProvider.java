/*
 * Copyright (c) 2002-2017, Mairie de Paris
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

import fr.paris.lutece.plugins.grubusiness.business.customer.Customer;
import fr.paris.lutece.plugins.grubusiness.business.demand.Demand;
import fr.paris.lutece.plugins.grubusiness.service.encryption.ICustomerEncryptionService;
import fr.paris.lutece.plugins.identitystore.v1.web.rs.dto.AttributeDto;
import fr.paris.lutece.plugins.identitystore.v1.web.rs.dto.IdentityDto;
import fr.paris.lutece.plugins.identitystore.v1.web.service.IdentityService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

/**
 * This class provides customers
 *
 */
public class CustomerProvider
{
    // Properties
    private static final String PROPERTIES_APPLICATION_CODE = "grusupply.application.code";
    private static final String PROPERTIES_ATTRIBUTE_USER_NAME_GIVEN = "grusupply.identity.attribute.user.name.given";
    private static final String PROPERTIES_ATTRIBUTE_USER_NAME_FAMILLY = "grusupply.identity.attribute.user.name.family";
    private static final String PROPERTIES_ATTRIBUTE_USER_HOMEINFO_ONLINE_EMAIL = "grusupply.identity.attribute.user.home-info.online.email";
    private static final String PROPERTIES_ATTRIBUTE_USER_HOMEINFO_TELECOM_TELEPHONE_NUMBER = "grusupply.identity.attribute.user.home-info.telecom.telephone.number";
    private static final String PROPERTIES_ATTRIBUTE_USER_HOMEINFO_TELECOM_MOBILE_NUMBER = "grusupply.identity.attribute.user.home-info.telecom.mobile.number";
    private static final String PROPERTIES_ATTRIBUTE_USER_GENDER = "grusupply.identity.attribute.user.gender";
    private static final String PROPERTIES_ATTRIBUTE_USER_BIRTHDATE = "grusupply.identity.attribute.user.bdate";
    private static final String APPLICATION_CODE = AppPropertiesService.getProperty( PROPERTIES_APPLICATION_CODE );
    private static final String ATTRIBUTE_IDENTITY_NAME_GIVEN = AppPropertiesService.getProperty( PROPERTIES_ATTRIBUTE_USER_NAME_GIVEN );
    private static final String ATTRIBUTE_IDENTITY_NAME_FAMILLY = AppPropertiesService.getProperty( PROPERTIES_ATTRIBUTE_USER_NAME_FAMILLY );
    private static final String ATTRIBUTE_IDENTITY_HOMEINFO_ONLINE_EMAIL = AppPropertiesService.getProperty( PROPERTIES_ATTRIBUTE_USER_HOMEINFO_ONLINE_EMAIL );
    private static final String ATTRIBUTE_IDENTITY_HOMEINFO_TELECOM_TELEPHONE_NUMBER = AppPropertiesService
            .getProperty( PROPERTIES_ATTRIBUTE_USER_HOMEINFO_TELECOM_TELEPHONE_NUMBER );
    private static final String ATTRIBUTE_IDENTITY_HOMEINFO_TELECOM_MOBILE_NUMBER = AppPropertiesService
            .getProperty( PROPERTIES_ATTRIBUTE_USER_HOMEINFO_TELECOM_MOBILE_NUMBER );
    private static final String ATTRIBUTE_IDENTITY_GENDER = AppPropertiesService.getProperty( PROPERTIES_ATTRIBUTE_USER_GENDER );
    private static final String ATTRIBUTE_IDENTITY_BIRTHDATE = AppPropertiesService.getProperty( PROPERTIES_ATTRIBUTE_USER_BIRTHDATE );

    // Service identityStore
    private static final String BEAN_IDENTITYSTORE_SERVICE = "grusupply.identitystore.service";
    private static CustomerProvider _singleton;
    private static boolean bIsInitialized = false;
    private static List<ICustomerEncryptionService> _listCustomerEncryption;

    private IdentityService _identityService;

    /**
     * retrieve singleton
     */
    public static CustomerProvider instance( )
    {
        if ( !bIsInitialized )
        {
            try
            {
                _singleton = new CustomerProvider( );
                _singleton.setIdentityService( (IdentityService) SpringContextService.getBean( BEAN_IDENTITYSTORE_SERVICE ) );
                _listCustomerEncryption = SpringContextService.getBeansOfType( ICustomerEncryptionService.class );
            }
            catch( NoSuchBeanDefinitionException e )
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
     * 
     * @param strGuid
     *            the GUID
     * @param strCid
     *            the customer id
     * @return the customer
     */
    public Customer get( String strGuid, String strCid )
    {
        if ( StringUtils.isBlank( strGuid ) )
        {
            strGuid = StringUtils.EMPTY;
        }

        IdentityDto identityDto = _identityService.getIdentity( strGuid, strCid, APPLICATION_CODE );

        Customer customerEncrypted = convert( identityDto );

        return decrypt( customerEncrypted, APPLICATION_CODE );
    }

    /**
     * <p>
     * Decrypts a {@link Customer} from the specified {@code Customer}.
     * <p>
     * <p>
     * The provided {@code Customer} is not modified.
     * </p>
     * 
     * @param customer
     *            the customer from which the {@code Customer} is decrypted. Must not be {@code null}
     * @param strCode
     *            the code used to decrypt the {@code Customer}. Must not be {@code null}
     * @return the decrypted {@code Customer}
     */
    public Customer decrypt( Customer customer, String strCode )
    {
        Customer customerResult = customer;

        if ( customerResult != null )
        {
            for ( ICustomerEncryptionService customerEncryptionService : _listCustomerEncryption )
            {
                customerResult = customerEncryptionService.decrypt( customerResult, strCode );
            }
        }

        return customerResult;
    }

    /**
     * <p>
     * Decrypts a {@link Customer} from the specified {@code Customer}.
     * <p>
     * <p>
     * The provided {@code Customer} is not modified.
     * </p>
     * 
     * @param customer
     *            the customer from which the {@code Customer} is decrypted. Must not be {@code null}
     * @param demand
     *            the demand for which the {@code Customer} is decrypted. Must not be {@code null}
     * @return the decrypted {@code Customer}
     */
    public Customer decrypt( Customer customer, Demand demand )
    {
        Customer customerResult = customer;

        if ( customerResult != null )
        {
            for ( ICustomerEncryptionService customerEncryptionService : _listCustomerEncryption )
            {
                customerResult = customerEncryptionService.decrypt( customerResult, demand );
            }
        }

        return customerResult;
    }

    /**
     * Converts a IdentityDto to a customer
     *
     * @param identityDto
     *            the identity
     * @return the customer
     */
    private static Customer convert( IdentityDto identityDto )
    {
        Customer customer = new Customer( );

        customer.setId( identityDto.getCustomerId( ) );
        customer.setConnectionId( identityDto.getConnectionId( ) );
        customer.setLastname( getAttribute( identityDto, ATTRIBUTE_IDENTITY_NAME_FAMILLY ) );
        customer.setFirstname( getAttribute( identityDto, ATTRIBUTE_IDENTITY_NAME_GIVEN ) );
        customer.setEmail( getAttribute( identityDto, ATTRIBUTE_IDENTITY_HOMEINFO_ONLINE_EMAIL ) );
        customer.setMobilePhone( getAttribute( identityDto, ATTRIBUTE_IDENTITY_HOMEINFO_TELECOM_MOBILE_NUMBER ) );
        customer.setFixedPhoneNumber( getAttribute( identityDto, ATTRIBUTE_IDENTITY_HOMEINFO_TELECOM_TELEPHONE_NUMBER ) );
        customer.setIdTitle( NumberUtils.toInt( getAttribute( identityDto, ATTRIBUTE_IDENTITY_GENDER ) ) );
        customer.setBirthDate( getAttribute( identityDto, ATTRIBUTE_IDENTITY_BIRTHDATE ) );

        return customer;
    }

    /**
     * Gets the attribute value from the specified identity
     * 
     * @param identityDto
     *            the identity
     * @param strCode
     *            the attribute code
     * @return {@code null} if the attribute does not exist, the attribute value otherwise
     */
    private static String getAttribute( IdentityDto identityDto, String strCode )
    {
        AttributeDto attribute = identityDto.getAttributes( ).get( strCode );

        return ( attribute == null ) ? null : attribute.getValue( );
    }
}
