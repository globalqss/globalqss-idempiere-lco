package org.globalqss.model;

import java.sql.ResultSet;

import org.adempiere.base.IModelFactory;
import org.compiere.model.PO;
import org.compiere.util.Env;

public class LCO_ModelFactoryINC  implements IModelFactory {

	@Override
	public Class<?> getClass(String tableName) {
		if (X_LCO_PrintedFormControl.Table_Name.equals(tableName))
			return X_LCO_PrintedFormControl.class;
		return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		if (X_LCO_PrintedFormControl.Table_Name.equals(tableName))
			return new X_LCO_PrintedFormControl(Env.getCtx(), Record_ID, trxName);
		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		if (X_LCO_PrintedFormControl.Table_Name.equals(tableName))
			return new X_LCO_PrintedFormControl(Env.getCtx(), rs, trxName);
		return null;
	}

}
