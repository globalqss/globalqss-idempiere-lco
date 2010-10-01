/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                          *
 * http://www.adempiere.org                                           *
 *                                                                    *
 * Copyright (C) Trifon Trifonov.                                     *
 * Copyright (C) Contributors                                         *
 *                                                                    *
 * This program is free software;
 you can redistribute it and/or      *
 * modify it under the terms of the GNU General Public License        *
 * as published by the Free Software Foundation;
 either version 2     *
 * of the License, or (at your option) any later version.             *
 *                                                                    *
 * This program is distributed in the hope that it will be useful,    *
 * but WITHOUT ANY WARRANTY;
 without even the implied warranty of     *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the       *
 * GNU General Public License for more details.                       *
 *                                                                    *
 * You should have received a copy of the GNU General Public License  *
 * along with this program;
 if not, write to the Free Software        *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,         *
 * MA 02110-1301, USA.                                                *
 *                                                                    *
 * Contributors:                                                      *
 * - Trifon Trifonov (trifonnt@users.sourceforge.net)                 *
 *                                                                    *
 * Sponsors:                                                          *
 * - Company (http://www.site.com)                                    *
 **********************************************************************/
package org.globalqss.model;

import java.math.BigDecimal;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for LCO_DIAN_SendScheduleLine
 *  @author Trifon Trifonov (generated) 
 *  @version Release 3.4.2s
 */
public interface I_LCO_DIAN_SendScheduleLine 
{

    /** TableName=LCO_DIAN_SendScheduleLine */
    public static final String Table_Name = "LCO_DIAN_SendScheduleLine";

    /** AD_Table_ID=1000011 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

    /** Load Meta Data */

    /** Column name AttributeName1 */
    public static final String COLUMNNAME_AttributeName1 = "AttributeName1";

	/** Set Attribute Name 1.
	  * Name of the Attribute
	  */
	public void setAttributeName1 (String AttributeName1);

	/** Get Attribute Name 1.
	  * Name of the Attribute
	  */
	public String getAttributeName1();

    /** Column name AttributeName10 */
    public static final String COLUMNNAME_AttributeName10 = "AttributeName10";

	/** Set Attribute Name 10.
	  * Name of the Attribute 10
	  */
	public void setAttributeName10 (String AttributeName10);

	/** Get Attribute Name 10.
	  * Name of the Attribute 10
	  */
	public String getAttributeName10();

    /** Column name AttributeName2 */
    public static final String COLUMNNAME_AttributeName2 = "AttributeName2";

	/** Set Attribute Name 2.
	  * Name of the Attribute 2
	  */
	public void setAttributeName2 (String AttributeName2);

	/** Get Attribute Name 2.
	  * Name of the Attribute 2
	  */
	public String getAttributeName2();

    /** Column name AttributeName3 */
    public static final String COLUMNNAME_AttributeName3 = "AttributeName3";

	/** Set Attribute Name 3.
	  * Name of the Attribute 3
	  */
	public void setAttributeName3 (String AttributeName3);

	/** Get Attribute Name 3.
	  * Name of the Attribute 3
	  */
	public String getAttributeName3();

    /** Column name AttributeName4 */
    public static final String COLUMNNAME_AttributeName4 = "AttributeName4";

	/** Set Attribute Name 4.
	  * Name of the Attribute 4
	  */
	public void setAttributeName4 (String AttributeName4);

	/** Get Attribute Name 4.
	  * Name of the Attribute 4
	  */
	public String getAttributeName4();

    /** Column name AttributeName5 */
    public static final String COLUMNNAME_AttributeName5 = "AttributeName5";

	/** Set Attribute Name 5.
	  * Name of the Attribute 5
	  */
	public void setAttributeName5 (String AttributeName5);

	/** Get Attribute Name 5.
	  * Name of the Attribute 5
	  */
	public String getAttributeName5();

    /** Column name AttributeName6 */
    public static final String COLUMNNAME_AttributeName6 = "AttributeName6";

	/** Set Attribute Name 6.
	  * Name of the Attribute 6
	  */
	public void setAttributeName6 (String AttributeName6);

	/** Get Attribute Name 6.
	  * Name of the Attribute 6
	  */
	public String getAttributeName6();

    /** Column name AttributeName7 */
    public static final String COLUMNNAME_AttributeName7 = "AttributeName7";

	/** Set Attribute Name 7.
	  * Name of the Attribute 7
	  */
	public void setAttributeName7 (String AttributeName7);

	/** Get Attribute Name 7.
	  * Name of the Attribute 7
	  */
	public String getAttributeName7();

    /** Column name AttributeName8 */
    public static final String COLUMNNAME_AttributeName8 = "AttributeName8";

	/** Set Attribute Name 8.
	  * Name of the Attribute 8
	  */
	public void setAttributeName8 (String AttributeName8);

	/** Get Attribute Name 8.
	  * Name of the Attribute 8
	  */
	public String getAttributeName8();

    /** Column name AttributeName9 */
    public static final String COLUMNNAME_AttributeName9 = "AttributeName9";

	/** Set Attribute Name 9.
	  * Name of the Attribute 9
	  */
	public void setAttributeName9 (String AttributeName9);

	/** Get Attribute Name 9.
	  * Name of the Attribute 9
	  */
	public String getAttributeName9();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public I_C_BPartner getC_BPartner() throws Exception;

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID();

	public I_C_BPartner_Location getC_BPartner_Location() throws Exception;

    /** Column name FieldAmt1 */
    public static final String COLUMNNAME_FieldAmt1 = "FieldAmt1";

	/** Set Field Amt 1	  */
	public void setFieldAmt1 (BigDecimal FieldAmt1);

	/** Get Field Amt 1	  */
	public BigDecimal getFieldAmt1();

    /** Column name FieldAmt10 */
    public static final String COLUMNNAME_FieldAmt10 = "FieldAmt10";

	/** Set Field Amt 10	  */
	public void setFieldAmt10 (BigDecimal FieldAmt10);

	/** Get Field Amt 10	  */
	public BigDecimal getFieldAmt10();

    /** Column name FieldAmt2 */
    public static final String COLUMNNAME_FieldAmt2 = "FieldAmt2";

	/** Set Field Amt 2	  */
	public void setFieldAmt2 (BigDecimal FieldAmt2);

	/** Get Field Amt 2	  */
	public BigDecimal getFieldAmt2();

    /** Column name FieldAmt3 */
    public static final String COLUMNNAME_FieldAmt3 = "FieldAmt3";

	/** Set Field Amt 3	  */
	public void setFieldAmt3 (BigDecimal FieldAmt3);

	/** Get Field Amt 3	  */
	public BigDecimal getFieldAmt3();

    /** Column name FieldAmt4 */
    public static final String COLUMNNAME_FieldAmt4 = "FieldAmt4";

	/** Set Field Amt 4	  */
	public void setFieldAmt4 (BigDecimal FieldAmt4);

	/** Get Field Amt 4	  */
	public BigDecimal getFieldAmt4();

    /** Column name FieldAmt5 */
    public static final String COLUMNNAME_FieldAmt5 = "FieldAmt5";

	/** Set Field Amt 5	  */
	public void setFieldAmt5 (BigDecimal FieldAmt5);

	/** Get Field Amt 5	  */
	public BigDecimal getFieldAmt5();

    /** Column name FieldAmt6 */
    public static final String COLUMNNAME_FieldAmt6 = "FieldAmt6";

	/** Set Field Amt 6	  */
	public void setFieldAmt6 (BigDecimal FieldAmt6);

	/** Get Field Amt 6	  */
	public BigDecimal getFieldAmt6();

    /** Column name FieldAmt7 */
    public static final String COLUMNNAME_FieldAmt7 = "FieldAmt7";

	/** Set Field Amt 7	  */
	public void setFieldAmt7 (BigDecimal FieldAmt7);

	/** Get Field Amt 7	  */
	public BigDecimal getFieldAmt7();

    /** Column name FieldAmt8 */
    public static final String COLUMNNAME_FieldAmt8 = "FieldAmt8";

	/** Set Field Amt 8	  */
	public void setFieldAmt8 (BigDecimal FieldAmt8);

	/** Get Field Amt 8	  */
	public BigDecimal getFieldAmt8();

    /** Column name FieldAmt9 */
    public static final String COLUMNNAME_FieldAmt9 = "FieldAmt9";

	/** Set Field Amt 9	  */
	public void setFieldAmt9 (BigDecimal FieldAmt9);

	/** Get Field Amt 9	  */
	public BigDecimal getFieldAmt9();

    /** Column name LCO_DIAN_Concept_ID */
    public static final String COLUMNNAME_LCO_DIAN_Concept_ID = "LCO_DIAN_Concept_ID";

	/** Set DIAN Concept	  */
	public void setLCO_DIAN_Concept_ID (int LCO_DIAN_Concept_ID);

	/** Get DIAN Concept	  */
	public int getLCO_DIAN_Concept_ID();

    /** Column name LCO_DIAN_SendSchedule_ID */
    public static final String COLUMNNAME_LCO_DIAN_SendSchedule_ID = "LCO_DIAN_SendSchedule_ID";

	/** Set DIAN Send Schedule	  */
	public void setLCO_DIAN_SendSchedule_ID (int LCO_DIAN_SendSchedule_ID);

	/** Get DIAN Send Schedule	  */
	public int getLCO_DIAN_SendSchedule_ID();

	public org.globalqss.model.I_LCO_DIAN_SendSchedule getLCO_DIAN_SendSchedule() throws Exception;

    /** Column name LCO_DIAN_SendScheduleLine_ID */
    public static final String COLUMNNAME_LCO_DIAN_SendScheduleLine_ID = "LCO_DIAN_SendScheduleLine_ID";

	/** Set DIAN Send Schedule Line	  */
	public void setLCO_DIAN_SendScheduleLine_ID (int LCO_DIAN_SendScheduleLine_ID);

	/** Get DIAN Send Schedule Line	  */
	public int getLCO_DIAN_SendScheduleLine_ID();

    /** Column name LCO_DIAN_XML_Header_ID */
    public static final String COLUMNNAME_LCO_DIAN_XML_Header_ID = "LCO_DIAN_XML_Header_ID";

	/** Set DIAN XML Header	  */
	public void setLCO_DIAN_XML_Header_ID (int LCO_DIAN_XML_Header_ID);

	/** Get DIAN XML Header	  */
	public int getLCO_DIAN_XML_Header_ID();

	public org.globalqss.model.I_LCO_DIAN_XML_Header getLCO_DIAN_XML_Header() throws Exception;
}
