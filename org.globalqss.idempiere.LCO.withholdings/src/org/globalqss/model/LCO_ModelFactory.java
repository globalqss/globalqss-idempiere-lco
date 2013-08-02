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

import java.sql.ResultSet;

import org.adempiere.base.IModelFactory;
import org.compiere.model.PO;
import org.compiere.util.Env;

public class LCO_ModelFactory implements IModelFactory {

	@Override
	public Class<?> getClass(String tableName) {
		if (tableName.equals("LCO_ConversionCode"))
			return X_LCO_ConversionCode.class;
		if (tableName.equals("LCO_Conversion"))
			return MLCOConversion.class;
		if (tableName.equals("LCO_DIAN_ConceptFormat"))
			return X_LCO_DIAN_ConceptFormat.class;
		if (tableName.equals("LCO_DIAN_Concept"))
			return MLCODIANConcept.class;
		if (tableName.equals("LCO_DIAN_ConceptSource"))
			return X_LCO_DIAN_ConceptSource.class;
		if (tableName.equals("LCO_DIAN_FieldFormat"))
			return X_LCO_DIAN_FieldFormat.class;
		if (tableName.equals("LCO_DIAN_Format"))
			return MLCODIANFormat.class;
		if (tableName.equals("LCO_DIAN_MediaFile"))
			return X_LCO_DIAN_MediaFile.class;
		if (tableName.equals("LCO_DIAN_SendSchedule"))
			return X_LCO_DIAN_SendSchedule.class;
		if (tableName.equals("LCO_DIAN_SendScheduleLine"))
			return X_LCO_DIAN_SendScheduleLine.class;
		if (tableName.equals("LCO_DIAN_XML_Header"))
			return X_LCO_DIAN_XML_Header.class;
		if (tableName.equals("LCO_DIAN_XMLPrintLabel"))
			return X_LCO_DIAN_XMLPrintLabel.class;
		if (tableName.equals("LCO_InvoiceWithholding"))
			return MLCOInvoiceWithholding.class;
		if (tableName.equals("LCO_ISIC"))
			return X_LCO_ISIC.class;
		if (tableName.equals("LCO_TaxIdType"))
			return X_LCO_TaxIdType.class;
		if (tableName.equals("LCO_TaxPayerType"))
			return X_LCO_TaxPayerType.class;
		if (tableName.equals("LCO_WithholdingCalc"))
			return X_LCO_WithholdingCalc.class;
		if (tableName.equals("LCO_WithholdingCategory"))
			return X_LCO_WithholdingCategory.class;
		if (tableName.equals("LCO_WithholdingRuleConf"))
			return X_LCO_WithholdingRuleConf.class;
		if (tableName.equals("LCO_WithholdingRule"))
			return X_LCO_WithholdingRule.class;
		if (tableName.equals("LCO_WithholdingType"))
			return X_LCO_WithholdingType.class;
		return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		if (tableName.equals("LCO_ConversionCode"))
			return new X_LCO_ConversionCode(Env.getCtx(), Record_ID, trxName);
		if (tableName.equals("LCO_Conversion"))
			return new MLCOConversion(Env.getCtx(), Record_ID, trxName);
		if (tableName.equals("LCO_DIAN_ConceptFormat"))
			return new X_LCO_DIAN_ConceptFormat(Env.getCtx(), Record_ID, trxName);
		if (tableName.equals("LCO_DIAN_Concept"))
			return new MLCODIANConcept(Env.getCtx(), Record_ID, trxName);
		if (tableName.equals("LCO_DIAN_ConceptSource"))
			return new X_LCO_DIAN_ConceptSource(Env.getCtx(), Record_ID, trxName);
		if (tableName.equals("LCO_DIAN_FieldFormat"))
			return new X_LCO_DIAN_FieldFormat(Env.getCtx(), Record_ID, trxName);
		if (tableName.equals("LCO_DIAN_Format"))
			return new MLCODIANFormat(Env.getCtx(), Record_ID, trxName);
		if (tableName.equals("LCO_DIAN_MediaFile"))
			return new X_LCO_DIAN_MediaFile(Env.getCtx(), Record_ID, trxName);
		if (tableName.equals("LCO_DIAN_SendSchedule"))
			return new X_LCO_DIAN_SendSchedule(Env.getCtx(), Record_ID, trxName);
		if (tableName.equals("LCO_DIAN_SendScheduleLine"))
			return new X_LCO_DIAN_SendScheduleLine(Env.getCtx(), Record_ID, trxName);
		if (tableName.equals("LCO_DIAN_XML_Header"))
			return new X_LCO_DIAN_XML_Header(Env.getCtx(), Record_ID, trxName);
		if (tableName.equals("LCO_DIAN_XMLPrintLabel"))
			return new X_LCO_DIAN_XMLPrintLabel(Env.getCtx(), Record_ID, trxName);
		if (tableName.equals("LCO_InvoiceWithholding"))
			return new MLCOInvoiceWithholding(Env.getCtx(), Record_ID, trxName);
		if (tableName.equals("LCO_ISIC"))
			return new X_LCO_ISIC(Env.getCtx(), Record_ID, trxName);
		if (tableName.equals("LCO_TaxIdType"))
			return new X_LCO_TaxIdType(Env.getCtx(), Record_ID, trxName);
		if (tableName.equals("LCO_TaxPayerType"))
			return new X_LCO_TaxPayerType(Env.getCtx(), Record_ID, trxName);
		if (tableName.equals("LCO_WithholdingCalc"))
			return new X_LCO_WithholdingCalc(Env.getCtx(), Record_ID, trxName);
		if (tableName.equals("LCO_WithholdingCategory"))
			return new X_LCO_WithholdingCategory(Env.getCtx(), Record_ID, trxName);
		if (tableName.equals("LCO_WithholdingRuleConf"))
			return new X_LCO_WithholdingRuleConf(Env.getCtx(), Record_ID, trxName);
		if (tableName.equals("LCO_WithholdingRule"))
			return new X_LCO_WithholdingRule(Env.getCtx(), Record_ID, trxName);
		if (tableName.equals("LCO_WithholdingType"))
			return new X_LCO_WithholdingType(Env.getCtx(), Record_ID, trxName);
		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		if (tableName.equals("LCO_ConversionCode"))
			return new X_LCO_ConversionCode(Env.getCtx(), rs, trxName);
		if (tableName.equals("LCO_Conversion"))
			return new MLCOConversion(Env.getCtx(), rs, trxName);
		if (tableName.equals("LCO_DIAN_ConceptFormat"))
			return new X_LCO_DIAN_ConceptFormat(Env.getCtx(), rs, trxName);
		if (tableName.equals("LCO_DIAN_Concept"))
			return new MLCODIANConcept(Env.getCtx(), rs, trxName);
		if (tableName.equals("LCO_DIAN_ConceptSource"))
			return new X_LCO_DIAN_ConceptSource(Env.getCtx(), rs, trxName);
		if (tableName.equals("LCO_DIAN_FieldFormat"))
			return new X_LCO_DIAN_FieldFormat(Env.getCtx(), rs, trxName);
		if (tableName.equals("LCO_DIAN_Format"))
			return new MLCODIANFormat(Env.getCtx(), rs, trxName);
		if (tableName.equals("LCO_DIAN_MediaFile"))
			return new X_LCO_DIAN_MediaFile(Env.getCtx(), rs, trxName);
		if (tableName.equals("LCO_DIAN_SendSchedule"))
			return new X_LCO_DIAN_SendSchedule(Env.getCtx(), rs, trxName);
		if (tableName.equals("LCO_DIAN_SendScheduleLine"))
			return new X_LCO_DIAN_SendScheduleLine(Env.getCtx(), rs, trxName);
		if (tableName.equals("LCO_DIAN_XML_Header"))
			return new X_LCO_DIAN_XML_Header(Env.getCtx(), rs, trxName);
		if (tableName.equals("LCO_DIAN_XMLPrintLabel"))
			return new X_LCO_DIAN_XMLPrintLabel(Env.getCtx(), rs, trxName);
		if (tableName.equals("LCO_InvoiceWithholding"))
			return new MLCOInvoiceWithholding(Env.getCtx(), rs, trxName);
		if (tableName.equals("LCO_ISIC"))
			return new X_LCO_ISIC(Env.getCtx(), rs, trxName);
		if (tableName.equals("LCO_TaxIdType"))
			return new X_LCO_TaxIdType(Env.getCtx(), rs, trxName);
		if (tableName.equals("LCO_TaxPayerType"))
			return new X_LCO_TaxPayerType(Env.getCtx(), rs, trxName);
		if (tableName.equals("LCO_WithholdingCalc"))
			return new X_LCO_WithholdingCalc(Env.getCtx(), rs, trxName);
		if (tableName.equals("LCO_WithholdingCategory"))
			return new X_LCO_WithholdingCategory(Env.getCtx(), rs, trxName);
		if (tableName.equals("LCO_WithholdingRuleConf"))
			return new X_LCO_WithholdingRuleConf(Env.getCtx(), rs, trxName);
		if (tableName.equals("LCO_WithholdingRule"))
			return new X_LCO_WithholdingRule(Env.getCtx(), rs, trxName);
		if (tableName.equals("LCO_WithholdingType"))
			return new X_LCO_WithholdingType(Env.getCtx(), rs, trxName);
		return null;
	}

}
