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
package org.globalqss.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for LCO_FE_DIAN_Format
 *  @author iDempiere (generated) 
 *  @version Release 8.2
 */
@SuppressWarnings("all")
public interface I_LCO_FE_DIAN_Format 
{

    /** TableName=LCO_FE_DIAN_Format */
    public static final String Table_Name = "LCO_FE_DIAN_Format";

    /** AD_Table_ID=1000120 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AD_Sequence_ID */
    public static final String COLUMNNAME_AD_Sequence_ID = "AD_Sequence_ID";

	/** Set Sequence.
	  * Document Sequence
	  */
	public void setAD_Sequence_ID (int AD_Sequence_ID);

	/** Get Sequence.
	  * Document Sequence
	  */
	public int getAD_Sequence_ID();

	public org.compiere.model.I_AD_Sequence getAD_Sequence() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name LCO_FE_DIAN_Format_ID */
    public static final String COLUMNNAME_LCO_FE_DIAN_Format_ID = "LCO_FE_DIAN_Format_ID";

	/** Set DIAN Format EI	  */
	public void setLCO_FE_DIAN_Format_ID (int LCO_FE_DIAN_Format_ID);

	/** Get DIAN Format EI	  */
	public int getLCO_FE_DIAN_Format_ID();

    /** Column name LCO_FE_DIAN_Format_UU */
    public static final String COLUMNNAME_LCO_FE_DIAN_Format_UU = "LCO_FE_DIAN_Format_UU";

	/** Set LCO_FE_DIAN_Format_UU	  */
	public void setLCO_FE_DIAN_Format_UU (String LCO_FE_DIAN_Format_UU);

	/** Get LCO_FE_DIAN_Format_UU	  */
	public String getLCO_FE_DIAN_Format_UU();

    /** Column name LCO_FE_DocType_ID */
    public static final String COLUMNNAME_LCO_FE_DocType_ID = "LCO_FE_DocType_ID";

	/** Set Doc Type EI	  */
	public void setLCO_FE_DocType_ID (int LCO_FE_DocType_ID);

	/** Get Doc Type EI	  */
	public int getLCO_FE_DocType_ID();

	public org.globalqss.model.I_LCO_FE_DocType getLCO_FE_DocType() throws RuntimeException;

    /** Column name LCO_FE_EDIType */
    public static final String COLUMNNAME_LCO_FE_EDIType = "LCO_FE_EDIType";

	/** Set EDI Type EI	  */
	public void setLCO_FE_EDIType (String LCO_FE_EDIType);

	/** Get EDI Type EI	  */
	public String getLCO_FE_EDIType();

    /** Column name LCO_FE_UuidType */
    public static final String COLUMNNAME_LCO_FE_UuidType = "LCO_FE_UuidType";

	/** Set UUID Type	  */
	public void setLCO_FE_UuidType (String LCO_FE_UuidType);

	/** Get UUID Type	  */
	public String getLCO_FE_UuidType();

    /** Column name MaxXMLRecords */
    public static final String COLUMNNAME_MaxXMLRecords = "MaxXMLRecords";

	/** Set MaxXMLRecords	  */
	public void setMaxXMLRecords (int MaxXMLRecords);

	/** Get MaxXMLRecords	  */
	public int getMaxXMLRecords();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name UBLVersionNo */
    public static final String COLUMNNAME_UBLVersionNo = "UBLVersionNo";

	/** Set UBL Version Nr.	  */
	public void setUBLVersionNo (String UBLVersionNo);

	/** Get UBL Version Nr.	  */
	public String getUBLVersionNo();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name URL_XSD */
    public static final String COLUMNNAME_URL_XSD = "URL_XSD";

	/** Set URL_XSD	  */
	public void setURL_XSD (String URL_XSD);

	/** Get URL_XSD	  */
	public String getURL_XSD();

    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/** Set Valid from.
	  * Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom);

	/** Get Valid from.
	  * Valid from including this date (first day)
	  */
	public Timestamp getValidFrom();

    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";

	/** Set Valid to.
	  * Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo);

	/** Get Valid to.
	  * Valid to including this date (last day)
	  */
	public Timestamp getValidTo();

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();

    /** Column name VersionNo */
    public static final String COLUMNNAME_VersionNo = "VersionNo";

	/** Set Version No.
	  * Version Number
	  */
	public void setVersionNo (String VersionNo);

	/** Get Version No.
	  * Version Number
	  */
	public String getVersionNo();

    /** Column name XmlPrintLabel */
    public static final String COLUMNNAME_XmlPrintLabel = "XmlPrintLabel";

	/** Set XmlPrintLabel	  */
	public void setXmlPrintLabel (String XmlPrintLabel);

	/** Get XmlPrintLabel	  */
	public String getXmlPrintLabel();
}
