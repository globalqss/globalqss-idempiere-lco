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
import org.compiere.util.KeyNamePair;

/** Generated Model for LCO_DIAN_Format
 *  @author Adempiere (generated) 
 *  @version Release 3.4.2s - $Id$ */
public class X_LCO_DIAN_Format extends PO implements I_LCO_DIAN_Format, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    /** Standard Constructor */
    public X_LCO_DIAN_Format (Properties ctx, int LCO_DIAN_Format_ID, String trxName)
    {
      super (ctx, LCO_DIAN_Format_ID, trxName);
      /** if (LCO_DIAN_Format_ID == 0)
        {
			setAD_Sequence_ID (0);
			setIsBPartnerDetailed (false);
// N
			setLCO_DIAN_Format_ID (0);
			setLCO_DIAN_XMLPrintLabel_ID (0);
			setMaxXMLRecords (0);
// 5000
			setName (null);
			setValue (null);
			setVersionNo (null);
        } */
    }

    /** Load Constructor */
    public X_LCO_DIAN_Format (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_LCO_DIAN_Format[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Sequence getAD_Sequence() throws Exception 
    {
        Class<?> clazz = MTable.getClass(I_AD_Sequence.Table_Name);
        I_AD_Sequence result = null;
        try	{
	        Constructor<?> constructor = null;
	    	constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, int.class, String.class});
    	    result = (I_AD_Sequence)constructor.newInstance(new Object[] {getCtx(), new Integer(getAD_Sequence_ID()), get_TrxName()});
        } catch (Exception e) {
	        log.log(Level.SEVERE, "(id) - Table=" + Table_Name + ",Class=" + clazz, e);
	        log.saveError("Error", "Table=" + Table_Name + ",Class=" + clazz);
           throw e;
        }
        return result;
    }

	/** Set Sequence.
		@param AD_Sequence_ID 
		Document Sequence
	  */
	public void setAD_Sequence_ID (int AD_Sequence_ID)
	{
		if (AD_Sequence_ID < 1)
			 throw new IllegalArgumentException ("AD_Sequence_ID is mandatory.");
		set_Value (COLUMNNAME_AD_Sequence_ID, Integer.valueOf(AD_Sequence_ID));
	}

	/** Get Sequence.
		@return Document Sequence
	  */
	public int getAD_Sequence_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Sequence_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_AcctSchema getC_AcctSchema() throws Exception 
    {
        Class<?> clazz = MTable.getClass(I_C_AcctSchema.Table_Name);
        I_C_AcctSchema result = null;
        try	{
	        Constructor<?> constructor = null;
	    	constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, int.class, String.class});
    	    result = (I_C_AcctSchema)constructor.newInstance(new Object[] {getCtx(), new Integer(getC_AcctSchema_ID()), get_TrxName()});
        } catch (Exception e) {
	        log.log(Level.SEVERE, "(id) - Table=" + Table_Name + ",Class=" + clazz, e);
	        log.saveError("Error", "Table=" + Table_Name + ",Class=" + clazz);
           throw e;
        }
        return result;
    }

	/** Set Accounting Schema.
		@param C_AcctSchema_ID 
		Rules for accounting
	  */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_Value (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_Value (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Accounting Schema.
		@return Rules for accounting
	  */
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{

		if (Description != null && Description.length() > 255)
		{
			log.warning("Length > 255 - truncated");
			Description = Description.substring(0, 255);
		}
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set IsBPartnerDetailed.
		@param IsBPartnerDetailed IsBPartnerDetailed	  */
	public void setIsBPartnerDetailed (boolean IsBPartnerDetailed)
	{
		set_Value (COLUMNNAME_IsBPartnerDetailed, Boolean.valueOf(IsBPartnerDetailed));
	}

	/** Get IsBPartnerDetailed.
		@return IsBPartnerDetailed	  */
	public boolean isBPartnerDetailed () 
	{
		Object oo = get_Value(COLUMNNAME_IsBPartnerDetailed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set DIAN Format.
		@param LCO_DIAN_Format_ID DIAN Format	  */
	public void setLCO_DIAN_Format_ID (int LCO_DIAN_Format_ID)
	{
		if (LCO_DIAN_Format_ID < 1)
			 throw new IllegalArgumentException ("LCO_DIAN_Format_ID is mandatory.");
		set_ValueNoCheck (COLUMNNAME_LCO_DIAN_Format_ID, Integer.valueOf(LCO_DIAN_Format_ID));
	}

	/** Get DIAN Format.
		@return DIAN Format	  */
	public int getLCO_DIAN_Format_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LCO_DIAN_Format_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** LCO_DIAN_XMLPrintLabel_ID AD_Reference_ID=1000008 */
	public static final int LCO_DIAN_XMLPRINTLABEL_ID_AD_Reference_ID=1000008;
	/** Set DIAN XML Print Label.
		@param LCO_DIAN_XMLPrintLabel_ID DIAN XML Print Label	  */
	public void setLCO_DIAN_XMLPrintLabel_ID (int LCO_DIAN_XMLPrintLabel_ID)
	{
		if (LCO_DIAN_XMLPrintLabel_ID < 1)
			 throw new IllegalArgumentException ("LCO_DIAN_XMLPrintLabel_ID is mandatory.");
		set_Value (COLUMNNAME_LCO_DIAN_XMLPrintLabel_ID, Integer.valueOf(LCO_DIAN_XMLPrintLabel_ID));
	}

	/** Get DIAN XML Print Label.
		@return DIAN XML Print Label	  */
	public int getLCO_DIAN_XMLPrintLabel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LCO_DIAN_XMLPrintLabel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MaxXMLRecords.
		@param MaxXMLRecords MaxXMLRecords	  */
	public void setMaxXMLRecords (int MaxXMLRecords)
	{
		set_Value (COLUMNNAME_MaxXMLRecords, Integer.valueOf(MaxXMLRecords));
	}

	/** Get MaxXMLRecords.
		@return MaxXMLRecords	  */
	public int getMaxXMLRecords () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaxXMLRecords);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		if (Name == null)
			throw new IllegalArgumentException ("Name is mandatory.");

		if (Name.length() > 60)
		{
			log.warning("Length > 60 - truncated");
			Name = Name.substring(0, 60);
		}
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	public I_PA_Hierarchy getPA_Hierarchy() throws Exception 
    {
        Class<?> clazz = MTable.getClass(I_PA_Hierarchy.Table_Name);
        I_PA_Hierarchy result = null;
        try	{
	        Constructor<?> constructor = null;
	    	constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, int.class, String.class});
    	    result = (I_PA_Hierarchy)constructor.newInstance(new Object[] {getCtx(), new Integer(getPA_Hierarchy_ID()), get_TrxName()});
        } catch (Exception e) {
	        log.log(Level.SEVERE, "(id) - Table=" + Table_Name + ",Class=" + clazz, e);
	        log.saveError("Error", "Table=" + Table_Name + ",Class=" + clazz);
           throw e;
        }
        return result;
    }

	/** Set Reporting Hierarchy.
		@param PA_Hierarchy_ID 
		Optional Reporting Hierarchy - If not selected the default hierarchy trees are used.
	  */
	public void setPA_Hierarchy_ID (int PA_Hierarchy_ID)
	{
		if (PA_Hierarchy_ID < 1) 
			set_Value (COLUMNNAME_PA_Hierarchy_ID, null);
		else 
			set_Value (COLUMNNAME_PA_Hierarchy_ID, Integer.valueOf(PA_Hierarchy_ID));
	}

	/** Get Reporting Hierarchy.
		@return Optional Reporting Hierarchy - If not selected the default hierarchy trees are used.
	  */
	public int getPA_Hierarchy_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PA_Hierarchy_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Post Process Class.
		@param PostProcessClass Post Process Class	  */
	public void setPostProcessClass (String PostProcessClass)
	{

		if (PostProcessClass != null && PostProcessClass.length() > 60)
		{
			log.warning("Length > 60 - truncated");
			PostProcessClass = PostProcessClass.substring(0, 60);
		}
		set_Value (COLUMNNAME_PostProcessClass, PostProcessClass);
	}

	/** Get Post Process Class.
		@return Post Process Class	  */
	public String getPostProcessClass () 
	{
		return (String)get_Value(COLUMNNAME_PostProcessClass);
	}

	/** Set URL_XSD.
		@param URL_XSD URL_XSD	  */
	public void setURL_XSD (String URL_XSD)
	{

		if (URL_XSD != null && URL_XSD.length() > 255)
		{
			log.warning("Length > 255 - truncated");
			URL_XSD = URL_XSD.substring(0, 255);
		}
		set_Value (COLUMNNAME_URL_XSD, URL_XSD);
	}

	/** Get URL_XSD.
		@return URL_XSD	  */
	public String getURL_XSD () 
	{
		return (String)get_Value(COLUMNNAME_URL_XSD);
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		if (Value == null)
			throw new IllegalArgumentException ("Value is mandatory.");

		if (Value.length() > 40)
		{
			log.warning("Length > 40 - truncated");
			Value = Value.substring(0, 40);
		}
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set Version No.
		@param VersionNo 
		Version Number
	  */
	public void setVersionNo (String VersionNo)
	{
		if (VersionNo == null)
			throw new IllegalArgumentException ("VersionNo is mandatory.");

		if (VersionNo.length() > 2)
		{
			log.warning("Length > 2 - truncated");
			VersionNo = VersionNo.substring(0, 2);
		}
		set_Value (COLUMNNAME_VersionNo, VersionNo);
	}

	/** Get Version No.
		@return Version Number
	  */
	public String getVersionNo () 
	{
		return (String)get_Value(COLUMNNAME_VersionNo);
	}
}