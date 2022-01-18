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

/** Generated Model for LCO_FE_OFE_Contingency
 *  @author iDempiere (generated) 
 *  @version Release 7.1 - $Id$ */
public class X_LCO_FE_OFE_Contingency extends PO implements I_LCO_FE_OFE_Contingency, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200204L;

    /** Standard Constructor */
    public X_LCO_FE_OFE_Contingency (Properties ctx, int LCO_FE_OFE_Contingency_ID, String trxName)
    {
      super (ctx, LCO_FE_OFE_Contingency_ID, trxName);
      /** if (LCO_FE_OFE_Contingency_ID == 0)
        {
			setIdContingency (null);
			setIdTranscriptionSeqNo (null);
			setIsEnable (false);
// N
			setLCO_FE_OFE_Contingency_ID (0);
			setStartDate (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_LCO_FE_OFE_Contingency (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_LCO_FE_OFE_Contingency[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Date Transcription.
		@param DateTranscription Date Transcription	  */
	public void setDateTranscription (Timestamp DateTranscription)
	{
		set_Value (COLUMNNAME_DateTranscription, DateTranscription);
	}

	/** Get Date Transcription.
		@return Date Transcription	  */
	public Timestamp getDateTranscription () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTranscription);
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

	/** Set End Date.
		@param EndDate 
		Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
	}

	/** Set Id Contingency.
		@param IdContingency Id Contingency	  */
	public void setIdContingency (String IdContingency)
	{
		set_Value (COLUMNNAME_IdContingency, IdContingency);
	}

	/** Get Id Contingency.
		@return Id Contingency	  */
	public String getIdContingency () 
	{
		return (String)get_Value(COLUMNNAME_IdContingency);
	}

	/** Set Id Transcription Seq No.
		@param IdTranscriptionSeqNo Id Transcription Seq No	  */
	public void setIdTranscriptionSeqNo (String IdTranscriptionSeqNo)
	{
		set_Value (COLUMNNAME_IdTranscriptionSeqNo, IdTranscriptionSeqNo);
	}

	/** Get Id Transcription Seq No.
		@return Id Transcription Seq No	  */
	public String getIdTranscriptionSeqNo () 
	{
		return (String)get_Value(COLUMNNAME_IdTranscriptionSeqNo);
	}

	/** Set Enabled.
		@param IsEnable Enabled	  */
	public void setIsEnable (boolean IsEnable)
	{
		set_Value (COLUMNNAME_IsEnable, Boolean.valueOf(IsEnable));
	}

	/** Get Enabled.
		@return Enabled	  */
	public boolean isEnable () 
	{
		Object oo = get_Value(COLUMNNAME_IsEnable);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Contingency EI.
		@param LCO_FE_OFE_Contingency_ID Contingency EI	  */
	public void setLCO_FE_OFE_Contingency_ID (int LCO_FE_OFE_Contingency_ID)
	{
		if (LCO_FE_OFE_Contingency_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_OFE_Contingency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_OFE_Contingency_ID, Integer.valueOf(LCO_FE_OFE_Contingency_ID));
	}

	/** Get Contingency EI.
		@return Contingency EI	  */
	public int getLCO_FE_OFE_Contingency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LCO_FE_OFE_Contingency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set LCO_FE_OFE_Contingency_UU.
		@param LCO_FE_OFE_Contingency_UU LCO_FE_OFE_Contingency_UU	  */
	public void setLCO_FE_OFE_Contingency_UU (String LCO_FE_OFE_Contingency_UU)
	{
		set_ValueNoCheck (COLUMNNAME_LCO_FE_OFE_Contingency_UU, LCO_FE_OFE_Contingency_UU);
	}

	/** Get LCO_FE_OFE_Contingency_UU.
		@return LCO_FE_OFE_Contingency_UU	  */
	public String getLCO_FE_OFE_Contingency_UU () 
	{
		return (String)get_Value(COLUMNNAME_LCO_FE_OFE_Contingency_UU);
	}

	/** Set Prefix.
		@param Prefix 
		Prefix before the sequence number
	  */
	public void setPrefix (String Prefix)
	{
		set_Value (COLUMNNAME_Prefix, Prefix);
	}

	/** Get Prefix.
		@return Prefix before the sequence number
	  */
	public String getPrefix () 
	{
		return (String)get_Value(COLUMNNAME_Prefix);
	}

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}
}