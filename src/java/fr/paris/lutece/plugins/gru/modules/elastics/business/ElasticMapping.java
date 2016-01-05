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
package fr.paris.lutece.plugins.gru.modules.elastics.business;
/**
 * 
 * @author
 *
 */
 
public class ElasticMapping {

	private int id_customer;
	private int id_user;
	private int id_mapping;
	private String strRefUser;
	/**
	 * 
	 * @return
	 */
	public String getStrRefUser() {
		return strRefUser;
	}
	/**
	 * 
	 * @param strRefUser
	 */
	public void setStrRefUser(String strRefUser) {
		this.strRefUser = strRefUser;
	}
	/**
	 * 
	 * @return
	 */
	public int getId_mapping() {
		return id_mapping;
	}
	/**
	 * 
	 * @param id_mapping
	 */
	public void setId_mapping(int id_mapping) {
		this.id_mapping = id_mapping;
	}
	/**
	 * 
	 * @return
	 */
	public int getId_customer() {
		return id_customer;
	}
	/**
	 * 
	 * @param id_customer
	 */
	public void setId_customer(int id_customer) {
		this.id_customer = id_customer;
	}
	/**
	 * 
	 * @return
	 */
	public int getId_user() {
		return id_user;
	}
	/**
	 * 
	 * @param id_user
	 */
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	
}
