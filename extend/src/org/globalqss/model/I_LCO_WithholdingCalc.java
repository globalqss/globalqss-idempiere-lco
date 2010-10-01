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

/** Generated Interface for LCO_WithholdingCalc
 *  @author Trifon Trifonov (generated) 
 *  @version Release 3.4.2s
 */
public interface I_LCO_WithholdingCalc 
{

    /** TableName=LCO_WithholdingCalc */
    public static final String Table_Name = "LCO_WithholdingCalc";

    /** AD_Table_ID=1000004 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

    /** Load Meta Data */

    /** Column name AmountRefunded */
    public static final String COLUMNNAME_AmountRefunded = "AmountRefunded";

	/** Set AmountRefunded	  */
	public void setAmountRefunded (BigDecimal AmountRefunded);

	/** Get AmountRefunded	  */
	public BigDecimal getAmountRefunded();

    /** Column name BaseType */
    public static final String COLUMNNAME_BaseType = "BaseType";

	/** Set Base Type	  */
	public void setBaseType (String BaseType);

	/** Get Base Type	  */
	public String getBaseType();

    /** Column name C_BaseTax_ID */
    public static final String COLUMNNAME_C_BaseTax_ID = "C_BaseTax_ID";

	/** Set Base Tax	  */
	public void setC_BaseTax_ID (int C_BaseTax_ID);

	/** Get Base Tax	  */
	public int getC_BaseTax_ID();

    /** Column name C_Tax_ID */
    public static final String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/** Set Tax.
	  * Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID);

	/** Get Tax.
	  * Tax identifier
	  */
	public int getC_Tax_ID();

	public I_C_Tax getC_Tax() throws Exception;

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

    /** Column name IsCalcOnInvoice */
    public static final String COLUMNNAME_IsCalcOnInvoice = "IsCalcOnInvoice";

	/** Set Is Calc On Invoice	  */
	public void setIsCalcOnInvoice (boolean IsCalcOnInvoice);

	/** Get Is Calc On Invoice	  */
	public boolean isCalcOnInvoice();

    /** Column name IsCalcOnPayment */
    public static final String COLUMNNAME_IsCalcOnPayment = "IsCalcOnPayment";

	/** Set Is Calc On Payment	  */
	public void setIsCalcOnPayment (boolean IsCalcOnPayment);

	/** Get Is Calc On Payment	  */
	public boolean isCalcOnPayment();

    /** Column name IsModifiableOnPayment */
    public static final String COLUMNNAME_IsModifiableOnPayment = "IsModifiableOnPayment";

	/** Set Is Modifiable On Payment	  */
	public void setIsModifiableOnPayment (boolean IsModifiableOnPayment);

	/** Get Is Modifiable On Payment	  */
	public boolean isModifiableOnPayment();

    /** Column name LCO_WithholdingCalc_ID */
    public static final String COLUMNNAME_LCO_WithholdingCalc_ID = "LCO_WithholdingCalc_ID";

	/** Set Withholding Calculation	  */
	public void setLCO_WithholdingCalc_ID (int LCO_WithholdingCalc_ID);

	/** Get Withholding Calculation	  */
	public int getLCO_WithholdingCalc_ID();

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

    /** Column name ThresholdMax */
    public static final String COLUMNNAME_ThresholdMax = "ThresholdMax";

	/** Set Threshold max.
	  * Maximum gross amount for withholding calculation  (0=no limit)
	  */
	public void setThresholdMax (BigDecimal ThresholdMax);

	/** Get Threshold max.
	  * Maximum gross amount for withholding calculation  (0=no limit)
	  */
	public BigDecimal getThresholdMax();

    /** Column name Thresholdmin */
    public static final String COLUMNNAME_Thresholdmin = "Thresholdmin";

	/** Set Threshold min.
	  * Minimum gross amount for withholding calculation
	  */
	public void setThresholdmin (BigDecimal Thresholdmin);

	/** Get Threshold min.
	  * Minimum gross amount for withholding calculation
	  */
	public BigDecimal getThresholdmin();
}
