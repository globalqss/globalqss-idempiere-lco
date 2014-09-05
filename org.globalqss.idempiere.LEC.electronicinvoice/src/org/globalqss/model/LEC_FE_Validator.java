/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.globalqss.model;


import java.io.File;

import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.globalqss.model.LEC_FE_MInvoice;
import org.globalqss.util.LEC_FE_Utils;
import org.compiere.model.MAttachment;
import org.compiere.model.MAttachmentEntry;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTable;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;


/**
 *	Validator or Localization Ecuador (Facturacion Electronica)
 *	
 *  @author Carlos Ruiz - globalqss - Quality Systems & Solutions - http://globalqss.com 
 *	@version $Id: LEC_FE_Validator.java,v 1.4 2007/05/13 06:53:26 cruiz Exp $
 */
public class LEC_FE_Validator implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public LEC_FE_Validator ()
	{
		super ();
	}	//	MyValidator
	
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(LEC_FE_Validator.class);
	/** Client			*/
	private int		m_AD_Client_ID = -1;
	
	/**
	 *	Initialize Validation
	 *	@param engine validation engine 
	 *	@param client client
	 */
	public void initialize (ModelValidationEngine engine, MClient client)
	{
		//client = null for global validator
		if (client != null) {	
			m_AD_Client_ID = client.getAD_Client_ID();
			log.info(client.toString());
		}
		else  {
			log.info("Initializing global validator: "+this.toString());
		}

		//	Tables to be monitored
		engine.addModelChange(MInvoice.Table_Name, this);
		engine.addModelChange(MInOut.Table_Name, this);

		//	Documents to be monitored
		engine.addDocValidate(MInvoice.Table_Name, this);
		engine.addDocValidate(MInOut.Table_Name, this);

	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	Called after PO.beforeSave/PO.beforeDelete
     *	when you called addModelChange for the table
     *	@param po persistent object
     *	@param type TYPE_
     *	@return error message or null
     *	@exception Exception if the recipient wishes the change to be not accept.
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		String msg;

		if (po.get_TableName().equals(MInOut.Table_Name) && type == ModelValidator.TYPE_BEFORE_NEW) {
			MInOut inout = ((MInOut) po);
			MDocType dt = new MDocType(inout.getCtx(), inout.getC_DocType_ID(), inout.get_TrxName());
			if ( MSysConfig.getBooleanValue("QSSLEC_FE_UseClaveContingenciaVentaMostradoryFolio", false, inout.getAD_Client_ID())
				&& inout.isSOTrx() && inout.getC_Order_ID() > 0
				// && MDocType.DOCSUBTYPESO_OnCreditOrder.equals(inout.getC_Order().getC_DocType().getDocSubTypeSO())	// (W)illCall(I)nvoice
				&& dt.get_Value("SRI_ShortDocType") != null ) {
					inout.set_ValueOfColumn("SRI_IsUseContingency", "Y");
			}
		}
		
		if (po.get_TableName().equals(MInvoice.Table_Name) && type == ModelValidator.TYPE_BEFORE_NEW) {
			MInvoice invoice = ((MInvoice) po);
			MDocType dt = new MDocType(invoice.getCtx(), invoice.getC_DocTypeTarget_ID(), invoice.get_TrxName());
			if ( MSysConfig.getBooleanValue("QSSLEC_FE_UseClaveContingenciaVentaMostradoryFolio", false, invoice.getAD_Client_ID())
				&& invoice.isSOTrx() && invoice.getC_Order_ID() > 0
				// && MDocType.DOCSUBTYPESO_OnCreditOrder.equals(invoice.getC_Order().getC_DocType().getDocSubTypeSO())	// (W)illCall(I)nvoice
				&& dt.get_Value("SRI_ShortDocType") != null ) {
					invoice.set_ValueOfColumn("SRI_IsUseContingency", "Y");
			}
		}

		return null;
	}	//	modelChange
	

	/**
	 *	Validate Document.
	 *	Called as first step of DocAction.prepareIt 
     *	when you called addDocValidate for the table.
     *	Note that totals, etc. may not be correct.
	 *	@param po persistent object
	 *	@param timing see TIMING_ constants
     *	@return error message or null
	 */
	public String docValidate (PO po, int timing)
	{
		log.info(po.get_TableName() + " Timing: "+timing);
		String msg;

		// before completing SO invoice set SO DocumentNo -- Previene custom e-evolution Morder.completeIt
		if (po.get_TableName().equals(MInvoice.Table_Name) && timing == TIMING_BEFORE_COMPLETE) {
			MInvoice invoice = (MInvoice)po;
			if (MDocType.DOCSUBTYPESO_OnCreditOrder.equals(invoice.getC_Order().getC_DocType().getDocSubTypeSO())) {	// (W)illCall(I)nvoice
				invoice.setDocumentNo(invoice.getC_Order().getDocumentNo());
				invoice.saveEx();
			}
		}
		
		// after completing SO invoice process electronic invoice
		if (po.get_TableName().equals(MInvoice.Table_Name) && timing == TIMING_AFTER_COMPLETE) {
			MInvoice invoice = (MInvoice)po;
			//
			msg = invoiceGenerateXml(invoice);
			if (msg != null)
				return msg;
		}
		
		// after completing SO inout process electronic inout
		if (po.get_TableName().equals(MInOut.Table_Name) && timing == TIMING_AFTER_COMPLETE) {
			MInOut inout = (MInOut)po;
			//
			msg = inoutGenerateXml(inout);
			if (msg != null)
				return msg;
		}


		return null;
	}	//	docValidate


	/**
	 *	User Login.
	 *	Called when preferences are set
	 *	@param AD_Org_ID org
	 *	@param AD_Role_ID role
	 *	@param AD_User_ID user
	 *	@return error message or null
	 */
	public String login (int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		log.info("AD_User_ID=" + AD_User_ID);
		return null;
	}	//	login

	
	/**
	 *	Get Client to be monitored
	 *	@return AD_Client_ID client
	 */
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}	//	getAD_Client_ID

	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("LEC_FE_Validator");
		return sb.toString ();
	}	//	toString
	
	

	private String invoiceGenerateXml (MInvoice inv) {
		
		String msg = null;
		
		MDocType dt = new MDocType(inv.getCtx(), inv.getC_DocTypeTarget_ID(), inv.get_TrxName());
		
		String shortdoctype = dt.get_ValueAsString("SRI_ShortDocType");
		
		if ( shortdoctype.equals("")) {
			msg = "No existe definicion SRI_ShortDocType: " + dt.toString();
			log.info("Invoice: " + inv.toString() + msg);
			
			// if (LEC_FE_Utils.breakDialog(msg)) return "Cancelado...";	// Temp
		
		}
		
		MUser user = new MUser(inv.getCtx(), inv.getAD_User_ID(), inv.get_TrxName());
			
		if (! valideUserMail (user)) {
			msg = "@RequestActionEMailNoTo@";
			return msg;
		}
		
		msg = null;
		LEC_FE_MInvoice lecfeinv = new LEC_FE_MInvoice(inv.getCtx(), inv.getC_Invoice_ID(), inv.get_TrxName());
		LEC_FE_MNotaCredito lecfeinvnc = new LEC_FE_MNotaCredito(inv.getCtx(), inv.getC_Invoice_ID(), inv.get_TrxName());
		LEC_FE_MNotaDebito lecfeinvnd = new LEC_FE_MNotaDebito(inv.getCtx(), inv.getC_Invoice_ID(), inv.get_TrxName());
		LEC_FE_MRetencion lecfeinvret = new LEC_FE_MRetencion(inv.getCtx(), inv.getC_Invoice_ID(), inv.get_TrxName());
		// isSOTrx()
		if (shortdoctype.equals("01"))	// FACTURA
			msg = lecfeinv.lecfeinv_SriExportInvoiceXML100();
		else if (shortdoctype.equals("04"))	// NOTA DE CRÉDITO
			msg = lecfeinvnc.lecfeinvnc_SriExportNotaCreditoXML100();
		else if (shortdoctype.equals("05"))	// NOTA DE DÉBITO
			msg = lecfeinvnd.lecfeinvnd_SriExportNotaDebitoXML100();
		// !isSOTrx()
		else if (shortdoctype.equals("07"))	// COMPROBANTE DE RETENCIÓN
			msg = lecfeinvret.lecfeinvret_SriExportRetencionXML100();
		else
			log.warning("Formato no habilitado SRI: " + dt.toString() + shortdoctype);
			
		return msg;
	}
	
	private String inoutGenerateXml (MInOut inout) {
		
		String msg = null;
		
		MDocType dt = new MDocType(inout.getCtx(), inout.getC_DocType_ID(), inout.get_TrxName());
		
		String shortdoctype = dt.get_ValueAsString("SRI_ShortDocType");
		
		if ( shortdoctype.equals("")) {
			msg = "No existe definicion SRI_ShortDocType: " + dt.toString();
			log.info("Invoice: " + inout.toString() + msg);
			
			// if (LEC_FE_Utils.breakDialog(msg)) return "Cancelado...";	// Temp
		}
		
		MUser user = new MUser(inout.getCtx(), inout.getAD_User_ID(), inout.get_TrxName());
		
		if (! valideUserMail (user)) {
			msg = "@RequestActionEMailNoTo@";
			return msg;
		}
		
		msg = null;
		LEC_FE_MInOut lecfeinout = new LEC_FE_MInOut(inout.getCtx(), inout.getM_InOut_ID(), inout.get_TrxName());
		// isSOTrx()
		if (shortdoctype.equals("06"))	// GUÍA DE REMISIÓN 
			msg = lecfeinout.lecfeinout_SriExportInOutXML100();
		else
			log.warning("Formato no habilitado SRI: " + dt.toString() + shortdoctype);
			
		return msg;
	}

	
	/**
	 * 	valideOrgInfoSri
	 *	@param MOrgInfo orginfo
	 *	@return error message or null
	 */
	public static String valideOrgInfoSri (MOrgInfo orginfo)
	{
		
		String msg = null;
		
		int c_location_matriz_id = MSysConfig.getIntValue("QSSLEC_FE_LocalizacionDireccionMatriz", -1, orginfo.getAD_Client_ID());
		
		if ( c_location_matriz_id < 1)
			msg = "No existe parametro para LocalizacionDireccionMatriz";
		
		String clavecert = MSysConfig.getValue("QSSLEC_FE_ClaveCertificadoDigital", null, orginfo.getAD_Client_ID(), orginfo.getAD_Org_ID());
		
		if (clavecert == null)
			msg = "No existe parametro para ClaveCertificadoDigital";
		
		String rutacert = MSysConfig.getValue("QSSLEC_FE_RutaCertificadoDigital", null, orginfo.getAD_Client_ID(), orginfo.getAD_Org_ID());
			// msg = "No existe parametro para RutaCertificadoDigital";
		
		if (rutacert != null) {
			File folder = new File(rutacert);
			
			if (!folder.exists() || !folder.isFile() || !folder.canRead())
				msg = "No existe o no se puede leer el archivo de Certificado Digital";
			
		}
		else {
			// Obtencion del certificado para firmar. Utilizando un attachment - AD_Org
			boolean isattachcert = false;
			MAttachment attach =  MAttachment.get(orginfo.getCtx(), MTable.getTable_ID("AD_Org"), orginfo.getAD_Org_ID());
			if (attach != null) {
	    		for (MAttachmentEntry entry : attach.getEntries()) {
	            	if (entry.getName().endsWith("p12") || entry.getName().endsWith("pfx"))
	            		isattachcert = true;
	            }
			}
			if (! isattachcert)
				msg = "No existe parametro o adjunto de Certificado Digital";
		}

		int c_bpartner_id = LEC_FE_Utils.getOrgBPartner(orginfo.getAD_Client_ID(), orginfo.get_ValueAsString("TaxID"));
		
		if (c_bpartner_id < 1)
			msg = "No existe BP relacionado a OrgInfo.Documento: " + orginfo.get_ValueAsString("TaxID");
		else if (orginfo.get_ValueAsString("TaxID").equals(""))
			msg = "No existe definicion OrgInfo.Documento: " + orginfo.toString();
		else if (orginfo.get_ValueAsString("SRI_DocumentCode").equals(""))
			msg = "No existe definicion OrgInfo.DocumentCode: " + orginfo.toString();
		else if (orginfo.get_ValueAsString("SRI_OrgCode").equals(""))
			msg = "No existe definicion OrgInfo.SRI_OrgCode: " + orginfo.toString();
		//else if (orginfo.get_ValueAsString("SRI_StoreCode").equals(""))
		//	msg = "No existe definicion OrgInfo.SRI_StoreCode: " + orginfo.toString();
		else if (orginfo.get_ValueAsString("SRI_DocumentCode").equals(""))
			msg = "No existe definicion OrgInfo.SRI_DocumentCode: " + orginfo.toString();
		else if (orginfo.get_ValueAsString("SRI_IsKeepAccounting").equals(""))
			msg = "No existe definicion OrgInfo.SRI_IsKeepAccounting: " + orginfo.toString();
		else if (orginfo.getC_Location_ID() == 0)
			msg = "No existe definicion OrgInfo.Address1: " + orginfo.toString();
		else {
			MBPartner bpe = new MBPartner(orginfo.getCtx(), c_bpartner_id, orginfo.get_TrxName());
			if ( (Integer) bpe.get_Value("LCO_TaxPayerType_ID") == 1000027)	// Hardcoded
				if (orginfo.get_ValueAsString("SRI_TaxPayerCode").equals(""))
					msg = "No existe definicion OrgInfo.SRI_TaxPayerCode: " + orginfo.toString();
				;
		}

		return msg;
		
	}	//	valideOrgInfoSri
	
	public static boolean valideUserMail (MUser user)
	{
		if (MSysConfig.getBooleanValue("QSSLEC_FE_EnvioXmlAutorizadoBPEmail", false, user.getAD_Client_ID())) {
			
			if ((user.get_ID() == 0 || user.isNotificationEMail() && (user.getEMail() == null || user.getEMail().length() == 0))) {
				return false;
			}
		}
		
		return true;
	
	}	//	valideUserMail
	
	
}	//	LEC_FE_Validator