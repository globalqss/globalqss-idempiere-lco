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
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for LCO_FE_ContactType
 *  @author iDempiere (generated) 
 *  @version Release 7.1 - $Id$ */
public class X_LCO_FE_ContactType extends PO implements I_LCO_FE_ContactType, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200204L;

    /** Standard Constructor */
    public X_LCO_FE_ContactType (Properties ctx, int LCO_FE_ContactType_ID, String trxName)
    {
      super (ctx, LCO_FE_ContactType_ID, trxName);
      /** if (LCO_FE_ContactType_ID == 0)
        {
			setDianContactCode (null);
// 1
			setLCO_FE_ContactType_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_LCO_FE_ContactType (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_LCO_FE_ContactType[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** 1-Persona de contacto = 1 */
	public static final String DIANCONTACTCODE_1_PersonaDeContacto = "1";
	/** 2-Contacto de despacho o entrega = 2 */
	public static final String DIANCONTACTCODE_2_ContactoDeDespachoOEntrega = "2";
	/** 3-Contacto de contabilidad = 3 */
	public static final String DIANCONTACTCODE_3_ContactoDeContabilidad = "3";
	/** 4-Contacto de ventas o compras = 4 */
	public static final String DIANCONTACTCODE_4_ContactoDeVentasOCompras = "4";
	/** 5-Departamento responsable del procesamiento del pedido = 5 */
	public static final String DIANCONTACTCODE_5_DepartamentoResponsableDelProcesamientoDelPedido = "5";
	/** Set Contact Code DIAN.
		@param DianContactCode Contact Code DIAN	  */
	public void setDianContactCode (String DianContactCode)
	{

		set_Value (COLUMNNAME_DianContactCode, DianContactCode);
	}

	/** Get Contact Code DIAN.
		@return Contact Code DIAN	  */
	public String getDianContactCode () 
	{
		return (String)get_Value(COLUMNNAME_DianContactCode);
	}

	/** Set Contact Type EI.
		@param LCO_FE_ContactType_ID Contact Type EI	  */
	public void setLCO_FE_ContactType_ID (int LCO_FE_ContactType_ID)
	{
		if (LCO_FE_ContactType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_ContactType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_LCO_FE_ContactType_ID, Integer.valueOf(LCO_FE_ContactType_ID));
	}

	/** Get Contact Type EI.
		@return Contact Type EI	  */
	public int getLCO_FE_ContactType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LCO_FE_ContactType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set LCO_FE_ContactType_UU.
		@param LCO_FE_ContactType_UU LCO_FE_ContactType_UU	  */
	public void setLCO_FE_ContactType_UU (String LCO_FE_ContactType_UU)
	{
		set_ValueNoCheck (COLUMNNAME_LCO_FE_ContactType_UU, LCO_FE_ContactType_UU);
	}

	/** Get LCO_FE_ContactType_UU.
		@return LCO_FE_ContactType_UU	  */
	public String getLCO_FE_ContactType_UU () 
	{
		return (String)get_Value(COLUMNNAME_LCO_FE_ContactType_UU);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}