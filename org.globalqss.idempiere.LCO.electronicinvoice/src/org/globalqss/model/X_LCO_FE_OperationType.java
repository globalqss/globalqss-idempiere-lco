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

/** Generated Model for LCO_FE_OperationType
 *  @author iDempiere (generated) 
 *  @version Release 7.1 - $Id$ */
public class X_LCO_FE_OperationType extends PO implements I_LCO_FE_OperationType, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200204L;

    /** Standard Constructor */
    public X_LCO_FE_OperationType (Properties ctx, int LCO_FE_OperationType_ID, String trxName)
    {
      super (ctx, LCO_FE_OperationType_ID, trxName);
      /** if (LCO_FE_OperationType_ID == 0)
        {
			setLCO_FE_OperationType_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_LCO_FE_OperationType (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_LCO_FE_OperationType[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Operation Type EI.
		@param LCO_FE_OperationType_ID Operation Type EI	  */
	public void setLCO_FE_OperationType_ID (int LCO_FE_OperationType_ID)
	{
		if (LCO_FE_OperationType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_OperationType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_OperationType_ID, Integer.valueOf(LCO_FE_OperationType_ID));
	}

	/** Get Operation Type EI.
		@return Operation Type EI	  */
	public int getLCO_FE_OperationType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LCO_FE_OperationType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set LCO_FE_OperationType_UU.
		@param LCO_FE_OperationType_UU LCO_FE_OperationType_UU	  */
	public void setLCO_FE_OperationType_UU (String LCO_FE_OperationType_UU)
	{
		set_ValueNoCheck (COLUMNNAME_LCO_FE_OperationType_UU, LCO_FE_OperationType_UU);
	}

	/** Get LCO_FE_OperationType_UU.
		@return LCO_FE_OperationType_UU	  */
	public String getLCO_FE_OperationType_UU () 
	{
		return (String)get_Value(COLUMNNAME_LCO_FE_OperationType_UU);
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