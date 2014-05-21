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
import org.compiere.model.*;

/** Generated Model for SRI_Authorisation
 *  @author Adempiere (generated) 
 *  @version 3.6.1LTS.Final - $Id$ */
public class X_SRI_Authorisation extends PO implements I_SRI_Authorisation, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140519L;

    /** Standard Constructor */
    public X_SRI_Authorisation (Properties ctx, int SRI_Authorisation_ID, String trxName)
    {
      super (ctx, SRI_Authorisation_ID, trxName);
      /** if (SRI_Authorisation_ID == 0)
        {
			setProcessed (false);
			setSRI_Authorisation_ID (0);
			setSRI_ShortDocType (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_SRI_Authorisation (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_SRI_Authorisation[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_UserMail getAD_UserMail() throws RuntimeException
    {
		return (I_AD_UserMail)MTable.get(getCtx(), I_AD_UserMail.Table_Name)
			.getPO(getAD_UserMail_ID(), get_TrxName());	}

	/** Set User Mail.
		@param AD_UserMail_ID 
		Mail sent to the user
	  */
	public void setAD_UserMail_ID (int AD_UserMail_ID)
	{
		if (AD_UserMail_ID < 1) 
			set_Value (COLUMNNAME_AD_UserMail_ID, null);
		else 
			set_Value (COLUMNNAME_AD_UserMail_ID, Integer.valueOf(AD_UserMail_ID));
	}

	/** Get User Mail.
		@return Mail sent to the user
	  */
	public int getAD_UserMail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_UserMail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ContingencyProccessing.
		@param ContingencyProccessing ContingencyProccessing	  */
	public void setContingencyProccessing (String ContingencyProccessing)
	{
		set_Value (COLUMNNAME_ContingencyProccessing, ContingencyProccessing);
	}

	/** Get ContingencyProccessing.
		@return ContingencyProccessing	  */
	public String getContingencyProccessing () 
	{
		return (String)get_Value(COLUMNNAME_ContingencyProccessing);
	}

	/** Set Mailing.
		@param Mailing Mailing	  */
	public void setMailing (String Mailing)
	{
		set_Value (COLUMNNAME_Mailing, Mailing);
	}

	/** Get Mailing.
		@return Mailing	  */
	public String getMailing () 
	{
		return (String)get_Value(COLUMNNAME_Mailing);
	}

	/** Set New Value.
		@param NewValue 
		New field value
	  */
	public void setNewValue (String NewValue)
	{
		set_Value (COLUMNNAME_NewValue, NewValue);
	}

	/** Get New Value.
		@return New field value
	  */
	public String getNewValue () 
	{
		return (String)get_Value(COLUMNNAME_NewValue);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ReProcessing.
		@param ReProcessing ReProcessing	  */
	public void setReProcessing (String ReProcessing)
	{
		set_Value (COLUMNNAME_ReProcessing, ReProcessing);
	}

	/** Get ReProcessing.
		@return ReProcessing	  */
	public String getReProcessing () 
	{
		return (String)get_Value(COLUMNNAME_ReProcessing);
	}

	public org.globalqss.model.I_SRI_AccessCode getSRI_AccessCode() throws RuntimeException
    {
		return (org.globalqss.model.I_SRI_AccessCode)MTable.get(getCtx(), org.globalqss.model.I_SRI_AccessCode.Table_Name)
			.getPO(getSRI_AccessCode_ID(), get_TrxName());	}

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

	/** Set SRI_Authorisation.
		@param SRI_Authorisation_ID SRI_Authorisation	  */
	public void setSRI_Authorisation_ID (int SRI_Authorisation_ID)
	{
		if (SRI_Authorisation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SRI_Authorisation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SRI_Authorisation_ID, Integer.valueOf(SRI_Authorisation_ID));
	}

	/** Get SRI_Authorisation.
		@return SRI_Authorisation	  */
	public int getSRI_Authorisation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SRI_Authorisation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.globalqss.model.I_SRI_ErrorCode getSRI_ErrorCode() throws RuntimeException
    {
		return (org.globalqss.model.I_SRI_ErrorCode)MTable.get(getCtx(), org.globalqss.model.I_SRI_ErrorCode.Table_Name)
			.getPO(getSRI_ErrorCode_ID(), get_TrxName());	}

	/** Set SRI_ErrorCode_ID.
		@param SRI_ErrorCode_ID SRI_ErrorCode_ID	  */
	public void setSRI_ErrorCode_ID (int SRI_ErrorCode_ID)
	{
		if (SRI_ErrorCode_ID < 1) 
			set_Value (COLUMNNAME_SRI_ErrorCode_ID, null);
		else 
			set_Value (COLUMNNAME_SRI_ErrorCode_ID, Integer.valueOf(SRI_ErrorCode_ID));
	}

	/** Get SRI_ErrorCode_ID.
		@return SRI_ErrorCode_ID	  */
	public int getSRI_ErrorCode_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SRI_ErrorCode_ID);
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