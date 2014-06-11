package org.globalqss.util;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAttachment;
import org.compiere.model.MAttachmentEntry;
import org.compiere.model.MTable;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.globalqss.model.X_SRI_Authorisation;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;


/**
 *	Utils for Localization LCO
 *
 *  @author Jesus Garcia - globalqss - Quality Systems & Solutions - http://globalqss.com
 *	@version $Id: LCO_Utils.java,v 1.0 2008/05/26 23:01:26 cruiz Exp $
 */

public class LEC_FE_Utils
{


	/**
	 *	Calculate SRI Digit based on AccesCode 48
	 *  Metodo Modulo 11 con factor de chequeo ponderado (2)
	 */
	public static int calculateDigitSri(String strAccessCode) {

		String strSriFactor = "765432";	// SRI Hardcoded
		
		if (strAccessCode.length() != 48)
			throw new AdempiereException(Msg.getMsg(Env.getCtx(), "LCO_WrongLength"));
		
		// Vector de cadena validacion Sismode
		Integer iNros[];
		iNros = new Integer[48];
	
		// Ciclo para asignar cada uno de los digitos del vector
		// La cadena de 6 se repite 8 veces
		try {
			for (int i = 0; i < 8 ; i++) {
				for (int j = 0; j < 6 ; j++) {
					iNros[(i * 6) + j] = Integer.parseInt(strSriFactor.substring(j, j + 1));
				}
			}
		} catch (NumberFormatException e) {
			throw new AdempiereException(Msg.getMsg(Env.getCtx(), "LCO_NotANumber"));
		}
	
		return modulo (strAccessCode, iNros, 11);
	
	}	// calculateDigitSri

	/**
	 * Implementa algoritmos modulo 10 y 11 con coeficientes
	 * @param valida - cadena de numeros a generar el digito de verificacion
	 * @param coeficientes - arreglo de enteros usados como coeficientes
	 * @param mod - modulo (LEC usa 10 u 11)
	 * @return
	 */
	public static int modulo(String valida, Integer[] coeficientes, int mod) {
		if (valida.length() > coeficientes.length) {
			throw new AdempiereException(Msg.getMsg(Env.getCtx(), "LCO_WrongLength"));
		}
		int iOperacion = 0;

		//Ciclo para multiplicar cada uno de los digitos con el vector
		for (int i = 0; i < valida.trim().length() ; i++) {
			try {
				int sumando = Integer.parseInt(valida.substring(i, i + 1)) * coeficientes[i];
				if (mod == 10 && sumando > 9) // para el algoritmo modulo 10 (Luhn algorithm) se utiliza el residuo de 9
					sumando -= 9;
				iOperacion = iOperacion + sumando;
			} catch (NumberFormatException e) {
				throw new AdempiereException(Msg.getMsg(Env.getCtx(), "LCO_NotANumber"));
			}
		}

		// Obtener el residuo de la operacion
		iOperacion %= mod;
		iOperacion = mod - iOperacion;
		if (iOperacion >= 10) {
			iOperacion = mod - iOperacion;
		}

		return iOperacion;
	}

	/**
	 * Get Doc or Current date in format - default yyyy/MM/dd
	 * @return Date
	 */
	public static String getDate(Date docDate, int format) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		
		if (docDate == null) docDate = new Date();
		
		if (format == 8)
			dateFormat = new SimpleDateFormat("ddMMyyyy");
		if (format == 9)
			dateFormat = new SimpleDateFormat("yyyyMMdd");
		else if (format == 10)
			dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		else if (format == 19)
			dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		return dateFormat.format(docDate);
	}
	
	/**
	 * 	String fillString count with char
	 * 	@return string
	 */
	public static String fillString(int count, char fill) {
	    if (count < 0)
	    	return "";
		char[] s = new char[count];
	    Arrays.fill(s, fill);
	    return new String(s);
	}

	/**
	 * 	String cutString lenght
	 * 	@return string
	 */
	public static String cutString(String str, int length) {
		if (length < str.length())
			return new String(str.substring(0, length).trim());

		return new String(str.trim());
	}
	
	/**
	 * 	String getSecuencial from DocumentNo
	 * 	@return string
	 */
	public static String getSecuencial(String docno, String shortdoctype) {
		if (shortdoctype.equals("01"))	// FACTURA
			return new String(docno.substring(docno.lastIndexOf('-') + 1));
		else if (shortdoctype.equals("04"))	// NOTA DE CRÉDITO
			return new String(docno.substring(docno.lastIndexOf('-') + 1));
		else if (shortdoctype.equals("05"))	// NOTA DE DÉBITO
			return new String(docno.substring(docno.lastIndexOf('-') + 1));
		else if (shortdoctype.equals("06"))	// GUÍA DE REMISIÓN
			return new String(docno.substring(docno.lastIndexOf('-') + 5));
		else if (shortdoctype.equals("07"))	// COMPROBANTE DE RETENCIÓN
			return new String(docno.substring(docno.lastIndexOf('-') + 1));

		return new String(docno.trim());
	}

	/**
	 * 	String getTipoIdentificacionSri segun TaxCodeSRI
	 * 	@return string
	 */
	public static String getTipoIdentificacionSri(String taxcodesri) {
	
		String tipoidentificacionsri = "";
		
		if (taxcodesri.equals("1"))
			tipoidentificacionsri = "04"; 	// VENTA CON RUC
		else if (taxcodesri.equals("2"))
			tipoidentificacionsri = "05"; 	// VENTA CON CEDULA
		else if (taxcodesri.equals("3"))
			tipoidentificacionsri = "06";	// VENTA CON PASAPORTE
		else tipoidentificacionsri = "07"; 	// VENTA A CONSUMIDOR FINAL	// TODO Deprecated
		
		return tipoidentificacionsri;
	
	}
	
	/**
	 * 	String getLecSriFormat
	 * 	@return int
	 */
	public static int getLecSriFormat(int ad_client_id, String tipoemision, String coddoc, Date startdate, Date enddate) {
	
		int lec_sri_format_id = DB.getSQLValue(null, "SELECT MAX(LEC_SRI_Format_ID) FROM LEC_SRI_Format WHERE AD_Client_ID = ? AND IsActive = 'Y' AND SRI_DeliveredType = ? AND SRI_ShortDocType = ? AND ? >= ValidFrom AND ( ? <= ValidTo OR ValidTo IS NULL)", ad_client_id, tipoemision, coddoc, startdate, enddate);
		
		return lec_sri_format_id;

	}
	
	/**
	 * 	String getOrgBPartner
	 * 	@return int
	 */
	public static int getOrgBPartner(int ad_client_id, String taxid) {
	
		int c_bpartner_id = DB.getSQLValue(null, "SELECT C_BPartner_ID FROM C_BPartner WHERE AD_Client_ID = ? AND TaxId = ? ", ad_client_id, taxid);
		
		return c_bpartner_id;

	}
	
	/**
	 * 	String getAccessCode
	 * 	@return String
	 */
	public static String getAccessCode(Date docdate, String coddoc, String taxid, String tipoambiente, String orgcode, String storecode, String documentno, String documentcode, String tipoemision) {
	
		String accesscode;
				
		try {
		accesscode = ""
			+ String.format("%8s", getDate(docdate,8))		// fechaEmision
			+ String.format("%2s", coddoc)								// codDoc
			+ String.format("%13s", (fillString(13 - (cutString(taxid, 13)).length(), '0'))
				+ cutString(taxid,13))						// ruc
			+ String.format("%1s", tipoambiente)						// ambiente
			+ String.format("%3s", orgcode)								// serie / estab
			+ String.format("%3s", storecode)							// serie / ptoEmi
			+ String.format("%9s", (fillString(9 - (cutString(getSecuencial(documentno, coddoc), 9)).length(), '0'))
				+ cutString(getSecuencial(documentno, coddoc), 9))	// numero / secuencial
			+ String.format("%8s", documentcode)						// codigo
			+ String.format("%1s", tipoemision);						// tipoEmision

			accesscode = accesscode
				+ String.format("%1s", calculateDigitSri(accesscode));	// digito
		
		} catch (Exception e) {
			throw new AdempiereException(Msg.getMsg(Env.getCtx(), "LCO_NotANumber"));
		}
			
		return accesscode;
		
	}
		
	/**
	 * 	String atachXmlFile
	 * 	@return void
	 */
	public static void attachXmlFile(Properties ctx, String trxName, int sri_authorisation_id, String file_name) {
		
		LEC_FE_UtilsXml signature = new LEC_FE_UtilsXml();
		
		try {
		int  AD_Table_ID = MTable.getTable_ID(X_SRI_Authorisation.Table_Name);
		//if one attach is found , it means that a xml file was attached before
		MAttachment attach =  MAttachment.get(ctx, AD_Table_ID, sri_authorisation_id);
		//no se encontro archivo previo
		if (attach == null ) {
			attach = new  MAttachment(ctx, AD_Table_ID , sri_authorisation_id, trxName);
			attach.addEntry(new File (file_name));
			attach.saveEx();

		} else {
			// se encontro un archivo adjunto previamente
			//toma el index  del penultimo archivo y lo renombra
			//REVIEWME
			int index = (attach.getEntryCount()-1);
			MAttachmentEntry entry = attach.getEntry(index) ;
			String renamed = signature.getFolderRaiz()+File.separator+entry.getName().substring(0,entry.getName().length()-4 )+"_old_"+ LEC_FE_Utils.getDate(null, 19) + ".xml";
			entry.setName(renamed);
			attach.saveEx();
			//agrega el nuevo archivo ya q el anterior ha sido renombrado
			attach.addEntry(new File (file_name));
			attach.saveEx();
		}
		//DB.getSQLValue(get_TrxName(),"SELECT AD_Attachment_ID FROM AD_Attachment WHERE AD_Table_ID=? AND Record_ID=?",AD_Table_ID)
	
		// MAttachment
		/*
		  Si IsAttachXML
	      Anexar el archivo XML al XML Header (probar con Archivador
		  a ver si funciona, si no con Attachment)
	      NOTA: ¿Que hacer si ya hay un archivo previo generado?
		  Verificar si el archivador es read-only, y si podria renombrar
		  un archivo previo generado para añadirle un sufijo _old_yyyymmdd
		 */
		
		} catch (Exception e) {
			throw new AdempiereException(Msg.getMsg(Env.getCtx(), "AttachmentNull"));
		}
	}
	
	
}	// LEC_FE_Utils
