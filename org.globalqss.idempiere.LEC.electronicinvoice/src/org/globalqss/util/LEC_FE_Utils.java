package org.globalqss.util;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


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
	
	
}	// LEC_FE_Utils
