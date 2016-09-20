/*
 * Copyright (c) 2002-2013, Mairie de Paris
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
package fr.paris.lutece.plugins.grusupply.business;


/**
 * This is the business class for the object Demand
 */
public class Demand
{
    // Variables declarations 
    private Customer _oCustomer;
    private int _nDemandId;
    private int _nDemandTypeId;
    private int _nDemandMaxStep;
    private int _nDemandUserCurrentStep;
    private int _nDemandStatus;
    private String _strNotifType;
    private int _nCRMStatus;
    private String _strReference;

    /**
     * Returns the Customer
     * @return The Customer
     */
    public Customer getCustomer(  )
    {
        return _oCustomer;
    }

    /**
     * Gets the Customer
     * @param _oCustomer
     */
    public void setCustomer( Customer oCustomer )
    {
        this._oCustomer = oCustomer;
    }

    /**
    * Returns the DemandId
    * @return The DemandId
    */
    public int getDemandId(  )
    {
        return _nDemandId;
    }

    /**
     * Sets the DemandId
     * @param nDemandId The DemandId
     */
    public void setDemandId( int nDemandId )
    {
        _nDemandId = nDemandId;
    }

    /**
     * Returns the DemandTypeId
     * @return The DemandTypeId
     */
    public int getDemandTypeId(  )
    {
        return _nDemandTypeId;
    }

    /**
     * Sets the DemandTypeId
     * @param nDemandTypeId The DemandTypeId
     */
    public void setDemandTypeId( int nDemandTypeId )
    {
        _nDemandTypeId = nDemandTypeId;
    }

    /**
     * Returns the DemandMaxStep
     * @return The DemandMaxStep
     */
    public int getDemandMaxStep(  )
    {
        return _nDemandMaxStep;
    }

    /**
     * Sets the DemandMaxStep
     * @param nDemandMaxStep The DemandMaxStep
     */
    public void setDemandMaxStep( int nDemandMaxStep )
    {
        _nDemandMaxStep = nDemandMaxStep;
    }

    /**
     * Returns the DemandUserCurrentStep
     * @return The DemandUserCurrentStep
     */
    public int getDemandUserCurrentStep(  )
    {
        return _nDemandUserCurrentStep;
    }

    /**
     * Sets the DemandUserCurrentStep
     * @param nDemandUserCurrentStep The DemandUserCurrentStep
     */
    public void setDemandUserCurrentStep( int nDemandUserCurrentStep )
    {
        _nDemandUserCurrentStep = nDemandUserCurrentStep;
    }

    /**
     * Returns the NotifType
     * @return The NotifType
     */
    public String getNotifType(  )
    {
        return _strNotifType;
    }

    /**
     * Sets the NotifType
     * @param strNotifType The NotifType
     */
    public void setNotifType( String strNotifType )
    {
        _strNotifType = strNotifType;
    }

    /**
     * Returns the CRMStatus
     * @return The CRMStatus
     */
    public int getCRMStatus(  )
    {
        return _nCRMStatus;
    }

    /**
     * Sets the CRMStatus
     * @param nCRMStatus The CRMStatus
     */
    public void setCRMStatus( int nCRMStatus )
    {
        _nCRMStatus = nCRMStatus;
    }

    /**
     * Returns the Demand Status
     * @return
     */
    public int getDemandStatus(  )
    {
        return _nDemandStatus;
    }

    /**
     * Sets the Demand Status
     * @param nDemandStatus
     */
    public void setDemandStatus( int nDemandStatus )
    {
        this._nDemandStatus = nDemandStatus;
    }

    /**
    * Returns the Reference
    * @return The Reference
    */
    public String getReference(  )
    {
        return _strReference;
    }

    /**
     * Sets the Reference
     * @param strReference The Reference
     */
    public void setReference( String strReference )
    {
        _strReference = strReference;
    }
}
