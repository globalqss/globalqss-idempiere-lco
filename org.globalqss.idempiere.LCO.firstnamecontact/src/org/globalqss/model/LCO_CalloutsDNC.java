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

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.base.IColumnCalloutFactory;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUser;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.globalqss.util.LCO_UtilsDNC;


/**
 *	User Callout for LCO Localization Colombia
 *
 *  @author Carlos Ruiz
 *  @version  $Id: LCO_Callouts.java,v 1.0 2008/05/26
 */
public class LCO_CalloutsDNC implements IColumnCalloutFactory
{
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(LCO_CalloutsDNC.class);

	@Override
	public IColumnCallout[] getColumnCallouts(String tableName, String columnName) {
		if (! MSysConfig.getBooleanValue("LCO_USE_FIRST_NAME_CONTACT", true, Env.getAD_Client_ID(Env.getCtx())))
			return null;

		if (tableName.equalsIgnoreCase(MUser.Table_Name)) {
			if (   columnName.equalsIgnoreCase("FirstName1")
					  || columnName.equalsIgnoreCase("LastName1"))
				return new IColumnCallout[]{new FillName()};
		}

		return null;
	}

  private static class FillName implements IColumnCallout {
	/**
	 *	fillName
	 */
	@Override
	public String start (Properties ctx, int WindowNo,
			GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		log.info("");

		String fn1 = mTab.get_ValueAsString("FirstName1");
		String ln1 = mTab.get_ValueAsString("LastName1");
		int AD_Client_ID = Env.getAD_Client_ID(Env.getCtx());
		String fullName = LCO_UtilsDNC.getFullName(fn1, ln1, AD_Client_ID);
		mTab.setValue(MUser.COLUMNNAME_Name, fullName);
		return "";
	}	//	fillName
  }

}	//	LCO_Callouts
