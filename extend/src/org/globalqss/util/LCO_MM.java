/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.globalqss.util;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.globalqss.model.MLCODIANConcept;
import org.globalqss.model.MLCODIANFormat;
import org.globalqss.model.X_LCO_DIAN_ConceptSource;
import org.globalqss.model.X_LCO_DIAN_SendSchedule;

/**
 *	LCO_MM - DB wrapper
 *
 *  @author Carlos Ruiz - globalqss - Quality Systems & Solutions - http://globalqss.com
 *  @version  $Id: LCO_MM
 *  
 */
public class LCO_MM
{
	/**	Logger							*/
	protected transient CLogger	log = CLogger.getCLogger (getClass());

	public BigDecimal get1001vded(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, Integer bpID, Integer bpID2, X_LCO_DIAN_ConceptSource conceptSource, String trxName) throws Exception {
		return get(getCurrentMethodName(), ctx, sendScheduleProcess, bpID, bpID2, conceptSource, trxName);
	}

	public BigDecimal get1001vpag(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, Integer bpID, Integer bpID2, X_LCO_DIAN_ConceptSource conceptSource, String trxName) throws Exception {
		return get(getCurrentMethodName(), ctx, sendScheduleProcess, bpID, bpID2, conceptSource, trxName);
	}

	public BigDecimal get1002vabo(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, Integer bpID, Integer bpID2, X_LCO_DIAN_ConceptSource conceptSource, String trxName) throws Exception {
		return get(getCurrentMethodName(), ctx, sendScheduleProcess, bpID, bpID2, conceptSource, trxName);
	}

	public BigDecimal get1002vret(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, Integer bpID, Integer bpID2, X_LCO_DIAN_ConceptSource conceptSource, String trxName) throws Exception {
		return get(getCurrentMethodName(), ctx, sendScheduleProcess, bpID, bpID2, conceptSource, trxName);
	}

	public BigDecimal get1003ret(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, Integer bpID, Integer bpID2, X_LCO_DIAN_ConceptSource conceptSource, String trxName) throws Exception {
		return get(getCurrentMethodName(), ctx, sendScheduleProcess, bpID, bpID2, conceptSource, trxName);
	}

	public BigDecimal get1003valor(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, Integer bpID, Integer bpID2, X_LCO_DIAN_ConceptSource conceptSource, String trxName) throws Exception {
		return get(getCurrentMethodName(), ctx, sendScheduleProcess, bpID, bpID2, conceptSource, trxName);
	}

	public BigDecimal get1004vdes(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, Integer bpID, Integer bpID2, X_LCO_DIAN_ConceptSource conceptSource, String trxName) throws Exception {
		return get(getCurrentMethodName(), ctx, sendScheduleProcess, bpID, bpID2, conceptSource, trxName);
	}

	public BigDecimal get1004vpag(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, Integer bpID, Integer bpID2, X_LCO_DIAN_ConceptSource conceptSource, String trxName) throws Exception {
		return get(getCurrentMethodName(), ctx, sendScheduleProcess, bpID, bpID2, conceptSource, trxName);
	}

	public BigDecimal get1005vimp(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, Integer bpID, Integer bpID2, X_LCO_DIAN_ConceptSource conceptSource, String trxName) throws Exception {
		return get(getCurrentMethodName(), ctx, sendScheduleProcess, bpID, bpID2, conceptSource, trxName);
	}

	public BigDecimal get1006vimp(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, Integer bpID, Integer bpID2, X_LCO_DIAN_ConceptSource conceptSource, String trxName) throws Exception {
		return get(getCurrentMethodName(), ctx, sendScheduleProcess, bpID, bpID2, conceptSource, trxName);
	}

	public BigDecimal get1007dev(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, Integer bpID, Integer bpID2, X_LCO_DIAN_ConceptSource conceptSource, String trxName) throws Exception {
		return get(getCurrentMethodName(), ctx, sendScheduleProcess, bpID, bpID2, conceptSource, trxName);
	}

	public BigDecimal get1007ing(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, Integer bpID, Integer bpID2, X_LCO_DIAN_ConceptSource conceptSource, String trxName) throws Exception {
		return get(getCurrentMethodName(), ctx, sendScheduleProcess, bpID, bpID2, conceptSource, trxName);
	}

	public BigDecimal get1008sal(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, Integer bpID, Integer bpID2, X_LCO_DIAN_ConceptSource conceptSource, String trxName) throws Exception {
		return get(getCurrentMethodName(), ctx, sendScheduleProcess, bpID, bpID2, conceptSource, trxName);
	}

	public BigDecimal get1009sal(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, Integer bpID, Integer bpID2, X_LCO_DIAN_ConceptSource conceptSource, String trxName) throws Exception {
		return get(getCurrentMethodName(), ctx, sendScheduleProcess, bpID, bpID2, conceptSource, trxName);
	}

	public BigDecimal get1011sal(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, Integer bpID, Integer bpID2, X_LCO_DIAN_ConceptSource conceptSource, String trxName) throws Exception {
		return get(getCurrentMethodName(), ctx, sendScheduleProcess, bpID, bpID2, conceptSource, trxName);
	}
	
	private BigDecimal get(String function,
			Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, Integer bpInt, Integer bpInt2, X_LCO_DIAN_ConceptSource conceptSource, String trxName) throws Exception {
		BigDecimal retValue = null;
		String accountValue = null;
		if (conceptSource.getC_ElementValue_ID() > 0)
			accountValue = conceptSource.getC_ElementValue().getValue();
		int bpid = 0;
		if (bpInt != null)
			bpid = bpInt.intValue();
		int bpid2 = 0;
		if (bpInt2 != null)
			bpid2 = bpInt2.intValue();
		
		String sql = "{call " + function + "(?,?,?,?,?,?,?,?,?,?,?)}";

		Trx trx = Trx.get(trxName, false);
		Connection conn = trx.getConnection();
		
		MLCODIANFormat format = new MLCODIANFormat(ctx, sendScheduleProcess.getLCO_DIAN_Format_ID(), trxName);
		MLCODIANConcept concept = new MLCODIANConcept(ctx, conceptSource.getLCO_DIAN_Concept_ID(), trxName);

		CallableStatement cstmt = null;
		try
		{
			cstmt = conn.prepareCall (sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			cstmt.setInt(1, Env.getAD_Client_ID(ctx)); // the client
			cstmt.setTimestamp(2, sendScheduleProcess.getStartDate()); // start
			cstmt.setTimestamp(3, sendScheduleProcess.getEndDate()); // end
			cstmt.setString(4, accountValue); // the account
			cstmt.setString(5, format.getValue()); // the format
			cstmt.setString(6, concept.getValue()); // the concept
			cstmt.setInt(7, bpid); // the BP
			cstmt.setInt(8, bpid2); // the BP2
			cstmt.setInt(9, sendScheduleProcess.getLCO_DIAN_SendSchedule_ID()); // the schedule process id
			cstmt.setInt(10, conceptSource.getLCO_DIAN_ConceptSource_ID()); // the concept source id
			cstmt.registerOutParameter(11, Types.DECIMAL);
			cstmt.execute();
			retValue = cstmt.getBigDecimal(11);
		}
		finally
		{
			DB.close(cstmt);
			cstmt = null;
		}

		if (retValue != null)
			retValue.setScale(0, BigDecimal.ROUND_HALF_UP);
		return retValue;
	}

	public BigDecimal consolidate1001(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, String trxName) throws Exception {
		return consolidate(getCurrentMethodName(), ctx, sendScheduleProcess, trxName);
	}
	
	public BigDecimal consolidate1002(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, String trxName) throws Exception {
		return consolidate(getCurrentMethodName(), ctx, sendScheduleProcess, trxName);
	}
	
	public BigDecimal consolidate1006(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, String trxName) throws Exception {
		return consolidate(getCurrentMethodName(), ctx, sendScheduleProcess, trxName);
	}
	
	public BigDecimal consolidate1007(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, String trxName) throws Exception {
		return consolidate(getCurrentMethodName(), ctx, sendScheduleProcess, trxName);
	}
	
	public BigDecimal consolidate1008(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, String trxName) throws Exception {
		return consolidate(getCurrentMethodName(), ctx, sendScheduleProcess, trxName);
	}
	
	public BigDecimal consolidate1009(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, String trxName) throws Exception {
		return consolidate(getCurrentMethodName(), ctx, sendScheduleProcess, trxName);
	}
	
	public BigDecimal consolidate1016(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, String trxName) throws Exception {
		return consolidate(getCurrentMethodName(), ctx, sendScheduleProcess, trxName);
	}
	
	public BigDecimal consolidate1053(Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, String trxName) throws Exception {
		return consolidate(getCurrentMethodName(), ctx, sendScheduleProcess, trxName);
	}
	
	private BigDecimal consolidate(String function, Properties ctx, X_LCO_DIAN_SendSchedule sendScheduleProcess, String trxName) throws SQLException {
		BigDecimal retValue = null;
		String sql = "{call " + function + "(?,?,?,?)}";

		Trx trx = Trx.get(trxName, false);
		Connection conn = trx.getConnection();
		
		MLCODIANFormat format = new MLCODIANFormat(ctx, sendScheduleProcess.getLCO_DIAN_Format_ID(), trxName);

		CallableStatement cstmt = null;
		try
		{
			cstmt = conn.prepareCall (sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			cstmt.setInt(1, Env.getAD_Client_ID(ctx)); // the client
			cstmt.setString(2, format.getValue()); // the format
			cstmt.setInt(3, sendScheduleProcess.getLCO_DIAN_SendSchedule_ID()); // the schedule process id
			cstmt.registerOutParameter(4, Types.DECIMAL);
			cstmt.execute();
			retValue = cstmt.getBigDecimal(4);
		}
		finally
		{
			DB.close(cstmt);
			cstmt = null;
		}

		if (retValue != null)
			retValue.setScale(0, BigDecimal.ROUND_HALF_UP);
		return retValue;
	}

	public String getCurrentMethodName() {
		StackTraceElement stackTraceElements[] = (new Throwable()).getStackTrace();
		return stackTraceElements[1].getMethodName().toString();
	}	
}	// LCO_MM
