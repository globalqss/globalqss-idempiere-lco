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

/** Generated Model for LCO_FE_TributaryType
 *  @author iDempiere (generated) 
 *  @version Release 7.1 - $Id$ */
public class X_LCO_FE_TributaryType extends PO implements I_LCO_FE_TributaryType, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200204L;

    /** Standard Constructor */
    public X_LCO_FE_TributaryType (Properties ctx, int LCO_FE_TributaryType_ID, String trxName)
    {
      super (ctx, LCO_FE_TributaryType_ID, trxName);
      /** if (LCO_FE_TributaryType_ID == 0)
        {
			setIsMain (false);
// N
			setLCO_FE_TributaryType_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_LCO_FE_TributaryType (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_LCO_FE_TributaryType[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Main.
		@param IsMain Main	  */
	public void setIsMain (boolean IsMain)
	{
		set_Value (COLUMNNAME_IsMain, Boolean.valueOf(IsMain));
	}

	/** Get Main.
		@return Main	  */
	public boolean isMain () 
	{
		Object oo = get_Value(COLUMNNAME_IsMain);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.globalqss.model.I_LCO_FE_TributaryGroup getLCO_FE_TributaryGroup() throws RuntimeException
    {
		return (org.globalqss.model.I_LCO_FE_TributaryGroup)MTable.get(getCtx(), org.globalqss.model.I_LCO_FE_TributaryGroup.Table_Name)
			.getPO(getLCO_FE_TributaryGroup_ID(), get_TrxName());	}

	/** Set Tributary Group EI.
		@param LCO_FE_TributaryGroup_ID Tributary Group EI	  */
	public void setLCO_FE_TributaryGroup_ID (int LCO_FE_TributaryGroup_ID)
	{
		if (LCO_FE_TributaryGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_TributaryGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_TributaryGroup_ID, Integer.valueOf(LCO_FE_TributaryGroup_ID));
	}

	/** Get Tributary Group EI.
		@return Tributary Group EI	  */
	public int getLCO_FE_TributaryGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LCO_FE_TributaryGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Tributary Type.
		@param LCO_FE_TributaryType_ID Tributary Type	  */
	public void setLCO_FE_TributaryType_ID (int LCO_FE_TributaryType_ID)
	{
		if (LCO_FE_TributaryType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_TributaryType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_TributaryType_ID, Integer.valueOf(LCO_FE_TributaryType_ID));
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

	/** Set LCO_FE_TributaryType_UU.
		@param LCO_FE_TributaryType_UU LCO_FE_TributaryType_UU	  */
	public void setLCO_FE_TributaryType_UU (String LCO_FE_TributaryType_UU)
	{
		set_ValueNoCheck (COLUMNNAME_LCO_FE_TributaryType_UU, LCO_FE_TributaryType_UU);
	}

	/** Get LCO_FE_TributaryType_UU.
		@return LCO_FE_TributaryType_UU	  */
	public String getLCO_FE_TributaryType_UU () 
	{
		return (String)get_Value(COLUMNNAME_LCO_FE_TributaryType_UU);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
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