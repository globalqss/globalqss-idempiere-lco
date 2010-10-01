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

/** Generated Interface for LCO_ConversionCode
 *  @author Trifon Trifonov (generated) 
 *  @version Release 3.4.2s
 */
public interface I_LCO_ConversionCode 
{

    /** TableName=LCO_ConversionCode */
    public static final String Table_Name = "LCO_ConversionCode";

    /** AD_Table_ID=1000021 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

    /** Load Meta Data */

    /** Column name ExternalCode */
    public static final String COLUMNNAME_ExternalCode = "ExternalCode";

	/** Set External Code	  */
	public void setExternalCode (String ExternalCode);

	/** Get External Code	  */
	public String getExternalCode();

    /** Column name InternalCode */
    public static final String COLUMNNAME_InternalCode = "InternalCode";

	/** Set Internal Code	  */
	public void setInternalCode (String InternalCode);

	/** Get Internal Code	  */
	public String getInternalCode();

    /** Column name LCO_ConversionCode_ID */
    public static final String COLUMNNAME_LCO_ConversionCode_ID = "LCO_ConversionCode_ID";

	/** Set LCO_ConversionCode	  */
	public void setLCO_ConversionCode_ID (int LCO_ConversionCode_ID);

	/** Get LCO_ConversionCode	  */
	public int getLCO_ConversionCode_ID();

    /** Column name LCO_Conversion_ID */
    public static final String COLUMNNAME_LCO_Conversion_ID = "LCO_Conversion_ID";

	/** Set LCO_Conversion	  */
	public void setLCO_Conversion_ID (int LCO_Conversion_ID);

	/** Get LCO_Conversion	  */
	public int getLCO_Conversion_ID();

	public org.globalqss.model.I_LCO_Conversion getLCO_Conversion() throws Exception;
}
