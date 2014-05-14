package org.globalqss.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	public static int calculateDigitSri(String strAccesCode) {

		//Vector de cadena validacion Sismode	// Sysconfig ?
		int iNros[] = {
				3, 2, 7, 6, 5, 4, 3, 2,
				3, 2, 7, 6, 5, 4, 3, 2,
				3, 2, 7, 6, 5, 4, 3, 2,
				3, 2, 7, 6, 5, 4, 3, 2,
				3, 2, 7, 6, 5, 4, 3, 2,
				3, 2, 7, 6, 5, 4, 3, 2
				};
		//Variable para realizar las operaciones
		int iOperacion = 0;
		int posini = 0;
	
		//Ciclo para multiplicar cada uno de los digitos del Codigo con el vector
		for (int i = 0; i < strAccesCode.trim().length() ; i++) {
			posini = strAccesCode.trim().length() - (i + 1);
			try {
				iOperacion = iOperacion + Integer.parseInt(strAccesCode.substring(posini, posini + 1)) * iNros[i];
			} catch (NumberFormatException e) {
				return -1;
			}
		}
	
		//Obtener el residuo de la operacion
		iOperacion %= 11;
		
        if (iOperacion == 0 || iOperacion == 1)	{
		    return iOperacion;
		}
		else {
		    return 11 - iOperacion;
		}
	
	}	// calculateDigitSri
	
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
	
	
}	// LEC_FE_Utils
