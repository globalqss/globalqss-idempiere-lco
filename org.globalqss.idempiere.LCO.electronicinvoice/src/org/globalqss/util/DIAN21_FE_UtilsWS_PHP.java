package org.globalqss.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import org.compiere.util.Util;
import org.globalqss.model.LCO_FE_MInvoice;
import org.globalqss.model.X_LCO_FE_Authorization;
import org.globalqss.model.X_LCO_FE_DIAN_Format;

/**
 *	Utils for LCO FE Xml
 *
 *  @author Jesus Garcia - globalqss - Quality Systems & Solutions - http://globalqss.com
 *	@version $Id: LCO_FE_UtilsXml21.java,v 1.0 2013/05/27 23:01:26 cruiz Exp $
 */

public class DIAN21_FE_UtilsWS_PHP {

	/**	s_log			*/
	private static CLogger s_log = CLogger.getCLogger(DIAN21_FE_UtilsWS_PHP.class);

	private static String m_Output_Directory;
	private static String m_Resource_To_Send;

	public String sendFile(LCO_FE_MInvoice invoice) {
		String msg = null;

		m_Output_Directory = invoice.get_FE_FolderRaiz() + File.separator + LCO_FE_Utils.FOLDER_COMPROBANTES_TRANSMITIDOS;
		(new File(invoice.get_FE_FolderRaiz() + File.separator + LCO_FE_Utils.FOLDER_COMPROBANTES_TRANSMITIDOS + File.separator)).mkdirs();

		if (!invoice.is_FE_UseContingency() && !invoice.is_FE_GenerateInBatch()) {

			m_Resource_To_Send = invoice.get_FE_FileToSend();
			// Procesar Recepcion PTFE -- WS EnvioFacturaElectronica
			s_log.warning("@Sending@ -> " + m_Resource_To_Send);
			String pathScripts = MSysConfig.getValue("QSSLCO_FE_Path_PHP_Scripts", "", Env.getAD_Client_ID(Env.getCtx()));
			if (Util.isEmpty(pathScripts)) {
				throw new AdempiereException("Please configure SysConfig QSSLCO_FE_Path_PHP_Scripts");
			}
			String sendScript = MSysConfig.getValue("QSSLCO_FE_SendScript", "RequestSendBillSync.php", Env.getAD_Client_ID(Env.getCtx()));
			String suffix = sendScript.substring(7, sendScript.length()-4);

			String output_base_file = m_Output_Directory 
					+ File.separator 
					+ m_Resource_To_Send.substring(m_Resource_To_Send.lastIndexOf("/") + 1, m_Resource_To_Send.length() - 4)
					+ "_" + suffix;

			boolean isOnTesting = MSysConfig.getBooleanValue("QSSLCO_FE_EnPruebas", false, Env.getAD_Client_ID(Env.getCtx()));
			String[] command = new String[] {
					"php",
					pathScripts + File.separator + sendScript,
					Integer.toString(invoice.getAD_Client_ID()),
					isOnTesting ? LCO_FE_Utils.AMBIENTE_CERTIFICACION : LCO_FE_Utils.AMBIENTE_PRODUCCION,
					output_base_file,
					invoice.get_FE_FileToSend()
			};

			msg = LCO_FE_Utils.runCommand(command);

			if ("OK".equals(msg)) {
				msg = null;
				// out of trx
				X_LCO_FE_Authorization a = new X_LCO_FE_Authorization (invoice.getCtx(), invoice.get_ValueAsInt("LCO_FE_Authorization_ID"), null);
				if (invoice.is_FE_AttachXml()) {
					LCO_FE_Utils.attachFile(a.getCtx(), a.get_TrxName(), a.getLCO_FE_Authorization_ID(), output_base_file + "_request.xml", LCO_FE_Utils.RESOURCE_XML);
					LCO_FE_Utils.attachFile(a.getCtx(), a.get_TrxName(), a.getLCO_FE_Authorization_ID(), output_base_file + "_answer.xml", LCO_FE_Utils.RESOURCE_XML);
				}
				if ("RequestSendBillSync.php".equals(sendScript)) {
					LCO_FE_Utils.setErrorMsgFromFile_PHP(a, output_base_file + "_answer.xml");
					if (! a.isProcessed()) {
						msg = a.getErrorMsg();
					}
				}
			}
		}

		return msg;
	}

	public String getStatus(X_LCO_FE_Authorization auth) {
		
		String msg = null;
		
		if (auth.isProcessed())
			return null;

		String pathScripts = MSysConfig.getValue("QSSLCO_FE_Path_PHP_Scripts", "", Env.getAD_Client_ID(Env.getCtx()));
		if (Util.isEmpty(pathScripts)) {
			throw new AdempiereException("Please configure SysConfig QSSLCO_FE_Path_PHP_Scripts");
		}
		
		String folderRaiz = MSysConfig.getValue("QSSLCO_FE_RutaGeneracionXml", null, Env.getAD_Client_ID(Env.getCtx()));	// Segun SysConfig + Formato
		m_Output_Directory = folderRaiz + File.separator + LCO_FE_Utils.FOLDER_COMPROBANTES_AUTORIZADOS;
		(new File(folderRaiz + File.separator + LCO_FE_Utils.FOLDER_COMPROBANTES_AUTORIZADOS + File.separator)).mkdirs();
		
		MInvoice invoice = new MInvoice (auth.getCtx(), auth.getRecord_ID(), auth.get_TrxName());
		String  documentno = invoice.getDocumentNo().trim();
		if (! invoice.isProcessed()) {
			documentno = auth.getValue().trim().substring(auth.getValue().lastIndexOf("-")+1);
		}
		MDocType dt = MDocType.get(auth.getCtx(), invoice.getC_DocTypeTarget_ID());
		MSequence seq = new MSequence(auth.getCtx(), dt.getDefiniteSequence_ID(), auth.get_TrxName());
		String prefix = seq.getPrefix();
		documentno = (prefix != null ? documentno.replace(prefix, "") : documentno);
		
		// Emisor
		MOrgInfo oi = MOrgInfo.get(auth.getCtx(), invoice.getAD_Org_ID(), auth.get_TrxName());
		MBPartner bpe = new MBPartner(auth.getCtx(), oi.get_ValueAsInt("C_BPartner_ID"), auth.get_TrxName());
		
		// Formato
		X_LCO_FE_DIAN_Format df = new X_LCO_FE_DIAN_Format (auth.getCtx(), auth.getLCO_FE_DIAN_Format_ID(), auth.get_TrxName());
		
		String xmlFileName = DIAN21_FE_UtilsXML.constructFileName(bpe.getTaxID(), df.getValue(), documentno, df.getLCO_FE_EDIType(), true);
		
		String output_base_file = m_Output_Directory 
				+ File.separator
				+ xmlFileName.substring(0, xmlFileName.lastIndexOf("."))
				+ "_GetStatus";
		output_base_file = output_base_file.replace( "face_", "ws_");

		String statusScript = MSysConfig.getValue("QSSLCO_FE_StatusScript", "RequestGetStatus.php", Env.getAD_Client_ID(Env.getCtx()));
		
		boolean isOnTesting = MSysConfig.getBooleanValue("QSSLCO_FE_EnPruebas", false, Env.getAD_Client_ID(Env.getCtx()));
		String[] command = new String[] {
				"php",
				pathScripts + File.separator + statusScript,
				Integer.toString(auth.getAD_Client_ID()),
				isOnTesting ? LCO_FE_Utils.AMBIENTE_CERTIFICACION : LCO_FE_Utils.AMBIENTE_PRODUCCION,
				output_base_file,
				auth.getLCO_FE_Dian_Uuid()
		};

		msg = LCO_FE_Utils.runCommand(command);

		if ("OK".equals(msg)) {
			if (MSysConfig.getBooleanValue("QSSLCO_FE_DebugEnvioRecepcion", true, Env.getAD_Client_ID(Env.getCtx()))) {
				LCO_FE_Utils.attachFile(auth.getCtx(), auth.get_TrxName(), auth.getLCO_FE_Authorization_ID(), output_base_file + "_request.xml", LCO_FE_Utils.RESOURCE_XML);
				LCO_FE_Utils.attachFile(auth.getCtx(), auth.get_TrxName(), auth.getLCO_FE_Authorization_ID(), output_base_file + "_answer.xml", LCO_FE_Utils.RESOURCE_XML);
			}
			LCO_FE_Utils.setErrorMsgFromFile_PHP(auth, output_base_file + "_answer.xml");
		}

		return msg;
	}

	public String getNumberingRange(MOrg org) {

		String msg = null;

		String pathScripts = MSysConfig.getValue("QSSLCO_FE_Path_PHP_Scripts", "", Env.getAD_Client_ID(Env.getCtx()));
		if (Util.isEmpty(pathScripts)) {
			throw new AdempiereException("Please configure SysConfig QSSLCO_FE_Path_PHP_Scripts");
		}

		String folderRaiz = MSysConfig.getValue("QSSLCO_FE_RutaGeneracionXml", null, Env.getAD_Client_ID(Env.getCtx()));	// Segun SysConfig + Formato
		m_Output_Directory = folderRaiz + File.separator + LCO_FE_Utils.FOLDER_COMPROBANTES_TRANSMITIDOS;
		(new File(folderRaiz + File.separator + LCO_FE_Utils.FOLDER_COMPROBANTES_TRANSMITIDOS + File.separator)).mkdirs();

		// Emisor
		MOrgInfo oi = org.getInfo();

		String output_base_file = m_Output_Directory 
				+ File.separator
				+ "GetNumberingRange"
				+ new SimpleDateFormat("yyyyMMddHHmm").format(new Date());

		String numberingRangeScript = MSysConfig.getValue("QSSLCO_FE_NumberingRangeScript", "RequestGetNumberingRange.php", Env.getAD_Client_ID(Env.getCtx()));

		boolean isOnTesting = MSysConfig.getBooleanValue("QSSLCO_FE_EnPruebas", false, Env.getAD_Client_ID(Env.getCtx()));
		String[] command = new String[] {
				"php",
				pathScripts + File.separator + numberingRangeScript,
				Integer.toString(org.getAD_Client_ID()),
				isOnTesting ? LCO_FE_Utils.AMBIENTE_CERTIFICACION : LCO_FE_Utils.AMBIENTE_PRODUCCION,
				output_base_file,
				oi.get_ValueAsString("LCO_FE_IdCompany"),
				oi.get_ValueAsString("LCO_FE_IdCompany"),
				oi.get_ValueAsString("LCO_FE_UserName")
		};

		msg = LCO_FE_Utils.runCommand(command);

		if ("OK".equals(msg)) {
			if (MSysConfig.getBooleanValue("QSSLCO_FE_DebugEnvioRecepcion", true, Env.getAD_Client_ID(Env.getCtx()))) {
				LCO_FE_Utils.attachFileToOrg(org, output_base_file + "_request.xml", LCO_FE_Utils.RESOURCE_XML);
				LCO_FE_Utils.attachFileToOrg(org, output_base_file + "_answer.xml", LCO_FE_Utils.RESOURCE_XML);
			}
			// TODO: create LCO_PrintedFormControl records based on the answer
		}

		return msg;
	}

}	// DIAN21_FE_UtilsWS_PHP
