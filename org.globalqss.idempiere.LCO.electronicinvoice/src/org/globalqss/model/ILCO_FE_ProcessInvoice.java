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

import org.compiere.model.MOrg;

public interface ILCO_FE_ProcessInvoice {

	// This method must attach the file to the authorization and return a message
	// the file can be an XML or CSV depending on the agreement with the technological provider 
	public String getFile(LCO_FE_MInvoice invoice);

	// This method must attach the signed file in case the technological provider supports that
	public String signFile(LCO_FE_MInvoice invoice);

	// This method sends a file, and returns a String with an answer (or throws an Exception)
	public String sendFile(LCO_FE_MInvoice invoice);

	// This method inquire for the status of an authorization record, returning a String with the message
	public String getStatus(MLCOFEAuthorization auth);

	// This method inquire for the status of an authorization record, returning a String with the message
	public String getNumberingRange(MOrg org);

}
