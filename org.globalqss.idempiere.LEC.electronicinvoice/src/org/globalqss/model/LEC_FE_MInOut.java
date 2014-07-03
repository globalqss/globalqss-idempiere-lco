package org.globalqss.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInvoice;
import org.compiere.model.MLocation;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MSysConfig;
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
	private int		m_c_invoice_sus_id = 0;

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


	public LEC_FE_MInOut(Properties ctx, int M_InOut_ID, String trxName) {
		super(ctx, M_InOut_ID, trxName);
	}
	
	public String lecfeinout_SriExportInOutXML100 ()
	{
		
		String msg = null;	// TODO Reviewe No completar if error
		
		LEC_FE_UtilsXml signature = new LEC_FE_UtilsXml();
		
		try
		{
			
		signature.setOnTesting(MSysConfig.getBooleanValue("QSSLEC_FE_EnPruebas", false, getAD_Client_ID()));
			
		if (signature.isOnTesting()) m_tipoambiente = "1";
					
		signature.setAttachXml(MSysConfig.getBooleanValue("QSSLEC_FE_DebugEnvioRecepcion", false, getAD_Client_ID()));
		
		m_identificacionconsumidor=MSysConfig.getValue("QSSLEC_FE_IdentificacionConsumidorFinal", null, getAD_Client_ID());
		
		m_razonsocial=MSysConfig.getValue("QSSLEC_FE_RazonSocialPruebas", null, getAD_Client_ID());
		
		signature.setPKCS12_Resource(MSysConfig.getValue("QSSLEC_FE_RutaCertificadoDigital", null, getAD_Client_ID(), getAD_Org_ID()));
		
		signature.setFolderRaiz(MSysConfig.getValue("QSSLEC_FE_RutaGeneracionXml", null, getAD_Client_ID()));	// Segun SysConfig + Formato
		
		if (signature.getFolderRaiz() == null)
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
		
		msg = LEC_FE_Validator.valideOrgInfoSri (oi);
		
		if (msg != null)
			throw new AdempiereUserError(msg);
		
		if ( (Boolean) oi.get_Value("SRI_IsKeepAccounting"))
			m_obligadocontabilidad = "SI";
		
		int c_bpartner_id = LEC_FE_Utils.getOrgBPartner(getAD_Client_ID(), oi.get_ValueAsString("TaxID"));
		MBPartner bpe = new MBPartner(getCtx(), c_bpartner_id, get_TrxName());
		
		MLocation lo = new MLocation(getCtx(), oi.getC_Location_ID(), get_TrxName());
		
		MLocation lw = new MLocation(getCtx(), getM_Warehouse().getC_Location_ID(), get_TrxName());
		
		// Comprador
		MBPartner bp = new MBPartner(getCtx(), getC_BPartner_ID(), get_TrxName());
		if (!signature.isOnTesting()) m_razonsocial = bp.getName();
		
		MLocation bpl = new MLocation(getCtx(), getC_BPartner_Location().getC_Location_ID(), get_TrxName());	// TODO Reviewme
		
		X_LCO_TaxIdType ttc = new X_LCO_TaxIdType(getCtx(), (Integer) bp.get_Value("LCO_TaxIdType_ID"), get_TrxName());
		
		m_tipoidentificacioncomprador = LEC_FE_Utils.getTipoIdentificacionSri(ttc.get_Value("LEC_TaxCodeSRI").toString());
		
		m_identificacioncomprador = bp.getTaxID();
		
		X_LCO_TaxIdType tt = new X_LCO_TaxIdType(getCtx(), (Integer) bp.get_Value("LCO_TaxIdType_ID"), get_TrxName());
		if (tt.getLCO_TaxIdType_ID() == 1000011)	// Hardcoded F Final	// TODO Deprecated
			m_identificacioncomprador = m_identificacionconsumidor;
		
		// Transportista
		if (getM_Shipper_ID() == 0)
			throw new AdempiereUserError("No existe definicion Transportista");
			
		MBPartner bpt = new MBPartner(getCtx(), getM_Shipper().getC_BPartner_ID(), get_TrxName());
		
		X_LCO_TaxIdType ttt = new X_LCO_TaxIdType(getCtx(), (Integer) bpt.get_Value("LCO_TaxIdType_ID"), get_TrxName());
		
		X_LCO_TaxPayerType tpt = new X_LCO_TaxPayerType(getCtx(), (Integer) bpt.get_Value("LCO_TaxPayerType_ID"), get_TrxName());
		
		m_tipoidentificaciontransportista = LEC_FE_Utils.getTipoIdentificacionSri(ttt.get_Value("LEC_TaxCodeSRI").toString());
		
		m_c_invoice_sus_id = LEC_FE_Utils.getInOutDocSustento(getM_InOut_ID());
		
		if ( m_c_invoice_sus_id < 1)
			log.warning("No existe documento sustento para el comprobante");	// TODO Reviewme
			//throw new AdempiereUserError("No existe documento sustento para el comprobante");
		
		MInvoice invsus = null;
		if ( m_c_invoice_sus_id > 0)
			invsus = new MInvoice(getCtx(), m_c_invoice_sus_id, get_TrxName());
		
		// Access Code
		m_accesscode = LEC_FE_Utils.getAccessCode(getMovementDate(), m_coddoc, bpe.getTaxID(), m_tipoambiente, oi.get_ValueAsString("SRI_OrgCode"), LEC_FE_Utils.getStoreCode(LEC_FE_Utils.formatDocNo(getDocumentNo(), m_coddoc)), getDocumentNo(), oi.get_ValueAsString("SRI_DocumentCode"), m_tipoemision);
		
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
			msg = "@SaveError@ No se pudo grabar SRI Access Code";
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
			msg = "@SaveError@ No se pudo grabar SRI Autorizacion";
			throw new AdempiereException(msg);
		}
		
		set_Value("SRI_Authorisation_ID", a.get_ID());
		this.saveEx();	// TODO Revieme
					
		OutputStream  mmDocStream = null;
				
		String xmlFileName = "SRI_" + m_coddoc + "-" + LEC_FE_Utils.getDate(getMovementDate(),9) + "-" + m_accesscode + ".xml";
	
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
		atts.addAttribute("", "", "xmlns:ds", "CDATA", "http://www.w3.org/2000/09/xmldsig#");
		atts.addAttribute("", "", "xmlns:xsi", "CDATA", "http://www.w3.org/2001/XMLSchema-instance");
		atts.addAttribute("", "", "xsi:noNamespaceSchemaLocation", "CDATA", f.get_ValueAsString("Url_Xsd"));
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
			addHeaderElement(mmDoc, "ptoEmi", LEC_FE_Utils.getStoreCode(LEC_FE_Utils.formatDocNo(getDocumentNo(), m_coddoc)), atts);
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
					+ LEC_FE_Utils.cutString(bpt.getTaxID(),13), atts);			
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
			if  ( m_c_invoice_sus_id > 0) {
				// Numerico2
				if (m_coddoc.equals("06"))
					addHeaderElement(mmDoc, "codDocSustento", "01", atts);	// Hardcoded
				// Numerico15 -- Incluye guiones
				addHeaderElement(mmDoc, "numDocSustento", LEC_FE_Utils.formatDocNo(invsus.getDocumentNo(), "01"), atts);
				// Numerico10-37
				addHeaderElement(mmDoc, "numAutDocSustento", "TODO", atts);
				// Fecha8 ddmmaaaa
				addHeaderElement(mmDoc, "fechaEmisionDocSustento", LEC_FE_Utils.getDate(invsus.getDateInvoiced(),10), atts);
			}
			
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
				
				if (rs.getString(6) != null)  {
					mmDoc.startElement("","","detallesAdicionales",atts);
					
					atts.clear();
					atts.addAttribute("", "", "nombre", "CDATA", "descripcion1");
					atts.addAttribute("", "", "valor", "CDATA", LEC_FE_Utils.cutString(rs.getString(14),300));
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
		
		if (getDescription() != null)  {
			mmDoc.startElement("","","infoAdicional",atts);
			
				atts.clear();
				atts.addAttribute("", "", "nombre", "CDATA", "descripcion2");
				mmDoc.startElement("", "", "campoAdicional", atts);
				String valor = LEC_FE_Utils.cutString(getDescription(),300);
				mmDoc.characters(valor.toCharArray(), 0, valor.length());
			
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
		
		// TODO Attach XML Autorizado
		if (signature.isAttachXml())
			LEC_FE_Utils.attachXmlFile(getCtx(), get_TrxName(), a.getSRI_Authorisation_ID(), file_name);

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