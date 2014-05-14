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


import org.compiere.apps.ADialog;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.globalqss.model.LEC_FE_MInvoice;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.Env;


/**
 *	Validator or Localization Colombia (Withholdings)
 *	
 *  @author Carlos Ruiz - globalqss - Quality Systems & Solutions - http://globalqss.com 
 *	@version $Id: LCO_Validator.java,v 1.4 2007/05/13 06:53:26 cruiz Exp $
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
	private static CLogger log = CLogger.getCLogger(LCO_Validator.class);
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
		//engine.addModelChange(MInvoice.Table_Name, this);

		//	Documents to be monitored
		engine.addDocValidate(MInvoice.Table_Name, this);

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

		if (po.get_TableName().equals(MInvoice.Table_Name) && type == ModelValidator.TYPE_BEFORE_CHANGE) {
			msg = dummy((MInvoice) po);
			if (msg != null)
				return msg;
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

		// after completing invoice process electronic invoice
		if (po.get_TableName().equals(MInvoice.Table_Name) && timing == TIMING_AFTER_COMPLETE) {
			MInvoice invoice = (MInvoice)po;
			if (invoice.isSOTrx()) {
				//
				msg = invoiceGenerateXml(invoice);
				if (msg != null)
					return msg;
				//
				msg = invoiceSignXml(invoice);
				if (msg != null)
					return msg;
				//
				msg = invoiceAuthoriseXml(invoice);
				if (msg != null)
					return msg;
			}
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
	
	

	private String dummy (MInvoice inv) {
		
		return null;
	}
	
	private String invoiceGenerateXml (MInvoice inv) {
		
		String msg = "Generando XML";
		log.info("Invoice: " + inv);
		if (!ADialog.ask(0, null, msg)) {
			return "Canceled...";
		}
		
		LEC_FE_MInvoice lecfeinv = new LEC_FE_MInvoice(inv.getCtx(), inv.getC_Invoice_ID(), inv.get_TrxName());
		msg = lecfeinv.lecfeinv_SriExportInvoiceXML100();
		if (msg != null)
			return msg;

		return null;
	}
	
	private String invoiceSignXml (MInvoice inv) {
		
		String msg = "Firmando XML";
		log.info("Invoice: " + inv);
		if (!ADialog.ask(0, null, msg)) {
			return "Canceled...";
		}
		
		return null;
	}
	
	private String invoiceAuthoriseXml (MInvoice inv) {
		
		String msg = "Autorizando XML";
		log.info("Invoice: " + inv);
		if (!ADialog.ask(0, null, msg)) {
			return "Canceled...";
		}
		

		//return "Simulando...";	// Rollback
		return null; //	Ok
	}
	
	
}	//	LEC_FE_Validator