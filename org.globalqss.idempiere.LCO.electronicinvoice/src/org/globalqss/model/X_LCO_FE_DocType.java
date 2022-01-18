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

/** Generated Model for LCO_FE_DocType
 *  @author iDempiere (generated) 
 *  @version Release 7.1 - $Id$ */
public class X_LCO_FE_DocType extends PO implements I_LCO_FE_DocType, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200204L;

    /** Standard Constructor */
    public X_LCO_FE_DocType (Properties ctx, int LCO_FE_DocType_ID, String trxName)
    {
      super (ctx, LCO_FE_DocType_ID, trxName);
      /** if (LCO_FE_DocType_ID == 0)
        {
			setDianDocTypeCode (null);
			setDianShortDocType (null);
			setLCO_FE_DocType_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_LCO_FE_DocType (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_LCO_FE_DocType[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** 1-FACTURA DE VENTA = 1 */
	public static final String DIANDOCTYPECODE_1_FACTURADEVENTA = "1";
	/** 2-FACTURA DE EXPORTACIÓN = 2 */
	public static final String DIANDOCTYPECODE_2_FACTURADEEXPORTACIÓN = "2";
	/** 3-FACTURA DE CONTINGENCIA = 3 */
	public static final String DIANDOCTYPECODE_3_FACTURADECONTINGENCIA = "3";
	/** 9-NOTA DEBITO - CREDITO = 9 */
	public static final String DIANDOCTYPECODE_9_NOTADEBITO_CREDITO = "9";
	/** 01-FACTURA DE VENTA = 01 */
	public static final String DIANDOCTYPECODE_01_FACTURADEVENTA = "01";
	/** 02-FACTURA DE EXPORTACIÓN = 02 */
	public static final String DIANDOCTYPECODE_02_FACTURADEEXPORTACIÓN = "02";
	/** 03-FACTURA DE CONTINGENCIA = 03 */
	public static final String DIANDOCTYPECODE_03_FACTURADECONTINGENCIA = "03";
	/** 91-NOTA CREDITO = 91 */
	public static final String DIANDOCTYPECODE_91_NOTACREDITO = "91";
	/** 92-NOTA DEBITO = 92 */
	public static final String DIANDOCTYPECODE_92_NOTADEBITO = "92";
	/** Set Doc Type Code DIAN.
		@param DianDocTypeCode Doc Type Code DIAN	  */
	public void setDianDocTypeCode (String DianDocTypeCode)
	{

		set_Value (COLUMNNAME_DianDocTypeCode, DianDocTypeCode);
	}

	/** Get Doc Type Code DIAN.
		@return Doc Type Code DIAN	  */
	public String getDianDocTypeCode () 
	{
		return (String)get_Value(COLUMNNAME_DianDocTypeCode);
	}

	/** FA-FACTURA ELECTRONICA = FA */
	public static final String DIANSHORTDOCTYPE_FA_FACTURAELECTRONICA = "FA";
	/** INVCON-FACTURA DE CONTINGENCIA = INVCON */
	public static final String DIANSHORTDOCTYPE_INVCON_FACTURADECONTINGENCIA = "INVCON";
	/** INVEXP-FACTURA DE EXPORTACIÓN = INVEXP */
	public static final String DIANSHORTDOCTYPE_INVEXP_FACTURADEEXPORTACIÓN = "INVEXP";
	/** INVOIC-FACTURA DE VENTA = INVOIC */
	public static final String DIANSHORTDOCTYPE_INVOIC_FACTURADEVENTA = "INVOIC";
	/** NC-NOTA CREDITO = NC */
	public static final String DIANSHORTDOCTYPE_NC_NOTACREDITO = "NC";
	/** ND-NOTA DEBITO = ND */
	public static final String DIANSHORTDOCTYPE_ND_NOTADEBITO = "ND";
	/** Set Short Doc Type DIAN.
		@param DianShortDocType Short Doc Type DIAN	  */
	public void setDianShortDocType (String DianShortDocType)
	{

		set_Value (COLUMNNAME_DianShortDocType, DianShortDocType);
	}

	/** Get Short Doc Type DIAN.
		@return Short Doc Type DIAN	  */
	public String getDianShortDocType () 
	{
		return (String)get_Value(COLUMNNAME_DianShortDocType);
	}

	/** Set Doc Type EI.
		@param LCO_FE_DocType_ID Doc Type EI	  */
	public void setLCO_FE_DocType_ID (int LCO_FE_DocType_ID)
	{
		if (LCO_FE_DocType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_DocType_ID, Integer.valueOf(LCO_FE_DocType_ID));
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

	/** Set LCO_FE_DocType_UU.
		@param LCO_FE_DocType_UU LCO_FE_DocType_UU	  */
	public void setLCO_FE_DocType_UU (String LCO_FE_DocType_UU)
	{
		set_ValueNoCheck (COLUMNNAME_LCO_FE_DocType_UU, LCO_FE_DocType_UU);
	}

	/** Get LCO_FE_DocType_UU.
		@return LCO_FE_DocType_UU	  */
	public String getLCO_FE_DocType_UU () 
	{
		return (String)get_Value(COLUMNNAME_LCO_FE_DocType_UU);
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