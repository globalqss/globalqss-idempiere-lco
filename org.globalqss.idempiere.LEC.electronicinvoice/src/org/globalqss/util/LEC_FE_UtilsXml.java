package org.globalqss.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;

import org.apache.commons.io.FileUtils;
import org.globalqss.model.GenericXMLSignature;
import org.globalqss.model.X_SRI_AccessCode;
import org.globalqss.model.X_SRI_Authorisation;

import ec.gob.sri.comprobantes.ws.Comprobante;
// import ec.gob.sri.comprobantes.ws.Comprobante.Mensajes;	// Collides
// import ec.gob.sri.comprobantes.ws.Mensaje;	// Collides
import ec.gob.sri.comprobantes.ws.RecepcionComprobantes;
import ec.gob.sri.comprobantes.ws.RecepcionComprobantesService;
import ec.gob.sri.comprobantes.ws.RespuestaSolicitud;
import ec.gob.sri.comprobantes.ws.RespuestaSolicitud.Comprobantes;
import ec.gob.sri.comprobantes.ws.aut.Autorizacion;
import ec.gob.sri.comprobantes.ws.aut.AutorizacionComprobantes;
import ec.gob.sri.comprobantes.ws.aut.AutorizacionComprobantesService;
import ec.gob.sri.comprobantes.ws.aut.Mensaje;
import ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante;
import ec.gob.sri.comprobantes.ws.aut.Autorizacion.Mensajes;
import ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante.Autorizaciones;

import org.w3c.dom.Document;

import es.mityc.firmaJava.libreria.ConstantesXADES;
import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
import es.mityc.javasign.EnumFormatoFirma;
import es.mityc.javasign.xml.refs.AllXMLToSign;
import es.mityc.javasign.xml.refs.InternObjectToSign;
import es.mityc.javasign.xml.refs.ObjectToSign;


/**
 *	Utils for LEC FE Xml
 *
 *  @author Jesus Garcia - globalqss - Quality Systems & Solutions - http://globalqss.com
 *	@version $Id: LEC_FE_UtilsXml.java,v 1.0 2013/05/27 23:01:26 cruiz Exp $
 */

public class LEC_FE_UtilsXml extends GenericXMLSignature
{
	
	public void main(String[] args) {
    
		LEC_FE_UtilsXml signature = new LEC_FE_UtilsXml();
        signature.execute();
        
    }
	
    @Override
    public DataToSign createDataToSign() {
        DataToSign dataToSign = new DataToSign();
        dataToSign.setXadesFormat(EnumFormatoFirma.XAdES_BES);
        dataToSign.setEsquema(XAdESSchemas.XAdES_132);
        dataToSign.setXMLEncoding(XmlEncoding);
        dataToSign.setEnveloped(true);
        dataToSign.addObject(new ObjectToSign(new InternObjectToSign("comprobante"), "contenido comprobante", null, "text/xml", null));
        dataToSign.setParentSignNode("comprobante");
        Document docToSign = getDocument(getResource_To_Sign());
        dataToSign.setDocument(docToSign);
        dataToSign.addObject(new ObjectToSign(new AllXMLToSign(), "Contenido comprobante", null, "text/xml", null));
        return dataToSign;
    }
    
    public static String respuestaRecepcionComprobante(LEC_FE_UtilsXml signature, String file_name) {
        
    	String msg = null;
    	
    	try	{
    		
    	//log.warning("@Verificando Conexion servicio recepcion SRI@" + (signature.isOnTesting ? "PRUEBAS " : "PRODUCCION"));
    	System.out.println("@Verificando Conexion servicio recepcion SRI@" + (signature.isOnTesting ? "PRUEBAS " : "PRODUCCION"));
    	if (! signature.existeConexion(signature.recepcionComprobantesService)) {
        	msg = "Error no hay conexion al servicio recepcion SRI: " + (signature.isOnTesting ? "PRUEBAS " : "PRODUCCION");
        	return msg;	// throw new AdempiereException(msg);
		}
        	
        //log.warning("@Sending Xml@ -> " + file_name);
        System.out.println("@Sending Xml@ -> " + file_name);
        // Enviar a Recepcion Comprobante SRI
        // TODO 43 Clave acceso registrada
        byte[] bytes = signature.getBytesFromFile(file_name);
        ///*
        RespuestaSolicitud respuestasolicitud = signature.validarComprobante(bytes);
        msg = respuestasolicitud.getEstado();
        //log.warning("@Recepcion SRI@ -> " + msg);
        System.out.println("@Recepcion SRI@ -> " + msg);
        Comprobantes comprobantes = respuestasolicitud.getComprobantes();
        for (Comprobante comprobante : comprobantes.getComprobante()) {
        	Comprobante.Mensajes mensajes = comprobante.getMensajes();
        	for (ec.gob.sri.comprobantes.ws.Mensaje mensaje : mensajes.getMensaje()) {
        		if (mensaje.getIdentificador().equals("60"))	// Ambiente ejecución
	    			// Ignore Advertencia Certificacion 
	    			continue;
        		msg = mensaje.getIdentificador() + ConstantesXADES.GUION + mensaje.getMensaje() + ConstantesXADES.GUION + mensaje.getInformacionAdicional();
	    		// log.warning("@Mensaje Xml@ -> " + msg);
	    		System.out.println("@Mensaje Xml@ -> " + msg);
        	}
        	// TODO Extraer y guardar comprobante xml en file_name
        	file_name = LEC_FE_Utils.getFilename(signature, signature.folderComprobantesTransmitidos);
        	FileUtils.writeStringToFile(new File(file_name), comprobante.toString());
	    }
        if (! respuestasolicitud.getEstado().equals(signature.recepcionRecibida)) {
        	msg = respuestasolicitud.getEstado() + ConstantesXADES.GUION + comprobantes.getComprobante().toString() + ConstantesXADES.GUION + msg;
        	return msg;	// throw new AdempiereException(msg);
		}
	    //*/
        
    	}
    	catch (Exception e)
		{
			return e.getMessage();
		}
    	
    	return null;
	}
    
    public static String respuestaAutorizacionComprobante(LEC_FE_UtilsXml signature, X_SRI_AccessCode ac, X_SRI_Authorisation a, String accesscode) {
    	
    	Boolean isAutorizacion = false;
    	String msg = null;
    	String file_name = null;
    	
    	try	{
    		
    	//log.warning("@Verificando Conexion servicio autorizacion SRI@" + (signature.isOnTesting ? "PRUEBAS " : "PRODUCCION"));
        System.out.println("@Verificando Conexion servicio autorizacion SRI@" + (signature.isOnTesting ? "PRUEBAS " : "PRODUCCION"));
        if (! signature.existeConexion(signature.autorizacionComprobantesService)) {
        	msg = "Error no hay conexion al servicio autorizacion SRI: " + (signature.isOnTesting ? "PRUEBAS " : "PRODUCCION");
			return msg;	// throw new AdempiereException(msg);
		}
    	
        RespuestaComprobante respuestacomprobante = signature.autorizacionComprobante(accesscode);
	    Autorizaciones autorizaciones = respuestacomprobante.getAutorizaciones();
	    // Procesar Respuesta Autorizacion SRI
	    for (Autorizacion autorizacion : autorizaciones.getAutorizacion()) {
	    	isAutorizacion = true;
	        // msg = autorizaciones.getAutorizacion().get(0).getEstado();
	    	msg = autorizacion.getEstado() + ConstantesXADES.GUION + autorizacion.getNumeroAutorizacion() + ConstantesXADES.GUION + autorizacion.getFechaAutorizacion().toString();
	    	// log.warning("@Autorizacion Xml@ -> " + msg);
	    	System.out.println("@Autorizacion Xml@ -> " + msg);
	        //
	    	Mensajes mensajes = autorizacion.getMensajes();
	    	for (Mensaje mensaje : mensajes.getMensaje()) {
	    		if (mensaje.getIdentificador().equals("60"))	// Ambiente ejecución
	    			// Ignore Advertencia Certificacion 
	    			continue;
	    		msg = autorizacion.getEstado() + ConstantesXADES.GUION + mensaje.getIdentificador() + ConstantesXADES.GUION + mensaje.getMensaje() + ConstantesXADES.GUION + mensaje.getInformacionAdicional();
	    		// log.warning("@Mensaje Xml@ -> " + msg);
	    		System.out.println("@Mensaje Xml@ -> " + msg);
	    		a.setSRI_ErrorCode_ID(Integer.parseInt(mensaje.getIdentificador()));
	    	}
	    	//
	    	if (autorizacion.getEstado().equals(signature.comprobanteAutorizado)) {
	    		
	    		msg = null;
	    		// TODO Log.warn(message, ex)
	    		// Update AccessCode
	    		if (ac.getCodeAccessType().equals("1")) {	// 1-Automatica
	    			ac.setValue(autorizacion.getNumeroAutorizacion());
	    			ac.saveEx();
	    		}	
	    		// Update Authorisation
	    		a.setSRI_AuthorisationCode(autorizacion.getNumeroAutorizacion());
	    		a.setSRI_DateAuthorisation(autorizacion.getFechaAutorizacion().toString());
	    		a.setProcessed(true);
	    		a.saveEx();
	    		
	    		file_name = LEC_FE_Utils.getFilename(signature, signature.folderComprobantesAutorizados);
	    	} else if (autorizacion.getEstado().equals(signature.comprobanteNoAutorizado))
	    		file_name = LEC_FE_Utils.getFilename(signature, signature.folderComprobantesNoAutorizados);
	    	else
	    		file_name = LEC_FE_Utils.getFilename(signature, signature.folderComprobantesRechazados);
	    	
	    	// TODO Extraer y guardar autorizacion xml signed and authorised en file_name
	    	// FileUtils.writeStringToFile(new File(file_name), autorizacion.toString());
	    	FileUtils.writeStringToFile(new File(file_name), autorizacion.getComprobante());
	    	
	  		// Atach XML Autorizado
    		if (autorizacion.getEstado().equals(signature.comprobanteAutorizado) && signature.isAttachXml())
    			LEC_FE_Utils.attachXmlFile(a.getCtx(), a.get_TrxName(), a.getSRI_Authorisation_ID(), file_name);
  	    	
	    	break;	// Solo Respuesta autorizacion mas reciente segun accesscode
	    	
	    	}
		
    	}
    	catch (Exception e)
		{
			return e.getMessage();
		}
		
    	if (! isAutorizacion)
    		msg = "@Autorizacion Xml@ -> No hay Respuesta Autorizacion SRI";
    	 
    	return msg;
	}
    
    public RespuestaSolicitud validarComprobante(byte[] xml) {
        
    	// RecepcionComprobantesService service = new RecepcionComprobantesService();
    	RecepcionComprobantesService service = null;
    	
    	String wsdlLocation = getUrlWSRecepcionComprobantes();
        
        try {
            QName qname = new QName(recepcionComprobantesQname, recepcionComprobantesService);
            URL url = new URL(wsdlLocation);
            service = new RecepcionComprobantesService(url, qname);
        } catch (MalformedURLException ex) {
            //return ex;
        } catch (WebServiceException ws) {
            //return ws;
        }
        
       	RecepcionComprobantes port = service.getRecepcionComprobantesPort();
       	// Controlar el tiempo de espera al consumir un webservice
    	// ((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", 5000);
    	// ((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.request.timeout", 5000);
        return port.validarComprobante(xml);
    
    }
    
    public RespuestaComprobante autorizacionComprobante(String claveAccesoComprobante) {
        
    	// AutorizacionComprobantesService service = new AutorizacionComprobantesService();
    	AutorizacionComprobantesService service = null;
    	
    	String wsdlLocation = getUrlWSAutorizacionComprobantes();
        
        try {
            QName qname = new QName(autorizacionComprobantesQname, autorizacionComprobantesService);
            URL url = new URL(wsdlLocation);
            service = new AutorizacionComprobantesService(url, qname);
        } catch (MalformedURLException ex) {
            //return ex;
        } catch (WebServiceException ws) {
            //return ws;
        }
        
    	AutorizacionComprobantes port = service.getAutorizacionComprobantesPort();
    	// Controlar el tiempo de espera al consumir un webservice
    	// ((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", 5000);
    	// ((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.request.timeout", 5000);
    	return port.autorizacionComprobante(claveAccesoComprobante);
    
    }
    
    public boolean existeConexion(String accionComprobantesService) {
        
    	int i = 0;
        boolean respuesta = false;
        
        String url = null;
        if (accionComprobantesService.equals(recepcionComprobantesService))
        	url = getUrlWSRecepcionComprobantes();
        else
	        url = getUrlWSAutorizacionComprobantes();
    
        while (i < 3) {
            Object obj = getWebService(url, accionComprobantesService);
            if (obj  == null) {
                return true;
            }
            if ((obj  instanceof WebServiceException)) {
                respuesta = false;
            }
            i++;
        }
        
        return respuesta;
    }
    
    public Object getWebService(String wsdlLocation, String accionComprobantesService) {
        
    	try {
            
            URL url = new URL(wsdlLocation);
            if (accionComprobantesService.equals(this.recepcionComprobantesService)) {
            	QName qname = new QName(recepcionComprobantesQname, accionComprobantesService);
            	RecepcionComprobantesService service = new RecepcionComprobantesService(url, qname);
            } else {
            	QName qname = new QName(autorizacionComprobantesQname, accionComprobantesService);
            	AutorizacionComprobantesService service = new AutorizacionComprobantesService(url, qname);
            }
            return null;
        } catch (MalformedURLException ex) {
            return ex;
        } catch (WebServiceException ws) {
            return ws;
        }
    
    }
    
    public byte[] getBytesFromFile(String xmlFilePath) throws Exception {
    	
    	byte[] bytes = null;
        File file = new File(xmlFilePath);
        InputStream is;
        is = new FileInputStream(file);
        long length = file.length();
        bytes = new byte[(int) length];
        
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        is.close();
	        
        return bytes;
    }
    
	
}	// LEC_FE_UtilsXml
