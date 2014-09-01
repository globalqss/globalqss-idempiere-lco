package org.globalqss.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;

import org.apache.commons.io.FileUtils;
import org.compiere.model.MAttachment;
import org.compiere.model.MAttachmentEntry;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTable;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Env;
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
	
	public LEC_FE_UtilsXml() {
		
		setOnTesting(MSysConfig.getBooleanValue("QSSLEC_FE_EnPruebas", false, Env.getAD_Client_ID(Env.getCtx())));
		
		setEnvType(ambienteProduccion);
		if (isOnTesting())
			setEnvType(ambienteCertificacion);
		
		setDeliveredType(LEC_FE_UtilsXml.emisionNormal);
		setCodeAccessType(LEC_FE_UtilsXml.claveAccesoAutomatica);
		
		// if (IsUseContingency) {
		// 	setDeliveredType(LEC_FE_UtilsXml.emisionContingencia);
		// 	setCodeAccessType(LEC_FE_UtilsXml.claveAccesoContingencia);
		// }
		
		if (! isOnTesting()) {
			setUrlWSRecepcionComprobantes(MSysConfig.getValue("QSSLEC_FE_SRIURLWSProdRecepcionComprobante", null, Env.getAD_Client_ID(Env.getCtx())));
			setUrlWSAutorizacionComprobantes(MSysConfig.getValue("QSSLEC_FE_SRIURLWSProdAutorizacionComprobante", null, Env.getAD_Client_ID(Env.getCtx())));
		} else {
			setUrlWSRecepcionComprobantes(MSysConfig.getValue("QSSLEC_FE_SRIURLWSCertRecepcionComprobante", null, Env.getAD_Client_ID(Env.getCtx())));
			setUrlWSAutorizacionComprobantes(MSysConfig.getValue("QSSLEC_FE_SRIURLWSCertAutorizacionComprobante", null, Env.getAD_Client_ID(Env.getCtx())));
		}
		
		setSriWSTimeout(MSysConfig.getIntValue("QSSLEC_FE_SRIWebServiceTimeout", 0, Env.getAD_Client_ID(Env.getCtx())));
		
		setAttachXml(MSysConfig.getBooleanValue("QSSLEC_FE_DebugEnvioRecepcion", false, Env.getAD_Client_ID(Env.getCtx())));

		// setPKCS12_Resource(MSysConfig.getValue("QSSLEC_FE_RutaCertificadoDigital", null, Env.getAD_Client_ID(Env.getCtx()), getAD_Org_ID()));
		// setPKCS12_Password(MSysConfig.getValue("QSSLEC_FE_ClaveCertificadoDigital", null, Env.getAD_Client_ID(Env.getCtx()), getAD_Org_ID()));

		setFolderRaiz(MSysConfig.getValue("QSSLEC_FE_RutaGeneracionXml", null, Env.getAD_Client_ID(Env.getCtx())));	// Segun SysConfig + Formato
		
		//crea los directorios para los archivos xml
		(new File(getFolderRaiz() + File.separator + LEC_FE_UtilsXml.folderComprobantesGenerados + File.separator)).mkdirs();
		(new File(getFolderRaiz() + File.separator + LEC_FE_UtilsXml.folderComprobantesFirmados + File.separator)).mkdirs();
		(new File(getFolderRaiz() + File.separator + LEC_FE_UtilsXml.folderComprobantesTransmitidos + File.separator)).mkdirs();
		(new File(getFolderRaiz() + File.separator + LEC_FE_UtilsXml.folderComprobantesRechazados + File.separator)).mkdirs();
		(new File(getFolderRaiz() + File.separator + LEC_FE_UtilsXml.folderComprobantesAutorizados + File.separator)).mkdirs();
		(new File(getFolderRaiz() + File.separator + LEC_FE_UtilsXml.folderComprobantesNoAutorizados + File.separator)).mkdirs();

	}
	
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
    
    public  String respuestaRecepcionComprobante(LEC_FE_UtilsXml signaturedummy, String file_name) {
        
    	String msg = null;
    	
    	try	{
    		
    	//log.warning("@Verificando Conexion servicio recepcion SRI@" + (isOnTesting ? "PRUEBAS " : "PRODUCCION"));
    	System.out.println("@Verificando Conexion servicio recepcion SRI@" + (isOnTesting ? "PRUEBAS " : "PRODUCCION"));
    	if (! existeConexion(recepcionComprobantesService)) {
        	msg = "Error no hay conexion al servicio recepcion SRI: " + (isOnTesting ? "PRUEBAS " : "PRODUCCION");
        	return msg;	// throw new AdempiereException(msg);
		}
        	
        //log.warning("@Sending Xml@ -> " + file_name);
        System.out.println("@Sending Xml@ -> " + file_name);
        // Enviar a Recepcion Comprobante SRI
        byte[] bytes = getBytesFromFile(file_name);
        ///*
        RespuestaSolicitud respuestasolicitud = validarComprobante(bytes);
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
        	file_name = getFilename(this, folderComprobantesTransmitidos);
        	FileUtils.writeStringToFile(new File(file_name), comprobante.toString());
	    }
        if (! respuestasolicitud.getEstado().equals(recepcionRecibida)) {
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
    
    public String respuestaAutorizacionComprobante(LEC_FE_UtilsXml signaturedummy, X_SRI_AccessCode ac, X_SRI_Authorisation a, String accesscode) {
    	
    	Boolean isAutorizacion = false;
    	String msg = null;
    	String file_name = null;
    	
    	try	{
    		
    	//log.warning("@Verificando Conexion servicio autorizacion SRI@" + (isOnTesting ? "PRUEBAS " : "PRODUCCION"));
        System.out.println("@Verificando Conexion servicio autorizacion SRI@" + (isOnTesting ? "PRUEBAS " : "PRODUCCION"));
        if (! existeConexion(autorizacionComprobantesService)) {
        	msg = "Error no hay conexion al servicio autorizacion SRI: " + (isOnTesting ? "PRUEBAS " : "PRODUCCION");
			return msg;	// throw new AdempiereException(msg);
		}
    	
        RespuestaComprobante respuestacomprobante = autorizacionComprobante(accesscode);
	    Autorizaciones autorizaciones = respuestacomprobante.getAutorizaciones();
	    // Procesar Respuesta Autorizacion SRI
	    for (Autorizacion autorizacion : autorizaciones.getAutorizacion()) {
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
	    		a.setSRI_ErrorCode_ID(LEC_FE_Utils.getErrorCode(mensaje.getIdentificador()));
	    		a.saveEx();
	    	}
	    	//
	    	if (autorizacion.getEstado().equals(LEC_FE_UtilsXml.comprobanteAutorizado)) {
	    		
	    		msg = null;
	    		isAutorizacion = true;

	    		// Update AccessCode
	    		if (ac.getCodeAccessType().equals(LEC_FE_UtilsXml.claveAccesoAutomatica)) {
	    			ac.setValue(autorizacion.getNumeroAutorizacion());
	    			ac.saveEx();
	    		}	
	    		// Update Authorisation
	    		a.setSRI_AuthorisationCode(autorizacion.getNumeroAutorizacion());
	    		a.setSRI_DateAuthorisation(autorizacion.getFechaAutorizacion().toString());
	    		a.setSRI_ErrorCode_ID(0);
	    		a.setProcessed(true);
	    		a.saveEx();
	    		
	    		file_name = getFilename(this, folderComprobantesAutorizados);
	    	} else if (autorizacion.getEstado().equals(comprobanteNoAutorizado))
	    		file_name = getFilename(this, folderComprobantesNoAutorizados);
	    	else
	    		file_name = getFilename(this, folderComprobantesRechazados);
	    	
	    	if (autorizacion.getEstado().equals(LEC_FE_UtilsXml.comprobanteNoAutorizado)
    			// Completar en estos casos, luego usar Boton Reprocesar
    			// 43 Clave acceso registrada
	        	// 70-Clave de acceso en procesamiento
    			&& (a.getSRI_ErrorCode().getValue().equals("43") || a.getSRI_ErrorCode().getValue().equals("70")) ) {
    	    		isAutorizacion = true;
    	    		file_name = getFilename(this, folderComprobantesAutorizados);
    			}
	    	
	    	// TODO Extraer y guardar autorizacion xml signed and authorised en file_name
	    	// FileUtils.writeStringToFile(new File(file_name), autorizacion.toString());
	    	FileUtils.writeStringToFile(new File(file_name), autorizacion.getComprobante());
	    	
	  		// Atach XML Autorizado
    		if (isAutorizacion && isAttachXml())
    			LEC_FE_Utils.attachXmlFile(a.getCtx(), a.get_TrxName(), a.getSRI_Authorisation_ID(), file_name);
  	    	
	    	break;	// Solo Respuesta autorizacion mas reciente segun accesscode
	    	
	    	}
	    
    	}
    	catch (Exception e)
		{
			msg = msg + e.getMessage();
    		return msg;
		}
		
    	if (msg == null)
    		if (! isAutorizacion) {
    			a.setSRI_ErrorCode_ID(LEC_FE_Utils.getErrorCode("70"));
	    		a.saveEx();
	    		file_name = getFilename(this, folderComprobantesFirmados);
		  		// Atach XML Autorizado
	    		if (isAttachXml())
	    			LEC_FE_Utils.attachXmlFile(a.getCtx(), a.get_TrxName(), a.getSRI_Authorisation_ID(), file_name);
     			msg = "@Autorizacion Xml@ -> No hay Respuesta Autorizacion SRI";
    		}
    	
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
    	((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", getSriWSTimeout());
    	((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.request.timeout", getSriWSTimeout());
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
    	((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", getSriWSTimeout());
    	((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.request.timeout", getSriWSTimeout());
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
            if (accionComprobantesService.equals(recepcionComprobantesService)) {
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
    
    public File getFileFromStream(String xmlFilePath, int sri_authorisation_id) throws Exception {
    	
    	InputStream inputStream = null;
		OutputStream outputStream = null;
		File file = null;
		MAttachment attach =  MAttachment.get(Env.getCtx(), MTable.getTable_ID("SRI_Authorisation"), sri_authorisation_id);
		if (attach != null) {
    		for (MAttachmentEntry entry : attach.getEntries()) {
            	if (entry.getName().endsWith("xml") && !entry.getName().contains("old")) {
            		setResource_To_Sign(entry.getName());
            		xmlFilePath = getOutput_Directory() + File.separator + folderComprobantesFirmados + File.separator + getSignatureFileName().substring(getSignatureFileName().lastIndexOf(File.separator) + 1);
            		inputStream = new FileInputStream(entry.getFile());
            		outputStream = new FileOutputStream(xmlFilePath);
            		
            		int numRead = 0;
            		byte[] bytes = new byte[1024];
            		 
            		while ((numRead = inputStream.read(bytes)) != -1) {
            			outputStream.write(bytes, 0, numRead);
            		}
            		inputStream.close();
            		outputStream.flush();
            		outputStream.close();
            		file = (new File (xmlFilePath));
            		break;	// First
            	}
            }
		}
		
		return file;
    }
    
	public String getFilename(LEC_FE_UtilsXml signature, String folderComprobantesDestino)	// Trace temporal
	{
		
		String file_name = signature.getFolderRaiz() + File.separator + folderComprobantesDestino + File.separator
        		+ signature.getSignatureFileName().substring(signature.getSignatureFileName().lastIndexOf(File.separator) + 1);
	
		return file_name;
	}
    
	
}	// LEC_FE_UtilsXml
