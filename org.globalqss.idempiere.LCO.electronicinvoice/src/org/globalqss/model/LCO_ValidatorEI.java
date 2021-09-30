/***********************************************************************
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
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.adempiere.base.event.AbstractEventHandler;
import org.adempiere.base.event.IEventManager;
import org.adempiere.base.event.IEventTopics;
import org.adempiere.base.event.LoginEventData;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MLocation;
import org.compiere.model.MOrder;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MProduct;
import org.compiere.model.MSequence;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUser;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.globalqss.util.LCO_FE_Utils;
import org.osgi.service.event.Event;

/**
 *	Validator for Localization Colombia (Electronic Invoice)
 *
 *  @author Carlos Ruiz - globalqss - Quality Systems & Solutions - http://globalqss.com
 */
public class LCO_ValidatorEI extends AbstractEventHandler
{
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(LCO_ValidatorEI.class);

	/**
	 *	Initialize Validation
	 */
	@Override
	protected void initialize() {
		log.warning("");

		registerTableEvent(IEventTopics.PO_BEFORE_NEW, MInvoiceLine.Table_Name);
		registerTableEvent(IEventTopics.PO_BEFORE_CHANGE, MInvoiceLine.Table_Name);
		registerTableEvent(IEventTopics.PO_BEFORE_DELETE, MInvoiceLine.Table_Name);
		registerTableEvent(IEventTopics.PO_BEFORE_CHANGE, MInvoice.Table_Name);
		registerTableEvent(IEventTopics.PO_BEFORE_DELETE, MInvoice.Table_Name);

		registerTableEvent(IEventTopics.DOC_BEFORE_VOID, MInvoice.Table_Name);
		registerTableEvent(IEventTopics.DOC_BEFORE_COMPLETE, MInvoice.Table_Name);
		registerTableEvent(IEventTopics.DOC_AFTER_COMPLETE, MInvoice.Table_Name);

		registerEvent(IEventTopics.AFTER_LOGIN);
	}	//	initialize

	/**
	 *	Model Change of a monitored Table.
	 *	Called after PO.beforeSave/PO.beforeDelete
	 *	when you called addModelChange for the table
	 *  @param event
	 *	@exception Exception if the recipient wishes the change to be not accept.
	 */
	@Override
	protected void doHandleEvent(Event event) {
		String type = event.getTopic();

		if (type.equals(IEventTopics.AFTER_LOGIN)) {
			log.info("Type: " + type);
			// on login set context variable #LCO_USE_ELECTRONIC_INVOICE
			LoginEventData loginData = (LoginEventData) event.getProperty(IEventManager.EVENT_DATA);
			boolean useEI = MSysConfig.getBooleanValue("LCO_USE_ELECTRONIC_INVOICE", true, loginData.getAD_Client_ID());
			Env.setContext(Env.getCtx(), "#LCO_USE_ELECTRONIC_INVOICE", useEI);
			return;
		}

		if (! MSysConfig.getBooleanValue("LCO_USE_ELECTRONIC_INVOICE", true, Env.getAD_Client_ID(Env.getCtx())))
			return;

		PO po = getPO(event);
		log.info(po + " Type: " + type);

		String msg = null;

		// do not allow changes in MInvoice and MInvoiceLine if there is an authorization in progress
		if (   (   po.get_TableName().equals(MInvoiceLine.Table_Name)
			    && (   type.equals(IEventTopics.PO_BEFORE_NEW)
			        || type.equals(IEventTopics.PO_BEFORE_CHANGE)
			        || type.equals(IEventTopics.PO_BEFORE_DELETE)))
			|| (   po.get_TableName().equals(MInvoice.Table_Name)
				&& (   type.equals(IEventTopics.PO_BEFORE_CHANGE)
					|| type.equals(IEventTopics.PO_BEFORE_DELETE)))
			) {
			if (! po.get_TrxName().startsWith("WFP_")) {
				int invoiceId = po.get_ValueAsInt("C_Invoice_ID");
				int actionColumn = po.get_ColumnIndex("DocAction");
				int isPaidColumn = po.get_ColumnIndex("IsPaid");
				int paymentIdColumn = po.get_ColumnIndex("C_Payment_ID");
				for (int idx = 0; idx < po.get_ColumnCount(); idx++) {
					if (po.is_ValueChanged(idx)) {
						if (idx == actionColumn && "VO".equals(po.get_ValueAsString("DocAction"))) {
							continue;
						}
						if (idx == isPaidColumn || idx == paymentIdColumn) {
							// this is OK changed by Validate Business Partner, or reversing an allocation
							continue;
						}
						int authId = MLCOFEAuthorization.getIdFromInvoice(invoiceId);
						if (authId > 0) {
							MLCOFEAuthorization auth = new MLCOFEAuthorization(po.getCtx(), authId, po.get_TrxName());
							throw new AdempiereException("Autorización " + auth.getValue() + " en proceso, no se puede modificar la factura, verifique estado de la autorización");
						}
					}
				}
			}
		}
		// do not allow void or complete MInvoice if there is an authorization in progress
		if (   po.get_TableName().equals(MInvoice.Table_Name)
			&& (   type.equals(IEventTopics.DOC_BEFORE_VOID)
				|| type.equals(IEventTopics.DOC_BEFORE_COMPLETE))
			) {
			int invoiceId = po.get_ValueAsInt("C_Invoice_ID");
			int authId = MLCOFEAuthorization.getIdFromInvoice(invoiceId);
			if (authId > 0) {
				MLCOFEAuthorization auth = new MLCOFEAuthorization(po.getCtx(), authId, po.get_TrxName());
				if (   type.equals(IEventTopics.DOC_BEFORE_VOID)
					&& auth.getLCO_FE_IdErrorCode().signum() != 0
					&& !auth.isProcessed()) {
					MInvoice invoice = (MInvoice)po;
					invoice.set_ValueOfColumn("LCO_FE_Authorization_ID", auth.getLCO_FE_Authorization_ID());
					String documentno = auth.getValue().trim().substring(auth.getValue().lastIndexOf("-")+1);
					invoice.setDocumentNo(documentno);
					auth.setProcessed(true);
					auth.saveEx();
				} else if (!(   type.equals(IEventTopics.DOC_BEFORE_COMPLETE)
					  && auth.isProcessed()
					 )
				   ) {
					throw new AdempiereException("Autorización " + auth.getValue() + " en proceso, no se puede modificar la factura, verifique estado de la autorización");
				}
			}
		}
		
		// Model Events
		if (   po.get_TableName().equals(MInvoiceLine.Table_Name)
			&& (   type.equals(IEventTopics.PO_BEFORE_NEW)
				|| type.equals(IEventTopics.PO_BEFORE_CHANGE))) {
			MInvoiceLine invoiceLine = (MInvoiceLine) po;
			MInvoice invoice = (MInvoice) invoiceLine.getC_Invoice();
			// if (invoice.isSOTrx()) {
				MDocType dt = MDocType.get(invoice.getCtx(), invoice.getC_DocTypeTarget_ID());
				X_LCO_FE_DocType fedt = null;
				String dianshortdoctype = null;
				if (dt.get_ValueAsInt("LCO_FE_DocType_ID") > 0) {
					fedt = new X_LCO_FE_DocType (invoice.getCtx(), dt.get_ValueAsInt("LCO_FE_DocType_ID"), invoice.get_TrxName());
					dianshortdoctype = fedt.getDianShortDocType();
				}
				//
				if (dianshortdoctype != null) {	// Electronic Invoice
					// Max 4 digit
					validateDigitAllowed(invoiceLine.getQtyEntered(), 4, MInvoiceLine.COLUMNNAME_QtyEntered);
					validateDigitAllowed(invoiceLine.getPriceEntered(), 4, MInvoiceLine.COLUMNNAME_PriceEntered);
					validateDigitAllowed(invoiceLine.getLineNetAmt(), 4, MInvoiceLine.COLUMNNAME_LineNetAmt);
				}
			// }
		}

		// before completing SO/PO invoice set LCO_FE
		if (   po.get_TableName().equals(MInvoice.Table_Name)
			&& type.equals(IEventTopics.DOC_BEFORE_COMPLETE)) {
			MInvoice invoice = (MInvoice) po;
			// if (invoice.isSOTrx()) {
				MDocType dt = MDocType.get(invoice.getCtx(), invoice.getC_DocTypeTarget_ID());
				X_LCO_FE_DocType fedt = null;
				String dianshortdoctype = null;
				if (dt.get_ValueAsInt("LCO_FE_DocType_ID") > 0) {
					fedt = new X_LCO_FE_DocType (invoice.getCtx(), dt.get_ValueAsInt("LCO_FE_DocType_ID"), invoice.get_TrxName());
					dianshortdoctype = fedt.getDianShortDocType();
					if (dianshortdoctype != null) {
						Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
						invoice.set_ValueOfColumn("LCO_FE_DateTrx", now);
						invoice.setDateInvoiced(now);
						invoice.set_ValueOfColumn("LCO_FE_IsExport", dt.get_ValueAsBoolean("LCO_FE_IsExport"));
						invoice.set_ValueOfColumn("LCO_FE_IsUseContingency", dt.get_ValueAsBoolean("LCO_FE_IsUseContingency"));
						if (dianshortdoctype.equals(LCO_FE_Utils.TIPO_COMPROBANTE_NC)
								|| dianshortdoctype.equals(LCO_FE_Utils.TIPO_COMPROBANTE_ND)) {
							int ref_invoice_id = invoice.getRelatedInvoice_ID();
							if (ref_invoice_id == 0) {
								ref_invoice_id = LCO_FE_Utils.getRefInvoiceID(invoice);
								if (ref_invoice_id > 0)
									// invoice.setRef_Invoice_ID(ref_invoice_id);		// Until 5.1
									invoice.setRelatedInvoice_ID(ref_invoice_id);
							}
						}
						invoice.saveEx();
					}
					validateInvoice(invoice);
					validateInvoiceLines(invoice);
				}
			// }
		}

		// after completing SO/PO invoice process electronic invoice
		if (   po.get_TableName().equals(MInvoice.Table_Name)
			&& type.equals(IEventTopics.DOC_AFTER_COMPLETE)) {
			MInvoice invoice = (MInvoice) po;
			// if (invoice.isSOTrx()) {
				int authId = MLCOFEAuthorization.getIdFromInvoice(invoice.getC_Invoice_ID());
				if (authId <= 0) {
					MLCOFEAuthorization auth = new MLCOFEAuthorization(invoice.getCtx(), authId, invoice.get_TrxName());
					if (auth.isProcessed()) {
						// authorization is already processed, set invoice number from authorization
						String docNo = auth.getValue().substring(auth.getValue().indexOf("-"));
						invoice.setDocumentNo(docNo);
						// TODO: set LCO_FE_DateTrx, DateInvoiced, LCO_FE_IsExport, LCO_FE_IsUseContingency, RelatedInvoice_ID from the XML file  
					} else {
						boolean IsGenerateInBatch = false;
						MDocType dt = new MDocType(invoice.getCtx(), invoice.getC_DocTypeTarget_ID(), invoice.get_TrxName());
						if (invoice.getC_Order_ID() > 0) {
							MDocType odt = new MDocType(invoice.getCtx(), invoice
									.getC_Order().getC_DocType_ID(), invoice.get_TrxName());

							if (odt.get_ValueAsBoolean("LCO_FE_IsGenerateInBatch"))
								IsGenerateInBatch = true;
						}

						if (dt.get_ValueAsBoolean("LCO_FE_IsGenerateInBatch"))
							IsGenerateInBatch = true;

						//
						if (dt.get_ValueAsInt("LCO_FE_DocType_ID") > 0 && !IsGenerateInBatch) {
							msg = LCO_ValidatorEI.invoiceGenerateXml(invoice);
							if (msg != null)
								throw new RuntimeException(msg);
						}
					}
				}
			// }
		}

	}

	/**
	 * valide DigitAllowed
	 * 
	 * @param qtyEntered
	 * 
	 * @return void or throw Maximum allowed two decimal digits
	 */
	private void validateDigitAllowed(BigDecimal amt, int digitAllowed, String columnName) {
		String amtStr = String.valueOf(amt);
		int pos = 0;
		String decimal = "";
		pos = amtStr.lastIndexOf('.');

		if (pos > 0) {
			decimal = amtStr.substring(pos);
		}
		if ((decimal.length() - 1) > digitAllowed) { // .00 = 3 Digits
			throw new AdempiereException(Msg.translate(Env.getCtx(),
					"Maximum allowed " + digitAllowed + " decimal digits")
					+ "-"
					+ Msg.getElement(Env.getCtx(), columnName));
		}
	}	// validateDigitAllowed

	/**
	 * validateInvoice
	 * @param MInvoice
	 */
	private void validateInvoice(MInvoice invoice) {
		String msg = null;
		MDocType dt = new MDocType(invoice.getCtx(), invoice.getC_DocTypeTarget_ID(), invoice.get_TrxName());
		X_LCO_FE_DocType fedt = null;
		String dianshortdoctype = null;
		if (dt.get_ValueAsInt("LCO_FE_DocType_ID") > 0) {
			fedt = new X_LCO_FE_DocType (invoice.getCtx(), dt.get_ValueAsInt("LCO_FE_DocType_ID"), invoice.get_TrxName());
			dianshortdoctype = fedt.getDianShortDocType();
		}
		if (dianshortdoctype != null) {	// Electronic Billing

			String prefix = "";
			String docNumWithoutPrefix = invoice.getDocumentNo();
			if (dt.getDefiniteSequence().getPrefix() != null) {
				prefix = dt.getDefiniteSequence().getPrefix();
				docNumWithoutPrefix = invoice.getDocumentNo().replace(prefix, "");
			}
			try {
				long docNum = Long.parseLong(docNumWithoutPrefix);
				if (docNum < 0)
					throw new NumberFormatException();
			} catch (NumberFormatException e) {
				throw new AdempiereException((Msg.getMsg(Env.getCtx(), "LCO_NotANumber") + " "
						+ Msg.getElement(Env.getCtx(), MInvoice.COLUMNNAME_DocumentNo) + "-"
						+ docNumWithoutPrefix));
			}

			if (LCO_FE_Utils.notValidLength(invoice.getDocumentNo(), 1, 20))
				throw new AdempiereException(Msg.translate(Env.getCtx(), "Invalid")
						+ Msg.translate(Env.getCtx(), "Length") + " > 20 "
						+ Msg.getElement(Env.getCtx(), MInvoice.COLUMNNAME_DocumentNo) + "-"
						+ invoice.getDocumentNo());

			if (fedt.getDianDocTypeCode() == null)
				throw new AdempiereException(Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_DocType.COLUMNNAME_LCO_FE_DocType_ID) + "-"
						+ fedt.getValue() + "-"
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_DocType.COLUMNNAME_DianDocTypeCode));
			if (fedt.getDianShortDocType() == null)
				throw new AdempiereException(Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_DocType.COLUMNNAME_LCO_FE_DocType_ID) + "-"
						+ fedt.getValue() + "-"
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_DocType.COLUMNNAME_DianShortDocType));
			if (LCO_FE_Utils.notValidLength(dianshortdoctype, 1, 6))
				throw new AdempiereException(Msg.translate(Env.getCtx(), "Length") + " > 6 "
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_DocType.COLUMNNAME_LCO_FE_DocType_ID) + "-"
						+ fedt.getValue() + "-"
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_DocType.COLUMNNAME_DianShortDocType) + " > 6");
			if (invoice.get_ValueAsBoolean("LCO_FE_IsUseContingency")) {
				int lco_fe_ofe_contingency_id = LCO_FE_Utils.getOfeContingenciaID(invoice);
				if ( lco_fe_ofe_contingency_id < 1 )
					throw new AdempiereException(Msg.translate(Env.getCtx(), "NotValid") + " "
							+ Msg.getElement(Env.getCtx(), X_LCO_FE_OFE_Contingency.COLUMNNAME_LCO_FE_OFE_Contingency_ID));
			}

			int lco_fe_operationtype_id = invoice.get_ValueAsInt("LCO_FE_OperationType_ID");

			if ( lco_fe_operationtype_id < 1)
				throw new AdempiereException(Msg.translate(Env.getCtx(), "NotValid") + " "
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_OperationType.COLUMNNAME_LCO_FE_OperationType_ID));

			X_LCO_FE_OperationType feot = new X_LCO_FE_OperationType (invoice.getCtx(), lco_fe_operationtype_id, invoice.get_TrxName());

			if ( feot.getValue() == null)
				throw new AdempiereException(Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_OperationType.COLUMNNAME_LCO_FE_OperationType_ID) + "-"
						+ fedt.getValue() + "-"
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_DIAN_Format.COLUMNNAME_Value));

			if (LCO_FE_Utils.notValidLength(feot.getValue(), 1, 2))
				throw new AdempiereException(Msg.translate(Env.getCtx(), "Invalid")
						+ Msg.translate(Env.getCtx(), "Length") + " > 2 "
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_OperationType.COLUMNNAME_Value) + "-"
						+ feot.getValue());

			int lco_fe_dian_format_id = LCO_FE_Utils.getLcoFeDianFormatID(invoice, fedt.getLCO_FE_DocType_ID());

			if ( lco_fe_dian_format_id < 1)
				throw new AdempiereException(Msg.translate(Env.getCtx(), "NotValid") + " "
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_DIAN_Format.COLUMNNAME_LCO_FE_DIAN_Format_ID));

			X_LCO_FE_DIAN_Format f = new X_LCO_FE_DIAN_Format (invoice.getCtx(), lco_fe_dian_format_id, invoice.get_TrxName());

			if (   f.getUBLVersionNo() == null 
					|| f.getVersionNo() == null
					|| f.getXmlPrintLabel() == null
					|| f.getLCO_FE_EDIType() == null)
				throw new AdempiereException(Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_DIAN_Format.COLUMNNAME_LCO_FE_DIAN_Format_ID) + "-"
						+ fedt.getValue() + "-"
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_DIAN_Format.COLUMNNAME_UBLVersionNo) + "-"
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_DIAN_Format.COLUMNNAME_VersionNo) + "-"
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_DIAN_Format.COLUMNNAME_XmlPrintLabel) + "-"
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_DIAN_Format.COLUMNNAME_LCO_FE_EDIType));

			if (LCO_FE_Utils.notValidLength(f.getUBLVersionNo(), 7, 7))
				throw new AdempiereException(Msg.translate(Env.getCtx(), "Invalid")
						+ Msg.translate(Env.getCtx(), "Length") + " > 7" + " "
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_DIAN_Format.COLUMNNAME_VersionNo) + "-"
						+ f.getUBLVersionNo());

			if (LCO_FE_Utils.notValidLength(f.getVersionNo(), 8, 8))
				throw new AdempiereException(Msg.translate(Env.getCtx(), "Invalid")
						+ Msg.translate(Env.getCtx(), "Length") + " > 8" + " "
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_DIAN_Format.COLUMNNAME_VersionNo) + "-"
						+ f.getVersionNo());
			
			if (dianshortdoctype.equals(LCO_FE_Utils.TIPO_COMPROBANTE_SOPORTE) && f.getAD_Sequence_ID() == 0)
				throw new AdempiereException(Msg.translate(Env.getCtx(), "FillMandatory")
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_DIAN_Format.COLUMNNAME_AD_Sequence_ID));

			int lco_printedformcontrol_id = LCO_FE_Utils.getDianResolutionID(invoice, dt.get_ValueAsBoolean("LCO_FE_IsUseContingency"));

			if ( lco_printedformcontrol_id < 1)
				throw new AdempiereException(Msg.translate(Env.getCtx(), "NotValid") + " "
						+ fedt.getValue() + "-"
						+ Msg.getElement(Env.getCtx(), X_LCO_PrintedFormControl.COLUMNNAME_LCO_PrintedFormControl_ID));

			X_LCO_PrintedFormControl pfc = new X_LCO_PrintedFormControl (invoice.getCtx(), lco_printedformcontrol_id, invoice.get_TrxName());

			if (Integer.valueOf(docNumWithoutPrefix) > pfc.getFinalSequence())
				throw new AdempiereException(Msg.translate(Env.getCtx(), "NotValid") + " "
						+ Msg.getElement(Env.getCtx(), X_LCO_PrintedFormControl.COLUMNNAME_LCO_PrintedFormControl_ID) + "-"
						+ Msg.getElement(Env.getCtx(), MInvoice.COLUMNNAME_DocumentNo) + "-"
						+ docNumWithoutPrefix + " > "
						+ Msg.getElement(Env.getCtx(), X_LCO_PrintedFormControl.COLUMNNAME_FinalSequence) + "-"
						+ pfc.getFinalSequence());

			if (pfc.getPrefix() != null && LCO_FE_Utils.notValidLength(pfc.getPrefix(), 0, 5))
				throw new AdempiereException(Msg.translate(Env.getCtx(), "Invalid")
						+ Msg.translate(Env.getCtx(), "Length") + " > 5" + " "
						+ Msg.getElement(Env.getCtx(), X_LCO_PrintedFormControl.COLUMNNAME_Prefix) + "-"
						+ pfc.getPrefix());

			if ( pfc.getPrefix() == null && !prefix.equals("")
					|| pfc.getPrefix() != null && !prefix.replace("-","").equals(pfc.getPrefix()) )
				throw new AdempiereException(Msg.translate(Env.getCtx(), "NotValid") + " "
						+ Msg.getElement(Env.getCtx(), X_LCO_PrintedFormControl.COLUMNNAME_LCO_PrintedFormControl_ID) + "-"
						+ Msg.getElement(Env.getCtx(), X_LCO_PrintedFormControl.COLUMNNAME_Prefix) + "-"
						+ pfc.getPrefix() + " <> "
						+ Msg.getElement(Env.getCtx(), MSequence.COLUMNNAME_Prefix) + "-"
						+ prefix);

			if (   dianshortdoctype.equals(LCO_FE_Utils.TIPO_COMPROBANTE_FACTURA)
					&& (   pfc.getAuthorizationNo() == null 
					|| pfc.getAuthorizationNo().trim().length() == 0
					|| pfc.getValidFrom() == null
					|| pfc.getValidUntil() == null))
				throw new AdempiereException(Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), X_LCO_PrintedFormControl.COLUMNNAME_AuthorizationNo) + "-"
						+ Msg.getElement(Env.getCtx(), X_LCO_PrintedFormControl.COLUMNNAME_ValidFrom) + "-"
						+ Msg.getElement(Env.getCtx(), X_LCO_PrintedFormControl.COLUMNNAME_ValidUntil) + "-"
						+ pfc.getAuthorizationNo());

			if (pfc.getAuthorizationNo() != null && LCO_FE_Utils.notValidLength(pfc.getAuthorizationNo(), 1, 14))
				throw new AdempiereException(Msg.translate(Env.getCtx(), "Invalid")
						+ Msg.translate(Env.getCtx(), "Length") + " > 14" + " "
						+ Msg.getElement(Env.getCtx(), X_LCO_PrintedFormControl.COLUMNNAME_AuthorizationNo) + "-"
						+ pfc.getAuthorizationNo());

			if (pfc.getPrefix() != null && LCO_FE_Utils.notValidLength(pfc.getPrefix(), 0, 4))
				throw new AdempiereException(Msg.translate(Env.getCtx(), "Invalid")
						+ Msg.translate(Env.getCtx(), "Length") + " > 4" + " "
						+ Msg.getElement(Env.getCtx(), X_LCO_PrintedFormControl.COLUMNNAME_Prefix) + "-"
						+ pfc.getPrefix());

			if (   LCO_FE_Utils.notValidLength(String.valueOf(pfc.getInitialSequence()), 1, 9)
					|| LCO_FE_Utils.notValidLength(String.valueOf(pfc.getFinalSequence()), 1, 9))
				throw new AdempiereException(Msg.translate(Env.getCtx(), "Invalid")
						+ Msg.translate(Env.getCtx(), "Length") + " > 9" + " "
						+ Msg.getElement(Env.getCtx(), X_LCO_PrintedFormControl.COLUMNNAME_InitialSequence) + "-"
						+ Msg.getElement(Env.getCtx(), X_LCO_PrintedFormControl.COLUMNNAME_FinalSequence) + "-"
						+ pfc.getInitialSequence() + "-" + pfc.getFinalSequence());

			MOrgInfo oi = MOrgInfo.get(invoice.getCtx(), invoice.getAD_Org_ID(), invoice.get_TrxName());
			msg = LCO_ValidatorEI.validateOrgInfoDian (oi);
			if (msg != null)
				throw new AdempiereException(Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), MOrgInfo.COLUMNNAME_AD_Org_ID) + "-"
						+ msg);

			MBPartner bpe = new MBPartner(invoice.getCtx(), oi.get_ValueAsInt("C_BPartner_ID"), invoice.get_TrxName());
			msg = LCO_ValidatorEI.validateBPInfoDian (bpe);
			if (msg != null)
				throw new AdempiereException(Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), MBPartner.COLUMNNAME_C_BPartner_ID) + "-"
						+ msg);

			int ad_user_id = invoice.get_ValueAsInt("LCO_FE_UserFrom_ID");
			msg = LCO_ValidatorEI.validateUserInfoDian (ad_user_id);
			if (msg != null)
				throw new AdempiereException(Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), MUser.COLUMNNAME_AD_User_ID) + "-"
						+ msg);

			MBPartner bp = new MBPartner(invoice.getCtx(), invoice.getC_BPartner_ID(), invoice.get_TrxName());
			msg = LCO_ValidatorEI.validateBPInfoDian (bp);
			if (msg != null)
				throw new AdempiereException(Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), MBPartner.COLUMNNAME_C_BPartner_ID) + "-"
						+ msg);

			ad_user_id = invoice.get_ValueAsInt("LCO_FE_UserTo_ID");
			msg = LCO_ValidatorEI.validateUserInfoDian (ad_user_id);
			if (msg != null)
				throw new AdempiereException(Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), MUser.COLUMNNAME_AD_User_ID) + "-"
						+ msg);

			MUser ua = new MUser(invoice.getCtx(), ad_user_id, invoice.get_TrxName());
			if (!validateUserMail(ua))
				throw new AdempiereException(Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), MUser.COLUMNNAME_AD_User_ID)	+ "-"
						+ ua.getName() + "-"
						+ Msg.getElement(Env.getCtx(), MUser.COLUMNNAME_EMail));

			ad_user_id = invoice.getSalesRep_ID();
			msg = LCO_ValidatorEI.validateUserInfoDian (ad_user_id);
			if (msg != null)
				throw new AdempiereException(Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), MUser.COLUMNNAME_AD_User_ID) + "-"
						+ msg);

			//
			if (invoice.getAD_User_ID() <= 0)
				throw new AdempiereException(Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), MInvoice.COLUMNNAME_AD_User_ID));
			if (!validateUserMail((MUser) invoice.getAD_User()))
				throw new AdempiereException(Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), MUser.COLUMNNAME_AD_User_ID)	+ "-"
						+ MUser.getNameOfUser(invoice.getAD_User_ID()) + "-"
						+ Msg.getElement(Env.getCtx(), MUser.COLUMNNAME_EMail));
			if (bp.get_Value(X_LCO_TaxIdType.COLUMNNAME_LCO_TaxIdType_ID) == null)
				throw new AdempiereException(Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), MBPartner.COLUMNNAME_C_BPartner_ID) + "-"
						+ Msg.getElement(Env.getCtx(), X_LCO_TaxIdType.COLUMNNAME_LCO_TaxIdType_ID));
			if (bp.get_Value(X_LCO_TaxPayerType.COLUMNNAME_LCO_TaxPayerType_ID) == null)
				throw new AdempiereException(Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), MBPartner.COLUMNNAME_C_BPartner_ID) + "-"
						+ Msg.getElement(Env.getCtx(), X_LCO_TaxPayerType.COLUMNNAME_LCO_TaxPayerType_ID));
			if (dianshortdoctype.equals(LCO_FE_Utils.TIPO_COMPROBANTE_NC)
					|| dianshortdoctype.equals(LCO_FE_Utils.TIPO_COMPROBANTE_ND)) {
				// int ref_invoice_id = LCO_FE_Utils.getRefInvoiceID(invoice);	// Until 5.1
				int ref_invoice_id = invoice.getRelatedInvoice_ID();
				if ( ref_invoice_id <= 0 &&	// Regla CBG02
						!(lco_fe_operationtype_id == 1000033			// Nota Crédito sin referencia a facturas
							|| lco_fe_operationtype_id == 1000036) )	// Nota Débito sin referencia a facturas
					throw new AdempiereException(Msg.translate(Env.getCtx(), "NotValid") + " "
							+ Msg.getElement(Env.getCtx(), MInvoice.COLUMNNAME_RelatedInvoice_ID));
				// Solo comprobantes FE
				MInvoice ref_invoice = new MInvoice(invoice.getCtx(), ref_invoice_id, invoice.get_TrxName());
				if (invoice.getAD_Org_ID() != ref_invoice.getAD_Org_ID())
					throw new AdempiereException(Msg.translate(Env.getCtx(), "Difference") + " "
							+ Msg.getElement(Env.getCtx(), MInvoice.COLUMNNAME_AD_Org_ID) + "-"
							+ ref_invoice.getAD_Org_ID() + " <> " + invoice.getAD_Org_ID());
				MDocType rdt = new MDocType(invoice.getCtx(), ref_invoice.getC_DocTypeTarget_ID(), invoice.get_TrxName());
				if (rdt.get_ValueAsInt("LCO_FE_DocType_ID") == 0)
					throw new AdempiereException(Msg.translate(Env.getCtx(), "NotValid") + " "
							+ Msg.getElement(Env.getCtx(), MInvoice.COLUMNNAME_POReference) + "-"
							+ Msg.getElement(Env.getCtx(), X_LCO_FE_DocType.COLUMNNAME_LCO_FE_DocType_ID));
				// TODO Reviewme Igual DIAN Version
				MLCOFEAuthorization ra = new MLCOFEAuthorization (invoice.getCtx(), ref_invoice.get_ValueAsInt("LCO_FE_Authorization_ID"), invoice.get_TrxName());
				int ref_lco_fe_dian_format_id = ra.getLCO_FE_DIAN_Format_ID();
				if ( ref_lco_fe_dian_format_id < 1)
					throw new AdempiereException(Msg.translate(Env.getCtx(), "NotReferenceTable") + " "
							+ Msg.getElement(Env.getCtx(), X_LCO_FE_DIAN_Format.COLUMNNAME_LCO_FE_DIAN_Format_ID));

				X_LCO_FE_DIAN_Format rf = new X_LCO_FE_DIAN_Format (invoice.getCtx(), ref_lco_fe_dian_format_id, invoice.get_TrxName());
				if (rf.getVersionNo().equals(LCO_FE_Utils.UBL_VERSION_21)
						&& (ra.getLCO_FE_Dian_Uuid() == null || LCO_FE_Utils.notValidLength(ra.getLCO_FE_Dian_Uuid(), 40, 96)))
					throw new AdempiereException(Msg.translate(Env.getCtx(), "Invalid")
							+ Msg.translate(Env.getCtx(), "Length") + " > 96" + " "
							+ Msg.getElement(Env.getCtx(), MLCOFEAuthorization.COLUMNNAME_LCO_FE_Dian_Uuid) + "-"
							+ ra.getLCO_FE_Dian_Uuid());

				if (!(f.getVersionNo().equals(rf.getVersionNo())))
					log.warning(Msg.translate(Env.getCtx(), "Difference") + " "
							+ Msg.getElement(Env.getCtx(), X_LCO_FE_DIAN_Format.COLUMNNAME_VersionNo));
				
				BigDecimal ref_totalretenciones = (ref_invoice.get_Value("WithholdingAmt") != null ? (BigDecimal) ref_invoice.get_Value("WithholdingAmt") : Env.ZERO);
				if (invoice.getGrandTotal().compareTo(ref_invoice.getGrandTotal().add(ref_totalretenciones)) > 0)
					throw new AdempiereException(Msg.translate(Env.getCtx(), "Difference") + " "
						+ Msg.getElement(Env.getCtx(), MInvoice.COLUMNNAME_GrandTotal) + "-"
						+ ref_invoice.getGrandTotal().add(ref_totalretenciones) + " <> " + invoice.getGrandTotal());
				
				if (invoice.getC_BPartner_ID() != ref_invoice.getC_BPartner_ID())
					throw new AdempiereException(Msg.translate(Env.getCtx(), "Difference") + " "
						+ Msg.getElement(Env.getCtx(), MInvoice.COLUMNNAME_C_BPartner_ID) + "-"
						+ ref_invoice.getC_BPartner_ID() + " <> " + invoice.getC_BPartner_ID());

				if (ref_invoice.getDateInvoiced().after(invoice.getDateInvoiced()))
					throw new AdempiereException(Msg.translate(Env.getCtx(), "NotValid") + " "
							+ Msg.getElement(Env.getCtx(), MInvoice.COLUMNNAME_DateInvoiced)
							+ ref_invoice.getDateInvoiced() + " Ref. > " + invoice.getDateInvoiced());

				if (invoice.getDescription() == null)
					throw new AdempiereException(Msg.translate(Env.getCtx(), "FillMandatory") + " "
							+ Msg.getElement(Env.getCtx(), MInvoice.COLUMNNAME_Description));
			}
			if (dianshortdoctype.equals(LCO_FE_Utils.TIPO_COMPROBANTE_NC)) {
				if (invoice.get_ValueAsInt("LCO_FE_NCConcept_ID") == 0)
					throw new AdempiereException(Msg.translate(Env.getCtx(), "FillMandatory") + " "
							+ Msg.getElement(Env.getCtx(), X_LCO_FE_NCConcept.COLUMNNAME_LCO_FE_NCConcept_ID));
			}
			if (dianshortdoctype.equals(LCO_FE_Utils.TIPO_COMPROBANTE_ND)) {
				if (invoice.get_ValueAsInt("LCO_FE_NDConcept_ID") == 0)
					throw new AdempiereException(Msg.translate(Env.getCtx(), "FillMandatory") + " "
							+ Msg.getElement(Env.getCtx(), X_LCO_FE_NDConcept.COLUMNNAME_LCO_FE_NDConcept_ID));
			}

			List<List<Object>> hlines = DB.getSQLArrayObjectsEx(invoice.get_TrxName(), LCO_FE_Utils.SQL_ITEMS, invoice.getC_Invoice_ID());
			int totalnolineas = 0;
			if (hlines != null)
				totalnolineas = hlines.size();
			if (hlines == null || totalnolineas == 0)
				throw new AdempiereException(Msg.translate(Env.getCtx(), "NotValid") + " "
						+ Msg.translate(Env.getCtx(), "NoLines"));

			Object[] args = new Object[] { invoice.getAD_Client_ID(), "Y", "%" };
			int lco_fe_productscheme_id = DB.getSQLValueEx(invoice.get_TrxName(), LCO_FE_Utils.SQL_SCHEMA, args);
			if ( lco_fe_productscheme_id < 1)
				throw new AdempiereException(Msg.translate(Env.getCtx(), "NotValid") + " "
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_ProductScheme.COLUMNNAME_LCO_FE_ProductScheme_ID));

			if (dt.get_ValueAsBoolean("LCO_FE_IsExport")) {

				args = new Object[] { invoice.getAD_Client_ID(), "%", LCO_FE_Utils.PRODUCT_SCHEME_PAID };
				lco_fe_productscheme_id = DB.getSQLValueEx(invoice.get_TrxName(), LCO_FE_Utils.SQL_SCHEMA, args);
				if ( lco_fe_productscheme_id < 1)
					throw new AdempiereException(Msg.translate(Env.getCtx(), "NotValid") + " "
							+ Msg.getElement(Env.getCtx(), X_LCO_FE_ProductScheme.COLUMNNAME_LCO_FE_ProductScheme_ID));

				boolean address = false;
				if (invoice.getC_Order_ID() > 0) {
					if (invoice.getC_Order().getC_BPartner_Location() != null) {
						if (invoice.getC_Order().getC_BPartner_Location()
								.getC_Location() != null)
							if (invoice.getC_Order()
									.getC_BPartner_Location()
									.getC_Location().getAddress1() != null)
								address = true;
					}
				}
				if (!address)
					throw new AdempiereException(Msg.translate(Env.getCtx(), "FillMandatory") + " "
							+ Msg.getElement(Env.getCtx(), MOrder.COLUMNNAME_C_Order_ID) + "-"
							+ Msg.getElement(Env.getCtx(), MLocation.COLUMNNAME_Address1));

			}
		}

	}	// validateInvoice

	/**
	 * validateOrgInfoDian
	 * 
	 * @param MOrgInfo orginfo
	 * @return error message or null
	 */
	public static String validateOrgInfoDian(MOrgInfo orginfo) {
		String msg = "";

		int c_bpartner_id = (orginfo.get_Value("C_BPartner_ID") != null ? orginfo.get_ValueAsInt("C_BPartner_ID") : 0);

		if (c_bpartner_id == 0)
			msg = Msg.translate(Env.getCtx(), "FillMandatory") + " "
					+ Msg.getElement(Env.getCtx(), "C_BPartner_ID");
		else if (orginfo.getC_Location_ID() == 0)
			msg = Msg.translate(Env.getCtx(), "FillMandatory") + " "
					+ Msg.getElement(Env.getCtx(), MOrgInfo.COLUMNNAME_C_Location_ID);
		else if (orginfo.get_ValueAsString("LCO_FE_IdCompany").equals(""))
			msg = Msg.translate(Env.getCtx(), "FillMandatory") + " "
					+ Msg.getElement(Env.getCtx(), "LCO_FE_IdCompany");
		else if (orginfo.get_ValueAsString("LCO_FE_UserName").equals(""))
			msg = Msg.translate(Env.getCtx(), "FillMandatory") + " "
					+ Msg.getElement(Env.getCtx(), "LCO_FE_UserName");
		else if (orginfo.get_ValueAsString("LCO_FE_UserName").length() > 50)
			msg = Msg.translate(Env.getCtx(), "Invalid")
			+ Msg.translate(Env.getCtx(), "Length") + " > 50" + " "
			+ Msg.getElement(Env.getCtx(), "LCO_FE_UserName");
		else if (orginfo.get_ValueAsString("LCO_FE_UserPass").equals(""))
			msg = Msg.translate(Env.getCtx(), "FillMandatory") + " "
					+ Msg.getElement(Env.getCtx(), "LCO_FE_UserPass");
		else if (orginfo.get_ValueAsString("LCO_FE_IdAccount").equals(""))
			msg = Msg.translate(Env.getCtx(), "FillMandatory") + " "
					+ Msg.getElement(Env.getCtx(), "LCO_FE_IdAccount");
		else if (LCO_FE_Utils.notValidLength(orginfo.get_ValueAsString("LCO_FE_IdAccount"), 1, 48))
			msg = Msg.translate(Env.getCtx(), "Invalid")
			+ Msg.translate(Env.getCtx(), "Length") + " > 48" + " "
			+ Msg.getElement(Env.getCtx(), "LCO_FE_IdAccount");

		MBPartner bpe = new MBPartner(orginfo.getCtx(), c_bpartner_id, orginfo.get_TrxName());
		String msgbpe = LCO_ValidatorEI.validateBPInfoDian (bpe);

		if (msgbpe != null)
			return msgbpe;

		X_LCO_TaxPayerType tpte = new X_LCO_TaxPayerType(bpe.getCtx(), orginfo.get_ValueAsInt("LCO_TaxPayerType_ID"), bpe.get_TrxName());

		if ( tpte.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_GRAN_CONTRIBUYENTE)) {
			// TODO IsAutoretenedor
			if (orginfo.get_ValueAsString("LCO_FE_AutoretResolucionNo").equals(""))
				msg = Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), "LCO_FE_AutoretResolucionNo");
			else if (orginfo.get_Value("LCO_FE_AutoretDate") == null)
				msg = Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), "LCO_FE_AutoretDate");
			else if (orginfo.get_ValueAsString("LCO_FE_GranConResolucionNo").equals(""))
				msg = msg + Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), "LCO_FE_GranConResolucionNo");
			else if (orginfo.get_ValueAsString("LCO_FE_GranConDate") == null)
				msg = msg + Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), "LCO_FE_GranConDate");
		}

		if (!msg.equals(""))
			msg = Msg.getElement(Env.getCtx(), MOrgInfo.COLUMNNAME_AD_Org_ID) + " "
					+ orginfo.getTaxID() + "-" + msg;
		else
			msg = null;

		return msg;

	} // validateOrgInfoDian

	public static String validateBPInfoDian(MBPartner bpartner) {

		String msg = "";

		if (bpartner.get_ValueAsInt("LCO_TaxIdType_ID") == 0)
			msg = Msg.translate(Env.getCtx(), "FillMandatory") + " "
					+ Msg.getElement(Env.getCtx(), "LCO_TaxIdType_ID");
		else if (bpartner.get_ValueAsInt("LCO_TaxPayerType_ID") == 0)
			msg = Msg.translate(Env.getCtx(), "FillMandatory") + " "
					+ Msg.getElement(Env.getCtx(), "LCO_TaxPayerType_ID");
		else if (bpartner.getTaxID() == null || bpartner.getTaxID().trim().length() == 0)
			msg = Msg.translate(Env.getCtx(), "LCO_NoTaxID") + " "
					+ Msg.getElement(Env.getCtx(), MBPartner.COLUMNNAME_TaxID);
		else if (LCO_FE_Utils.notValidLength(bpartner.getTaxID(), 5, 12))
			msg = Msg.translate(Env.getCtx(), "LCO_WrongLength") + " "
					+ Msg.getElement(Env.getCtx(), MBPartner.COLUMNNAME_TaxID);

		X_LCO_TaxIdType tt = null;
		if (bpartner.get_ValueAsInt("LCO_TaxIdType_ID") > 0) {
			tt = new X_LCO_TaxIdType(bpartner.getCtx(), bpartner.get_ValueAsInt("LCO_TaxIdType_ID"), bpartner.get_TrxName());

			if (tt.get_Value("LCO_TaxCodeDian") == null)
				msg = msg + Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), "LCO_TaxIdType_ID") + "-"
						+ Msg.getElement(Env.getCtx(), X_LCO_TaxIdType.COLUMNNAME_LCO_TaxCodeDian);
			if (tt.get_Value("LCO_TaxCodeDian") != null && LCO_FE_Utils.notValidLength(tt.get_ValueAsString("LCO_TaxCodeDian"), 2, 7))
				msg = msg + Msg.translate(Env.getCtx(), "Length") + " > 7" + " "
						+ Msg.getElement(Env.getCtx(), "LCO_TaxIdType_ID") + "-"
						+ Msg.getElement(Env.getCtx(), X_LCO_TaxIdType.COLUMNNAME_LCO_TaxCodeDian);
		}

		if (bpartner.get_ValueAsInt("LCO_TaxPayerType_ID") > 0) {

			X_LCO_TaxPayerType tpt = new X_LCO_TaxPayerType(bpartner.getCtx(), bpartner.get_ValueAsInt("LCO_TaxPayerType_ID"), bpartner.get_TrxName());

			if (tpt.get_Value("DianTaxPayerCode") == null)
				msg = msg + Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), "LCO_TaxPayerType_ID") + "-"
						+ Msg.getElement(Env.getCtx(), "DianTaxPayerCode");
			else if (tpt.get_ValueAsString("DianRegimeCode") == null)
				msg = msg + Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), "LCO_TaxPayerType_ID") + "-"
						+ Msg.getElement(Env.getCtx(), "DianRegimeCode");
			else if ( tpt.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_PERSONA_NATURAL) )
				if ( (bpartner.get_Value("FirstName1") == null)  || (bpartner.get_ValueAsString("LastName1") == null) )
					msg = msg + Msg.translate(Env.getCtx(), "FillMandatory") + " "
							+ Msg.getElement(Env.getCtx(), "FirstName1") + "-"
							+ Msg.getElement(Env.getCtx(), "LastName1");

			List<List<Object>> rows = DB.getSQLArrayObjectsEx(bpartner.get_TrxName(), LCO_FE_Utils.SQL_BP_INFO, bpartner.getC_BPartner_ID());
			if (rows == null || rows.size() == 0)
				msg = msg + (Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), MBPartner.COLUMNNAME_C_BPartner_ID) + "-"
						+ Msg.getElement(Env.getCtx(), "LCO_FE_TributaryType_ID"));

			if ( tpt.get_ValueAsString("DianTaxPayerCode").equals(LCO_FE_Utils.TIPO_PERSONA_JURIDICA)
					&& !tt.get_Value("LCO_TaxCodeDian").equals(LCO_FE_Utils.TIPO_IDENTIFICACION_EXTRANJERO) ) {
				if (bpartner.get_ValueAsString("LCO_FE_CCMatriculaMercantilNo").equals(""))
					msg = msg + Msg.translate(Env.getCtx(), "FillMandatory") + " "
							+ Msg.getElement(Env.getCtx(), "LCO_FE_CCMatriculaMercantilNo");
				else if (bpartner.get_ValueAsInt("LCO_FE_CCLocation_ID") == 0)
					msg = msg + Msg.translate(Env.getCtx(), "FillMandatory") + " "
							+ Msg.getElement(Env.getCtx(), "LCO_FE_CCLocation_ID");
				MLocation lacc = new MLocation(bpartner.getCtx(), bpartner.get_ValueAsInt("LCO_FE_CCLocation_ID"), bpartner.get_TrxName());
				String msgloc = LCO_ValidatorEI.validateBPLocation (lacc);
				if (msgloc != null)
					return Msg.getElement(Env.getCtx(), "LCO_FE_CCLocation_ID") + "-" + msgloc;
			}

		}

		if (!msg.equals(""))
			msg = Msg.getElement(Env.getCtx(), MBPartner.COLUMNNAME_C_BPartner_ID) + " "
					+ bpartner.getName() + "-" + msg;
		else
			msg = null;

		return msg;

	} // validateBPInfoDian

	public static String validateBPLocation(MLocation l) {

		String msg = "";

//		if (   l.getPostal() == null
//				|| LCO_FE_Utils.notValidLength(l.getPostal(), 0, 10))
//			msg = msg + Msg.translate(Env.getCtx(), "Invalid")
//			+ Msg.translate(Env.getCtx(), "Length") + " > 10" + " "
//			+ Msg.getElement(Env.getCtx(), MLocation.COLUMNNAME_Postal) + "-"
//			+ l.getPostal();

		if (   l.getPostal() != null
				&& LCO_FE_Utils.COUNTRY_CODE_CO.contentEquals(l.getCountry().getCountryCode())
				&& LCO_FE_Utils.notValidNumber(l.getPostal()))
			msg = msg + Msg.getMsg(Env.getCtx(), "LCO_NotANumber") + " "
					+ Msg.getElement(Env.getCtx(), MLocation.COLUMNNAME_Postal) + "-"
					+ l.getPostal();

//		if (   l.getAddress4() == null
//				|| l.getPostal() == null)	// || l.getAddress5() == null))
//			msg = msg + Msg.translate(Env.getCtx(), "FillMandatory") + " "
//					+ Msg.getElement(Env.getCtx(), "LCO_FE_CCLocation_ID") + "-"
//					+ Msg.getElement(Env.getCtx(), MLocation.COLUMNNAME_Address4) + "-Barrio" + "-"
//					+ Msg.getElement(Env.getCtx(), MLocation.COLUMNNAME_Postal);

		if (!msg.equals(""))
			msg = l.getC_Location_ID() + "-" + msg;
		else
			msg = null;

		return msg;

	} // validateBPLocation

	public static String validateUserInfoDian(int ad_user_id) {

		String msg = "";

		if (ad_user_id == 0) {
			msg = Msg.translate(Env.getCtx(), "FillMandatory") + " "
					+ Msg.getElement(Env.getCtx(), MInvoice.COLUMNNAME_C_BPartner_ID) + "-"
					+ Msg.getElement(Env.getCtx(), "LCO_FE_UserFrom_ID") + "-"
					+ Msg.getElement(Env.getCtx(), "LCO_FE_UserTo_ID");
			return msg;
		}

		MUser user = new MUser(Env.getCtx(), ad_user_id, null);

		if (user.get_Value("LCO_FE_ContactType_ID") == null)
			msg = Msg.translate(Env.getCtx(), "FillMandatory") + " "
					+ Msg.getElement(Env.getCtx(), "LCO_FE_ContactType_ID");
//		else if (user.getC_Job().getName() == null)
//			msg = Msg.translate(Env.getCtx(), "FillMandatory") + " "
//					+ Msg.getElement(Env.getCtx(), MUser.COLUMNNAME_C_Job_ID);

		if (user.get_ValueAsInt("LCO_FE_ContactType_ID") > 0) {
			X_LCO_FE_ContactType uoct = new X_LCO_FE_ContactType(user.getCtx(), user.get_ValueAsInt("LCO_FE_ContactType_ID"), user.get_TrxName());

			if (uoct.get_Value("DianContactCode") == null)
				msg = msg + Msg.translate(Env.getCtx(), "FillMandatory") + " "
						+ Msg.getElement(Env.getCtx(), X_LCO_FE_ContactType.COLUMNNAME_LCO_FE_ContactType_ID)
						+ Msg.getElement(Env.getCtx(), "DianContactCode");
		}

		if (!msg.equals(""))
			msg = Msg.getElement(Env.getCtx(), MUser.COLUMNNAME_AD_User_ID) + " "
					+ user.getName() + "-" + msg;
		else
			msg = null;

		return msg;

	} // validateUserInfoDian

	public static boolean validateUserMail(MUser user) {
		if (MSysConfig.getBooleanValue("QSSLCO_FE_EnvioXmlAutorizadoBPEmail", false, user.getAD_Client_ID())) {

			if (   (user.get_ID() == 0 || user.isNotificationEMail()
					&& (user.getEMail() == null || user.getEMail().length() == 0))) {
				return false;
			}
		}

		return true;
	} // validateUserMail

	/**
	 * validateInvoiceLines
	 *  
	 * @param MInvoice
	 * 
	 * @return error message or void
	 */
	private void validateInvoiceLines(MInvoice invoice) {

		List<List<Object>> rows = DB.getSQLArrayObjectsEx(invoice.get_TrxName(), LCO_FE_Utils.SQL_ITEMS, invoice.getC_Invoice_ID());
		if (rows != null) {
			for (List<Object> row : rows) {
				BigDecimal Consecutivo = (BigDecimal) row.get(0);
				BigDecimal qtyinvoiced = (BigDecimal) row.get(2);
				BigDecimal CostoTotal = (BigDecimal) row.get(4);
				String pcodigo = (String) row.get(13);
				String TipoImpuesto = (String) row.get(15);
				String NombreImpuesto = (String) row.get(21);
				String TipoProducto = (String) row.get(22);
				String CodigoEstandar = (String) row.get(23);
				String PartidaArancelaria = (String) row.get(24);

				if (qtyinvoiced.compareTo(Env.ZERO) == 0)
					throw new AdempiereException(Msg.getElement(Env.getCtx(), MInvoiceLine.COLUMNNAME_Line) + " "
							+ Consecutivo.toString() + "-"
							+ Msg.translate(Env.getCtx(), "FillMandatory") + " "
							+ Msg.getElement(Env.getCtx(), MInvoiceLine.COLUMNNAME_QtyInvoiced) + "-"
							+ qtyinvoiced);
				
				if (CostoTotal.compareTo(Env.ZERO) < 1)	// Regla: VLR01
					throw new AdempiereException(Msg.getElement(Env.getCtx(), MInvoiceLine.COLUMNNAME_Line) + " "
							+ Consecutivo.toString() + "-"
							+ Msg.translate(Env.getCtx(), "FillMandatory") + " "
							+ Msg.getElement(Env.getCtx(), MInvoiceLine.COLUMNNAME_PriceEntered) + "-"
							+ CostoTotal);
				
				if (!invoice.get_ValueAsBoolean("LCO_FE_IsExport") && CostoTotal.compareTo(Env.ZERO) == 0)
					throw new AdempiereException(Msg.getElement(Env.getCtx(), MInvoiceLine.COLUMNNAME_Line) + " "
							+ Consecutivo.toString() + "-"
							+ Msg.translate(Env.getCtx(), "FillMandatory") + " "
							+ Msg.getElement(Env.getCtx(), MInvoiceLine.COLUMNNAME_LineNetAmt) + "-"
							+ CostoTotal);

				if (TipoImpuesto == null || LCO_FE_Utils.notValidLength(TipoImpuesto, 1, 2))
					throw new AdempiereException(Msg.getElement(Env.getCtx(), MInvoiceLine.COLUMNNAME_Line) + " "
							+ Consecutivo.toString() + "-"
							+ Msg.translate(Env.getCtx(), "Invalid") + " "
							+ Msg.translate(Env.getCtx(), "Length") + " > 2" + " "
							+ TipoImpuesto);

				if (TipoImpuesto != null && LCO_FE_Utils.notValidLength(NombreImpuesto, 1, 30))
					throw new AdempiereException(Msg.getElement(Env.getCtx(), MInvoiceLine.COLUMNNAME_Line) + " "
							+ Consecutivo.toString() + "-"
							+ Msg.translate(Env.getCtx(), "Invalid") + " "
							+ Msg.translate(Env.getCtx(), "Length") + " > 30" + " "
							+ NombreImpuesto);

				if (!invoice.get_ValueAsBoolean("LCO_FE_IsExport") && (CodigoEstandar == null || LCO_FE_Utils.notValidLength(CodigoEstandar, 1, 10)))
					throw new AdempiereException(Msg.getElement(Env.getCtx(), MInvoiceLine.COLUMNNAME_Line) + " "
							+ Consecutivo.toString() + "-"
							+ Msg.translate(Env.getCtx(), "FillMandatory") + " "
							+ Msg.translate(Env.getCtx(), "Length") + " > 10" + " "
							+ Msg.getElement(Env.getCtx(), "LCO_FE_ProductSchemeList_ID") + "-"
							+ CodigoEstandar);

				if (invoice.get_ValueAsBoolean("LCO_FE_IsExport") && TipoProducto.equals(MProduct.PRODUCTTYPE_Item) && PartidaArancelaria == null)
					throw new AdempiereException(Msg.getElement(Env.getCtx(), MInvoiceLine.COLUMNNAME_Line) + " "
							+ Consecutivo.toString() + "-"
							+ Msg.translate(Env.getCtx(), "FillMandatory") + " "
							+ Msg.translate(Env.getCtx(), "Length") + " > 10" + " "
							+ Msg.getElement(Env.getCtx(), "LCO_FE_ProductSchemeListPA_ID") + "-"
							+ PartidaArancelaria);

			}
		}

	} // validateInvoiceLines

	public static String invoiceGenerateXml(MInvoice inv) {
		String msg = null;

		MDocType dt = new MDocType(inv.getCtx(), inv.getC_DocTypeTarget_ID(), inv.get_TrxName());

		X_LCO_FE_DocType fedt = null;
		String dianshortdoctype = null;
		if (dt.get_ValueAsInt("LCO_FE_DocType_ID") > 0) {
			fedt = new X_LCO_FE_DocType (inv.getCtx(), dt.get_ValueAsInt("LCO_FE_DocType_ID"), inv.get_TrxName());
			dianshortdoctype = fedt.getDianShortDocType();
		}

		if (dianshortdoctype == null) {
			msg = Msg.translate(Env.getCtx(), "FillMandatory") + " "
					+ Msg.getElement(Env.getCtx(), X_LCO_FE_DocType.COLUMNNAME_LCO_FE_DocType_ID) + "-"
					+ fedt.getValue() + "-"
					+ Msg.getElement(Env.getCtx(), X_LCO_FE_DocType.COLUMNNAME_DianShortDocType);
			log.info("Invoice: " + inv.toString() + msg);
		}

		MUser user = new MUser(inv.getCtx(), inv.get_ValueAsInt("LCO_FE_UserTo_ID"), inv.get_TrxName());

		if (!validateUserMail(user) && dianshortdoctype != null) {
			msg = Msg.translate(Env.getCtx(), "InvalidUserNameAndEmail") + " "
					+ Msg.getElement(Env.getCtx(), MInvoice.COLUMNNAME_C_Invoice_ID) + "-"
					+ inv.getDocumentNo();
			return msg;
		}

		msg = null;
		LCO_FE_MInvoice lcofeinv = new LCO_FE_MInvoice(inv.getCtx(), inv.getC_Invoice_ID(), inv.get_TrxName());
		if (inv.isSOTrx()) {
			if (   LCO_FE_Utils.TIPO_COMPROBANTE_FACTURA.equals(dianshortdoctype)
				|| LCO_FE_Utils.TIPO_COMPROBANTE_NC.equals(dianshortdoctype)
				|| LCO_FE_Utils.TIPO_COMPROBANTE_ND.equals(dianshortdoctype)
				|| LCO_FE_Utils.TIPO_COMPROBANTE_CONTINGENCIA.equals(dianshortdoctype)
				|| LCO_FE_Utils.TIPO_COMPROBANTE_EXPORTACION.equals(dianshortdoctype)
				)
				msg = lcofeinv.afterCompleteLCOFE();
		} else if (   LCO_FE_Utils.TIPO_COMPROBANTE_SOPORTE.equals(dianshortdoctype)
				)
				msg = lcofeinv.afterCompleteLCOFE();
		else
			log.warning(Msg.translate(Env.getCtx(), "NotAvailable") + " "
					+ Msg.getElement(Env.getCtx(), X_LCO_FE_DocType.COLUMNNAME_LCO_FE_DocType_ID) + "-"
					+ fedt.getValue() + "-"
					+ Msg.getElement(Env.getCtx(), X_LCO_FE_DocType.COLUMNNAME_DianShortDocType));

		return msg;
	}

}	//	LCO_ValidatorEI
