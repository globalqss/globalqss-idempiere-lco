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

/** Generated Interface for LCO_DIAN_SendScheduleLine
 *  @author iDempiere (generated) 
 *  @version Release 8.2
 */
@SuppressWarnings("all")
public interface I_LCO_DIAN_SendScheduleLine 
{

    /** TableName=LCO_DIAN_SendScheduleLine */
    public static final String Table_Name = "LCO_DIAN_SendScheduleLine";

    /** AD_Table_ID=1000009 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

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

    /** Column name AttributeNm1 */
    public static final String COLUMNNAME_AttributeNm1 = "AttributeNm1";

	/** Set Attribute Name 1.
	  * Name of the Attribute 1
	  */
	public void setAttributeNm1 (String AttributeNm1);

	/** Get Attribute Name 1.
	  * Name of the Attribute 1
	  */
	public String getAttributeNm1();

    /** Column name AttributeNm10 */
    public static final String COLUMNNAME_AttributeNm10 = "AttributeNm10";

	/** Set Attribute Name 10.
	  * Name of the Attribute 10
	  */
	public void setAttributeNm10 (String AttributeNm10);

	/** Get Attribute Name 10.
	  * Name of the Attribute 10
	  */
	public String getAttributeNm10();

    /** Column name AttributeNm11 */
    public static final String COLUMNNAME_AttributeNm11 = "AttributeNm11";

	/** Set Attribute Name 11.
	  * Name of the Attribute 11
	  */
	public void setAttributeNm11 (String AttributeNm11);

	/** Get Attribute Name 11.
	  * Name of the Attribute 11
	  */
	public String getAttributeNm11();

    /** Column name AttributeNm12 */
    public static final String COLUMNNAME_AttributeNm12 = "AttributeNm12";

	/** Set Attribute Name 12.
	  * Name of the Attribute 12
	  */
	public void setAttributeNm12 (String AttributeNm12);

	/** Get Attribute Name 12.
	  * Name of the Attribute 12
	  */
	public String getAttributeNm12();

    /** Column name AttributeNm13 */
    public static final String COLUMNNAME_AttributeNm13 = "AttributeNm13";

	/** Set Attribute Name 13.
	  * Name of the Attribute 13
	  */
	public void setAttributeNm13 (String AttributeNm13);

	/** Get Attribute Name 13.
	  * Name of the Attribute 13
	  */
	public String getAttributeNm13();

    /** Column name AttributeNm14 */
    public static final String COLUMNNAME_AttributeNm14 = "AttributeNm14";

	/** Set Attribute Name 14.
	  * Name of the Attribute 14
	  */
	public void setAttributeNm14 (String AttributeNm14);

	/** Get Attribute Name 14.
	  * Name of the Attribute 14
	  */
	public String getAttributeNm14();

    /** Column name AttributeNm15 */
    public static final String COLUMNNAME_AttributeNm15 = "AttributeNm15";

	/** Set Attribute Name 15.
	  * Name of the Attribute 15
	  */
	public void setAttributeNm15 (String AttributeNm15);

	/** Get Attribute Name 15.
	  * Name of the Attribute 15
	  */
	public String getAttributeNm15();

    /** Column name AttributeNm16 */
    public static final String COLUMNNAME_AttributeNm16 = "AttributeNm16";

	/** Set Attribute Name 16.
	  * Name of the Attribute 16
	  */
	public void setAttributeNm16 (String AttributeNm16);

	/** Get Attribute Name 16.
	  * Name of the Attribute 16
	  */
	public String getAttributeNm16();

    /** Column name AttributeNm17 */
    public static final String COLUMNNAME_AttributeNm17 = "AttributeNm17";

	/** Set Attribute Name 17.
	  * Name of the Attribute 17
	  */
	public void setAttributeNm17 (String AttributeNm17);

	/** Get Attribute Name 17.
	  * Name of the Attribute 17
	  */
	public String getAttributeNm17();

    /** Column name AttributeNm18 */
    public static final String COLUMNNAME_AttributeNm18 = "AttributeNm18";

	/** Set Attribute Name 18.
	  * Name of the Attribute 18
	  */
	public void setAttributeNm18 (String AttributeNm18);

	/** Get Attribute Name 18.
	  * Name of the Attribute 18
	  */
	public String getAttributeNm18();

    /** Column name AttributeNm19 */
    public static final String COLUMNNAME_AttributeNm19 = "AttributeNm19";

	/** Set Attribute Name 19.
	  * Name of the Attribute 19
	  */
	public void setAttributeNm19 (String AttributeNm19);

	/** Get Attribute Name 19.
	  * Name of the Attribute 19
	  */
	public String getAttributeNm19();

    /** Column name AttributeNm2 */
    public static final String COLUMNNAME_AttributeNm2 = "AttributeNm2";

	/** Set Attribute Name 2.
	  * Name of the Attribute 2
	  */
	public void setAttributeNm2 (String AttributeNm2);

	/** Get Attribute Name 2.
	  * Name of the Attribute 2
	  */
	public String getAttributeNm2();

    /** Column name AttributeNm20 */
    public static final String COLUMNNAME_AttributeNm20 = "AttributeNm20";

	/** Set Attribute Name 20.
	  * Name of the Attribute 20
	  */
	public void setAttributeNm20 (String AttributeNm20);

	/** Get Attribute Name 20.
	  * Name of the Attribute 20
	  */
	public String getAttributeNm20();

    /** Column name AttributeNm21 */
    public static final String COLUMNNAME_AttributeNm21 = "AttributeNm21";

	/** Set Attribute Name 21.
	  * Name of the Attribute 21
	  */
	public void setAttributeNm21 (String AttributeNm21);

	/** Get Attribute Name 21.
	  * Name of the Attribute 21
	  */
	public String getAttributeNm21();

    /** Column name AttributeNm22 */
    public static final String COLUMNNAME_AttributeNm22 = "AttributeNm22";

	/** Set Attribute Name 22.
	  * Name of the Attribute 22
	  */
	public void setAttributeNm22 (String AttributeNm22);

	/** Get Attribute Name 22.
	  * Name of the Attribute 22
	  */
	public String getAttributeNm22();

    /** Column name AttributeNm23 */
    public static final String COLUMNNAME_AttributeNm23 = "AttributeNm23";

	/** Set Attribute Name 23.
	  * Name of the Attribute 23
	  */
	public void setAttributeNm23 (String AttributeNm23);

	/** Get Attribute Name 23.
	  * Name of the Attribute 23
	  */
	public String getAttributeNm23();

    /** Column name AttributeNm24 */
    public static final String COLUMNNAME_AttributeNm24 = "AttributeNm24";

	/** Set Attribute Name 24.
	  * Name of the Attribute 24
	  */
	public void setAttributeNm24 (String AttributeNm24);

	/** Get Attribute Name 24.
	  * Name of the Attribute 24
	  */
	public String getAttributeNm24();

    /** Column name AttributeNm25 */
    public static final String COLUMNNAME_AttributeNm25 = "AttributeNm25";

	/** Set Attribute Name 25.
	  * Name of the Attribute 25
	  */
	public void setAttributeNm25 (String AttributeNm25);

	/** Get Attribute Name 25.
	  * Name of the Attribute 25
	  */
	public String getAttributeNm25();

    /** Column name AttributeNm26 */
    public static final String COLUMNNAME_AttributeNm26 = "AttributeNm26";

	/** Set Attribute Name 26.
	  * Name of the Attribute 26
	  */
	public void setAttributeNm26 (String AttributeNm26);

	/** Get Attribute Name 26.
	  * Name of the Attribute 26
	  */
	public String getAttributeNm26();

    /** Column name AttributeNm27 */
    public static final String COLUMNNAME_AttributeNm27 = "AttributeNm27";

	/** Set Attribute Name 27.
	  * Name of the Attribute 27
	  */
	public void setAttributeNm27 (String AttributeNm27);

	/** Get Attribute Name 27.
	  * Name of the Attribute 27
	  */
	public String getAttributeNm27();

    /** Column name AttributeNm28 */
    public static final String COLUMNNAME_AttributeNm28 = "AttributeNm28";

	/** Set Attribute Name 28.
	  * Name of the Attribute 28
	  */
	public void setAttributeNm28 (String AttributeNm28);

	/** Get Attribute Name 28.
	  * Name of the Attribute 28
	  */
	public String getAttributeNm28();

    /** Column name AttributeNm29 */
    public static final String COLUMNNAME_AttributeNm29 = "AttributeNm29";

	/** Set Attribute Name 29.
	  * Name of the Attribute 29
	  */
	public void setAttributeNm29 (String AttributeNm29);

	/** Get Attribute Name 29.
	  * Name of the Attribute 29
	  */
	public String getAttributeNm29();

    /** Column name AttributeNm3 */
    public static final String COLUMNNAME_AttributeNm3 = "AttributeNm3";

	/** Set Attribute Name 3.
	  * Name of the Attribute 3
	  */
	public void setAttributeNm3 (String AttributeNm3);

	/** Get Attribute Name 3.
	  * Name of the Attribute 3
	  */
	public String getAttributeNm3();

    /** Column name AttributeNm30 */
    public static final String COLUMNNAME_AttributeNm30 = "AttributeNm30";

	/** Set Attribute Name 30.
	  * Name of the Attribute 30
	  */
	public void setAttributeNm30 (String AttributeNm30);

	/** Get Attribute Name 30.
	  * Name of the Attribute 30
	  */
	public String getAttributeNm30();

    /** Column name AttributeNm31 */
    public static final String COLUMNNAME_AttributeNm31 = "AttributeNm31";

	/** Set Attribute Name 31.
	  * Name of the Attribute 31
	  */
	public void setAttributeNm31 (String AttributeNm31);

	/** Get Attribute Name 31.
	  * Name of the Attribute 31
	  */
	public String getAttributeNm31();

    /** Column name AttributeNm32 */
    public static final String COLUMNNAME_AttributeNm32 = "AttributeNm32";

	/** Set Attribute Name 32.
	  * Name of the Attribute 32
	  */
	public void setAttributeNm32 (String AttributeNm32);

	/** Get Attribute Name 32.
	  * Name of the Attribute 32
	  */
	public String getAttributeNm32();

    /** Column name AttributeNm33 */
    public static final String COLUMNNAME_AttributeNm33 = "AttributeNm33";

	/** Set Attribute Name 33.
	  * Name of the Attribute 33
	  */
	public void setAttributeNm33 (String AttributeNm33);

	/** Get Attribute Name 33.
	  * Name of the Attribute 33
	  */
	public String getAttributeNm33();

    /** Column name AttributeNm34 */
    public static final String COLUMNNAME_AttributeNm34 = "AttributeNm34";

	/** Set Attribute Name 34.
	  * Name of the Attribute 34
	  */
	public void setAttributeNm34 (String AttributeNm34);

	/** Get Attribute Name 34.
	  * Name of the Attribute 34
	  */
	public String getAttributeNm34();

    /** Column name AttributeNm35 */
    public static final String COLUMNNAME_AttributeNm35 = "AttributeNm35";

	/** Set Attribute Name 35.
	  * Name of the Attribute 35
	  */
	public void setAttributeNm35 (String AttributeNm35);

	/** Get Attribute Name 35.
	  * Name of the Attribute 35
	  */
	public String getAttributeNm35();

    /** Column name AttributeNm36 */
    public static final String COLUMNNAME_AttributeNm36 = "AttributeNm36";

	/** Set Attribute Name 36.
	  * Name of the Attribute 36
	  */
	public void setAttributeNm36 (String AttributeNm36);

	/** Get Attribute Name 36.
	  * Name of the Attribute 36
	  */
	public String getAttributeNm36();

    /** Column name AttributeNm37 */
    public static final String COLUMNNAME_AttributeNm37 = "AttributeNm37";

	/** Set Attribute Name 37.
	  * Name of the Attribute 37
	  */
	public void setAttributeNm37 (String AttributeNm37);

	/** Get Attribute Name 37.
	  * Name of the Attribute 37
	  */
	public String getAttributeNm37();

    /** Column name AttributeNm38 */
    public static final String COLUMNNAME_AttributeNm38 = "AttributeNm38";

	/** Set Attribute Name 38.
	  * Name of the Attribute 38
	  */
	public void setAttributeNm38 (String AttributeNm38);

	/** Get Attribute Name 38.
	  * Name of the Attribute 38
	  */
	public String getAttributeNm38();

    /** Column name AttributeNm39 */
    public static final String COLUMNNAME_AttributeNm39 = "AttributeNm39";

	/** Set Attribute Name 39.
	  * Name of the Attribute 39
	  */
	public void setAttributeNm39 (String AttributeNm39);

	/** Get Attribute Name 39.
	  * Name of the Attribute 39
	  */
	public String getAttributeNm39();

    /** Column name AttributeNm4 */
    public static final String COLUMNNAME_AttributeNm4 = "AttributeNm4";

	/** Set Attribute Name 4.
	  * Name of the Attribute 4
	  */
	public void setAttributeNm4 (String AttributeNm4);

	/** Get Attribute Name 4.
	  * Name of the Attribute 4
	  */
	public String getAttributeNm4();

    /** Column name AttributeNm40 */
    public static final String COLUMNNAME_AttributeNm40 = "AttributeNm40";

	/** Set Attribute Name 40.
	  * Name of the Attribute 40
	  */
	public void setAttributeNm40 (String AttributeNm40);

	/** Get Attribute Name 40.
	  * Name of the Attribute 40
	  */
	public String getAttributeNm40();

    /** Column name AttributeNm41 */
    public static final String COLUMNNAME_AttributeNm41 = "AttributeNm41";

	/** Set Attribute Name 41.
	  * Name of the Attribute 41
	  */
	public void setAttributeNm41 (String AttributeNm41);

	/** Get Attribute Name 41.
	  * Name of the Attribute 41
	  */
	public String getAttributeNm41();

    /** Column name AttributeNm42 */
    public static final String COLUMNNAME_AttributeNm42 = "AttributeNm42";

	/** Set Attribute Name 42.
	  * Name of the Attribute 42
	  */
	public void setAttributeNm42 (String AttributeNm42);

	/** Get Attribute Name 42.
	  * Name of the Attribute 42
	  */
	public String getAttributeNm42();

    /** Column name AttributeNm43 */
    public static final String COLUMNNAME_AttributeNm43 = "AttributeNm43";

	/** Set Attribute Name 43.
	  * Name of the Attribute 43
	  */
	public void setAttributeNm43 (String AttributeNm43);

	/** Get Attribute Name 43.
	  * Name of the Attribute 43
	  */
	public String getAttributeNm43();

    /** Column name AttributeNm44 */
    public static final String COLUMNNAME_AttributeNm44 = "AttributeNm44";

	/** Set Attribute Name 44.
	  * Name of the Attribute 44
	  */
	public void setAttributeNm44 (String AttributeNm44);

	/** Get Attribute Name 44.
	  * Name of the Attribute 44
	  */
	public String getAttributeNm44();

    /** Column name AttributeNm45 */
    public static final String COLUMNNAME_AttributeNm45 = "AttributeNm45";

	/** Set Attribute Name 45.
	  * Name of the Attribute 45
	  */
	public void setAttributeNm45 (String AttributeNm45);

	/** Get Attribute Name 45.
	  * Name of the Attribute 45
	  */
	public String getAttributeNm45();

    /** Column name AttributeNm46 */
    public static final String COLUMNNAME_AttributeNm46 = "AttributeNm46";

	/** Set Attribute Name 46.
	  * Name of the Attribute 46
	  */
	public void setAttributeNm46 (String AttributeNm46);

	/** Get Attribute Name 46.
	  * Name of the Attribute 46
	  */
	public String getAttributeNm46();

    /** Column name AttributeNm47 */
    public static final String COLUMNNAME_AttributeNm47 = "AttributeNm47";

	/** Set Attribute Name 47.
	  * Name of the Attribute 47
	  */
	public void setAttributeNm47 (String AttributeNm47);

	/** Get Attribute Name 47.
	  * Name of the Attribute 47
	  */
	public String getAttributeNm47();

    /** Column name AttributeNm48 */
    public static final String COLUMNNAME_AttributeNm48 = "AttributeNm48";

	/** Set Attribute Name 48.
	  * Name of the Attribute 48
	  */
	public void setAttributeNm48 (String AttributeNm48);

	/** Get Attribute Name 48.
	  * Name of the Attribute 48
	  */
	public String getAttributeNm48();

    /** Column name AttributeNm49 */
    public static final String COLUMNNAME_AttributeNm49 = "AttributeNm49";

	/** Set Attribute Name 49.
	  * Name of the Attribute 49
	  */
	public void setAttributeNm49 (String AttributeNm49);

	/** Get Attribute Name 49.
	  * Name of the Attribute 49
	  */
	public String getAttributeNm49();

    /** Column name AttributeNm5 */
    public static final String COLUMNNAME_AttributeNm5 = "AttributeNm5";

	/** Set Attribute Name 5.
	  * Name of the Attribute 5
	  */
	public void setAttributeNm5 (String AttributeNm5);

	/** Get Attribute Name 5.
	  * Name of the Attribute 5
	  */
	public String getAttributeNm5();

    /** Column name AttributeNm50 */
    public static final String COLUMNNAME_AttributeNm50 = "AttributeNm50";

	/** Set Attribute Name 50.
	  * Name of the Attribute 50
	  */
	public void setAttributeNm50 (String AttributeNm50);

	/** Get Attribute Name 50.
	  * Name of the Attribute 50
	  */
	public String getAttributeNm50();

    /** Column name AttributeNm6 */
    public static final String COLUMNNAME_AttributeNm6 = "AttributeNm6";

	/** Set Attribute Name 6.
	  * Name of the Attribute 6
	  */
	public void setAttributeNm6 (String AttributeNm6);

	/** Get Attribute Name 6.
	  * Name of the Attribute 6
	  */
	public String getAttributeNm6();

    /** Column name AttributeNm7 */
    public static final String COLUMNNAME_AttributeNm7 = "AttributeNm7";

	/** Set Attribute Name 7.
	  * Name of the Attribute 7
	  */
	public void setAttributeNm7 (String AttributeNm7);

	/** Get Attribute Name 7.
	  * Name of the Attribute 7
	  */
	public String getAttributeNm7();

    /** Column name AttributeNm8 */
    public static final String COLUMNNAME_AttributeNm8 = "AttributeNm8";

	/** Set Attribute Name 8.
	  * Name of the Attribute 8
	  */
	public void setAttributeNm8 (String AttributeNm8);

	/** Get Attribute Name 8.
	  * Name of the Attribute 8
	  */
	public String getAttributeNm8();

    /** Column name AttributeNm9 */
    public static final String COLUMNNAME_AttributeNm9 = "AttributeNm9";

	/** Set Attribute Name 9.
	  * Name of the Attribute 9
	  */
	public void setAttributeNm9 (String AttributeNm9);

	/** Get Attribute Name 9.
	  * Name of the Attribute 9
	  */
	public String getAttributeNm9();

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

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

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

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

    /** Column name C_BPartnerRelation_ID */
    public static final String COLUMNNAME_C_BPartnerRelation_ID = "C_BPartnerRelation_ID";

	/** Set Related Partner.
	  * Related Business Partner
	  */
	public void setC_BPartnerRelation_ID (int C_BPartnerRelation_ID);

	/** Get Related Partner.
	  * Related Business Partner
	  */
	public int getC_BPartnerRelation_ID();

	public org.compiere.model.I_C_BPartner getC_BPartnerRelation() throws RuntimeException;

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

    /** Column name FieldAmt11 */
    public static final String COLUMNNAME_FieldAmt11 = "FieldAmt11";

	/** Set Field Amt 11	  */
	public void setFieldAmt11 (BigDecimal FieldAmt11);

	/** Get Field Amt 11	  */
	public BigDecimal getFieldAmt11();

    /** Column name FieldAmt12 */
    public static final String COLUMNNAME_FieldAmt12 = "FieldAmt12";

	/** Set Field Amt 12	  */
	public void setFieldAmt12 (BigDecimal FieldAmt12);

	/** Get Field Amt 12	  */
	public BigDecimal getFieldAmt12();

    /** Column name FieldAmt13 */
    public static final String COLUMNNAME_FieldAmt13 = "FieldAmt13";

	/** Set Field Amt 13	  */
	public void setFieldAmt13 (BigDecimal FieldAmt13);

	/** Get Field Amt 13	  */
	public BigDecimal getFieldAmt13();

    /** Column name FieldAmt14 */
    public static final String COLUMNNAME_FieldAmt14 = "FieldAmt14";

	/** Set Field Amt 14	  */
	public void setFieldAmt14 (BigDecimal FieldAmt14);

	/** Get Field Amt 14	  */
	public BigDecimal getFieldAmt14();

    /** Column name FieldAmt15 */
    public static final String COLUMNNAME_FieldAmt15 = "FieldAmt15";

	/** Set Field Amt 15	  */
	public void setFieldAmt15 (BigDecimal FieldAmt15);

	/** Get Field Amt 15	  */
	public BigDecimal getFieldAmt15();

    /** Column name FieldAmt16 */
    public static final String COLUMNNAME_FieldAmt16 = "FieldAmt16";

	/** Set Field Amt 16	  */
	public void setFieldAmt16 (BigDecimal FieldAmt16);

	/** Get Field Amt 16	  */
	public BigDecimal getFieldAmt16();

    /** Column name FieldAmt17 */
    public static final String COLUMNNAME_FieldAmt17 = "FieldAmt17";

	/** Set Field Amt 17	  */
	public void setFieldAmt17 (BigDecimal FieldAmt17);

	/** Get Field Amt 17	  */
	public BigDecimal getFieldAmt17();

    /** Column name FieldAmt18 */
    public static final String COLUMNNAME_FieldAmt18 = "FieldAmt18";

	/** Set Field Amt 18	  */
	public void setFieldAmt18 (BigDecimal FieldAmt18);

	/** Get Field Amt 18	  */
	public BigDecimal getFieldAmt18();

    /** Column name FieldAmt19 */
    public static final String COLUMNNAME_FieldAmt19 = "FieldAmt19";

	/** Set Field Amt 19	  */
	public void setFieldAmt19 (BigDecimal FieldAmt19);

	/** Get Field Amt 19	  */
	public BigDecimal getFieldAmt19();

    /** Column name FieldAmt2 */
    public static final String COLUMNNAME_FieldAmt2 = "FieldAmt2";

	/** Set Field Amt 2	  */
	public void setFieldAmt2 (BigDecimal FieldAmt2);

	/** Get Field Amt 2	  */
	public BigDecimal getFieldAmt2();

    /** Column name FieldAmt20 */
    public static final String COLUMNNAME_FieldAmt20 = "FieldAmt20";

	/** Set Field Amt 20	  */
	public void setFieldAmt20 (BigDecimal FieldAmt20);

	/** Get Field Amt 20	  */
	public BigDecimal getFieldAmt20();

    /** Column name FieldAmt21 */
    public static final String COLUMNNAME_FieldAmt21 = "FieldAmt21";

	/** Set Field Amt 21	  */
	public void setFieldAmt21 (BigDecimal FieldAmt21);

	/** Get Field Amt 21	  */
	public BigDecimal getFieldAmt21();

    /** Column name FieldAmt22 */
    public static final String COLUMNNAME_FieldAmt22 = "FieldAmt22";

	/** Set Field Amt 22	  */
	public void setFieldAmt22 (BigDecimal FieldAmt22);

	/** Get Field Amt 22	  */
	public BigDecimal getFieldAmt22();

    /** Column name FieldAmt23 */
    public static final String COLUMNNAME_FieldAmt23 = "FieldAmt23";

	/** Set Field Amt 23	  */
	public void setFieldAmt23 (BigDecimal FieldAmt23);

	/** Get Field Amt 23	  */
	public BigDecimal getFieldAmt23();

    /** Column name FieldAmt24 */
    public static final String COLUMNNAME_FieldAmt24 = "FieldAmt24";

	/** Set Field Amt 24	  */
	public void setFieldAmt24 (BigDecimal FieldAmt24);

	/** Get Field Amt 24	  */
	public BigDecimal getFieldAmt24();

    /** Column name FieldAmt25 */
    public static final String COLUMNNAME_FieldAmt25 = "FieldAmt25";

	/** Set Field Amt 25	  */
	public void setFieldAmt25 (BigDecimal FieldAmt25);

	/** Get Field Amt 25	  */
	public BigDecimal getFieldAmt25();

    /** Column name FieldAmt26 */
    public static final String COLUMNNAME_FieldAmt26 = "FieldAmt26";

	/** Set Field Amt 26	  */
	public void setFieldAmt26 (BigDecimal FieldAmt26);

	/** Get Field Amt 26	  */
	public BigDecimal getFieldAmt26();

    /** Column name FieldAmt27 */
    public static final String COLUMNNAME_FieldAmt27 = "FieldAmt27";

	/** Set Field Amt 27	  */
	public void setFieldAmt27 (BigDecimal FieldAmt27);

	/** Get Field Amt 27	  */
	public BigDecimal getFieldAmt27();

    /** Column name FieldAmt28 */
    public static final String COLUMNNAME_FieldAmt28 = "FieldAmt28";

	/** Set Field Amt 28	  */
	public void setFieldAmt28 (BigDecimal FieldAmt28);

	/** Get Field Amt 28	  */
	public BigDecimal getFieldAmt28();

    /** Column name FieldAmt29 */
    public static final String COLUMNNAME_FieldAmt29 = "FieldAmt29";

	/** Set Field Amt 29	  */
	public void setFieldAmt29 (BigDecimal FieldAmt29);

	/** Get Field Amt 29	  */
	public BigDecimal getFieldAmt29();

    /** Column name FieldAmt3 */
    public static final String COLUMNNAME_FieldAmt3 = "FieldAmt3";

	/** Set Field Amt 3	  */
	public void setFieldAmt3 (BigDecimal FieldAmt3);

	/** Get Field Amt 3	  */
	public BigDecimal getFieldAmt3();

    /** Column name FieldAmt30 */
    public static final String COLUMNNAME_FieldAmt30 = "FieldAmt30";

	/** Set Field Amt 30	  */
	public void setFieldAmt30 (BigDecimal FieldAmt30);

	/** Get Field Amt 30	  */
	public BigDecimal getFieldAmt30();

    /** Column name FieldAmt31 */
    public static final String COLUMNNAME_FieldAmt31 = "FieldAmt31";

	/** Set Field Amt 31	  */
	public void setFieldAmt31 (BigDecimal FieldAmt31);

	/** Get Field Amt 31	  */
	public BigDecimal getFieldAmt31();

    /** Column name FieldAmt32 */
    public static final String COLUMNNAME_FieldAmt32 = "FieldAmt32";

	/** Set Field Amt 32	  */
	public void setFieldAmt32 (BigDecimal FieldAmt32);

	/** Get Field Amt 32	  */
	public BigDecimal getFieldAmt32();

    /** Column name FieldAmt33 */
    public static final String COLUMNNAME_FieldAmt33 = "FieldAmt33";

	/** Set Field Amt 33	  */
	public void setFieldAmt33 (BigDecimal FieldAmt33);

	/** Get Field Amt 33	  */
	public BigDecimal getFieldAmt33();

    /** Column name FieldAmt34 */
    public static final String COLUMNNAME_FieldAmt34 = "FieldAmt34";

	/** Set Field Amt 34	  */
	public void setFieldAmt34 (BigDecimal FieldAmt34);

	/** Get Field Amt 34	  */
	public BigDecimal getFieldAmt34();

    /** Column name FieldAmt35 */
    public static final String COLUMNNAME_FieldAmt35 = "FieldAmt35";

	/** Set Field Amt 35	  */
	public void setFieldAmt35 (BigDecimal FieldAmt35);

	/** Get Field Amt 35	  */
	public BigDecimal getFieldAmt35();

    /** Column name FieldAmt36 */
    public static final String COLUMNNAME_FieldAmt36 = "FieldAmt36";

	/** Set Field Amt 36	  */
	public void setFieldAmt36 (BigDecimal FieldAmt36);

	/** Get Field Amt 36	  */
	public BigDecimal getFieldAmt36();

    /** Column name FieldAmt37 */
    public static final String COLUMNNAME_FieldAmt37 = "FieldAmt37";

	/** Set Field Amt 37	  */
	public void setFieldAmt37 (BigDecimal FieldAmt37);

	/** Get Field Amt 37	  */
	public BigDecimal getFieldAmt37();

    /** Column name FieldAmt38 */
    public static final String COLUMNNAME_FieldAmt38 = "FieldAmt38";

	/** Set Field Amt 38	  */
	public void setFieldAmt38 (BigDecimal FieldAmt38);

	/** Get Field Amt 38	  */
	public BigDecimal getFieldAmt38();

    /** Column name FieldAmt39 */
    public static final String COLUMNNAME_FieldAmt39 = "FieldAmt39";

	/** Set Field Amt 39	  */
	public void setFieldAmt39 (BigDecimal FieldAmt39);

	/** Get Field Amt 39	  */
	public BigDecimal getFieldAmt39();

    /** Column name FieldAmt4 */
    public static final String COLUMNNAME_FieldAmt4 = "FieldAmt4";

	/** Set Field Amt 4	  */
	public void setFieldAmt4 (BigDecimal FieldAmt4);

	/** Get Field Amt 4	  */
	public BigDecimal getFieldAmt4();

    /** Column name FieldAmt40 */
    public static final String COLUMNNAME_FieldAmt40 = "FieldAmt40";

	/** Set Field Amt 40	  */
	public void setFieldAmt40 (BigDecimal FieldAmt40);

	/** Get Field Amt 40	  */
	public BigDecimal getFieldAmt40();

    /** Column name FieldAmt41 */
    public static final String COLUMNNAME_FieldAmt41 = "FieldAmt41";

	/** Set Field Amt 41	  */
	public void setFieldAmt41 (BigDecimal FieldAmt41);

	/** Get Field Amt 41	  */
	public BigDecimal getFieldAmt41();

    /** Column name FieldAmt42 */
    public static final String COLUMNNAME_FieldAmt42 = "FieldAmt42";

	/** Set Field Amt 42	  */
	public void setFieldAmt42 (BigDecimal FieldAmt42);

	/** Get Field Amt 42	  */
	public BigDecimal getFieldAmt42();

    /** Column name FieldAmt43 */
    public static final String COLUMNNAME_FieldAmt43 = "FieldAmt43";

	/** Set Field Amt 43	  */
	public void setFieldAmt43 (BigDecimal FieldAmt43);

	/** Get Field Amt 43	  */
	public BigDecimal getFieldAmt43();

    /** Column name FieldAmt44 */
    public static final String COLUMNNAME_FieldAmt44 = "FieldAmt44";

	/** Set Field Amt 44	  */
	public void setFieldAmt44 (BigDecimal FieldAmt44);

	/** Get Field Amt 44	  */
	public BigDecimal getFieldAmt44();

    /** Column name FieldAmt45 */
    public static final String COLUMNNAME_FieldAmt45 = "FieldAmt45";

	/** Set Field Amt 45	  */
	public void setFieldAmt45 (BigDecimal FieldAmt45);

	/** Get Field Amt 45	  */
	public BigDecimal getFieldAmt45();

    /** Column name FieldAmt46 */
    public static final String COLUMNNAME_FieldAmt46 = "FieldAmt46";

	/** Set Field Amt 46	  */
	public void setFieldAmt46 (BigDecimal FieldAmt46);

	/** Get Field Amt 46	  */
	public BigDecimal getFieldAmt46();

    /** Column name FieldAmt47 */
    public static final String COLUMNNAME_FieldAmt47 = "FieldAmt47";

	/** Set Field Amt 47	  */
	public void setFieldAmt47 (BigDecimal FieldAmt47);

	/** Get Field Amt 47	  */
	public BigDecimal getFieldAmt47();

    /** Column name FieldAmt48 */
    public static final String COLUMNNAME_FieldAmt48 = "FieldAmt48";

	/** Set Field Amt 48	  */
	public void setFieldAmt48 (BigDecimal FieldAmt48);

	/** Get Field Amt 48	  */
	public BigDecimal getFieldAmt48();

    /** Column name FieldAmt49 */
    public static final String COLUMNNAME_FieldAmt49 = "FieldAmt49";

	/** Set Field Amt 49	  */
	public void setFieldAmt49 (BigDecimal FieldAmt49);

	/** Get Field Amt 49	  */
	public BigDecimal getFieldAmt49();

    /** Column name FieldAmt5 */
    public static final String COLUMNNAME_FieldAmt5 = "FieldAmt5";

	/** Set Field Amt 5	  */
	public void setFieldAmt5 (BigDecimal FieldAmt5);

	/** Get Field Amt 5	  */
	public BigDecimal getFieldAmt5();

    /** Column name FieldAmt50 */
    public static final String COLUMNNAME_FieldAmt50 = "FieldAmt50";

	/** Set Field Amt 50	  */
	public void setFieldAmt50 (BigDecimal FieldAmt50);

	/** Get Field Amt 50	  */
	public BigDecimal getFieldAmt50();

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

    /** Column name LCO_DIAN_Concept_ID */
    public static final String COLUMNNAME_LCO_DIAN_Concept_ID = "LCO_DIAN_Concept_ID";

	/** Set DIAN Concept	  */
	public void setLCO_DIAN_Concept_ID (int LCO_DIAN_Concept_ID);

	/** Get DIAN Concept	  */
	public int getLCO_DIAN_Concept_ID();

	public org.globalqss.model.I_LCO_DIAN_Concept getLCO_DIAN_Concept() throws RuntimeException;

    /** Column name LCO_DIAN_SendSchedule_ID */
    public static final String COLUMNNAME_LCO_DIAN_SendSchedule_ID = "LCO_DIAN_SendSchedule_ID";

	/** Set DIAN Send Schedule	  */
	public void setLCO_DIAN_SendSchedule_ID (int LCO_DIAN_SendSchedule_ID);

	/** Get DIAN Send Schedule	  */
	public int getLCO_DIAN_SendSchedule_ID();

	public org.globalqss.model.I_LCO_DIAN_SendSchedule getLCO_DIAN_SendSchedule() throws RuntimeException;

    /** Column name LCO_DIAN_SendScheduleLine_ID */
    public static final String COLUMNNAME_LCO_DIAN_SendScheduleLine_ID = "LCO_DIAN_SendScheduleLine_ID";

	/** Set DIAN Send Schedule Line	  */
	public void setLCO_DIAN_SendScheduleLine_ID (int LCO_DIAN_SendScheduleLine_ID);

	/** Get DIAN Send Schedule Line	  */
	public int getLCO_DIAN_SendScheduleLine_ID();

    /** Column name LCO_DIAN_SendScheduleLine_UU */
    public static final String COLUMNNAME_LCO_DIAN_SendScheduleLine_UU = "LCO_DIAN_SendScheduleLine_UU";

	/** Set LCO_DIAN_SendScheduleLine_UU	  */
	public void setLCO_DIAN_SendScheduleLine_UU (String LCO_DIAN_SendScheduleLine_UU);

	/** Get LCO_DIAN_SendScheduleLine_UU	  */
	public String getLCO_DIAN_SendScheduleLine_UU();

    /** Column name LCO_DIAN_XML_Header_ID */
    public static final String COLUMNNAME_LCO_DIAN_XML_Header_ID = "LCO_DIAN_XML_Header_ID";

	/** Set DIAN XML Header	  */
	public void setLCO_DIAN_XML_Header_ID (int LCO_DIAN_XML_Header_ID);

	/** Get DIAN XML Header	  */
	public int getLCO_DIAN_XML_Header_ID();

	public org.globalqss.model.I_LCO_DIAN_XML_Header getLCO_DIAN_XML_Header() throws RuntimeException;

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
