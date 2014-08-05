/**********************************************************************
* This file is part of Adempiere ERP Bazaar                           *
* http://www.adempiere.org                                            *
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
* - Jesus Garcia - GlobalQSS Colombia                                 *
* - Carlos Ruiz  - GlobalQSS Colombia                                 *
**********************************************************************/
package org.globalqss.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.compiere.model.MSysConfig;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.globalqss.model.X_I_SRI_AccessCode;
import org.globalqss.model.X_SRI_AccessCode;

/**
 *	Import SRI Access Codes from I_SRI_AccessCode
 *
 * 	@author 	GlobalQSS/jjgq
 */
public class ImportSriAccessCode extends SvrProcess
{
	/**	Client to be imported to		*/
	private int				m_AD_Client_ID = 0;
	/**	Org to be imported to		*/
	private int				m_AD_Org_ID = 0;
	
	/**	Delete old Imported				*/
	private boolean			m_deleteOldImported = false;
	/**	Short Doc Type to be imported to		*/
	private String			m_SRI_ShortDocType = null;
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("AD_Org_ID"))
				m_AD_Org_ID = para[i].getParameterAsInt();
			// else if (name.equals("SRI_ShortDocType"))
			//	m_SRI_ShortDocType = para[i].getParameter().toString();
			else if (name.equals("DeleteOldImported"))
				m_deleteOldImported = "Y".equals(para[i].getParameter());
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		m_AD_Client_ID = getAD_Client_ID();
	}	//	prepare


	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	protected String doIt() throws Exception
	{
		StringBuffer sql = null;
		int no = 0;
		String clientCheck = " AND AD_Client_ID=" + m_AD_Client_ID;
		
		//	****	Prepare	****

		//	Delete Old Imported
		if (m_deleteOldImported)
		{
			sql = new StringBuffer ("DELETE I_SRI_AccessCode "
				+ "WHERE I_IsImported='Y'").append(clientCheck);
			no = DB.executeUpdate(sql.toString(), get_TrxName());
			log.info("Delete Old Imported =" + no);
		}

		//	Set Client, Org, IsActive, Created/Updated
		sql = new StringBuffer ("UPDATE I_SRI_AccessCode "
			+ "SET AD_Client_ID = COALESCE (AD_Client_ID, ").append(m_AD_Client_ID).append("),"
			+ " AD_Org_ID = COALESCE (").append(m_AD_Org_ID).append(", 0)," 
			+ " IsActive = COALESCE (IsActive, 'Y'),"
			+ " Created = COALESCE (Created, SysDate),"
			+ " CreatedBy = COALESCE (CreatedBy, 0),"
			+ " Updated = COALESCE (Updated, SysDate),"
			+ " UpdatedBy = COALESCE (UpdatedBy, 0),"
			+ " I_ErrorMsg = ' ',"
			+ " I_IsImported = 'N' "
			+ "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL");
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.info("Reset=" + no);
		
		//	Mandatories
		sql = new StringBuffer ("UPDATE I_SRI_AccessCode "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No Mandatory Value,' "
			+ "WHERE Value IS NULL"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning("No Mandatory Value=" + no);
		
		//	Environment
		sql = new StringBuffer ("UPDATE I_SRI_AccessCode "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Source File Environment differs' "
			//+ "WHERE Value IS NOT NULL AND SUBSTR(Value,14,1) <> ").append(m_EnvType).append("::bpchar"
			+ "WHERE Value IS NOT NULL AND SUBSTR(Value,14,1) NOT IN ('1', '2')"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning("Source File Environment differs=" + no);
		
		//	RUC
		sql = new StringBuffer ("UPDATE I_SRI_AccessCode "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Source File RUC differs' "
			+ "WHERE Value IS NOT NULL AND SUBSTR(Value,1,13) NOT IN "
			+ " (SELECT TaxID FROM AD_OrgInfo WHERE TaxID = SUBSTR(Value,1,13))"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning("Source File RUC differs=" + no);
		
		//	Org + RUC
		sql = new StringBuffer ("UPDATE I_SRI_AccessCode "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Source File Org differs' "
			+ "WHERE AD_Org_ID NOT IN "
			+ " (SELECT AD_Org_ID FROM AD_OrgInfo WHERE AD_Org_ID = ").append(m_AD_Org_ID).append(" AND TaxID = SUBSTR(Value,1,13))"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning("Source File Org differs=" + no);
		
		commitEx();
		
		//Import Access Code
		sql = new StringBuffer ("SELECT * FROM I_SRI_AccessCode WHERE I_IsImported='N'")
			.append(clientCheck);
		
		int noInsert = 0;
		PreparedStatement pstmt_setImported = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				X_I_SRI_AccessCode imp = new X_I_SRI_AccessCode(getCtx(), rs, get_TrxName());
				
				// New Access Code
				X_SRI_AccessCode sri_accesscode = new X_SRI_AccessCode (getCtx(), 0, get_TrxName());
				//sri_accesscode.setAD_ClientID(imp.getAD_Client_ID());
				sri_accesscode.setAD_Org_ID(imp.getAD_Org_ID());
				sri_accesscode.setValue(imp.getValue());
				sri_accesscode.setOldValue(imp.getValue());
				sri_accesscode.setEnvType("2");	// Produccion
				if (MSysConfig.getBooleanValue("QSSLEC_FE_EnPruebas", false, getAD_Client_ID()))
					sri_accesscode.setEnvType("1");	// Pruebas
				sri_accesscode.setCodeAccessType("2"); // Contingencia
				sri_accesscode.setSRI_ShortDocType(m_SRI_ShortDocType);
				sri_accesscode.setIsUsed(false);
				
				// Save Access Code
				if (sri_accesscode.save())
				{
					imp.setI_IsImported(true);
					imp.setProcessed(true);
					imp.saveEx();
					noInsert++;

				}
			}
			
			//		Close database connection
			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;
			
		}

		catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
			DB.close(pstmt_setImported);
			pstmt_setImported = null;
		}

		//	Set Error to indicator to not imported
		sql = new StringBuffer ("UPDATE I_SRI_AccessCode "
			+ "SET I_IsImported='N', Updated=SysDate "
			+ "WHERE I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		addLog (0, null, new BigDecimal (no), "@Errors@");
		//
		addLog (0, null, new BigDecimal (noInsert), "@SRI_AccessCode_ID@: @Inserted@");
		return "";
	}	//	doIt
	

}	//	ImportSriAccessCode
