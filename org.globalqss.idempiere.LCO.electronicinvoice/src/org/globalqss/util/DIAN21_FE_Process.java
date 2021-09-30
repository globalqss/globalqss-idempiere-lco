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

import org.compiere.model.MOrg;
import org.compiere.model.MSysConfig;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.globalqss.model.ILCO_FE_ProcessInvoice;
import org.globalqss.model.LCO_FE_MInvoice;
import org.globalqss.model.MLCOFEAuthorization;

public class DIAN21_FE_Process implements ILCO_FE_ProcessInvoice {

	@Override
	public String getFile(LCO_FE_MInvoice invoice) {
		DIAN21_FE_UtilsXML utilXml = new DIAN21_FE_UtilsXML();
		String msg = utilXml.getFile(invoice);
		return msg;
	}

	@Override
	public String signFile(LCO_FE_MInvoice invoice) {
		String msg;
		String pathScripts = MSysConfig.getValue("QSSLCO_FE_Path_PHP_Scripts", "", Env.getAD_Client_ID(Env.getCtx()));
		boolean usePHP = !Util.isEmpty(pathScripts);
		String signScript = MSysConfig.getValue("QSSLCO_FE_SignScript", "", Env.getAD_Client_ID(Env.getCtx()));
		boolean useSignScript = !Util.isEmpty(signScript);
		if (usePHP && useSignScript) {
			DIAN21_FE_UtilsSign_PHP utilSign = new DIAN21_FE_UtilsSign_PHP();
			msg = utilSign.signFile(invoice);
		} else {
			DIAN21_FE_UtilsSign utilSign = new DIAN21_FE_UtilsSign();
			msg = utilSign.signFile(invoice);
		}
		return msg;
	}

	@Override
	public String sendFile(LCO_FE_MInvoice invoice) {
		String msg;
		String pathScripts = MSysConfig.getValue("QSSLCO_FE_Path_PHP_Scripts", "", Env.getAD_Client_ID(Env.getCtx()));
		boolean usePHP = !Util.isEmpty(pathScripts);
		String sendScript = MSysConfig.getValue("QSSLCO_FE_SendScript", "", Env.getAD_Client_ID(Env.getCtx()));
		boolean useSendScript = !Util.isEmpty(sendScript);
		if (usePHP && useSendScript) {
			DIAN21_FE_UtilsWS_PHP utilSend = new DIAN21_FE_UtilsWS_PHP();
			msg = utilSend.sendFile(invoice);
		} else {
			DIAN21_FE_UtilsWS utilSend = new DIAN21_FE_UtilsWS();
			msg = utilSend.sendFile(invoice);
		}
		return msg;
	}

	@Override
	public String getStatus(MLCOFEAuthorization auth) {
		String msg;
		String pathScripts = MSysConfig.getValue("QSSLCO_FE_Path_PHP_Scripts", "", Env.getAD_Client_ID(Env.getCtx()));
		boolean usePHP = !Util.isEmpty(pathScripts);
		String statusScript = MSysConfig.getValue("QSSLCO_FE_StatusScript", "", Env.getAD_Client_ID(Env.getCtx()));
		boolean useStatusScript = !Util.isEmpty(statusScript);
		if (usePHP && useStatusScript) {
			DIAN21_FE_UtilsWS_PHP utilGetStatus = new DIAN21_FE_UtilsWS_PHP();
			msg = utilGetStatus.getStatus(auth);
		} else {
			DIAN21_FE_UtilsWS utilGetStatus = new DIAN21_FE_UtilsWS();
			msg = utilGetStatus.getStatus(auth);
		}
		return msg;
	}

	@Override
	public String getNumberingRange(MOrg org) {
		String msg;
		String pathScripts = MSysConfig.getValue("QSSLCO_FE_Path_PHP_Scripts", "", Env.getAD_Client_ID(Env.getCtx()));
		boolean usePHP = !Util.isEmpty(pathScripts);
		String numberingRangeScript = MSysConfig.getValue("QSSLCO_FE_NumberingRangeScript", "", Env.getAD_Client_ID(Env.getCtx()));
		boolean useNumberingRangeScript = !Util.isEmpty(numberingRangeScript);
		if (usePHP && useNumberingRangeScript) {
			DIAN21_FE_UtilsWS_PHP utilGetNumberingRange = new DIAN21_FE_UtilsWS_PHP();
			msg = utilGetNumberingRange.getNumberingRange(org);
		} else {
			DIAN21_FE_UtilsWS utilGetNumberingRange = new DIAN21_FE_UtilsWS();
			msg = utilGetNumberingRange.getNumberingRange(org);
		}
		return msg;
	}

}
