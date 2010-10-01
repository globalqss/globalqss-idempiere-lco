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
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for LCO_WithholdingRule
 *  @author Trifon Trifonov (generated) 
 *  @version Release 3.4.2s
 */
public interface I_LCO_WithholdingRule 
{

    /** TableName=LCO_WithholdingRule */
    public static final String Table_Name = "LCO_WithholdingRule";

    /** AD_Table_ID=1000006 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

    /** Load Meta Data */

    /** Column name C_TaxCategory_ID */
    public static final String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

	/** Set Tax Category.
	  * Tax Category
	  */
	public void setC_TaxCategory_ID (int C_TaxCategory_ID);

	/** Get Tax Category.
	  * Tax Category
	  */
	public int getC_TaxCategory_ID();

	public I_C_TaxCategory getC_TaxCategory() throws Exception;

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

    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

	/** Set Default.
	  * Default value
	  */
	public void setIsDefault (boolean IsDefault);

	/** Get Default.
	  * Default value
	  */
	public boolean isDefault();

    /** Column name IsUseBPCity */
    public static final String COLUMNNAME_IsUseBPCity = "IsUseBPCity";

	/** Set Is Use BP City	  */
	public void setIsUseBPCity (boolean IsUseBPCity);

	/** Get Is Use BP City	  */
	public boolean isUseBPCity();

    /** Column name IsUseBPISIC */
    public static final String COLUMNNAME_IsUseBPISIC = "IsUseBPISIC";

	/** Set Is Use BP ISIC	  */
	public void setIsUseBPISIC (boolean IsUseBPISIC);

	/** Get Is Use BP ISIC	  */
	public boolean isUseBPISIC();

    /** Column name IsUseBPTaxPayerType */
    public static final String COLUMNNAME_IsUseBPTaxPayerType = "IsUseBPTaxPayerType";

	/** Set Is Use BP Tax Payer Type	  */
	public void setIsUseBPTaxPayerType (boolean IsUseBPTaxPayerType);

	/** Get Is Use BP Tax Payer Type	  */
	public boolean isUseBPTaxPayerType();

    /** Column name IsUseOrgCity */
    public static final String COLUMNNAME_IsUseOrgCity = "IsUseOrgCity";

	/** Set Is Use Org City	  */
	public void setIsUseOrgCity (boolean IsUseOrgCity);

	/** Get Is Use Org City	  */
	public boolean isUseOrgCity();

    /** Column name IsUseOrgISIC */
    public static final String COLUMNNAME_IsUseOrgISIC = "IsUseOrgISIC";

	/** Set Is Use Org ISIC	  */
	public void setIsUseOrgISIC (boolean IsUseOrgISIC);

	/** Get Is Use Org ISIC	  */
	public boolean isUseOrgISIC();

    /** Column name IsUseOrgTaxPayerType */
    public static final String COLUMNNAME_IsUseOrgTaxPayerType = "IsUseOrgTaxPayerType";

	/** Set Is Use Org Tax Payer Type	  */
	public void setIsUseOrgTaxPayerType (boolean IsUseOrgTaxPayerType);

	/** Get Is Use Org Tax Payer Type	  */
	public boolean isUseOrgTaxPayerType();

    /** Column name IsUseProductTaxCategory */
    public static final String COLUMNNAME_IsUseProductTaxCategory = "IsUseProductTaxCategory";

	/** Set Is Use Product Tax Category	  */
	public void setIsUseProductTaxCategory (boolean IsUseProductTaxCategory);

	/** Get Is Use Product Tax Category	  */
	public boolean isUseProductTaxCategory();

    /** Column name IsUseWithholdingCategory */
    public static final String COLUMNNAME_IsUseWithholdingCategory = "IsUseWithholdingCategory";

	/** Set Is Use Withholding Category	  */
	public void setIsUseWithholdingCategory (boolean IsUseWithholdingCategory);

	/** Get Is Use Withholding Category	  */
	public boolean isUseWithholdingCategory();

    /** Column name LCO_BP_City_ID */
    public static final String COLUMNNAME_LCO_BP_City_ID = "LCO_BP_City_ID";

	/** Set City Business Partner	  */
	public void setLCO_BP_City_ID (int LCO_BP_City_ID);

	/** Get City Business Partner	  */
	public int getLCO_BP_City_ID();

    /** Column name LCO_BP_ISIC_ID */
    public static final String COLUMNNAME_LCO_BP_ISIC_ID = "LCO_BP_ISIC_ID";

	/** Set ISIC Business Partner	  */
	public void setLCO_BP_ISIC_ID (int LCO_BP_ISIC_ID);

	/** Get ISIC Business Partner	  */
	public int getLCO_BP_ISIC_ID();

    /** Column name LCO_BP_TaxPayerType_ID */
    public static final String COLUMNNAME_LCO_BP_TaxPayerType_ID = "LCO_BP_TaxPayerType_ID";

	/** Set Tax Payer Type Business Partner	  */
	public void setLCO_BP_TaxPayerType_ID (int LCO_BP_TaxPayerType_ID);

	/** Get Tax Payer Type Business Partner	  */
	public int getLCO_BP_TaxPayerType_ID();

    /** Column name LCO_Org_City_ID */
    public static final String COLUMNNAME_LCO_Org_City_ID = "LCO_Org_City_ID";

	/** Set City Organization	  */
	public void setLCO_Org_City_ID (int LCO_Org_City_ID);

	/** Get City Organization	  */
	public int getLCO_Org_City_ID();

    /** Column name LCO_Org_ISIC_ID */
    public static final String COLUMNNAME_LCO_Org_ISIC_ID = "LCO_Org_ISIC_ID";

	/** Set ISIC Organization	  */
	public void setLCO_Org_ISIC_ID (int LCO_Org_ISIC_ID);

	/** Get ISIC Organization	  */
	public int getLCO_Org_ISIC_ID();

    /** Column name LCO_Org_TaxPayerType_ID */
    public static final String COLUMNNAME_LCO_Org_TaxPayerType_ID = "LCO_Org_TaxPayerType_ID";

	/** Set Tax Payer Type Organization	  */
	public void setLCO_Org_TaxPayerType_ID (int LCO_Org_TaxPayerType_ID);

	/** Get Tax Payer Type Organization	  */
	public int getLCO_Org_TaxPayerType_ID();

    /** Column name LCO_WithholdingCalc_ID */
    public static final String COLUMNNAME_LCO_WithholdingCalc_ID = "LCO_WithholdingCalc_ID";

	/** Set Withholding Calculation	  */
	public void setLCO_WithholdingCalc_ID (int LCO_WithholdingCalc_ID);

	/** Get Withholding Calculation	  */
	public int getLCO_WithholdingCalc_ID();

	public org.globalqss.model.I_LCO_WithholdingCalc getLCO_WithholdingCalc() throws Exception;

    /** Column name LCO_WithholdingCategory_ID */
    public static final String COLUMNNAME_LCO_WithholdingCategory_ID = "LCO_WithholdingCategory_ID";

	/** Set Withholding Category	  */
	public void setLCO_WithholdingCategory_ID (int LCO_WithholdingCategory_ID);

	/** Get Withholding Category	  */
	public int getLCO_WithholdingCategory_ID();

	public org.globalqss.model.I_LCO_WithholdingCategory getLCO_WithholdingCategory() throws Exception;

    /** Column name LCO_WithholdingRule_ID */
    public static final String COLUMNNAME_LCO_WithholdingRule_ID = "LCO_WithholdingRule_ID";

	/** Set Withholding Rule	  */
	public void setLCO_WithholdingRule_ID (int LCO_WithholdingRule_ID);

	/** Get Withholding Rule	  */
	public int getLCO_WithholdingRule_ID();

    /** Column name LCO_WithholdingType_ID */
    public static final String COLUMNNAME_LCO_WithholdingType_ID = "LCO_WithholdingType_ID";

	/** Set Withholding Type	  */
	public void setLCO_WithholdingType_ID (int LCO_WithholdingType_ID);

	/** Get Withholding Type	  */
	public int getLCO_WithholdingType_ID();

	public org.globalqss.model.I_LCO_WithholdingType getLCO_WithholdingType() throws Exception;

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
}
