/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.globalqss.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for LCO_FE_Authorization
 *  @author iDempiere (generated) 
 *  @version Release 7.1 - $Id$ */
public class X_LCO_FE_Authorization extends PO implements I_LCO_FE_Authorization, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200204L;

    /** Standard Constructor */
    public X_LCO_FE_Authorization (Properties ctx, int LCO_FE_Authorization_ID, String trxName)
    {
      super (ctx, LCO_FE_Authorization_ID, trxName);
      /** if (LCO_FE_Authorization_ID == 0)
        {
			setAD_Table_ID (0);
			setIsMailReceived (false);
// N
			setIsMailSend (false);
// N
			setLCO_FE_Authorization_ID (0);
			setLCO_FE_DocType_ID (0);
			setProcessed (false);
// N
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_LCO_FE_Authorization (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_LCO_FE_Authorization[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_User getAD_UserMail() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_UserMail_ID(), get_TrxName());	}

	/** Set User Mail.
		@param AD_UserMail_ID 
		Mail sent to the user
	  */
	public void setAD_UserMail_ID (int AD_UserMail_ID)
	{
		if (AD_UserMail_ID < 1) 
			set_Value (COLUMNNAME_AD_UserMail_ID, null);
		else 
			set_Value (COLUMNNAME_AD_UserMail_ID, Integer.valueOf(AD_UserMail_ID));
	}

	/** Get User Mail.
		@return Mail sent to the user
	  */
	public int getAD_UserMail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_UserMail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Contingency Processing.
		@param ContingencyProccessing Contingency Processing	  */
	public void setContingencyProccessing (String ContingencyProccessing)
	{
		set_Value (COLUMNNAME_ContingencyProccessing, ContingencyProccessing);
	}

	/** Get Contingency Processing.
		@return Contingency Processing	  */
	public String getContingencyProccessing () 
	{
		return (String)get_Value(COLUMNNAME_ContingencyProccessing);
	}

	/** Set Download URL.
		@param DownloadURL 
		URL of the Download files
	  */
	public void setDownloadURL (String DownloadURL)
	{
		set_Value (COLUMNNAME_DownloadURL, DownloadURL);
	}

	/** Get Download URL.
		@return URL of the Download files
	  */
	public String getDownloadURL () 
	{
		return (String)get_Value(COLUMNNAME_DownloadURL);
	}

	/** Set Error Msg.
		@param ErrorMsg Error Msg	  */
	public void setErrorMsg (String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Error Msg.
		@return Error Msg	  */
	public String getErrorMsg () 
	{
		return (String)get_Value(COLUMNNAME_ErrorMsg);
	}

	/** Set Send EMail.
		@param FE_Mailing Send EMail	  */
	public void setFE_Mailing (String FE_Mailing)
	{
		set_Value (COLUMNNAME_FE_Mailing, FE_Mailing);
	}

	/** Get Send EMail.
		@return Send EMail	  */
	public String getFE_Mailing () 
	{
		return (String)get_Value(COLUMNNAME_FE_Mailing);
	}

	/** Set Query Status.
		@param FE_QueryStatus Query Status	  */
	public void setFE_QueryStatus (String FE_QueryStatus)
	{
		set_Value (COLUMNNAME_FE_QueryStatus, FE_QueryStatus);
	}

	/** Get Query Status.
		@return Query Status	  */
	public String getFE_QueryStatus () 
	{
		return (String)get_Value(COLUMNNAME_FE_QueryStatus);
	}

	/** Set Reprocess Authorization.
		@param FE_Reprocessing Reprocess Authorization	  */
	public void setFE_Reprocessing (String FE_Reprocessing)
	{
		set_Value (COLUMNNAME_FE_Reprocessing, FE_Reprocessing);
	}

	/** Get Reprocess Authorization.
		@return Reprocess Authorization	  */
	public String getFE_Reprocessing () 
	{
		return (String)get_Value(COLUMNNAME_FE_Reprocessing);
	}

	/** Set Mail Received.
		@param IsMailReceived Mail Received	  */
	public void setIsMailReceived (boolean IsMailReceived)
	{
		set_Value (COLUMNNAME_IsMailReceived, Boolean.valueOf(IsMailReceived));
	}

	/** Get Mail Received.
		@return Mail Received	  */
	public boolean isMailReceived () 
	{
		Object oo = get_Value(COLUMNNAME_IsMailReceived);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Mail Sent.
		@param IsMailSend Mail Sent	  */
	public void setIsMailSend (boolean IsMailSend)
	{
		set_Value (COLUMNNAME_IsMailSend, Boolean.valueOf(IsMailSend));
	}

	/** Get Mail Sent.
		@return Mail Sent	  */
	public boolean isMailSend () 
	{
		Object oo = get_Value(COLUMNNAME_IsMailSend);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Authorization EI.
		@param LCO_FE_Authorization_ID Authorization EI	  */
	public void setLCO_FE_Authorization_ID (int LCO_FE_Authorization_ID)
	{
		if (LCO_FE_Authorization_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_Authorization_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_Authorization_ID, Integer.valueOf(LCO_FE_Authorization_ID));
	}

	/** Get Authorization EI.
		@return Authorization EI	  */
	public int getLCO_FE_Authorization_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LCO_FE_Authorization_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set LCO_FE_Authorization_UU.
		@param LCO_FE_Authorization_UU LCO_FE_Authorization_UU	  */
	public void setLCO_FE_Authorization_UU (String LCO_FE_Authorization_UU)
	{
		set_ValueNoCheck (COLUMNNAME_LCO_FE_Authorization_UU, LCO_FE_Authorization_UU);
	}

	/** Get LCO_FE_Authorization_UU.
		@return LCO_FE_Authorization_UU	  */
	public String getLCO_FE_Authorization_UU () 
	{
		return (String)get_Value(COLUMNNAME_LCO_FE_Authorization_UU);
	}

	/** Set Authorization Date.
		@param LCO_FE_DateAuthorization Authorization Date	  */
	public void setLCO_FE_DateAuthorization (String LCO_FE_DateAuthorization)
	{
		set_Value (COLUMNNAME_LCO_FE_DateAuthorization, LCO_FE_DateAuthorization);
	}

	/** Get Authorization Date.
		@return Authorization Date	  */
	public String getLCO_FE_DateAuthorization () 
	{
		return (String)get_Value(COLUMNNAME_LCO_FE_DateAuthorization);
	}

	public org.globalqss.model.I_LCO_FE_DIAN_Format getLCO_FE_DIAN_Format() throws RuntimeException
    {
		return (org.globalqss.model.I_LCO_FE_DIAN_Format)MTable.get(getCtx(), org.globalqss.model.I_LCO_FE_DIAN_Format.Table_Name)
			.getPO(getLCO_FE_DIAN_Format_ID(), get_TrxName());	}

	/** Set DIAN Format EI.
		@param LCO_FE_DIAN_Format_ID DIAN Format EI	  */
	public void setLCO_FE_DIAN_Format_ID (int LCO_FE_DIAN_Format_ID)
	{
		if (LCO_FE_DIAN_Format_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_DIAN_Format_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_DIAN_Format_ID, Integer.valueOf(LCO_FE_DIAN_Format_ID));
	}

	/** Get DIAN Format EI.
		@return DIAN Format EI	  */
	public int getLCO_FE_DIAN_Format_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LCO_FE_DIAN_Format_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UUID DIAN.
		@param LCO_FE_Dian_Uuid UUID DIAN	  */
	public void setLCO_FE_Dian_Uuid (String LCO_FE_Dian_Uuid)
	{
		set_Value (COLUMNNAME_LCO_FE_Dian_Uuid, LCO_FE_Dian_Uuid);
	}

	/** Get UUID DIAN.
		@return UUID DIAN	  */
	public String getLCO_FE_Dian_Uuid () 
	{
		return (String)get_Value(COLUMNNAME_LCO_FE_Dian_Uuid);
	}

	public org.globalqss.model.I_LCO_FE_DocType getLCO_FE_DocType() throws RuntimeException
    {
		return (org.globalqss.model.I_LCO_FE_DocType)MTable.get(getCtx(), org.globalqss.model.I_LCO_FE_DocType.Table_Name)
			.getPO(getLCO_FE_DocType_ID(), get_TrxName());	}

	/** Set Doc Type EI.
		@param LCO_FE_DocType_ID Doc Type EI	  */
	public void setLCO_FE_DocType_ID (int LCO_FE_DocType_ID)
	{
		if (LCO_FE_DocType_ID < 1) 
			set_Value (COLUMNNAME_LCO_FE_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_LCO_FE_DocType_ID, Integer.valueOf(LCO_FE_DocType_ID));
	}

	/** Get Doc Type EI.
		@return Doc Type EI	  */
	public int getLCO_FE_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LCO_FE_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Error Code EI.
		@param LCO_FE_IdErrorCode Error Code EI	  */
	public void setLCO_FE_IdErrorCode (BigDecimal LCO_FE_IdErrorCode)
	{
		set_Value (COLUMNNAME_LCO_FE_IdErrorCode, LCO_FE_IdErrorCode);
	}

	/** Get Error Code EI.
		@return Error Code EI	  */
	public BigDecimal getLCO_FE_IdErrorCode () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LCO_FE_IdErrorCode);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Transaction Id DIAN.
		@param LCO_FE_IdTransaction Transaction Id DIAN	  */
	public void setLCO_FE_IdTransaction (String LCO_FE_IdTransaction)
	{
		set_Value (COLUMNNAME_LCO_FE_IdTransaction, LCO_FE_IdTransaction);
	}

	/** Get Transaction Id DIAN.
		@return Transaction Id DIAN	  */
	public String getLCO_FE_IdTransaction () 
	{
		return (String)get_Value(COLUMNNAME_LCO_FE_IdTransaction);
	}

	public org.globalqss.model.I_LCO_FE_OFE_Contingency getLCO_FE_OFE_Contingency() throws RuntimeException
    {
		return (org.globalqss.model.I_LCO_FE_OFE_Contingency)MTable.get(getCtx(), org.globalqss.model.I_LCO_FE_OFE_Contingency.Table_Name)
			.getPO(getLCO_FE_OFE_Contingency_ID(), get_TrxName());	}

	/** Set Contingency EI.
		@param LCO_FE_OFE_Contingency_ID Contingency EI	  */
	public void setLCO_FE_OFE_Contingency_ID (int LCO_FE_OFE_Contingency_ID)
	{
		if (LCO_FE_OFE_Contingency_ID < 1) 
			set_Value (COLUMNNAME_LCO_FE_OFE_Contingency_ID, null);
		else 
			set_Value (COLUMNNAME_LCO_FE_OFE_Contingency_ID, Integer.valueOf(LCO_FE_OFE_Contingency_ID));
	}

	/** Get Contingency EI.
		@return Contingency EI	  */
	public int getLCO_FE_OFE_Contingency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LCO_FE_OFE_Contingency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Printed Form Control.
		@param LCO_PrintedFormControl_ID Printed Form Control	  */
	public void setLCO_PrintedFormControl_ID (int LCO_PrintedFormControl_ID)
	{
		if (LCO_PrintedFormControl_ID < 1) 
			set_Value (COLUMNNAME_LCO_PrintedFormControl_ID, null);
		else 
			set_Value (COLUMNNAME_LCO_PrintedFormControl_ID, Integer.valueOf(LCO_PrintedFormControl_ID));
	}

	/** Get Printed Form Control.
		@return Printed Form Control	  */
	public int getLCO_PrintedFormControl_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LCO_PrintedFormControl_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}