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

/** Generated Interface for LCO_FE_OFE_Contingency
 *  @author iDempiere (generated) 
 *  @version Release 7.1
 */
@SuppressWarnings("all")
public interface I_LCO_FE_OFE_Contingency 
{

    /** TableName=LCO_FE_OFE_Contingency */
    public static final String Table_Name = "LCO_FE_OFE_Contingency";

    /** AD_Table_ID=1000027 */
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

    /** Column name DateTranscription */
    public static final String COLUMNNAME_DateTranscription = "DateTranscription";

	/** Set Date Transcription	  */
	public void setDateTranscription (Timestamp DateTranscription);

	/** Get Date Transcription	  */
	public Timestamp getDateTranscription();

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

    /** Column name EndDate */
    public static final String COLUMNNAME_EndDate = "EndDate";

	/** Set End Date.
	  * Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate);

	/** Get End Date.
	  * Last effective date (inclusive)
	  */
	public Timestamp getEndDate();

    /** Column name IdContingency */
    public static final String COLUMNNAME_IdContingency = "IdContingency";

	/** Set Id Contingency	  */
	public void setIdContingency (String IdContingency);

	/** Get Id Contingency	  */
	public String getIdContingency();

    /** Column name IdTranscriptionSeqNo */
    public static final String COLUMNNAME_IdTranscriptionSeqNo = "IdTranscriptionSeqNo";

	/** Set Id Transcription Seq No	  */
	public void setIdTranscriptionSeqNo (String IdTranscriptionSeqNo);

	/** Get Id Transcription Seq No	  */
	public String getIdTranscriptionSeqNo();

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

    /** Column name IsEnable */
    public static final String COLUMNNAME_IsEnable = "IsEnable";

	/** Set Enabled	  */
	public void setIsEnable (boolean IsEnable);

	/** Get Enabled	  */
	public boolean isEnable();

    /** Column name LCO_FE_OFE_Contingency_ID */
    public static final String COLUMNNAME_LCO_FE_OFE_Contingency_ID = "LCO_FE_OFE_Contingency_ID";

	/** Set Contingency EI	  */
	public void setLCO_FE_OFE_Contingency_ID (int LCO_FE_OFE_Contingency_ID);

	/** Get Contingency EI	  */
	public int getLCO_FE_OFE_Contingency_ID();

    /** Column name LCO_FE_OFE_Contingency_UU */
    public static final String COLUMNNAME_LCO_FE_OFE_Contingency_UU = "LCO_FE_OFE_Contingency_UU";

	/** Set LCO_FE_OFE_Contingency_UU	  */
	public void setLCO_FE_OFE_Contingency_UU (String LCO_FE_OFE_Contingency_UU);

	/** Get LCO_FE_OFE_Contingency_UU	  */
	public String getLCO_FE_OFE_Contingency_UU();

    /** Column name Prefix */
    public static final String COLUMNNAME_Prefix = "Prefix";

	/** Set Prefix.
	  * Prefix before the sequence number
	  */
	public void setPrefix (String Prefix);

	/** Get Prefix.
	  * Prefix before the sequence number
	  */
	public String getPrefix();

    /** Column name StartDate */
    public static final String COLUMNNAME_StartDate = "StartDate";

	/** Set Start Date.
	  * First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate);

	/** Get Start Date.
	  * First effective day (inclusive)
	  */
	public Timestamp getStartDate();

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
}
