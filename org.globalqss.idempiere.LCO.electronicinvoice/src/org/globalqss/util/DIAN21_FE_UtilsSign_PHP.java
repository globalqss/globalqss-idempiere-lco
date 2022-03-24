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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MSysConfig;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.globalqss.model.LCO_FE_MInvoice;
import org.globalqss.model.MLCOFEAuthorization;
import org.globalqss.model.X_LCO_FE_DocType;

public class DIAN21_FE_UtilsSign_PHP {

	/**	s_log			*/
	private static CLogger s_log = CLogger.getCLogger(DIAN21_FE_UtilsSign_PHP.class);

	private static String m_Output_Directory;
	private static String m_Resource_To_Sign;

	public String signFile(LCO_FE_MInvoice invoice) {
		String msg = null;

		String pathScripts = MSysConfig.getValue("QSSLCO_FE_Path_PHP_Scripts", "", Env.getAD_Client_ID(Env.getCtx()));
		if (Util.isEmpty(pathScripts)) {
			throw new AdempiereException("Please configure SysConfig QSSLCO_FE_Path_PHP_Scripts");
		}

		m_Resource_To_Sign = invoice.get_FE_FileToSign();
		s_log.warning("@Signing Xml@ -> " + m_Resource_To_Sign);

		String folderRaiz = MSysConfig.getValue("QSSLCO_FE_RutaGeneracionXml", null, Env.getAD_Client_ID(Env.getCtx()));	// Segun SysConfig + Formato
		m_Output_Directory = invoice.get_FE_FolderRaiz() + File.separator + LCO_FE_Utils.FOLDER_COMPROBANTES_FIRMADOS;
		(new File(folderRaiz + File.separator + LCO_FE_Utils.FOLDER_COMPROBANTES_FIRMADOS + File.separator)).mkdirs();

		// Guardamos la firma a un fichero en el path indicado
		String signed_file_name = m_Output_Directory + File.separator + m_Resource_To_Sign.substring(m_Resource_To_Sign.lastIndexOf(File.separator) + 1);

		MDocType dt = new MDocType(invoice.getCtx(), invoice.getC_DocTypeTarget_ID(), invoice.get_TrxName());
		X_LCO_FE_DocType fedt = new X_LCO_FE_DocType (invoice.getCtx(), dt.get_ValueAsInt("LCO_FE_DocType_ID"), invoice.get_TrxName());
		String dianShortDocType = fedt.getDianShortDocType();

		String signScript = MSysConfig.getValue("QSSLCO_FE_SignScript", "CallSignInvoice.php", Env.getAD_Client_ID(Env.getCtx()));

		String[] command = new String[] {
				"php",
				pathScripts + File.separator + signScript,
				Integer.toString(invoice.getAD_Client_ID()),
				dianShortDocType,
				m_Resource_To_Sign,
				signed_file_name
		};

		msg = LCO_FE_Utils.runCommand(command);

		if ("OK".equals(msg)) {
			invoice.set_FE_FileSigned(signed_file_name);
			msg = null;

			// out of Trx to save auth and attachments independently
			MLCOFEAuthorization a = new MLCOFEAuthorization (invoice.getCtx(), invoice.get_ValueAsInt("LCO_FE_Authorization_ID"), null);

			// Attach XML Firmado
			try {
				Files.copy(Paths.get(signed_file_name), Paths.get(signed_file_name.replace(".xml", "_signed.xml")), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new AdempiereException(e);
			}
			if (invoice.is_FE_AttachXml())
				LCO_FE_Utils.attachFile(a.getCtx(), a.get_TrxName(), a.getLCO_FE_Authorization_ID(), signed_file_name.replace(".xml", "_signed.xml"), LCO_FE_Utils.RESOURCE_XML);
			
			// Prepare the file to send via webservices
	        String file_zip = zipFSPUser(signed_file_name);
			invoice.set_FE_FileToSend(file_zip);
			if (invoice.is_FE_AttachXml())
				LCO_FE_Utils.attachFile(a.getCtx(), a.get_TrxName(), a.getLCO_FE_Authorization_ID(), file_zip, LCO_FE_Utils.RESOURCE_ZIP);
		}

		return msg;
	}

	/* copy from DIAN21_FE_UtilsSign */
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

} // DIAN21_FE_UtilsSign_PHP
