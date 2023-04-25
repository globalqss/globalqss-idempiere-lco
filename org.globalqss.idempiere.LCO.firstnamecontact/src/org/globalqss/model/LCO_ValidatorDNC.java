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

import org.adempiere.base.event.AbstractEventHandler;
import org.adempiere.base.event.IEventManager;
import org.adempiere.base.event.IEventTopics;
import org.adempiere.base.event.LoginEventData;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUser;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.globalqss.util.LCO_UtilsDNC;
import org.osgi.service.event.Event;

/**
 *	Validator or Localization Colombia (First Name Contact)
 *
 *  @author Carlos Ruiz - globalqss - Quality Systems & Solutions - http://globalqss.com
 */
public class LCO_ValidatorDNC extends AbstractEventHandler
{
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(LCO_ValidatorDNC.class);

	/**
	 *	Initialize Validation
	 */
	@Override
	protected void initialize() {
		log.warning("");

		registerTableEvent(IEventTopics.PO_BEFORE_NEW, MUser.Table_Name);
		registerTableEvent(IEventTopics.PO_BEFORE_CHANGE, MUser.Table_Name);

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
			// on login set context variable #LCO_USE_FIRST_NAME
			LoginEventData loginData = (LoginEventData) event.getProperty(IEventManager.EVENT_DATA);
			boolean useDN = MSysConfig.getBooleanValue("LCO_USE_FIRST_NAME_CONTACT", true, loginData.getAD_Client_ID());
			Env.setContext(Env.getCtx(), "#LCO_USE_FIRST_NAME_CONTACT", useDN);
			return;
		}

		if (! MSysConfig.getBooleanValue("LCO_USE_FIRST_NAME_CONTACT", true, Env.getAD_Client_ID(Env.getCtx())))
			return;

		PO po = getPO(event);
		log.info(po + " Type: " + type);
		String msg;

		// Check Digit based on TaxID
		if (po instanceof MUser && ( type.equals(IEventTopics.PO_BEFORE_NEW) || type.equals(IEventTopics.PO_BEFORE_CHANGE)))
		{
			MUser user = (MUser)po;
			msg = mfillName(user);
			if (msg != null)
				throw new RuntimeException(msg);
		}

	}	//	doHandleEvent

	/**
	 * 	Fill Name based on First and Last Names
	 *	@param user user/contact
	 *	@return error message or null
	 */
	public String mfillName (MUser user)
	{
		log.info("");
		String fn1 = user.get_ValueAsString("FirstName1");
		String ln1 = user.get_ValueAsString("LastName1");

		if (Util.isEmpty(fn1, true) && Util.isEmpty(ln1, true)) // no detailed names
			return null;

		int clientId = Env.getAD_Client_ID(user.getCtx());
		if (Util.isEmpty(fn1, true) && MSysConfig.getBooleanValue("QSSLCO_FirstNameMandatory", true, clientId))
			 return Msg.getMsg(user.getCtx(), "LCO_FirstName1Required");

		 if (Util.isEmpty(ln1, true) && MSysConfig.getBooleanValue("QSSLCO_LastNameMandatory", true, clientId))
			return Msg.getMsg(user.getCtx(), "LCO_LastName1Required");

		String fullName = LCO_UtilsDNC.getFullName(fn1, ln1, user.getAD_Client_ID());
		user.setName(fullName);
		return null;
	}	//	mfillName

}	//	LCO_ValidatorDNC
