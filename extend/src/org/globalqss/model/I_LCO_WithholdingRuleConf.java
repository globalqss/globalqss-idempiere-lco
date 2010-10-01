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

/** Generated Interface for LCO_WithholdingRuleConf
 *  @author Trifon Trifonov (generated) 
 *  @version Release 3.4.2s
 */
public interface I_LCO_WithholdingRuleConf 
{

    /** TableName=LCO_WithholdingRuleConf */
    public static final String Table_Name = "LCO_WithholdingRuleConf";

    /** AD_Table_ID=1000005 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

    /** Load Meta Data */

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

    /** Column name LCO_WithholdingType_ID */
    public static final String COLUMNNAME_LCO_WithholdingType_ID = "LCO_WithholdingType_ID";

	/** Set Withholding Type	  */
	public void setLCO_WithholdingType_ID (int LCO_WithholdingType_ID);

	/** Get Withholding Type	  */
	public int getLCO_WithholdingType_ID();

	public org.globalqss.model.I_LCO_WithholdingType getLCO_WithholdingType() throws Exception;
}
