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

/** Generated Model for LCO_FE_NDConcept
 *  @author iDempiere (generated) 
 *  @version Release 7.1 - $Id$ */
public class X_LCO_FE_NDConcept extends PO implements I_LCO_FE_NDConcept, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200204L;

    /** Standard Constructor */
    public X_LCO_FE_NDConcept (Properties ctx, int LCO_FE_NDConcept_ID, String trxName)
    {
      super (ctx, LCO_FE_NDConcept_ID, trxName);
      /** if (LCO_FE_NDConcept_ID == 0)
        {
			setIsDefault (false);
// N
			setLCO_FE_NDConcept_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_LCO_FE_NDConcept (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_LCO_FE_NDConcept[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Default.
		@param IsDefault 
		Default value
	  */
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Default.
		@return Default value
	  */
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Debit Note Concept EI.
		@param LCO_FE_NDConcept_ID Debit Note Concept EI	  */
	public void setLCO_FE_NDConcept_ID (int LCO_FE_NDConcept_ID)
	{
		if (LCO_FE_NDConcept_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_NDConcept_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_NDConcept_ID, Integer.valueOf(LCO_FE_NDConcept_ID));
	}

	/** Get Debit Note Concept EI.
		@return Debit Note Concept EI	  */
	public int getLCO_FE_NDConcept_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LCO_FE_NDConcept_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set LCO_FE_NDConcept_UU.
		@param LCO_FE_NDConcept_UU LCO_FE_NDConcept_UU	  */
	public void setLCO_FE_NDConcept_UU (String LCO_FE_NDConcept_UU)
	{
		set_ValueNoCheck (COLUMNNAME_LCO_FE_NDConcept_UU, LCO_FE_NDConcept_UU);
	}

	/** Get LCO_FE_NDConcept_UU.
		@return LCO_FE_NDConcept_UU	  */
	public String getLCO_FE_NDConcept_UU () 
	{
		return (String)get_Value(COLUMNNAME_LCO_FE_NDConcept_UU);
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