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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for LCO_FE_DIAN_Format
 *  @author iDempiere (generated) 
 *  @version Release 8.2 - $Id$ */
public class X_LCO_FE_DIAN_Format extends PO implements I_LCO_FE_DIAN_Format, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210512L;

    /** Standard Constructor */
    public X_LCO_FE_DIAN_Format (Properties ctx, int LCO_FE_DIAN_Format_ID, String trxName)
    {
      super (ctx, LCO_FE_DIAN_Format_ID, trxName);
      /** if (LCO_FE_DIAN_Format_ID == 0)
        {
			setLCO_FE_DIAN_Format_ID (0);
			setMaxXMLRecords (0);
			setName (null);
			setUBLVersionNo (null);
// 2.0
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
			setValue (null);
			setVersionNo (null);
// 1.0
			setXmlPrintLabel (null);
        } */
    }

    /** Load Constructor */
    public X_LCO_FE_DIAN_Format (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_LCO_FE_DIAN_Format[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Sequence getAD_Sequence() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Sequence)MTable.get(getCtx(), org.compiere.model.I_AD_Sequence.Table_Name)
			.getPO(getAD_Sequence_ID(), get_TrxName());	}

	/** Set Sequence.
		@param AD_Sequence_ID 
		Document Sequence
	  */
	public void setAD_Sequence_ID (int AD_Sequence_ID)
	{
		if (AD_Sequence_ID < 1) 
			set_Value (COLUMNNAME_AD_Sequence_ID, null);
		else 
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

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set DIAN Format EI.
		@param LCO_FE_DIAN_Format_ID DIAN Format EI	  */
	public void setLCO_FE_DIAN_Format_ID (int LCO_FE_DIAN_Format_ID)
	{
		if (LCO_FE_DIAN_Format_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_DIAN_Format_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_DIAN_Format_ID, Integer.valueOf(LCO_FE_DIAN_Format_ID));
	}

	/** Get DIAN Format EI.
		@return DIAN Format EI	  */
	public int getLCO_FE_DIAN_Format_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LCO_FE_DIAN_Format_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set LCO_FE_DIAN_Format_UU.
		@param LCO_FE_DIAN_Format_UU LCO_FE_DIAN_Format_UU	  */
	public void setLCO_FE_DIAN_Format_UU (String LCO_FE_DIAN_Format_UU)
	{
		set_ValueNoCheck (COLUMNNAME_LCO_FE_DIAN_Format_UU, LCO_FE_DIAN_Format_UU);
	}

	/** Get LCO_FE_DIAN_Format_UU.
		@return LCO_FE_DIAN_Format_UU	  */
	public String getLCO_FE_DIAN_Format_UU () 
	{
		return (String)get_Value(COLUMNNAME_LCO_FE_DIAN_Format_UU);
	}

	public org.globalqss.model.I_LCO_FE_DocType getLCO_FE_DocType() throws RuntimeException
    {
		return (org.globalqss.model.I_LCO_FE_DocType)MTable.get(getCtx(), org.globalqss.model.I_LCO_FE_DocType.Table_Name)
			.getPO(getLCO_FE_DocType_ID(), get_TrxName());	}

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

	/** Set EDI Type EI.
		@param LCO_FE_EDIType EDI Type EI	  */
	public void setLCO_FE_EDIType (String LCO_FE_EDIType)
	{
		set_Value (COLUMNNAME_LCO_FE_EDIType, LCO_FE_EDIType);
	}

	/** Get EDI Type EI.
		@return EDI Type EI	  */
	public String getLCO_FE_EDIType () 
	{
		return (String)get_Value(COLUMNNAME_LCO_FE_EDIType);
	}

	/** Set UUID Type.
		@param LCO_FE_UuidType UUID Type	  */
	public void setLCO_FE_UuidType (String LCO_FE_UuidType)
	{
		set_Value (COLUMNNAME_LCO_FE_UuidType, LCO_FE_UuidType);
	}

	/** Get UUID Type.
		@return UUID Type	  */
	public String getLCO_FE_UuidType () 
	{
		return (String)get_Value(COLUMNNAME_LCO_FE_UuidType);
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
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set UBL Version Nr..
		@param UBLVersionNo UBL Version Nr.	  */
	public void setUBLVersionNo (String UBLVersionNo)
	{
		set_Value (COLUMNNAME_UBLVersionNo, UBLVersionNo);
	}

	/** Get UBL Version Nr..
		@return UBL Version Nr.	  */
	public String getUBLVersionNo () 
	{
		return (String)get_Value(COLUMNNAME_UBLVersionNo);
	}

	/** Set URL_XSD.
		@param URL_XSD URL_XSD	  */
	public void setURL_XSD (String URL_XSD)
	{
		set_Value (COLUMNNAME_URL_XSD, URL_XSD);
	}

	/** Get URL_XSD.
		@return URL_XSD	  */
	public String getURL_XSD () 
	{
		return (String)get_Value(COLUMNNAME_URL_XSD);
	}

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Valid to.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Valid to.
		@return Valid to including this date (last day)
	  */
	public Timestamp getValidTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
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

	/** Set Version No.
		@param VersionNo 
		Version Number
	  */
	public void setVersionNo (String VersionNo)
	{
		set_Value (COLUMNNAME_VersionNo, VersionNo);
	}

	/** Get Version No.
		@return Version Number
	  */
	public String getVersionNo () 
	{
		return (String)get_Value(COLUMNNAME_VersionNo);
	}

	/** Set XmlPrintLabel.
		@param XmlPrintLabel XmlPrintLabel	  */
	public void setXmlPrintLabel (String XmlPrintLabel)
	{
		set_Value (COLUMNNAME_XmlPrintLabel, XmlPrintLabel);
	}

	/** Get XmlPrintLabel.
		@return XmlPrintLabel	  */
	public String getXmlPrintLabel () 
	{
		return (String)get_Value(COLUMNNAME_XmlPrintLabel);
	}
}