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

/** Generated Interface for LCO_DIAN_FieldFormat
 *  @author Trifon Trifonov (generated) 
 *  @version Release 3.4.2s
 */
public interface I_LCO_DIAN_FieldFormat 
{

    /** TableName=LCO_DIAN_FieldFormat */
    public static final String Table_Name = "LCO_DIAN_FieldFormat";

    /** AD_Table_ID=1000017 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

    /** Load Meta Data */

    /** Column name CalcColumnPosition */
    public static final String COLUMNNAME_CalcColumnPosition = "CalcColumnPosition";

	/** Set CalcColumnPosition	  */
	public void setCalcColumnPosition (int CalcColumnPosition);

	/** Get CalcColumnPosition	  */
	public int getCalcColumnPosition();

    /** Column name FieldPrintFormat */
    public static final String COLUMNNAME_FieldPrintFormat = "FieldPrintFormat";

	/** Set Field Print Format	  */
	public void setFieldPrintFormat (String FieldPrintFormat);

	/** Get Field Print Format	  */
	public String getFieldPrintFormat();

    /** Column name LCO_DIAN_FieldFormat_ID */
    public static final String COLUMNNAME_LCO_DIAN_FieldFormat_ID = "LCO_DIAN_FieldFormat_ID";

	/** Set DIAN Field Format	  */
	public void setLCO_DIAN_FieldFormat_ID (int LCO_DIAN_FieldFormat_ID);

	/** Get DIAN Field Format	  */
	public int getLCO_DIAN_FieldFormat_ID();

    /** Column name LCO_DIAN_Format_ID */
    public static final String COLUMNNAME_LCO_DIAN_Format_ID = "LCO_DIAN_Format_ID";

	/** Set DIAN Format	  */
	public void setLCO_DIAN_Format_ID (int LCO_DIAN_Format_ID);

	/** Get DIAN Format	  */
	public int getLCO_DIAN_Format_ID();

    /** Column name LCO_DIAN_XMLPrintLabel_ID */
    public static final String COLUMNNAME_LCO_DIAN_XMLPrintLabel_ID = "LCO_DIAN_XMLPrintLabel_ID";

	/** Set DIAN XML Print Label	  */
	public void setLCO_DIAN_XMLPrintLabel_ID (int LCO_DIAN_XMLPrintLabel_ID);

	/** Get DIAN XML Print Label	  */
	public int getLCO_DIAN_XMLPrintLabel_ID();

    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/** Set Line No.
	  * Unique line for this document
	  */
	public void setLine (int Line);

	/** Get Line No.
	  * Unique line for this document
	  */
	public int getLine();

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
