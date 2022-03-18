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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.io.FileUtils;
import org.compiere.model.MAttachment;
import org.compiere.model.MAttachmentEntry;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MMailText;
import org.compiere.model.MOrg;
import org.compiere.model.MTable;
import org.compiere.model.MUser;
import org.compiere.model.MUserMail;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.EMail;
import org.compiere.util.Util;
import org.datacontract.schemas._2004._07.dianresponse.DianResponse;
import org.globalqss.model.MLCOFEAuthorization;
import org.globalqss.model.X_LCO_FE_Authorization;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *	Utils for Localization LCO FE
 *
 *  @author Jesus Garcia - globalqss - Quality Systems & Solutions - http://globalqss.com
 *	@version $Id: LCO_Utils.java,v 1.0 2008/05/26 23:01:26 cruiz Exp $
 */

public class LCO_FE_Utils {
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(LCO_FE_Utils.class);

	public static String TIPO_COMPROBANTE_ND = "ND";
	public static String TIPO_COMPROBANTE_NC = "NC";
	public static String TIPO_COMPROBANTE_SOPORTE = "DS";
	public static String TIPO_COMPROBANTE_AJUSTE_AC = "AC";
	public static String TIPO_COMPROBANTE_FACTURA = "INVOIC";
	public static String TIPO_COMPROBANTE_CONTINGENCIA = "INVCON";
	public static String TIPO_COMPROBANTE_EXPORTACION = "INVEXP";
	public static String DOCUMENTO_SOPORTE_ELECTRONICO = "ds";

	public static String TIPO_PERSONA_JURIDICA = "1";
	public static String TIPO_PERSONA_NATURAL = "2";
	public static String TIPO_GRAN_CONTRIBUYENTE = "3";

	public static String CODIGO_IVA_01 = "01";
	public static String CODIGO_CONSUMO_02 = "02";	// 2.0
	public static String CODIGO_ICA_03 = "03";
	public static String CODIGO_INC_04 = "04";		// 2.1 Imp. Nal. Consumo
	public static String CODIGO_RETFTE_06 = "06";
	public static String CODIGO_NO_CAUSA_ZY = "ZY";	// Excluido

	public static String UBL_VERSION_20 = "UBL 2.0";
	public static String UBL_VERSION_21 = "UBL 2.1";

	public static String TIPO_IDENTIFICACION_EXTRANJERO = "42";

	public static String PRODUCT_SCHEME_PAID = "020";	// Partidas Arancelarias

	public static String EMISION_NORMAL = "1";
	public static String EMISION_CONTINGENCIA = "2";

	public static String AMBIENTE_CERTIFICACION = "2";
	public static String AMBIENTE_PRODUCCION = "1";

	public static String NOMBRE_CERTIFICACION = "PRUEBAS";
	public static String NOMBRE_PRODUCCION = "PRODUCCI\u00d3N";

	public static String FOLDER_COMPROBANTES_GENERADOS = "ComprobantesGenerados";
	public static String FOLDER_COMPROBANTES_FIRMADOS = "ComprobantesFirmados";
	public static String FOLDER_COMPROBANTES_TRANSMITIDOS = "ComprobantesTransmitidos";
	public static String FOLDER_COMPROBANTES_RECHAZADOS = "ComprobantesRechazados";
	public static String FOLDER_COMPROBANTES_AUTORIZADOS = "ComprobantesAutorizados";
	public static String FOLDER_COMPROBANTES_NO_AUTORIZADOS = "ComprobantesNoAutorizados";
	public static String FOLDER_COMPROBANTES_TRAZABILIDAD = "ComprobantesTrazabilidad";

	public static String RESOURCE_XML = "xml";
	public static String RESOURCE_PDF = "pdf";
	public static String RESOURCE_ZIP = "zip";
	public static String RESOURCE_SIGNED_XML = "SIGNED_XML";
	public static String RESOURCE_CUFE_PDF = "PDF";

	public static String SIGNER_POLICY_V1 = "http://www.dian.gov.co/contratos/facturaelectronica/v1";
	public static String SIGNER_POLICY_V2 = "https://facturaelectronica.dian.gov.co/politicadefirma/v2/politicadefirmav2.pdf";
	public static String SIGNER_ELEMENT = "ext:ExtensionContent";
	public static String SIGNER_POLICY_ID = "Política de firma para facturas electrónicas de la República de Colombia.";
	public static String SIGNER_OFE_SUPPLIER = "supplier";
	public static String SIGNER_POLICY_RSASHA256 = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256";

	public static int CURRENCY_COP_ID = 230;
	public static int CONVERSION_TRM_ID = 114;
	public static String COUNTRY_CODE_CO = "CO";
	public static String TIPO_REGIMEN_FISCAL_48 = "48";	// Impuestos sobre la venta del IVA

	public static String MEDIO_PAGO_NO_DEFINIDO = "1";	// Instrumento no definido
	public static String MEDIO_PAGO_DEBITO_CA = "14";	// Débito de ahorros ACH
	public static String METODO_PAGO_CONTADO = "1";
	public static String METODO_PAGO_CREDITO = "2";

	public static String STATUS_CODE_PROCESADO = "00";

	public static String PROVEEDOR_TECNOLOGICO = "DIAN";
	public static String CODIGO_PROVEEDOR_TECNOLOGICO = "000";	// Software Propio
	
	public static String CODIGO_PA_DEFAULT = "0000000000";		// Partida Arancelaria Default

	public static String OLD = "_old";
	public static String SIG = "_signed";

	public static int InvoiceTableID = 318;

	/**	Big Decimal 0.5	 */
	public static final BigDecimal CERO_PUNTO_CINCO = new BigDecimal(0.5);

	public static String SQL_BP_INFO =
			"SELECT fetg.Value AS GrupoRut, fett.Value AS CodigoRut "				// 1, 2
			+ "FROM LCO_FE_BP_Info bpi "
			+ "LEFT JOIN LCO_FE_TributaryType fett ON fett.LCO_FE_TributaryType_ID = bpi.LCO_FE_TributaryType_ID "
			+ "LEFT JOIN LCO_FE_TributaryGroup fetg ON fetg.LCO_FE_TributaryGroup_ID = fett.LCO_FE_TributaryGroup_ID "
			+ "  WHERE fett.IsActive = 'Y' AND C_BPartner_ID=? "
			+ "  AND (fett.Value LIKE 'O-%' OR fett.Value='R-99-PN') "		// TODO Reviewme
			+ "  ORDER BY 1, 2 ";

	public static String SQL_SCHEMA =
			"SELECT MAX(LCO_FE_ProductScheme_ID) "
					+ "FROM LCO_FE_ProductScheme "
					+ "WHERE AD_Client_ID = ? "
					+ "AND IsActive = 'Y' "
					+ "AND IsDefault LIKE ? "
					+ "AND Value LIKE ? ";

	public static String SQL_ITEMS =
			"SELECT il.line AS Consecutivo, CASE WHEN i.IsSOTrx = 'Y' THEN 'false' ELSE 'true' END AS EsGratis "	// 1, 2
			+ ", ilt.qtyinvoiced, um.x12de355 AS UnidadMedida "		// 3, 4
			+ ", ilt.linenetamt AS CostoTotal, cc.Iso_Code, ROUND(ilt.priceactual, cc.StdPrecision) AS PrecioUnitario, cc.Iso_Code "	// 5, 6, 7, 8
			+ ", COALESCE( p.value, p.UPC) AS pidentificador, p.name || p.description AS pdescription "	// 9, 10
			+ ", COALESCE(ilt.name, p.name) || ' ' || COALESCE(ilt.description, '') AS ildescription "	// 11
			+ ", ilt.name "											// 12
			+ ", CASE WHEN i.isdiscountprinted = 'Y'::bpchar AND ilt.pricelist > ilt.priceactual AND ilt.pricelist <> 0::numeric THEN (ilt.pricelist - ilt.priceactual)::numeric ELSE 0::numeric END AS Descuento "	// 13
			+ ", COALESCE(p.value, '') AS pcodigo "					// 14
			+ ", tc.IsWithHolding AS EsRetencion "					// 15
			+ ", COALESCE(tc.LCO_FE_DianTaxCategoryCode, '') AS TipoImpuesto "	// 16
			+ ", CASE tc.LCO_FE_IsBaseAsTax WHEN 'N' THEN ROUND(ilt.linenetamt * t.rate / 100, cc.StdPrecision) ELSE ilt.linenetamt END AS ImporteImpuesto "	// 17
			+ ", cc.Iso_Code AS MonedaImporte "						// 18
			+ ", CASE tc.LCO_FE_IsBaseAsTax WHEN 'N' THEN ilt.linenetamt ELSE 0::numeric END AS BaseImponible "					// 19
			+ ", cc.Iso_Code AS MonedaBase "						// 20
			+ ", COALESCE(t.rate, 0) AS PorcentajeImpuesto "		// 21
			+ ", COALESCE(tc.LCO_FE_DianTaxCategoryName, '') AS NombreImpuesto "	// 22
			+ ", COALESCE(p.ProductType, '') AS TipoProducto "		// 23
			+ ", COALESCE(psl.value, COALESCE(pslch.value, '')) AS CodigoEstandar "	// 24
			+ ", COALESCE(pslpa.value, '') AS PartidaArancelaria "	// 25
			+ ", t.IsTaxExempt AS EsExento "						// 26
			+ "FROM C_Invoice i "
			+ "JOIN AD_Client c ON c.AD_Client_ID = i.AD_Client_ID "
			+ "JOIN C_BPartner bp ON bp.C_BPartner_ID = i.C_BPartner_ID "
			+ "JOIN C_InvoiceLine il ON il.C_Invoice_ID = i.C_Invoice_ID "
			+ "JOIN C_Invoice_LineTax_VT ilt ON ilt.C_InvoiceLine_ID = il.C_InvoiceLine_ID AND ilt.C_Invoice_ID = il.C_Invoice_ID AND ilt.AD_Language = COALESCE(bp.AD_Language, c.AD_Language) "
			+ "JOIN C_Tax t ON t.C_Tax_ID = ilt.C_Tax_ID "
			+ "JOIN C_TaxCategory tc ON tc.C_TaxCategory_ID = t.C_TaxCategory_ID "
			+ "LEFT JOIN M_Product p ON p.M_Product_ID = il.M_Product_ID "
			+ "LEFT JOIN M_Product_Category pc ON pc.M_Product_Category_ID = p.M_Product_Category_ID "
			+ "LEFT JOIN C_Charge ch ON il.C_Charge_ID = ch.C_Charge_ID "
			+ "LEFT JOIN C_UOM um ON il.C_UOM_ID = um.C_UOM_ID "
			+ "LEFT JOIN C_Currency cc ON i.C_Currency_ID = cc.C_Currency_ID "
			+ "LEFT JOIN LCO_FE_ProductSchemeList psl ON p.LCO_FE_ProductSchemeList_ID = psl.LCO_FE_ProductSchemeList_ID "
			+ "LEFT JOIN LCO_FE_ProductSchemeList pslpa ON p.LCO_FE_ProductSchemeListPA_ID = pslpa.LCO_FE_ProductSchemeList_ID "
			+ "LEFT JOIN LCO_FE_ProductSchemeList pslch ON ch.LCO_FE_ProductSchemeList_ID = pslch.LCO_FE_ProductSchemeList_ID "
			+ "WHERE i.C_Invoice_ID=? AND il.IsDescription = 'N' "
			+ "ORDER BY il.Line, il.C_InvoiceLine_ID";

	public static String SQL_TAX_INFO =
			// Line Taxes
			"SELECT tc.IsWithHolding AS EsRetencion "				// 1
			+ ", CASE tc.LCO_FE_IsBaseAsTax WHEN 'N' THEN SUM(ROUND(ilt.linenetamt * t.rate / 100, cc.StdPrecision)) ELSE SUM(ilt.linenetamt) END AS ImporteImpuesto "	// 2
			+ ", MIN(cc.Iso_Code) AS Moneda "						// 3
			+ ", COALESCE(tc.LCO_FE_DianTaxCategoryCode, '') AS TipoImpuesto "		// 4
			+ ", CASE tc.LCO_FE_IsBaseAsTax WHEN 'N' THEN SUM(ilt.linenetamt) ELSE 0::numeric END AS BaseImponible "				// 5
			+ ", t.rate AS PorcentajeImpuesto "						// 6
			+ ", MIN(tc.LCO_FE_DianTaxCategoryName) AS NombreImpuesto "				// 7
			+ ", t.IsTaxExempt AS EsExento "						// 8
			+ "FROM C_Invoice i "
			+ "JOIN AD_Client c ON c.AD_Client_ID = i.AD_Client_ID "
			+ "JOIN C_BPartner bp ON bp.C_BPartner_ID = i.C_BPartner_ID "
			+ "JOIN C_InvoiceLine il ON il.C_Invoice_ID = i.C_Invoice_ID "
			+ "JOIN C_Invoice_LineTax_VT ilt ON ilt.C_InvoiceLine_ID = il.C_InvoiceLine_ID AND ilt.C_Invoice_ID = il.C_Invoice_ID AND ilt.AD_Language = COALESCE(bp.AD_Language, c.AD_Language) "
			+ "JOIN C_Tax t ON t.C_Tax_ID = ilt.C_Tax_ID "
			+ "JOIN C_TaxCategory tc ON tc.C_TaxCategory_ID = t.C_TaxCategory_ID "
			+ "LEFT JOIN C_Currency cc ON i.C_Currency_ID = cc.C_Currency_ID "
			+ "WHERE i.C_Invoice_ID = ? AND il.IsDescription = 'N' "	// AND t.IsTaxExempt = 'N'
			+ "AND tc.LCO_FE_DianTaxCategoryCode LIKE ? "
			+ "GROUP BY IsWithHolding, TipoImpuesto, EsExento, PorcentajeImpuesto, LCO_FE_IsBaseAsTax "
			// + "HAVING SUM(ROUND(ilt.linenetamt * t.rate / 100, cc.StdPrecision)) > 0 "
			// Invoice Taxes
			+ " UNION "
			+ "SELECT tc.IsWithHolding AS EsRetencion "				// 1
			+ ", SUM(ROUND(iw.taxamt, cc.StdPrecision)) AS ImporteImpuesto "		// 2
			+ ", MIN(cc.Iso_Code) AS Moneda "						// 3
			+ ", COALESCE(tc.LCO_FE_DianTaxCategoryCode, '') AS TipoImpuesto "		// 4
			+ ", CASE tc.LCO_FE_IsBaseAsTax WHEN 'N' THEN SUM(iw.taxbaseamt) ELSE 0::numeric END AS BaseImponible "				// 5
			+ ", t.rate AS PorcentajeImpuesto "						// 6
			+ ", MIN(tc.LCO_FE_DianTaxCategoryName) AS NombreImpuesto "				// 7
			+ ", t.IsTaxExempt AS EsExento "						// 8
			+ "FROM C_Invoice i "
			+ "JOIN AD_Client c ON c.AD_Client_ID = i.AD_Client_ID "
			+ "JOIN LCO_InvoiceWithholding iw ON iw.C_Invoice_ID = i.C_Invoice_ID "
			+ "JOIN C_Tax t ON t.C_Tax_ID = iw.C_Tax_ID "
			+ "JOIN C_TaxCategory tc ON tc.C_TaxCategory_ID = t.C_TaxCategory_ID "
			+ "LEFT JOIN C_Currency cc ON i.C_Currency_ID = cc.C_Currency_ID "
			+ "WHERE i.C_Invoice_ID = ? "	// AND il.IsDescription = 'N' AND t.IsTaxExempt = 'N'
			+ "AND tc.LCO_FE_DianTaxCategoryCode LIKE ? "
			+ "GROUP BY IsWithHolding, TipoImpuesto, EsExento, PorcentajeImpuesto, LCO_FE_IsBaseAsTax "
			// + "HAVING SUM(ROUND(it.taxamt, cc.StdPrecision)) > 0 "
			+ "ORDER BY EsRetencion, TipoImpuesto, PorcentajeImpuesto";

	public static String SQL_TAX_GROUP =
			// Line Taxes
			"SELECT tc.IsWithHolding AS EsRetencion "				// 1
			+ ", CASE tc.LCO_FE_IsBaseAsTax WHEN 'N' THEN SUM(ROUND(ilt.linenetamt * t.rate / 100, cc.StdPrecision)) ELSE SUM(ilt.linenetamt) END AS ImporteImpuesto "	// 2
			+ ", MIN(cc.Iso_Code) AS Moneda "						// 3
			+ ", COALESCE(tc.LCO_FE_DianTaxCategoryCode, '') AS TipoImpuesto "		// 4
			+ ", CASE tc.LCO_FE_IsBaseAsTax WHEN 'N' THEN SUM(ilt.linenetamt) ELSE 0::numeric END AS BaseImponible "				// 5
			+ ", MIN(tc.LCO_FE_DianTaxCategoryName) AS NombreImpuesto "				// 6
			+ "FROM C_Invoice i "
			+ "JOIN AD_Client c ON c.AD_Client_ID = i.AD_Client_ID "
			+ "JOIN C_BPartner bp ON bp.C_BPartner_ID = i.C_BPartner_ID "
			+ "JOIN C_InvoiceLine il ON il.C_Invoice_ID = i.C_Invoice_ID "
			+ "JOIN C_Invoice_LineTax_VT ilt ON ilt.C_InvoiceLine_ID = il.C_InvoiceLine_ID AND ilt.C_Invoice_ID = il.C_Invoice_ID AND ilt.AD_Language = COALESCE(bp.AD_Language, c.AD_Language) "
			+ "JOIN C_Tax t ON t.C_Tax_ID = ilt.C_Tax_ID "
			+ "JOIN C_TaxCategory tc ON tc.C_TaxCategory_ID = t.C_TaxCategory_ID "
			+ "LEFT JOIN C_Currency cc ON i.C_Currency_ID = cc.C_Currency_ID "
			+ "WHERE i.C_Invoice_ID = ? AND il.IsDescription = 'N' "	// AND t.IsTaxExempt = 'N'
			+ "GROUP BY IsWithHolding, TipoImpuesto, LCO_FE_IsBaseAsTax "
			// + "HAVING SUM(ROUND(ilt.linenetamt * t.rate / 100, cc.StdPrecision)) > 0 "
			// Invoice Taxes
			+ " UNION "
			+ "SELECT tc.IsWithHolding AS EsRetencion "				// 1
			+ ", SUM(ROUND(iw.taxamt, cc.StdPrecision)) AS ImporteImpuesto "		// 2
			+ ", MIN(cc.Iso_Code) AS Moneda "						// 3
			+ ", COALESCE(tc.LCO_FE_DianTaxCategoryCode, '') AS TipoImpuesto "		// 4
			+ ", CASE tc.LCO_FE_IsBaseAsTax WHEN 'N' THEN SUM(iw.taxbaseamt) ELSE 0::numeric END AS BaseImponible "				// 5
			+ ", MIN(tc.LCO_FE_DianTaxCategoryName) AS NombreImpuesto "				// 6
			+ "FROM C_Invoice i "
			+ "JOIN AD_Client c ON c.AD_Client_ID = i.AD_Client_ID "
			+ "JOIN LCO_InvoiceWithholding iw ON iw.C_Invoice_ID = i.C_Invoice_ID "
			+ "JOIN C_Tax t ON t.C_Tax_ID = iw.C_Tax_ID "
			+ "JOIN C_TaxCategory tc ON tc.C_TaxCategory_ID = t.C_TaxCategory_ID "
			+ "LEFT JOIN C_Currency cc ON i.C_Currency_ID = cc.C_Currency_ID "
			+ "WHERE i.C_Invoice_ID = ? "	// AND il.IsDescription = 'N' "	AND t.IsTaxExempt = 'N'
			+ "GROUP BY IsWithHolding, TipoImpuesto, LCO_FE_IsBaseAsTax "
			// + "HAVING SUM(ROUND(iw.taxamt, cc.StdPrecision)) > 0 "
			+ "ORDER BY EsRetencion, TipoImpuesto";

	/**
	 * 	boolean notValidLength
	 *  min = 0, bypass
	 *  max = 0, bypass
	 * 	@return boolean
	 */
	public static boolean notValidLength(String str, int min, int max) {
		if (min > 0 && str.trim().length() < min)
			return true;
		if (max > 0 && str.trim().length() > max)
			return true;
		return false;
	}

	/**
	 * 	boolean notValidNumbrer
	 * 	@return boolean 
	 */
	public static boolean notValidNumber(String strNum) {
		try {
			Long.parseLong(strNum);
		} catch (NumberFormatException e) {
			return true;
		}
		return false;
	}

	/**
	 * 	int getRefInvoiceID
	 *  @param invoice Invoice
	 * 	@return int ReferencedInvoice C_Invoice_ID
	 */
	public static int getRefInvoiceID(MInvoice invoice) {
		String sql = ""
				+ "SELECT COALESCE(i.C_Invoice_ID, -1) "
				+ "FROM   C_Invoice i "
				+ "WHERE  i.AD_Client_ID = ? "
				+ "       AND i.C_BPartner_ID = ? "
				+ "       AND i.DocumentNo = ?";
		int ref_invoice_id = DB.getSQLValue(invoice.get_TrxName(), sql,
				invoice.getAD_Client_ID(),
				invoice.getC_BPartner_ID(),
				invoice.getPOReference());
		return ref_invoice_id;
	}

	/**
	 * 	int getOfeContingenciaID
	 *  @param invoice Invoice
	 * 	@return int LCO_FE_OFE_Contingency_ID
	 */
	public static int getOfeContingenciaID(MInvoice invoice) {
		String sql = ""
				+ "SELECT MAX(LCO_FE_OFE_Contingency_ID) "
				+ "FROM   LCO_FE_OFE_Contingency "
				+ "WHERE  AD_Client_ID = ? "
				+ "       AND AD_Org_ID = ? "
				+ "       AND IsActive = 'Y' "
				+ "       AND IsEnable = 'Y' "
				+ "       AND ? >= StartDate "
				+ "       AND ( ? <= EndDate OR EndDate IS NULL )";
		int lco_fe_contingency_id = DB.getSQLValueEx(invoice.get_TrxName(), sql,
				invoice.getAD_Client_ID(),
				invoice.getAD_Org_ID(),
				(Timestamp) invoice.get_Value("LCO_FE_DateTrx"),
				(Timestamp) invoice.get_Value("LCO_FE_DateTrx"));
		return lco_fe_contingency_id;
	}

	/**
	 * 	String getLcoFeDianFormat
	 *  @param invoice Invoice
	 *  @param lco_fe_doctype_id
	 * 	@return int LCO_FE_DIAN_Format_ID
	 */
	public static int getLcoFeDianFormatID(MInvoice invoice, int lco_fe_doctype_id) {
		String sql = ""
				+ "SELECT MAX(LCO_FE_DIAN_Format_ID) "
				+ "FROM   LCO_FE_DIAN_Format fedf "
				+ "       JOIN LCO_FE_DocType fedt "
				+ "         ON fedt.LCO_FE_DocType_ID = fedf.LCO_FE_DocType_ID "
				+ "WHERE  fedf.AD_Client_ID = ? "
				+ "       AND fedf.IsActive = 'Y' "
				+ "       AND fedt.LCO_FE_DocType_ID = ? "
				+ "       AND ? >= fedf.ValidFrom "
				+ "       AND ( ? <= fedf.ValidTo OR fedf.ValidTo IS NULL )";
		int lco_fe_dian_format_id = DB.getSQLValueEx(invoice.get_TrxName(), sql,
				invoice.getAD_Client_ID(),
				lco_fe_doctype_id,
				invoice.getDateInvoiced(),
				invoice.getDateInvoiced());
		return lco_fe_dian_format_id;
	}

	/**
	 * 	int getDianResolutionID
	 * 	@return int LCO_PrintedFormControl_ID
	 */
	public static int getDianResolutionID(MInvoice invoice, boolean isUseContingency) {
		String sql = ""
				+ "SELECT MAX(LCO_PrintedFormControl_ID) "
				+ "FROM   LCO_PrintedFormControl "
				+ "WHERE  AD_Client_ID = ? "
				+ "       AND AD_Org_ID = ? "
				+ "       AND COALESCE(C_BPartner_ID,0) = 0 "
				+ "       AND C_DocTypeTarget_ID = ? "
				+ "       AND IsActive = 'Y' "
				+ "       AND IsEnable = 'Y' "
				+ "       AND ? >= ValidFrom "
				+ "       AND ( ? <= ValidUntil OR ValidUntil IS NULL ) "
				+ "       AND IsContingency = ?";
		int lco_printedformcontrol_id = DB.getSQLValueEx(invoice.get_TrxName(), sql,
				invoice.getAD_Client_ID(),
				invoice.getAD_Org_ID(),
				invoice.getC_DocTypeTarget_ID(),
				invoice.getDateInvoiced(),
				invoice.getDateInvoiced(),
				isUseContingency ? "Y" : "N");	// Y-Pruebas, N-Producción
		return lco_printedformcontrol_id;
	}

	/**
	 * Get Doc or Current Timestamp in format - default yyyy/MM/dd
	 * @return Date
	 */
	public static String getDateTime(Timestamp docTimestamp, int format) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

		if (docTimestamp == null) docTimestamp = now();

		if (format == 7)
			dateFormat = new SimpleDateFormat("MM/yyyy");
		else if (format == 8)
			dateFormat = new SimpleDateFormat("ddMMyyyy");
		else if (format == 9)
			dateFormat = new SimpleDateFormat("yyyyMMdd");
		else if (format == 10)
			dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		else if (format == 11)
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		else if (format == 12)
			dateFormat = new SimpleDateFormat("HH:mm:ss");
		else if (format == 13)
			dateFormat = new SimpleDateFormat("HHmmss");
		else if (format == 14)
			dateFormat = new SimpleDateFormat("HH:mm:ssXXX");
		else if (format == 15)
			dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		else if (format == 16)
			dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		else if (format == 17)
			dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"); // .SSSZ
		else if (format == 18)
			dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		else if (format == 19)
			dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");	// YYYYmmddHHMMss

		// fix FAD10, Notificacion: Debe ser informada la  hora en una zona horaria -5, que es la zona horaria oficial de Colombia.
		dateFormat.setTimeZone(TimeZone.getTimeZone("America/Bogota"));

		return dateFormat.format(docTimestamp);
	}

	/**
	 * Get the now Timestamp
	 * 
	 * @return Timestamp now value
	 */
	public static Timestamp now() {
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	public static String decimalFormat(BigDecimal bd) {
		return String.format(Locale.US, "%.2f", bd);
	}

	/**
	 * 	void atachFile
	 * 	@return void
	 */
	public static void attachFile(Properties ctx, String trxName, int sri_authorization_id, String file_name, String resourcetype) {

		try {
			int  ad_table_id = MTable.getTable_ID(MLCOFEAuthorization.Table_Name);
			// if one attach is found , it means that a resourcetype file was attached before
			MAttachment attach =  MAttachment.get(ctx, ad_table_id, sri_authorization_id, trxName);
			// No se encontro archivo previo
			if (attach == null ) {
				attach = new  MAttachment(ctx, ad_table_id , sri_authorization_id, trxName);
				attach.addEntry(new File (file_name));
				attach.saveEx();

			} else {
				// Se encontro un archivo adjunto previamente
				// busca el index del anterior archivo y lo renombra
				int i = 0;
				String filename = null;
				String fileext = null;
				for (i=0; i < attach.getEntryCount();  i++) {
					MAttachmentEntry entry = attach.getEntry(i);
					if (file_name.equals(entry.getName())) { // tratando de anexar archivo con el mismo nombre, renombrar el anterior
						filename = entry.getName().substring(0,entry.getName().length()-4);
						fileext = entry.getName().substring(entry.getName().lastIndexOf(".")+1);
						if (!entry.getName().contains("_old")
								&& entry.getName().endsWith(resourcetype) && entry.getName().contains(filename)) {
							String renamed = filename + "_old_" + LCO_FE_Utils.getDate(null, 15) + "." + fileext;
							entry.setName(renamed);
							attach.saveEx();
						}
					}
				} 
				//agrega el nuevo archivo ya que el anterior ha sido renombrado
				attach.addEntry(new File (file_name));
				attach.saveEx();
			}

			// MAttachment
			/*
		  Si IsAttachXML
	      Anexar el archivo XML al XML Header (probar con Archivador
		  a ver si funciona, si no con Attachment)
	      NOTA: \u00bfQue hacer si ya hay un archivo previo generado?
		  Verificar si el archivador es read-only, y si podria renombrar
		  un archivo previo generado para a\u00f1adirle un sufijo _old_yyyymmdd
			 */

		} catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	public static void attachFileToOrg(MOrg org, String file_name, String resourcetype) {
		try {
			int  ad_table_id = org.get_Table_ID();
			MAttachment attach =  MAttachment.get(org.getCtx(), ad_table_id, org.getAD_Org_ID(), org.get_TrxName());
			if (attach == null )
				attach = new  MAttachment(org.getCtx(), ad_table_id , org.getAD_Org_ID(), org.get_TrxName());
			attach.addEntry(new File (file_name));
			attach.saveEx();
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	/**
	 * Get Doc or Current date in format - default yyyy/MM/dd
	 * @return Date
	 */
	public static String getDate(Date docDate, int format) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

		if (docDate == null) docDate = new Date();

		if (format == 7)
			dateFormat = new SimpleDateFormat("MM/yyyy");
		else if (format == 8)
			dateFormat = new SimpleDateFormat("ddMMyyyy");
		else if (format == 9)
			dateFormat = new SimpleDateFormat("yyyyMMdd");
		else if (format == 10)
			dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		else if (format == 11)
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		else if (format == 12)
			dateFormat = new SimpleDateFormat("HH:mm:ss");
		else if (format == 15)
			dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		else if (format == 16)
			dateFormat = new SimpleDateFormat("yy");

		return dateFormat.format(docDate);
	}

	/**
	 * 	String cutString length
	 * 	@return string
	 */
	public static String cutString(String str, int length) {
		if (length < str.length())
			return new String(str.substring(0, length).trim());

		return new String(str.trim());
	}

	public static String setErrorMsgFromDianResponse(MLCOFEAuthorization auth, DianResponse response) {
		Boolean isValid = response.isIsValid();
		String statusCode = response.getStatusCode().getValue();
		String statusDescription = response.getStatusDescription().getValue();
		String statusMessage = response.getStatusMessage().getValue();
		List<String> errors = null;
		try {
			errors = response.getErrorMessage().getValue().getString();
		} catch (Exception e) {
			log.warning(statusDescription + auth.toString());
		}
		
		StringBuilder errorMsg = new StringBuilder();
		errorMsg.append(isValid).append(" ").append(statusCode).append(" ").append(statusDescription).append("\n").append(statusMessage);
		if (errors != null)
			for (String error : errors) {
				errorMsg.append("\n").append(error);
			}
		
		String validationDate = null;
		byte[] bytes = response.getXmlBase64Bytes().getValue();
		if (bytes != null) {
			Document document = DIAN21_FE_UtilsSign.getDocumentFromXml64Bytes(bytes);
			try {
				validationDate = evaluateXPath(document, "//*[local-name()='SigningTime']/text()").get(0);
			} catch (Exception e) {
				log.warning("Could not find SigningTime in the ApplicationResponse for " + auth.toString());
			}
		}

		auth.setErrorMsg(errorMsg.toString());
		auth.setLCO_FE_IdErrorCode(new BigDecimal(statusCode));
		if (STATUS_CODE_PROCESADO.equals(statusCode) && isValid) {
			if (auth.getLCO_FE_DateAuthorization() == null && validationDate != null) {
				auth.setLCO_FE_DateAuthorization(validationDate);	// First Time
			}
			auth.setProcessed(true);
		}
		auth.saveEx();
		return statusDescription + (statusMessage == null ? "" : " - " + statusMessage);
	}

	/**
	 * Fix TimeZone DIAN
	 * @param
	 * @return Timestamp
	 */
	public static String fixTimeZone(String strdatetime) {

		Timestamp ts = null;
		String bogotaDatetime = null;

		try {
			if (strdatetime != null) {
				DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSX");
				ZonedDateTime zdt;
				try {
					zdt = ZonedDateTime.parse(strdatetime, dateFormat);
				} catch (DateTimeParseException e) {
					dateFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSXXX");
				}
				zdt = ZonedDateTime.parse(strdatetime, dateFormat);
				GregorianCalendar calDate = GregorianCalendar.from(zdt);
				calDate.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
				ts = new Timestamp(calDate.getTimeInMillis());
				DateFormat bogotaDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
				bogotaDateFormat.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
				bogotaDatetime = bogotaDateFormat.format(ts);

			}
		} catch (Exception e) {
			return null;
		}

		return bogotaDatetime;
	}

	private static List<String> evaluateXPath(Document document, String xpathExpression) throws Exception {
		// Create XPathFactory object
		XPathFactory xpathFactory = XPathFactory.newInstance();
		// Create XPath object
		XPath xpath = xpathFactory.newXPath();
		List<String> values = new ArrayList<>();
		// Create XPathExpression object
		XPathExpression expr = xpath.compile(xpathExpression);
		// Evaluate expression result on XML document
		NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
		for (int i = 0; i < nodes.getLength(); i++) {
			values.add(nodes.item(i).getNodeValue());
		}
		return values;
	}

	/**
	 * 	String getMailText
	 * 	@return int
	 */
	public static int getMailText(String name, int ad_client_id, int ad_org_id) {

		Object[] args = new Object[] { name, ad_client_id, ad_org_id };
		int r_mailtext_id = DB.getSQLValueEx(null, "SELECT MAX(R_MailText_ID) FROM R_MailText WHERE Name = ? AND AD_Client_ID = ? AND AD_Org_ID = COALESCE (0, ?) AND IsActive = 'Y' ", args);

		return  r_mailtext_id;

	}

	/**
	 * EMail user
	 * int notifyUsers
	 * @param
	 * @return how many email were sent
	 */
	public static int notifyUsers(Properties ctx, MMailText mText, String replyTo, int userFromInvoiceId, int to_user_id, int cc_user_id, String subject, String message, List<File> attachments)
	{
		int countMail = 0;

		if (to_user_id <= 0) {
			return countMail;
		}

		MClient client = MClient.get(ctx);
		MUser userFromInvoice = null;
		MUser toUser = null;
		MUser ccUser = null;
		if (userFromInvoiceId > 0)
			userFromInvoice = MUser.get(ctx, userFromInvoiceId);
		if (to_user_id > 0)
			toUser = MUser.get(ctx, to_user_id);
		if (cc_user_id > 0)
			ccUser = MUser.get(ctx, cc_user_id);

		String msg = null;
		MUser from = MUser.get(ctx);
		EMail email = client.createEMail(from, toUser, subject, message);

		if (email != null && email.isValid()) {
			for (File att : attachments) {
				email.addAttachment(att);
			}
			if (!Util.isEmpty(replyTo, true)) {
				email.setReplyTo(replyTo);
			}
			if (cc_user_id > 0 && !Util.isEmpty(ccUser.getEMail())) {
				email.addCc(ccUser.getEMail());
			}
			if (userFromInvoiceId > 0 && userFromInvoiceId != cc_user_id && !Util.isEmpty(userFromInvoice.getEMail())) {
				email.addCc(userFromInvoice.getEMail());
			}

			msg = email.send();

			MUserMail um = new MUserMail(mText, from.getAD_User_ID(), email);
			um.setSubject(subject);
			um.setMailText(message);
			um.saveEx();

			if (msg.equals(EMail.SENT_OK))
			{
				countMail++;
			}

		}

		return countMail;
	}

	/**
	 * 	void setErrorMsgFromFile_PHP
	 *  @param invoice Invoice
	 * 	@return void
	 */
	public static void setErrorMsgFromFile_PHP(X_LCO_FE_Authorization auth, String file) {
		try {
			Document document = DIAN21_FE_UtilsSign.getDocument(file);
	        String validationDate = evaluateXPath(document, "//*[local-name()='Created']/text()").get(0);
	        String isValid = evaluateXPath(document, "//*[local-name()='IsValid']/text()").get(0);
	        String statusCode = evaluateXPath(document, "//*[local-name()='StatusCode']/text()").get(0);
	        String statusDescription = evaluateXPath(document, "//*[local-name()='StatusDescription']/text()").get(0);
	        String statusMessage = "";
	        try {
	        	statusMessage = evaluateXPath(document, "//*[local-name()='StatusMessage']/text()").get(0);
	        } catch (IndexOutOfBoundsException e) {}
	        List<String >errors = evaluateXPath(document, "//*[local-name()='ErrorMessage']/*/text()");
	        StringBuilder errorMsg = new StringBuilder();
	        errorMsg.append(isValid).append(" ").append(statusCode).append(" ").append(statusDescription).append("\n").append(statusMessage);
	        for (String error : errors) {
	        	errorMsg.append("\n").append(error);
	        }
	        auth.setErrorMsg(errorMsg.toString());
	        auth.setLCO_FE_IdErrorCode(new BigDecimal(statusCode));
        	String output_Directory = file.substring(0, file.lastIndexOf(File.separator));
        	String file_response = null;
	        if (STATUS_CODE_PROCESADO.equals(statusCode) && "true".equals(isValid)) {
	        	if (auth.getLCO_FE_DateAuthorization() == null) {
	        		auth.setLCO_FE_DateAuthorization(fixTimeZone(validationDate).toString());	// First Time
	        	} 
	        	auth.setProcessed(true);
	        	auth.saveEx();
	        	output_Directory = output_Directory.replace(LCO_FE_Utils.FOLDER_COMPROBANTES_TRANSMITIDOS, LCO_FE_Utils.FOLDER_COMPROBANTES_AUTORIZADOS);
	        } else {
	        	output_Directory = output_Directory.replace(LCO_FE_Utils.FOLDER_COMPROBANTES_TRANSMITIDOS, LCO_FE_Utils.FOLDER_COMPROBANTES_RECHAZADOS);
	        }
	        (new File(output_Directory)).mkdirs();
	        file_response = output_Directory + File.separator + file.substring(file.lastIndexOf(File.separator) + 1, file.lastIndexOf(File.separator) + 25) + "_response" + "." + LCO_FE_Utils.RESOURCE_XML;
	        file_response = file_response.replace("ws_", "face_");
	        FileUtils.copyFile(new File(file), new File(file_response));
	        if (auth.isProcessed())	// TODO Reviewme
	        	LCO_FE_Utils.attachFile(auth.getCtx(), auth.get_TrxName(), auth.getLCO_FE_Authorization_ID(), file_response, LCO_FE_Utils.RESOURCE_XML);
	        auth.saveEx();
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	public static String runCommand(String[] command) {
		StringBuilder msg = new StringBuilder();
		try {
			String s;
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			// read the output from the command
			while ((s = stdInput.readLine()) != null) {
				msg.append(s);
			}
			// read any errors from the attempted command
			while ((s = stdError.readLine()) != null) {
				msg.append(s);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			msg.append(e.getLocalizedMessage());
		}
		return msg.toString();
	}

}	// LCO_FE_Utils
