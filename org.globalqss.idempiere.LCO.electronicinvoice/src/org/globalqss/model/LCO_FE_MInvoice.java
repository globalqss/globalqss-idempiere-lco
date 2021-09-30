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

package org.globalqss.model;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.Service;
import org.adempiere.base.ServiceQuery;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.model.MSysConfig;
import org.compiere.util.Env;
import org.compiere.util.Util;

public class LCO_FE_MInvoice extends MInvoice {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4902082513921928573L;

	public LCO_FE_MInvoice(Properties ctx, int C_Invoice_ID, String trxName) {
		super(ctx, C_Invoice_ID, trxName);
	}

	public String afterCompleteLCOFE() {

		// init values to be shared with posterior processes
		m_FolderRaiz = MSysConfig.getValue("QSSLCO_FE_RutaGeneracionXml", null, Env.getAD_Client_ID(Env.getCtx()));	// Segun SysConfig + Formato
		m_IsAttachXml = MSysConfig.getBooleanValue("QSSLCO_FE_DebugEnvioRecepcion", true, Env.getAD_Client_ID(Env.getCtx()));

		ServiceQuery query = new ServiceQuery();
		String techProvider = MSysConfig.getValue("QSS_LCO_ProveedorTecnologico", "dian21", getAD_Client_ID());
		query.put("proveedor", techProvider); // QSS_LCO_ProveedorTecnologico
		ILCO_FE_ProcessInvoice custom = Service.locator().locate(ILCO_FE_ProcessInvoice.class, query).getService();			
		if (custom == null)
			throw new AdempiereException("No ILCO_FE_ProcessInvoice provider found for technological provider " + techProvider);

		// attach the file to the authorization
		String msg = custom.getFile(this);
		if (! Util.isEmpty(msg))
			return msg;

		// sign and attach the signed file to the authorization
		msg = custom.signFile(this);
		if (! Util.isEmpty(msg))
			return msg;

		// send the file
		msg = custom.sendFile(this);

		return msg;
	}

	private String m_FileToSign;
	public String get_FE_FileToSign() {
		return m_FileToSign;
	}

	public void set_FE_FileToSign(String fileToSign) {
		m_FileToSign = fileToSign;
	}

	private String m_FileSigned;
	public String get_FE_FileSigned() {
		return m_FileSigned;
	}

	public void set_FE_FileSigned(String fileSigned) {
		m_FileSigned = fileSigned;
	}

	private String m_FileToSend;
	public String get_FE_FileToSend() {
		return m_FileToSend;
	}

	public void set_FE_FileToSend(String fileToSend) {
		m_FileToSend = fileToSend;
	}

	private String m_FolderRaiz;
	public String get_FE_FolderRaiz() {
		return m_FolderRaiz;
	}

	private boolean m_IsAttachXml;
	public void set_FE_IsAttachXml(boolean isAttachXml) {
		m_IsAttachXml = isAttachXml;
	}

	public boolean is_FE_AttachXml() {
		return m_IsAttachXml;
	}

	private boolean m_UseContingency;
	public void set_FE_UseContingency(boolean useContingency) {
		m_UseContingency = useContingency;
	}

	public boolean is_FE_UseContingency() {
		return m_UseContingency;
	}

	private boolean m_IsGenerateInBatch;
	public void set_FE_IsGenerateInBatch(boolean isGenerateInBatch) {
		m_IsGenerateInBatch = isGenerateInBatch;
	}

	public boolean is_FE_GenerateInBatch() {
		return m_IsGenerateInBatch;
	}

	private String m_FileType = "xml";
	public String get_FE_FileType() {
		return m_FileType;
	}

	public void set_FE_FileType(String fileType) {
		m_FileType = fileType;
	}

	public BigDecimal getGrandTotalPlusWithholdings() {
		BigDecimal withHoldings = (BigDecimal) get_Value("WithholdingAmt");
		if (withHoldings == null)
			withHoldings = Env.ZERO;
		return getGrandTotal().add(withHoldings);
	}

}	// LCO_FE_MInvoice
