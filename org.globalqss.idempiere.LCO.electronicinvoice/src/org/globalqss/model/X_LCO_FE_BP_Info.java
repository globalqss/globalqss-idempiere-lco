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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for LCO_FE_BP_Info
 *  @author iDempiere (generated) 
 *  @version Release 7.1 - $Id$ */
public class X_LCO_FE_BP_Info extends PO implements I_LCO_FE_BP_Info, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200204L;

    /** Standard Constructor */
    public X_LCO_FE_BP_Info (Properties ctx, int LCO_FE_BP_Info_ID, String trxName)
    {
      super (ctx, LCO_FE_BP_Info_ID, trxName);
      /** if (LCO_FE_BP_Info_ID == 0)
        {
			setC_BPartner_ID (0);
// @C_BPartner_ID@
			setLCO_FE_BP_Info_ID (0);
			setLCO_FE_TributaryType_ID (0);
        } */
    }

    /** Load Constructor */
    public X_LCO_FE_BP_Info (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_LCO_FE_BP_Info[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Info BP EI.
		@param LCO_FE_BP_Info_ID Info BP EI	  */
	public void setLCO_FE_BP_Info_ID (int LCO_FE_BP_Info_ID)
	{
		if (LCO_FE_BP_Info_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_BP_Info_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_BP_Info_ID, Integer.valueOf(LCO_FE_BP_Info_ID));
	}

	/** Get Info BP EI.
		@return Info BP EI	  */
	public int getLCO_FE_BP_Info_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LCO_FE_BP_Info_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set LCO_FE_BP_Info_UU.
		@param LCO_FE_BP_Info_UU LCO_FE_BP_Info_UU	  */
	public void setLCO_FE_BP_Info_UU (String LCO_FE_BP_Info_UU)
	{
		set_ValueNoCheck (COLUMNNAME_LCO_FE_BP_Info_UU, LCO_FE_BP_Info_UU);
	}

	/** Get LCO_FE_BP_Info_UU.
		@return LCO_FE_BP_Info_UU	  */
	public String getLCO_FE_BP_Info_UU () 
	{
		return (String)get_Value(COLUMNNAME_LCO_FE_BP_Info_UU);
	}

	public org.globalqss.model.I_LCO_FE_TributaryType getLCO_FE_TributaryType() throws RuntimeException
    {
		return (org.globalqss.model.I_LCO_FE_TributaryType)MTable.get(getCtx(), org.globalqss.model.I_LCO_FE_TributaryType.Table_Name)
			.getPO(getLCO_FE_TributaryType_ID(), get_TrxName());	}

	/** Set Tributary Type.
		@param LCO_FE_TributaryType_ID Tributary Type	  */
	public void setLCO_FE_TributaryType_ID (int LCO_FE_TributaryType_ID)
	{
		if (LCO_FE_TributaryType_ID < 1) 
			set_Value (COLUMNNAME_LCO_FE_TributaryType_ID, null);
		else 
			set_Value (COLUMNNAME_LCO_FE_TributaryType_ID, Integer.valueOf(LCO_FE_TributaryType_ID));
	}

	/** Get Tributary Type.
		@return Tributary Type	  */
	public int getLCO_FE_TributaryType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LCO_FE_TributaryType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}