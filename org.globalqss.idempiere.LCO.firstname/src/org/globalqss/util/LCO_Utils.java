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

import org.compiere.model.MSysConfig;

/**
 *	Utils for Localization LCO
 *
 *  @author Jesus Garcia - globalqss - Quality Systems & Solutions - http://globalqss.com
 */

public class LCO_Utils
{

	public static final String SPACE = " ";
	public static String getFullName(String fn1, String ln1, int AD_Client_ID) {
		StringBuilder fullName = new StringBuilder();

		String nameSeparator = MSysConfig.getValue("QSSLCO_NameSeparator", " ", AD_Client_ID);
		boolean namesFirst = MSysConfig.getBooleanValue("QSSLCO_NamesFirst", true, AD_Client_ID);

		if (fn1.length() == 0 && ln1.length() == 0)
			return null;

		if (namesFirst)
			fullName.append(fn1).append(nameSeparator).append(ln1);
		else
			fullName.append(ln1).append(nameSeparator).append(fn1);

		return fullName.toString();
	}

}	// LCO_Utils
