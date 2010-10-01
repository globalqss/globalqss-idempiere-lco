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

/** Generated Interface for LCO_TaxIdType
 *  @author Trifon Trifonov (generated) 
 *  @version Release 3.4.2s
 */
public interface I_LCO_TaxIdType 
{

    /** TableName=LCO_TaxIdType */
    public static final String Table_Name = "LCO_TaxIdType";

    /** AD_Table_ID=1000010 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

    /** Load Meta Data */

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

    /** Column name IsDetailedNames */
    public static final String COLUMNNAME_IsDetailedNames = "IsDetailedNames";

	/** Set Detailed Names	  */
	public void setIsDetailedNames (boolean IsDetailedNames);

	/** Get Detailed Names	  */
	public boolean isDetailedNames();

    /** Column name IsDigitChecked */
    public static final String COLUMNNAME_IsDigitChecked = "IsDigitChecked";

	/** Set Is Digit Checked	  */
	public void setIsDigitChecked (boolean IsDigitChecked);

	/** Get Is Digit Checked	  */
	public boolean isDigitChecked();

    /** Column name IsUseTaxIdDigit */
    public static final String COLUMNNAME_IsUseTaxIdDigit = "IsUseTaxIdDigit";

	/** Set Use Tax Id Digit	  */
	public void setIsUseTaxIdDigit (boolean IsUseTaxIdDigit);

	/** Get Use Tax Id Digit	  */
	public boolean isUseTaxIdDigit();

    /** Column name LCO_TaxCodeDian */
    public static final String COLUMNNAME_LCO_TaxCodeDian = "LCO_TaxCodeDian";

	/** Set Tax Code DIAN	  */
	public void setLCO_TaxCodeDian (String LCO_TaxCodeDian);

	/** Get Tax Code DIAN	  */
	public String getLCO_TaxCodeDian();

    /** Column name LCO_TaxIdType_ID */
    public static final String COLUMNNAME_LCO_TaxIdType_ID = "LCO_TaxIdType_ID";

	/** Set Tax ID Type	  */
	public void setLCO_TaxIdType_ID (int LCO_TaxIdType_ID);

	/** Get Tax ID Type	  */
	public int getLCO_TaxIdType_ID();

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
}
