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
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for LCO_DIAN_SendScheduleLine
 *  @author Adempiere (generated) 
 *  @version Release 3.4.2s - $Id$ */
public class X_LCO_DIAN_SendScheduleLine extends PO implements I_LCO_DIAN_SendScheduleLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    /** Standard Constructor */
    public X_LCO_DIAN_SendScheduleLine (Properties ctx, int LCO_DIAN_SendScheduleLine_ID, String trxName)
    {
      super (ctx, LCO_DIAN_SendScheduleLine_ID, trxName);
      /** if (LCO_DIAN_SendScheduleLine_ID == 0)
        {
			setLCO_DIAN_Concept_ID (0);
			setLCO_DIAN_SendScheduleLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_LCO_DIAN_SendScheduleLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_LCO_DIAN_SendScheduleLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Attribute Name 1.
		@param AttributeName1 
		Name of the Attribute
	  */
	public void setAttributeName1 (String AttributeName1)
	{
		throw new IllegalArgumentException ("AttributeName1 is virtual column");	}

	/** Get Attribute Name 1.
		@return Name of the Attribute
	  */
	public String getAttributeName1 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeName1);
	}

	/** Set Attribute Name 10.
		@param AttributeName10 
		Name of the Attribute 10
	  */
	public void setAttributeName10 (String AttributeName10)
	{
		throw new IllegalArgumentException ("AttributeName10 is virtual column");	}

	/** Get Attribute Name 10.
		@return Name of the Attribute 10
	  */
	public String getAttributeName10 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeName10);
	}

	/** Set Attribute Name 2.
		@param AttributeName2 
		Name of the Attribute 2
	  */
	public void setAttributeName2 (String AttributeName2)
	{
		throw new IllegalArgumentException ("AttributeName2 is virtual column");	}

	/** Get Attribute Name 2.
		@return Name of the Attribute 2
	  */
	public String getAttributeName2 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeName2);
	}

	/** Set Attribute Name 3.
		@param AttributeName3 
		Name of the Attribute 3
	  */
	public void setAttributeName3 (String AttributeName3)
	{
		throw new IllegalArgumentException ("AttributeName3 is virtual column");	}

	/** Get Attribute Name 3.
		@return Name of the Attribute 3
	  */
	public String getAttributeName3 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeName3);
	}

	/** Set Attribute Name 4.
		@param AttributeName4 
		Name of the Attribute 4
	  */
	public void setAttributeName4 (String AttributeName4)
	{
		throw new IllegalArgumentException ("AttributeName4 is virtual column");	}

	/** Get Attribute Name 4.
		@return Name of the Attribute 4
	  */
	public String getAttributeName4 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeName4);
	}

	/** Set Attribute Name 5.
		@param AttributeName5 
		Name of the Attribute 5
	  */
	public void setAttributeName5 (String AttributeName5)
	{
		throw new IllegalArgumentException ("AttributeName5 is virtual column");	}

	/** Get Attribute Name 5.
		@return Name of the Attribute 5
	  */
	public String getAttributeName5 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeName5);
	}

	/** Set Attribute Name 6.
		@param AttributeName6 
		Name of the Attribute 6
	  */
	public void setAttributeName6 (String AttributeName6)
	{
		throw new IllegalArgumentException ("AttributeName6 is virtual column");	}

	/** Get Attribute Name 6.
		@return Name of the Attribute 6
	  */
	public String getAttributeName6 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeName6);
	}

	/** Set Attribute Name 7.
		@param AttributeName7 
		Name of the Attribute 7
	  */
	public void setAttributeName7 (String AttributeName7)
	{
		throw new IllegalArgumentException ("AttributeName7 is virtual column");	}

	/** Get Attribute Name 7.
		@return Name of the Attribute 7
	  */
	public String getAttributeName7 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeName7);
	}

	/** Set Attribute Name 8.
		@param AttributeName8 
		Name of the Attribute 8
	  */
	public void setAttributeName8 (String AttributeName8)
	{
		throw new IllegalArgumentException ("AttributeName8 is virtual column");	}

	/** Get Attribute Name 8.
		@return Name of the Attribute 8
	  */
	public String getAttributeName8 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeName8);
	}

	/** Set Attribute Name 9.
		@param AttributeName9 
		Name of the Attribute 9
	  */
	public void setAttributeName9 (String AttributeName9)
	{
		throw new IllegalArgumentException ("AttributeName9 is virtual column");	}

	/** Get Attribute Name 9.
		@return Name of the Attribute 9
	  */
	public String getAttributeName9 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeName9);
	}

	public I_C_BPartner getC_BPartner() throws Exception 
    {
        Class<?> clazz = MTable.getClass(I_C_BPartner.Table_Name);
        I_C_BPartner result = null;
        try	{
	        Constructor<?> constructor = null;
	    	constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, int.class, String.class});
    	    result = (I_C_BPartner)constructor.newInstance(new Object[] {getCtx(), new Integer(getC_BPartner_ID()), get_TrxName()});
        } catch (Exception e) {
	        log.log(Level.SEVERE, "(id) - Table=" + Table_Name + ",Class=" + clazz, e);
	        log.saveError("Error", "Table=" + Table_Name + ",Class=" + clazz);
           throw e;
        }
        return result;
    }

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	public I_C_BPartner_Location getC_BPartner_Location() throws Exception 
    {
        Class<?> clazz = MTable.getClass(I_C_BPartner_Location.Table_Name);
        I_C_BPartner_Location result = null;
        try	{
	        Constructor<?> constructor = null;
	    	constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, int.class, String.class});
    	    result = (I_C_BPartner_Location)constructor.newInstance(new Object[] {getCtx(), new Integer(getC_BPartner_Location_ID()), get_TrxName()});
        } catch (Exception e) {
	        log.log(Level.SEVERE, "(id) - Table=" + Table_Name + ",Class=" + clazz, e);
	        log.saveError("Error", "Table=" + Table_Name + ",Class=" + clazz);
           throw e;
        }
        return result;
    }

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Field Amt 1.
		@param FieldAmt1 Field Amt 1	  */
	public void setFieldAmt1 (BigDecimal FieldAmt1)
	{
		set_Value (COLUMNNAME_FieldAmt1, FieldAmt1);
	}

	/** Get Field Amt 1.
		@return Field Amt 1	  */
	public BigDecimal getFieldAmt1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 10.
		@param FieldAmt10 Field Amt 10	  */
	public void setFieldAmt10 (BigDecimal FieldAmt10)
	{
		set_Value (COLUMNNAME_FieldAmt10, FieldAmt10);
	}

	/** Get Field Amt 10.
		@return Field Amt 10	  */
	public BigDecimal getFieldAmt10 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt10);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 2.
		@param FieldAmt2 Field Amt 2	  */
	public void setFieldAmt2 (BigDecimal FieldAmt2)
	{
		set_Value (COLUMNNAME_FieldAmt2, FieldAmt2);
	}

	/** Get Field Amt 2.
		@return Field Amt 2	  */
	public BigDecimal getFieldAmt2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 3.
		@param FieldAmt3 Field Amt 3	  */
	public void setFieldAmt3 (BigDecimal FieldAmt3)
	{
		set_Value (COLUMNNAME_FieldAmt3, FieldAmt3);
	}

	/** Get Field Amt 3.
		@return Field Amt 3	  */
	public BigDecimal getFieldAmt3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 4.
		@param FieldAmt4 Field Amt 4	  */
	public void setFieldAmt4 (BigDecimal FieldAmt4)
	{
		set_Value (COLUMNNAME_FieldAmt4, FieldAmt4);
	}

	/** Get Field Amt 4.
		@return Field Amt 4	  */
	public BigDecimal getFieldAmt4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 5.
		@param FieldAmt5 Field Amt 5	  */
	public void setFieldAmt5 (BigDecimal FieldAmt5)
	{
		set_Value (COLUMNNAME_FieldAmt5, FieldAmt5);
	}

	/** Get Field Amt 5.
		@return Field Amt 5	  */
	public BigDecimal getFieldAmt5 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt5);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 6.
		@param FieldAmt6 Field Amt 6	  */
	public void setFieldAmt6 (BigDecimal FieldAmt6)
	{
		set_Value (COLUMNNAME_FieldAmt6, FieldAmt6);
	}

	/** Get Field Amt 6.
		@return Field Amt 6	  */
	public BigDecimal getFieldAmt6 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt6);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 7.
		@param FieldAmt7 Field Amt 7	  */
	public void setFieldAmt7 (BigDecimal FieldAmt7)
	{
		set_Value (COLUMNNAME_FieldAmt7, FieldAmt7);
	}

	/** Get Field Amt 7.
		@return Field Amt 7	  */
	public BigDecimal getFieldAmt7 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt7);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 8.
		@param FieldAmt8 Field Amt 8	  */
	public void setFieldAmt8 (BigDecimal FieldAmt8)
	{
		set_Value (COLUMNNAME_FieldAmt8, FieldAmt8);
	}

	/** Get Field Amt 8.
		@return Field Amt 8	  */
	public BigDecimal getFieldAmt8 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt8);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 9.
		@param FieldAmt9 Field Amt 9	  */
	public void setFieldAmt9 (BigDecimal FieldAmt9)
	{
		set_Value (COLUMNNAME_FieldAmt9, FieldAmt9);
	}

	/** Get Field Amt 9.
		@return Field Amt 9	  */
	public BigDecimal getFieldAmt9 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt9);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** LCO_DIAN_Concept_ID AD_Reference_ID=1000005 */
	public static final int LCO_DIAN_CONCEPT_ID_AD_Reference_ID=1000005;
	/** Set DIAN Concept.
		@param LCO_DIAN_Concept_ID DIAN Concept	  */
	public void setLCO_DIAN_Concept_ID (int LCO_DIAN_Concept_ID)
	{
		if (LCO_DIAN_Concept_ID < 1)
			 throw new IllegalArgumentException ("LCO_DIAN_Concept_ID is mandatory.");
		set_Value (COLUMNNAME_LCO_DIAN_Concept_ID, Integer.valueOf(LCO_DIAN_Concept_ID));
	}

	/** Get DIAN Concept.
		@return DIAN Concept	  */
	public int getLCO_DIAN_Concept_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LCO_DIAN_Concept_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.globalqss.model.I_LCO_DIAN_SendSchedule getLCO_DIAN_SendSchedule() throws Exception 
    {
        Class<?> clazz = MTable.getClass(org.globalqss.model.I_LCO_DIAN_SendSchedule.Table_Name);
        org.globalqss.model.I_LCO_DIAN_SendSchedule result = null;
        try	{
	        Constructor<?> constructor = null;
	    	constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, int.class, String.class});
    	    result = (org.globalqss.model.I_LCO_DIAN_SendSchedule)constructor.newInstance(new Object[] {getCtx(), new Integer(getLCO_DIAN_SendSchedule_ID()), get_TrxName()});
        } catch (Exception e) {
	        log.log(Level.SEVERE, "(id) - Table=" + Table_Name + ",Class=" + clazz, e);
	        log.saveError("Error", "Table=" + Table_Name + ",Class=" + clazz);
           throw e;
        }
        return result;
    }

	/** Set DIAN Send Schedule.
		@param LCO_DIAN_SendSchedule_ID DIAN Send Schedule	  */
	public void setLCO_DIAN_SendSchedule_ID (int LCO_DIAN_SendSchedule_ID)
	{
		if (LCO_DIAN_SendSchedule_ID < 1) 
			set_Value (COLUMNNAME_LCO_DIAN_SendSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_LCO_DIAN_SendSchedule_ID, Integer.valueOf(LCO_DIAN_SendSchedule_ID));
	}

	/** Get DIAN Send Schedule.
		@return DIAN Send Schedule	  */
	public int getLCO_DIAN_SendSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LCO_DIAN_SendSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DIAN Send Schedule Line.
		@param LCO_DIAN_SendScheduleLine_ID DIAN Send Schedule Line	  */
	public void setLCO_DIAN_SendScheduleLine_ID (int LCO_DIAN_SendScheduleLine_ID)
	{
		if (LCO_DIAN_SendScheduleLine_ID < 1)
			 throw new IllegalArgumentException ("LCO_DIAN_SendScheduleLine_ID is mandatory.");
		set_ValueNoCheck (COLUMNNAME_LCO_DIAN_SendScheduleLine_ID, Integer.valueOf(LCO_DIAN_SendScheduleLine_ID));
	}

	/** Get DIAN Send Schedule Line.
		@return DIAN Send Schedule Line	  */
	public int getLCO_DIAN_SendScheduleLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LCO_DIAN_SendScheduleLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.globalqss.model.I_LCO_DIAN_XML_Header getLCO_DIAN_XML_Header() throws Exception 
    {
        Class<?> clazz = MTable.getClass(org.globalqss.model.I_LCO_DIAN_XML_Header.Table_Name);
        org.globalqss.model.I_LCO_DIAN_XML_Header result = null;
        try	{
	        Constructor<?> constructor = null;
	    	constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, int.class, String.class});
    	    result = (org.globalqss.model.I_LCO_DIAN_XML_Header)constructor.newInstance(new Object[] {getCtx(), new Integer(getLCO_DIAN_XML_Header_ID()), get_TrxName()});
        } catch (Exception e) {
	        log.log(Level.SEVERE, "(id) - Table=" + Table_Name + ",Class=" + clazz, e);
	        log.saveError("Error", "Table=" + Table_Name + ",Class=" + clazz);
           throw e;
        }
        return result;
    }

	/** Set DIAN XML Header.
		@param LCO_DIAN_XML_Header_ID DIAN XML Header	  */
	public void setLCO_DIAN_XML_Header_ID (int LCO_DIAN_XML_Header_ID)
	{
		if (LCO_DIAN_XML_Header_ID < 1) 
			set_Value (COLUMNNAME_LCO_DIAN_XML_Header_ID, null);
		else 
			set_Value (COLUMNNAME_LCO_DIAN_XML_Header_ID, Integer.valueOf(LCO_DIAN_XML_Header_ID));
	}

	/** Get DIAN XML Header.
		@return DIAN XML Header	  */
	public int getLCO_DIAN_XML_Header_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LCO_DIAN_XML_Header_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}