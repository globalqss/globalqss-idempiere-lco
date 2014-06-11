package org.globalqss.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAttachment;
import org.compiere.model.MAttachmentEntry;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MLocation;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTable;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.globalqss.util.LEC_FE_Utils;
import org.globalqss.util.LEC_FE_UtilsXml;
import org.xml.sax.helpers.AttributesImpl;


/**
 *	LEC_FE_MInvoice
 *
 *  @author Carlos Ruiz - globalqss - Quality Systems & Solutions - http://globalqss.com 
 *  @version  $Id: LEC_FE_MInOut.java,v 1.0 2014/05/06 03:37:29 cruiz Exp $
 */
public class LEC_FE_MInOut extends MInOut
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -924606040343895114L;
	
	private int		m_SRI_Authorisation_ID = 0;
	private int		m_lec_sri_format_id = 0;

	private String file_name = "";
	private String m_tipoclaveacceso = "1";	// 1-Automatica, 2-Contingencia
	private String m_tipoambiente = "2";	// 1-Pruebas, 2-Produccion
	private String m_tipoemision = "1";		// 1-Normal, 2-Contingencia
	private String m_obligadocontabilidad = "NO";
	private String m_coddoc = "";
	private String m_accesscode;
	private String m_identificacionconsumidor = "";
	private String m_tipoidentificacioncomprador = "";
	private String m_tipoidentificaciontransportista = "";
	private String m_identificacioncomprador = "";
	private String m_razonsocial = "";

	private boolean isOnTesting = false;
	private boolean isAttachXML = false;
	
	public LEC_FE_MInOut(Properties ctx, int M_InOut_ID, String trxName) {
		super(ctx, M_InOut_ID, trxName);
	}
	
	public String lecfeinv_SriExportInOutXML100 ()
	{
		
		String msg = null;	// TODO Reviewe No completar if error
		
		LEC_FE_UtilsXml signature = new LEC_FE_UtilsXml();
		
		try
		{
			
		isOnTesting=MSysConfig.getBooleanValue("QSSLEC_FE_EnPruebas", false, getAD_Client_ID());
		
		if (isOnTesting) m_tipoambiente = "1";
				
		isAttachXML=MSysConfig.getBooleanValue("QSSLEC_FE_DebugEnvioRecepcion", false, getAD_Client_ID());
		
		m_identificacionconsumidor=MSysConfig.getValue("QSSLEC_FE_IdentificacionConsumidorFinal", null, getAD_Client_ID());
		
		m_razonsocial=MSysConfig.getValue("QSSLEC_FE_RazonSocialPruebas", null, getAD_Client_ID());
		
		signature.setPKCS12_Resource(MSysConfig.getValue("QSSLEC_FE_RutaCertificadoDigital", null, getAD_Client_ID(), getAD_Org_ID()));
		
		if (signature.getPKCS12_Resource().equals(""))
			throw new AdempiereUserError("No existe parametro para RutaCertificadoDigital");
		
		signature.setFolderRaiz(MSysConfig.getValue("QSSLEC_FE_RutaGeneracionXml", null, getAD_Client_ID()));	// Segun SysConfig + Formato
		
		if (signature.getFolderRaiz().equals(""))
			throw new AdempiereUserError("No existe parametro para Ruta Generacion Xml");
		
		MDocType dt = new MDocType(getCtx(), getC_DocType_ID(), get_TrxName());
		
		m_coddoc = dt.get_ValueAsString("SRI_ShortDocType");
		
		if ( m_coddoc.equals(""))
			throw new AdempiereUserError("No existe definicion SRI_ShortDocType: " + dt.toString());
		
		// Formato
		m_lec_sri_format_id = LEC_FE_Utils.getLecSriFormat(getAD_Client_ID(), m_tipoemision, m_coddoc, getMovementDate(), getMovementDate());
		
		if ( m_lec_sri_format_id < 1)
			throw new AdempiereUserError("No existe formato para el comprobante");
		
		X_LEC_SRI_Format f = new X_LEC_SRI_Format (getCtx(), m_lec_sri_format_id, get_TrxName());
		
		// Emisor
		MOrgInfo oi = MOrgInfo.get(getCtx(), getAD_Org_ID(), get_TrxName());
		
		MLocation lo = new MLocation(getCtx(), oi.getC_Location_ID(), get_TrxName());
		
		if ( (Boolean) oi.get_Value("SRI_IsKeepAccounting"))
			m_obligadocontabilidad = "SI";
		
		if (oi.get_ValueAsString("TaxID").equals(""))
			throw new AdempiereUserError("No existe definicion OrgInfo.Documento: " + oi.toString());
		if (oi.get_ValueAsString("SRI_DocumentCode").equals(""))
			throw new AdempiereUserError("No existe definicion OrgInfo.DocumentCode: " + oi.toString());
		int c_bpartner_id = LEC_FE_Utils.getOrgBPartner(getAD_Client_ID(), oi.get_ValueAsString("TaxID"));
		if (c_bpartner_id < 1)
			throw new AdempiereUserError("No existe BP relacioando a OrgInfo.Documento: " + oi.get_ValueAsString("TaxID"));
		if (oi.get_ValueAsString("SRI_OrgCode").equals(""))
			throw new AdempiereUserError("No existe definicion  OrgInfo.SRI_OrgCode: " + oi.toString());
		if (oi.get_ValueAsString("SRI_StoreCode").equals(""))
			throw new AdempiereUserError("No existe definicion  OrgInfo.SRI_StoreCode: " + oi.toString());
		if (oi.get_ValueAsString("SRI_DocumentCode").equals(""))
			throw new AdempiereUserError("No existe definicion  OrgInfo.SRI_DocumentCode: " + oi.toString());
		if (oi.get_ValueAsString("SRI_IsKeepAccounting").equals(""))
			throw new AdempiereUserError("No existe definicion  OrgInfo.SRI_IsKeepAccounting: " + oi.toString());
		if (oi.getC_Location_ID() == 0)
			throw new AdempiereUserError("No existe definicion  OrgInfo.Address1: " + oi.toString());
				
		MBPartner bpe = new MBPartner(getCtx(), c_bpartner_id, get_TrxName());
		if ( (Integer) bpe.get_Value("LCO_TaxPayerType_ID") == 1000027)	// Hardcoded
			if (oi.get_ValueAsString("SRI_TaxPayerCode").equals(""))
				throw new AdempiereUserError("No existe definicion  OrgInfo.SRI_TaxPayerCode: " + oi.toString());
			;
			
		MLocation lw = new MLocation(getCtx(), getM_Warehouse().getC_Location_ID(), get_TrxName());
		
		// Comprador
		MBPartner bp = new MBPartner(getCtx(), getC_BPartner_ID(), get_TrxName());
		if (!isOnTesting) m_razonsocial = bp.getName();
		
		MLocation bpl = new MLocation(getCtx(), getC_BPartner_Location().getC_Location_ID(), get_TrxName());	// TODO Reviewme
		
		X_LCO_TaxIdType ttc = new X_LCO_TaxIdType(getCtx(), (Integer) bp.get_Value("LCO_TaxIdType_ID"), get_TrxName());
		
		m_tipoidentificacioncomprador = LEC_FE_Utils.getTipoIdentificacionSri(ttc.get_Value("LEC_TaxCodeSRI").toString());
		
		m_identificacioncomprador = bp.getTaxID();
		
		X_LCO_TaxIdType tt = new X_LCO_TaxIdType(getCtx(), (Integer) bp.get_Value("LCO_TaxIdType_ID"), get_TrxName());
		if (tt.getLCO_TaxIdType_ID() == 1000011)	// Hardcoded F Final	// TODO Deprecated
			m_identificacioncomprador = m_identificacionconsumidor;
		
		// Transportista
		MBPartner bpt = new MBPartner(getCtx(), getM_Shipper().getC_BPartner_ID(), get_TrxName());
		
		X_LCO_TaxIdType ttt = new X_LCO_TaxIdType(getCtx(), (Integer) bpt.get_Value("LCO_TaxIdType_ID"), get_TrxName());
		
		X_LCO_TaxPayerType tpt = new X_LCO_TaxPayerType(getCtx(), (Integer) bpt.get_Value("LCO_TaxPayerType_ID"), get_TrxName());
		
		m_tipoidentificaciontransportista = LEC_FE_Utils.getTipoIdentificacionSri(ttt.get_Value("LEC_TaxCodeSRI").toString());

		m_accesscode = LEC_FE_Utils.getAccessCode(getMovementDate(), m_coddoc, bpe.getTaxID(), m_tipoambiente, oi.get_ValueAsString("SRI_OrgCode"), oi.get_ValueAsString("SRI_StoreCode"), getDocumentNo(), oi.get_ValueAsString("SRI_DocumentCode"), m_tipoemision);
		
		// TODO IsUseContingency
		// if (IsUseContingency) m_tipoclaveacceso = "2";
		
		// New Auto Access Code
		X_SRI_AccessCode ac = new X_SRI_AccessCode (getCtx(), 0, get_TrxName());
		ac.setAD_Org_ID(getAD_Org_ID());
		ac.setValue(m_accesscode);
		ac.setOldValue(null);
		ac.setEnvType(m_tipoambiente);	// Before Save ?
		ac.setCodeAccessType(m_tipoclaveacceso); // Auto Before Save ?
		ac.setSRI_ShortDocType(m_coddoc);
		ac.setIsUsed(true);
		
		if (!ac.save()) {
			msg = "No se pudo grabar SRI Access Code";
			throw new AdempiereException(msg);
		}
		
		// New Authorisation
		X_SRI_Authorisation a = new X_SRI_Authorisation (getCtx(), 0, get_TrxName());
		a.setAD_Org_ID(getAD_Org_ID());
		a.setSRI_ShortDocType(m_coddoc);
		a.setValue(m_accesscode);
		a.setNewValue(null);
		a.setSRI_AccessCode_ID(ac.get_ID());
		a.setSRI_ErrorCode_ID(0);
		a.setAD_UserMail_ID(0);
		//a.setProcessed(true);
		
		if (!a.save()) {
			msg = "No se pudo grabar SRI Autorizacion";
			throw new AdempiereException(msg);
		}
		
		set_Value("SRI_Authorisation_ID", a.get_ID());
		this.saveEx();	// TODO Revieme
					
		OutputStream  mmDocStream = null;
				
		String xmlFileName = "SRI_" + m_accesscode + ".xml";
	
		//crea los directorios para los archivos xml
		(new File(signature.getFolderRaiz() + File.separator + signature.getFolderComprobantesGenerados() + File.separator)).mkdirs();
		(new File(signature.getFolderRaiz() + File.separator + signature.getFolderComprobantesFirmados() + File.separator)).mkdirs();
		//ruta completa del archivo xml
		file_name = signature.getFolderRaiz() + File.separator + signature.getFolderComprobantesGenerados() + File.separator + xmlFileName;	
		//Stream para el documento xml
		mmDocStream = new FileOutputStream (file_name, false);
		StreamResult streamResult_menu = new StreamResult(new OutputStreamWriter(mmDocStream,signature.getXmlEncoding()));
		SAXTransformerFactory tf_menu = (SAXTransformerFactory) SAXTransformerFactory.newInstance();					
		try {
			tf_menu.setAttribute("indent-number", new Integer(0));
		} catch (Exception e) {
			// swallow
		}
		TransformerHandler mmDoc = tf_menu.newTransformerHandler();	
		Transformer serializer_menu = mmDoc.getTransformer();	
		serializer_menu.setOutputProperty(OutputKeys.ENCODING,signature.getXmlEncoding());
		try {
			serializer_menu.setOutputProperty(OutputKeys.INDENT,"yes");
		} catch (Exception e) {
			// swallow
		}
		mmDoc.setResult(streamResult_menu);
		
		mmDoc.startDocument();
		
		AttributesImpl atts = new AttributesImpl();
		
		StringBuffer sql = null;

		// Encabezado
		atts.clear();
		atts.addAttribute("", "", "id", "CDATA", "comprobante");
		atts.addAttribute("", "", "version", "CDATA", f.get_ValueAsString("VersionNo"));
		mmDoc.startElement("", "", f.get_ValueAsString("XmlPrintLabel"), atts);
		
		atts.clear();
		
		// Emisor
		mmDoc.startElement("","","infoTributaria", atts);
			// Numerico1
			addHeaderElement(mmDoc, "ambiente", m_tipoambiente, atts);
			// Numerico1
			addHeaderElement(mmDoc, "tipoEmision", m_tipoemision, atts);
			// Alfanumerico Max 300
			addHeaderElement(mmDoc, "razonSocial", bpe.getName(), atts);
			// Alfanumerico Max 300
			addHeaderElement(mmDoc, "nombreComercial", bpe.getName2(), atts);
			// Numerico13
			addHeaderElement(mmDoc, "ruc", (LEC_FE_Utils.fillString(13 - (LEC_FE_Utils.cutString(bpe.getTaxID(), 13)).length(), '0'))
				+ LEC_FE_Utils.cutString(bpe.getTaxID(),13), atts);
			// Numérico49
			addHeaderElement(mmDoc, "claveAcceso", a.getValue(), atts);
			// Numerico2
			addHeaderElement(mmDoc, "codDoc", m_coddoc, atts);
			// Numerico3
			addHeaderElement(mmDoc, "estab", oi.get_ValueAsString("SRI_OrgCode"), atts);
			// Numerico3
			addHeaderElement(mmDoc, "ptoEmi", oi.get_ValueAsString("SRI_StoreCode"), atts);
			// Numerico9
			addHeaderElement(mmDoc, "secuencial", (LEC_FE_Utils.fillString(9 - (LEC_FE_Utils.cutString(LEC_FE_Utils.getSecuencial(getDocumentNo(), m_coddoc), 9)).length(), '0'))
					+ LEC_FE_Utils.cutString(LEC_FE_Utils.getSecuencial(getDocumentNo(), m_coddoc), 9), atts);
			// Alfanumerico Max 300
			addHeaderElement(mmDoc, "dirMatriz", lo.getAddress1(), atts);
		mmDoc.endElement("","","infoTributaria");
		
		mmDoc.startElement("","","infoGuiaRemision",atts);
		// Emisor
			// Alfanumerico Max 300
			addHeaderElement(mmDoc, "dirEstablecimiento", lo.getAddress1(), atts);
			// Alfanumerico Max 300
			addHeaderElement(mmDoc, "dirPartida", lw.getAddress1(), atts);	// TODO Reviewme
		// Transportista
			// Alfanumerico Max 300
			addHeaderElement(mmDoc, "razonSocialTransportista", bpt.getName(), atts);
			// Numerico2
			addHeaderElement(mmDoc, "tipoIdentificacionTransportista", m_tipoidentificaciontransportista, atts);
			// Numerico13
			addHeaderElement(mmDoc, "rucTransportista", (LEC_FE_Utils.fillString(13 - (LEC_FE_Utils.cutString(bpt.getTaxID(), 13)).length(), '0'))
					+ LEC_FE_Utils.cutString(m_identificacioncomprador,13), atts);			
			// Alfanumerico Max 40
			addHeaderElement(mmDoc, "rise", LEC_FE_Utils.cutString(tpt.getName(), 40), atts);
			// Texto2
			addHeaderElement(mmDoc, "obligadoContabilidad", m_obligadocontabilidad, atts);
			// Numerico3-5
			addHeaderElement(mmDoc, "contribuyenteEspecial", oi.get_ValueAsString("SRI_TaxPayerCode"), atts);
			// Fecha8 ddmmaaaa
			addHeaderElement(mmDoc, "fechaIniTransporte", LEC_FE_Utils.getDate(getShipDate(),10), atts);
			Timestamp datets = (Timestamp) get_Value("ShipDateE");
			Date dates = new Date(datets.getTime()); 
			// Fecha8 ddmmaaaa
			addHeaderElement(mmDoc, "fechaFinTransporte", LEC_FE_Utils.getDate(dates,10), atts);
			// Alfanumerico Max 20
			addHeaderElement(mmDoc, "placa", LEC_FE_Utils.cutString(getM_Shipper().getName(), 40), atts);
			
		mmDoc.endElement("","","infoGuiaRemision");	
		
		// Destinatarios
		mmDoc.startElement("","","destinatarios",atts);
		
			mmDoc.startElement("","","destinatario",atts);
			
			// Numerico13
			addHeaderElement(mmDoc, "identificacionDestinatario", (LEC_FE_Utils.fillString(13 - (LEC_FE_Utils.cutString(bp.getTaxID(), 13)).length(), '0'))
					+ LEC_FE_Utils.cutString(bp.getTaxID(),13), atts);
			// Alfanumerico Max 300
			addHeaderElement(mmDoc, "razonSocialDestinatario", bp.getName(), atts);
			// Alfanumerico Max 300
			addHeaderElement(mmDoc, "dirDestinatario", bpl.getAddress1(), atts);
			// Alfanumerico Max 300
			addHeaderElement(mmDoc, "motivoTraslado", "TODO", atts);
			// Alfanumerico Max 20
			addHeaderElement(mmDoc, "docAduaneroUnico", "TODO", atts);
			// Numerico3
			addHeaderElement(mmDoc, "codEstabDestino", "TODO", atts);
			// Alfanumerico Max 300
			addHeaderElement(mmDoc, "ruta", lw.getCityRegionPostal() + " - " + bpl.getCityRegionPostal(), atts);
			// Numerico2
			addHeaderElement(mmDoc, "codDocSustento", "TODO", atts);
			// Numerico15 -- Incluye guiones
			addHeaderElement(mmDoc, "numDocSustento",  "TODO", atts);
			// Numerico10-37
			addHeaderElement(mmDoc, "numAutDocSustento",  "TODO", atts);
			// Fecha8 ddmmaaaa
			addHeaderElement(mmDoc, "fechaEmisionDocSustento","TODO", atts);
						
		// Detalles
		mmDoc.startElement("","","detalles",atts);
		
		sql = new StringBuffer(
	            "SELECT io.M_InOut_ID, COALESCE(p.value, '0'), 0::text, ilt.name, ilt.qtyentered "
				+ ", ilt.description AS description1 "
	            + "FROM M_InOut io "
	            + "JOIN M_InOutLine iol ON iol.M_InOut_ID = io.M_InOut_ID "
	            + "JOIN M_InOut_Line_VT ilt ON ilt.M_InOutLine_ID = iol.M_InOutLine_ID "
	            + "LEFT JOIN M_Product p ON p.M_Product_ID = iol.M_Product_ID "
	            + "LEFT JOIN M_Product_Category pc ON pc.M_Product_Category_ID = p.M_Product_Category_ID "
	            + "WHERE iol.IsDescription = 'N' AND io.M_InOut_ID=? "
	            + "ORDER BY iol.line");
		
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, getM_InOut_ID());
			ResultSet rs = pstmt.executeQuery();
			//
			
			while (rs.next())
			{
				mmDoc.startElement("","","detalle",atts);
				
				// Alfanumerico MAx 25
				addHeaderElement(mmDoc, "codigoInterno",  LEC_FE_Utils.cutString(rs.getString(2),25), atts);
				// Alfanumerico MAx 25
				addHeaderElement(mmDoc, "codigoAdicional", LEC_FE_Utils.cutString(rs.getString(3),25), atts);
				// Alfanumerico Max 300
				addHeaderElement(mmDoc, "descripcion", LEC_FE_Utils.cutString(rs.getString(4),300), atts);
				// Numerico Max 14
				addHeaderElement(mmDoc, "cantidad", rs.getBigDecimal(5).toString(), atts);
				
				if (rs.getString(6) != null)  {	// TODO Reviewme
					mmDoc.startElement("","","detallesAdicionales",atts);
					
						atts.clear();
						atts.addAttribute("", "", "descripcion1", "CDATA", LEC_FE_Utils.cutString(rs.getString(6),300));
						mmDoc.startElement("", "", "detAdicional", atts);
						mmDoc.endElement("","","detAdicional");
						
					mmDoc.endElement("","","detallesAdicionales");
				}
				
				atts.clear();
				//
				mmDoc.endElement("","","detalle");
				
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
			msg = "Error SQL: " + sql.toString();
			throw new AdempiereException(msg);
		}
		
		mmDoc.endElement("","","detalles");
		
		mmDoc.endElement("","","destinatario");
		
		mmDoc.endElement("","","destinatarios");
		
		if (getDescription() != null)  {	// TODO Reviewme
			mmDoc.startElement("","","infoAdicional",atts);
			
				atts.clear();
				atts.addAttribute("", "", "descripcion2", "CDATA", LEC_FE_Utils.cutString(getDescription(),300));
				mmDoc.startElement("", "", "campoAdicional", atts);
				mmDoc.endElement("","","campoAdicional");
			
			mmDoc.endElement("","","infoAdicional");
		}
	
		mmDoc.endElement("","",f.get_ValueAsString("XmlPrintLabel"));
		
		mmDoc.endDocument();
	
		if (mmDocStream != null) {
			try {
				mmDocStream.close();
			} catch (Exception e2) {}
		}
	
		log.warning("@Signing Xml@ -> " + file_name);
		signature.setResource_To_Sign(file_name);
		// TODO signature.setPKCS12_Password("changeit");
		signature.setOutput_Directory(signature.getFolderRaiz() + File.separator + signature.getFolderComprobantesFirmados());
        signature.execute();
        file_name = signature.getFolderRaiz() + File.separator + signature.getFolderComprobantesFirmados() + File.separator
        		+ signature.getSignatureFileName().substring(signature.getSignatureFileName().lastIndexOf(File.separator) + 1);
        
        log.warning("@Sending Xml@ -> " + file_name);
        // TODO Enviar a Recepcion Comprobante SRI
		// TODO Procesar Solicitud Autorizacion SRI
		// TODO Procesar Respuesta SRI
		// TODO Enviar Email Cliente
		
		// TODO Atach XML Autorizado
		if (isAttachXML) {
			int  AD_Table_ID = MTable.getTable_ID(X_SRI_Authorisation.Table_Name);
			//if one attach is found , it means that a xml file was attached before
			MAttachment attach =  MAttachment.get(getCtx(),AD_Table_ID, a.getSRI_Authorisation_ID());
			//no se encontro archivo previo
			if (attach == null ) {
				attach = new  MAttachment(getCtx(),AD_Table_ID , a.getSRI_Authorisation_ID(),get_TrxName());
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
		}
		// MAttachment
		/*
		  Si IsAttachXML
	      Anexar el archivo XML al XML Header (probar con Archivador
		  a ver si funciona, si no con Attachment)
	      NOTA: ¿Que hacer si ya hay un archivo previo generado?
		  Verificar si el archivador es read-only, y si podria renombrar
		  un archivo previo generado para añadirle un sufijo _old_yyyymmdd
		 */
	
		}
		catch (Exception e)
		{
			msg = "No se pudo crear XML - " + e.getMessage();
			log.severe(msg);
			throw new AdempiereException(msg);
		}
		
		log.warning("@SRI_FileGenerated@ -> " + file_name);
		
		//return null;
		return msg;
	
	} // lecfeinout_SriExportInOutXML100
	
	public void addHeaderElement(TransformerHandler mmDoc, String att, String value, AttributesImpl atts) throws Exception {
		if (att != null) {
			mmDoc.startElement("","",att,atts);
			mmDoc.characters(value.toCharArray(),0,value.toCharArray().length);
			mmDoc.endElement("","",att);
		} else {
			throw new AdempiereUserError(att + " empty");
		}
	}
	

}	// LEC_FE_MInOut