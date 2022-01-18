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
import org.compiere.model.MBPartner;
import org.compiere.model.MSysConfig;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.globalqss.util.LCO_Utils;
import org.osgi.service.event.Event;

/**
 *	Validator or Localization Colombia (First Name - this is a subset of Detailed Names)
 *
 *  @author Carlos Ruiz - globalqss - Quality Systems & Solutions - http://globalqss.com
 */
public class LCO_ValidatorDN extends AbstractEventHandler
{
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(LCO_ValidatorDN.class);

	/**
	 *	Initialize Validation
	 */
	@Override
	protected void initialize() {
		log.warning("");

		registerTableEvent(IEventTopics.PO_BEFORE_NEW, MBPartner.Table_Name);
		registerTableEvent(IEventTopics.PO_BEFORE_CHANGE, MBPartner.Table_Name);

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
			boolean useDN = MSysConfig.getBooleanValue("LCO_USE_FIRST_NAME", true, loginData.getAD_Client_ID());
			Env.setContext(Env.getCtx(), "#LCO_USE_FIRST_NAME", useDN);
			return;
		}

		if (! MSysConfig.getBooleanValue("LCO_USE_FIRST_NAME", true, Env.getAD_Client_ID(Env.getCtx())))
			return;

		PO po = getPO(event);
		log.info(po + " Type: " + type);
		String msg;

		// Check Digit based on TaxID
		if (po instanceof MBPartner && ( type.equals(IEventTopics.PO_BEFORE_NEW) || type.equals(IEventTopics.PO_BEFORE_CHANGE)))
		{
			MBPartner bpartner = (MBPartner)po;
			msg = mfillName(bpartner);
			if (msg != null)
				throw new RuntimeException(msg);
		}

	}	//	doHandleEvent

	/**
	 * 	Fill Name based on First and Last Names
	 *	@param bpartner bpartner
	 *	@return error message or null
	 */
	public String mfillName (MBPartner bpartner)
	{
		log.info("");
		String fn1 = bpartner.get_ValueAsString("FirstName1");
		String ln1 = bpartner.get_ValueAsString("LastName1");

		if (Util.isEmpty(fn1, true) && Util.isEmpty(ln1, true)) // no detailed names
			return null;
		
		if (Util.isEmpty(fn1, true))
			 return Msg.getMsg(bpartner.getCtx(), "LCO_FirstName1Required");

		 if (Util.isEmpty(ln1, true))
			return Msg.getMsg(bpartner.getCtx(), "LCO_LastName1Required");

		String fullName = LCO_Utils.getFullName(fn1, ln1, bpartner.getAD_Client_ID());
		bpartner.setName(fullName);
		return null;
	}	//	mfillName

}	//	LCO_ValidatorDN
