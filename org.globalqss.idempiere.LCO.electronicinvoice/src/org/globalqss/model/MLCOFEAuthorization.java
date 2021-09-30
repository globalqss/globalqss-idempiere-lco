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
import java.util.Properties;

import org.compiere.model.MInvoice;
import org.compiere.util.DB;

public class MLCOFEAuthorization extends X_LCO_FE_Authorization {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4165606266892050869L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param LCO_FE_Authorization_ID id
	 *	@param trxName transaction
	 */
	public MLCOFEAuthorization (Properties ctx, int LCO_FE_Authorization_ID, String trxName)
	{
		super (ctx, LCO_FE_Authorization_ID, trxName);

	}	//	MLCOFEAuthorization

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MLCOFEAuthorization (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MLCOFEAuthorization

	/**
	 * 	getIdFromInvoice
	 */
	public static int getIdFromInvoice(int invoiceId) {
		int authID = DB.getSQLValueEx(null, 
				"SELECT LCO_FE_Authorization_ID FROM LCO_FE_Authorization WHERE AD_Table_ID=? AND Record_ID=?", 
				MInvoice.Table_ID, 
				invoiceId);
		if (authID < 0) {
			authID = 0;
		}
		return authID;
	}

	@Override
	protected boolean beforeDelete() {
		if (getErrorMsg() == null) {
			log.saveError("Error", "No se puede borrar autorización con mensaje de error sin procesar, intente Consultar Estado");
			return false;
		}
		return true;
	}
	
} // MLCOFEAuthorization
