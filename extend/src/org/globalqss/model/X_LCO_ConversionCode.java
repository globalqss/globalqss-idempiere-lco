/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
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
/** Generated Model - DO NOT CHANGE */
package org.globalqss.model;

import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.*;

/** Generated Model for LCO_ConversionCode
 *  @author Adempiere (generated) 
 *  @version Release 3.4.2s - $Id$ */
public class X_LCO_ConversionCode extends PO implements I_LCO_ConversionCode, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    /** Standard Constructor */
    public X_LCO_ConversionCode (Properties ctx, int LCO_ConversionCode_ID, String trxName)
    {
      super (ctx, LCO_ConversionCode_ID, trxName);
      /** if (LCO_ConversionCode_ID == 0)
        {
			setExternalCode (null);
			setInternalCode (null);
			setLCO_ConversionCode_ID (0);
			setLCO_Conversion_ID (0);
        } */
    }

    /** Load Constructor */
    public X_LCO_ConversionCode (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
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
      StringBuffer sb = new StringBuffer ("X_LCO_ConversionCode[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set External Code.
		@param ExternalCode External Code	  */
	public void setExternalCode (String ExternalCode)
	{
		if (ExternalCode == null)
			throw new IllegalArgumentException ("ExternalCode is mandatory.");

		if (ExternalCode.length() > 60)
		{
			log.warning("Length > 60 - truncated");
			ExternalCode = ExternalCode.substring(0, 60);
		}
		set_Value (COLUMNNAME_ExternalCode, ExternalCode);
	}

	/** Get External Code.
		@return External Code	  */
	public String getExternalCode () 
	{
		return (String)get_Value(COLUMNNAME_ExternalCode);
	}

	/** Set Internal Code.
		@param InternalCode Internal Code	  */
	public void setInternalCode (String InternalCode)
	{
		if (InternalCode == null)
			throw new IllegalArgumentException ("InternalCode is mandatory.");

		if (InternalCode.length() > 60)
		{
			log.warning("Length > 60 - truncated");
			InternalCode = InternalCode.substring(0, 60);
		}
		set_Value (COLUMNNAME_InternalCode, InternalCode);
	}

	/** Get Internal Code.
		@return Internal Code	  */
	public String getInternalCode () 
	{
		return (String)get_Value(COLUMNNAME_InternalCode);
	}

	/** Set LCO_ConversionCode.
		@param LCO_ConversionCode_ID LCO_ConversionCode	  */
	public void setLCO_ConversionCode_ID (int LCO_ConversionCode_ID)
	{
		if (LCO_ConversionCode_ID < 1)
			 throw new IllegalArgumentException ("LCO_ConversionCode_ID is mandatory.");
		set_ValueNoCheck (COLUMNNAME_LCO_ConversionCode_ID, Integer.valueOf(LCO_ConversionCode_ID));
	}

	/** Get LCO_ConversionCode.
		@return LCO_ConversionCode	  */
	public int getLCO_ConversionCode_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LCO_ConversionCode_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.globalqss.model.I_LCO_Conversion getLCO_Conversion() throws Exception 
    {
        Class<?> clazz = MTable.getClass(org.globalqss.model.I_LCO_Conversion.Table_Name);
        org.globalqss.model.I_LCO_Conversion result = null;
        try	{
	        Constructor<?> constructor = null;
	    	constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, int.class, String.class});
    	    result = (org.globalqss.model.I_LCO_Conversion)constructor.newInstance(new Object[] {getCtx(), new Integer(getLCO_Conversion_ID()), get_TrxName()});
        } catch (Exception e) {
	        log.log(Level.SEVERE, "(id) - Table=" + Table_Name + ",Class=" + clazz, e);
	        log.saveError("Error", "Table=" + Table_Name + ",Class=" + clazz);
           throw e;
        }
        return result;
    }

	/** Set LCO_Conversion.
		@param LCO_Conversion_ID LCO_Conversion	  */
	public void setLCO_Conversion_ID (int LCO_Conversion_ID)
	{
		if (LCO_Conversion_ID < 1)
			 throw new IllegalArgumentException ("LCO_Conversion_ID is mandatory.");
		set_ValueNoCheck (COLUMNNAME_LCO_Conversion_ID, Integer.valueOf(LCO_Conversion_ID));
	}

	/** Get LCO_Conversion.
		@return LCO_Conversion	  */
	public int getLCO_Conversion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LCO_Conversion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}