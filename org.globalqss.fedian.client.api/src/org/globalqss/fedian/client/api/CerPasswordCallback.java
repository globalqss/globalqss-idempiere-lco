/**********************************************************************
* This file is part of iDempiere ERP Open Source                      *
* http://www.idempiere.org                                            *
*                                                                     *
* Copyright (C) Contributors                                          *
*                                                                     *
* This program is free software; you can redistribute it and/or       *
* modify it under the terms of the GNU General Public License         *
* as published by the Free Software Foundation; either version 2      *
* of the License, or (at your option) any later version.              *
*                                                                     *
* This program is distributed in the hope that it will be useful,     *
* but WITHOUT ANY WARRANTY; without even the implied warranty of      *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
* GNU General Public License for more details.                        *
*                                                                     *
* You should have received a copy of the GNU General Public License   *
* along with this program; if not, write to the Free Software         *
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
* MA 02110-1301, USA.                                                 *
*                                                                     *
* Contributors:                                                       *
* - Carlos Ruiz - globalqss                                           *
**********************************************************************/

package org.globalqss.fedian.client.api;

import java.io.IOException;
import java.util.UUID;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.crypto.JasyptPasswordEncryptor;
import org.apache.wss4j.common.crypto.PasswordEncryptor;
import org.apache.wss4j.common.ext.WSPasswordCallback;

public class CerPasswordCallback implements CallbackHandler{
	public final static String encrytedPasswordKey = "JasyptPasswordEncryptor.encrytedPassword.keystore";
	public final static String masterPasswordKey = "JasyptPasswordEncryptor.master.password";
	
	public static String getMasterPassword () {
		String masterPass = System.getProperty(CerPasswordCallback.masterPasswordKey);
		if (masterPass == null || masterPass.length() == 0) {
			masterPass = UUID.randomUUID().toString();
			System.setProperty(CerPasswordCallback.masterPasswordKey, masterPass);
		}
		return masterPass;
	}
	
	public static String getEncrytedPassword () {
		return System.getProperty(CerPasswordCallback.encrytedPasswordKey);
	}
	
	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		for (int i = 0; i < callbacks.length; i++) {
			if (!(callbacks[i] instanceof WSPasswordCallback))
				continue;
			
			WSPasswordCallback pc = (WSPasswordCallback) callbacks[i];

			if (pc.getUsage() == WSPasswordCallback.PASSWORD_ENCRYPTOR_PASSWORD) {
				/*
				 * set master password for JasyptPasswordEncryptor to decrypt keystore password set on Merlin.PREFIX + Merlin.KEYSTORE_PASSWORD 
				 */
				pc.setPassword(CerPasswordCallback.getMasterPassword());
			}else if (pc.getUsage() == WSPasswordCallback.SIGNATURE) {// WSPasswordCallback.SIGNATURE - SIGNATURE usage is used on the outbound side only, to get a password to get the private key of this identifier (alias) from a keystore. The CallbackHandler must set the password via the setPassword(String) method.
				
				// we use Merlin, so set null to use encrypted password set on properties with key Merlin.PREFIX + Merlin.KEYSTORE_PRIVATE_PASSWORD"
				// UPDATE pending to investigate: at moment, this method get issue, because one PasswordEncryptor can't call two time (exception Encryption entity already initialized)
				// pc.setPassword(null);
				
				// call JasyptPasswordEncryptor manual
				PasswordEncryptor passwordEncryptor = new JasyptPasswordEncryptor(this);
				pc.setPassword(passwordEncryptor.decrypt(CerPasswordCallback.getEncrytedPassword()));
			}else {
				PasswordEncryptor passwordEncryptor = new JasyptPasswordEncryptor(this);
				pc.setPassword(passwordEncryptor.decrypt(CerPasswordCallback.getEncrytedPassword()));
			}
		}
		
	}

}
