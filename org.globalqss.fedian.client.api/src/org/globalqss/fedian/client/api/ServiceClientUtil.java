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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import javax.xml.ws.BindingProvider;

import org.adempiere.exceptions.AdempiereException;
import org.apache.cxf.ws.security.SecurityConstants;
import org.apache.wss4j.common.crypto.JasyptPasswordEncryptor;
import org.apache.wss4j.common.crypto.Merlin;
import org.apache.wss4j.policy.SPConstants;
import org.compiere.model.MSysConfig;
import org.compiere.util.Env;
import org.datacontract.schemas._2004._07.dianresponse.DianResponse;
import org.datacontract.schemas._2004._07.numberrangeresponselist.NumberRangeResponseList;

import colombia.dian.wcf.IWcfDianCustomerServices;
import colombia.dian.wcf.WcfDianCustomerServices;

public class ServiceClientUtil {

	public static String URL_CERTIFICACION = "https://vpfe-hab.dian.gov.co/WcfDianCustomerServices.svc?wsdl";
	public static String URL_PRODUCCION = "https://vpfe.dian.gov.co/WcfDianCustomerServices.svc?wsdl";

	public static IWcfDianCustomerServices getClientProxy() {
		System.setProperty("javax.xml.soap.SAAJMetaFactory", "com.sun.xml.messaging.saaj.soap.SAAJMetaFactoryImpl");
		System.setProperty("javax.xml.ws.spi.Provider", "org.apache.cxf.jaxws.spi.ProviderImpl");

		boolean isOnTesting = MSysConfig.getBooleanValue("QSSLCO_FE_EnPruebas", false, Env.getAD_Client_ID(Env.getCtx()));
		URL url;
		try {
			String urlEnv = isOnTesting ? URL_CERTIFICACION : URL_PRODUCCION;
			url = new URL(urlEnv);
		} catch (MalformedURLException e) {
			throw new AdempiereException(e);
		}

		WcfDianCustomerServices service = new WcfDianCustomerServices (url);

		//LoggingFeature logRequestFeature = new LoggingFeature();
		//logRequestFeature.setPrettyLogging(true);
		IWcfDianCustomerServices port = service.getWSHttpBindingIWcfDianCustomerServices();

		Properties cerProperties = new Properties();
		cerProperties.put("org.apache.ws.security.crypto.provider", "org.apache.ws.security.components.crypto.Merlin");
		cerProperties.put(Merlin.PREFIX + Merlin.KEYSTORE_TYPE, "PKCS12");

		String cerPath = MSysConfig.getValue("QSSLCO_FE_RutaCertificadoDigital", Env.getAD_Client_ID(Env.getCtx()));
		cerProperties.put(Merlin.PREFIX + Merlin.KEYSTORE_FILE, cerPath);
		String plainPassword = MSysConfig.getValue("QSSLCO_FE_ClaveCertificadoDigital", Env.getAD_Client_ID(Env.getCtx()));
		JasyptPasswordEncryptor encryptor = new JasyptPasswordEncryptor(new CerPasswordCallback());
		String encryptedPassword =  encryptor.encrypt(plainPassword);
		System.setProperty(CerPasswordCallback.encrytedPasswordKey, encryptedPassword);
		cerProperties.put(Merlin.PREFIX + Merlin.KEYSTORE_PASSWORD, Merlin.ENCRYPTED_PASSWORD_PREFIX + encryptedPassword + Merlin.ENCRYPTED_PASSWORD_SUFFIX);

		Map<String, Object> ctx = ((BindingProvider) port).getRequestContext();

		ctx.put(SecurityConstants.SIGNATURE_PROPERTIES, cerProperties);
		//ctx.put(SecurityConstants.CALLBACK_HANDLER, CerPasswordCallback.class.getName());
		ctx.put(SecurityConstants.CALLBACK_HANDLER, new CerPasswordCallback());
		String signatureUsername = MSysConfig.getValue("QSSLCO_FE_UsuarioCertificadoDigital", Env.getAD_Client_ID(Env.getCtx()));
		ctx.put(SecurityConstants.SIGNATURE_USERNAME, " " + signatureUsername);
		ctx.put(SecurityConstants.ASYMMETRIC_SIGNATURE_ALGORITHM, SPConstants.RSA_SHA256);

		return port;
	}

	public static DianResponse getStatus(String auth) {
		ClassLoader currentThreadClassLoader = null;
		try {
			currentThreadClassLoader =  Thread.currentThread().getContextClassLoader();
			Thread.currentThread().setContextClassLoader(ServiceClientUtil.class.getClassLoader());

			IWcfDianCustomerServices port = getClientProxy();
			DianResponse response = port.getStatus(auth);
			return response;
		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			if (currentThreadClassLoader != null && currentThreadClassLoader != Thread.currentThread().getContextClassLoader())
				Thread.currentThread().setContextClassLoader(currentThreadClassLoader);
		}
	}

	public static NumberRangeResponseList getNumberingRange(String accountCode, String accountCodeT, String softwareCode) {
		ClassLoader currentThreadClassLoader = null;
		try {
			currentThreadClassLoader =  Thread.currentThread().getContextClassLoader();
			Thread.currentThread().setContextClassLoader(ServiceClientUtil.class.getClassLoader());

			IWcfDianCustomerServices port = getClientProxy();
			NumberRangeResponseList response = port.getNumberingRange(accountCode, accountCodeT, softwareCode);
			return response;
		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			if (currentThreadClassLoader != null && currentThreadClassLoader != Thread.currentThread().getContextClassLoader())
				Thread.currentThread().setContextClassLoader(currentThreadClassLoader);
		}
	}

	public static DianResponse sendFile(String fileName, byte[] contentFile) {
		ClassLoader currentThreadClassLoader = null;
		try {
			currentThreadClassLoader =  Thread.currentThread().getContextClassLoader();
			Thread.currentThread().setContextClassLoader(ServiceClientUtil.class.getClassLoader());

			IWcfDianCustomerServices port = getClientProxy();
			DianResponse response = port.sendBillSync(fileName, contentFile);
			return response;
		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			if (currentThreadClassLoader != null && currentThreadClassLoader != Thread.currentThread().getContextClassLoader())
				Thread.currentThread().setContextClassLoader(currentThreadClassLoader);
		}
	}

}
