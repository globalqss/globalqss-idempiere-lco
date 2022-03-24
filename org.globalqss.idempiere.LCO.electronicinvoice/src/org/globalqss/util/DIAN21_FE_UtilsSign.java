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

package org.globalqss.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.adempiere.exceptions.AdempiereException;
import org.apache.xml.security.algorithms.MessageDigestAlgorithm;
import org.apache.xml.security.c14n.Canonicalizer;
import org.compiere.model.MSysConfig;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.globalqss.model.LCO_FE_MInvoice;
import org.globalqss.model.MLCOFEAuthorization;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import xades4j.UnsupportedAlgorithmException;
import xades4j.XAdES4jException;
import xades4j.algorithms.Algorithm;
import xades4j.algorithms.EnvelopedSignatureTransform;
import xades4j.algorithms.GenericAlgorithm;
import xades4j.production.BasicSignatureOptions;
import xades4j.production.DataObjectReference;
import xades4j.production.SignatureAppendingStrategies;
import xades4j.production.SignedDataObjects;
import xades4j.production.XadesEpesSigningProfile;
import xades4j.production.XadesSigner;
import xades4j.properties.DataObjectDesc;
import xades4j.properties.IdentifierType;
import xades4j.properties.ObjectIdentifier;
import xades4j.properties.SignaturePolicyBase;
import xades4j.properties.SignaturePolicyIdentifierProperty;
import xades4j.properties.SignerRoleProperty;
import xades4j.properties.SigningTimeProperty;
import xades4j.providers.AlgorithmsProviderEx;
import xades4j.providers.KeyingDataProvider;
import xades4j.providers.SignaturePolicyInfoProvider;
import xades4j.providers.SignaturePropertiesCollector;
import xades4j.providers.SignaturePropertiesProvider;
import xades4j.providers.impl.FileSystemKeyStoreKeyingDataProvider;
import xades4j.providers.impl.KeyStoreKeyingDataProvider;
import xades4j.providers.impl.KeyStoreKeyingDataProvider.SigningCertSelector;

public class DIAN21_FE_UtilsSign {

	/**	s_log			*/
	private static CLogger s_log = CLogger.getCLogger(DIAN21_FE_UtilsSign.class);

	private String m_Resource_To_Sign = "";

	private String m_PKCS12_Resource = null;
	private String m_PKCS12_Password = "changeit";

	private String m_Output_Directory;

	public String signFile(LCO_FE_MInvoice invoice) {
		String msg = null;

		try
		{
			m_PKCS12_Resource = MSysConfig.getValue("QSSLCO_FE_RutaCertificadoDigital", null, invoice.getAD_Client_ID(), invoice.getAD_Org_ID());
			m_PKCS12_Password = MSysConfig.getValue("QSSLCO_FE_ClaveCertificadoDigital", "temporal", invoice.getAD_Client_ID(), invoice.getAD_Org_ID());

			m_Resource_To_Sign = invoice.get_FE_FileToSign();
			s_log.warning("@Signing Xml@ -> " + m_Resource_To_Sign);
			m_Output_Directory = invoice.get_FE_FolderRaiz() + File.separator + LCO_FE_Utils.FOLDER_COMPROBANTES_FIRMADOS;
			(new File(invoice.get_FE_FolderRaiz() + File.separator + LCO_FE_Utils.FOLDER_COMPROBANTES_FIRMADOS + File.separator)).mkdirs();
			sign(invoice);

			String signed_file_name = invoice.get_FE_FileSigned();

			// out of Trx to save auth and attachments independently
			MLCOFEAuthorization a = new MLCOFEAuthorization (invoice.getCtx(), invoice.get_ValueAsInt("LCO_FE_Authorization_ID"), null);

			// Attach XML Firmado
			Files.copy(Paths.get(signed_file_name), Paths.get(signed_file_name.replace(".xml", "_signed.xml")), StandardCopyOption.REPLACE_EXISTING);
			if (invoice.is_FE_AttachXml())
				LCO_FE_Utils.attachFile(a.getCtx(), a.get_TrxName(), a.getLCO_FE_Authorization_ID(), signed_file_name.replace(".xml", "_signed.xml"), LCO_FE_Utils.RESOURCE_XML);
			
			// Prepare the file to send via webservices
	        String file_zip = zipFSPUser(signed_file_name);
			invoice.set_FE_FileToSend(file_zip);
			if (invoice.is_FE_AttachXml())
				LCO_FE_Utils.attachFile(a.getCtx(), a.get_TrxName(), a.getLCO_FE_Authorization_ID(), file_zip, LCO_FE_Utils.RESOURCE_ZIP);

		}
		catch (Exception e)
		{
			msg = Msg.getMsg(Env.getCtx(), "FileCannotCreate") + " "
					+ invoice.get_FE_FileType() + "-"
					+ e.getCause() + "-" + e.getLocalizedMessage();
			s_log.severe(msg);
			return msg;
		} catch (Error e) {
			msg = Msg.getMsg(Env.getCtx(), "ValidateConnectionOnStartup") + " "
					+ LCO_FE_Utils.PROVEEDOR_TECNOLOGICO + "-"
					+ e.getCause() + "-" + e.getLocalizedMessage();
			s_log.severe(msg);
			return msg;
		}

		return msg;
	} // signFile

	public void sign(LCO_FE_MInvoice invoice) throws Exception {

		// Obtencion del certificado para firmar. Utilizaremos el primer
		// certificado del almacen.
		FirstCertificateSelector fcs = new FirstCertificateSelector();

		// Obtención de la clave privada asociada al certificado
		DirectPasswordProvider dpp = new DirectPasswordProvider(m_PKCS12_Password);

		// Abre el archivo XML que se desea firmar
		Document doc = getDocument(m_Resource_To_Sign);
		doc.normalizeDocument();

		// Establece el punto donde se requiere la firma (segundo elemento ext:ExtensionContent del XML
		NodeList tag = doc.getElementsByTagName(LCO_FE_Utils.SIGNER_ELEMENT);	// Busca los elementos que concuerden con el string indicado
		org.w3c.dom.Node elemToSign = tag.item(tag.getLength()-1);	// Encuentra el nodo en la lista anterior

		if (elemToSign == null) {
			System.err.println("Error no se encontró elemento para firmar");
			throw new AdempiereException("Error no se encontró elemento para firmar " + LCO_FE_Utils.SIGNER_ELEMENT);
		}

		DataObjectDesc dataObjRef = new DataObjectReference("").withTransform(new EnvelopedSignatureTransform());	// Crea un dataobject del xml para firmar

		// TODO From storeManager
		// From filesystem
		// returnFullChain (false) to avoid X509Certificate element dups	// TODO Reviewme
		KeyingDataProvider kdp = new FileSystemKeyStoreKeyingDataProvider("PKCS12", m_PKCS12_Resource, fcs, dpp, dpp, false);

		// Politica
		SignaturePolicyInfoProvider policyInfoProvider = new SignaturePolicyInfoProvider() {
			@Override
			public SignaturePolicyBase getSignaturePolicy() {
				try {
					return new SignaturePolicyIdentifierProperty(
							new ObjectIdentifier(LCO_FE_Utils.SIGNER_POLICY_V2, 
									IdentifierType.URI, 
									LCO_FE_Utils.SIGNER_POLICY_ID),
							new URL(LCO_FE_Utils.SIGNER_POLICY_V2).openStream());
				} catch (Exception ex) {
					throw new AdempiereException(ex);
				}
			}
		};

		// Role
		XadesSigner signer = null;
		ClassLoader cl1 = Thread.currentThread().getContextClassLoader();
		ClassLoader cl2 = this.getClass().getClassLoader();
		try {
			if (!cl1.equals(cl2)) {
				Thread.currentThread().setContextClassLoader(cl2);
			}

			signer = (XadesSigner) new XadesEpesSigningProfile(kdp, policyInfoProvider)               
					.withSignaturePropertiesProvider(new SignaturePropertiesProvider() {
						@Override
						public void provideProperties(SignaturePropertiesCollector arg0) {
							SigningTimeProperty sigTime = new SigningTimeProperty();
							arg0.setSignerRole(new SignerRoleProperty().withClaimedRole(LCO_FE_Utils.SIGNER_OFE_SUPPLIER));
							arg0.setSigningTime(sigTime );    
						}


					})
					.withBasicSignatureOptions(new BasicSignatureOptions())
					.withAlgorithmsProviderEx(new AlgorithmsProviderEx() {
						@Override
						public Algorithm getSignatureAlgorithm(String string) throws UnsupportedAlgorithmException {
							// El algoritmo de firma debe ser SHA384withRSA como lo pide la DIAN
							return new GenericAlgorithm(LCO_FE_Utils.SIGNER_POLICY_RSASHA256);	// TODO signerPolicyHash384 Namespace sig error
						}
						@Override
						public Algorithm getCanonicalizationAlgorithmForSignature() {
							return new GenericAlgorithm(Canonicalizer.ALGO_ID_C14N_OMIT_COMMENTS);
						}
						@Override
						public Algorithm getCanonicalizationAlgorithmForTimeStampProperties() {
							return new GenericAlgorithm(Canonicalizer.ALGO_ID_C14N_OMIT_COMMENTS);
						}
						@Override
						public String getDigestAlgorithmForDataObjsReferences() {
							return MessageDigestAlgorithm.ALGO_ID_DIGEST_SHA256;	// TODO ALGO_ID_DIGEST_SHA384
						}
						@Override
						public String getDigestAlgorithmForReferenceProperties() {
							return MessageDigestAlgorithm.ALGO_ID_DIGEST_SHA256;
						}
						@Override
						public String getDigestAlgorithmForTimeStampProperties() {
							return MessageDigestAlgorithm.ALGO_ID_DIGEST_SHA256;
						}
					})
					// Requires
					// -Djavax.xml.ws.spi.Provider=com.sun.xml.ws.spi.ProviderImpl
					// -Djavax.xml.soap.SAAJMetaFactory=com.sun.xml.messaging.saaj.soap.SAAJMetaFactoryImpl
					.newSigner();

		} finally {
			if (!cl1.equals(Thread.currentThread().getContextClassLoader())) {
				Thread.currentThread().setContextClassLoader(cl1);
			}
		}

		// Firma el documento XML en la ubicacion donde lo pide la dian como enveloped
		try {
			signer.sign(new SignedDataObjects(new DataObjectDesc[] { dataObjRef }), elemToSign, SignatureAppendingStrategies.AsFirstChild);
		} catch (XAdES4jException e) {
			e.printStackTrace();
			System.err.println("Error creando archivo XML firmado. " + e.getMessage());
			throw new AdempiereException("Error creando archivo XML firmado. ");
		}

		// Guardamos la firma a un fichero en el path indicado
		String signed_file_name = m_Output_Directory + File.separator + m_Resource_To_Sign.substring(m_Resource_To_Sign.lastIndexOf(File.separator) + 1);

		// Crea el archivo de salida que va a tener el resultado (archivo XML firmado)
		OutputStream os = null;
		try {
			os = new FileOutputStream(signed_file_name);
		} catch (FileNotFoundException e) {
			throw new AdempiereException(e);
		}

		// Crea Transformer
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = null;
		try {
			t = tf.newTransformer();
		} catch (TransformerConfigurationException e) {
			os.close(); //cierra el archivo para no dejarlo abierto
			throw new AdempiereException(e);
		}

		// Procesar xml firmandolo y dejandolo en archivo de salida (os)
		try {
			t.transform(new DOMSource(doc), new StreamResult(os));
		} catch (TransformerException e) {
			os.close();
			throw new AdempiereException(e);
		}

		invoice.set_FE_FileSigned(signed_file_name);

	}

	/**
	 * <p>
	 * Devuelve el <code>Document</code> correspondiente al
	 * <code>resource</code> pasado como parámetro
	 * </p>
	 * 
	 * @param resource
	 *            El recurso que se desea obtener
	 * @return El <code>Document</code> asociado al <code>resource</code>
	 */
	public static Document getDocument(String resource) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try {
			doc = dbf.newDocumentBuilder().parse(new File(resource));
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AdempiereException(ex);
		}
		return doc;
	}

	public static Document getDocumentFromXml64Bytes(byte[] bytes) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try {
			doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(bytes));
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AdempiereException(ex);
		}
		return doc;
	}

	public static class FirstCertificateSelector implements SigningCertSelector {
		@Override
		public X509Certificate selectCertificate(List<X509Certificate> availableCertificates) {
			X509Certificate certificate = availableCertificates.get(0);
			System.out.println("Usando certificado: " + certificate.getIssuerDN().getName());
			return certificate;
		}
	}

	public static class DirectPasswordProvider implements KeyStoreKeyingDataProvider.KeyStorePasswordProvider, KeyStoreKeyingDataProvider.KeyEntryPasswordProvider {
		private char[] password;

		public DirectPasswordProvider(String password) {
			this.password = password.toCharArray();
		}

		@Override
		public char[] getPassword() {
			return password;
		}

		@Override
		public char[] getPassword(String entryAlias, X509Certificate entryCert) {
			return password;
		}
	}

	/* there is an exact copy in DIAN21_FE_UtilsSign_PHP */
	private String zipFSPUser(String file_name) {
		String backSlash = "\\";
        String Slash = "/";
        Map<String, String> env = new HashMap<>(); 
        env.put("create", "true");
        File file = new File(file_name);
        String file_zip = null;
        if (!file.getPath().contains(LCO_FE_Utils.DOCUMENTO_SOPORTE_ELECTRONICO))
        	file_zip = file.getPath().substring(0, file.getPath().lastIndexOf(File.separator)) + File.separator + "ws_" + file.getName().substring(5, 26) + "." + LCO_FE_Utils.RESOURCE_ZIP;
        else
        	file_zip = file.getPath().substring(0, file.getPath().lastIndexOf(File.separator)) + File.separator + "zz_" + file.getName().substring(3, 25) + "." + LCO_FE_Utils.RESOURCE_ZIP;
        file_zip = file_zip.replace(backSlash, Slash);
        // locate file system by using the syntax 
        // defined in java.net.JarURLConnection
        URI uri = URI.create("jar:file:" + file_zip);
        // FileSystem zipfs = null;
        try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
            Path externalTxtFile = Paths.get(file_name);
            Path pathInZipfile = zipfs.getPath(file.getName());
            // copy a file into the zip file
            Files.copy(externalTxtFile, pathInZipfile, StandardCopyOption.REPLACE_EXISTING );
            zipfs.close();
        } catch (IOException ioe) {
    	   	System.out.println(ioe.getLocalizedMessage());
    	   	throw new AdempiereException(ioe);
        }
        return file_zip;
	}

} // DIAN21_FE_UtilsSign
