/**********************************************************************
* This file is part of Adempiere ERP Bazaar                           *
* http://www.adempiere.org                                            *
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
* - Jesus Garcia - GlobalQSS Colombia                                 *
* - Carlos Ruiz  - GlobalQSS Colombia                                 *
**********************************************************************/
package org.globalqss.process;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MInOut;
import org.compiere.model.MInvoice;
import org.compiere.model.MMailText;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MSysConfig;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;

import org.globalqss.model.X_LEC_SRI_Format;
import org.globalqss.model.X_SRI_AccessCode;
import org.globalqss.model.X_SRI_Authorisation;
import org.globalqss.util.LEC_FE_Utils;
import org.globalqss.util.LEC_FE_UtilsXml;

/**
 *	Generate Contingency Authorisations
 *	
 *  @author GlobalQSS/jjgq
 */
public class SRIEmailAuthorisation extends SvrProcess
{

	/**	Client							*/
	private int				m_AD_Client_ID = 0;
	
	/** Authorisation					*/
	private int			p_SRI_Authorisation_ID = 0;
	
	/** Number of authorisations	*/
	private int			m_created = 0;
	
	private int			m_lec_sri_format_id = 0;
	
	private String file_name = "";
	private String m_retencionno = "";

	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("SRI_Authorisation_ID"))
				p_SRI_Authorisation_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		
		m_AD_Client_ID = getAD_Client_ID();
		
		if (p_SRI_Authorisation_ID == 0)
			p_SRI_Authorisation_ID = getRecord_ID();

	}	//	prepare

	/**
	 * 	Generate Invoices
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		
		log.info("SRI_Authorisation_ID=" + p_SRI_Authorisation_ID);
		//
		String sql = null;
		sql = "SELECT * FROM SRI_Authorisation a "
			+ " WHERE AD_Client_ID=?"
			+ "  AND SRI_AuthorisationCode IS NOT NULL"
			+ "  AND IsActive = 'Y' AND Processed = 'Y'";
		//	+ "  AND SUBSTR(Value,48,1)=? ";
		if (p_SRI_Authorisation_ID != 0)
			sql += " AND SRI_Authorisation_ID=?";

		//	sql += " FOR UPDATE";
		
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			int index = 1;
			pstmt.setInt(index++, m_AD_Client_ID);
			// pstmt.setString(index++, LEC_FE_UtilsXml.emisionNormal);
			// pstmt.setString(index++, LEC_FE_UtilsXml.emisionContingencia);
			if (p_SRI_Authorisation_ID != 0)
				pstmt.setInt(index++, p_SRI_Authorisation_ID);

		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		
		return generate(pstmt);
	
	}	//	doIt
	
	
	/**
	 * 	Generate Authorisations
	 * 	@param pstmt order query 
	 *	@return info
	 */
	private String generate (PreparedStatement pstmt)
	{
		
		try
		{
			
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				
				String msg = null;
				
				X_SRI_Authorisation authorisation = new X_SRI_Authorisation (getCtx(), rs, get_TrxName());
				
				// isSOTrx()
				if (authorisation.getSRI_ShortDocType().equals("01"))	// FACTURA
					msg = lecfeinvoice_SriExportInvoiceXML100(authorisation);
				else if (authorisation.getSRI_ShortDocType().equals("04"))	// NOTA DE CRÉDITO
					msg = lecfeinvoice_SriExportInvoiceXML100(authorisation);
				else if (authorisation.getSRI_ShortDocType().equals("05"))	// NOTA DE DÉBITO
					msg = lecfeinvoice_SriExportInvoiceXML100(authorisation);
				else if (authorisation.getSRI_ShortDocType().equals("06"))	// GUÍA DE REMISIÓN 
					msg = lecfeinout_SriExportInOutXML100(authorisation);
				// !isSOTrx()
				else if (authorisation.getSRI_ShortDocType().equals("07"))	// COMPROBANTE DE RETENCIÓN
					msg = lecfeinvoice_SriExportInvoiceXML100(authorisation);
				else
					log.warning("Formato no habilitado SRI: " + authorisation.getSRI_ShortDocType());
				
			}	//	for all authorisations
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "", e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}

		return "@Created@ = " + m_created;
	}	//	generate

	
	public String lecfeinvoice_SriExportInvoiceXML100 (X_SRI_Authorisation authorisation)
	{
		
		String msg = null;
		
		LEC_FE_UtilsXml signature = new LEC_FE_UtilsXml();
		
		try {
			
			int c_invoice_id = 0;
			
			X_SRI_AccessCode accesscode = new X_SRI_AccessCode (getCtx(), authorisation.getSRI_AccessCode_ID(), get_TrxName());
			
			File file = signature.getFileFromStream(file_name, authorisation.getSRI_Authorisation_ID());
			
			file_name = signature.getFilename(signature, LEC_FE_UtilsXml.folderComprobantesFirmados);
			log.warning("@Signed Xml@ -> " + file_name);
			
			if (file.exists() || file.isFile() || file.canRead()) {
			    
			    file_name = signature.getFilename(signature, LEC_FE_UtilsXml.folderComprobantesAutorizados);
				m_created++;
			
			}
			
			c_invoice_id = LEC_FE_Utils.getAuthorisedInvoice(authorisation.getSRI_Authorisation_ID());
			
			MInvoice invoice = new MInvoice (getCtx(), c_invoice_id, get_TrxName());
			
			// Formato
			m_lec_sri_format_id = LEC_FE_Utils.getLecSriFormat(getAD_Client_ID(), signature.getDeliveredType(), authorisation.getSRI_ShortDocType(), invoice.getDateInvoiced(), invoice.getDateInvoiced());
					
			if ( m_lec_sri_format_id < 1)
				throw new AdempiereUserError("No existe formato para el comprobante");
			
			X_LEC_SRI_Format f = new X_LEC_SRI_Format (getCtx(), m_lec_sri_format_id, get_TrxName());
			
			// Emisor
			MOrgInfo oi = MOrgInfo.get(getCtx(), invoice.getAD_Org_ID(), get_TrxName());
			
			int c_bpartner_id = LEC_FE_Utils.getOrgBPartner(getAD_Client_ID(), oi.get_ValueAsString("TaxID"));
			MBPartner bpe = new MBPartner(getCtx(), c_bpartner_id, get_TrxName());
			
			m_retencionno = invoice.getDocumentNo();
			if (authorisation.getSRI_ShortDocType().equals("07"))	// COMPROBANTE DE RETENCIÓN
				m_retencionno = DB.getSQLValueString(get_TrxName(), "SELECT DISTINCT(DocumentNo) FROM LCO_InvoiceWithholding WHERE C_Invoice_ID = ? ", invoice.getC_Invoice_ID());

			//
			if (MSysConfig.getBooleanValue("QSSLEC_FE_EnvioXmlAutorizadoBPEmail", false, getAD_Client_ID()))
			{
				File attachment = (new File (file_name));
				
				if (attachment.exists() || attachment.isFile() || attachment.canRead()) {
					
			    	log.warning("@EMailing Xml@ -> " + file_name);
					// Enviar Email BPartner XML Autorizado
					// TODO Replicar en cada clase el definitivo
					MMailText mText = new MMailText(getCtx(), 0, get_TrxName());	// Solo en memoria
					mText.setPO(invoice);
					String subject = "SRI " + (signature.isOnTesting ? LEC_FE_UtilsXml.nombreCertificacion : LEC_FE_UtilsXml.nombreProduccion) + " " + bpe.getValue() + " : " + f.get_ValueAsString("XmlPrintLabel") + " " + m_retencionno;
					String text =
							" Emisor               : " + bpe.getName() +
							"\nFecha                : " + LEC_FE_Utils.getDate(invoice.getDateInvoiced(),10) +
							"\nCliente              : " + invoice.getC_BPartner().getName() +
							"\nComprobante          : " + f.get_ValueAsString("XmlPrintLabel") +
							"\nNumero               : " + m_retencionno +
							"\nAutorizacion No.     : " + authorisation.getSRI_AuthorisationCode() +
							"\nFecha Autorizacion   : " + authorisation.getSRI_DateAuthorisation() +
							"\nAdjunto              : " + file_name.substring(file_name.lastIndexOf(File.separator) + 1);
						
					int countMail = LEC_FE_Utils.notifyUsers(getCtx(), mText, authorisation.getAD_UserMail_ID(), subject, text, attachment, get_TrxName());
					if (countMail == 0)
						log.warning("@RequestActionEMailError@ -> " + file_name);
				}
			}
		
		//
		} catch (Exception e) {
			msg = "No se pudo obtener autorizacion - " + e.getMessage();
			log.severe(msg);
			throw new AdempiereException(msg);
		}

		return msg;
		
	} // lecfeinvoice_SriExportInvoiceXML100
	
	public String lecfeinout_SriExportInOutXML100 (X_SRI_Authorisation authorisation)
	{
		
		String msg = null;
		
		LEC_FE_UtilsXml signature = new LEC_FE_UtilsXml();
		
		try {
			
			int m_inout_id = 0;
			
			X_SRI_AccessCode accesscode = new X_SRI_AccessCode (getCtx(), authorisation.getSRI_AccessCode_ID(), get_TrxName());
			
			file_name = signature.getFilename(signature, LEC_FE_UtilsXml.folderComprobantesFirmados);
			
			File file = signature.getFileFromStream(file_name, authorisation.getSRI_Authorisation_ID());
			
			if (file.exists() || file.isFile() || file.canRead()) {
			 
			    file_name = signature.getFilename(signature, LEC_FE_UtilsXml.folderComprobantesAutorizados);
			    m_created++;
			
			}
			
			m_inout_id = LEC_FE_Utils.getAuthorisedInvoice(authorisation.getSRI_Authorisation_ID());
			
			MInOut inout = new MInOut (getCtx(), m_inout_id, get_TrxName());
			
			// Formato
			m_lec_sri_format_id = LEC_FE_Utils.getLecSriFormat(getAD_Client_ID(), signature.getDeliveredType(), authorisation.getSRI_ShortDocType(), inout.getMovementDate(), inout.getMovementDate());
					
			if ( m_lec_sri_format_id < 1)
				throw new AdempiereUserError("No existe formato para el comprobante");
			
			X_LEC_SRI_Format f = new X_LEC_SRI_Format (getCtx(), m_lec_sri_format_id, get_TrxName());
			
			// Emisor
			MOrgInfo oi = MOrgInfo.get(getCtx(), inout.getAD_Org_ID(), get_TrxName());
			
			int c_bpartner_id = LEC_FE_Utils.getOrgBPartner(getAD_Client_ID(), oi.get_ValueAsString("TaxID"));
			MBPartner bpe = new MBPartner(getCtx(), c_bpartner_id, get_TrxName());
			
			//
			if (MSysConfig.getBooleanValue("QSSLEC_FE_EnvioXmlAutorizadoBPEmail", false, getAD_Client_ID()))
			{
				File attachment = (new File (file_name));
				
				if (attachment.exists() || attachment.isFile() || attachment.canRead()) {
					
			    	log.warning("@EMailing Xml@ -> " + file_name);
					// Enviar Email BPartner XML Autorizado
					// TODO Replicar en cada clase el definitivo
					MMailText mText = new MMailText(getCtx(), 0, get_TrxName());	// Solo en memoria
					mText.setPO(inout);
					String subject = "SRI " + (signature.isOnTesting ? LEC_FE_UtilsXml.nombreCertificacion : LEC_FE_UtilsXml.nombreProduccion) + " " + bpe.getValue() + " : " + f.get_ValueAsString("XmlPrintLabel") + " " + inout.getDocumentNo();
					String text =
							" Emisor               : " + bpe.getName() +
							"\nFecha                : " + LEC_FE_Utils.getDate(inout.getMovementDate(),10) +
							"\nCliente              : " + inout.getC_BPartner().getName() +
							"\nComprobante          : " + f.get_ValueAsString("XmlPrintLabel") +
							"\nNumero               : " + inout.getDocumentNo() +
							"\nAutorizacion No.     : " + authorisation.getSRI_AuthorisationCode() +
							"\nFecha Autorizacion   : " + authorisation.getSRI_DateAuthorisation() +
							"\nAdjunto              : " + file_name.substring(file_name.lastIndexOf(File.separator) + 1);
						
					int countMail = LEC_FE_Utils.notifyUsers(getCtx(), mText, authorisation.getAD_UserMail_ID(), subject, text, attachment, get_TrxName());
					if (countMail == 0)
						log.warning("@RequestActionEMailError@ -> " + file_name);
				}
			}
		
		//
		} catch (Exception e) {
			msg = "No se pudo obtener autorizacion - " + e.getMessage();
			log.severe(msg);
			throw new AdempiereException(msg);
		}

		return msg;
		
	} // lecfeinout_SriExportInOutXML100

	
}	//	SRIEmailAuthorisation
