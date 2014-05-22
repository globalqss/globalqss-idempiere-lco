package org.globalqss.util;

import org.adempiere.exceptions.AdempiereException;
import org.apache.tomcat.util.log.Log;
import org.compiere.util.DB;
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
	public static int calculateDigitSri(String strAccessCode, String srtDocumentCode) {

		if (strAccessCode.length() != 48)
			throw new AdempiereException(Msg.getMsg(Env.getCtx(), "LCO_WrongLength"));
		//if (Long.parseLong(strAccessCode) < 0)
		//	throw new AdempiereException(Msg.getMsg(Env.getCtx(), "LCO_NotANumber"));
		if (srtDocumentCode.length() != 8)
			throw new AdempiereException(Msg.getMsg(Env.getCtx(), "LCO_WrongLength"));
		if (Long.parseLong(srtDocumentCode) < 0)
			throw new AdempiereException(Msg.getMsg(Env.getCtx(), "LCO_NotANumber"));
		
		//Vector de cadena validacion Sismode	// Sysconfig ?
		Integer iNros[] = {
				//3, 2, 7, 6, 5, 4, 3, 2,
				0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0
				};
		//Variable para realizar las operaciones
	
		//Ciclo para asignar cada uno de los digitos del vector
		try {
			for (int i = 0; i < 6 ; i++) {
				for (int j = 0; j < 8 ; j++) {
					iNros[(i * 8) + j] = Integer.parseInt(srtDocumentCode.substring(j, j + 1));
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
	
	
}	// LEC_FE_Utils
