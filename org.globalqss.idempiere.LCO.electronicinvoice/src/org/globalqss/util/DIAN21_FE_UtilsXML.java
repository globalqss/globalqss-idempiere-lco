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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAttachment;
import org.compiere.model.MAttachmentEntry;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MLocation;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MProduct;
import org.compiere.model.MSequence;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTable;
import org.compiere.model.MTaxCategory;
import org.compiere.model.MUser;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Secure;
import org.compiere.util.Util;
import org.globalqss.model.LCO_FE_MInvoice;
import org.globalqss.model.MLCOFEAuthorization;
import org.globalqss.model.X_LCO_FE_ContactType;
import org.globalqss.model.X_LCO_FE_DIAN_Format;
import org.globalqss.model.X_LCO_FE_DocType;
import org.globalqss.model.X_LCO_FE_NCConcept;
import org.globalqss.model.X_LCO_FE_NDConcept;
import org.globalqss.model.X_LCO_FE_OFE_Contingency;
import org.globalqss.model.X_LCO_FE_OperationType;
import org.globalqss.model.X_LCO_FE_ProductScheme;
import org.globalqss.model.X_LCO_ISIC;
import org.globalqss.model.X_LCO_PrintedFormControl;
import org.globalqss.model.X_LCO_TaxIdType;
import org.globalqss.model.X_LCO_TaxPayerType;
import org.xml.sax.helpers.AttributesImpl;

/**
 *	Utils for LCO FE Xml
 *
 *  @author Jesus Garcia - globalqss - Quality Systems & Solutions - http://globalqss.com
 *	@version $Id: LCO_FE_UtilsXml21.java,v 1.0 2013/05/27 23:01:26 cruiz Exp $
 */

public class DIAN21_FE_UtilsXML {

	/**	Logger			*/
	private static CLogger s_log = CLogger.getCLogger(DIAN21_FE_UtilsXML.class);

	private boolean m_IsExport;
	private String m_Prefix;
	private String m_DocumentNo;
	private String m_coddoc = "";
	private int		m_lco_fe_dian_format_id = 0;
	private String m_Root_Node;
	private BigDecimal m_totaldescuento = Env.ZERO;
	private String m_IdAccount;
	private String m_UBLVersionNo = "";
	private boolean m_IsGranContribuyente;
	private boolean m_IsAutoRetenedor;
	private String m_UserName;
	private String m_EnvType;
	private boolean m_IsOnTesting;
	private String m_CUFE = "";
	private String m_QR = "";
	//private boolean m_IsAttachXml;
	private String m_File_Name = "";
	private String m_DIANUrlQRCode = "";

	public int m_sumanolineas = 0;
	public BigDecimal m_sumadescuento = Env.ZERO;
	public BigDecimal m_sumabaseimponible = Env.ZERO;
	public BigDecimal m_sumabaseexcluida = Env.ZERO;
	public BigDecimal m_sumavalorimpuesto = Env.ZERO;
	public BigDecimal m_sumavalorretenciones = Env.ZERO;
	public int m_totalnolineas = 0;
	public BigDecimal m_totalbaseimponible = Env.ZERO;
	public BigDecimal m_totalbaseexcluida = Env.ZERO;
	public BigDecimal m_totalvalorimpuesto = Env.ZERO;
	public BigDecimal m_totalretenciones = Env.ZERO;
	public BigDecimal m_totalcargo = Env.ZERO;
	public BigDecimal m_totalanticipo = Env.ZERO;
	private boolean m_UseContingency;
	private String m_File_Type;
	private Timestamp m_DateInvoiced;
	private String m_seqEnvio;

	public String getFile (LCO_FE_MInvoice invoice) {
		String msg = null;

		m_UseContingency = invoice.is_FE_UseContingency();
		
		try
		{
			m_IsOnTesting = MSysConfig.getBooleanValue("QSSLCO_FE_EnPruebas", false, Env.getAD_Client_ID(Env.getCtx()));
			m_EnvType = LCO_FE_Utils.AMBIENTE_PRODUCCION;
			m_DIANUrlQRCode = "https://catalogo-vpfe.dian.gov.co/document/searchqr?documentkey=";
			if (m_IsOnTesting) {
				m_EnvType = LCO_FE_Utils.AMBIENTE_CERTIFICACION;
				m_DIANUrlQRCode = "https://catalogo-vpfe-hab.dian.gov.co/document/searchqr?documentkey=";
			}
			
			MDocType dt = new MDocType(invoice.getCtx(), invoice.getC_DocTypeTarget_ID(), invoice.get_TrxName());

			// Exterior
			if (dt.get_Value("LCO_FE_IsExport") != null)
				m_IsExport = dt.get_ValueAsBoolean("LCO_FE_IsExport");

			// Batch
			if (dt.get_Value("LCO_FE_IsGenerateInBatch") != null)
				invoice.set_FE_IsGenerateInBatch(dt.get_ValueAsBoolean("LCO_FE_IsGenerateInBatch")); 

			invoice.set_FE_IsAttachXml(MSysConfig.getBooleanValue("QSSLCO_FE_DebugEnvioRecepcion", true, Env.getAD_Client_ID(Env.getCtx())));
			
			m_Prefix = "";
			if (dt.getDefiniteSequence().getPrefix() != null)
				m_Prefix = dt.getDefiniteSequence().getPrefix();
			
			m_DocumentNo = invoice.getDocumentNo().replace(m_Prefix, "");

			try {
				long docNum = Long.parseLong(m_DocumentNo);
				if (docNum < 0)
					throw new NumberFormatException();
			} catch (NumberFormatException e) {
				throw new AdempiereException((Msg.getMsg(Env.getCtx(), "LCO_NotANumber") + " "
						+ Msg.getElement(Env.getCtx(), MInvoice.COLUMNNAME_DocumentNo) + "-"
						+ m_DocumentNo));
			}

			// Contingencia
			X_LCO_FE_OFE_Contingency feoc = null;

			if ( dt.get_Value("LCO_FE_IsUseContingency") != null && dt.get_ValueAsBoolean("LCO_FE_IsUseContingency") ) {
				int lco_fe_ofe_contingency_id = LCO_FE_Utils.getOfeContingenciaID(invoice);
				feoc = new X_LCO_FE_OFE_Contingency (invoice.getCtx(), lco_fe_ofe_contingency_id, invoice.get_TrxName());
				invoice.set_FE_UseContingency(feoc.get_ValueAsBoolean("IsEnable"));
				m_UseContingency = invoice.is_FE_UseContingency();
			}	

			X_LCO_FE_DocType fedt = new X_LCO_FE_DocType (invoice.getCtx(), dt.get_ValueAsInt("LCO_FE_DocType_ID"), invoice.get_TrxName());

			m_coddoc = fedt.getDianShortDocType();
			
			X_LCO_FE_OperationType feot = new X_LCO_FE_OperationType (invoice.getCtx(), invoice.get_ValueAsInt("LCO_FE_OperationType_ID"), invoice.get_TrxName());

			X_LCO_FE_NCConcept fencc = null;
			if (m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_NC))
				fencc = new X_LCO_FE_NCConcept (invoice.getCtx(), invoice.get_ValueAsInt("LCO_FE_NCConcept_ID"), invoice.get_TrxName());
			
			X_LCO_FE_NDConcept fendc = null;
			if (m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_ND))
				fendc = new X_LCO_FE_NDConcept (invoice.getCtx(), invoice.get_ValueAsInt("LCO_FE_NDConcept_ID"), invoice.get_TrxName());
			
			// Formato
			m_lco_fe_dian_format_id = LCO_FE_Utils.getLcoFeDianFormatID(invoice, fedt.getLCO_FE_DocType_ID());

			X_LCO_FE_DIAN_Format f = new X_LCO_FE_DIAN_Format (invoice.getCtx(), m_lco_fe_dian_format_id, null);
			MSequence seqxml = null;
			if ( m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_SOPORTE)
				|| m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_AJUSTE_AC) ) {
				seqxml = new MSequence(invoice.getCtx(), f.getAD_Sequence_ID(), invoice.get_TrxName());
				m_seqEnvio = MSequence.getDocumentNoFromSeq(seqxml, invoice.get_TrxName(), invoice);
				m_seqEnvio = m_seqEnvio.replace(m_Prefix, "");
				m_DateInvoiced = invoice.getDateInvoiced();
			}
			
			m_File_Type = f.getLCO_FE_EDIType();
			invoice.set_FE_FileType(m_File_Type);

			m_Root_Node = f.get_ValueAsString("XmlPrintLabel");
			m_UBLVersionNo = f.getUBLVersionNo();

			// Resolucion
			int lco_printedformcontrol_id = LCO_FE_Utils.getDianResolutionID(invoice, m_UseContingency);

			X_LCO_PrintedFormControl pfc = new X_LCO_PrintedFormControl (invoice.getCtx(), lco_printedformcontrol_id, invoice.get_TrxName());

			// Emisor
			MOrgInfo oi = MOrgInfo.get(invoice.getCtx(), invoice.getAD_Org_ID(), invoice.get_TrxName());

			// LCO_FE_IdAccount=NA, LCO_FE_UserName=SoftwareID, LCO_FE_UserPass=SoftwarePIN
			m_IdAccount = oi.get_ValueAsString("LCO_FE_UserName") + oi.get_ValueAsString("LCO_FE_UserPass");
			if (LCO_FE_Utils.UBL_VERSION_21.equals(m_UBLVersionNo))
				m_IdAccount = m_IdAccount + invoice.getDocumentNo();
			MessageDigest digest = MessageDigest.getInstance("SHA-384");
			byte[] bytes = digest.digest(m_IdAccount.getBytes(StandardCharsets.UTF_8));
			m_IdAccount = Secure.convertToHexString(bytes);	//	Context
			m_UserName = oi.get_ValueAsString("LCO_FE_UserName");
			m_IsGranContribuyente = !oi.get_ValueAsString("LCO_FE_GranConResolucionNo").equals("");
			m_IsAutoRetenedor = !oi.get_ValueAsString("LCO_FE_AutoretResolucionNo").equals("");

			MBPartner bpe = new MBPartner(invoice.getCtx(), oi.get_ValueAsInt("C_BPartner_ID"), invoice.get_TrxName());

			X_LCO_TaxIdType tte = new X_LCO_TaxIdType(invoice.getCtx(), bpe.get_ValueAsInt("LCO_TaxIdType_ID"), invoice.get_TrxName());

			X_LCO_TaxPayerType tpte = new X_LCO_TaxPayerType(invoice.getCtx(), bpe.get_ValueAsInt("LCO_TaxPayerType_ID"), invoice.get_TrxName());
			
			X_LCO_ISIC ie = new X_LCO_ISIC(invoice.getCtx(), bpe.get_ValueAsInt("LCO_ISIC_ID"), invoice.get_TrxName());

			MLocation lo = new MLocation(invoice.getCtx(), oi.getC_Location_ID(), invoice.get_TrxName());

			MLocation locc = new MLocation(invoice.getCtx(), bpe.get_ValueAsInt("LCO_FE_CCLocation_ID"), invoice.get_TrxName());

			// int ad_user_id = LCO_FE_Utils.getFEBPartnerContact(oi.getAD_Client_ID(), getSalesRep_ID());
			int ad_user_id = invoice.get_ValueAsInt("LCO_FE_UserFrom_ID");
			
			MUser uo = new MUser(invoice.getCtx(), ad_user_id, invoice.get_TrxName());
			
			X_LCO_FE_ContactType uoct = new X_LCO_FE_ContactType(invoice.getCtx(), uo.get_ValueAsInt("LCO_FE_ContactType_ID"), invoice.get_TrxName());

			// Adquiriente
			MBPartner bp = new MBPartner(invoice.getCtx(), invoice.getC_BPartner_ID(), invoice.get_TrxName());

			X_LCO_TaxIdType tta = new X_LCO_TaxIdType(invoice.getCtx(), bp.get_ValueAsInt("LCO_TaxIdType_ID"), invoice.get_TrxName());
			X_LCO_TaxPayerType tpta = new X_LCO_TaxPayerType(invoice.getCtx(), bp.get_ValueAsInt("LCO_TaxPayerType_ID"), invoice.get_TrxName());

			MLocation la = new MLocation(invoice.getCtx(), invoice.getC_BPartner_Location().getC_Location_ID(), invoice.get_TrxName());

			MLocation lacc = new MLocation(invoice.getCtx(), bp.get_ValueAsInt("LCO_FE_CCLocation_ID"), invoice.get_TrxName());

			// ad_user_id = LCO_FE_Utils.getFEBPartnerContact(getAD_Client_ID(), getC_Order().getBill_User_ID());
			ad_user_id = invoice.get_ValueAsInt("LCO_FE_UserTo_ID");
			
			MUser ua = new MUser(invoice.getCtx(), ad_user_id, invoice.get_TrxName());
			
			X_LCO_FE_ContactType uact = new X_LCO_FE_ContactType(invoice.getCtx(), ua.get_ValueAsInt("LCO_FE_ContactType_ID"), invoice.get_TrxName());

			// El descuento en iDempiere se maneja como Cargo
			m_totaldescuento = Env.ZERO; // DB.getSQLValueBD(get_TrxName(), "SELECT COALESCE(SUM(ilt.discount), 0) FROM c_invoice_linetax_vt ilt WHERE ilt.C_Invoice_ID = ? ", getC_Invoice_ID());

			// Generate CUFE/UUID
			if (m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_FACTURA)
					|| m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_EXPORTACION)
					|| m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_CONTINGENCIA)
					|| m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_NC)
					|| m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_ND)
					|| m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_SOPORTE)
					|| m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_AJUSTE_AC)) {
				m_CUFE = 
					generateCufe(
						invoice.get_TrxName(),
						invoice.getC_Invoice_ID(),
						fedt.getDianShortDocType(),
						invoice.getDocumentNo(),	// With Prefix m_DocumentNo
						(Timestamp)invoice.get_Value("LCO_FE_DateTrx"),
						invoice.getTotalLines(),
						invoice.getGrandTotalPlusWithholdings(),
						bpe.getTaxID(),
						tta.get_ValueAsString("LCO_TaxCodeDian"),
						bp.getTaxID(),
						pfc.get_ValueAsString("TechKey"),
						(fencc != null ?  fencc.getValue() : ""),
						(fendc != null ?  fendc.getValue() : ""),
						(feoc != null ?  feoc.getStartDate() : null),
						(feoc != null ?  feoc.getEndDate() : null),
						(feoc != null ?  feoc.getIdContingency() : "")
					);
			}
			
			// Generate QR
			if (m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_FACTURA)
					|| m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_EXPORTACION)
					|| m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_CONTINGENCIA)
					|| m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_NC)
					|| m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_ND)
					|| m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_SOPORTE)
					|| m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_AJUSTE_AC)) {
				m_QR =
					generateQR(
							invoice.get_TrxName(),
							invoice.getC_Invoice_ID(),
							fedt.getDianShortDocType(),
							invoice.getDocumentNo(),	// With Prefix
							(Timestamp)invoice.get_Value("LCO_FE_DateTrx"),
							invoice.getTotalLines(),
							invoice.getGrandTotalPlusWithholdings(),
							bpe.getTaxID(),
							bp.getTaxID(),
							m_CUFE
					);
			}

			int authID = DB.getSQLValueEx(null, "SELECT LCO_FE_Authorization_ID FROM LCO_FE_Authorization WHERE AD_Table_ID=? AND Record_ID=?", MInvoice.Table_ID, invoice.getC_Invoice_ID());
			if (authID < 0) {
				authID = 0;
			}

			// Create a new authorization - out of Trx to save auth and attachments independently 
			MLCOFEAuthorization a = new MLCOFEAuthorization(invoice.getCtx(), authID, null);

			a.setAD_Org_ID(invoice.getAD_Org_ID());
			a.setLCO_FE_DocType_ID(dt.get_ValueAsInt("LCO_FE_DocType_ID"));
			a.setValue(fedt.getValue() + "-" + invoice.getDocumentNo());
			a.setLCO_FE_IdErrorCode(Env.ZERO);
			a.setAD_UserMail_ID(ua.getAD_User_ID());
			a.setAD_Table_ID(MInvoice.Table_ID);
			a.setRecord_ID(invoice.get_ID());
			a.setLCO_PrintedFormControl_ID(pfc.getLCO_PrintedFormControl_ID());
			a.setLCO_FE_Dian_Uuid(m_CUFE);
			a.setLCO_FE_IdTransaction(m_QR);
			a.setLCO_FE_DIAN_Format_ID(f.getLCO_FE_DIAN_Format_ID());
			if (m_UseContingency)
				a.setLCO_FE_OFE_Contingency_ID(feoc.getLCO_FE_OFE_Contingency_ID());
				
			if (!a.save())
				return "@AlreadyExists@ " + "@SaveErrorNotUnique@ "
					+ Msg.getElement(Env.getCtx(), MLCOFEAuthorization.COLUMNNAME_LCO_FE_Authorization_ID);

			// Genera Comprobante
			String xmlFileName = constructFileName(bpe.getTaxID(), f.getValue(), true);
			
			// Ruta completa del archivo
			m_File_Name = invoice.get_FE_FolderRaiz() + File.separator + LCO_FE_Utils.FOLDER_COMPROBANTES_GENERADOS + File.separator + xmlFileName;
			//crea los directorios para los archivos xml
			(new File(invoice.get_FE_FolderRaiz() + File.separator + LCO_FE_Utils.FOLDER_COMPROBANTES_GENERADOS + File.separator)).mkdirs();

			if (LCO_FE_Utils.RESOURCE_XML.equals(m_File_Type)) {
				if (LCO_FE_Utils.UBL_VERSION_21.equals(m_UBLVersionNo)) {
					if (   m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_FACTURA)
						|| m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_EXPORTACION)
						|| m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_CONTINGENCIA))
						msg = lcofeinv_GovGeneraInvoiceXML210(
								m_File_Name, m_coddoc, invoice, dt, fedt,	feot, f, pfc, feoc, oi,
								bpe, tpte, tte, ie, lo, locc, uo, uoct,
								bp,	tpta, tta, la, lacc, ua, uact, fencc, fendc);
					else if (m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_NC))
						msg = lcofeinvcr_GovGeneraCreditNoteXML210(
								m_File_Name, m_coddoc, invoice, dt, fedt,	feot, f, pfc, feoc, oi,
								bpe, tpte, tte, ie, lo, locc, uo, uoct,
								bp,	tpta, tta, la, lacc, ua, uact, fencc, fendc);
					else if (m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_ND))
						msg = lcofeinvdr_GovGeneraDebitNoteXML210(
								m_File_Name, m_coddoc, invoice, dt, fedt,	feot, f, pfc, feoc, oi,
								bpe, tpte, tte, ie, lo, locc, uo, uoct,
								bp,	tpta, tta, la, lacc, ua, uact, fencc, fendc);
					else if (m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_SOPORTE))
						msg = lcofeinv_GovGeneraSoporteXML210(
								m_File_Name, m_coddoc, invoice, dt, fedt,	feot, f, pfc, feoc, oi,
								bpe, tpte, tte, ie, lo, locc, uo, uoct,
								bp,	tpta, tta, la, lacc, ua, uact, fencc, fendc);
				}
				else
					msg = Msg.translate(Env.getCtx(), "NotAvailable") + " "
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_DIAN_Format.COLUMNNAME_LCO_FE_DIAN_Format_ID) + "-"
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_DIAN_Format.COLUMNNAME_UBLVersionNo) + "-"
						+ f.getUBLVersionNo();
			} else
				msg = Msg.translate(Env.getCtx(), "NotAvailable") + " "
					+ Msg.getElement(Env.getCtx(), X_LCO_FE_DIAN_Format.COLUMNNAME_LCO_FE_DIAN_Format_ID) + "-"
					+ Msg.getElement(Env.getCtx(), X_LCO_FE_DIAN_Format.COLUMNNAME_LCO_FE_EDIType) + "-"
					+ m_File_Type;

			if (msg != null)
				return msg;
			
			// Attach XML Generado
			if (invoice.is_FE_AttachXml())
				LCO_FE_Utils.attachFile(a.getCtx(), a.get_TrxName(), a.getLCO_FE_Authorization_ID(), m_File_Name, LCO_FE_Utils.RESOURCE_XML);
	        
			invoice.set_FE_FileToSign(m_File_Name);
			invoice.set_ValueOfColumn("LCO_FE_Authorization_ID", a.getLCO_FE_Authorization_ID());
			invoice.setDateInvoiced((Timestamp)invoice.get_Value("LCO_FE_DateTrx"));
			invoice.setDateAcct((Timestamp)invoice.get_Value("LCO_FE_DateTrx"));
			invoice.saveEx();

		}
		catch (Exception e)
		{
			msg = Msg.getMsg(Env.getCtx(), "FileCannotCreate") + " "
					+ m_File_Type + "-"
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
	}
	
	/**
	 * Genera XML FV, FE, FC v2.1
	 * Prefijo fe desaparece quedando el prefijo propio que maneja el UBL: cac
	 * @param
	 * @return msg
	 */
	public String lcofeinv_GovGeneraInvoiceXML210(
			String file_name,
			String m_coddoc,
			LCO_FE_MInvoice inv,
			MDocType dt,
			X_LCO_FE_DocType fedt,
			X_LCO_FE_OperationType feot,
			X_LCO_FE_DIAN_Format f,
			X_LCO_PrintedFormControl pfc,
			X_LCO_FE_OFE_Contingency feoc,
			MOrgInfo oi,
			MBPartner bpe,
			X_LCO_TaxPayerType tpte,
			X_LCO_TaxIdType tte,
			X_LCO_ISIC ie,
			MLocation lo,
			MLocation locc,
			MUser uo,
			X_LCO_FE_ContactType uoct,
			MBPartner bp,
			X_LCO_TaxPayerType tpta,
			X_LCO_TaxIdType tta,
			MLocation la,
			MLocation lacc,
			MUser ua,
			X_LCO_FE_ContactType uact,
			X_LCO_FE_NCConcept fencc,
			X_LCO_FE_NDConcept fendc
			)
	{

		m_sumanolineas = 0;
		m_sumadescuento = Env.ZERO;
		m_sumabaseimponible = Env.ZERO;
		m_sumavalorimpuesto = Env.ZERO;

		String msg = null;

		try {

			OutputStream  mmDocStream = null;

			//Stream para el documento xml
			mmDocStream = new FileOutputStream (file_name, false);
			StreamResult streamResult_menu = new StreamResult(new OutputStreamWriter(mmDocStream,"UTF-8"));
			SAXTransformerFactory tf_menu = (SAXTransformerFactory) SAXTransformerFactory.newInstance();					
			try {
				tf_menu.setAttribute("indent-number", Integer.valueOf(2));
			} catch (Exception e) {
				// swallow
			}
			TransformerHandler mmDoc = tf_menu.newTransformerHandler();	
			Transformer serializer_menu = mmDoc.getTransformer();	
			serializer_menu.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			serializer_menu.setOutputProperty(OutputKeys.STANDALONE, "no");
			serializer_menu.setOutputProperty(OutputKeys.INDENT,"yes");
			// serializer_menu.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
			mmDoc.setResult(streamResult_menu);

			mmDoc.startDocument();

			AttributesImpl atts = new AttributesImpl();

			// Encabezado
			atts.clear();
			// atts.addAttribute("", "", "id", "CDATA", "comprobante");
			// atts.addAttribute("", "", "version", "CDATA", f.get_ValueAsString("VersionNo"));
			atts = addHeaderAttribute(fedt.getDianShortDocType(), LCO_FE_Utils.SIGNER_POLICY_V2); 

			mmDoc.startElement("", "", m_Root_Node, atts);

			atts.clear();

			// Emisor = Org
			// Adquiriente = BPartner
			// Requerido: M-Mandatorio, D-Dependiente, O-Opcional
			// Tipo: A-Alfabetico, AN-Alfanumrrico, N-Numerico
			// Long.

			// Informaci\u00f3n Cabecera
			
			// Extensions
			mmDoc.startElement("","","ext:UBLExtensions", atts);
				mmDoc.startElement("","","ext:UBLExtension", atts);
					mmDoc.startElement("","","ext:ExtensionContent", atts);
						mmDoc.startElement("","","sts:DianExtensions", atts);
							mmDoc.startElement("","","sts:InvoiceControl", atts);
								if (m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_FACTURA)) {
									// M AN 14 N\u00famero autorizaci\u00f3n
									addHeaderElement(mmDoc, "sts:InvoiceAuthorization", pfc.getAuthorizationNo(), atts);
									mmDoc.startElement("","","sts:AuthorizationPeriod", atts);
										// M AN 10 Fecha de inicio Resolucion AAAA-MM-DD
										addHeaderElement(mmDoc, "cbc:StartDate", String.format("%1$tF", pfc.getValidFrom()), atts);
										// M AN 10 Fecha de fin Resolucion AAAA-MM-DD
										addHeaderElement(mmDoc, "cbc:EndDate", String.format("%1$tF", pfc.getValidUntil()), atts);
									mmDoc.endElement("","","sts:AuthorizationPeriod");
								}
								mmDoc.startElement("","","sts:AuthorizedInvoices", atts);
									// D AN 4 Prefijo
									if (pfc.getPrefix() != null )
										addHeaderElement(mmDoc, "sts:Prefix", pfc.getPrefix(), atts);
									// M N 9 Rango de Numeraci\u00f3n (m\u00ednimo).
									addHeaderElement(mmDoc, "sts:From", String.valueOf(pfc.getInitialSequence()), atts);
									// M N 9 Rango de Numeraci\u00f3n (m\u00e1ximo).
									addHeaderElement(mmDoc, "sts:To", String.valueOf(pfc.getFinalSequence()), atts);
								mmDoc.endElement("","","sts:AuthorizedInvoices");
							mmDoc.endElement("","","sts:InvoiceControl");
							mmDoc.startElement("","","sts:InvoiceSource", atts);
								atts.clear();
								atts.addAttribute("", "", "listAgencyID", "CDATA", "6");
								atts.addAttribute("", "", "listAgencyName", "CDATA", "United Nations Economic Commission for Europe");
								atts.addAttribute("", "", "listSchemeURI", "CDATA", "urn:oasis:names:specification:ubl:codelist:gc:CountryIdentificationCode-2.1");
								// M A 2 Literal "CO"
								addHeaderElement(mmDoc, "cbc:IdentificationCode", lo.getCountry().getCountryCode(), atts);
								atts.clear();
							mmDoc.endElement("","","sts:InvoiceSource");
							mmDoc.startElement("","","sts:SoftwareProvider", atts);
								atts.clear();
								atts = addGovAttribute21(bpe.get_ValueAsString("TaxIdDigit"), LCO_FE_Utils.cutString(tte.get_ValueAsString("LCO_TaxCodeDian"), 7));
								// M N 5..12 NIT del Prestador de Servicios
								addHeaderElement(mmDoc, "sts:ProviderID", bpe.getTaxID().trim(), atts);
								atts.clear();
								atts = addGovAttribute21(null, null);
								// M AN, Identificador Software
								addHeaderElement(mmDoc, "sts:SoftwareID", oi.get_ValueAsString("LCO_FE_UserName"), atts);
								atts.clear();
							mmDoc.endElement("","","sts:SoftwareProvider");
							atts.clear();
							atts = addGovAttribute21(null, null);
							addHeaderElement(mmDoc, "sts:SoftwareSecurityCode", m_IdAccount, atts);
							atts.clear();
							// Proveedor Autorizado (PA) por la DIAN
							mmDoc.startElement("","","sts:AuthorizationProvider", atts);
								atts = addGovAttribute21("4", "31");	// NIT DV de DIAN 4
								// M N Debe corresponder al Nit de la DIAN
								addHeaderElement(mmDoc, "sts:AuthorizationProviderID", "800197268", atts);
							mmDoc.endElement("","","sts:AuthorizationProvider");
							atts.clear();
							// M AN
							addHeaderElement(mmDoc, "sts:QRCode", m_QR, atts);
							//
							if (m_UseContingency) {
								// Error: Invalid content was found starting with element 'sts:OFEContingencia' -- temp commented
								// M AN Max 35 Identificador de la contingencia
								// addHeaderElement(mmDoc, "sts:OFEContingenciaID", feoc.getIdContingency(), atts);	// TODO uncomment FC
								/*
								mmDoc.startElement("","","sts:OFEContingencia", atts);
									mmDoc.startElement("","","sts:OFEContingenciaPeriod", atts);
										// M AN Max 10 Fecha de comienzo de la contingencia - Formato: AAAA-MM-DD
										addHeaderElement(mmDoc, "cbc:StartDate", String.format("%1$tF", feoc.getStartDate()), atts);
										// M AN Max 8, Hora de comienzo de la contingencia - Formato: hh:mm:ss
										addHeaderElement(mmDoc, "cbc:StartTime", String.format("%1$tT", feoc.getStartDate()), atts);
										// M AN Max 10 Fecha de terminaci\u00f3n de la contingencia - Formato: AAAA-MM-DD
										addHeaderElement(mmDoc, "cbc:EndDate", String.format("%1$tF", feoc.getEndDate()), atts);
										// M AN Max 8, Hora de terminaci\u00f3n de la contingencia - Formato: hh:mm:ss
										addHeaderElement(mmDoc, "cbc:EndTime", String.format("%1$tT", feoc.getEndDate()), atts);
									mmDoc.endElement("","","sts:OFEContingenciaPeriod");
									// M AN Max 35 Identificador de la contingencia
									addHeaderElement(mmDoc, "sts:OFEContingenciaID", feoc.getIdContingency(), atts);
									atts.addAttribute("", "", "schemeName", "CDATA", "identificador universal del evento contingente; Anexo-3");
									atts.addAttribute("", "", "schemeURI", "CDATA", "http://www.dian.gov.co/");
									addHeaderElement(mmDoc, "sts:UUID", m_CUFE, atts);
									atts.clear();
								mmDoc.endElement("","","sts:OFEContingencia");
								*/
							}
							if (!(inv.getC_Currency_ID() == LCO_FE_Utils.CURRENCY_COP_ID)) {
								Object[] args = new Object[] { inv.getGrandTotalPlusWithholdings(), inv.getC_Currency_ID(), LCO_FE_Utils.CURRENCY_COP_ID, inv.getDateInvoiced(), LCO_FE_Utils.CONVERSION_TRM_ID, inv.getAD_Client_ID(), Env.ZERO };
								BigDecimal baseamount = DB.getSQLValueBDEx(inv.get_TrxName(), "select currencyconvert(?, ?, ?, ?, ?, ?, ?)", args);
								if (baseamount != null) {
									mmDoc.startElement("","","sts:AdditionalMonetaryTotal", atts);
										atts.clear();
										atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
										// Obligatorio si es una factura con divisa extranjera
										addHeaderElement(mmDoc, "cbc:InvoiceTotalLocalCurrencyAmount", LCO_FE_Utils.decimalFormat(baseamount), atts);
										atts.clear();
									mmDoc.endElement("","","sts:AdditionalMonetaryTotal");
								}
							}
						mmDoc.endElement("","","sts:DianExtensions");
					mmDoc.endElement("","","ext:ExtensionContent");
				mmDoc.endElement("","","ext:UBLExtension");
				mmDoc.startElement("","","ext:UBLExtension", atts);
					mmDoc.startElement("","","ext:ExtensionContent", atts);
						// <ds:Signature
						// </ds:Signature>
					mmDoc.endElement("","","ext:ExtensionContent");
				mmDoc.endElement("","","ext:UBLExtension");
			mmDoc.endElement("","","ext:UBLExtensions");
			//
			// M AF Max 7, Version UBL
			addHeaderElement(mmDoc, "cbc:UBLVersionID", LCO_FE_Utils.cutString(f.getUBLVersionNo(), 7), atts);	// UBL 2.1
			// M AN 1..4, Indicador del tipo de operación
			addHeaderElement(mmDoc, "cbc:CustomizationID", LCO_FE_Utils.cutString(feot.getValue(), 2), atts);
			// M AF Max 55, Version Formato
			addHeaderElement(mmDoc, "cbc:ProfileID", LCO_FE_Utils.cutString(f.getVersionNo() + ": " + f.getName(), 55), atts);	// DIAN 2.1
			// M N 1, Ambiente de Destino
			addHeaderElement(mmDoc, "cbc:ProfileExecutionID", m_EnvType, atts);
			// M AF Max 20, Prefijo + No. Documento Formato
			addHeaderElement(mmDoc, "cbc:ID", inv.getDocumentNo(), atts); // With Prefix getDocumentNo()
			atts.clear();
			atts = addGovAttribute21(m_EnvType, f.getLCO_FE_UuidType());
			// Error: El valor del CUFE es obligatorio cuando el tipo de documento es 1 = 'INVOICE'
			// M AF 96, CUFE: Obligatorio si es factura nacional
			addHeaderElement(mmDoc, "cbc:UUID", m_CUFE, atts);	// TODO reviewme FC
			atts.clear();
			// M AN Max 10, Fecha de emisi\u00f3n de la factura Formato AAAA-MM-DD
			addHeaderElement(mmDoc, "cbc:IssueDate", LCO_FE_Utils.getDateTime((Timestamp) inv.get_Value("LCO_FE_DateTrx"), 11), atts);
			// M AN Max 14, Hora de emisi\u00f3n de la factura Formato HH:MM:DD-05:00
			addHeaderElement(mmDoc, "cbc:IssueTime", LCO_FE_Utils.getDateTime((Timestamp) inv.get_Value("LCO_FE_DateTrx"), 14), atts);
			atts.clear();
			// atts.addAttribute("", "", "listAgencyID", "CDATA", "195");
			// atts.addAttribute("", "", "listAgencyName", "CDATA", "CO, DIAN (Direccion de Impuestos y Aduanas Nacionales)");
			// atts.addAttribute("", "", "listSchemeURI", "CDATA", "http://www.dian.gov.co/contratos/facturaelectronica/v1/InvoiceType");
			// M AN Max 2, Tipo de documento
			addHeaderElement(mmDoc, "cbc:InvoiceTypeCode", LCO_FE_Utils.cutString(fedt.getDianDocTypeCode(), 2), atts);
			atts.clear();
			//
			// O AF 15..5000, Información adicional: Texto libre
			addHeaderElement(mmDoc, "cbc:Note", "QRCode: " + m_QR, atts);
			// D AN Max 3, Divisa de la Factura
			addHeaderElement(mmDoc, "cbc:DocumentCurrencyCode", inv.getCurrencyISO(), atts);
			//
			List<List<Object>> lines = DB.getSQLArrayObjectsEx(inv.get_TrxName(), LCO_FE_Utils.SQL_ITEMS, inv.getC_Invoice_ID());
			m_totalnolineas = lines.size();
			// M N 3, Número de elementos InvoiceLine
			addHeaderElement(mmDoc, "cbc:LineCountNumeric", String.valueOf(m_totalnolineas), atts);
			//
			if (m_UseContingency) {
				atts.clear();
				mmDoc.startElement("","","cac:BillingReference", atts);
					mmDoc.startElement("","","cac:InvoiceDocumentReference", atts);
						atts.addAttribute("", "", "schemeName", "CDATA", "Rango Manual");
						atts.addAttribute("", "", "schemeURI", "CDATA", "http://www.dian.gov.co/contratos/facturaelectronica/v1/anexo_v1_0.html#rangoManual");
					    addHeaderElement(mmDoc, "cbc:ID", inv.getDocumentNo(), atts);	// With Prefix
						atts.clear();
						addHeaderElement(mmDoc, "cbc:CopyIndicator", "true", atts);
						addHeaderElement(mmDoc, "cbc:IssueDate", String.format("%1$tF", feoc.getDateTranscription()), atts);
						// D AF 2, Language Code CodeList_LanguageCode_ISO_7_04.xsd
						atts.addAttribute("", "", "languageID", "CDATA", LCO_FE_Utils.cutString(bpe.getAD_Language(), 3));
						addHeaderElement(mmDoc, "cbc:DocumentType", "Transcripción de factura de venta expedida manualmente motivada en una contingencia", atts);
						atts.clear();
					mmDoc.endElement("","","cac:InvoiceDocumentReference");
				mmDoc.endElement("","","cac:BillingReference");
			}
			
			// Informaci\u00f3n Emisor
			atts.clear();
			mmDoc.startElement("","","cac:AccountingSupplierParty", atts);
				atts.clear();
				atts.addAttribute("", "", "schemeAgencyID", "CDATA", "195");
				// M N 1, Tipo de organización jurídica Tabla 9
				addHeaderElement(mmDoc, "cbc:AdditionalAccountID", LCO_FE_Utils.cutString(tpte.get_ValueAsString("DianTaxPayerCode"), 1) , atts);
				atts.clear();
				mmDoc.startElement("","","cac:Party", atts);
					// O N 4, código de actividad económica CIIU
					// addHeaderElement(mmDoc, "cbc:IndustryClasificationCode", ie.getValue().substring(1, 5), atts);	// Fix ZB01
					mmDoc.startElement("","","cac:PartyIdentification", atts);
					atts.clear();
					// M AN 7, Tipo identificaci\u00f3n Tabla 2
					atts = addGovAttribute21(null, LCO_FE_Utils.cutString(tte.get_ValueAsString("LCO_TaxCodeDian"), 7));
					// M AF Max 35, No. Identificacion Emisor
					addHeaderElement(mmDoc, "cbc:ID", bpe.getTaxID().trim(), atts);
					atts.clear();
					mmDoc.endElement("","","cac:PartyIdentification");
					if ( tpte.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_PERSONA_JURIDICA)
							|| tpte.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_GRAN_CONTRIBUYENTE) ) {
						mmDoc.startElement("","","cac:PartyName", atts);
							// D AN 5..450 Nombre comercial del emisor
							addHeaderElement(mmDoc, "cbc:Name", bpe.getName(), atts);
						mmDoc.endElement("","","cac:PartyName");
					}
					mmDoc.startElement("","","cac:PhysicalLocation", atts);
						mmDoc.startElement("","","cac:Address", atts);
							// M N 5 Codigo del municipio
							addHeaderElement(mmDoc, "cbc:ID", LCO_FE_Utils.cutString(lo.getC_City().getPostal(), 5), atts);
							// M AN Max 60 Nombre de la ciudad
							addHeaderElement(mmDoc, "cbc:CityName", lo.getC_City().getName(), atts);
							// M AN Max 60 Nombre del Departamento
							addHeaderElement(mmDoc, "cbc:CountrySubentity", lo.getRegionName(), atts);
							// M N 1..5 Código del Departamento
							addHeaderElement(mmDoc, "cbc:CountrySubentityCode", LCO_FE_Utils.cutString(lo.getRegion().getDescription(), 2), atts);
							// X AN Max 40 Municipio (distrito administrativo)
							// addHeaderElement(mmDoc, "cbc:CitySubdivisionName", lo.getC_City().getName(), atts);
							mmDoc.startElement("","","cac:AddressLine", atts);
								// M AN Max 300 Direccion
								addHeaderElement(mmDoc, "cbc:Line", lo.getAddress1(), atts);
							mmDoc.endElement("","","cac:AddressLine");
							mmDoc.startElement("","","cac:Country", atts);
								// M AN Max 2 Pais Tabla 8
								addHeaderElement(mmDoc, "cbc:IdentificationCode", lo.getCountry().getCountryCode(), atts);
								// O A 4..40 Nombre Pais Tabla 8
								atts.addAttribute("", "", "languageID", "CDATA", LCO_FE_Utils.cutString(Env.getAD_Language(Env.getCtx()), 2));
								addHeaderElement(mmDoc, "cbc:Name", lo.getCountry().get_Translation("Name"), atts);
								atts.clear();
							mmDoc.endElement("","","cac:Country");
						mmDoc.endElement("","","cac:Address");
					mmDoc.endElement("","","cac:PhysicalLocation");
					mmDoc.startElement("","","cac:PartyTaxScheme", atts);
						// M AF 5..450, Nombre o Razón Social del emisor
						addHeaderElement(mmDoc, "cbc:RegistrationName", bpe.getName(), atts);
						atts.clear();
						atts = addGovAttribute21(bpe.get_ValueAsString("TaxIdDigit"), LCO_FE_Utils.cutString(tte.get_ValueAsString("LCO_TaxCodeDian"), 7));	// TODO Reviewme
						// M N 5..12, No. Identificacion Emisor
						addHeaderElement(mmDoc, "cbc:CompanyID", bpe.getTaxID().trim(), atts);
						atts.clear();
						{ // BP Info Emisor
							boolean nobpeinfo = true;
							String codigosRUT = "";
							List<List<Object>> rowse = DB.getSQLArrayObjectsEx(bpe.get_TrxName(), LCO_FE_Utils.SQL_BP_INFO, bpe.getC_BPartner_ID());
							if (rowse != null) {
								for (List<Object> rowe : rowse) {
									// String grupoRUT = rowe.get(0).toString();
									String codigoRUT = rowe.get(1).toString();
									if (Util.isEmpty(codigoRUT, true)) {
										nobpeinfo = true;
										break;
									}
									nobpeinfo = false;
									codigosRUT = codigosRUT + codigoRUT + ";";
								}
								atts.clear();
								atts.addAttribute("", "", "listName", "CDATA", LCO_FE_Utils.cutString(tpte.get_ValueAsString("DianRegimeCode"), 2));
								// M AN 1 Max 30, Obligaciones del contribuyente
								addHeaderElement(mmDoc, "cbc:TaxLevelCode", codigosRUT.substring(0, codigosRUT.length()-1), atts);
								atts.clear();
	
							}
							if (nobpeinfo) {
								msg = (Msg.translate(Env.getCtx(), "FillMandatory") + " "
										+ Msg.getElement(Env.getCtx(), MBPartner.COLUMNNAME_C_BPartner_ID) + "-"
										+ Msg.getElement(Env.getCtx(), "LCO_FE_TributaryType_ID"));
								return "@Error@ @invoice.no@ " + inv.getDocumentNo() + " " + msg;
							}
						}
						// M 1..1 Dirección fiscal del emisor. 
						mmDoc.startElement("","","cac:RegistrationAddress", atts);
							// M N 5 Codigo del municipio
							addHeaderElement(mmDoc, "cbc:ID", LCO_FE_Utils.cutString(lo.getC_City().getPostal(), 5), atts);
							// M AN Max 60 Nombre de la ciudad
							addHeaderElement(mmDoc, "cbc:CityName", lo.getC_City().getName(), atts);
							// M AN Max 60 Nombre del Departamento
							addHeaderElement(mmDoc, "cbc:CountrySubentity", lo.getRegionName(), atts);
							// M N 1..5 Código del Departamento
							addHeaderElement(mmDoc, "cbc:CountrySubentityCode", LCO_FE_Utils.cutString(lo.getRegion().getDescription(), 2), atts);
							// X AN Max 40 Municipio (distrito administrativo)
							// addHeaderElement(mmDoc, "cbc:CitySubdivisionName", lo.getC_City().getName(), atts);
							mmDoc.startElement("","","cac:AddressLine", atts);
								// M AN Max 300 Direccion
								addHeaderElement(mmDoc, "cbc:Line", lo.getAddress1(), atts);
							mmDoc.endElement("","","cac:AddressLine");
							mmDoc.startElement("","","cac:Country", atts);
								// M AN Max 2 Pais Tabla 8
								addHeaderElement(mmDoc, "cbc:IdentificationCode", lo.getCountry().getCountryCode(), atts);
								// O A 4..40 Nombre Pais Tabla 8
								atts.addAttribute("", "", "languageID", "CDATA", LCO_FE_Utils.cutString(Env.getAD_Language(Env.getCtx()), 2));
								addHeaderElement(mmDoc, "cbc:Name", lo.getCountry().get_Translation("Name"), atts);
								atts.clear();
							mmDoc.endElement("","","cac:Country");
						mmDoc.endElement("","","cac:RegistrationAddress");
						//
						if (tpte.get_ValueAsString("DianRegimeCode").equals(LCO_FE_Utils.TIPO_REGIMEN_FISCAL_48)) {
							// M 1 Veces max
							mmDoc.startElement("","","cac:TaxScheme", atts);
								// M AF 3..10, Identificador del tributo
								addHeaderElement(mmDoc, "cbc:ID", LCO_FE_Utils.CODIGO_IVA_01, atts);	// TipoImpuesto
								// M AF 10..30, Nombre del tributo
								addHeaderElement(mmDoc, "cbc:Name", "IVA", atts);	// TODO NombreImpuesto
							mmDoc.endElement("","","cac:TaxScheme");
						}
					mmDoc.endElement("","","cac:PartyTaxScheme");
					mmDoc.startElement("","","cac:PartyLegalEntity", atts);
						// M AF 5..450, Nombre o Razón Social del emisor
						addHeaderElement(mmDoc, "cbc:RegistrationName", bpe.getName(), atts);
						atts.clear();
						atts = addGovAttribute21(bpe.get_ValueAsString("TaxIdDigit"), LCO_FE_Utils.cutString(tte.get_ValueAsString("LCO_TaxCodeDian"), 7));	// TODO Reviewme
						// M N 5..12, NIT del emisor
						addHeaderElement(mmDoc, "cbc:CompanyID", bpe.getTaxID().trim(), atts);
						atts.clear();
						mmDoc.startElement("","","cac:CorporateRegistrationScheme", atts);
							if (! "".equals(m_Prefix))
								// M N 6, Prefijo de la facturación usada para el punto de venta
								addHeaderElement(mmDoc, "cbc:ID", m_Prefix, atts);	// TODO reviewme
								// O N 6, Número de matrícula mercantil (identificador de sucursal: punto de facturación) 
								// addHeaderElement(mmDoc, "cbc:Name", m_Prefix, atts);	// TODO
						mmDoc.endElement("","","cac:CorporateRegistrationScheme");
					mmDoc.endElement("","","cac:PartyLegalEntity");
					String mailContacto = MSysConfig.getValue("QSSLCO_FE_ReplyToEMail", null, inv.getAD_Client_ID());
					if (mailContacto != null) {
						mmDoc.startElement("","","cac:Contact", atts);
							// O AN Max 250 Correo electrónico  de la persona de contacto.
							addHeaderElement(mmDoc, "cbc:ElectronicMail", mailContacto, atts);
						mmDoc.endElement("","","cac:Contact");
					}
				mmDoc.endElement("","","cac:Party");
			mmDoc.endElement("","","cac:AccountingSupplierParty");
			
			// Informacion Adquiriente
			mmDoc.startElement("","","cac:AccountingCustomerParty", atts);
				// M N 1, Tipo de persona Tabla 9
				addHeaderElement(mmDoc, "cbc:AdditionalAccountID", tpta.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_GRAN_CONTRIBUYENTE) ? LCO_FE_Utils.TIPO_PERSONA_JURIDICA : tpta.get_ValueAsString("DianTaxPayerCode"), atts);
				mmDoc.startElement("","","cac:Party", atts);
					mmDoc.startElement("","","cac:PartyIdentification", atts);
						atts.clear();
						// M AN 7, Tipo identificaci\u00f3n Tabla 2
						atts = addGovAttribute21(null, LCO_FE_Utils.cutString(tta.get_ValueAsString("LCO_TaxCodeDian"), 7));
						// M AF Max 35, No. Identificacion Adquiriente
						addHeaderElement(mmDoc, "cbc:ID", bp.getTaxID(), atts);
						atts.clear();
					mmDoc.endElement("","","cac:PartyIdentification");
					if ( tpta.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_PERSONA_JURIDICA)
							|| tpta.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_GRAN_CONTRIBUYENTE) ) {
						mmDoc.startElement("","","cac:PartyName", atts);
							// D AN 5..450 Nombre comercial del adquiriente
							addHeaderElement(mmDoc, "cbc:Name", bp.getName(), atts);
						mmDoc.endElement("","","cac:PartyName");
					} else if ( tpta.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_PERSONA_NATURAL) ) {
						mmDoc.startElement("","","cac:Person", atts);	// MAx 450
							// D AN Max 150 Nombre del adquiriente
							addHeaderElement(mmDoc, "cbc:FirstName", bp.get_ValueAsString("FirstName1"), atts);
							// D AN Max 150 Nombre del adquiriente
							addHeaderElement(mmDoc, "cbc:MiddleName", bp.get_Value("FirstName2") != null ? bp.get_ValueAsString("FirstName2") : "", atts);
							// D AN Max 150 Primer y segundo Apellido
							addHeaderElement(mmDoc, "cbc:FamilyName", bp.get_ValueAsString("LastName1")
									+ bp.get_Value("LastName2") != null ? " " + bp.get_ValueAsString("LastName2") : "", atts);
						mmDoc.endElement("","","cac:Person");
					}
					mmDoc.startElement("","","cac:PhysicalLocation", atts);
						mmDoc.startElement("","","cac:Address", atts);
							if (la.getC_City().getPostal() != null)
								// M N 5 Codigo del municipio
								addHeaderElement(mmDoc, "cbc:ID", LCO_FE_Utils.cutString(la.getC_City().getPostal(), 5), atts);
							if (la.getC_City().getName() != null)
								// M AN Max 60 Nombre de la ciudad
								addHeaderElement(mmDoc, "cbc:CityName", la.getC_City().getName(), atts);
							if (la.getRegionName() != null)
								// M AN Max 60 Nombre del Departamento
								addHeaderElement(mmDoc, "cbc:CountrySubentity", la.getRegionName(), atts);
							// M N 1..5 Código del Departamento
							addHeaderElement(mmDoc, "cbc:CountrySubentityCode", LCO_FE_Utils.cutString(la.getRegion().getDescription(), 2), atts);
							// X AN Max 40 Municipio (distrito administrativo)
							// addHeaderElement(mmDoc, "cbc:CitySubdivisionName", la.getC_City().getName(), atts);
							mmDoc.startElement("","","cac:AddressLine", atts);
								// M AN Max 300 Direccion
								addHeaderElement(mmDoc, "cbc:Line", la.getAddress1(), atts);
							mmDoc.endElement("","","cac:AddressLine");
							mmDoc.startElement("","","cac:Country", atts);
								// M AN Max 2 Pais Tabla 8
								addHeaderElement(mmDoc, "cbc:IdentificationCode", la.getCountry().getCountryCode(), atts);
								// O A 4..40 Nombre Pais Tabla 8
								atts.addAttribute("", "", "languageID", "CDATA", LCO_FE_Utils.cutString(Env.getAD_Language(Env.getCtx()), 2));
								addHeaderElement(mmDoc, "cbc:Name", la.getCountry().get_Translation("Name"), atts);
								atts.clear();
							mmDoc.endElement("","","cac:Country");
						mmDoc.endElement("","","cac:Address");
					mmDoc.endElement("","","cac:PhysicalLocation");
					
					mmDoc.startElement("","","cac:PartyTaxScheme", atts);
						// M AF 5..450, Nombre o Razón Social del adquiriente
						addHeaderElement(mmDoc, "cbc:RegistrationName", bp.getName(), atts);
						atts.clear();
						atts = addGovAttribute21(bp.get_ValueAsString("TaxIdDigit"), LCO_FE_Utils.cutString(tta.get_ValueAsString("LCO_TaxCodeDian"), 7));	// TODO Reviewme
						// M N 5..12, No. Identificacion Adquiriente
						addHeaderElement(mmDoc, "cbc:CompanyID", bp.getTaxID(), atts);
						atts.clear();
						{ // BP Info Adquiriente
							boolean nobpainfo = true;
							String codigosRUT = "";
							List<List<Object>> rowsa = DB.getSQLArrayObjectsEx(bp.get_TrxName(), LCO_FE_Utils.SQL_BP_INFO, bp.getC_BPartner_ID());
							if (rowsa != null) {
								for (List<Object> rowa : rowsa) {
									// String grupoRUT = rowa.get(0).toString();
									String codigoRUT = rowa.get(1).toString();
									if (Util.isEmpty(codigoRUT, true)) {
										nobpainfo = true;
										break;
									}
									nobpainfo = false;
									codigosRUT = codigosRUT + codigoRUT + ";";
								}
								atts.clear();
								atts.addAttribute("", "", "listName", "CDATA", LCO_FE_Utils.cutString(tpta.get_ValueAsString("DianRegimeCode"), 2));
								// M AN 1 Max 30, Obligaciones del contribuyente
								addHeaderElement(mmDoc, "cbc:TaxLevelCode", LCO_FE_Utils.cutString(codigosRUT.substring(0, codigosRUT.length()-1), 30), atts);
								atts.clear();
								
							}
							if (nobpainfo) {
								msg = (Msg.translate(Env.getCtx(), "FillMandatory") + " "
										+ Msg.getElement(Env.getCtx(), MBPartner.COLUMNNAME_C_BPartner_ID) + "-"
										+ Msg.getElement(Env.getCtx(), "LCO_FE_TributaryType_ID"));
								return "@Error@ @invoice.no@ " + inv.getDocumentNo() + " " + msg;
							}
						}
						List<List<Object>> taxas = DB.getSQLArrayObjectsEx(inv.get_TrxName(), LCO_FE_Utils.SQL_TAX_INFO, inv.getC_Invoice_ID(), "%", inv.getC_Invoice_ID(), "%");
						if (taxas != null) {
							for (List<Object> taxa : taxas) {
								// if (taxa != null && taxa.get(3).toString().equals(codigoIva_01)) {
									// M 1 Veces max
									mmDoc.startElement("","","cac:TaxScheme", atts);
										// M AF 3..10, Identificador del tributo
										addHeaderElement(mmDoc, "cbc:ID", taxa.get(3).toString(), atts);	// TipoImpuesto
										// M AF 10..35, Nombre del tributo
										addHeaderElement(mmDoc, "cbc:Name", LCO_FE_Utils.cutString(taxa.get(6).toString(), 35), atts);	// NombreImpuesto
									mmDoc.endElement("","","cac:TaxScheme");
								// }
								break;	// First
							}
						}
					mmDoc.endElement("","","cac:PartyTaxScheme");
					mmDoc.startElement("","","cac:PartyLegalEntity", atts);
						// M AF 5..450, Nombre o Razón Social del adquiriente
						addHeaderElement(mmDoc, "cbc:RegistrationName", bp.getName(), atts);
						atts.clear();
						atts = addGovAttribute21(bp.get_ValueAsString("TaxIdDigit"), LCO_FE_Utils.cutString(tta.get_ValueAsString("LCO_TaxCodeDian"), 7));	// TODO Reviewme
						// M N 5..12, ID del Adquiriente
						addHeaderElement(mmDoc, "cbc:CompanyID", bp.getTaxID(), atts);
						atts.clear();
					mmDoc.endElement("","","cac:PartyLegalEntity");
					if (ua.getEMail() != null) {
						mmDoc.startElement("","","cac:Contact", atts);
							// O AN Max 250 Correo electrónico  de la persona de contacto.
							addHeaderElement(mmDoc, "cbc:ElectronicMail", ua.getEMail().trim(), atts);
						mmDoc.endElement("","","cac:Contact");
					}
				mmDoc.endElement("","","cac:Party");
			mmDoc.endElement("","","cac:AccountingCustomerParty");
			
			if (m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_FACTURA)) {
				// M 1..1, Método de pago
				mmDoc.startElement("","","cac:PaymentMeans",atts);
				// O AN Max 3 Código correspondiente al medio de pago Tabla 5
				addHeaderElement(mmDoc, "cbc:ID", inv.getPaymentRule().equals(MInvoice.PAYMENTRULE_DirectDeposit) ? LCO_FE_Utils.MEDIO_PAGO_DEBITO_CA : LCO_FE_Utils.MEDIO_PAGO_NO_DEFINIDO, atts);
				// D AN Max 3 Método de pago. Tabla 26
				if (inv.getPaymentRule().equals(MInvoice.PAYMENTRULE_OnCredit))
					addHeaderElement(mmDoc, "cbc:PaymentMeansCode", LCO_FE_Utils.METODO_PAGO_CREDITO, atts);
				else if (inv.getPaymentRule().equals(MInvoice.PAYMENTRULE_DirectDeposit))
					addHeaderElement(mmDoc, "cbc:PaymentMeansCode", LCO_FE_Utils.METODO_PAGO_CONTADO, atts);
				// D AN Max 10 Fecha de pago Formato AAAA-MM-DD
				if (inv.getPaymentRule().equals(MInvoice.PAYMENTRULE_OnCredit)) {
					Object[] args = new Object[] { inv.getC_PaymentTerm_ID(), (Timestamp) inv.get_Value("LCO_FE_DateTrx")};
					Timestamp duedate = DB.getSQLValueTSEx(null, "SELECT paymenttermduedate(?, ?)", args);
					addHeaderElement(mmDoc, "cbc:PaymentDueDate", LCO_FE_Utils.getDateTime(duedate, 11), atts);
				}
				mmDoc.endElement("","","cac:PaymentMeans");
			}
			
			// E 1..N, Término de pago
			// mmDoc.startElement("","","cac:PaymentTerms", atts);
				// Elementos opcionales
				// O N 1, Término de pago
				// addHeaderElement(mmDoc, "cbc:ReferenceEventCode", inv.getC_PaymentTerm().getValue(), atts);	// TODO Homologar
			// mmDoc.endElement("","","cac:PaymentTerms");
			
			{ // Impuestos
				List<List<Object>> rows = DB.getSQLArrayObjectsEx(inv.get_TrxName(), LCO_FE_Utils.SQL_TAX_GROUP, inv.getC_Invoice_ID(), inv.getC_Invoice_ID());
				if (rows != null) {
					// No y retenidos
					for (List<Object> row : rows) {
						boolean EsRetencion = (row.get(0) != null && ((String)row.get(0)).equals("Y")) ? true : false;
						BigDecimal ImporteImpuesto = (BigDecimal) row.get(1);
						String Moneda = (String) row.get(2);
						String TipoImpuestoGrupo = (String) row.get(3);
						BigDecimal BaseImponible = (BigDecimal) row.get(4);
						BigDecimal PorcentajeImpuesto = Env.ZERO;
						String NombreImpuesto = (String) row.get(5);
						if (Util.isEmpty(TipoImpuestoGrupo, true)) {
							msg = (Msg.translate(Env.getCtx(), "FillMandatory") + " "
									+ Msg.getElement(Env.getCtx(), MTaxCategory.COLUMNNAME_C_TaxCategory_ID) + "-"
									+ Msg.getElement(Env.getCtx(), "IsWithHolding") + "-"
									+ Msg.getElement(Env.getCtx(), "LCO_FE_DianTaxCategoryCode"));
							return "@Error@ @invoice.no@ " + inv.getDocumentNo() + " " + msg;
						}
						// Impuestos Retenidos
						if (EsRetencion && !m_IsAutoRetenedor)
							continue;
						if (TipoImpuestoGrupo.equals(LCO_FE_Utils.CODIGO_RETFTE_06))	// No Reporta ?
							continue;
						// O 50 Veces max Impuestos
						if (!EsRetencion)
							mmDoc.startElement("","","cac:TaxTotal",atts);
						else
							mmDoc.startElement("","","cac:WithholdingTaxTotal",atts);
							atts.clear();
							atts.addAttribute("", "", "currencyID", "CDATA", Moneda);
							// M N 14.4 Importe Impuesto: Importe del impuesto retenido
							addHeaderElement(mmDoc, "cbc:TaxAmount", LCO_FE_Utils.decimalFormat(ImporteImpuesto), atts);
							// M N 14.4 Redondeo agregado al total del impuesto
							addHeaderElement(mmDoc, "cbc:RoundingAmount", LCO_FE_Utils.decimalFormat(Env.ZERO), atts);
							atts.clear();
							// M AN 5 Impuesto retenido o retenci\u00f3n
							// addHeaderElement(mmDoc, "cbc:TaxEvidenceIndicator", EsRetencion ? "true" : "false", atts);
						
							List<List<Object>> rowsi = DB.getSQLArrayObjectsEx(inv.get_TrxName(), LCO_FE_Utils.SQL_TAX_INFO, inv.getC_Invoice_ID(), TipoImpuestoGrupo, inv.getC_Invoice_ID(), TipoImpuestoGrupo);
							for (List<Object> rowi : rowsi) {
								EsRetencion = (rowi.get(0) != null && ((String)rowi.get(0)).equals("Y")) ? true : false;
								ImporteImpuesto = (BigDecimal) rowi.get(1);
								Moneda = (String) rowi.get(2);
								String TipoImpuesto = (String) rowi.get(3);
								BaseImponible = (BigDecimal) rowi.get(4);
								PorcentajeImpuesto = (BigDecimal) rowi.get(5);
								NombreImpuesto = (String) rowi.get(6);
								boolean EsExento = (rowi.get(7) != null && ((String)rowi.get(7)).equals("Y")) ? true : false;
								if (TipoImpuestoGrupo.equals(LCO_FE_Utils.CODIGO_IVA_01) && !EsExento && PorcentajeImpuesto.compareTo(Env.ZERO) == 0)
									continue;
								// O 50 Veces max Total Impuestos.
								atts.clear();
								mmDoc.startElement("","","cac:TaxSubtotal",atts);
									atts.clear();
									atts.addAttribute("", "", "currencyID", "CDATA", Moneda);
									// M N 14.4 Base Imponible
									addHeaderElement(mmDoc, "cbc:TaxableAmount", LCO_FE_Utils.decimalFormat(BaseImponible), atts);
									atts.clear();
									atts.addAttribute("", "", "currencyID", "CDATA", Moneda);
									// M N 14.4 Importe Impuesto (detalle)
									addHeaderElement(mmDoc, "cbc:TaxAmount", LCO_FE_Utils.decimalFormat(ImporteImpuesto), atts);
									atts.clear();
									mmDoc.startElement("","","cac:TaxCategory",atts);
										// O N 3.3 Porcentaje del impuesto
										addHeaderElement(mmDoc, "cbc:Percent", LCO_FE_Utils.decimalFormat(PorcentajeImpuesto), atts);
										mmDoc.startElement("","","cac:TaxScheme",atts);
											// M AN 3..10 Identificador del tributo Tabla 5
											addHeaderElement(mmDoc, "cbc:ID", TipoImpuesto, atts);
											// M AN 10..30 Nombre del tributo Tabla 5
											addHeaderElement(mmDoc, "cbc:Name", NombreImpuesto, atts);
										mmDoc.endElement("","","cac:TaxScheme");
									mmDoc.endElement("","","cac:TaxCategory");
								mmDoc.endElement("","","cac:TaxSubtotal");
								if (EsRetencion)
									BaseImponible = Env.ZERO;	// No suma
								if (!TipoImpuesto.equals(LCO_FE_Utils.CODIGO_NO_CAUSA_ZY)) {
									if (TipoImpuesto.equals(LCO_FE_Utils.CODIGO_IVA_01) && !EsExento && PorcentajeImpuesto.compareTo(Env.ZERO) == 0)
										m_totalbaseexcluida = m_totalbaseexcluida.add(BaseImponible);
									else
										m_totalbaseimponible = m_totalbaseimponible.add(BaseImponible);
									if (!EsRetencion)
										m_totalvalorimpuesto = m_totalvalorimpuesto.add(ImporteImpuesto);
									else
										m_totalretenciones = m_totalretenciones.add(ImporteImpuesto);
								}
							}
						if (!EsRetencion)
							mmDoc.endElement("","","cac:TaxTotal");
						else
							mmDoc.endElement("","","cac:WithholdingTaxTotal");
					}
				}
			}
			
			m_totalvalorimpuesto = m_totalvalorimpuesto.setScale(0, RoundingMode.HALF_UP);	// TODO Reviewme

			// M 1 Valores Totales
			mmDoc.startElement("","","cac:LegalMonetaryTotal", atts);
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// M N 14.4 Total Valor Bruto 
				addHeaderElement(mmDoc, "cbc:LineExtensionAmount", LCO_FE_Utils.decimalFormat(inv.getTotalLines()), atts);
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// M N 14.4 Total Valor Base Imponible menos Excluidos
				addHeaderElement(mmDoc, "cbc:TaxExclusiveAmount", LCO_FE_Utils.decimalFormat(m_totalbaseimponible), atts);	// TODO Excluidos
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// M N 14.4 Total de Valor Bruto más tributos
				addHeaderElement(mmDoc, "cbc:TaxInclusiveAmount", LCO_FE_Utils.decimalFormat(inv.getTotalLines().add(m_totalvalorimpuesto)), atts);	// TODO Reviewme
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// O N 14.4 Descuento Total
				if (inv.isDiscountPrinted())
					addHeaderElement(mmDoc, "cbc:AllowanceTotalAmount", LCO_FE_Utils.decimalFormat(m_totaldescuento), atts);	// TODO
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// O N 14.4 Cargo Total
				addHeaderElement(mmDoc, "cbc:ChargeTotalAmount", LCO_FE_Utils.decimalFormat(m_totalcargo), atts);	// TODO
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// O N 14.4 Anticipo Total
				addHeaderElement(mmDoc, "cbc:PrepaidAmount", LCO_FE_Utils.decimalFormat(m_totalanticipo), atts);	// TODO
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// O N 14.4 Diferencia Total
				addHeaderElement(mmDoc, "cbc:PayableRoundingAmount", LCO_FE_Utils.decimalFormat(Env.ZERO), atts);	// TODO
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// M N 14.4 Valor a Pagar de Factura
				addHeaderElement(mmDoc, "cbc:PayableAmount", LCO_FE_Utils.decimalFormat(inv.getGrandTotalPlusWithholdings()), atts);
				atts.clear();
			mmDoc.endElement("","","cac:LegalMonetaryTotal");
			
			Object[] args = null;
			// !m_IsExport
			int lco_fe_productscheme_id = -1;
			X_LCO_FE_ProductScheme feps = null;
			args = new Object[] { inv.getAD_Client_ID(), "Y", "%" };
			lco_fe_productscheme_id = DB.getSQLValueEx(inv.get_TrxName(), LCO_FE_Utils.SQL_SCHEMA, args);
			feps = new X_LCO_FE_ProductScheme (inv.getCtx(), lco_fe_productscheme_id, inv.get_TrxName());
			//
			X_LCO_FE_ProductScheme fepspa = null;
			if (m_IsExport) {
				args = new Object[] { inv.getAD_Client_ID(), "%", LCO_FE_Utils.PRODUCT_SCHEME_PAID };
				lco_fe_productscheme_id = DB.getSQLValueEx(inv.get_TrxName(), LCO_FE_Utils.SQL_SCHEMA, args);
				fepspa = new X_LCO_FE_ProductScheme (inv.getCtx(), lco_fe_productscheme_id, inv.get_TrxName());
			}
			
			{ // Items
				// M 1500 Veces max \u00cdtems del Documento // TODO
				List<List<Object>> rows = DB.getSQLArrayObjectsEx(inv.get_TrxName(), LCO_FE_Utils.SQL_ITEMS, inv.getC_Invoice_ID());
				if (rows == null) {
					msg = (Msg.translate(Env.getCtx(), "NoLines") + " "
							+ Msg.getElement(Env.getCtx(), MInvoice.COLUMNNAME_C_Invoice_ID));
					return "@Error@ @invoice.no@ " + inv.getDocumentNo() + " " + msg;
				}
				// if (rows != null) {
				    int i = 1;
					for (List<Object> row : rows) {
						BigDecimal Consecutivo = new BigDecimal(i);
						i++;
						boolean EsGratis = (row.get(1) != null && ((String)row.get(1)).equals("true")) ? true : false;
						BigDecimal qtyinvoiced = (BigDecimal) row.get(2);
						String UnidadMedida = (String) row.get(3);
						BigDecimal CostoTotal = (BigDecimal) row.get(4);
						String Iso_Code = (String) row.get(5);
						BigDecimal PrecioUnitario = (BigDecimal) row.get(6);
						String Iso_CodeDup = (String) row.get(7);
						//String pidentificador = (String) row.get(8);
						//String pdescription = (String) row.get(9);
						String ildescription = (String) row.get(10);
						//String name = (String) row.get(11);
						BigDecimal Descuento = (BigDecimal) row.get(12);
						String pcodigo = (String) row.get(13);
						boolean EsRetencion = (row.get(14) != null && ((String)row.get(14)).equals("Y")) ? true : false;
						String TipoImpuesto = (String) row.get(15);
						BigDecimal ImporteImpuesto = (BigDecimal) row.get(16);
						String MonedaImporte = (String) row.get(17);
						BigDecimal BaseImponible = (BigDecimal) row.get(18);
						String MonedaBase = (String) row.get(19);
						BigDecimal PorcentajeImpuesto = (BigDecimal) row.get(20);
						String NombreImpuesto = (String) row.get(21);
						String TipoProducto = (String) row.get(22);
						String CodigoEstandar = (String) row.get(23);
						String PartidaArancelaria = (String) row.get(24);
						boolean EsExento = (row.get(25) != null && ((String)row.get(25)).equals("Y")) ? true : false;

						atts.clear();
						mmDoc.startElement("","","cac:InvoiceLine",atts);
							// M N 1..4, Número de Línea
							addHeaderElement(mmDoc, "cbc:ID", LCO_FE_Utils.cutString(Consecutivo.toString(), 4), atts);	// TODO
							atts.addAttribute("", "", "unitCode", "CDATA", UnidadMedida);
							// M N 14.4, Cantidad del art\u00edculo solicitado
							addHeaderElement(mmDoc, "cbc:InvoicedQuantity", String.format(Locale.US, "%.2f", qtyinvoiced), atts);
							atts.clear();
							atts.addAttribute("", "", "currencyID", "CDATA", Iso_Code);
							// D N 14.4, Costo Total
							addHeaderElement(mmDoc, "cbc:LineExtensionAmount", LCO_FE_Utils.decimalFormat(CostoTotal), atts);
							atts.clear();
							addHeaderElement(mmDoc, "cbc:FreeOfChargeIndicator", (EsGratis ? "true" : "false"), atts);
							// O 50 Veces max Total Impuestos \u00cdtem.
							atts.clear();
							if (!TipoImpuesto.equals(LCO_FE_Utils.CODIGO_NO_CAUSA_ZY)) {
							mmDoc.startElement("","","cac:TaxTotal",atts);
								// if (!isTimHeader) {
									atts.clear();
									atts.addAttribute("", "", "currencyID", "CDATA", MonedaImporte);
									// M N 14.4 Importe Impuesto: Importe del impuesto retenido
									addHeaderElement(mmDoc, "cbc:TaxAmount", LCO_FE_Utils.decimalFormat(ImporteImpuesto), atts);
									// M N 14.4 Redondeo agregado al total del impuesto
									addHeaderElement(mmDoc, "cbc:RoundingAmount", LCO_FE_Utils.decimalFormat(Env.ZERO), atts);
									atts.clear();
									// M AN 5 Impuesto retenido o retenci\u00f3n
									// addHeaderElement(mmDoc, "cbc:TaxEvidenceIndicator", EsRetencion ? "true" : "false", atts);
									// isTimHeader = true;
								// }
								// O 50 Veces max Impuestos
								mmDoc.startElement("","","cac:TaxSubtotal",atts);
									atts.clear();
									atts.addAttribute("", "", "currencyID", "CDATA", MonedaBase);
									// M N 14.4 Base Imponible
									addHeaderElement(mmDoc, "cbc:TaxableAmount", LCO_FE_Utils.decimalFormat(BaseImponible), atts);
									atts.clear();
									atts.addAttribute("", "", "currencyID", "CDATA", MonedaImporte);
									// M N 14.4 Importe Impuesto (detalle)
									addHeaderElement(mmDoc, "cbc:TaxAmount", LCO_FE_Utils.decimalFormat(ImporteImpuesto), atts);
									atts.clear();
									mmDoc.startElement("","","cac:TaxCategory",atts);
										// O N 3.3 Porcentaje del impuesto
										addHeaderElement(mmDoc, "cbc:Percent", LCO_FE_Utils.decimalFormat(PorcentajeImpuesto), atts);
										mmDoc.startElement("","","cac:TaxScheme",atts);
											// M AN 3..10 Identificador del tributo Tabla 5
											addHeaderElement(mmDoc, "cbc:ID", TipoImpuesto, atts);
											// M AN 10..30 Nombre del tributo Tabla 5
											addHeaderElement(mmDoc, "cbc:Name", NombreImpuesto, atts);
										mmDoc.endElement("","","cac:TaxScheme");
									mmDoc.endElement("","","cac:TaxCategory");
								mmDoc.endElement("","","cac:TaxSubtotal");
							mmDoc.endElement("","","cac:TaxTotal");
							}
							//
							mmDoc.startElement("","","cac:Item",atts);
								if (ildescription != null) {
									// O AN 5..300, Descripci\u00f3n
									addHeaderElement(mmDoc, "cbc:Description", LCO_FE_Utils.cutString(ildescription, 300), atts);
								// M 3 Veces max IDENTIFICACIÓN DEL ARTÍCULO O SERVICIO DE ACUERDO CON UN ESTÁNDAR
								}
								mmDoc.startElement("","","cac:StandardItemIdentification",atts);
								if ( !(m_IsExport || !la.getCountry().getCountryCode().equals(LCO_FE_Utils.COUNTRY_CODE_CO)) ) {
									// M AN Max 3, Código del estándar Tabla 31
									atts.addAttribute("", "", "schemeID", "CDATA", feps.getValue());
									// M AN Max 2, Código del estándar Tabla 31
									atts.addAttribute("", "", "schemeAgencyID", "CDATA", feps.getIdAgency());
									// M AN Max 35, Nombre del estándar Tabla 31
									atts.addAttribute("", "", "schemeName", "CDATA", feps.getName());
									// M AN Max 35, Código del producto segun IAE_2
									addHeaderElement(mmDoc, "cbc:ID", CodigoEstandar, atts);	// Regla: ZB01 Also Charge CodigoEstandar
								} else {
									atts.addAttribute("", "", "schemeID", "CDATA", fepspa.getValue());
									atts.addAttribute("", "", "schemeAgencyID", "CDATA", fepspa.getIdAgency());
									atts.addAttribute("", "", "schemeName", "CDATA", fepspa.getName());
									addHeaderElement(mmDoc, "cbc:ID", TipoProducto.equals(MProduct.PRODUCTTYPE_Item) ? PartidaArancelaria : LCO_FE_Utils.CODIGO_PA_DEFAULT, atts);
								}
								mmDoc.endElement("","","cac:StandardItemIdentification");
							mmDoc.endElement("","","cac:Item");
							atts.clear();
							mmDoc.startElement("","","cac:Price",atts);
								atts.addAttribute("", "", "currencyID", "CDATA", Iso_CodeDup);
								// D N 14.4, Precio Unitario
								addHeaderElement(mmDoc, "cbc:PriceAmount", LCO_FE_Utils.decimalFormat(PrecioUnitario), atts);
								atts.clear();
								// D A 1..3, Código del tipo de precio informado Tabla 6.3.10
								// addHeaderElement(mmDoc, "cbc:PriceTypeCode", Iso_CodeDup, atts);	// TODO reviewme
								atts.addAttribute("", "", "unitCode", "CDATA", UnidadMedida);
								// M N 1..6, Cantidad del art\u00edculo solicitado
								addHeaderElement(mmDoc, "cbc:BaseQuantity", String.format(Locale.US, "%.2f", qtyinvoiced), atts);
								atts.clear();
							mmDoc.endElement("","","cac:Price");
						mmDoc.endElement("","","cac:InvoiceLine");
						
						m_sumanolineas ++;
						
						/* // TODO Retenidos: Se reporta una sola vez la estructura item WithholdingTaxTotal similar a TaxTotal
						// Regla: FAS07, Rechazo:  El valor del tributo informado no corresponde al producto del ..
						if (m_sumanolineas == 1) {
							List<List<Object>> rowsz = DB.getSQLArrayObjectsEx(inv.get_TrxName(), LCO_FE_Utils.SQL_TAX_GROUP, inv.getC_Invoice_ID(), inv.getC_Invoice_ID());
							if (rowsz != null) {
								for (List<Object> rowz : rowsz) {
									EsRetencion = (rowz.get(0) != null && ((String)row.get(0)).equals("Y")) ? true : false;
									String TipoImpuestoGrupo = (String) row.get(3);
									// ..
									if (!EsRetencion)
										continue;
									if (EsRetencion && !m_IsAutoRetenedor)
										continue;
									//if (TipoImpuestoGrupo.equals(LCO_FE_Utils.CODIGO_RETFTE_06) && !m_IsGranContribuyente)	// Revieme
									//	continue;
									// ..
								}
							}
						} */
						
						if (inv.isDiscountPrinted())
							m_sumadescuento = m_sumadescuento.add(Descuento);
						if (EsRetencion)
							BaseImponible = Env.ZERO;	// No suma
						if (!TipoImpuesto.equals(LCO_FE_Utils.CODIGO_NO_CAUSA_ZY)) {
							if (TipoImpuesto.equals(LCO_FE_Utils.CODIGO_IVA_01) && !EsExento && PorcentajeImpuesto.compareTo(Env.ZERO) == 0)
								m_sumabaseexcluida = m_sumabaseexcluida.add(BaseImponible);
							else
								m_sumabaseimponible = m_sumabaseimponible.add(BaseImponible);
							if (!EsRetencion)
								m_sumavalorimpuesto = m_sumavalorimpuesto.add(ImporteImpuesto);
							else
								m_sumavalorretenciones = m_sumavalorretenciones.add(ImporteImpuesto);
						}	
					}
				}
			// }

			mmDoc.endElement("","",m_Root_Node);

			mmDoc.endDocument();

			if (mmDocStream != null) {
				try {
					mmDocStream.close();
				} catch (Exception e2) {}
			}

			if (m_sumanolineas != m_totalnolineas) {
				msg = "@DiffParentValue@ @NoOfLines@ @Total@: " + m_totalnolineas + "@sales.details@" + m_sumanolineas;
				return "@Error@ @invoice.no@ "+ inv.getDocumentNo() + " " + msg;
			}
			
			if (inv.isDiscountPrinted() && m_sumadescuento.compareTo(m_totaldescuento) != 0 ) {
				msg = "@DiffParentValue@ @discount.amt@ @Total@: " + m_totaldescuento.toString() + " @sales.details@ " + m_sumadescuento.toString();
				s_log.warning("@Error@ @invoice.no@ "+ inv.getDocumentNo() + " " + msg);
				msg = null;	// TODO Bypass temp
			}

			if (m_sumabaseimponible.compareTo(m_totalbaseimponible) != 0
					&& m_totalbaseimponible.subtract(m_sumabaseimponible).abs().compareTo(LCO_FE_Utils.CERO_PUNTO_CINCO) > 1) {
				msg = "@DiffParentValue@ @TaxBase@ @Total@: " + m_totalbaseimponible.toString() + " @sales.details@ " + m_sumabaseimponible.toString();
				return "@Error@ @invoice.no@ "+ inv.getDocumentNo() + " " + msg;
			}

			if (m_sumavalorimpuesto.compareTo(m_totalvalorimpuesto) != 0
					&& m_totalvalorimpuesto.subtract(m_sumavalorimpuesto).abs().compareTo(LCO_FE_Utils.CERO_PUNTO_CINCO) > 1) {
				msg = "@DiffParentValue@ @smenu.tax@ @Total@: " + m_totalvalorimpuesto.toString() + " @sales.details@ " + m_sumavalorimpuesto.toString();
				return "@Error@ @invoice.no@ "+ inv.getDocumentNo() + " " + msg;
			}

			//
		} catch (Exception e) {
			msg = Msg.getMsg(Env.getCtx(), "FileCannotCreate") + " "
					+ m_File_Type + "-"
					+ e.getLocalizedMessage();
			s_log.severe(msg);
			return msg;
		}

		return msg;

	} 	// lcofeinv_GovGeneraInvoiceXML210
	
	/**
	 * Genera XML NC v2.1
	 * Prefijo fe desaparece quedando el prefijo propio que maneja el UBL: cac
	 * @param
	 * @return msg
	 */
	public String lcofeinvcr_GovGeneraCreditNoteXML210(
			String file_name,
			String m_coddoc,
			LCO_FE_MInvoice inv,
			MDocType dt,
			X_LCO_FE_DocType fedt,
			X_LCO_FE_OperationType feot,
			X_LCO_FE_DIAN_Format f,
			X_LCO_PrintedFormControl pfc,
			X_LCO_FE_OFE_Contingency feoc,
			MOrgInfo oi,
			MBPartner bpe,
			X_LCO_TaxPayerType tpte,
			X_LCO_TaxIdType tte,
			X_LCO_ISIC ie,
			MLocation lo,
			MLocation locc,
			MUser uo,
			X_LCO_FE_ContactType uoct,
			MBPartner bp,
			X_LCO_TaxPayerType tpta,
			X_LCO_TaxIdType tta,
			MLocation la,
			MLocation lacc,
			MUser ua,
			X_LCO_FE_ContactType uact,
			X_LCO_FE_NCConcept fencc,
			X_LCO_FE_NDConcept fendc
			)
	{

		m_sumanolineas = 0;
		m_sumadescuento = Env.ZERO;
		m_sumabaseimponible = Env.ZERO;
		m_sumavalorimpuesto = Env.ZERO;

		String msg = null;
		
		MInvoice invref = null;
		MLCOFEAuthorization autref = null;
		MDocType dtref = null;
		X_LCO_FE_DocType fedtref = null;
		X_LCO_FE_DIAN_Format fref = null;
		
		// Reference
		if (inv.getRelatedInvoice_ID() > 0 && inv.get_ValueAsInt("LCO_FE_OperationType_ID") != 1000033) {	// Nota Crédito sin referencia a facturas
			invref = new MInvoice (inv.getCtx(), inv.getRelatedInvoice_ID(), inv.get_TrxName());
			autref = new MLCOFEAuthorization (inv.getCtx(), invref.get_ValueAsInt("LCO_FE_Authorization_ID"), inv.get_TrxName());
			dtref = new MDocType(inv.getCtx(), invref.getC_DocTypeTarget_ID(), inv.get_TrxName());
			fedtref = new X_LCO_FE_DocType (inv.getCtx(), dtref.get_ValueAsInt("LCO_FE_DocType_ID"), inv.get_TrxName());
			fref = new X_LCO_FE_DIAN_Format (inv.getCtx(), autref.getLCO_FE_DIAN_Format_ID(), inv.get_TrxName());
		}

		try {

			OutputStream  mmDocStream = null;

			//Stream para el documento xml
			mmDocStream = new FileOutputStream (file_name, false);
			StreamResult streamResult_menu = new StreamResult(new OutputStreamWriter(mmDocStream,"UTF-8"));
			SAXTransformerFactory tf_menu = (SAXTransformerFactory) SAXTransformerFactory.newInstance();					
			try {
				tf_menu.setAttribute("indent-number", Integer.valueOf(2));
			} catch (Exception e) {
				// swallow
			}
			TransformerHandler mmDoc = tf_menu.newTransformerHandler();	
			Transformer serializer_menu = mmDoc.getTransformer();	
			serializer_menu.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			serializer_menu.setOutputProperty(OutputKeys.STANDALONE, "no");
			serializer_menu.setOutputProperty(OutputKeys.INDENT,"yes");
			// serializer_menu.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
			mmDoc.setResult(streamResult_menu);

			mmDoc.startDocument();

			AttributesImpl atts = new AttributesImpl();

			// Encabezado
			atts.clear();
			// atts.addAttribute("", "", "id", "CDATA", "comprobante");
			// atts.addAttribute("", "", "version", "CDATA", f.get_ValueAsString("VersionNo"));
			atts = addHeaderAttribute(fedt.getDianShortDocType(), LCO_FE_Utils.SIGNER_POLICY_V2); 

			mmDoc.startElement("", "", m_Root_Node, atts);

			atts.clear();

			// Emisor = Org
			// Adquiriente = BPartner
			// Requerido: M-Mandatorio, D-Dependiente, O-Opcional
			// Tipo: A-Alfabetico, AN-Alfanumrrico, N-Numerico
			// Long.

			// Informaci\u00f3n Cabecera
			
			// Extensions
			mmDoc.startElement("","","ext:UBLExtensions", atts);
				mmDoc.startElement("","","ext:UBLExtension", atts);
					mmDoc.startElement("","","ext:ExtensionContent", atts);
						mmDoc.startElement("","","sts:DianExtensions", atts);
							mmDoc.startElement("","","sts:InvoiceSource", atts);
								atts.clear();
								atts.addAttribute("", "", "listAgencyID", "CDATA", "6");
								atts.addAttribute("", "", "listAgencyName", "CDATA", "United Nations Economic Commission for Europe");
								atts.addAttribute("", "", "listSchemeURI", "CDATA", "urn:oasis:names:specification:ubl:codelist:gc:CountryIdentificationCode-2.1");
								// M A 2 Literal "CO"
								addHeaderElement(mmDoc, "cbc:IdentificationCode", lo.getCountry().getCountryCode(), atts);
								atts.clear();
							mmDoc.endElement("","","sts:InvoiceSource");
							mmDoc.startElement("","","sts:SoftwareProvider", atts);
								atts.clear();
								atts = addGovAttribute21(bpe.get_ValueAsString("TaxIdDigit"), LCO_FE_Utils.cutString(tte.get_ValueAsString("LCO_TaxCodeDian"), 7));
								// M N 5..12 NIT del Prestador de Servicios
								addHeaderElement(mmDoc, "sts:ProviderID", bpe.getTaxID().trim(), atts);
								atts.clear();
								atts = addGovAttribute21(null, null);
								// M AN, Identificador Software
								addHeaderElement(mmDoc, "sts:SoftwareID", oi.get_ValueAsString("LCO_FE_UserName"), atts);
								atts.clear();
							mmDoc.endElement("","","sts:SoftwareProvider");
							atts.clear();
							atts = addGovAttribute21(null, null);
							addHeaderElement(mmDoc, "sts:SoftwareSecurityCode", m_IdAccount, atts);
							atts.clear();
							// Proveedor Autorizado (PA) por la DIAN
							mmDoc.startElement("","","sts:AuthorizationProvider", atts);
								atts = addGovAttribute21("4", "31");	// NIT DV de DIAN 4
								// M N Debe corresponder al Nit de la DIAN
								addHeaderElement(mmDoc, "sts:AuthorizationProviderID", "800197268", atts);
							mmDoc.endElement("","","sts:AuthorizationProvider");
							atts.clear();
							// M AN
							addHeaderElement(mmDoc, "sts:QRCode", m_QR, atts);
						mmDoc.endElement("","","sts:DianExtensions");
					mmDoc.endElement("","","ext:ExtensionContent");
				mmDoc.endElement("","","ext:UBLExtension");
				mmDoc.startElement("","","ext:UBLExtension", atts);
					mmDoc.startElement("","","ext:ExtensionContent", atts);
						// <ds:Signature
						// </ds:Signature>
					mmDoc.endElement("","","ext:ExtensionContent");
				mmDoc.endElement("","","ext:UBLExtension");
			mmDoc.endElement("","","ext:UBLExtensions");
			//
			// M AF Max 7, Version UBL
			addHeaderElement(mmDoc, "cbc:UBLVersionID", LCO_FE_Utils.cutString(f.getUBLVersionNo(), 7), atts);	// UBL 2.1
			// M AN 1..4, Indicador del tipo de operación
			addHeaderElement(mmDoc, "cbc:CustomizationID", LCO_FE_Utils.cutString(feot.getValue(), 2), atts);
			// M AF Max 55, Version Formato
			addHeaderElement(mmDoc, "cbc:ProfileID", LCO_FE_Utils.cutString(f.getVersionNo() + ": " + f.getName(), 55), atts);	// DIAN 2.1
			// M N 1, Ambiente de Destino
			addHeaderElement(mmDoc, "cbc:ProfileExecutionID", m_EnvType, atts);
			// M AF Max 20, Prefijo + No. Documento Formato
			addHeaderElement(mmDoc, "cbc:ID", inv.getDocumentNo(), atts); // With Prefix getDocumentNo()
			atts.clear();
			atts = addGovAttribute21(m_EnvType, f.getLCO_FE_UuidType());
			// M AF 96, CUFE de la Nota Crédito. (CUDE según resolución) 
			addHeaderElement(mmDoc, "cbc:UUID", m_CUFE, atts);
			atts.clear();
			// M AN Max 10, Fecha de emisi\u00f3n de la factura Formato AAAA-MM-DD
			addHeaderElement(mmDoc, "cbc:IssueDate", LCO_FE_Utils.getDateTime((Timestamp) inv.get_Value("LCO_FE_DateTrx"), 11), atts);
			// M AN Max 14, Hora de emisi\u00f3n de la factura Formato HH:MM:DD-05:00
			addHeaderElement(mmDoc, "cbc:IssueTime", LCO_FE_Utils.getDateTime((Timestamp) inv.get_Value("LCO_FE_DateTrx"), 14), atts);
			//
			atts.clear();
			// atts.addAttribute("", "", "listAgencyID", "CDATA", "195");
			// atts.addAttribute("", "", "listAgencyName", "CDATA", "CO, DIAN (Direccion de Impuestos y Aduanas Nacionales)");
			// atts.addAttribute("", "", "listSchemeURI", "CDATA", "http://www.dian.gov.co/contratos/facturaelectronica/v1/InvoiceType");
			// M AN Max 2, Tipo de documento
			addHeaderElement(mmDoc, "cbc:CreditNoteTypeCode", LCO_FE_Utils.cutString(fedt.getDianDocTypeCode(), 2), atts);
			atts.clear();
			// O AF 15..5000, Información adicional: Texto libre
			addHeaderElement(mmDoc, "cbc:Note", "QRCode: " + m_QR, atts);
			// D AN Max 3, Divisa de la Factura
			addHeaderElement(mmDoc, "cbc:DocumentCurrencyCode", inv.getCurrencyISO(), atts);
			//
			List<List<Object>> lines = DB.getSQLArrayObjectsEx(inv.get_TrxName(), LCO_FE_Utils.SQL_ITEMS, inv.getC_Invoice_ID());
			m_totalnolineas = lines.size();
			// M N 3, Número de elementos InvoiceLine
			addHeaderElement(mmDoc, "cbc:LineCountNumeric", String.valueOf(m_totalnolineas), atts);
			
			mmDoc.startElement("","","cac:DiscrepancyResponse", atts);
			// <cbc:ResponseCode listName="concepto de notas crédito" listSchemeURI="http://www.dian.gov.co/micrositios/fac_electronica/documentos/Anexo_Tecnico_001_Formatos_de_los_Documentos_XML_de_Facturacion_Electron.pdf" name="2:= anulación de la factura electrónica">2</cbc:ResponseCode>
			atts.clear();
			// O N 4 Identifica la sección de la factura original a la cual se aplica la corrección
			addHeaderElement(mmDoc, "cbc:ReferenceID", "", atts);
			// O N 1 Código de descripción de la corrección. Tabla 3
			addHeaderElement(mmDoc, "cbc:ResponseCode", fencc.getValue(), atts);
			atts.clear();
			// M AN Max 5000, Descripción de la naturaleza de la corrección 
			addHeaderElement(mmDoc, "cbc:Description", inv.getDescription() != null ? inv.getDescription() : fencc.getName(), atts);
			mmDoc.endElement("","","cac:DiscrepancyResponse");
			//
			if (inv.getRelatedInvoice_ID() > 0 && inv.get_ValueAsInt("LCO_FE_OperationType_ID") != 1000033) {	// Nota Crédito sin referencia a facturas
				atts.clear();
				mmDoc.startElement("","","cac:BillingReference", atts);
					mmDoc.startElement("","","cac:InvoiceDocumentReference", atts);
						// M AF Max 20, Prefijo + Número de la factura relacionada
						addHeaderElement(mmDoc, "cbc:ID", invref.getDocumentNo(), atts); // With Prefix getDocumentNo()
						atts.clear();
						if (fedtref.getDianShortDocType().equals(LCO_FE_Utils.TIPO_COMPROBANTE_FACTURA)
							|| fedtref.getDianShortDocType().equals(LCO_FE_Utils.TIPO_COMPROBANTE_EXPORTACION)) {
							atts.addAttribute("", "", "schemeName", "CDATA", fref.getLCO_FE_UuidType());
							// M AN 96 CUFE de la factura relacionada 
							addHeaderElement(mmDoc, "cbc:UUID", autref.getLCO_FE_Dian_Uuid() != null ? autref.getLCO_FE_Dian_Uuid() : "", atts);	// getCUFE()
						}
						atts.clear();	
						// M AN Max 10, Fecha de emisi\u00f3n de la factura Formato AAAA-MM-DD
						addHeaderElement(mmDoc, "cbc:IssueDate", LCO_FE_Utils.getDateTime((Timestamp) invref.get_Value("LCO_FE_DateTrx") != null ? (Timestamp) invref.get_Value("LCO_FE_DateTrx") : invref.getDateInvoiced(), 11), atts);
					mmDoc.endElement("","","cac:InvoiceDocumentReference");
				mmDoc.endElement("","","cac:BillingReference");
			}

			// Informaci\u00f3n Emisor
			atts.clear();
			mmDoc.startElement("","","cac:AccountingSupplierParty", atts);
				atts.clear();
				atts.addAttribute("", "", "schemeAgencyID", "CDATA", "195");
				// M N 1, Tipo de organización jurídica Tabla 9
				addHeaderElement(mmDoc, "cbc:AdditionalAccountID", tpte.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_GRAN_CONTRIBUYENTE) ? LCO_FE_Utils.TIPO_PERSONA_JURIDICA : tpte.get_ValueAsString("DianTaxPayerCode"), atts);
				atts.clear();
				mmDoc.startElement("","","cac:Party", atts);
					// O N 4, código de actividad económica CIIU
					// addHeaderElement(mmDoc, "cbc:IndustryClasificationCode", ie.getValue().substring(1, 5), atts);	// Fix ZB01
					mmDoc.startElement("","","cac:PartyIdentification", atts);
					atts.clear();
					// M AN 7, Tipo identificaci\u00f3n Tabla 2
					atts = addGovAttribute21(null, LCO_FE_Utils.cutString(tte.get_ValueAsString("LCO_TaxCodeDian"), 7));
					// M AF Max 35, No. Identificacion Emisor
					addHeaderElement(mmDoc, "cbc:ID", bpe.getTaxID().trim(), atts);
					atts.clear();
					mmDoc.endElement("","","cac:PartyIdentification");
					if ( tpte.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_PERSONA_JURIDICA)
							|| tpte.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_GRAN_CONTRIBUYENTE) ) {
						mmDoc.startElement("","","cac:PartyName", atts);
							// D AN 5..450 Nombre comercial del emisor
							addHeaderElement(mmDoc, "cbc:Name", bpe.getName(), atts);
						mmDoc.endElement("","","cac:PartyName");
					}
					mmDoc.startElement("","","cac:PhysicalLocation", atts);
						mmDoc.startElement("","","cac:Address", atts);
							// M N 5 Codigo del municipio
							addHeaderElement(mmDoc, "cbc:ID", LCO_FE_Utils.cutString(lo.getC_City().getPostal(), 5), atts);
							// M AN Max 60 Nombre de la ciudad
							addHeaderElement(mmDoc, "cbc:CityName", lo.getC_City().getName(), atts);
							// M AN Max 60 Nombre del Departamento
							addHeaderElement(mmDoc, "cbc:CountrySubentity", lo.getRegionName(), atts);
							// M N 1..5 Código del Departamento
							addHeaderElement(mmDoc, "cbc:CountrySubentityCode", LCO_FE_Utils.cutString(lo.getRegion().getDescription(), 2), atts);
							// X AN Max 40 Municipio (distrito administrativo)
							// addHeaderElement(mmDoc, "cbc:CitySubdivisionName", lo.getC_City().getName(), atts);
							mmDoc.startElement("","","cac:AddressLine", atts);
								// M AN Max 300 Direccion
								addHeaderElement(mmDoc, "cbc:Line", lo.getAddress1(), atts);
							mmDoc.endElement("","","cac:AddressLine");
							mmDoc.startElement("","","cac:Country", atts);
								// M AN Max 2 Pais Tabla 8
								addHeaderElement(mmDoc, "cbc:IdentificationCode", lo.getCountry().getCountryCode(), atts);
								// O A 4..40 Nombre Pais Tabla 8
								atts.addAttribute("", "", "languageID", "CDATA", LCO_FE_Utils.cutString(Env.getAD_Language(Env.getCtx()), 2));
								addHeaderElement(mmDoc, "cbc:Name", lo.getCountry().get_Translation("Name"), atts);
								atts.clear();
							mmDoc.endElement("","","cac:Country");
						mmDoc.endElement("","","cac:Address");
					mmDoc.endElement("","","cac:PhysicalLocation");
					mmDoc.startElement("","","cac:PartyTaxScheme", atts);
						// M AF 5..450, Nombre o Razón Social del emisor
						addHeaderElement(mmDoc, "cbc:RegistrationName", bpe.getName(), atts);
						atts.clear();
						atts = addGovAttribute21(bpe.get_ValueAsString("TaxIdDigit"), LCO_FE_Utils.cutString(tte.get_ValueAsString("LCO_TaxCodeDian"), 7));	// TODO Reviewme
						// M N 5..12, No. Identificacion Emisor
						addHeaderElement(mmDoc, "cbc:CompanyID", bpe.getTaxID().trim(), atts);
						atts.clear();
						{ // BP Info Emisor
							boolean nobpeinfo = true;
							String codigosRUT = "";
							List<List<Object>> rowse = DB.getSQLArrayObjectsEx(bpe.get_TrxName(), LCO_FE_Utils.SQL_BP_INFO, bpe.getC_BPartner_ID());
							if (rowse != null) {
								for (List<Object> rowe : rowse) {
									// String grupoRUT = rowe.get(0).toString();
									String codigoRUT = rowe.get(1).toString();
									if (Util.isEmpty(codigoRUT, true)) {
										nobpeinfo = true;
										break;
									}
								nobpeinfo = false;
								codigosRUT = codigosRUT + codigoRUT + ";";
								}
								atts.clear();
								atts.addAttribute("", "", "listName", "CDATA", LCO_FE_Utils.cutString(tpte.get_ValueAsString("DianRegimeCode"), 2));
								// M AN 1 Max 30, Obligaciones del contribuyente
								addHeaderElement(mmDoc, "cbc:TaxLevelCode", LCO_FE_Utils.cutString(codigosRUT.substring(0, codigosRUT.length()-1), 30), atts);
								atts.clear();
							}
							if (nobpeinfo) {
								msg = (Msg.translate(Env.getCtx(), "FillMandatory") + " "
										+ Msg.getElement(Env.getCtx(), MBPartner.COLUMNNAME_C_BPartner_ID) + "-"
										+ Msg.getElement(Env.getCtx(), "LCO_FE_TributaryType_ID"));
								return "@Error@ @invoice.no@ " + inv.getDocumentNo() + " " + msg;
							}
						}
						if (tpte.get_ValueAsString("DianRegimeCode").equals(LCO_FE_Utils.TIPO_REGIMEN_FISCAL_48)) {
							// M 1 Veces max
							mmDoc.startElement("","","cac:TaxScheme", atts);	// TODO Fixed ?
								// M AF 3..10, Identificador del tributo
								addHeaderElement(mmDoc, "cbc:ID", LCO_FE_Utils.CODIGO_IVA_01, atts);	// TipoImpuesto
								// M AF 10..30, Nombre del tributo
								addHeaderElement(mmDoc, "cbc:Name", "IVA", atts);	// TODO NombreImpuesto
							mmDoc.endElement("","","cac:TaxScheme");
						}
					mmDoc.endElement("","","cac:PartyTaxScheme");
					mmDoc.startElement("","","cac:PartyLegalEntity", atts);
						// M AF 5..450, Nombre o Razón Social del emisor
						addHeaderElement(mmDoc, "cbc:RegistrationName", bpe.getName(), atts);
						atts.clear();
						atts = addGovAttribute21(bpe.get_ValueAsString("TaxIdDigit"), LCO_FE_Utils.cutString(tte.get_ValueAsString("LCO_TaxCodeDian"), 7));	// TODO Reviewme
						// M N 5..12, NIT del emisor
						addHeaderElement(mmDoc, "cbc:CompanyID", bpe.getTaxID().trim(), atts);
						atts.clear();
						mmDoc.startElement("","","cac:CorporateRegistrationScheme", atts);
							if (! "".equals(m_Prefix))
								// M N 6, Prefijo de la facturación usada para el punto de venta
								addHeaderElement(mmDoc, "cbc:ID", m_Prefix, atts);	// TODO reviewme
						mmDoc.endElement("","","cac:CorporateRegistrationScheme");
					mmDoc.endElement("","","cac:PartyLegalEntity");
					String mailContacto = MSysConfig.getValue("QSSLCO_FE_ReplyToEMail", null, inv.getAD_Client_ID());
					if (mailContacto != null) {
						mmDoc.startElement("","","cac:Contact", atts);
							// O AN Max 250 Correo electrónico  de la persona de contacto.
							addHeaderElement(mmDoc, "cbc:ElectronicMail", mailContacto, atts);
						mmDoc.endElement("","","cac:Contact");
					}
				mmDoc.endElement("","","cac:Party");
			mmDoc.endElement("","","cac:AccountingSupplierParty");
			
			// Informacion Adquiriente
			mmDoc.startElement("","","cac:AccountingCustomerParty", atts);
				// M N 1, Tipo de persona Tabla 9
				addHeaderElement(mmDoc, "cbc:AdditionalAccountID", tpta.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_GRAN_CONTRIBUYENTE) ? LCO_FE_Utils.TIPO_PERSONA_JURIDICA : tpta.get_ValueAsString("DianTaxPayerCode"), atts);
				mmDoc.startElement("","","cac:Party", atts);
					mmDoc.startElement("","","cac:PartyIdentification", atts);
						atts.clear();
						// M AN 7, Tipo identificaci\u00f3n Tabla 2
						atts = addGovAttribute21(null, LCO_FE_Utils.cutString(tta.get_ValueAsString("LCO_TaxCodeDian"), 7));
						// M AF Max 35, No. Identificacion Adquiriente
						addHeaderElement(mmDoc, "cbc:ID", bp.getTaxID(), atts);
						atts.clear();
					mmDoc.endElement("","","cac:PartyIdentification");
					if ( tpta.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_PERSONA_JURIDICA)
							|| tpta.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_GRAN_CONTRIBUYENTE) ) {
						mmDoc.startElement("","","cac:PartyName", atts);
							// D AN 5..450 Nombre comercial del adquiriente
							addHeaderElement(mmDoc, "cbc:Name", bp.getName(), atts);
						mmDoc.endElement("","","cac:PartyName");
					} else if ( tpta.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_PERSONA_NATURAL) ) {
						mmDoc.startElement("","","cac:Person", atts);	// MAx 450
							// D AN Max 150 Nombre del adquiriente
							addHeaderElement(mmDoc, "cbc:FirstName", bp.get_ValueAsString("FirstName1"), atts);
							// D AN Max 150 Nombre del adquiriente
							addHeaderElement(mmDoc, "cbc:MiddleName", bp.get_Value("FirstName2") != null ? bp.get_ValueAsString("FirstName2") : "", atts);
							// D AN Max 150 Primer y segundo Apellido
							addHeaderElement(mmDoc, "cbc:FamilyName", bp.get_ValueAsString("LastName1")
									+ bp.get_Value("LastName2") != null ? " " + bp.get_ValueAsString("LastName2") : "", atts);
						mmDoc.endElement("","","cac:Person");
					}
					mmDoc.startElement("","","cac:PhysicalLocation", atts);
						mmDoc.startElement("","","cac:Address", atts);
							if (la.getC_City().getPostal() != null)
								// M N 5 Codigo del municipio
								addHeaderElement(mmDoc, "cbc:ID", LCO_FE_Utils.cutString(la.getC_City().getPostal(), 5), atts);
							if (la.getC_City().getName() != null)
								// M AN Max 60 Nombre de la ciudad
								addHeaderElement(mmDoc, "cbc:CityName", la.getC_City().getName(), atts);
							if (la.getRegionName() != null)
								// M AN Max 60 Nombre del Departamento
								addHeaderElement(mmDoc, "cbc:CountrySubentity", la.getRegionName(), atts);
							// M N 1..5 Código del Departamento
							if (la.getCountry().getCountryCode().equals(LCO_FE_Utils.COUNTRY_CODE_CO))
								addHeaderElement(mmDoc, "cbc:CountrySubentityCode", LCO_FE_Utils.cutString(la.getRegion().getDescription(), 2), atts);
							else
								addHeaderElement(mmDoc, "cbc:CountrySubentityCode", la.getCountry().getCountryCode(), atts);	// TODO En definicion
							// X AN Max 40 Municipio (distrito administrativo)
							// addHeaderElement(mmDoc, "cbc:CitySubdivisionName", la.getC_City().getName(), atts);
							mmDoc.startElement("","","cac:AddressLine", atts);
								// M AN Max 300 Direccion
								addHeaderElement(mmDoc, "cbc:Line", la.getAddress1(), atts);
							mmDoc.endElement("","","cac:AddressLine");
							mmDoc.startElement("","","cac:Country", atts);
								// M AN Max 2 Pais Tabla 8
								addHeaderElement(mmDoc, "cbc:IdentificationCode", la.getCountry().getCountryCode(), atts);
								// O A 4..40 Nombre Pais Tabla 8
								atts.addAttribute("", "", "languageID", "CDATA", LCO_FE_Utils.cutString(Env.getAD_Language(Env.getCtx()), 2));
								addHeaderElement(mmDoc, "cbc:Name", la.getCountry().get_Translation("Name"), atts);
								atts.clear();
							mmDoc.endElement("","","cac:Country");
						mmDoc.endElement("","","cac:Address");
					mmDoc.endElement("","","cac:PhysicalLocation");
					mmDoc.startElement("","","cac:PartyTaxScheme", atts);
						// M AF 5..450, Nombre o Razón Social del adquiriente
						addHeaderElement(mmDoc, "cbc:RegistrationName", bp.getName(), atts);
						atts.clear();
						atts = addGovAttribute21(bp.get_ValueAsString("TaxIdDigit"), LCO_FE_Utils.cutString(tta.get_ValueAsString("LCO_TaxCodeDian"), 7));	// TODO Reviewme
						// M N 5..12, No. Identificacion Adquiriente
						addHeaderElement(mmDoc, "cbc:CompanyID", bp.getTaxID().trim(), atts);
						atts.clear();
						{ // BP Info Adquiriente
							boolean nobpainfo = true;
							String codigosRUT = "";
							List<List<Object>> rowsa = DB.getSQLArrayObjectsEx(bp.get_TrxName(), LCO_FE_Utils.SQL_BP_INFO, bp.getC_BPartner_ID());
							if (rowsa != null) {
								for (List<Object> rowa : rowsa) {
									// String grupoRUT = rowa.get(0).toString();
									String codigoRUT = rowa.get(1).toString();
									if (Util.isEmpty(codigoRUT, true)) {
										nobpainfo = true;
										break;
									}
									nobpainfo = false;
									codigosRUT = codigosRUT + codigoRUT + ";";
								}
								atts.clear();
								atts.addAttribute("", "", "listName", "CDATA", LCO_FE_Utils.cutString(tpta.get_ValueAsString("DianRegimeCode"), 2));
								// M AN 1 Max 30, Obligaciones del contribuyente
								addHeaderElement(mmDoc, "cbc:TaxLevelCode", codigosRUT.substring(0, codigosRUT.length()-1), atts);
								atts.clear();
							}
							if (nobpainfo) {
								msg = (Msg.translate(Env.getCtx(), "FillMandatory") + " "
										+ Msg.getElement(Env.getCtx(), MBPartner.COLUMNNAME_C_BPartner_ID) + "-"
										+ Msg.getElement(Env.getCtx(), "LCO_FE_TributaryType_ID"));
								return "@Error@ @invoice.no@ " + inv.getDocumentNo() + " " + msg;
							}
						}
						List<List<Object>> taxas = DB.getSQLArrayObjectsEx(inv.get_TrxName(), LCO_FE_Utils.SQL_TAX_INFO, inv.getC_Invoice_ID(), "%", inv.getC_Invoice_ID(), "%");
						if (taxas != null) {
							for (List<Object> taxa : taxas) {
								// M 1 Veces max
								mmDoc.startElement("","","cac:TaxScheme", atts);	// TODO Fixed ?
									// M AF 3..10, Identificador del tributo
									addHeaderElement(mmDoc, "cbc:ID", taxa.get(3).toString(), atts);	// TipoImpuesto
									// M AF 10..30, Nombre del tributo
									addHeaderElement(mmDoc, "cbc:Name", taxa.get(6).toString(), atts);	// NombreImpuesto
								mmDoc.endElement("","","cac:TaxScheme");
								break;	// First
							}
						}
					mmDoc.endElement("","","cac:PartyTaxScheme");
					if ( tpte.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_PERSONA_JURIDICA)
							|| tpte.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_GRAN_CONTRIBUYENTE) ) {
						mmDoc.startElement("","","cac:PartyLegalEntity", atts);
							// M AF 5..450, Nombre o Razón Social del adquiriente
							addHeaderElement(mmDoc, "cbc:RegistrationName", bp.getName(), atts);
							atts.clear();
							atts = addGovAttribute21(bp.get_ValueAsString("TaxIdDigit"), LCO_FE_Utils.cutString(tta.get_ValueAsString("LCO_TaxCodeDian"), 7));	// TODO Reviewme
							// M N 5..12, ID del Adquiriente
							addHeaderElement(mmDoc, "cbc:CompanyID", bp.getTaxID(), atts);
							atts.clear();
						mmDoc.endElement("","","cac:PartyLegalEntity");
					} else if ( tpte.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_PERSONA_NATURAL) ) {
						mmDoc.startElement("","","cac:Person", atts);	// Max 450
							// D AN Max 150 Nombre del adquiriente
							addHeaderElement(mmDoc, "cbc:FirstName", bp.get_ValueAsString("FirstName1"), atts);
							// D AN Max 150 Nombre del adquiriente
							addHeaderElement(mmDoc, "cbc:MiddleName", bp.get_Value("FirstName2") != null ? bp.get_ValueAsString("FirstName2") : "", atts);
							// D AN Max 150 Primer y segundo Apellido
							addHeaderElement(mmDoc, "cbc:FamilyName", bp.get_ValueAsString("LastName1")
									+ bp.get_Value("LastName2") != null ? " " + bp.get_ValueAsString("LastName2") : "", atts);
						mmDoc.endElement("","","cac:Person");
					}
					if (ua.getEMail() != null) {
						mmDoc.startElement("","","cac:Contact", atts);
							// O AN Max 250 Correo electrónico  de la persona de contacto.
							addHeaderElement(mmDoc, "cbc:ElectronicMail", ua.getEMail().trim(), atts);
						mmDoc.endElement("","","cac:Contact");
					}
				mmDoc.endElement("","","cac:Party");
			mmDoc.endElement("","","cac:AccountingCustomerParty");
			
			if (m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_NC)) {
				// M 1..1, Método de pago
				mmDoc.startElement("","","cac:PaymentMeans",atts);
				// O AN Max 3 Código correspondiente al medio de pago Tabla 5
				addHeaderElement(mmDoc, "cbc:ID", inv.getPaymentRule().equals(MInvoice.PAYMENTRULE_DirectDeposit) ? LCO_FE_Utils.MEDIO_PAGO_DEBITO_CA : LCO_FE_Utils.MEDIO_PAGO_NO_DEFINIDO, atts);
				// D AN Max 3 Método de pago. Tabla 26
				if (inv.getPaymentRule().equals(MInvoice.PAYMENTRULE_OnCredit))
					addHeaderElement(mmDoc, "cbc:PaymentMeansCode", LCO_FE_Utils.METODO_PAGO_CREDITO, atts);
				else if (inv.getPaymentRule().equals(MInvoice.PAYMENTRULE_DirectDeposit))
					addHeaderElement(mmDoc, "cbc:PaymentMeansCode", LCO_FE_Utils.METODO_PAGO_CONTADO, atts);
				// D AN Max 10 Fecha de pago Formato AAAA-MM-DD
				if (inv.getPaymentRule().equals(MInvoice.PAYMENTRULE_OnCredit)) {
					Object[] args = new Object[] { inv.getC_PaymentTerm_ID(), (Timestamp) inv.get_Value("LCO_FE_DateTrx")};
					Timestamp duedate = DB.getSQLValueTSEx(null, "SELECT paymenttermduedate(?, ?)", args);
					addHeaderElement(mmDoc, "cbc:PaymentDueDate", LCO_FE_Utils.getDateTime(duedate, 11), atts);
				}
				mmDoc.endElement("","","cac:PaymentMeans");
			}
			
			{ // Impuestos
				List<List<Object>> rows = DB.getSQLArrayObjectsEx(inv.get_TrxName(), LCO_FE_Utils.SQL_TAX_GROUP, inv.getC_Invoice_ID(), inv.getC_Invoice_ID());
				if (rows != null) {
					// No y retenidos
					for (List<Object> row : rows) {
						boolean EsRetencion = (row.get(0) != null && ((String)row.get(0)).equals("Y")) ? true : false;
						BigDecimal ImporteImpuesto = (BigDecimal) row.get(1);
						String Moneda = (String) row.get(2);
						String TipoImpuestoGrupo = (String) row.get(3);
						BigDecimal BaseImponible = (BigDecimal) row.get(4);
						BigDecimal PorcentajeImpuesto = Env.ZERO;
						String NombreImpuesto = (String) row.get(5);
						if (Util.isEmpty(TipoImpuestoGrupo, true)) {
							msg = (Msg.translate(Env.getCtx(), "FillMandatory") + " "
									+ Msg.getElement(Env.getCtx(), MTaxCategory.COLUMNNAME_C_TaxCategory_ID) + "-"
									+ Msg.getElement(Env.getCtx(), "IsWithHolding") + "-"
									+ Msg.getElement(Env.getCtx(), "LCO_FE_DianTaxCategoryCode"));
							return "@Error@ @invoice.no@ " + inv.getDocumentNo() + " " + msg;
						}
						// Impuestos Retenidos
						// Regla: ZB01 CreditNote-2 has invalid child element 'WithholdingTaxTotal'
						if (EsRetencion)	// && !m_IsAutoRetenedor)
							continue;
						if (TipoImpuestoGrupo.equals(LCO_FE_Utils.CODIGO_RETFTE_06))	// No Reporta ?
							continue;
						// O 50 Veces max Impuestos
						if (!EsRetencion)
							mmDoc.startElement("","","cac:TaxTotal",atts);
						else
							mmDoc.startElement("","","cac:WithholdingTaxTotal",atts);
							atts.clear();
							atts.addAttribute("", "", "currencyID", "CDATA", Moneda);
							// M N 14.4 Importe Impuesto: Importe del impuesto retenido
							addHeaderElement(mmDoc, "cbc:TaxAmount", LCO_FE_Utils.decimalFormat(ImporteImpuesto), atts);
							// M N 14.4 Redondeo agregado al total del impuesto
							addHeaderElement(mmDoc, "cbc:RoundingAmount", LCO_FE_Utils.decimalFormat(Env.ZERO), atts);
							atts.clear();
							// M AN 5 Impuesto retenido o retenci\u00f3n
							// addHeaderElement(mmDoc, "cbc:TaxEvidenceIndicator", EsRetencion ? "true" : "false", atts);
						
							List<List<Object>> rowsi = DB.getSQLArrayObjectsEx(inv.get_TrxName(), LCO_FE_Utils.SQL_TAX_INFO, inv.getC_Invoice_ID(), TipoImpuestoGrupo, inv.getC_Invoice_ID(), TipoImpuestoGrupo);
							for (List<Object> rowi : rowsi) {
								EsRetencion = (rowi.get(0) != null && ((String)rowi.get(0)).equals("Y")) ? true : false;
								ImporteImpuesto = (BigDecimal) rowi.get(1);
								Moneda = (String) rowi.get(2);
								String TipoImpuesto = (String) rowi.get(3);
								BaseImponible = (BigDecimal) rowi.get(4);
								PorcentajeImpuesto = (BigDecimal) rowi.get(5);
								NombreImpuesto = (String) rowi.get(6);
								boolean EsExento = (rowi.get(7) != null && ((String)rowi.get(7)).equals("Y")) ? true : false;
								if (TipoImpuestoGrupo.equals(LCO_FE_Utils.CODIGO_IVA_01) && !EsExento && PorcentajeImpuesto.compareTo(Env.ZERO) == 0)
									continue;
								// O 50 Veces max Total Impuestos.
								atts.clear();
								mmDoc.startElement("","","cac:TaxSubtotal",atts);
									atts.clear();
									atts.addAttribute("", "", "currencyID", "CDATA", Moneda);
									// M N 14.4 Base Imponible
									addHeaderElement(mmDoc, "cbc:TaxableAmount", LCO_FE_Utils.decimalFormat(BaseImponible), atts);
									atts.clear();
									atts.addAttribute("", "", "currencyID", "CDATA", Moneda);
									// M N 14.4 Importe Impuesto (detalle)
									addHeaderElement(mmDoc, "cbc:TaxAmount", LCO_FE_Utils.decimalFormat(ImporteImpuesto), atts);
									atts.clear();
									mmDoc.startElement("","","cac:TaxCategory",atts);
										// O N 3.3 Porcentaje del impuesto
										addHeaderElement(mmDoc, "cbc:Percent", LCO_FE_Utils.decimalFormat(PorcentajeImpuesto), atts);
										mmDoc.startElement("","","cac:TaxScheme",atts);
											// M AN 3..10 Identificador del tributo Tabla 5
											addHeaderElement(mmDoc, "cbc:ID", TipoImpuesto, atts);
											// M AN 10..30 Nombre del tributo Tabla 5
											addHeaderElement(mmDoc, "cbc:Name", NombreImpuesto, atts);
										mmDoc.endElement("","","cac:TaxScheme");
									mmDoc.endElement("","","cac:TaxCategory");
								mmDoc.endElement("","","cac:TaxSubtotal");
								if (EsRetencion)
									BaseImponible = Env.ZERO;	// No suma
								if (!TipoImpuesto.equals(LCO_FE_Utils.CODIGO_NO_CAUSA_ZY)) {
									if (TipoImpuesto.equals(LCO_FE_Utils.CODIGO_IVA_01) && !EsExento && PorcentajeImpuesto.compareTo(Env.ZERO) == 0)
										m_totalbaseexcluida = m_totalbaseexcluida.add(BaseImponible);
									else
										m_totalbaseimponible = m_totalbaseimponible.add(BaseImponible);
									if (!EsRetencion)
										m_totalvalorimpuesto = m_totalvalorimpuesto.add(ImporteImpuesto);
									else
										m_totalretenciones = m_totalretenciones.add(ImporteImpuesto);
								}
							}
						if (!EsRetencion)
							mmDoc.endElement("","","cac:TaxTotal");
						else
							mmDoc.endElement("","","cac:WithholdingTaxTotal");
					}
				}
			}
			
			// M 1 Valores Totales
			mmDoc.startElement("","","cac:LegalMonetaryTotal", atts);
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// M N 14.4 Total Valor Bruto
				addHeaderElement(mmDoc, "cbc:LineExtensionAmount", LCO_FE_Utils.decimalFormat(inv.getTotalLines()), atts);
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// M N 14.4 Total Valor Base Imponible menos Excluidos
				addHeaderElement(mmDoc, "cbc:TaxExclusiveAmount", LCO_FE_Utils.decimalFormat(m_totalbaseimponible), atts);
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// M N 14.4 Total de Valor Bruto más tributos
				addHeaderElement(mmDoc, "cbc:TaxInclusiveAmount", LCO_FE_Utils.decimalFormat(inv.getTotalLines().add(m_totalvalorimpuesto)), atts); // TODO Excluidos
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// O N 14.4 Descuento Total
				if (inv.isDiscountPrinted())
					addHeaderElement(mmDoc, "cbc:AllowanceTotalAmount", LCO_FE_Utils.decimalFormat(m_totaldescuento), atts);	// TODO
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// O N 14.4 Cargo Total
				addHeaderElement(mmDoc, "cbc:ChargeTotalAmount", LCO_FE_Utils.decimalFormat(m_totalcargo), atts);	// TODO
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// O N 14.4 Anticipo Total
				addHeaderElement(mmDoc, "cbc:PrepaidAmount", LCO_FE_Utils.decimalFormat(m_totalanticipo), atts);	// TODO
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// O N 14.4 Diferencia Total
				addHeaderElement(mmDoc, "cbc:PayableRoundingAmount", LCO_FE_Utils.decimalFormat(Env.ZERO), atts);	// TODO
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// M N 14.4 Valor a Pagar de Factura
				addHeaderElement(mmDoc, "cbc:PayableAmount", LCO_FE_Utils.decimalFormat(inv.getGrandTotalPlusWithholdings()), atts);
				atts.clear();
			mmDoc.endElement("","","cac:LegalMonetaryTotal");
			
			Object[] args = null;
			// !m_IsExport
			int lco_fe_productscheme_id = -1;
			X_LCO_FE_ProductScheme feps = null;
			args = new Object[] { inv.getAD_Client_ID(), "Y", "%" };
			lco_fe_productscheme_id = DB.getSQLValueEx(inv.get_TrxName(), LCO_FE_Utils.SQL_SCHEMA, args);
			feps = new X_LCO_FE_ProductScheme (inv.getCtx(), lco_fe_productscheme_id, inv.get_TrxName());
			//
			X_LCO_FE_ProductScheme fepspa = null;
			if (m_IsExport) {
				args = new Object[] { inv.getAD_Client_ID(), "%", LCO_FE_Utils.PRODUCT_SCHEME_PAID };
				lco_fe_productscheme_id = DB.getSQLValueEx(inv.get_TrxName(), LCO_FE_Utils.SQL_SCHEMA, args);
				fepspa = new X_LCO_FE_ProductScheme (inv.getCtx(), lco_fe_productscheme_id, inv.get_TrxName());
			}
			
			{ // Items
				// M 1500 Veces max \u00cdtems del Documento // TODO
				List<List<Object>> rows = DB.getSQLArrayObjectsEx(inv.get_TrxName(), LCO_FE_Utils.SQL_ITEMS, inv.getC_Invoice_ID());
				if (rows == null) {
					msg = (Msg.translate(Env.getCtx(), "NoLines") + " "
							+ Msg.getElement(Env.getCtx(), MInvoice.COLUMNNAME_C_Invoice_ID));
					return "@Error@ @invoice.no@ " + inv.getDocumentNo() + " " + msg;
				}
				// if (rows != null) {
				    int i = 1;
					for (List<Object> row : rows) {
						BigDecimal Consecutivo = new BigDecimal(i);
						i++;
						boolean EsGratis = (row.get(1) != null && ((String)row.get(1)).equals("true")) ? true : false;
						BigDecimal qtyinvoiced = (BigDecimal) row.get(2);
						String UnidadMedida = (String) row.get(3);
						BigDecimal CostoTotal = (BigDecimal) row.get(4);
						String Iso_Code = (String) row.get(5);
						BigDecimal PrecioUnitario = (BigDecimal) row.get(6);
						String Iso_CodeDup = (String) row.get(7);
						//String pidentificador = (String) row.get(8);
						//String pdescription = (String) row.get(9);
						String ildescription = (String) row.get(10);
						//String name = (String) row.get(11);
						BigDecimal Descuento = (BigDecimal) row.get(12);
						//String pcodigo = (String) row.get(13);
						boolean EsRetencion = (row.get(14) != null && ((String)row.get(14)).equals("Y")) ? true : false;
						String TipoImpuesto = (String) row.get(15);
						BigDecimal ImporteImpuesto = (BigDecimal) row.get(16);
						String MonedaImporte = (String) row.get(17);
						BigDecimal BaseImponible = (BigDecimal) row.get(18);
						String MonedaBase = (String) row.get(19);
						BigDecimal PorcentajeImpuesto = (BigDecimal) row.get(20);
						String NombreImpuesto = (String) row.get(21);
						String TipoProducto = (String) row.get(22);
						String CodigoEstandar = (String) row.get(23);
						String PartidaArancelaria = (String) row.get(24);
						boolean EsExento = (row.get(25) != null && ((String)row.get(25)).equals("Y")) ? true : false;

						atts.clear();
						mmDoc.startElement("","","cac:CreditNoteLine",atts);
							// M N 1..4, Número de Línea
							addHeaderElement(mmDoc, "cbc:ID", LCO_FE_Utils.cutString(Consecutivo.toString(), 4), atts);
							atts.addAttribute("", "", "unitCode", "CDATA", UnidadMedida);
							// M N 14.4, Cantidad del art\u00edculo solicitado
							addHeaderElement(mmDoc, "cbc:CreditedQuantity", String.format(Locale.US, "%.2f", qtyinvoiced), atts);
							atts.clear();
							atts.addAttribute("", "", "currencyID", "CDATA", Iso_Code);
							// D N 14.4, Costo Total
							addHeaderElement(mmDoc, "cbc:LineExtensionAmount", LCO_FE_Utils.decimalFormat(CostoTotal), atts);
							atts.clear();
							addHeaderElement(mmDoc, "cbc:FreeOfChargeIndicator", (EsGratis ? "true" : "false"), atts);
							// O 50 Veces max Total Impuestos \u00cdtem.
							atts.clear();
							if (!TipoImpuesto.equals(LCO_FE_Utils.CODIGO_NO_CAUSA_ZY)) {
							mmDoc.startElement("","","cac:TaxTotal",atts);
								// if (!isTimHeader) {
									atts.clear();
									atts.addAttribute("", "", "currencyID", "CDATA", MonedaImporte);
									// M N 14.4 Importe Impuesto: Importe del impuesto retenido
									addHeaderElement(mmDoc, "cbc:TaxAmount", LCO_FE_Utils.decimalFormat(ImporteImpuesto), atts);
									// M N 14.4 Redondeo agregado al total del impuesto
									addHeaderElement(mmDoc, "cbc:RoundingAmount", LCO_FE_Utils.decimalFormat(Env.ZERO), atts);
									atts.clear();
									// M AN 5 Impuesto retenido o retenci\u00f3n
									// addHeaderElement(mmDoc, "cbc:TaxEvidenceIndicator", EsRetencion ? "true" : "false", atts);
									// isTimHeader = true;
								// }
								// O 50 Veces max Impuestos
								mmDoc.startElement("","","cac:TaxSubtotal",atts);
									atts.clear();
									atts.addAttribute("", "", "currencyID", "CDATA", MonedaBase);
									// M N 14.4 Base Imponible
									addHeaderElement(mmDoc, "cbc:TaxableAmount", LCO_FE_Utils.decimalFormat(BaseImponible), atts);
									atts.clear();
									atts.addAttribute("", "", "currencyID", "CDATA", MonedaImporte);
									// M N 14.4 Importe Impuesto (detalle)
									addHeaderElement(mmDoc, "cbc:TaxAmount", LCO_FE_Utils.decimalFormat(ImporteImpuesto), atts);
									atts.clear();
									mmDoc.startElement("","","cac:TaxCategory",atts);
										// O N 3.3 Porcentaje del impuesto
										addHeaderElement(mmDoc, "cbc:Percent", LCO_FE_Utils.decimalFormat(PorcentajeImpuesto), atts);
										mmDoc.startElement("","","cac:TaxScheme",atts);
											// M AN 3..10 Identificador del tributo Tabla 5
											addHeaderElement(mmDoc, "cbc:ID", TipoImpuesto, atts);
											// M AN 10..30 Nombre del tributo Tabla 5
											addHeaderElement(mmDoc, "cbc:Name", NombreImpuesto, atts);
										mmDoc.endElement("","","cac:TaxScheme");
									mmDoc.endElement("","","cac:TaxCategory");
								mmDoc.endElement("","","cac:TaxSubtotal");
							mmDoc.endElement("","","cac:TaxTotal");
							}
							//
							mmDoc.startElement("","","cac:Item",atts);
							if (ildescription != null) {
								// O AN 5..300, Descripci\u00f3n
								addHeaderElement(mmDoc, "cbc:Description", LCO_FE_Utils.cutString(ildescription, 300), atts);
							// M 3 Veces max IDENTIFICACIÓN DEL ARTÍCULO O SERVICIO DE ACUERDO CON UN ESTÁNDAR
							}
							mmDoc.startElement("","","cac:StandardItemIdentification",atts);
							if ( !(m_IsExport || !la.getCountry().getCountryCode().equals(LCO_FE_Utils.COUNTRY_CODE_CO)) ) {
								// M AN Max 3, Código del estándar Tabla 31
								atts.addAttribute("", "", "schemeID", "CDATA", feps.getValue());
								// M AN Max 2, Código del estándar Tabla 31
								atts.addAttribute("", "", "schemeAgencyID", "CDATA", feps.getIdAgency());
								// M AN Max 35, Nombre del estándar Tabla 31
								atts.addAttribute("", "", "schemeName", "CDATA", feps.getName());
								// M AN Max 35, Código del producto segun IAE_2
								addHeaderElement(mmDoc, "cbc:ID", CodigoEstandar, atts); // Regla: ZB01 Also Charge CodigoEstandar
								// addHeaderElement(mmDoc, "@schemeID", feps.getValue(), atts);
							} else {	// TODO Reviewme
								atts.addAttribute("", "", "schemeID", "CDATA", fepspa.getValue());
								atts.addAttribute("", "", "schemeAgencyID", "CDATA", fepspa.getIdAgency());
								atts.addAttribute("", "", "schemeName", "CDATA", fepspa.getName());
								addHeaderElement(mmDoc, "cbc:ID", TipoProducto.equals(MProduct.PRODUCTTYPE_Item) ? PartidaArancelaria : LCO_FE_Utils.CODIGO_PA_DEFAULT, atts);
							}
							mmDoc.endElement("","","cac:StandardItemIdentification");
						mmDoc.endElement("","","cac:Item");
						atts.clear();
							mmDoc.startElement("","","cac:Price",atts);
								atts.clear();
								atts.addAttribute("", "", "currencyID", "CDATA", Iso_CodeDup);
								// D N 14.4, Precio Unitario
								addHeaderElement(mmDoc, "cbc:PriceAmount", LCO_FE_Utils.decimalFormat(PrecioUnitario), atts);
								atts.clear();
								// D A 1..3, Código del tipo de precio informado Tabla 6.3.10
								// addHeaderElement(mmDoc, "cbc:PriceTypeCode", Iso_CodeDup, atts);	// TODO reviewme
								atts.addAttribute("", "", "unitCode", "CDATA", UnidadMedida);
								// M N 1..6, Cantidad del art\u00edculo solicitado
								addHeaderElement(mmDoc, "cbc:BaseQuantity", String.format(Locale.US, "%.2f", qtyinvoiced), atts);
								atts.clear();
							mmDoc.endElement("","","cac:Price");
							
							// O 50 Veces max Total Impuestos \u00cdtem.
							// mmDoc.startElement("","","TII",atts);
							//
							// mmDoc.endElement("","","TII");
							
						mmDoc.endElement("","","cac:CreditNoteLine");
						
						// mmDoc.startElement("","","cac:CreditNoteLine",atts);
						// <cbc:ID schemeID="fos0001_OPE18726_0B_700085464_2_40-firmado-SHA256.nCr.xml" schemeName="archivo embebido">2</cbc:ID>
						// mmDoc.endElement("","","cac:CreditNoteLine");
						
						m_sumanolineas ++;
						if (inv.isDiscountPrinted())
							m_sumadescuento = m_sumadescuento.add(Descuento);
						if (EsRetencion)
							BaseImponible = Env.ZERO;	// No suma
						if (!TipoImpuesto.equals(LCO_FE_Utils.CODIGO_NO_CAUSA_ZY)) {
							if (TipoImpuesto.equals(LCO_FE_Utils.CODIGO_IVA_01) && !EsExento && PorcentajeImpuesto.compareTo(Env.ZERO) == 0)
								m_sumabaseexcluida = m_sumabaseexcluida.add(BaseImponible);
							else
								m_sumabaseimponible = m_sumabaseimponible.add(BaseImponible);
							if (!EsRetencion)
								m_sumavalorimpuesto = m_sumavalorimpuesto.add(ImporteImpuesto);
							else
								m_sumavalorretenciones = m_sumavalorretenciones.add(ImporteImpuesto);
						}
					}
				}
			// }

			mmDoc.endElement("","",m_Root_Node);

			mmDoc.endDocument();

			if (mmDocStream != null) {
				try {
					mmDocStream.close();
				} catch (Exception e2) {}
			}

			if (m_sumanolineas != m_totalnolineas) {
				msg = "@DiffParentValue@ @NoOfLines@ @Total@: " + m_totalnolineas + "@sales.details@" + m_sumanolineas;
				return "@Error@ @invoice.no@ "+ inv.getDocumentNo() + " " + msg;
			}
			
			if (inv.isDiscountPrinted() && m_sumadescuento.compareTo(m_totaldescuento) != 0 ) {
				msg = "@DiffParentValue@ @discount.amt@ @Total@: " + m_totaldescuento.toString() + " @sales.details@ " + m_sumadescuento.toString();
				s_log.warning("@Error@ @invoice.no@ "+ inv.getDocumentNo() + " " + msg);
				msg = null;	// TODO Bypass temp
			}

			if (m_sumabaseimponible.compareTo(m_totalbaseimponible) != 0
					&& m_totalbaseimponible.subtract(m_sumabaseimponible).abs().compareTo(LCO_FE_Utils.CERO_PUNTO_CINCO) > 1) {
				msg = "@DiffParentValue@ @TaxBase@ @Total@: " + m_totalbaseimponible.toString() + " @sales.details@ " + m_sumabaseimponible.toString();
				return "@Error@ @invoice.no@ "+ inv.getDocumentNo() + " " + msg;
			}

			if (m_sumavalorimpuesto.compareTo(m_totalvalorimpuesto) != 0
					&& m_totalvalorimpuesto.subtract(m_sumavalorimpuesto).abs().compareTo(LCO_FE_Utils.CERO_PUNTO_CINCO) > 1) {
				msg = "@DiffParentValue@ @smenu.tax@ @Total@: " + m_totalvalorimpuesto.toString() + " @sales.details@ " + m_sumavalorimpuesto.toString();
				return "@Error@ @invoice.no@ "+ inv.getDocumentNo() + " " + msg;
			}

			//
		} catch (Exception e) {
			msg = Msg.getMsg(Env.getCtx(), "FileCannotCreate") + " "
					+ m_File_Type + "-"
					+ e.getLocalizedMessage();
			s_log.severe(msg);
			return msg;
		}

		return msg;

	} 	// lcofeinvcr_GovGeneraCreditNoteXML210
	
	/**
	 * Genera XML ND v2.1
	 * Prefijo fe desaparece quedando el prefijo propio que maneja el UBL: cac
	 * @param
	 * @return msg
	 */
	public String lcofeinvdr_GovGeneraDebitNoteXML210(
			String file_name,
			String m_coddoc,
			LCO_FE_MInvoice inv,
			MDocType dt,
			X_LCO_FE_DocType fedt,
			X_LCO_FE_OperationType feot,
			X_LCO_FE_DIAN_Format f,
			X_LCO_PrintedFormControl pfc,
			X_LCO_FE_OFE_Contingency feoc,
			MOrgInfo oi,
			MBPartner bpe,
			X_LCO_TaxPayerType tpte,
			X_LCO_TaxIdType tte,
			X_LCO_ISIC ie,
			MLocation lo,
			MLocation locc,
			MUser uo,
			X_LCO_FE_ContactType uoct,
			MBPartner bp,
			X_LCO_TaxPayerType tpta,
			X_LCO_TaxIdType tta,
			MLocation la,
			MLocation lacc,
			MUser ua,
			X_LCO_FE_ContactType uact,
			X_LCO_FE_NCConcept fencc,
			X_LCO_FE_NDConcept fendc
			)
	{

		m_sumanolineas = 0;
		m_sumadescuento = Env.ZERO;
		m_sumabaseimponible = Env.ZERO;
		m_sumavalorimpuesto = Env.ZERO;

		String msg = null;
		
		MInvoice invref = null;
		MLCOFEAuthorization autref = null;
		MDocType dtref = null;
		X_LCO_FE_DocType fedtref = null;
		X_LCO_FE_DIAN_Format fref = null;
		
		// Reference
		if (inv.getRelatedInvoice_ID() > 0 && inv.get_ValueAsInt("LCO_FE_OperationType_ID") != 1000036) {	// Nota Débito sin referencia a facturas
			invref = new MInvoice (inv.getCtx(), inv.getRelatedInvoice_ID(), inv.get_TrxName());
			autref = new MLCOFEAuthorization (inv.getCtx(), invref.get_ValueAsInt("LCO_FE_Authorization_ID"), inv.get_TrxName());
			dtref = new MDocType(inv.getCtx(), invref.getC_DocTypeTarget_ID(), inv.get_TrxName());
			fedtref = new X_LCO_FE_DocType (inv.getCtx(), dtref.get_ValueAsInt("LCO_FE_DocType_ID"), inv.get_TrxName());
			fref = new X_LCO_FE_DIAN_Format (inv.getCtx(), autref.getLCO_FE_DIAN_Format_ID(), inv.get_TrxName());
		}
		
		try {

			OutputStream  mmDocStream = null;

			//Stream para el documento xml
			mmDocStream = new FileOutputStream (file_name, false);
			StreamResult streamResult_menu = new StreamResult(new OutputStreamWriter(mmDocStream,"UTF-8"));
			SAXTransformerFactory tf_menu = (SAXTransformerFactory) SAXTransformerFactory.newInstance();					
			try {
				tf_menu.setAttribute("indent-number", Integer.valueOf(2));
			} catch (Exception e) {
				// swallow
			}
			TransformerHandler mmDoc = tf_menu.newTransformerHandler();	
			Transformer serializer_menu = mmDoc.getTransformer();	
			serializer_menu.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			serializer_menu.setOutputProperty(OutputKeys.STANDALONE, "no");
			serializer_menu.setOutputProperty(OutputKeys.INDENT,"yes");
			// serializer_menu.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
			mmDoc.setResult(streamResult_menu);

			mmDoc.startDocument();

			AttributesImpl atts = new AttributesImpl();

			// Encabezado
			atts.clear();
			// atts.addAttribute("", "", "id", "CDATA", "comprobante");
			// atts.addAttribute("", "", "version", "CDATA", f.get_ValueAsString("VersionNo"));
			atts = addHeaderAttribute(fedt.getDianShortDocType(), LCO_FE_Utils.SIGNER_POLICY_V2); 

			mmDoc.startElement("", "", m_Root_Node, atts);

			atts.clear();

			// Emisor = Org
			// Adquiriente = BPartner
			// Requerido: M-Mandatorio, D-Dependiente, O-Opcional
			// Tipo: A-Alfabetico, AN-Alfanumrrico, N-Numerico
			// Long.

			// Informaci\u00f3n Cabecera
			
			// Extensions
			mmDoc.startElement("","","ext:UBLExtensions", atts);
				mmDoc.startElement("","","ext:UBLExtension", atts);
					mmDoc.startElement("","","ext:ExtensionContent", atts);
						mmDoc.startElement("","","sts:DianExtensions", atts);
							mmDoc.startElement("","","sts:InvoiceSource", atts);
								atts.clear();
								atts.addAttribute("", "", "listAgencyID", "CDATA", "6");
								atts.addAttribute("", "", "listAgencyName", "CDATA", "United Nations Economic Commission for Europe");
								atts.addAttribute("", "", "listSchemeURI", "CDATA", "urn:oasis:names:specification:ubl:codelist:gc:CountryIdentificationCode-2.1");
								// M A 2 Literal "CO"
								addHeaderElement(mmDoc, "cbc:IdentificationCode", lo.getCountry().getCountryCode(), atts);
								atts.clear();
							mmDoc.endElement("","","sts:InvoiceSource");
							mmDoc.startElement("","","sts:SoftwareProvider", atts);
								atts.clear();
								atts = addGovAttribute21(bpe.get_ValueAsString("TaxIdDigit"), LCO_FE_Utils.cutString(tte.get_ValueAsString("LCO_TaxCodeDian"), 7));
								// M N 5..12 NIT del Prestador de Servicios
								addHeaderElement(mmDoc, "sts:ProviderID", bpe.getTaxID().trim(), atts);
								atts.clear();
								atts = addGovAttribute21(null, null);
								// M AN, Identificador Software
								addHeaderElement(mmDoc, "sts:SoftwareID", oi.get_ValueAsString("LCO_FE_UserName"), atts);
								atts.clear();
							mmDoc.endElement("","","sts:SoftwareProvider");
							atts.clear();
							atts = addGovAttribute21(null, null);
							addHeaderElement(mmDoc, "sts:SoftwareSecurityCode", m_IdAccount, atts);
							atts.clear();
							// Proveedor Autorizado (PA) por la DIAN
							mmDoc.startElement("","","sts:AuthorizationProvider", atts);
								atts = addGovAttribute21("4", "31");	// NIT DV de DIAN 4
								// M N Debe corresponder al Nit de la DIAN
								addHeaderElement(mmDoc, "sts:AuthorizationProviderID", "800197268", atts);
							mmDoc.endElement("","","sts:AuthorizationProvider");
							atts.clear();
							// M AN
							addHeaderElement(mmDoc, "sts:QRCode", m_QR, atts);
						mmDoc.endElement("","","sts:DianExtensions");
					mmDoc.endElement("","","ext:ExtensionContent");
				mmDoc.endElement("","","ext:UBLExtension");
				mmDoc.startElement("","","ext:UBLExtension", atts);
					mmDoc.startElement("","","ext:ExtensionContent", atts);
						// <ds:Signature
						// </ds:Signature>
					mmDoc.endElement("","","ext:ExtensionContent");
				mmDoc.endElement("","","ext:UBLExtension");
			mmDoc.endElement("","","ext:UBLExtensions");
			//
			// M AF Max 7, Version UBL
			addHeaderElement(mmDoc, "cbc:UBLVersionID", LCO_FE_Utils.cutString(f.getUBLVersionNo(), 7), atts);	// UBL 2.1
			// M AN 1..4, Indicador del tipo de operación
			addHeaderElement(mmDoc, "cbc:CustomizationID", LCO_FE_Utils.cutString(feot.getValue(), 2), atts);
			// M AF Max 55, Version Formato
			addHeaderElement(mmDoc, "cbc:ProfileID", LCO_FE_Utils.cutString(f.getVersionNo() + ": " + f.getName(), 55), atts);	// DIAN 2.1
			// M N 1, Ambiente de Destino
			addHeaderElement(mmDoc, "cbc:ProfileExecutionID", m_EnvType, atts);
			// M AF Max 20, Prefijo + No. Documento Formato
			addHeaderElement(mmDoc, "cbc:ID", inv.getDocumentNo(), atts); // With Prefix getDocumentNo()
			atts.clear();
			atts = addGovAttribute21(m_EnvType, f.getLCO_FE_UuidType());
			// M AF 96, CUFE de la Nota Débito (CUDE según resolución)
			addHeaderElement(mmDoc, "cbc:UUID", m_CUFE, atts);
			atts.clear();
			// M AN Max 10, Fecha de emisi\u00f3n de la factura Formato AAAA-MM-DD
			addHeaderElement(mmDoc, "cbc:IssueDate", LCO_FE_Utils.getDateTime((Timestamp) inv.get_Value("LCO_FE_DateTrx"), 11), atts);
			// M AN Max 14, Hora de emisi\u00f3n de la factura Formato HH:MM:DD-05:00
			addHeaderElement(mmDoc, "cbc:IssueTime", LCO_FE_Utils.getDateTime((Timestamp) inv.get_Value("LCO_FE_DateTrx"), 14), atts);
			//
			atts.clear();
			// atts.addAttribute("", "", "listAgencyID", "CDATA", "195");
			// atts.addAttribute("", "", "listAgencyName", "CDATA", "CO, DIAN (Direccion de Impuestos y Aduanas Nacionales)");
			// atts.addAttribute("", "", "listSchemeURI", "CDATA", "http://www.dian.gov.co/contratos/facturaelectronica/v1/InvoiceType");
			// M AN Max 2, Tipo de documento
//			addHeaderElement(mmDoc, "cbc:DebitNoteTypeCode", LCO_FE_Utils.cutString(fedt.getDianDocTypeCode(), 2), atts);
//			atts.clear();
			// O AF 15..5000, Información adicional: Texto libre
			addHeaderElement(mmDoc, "cbc:Note", "QRCode: " + m_QR, atts);
			// D AN Max 3, Divisa de la Factura
			addHeaderElement(mmDoc, "cbc:DocumentCurrencyCode", inv.getCurrencyISO(), atts);
			//
			List<List<Object>> lines = DB.getSQLArrayObjectsEx(inv.get_TrxName(), LCO_FE_Utils.SQL_ITEMS, inv.getC_Invoice_ID());
			m_totalnolineas = lines.size();
			// M N 3, Número de elementos InvoiceLine
			addHeaderElement(mmDoc, "cbc:LineCountNumeric", String.valueOf(m_totalnolineas), atts);
			
			mmDoc.startElement("","","cac:DiscrepancyResponse", atts);
			// <cbc:ResponseCode listName="concepto de notas crédito" listSchemeURI="http://www.dian.gov.co/micrositios/fac_electronica/documentos/Anexo_Tecnico_001_Formatos_de_los_Documentos_XML_de_Facturacion_Electron.pdf" name="2:= anulación de la factura electrónica">2</cbc:ResponseCode>
			atts.clear();
			// O N 4 Identifica la sección de la factura original a la cual se aplica la corrección
			addHeaderElement(mmDoc, "cbc:ReferenceID", "", atts);
			// O N 1 Código de descripción de la corrección. Tabla 4
			addHeaderElement(mmDoc, "cbc:ResponseCode", fendc.getValue(), atts);
			atts.clear();
			// M AN Max 5000, Descripción de la naturaleza de la corrección 
			addHeaderElement(mmDoc, "cbc:Description", inv.getDescription() != null ? inv.getDescription() : fendc.getName(), atts);
			mmDoc.endElement("","","cac:DiscrepancyResponse");
			//
			if (inv.getRelatedInvoice_ID() > 0 && inv.get_ValueAsInt("LCO_FE_OperationType_ID") != 1000036) {	// Nota Débito sin referencia a facturas
				atts.clear();
				mmDoc.startElement("","","cac:BillingReference", atts);
					mmDoc.startElement("","","cac:InvoiceDocumentReference", atts);
						// M AF Max 20, Prefijo + Número de la factura relacionada
						addHeaderElement(mmDoc, "cbc:ID", invref.getDocumentNo(), atts); // With Prefix getDocumentNo()
						atts.clear();
						if (fedtref.getDianShortDocType().equals(LCO_FE_Utils.TIPO_COMPROBANTE_FACTURA)
							|| fedtref.getDianShortDocType().equals(LCO_FE_Utils.TIPO_COMPROBANTE_EXPORTACION)) {
							atts.addAttribute("", "", "schemeName", "CDATA", fref.getLCO_FE_UuidType());
							// M AN 96 CUFE de la factura relacionada 
							addHeaderElement(mmDoc, "cbc:UUID", autref.getLCO_FE_Dian_Uuid() != null ? autref.getLCO_FE_Dian_Uuid() : "", atts);	// getCUFE()
						}
						atts.clear();	
						// M AN Max 10, Fecha de emisi\u00f3n de la factura Formato AAAA-MM-DD
						addHeaderElement(mmDoc, "cbc:IssueDate", LCO_FE_Utils.getDateTime((Timestamp) invref.get_Value("LCO_FE_DateTrx") != null ? (Timestamp) invref.get_Value("LCO_FE_DateTrx") : invref.getDateInvoiced(), 11), atts);
					mmDoc.endElement("","","cac:InvoiceDocumentReference");
				mmDoc.endElement("","","cac:BillingReference");
			}

			// Informaci\u00f3n Emisor
			atts.clear();
			mmDoc.startElement("","","cac:AccountingSupplierParty", atts);
				atts.clear();
				atts.addAttribute("", "", "schemeAgencyID", "CDATA", "195");
				// M N 1, Tipo de organización jurídica Tabla 9
				addHeaderElement(mmDoc, "cbc:AdditionalAccountID", tpte.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_GRAN_CONTRIBUYENTE) ? LCO_FE_Utils.TIPO_PERSONA_JURIDICA : tpte.get_ValueAsString("DianTaxPayerCode"), atts);
				atts.clear();
				mmDoc.startElement("","","cac:Party", atts);
					// O N 4, código de actividad económica CIIU
					// addHeaderElement(mmDoc, "cbc:IndustryClasificationCode", ie.getValue().substring(1, 5), atts);	// Fix ZB01
					mmDoc.startElement("","","cac:PartyIdentification", atts);
					atts.clear();
					// M AN 7, Tipo identificaci\u00f3n Tabla 2
					atts = addGovAttribute21(null, LCO_FE_Utils.cutString(tte.get_ValueAsString("LCO_TaxCodeDian"), 7));
					// M AF Max 35, No. Identificacion Emisor
					addHeaderElement(mmDoc, "cbc:ID", bpe.getTaxID().trim(), atts);
					atts.clear();
					mmDoc.endElement("","","cac:PartyIdentification");
					if ( tpte.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_PERSONA_JURIDICA)
							|| tpte.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_GRAN_CONTRIBUYENTE) ) {
						mmDoc.startElement("","","cac:PartyName", atts);
							// D AN 5..450 Nombre comercial del emisor
							addHeaderElement(mmDoc, "cbc:Name", bpe.getName(), atts);
						mmDoc.endElement("","","cac:PartyName");
					}
					mmDoc.startElement("","","cac:PhysicalLocation", atts);
						mmDoc.startElement("","","cac:Address", atts);
						// M N 5 Codigo del municipio
						addHeaderElement(mmDoc, "cbc:ID", LCO_FE_Utils.cutString(lo.getC_City().getPostal(), 5), atts);
						// M AN Max 60 Nombre de la ciudad
						addHeaderElement(mmDoc, "cbc:CityName", lo.getC_City().getName(), atts);
						// M AN Max 60 Nombre del Departamento
						addHeaderElement(mmDoc, "cbc:CountrySubentity", lo.getRegionName(), atts);
						// M N 1..5 Código del Departamento
						addHeaderElement(mmDoc, "cbc:CountrySubentityCode", LCO_FE_Utils.cutString(lo.getRegion().getDescription(), 2), atts);
						// X AN Max 40 Municipio (distrito administrativo)
						// addHeaderElement(mmDoc, "cbc:CitySubdivisionName", lo.getC_City().getName(), atts);
						mmDoc.startElement("","","cac:AddressLine", atts);
							// M AN Max 300 Direccion
							addHeaderElement(mmDoc, "cbc:Line", lo.getAddress1(), atts);
						mmDoc.endElement("","","cac:AddressLine");
						mmDoc.startElement("","","cac:Country", atts);
							// M AN Max 2 Pais Tabla 8
							addHeaderElement(mmDoc, "cbc:IdentificationCode", lo.getCountry().getCountryCode(), atts);
							// O A 4..40 Nombre Pais Tabla 8
							atts.addAttribute("", "", "languageID", "CDATA", LCO_FE_Utils.cutString(Env.getAD_Language(Env.getCtx()), 2));
							addHeaderElement(mmDoc, "cbc:Name", lo.getCountry().get_Translation("Name"), atts);
							atts.clear();
						mmDoc.endElement("","","cac:Country");
						mmDoc.endElement("","","cac:Address");
					mmDoc.endElement("","","cac:PhysicalLocation");
					mmDoc.startElement("","","cac:PartyTaxScheme", atts);
						// M AF 5..450, Nombre o Razón Social del emisor
						addHeaderElement(mmDoc, "cbc:RegistrationName", bpe.getName(), atts);
						atts.clear();
						atts = addGovAttribute21(bpe.get_ValueAsString("TaxIdDigit"), LCO_FE_Utils.cutString(tte.get_ValueAsString("LCO_TaxCodeDian"), 7));	// TODO Reviewme
						// M N 5..12, No. Identificacion Emisor
						addHeaderElement(mmDoc, "cbc:CompanyID", bpe.getTaxID().trim(), atts);
						atts.clear();
						{ // BP Info Emisor
							boolean nobpeinfo = true;
							String codigosRUT = "";
							List<List<Object>> rowse = DB.getSQLArrayObjectsEx(bpe.get_TrxName(), LCO_FE_Utils.SQL_BP_INFO, bpe.getC_BPartner_ID());
							if (rowse != null) {
								for (List<Object> rowe : rowse) {
									// String grupoRUT = rowe.get(0).toString();
									String codigoRUT = rowe.get(1).toString();
									if (Util.isEmpty(codigoRUT, true)) {
										nobpeinfo = true;
										break;
									}
									nobpeinfo = false;
									codigosRUT = codigosRUT + codigoRUT + ";";
								}
								atts.clear();
								atts.addAttribute("", "", "listName", "CDATA", LCO_FE_Utils.cutString(tpte.get_ValueAsString("DianRegimeCode"), 2));
								// M AN 1 Max 30, Obligaciones del contribuyente
								addHeaderElement(mmDoc, "cbc:TaxLevelCode", codigosRUT.substring(0, codigosRUT.length()-1), atts);
								atts.clear();
							}
							if (nobpeinfo) {
								msg = (Msg.translate(Env.getCtx(), "FillMandatory") + " "
										+ Msg.getElement(Env.getCtx(), MBPartner.COLUMNNAME_C_BPartner_ID) + "-"
										+ Msg.getElement(Env.getCtx(), "LCO_FE_TributaryType_ID"));
								return "@Error@ @invoice.no@ " + inv.getDocumentNo() + " " + msg;
							}
						}
						if (tpte.get_ValueAsString("DianRegimeCode").equals(LCO_FE_Utils.TIPO_REGIMEN_FISCAL_48)) {
							// M 1 Veces max
							mmDoc.startElement("","","cac:TaxScheme", atts);	// TODO Fixed ?
								// M AF 3..10, Identificador del tributo
								addHeaderElement(mmDoc, "cbc:ID", LCO_FE_Utils.CODIGO_IVA_01, atts);	// TipoImpuesto
								// M AF 10..30, Nombre del tributo
								addHeaderElement(mmDoc, "cbc:Name", "IVA", atts);	// TODO NombreImpuesto
							mmDoc.endElement("","","cac:TaxScheme");
						}
					mmDoc.endElement("","","cac:PartyTaxScheme");
					mmDoc.startElement("","","cac:PartyLegalEntity", atts);
						// M AF 5..450, Nombre o Razón Social del emisor
						addHeaderElement(mmDoc, "cbc:RegistrationName", bpe.getName(), atts);
						atts.clear();
						atts = addGovAttribute21(bpe.get_ValueAsString("TaxIdDigit"), LCO_FE_Utils.cutString(tte.get_ValueAsString("LCO_TaxCodeDian"), 7));	// TODO Reviewme
						// M N 5..12, NIT del emisor
						addHeaderElement(mmDoc, "cbc:CompanyID", bpe.getTaxID().trim(), atts);
						atts.clear();
						mmDoc.startElement("","","cac:CorporateRegistrationScheme", atts);
							if (! "".equals(m_Prefix))
								// M N 6, Prefijo de la facturación usada para el punto de venta
								addHeaderElement(mmDoc, "cbc:ID", m_Prefix, atts);	// TODO reviewme
							// O N 6, Número de matrícula mercantil (identificador de sucursal: punto de facturación) 
							// addHeaderElement(mmDoc, "cbc:Name", m_Prefix, atts);	// TODO
						mmDoc.endElement("","","cac:CorporateRegistrationScheme");
					mmDoc.endElement("","","cac:PartyLegalEntity");
					String mailContacto = MSysConfig.getValue("QSSLCO_FE_ReplyToEMail", null, inv.getAD_Client_ID());
					if (mailContacto != null) {
						mmDoc.startElement("","","cac:Contact", atts);
							// O AN Max 250 Correo electrónico  de la persona de contacto.
							addHeaderElement(mmDoc, "cbc:ElectronicMail", mailContacto, atts);
						mmDoc.endElement("","","cac:Contact");
					}
				mmDoc.endElement("","","cac:Party");
			mmDoc.endElement("","","cac:AccountingSupplierParty");
			
			// Informacion Adquiriente
			mmDoc.startElement("","","cac:AccountingCustomerParty", atts);
				// M N 1, Tipo de persona Tabla 9
				addHeaderElement(mmDoc, "cbc:AdditionalAccountID", tpta.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_GRAN_CONTRIBUYENTE) ? LCO_FE_Utils.TIPO_PERSONA_JURIDICA : tpta.get_ValueAsString("DianTaxPayerCode"), atts);
				mmDoc.startElement("","","cac:Party", atts);
					mmDoc.startElement("","","cac:PartyIdentification", atts);
					atts.clear();
					// M AN 7, Tipo identificaci\u00f3n Tabla 2
					atts = addGovAttribute21(null, LCO_FE_Utils.cutString(tta.get_ValueAsString("LCO_TaxCodeDian"), 7));
					// M AF Max 35, No. Identificacion Adquiriente
					addHeaderElement(mmDoc, "cbc:ID", bp.getTaxID(), atts);
					atts.clear();
					mmDoc.endElement("","","cac:PartyIdentification");
					if ( tpta.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_PERSONA_JURIDICA)
							|| tpta.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_GRAN_CONTRIBUYENTE) ) {
						mmDoc.startElement("","","cac:PartyName", atts);
							// D AN 5..450 Nombre comercial del adquiriente
							addHeaderElement(mmDoc, "cbc:Name", bp.getName(), atts);
						mmDoc.endElement("","","cac:PartyName");
					} else if ( tpta.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_PERSONA_NATURAL) ) {
						mmDoc.startElement("","","cac:Person", atts);	// MAx 450
							// D AN Max 150 Nombre del adquiriente
							addHeaderElement(mmDoc, "cbc:FirstName", bp.get_ValueAsString("FirstName1"), atts);
							// D AN Max 150 Nombre del adquiriente
							addHeaderElement(mmDoc, "cbc:MiddleName", bp.get_Value("FirstName2") != null ? bp.get_ValueAsString("FirstName2") : "", atts);
							// D AN Max 150 Primer y segundo Apellido
							addHeaderElement(mmDoc, "cbc:FamilyName", bp.get_ValueAsString("LastName1")
									+ bp.get_Value("LastName2") != null ? " " + bp.get_ValueAsString("LastName2") : "", atts);
						mmDoc.endElement("","","cac:Person");
					}
					mmDoc.startElement("","","cac:PhysicalLocation", atts);
						mmDoc.startElement("","","cac:Address", atts);
						if (la.getC_City().getPostal() != null)
							// M N 5 Codigo del municipio
							addHeaderElement(mmDoc, "cbc:ID", LCO_FE_Utils.cutString(la.getC_City().getPostal(), 5), atts);
						if (la.getC_City().getName() != null)
							// M AN Max 60 Nombre de la ciudad
							addHeaderElement(mmDoc, "cbc:CityName", la.getC_City().getName(), atts);
						if (la.getRegionName() != null)
							// M AN Max 60 Nombre del Departamento
							addHeaderElement(mmDoc, "cbc:CountrySubentity", la.getRegionName(), atts);
						// M N 1..5 Código del Departamento
						if (la.getCountry().getCountryCode().equals(LCO_FE_Utils.COUNTRY_CODE_CO))
							addHeaderElement(mmDoc, "cbc:CountrySubentityCode", LCO_FE_Utils.cutString(la.getRegion().getDescription(), 2), atts);
						else
							addHeaderElement(mmDoc, "cbc:CountrySubentityCode", la.getCountry().getCountryCode(), atts);	// TODO En definicion
						// X AN Max 40 Municipio (distrito administrativo)
						// addHeaderElement(mmDoc, "cbc:CitySubdivisionName", la.getC_City().getName(), atts);
						mmDoc.startElement("","","cac:AddressLine", atts);
							// M AN Max 300 Direccion
							addHeaderElement(mmDoc, "cbc:Line", la.getAddress1(), atts);
						mmDoc.endElement("","","cac:AddressLine");
						mmDoc.startElement("","","cac:Country", atts);
							// M AN Max 2 Pais Tabla 8
							addHeaderElement(mmDoc, "cbc:IdentificationCode", la.getCountry().getCountryCode(), atts);
							// O A 4..40 Nombre Pais Tabla 8
							atts.addAttribute("", "", "languageID", "CDATA", LCO_FE_Utils.cutString(Env.getAD_Language(Env.getCtx()), 2));
							addHeaderElement(mmDoc, "cbc:Name", la.getCountry().get_Translation("Name"), atts);
							atts.clear();
						mmDoc.endElement("","","cac:Country");
					mmDoc.endElement("","","cac:Address");
					mmDoc.endElement("","","cac:PhysicalLocation");
					mmDoc.startElement("","","cac:PartyTaxScheme", atts);
						// D AN 5..450, Nombre comercial del adquiriente
						addHeaderElement(mmDoc, "cbc:RegistrationName", bp.getName(), atts);
						atts.clear();
						atts = addGovAttribute21(bp.get_ValueAsString("TaxIdDigit"), LCO_FE_Utils.cutString(tta.get_ValueAsString("LCO_TaxCodeDian"), 7));	// TODO Reviewme
						// M N 5..12, No. Identificacion Adquiriente
						addHeaderElement(mmDoc, "cbc:CompanyID", bp.getTaxID().trim(), atts);
						atts.clear();
						{ // BP Info Adquiriente
							boolean nobpainfo = true;
							String codigosRUT = "";
							List<List<Object>> rowsa = DB.getSQLArrayObjectsEx(bp.get_TrxName(), LCO_FE_Utils.SQL_BP_INFO, bp.getC_BPartner_ID());
							if (rowsa != null) {
								for (List<Object> rowa : rowsa) {
									// String grupoRUT = rowa.get(0).toString();
									String codigoRUT = rowa.get(1).toString();
									if (Util.isEmpty(codigoRUT, true)) {
										nobpainfo = true;
										break;
									}
									nobpainfo = false;
									codigosRUT = codigosRUT + codigoRUT + ";";
									}
									atts.clear();
									atts.addAttribute("", "", "listName", "CDATA", LCO_FE_Utils.cutString(tpta.get_ValueAsString("DianRegimeCode"), 2));
									// M AN 1 Max 30, Obligaciones del contribuyente
									addHeaderElement(mmDoc, "cbc:TaxLevelCode", LCO_FE_Utils.cutString(codigosRUT.substring(0, codigosRUT.length()-1), 30), atts);
									atts.clear();
								}
								if (nobpainfo) {
									msg = (Msg.translate(Env.getCtx(), "FillMandatory") + " "
											+ Msg.getElement(Env.getCtx(), MBPartner.COLUMNNAME_C_BPartner_ID) + "-"
											+ Msg.getElement(Env.getCtx(), "LCO_FE_TributaryType_ID"));
									return "@Error@ @invoice.no@ " + inv.getDocumentNo() + " " + msg;
								}
						}
						List<List<Object>> taxas = DB.getSQLArrayObjectsEx(inv.get_TrxName(), LCO_FE_Utils.SQL_TAX_INFO, inv.getC_Invoice_ID(), "%", inv.getC_Invoice_ID(), "%");
						if (taxas != null) {
							for (List<Object> taxa : taxas) {
								// M 1 Veces max
								mmDoc.startElement("","","cac:TaxScheme", atts);
									// M AF 3..10, Identificador del tributo
									addHeaderElement(mmDoc, "cbc:ID", taxa.get(3).toString(), atts);	// TipoImpuesto
									// M AF 10..30, Nombre del tributo
									addHeaderElement(mmDoc, "cbc:Name", taxa.get(6).toString(), atts);	// NombreImpuesto
								mmDoc.endElement("","","cac:TaxScheme");
								break;	// First
							}
						}
					mmDoc.endElement("","","cac:PartyTaxScheme");
					if ( tpte.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_PERSONA_JURIDICA)
							|| tpte.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_GRAN_CONTRIBUYENTE) ) {
						mmDoc.startElement("","","cac:PartyLegalEntity", atts);
							// M AF 5..450, Nombre o Razón Social del adquiriente
							addHeaderElement(mmDoc, "cbc:RegistrationName", bp.getName(), atts);
							atts.clear();
							atts = addGovAttribute21(bp.get_ValueAsString("TaxIdDigit"), LCO_FE_Utils.cutString(tta.get_ValueAsString("LCO_TaxCodeDian"), 7));	// TODO Reviewme
							// M N 5..12, ID del Adquiriente
							addHeaderElement(mmDoc, "cbc:CompanyID", bp.getTaxID(), atts);
							atts.clear();
						mmDoc.endElement("","","cac:PartyLegalEntity");
					} else if ( tpte.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_PERSONA_NATURAL) ) {
						mmDoc.startElement("","","cac:Person", atts);	// Max 450
							// D AN Max 150 Nombre del adquiriente
							addHeaderElement(mmDoc, "cbc:FirstName", bp.get_ValueAsString("FirstName1"), atts);
							// D AN Max 150 Nombre del adquiriente
							addHeaderElement(mmDoc, "cbc:MiddleName", bp.get_Value("FirstName2") != null ? bp.get_ValueAsString("FirstName2") : "", atts);
							// D AN Max 150 Primer y segundo Apellido
							addHeaderElement(mmDoc, "cbc:FamilyName", bp.get_ValueAsString("LastName1")
									+ bp.get_Value("LastName2") != null ? " " + bp.get_ValueAsString("LastName2") : "", atts);
						mmDoc.endElement("","","cac:Person");
					}
					if (ua.getEMail() != null) {
						mmDoc.startElement("","","cac:Contact", atts);
							// O AN Max 250 Correo electrónico  de la persona de contacto.
							addHeaderElement(mmDoc, "cbc:ElectronicMail", ua.getEMail().trim(), atts);
						mmDoc.endElement("","","cac:Contact");
					}
				mmDoc.endElement("","","cac:Party");
			mmDoc.endElement("","","cac:AccountingCustomerParty");
			
			if (m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_ND)) {
				// M 1..1, Método de pago
				mmDoc.startElement("","","cac:PaymentMeans",atts);
				// O AN Max 3 Código correspondiente al medio de pago Tabla 5
				addHeaderElement(mmDoc, "cbc:ID", inv.getPaymentRule().equals(MInvoice.PAYMENTRULE_DirectDeposit) ? LCO_FE_Utils.MEDIO_PAGO_DEBITO_CA : LCO_FE_Utils.MEDIO_PAGO_NO_DEFINIDO, atts);
				// D AN Max 3 Método de pago. Tabla 26
				if (inv.getPaymentRule().equals(MInvoice.PAYMENTRULE_OnCredit))
					addHeaderElement(mmDoc, "cbc:PaymentMeansCode", LCO_FE_Utils.METODO_PAGO_CREDITO, atts);
				else if (inv.getPaymentRule().equals(MInvoice.PAYMENTRULE_DirectDeposit))
					addHeaderElement(mmDoc, "cbc:PaymentMeansCode", LCO_FE_Utils.METODO_PAGO_CONTADO, atts);
				// D AN Max 10 Fecha de pago Formato AAAA-MM-DD
				if (inv.getPaymentRule().equals(MInvoice.PAYMENTRULE_OnCredit)) {
					Object[] args = new Object[] { inv.getC_PaymentTerm_ID(), (Timestamp) inv.get_Value("LCO_FE_DateTrx")};
					Timestamp duedate = DB.getSQLValueTSEx(null, "SELECT paymenttermduedate(?, ?)", args);
					addHeaderElement(mmDoc, "cbc:PaymentDueDate", LCO_FE_Utils.getDateTime(duedate, 11), atts);
				}
				mmDoc.endElement("","","cac:PaymentMeans");
			}
			
			{ // Impuestos
				List<List<Object>> rows = DB.getSQLArrayObjectsEx(inv.get_TrxName(), LCO_FE_Utils.SQL_TAX_GROUP, inv.getC_Invoice_ID(), inv.getC_Invoice_ID());
				if (rows != null) {
					// no retenidos
					for (List<Object> row : rows) {
						boolean EsRetencion = (row.get(0) != null && ((String)row.get(0)).equals("Y")) ? true : false;
						BigDecimal ImporteImpuesto = (BigDecimal) row.get(1);
						String Moneda = (String) row.get(2);
						String TipoImpuestoGrupo = (String) row.get(3);
						BigDecimal BaseImponible = (BigDecimal) row.get(4);
						BigDecimal PorcentajeImpuesto = Env.ZERO;
						String NombreImpuesto = (String) row.get(5);
						if (Util.isEmpty(TipoImpuestoGrupo, true)) {
							msg = (Msg.translate(Env.getCtx(), "FillMandatory") + " "
									+ Msg.getElement(Env.getCtx(), MTaxCategory.COLUMNNAME_C_TaxCategory_ID) + "-"
									+ Msg.getElement(Env.getCtx(), "IsWithHolding") + "-"
									+ Msg.getElement(Env.getCtx(), "LCO_FE_DianTaxCategoryCode"));
							return "@Error@ @invoice.no@ " + inv.getDocumentNo() + " " + msg;
						}
						if (EsRetencion)
							continue;
						// O 50 Veces max Impuestos
						mmDoc.startElement("","","cac:TaxTotal",atts);
							atts.clear();
							atts.addAttribute("", "", "currencyID", "CDATA", Moneda);
							// M N 14.4 Importe Impuesto: Importe del impuesto retenido
							addHeaderElement(mmDoc, "cbc:TaxAmount", LCO_FE_Utils.decimalFormat(ImporteImpuesto), atts);
							// M N 14.4 Redondeo agregado al total del impuesto
							addHeaderElement(mmDoc, "cbc:RoundingAmount", LCO_FE_Utils.decimalFormat(Env.ZERO), atts);
							atts.clear();
							// M AN 5 Impuesto retenido o retenci\u00f3n
							// addHeaderElement(mmDoc, "cbc:TaxEvidenceIndicator", EsRetencion ? "true" : "false", atts);
						
							List<List<Object>> rowsi = DB.getSQLArrayObjectsEx(inv.get_TrxName(), LCO_FE_Utils.SQL_TAX_INFO, inv.getC_Invoice_ID(), TipoImpuestoGrupo, inv.getC_Invoice_ID(), TipoImpuestoGrupo);
							for (List<Object> rowi : rowsi) {
								EsRetencion = (rowi.get(0) != null && ((String)rowi.get(0)).equals("Y")) ? true : false;
								ImporteImpuesto = (BigDecimal) rowi.get(1);
								Moneda = (String) rowi.get(2);
								String TipoImpuesto = (String) rowi.get(3);
								BaseImponible = (BigDecimal) rowi.get(4);
								PorcentajeImpuesto = (BigDecimal) rowi.get(5);
								NombreImpuesto = (String) rowi.get(6);
								// O 50 Veces max Total Impuestos.
								atts.clear();
								mmDoc.startElement("","","cac:TaxSubtotal",atts);
									atts.clear();
									atts.addAttribute("", "", "currencyID", "CDATA", Moneda);
									// M N 14.4 Base Imponible
									addHeaderElement(mmDoc, "cbc:TaxableAmount", LCO_FE_Utils.decimalFormat(BaseImponible), atts);
									atts.clear();
									atts.addAttribute("", "", "currencyID", "CDATA", Moneda);
									// M N 14.4 Importe Impuesto (detalle)
									addHeaderElement(mmDoc, "cbc:TaxAmount", LCO_FE_Utils.decimalFormat(ImporteImpuesto), atts);
									atts.clear();
									mmDoc.startElement("","","cac:TaxCategory",atts);
										// O N 3.3 Porcentaje del impuesto
										addHeaderElement(mmDoc, "cbc:Percent", LCO_FE_Utils.decimalFormat(PorcentajeImpuesto), atts);
										mmDoc.startElement("","","cac:TaxScheme",atts);
											// M AN 3..10 Identificador del tributo Tabla 5
											addHeaderElement(mmDoc, "cbc:ID", TipoImpuesto, atts);
											// M AN 10..30 Nombre del tributo Tabla 5
											addHeaderElement(mmDoc, "cbc:Name", NombreImpuesto, atts);
										mmDoc.endElement("","","cac:TaxScheme");
									mmDoc.endElement("","","cac:TaxCategory");
								mmDoc.endElement("","","cac:TaxSubtotal");
								if (!TipoImpuesto.equals(LCO_FE_Utils.CODIGO_NO_CAUSA_ZY)) {
									m_totalbaseimponible = m_totalbaseimponible.add(BaseImponible);
									m_totalvalorimpuesto = m_totalvalorimpuesto.add(ImporteImpuesto);
								}
							}
						mmDoc.endElement("","","cac:TaxTotal");
					}
				}
			}
			
			// M 1 Valores Totales
			mmDoc.startElement("","","cac:RequestedMonetaryTotal", atts);
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// M N 14.4 Total Valor Bruto
				addHeaderElement(mmDoc, "cbc:LineExtensionAmount", LCO_FE_Utils.decimalFormat(inv.getTotalLines()), atts);
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// M N 14.4 Total Valor Base Imponible menos Excluidos
				addHeaderElement(mmDoc, "cbc:TaxExclusiveAmount", LCO_FE_Utils.decimalFormat(m_totalbaseimponible), atts);
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// M N 14.4 Total de Valor Bruto más tributos
				addHeaderElement(mmDoc, "cbc:TaxInclusiveAmount", LCO_FE_Utils.decimalFormat(inv.getGrandTotalPlusWithholdings()), atts); // TODO Revieme
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// O N 14.4 Descuento Total
				if (inv.isDiscountPrinted())
					addHeaderElement(mmDoc, "cbc:AllowanceTotalAmount", LCO_FE_Utils.decimalFormat(m_totaldescuento), atts);	// TODO
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// O N 14.4 Cargo Total
				addHeaderElement(mmDoc, "cbc:ChargeTotalAmount", LCO_FE_Utils.decimalFormat(m_totalcargo), atts);	// TODO
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// O N 14.4 Anticipo Total
				addHeaderElement(mmDoc, "cbc:PrepaidAmount", LCO_FE_Utils.decimalFormat(m_totalanticipo), atts);	// TODO
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// O N 14.4 Diferencia Total
				addHeaderElement(mmDoc, "cbc:PayableRoundingAmount", LCO_FE_Utils.decimalFormat(Env.ZERO), atts);	// TODO
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// M N 14.4 Valor a Pagar de Factura
				addHeaderElement(mmDoc, "cbc:PayableAmount", LCO_FE_Utils.decimalFormat(inv.getGrandTotalPlusWithholdings()), atts);
				atts.clear();
			mmDoc.endElement("","","cac:RequestedMonetaryTotal");
			
			{ // Items
				// M 1500 Veces max \u00cdtems del Documento // TODO
				List<List<Object>> rows = DB.getSQLArrayObjectsEx(inv.get_TrxName(), LCO_FE_Utils.SQL_ITEMS, inv.getC_Invoice_ID());
				if (rows == null) {
					msg = (Msg.translate(Env.getCtx(), "NoLines") + " "
							+ Msg.getElement(Env.getCtx(), MInvoice.COLUMNNAME_C_Invoice_ID));
					return "@Error@ @invoice.no@ " + inv.getDocumentNo() + " " + msg;
				}
				// if (rows != null) {
				    int i = 1;
					for (List<Object> row : rows) {
						BigDecimal Consecutivo = new BigDecimal(i);
						i++;
						BigDecimal qtyinvoiced = (BigDecimal) row.get(2);
						String UnidadMedida = (String) row.get(3);
						BigDecimal CostoTotal = (BigDecimal) row.get(4);
						String Iso_Code = (String) row.get(5);
						BigDecimal PrecioUnitario = (BigDecimal) row.get(6);
						String Iso_CodeDup = (String) row.get(7);
						//String pidentificador = (String) row.get(8);
						//String pdescription = (String) row.get(9);
						String ildescription = (String) row.get(10);
						//String name = (String) row.get(11);
						BigDecimal Descuento = (BigDecimal) row.get(12);
						//String pcodigo = (String) row.get(13);
						boolean EsRetencion = (row.get(14) != null && ((String)row.get(14)).equals("Y")) ? true : false;
						String TipoImpuesto = (String) row.get(15);
						BigDecimal ImporteImpuesto = (BigDecimal) row.get(16);
						String MonedaImporte = (String) row.get(17);
						BigDecimal BaseImponible = (BigDecimal) row.get(18);
						String MonedaBase = (String) row.get(19);
						BigDecimal PorcentajeImpuesto = (BigDecimal) row.get(20);
						String NombreImpuesto = (String) row.get(21);
						//String TipoProducto = (String) row.get(22);
						//String CodigoEstandar = (String) row.get(23);
						//String PartidaArancelaria = (String) row.get(24);
						
						atts.clear();
						mmDoc.startElement("","","cac:DebitNoteLine",atts);
							// M N 1..4, Número de Línea
							addHeaderElement(mmDoc, "cbc:ID", LCO_FE_Utils.cutString(Consecutivo.toString(), 4), atts);
							atts.addAttribute("", "", "unitCode", "CDATA", UnidadMedida);
							// M N 14.4, Cantidad del art\u00edculo solicitado
							addHeaderElement(mmDoc, "cbc:DebitedQuantity", String.format(Locale.US, "%.2f", qtyinvoiced), atts);
							atts.clear();
							atts.addAttribute("", "", "currencyID", "CDATA", Iso_Code);
							// D N 14.4, Costo Total
							addHeaderElement(mmDoc, "cbc:LineExtensionAmount", LCO_FE_Utils.decimalFormat(CostoTotal), atts);
							atts.clear();
//							addHeaderElement(mmDoc, "cbc:FreeOfChargeIndicator", (EsGratis ? "true" : "false"), atts);
//							// O 50 Veces max Total Impuestos \u00cdtem.
//							atts.clear();
							if (!TipoImpuesto.equals(LCO_FE_Utils.CODIGO_NO_CAUSA_ZY)) {
							mmDoc.startElement("","","cac:TaxTotal",atts);
								// if (!isTimHeader) {
									atts.clear();
									atts.addAttribute("", "", "currencyID", "CDATA", MonedaImporte);
									// M N 14.4 Importe Impuesto: Importe del impuesto retenido
									addHeaderElement(mmDoc, "cbc:TaxAmount", LCO_FE_Utils.decimalFormat(ImporteImpuesto), atts);
									// M N 14.4 Redondeo agregado al total del impuesto
									addHeaderElement(mmDoc, "cbc:RoundingAmount", LCO_FE_Utils.decimalFormat(Env.ZERO), atts);
									atts.clear();
									// M AN 5 Impuesto retenido o retenci\u00f3n
									// addHeaderElement(mmDoc, "cbc:TaxEvidenceIndicator", EsRetencion ? "true" : "false", atts);
									// isTimHeader = true;
								// }
								// O 50 Veces max Impuestos
								mmDoc.startElement("","","cac:TaxSubtotal",atts);
									atts.clear();
									atts.addAttribute("", "", "currencyID", "CDATA", MonedaBase);
									// M N 14.4 Base Imponible
									addHeaderElement(mmDoc, "cbc:TaxableAmount", LCO_FE_Utils.decimalFormat(BaseImponible), atts);
									atts.clear();
									atts.addAttribute("", "", "currencyID", "CDATA", MonedaImporte);
									// M N 14.4 Importe Impuesto (detalle)
									addHeaderElement(mmDoc, "cbc:TaxAmount", LCO_FE_Utils.decimalFormat(ImporteImpuesto), atts);
									atts.clear();
									mmDoc.startElement("","","cac:TaxCategory",atts);
										// O N 3.3 Porcentaje del impuesto
										addHeaderElement(mmDoc, "cbc:Percent", LCO_FE_Utils.decimalFormat(PorcentajeImpuesto), atts);
										mmDoc.startElement("","","cac:TaxScheme",atts);
											// M AN 3..10 Identificador del tributo Tabla 5
											addHeaderElement(mmDoc, "cbc:ID", TipoImpuesto, atts);
											// M AN 10..30 Nombre del tributo Tabla 5
											addHeaderElement(mmDoc, "cbc:Name", NombreImpuesto, atts);
										mmDoc.endElement("","","cac:TaxScheme");
									mmDoc.endElement("","","cac:TaxCategory");
								mmDoc.endElement("","","cac:TaxSubtotal");
							mmDoc.endElement("","","cac:TaxTotal");
							}
							//
							mmDoc.startElement("","","cac:Item",atts);
								if (ildescription != null) {
									// O AN 5..300, Descripci\u00f3n
									addHeaderElement(mmDoc, "cbc:Description", LCO_FE_Utils.cutString(ildescription, 300), atts);
								}
							mmDoc.endElement("","","cac:Item");
							atts.clear();
							mmDoc.startElement("","","cac:Price",atts);
								atts.clear();
								atts.addAttribute("", "", "currencyID", "CDATA", Iso_CodeDup);
								// D N 14.4, Precio Unitario
								addHeaderElement(mmDoc, "cbc:PriceAmount", LCO_FE_Utils.decimalFormat(PrecioUnitario), atts);
								atts.clear();
								// D A 1..3, Código del tipo de precio informado Tabla 6.3.10
								// addHeaderElement(mmDoc, "cbc:PriceTypeCode", Iso_CodeDup, atts);	// TODO reviewme
								atts.addAttribute("", "", "unitCode", "CDATA", UnidadMedida);
								// M N 1..6, Cantidad del art\u00edculo solicitado
								addHeaderElement(mmDoc, "cbc:BaseQuantity", String.format(Locale.US, "%.2f", qtyinvoiced), atts);
								atts.clear();
							mmDoc.endElement("","","cac:Price");
							
							// O 50 Veces max Total Impuestos \u00cdtem.
							// mmDoc.startElement("","","TII",atts);
							//
							// mmDoc.endElement("","","TII");
							
						mmDoc.endElement("","","cac:DebitNoteLine");
						
						m_sumanolineas ++;
						if (inv.isDiscountPrinted())
							m_sumadescuento = m_sumadescuento.add(Descuento);
						if (!EsRetencion) {
							m_sumabaseimponible = m_sumabaseimponible.add(BaseImponible);
							m_sumavalorimpuesto = m_sumavalorimpuesto.add(ImporteImpuesto);
						}
					}
				}
			// }

			mmDoc.endElement("","",m_Root_Node);

			mmDoc.endDocument();

			if (mmDocStream != null) {
				try {
					mmDocStream.close();
				} catch (Exception e2) {}
			}
			
			if (m_sumanolineas != m_totalnolineas) {
				msg = "@DiffParentValue@ @NoOfLines@ @Total@: " + m_totalnolineas + "@sales.details@" + m_sumanolineas;
				return "@Error@ @invoice.no@ "+ inv.getDocumentNo() + " " + msg;
			}
			
			if (inv.isDiscountPrinted() && m_sumadescuento.compareTo(m_totaldescuento) != 0 ) {
				msg = "@DiffParentValue@ @discount.amt@ @Total@: " + m_totaldescuento.toString() + " @sales.details@ " + m_sumadescuento.toString();
				s_log.warning("@Error@ @invoice.no@ "+ inv.getDocumentNo() + " " + msg);
				msg = null;	// TODO Bypass temp
			}

			if (m_sumabaseimponible.compareTo(m_totalbaseimponible) != 0
					&& m_totalbaseimponible.subtract(m_sumabaseimponible).abs().compareTo(LCO_FE_Utils.CERO_PUNTO_CINCO) > 1) {
				msg = "@DiffParentValue@ @TaxBase@ @Total@: " + m_totalbaseimponible.toString() + " @sales.details@ " + m_sumabaseimponible.toString();
				return "@Error@ @invoice.no@ "+ inv.getDocumentNo() + " " + msg;
			}

			if (m_sumavalorimpuesto.compareTo(m_totalvalorimpuesto) != 0
					&& m_totalvalorimpuesto.subtract(m_sumavalorimpuesto).abs().compareTo(LCO_FE_Utils.CERO_PUNTO_CINCO) > 1) {
				msg = "@DiffParentValue@ @smenu.tax@ @Total@: " + m_totalvalorimpuesto.toString() + " @sales.details@ " + m_sumavalorimpuesto.toString();
				return "@Error@ @invoice.no@ "+ inv.getDocumentNo() + " " + msg;
			}

			//
		} catch (Exception e) {
			msg = Msg.getMsg(Env.getCtx(), "FileCannotCreate") + " "
					+ m_File_Type + "-"
					+ e.getLocalizedMessage();
			s_log.severe(msg);
			return msg;
		}

		return msg;

	} 	// lcofeinvdr_GovGeneraDebitNoteXML210
	
	/**
	 * Genera XML FV, FE, FC v2.1
	 * Prefijo fe desaparece quedando el prefijo propio que maneja el UBL: cac
	 * @param
	 * @return msg
	 */
	public String lcofeinv_GovGeneraSoporteXML210(
			String file_name,
			String m_coddoc,
			LCO_FE_MInvoice inv,
			MDocType dt,
			X_LCO_FE_DocType fedt,
			X_LCO_FE_OperationType feot,
			X_LCO_FE_DIAN_Format f,
			X_LCO_PrintedFormControl pfc,
			X_LCO_FE_OFE_Contingency feoc,
			MOrgInfo oi,
			MBPartner bpe,
			X_LCO_TaxPayerType tpte,
			X_LCO_TaxIdType tte,
			X_LCO_ISIC ie,
			MLocation lo,
			MLocation locc,
			MUser uo,
			X_LCO_FE_ContactType uoct,
			MBPartner bp,
			X_LCO_TaxPayerType tpta,
			X_LCO_TaxIdType tta,
			MLocation la,
			MLocation lacc,
			MUser ua,
			X_LCO_FE_ContactType uact,
			X_LCO_FE_NCConcept fencc,
			X_LCO_FE_NDConcept fendc
			)
	{

		m_sumanolineas = 0;
		m_sumadescuento = Env.ZERO;
		m_sumabaseimponible = Env.ZERO;
		m_sumavalorimpuesto = Env.ZERO;

		String msg = null;

		try {

			OutputStream  mmDocStream = null;

			//Stream para el documento xml
			mmDocStream = new FileOutputStream (file_name, false);
			StreamResult streamResult_menu = new StreamResult(new OutputStreamWriter(mmDocStream,"UTF-8"));
			SAXTransformerFactory tf_menu = (SAXTransformerFactory) SAXTransformerFactory.newInstance();					
			try {
				tf_menu.setAttribute("indent-number", Integer.valueOf(2));
			} catch (Exception e) {
				// swallow
			}
			TransformerHandler mmDoc = tf_menu.newTransformerHandler();	
			Transformer serializer_menu = mmDoc.getTransformer();	
			serializer_menu.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			serializer_menu.setOutputProperty(OutputKeys.STANDALONE, "no");
			serializer_menu.setOutputProperty(OutputKeys.INDENT,"yes");
			// serializer_menu.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
			mmDoc.setResult(streamResult_menu);

			mmDoc.startDocument();

			AttributesImpl atts = new AttributesImpl();

			// Encabezado
			atts.clear();
			// atts.addAttribute("", "", "id", "CDATA", "comprobante");
			// atts.addAttribute("", "", "version", "CDATA", f.get_ValueAsString("VersionNo"));
			atts = addHeaderAttribute(fedt.getDianShortDocType(), LCO_FE_Utils.SIGNER_POLICY_V2); 

			mmDoc.startElement("", "", m_Root_Node, atts);

			atts.clear();

			// Emisor = Org
			// Adquiriente = BPartner
			// Requerido: M-Mandatorio, D-Dependiente, O-Opcional
			// Tipo: A-Alfabetico, AN-Alfanumrrico, N-Numerico
			// Long.

			// Informaci\u00f3n Cabecera
			
			// Extensions
			mmDoc.startElement("","","ext:UBLExtensions", atts);
				mmDoc.startElement("","","ext:UBLExtension", atts);
					mmDoc.startElement("","","ext:ExtensionContent", atts);
						mmDoc.startElement("","","sts:DianExtensions", atts);
							mmDoc.startElement("","","sts:InvoiceControl", atts);
								if (m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_SOPORTE)) {
									// M AN 14 N\u00famero autorizaci\u00f3n
									addHeaderElement(mmDoc, "sts:InvoiceAuthorization", pfc.getAuthorizationNo(), atts);
									mmDoc.startElement("","","sts:AuthorizationPeriod", atts);
										// M AN 10 Fecha de inicio Resolucion AAAA-MM-DD
										addHeaderElement(mmDoc, "cbc:StartDate", String.format("%1$tF", pfc.getValidFrom()), atts);
										// M AN 10 Fecha de fin Resolucion AAAA-MM-DD
										addHeaderElement(mmDoc, "cbc:EndDate", String.format("%1$tF", pfc.getValidUntil()), atts);
									mmDoc.endElement("","","sts:AuthorizationPeriod");
								}
								mmDoc.startElement("","","sts:AuthorizedInvoices", atts);
									// D AN 4 Prefijo
									if (pfc.getPrefix() != null )
										addHeaderElement(mmDoc, "sts:Prefix", pfc.getPrefix(), atts);
									// M N 9 Rango de Numeraci\u00f3n (m\u00ednimo).
									addHeaderElement(mmDoc, "sts:From", String.valueOf(pfc.getInitialSequence()), atts);
									// M N 9 Rango de Numeraci\u00f3n (m\u00e1ximo).
									addHeaderElement(mmDoc, "sts:To", String.valueOf(pfc.getFinalSequence()), atts);
								mmDoc.endElement("","","sts:AuthorizedInvoices");
							mmDoc.endElement("","","sts:InvoiceControl");
							mmDoc.startElement("","","sts:InvoiceSource", atts);
								atts.clear();
								atts.addAttribute("", "", "listAgencyID", "CDATA", "6");
								atts.addAttribute("", "", "listAgencyName", "CDATA", "United Nations Economic Commission for Europe");
								atts.addAttribute("", "", "listSchemeURI", "CDATA", "urn:oasis:names:specification:ubl:codelist:gc:CountryIdentificationCode-2.1");
								// M A 2 Literal "CO"
								addHeaderElement(mmDoc, "cbc:IdentificationCode", lo.getCountry().getCountryCode(), atts);
								atts.clear();
							mmDoc.endElement("","","sts:InvoiceSource");
							mmDoc.startElement("","","sts:SoftwareProvider", atts);
								atts.clear();
								atts = addGovAttribute21(bpe.get_ValueAsString("TaxIdDigit"), LCO_FE_Utils.cutString(tte.get_ValueAsString("LCO_TaxCodeDian"), 7));
								// M N 5..12 NIT del Prestador de Servicios
								addHeaderElement(mmDoc, "sts:ProviderID", bpe.getTaxID().trim(), atts);
								atts.clear();
								atts = addGovAttribute21(null, null);
								// M AN, Identificador Software
								addHeaderElement(mmDoc, "sts:SoftwareID", oi.get_ValueAsString("LCO_FE_UserName"), atts);
								atts.clear();
							mmDoc.endElement("","","sts:SoftwareProvider");
							atts.clear();
							atts = addGovAttribute21(null, null);
							addHeaderElement(mmDoc, "sts:SoftwareSecurityCode", m_IdAccount, atts);
							atts.clear();
							// Proveedor Autorizado (PA) por la DIAN
							mmDoc.startElement("","","sts:AuthorizationProvider", atts);
								atts = addGovAttribute21("4", "31");	// NIT DV de DIAN 4
								// M N Debe corresponder al Nit de la DIAN
								addHeaderElement(mmDoc, "sts:AuthorizationProviderID", "800197268", atts);
							mmDoc.endElement("","","sts:AuthorizationProvider");
							atts.clear();
							// M AN
							addHeaderElement(mmDoc, "sts:QRCode", m_QR, atts);
							//
							if (m_UseContingency) {
								// Error: Invalid content was found starting with element 'sts:OFEContingencia' -- temp commented
								// M AN Max 35 Identificador de la contingencia
								// addHeaderElement(mmDoc, "sts:OFEContingenciaID", feoc.getIdContingency(), atts);	// TODO uncomment FC
								/*
								mmDoc.startElement("","","sts:OFEContingencia", atts);
									mmDoc.startElement("","","sts:OFEContingenciaPeriod", atts);
										// M AN Max 10 Fecha de comienzo de la contingencia - Formato: AAAA-MM-DD
										addHeaderElement(mmDoc, "cbc:StartDate", String.format("%1$tF", feoc.getStartDate()), atts);
										// M AN Max 8, Hora de comienzo de la contingencia - Formato: hh:mm:ss
										addHeaderElement(mmDoc, "cbc:StartTime", String.format("%1$tT", feoc.getStartDate()), atts);
										// M AN Max 10 Fecha de terminaci\u00f3n de la contingencia - Formato: AAAA-MM-DD
										addHeaderElement(mmDoc, "cbc:EndDate", String.format("%1$tF", feoc.getEndDate()), atts);
										// M AN Max 8, Hora de terminaci\u00f3n de la contingencia - Formato: hh:mm:ss
										addHeaderElement(mmDoc, "cbc:EndTime", String.format("%1$tT", feoc.getEndDate()), atts);
									mmDoc.endElement("","","sts:OFEContingenciaPeriod");
									// M AN Max 35 Identificador de la contingencia
									addHeaderElement(mmDoc, "sts:OFEContingenciaID", feoc.getIdContingency(), atts);
									atts.addAttribute("", "", "schemeName", "CDATA", "identificador universal del evento contingente; Anexo-3");
									atts.addAttribute("", "", "schemeURI", "CDATA", "http://www.dian.gov.co/");
									addHeaderElement(mmDoc, "sts:UUID", m_CUFE, atts);
									atts.clear();
								mmDoc.endElement("","","sts:OFEContingencia");
								*/
							}
							if (!(inv.getC_Currency_ID() == LCO_FE_Utils.CURRENCY_COP_ID)) {
								Object[] args = new Object[] { inv.getGrandTotalPlusWithholdings(), inv.getC_Currency_ID(), LCO_FE_Utils.CURRENCY_COP_ID, inv.getDateInvoiced(), LCO_FE_Utils.CONVERSION_TRM_ID, inv.getAD_Client_ID(), Env.ZERO };
								BigDecimal baseamount = DB.getSQLValueBDEx(inv.get_TrxName(), "select currencyconvert(?, ?, ?, ?, ?, ?, ?)", args);
								if (baseamount != null) {
									mmDoc.startElement("","","sts:AdditionalMonetaryTotal", atts);
										atts.clear();
										atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
										// Obligatorio si es una factura con divisa extranjera
										addHeaderElement(mmDoc, "cbc:InvoiceTotalLocalCurrencyAmount", LCO_FE_Utils.decimalFormat(baseamount), atts);
										atts.clear();
									mmDoc.endElement("","","sts:AdditionalMonetaryTotal");
								}
							}
						mmDoc.endElement("","","sts:DianExtensions");
					mmDoc.endElement("","","ext:ExtensionContent");
				mmDoc.endElement("","","ext:UBLExtension");
				mmDoc.startElement("","","ext:UBLExtension", atts);
					mmDoc.startElement("","","ext:ExtensionContent", atts);
						// <ds:Signature
						// </ds:Signature>
					mmDoc.endElement("","","ext:ExtensionContent");
				mmDoc.endElement("","","ext:UBLExtension");
			mmDoc.endElement("","","ext:UBLExtensions");
			//
			// M AF Max 7, Version UBL
			addHeaderElement(mmDoc, "cbc:UBLVersionID", LCO_FE_Utils.cutString(f.getUBLVersionNo(), 7), atts);	// UBL 2.1
			// M AN 2, Procedencia de Vendedor
			addHeaderElement(mmDoc, "cbc:CustomizationID", "10", atts);	// TODO 10-Residente Tabla 16.1.4
			// M AF Max 82, Version Formato
			addHeaderElement(mmDoc, "cbc:ProfileID", LCO_FE_Utils.cutString(f.getVersionNo() + ": " + f.getDescription(), 82), atts);	// DIAN 2.1 DS
			// M N 1, Ambiente de Destino
			addHeaderElement(mmDoc, "cbc:ProfileExecutionID", m_EnvType, atts);
			// M AF Max 20, Prefijo + No. Documento Formato
			addHeaderElement(mmDoc, "cbc:ID", inv.getDocumentNo(), atts); // With Prefix getDocumentNo()
			atts.clear();
			atts = addGovAttribute21(m_EnvType, f.getLCO_FE_UuidType());
			// Error: El valor del CUFE es obligatorio cuando el tipo de documento es 1 = 'INVOICE'
			// M AF 96, CUFE: Obligatorio si es factura nacional
			addHeaderElement(mmDoc, "cbc:UUID", m_CUFE, atts);	// TODO reviewme DS
			atts.clear();
			// M AN Max 10, Fecha de emisi\u00f3n de la factura Formato AAAA-MM-DD
			addHeaderElement(mmDoc, "cbc:IssueDate", LCO_FE_Utils.getDateTime((Timestamp) inv.get_Value("LCO_FE_DateTrx"), 11), atts);
			// M AN Max 14, Hora de emisi\u00f3n de la factura Formato HH:MM:DD-05:00
			addHeaderElement(mmDoc, "cbc:IssueTime", LCO_FE_Utils.getDateTime((Timestamp) inv.get_Value("LCO_FE_DateTrx"), 14), atts);
			// O AN Max 10 Fecha de pago Formato AAAA-MM-DD
			Object[] args = new Object[] { inv.getC_PaymentTerm_ID(), (Timestamp) inv.get_Value("LCO_FE_DateTrx")};
			Timestamp duedate = DB.getSQLValueTSEx(null, "SELECT paymenttermduedate(?, ?)", args);
			addHeaderElement(mmDoc, "cbc:DueDate", LCO_FE_Utils.getDateTime(duedate, 11), atts);
			atts.clear();
			// atts.addAttribute("", "", "listAgencyID", "CDATA", "195");
			// atts.addAttribute("", "", "listAgencyName", "CDATA", "CO, DIAN (Direccion de Impuestos y Aduanas Nacionales)");
			// atts.addAttribute("", "", "listSchemeURI", "CDATA", "http://www.dian.gov.co/contratos/facturaelectronica/v1/InvoiceType");
			// M AN Max 2, Tipo de documento
			addHeaderElement(mmDoc, "cbc:InvoiceTypeCode", LCO_FE_Utils.cutString(fedt.getDianDocTypeCode(), 2), atts);
			atts.clear();
			//
			// O AF 15..5000, Información adicional: Texto libre
			addHeaderElement(mmDoc, "cbc:Note", "QRCode: " + m_QR, atts);
			// D AN Max 3, Divisa de la Factura
			addHeaderElement(mmDoc, "cbc:DocumentCurrencyCode", inv.getCurrencyISO(), atts);
			//
			List<List<Object>> lines = DB.getSQLArrayObjectsEx(inv.get_TrxName(), LCO_FE_Utils.SQL_ITEMS, inv.getC_Invoice_ID());
			m_totalnolineas = lines.size();
			// M N 3, Número de elementos InvoiceLine
			addHeaderElement(mmDoc, "cbc:LineCountNumeric", String.valueOf(m_totalnolineas), atts);
			//
			if (m_UseContingency) {
				atts.clear();
				mmDoc.startElement("","","cac:BillingReference", atts);
					mmDoc.startElement("","","cac:InvoiceDocumentReference", atts);
						atts.addAttribute("", "", "schemeName", "CDATA", "Rango Manual");
						atts.addAttribute("", "", "schemeURI", "CDATA", "http://www.dian.gov.co/contratos/facturaelectronica/v1/anexo_v1_0.html#rangoManual");
					    addHeaderElement(mmDoc, "cbc:ID", inv.getDocumentNo(), atts);	// With Prefix
						atts.clear();
						addHeaderElement(mmDoc, "cbc:CopyIndicator", "true", atts);
						addHeaderElement(mmDoc, "cbc:IssueDate", String.format("%1$tF", feoc.getDateTranscription()), atts);
						// D AF 2, Language Code CodeList_LanguageCode_ISO_7_04.xsd
						atts.addAttribute("", "", "languageID", "CDATA", LCO_FE_Utils.cutString(bpe.getAD_Language(), 3));
						addHeaderElement(mmDoc, "cbc:DocumentType", "Transcripción de factura de venta expedida manualmente motivada en una contingencia", atts);
						atts.clear();
					mmDoc.endElement("","","cac:InvoiceDocumentReference");
				mmDoc.endElement("","","cac:BillingReference");
			}
			
			// Informaci\u00f3n ABS Adquiriente Bienes o Servicios
			atts.clear();
			mmDoc.startElement("","","cac:AccountingCustomerParty", atts);
				atts.clear();
				atts.addAttribute("", "", "schemeAgencyID", "CDATA", "195");
				// M N 1, Tipo de organización jurídica Tabla 9
				addHeaderElement(mmDoc, "cbc:AdditionalAccountID", LCO_FE_Utils.cutString(tpte.get_ValueAsString("DianTaxPayerCode"), 1) , atts);
				atts.clear();
				mmDoc.startElement("","","cac:Party", atts);
					// O N 4, código de actividad económica CIIU
					// addHeaderElement(mmDoc, "cbc:IndustryClasificationCode", ie.getValue().substring(1, 5), atts);	// Fix ZB01
					mmDoc.startElement("","","cac:PartyIdentification", atts);
					atts.clear();
					// M AN 7, Tipo identificaci\u00f3n Tabla 2
					atts = addGovAttribute21(null, LCO_FE_Utils.cutString(tte.get_ValueAsString("LCO_TaxCodeDian"), 7));
					// M AF Max 35, No. Identificacion Emisor
					addHeaderElement(mmDoc, "cbc:ID", bpe.getTaxID().trim(), atts);
					atts.clear();
					mmDoc.endElement("","","cac:PartyIdentification");
					if ( tpte.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_PERSONA_JURIDICA)
							|| tpte.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_GRAN_CONTRIBUYENTE) ) {
						mmDoc.startElement("","","cac:PartyName", atts);
							// D AN 5..450 Nombre comercial del emisor
							addHeaderElement(mmDoc, "cbc:Name", bpe.getName(), atts);
						mmDoc.endElement("","","cac:PartyName");
					}
					mmDoc.startElement("","","cac:PhysicalLocation", atts);
						mmDoc.startElement("","","cac:Address", atts);
							// M N 5 Codigo del municipio
							addHeaderElement(mmDoc, "cbc:ID", LCO_FE_Utils.cutString(lo.getC_City().getPostal(), 5), atts);
							// M AN Max 60 Nombre de la ciudad
							addHeaderElement(mmDoc, "cbc:CityName", lo.getC_City().getName(), atts);
							// M AN Max 60 Nombre del Departamento
							addHeaderElement(mmDoc, "cbc:CountrySubentity", lo.getRegionName(), atts);
							// M N 1..5 Código del Departamento
							addHeaderElement(mmDoc, "cbc:CountrySubentityCode", LCO_FE_Utils.cutString(lo.getRegion().getDescription(), 2), atts);
							// X AN Max 40 Municipio (distrito administrativo)
							// addHeaderElement(mmDoc, "cbc:CitySubdivisionName", lo.getC_City().getName(), atts);
							mmDoc.startElement("","","cac:AddressLine", atts);
								// M AN Max 300 Direccion
								addHeaderElement(mmDoc, "cbc:Line", lo.getAddress1(), atts);
							mmDoc.endElement("","","cac:AddressLine");
							mmDoc.startElement("","","cac:Country", atts);
								// M AN Max 2 Pais Tabla 8
								addHeaderElement(mmDoc, "cbc:IdentificationCode", lo.getCountry().getCountryCode(), atts);
								// O A 4..40 Nombre Pais Tabla 8
								atts.addAttribute("", "", "languageID", "CDATA", LCO_FE_Utils.cutString(Env.getAD_Language(Env.getCtx()), 2));
								addHeaderElement(mmDoc, "cbc:Name", lo.getCountry().get_Translation("Name"), atts);
								atts.clear();
							mmDoc.endElement("","","cac:Country");
						mmDoc.endElement("","","cac:Address");
					mmDoc.endElement("","","cac:PhysicalLocation");
					mmDoc.startElement("","","cac:PartyTaxScheme", atts);
						// M AF 5..450, Nombre o Razón Social del emisor
						addHeaderElement(mmDoc, "cbc:RegistrationName", bpe.getName(), atts);
						atts.clear();
						atts = addGovAttribute21(bpe.get_ValueAsString("TaxIdDigit"), LCO_FE_Utils.cutString(tte.get_ValueAsString("LCO_TaxCodeDian"), 7));	// TODO Reviewme
						// M N 5..12, No. Identificacion Emisor
						addHeaderElement(mmDoc, "cbc:CompanyID", bpe.getTaxID().trim(), atts);
						atts.clear();
						{ // BP Info Emisor
							boolean nobpeinfo = true;
							String codigosRUT = "";
							List<List<Object>> rowse = DB.getSQLArrayObjectsEx(bpe.get_TrxName(), LCO_FE_Utils.SQL_BP_INFO, bpe.getC_BPartner_ID());
							if (rowse != null) {
								for (List<Object> rowe : rowse) {
									// String grupoRUT = rowe.get(0).toString();
									String codigoRUT = rowe.get(1).toString();
									if (Util.isEmpty(codigoRUT, true)) {
										nobpeinfo = true;
										break;
									}
									nobpeinfo = false;
									codigosRUT = codigosRUT + codigoRUT + ";";
								}
								atts.clear();
								atts.addAttribute("", "", "listName", "CDATA", LCO_FE_Utils.cutString(tpte.get_ValueAsString("DianRegimeCode"), 2));
								// M AN 1 Max 30, Obligaciones del contribuyente
								addHeaderElement(mmDoc, "cbc:TaxLevelCode", codigosRUT.substring(0, codigosRUT.length()-1), atts);
								atts.clear();
	
							}
							if (nobpeinfo) {
								msg = (Msg.translate(Env.getCtx(), "FillMandatory") + " "
										+ Msg.getElement(Env.getCtx(), MBPartner.COLUMNNAME_C_BPartner_ID) + "-"
										+ Msg.getElement(Env.getCtx(), "LCO_FE_TributaryType_ID"));
								return "@Error@ @invoice.no@ " + inv.getDocumentNo() + " " + msg;
							}
						}
						// M 1..1 Dirección fiscal del emisor. 
						mmDoc.startElement("","","cac:RegistrationAddress", atts);
							// M N 5 Codigo del municipio
							addHeaderElement(mmDoc, "cbc:ID", LCO_FE_Utils.cutString(lo.getC_City().getPostal(), 5), atts);
							// M AN Max 60 Nombre de la ciudad
							addHeaderElement(mmDoc, "cbc:CityName", lo.getC_City().getName(), atts);
							// M AN Max 60 Nombre del Departamento
							addHeaderElement(mmDoc, "cbc:CountrySubentity", lo.getRegionName(), atts);
							// M N 1..5 Código del Departamento
							addHeaderElement(mmDoc, "cbc:CountrySubentityCode", LCO_FE_Utils.cutString(lo.getRegion().getDescription(), 2), atts);
							// X AN Max 40 Municipio (distrito administrativo)
							// addHeaderElement(mmDoc, "cbc:CitySubdivisionName", lo.getC_City().getName(), atts);
							mmDoc.startElement("","","cac:AddressLine", atts);
								// M AN Max 300 Direccion
								addHeaderElement(mmDoc, "cbc:Line", lo.getAddress1(), atts);
							mmDoc.endElement("","","cac:AddressLine");
							mmDoc.startElement("","","cac:Country", atts);
								// M AN Max 2 Pais Tabla 8
								addHeaderElement(mmDoc, "cbc:IdentificationCode", lo.getCountry().getCountryCode(), atts);
								// O A 4..40 Nombre Pais Tabla 8
								atts.addAttribute("", "", "languageID", "CDATA", LCO_FE_Utils.cutString(Env.getAD_Language(Env.getCtx()), 2));
								addHeaderElement(mmDoc, "cbc:Name", lo.getCountry().get_Translation("Name"), atts);
								atts.clear();
							mmDoc.endElement("","","cac:Country");
						mmDoc.endElement("","","cac:RegistrationAddress");
						//
						if (tpte.get_ValueAsString("DianRegimeCode").equals(LCO_FE_Utils.TIPO_REGIMEN_FISCAL_48)) {
							// M 1 Veces max
							mmDoc.startElement("","","cac:TaxScheme", atts);
								// M AF 3..10, Identificador del tributo
								addHeaderElement(mmDoc, "cbc:ID", LCO_FE_Utils.CODIGO_IVA_01, atts);	// TipoImpuesto
								// M AF 10..30, Nombre del tributo
								addHeaderElement(mmDoc, "cbc:Name", "IVA", atts);	// TODO NombreImpuesto
							mmDoc.endElement("","","cac:TaxScheme");
						}
					mmDoc.endElement("","","cac:PartyTaxScheme");
					mmDoc.startElement("","","cac:PartyLegalEntity", atts);
						// M AF 5..450, Nombre o Razón Social del emisor
						addHeaderElement(mmDoc, "cbc:RegistrationName", bpe.getName(), atts);
						atts.clear();
						atts = addGovAttribute21(bpe.get_ValueAsString("TaxIdDigit"), LCO_FE_Utils.cutString(tte.get_ValueAsString("LCO_TaxCodeDian"), 7));	// TODO Reviewme
						// M N 5..12, NIT del emisor
						addHeaderElement(mmDoc, "cbc:CompanyID", bpe.getTaxID().trim(), atts);
						atts.clear();
						mmDoc.startElement("","","cac:CorporateRegistrationScheme", atts);
							if (! "".equals(m_Prefix))
								// M N 6, Prefijo de la facturación usada para el punto de venta
								addHeaderElement(mmDoc, "cbc:ID", m_Prefix, atts);	// TODO reviewme
								// O N 6, Número de matrícula mercantil (identificador de sucursal: punto de facturación) 
								// addHeaderElement(mmDoc, "cbc:Name", m_Prefix, atts);	// TODO
						mmDoc.endElement("","","cac:CorporateRegistrationScheme");
					mmDoc.endElement("","","cac:PartyLegalEntity");
					String mailContacto = MSysConfig.getValue("QSSLCO_FE_ReplyToEMail", null, inv.getAD_Client_ID());
					if (mailContacto != null) {
						mmDoc.startElement("","","cac:Contact", atts);
							// O AN Max 250 Correo electrónico  de la persona de contacto.
							addHeaderElement(mmDoc, "cbc:ElectronicMail", mailContacto, atts);
						mmDoc.endElement("","","cac:Contact");
					}
				mmDoc.endElement("","","cac:Party");
			mmDoc.endElement("","","cac:AccountingCustomerParty");
			
			// Informacion Vendedor
			mmDoc.startElement("","","cac:AccountingSupplierParty", atts);
				// M N 1, Tipo de persona Tabla 9
				addHeaderElement(mmDoc, "cbc:AdditionalAccountID", tpta.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_GRAN_CONTRIBUYENTE) ? LCO_FE_Utils.TIPO_PERSONA_JURIDICA : tpta.get_ValueAsString("DianTaxPayerCode"), atts);
				mmDoc.startElement("","","cac:Party", atts);
					mmDoc.startElement("","","cac:PartyIdentification", atts);
						atts.clear();
						// M AN 7, Tipo identificaci\u00f3n Tabla 2
						atts = addGovAttribute21(null, LCO_FE_Utils.cutString(tta.get_ValueAsString("LCO_TaxCodeDian"), 7));
						// M AF Max 35, No. Identificacion Adquiriente
						addHeaderElement(mmDoc, "cbc:ID", bp.getTaxID(), atts);
						atts.clear();
					mmDoc.endElement("","","cac:PartyIdentification");
					if ( tpta.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_PERSONA_JURIDICA)
							|| tpta.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_GRAN_CONTRIBUYENTE) ) {
						mmDoc.startElement("","","cac:PartyName", atts);
							// D AN 5..450 Nombre comercial del adquiriente
							addHeaderElement(mmDoc, "cbc:Name", bp.getName(), atts);
						mmDoc.endElement("","","cac:PartyName");
					} else if ( tpta.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_PERSONA_NATURAL) ) {
						mmDoc.startElement("","","cac:Person", atts);	// MAx 450
							// D AN Max 150 Nombre del adquiriente
							addHeaderElement(mmDoc, "cbc:FirstName", bp.get_ValueAsString("FirstName1"), atts);
							// D AN Max 150 Nombre del adquiriente
							addHeaderElement(mmDoc, "cbc:MiddleName", bp.get_Value("FirstName2") != null ? bp.get_ValueAsString("FirstName2") : "", atts);
							// D AN Max 150 Primer y segundo Apellido
							addHeaderElement(mmDoc, "cbc:FamilyName", bp.get_ValueAsString("LastName1")
									+ bp.get_Value("LastName2") != null ? " " + bp.get_ValueAsString("LastName2") : "", atts);
						mmDoc.endElement("","","cac:Person");
					}
					mmDoc.startElement("","","cac:PhysicalLocation", atts);
						mmDoc.startElement("","","cac:Address", atts);
							if (la.getC_City().getPostal() != null)
								// M N 5 Codigo del municipio
								addHeaderElement(mmDoc, "cbc:ID", LCO_FE_Utils.cutString(la.getC_City().getPostal(), 5), atts);
							if (la.getC_City().getName() != null)
								// M AN Max 60 Nombre de la ciudad
								addHeaderElement(mmDoc, "cbc:CityName", la.getC_City().getName(), atts);
							if (la.getRegionName() != null)
								// M AN Max 60 Nombre del Departamento
								addHeaderElement(mmDoc, "cbc:CountrySubentity", la.getRegionName(), atts);
							// M N 1..5 Código del Departamento
							addHeaderElement(mmDoc, "cbc:CountrySubentityCode", LCO_FE_Utils.cutString(la.getRegion().getDescription(), 2), atts);
							// X AN Max 40 Municipio (distrito administrativo)
							// addHeaderElement(mmDoc, "cbc:CitySubdivisionName", la.getC_City().getName(), atts);
							mmDoc.startElement("","","cac:AddressLine", atts);
								// M AN Max 300 Direccion
								addHeaderElement(mmDoc, "cbc:Line", la.getAddress1(), atts);
							mmDoc.endElement("","","cac:AddressLine");
							mmDoc.startElement("","","cac:Country", atts);
								// M AN Max 2 Pais Tabla 8
								addHeaderElement(mmDoc, "cbc:IdentificationCode", la.getCountry().getCountryCode(), atts);
								// O A 4..40 Nombre Pais Tabla 8
								atts.addAttribute("", "", "languageID", "CDATA", LCO_FE_Utils.cutString(Env.getAD_Language(Env.getCtx()), 2));
								addHeaderElement(mmDoc, "cbc:Name", la.getCountry().get_Translation("Name"), atts);
								atts.clear();
							mmDoc.endElement("","","cac:Country");
						mmDoc.endElement("","","cac:Address");
					mmDoc.endElement("","","cac:PhysicalLocation");
					
					mmDoc.startElement("","","cac:PartyTaxScheme", atts);
						// M AF 5..450, Nombre o Razón Social del adquiriente
						addHeaderElement(mmDoc, "cbc:RegistrationName", bp.getName(), atts);
						atts.clear();
						atts = addGovAttribute21(bp.get_ValueAsString("TaxIdDigit"), LCO_FE_Utils.cutString(tta.get_ValueAsString("LCO_TaxCodeDian"), 7));	// TODO Reviewme
						// M N 5..12, No. Identificacion Adquiriente
						addHeaderElement(mmDoc, "cbc:CompanyID", bp.getTaxID(), atts);
						atts.clear();
						{ // BP Info Adquiriente
							boolean nobpainfo = true;
							String codigosRUT = "";
							List<List<Object>> rowsa = DB.getSQLArrayObjectsEx(bp.get_TrxName(), LCO_FE_Utils.SQL_BP_INFO, bp.getC_BPartner_ID());
							if (rowsa != null) {
								for (List<Object> rowa : rowsa) {
									// String grupoRUT = rowa.get(0).toString();
									String codigoRUT = rowa.get(1).toString();
									if (Util.isEmpty(codigoRUT, true)) {
										nobpainfo = true;
										break;
									}
									nobpainfo = false;
									codigosRUT = codigosRUT + codigoRUT + ";";
								}
								atts.clear();
								atts.addAttribute("", "", "listName", "CDATA", LCO_FE_Utils.cutString(tpta.get_ValueAsString("DianRegimeCode"), 2));
								// M AN 1 Max 30, Obligaciones del contribuyente
								addHeaderElement(mmDoc, "cbc:TaxLevelCode", codigosRUT.substring(0, codigosRUT.length()-1), atts);
								atts.clear();
								
							}
							if (nobpainfo) {
								msg = (Msg.translate(Env.getCtx(), "FillMandatory") + " "
										+ Msg.getElement(Env.getCtx(), MBPartner.COLUMNNAME_C_BPartner_ID) + "-"
										+ Msg.getElement(Env.getCtx(), "LCO_FE_TributaryType_ID"));
								return "@Error@ @invoice.no@ " + inv.getDocumentNo() + " " + msg;
							}
						}
						List<List<Object>> taxas = DB.getSQLArrayObjectsEx(inv.get_TrxName(), LCO_FE_Utils.SQL_TAX_INFO, inv.getC_Invoice_ID(), "%", inv.getC_Invoice_ID(), "%");
						if (taxas != null) {
							for (List<Object> taxa : taxas) {
								// if (taxa != null && taxa.get(3).toString().equals(codigoIva_01)) {
									// M 1 Veces max
									mmDoc.startElement("","","cac:TaxScheme", atts);
										// M AF 3..10, Identificador del tributo
										addHeaderElement(mmDoc, "cbc:ID", taxa.get(3).toString(), atts);	// TipoImpuesto
										// M AF 10..30, Nombre del tributo
										addHeaderElement(mmDoc, "cbc:Name", taxa.get(6).toString(), atts);	// NombreImpuesto
									mmDoc.endElement("","","cac:TaxScheme");
								// }
								break;	// First
							}
						}
					mmDoc.endElement("","","cac:PartyTaxScheme");
					mmDoc.startElement("","","cac:PartyLegalEntity", atts);
						// M AF 5..450, Nombre o Razón Social del adquiriente
						addHeaderElement(mmDoc, "cbc:RegistrationName", bp.getName(), atts);
						atts.clear();
						atts = addGovAttribute21(bp.get_ValueAsString("TaxIdDigit"), LCO_FE_Utils.cutString(tta.get_ValueAsString("LCO_TaxCodeDian"), 7));	// TODO Reviewme
						// M N 5..12, ID del Adquiriente
						addHeaderElement(mmDoc, "cbc:CompanyID", bp.getTaxID(), atts);
						atts.clear();
					mmDoc.endElement("","","cac:PartyLegalEntity");
					if (ua.getEMail() != null) {
						mmDoc.startElement("","","cac:Contact", atts);
							// O AN Max 250 Correo electrónico  de la persona de contacto.
							addHeaderElement(mmDoc, "cbc:ElectronicMail", ua.getEMail().trim(), atts);
						mmDoc.endElement("","","cac:Contact");
					}
				mmDoc.endElement("","","cac:Party");
			mmDoc.endElement("","","cac:AccountingSupplierParty");
			
			if (m_coddoc.equals(LCO_FE_Utils.TIPO_COMPROBANTE_SOPORTE)) {
				// M 1..1, Método de pago
				mmDoc.startElement("","","cac:PaymentMeans",atts);
				// O AN Max 3 Código correspondiente al medio de pago Tabla 5
				addHeaderElement(mmDoc, "cbc:ID", inv.getPaymentRule().equals(MInvoice.PAYMENTRULE_DirectDeposit) ? LCO_FE_Utils.MEDIO_PAGO_DEBITO_CA : LCO_FE_Utils.MEDIO_PAGO_NO_DEFINIDO, atts);
				// D AN Max 3 Método de pago. Tabla 26
				if (inv.getPaymentRule().equals(MInvoice.PAYMENTRULE_OnCredit))
					addHeaderElement(mmDoc, "cbc:PaymentMeansCode", LCO_FE_Utils.METODO_PAGO_CREDITO, atts);
				else if (inv.getPaymentRule().equals(MInvoice.PAYMENTRULE_DirectDeposit))
					addHeaderElement(mmDoc, "cbc:PaymentMeansCode", LCO_FE_Utils.METODO_PAGO_CONTADO, atts);
				// D AN Max 10 Fecha de pago Formato AAAA-MM-DD
				if (inv.getPaymentRule().equals(MInvoice.PAYMENTRULE_OnCredit)) {
					args = new Object[] { inv.getC_PaymentTerm_ID(), (Timestamp) inv.get_Value("LCO_FE_DateTrx")};
					duedate = DB.getSQLValueTSEx(null, "SELECT paymenttermduedate(?, ?)", args);
					addHeaderElement(mmDoc, "cbc:PaymentDueDate", LCO_FE_Utils.getDateTime(duedate, 11), atts);
				}
				mmDoc.endElement("","","cac:PaymentMeans");
			}
			
			// E 1..N, Término de pago
			// mmDoc.startElement("","","cac:PaymentTerms", atts);
				// Elementos opcionales
				// O N 1, Término de pago
				// addHeaderElement(mmDoc, "cbc:ReferenceEventCode", inv.getC_PaymentTerm().getValue(), atts);	// TODO Homologar
			// mmDoc.endElement("","","cac:PaymentTerms");
			
			{ // Impuestos
				List<List<Object>> rows = DB.getSQLArrayObjectsEx(inv.get_TrxName(), LCO_FE_Utils.SQL_TAX_GROUP, inv.getC_Invoice_ID(), inv.getC_Invoice_ID());
				if (rows != null) {
					// No y retenidos
					for (List<Object> row : rows) {
						boolean EsRetencion = (row.get(0) != null && ((String)row.get(0)).equals("Y")) ? true : false;
						BigDecimal ImporteImpuesto = (BigDecimal) row.get(1);
						String Moneda = (String) row.get(2);
						String TipoImpuestoGrupo = (String) row.get(3);
						BigDecimal BaseImponible = (BigDecimal) row.get(4);
						BigDecimal PorcentajeImpuesto = Env.ZERO;
						String NombreImpuesto = (String) row.get(5);
						if (Util.isEmpty(TipoImpuestoGrupo, true)) {
							msg = (Msg.translate(Env.getCtx(), "FillMandatory") + " "
									+ Msg.getElement(Env.getCtx(), MTaxCategory.COLUMNNAME_C_TaxCategory_ID) + "-"
									+ Msg.getElement(Env.getCtx(), "IsWithHolding") + "-"
									+ Msg.getElement(Env.getCtx(), "LCO_FE_DianTaxCategoryCode"));
							return "@Error@ @invoice.no@ " + inv.getDocumentNo() + " " + msg;
						}
						// Impuestos Retenidos
						if (EsRetencion && !TipoImpuestoGrupo.equals(LCO_FE_Utils.CODIGO_RETFTE_06) && !m_IsAutoRetenedor)
							continue;
						if (TipoImpuestoGrupo.equals(LCO_FE_Utils.CODIGO_RETFTE_06) && !m_IsGranContribuyente)	// Reviewme
							continue;
						// O 50 Veces max Impuestos
						if (!EsRetencion)
							mmDoc.startElement("","","cac:TaxTotal",atts);
						else
							mmDoc.startElement("","","cac:WithholdingTaxTotal",atts);
							atts.clear();
							atts.addAttribute("", "", "currencyID", "CDATA", Moneda);
							// M N 14.4 Importe Impuesto: Importe del impuesto retenido
							addHeaderElement(mmDoc, "cbc:TaxAmount", LCO_FE_Utils.decimalFormat(ImporteImpuesto), atts);
							atts.clear();
							// M AN 5 Impuesto retenido o retenci\u00f3n
							// addHeaderElement(mmDoc, "cbc:TaxEvidenceIndicator", EsRetencion ? "true" : "false", atts);
						
							List<List<Object>> rowsi = DB.getSQLArrayObjectsEx(inv.get_TrxName(), LCO_FE_Utils.SQL_TAX_INFO, inv.getC_Invoice_ID(), TipoImpuestoGrupo, inv.getC_Invoice_ID(), TipoImpuestoGrupo);
							for (List<Object> rowi : rowsi) {
								EsRetencion = (rowi.get(0) != null && ((String)rowi.get(0)).equals("Y")) ? true : false;
								ImporteImpuesto = (BigDecimal) rowi.get(1);
								Moneda = (String) rowi.get(2);
								String TipoImpuesto = (String) rowi.get(3);
								BaseImponible = (BigDecimal) rowi.get(4);
								PorcentajeImpuesto = (BigDecimal) rowi.get(5);
								NombreImpuesto = (String) rowi.get(6);
								boolean EsExento = (rowi.get(7) != null && ((String)rowi.get(7)).equals("Y")) ? true : false;
								if (TipoImpuestoGrupo.equals(LCO_FE_Utils.CODIGO_IVA_01) && !EsExento && PorcentajeImpuesto.compareTo(Env.ZERO) == 0)
									continue;
								// O 50 Veces max Total Impuestos.
								atts.clear();
								mmDoc.startElement("","","cac:TaxSubtotal",atts);
									atts.clear();
									atts.addAttribute("", "", "currencyID", "CDATA", Moneda);
									// M N 14.4 Base Imponible
									addHeaderElement(mmDoc, "cbc:TaxableAmount", LCO_FE_Utils.decimalFormat(BaseImponible), atts);
									atts.clear();
									atts.addAttribute("", "", "currencyID", "CDATA", Moneda);
									// M N 14.4 Importe Impuesto (detalle)
									addHeaderElement(mmDoc, "cbc:TaxAmount", LCO_FE_Utils.decimalFormat(ImporteImpuesto), atts);
									atts.clear();
									mmDoc.startElement("","","cac:TaxCategory",atts);
										// O N 3.3 Porcentaje del impuesto
										addHeaderElement(mmDoc, "cbc:Percent", LCO_FE_Utils.decimalFormat(PorcentajeImpuesto), atts);
										mmDoc.startElement("","","cac:TaxScheme",atts);
											// M AN 3..10 Identificador del tributo Tabla 5
											addHeaderElement(mmDoc, "cbc:ID", TipoImpuesto, atts);
											// M AN 10..30 Nombre del tributo Tabla 5
											addHeaderElement(mmDoc, "cbc:Name", NombreImpuesto, atts);
										mmDoc.endElement("","","cac:TaxScheme");
									mmDoc.endElement("","","cac:TaxCategory");
								mmDoc.endElement("","","cac:TaxSubtotal");
								if (EsRetencion)
									BaseImponible = Env.ZERO;	// No suma
								if (!TipoImpuesto.equals(LCO_FE_Utils.CODIGO_NO_CAUSA_ZY)) {
									if (TipoImpuesto.equals(LCO_FE_Utils.CODIGO_IVA_01) && !EsExento && PorcentajeImpuesto.compareTo(Env.ZERO) == 0)
										m_totalbaseexcluida = m_totalbaseexcluida.add(BaseImponible);
									else
										m_totalbaseimponible = m_totalbaseimponible.add(BaseImponible);
									if (!EsRetencion)
										m_totalvalorimpuesto = m_totalvalorimpuesto.add(ImporteImpuesto);
									else
										m_totalretenciones = m_totalretenciones.add(ImporteImpuesto);
								}
							}
						if (!EsRetencion)
							mmDoc.endElement("","","cac:TaxTotal");
						else
							mmDoc.endElement("","","cac:WithholdingTaxTotal");
					}
				}
			}
			
			m_totalvalorimpuesto = m_totalvalorimpuesto.setScale(0, RoundingMode.HALF_UP);	// TODO Reviewme

			// M 1 Valores Totales
			mmDoc.startElement("","","cac:LegalMonetaryTotal", atts);
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// M N 14.4 Total Valor Bruto 
				addHeaderElement(mmDoc, "cbc:LineExtensionAmount", LCO_FE_Utils.decimalFormat(inv.getTotalLines()), atts);
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// M N 14.4 Total Valor Base Imponible menos Excluidos
				addHeaderElement(mmDoc, "cbc:TaxExclusiveAmount", LCO_FE_Utils.decimalFormat(m_totalbaseimponible), atts);	// TODO Excluidos
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// M N 14.4 Total de Valor Bruto más tributos
				addHeaderElement(mmDoc, "cbc:TaxInclusiveAmount", LCO_FE_Utils.decimalFormat(inv.getTotalLines().add(m_totalvalorimpuesto)), atts);	// TODO Reviewme
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// O N 14.4 Descuento Total
				if (inv.isDiscountPrinted())
					addHeaderElement(mmDoc, "cbc:AllowanceTotalAmount", LCO_FE_Utils.decimalFormat(m_totaldescuento), atts);	// TODO
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// O N 14.4 Cargo Total
				addHeaderElement(mmDoc, "cbc:ChargeTotalAmount", LCO_FE_Utils.decimalFormat(m_totalcargo), atts);	// TODO
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// O N 14.4 Anticipo Total
				addHeaderElement(mmDoc, "cbc:PrepaidAmount", LCO_FE_Utils.decimalFormat(m_totalanticipo), atts);	// TODO
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// O N 14.4 Diferencia Total
				addHeaderElement(mmDoc, "cbc:PayableRoundingAmount", LCO_FE_Utils.decimalFormat(Env.ZERO), atts);	// TODO
				atts.clear();
				atts.addAttribute("", "", "currencyID", "CDATA", inv.getCurrencyISO());
				// M N 14.4 Valor a Pagar de Factura
				addHeaderElement(mmDoc, "cbc:PayableAmount", LCO_FE_Utils.decimalFormat(inv.getGrandTotalPlusWithholdings()), atts);
				atts.clear();
			mmDoc.endElement("","","cac:LegalMonetaryTotal");
			
			args = null;
			// !m_IsExport
			int lco_fe_productscheme_id = -1;
			X_LCO_FE_ProductScheme feps = null;
			args = new Object[] { inv.getAD_Client_ID(), "Y", "%" };
			lco_fe_productscheme_id = DB.getSQLValueEx(inv.get_TrxName(), LCO_FE_Utils.SQL_SCHEMA, args);
			feps = new X_LCO_FE_ProductScheme (inv.getCtx(), lco_fe_productscheme_id, inv.get_TrxName());
			//
			X_LCO_FE_ProductScheme fepspa = null;
			if (m_IsExport) {
				args = new Object[] { inv.getAD_Client_ID(), "%", LCO_FE_Utils.PRODUCT_SCHEME_PAID };
				lco_fe_productscheme_id = DB.getSQLValueEx(inv.get_TrxName(), LCO_FE_Utils.SQL_SCHEMA, args);
				fepspa = new X_LCO_FE_ProductScheme (inv.getCtx(), lco_fe_productscheme_id, inv.get_TrxName());
			}
			
			{ // Items
				// M 1500 Veces max \u00cdtems del Documento // TODO
				List<List<Object>> rows = DB.getSQLArrayObjectsEx(inv.get_TrxName(), LCO_FE_Utils.SQL_ITEMS, inv.getC_Invoice_ID());
				if (rows == null) {
					msg = (Msg.translate(Env.getCtx(), "NoLines") + " "
							+ Msg.getElement(Env.getCtx(), MInvoice.COLUMNNAME_C_Invoice_ID));
					return "@Error@ @invoice.no@ " + inv.getDocumentNo() + " " + msg;
				}
				// if (rows != null) {
				    int i = 1;
					for (List<Object> row : rows) {
						BigDecimal Consecutivo = new BigDecimal(i);
						i++;
						boolean EsGratis = (row.get(1) != null && ((String)row.get(1)).equals("true")) ? true : false;
						BigDecimal qtyinvoiced = (BigDecimal) row.get(2);
						String UnidadMedida = (String) row.get(3);
						BigDecimal CostoTotal = (BigDecimal) row.get(4);
						String Iso_Code = (String) row.get(5);
						BigDecimal PrecioUnitario = (BigDecimal) row.get(6);
						String Iso_CodeDup = (String) row.get(7);
						//String pidentificador = (String) row.get(8);
						//String pdescription = (String) row.get(9);
						String ildescription = (String) row.get(10);
						//String name = (String) row.get(11);
						BigDecimal Descuento = (BigDecimal) row.get(12);
						String pcodigo = (String) row.get(13);
						boolean EsRetencion = (row.get(14) != null && ((String)row.get(14)).equals("Y")) ? true : false;
						String TipoImpuesto = (String) row.get(15);
						BigDecimal ImporteImpuesto = (BigDecimal) row.get(16);
						String MonedaImporte = (String) row.get(17);
						BigDecimal BaseImponible = (BigDecimal) row.get(18);
						String MonedaBase = (String) row.get(19);
						BigDecimal PorcentajeImpuesto = (BigDecimal) row.get(20);
						String NombreImpuesto = (String) row.get(21);
						String TipoProducto = (String) row.get(22);
						String CodigoEstandar = (String) row.get(23);
						String PartidaArancelaria = (String) row.get(24);
						boolean EsExento = (row.get(25) != null && ((String)row.get(25)).equals("Y")) ? true : false;

						atts.clear();
						mmDoc.startElement("","","cac:InvoiceLine",atts);
							// M N 1..4, Número de Línea
							addHeaderElement(mmDoc, "cbc:ID", LCO_FE_Utils.cutString(Consecutivo.toString(), 4), atts);	// TODO
							atts.addAttribute("", "", "unitCode", "CDATA", UnidadMedida);
							// M N 14.4, Cantidad del art\u00edculo solicitado
							addHeaderElement(mmDoc, "cbc:InvoicedQuantity", String.format(Locale.US, "%.2f", qtyinvoiced), atts);
							atts.clear();
							atts.addAttribute("", "", "currencyID", "CDATA", Iso_Code);
							// D N 14.4, Costo Total
							addHeaderElement(mmDoc, "cbc:LineExtensionAmount", LCO_FE_Utils.decimalFormat(CostoTotal), atts);
							atts.clear();
							mmDoc.startElement("","","cac:InvoicePeriod",atts);
								// M F 10 Fecha de compra AAAA-MM-DD
								addHeaderElement(mmDoc, "cbc:StartDate", LCO_FE_Utils.getDateTime((Timestamp) inv.get_Value("LCO_FE_DateTrx"), 11), atts);
								// M N 1 Código para indicar la forma de generación y transmisión
								addHeaderElement(mmDoc, "cbc:DescriptionCode", "1", atts);	// TODO Table 16.1.6
								// M A 13-17 Descripción para indicar la forma de generación y transmisión
								addHeaderElement(mmDoc, "cbc:Description", "Por operación", atts);
							mmDoc.endElement("","","cac:InvoicePeriod");
							atts.clear();
							// O 0..N Grupo de campos para información relacionadas con un cargo o un descuento
							// mmDoc.startElement("","","cac:AllowanceCharge",atts);
								// M 1 4-5 Indica que el elemento es un Cargo y	no un descuento
								// addHeaderElement(mmDoc, "cbc:ChargeIndicator", (EsGratis ? "true" : "false"), atts);
							// mmDoc.endElement("","","cac:AllowanceCharge");
							// O 50 Veces max Total Impuestos \u00cdtem.
							atts.clear();
							if (!TipoImpuesto.equals(LCO_FE_Utils.CODIGO_NO_CAUSA_ZY)) {
							mmDoc.startElement("","","cac:TaxTotal",atts);
								// if (!isTimHeader) {
									atts.clear();
									atts.addAttribute("", "", "currencyID", "CDATA", MonedaImporte);
									// M N 14.4 Importe Impuesto: Importe del impuesto retenido
									addHeaderElement(mmDoc, "cbc:TaxAmount", LCO_FE_Utils.decimalFormat(ImporteImpuesto), atts);
									atts.clear();
									// M AN 5 Impuesto retenido o retenci\u00f3n
									// addHeaderElement(mmDoc, "cbc:TaxEvidenceIndicator", EsRetencion ? "true" : "false", atts);
									// isTimHeader = true;
								// }
								// O 50 Veces max Impuestos
								mmDoc.startElement("","","cac:TaxSubtotal",atts);
									atts.clear();
									atts.addAttribute("", "", "currencyID", "CDATA", MonedaBase);
									// M N 14.4 Base Imponible
									addHeaderElement(mmDoc, "cbc:TaxableAmount", LCO_FE_Utils.decimalFormat(BaseImponible), atts);
									atts.clear();
									atts.addAttribute("", "", "currencyID", "CDATA", MonedaImporte);
									// M N 14.4 Importe Impuesto (detalle)
									addHeaderElement(mmDoc, "cbc:TaxAmount", LCO_FE_Utils.decimalFormat(ImporteImpuesto), atts);
									atts.clear();
									mmDoc.startElement("","","cac:TaxCategory",atts);
										// O N 3.3 Porcentaje del impuesto
										addHeaderElement(mmDoc, "cbc:Percent", LCO_FE_Utils.decimalFormat(PorcentajeImpuesto), atts);
										mmDoc.startElement("","","cac:TaxScheme",atts);
											// M AN 3..10 Identificador del tributo Tabla 5
											addHeaderElement(mmDoc, "cbc:ID", TipoImpuesto, atts);
											// M AN 10..30 Nombre del tributo Tabla 5
											addHeaderElement(mmDoc, "cbc:Name", NombreImpuesto, atts);
										mmDoc.endElement("","","cac:TaxScheme");
									mmDoc.endElement("","","cac:TaxCategory");
								mmDoc.endElement("","","cac:TaxSubtotal");
							mmDoc.endElement("","","cac:TaxTotal");
							}
							//
							mmDoc.startElement("","","cac:Item",atts);
								if (ildescription != null) {
									// O AN 5..300, Descripci\u00f3n
									addHeaderElement(mmDoc, "cbc:Description", LCO_FE_Utils.cutString(ildescription, 300), atts);
								// M 3 Veces max IDENTIFICACIÓN DEL ARTÍCULO O SERVICIO DE ACUERDO CON UN ESTÁNDAR
								}
								mmDoc.startElement("","","cac:StandardItemIdentification",atts);
								if ( !(m_IsExport || !la.getCountry().getCountryCode().equals(LCO_FE_Utils.COUNTRY_CODE_CO)) ) {
									// M AN Max 3, Código del estándar Tabla 31
									atts.addAttribute("", "", "schemeID", "CDATA", feps.getValue());
									// M AN Max 2, Código del estándar Tabla 31
									atts.addAttribute("", "", "schemeAgencyID", "CDATA", feps.getIdAgency());
									// M AN Max 35, Nombre del estándar Tabla 31
									atts.addAttribute("", "", "schemeName", "CDATA", feps.getName());
									// M AN Max 35, Código del producto segun IAE_2
									addHeaderElement(mmDoc, "cbc:ID", CodigoEstandar, atts);	// Regla: ZB01 Also Charge CodigoEstandar
								} else {
									atts.addAttribute("", "", "schemeID", "CDATA", fepspa.getValue());
									atts.addAttribute("", "", "schemeAgencyID", "CDATA", fepspa.getIdAgency());
									atts.addAttribute("", "", "schemeName", "CDATA", fepspa.getName());
									addHeaderElement(mmDoc, "cbc:ID", TipoProducto.equals(MProduct.PRODUCTTYPE_Item) ? PartidaArancelaria : LCO_FE_Utils.CODIGO_PA_DEFAULT, atts);
								}
								mmDoc.endElement("","","cac:StandardItemIdentification");
							mmDoc.endElement("","","cac:Item");
							atts.clear();
							mmDoc.startElement("","","cac:Price",atts);
								atts.addAttribute("", "", "currencyID", "CDATA", Iso_CodeDup);
								// D N 14.4, Precio Unitario
								addHeaderElement(mmDoc, "cbc:PriceAmount", LCO_FE_Utils.decimalFormat(PrecioUnitario), atts);
								atts.clear();
								// D A 1..3, Código del tipo de precio informado Tabla 6.3.10
								// addHeaderElement(mmDoc, "cbc:PriceTypeCode", Iso_CodeDup, atts);	// TODO reviewme
								atts.addAttribute("", "", "unitCode", "CDATA", UnidadMedida);
								// M N 1..6, Cantidad del art\u00edculo solicitado
								addHeaderElement(mmDoc, "cbc:BaseQuantity", String.format(Locale.US, "%.2f", qtyinvoiced), atts);
								atts.clear();
							mmDoc.endElement("","","cac:Price");
						mmDoc.endElement("","","cac:InvoiceLine");
						
						m_sumanolineas ++;
						if (inv.isDiscountPrinted())
							m_sumadescuento = m_sumadescuento.add(Descuento);
						if (EsRetencion)
							BaseImponible = Env.ZERO;	// No suma
						if (!TipoImpuesto.equals(LCO_FE_Utils.CODIGO_NO_CAUSA_ZY)) {
							if (TipoImpuesto.equals(LCO_FE_Utils.CODIGO_IVA_01) && !EsExento && PorcentajeImpuesto.compareTo(Env.ZERO) == 0)
								m_sumabaseexcluida = m_sumabaseexcluida.add(BaseImponible);
							else
								m_sumabaseimponible = m_sumabaseimponible.add(BaseImponible);
							if (!EsRetencion)
								m_sumavalorimpuesto = m_sumavalorimpuesto.add(ImporteImpuesto);
							else
								m_sumavalorretenciones = m_sumavalorretenciones.add(ImporteImpuesto);
						}	
					}
				}
			// }

			mmDoc.endElement("","",m_Root_Node);

			mmDoc.endDocument();

			if (mmDocStream != null) {
				try {
					mmDocStream.close();
				} catch (Exception e2) {}
			}

			if (m_sumanolineas != m_totalnolineas) {
				msg = "@DiffParentValue@ @NoOfLines@ @Total@: " + m_totalnolineas + "@sales.details@" + m_sumanolineas;
				return "@Error@ @invoice.no@ "+ inv.getDocumentNo() + " " + msg;
			}
			
			if (inv.isDiscountPrinted() && m_sumadescuento.compareTo(m_totaldescuento) != 0 ) {
				msg = "@DiffParentValue@ @discount.amt@ @Total@: " + m_totaldescuento.toString() + " @sales.details@ " + m_sumadescuento.toString();
				s_log.warning("@Error@ @invoice.no@ "+ inv.getDocumentNo() + " " + msg);
				msg = null;	// TODO Bypass temp
			}

			if (m_sumabaseimponible.compareTo(m_totalbaseimponible) != 0
					&& m_totalbaseimponible.subtract(m_sumabaseimponible).abs().compareTo(LCO_FE_Utils.CERO_PUNTO_CINCO) > 1) {
				msg = "@DiffParentValue@ @TaxBase@ @Total@: " + m_totalbaseimponible.toString() + " @sales.details@ " + m_sumabaseimponible.toString();
				return "@Error@ @invoice.no@ "+ inv.getDocumentNo() + " " + msg;
			}

			if (m_sumavalorimpuesto.compareTo(m_totalvalorimpuesto) != 0
					&& m_totalvalorimpuesto.subtract(m_sumavalorimpuesto).abs().compareTo(LCO_FE_Utils.CERO_PUNTO_CINCO) > 1) {
				msg = "@DiffParentValue@ @smenu.tax@ @Total@: " + m_totalvalorimpuesto.toString() + " @sales.details@ " + m_sumavalorimpuesto.toString();
				return "@Error@ @invoice.no@ "+ inv.getDocumentNo() + " " + msg;
			}

			//
		} catch (Exception e) {
			msg = Msg.getMsg(Env.getCtx(), "FileCannotCreate") + " "
					+ m_File_Type + "-"
					+ e.getLocalizedMessage();
			s_log.severe(msg);
			return msg;
		}

		return msg;

	} 	// lcofeinv_GovGeneraSoporteXML210


    // public AttributesImpl addHeaderAttribute(String dianshortdoctype, String signerPolicy)
	// TODO Moved to GenericXMLSignature ?
    
	/**
	 * @param
	 * @return atts
	 */
	public AttributesImpl addGovAttribute21(String schemeid, String schemename) {
		
		AttributesImpl atts = new AttributesImpl();

		atts.clear();
		
		if (schemeid != null)
			atts.addAttribute("", "", "schemeID", "CDATA", schemeid);
		if (schemename != null)
			atts.addAttribute("", "", "schemeName", "CDATA", schemename);
		
		atts.addAttribute("", "", "schemeAgencyID", "CDATA", "195");
		atts.addAttribute("", "", "schemeAgencyName", "CDATA", "CO, DIAN (Dirección de Impuestos y Aduanas Nacionales)");

		return atts;
		
	}

    // public AttributesImpl addGovAttribute21(String schemeid, String schemename)
	// TODO Moved to GenericXMLSignature ?

	
    // public AttributesImpl addGovAttribute(String lco_taxcodedian)
	// TODO Moved to GenericXMLSignature ?
	
    // public void addHeaderElement(TransformerHandler mmDoc, String att, String value, AttributesImpl atts) throws Exception
	// TODO Moved to GenericXMLSignature ?
	
    // public String setFileName(String taxid, String repdoctype, Boolean hex) {
	// TODO Moved to GenericXMLSignature ?


	/**
	 * Aplica
	 * UBL 2.0: FV, FE - UUID: NC, ND, FC
	 * UBL 2.1: FV, FE, FC, NC, ND sin UUID, DS, AC
	 * @param
	 * @return CUFE
	 */
	public String generateCufe(
		String trx,
		Integer c_invoice_id,
		String dianshortdoctype,
		String documentno,
		Timestamp datetrx,
		BigDecimal totallines,
		BigDecimal grandtotal,
		String taxidofe,
		String taxcodedian,
		String taxidadq,
		String techkey,
		String ncvalue,
		String ndvalue,
		Timestamp startdate,
		Timestamp enddate,
		String idcontingency)
	{
	    /*
	    El CUFE, permite identificar unívocamente una factura electrónica en el territorio nacional, lo cual se logra por medio de la generación de un código único usando una función one-way hash.
		Para la generación del CUFE se debe utilizar el algoritmo SHA-1 | SHA384 que garantiza que dos (2) cadenas de texto no generarán el mismo hash. En expresión matemática tenemos que el Código Único de la Factura Electrónica es:
		NumFac = Número de factura.
		FecFac = Fecha de factura en formato (Java) YYYYmmddHHMMss	// HorFac + GMT 2.1
		ValFac = Valor Factura sin IVA, con punto decimal, con decimales a dos (2) dígitos, sin separadores de miles, ni símbolo pesos.
		CodImp1 = 01
		ValImp1 = Valor impuesto 01, con punto decimal, con decimales a dos (2) dígitos, sin separadores de miles, ni símbolo pesos.
		CodImp2 = 02	// 02 2.0 | 04 2.1
		ValImp2 = Valor impuesto 02, con punto decimal, con decimales a dos (2) dígitos, sin separadores de miles, ni símbolo pesos.
		CodImp3 = 03
		ValImp3 = Valor impuesto 03, con punto decimal, con decimales a dos (2) dígitos, sin separadores de miles, ni símbolo pesos.
		// ValImp = Valor IVA, con punto decimal, con decimales a dos (2) dígitos, sin separadores de miles, ni símbolo pesos.
		ValPag = Valor a pagar
		NitOFE = NIT del Facturador Electrónico sin puntos ni guiones, sin digito de verificación.
		TipAdq = tipo de adquiriente, de acuerdo con el valor registrado en /fe:Invoice/fe:AccountingCustomerParty/fe:Party/cac:PartyIdentification/cbc:ID/@schemeID la tabla Tipos de documentos de identidad del "Anexo 001 Formato estándar XML de la Factura, notas débito y notas crédito electrónicos"; si no se determinó y es un NIT, entonces use el valor "O-99", de lo contrario use "R-00-PN".
		NumAdq = Número de identificación del adquiriente sin puntos ni guiones, sin digito de verificación.
		ClTec = Clave técnica del rango de facturación (CUFE)
		  OR Software-PIN = Pin del software registrado en el catalogo del participante (CUDE)
		TipoAmbiente = Número de identificación del ambiente 2.1
		//
		El CUDS, permite identificar unívocamente en el territorio nacional un documento Soporte y Nota de ajuste al documento soporte
		Composición del CUDS = SHA-384(NumFac + FecFac + HorFac + ValDS + CodImp + ValImp + ValTot + NitOFE + NumAdq + Software-PIN + TipoAmbie)
		NumDS = Número del documento electrónico. (prefijo concatenado con el número del Documento Soporte o Nota de Ajuste)
		FecDS = Fecha de emisión
		HorDS = Hora del documento soporte incluyendo (UTC–05:00)
		ValDS = Valor del documento soporte sin Impuestos, con punto decimal, con decimales a dos (2) dígitos truncados, sin separadores de miles, ni símbolo pesos.
		CodImp = 01 Este valor es fijo.
		ValImp = Valor impuesto 01 - IVA, con punto decimal, con decimales a dos (2) dígitos truncados, sin separadores de miles, ni símbolo pesos. Si no está referenciado el impuesto 01 – IVA este valor se representa con 0.00
	    ValTot = Valor Total, con punto decimal, con decimales a dos (2) dígitos truncados, sin separadores de miles, ni símbolo pesos.
	    NumSNO = Número de Identificación del SNO sin puntos ni guiones, sin digito de verificación.
	    NITABS = Número del NIT del ABS del Documento Soporte sin puntos ni guiones, sin digito de verificación.
	    Software-PIN = Pin del software registrado en el catálogo del participante, el cual no está expresado en el XML
	    TipoAmbiente = Número de identificación del ambiente utilizado por el contribuyente para generar el documento soporte
	    */
		
		BigDecimal importeiva = Env.ZERO;
		BigDecimal importeconsumo = Env.ZERO;
		BigDecimal importeica = Env.ZERO;
		
		String cufe_sha = "";
		
		boolean isDocSoporte = (LCO_FE_Utils.TIPO_COMPROBANTE_SOPORTE.equals(dianshortdoctype)
				|| LCO_FE_Utils.TIPO_COMPROBANTE_AJUSTE_AC.equals(dianshortdoctype));
		
		List<List<Object>> rows = DB.getSQLArrayObjectsEx(trx, LCO_FE_Utils.SQL_TAX_INFO, c_invoice_id, "%", c_invoice_id, "%");
		if (rows != null) {
			for (List<Object> row : rows) {
				boolean EsRetencion = (row.get(0) != null && ((String)row.get(0)).equals("Y")) ? true : false;
				if (EsRetencion)
					continue;
				if (Util.isEmpty(row.get(3).toString(), true)) {
					throw new AdempiereUserError(Msg.translate(Env.getCtx(), "FillMandatory") + " "
							+ Msg.getElement(Env.getCtx(), MTaxCategory.COLUMNNAME_C_TaxCategory_ID) + "-"
							+ Msg.getElement(Env.getCtx(), "IsWithHolding") + "-"
							+ Msg.getElement(Env.getCtx(), "LCO_FE_DianTaxCategoryCode"));
				}
				if (row.get(3).toString().equals(LCO_FE_Utils.CODIGO_IVA_01))
						importeiva = (BigDecimal) row.get(1);
				else if (row.get(3).toString().equals(LCO_FE_Utils.CODIGO_CONSUMO_02))
						importeconsumo = (BigDecimal) row.get(1);
				else if (row.get(3).toString().equals(LCO_FE_Utils.CODIGO_ICA_03))
						importeica = (BigDecimal) row.get(1);
				else if (row.get(3).toString().equals(LCO_FE_Utils.CODIGO_INC_04))
					importeconsumo = (BigDecimal) row.get(1);
			}
		}
			
		StringBuilder cufedata = new StringBuilder();
		
		cufedata.append(documentno);
		if (LCO_FE_Utils.UBL_VERSION_21.equals(m_UBLVersionNo))
			cufedata.append(LCO_FE_Utils.getDateTime(datetrx, 11)
				+ LCO_FE_Utils.getDateTime(datetrx, 14));	// YYYYmmddHHMMss.TZ
		else
			cufedata.append(LCO_FE_Utils.getDateTime(datetrx, 19));	// YYYYmmddHHMMss	
		cufedata.append(LCO_FE_Utils.decimalFormat(totallines));
		cufedata.append(LCO_FE_Utils.CODIGO_IVA_01);
		cufedata.append(LCO_FE_Utils.decimalFormat(importeiva));
		if (!isDocSoporte) {
			if (LCO_FE_Utils.UBL_VERSION_21.equals(m_UBLVersionNo)) {
				cufedata.append(LCO_FE_Utils.CODIGO_INC_04);
				cufedata.append(LCO_FE_Utils.decimalFormat(importeconsumo));
			} else {
				cufedata.append(LCO_FE_Utils.CODIGO_CONSUMO_02);
				cufedata.append(LCO_FE_Utils.decimalFormat(importeconsumo));
			}
			cufedata.append(LCO_FE_Utils.CODIGO_ICA_03);
			cufedata.append(LCO_FE_Utils.decimalFormat(importeica));
		}
		cufedata.append(LCO_FE_Utils.decimalFormat(grandtotal));
		cufedata.append(taxidofe);
		if ( LCO_FE_Utils.TIPO_COMPROBANTE_FACTURA.equals(dianshortdoctype)
				|| LCO_FE_Utils.TIPO_COMPROBANTE_NC.equals(dianshortdoctype)
				|| LCO_FE_Utils.TIPO_COMPROBANTE_ND.equals(dianshortdoctype)
				|| LCO_FE_Utils.TIPO_COMPROBANTE_SOPORTE.equals(dianshortdoctype)
				|| LCO_FE_Utils.TIPO_COMPROBANTE_AJUSTE_AC.equals(dianshortdoctype) ) {
			if (!LCO_FE_Utils.UBL_VERSION_21.equals(m_UBLVersionNo))
				cufedata.append(taxcodedian);
			cufedata.append(taxidadq);
		}
		if (   !m_UseContingency
			&& (   LCO_FE_Utils.TIPO_COMPROBANTE_FACTURA.equals(dianshortdoctype)
				|| LCO_FE_Utils.TIPO_COMPROBANTE_EXPORTACION.equals(dianshortdoctype))) {
			cufedata.append(techkey);
		} else {
			if (LCO_FE_Utils.UBL_VERSION_21.equals(m_UBLVersionNo)) {
				if (   LCO_FE_Utils.TIPO_COMPROBANTE_NC.equals(dianshortdoctype)
					|| LCO_FE_Utils.TIPO_COMPROBANTE_ND.equals(dianshortdoctype)
					|| LCO_FE_Utils.TIPO_COMPROBANTE_SOPORTE.equals(dianshortdoctype)
					|| LCO_FE_Utils.TIPO_COMPROBANTE_AJUSTE_AC.equals(dianshortdoctype))
					cufedata.append(techkey);  // Software PIN
			} else {
				if (m_UseContingency || LCO_FE_Utils.TIPO_COMPROBANTE_CONTINGENCIA.equals(dianshortdoctype))
					cufedata.append(generateUUIDRef(datetrx, "", 0, startdate, enddate, idcontingency)); // Reviewme
				else if (LCO_FE_Utils.TIPO_COMPROBANTE_NC.equals(dianshortdoctype))
					cufedata.append(generateUUIDRef(datetrx, ncvalue, 1, startdate, enddate, idcontingency)); // Reviewme
				else if (LCO_FE_Utils.TIPO_COMPROBANTE_ND.equals(dianshortdoctype))
					cufedata.append(generateUUIDRef(datetrx, ndvalue, 0, startdate, enddate, idcontingency));
			}
		}
		if (LCO_FE_Utils.UBL_VERSION_21.equals(m_UBLVersionNo))
			cufedata.append(m_EnvType);
		
		System.out.println("CUFE/UUID String: " + cufedata.toString());
	
	    try
	    {
	    	MessageDigest crypt = null;
	    	if (LCO_FE_Utils.UBL_VERSION_21.equals(m_UBLVersionNo))
	    		crypt = MessageDigest.getInstance("SHA-384");
	    	else
	    		crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(cufedata.toString().getBytes(StandardCharsets.UTF_8));	// XmlEncoding
	        cufe_sha = byteToHex(crypt.digest());
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	    	throw new AdempiereException(e);
	    }
		
	    System.out.println("CUFE/UUID " + cufe_sha);
	    
	    // Length: SHA-1 40, SHA-256 64, SHA-384 96
		if (cufe_sha == null || LCO_FE_Utils.notValidLength(cufe_sha, 40, 96))
			throw new AdempiereException(Msg.getElement(Env.getCtx(), MLCOFEAuthorization.COLUMNNAME_LCO_FE_Authorization_ID) + " "	
				+ Msg.translate(Env.getCtx(), "Length") + " > 96" + " "
				+ Msg.getElement(Env.getCtx(), MLCOFEAuthorization.COLUMNNAME_LCO_FE_Dian_Uuid)  + "-"
				+ cufe_sha);

		return cufe_sha;
		
	}
	
	/**
	 * Aplica
	 * UBL 2.0: NC, ND, FC, DS
	 * @param
	 * @return UUID
	 */
	public String generateUUIDRef(
		Timestamp datetrx,
		String nvalue,
		int refcount,
		Timestamp startdate,
		Timestamp enddate,
		String idcontingency)
	{
		String uuidref_sha1 = "";
		
		StringBuilder uuiddata = new StringBuilder();
		
		if (!m_UseContingency) {
			uuiddata.append(LCO_FE_Utils.getDateTime(datetrx, 9));	// yyyyMMdd
			uuiddata.append(LCO_FE_Utils.getDateTime(datetrx, 13));	// HHmmss
			// oi.get_ValueAsString("LCO_FE_IdAccount")
			uuiddata.append(m_UserName);		// SoftwareID
			uuiddata.append(m_IdAccount);	// SoftwareSecurityCode Reviewme
			// Total de facturas involucradas con esta Nota Crédito
			if (refcount > 0)					// TODO Reviewme
				uuiddata.append(refcount);
			uuiddata.append(nvalue);
		} else {
			uuiddata.append(LCO_FE_Utils.getDateTime(startdate, 9));	// yyyyMMdd
			uuiddata.append(LCO_FE_Utils.getDateTime(startdate, 13));	// HHmmss
			uuiddata.append(idcontingency);
			uuiddata.append(LCO_FE_Utils.getDateTime(enddate, 9));		// yyyyMMdd
			uuiddata.append(LCO_FE_Utils.getDateTime(enddate, 13));		// HHmmss
		}
		
		System.out.println("UUIDRef String: " + uuiddata.toString());
		
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(uuiddata.toString().getBytes(StandardCharsets.UTF_8));	// XmlEncoding
	        uuidref_sha1 = byteToHex(crypt.digest());
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	    	throw new AdempiereException(e);
	    }
		
		System.out.println("UUIDRef " + uuidref_sha1);
		return uuidref_sha1;
	}

	private static String byteToHex(final byte[] hash)
	{
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
	
	/**
	 * Aplica
	 * UBL 2.0: FV, FE - UUID: NC, ND, FC
	 * UBL 2.1: FV, FE, FC, NC, ND sin UUID, DS, AC
	 * @param
	 * @return QR
	 */
	public String generateQR(
			String trx,
			Integer c_invoice_id,
			String dianshortdoctype,
			String documentno,
			Timestamp datetrx,
			BigDecimal totallines,
			BigDecimal grandtotal,
			String taxidofe,
			String taxidadq,
			String cufe)
	{
	    /*
	    CÓDIGO BIDIMENSIONAL «QR» DEL CUFE, para la representación gráfica de las facturas electrónicas
		NumFac = [NUMERO_FACTURA]
		FecFac = [FECHA_FACTURA] en formato YYYYmmddHHMMss	// HorFac + GMT 2.1 
		NitFac = [NIT FACTURADOR] sin puntos ni guiones
		DocAdq = [NUMERO_ID_ADQUIRIENTE] sin puntos ni guiones
		ValFac = [VALOR_FACTURA] con punto decimal, con decimales a dos (2) dígitos, sin separadores de miles, ni símbolo pesos.
		ValIva = [VALOR_IVA] con punto decimal, con decimales a dos (2) dígitos, sin separadores de miles, ni símbolo pesos.
		ValOtroIm = [VALOR_OTROS_IMPUESTOS] con punto decimal, con decimales a dos (2) dígitos, sin separadores de miles, ni símbolo pesos.
		ValFacIm = [VALOR_OTROS_IMPUESTOS] con punto decimal, con decimales a dos (2) dígitos, sin separadores de miles, ni símbolo pesos.
	    CUFE: [CUFE]
	    //
	    CÓDIGO BIDIMENSIONAL «QR» DEL CUDS, para la representación gráfica de los documentos soporte
	    NumDS: [NUMERO_DOCUMENTO SOPORTE]
		FecDS: [FECHA_DOCUMENTO SOPORTE]
		HorDS: [HORA_DOCUMENTO SOPORTE (con (UTC–05:00))]
		NumSNO: [NUMSNO] sin puntos ni guiones
		DocAdq: [NIT_ABS] sin puntos ni guiones
		ValDS: [VALOR_DOCUMENTO SOPORTE] con punto decimal, con decimales a dos (2) dígitos, sin separadores de miles, ni símbolo pesos.
		ValIva: [VALOR_IVA] con punto decimal, con decimales a dos (2) dígitos, sin separadores de miles, ni símbolo pesos.
		ValTolDS: [VALOR_TOTAL_DOCUMENTO SOPORTE] con punto decimal, con decimales a dos (2) dígitos, sin separadores de miles, ni símbolo pesos.
		CUDS: [CUDS]
		QRCode: URL disponible por la DIAN
	    */
		
		BigDecimal importeiva = Env.ZERO;
		BigDecimal importeotros = Env.ZERO;
		
		boolean isDocSoporte = (LCO_FE_Utils.TIPO_COMPROBANTE_SOPORTE.equals(dianshortdoctype)
				|| LCO_FE_Utils.TIPO_COMPROBANTE_AJUSTE_AC.equals(dianshortdoctype));

		List<List<Object>> rows = DB.getSQLArrayObjectsEx(trx, LCO_FE_Utils.SQL_TAX_INFO, c_invoice_id, "%", c_invoice_id, "%");
		if (rows != null) {
			for (List<Object> row : rows) {
				boolean EsRetencion = (row.get(0) != null && ((String)row.get(0)).equals("Y")) ? true : false;
				if (EsRetencion)
					continue;
				if (row.get(3).toString().equals(LCO_FE_Utils.CODIGO_IVA_01))
						importeiva = (BigDecimal) row.get(1);
				else if (row.get(3).toString().equals(LCO_FE_Utils.CODIGO_CONSUMO_02))
						importeotros.add((BigDecimal) row.get(1));
				else if (row.get(3).toString().equals(LCO_FE_Utils.CODIGO_ICA_03))
						importeotros.add((BigDecimal) row.get(1));
				else if (row.get(3).toString().equals(LCO_FE_Utils.CODIGO_INC_04))
					importeotros.add((BigDecimal) row.get(1));
			}
		}
			
		StringBuilder qrdata = new StringBuilder();
		
		qrdata.append((!isDocSoporte ? "NumFac: " : "NumDS: ") + documentno + " ");	// Dalayed RetNewLine
		if (LCO_FE_Utils.UBL_VERSION_21.equals(m_UBLVersionNo))
			qrdata.append((!isDocSoporte ? "FecFac: " : "FecDS: ") +  LCO_FE_Utils.getDateTime(datetrx, 11) + " "
				+ (!isDocSoporte ? "HorFac: " : "HorDS: ") + LCO_FE_Utils.getDateTime(datetrx, 14) + " ");	// YYYYmmddHHMMss.TZ
		else
			qrdata.append("FecFac: " + LCO_FE_Utils.getDateTime(datetrx, 19) + " ");	// YYYYmmddHHMMss
		qrdata.append((!isDocSoporte ? "NitFac: " : "NumSNO: ") + taxidofe + " ");
		qrdata.append("DocAdq: " + taxidadq + " ");
		qrdata.append((!isDocSoporte ? "ValFac: " : "ValDS: ") + LCO_FE_Utils.decimalFormat(totallines) + " ");
		qrdata.append("ValIva: " + LCO_FE_Utils.decimalFormat(importeiva) + " ");
		if (!isDocSoporte)
			qrdata.append("ValOtroIm: " + LCO_FE_Utils.decimalFormat(importeotros) + " ");
		if (LCO_FE_Utils.UBL_VERSION_21.equals(m_UBLVersionNo))
			qrdata.append((!isDocSoporte ? "ValFacIm: " : "ValTolDS: ") + LCO_FE_Utils.decimalFormat(grandtotal) + " ");
		else
			qrdata.append("ValTolFac: " + LCO_FE_Utils.decimalFormat(grandtotal) + " ");
		qrdata.append((!isDocSoporte ? "CUFE: " : "CUDS: ") + cufe);
		if (isDocSoporte)
			qrdata.append(" QRCode: " + m_DIANUrlQRCode + cufe);
		
		System.out.println("QR String: " + " " + qrdata.toString());
		return qrdata.toString();
		
	}

	public String constructFileName(String taxid, String repdoctype, Boolean hex) {
		
		boolean isDocSoporte = (LCO_FE_Utils.TIPO_COMPROBANTE_SOPORTE.equals(m_coddoc)
								|| LCO_FE_Utils.TIPO_COMPROBANTE_AJUSTE_AC.equals(m_coddoc));
		
		String docnoformat = "%010d";
		if (hex)
			if (isDocSoporte)
				docnoformat = "%08x";
			else
				docnoformat = "%010x";
				
		StringBuilder xmlFileName = new StringBuilder("");
		
		if (!isDocSoporte)
			xmlFileName = new StringBuilder("face_");
		
		xmlFileName.append(repdoctype);	// m_coddoc | DIAN face_{|f|d|c|ds}
		xmlFileName.append(String.format("%010d", Integer.valueOf(taxid)));
		if (isDocSoporte) {
			xmlFileName.append(String.format("%03d", Integer.valueOf(LCO_FE_Utils.CODIGO_PROVEEDOR_TECNOLOGICO)));
			xmlFileName.append(LCO_FE_Utils.getDate(m_DateInvoiced, 16));	// yy
		}
		xmlFileName.append(String.format(docnoformat, (!isDocSoporte ? Integer.valueOf(m_DocumentNo) : Integer.valueOf(m_seqEnvio))));
		xmlFileName.append("." + m_File_Type);
		
		return xmlFileName.toString();
	}
	
	/**
	 * 	File constructFileName
	 *  @param
	 * 	@return String filename
	 */
	public static String constructFileName(String taxid, String repdoctype, String documentno, String file_type, Boolean hex) {
		
		String docnoformat = "%010d";
		if (hex)
			docnoformat = "%010x";
		
		StringBuilder xmlFileName = new StringBuilder("face_");
		
		xmlFileName.append(repdoctype);	// m_coddoc | DIAN face_{|f|d|c}
		xmlFileName.append(String.format("%010d", Integer.valueOf(taxid)));
		xmlFileName.append(String.format(docnoformat, Integer.valueOf(documentno)));
		xmlFileName.append("." + file_type);
		
		return xmlFileName.toString();
	}
	
	/**
	 * 	File getFileFromStream
	 *  @param
	 * 	@return File file
	 */
	public File getFileFromStream(String xmlFilePath, int loc_fe_authorization_id, String resourcetype, String sufix) throws Exception {
    	
    	int  ad_table_id = MTable.getTable_ID(MLCOFEAuthorization.Table_Name);
    	InputStream inputStream = null;
		OutputStream outputStream = null;
		String filename = null;
		File file = null;
		MAttachment attach =  MAttachment.get(Env.getCtx(), ad_table_id, loc_fe_authorization_id);
		if (attach != null) {
    		for (MAttachmentEntry entry : attach.getEntries()) {
    			filename = entry.getName().substring(0,entry.getName().length()-4);
    			if (!entry.getName().contains(LCO_FE_Utils.OLD)
    					// && (signed ? entry.getName().contains(LCO_FE_Utils.SIG) : !entry.getName().contains(LCO_FE_Utils.SIG))
    					&& (!"None".equals(sufix) ? entry.getName().contains(sufix) : filename.length()==26)
    					&& entry.getName().endsWith(resourcetype) && entry.getName().contains(filename)) {
            		// setResource_To_Sign(entry.getName());
            		inputStream = new FileInputStream(entry.getFile());
            		outputStream = new FileOutputStream(xmlFilePath);
            		
            		int numRead = 0;
            		byte[] bytes = new byte[1024];
            		 
            		while ((numRead = inputStream.read(bytes)) != -1) {
            			outputStream.write(bytes, 0, numRead);
            		}
            		inputStream.close();
            		outputStream.flush();
            		outputStream.close();
            		file = (new File (xmlFilePath));
            		break;	// First
            	}
            }
		}
		
		return file;
    }

	public AttributesImpl addHeaderAttribute(String dianshortdoctype, String signerPolicy) {
		
		String xmlns = "";
		String xsi_schemalocation = "";
		String xmlns_sts = "http://www.dian.gov.co/contratos/facturaelectronica/v1/Structures";
		
		if (   LCO_FE_Utils.TIPO_COMPROBANTE_FACTURA.equals(dianshortdoctype)
			|| LCO_FE_Utils.TIPO_COMPROBANTE_EXPORTACION.equals(dianshortdoctype)
			|| LCO_FE_Utils.TIPO_COMPROBANTE_SOPORTE.equals(dianshortdoctype)) {
			xmlns = "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2";
			xsi_schemalocation = "http://www.dian.gov.co/contratos/facturaelectronica/v1 ../xsd/DIAN_UBL.xsd urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2 ../../ubl2/common/UnqualifiedDataTypeSchemaModule-2.0.xsd urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2 ../../ubl2/common/UBL-QualifiedDatatypes-2.0.xsd";
		} else if (LCO_FE_Utils.TIPO_COMPROBANTE_NC.equals(dianshortdoctype)
					|| LCO_FE_Utils.TIPO_COMPROBANTE_AJUSTE_AC.equals(dianshortdoctype)) {
			xmlns = "urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2";
			xsi_schemalocation = "http://www.dian.gov.co/contratos/facturaelectronica/v1 http://www.dian.gov.co/micrositios/fac_electronica/documentos/XSD/r0/DIAN_UBL.xsd urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2 http://www.dian.gov.co/micrositios/fac_electronica/documentos/common/UnqualifiedDataTypeSchemaModule-2.0.xsd urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2 http://www.dian.gov.co/micrositios/fac_electronica/documentos/common/UBL-QualifiedDatatypes-2.0.xsd";
		} else if (LCO_FE_Utils.TIPO_COMPROBANTE_ND.equals(dianshortdoctype)) {
			xmlns = "urn:oasis:names:specification:ubl:schema:xsd:DebitNote-2";
			xsi_schemalocation = "http://www.dian.gov.co/contratos/facturaelectronica/v1 http://www.dian.gov.co/micrositios/fac_electronica/documentos/XSD/r0/DIAN_UBL.xsd urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2 http://www.dian.gov.co/micrositios/fac_electronica/documentos/common/UnqualifiedDataTypeSchemaModule-2.0.xsd urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2 http://www.dian.gov.co/micrositios/fac_electronica/documentos/common/UBL-QualifiedDatatypes-2.0.xsd";
		}
		
		if (LCO_FE_Utils.UBL_VERSION_21.equals(m_UBLVersionNo)) {
			xmlns_sts = "dian:gov:co:facturaelectronica:Structures-2-1";
			if (LCO_FE_Utils.TIPO_COMPROBANTE_FACTURA.equals(dianshortdoctype)
					|| LCO_FE_Utils.TIPO_COMPROBANTE_EXPORTACION.equals(dianshortdoctype)
					|| LCO_FE_Utils.TIPO_COMPROBANTE_SOPORTE.equals(dianshortdoctype))
				xsi_schemalocation = "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2 http://docs.oasis-open.org/ubl/os-UBL-2.1/xsd/maindoc/UBL-Invoice-2.1.xsd";
			else if (LCO_FE_Utils.TIPO_COMPROBANTE_NC.equals(dianshortdoctype)
					|| LCO_FE_Utils.TIPO_COMPROBANTE_AJUSTE_AC.equals(dianshortdoctype))
				// xsi_schemalocation = "urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2 http://docs.oasis-open.org/ubl/os-UBL-2.1/xsd/maindoc/UBL-CreditNote-2.1.xsd";
				xsi_schemalocation = "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2 http://docs.oasis-open.org/ubl/os-UBL-2.1/xsd/maindoc/UBL-Invoice-2.1.xsd";
			else if (LCO_FE_Utils.TIPO_COMPROBANTE_ND.equals(dianshortdoctype))
				// xsi_schemalocation = "urn:oasis:names:specification:ubl:schema:xsd:DebitNote-2 http://docs.oasis-open.org/ubl/os-UBL-2.1/xsd/maindoc/UBL-DebitNote-2.1.xsd";
				xsi_schemalocation = "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2 http://docs.oasis-open.org/ubl/os-UBL-2.1/xsd/maindoc/UBL-Invoice-2.1.xsd";
		}
		
		AttributesImpl atts = new AttributesImpl();

		atts.clear();
		
		atts.addAttribute("", "", "xmlns", "CDATA", xmlns);		
		atts.addAttribute("", "", "xmlns:fe", "CDATA", signerPolicy);
		atts.addAttribute("", "", "xmlns:cac", "CDATA", "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2");
		atts.addAttribute("", "", "xmlns:cbc", "CDATA", "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2");
		atts.addAttribute("", "", "xmlns:clm54217", "CDATA", "urn:un:unece:uncefact:codelist:specification:54217:2001");
		atts.addAttribute("", "", "xmlns:clm66411", "CDATA", "urn:un:unece:uncefact:codelist:specification:66411:2001");
		atts.addAttribute("", "", "xmlns:clmIANAMIMEMediaType", "CDATA", "urn:un:unece:uncefact:codelist:specification:IANAMIMEMediaType:2003");
		atts.addAttribute("", "", "xmlns:ext", "CDATA", "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
		atts.addAttribute("", "", "xmlns:qdt", "CDATA", "urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2");
		atts.addAttribute("", "", "xmlns:sts", "CDATA", xmlns_sts);
		atts.addAttribute("", "", "xmlns:udt", "CDATA", "urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2");
		atts.addAttribute("", "", "xmlns:ds", "CDATA", "http://www.w3.org/2000/09/xmldsig#");
		atts.addAttribute("", "", "xmlns:xades", "CDATA", "http://uri.etsi.org/01903/v1.3.2#");
		atts.addAttribute("", "", "xmlns:xades141", "CDATA", "http://uri.etsi.org/01903/v1.4.1#");
		atts.addAttribute("", "", "xmlns:xsi", "CDATA", "http://www.w3.org/2001/XMLSchema-instance");
		atts.addAttribute("", "", "xsi:schemaLocation", "CDATA", xsi_schemalocation);

		return atts;
		
	}

	public void addHeaderElement(TransformerHandler mmDoc, String att, String value, AttributesImpl atts) throws Exception {
		if (att != null) {
			mmDoc.startElement("","",att,atts);
			mmDoc.characters(value.toCharArray(),0,value.toCharArray().length);
			mmDoc.endElement("","",att);
		} else {
			throw new AdempiereUserError(att + " empty");
		}
	}
	
}	// DIAN21_FE_UtilsXML
