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

/** Generated Model for LCO_DIAN_SendScheduleLine
 *  @author iDempiere (generated) 
 *  @version Release 8.2 - $Id$ */
public class X_LCO_DIAN_SendScheduleLine extends PO implements I_LCO_DIAN_SendScheduleLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210606L;

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
      StringBuilder sb = new StringBuilder ("X_LCO_DIAN_SendScheduleLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Attribute Name 1.
		@param AttributeNm1 
		Name of the Attribute 1
	  */
	public void setAttributeNm1 (String AttributeNm1)
	{
		throw new IllegalArgumentException ("AttributeNm1 is virtual column");	}

	/** Get Attribute Name 1.
		@return Name of the Attribute 1
	  */
	public String getAttributeNm1 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm1);
	}

	/** Set Attribute Name 10.
		@param AttributeNm10 
		Name of the Attribute 10
	  */
	public void setAttributeNm10 (String AttributeNm10)
	{
		throw new IllegalArgumentException ("AttributeNm10 is virtual column");	}

	/** Get Attribute Name 10.
		@return Name of the Attribute 10
	  */
	public String getAttributeNm10 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm10);
	}

	/** Set Attribute Name 11.
		@param AttributeNm11 
		Name of the Attribute 11
	  */
	public void setAttributeNm11 (String AttributeNm11)
	{
		throw new IllegalArgumentException ("AttributeNm11 is virtual column");	}

	/** Get Attribute Name 11.
		@return Name of the Attribute 11
	  */
	public String getAttributeNm11 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm11);
	}

	/** Set Attribute Name 12.
		@param AttributeNm12 
		Name of the Attribute 12
	  */
	public void setAttributeNm12 (String AttributeNm12)
	{
		throw new IllegalArgumentException ("AttributeNm12 is virtual column");	}

	/** Get Attribute Name 12.
		@return Name of the Attribute 12
	  */
	public String getAttributeNm12 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm12);
	}

	/** Set Attribute Name 13.
		@param AttributeNm13 
		Name of the Attribute 13
	  */
	public void setAttributeNm13 (String AttributeNm13)
	{
		throw new IllegalArgumentException ("AttributeNm13 is virtual column");	}

	/** Get Attribute Name 13.
		@return Name of the Attribute 13
	  */
	public String getAttributeNm13 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm13);
	}

	/** Set Attribute Name 14.
		@param AttributeNm14 
		Name of the Attribute 14
	  */
	public void setAttributeNm14 (String AttributeNm14)
	{
		throw new IllegalArgumentException ("AttributeNm14 is virtual column");	}

	/** Get Attribute Name 14.
		@return Name of the Attribute 14
	  */
	public String getAttributeNm14 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm14);
	}

	/** Set Attribute Name 15.
		@param AttributeNm15 
		Name of the Attribute 15
	  */
	public void setAttributeNm15 (String AttributeNm15)
	{
		throw new IllegalArgumentException ("AttributeNm15 is virtual column");	}

	/** Get Attribute Name 15.
		@return Name of the Attribute 15
	  */
	public String getAttributeNm15 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm15);
	}

	/** Set Attribute Name 16.
		@param AttributeNm16 
		Name of the Attribute 16
	  */
	public void setAttributeNm16 (String AttributeNm16)
	{
		throw new IllegalArgumentException ("AttributeNm16 is virtual column");	}

	/** Get Attribute Name 16.
		@return Name of the Attribute 16
	  */
	public String getAttributeNm16 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm16);
	}

	/** Set Attribute Name 17.
		@param AttributeNm17 
		Name of the Attribute 17
	  */
	public void setAttributeNm17 (String AttributeNm17)
	{
		throw new IllegalArgumentException ("AttributeNm17 is virtual column");	}

	/** Get Attribute Name 17.
		@return Name of the Attribute 17
	  */
	public String getAttributeNm17 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm17);
	}

	/** Set Attribute Name 18.
		@param AttributeNm18 
		Name of the Attribute 18
	  */
	public void setAttributeNm18 (String AttributeNm18)
	{
		throw new IllegalArgumentException ("AttributeNm18 is virtual column");	}

	/** Get Attribute Name 18.
		@return Name of the Attribute 18
	  */
	public String getAttributeNm18 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm18);
	}

	/** Set Attribute Name 19.
		@param AttributeNm19 
		Name of the Attribute 19
	  */
	public void setAttributeNm19 (String AttributeNm19)
	{
		throw new IllegalArgumentException ("AttributeNm19 is virtual column");	}

	/** Get Attribute Name 19.
		@return Name of the Attribute 19
	  */
	public String getAttributeNm19 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm19);
	}

	/** Set Attribute Name 2.
		@param AttributeNm2 
		Name of the Attribute 2
	  */
	public void setAttributeNm2 (String AttributeNm2)
	{
		throw new IllegalArgumentException ("AttributeNm2 is virtual column");	}

	/** Get Attribute Name 2.
		@return Name of the Attribute 2
	  */
	public String getAttributeNm2 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm2);
	}

	/** Set Attribute Name 20.
		@param AttributeNm20 
		Name of the Attribute 20
	  */
	public void setAttributeNm20 (String AttributeNm20)
	{
		throw new IllegalArgumentException ("AttributeNm20 is virtual column");	}

	/** Get Attribute Name 20.
		@return Name of the Attribute 20
	  */
	public String getAttributeNm20 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm20);
	}

	/** Set Attribute Name 21.
		@param AttributeNm21 
		Name of the Attribute 21
	  */
	public void setAttributeNm21 (String AttributeNm21)
	{
		throw new IllegalArgumentException ("AttributeNm21 is virtual column");	}

	/** Get Attribute Name 21.
		@return Name of the Attribute 21
	  */
	public String getAttributeNm21 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm21);
	}

	/** Set Attribute Name 22.
		@param AttributeNm22 
		Name of the Attribute 22
	  */
	public void setAttributeNm22 (String AttributeNm22)
	{
		throw new IllegalArgumentException ("AttributeNm22 is virtual column");	}

	/** Get Attribute Name 22.
		@return Name of the Attribute 22
	  */
	public String getAttributeNm22 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm22);
	}

	/** Set Attribute Name 23.
		@param AttributeNm23 
		Name of the Attribute 23
	  */
	public void setAttributeNm23 (String AttributeNm23)
	{
		throw new IllegalArgumentException ("AttributeNm23 is virtual column");	}

	/** Get Attribute Name 23.
		@return Name of the Attribute 23
	  */
	public String getAttributeNm23 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm23);
	}

	/** Set Attribute Name 24.
		@param AttributeNm24 
		Name of the Attribute 24
	  */
	public void setAttributeNm24 (String AttributeNm24)
	{
		throw new IllegalArgumentException ("AttributeNm24 is virtual column");	}

	/** Get Attribute Name 24.
		@return Name of the Attribute 24
	  */
	public String getAttributeNm24 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm24);
	}

	/** Set Attribute Name 25.
		@param AttributeNm25 
		Name of the Attribute 25
	  */
	public void setAttributeNm25 (String AttributeNm25)
	{
		throw new IllegalArgumentException ("AttributeNm25 is virtual column");	}

	/** Get Attribute Name 25.
		@return Name of the Attribute 25
	  */
	public String getAttributeNm25 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm25);
	}

	/** Set Attribute Name 26.
		@param AttributeNm26 
		Name of the Attribute 26
	  */
	public void setAttributeNm26 (String AttributeNm26)
	{
		throw new IllegalArgumentException ("AttributeNm26 is virtual column");	}

	/** Get Attribute Name 26.
		@return Name of the Attribute 26
	  */
	public String getAttributeNm26 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm26);
	}

	/** Set Attribute Name 27.
		@param AttributeNm27 
		Name of the Attribute 27
	  */
	public void setAttributeNm27 (String AttributeNm27)
	{
		throw new IllegalArgumentException ("AttributeNm27 is virtual column");	}

	/** Get Attribute Name 27.
		@return Name of the Attribute 27
	  */
	public String getAttributeNm27 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm27);
	}

	/** Set Attribute Name 28.
		@param AttributeNm28 
		Name of the Attribute 28
	  */
	public void setAttributeNm28 (String AttributeNm28)
	{
		throw new IllegalArgumentException ("AttributeNm28 is virtual column");	}

	/** Get Attribute Name 28.
		@return Name of the Attribute 28
	  */
	public String getAttributeNm28 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm28);
	}

	/** Set Attribute Name 29.
		@param AttributeNm29 
		Name of the Attribute 29
	  */
	public void setAttributeNm29 (String AttributeNm29)
	{
		throw new IllegalArgumentException ("AttributeNm29 is virtual column");	}

	/** Get Attribute Name 29.
		@return Name of the Attribute 29
	  */
	public String getAttributeNm29 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm29);
	}

	/** Set Attribute Name 3.
		@param AttributeNm3 
		Name of the Attribute 3
	  */
	public void setAttributeNm3 (String AttributeNm3)
	{
		throw new IllegalArgumentException ("AttributeNm3 is virtual column");	}

	/** Get Attribute Name 3.
		@return Name of the Attribute 3
	  */
	public String getAttributeNm3 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm3);
	}

	/** Set Attribute Name 30.
		@param AttributeNm30 
		Name of the Attribute 30
	  */
	public void setAttributeNm30 (String AttributeNm30)
	{
		throw new IllegalArgumentException ("AttributeNm30 is virtual column");	}

	/** Get Attribute Name 30.
		@return Name of the Attribute 30
	  */
	public String getAttributeNm30 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm30);
	}

	/** Set Attribute Name 31.
		@param AttributeNm31 
		Name of the Attribute 31
	  */
	public void setAttributeNm31 (String AttributeNm31)
	{
		throw new IllegalArgumentException ("AttributeNm31 is virtual column");	}

	/** Get Attribute Name 31.
		@return Name of the Attribute 31
	  */
	public String getAttributeNm31 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm31);
	}

	/** Set Attribute Name 32.
		@param AttributeNm32 
		Name of the Attribute 32
	  */
	public void setAttributeNm32 (String AttributeNm32)
	{
		throw new IllegalArgumentException ("AttributeNm32 is virtual column");	}

	/** Get Attribute Name 32.
		@return Name of the Attribute 32
	  */
	public String getAttributeNm32 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm32);
	}

	/** Set Attribute Name 33.
		@param AttributeNm33 
		Name of the Attribute 33
	  */
	public void setAttributeNm33 (String AttributeNm33)
	{
		throw new IllegalArgumentException ("AttributeNm33 is virtual column");	}

	/** Get Attribute Name 33.
		@return Name of the Attribute 33
	  */
	public String getAttributeNm33 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm33);
	}

	/** Set Attribute Name 34.
		@param AttributeNm34 
		Name of the Attribute 34
	  */
	public void setAttributeNm34 (String AttributeNm34)
	{
		throw new IllegalArgumentException ("AttributeNm34 is virtual column");	}

	/** Get Attribute Name 34.
		@return Name of the Attribute 34
	  */
	public String getAttributeNm34 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm34);
	}

	/** Set Attribute Name 35.
		@param AttributeNm35 
		Name of the Attribute 35
	  */
	public void setAttributeNm35 (String AttributeNm35)
	{
		throw new IllegalArgumentException ("AttributeNm35 is virtual column");	}

	/** Get Attribute Name 35.
		@return Name of the Attribute 35
	  */
	public String getAttributeNm35 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm35);
	}

	/** Set Attribute Name 36.
		@param AttributeNm36 
		Name of the Attribute 36
	  */
	public void setAttributeNm36 (String AttributeNm36)
	{
		throw new IllegalArgumentException ("AttributeNm36 is virtual column");	}

	/** Get Attribute Name 36.
		@return Name of the Attribute 36
	  */
	public String getAttributeNm36 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm36);
	}

	/** Set Attribute Name 37.
		@param AttributeNm37 
		Name of the Attribute 37
	  */
	public void setAttributeNm37 (String AttributeNm37)
	{
		throw new IllegalArgumentException ("AttributeNm37 is virtual column");	}

	/** Get Attribute Name 37.
		@return Name of the Attribute 37
	  */
	public String getAttributeNm37 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm37);
	}

	/** Set Attribute Name 38.
		@param AttributeNm38 
		Name of the Attribute 38
	  */
	public void setAttributeNm38 (String AttributeNm38)
	{
		throw new IllegalArgumentException ("AttributeNm38 is virtual column");	}

	/** Get Attribute Name 38.
		@return Name of the Attribute 38
	  */
	public String getAttributeNm38 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm38);
	}

	/** Set Attribute Name 39.
		@param AttributeNm39 
		Name of the Attribute 39
	  */
	public void setAttributeNm39 (String AttributeNm39)
	{
		throw new IllegalArgumentException ("AttributeNm39 is virtual column");	}

	/** Get Attribute Name 39.
		@return Name of the Attribute 39
	  */
	public String getAttributeNm39 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm39);
	}

	/** Set Attribute Name 4.
		@param AttributeNm4 
		Name of the Attribute 4
	  */
	public void setAttributeNm4 (String AttributeNm4)
	{
		throw new IllegalArgumentException ("AttributeNm4 is virtual column");	}

	/** Get Attribute Name 4.
		@return Name of the Attribute 4
	  */
	public String getAttributeNm4 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm4);
	}

	/** Set Attribute Name 40.
		@param AttributeNm40 
		Name of the Attribute 40
	  */
	public void setAttributeNm40 (String AttributeNm40)
	{
		throw new IllegalArgumentException ("AttributeNm40 is virtual column");	}

	/** Get Attribute Name 40.
		@return Name of the Attribute 40
	  */
	public String getAttributeNm40 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm40);
	}

	/** Set Attribute Name 41.
		@param AttributeNm41 
		Name of the Attribute 41
	  */
	public void setAttributeNm41 (String AttributeNm41)
	{
		throw new IllegalArgumentException ("AttributeNm41 is virtual column");	}

	/** Get Attribute Name 41.
		@return Name of the Attribute 41
	  */
	public String getAttributeNm41 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm41);
	}

	/** Set Attribute Name 42.
		@param AttributeNm42 
		Name of the Attribute 42
	  */
	public void setAttributeNm42 (String AttributeNm42)
	{
		throw new IllegalArgumentException ("AttributeNm42 is virtual column");	}

	/** Get Attribute Name 42.
		@return Name of the Attribute 42
	  */
	public String getAttributeNm42 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm42);
	}

	/** Set Attribute Name 43.
		@param AttributeNm43 
		Name of the Attribute 43
	  */
	public void setAttributeNm43 (String AttributeNm43)
	{
		throw new IllegalArgumentException ("AttributeNm43 is virtual column");	}

	/** Get Attribute Name 43.
		@return Name of the Attribute 43
	  */
	public String getAttributeNm43 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm43);
	}

	/** Set Attribute Name 44.
		@param AttributeNm44 
		Name of the Attribute 44
	  */
	public void setAttributeNm44 (String AttributeNm44)
	{
		throw new IllegalArgumentException ("AttributeNm44 is virtual column");	}

	/** Get Attribute Name 44.
		@return Name of the Attribute 44
	  */
	public String getAttributeNm44 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm44);
	}

	/** Set Attribute Name 45.
		@param AttributeNm45 
		Name of the Attribute 45
	  */
	public void setAttributeNm45 (String AttributeNm45)
	{
		throw new IllegalArgumentException ("AttributeNm45 is virtual column");	}

	/** Get Attribute Name 45.
		@return Name of the Attribute 45
	  */
	public String getAttributeNm45 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm45);
	}

	/** Set Attribute Name 46.
		@param AttributeNm46 
		Name of the Attribute 46
	  */
	public void setAttributeNm46 (String AttributeNm46)
	{
		throw new IllegalArgumentException ("AttributeNm46 is virtual column");	}

	/** Get Attribute Name 46.
		@return Name of the Attribute 46
	  */
	public String getAttributeNm46 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm46);
	}

	/** Set Attribute Name 47.
		@param AttributeNm47 
		Name of the Attribute 47
	  */
	public void setAttributeNm47 (String AttributeNm47)
	{
		throw new IllegalArgumentException ("AttributeNm47 is virtual column");	}

	/** Get Attribute Name 47.
		@return Name of the Attribute 47
	  */
	public String getAttributeNm47 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm47);
	}

	/** Set Attribute Name 48.
		@param AttributeNm48 
		Name of the Attribute 48
	  */
	public void setAttributeNm48 (String AttributeNm48)
	{
		throw new IllegalArgumentException ("AttributeNm48 is virtual column");	}

	/** Get Attribute Name 48.
		@return Name of the Attribute 48
	  */
	public String getAttributeNm48 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm48);
	}

	/** Set Attribute Name 49.
		@param AttributeNm49 
		Name of the Attribute 49
	  */
	public void setAttributeNm49 (String AttributeNm49)
	{
		throw new IllegalArgumentException ("AttributeNm49 is virtual column");	}

	/** Get Attribute Name 49.
		@return Name of the Attribute 49
	  */
	public String getAttributeNm49 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm49);
	}

	/** Set Attribute Name 5.
		@param AttributeNm5 
		Name of the Attribute 5
	  */
	public void setAttributeNm5 (String AttributeNm5)
	{
		throw new IllegalArgumentException ("AttributeNm5 is virtual column");	}

	/** Get Attribute Name 5.
		@return Name of the Attribute 5
	  */
	public String getAttributeNm5 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm5);
	}

	/** Set Attribute Name 50.
		@param AttributeNm50 
		Name of the Attribute 50
	  */
	public void setAttributeNm50 (String AttributeNm50)
	{
		throw new IllegalArgumentException ("AttributeNm50 is virtual column");	}

	/** Get Attribute Name 50.
		@return Name of the Attribute 50
	  */
	public String getAttributeNm50 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm50);
	}

	/** Set Attribute Name 6.
		@param AttributeNm6 
		Name of the Attribute 6
	  */
	public void setAttributeNm6 (String AttributeNm6)
	{
		throw new IllegalArgumentException ("AttributeNm6 is virtual column");	}

	/** Get Attribute Name 6.
		@return Name of the Attribute 6
	  */
	public String getAttributeNm6 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm6);
	}

	/** Set Attribute Name 7.
		@param AttributeNm7 
		Name of the Attribute 7
	  */
	public void setAttributeNm7 (String AttributeNm7)
	{
		throw new IllegalArgumentException ("AttributeNm7 is virtual column");	}

	/** Get Attribute Name 7.
		@return Name of the Attribute 7
	  */
	public String getAttributeNm7 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm7);
	}

	/** Set Attribute Name 8.
		@param AttributeNm8 
		Name of the Attribute 8
	  */
	public void setAttributeNm8 (String AttributeNm8)
	{
		throw new IllegalArgumentException ("AttributeNm8 is virtual column");	}

	/** Get Attribute Name 8.
		@return Name of the Attribute 8
	  */
	public String getAttributeNm8 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm8);
	}

	/** Set Attribute Name 9.
		@param AttributeNm9 
		Name of the Attribute 9
	  */
	public void setAttributeNm9 (String AttributeNm9)
	{
		throw new IllegalArgumentException ("AttributeNm9 is virtual column");	}

	/** Get Attribute Name 9.
		@return Name of the Attribute 9
	  */
	public String getAttributeNm9 () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNm9);
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

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());	}

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

	public org.compiere.model.I_C_BPartner getC_BPartnerRelation() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartnerRelation_ID(), get_TrxName());	}

	/** Set Related Partner.
		@param C_BPartnerRelation_ID 
		Related Business Partner
	  */
	public void setC_BPartnerRelation_ID (int C_BPartnerRelation_ID)
	{
		if (C_BPartnerRelation_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerRelation_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartnerRelation_ID, Integer.valueOf(C_BPartnerRelation_ID));
	}

	/** Get Related Partner.
		@return Related Business Partner
	  */
	public int getC_BPartnerRelation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartnerRelation_ID);
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

	/** Set Field Amt 11.
		@param FieldAmt11 Field Amt 11	  */
	public void setFieldAmt11 (BigDecimal FieldAmt11)
	{
		set_Value (COLUMNNAME_FieldAmt11, FieldAmt11);
	}

	/** Get Field Amt 11.
		@return Field Amt 11	  */
	public BigDecimal getFieldAmt11 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt11);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 12.
		@param FieldAmt12 Field Amt 12	  */
	public void setFieldAmt12 (BigDecimal FieldAmt12)
	{
		set_Value (COLUMNNAME_FieldAmt12, FieldAmt12);
	}

	/** Get Field Amt 12.
		@return Field Amt 12	  */
	public BigDecimal getFieldAmt12 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt12);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 13.
		@param FieldAmt13 Field Amt 13	  */
	public void setFieldAmt13 (BigDecimal FieldAmt13)
	{
		set_Value (COLUMNNAME_FieldAmt13, FieldAmt13);
	}

	/** Get Field Amt 13.
		@return Field Amt 13	  */
	public BigDecimal getFieldAmt13 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt13);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 14.
		@param FieldAmt14 Field Amt 14	  */
	public void setFieldAmt14 (BigDecimal FieldAmt14)
	{
		set_Value (COLUMNNAME_FieldAmt14, FieldAmt14);
	}

	/** Get Field Amt 14.
		@return Field Amt 14	  */
	public BigDecimal getFieldAmt14 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt14);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 15.
		@param FieldAmt15 Field Amt 15	  */
	public void setFieldAmt15 (BigDecimal FieldAmt15)
	{
		set_Value (COLUMNNAME_FieldAmt15, FieldAmt15);
	}

	/** Get Field Amt 15.
		@return Field Amt 15	  */
	public BigDecimal getFieldAmt15 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt15);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 16.
		@param FieldAmt16 Field Amt 16	  */
	public void setFieldAmt16 (BigDecimal FieldAmt16)
	{
		set_Value (COLUMNNAME_FieldAmt16, FieldAmt16);
	}

	/** Get Field Amt 16.
		@return Field Amt 16	  */
	public BigDecimal getFieldAmt16 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt16);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 17.
		@param FieldAmt17 Field Amt 17	  */
	public void setFieldAmt17 (BigDecimal FieldAmt17)
	{
		set_Value (COLUMNNAME_FieldAmt17, FieldAmt17);
	}

	/** Get Field Amt 17.
		@return Field Amt 17	  */
	public BigDecimal getFieldAmt17 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt17);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 18.
		@param FieldAmt18 Field Amt 18	  */
	public void setFieldAmt18 (BigDecimal FieldAmt18)
	{
		set_Value (COLUMNNAME_FieldAmt18, FieldAmt18);
	}

	/** Get Field Amt 18.
		@return Field Amt 18	  */
	public BigDecimal getFieldAmt18 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt18);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 19.
		@param FieldAmt19 Field Amt 19	  */
	public void setFieldAmt19 (BigDecimal FieldAmt19)
	{
		set_Value (COLUMNNAME_FieldAmt19, FieldAmt19);
	}

	/** Get Field Amt 19.
		@return Field Amt 19	  */
	public BigDecimal getFieldAmt19 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt19);
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

	/** Set Field Amt 20.
		@param FieldAmt20 Field Amt 20	  */
	public void setFieldAmt20 (BigDecimal FieldAmt20)
	{
		set_Value (COLUMNNAME_FieldAmt20, FieldAmt20);
	}

	/** Get Field Amt 20.
		@return Field Amt 20	  */
	public BigDecimal getFieldAmt20 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt20);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 21.
		@param FieldAmt21 Field Amt 21	  */
	public void setFieldAmt21 (BigDecimal FieldAmt21)
	{
		set_Value (COLUMNNAME_FieldAmt21, FieldAmt21);
	}

	/** Get Field Amt 21.
		@return Field Amt 21	  */
	public BigDecimal getFieldAmt21 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt21);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 22.
		@param FieldAmt22 Field Amt 22	  */
	public void setFieldAmt22 (BigDecimal FieldAmt22)
	{
		set_Value (COLUMNNAME_FieldAmt22, FieldAmt22);
	}

	/** Get Field Amt 22.
		@return Field Amt 22	  */
	public BigDecimal getFieldAmt22 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt22);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 23.
		@param FieldAmt23 Field Amt 23	  */
	public void setFieldAmt23 (BigDecimal FieldAmt23)
	{
		set_Value (COLUMNNAME_FieldAmt23, FieldAmt23);
	}

	/** Get Field Amt 23.
		@return Field Amt 23	  */
	public BigDecimal getFieldAmt23 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt23);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 24.
		@param FieldAmt24 Field Amt 24	  */
	public void setFieldAmt24 (BigDecimal FieldAmt24)
	{
		set_Value (COLUMNNAME_FieldAmt24, FieldAmt24);
	}

	/** Get Field Amt 24.
		@return Field Amt 24	  */
	public BigDecimal getFieldAmt24 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt24);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 25.
		@param FieldAmt25 Field Amt 25	  */
	public void setFieldAmt25 (BigDecimal FieldAmt25)
	{
		set_Value (COLUMNNAME_FieldAmt25, FieldAmt25);
	}

	/** Get Field Amt 25.
		@return Field Amt 25	  */
	public BigDecimal getFieldAmt25 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt25);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 26.
		@param FieldAmt26 Field Amt 26	  */
	public void setFieldAmt26 (BigDecimal FieldAmt26)
	{
		set_Value (COLUMNNAME_FieldAmt26, FieldAmt26);
	}

	/** Get Field Amt 26.
		@return Field Amt 26	  */
	public BigDecimal getFieldAmt26 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt26);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 27.
		@param FieldAmt27 Field Amt 27	  */
	public void setFieldAmt27 (BigDecimal FieldAmt27)
	{
		set_Value (COLUMNNAME_FieldAmt27, FieldAmt27);
	}

	/** Get Field Amt 27.
		@return Field Amt 27	  */
	public BigDecimal getFieldAmt27 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt27);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 28.
		@param FieldAmt28 Field Amt 28	  */
	public void setFieldAmt28 (BigDecimal FieldAmt28)
	{
		set_Value (COLUMNNAME_FieldAmt28, FieldAmt28);
	}

	/** Get Field Amt 28.
		@return Field Amt 28	  */
	public BigDecimal getFieldAmt28 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt28);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 29.
		@param FieldAmt29 Field Amt 29	  */
	public void setFieldAmt29 (BigDecimal FieldAmt29)
	{
		set_Value (COLUMNNAME_FieldAmt29, FieldAmt29);
	}

	/** Get Field Amt 29.
		@return Field Amt 29	  */
	public BigDecimal getFieldAmt29 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt29);
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

	/** Set Field Amt 30.
		@param FieldAmt30 Field Amt 30	  */
	public void setFieldAmt30 (BigDecimal FieldAmt30)
	{
		set_Value (COLUMNNAME_FieldAmt30, FieldAmt30);
	}

	/** Get Field Amt 30.
		@return Field Amt 30	  */
	public BigDecimal getFieldAmt30 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt30);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 31.
		@param FieldAmt31 Field Amt 31	  */
	public void setFieldAmt31 (BigDecimal FieldAmt31)
	{
		set_Value (COLUMNNAME_FieldAmt31, FieldAmt31);
	}

	/** Get Field Amt 31.
		@return Field Amt 31	  */
	public BigDecimal getFieldAmt31 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt31);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 32.
		@param FieldAmt32 Field Amt 32	  */
	public void setFieldAmt32 (BigDecimal FieldAmt32)
	{
		set_Value (COLUMNNAME_FieldAmt32, FieldAmt32);
	}

	/** Get Field Amt 32.
		@return Field Amt 32	  */
	public BigDecimal getFieldAmt32 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt32);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 33.
		@param FieldAmt33 Field Amt 33	  */
	public void setFieldAmt33 (BigDecimal FieldAmt33)
	{
		set_Value (COLUMNNAME_FieldAmt33, FieldAmt33);
	}

	/** Get Field Amt 33.
		@return Field Amt 33	  */
	public BigDecimal getFieldAmt33 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt33);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 34.
		@param FieldAmt34 Field Amt 34	  */
	public void setFieldAmt34 (BigDecimal FieldAmt34)
	{
		set_Value (COLUMNNAME_FieldAmt34, FieldAmt34);
	}

	/** Get Field Amt 34.
		@return Field Amt 34	  */
	public BigDecimal getFieldAmt34 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt34);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 35.
		@param FieldAmt35 Field Amt 35	  */
	public void setFieldAmt35 (BigDecimal FieldAmt35)
	{
		set_Value (COLUMNNAME_FieldAmt35, FieldAmt35);
	}

	/** Get Field Amt 35.
		@return Field Amt 35	  */
	public BigDecimal getFieldAmt35 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt35);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 36.
		@param FieldAmt36 Field Amt 36	  */
	public void setFieldAmt36 (BigDecimal FieldAmt36)
	{
		set_Value (COLUMNNAME_FieldAmt36, FieldAmt36);
	}

	/** Get Field Amt 36.
		@return Field Amt 36	  */
	public BigDecimal getFieldAmt36 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt36);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 37.
		@param FieldAmt37 Field Amt 37	  */
	public void setFieldAmt37 (BigDecimal FieldAmt37)
	{
		set_Value (COLUMNNAME_FieldAmt37, FieldAmt37);
	}

	/** Get Field Amt 37.
		@return Field Amt 37	  */
	public BigDecimal getFieldAmt37 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt37);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 38.
		@param FieldAmt38 Field Amt 38	  */
	public void setFieldAmt38 (BigDecimal FieldAmt38)
	{
		set_Value (COLUMNNAME_FieldAmt38, FieldAmt38);
	}

	/** Get Field Amt 38.
		@return Field Amt 38	  */
	public BigDecimal getFieldAmt38 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt38);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 39.
		@param FieldAmt39 Field Amt 39	  */
	public void setFieldAmt39 (BigDecimal FieldAmt39)
	{
		set_Value (COLUMNNAME_FieldAmt39, FieldAmt39);
	}

	/** Get Field Amt 39.
		@return Field Amt 39	  */
	public BigDecimal getFieldAmt39 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt39);
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

	/** Set Field Amt 40.
		@param FieldAmt40 Field Amt 40	  */
	public void setFieldAmt40 (BigDecimal FieldAmt40)
	{
		set_Value (COLUMNNAME_FieldAmt40, FieldAmt40);
	}

	/** Get Field Amt 40.
		@return Field Amt 40	  */
	public BigDecimal getFieldAmt40 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt40);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 41.
		@param FieldAmt41 Field Amt 41	  */
	public void setFieldAmt41 (BigDecimal FieldAmt41)
	{
		set_Value (COLUMNNAME_FieldAmt41, FieldAmt41);
	}

	/** Get Field Amt 41.
		@return Field Amt 41	  */
	public BigDecimal getFieldAmt41 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt41);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 42.
		@param FieldAmt42 Field Amt 42	  */
	public void setFieldAmt42 (BigDecimal FieldAmt42)
	{
		set_Value (COLUMNNAME_FieldAmt42, FieldAmt42);
	}

	/** Get Field Amt 42.
		@return Field Amt 42	  */
	public BigDecimal getFieldAmt42 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt42);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 43.
		@param FieldAmt43 Field Amt 43	  */
	public void setFieldAmt43 (BigDecimal FieldAmt43)
	{
		set_Value (COLUMNNAME_FieldAmt43, FieldAmt43);
	}

	/** Get Field Amt 43.
		@return Field Amt 43	  */
	public BigDecimal getFieldAmt43 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt43);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 44.
		@param FieldAmt44 Field Amt 44	  */
	public void setFieldAmt44 (BigDecimal FieldAmt44)
	{
		set_Value (COLUMNNAME_FieldAmt44, FieldAmt44);
	}

	/** Get Field Amt 44.
		@return Field Amt 44	  */
	public BigDecimal getFieldAmt44 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt44);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 45.
		@param FieldAmt45 Field Amt 45	  */
	public void setFieldAmt45 (BigDecimal FieldAmt45)
	{
		set_Value (COLUMNNAME_FieldAmt45, FieldAmt45);
	}

	/** Get Field Amt 45.
		@return Field Amt 45	  */
	public BigDecimal getFieldAmt45 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt45);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 46.
		@param FieldAmt46 Field Amt 46	  */
	public void setFieldAmt46 (BigDecimal FieldAmt46)
	{
		set_Value (COLUMNNAME_FieldAmt46, FieldAmt46);
	}

	/** Get Field Amt 46.
		@return Field Amt 46	  */
	public BigDecimal getFieldAmt46 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt46);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 47.
		@param FieldAmt47 Field Amt 47	  */
	public void setFieldAmt47 (BigDecimal FieldAmt47)
	{
		set_Value (COLUMNNAME_FieldAmt47, FieldAmt47);
	}

	/** Get Field Amt 47.
		@return Field Amt 47	  */
	public BigDecimal getFieldAmt47 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt47);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 48.
		@param FieldAmt48 Field Amt 48	  */
	public void setFieldAmt48 (BigDecimal FieldAmt48)
	{
		set_Value (COLUMNNAME_FieldAmt48, FieldAmt48);
	}

	/** Get Field Amt 48.
		@return Field Amt 48	  */
	public BigDecimal getFieldAmt48 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt48);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Field Amt 49.
		@param FieldAmt49 Field Amt 49	  */
	public void setFieldAmt49 (BigDecimal FieldAmt49)
	{
		set_Value (COLUMNNAME_FieldAmt49, FieldAmt49);
	}

	/** Get Field Amt 49.
		@return Field Amt 49	  */
	public BigDecimal getFieldAmt49 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt49);
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

	/** Set Field Amt 50.
		@param FieldAmt50 Field Amt 50	  */
	public void setFieldAmt50 (BigDecimal FieldAmt50)
	{
		set_Value (COLUMNNAME_FieldAmt50, FieldAmt50);
	}

	/** Get Field Amt 50.
		@return Field Amt 50	  */
	public BigDecimal getFieldAmt50 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FieldAmt50);
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

	public org.globalqss.model.I_LCO_DIAN_Concept getLCO_DIAN_Concept() throws RuntimeException
    {
		return (org.globalqss.model.I_LCO_DIAN_Concept)MTable.get(getCtx(), org.globalqss.model.I_LCO_DIAN_Concept.Table_Name)
			.getPO(getLCO_DIAN_Concept_ID(), get_TrxName());	}

	/** Set DIAN Concept.
		@param LCO_DIAN_Concept_ID DIAN Concept	  */
	public void setLCO_DIAN_Concept_ID (int LCO_DIAN_Concept_ID)
	{
		if (LCO_DIAN_Concept_ID < 1) 
			set_Value (COLUMNNAME_LCO_DIAN_Concept_ID, null);
		else 
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

	public org.globalqss.model.I_LCO_DIAN_SendSchedule getLCO_DIAN_SendSchedule() throws RuntimeException
    {
		return (org.globalqss.model.I_LCO_DIAN_SendSchedule)MTable.get(getCtx(), org.globalqss.model.I_LCO_DIAN_SendSchedule.Table_Name)
			.getPO(getLCO_DIAN_SendSchedule_ID(), get_TrxName());	}

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
			set_ValueNoCheck (COLUMNNAME_LCO_DIAN_SendScheduleLine_ID, null);
		else 
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

	/** Set LCO_DIAN_SendScheduleLine_UU.
		@param LCO_DIAN_SendScheduleLine_UU LCO_DIAN_SendScheduleLine_UU	  */
	public void setLCO_DIAN_SendScheduleLine_UU (String LCO_DIAN_SendScheduleLine_UU)
	{
		set_Value (COLUMNNAME_LCO_DIAN_SendScheduleLine_UU, LCO_DIAN_SendScheduleLine_UU);
	}

	/** Get LCO_DIAN_SendScheduleLine_UU.
		@return LCO_DIAN_SendScheduleLine_UU	  */
	public String getLCO_DIAN_SendScheduleLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_LCO_DIAN_SendScheduleLine_UU);
	}

	public org.globalqss.model.I_LCO_DIAN_XML_Header getLCO_DIAN_XML_Header() throws RuntimeException
    {
		return (org.globalqss.model.I_LCO_DIAN_XML_Header)MTable.get(getCtx(), org.globalqss.model.I_LCO_DIAN_XML_Header.Table_Name)
			.getPO(getLCO_DIAN_XML_Header_ID(), get_TrxName());	}

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