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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.xml.bind.JAXBElement;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MSequence;
import org.compiere.model.MSysConfig;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.datacontract.schemas._2004._07.dianresponse.DianResponse;
import org.datacontract.schemas._2004._07.numberrangeresponse.ArrayOfNumberRangeResponse;
import org.datacontract.schemas._2004._07.numberrangeresponse.NumberRangeResponse;
import org.datacontract.schemas._2004._07.numberrangeresponselist.NumberRangeResponseList;
import org.globalqss.fedian.client.api.ServiceClientUtil;
import org.globalqss.model.LCO_FE_MInvoice;
import org.globalqss.model.MLCOFEAuthorization;
import org.globalqss.model.X_LCO_FE_DIAN_Format;

/**
 *	Utils for LCO FE Xml
 *
 *  @author Jesus Garcia - globalqss - Quality Systems & Solutions - http://globalqss.com
 *	@version $Id: LCO_FE_UtilsXml21.java,v 1.0 2013/05/27 23:01:26 cruiz Exp $
 */

public class DIAN21_FE_UtilsWS {

	/**	s_log			*/
	private static CLogger s_log = CLogger.getCLogger(DIAN21_FE_UtilsWS.class);
	
	private static String m_Output_Directory;

	public String sendFile(LCO_FE_MInvoice invoice) {
		String msg = null;

		if (!invoice.is_FE_UseContingency() && !invoice.is_FE_GenerateInBatch()) {
			String fileName = invoice.get_FE_FileToSend();
			s_log.warning("@Sending@ -> " + fileName);
			DianResponse response;
			try {
				Path path = Paths.get(fileName);
				response = ServiceClientUtil.sendFile(path.getFileName().toString(), Files.readAllBytes(path));
			} catch (IOException e) {
				throw new AdempiereException(e);
			}
			
			String output_base_file = fileName.replace(LCO_FE_Utils.FOLDER_COMPROBANTES_FIRMADOS,LCO_FE_Utils.FOLDER_COMPROBANTES_AUTORIZADOS);
			output_base_file = output_base_file.replace("ws_", "face_");
			output_base_file = output_base_file.replace("zz_", LCO_FE_Utils.DOCUMENTO_SOPORTE_ELECTRONICO + "_");
			output_base_file = output_base_file.replace(".zip", "_response.xml");
			
			try	{
				Files.write(Paths.get(output_base_file), response.getXmlBase64Bytes().getValue(), StandardOpenOption.CREATE);
			} catch (Exception ex) {
				s_log.severe("Could not write " + output_base_file + " -> " + ex.getLocalizedMessage());
				ex.printStackTrace();
			}
			// out of trx
			MLCOFEAuthorization auth = new MLCOFEAuthorization (invoice.getCtx(), invoice.get_ValueAsInt("LCO_FE_Authorization_ID"), null);
			LCO_FE_Utils.setErrorMsgFromDianResponse(auth, response);
			if (auth.isProcessed()) {
				msg = null;
			} else {
				msg = auth.getErrorMsg();
			}
			//
			if (MSysConfig.getBooleanValue("QSSLCO_FE_DebugEnvioRecepcion", true, Env.getAD_Client_ID(Env.getCtx()))) {
				LCO_FE_Utils.attachFile(auth.getCtx(), auth.get_TrxName(), auth.getLCO_FE_Authorization_ID(), output_base_file, LCO_FE_Utils.RESOURCE_XML);
			}

		}

		return msg;
	}

	public String getStatus(MLCOFEAuthorization auth) {
		if (auth.isProcessed())
			return null;
		
		String folderRaiz = MSysConfig.getValue("QSSLCO_FE_RutaGeneracionXml", null, Env.getAD_Client_ID(Env.getCtx()));	// Segun SysConfig + Formato
		m_Output_Directory = folderRaiz + File.separator + LCO_FE_Utils.FOLDER_COMPROBANTES_AUTORIZADOS;
		(new File(folderRaiz + File.separator + LCO_FE_Utils.FOLDER_COMPROBANTES_AUTORIZADOS + File.separator)).mkdirs();
		
		MInvoice invoice = new MInvoice (auth.getCtx(), auth.getRecord_ID(), auth.get_TrxName());
		String documentno = invoice.getDocumentNo().trim();
		if (! invoice.isProcessed()) {
			documentno = auth.getValue().trim().substring(auth.getValue().lastIndexOf("-")+1);
		}
		MDocType dt = MDocType.get(auth.getCtx(), invoice.getC_DocTypeTarget_ID());
		MSequence seq = new MSequence(auth.getCtx(), dt.getDefiniteSequence_ID(), auth.get_TrxName());
		String prefix = seq.getPrefix();
		documentno = (prefix != null ? documentno.replace(prefix, "") : documentno);
		
		MOrgInfo oi = MOrgInfo.get(auth.getCtx(), invoice.getAD_Org_ID(), auth.get_TrxName());
		MBPartner bpe = new MBPartner(auth.getCtx(), oi.get_ValueAsInt("C_BPartner_ID"), auth.get_TrxName());
		
		// Formato
		X_LCO_FE_DIAN_Format df = new X_LCO_FE_DIAN_Format (auth.getCtx(), auth.getLCO_FE_DIAN_Format_ID(), auth.get_TrxName());

		String xmlFileName = DIAN21_FE_UtilsXML.constructFileName(bpe.getTaxID(), df.getValue(), documentno, df.getLCO_FE_EDIType(), true);
		
		String output_base_file = m_Output_Directory 
				+ File.separator
				+ xmlFileName.substring(0, xmlFileName.lastIndexOf("."))
				+ "_response.xml";
	
		DianResponse response = ServiceClientUtil.getStatus(auth.getLCO_FE_Dian_Uuid());
		
		if (LCO_FE_Utils.STATUS_CODE_PROCESADO.equals(response.getStatusCode().getValue()) && response.isIsValid()) {
			try	{
				Files.write(Paths.get(output_base_file), response.getXmlBase64Bytes().getValue(), StandardOpenOption.CREATE);
			} catch (Exception ex) {
		        ;	// Bypass
			}
			if (MSysConfig.getBooleanValue("QSSLCO_FE_DebugEnvioRecepcion", true, Env.getAD_Client_ID(Env.getCtx()))) {
				LCO_FE_Utils.attachFile(auth.getCtx(), auth.get_TrxName(), auth.getLCO_FE_Authorization_ID(), output_base_file, LCO_FE_Utils.RESOURCE_XML);
			}
		}
		String msg = LCO_FE_Utils.setErrorMsgFromDianResponse(auth, response);
		return msg;
	}

	public String getNumberingRange(MOrg org) {
		String folderRaiz = MSysConfig.getValue("QSSLCO_FE_RutaGeneracionXml", null, Env.getAD_Client_ID(Env.getCtx()));	// Segun SysConfig + Formato
		m_Output_Directory = folderRaiz + File.separator + LCO_FE_Utils.FOLDER_COMPROBANTES_AUTORIZADOS;
		(new File(folderRaiz + File.separator + LCO_FE_Utils.FOLDER_COMPROBANTES_AUTORIZADOS + File.separator)).mkdirs();

		// Emisor
		MOrgInfo oi = org.getInfo();

		NumberRangeResponseList response = ServiceClientUtil.getNumberingRange(oi.get_ValueAsString("LCO_FE_IdCompany"), oi.get_ValueAsString("LCO_FE_IdCompany"), oi.get_ValueAsString("LCO_FE_UserName"));

		StringBuilder msg = new StringBuilder();
		msg.append("OperationCode = ").append(response.getOperationCode().getValue()).append(" / ");
		msg.append("OperationDescription = ").append(response.getOperationDescription().getValue()).append(" / ");
		msg.append("ResponseList =");
		JAXBElement<ArrayOfNumberRangeResponse> jlist = response.getResponseList();
		if (jlist.isNil()) {
			msg.append("null");
		} else {
			msg.append(" / ");
			boolean addComma = false;
			for (NumberRangeResponse nrr : jlist.getValue().getNumberRangeResponse()) {
				// TODO: create LCO_PrintedFormControl records based on the answer
				if (addComma)
					msg.append(", / ");
				msg.append("[ / ");
				msg.append("  ResolutionNumber = ").append(nrr.getResolutionNumber().getValue()).append(" / ");
				msg.append("  ResolutionDate = ").append(nrr.getResolutionDate().getValue()).append(" / ");
				msg.append("  ValidDateFrom = ").append(nrr.getValidDateFrom().getValue()).append(" / ");
				msg.append("  ValidDateTo = ").append(nrr.getValidDateTo().getValue()).append(" / ");
				msg.append("  FromNumber = ").append(nrr.getFromNumber().longValue()).append(" / ");
				msg.append("  ToNumber = ").append(nrr.getToNumber().longValue()).append(" / ");
				msg.append("  Prefix = ").append(nrr.getPrefix().getValue()).append(" / ");
				msg.append("  TechnicalKey = ").append(nrr.getTechnicalKey().getValue()).append(" / ");
				msg.append("]");
				addComma = true;
			}
		}
		
		return msg.toString();
	}

}	// DIAN21_FE_UtilsWS
