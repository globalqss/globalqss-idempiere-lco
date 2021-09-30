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

/** Generated Interface for LCO_FE_Authorization
 *  @author iDempiere (generated) 
 *  @version Release 7.1
 */
@SuppressWarnings("all")
public interface I_LCO_FE_Authorization 
{

    /** TableName=LCO_FE_Authorization */
    public static final String Table_Name = "LCO_FE_Authorization";

    /** AD_Table_ID=1000026 */
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

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/** Set Table.
	  * Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID);

	/** Get Table.
	  * Database Table information
	  */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException;

    /** Column name AD_UserMail_ID */
    public static final String COLUMNNAME_AD_UserMail_ID = "AD_UserMail_ID";

	/** Set User Mail.
	  * Mail sent to the user
	  */
	public void setAD_UserMail_ID (int AD_UserMail_ID);

	/** Get User Mail.
	  * Mail sent to the user
	  */
	public int getAD_UserMail_ID();

	public org.compiere.model.I_AD_User getAD_UserMail() throws RuntimeException;

    /** Column name ContingencyProccessing */
    public static final String COLUMNNAME_ContingencyProccessing = "ContingencyProccessing";

	/** Set Contingency Processing	  */
	public void setContingencyProccessing (String ContingencyProccessing);

	/** Get Contingency Processing	  */
	public String getContingencyProccessing();

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

    /** Column name DownloadURL */
    public static final String COLUMNNAME_DownloadURL = "DownloadURL";

	/** Set Download URL.
	  * URL of the Download files
	  */
	public void setDownloadURL (String DownloadURL);

	/** Get Download URL.
	  * URL of the Download files
	  */
	public String getDownloadURL();

    /** Column name ErrorMsg */
    public static final String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/** Set Error Msg	  */
	public void setErrorMsg (String ErrorMsg);

	/** Get Error Msg	  */
	public String getErrorMsg();

    /** Column name FE_Mailing */
    public static final String COLUMNNAME_FE_Mailing = "FE_Mailing";

	/** Set Send EMail	  */
	public void setFE_Mailing (String FE_Mailing);

	/** Get Send EMail	  */
	public String getFE_Mailing();

    /** Column name FE_QueryStatus */
    public static final String COLUMNNAME_FE_QueryStatus = "FE_QueryStatus";

	/** Set Query Status	  */
	public void setFE_QueryStatus (String FE_QueryStatus);

	/** Get Query Status	  */
	public String getFE_QueryStatus();

    /** Column name FE_Reprocessing */
    public static final String COLUMNNAME_FE_Reprocessing = "FE_Reprocessing";

	/** Set Reprocess Authorization	  */
	public void setFE_Reprocessing (String FE_Reprocessing);

	/** Get Reprocess Authorization	  */
	public String getFE_Reprocessing();

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

    /** Column name IsMailReceived */
    public static final String COLUMNNAME_IsMailReceived = "IsMailReceived";

	/** Set Mail Received	  */
	public void setIsMailReceived (boolean IsMailReceived);

	/** Get Mail Received	  */
	public boolean isMailReceived();

    /** Column name IsMailSend */
    public static final String COLUMNNAME_IsMailSend = "IsMailSend";

	/** Set Mail Sent	  */
	public void setIsMailSend (boolean IsMailSend);

	/** Get Mail Sent	  */
	public boolean isMailSend();

    /** Column name LCO_FE_Authorization_ID */
    public static final String COLUMNNAME_LCO_FE_Authorization_ID = "LCO_FE_Authorization_ID";

	/** Set Authorization EI	  */
	public void setLCO_FE_Authorization_ID (int LCO_FE_Authorization_ID);

	/** Get Authorization EI	  */
	public int getLCO_FE_Authorization_ID();

    /** Column name LCO_FE_Authorization_UU */
    public static final String COLUMNNAME_LCO_FE_Authorization_UU = "LCO_FE_Authorization_UU";

	/** Set LCO_FE_Authorization_UU	  */
	public void setLCO_FE_Authorization_UU (String LCO_FE_Authorization_UU);

	/** Get LCO_FE_Authorization_UU	  */
	public String getLCO_FE_Authorization_UU();

    /** Column name LCO_FE_DateAuthorization */
    public static final String COLUMNNAME_LCO_FE_DateAuthorization = "LCO_FE_DateAuthorization";

	/** Set Authorization Date	  */
	public void setLCO_FE_DateAuthorization (String LCO_FE_DateAuthorization);

	/** Get Authorization Date	  */
	public String getLCO_FE_DateAuthorization();

    /** Column name LCO_FE_DIAN_Format_ID */
    public static final String COLUMNNAME_LCO_FE_DIAN_Format_ID = "LCO_FE_DIAN_Format_ID";

	/** Set DIAN Format EI	  */
	public void setLCO_FE_DIAN_Format_ID (int LCO_FE_DIAN_Format_ID);

	/** Get DIAN Format EI	  */
	public int getLCO_FE_DIAN_Format_ID();

	public org.globalqss.model.I_LCO_FE_DIAN_Format getLCO_FE_DIAN_Format() throws RuntimeException;

    /** Column name LCO_FE_Dian_Uuid */
    public static final String COLUMNNAME_LCO_FE_Dian_Uuid = "LCO_FE_Dian_Uuid";

	/** Set UUID DIAN	  */
	public void setLCO_FE_Dian_Uuid (String LCO_FE_Dian_Uuid);

	/** Get UUID DIAN	  */
	public String getLCO_FE_Dian_Uuid();

    /** Column name LCO_FE_DocType_ID */
    public static final String COLUMNNAME_LCO_FE_DocType_ID = "LCO_FE_DocType_ID";

	/** Set Doc Type EI	  */
	public void setLCO_FE_DocType_ID (int LCO_FE_DocType_ID);

	/** Get Doc Type EI	  */
	public int getLCO_FE_DocType_ID();

	public org.globalqss.model.I_LCO_FE_DocType getLCO_FE_DocType() throws RuntimeException;

    /** Column name LCO_FE_IdErrorCode */
    public static final String COLUMNNAME_LCO_FE_IdErrorCode = "LCO_FE_IdErrorCode";

	/** Set Error Code EI	  */
	public void setLCO_FE_IdErrorCode (BigDecimal LCO_FE_IdErrorCode);

	/** Get Error Code EI	  */
	public BigDecimal getLCO_FE_IdErrorCode();

    /** Column name LCO_FE_IdTransaction */
    public static final String COLUMNNAME_LCO_FE_IdTransaction = "LCO_FE_IdTransaction";

	/** Set Transaction Id DIAN	  */
	public void setLCO_FE_IdTransaction (String LCO_FE_IdTransaction);

	/** Get Transaction Id DIAN	  */
	public String getLCO_FE_IdTransaction();

    /** Column name LCO_FE_OFE_Contingency_ID */
    public static final String COLUMNNAME_LCO_FE_OFE_Contingency_ID = "LCO_FE_OFE_Contingency_ID";

	/** Set Contingency EI	  */
	public void setLCO_FE_OFE_Contingency_ID (int LCO_FE_OFE_Contingency_ID);

	/** Get Contingency EI	  */
	public int getLCO_FE_OFE_Contingency_ID();

	public org.globalqss.model.I_LCO_FE_OFE_Contingency getLCO_FE_OFE_Contingency() throws RuntimeException;

    /** Column name LCO_PrintedFormControl_ID */
    public static final String COLUMNNAME_LCO_PrintedFormControl_ID = "LCO_PrintedFormControl_ID";

	/** Set Printed Form Control	  */
	public void setLCO_PrintedFormControl_ID (int LCO_PrintedFormControl_ID);

	/** Get Printed Form Control	  */
	public int getLCO_PrintedFormControl_ID();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/** Set Record ID.
	  * Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID);

	/** Get Record ID.
	  * Direct internal record ID
	  */
	public int getRecord_ID();

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
}
