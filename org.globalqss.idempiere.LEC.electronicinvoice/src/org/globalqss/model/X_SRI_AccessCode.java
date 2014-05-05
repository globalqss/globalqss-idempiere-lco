/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
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

import org.compiere.model.I_Persistent;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for SRI_AccessCode
 *  @author Adempiere (generated) 
 *  @version 3.6.1LTS.Final - $Id$ */
public class X_SRI_AccessCode extends PO implements I_SRI_AccessCode, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140429L;

    /** Standard Constructor */
    public X_SRI_AccessCode (Properties ctx, int SRI_AccessCode_ID, String trxName)
    {
      super (ctx, SRI_AccessCode_ID, trxName);
      /** if (SRI_AccessCode_ID == 0)
        {
			setCodeAccessType (null);
			setEnvType (null);
			setIsUsed (false);
			setOldValue (null);
			setSRI_AccessCode_ID (0);
			setSRI_ShortDocType (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_SRI_AccessCode (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_SRI_AccessCode[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** CodeAccessType AD_Reference_ID=1000065 */
	public static final int CODEACCESSTYPE_AD_Reference_ID=1000065;
	/** Automatica = 1 */
	public static final String CODEACCESSTYPE_Automatica = "1";
	/** Contingencia = 2 */
	public static final String CODEACCESSTYPE_Contingencia = "2";
	/** Set CodeAccessType.
		@param CodeAccessType CodeAccessType	  */
	public void setCodeAccessType (String CodeAccessType)
	{

		set_Value (COLUMNNAME_CodeAccessType, CodeAccessType);
	}

	/** Get CodeAccessType.
		@return CodeAccessType	  */
	public String getCodeAccessType () 
	{
		return (String)get_Value(COLUMNNAME_CodeAccessType);
	}

	/** EnvType AD_Reference_ID=1000064 */
	public static final int ENVTYPE_AD_Reference_ID=1000064;
	/** Pruebas = 1 */
	public static final String ENVTYPE_Pruebas = "1";
	/** Producción = 2 */
	public static final String ENVTYPE_Producción = "2";
	/** Set EnvType.
		@param EnvType EnvType	  */
	public void setEnvType (String EnvType)
	{

		set_Value (COLUMNNAME_EnvType, EnvType);
	}

	/** Get EnvType.
		@return EnvType	  */
	public String getEnvType () 
	{
		return (String)get_Value(COLUMNNAME_EnvType);
	}

	/** Set IsUsed.
		@param IsUsed IsUsed	  */
	public void setIsUsed (boolean IsUsed)
	{
		set_Value (COLUMNNAME_IsUsed, Boolean.valueOf(IsUsed));
	}

	/** Get IsUsed.
		@return IsUsed	  */
	public boolean isUsed () 
	{
		Object oo = get_Value(COLUMNNAME_IsUsed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Old Value.
		@param OldValue 
		The old file data
	  */
	public void setOldValue (String OldValue)
	{
		set_ValueNoCheck (COLUMNNAME_OldValue, OldValue);
	}

	/** Get Old Value.
		@return The old file data
	  */
	public String getOldValue () 
	{
		return (String)get_Value(COLUMNNAME_OldValue);
	}

	/** Set SRI_AccessCode.
		@param SRI_AccessCode_ID SRI_AccessCode	  */
	public void setSRI_AccessCode_ID (int SRI_AccessCode_ID)
	{
		if (SRI_AccessCode_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SRI_AccessCode_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SRI_AccessCode_ID, Integer.valueOf(SRI_AccessCode_ID));
	}

	/** Get SRI_AccessCode.
		@return SRI_AccessCode	  */
	public int getSRI_AccessCode_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SRI_AccessCode_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** SRI_ShortDocType AD_Reference_ID=1000066 */
	public static final int SRI_SHORTDOCTYPE_AD_Reference_ID=1000066;
	/** FACTURA = 01 */
	public static final String SRI_SHORTDOCTYPE_FACTURA = "01";
	/** NOTA DE CRÉDITO = 04 */
	public static final String SRI_SHORTDOCTYPE_NOTADECRÉDITO = "04";
	/** NOTA DE DÉBITO = 05 */
	public static final String SRI_SHORTDOCTYPE_NOTADEDÉBITO = "05";
	/** GUÍA DE REMISIÓN = 06 */
	public static final String SRI_SHORTDOCTYPE_GUÍADEREMISIÓN = "06";
	/** COMPROBANTE DE RETENCIÓN = 07 */
	public static final String SRI_SHORTDOCTYPE_COMPROBANTEDERETENCIÓN = "07";
	/** Set SRI_ShortDocType.
		@param SRI_ShortDocType SRI_ShortDocType	  */
	public void setSRI_ShortDocType (String SRI_ShortDocType)
	{

		set_Value (COLUMNNAME_SRI_ShortDocType, SRI_ShortDocType);
	}

	/** Get SRI_ShortDocType.
		@return SRI_ShortDocType	  */
	public String getSRI_ShortDocType () 
	{
		return (String)get_Value(COLUMNNAME_SRI_ShortDocType);
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