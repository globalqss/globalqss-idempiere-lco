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

public class LCO_ModelFactoryEI implements IModelFactory {

	@Override
	public Class<?> getClass(String tableName) {
		if (MLCOFEAuthorization.Table_Name.equals(tableName))
			return MLCOFEAuthorization.class;
		if (X_LCO_FE_BP_Info.Table_Name.equals(tableName))
			return X_LCO_FE_BP_Info.class;
		if (X_LCO_FE_ContactType.Table_Name.equals(tableName))
			return X_LCO_FE_ContactType.class;
		if (X_LCO_FE_DIAN_Format.Table_Name.equals(tableName))
			return X_LCO_FE_DIAN_Format.class;
		if (X_LCO_FE_DocType.Table_Name.equals(tableName))
			return X_LCO_FE_DocType.class;
		if (X_LCO_FE_NCConcept.Table_Name.equals(tableName))
			return X_LCO_FE_NCConcept.class;
		if (X_LCO_FE_NDConcept.Table_Name.equals(tableName))
			return X_LCO_FE_NDConcept.class;
		if (X_LCO_FE_OFE_Contingency.Table_Name.equals(tableName))
			return X_LCO_FE_OFE_Contingency.class;
		if (X_LCO_FE_OperationType.Table_Name.equals(tableName))
			return X_LCO_FE_OperationType.class;
		if (X_LCO_FE_ProductScheme.Table_Name.equals(tableName))
			return X_LCO_FE_ProductScheme.class;
		if (X_LCO_FE_ProductSchemeList.Table_Name.equals(tableName))
			return X_LCO_FE_ProductSchemeList.class;
		if (X_LCO_FE_TributaryGroup.Table_Name.equals(tableName))
			return X_LCO_FE_TributaryGroup.class;
		if (X_LCO_FE_TributaryType.Table_Name.equals(tableName))
			return X_LCO_FE_TributaryType.class;
		return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		if (MLCOFEAuthorization.Table_Name.equals(tableName))
			return new MLCOFEAuthorization(Env.getCtx(), Record_ID, trxName);
		if (X_LCO_FE_BP_Info.Table_Name.equals(tableName))
			return new X_LCO_FE_BP_Info(Env.getCtx(), Record_ID, trxName);
		if (X_LCO_FE_ContactType.Table_Name.equals(tableName))
			return new X_LCO_FE_ContactType(Env.getCtx(), Record_ID, trxName);
		if (X_LCO_FE_DIAN_Format.Table_Name.equals(tableName))
			return new X_LCO_FE_DIAN_Format(Env.getCtx(), Record_ID, trxName);
		if (X_LCO_FE_DocType.Table_Name.equals(tableName))
			return new X_LCO_FE_DocType(Env.getCtx(), Record_ID, trxName);
		if (X_LCO_FE_NCConcept.Table_Name.equals(tableName))
			return new X_LCO_FE_NCConcept(Env.getCtx(), Record_ID, trxName);
		if (X_LCO_FE_NDConcept.Table_Name.equals(tableName))
			return new X_LCO_FE_NDConcept(Env.getCtx(), Record_ID, trxName);
		if (X_LCO_FE_OFE_Contingency.Table_Name.equals(tableName))
			return new X_LCO_FE_OFE_Contingency(Env.getCtx(), Record_ID, trxName);
		if (X_LCO_FE_OperationType.Table_Name.equals(tableName))
			return new X_LCO_FE_OperationType(Env.getCtx(), Record_ID, trxName);
		if (X_LCO_FE_ProductScheme.Table_Name.equals(tableName))
			return new X_LCO_FE_ProductScheme(Env.getCtx(), Record_ID, trxName);
		if (X_LCO_FE_ProductSchemeList.Table_Name.equals(tableName))
			return new X_LCO_FE_ProductSchemeList(Env.getCtx(), Record_ID, trxName);
		if (X_LCO_FE_TributaryGroup.Table_Name.equals(tableName))
			return new X_LCO_FE_TributaryGroup(Env.getCtx(), Record_ID, trxName);
		if (X_LCO_FE_TributaryType.Table_Name.equals(tableName))
			return new X_LCO_FE_TributaryType(Env.getCtx(), Record_ID, trxName);
		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		if (MLCOFEAuthorization.Table_Name.equals(tableName))
			return new MLCOFEAuthorization(Env.getCtx(), rs, trxName);
		if (X_LCO_FE_BP_Info.Table_Name.equals(tableName))
			return new X_LCO_FE_BP_Info(Env.getCtx(), rs, trxName);
		if (X_LCO_FE_ContactType.Table_Name.equals(tableName))
			return new X_LCO_FE_ContactType(Env.getCtx(), rs, trxName);
		if (X_LCO_FE_DIAN_Format.Table_Name.equals(tableName))
			return new X_LCO_FE_DIAN_Format(Env.getCtx(), rs, trxName);
		if (X_LCO_FE_DocType.Table_Name.equals(tableName))
			return new X_LCO_FE_DocType(Env.getCtx(), rs, trxName);
		if (X_LCO_FE_NCConcept.Table_Name.equals(tableName))
			return new X_LCO_FE_NCConcept(Env.getCtx(), rs, trxName);
		if (X_LCO_FE_NDConcept.Table_Name.equals(tableName))
			return new X_LCO_FE_NDConcept(Env.getCtx(), rs, trxName);
		if (X_LCO_FE_OFE_Contingency.Table_Name.equals(tableName))
			return new X_LCO_FE_OFE_Contingency(Env.getCtx(), rs, trxName);
		if (X_LCO_FE_OperationType.Table_Name.equals(tableName))
			return new X_LCO_FE_OperationType(Env.getCtx(), rs, trxName);
		if (X_LCO_FE_ProductScheme.Table_Name.equals(tableName))
			return new X_LCO_FE_ProductScheme(Env.getCtx(), rs, trxName);
		if (X_LCO_FE_ProductSchemeList.Table_Name.equals(tableName))
			return new X_LCO_FE_ProductSchemeList(Env.getCtx(), rs, trxName);
		if (X_LCO_FE_TributaryGroup.Table_Name.equals(tableName))
			return new X_LCO_FE_TributaryGroup(Env.getCtx(), rs, trxName);
		if (X_LCO_FE_TributaryType.Table_Name.equals(tableName))
			return new X_LCO_FE_TributaryType(Env.getCtx(), rs, trxName);
		return null;
	}

}
