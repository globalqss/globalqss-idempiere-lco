package org.globalqss.util;


import org.globalqss.model.GenericXMLSignature;
import org.w3c.dom.Document;

import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
import es.mityc.javasign.EnumFormatoFirma;
import es.mityc.javasign.xml.refs.AllXMLToSign;
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
        Document docToSign = getDocument(getResource_To_Sign());
        dataToSign.setDocument(docToSign);
        dataToSign.addObject(new ObjectToSign(new AllXMLToSign(), "Documento de ejemplo", null, "text/xml", null));
        return dataToSign;
    }
    
}	// LEC_FE_UtilsXml
