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

	/**
	 *	Calculate DIAN Digit based on TaxID.
	 */
	public static int calculateDigitDian(String strNit) {

		//Vector de numeros primos
		int iNrosPrimos[] = { 3, 7, 13, 17, 19, 23, 29, 37,	41, 43, 47, 53, 59, 67, 71 };
		//Variable para realizar las operaciones
		int iOperacion = 0;
		int posini = 0;

		//Ciclo para multiplicar cada uno de los digitos del NIT con el vector
		for (int i = 0; i < strNit.trim().length() ; i++) {
			posini = strNit.trim().length() - (i + 1);
			try {
				iOperacion = iOperacion + Integer.parseInt(strNit.substring(posini, posini + 1)) * iNrosPrimos[i];
			} catch (NumberFormatException e) {
				return -1;
			}
		}

		//Obtener el residuo de la operacion
		iOperacion %= 11;

		if (iOperacion == 0 || iOperacion == 1)	{
		    return iOperacion;
		}
		else {
		    return 11 - iOperacion;
		}
	}	// calculateDigitDian

	public static final String SPACE = " ";
	public static String getFullName(String fn1, String fn2, String ln1, String ln2, int AD_Client_ID) {
		StringBuffer fullFirstNames = new StringBuffer();
		StringBuffer fullLastNames = new StringBuffer();
		StringBuffer fullName = new StringBuffer();

		if (fn1 != null && fn1.trim().length() > 0)
			fullFirstNames.append(fn1.trim());
		if (fn2 != null && fn2.trim().length() > 0)
			fullFirstNames.append(SPACE).append(fn2.trim());
		if (ln1 != null && ln1.trim().length() > 0)
			fullLastNames.append(ln1.trim());
		if (ln2 != null && ln2.trim().length() > 0)
			fullLastNames.append(SPACE).append(ln2.trim());

		String nameSeparator = MSysConfig.getValue("QSSLCO_NameSeparator", " ", AD_Client_ID);
		boolean namesFirst = MSysConfig.getBooleanValue("QSSLCO_NamesFirst", true, AD_Client_ID);

		if (fullFirstNames.length() == 0 && fullLastNames.length() == 0)
			return null;

		if (namesFirst)
			fullName = fullFirstNames.append(nameSeparator).append(fullLastNames);
		else
			fullName = fullLastNames.append(nameSeparator).append(fullFirstNames);

		return fullName.toString();
	}

}	// LCO_Utils
