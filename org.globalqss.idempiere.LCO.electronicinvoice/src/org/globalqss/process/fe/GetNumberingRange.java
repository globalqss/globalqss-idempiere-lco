/**********************************************************************
* This file is part of DCIERP                                         *
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
package org.globalqss.process.fe;

import org.adempiere.base.Service;
import org.adempiere.base.ServiceQuery;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MOrg;
import org.compiere.model.MSysConfig;
import org.compiere.process.SvrProcess;
import org.globalqss.model.ILCO_FE_ProcessInvoice;

public class GetNumberingRange extends SvrProcess {

	private int orgId;

	@Override
	protected void prepare() {
		orgId = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		ServiceQuery query = new ServiceQuery();
		String techProvider = MSysConfig.getValue("QSS_LCO_ProveedorTecnologico", "dian21", getAD_Client_ID());
		query.put("proveedor", techProvider); // QSS_LCO_ProveedorTecnologico
		ILCO_FE_ProcessInvoice custom = Service.locator().locate(ILCO_FE_ProcessInvoice.class, query).getService();			
		if (custom == null)
			throw new AdempiereException("No ILCO_FE_ProcessInvoice provider found for technological provider " + techProvider);

		MOrg org = new MOrg(getCtx(), orgId, get_TrxName());
		String msg = custom.getNumberingRange(org);
		addLog(msg);
		return "@OK@";
	}

}
