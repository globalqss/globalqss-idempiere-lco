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

/** Generated Interface for LCO_DIAN_ConceptSource
 *  @author Trifon Trifonov (generated) 
 *  @version Release 3.4.2s
 */
public interface I_LCO_DIAN_ConceptSource 
{

    /** TableName=LCO_DIAN_ConceptSource */
    public static final String Table_Name = "LCO_DIAN_ConceptSource";

    /** AD_Table_ID=1000018 */
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

    /** Column name C_ElementValue_ID */
    public static final String COLUMNNAME_C_ElementValue_ID = "C_ElementValue_ID";

	/** Set Account Element.
	  * Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID);

	/** Get Account Element.
	  * Account Element
	  */
	public int getC_ElementValue_ID();

	public I_C_ElementValue getC_ElementValue() throws Exception;

    /** Column name FieldExpression */
    public static final String COLUMNNAME_FieldExpression = "FieldExpression";

	/** Set Field Expression	  */
	public void setFieldExpression (String FieldExpression);

	/** Get Field Expression	  */
	public String getFieldExpression();

    /** Column name GL_Category_ID */
    public static final String COLUMNNAME_GL_Category_ID = "GL_Category_ID";

	/** Set GL Category.
	  * General Ledger Category
	  */
	public void setGL_Category_ID (int GL_Category_ID);

	/** Get GL Category.
	  * General Ledger Category
	  */
	public int getGL_Category_ID();

	public I_GL_Category getGL_Category() throws Exception;

    /** Column name LCO_DIAN_Concept_ID */
    public static final String COLUMNNAME_LCO_DIAN_Concept_ID = "LCO_DIAN_Concept_ID";

	/** Set DIAN Concept	  */
	public void setLCO_DIAN_Concept_ID (int LCO_DIAN_Concept_ID);

	/** Get DIAN Concept	  */
	public int getLCO_DIAN_Concept_ID();

	public org.globalqss.model.I_LCO_DIAN_Concept getLCO_DIAN_Concept() throws Exception;

    /** Column name LCO_DIAN_ConceptSource_ID */
    public static final String COLUMNNAME_LCO_DIAN_ConceptSource_ID = "LCO_DIAN_ConceptSource_ID";

	/** Set DIAN Concept Source	  */
	public void setLCO_DIAN_ConceptSource_ID (int LCO_DIAN_ConceptSource_ID);

	/** Get DIAN Concept Source	  */
	public int getLCO_DIAN_ConceptSource_ID();
}
