package org.globalqss.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.compiere.model.MInvoice;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTable;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.xml.sax.helpers.AttributesImpl;

import org.globalqss.util.LEC_FE_Utils;

/**
 *	LEC_FE_MInvoice
 *
 *  @author Carlos Ruiz - globalqss - Quality Systems & Solutions - http://globalqss.com 
 *  @version  $Id: LEC_FE_MInvoice.java,v 1.0 2014/05/06 03:37:29 cruiz Exp $
 */
public class LEC_FE_MInvoice extends MInvoice
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -924606040343895114L;
	
	private int		m_SRI_Authorisation_ID = 0;
	/** Dir				*/
	private String folder = "";
	private String file_name = "";
	private String m_tipoclaveacceso = "1";	// 1-Automatica, 2-Contingencia
	private String m_tipoambiente = "2";	// 1-Pruebas, 2-Produccion
	private String m_tipoemision = "1";		// 1-Normal, 2-Contingencia
	private String m_obligadocontabilidad = "NO";
	private String m_accesscode;
	private String m_identificacionconsumidor = "";
	private String m_identificacioncomprador = "";
	private String m_razonsocial = "";

	private boolean isOnTesting = false;
	private boolean isAttachXML = false;
	
	public LEC_FE_MInvoice(Properties ctx, int C_Invoice_ID, String trxName) {
		super(ctx, C_Invoice_ID, trxName);
	}
	
	public String lecfeinv_SriExportInvoiceXML100 ()
	{
		
		String msg = null;	// TODO Reviewe No completar if error
		
		try
		{
			
		isOnTesting=MSysConfig.getBooleanValue("QSSLEC_FE_EnPruebas", false, getAD_Client_ID());
		
		if (isOnTesting) m_tipoambiente = "1";
				
		isAttachXML=MSysConfig.getBooleanValue("QSSLEC_FE_DebugEnvioRecepcion", false, getAD_Client_ID());
		
		m_identificacionconsumidor=MSysConfig.getValue("QSSLEC_FE_IdentificacionConsumidorFinal", null, getAD_Client_ID());
		
		m_razonsocial=MSysConfig.getValue("QSSLEC_FE_RazonSocialPruebas", null, getAD_Client_ID());
		
		folder=MSysConfig.getValue("QSSLEC_FE_RutaGeneracionXml", null, getAD_Client_ID());	// Segun SysConfig + Formato
		
		if ( folder.equals(""))
			throw new AdempiereUserError("No existe parametro para Ruta Generacion Xml");
		
		OutputStream  mmDocStream = null;
			
		MDocType dt = new MDocType(getCtx(), getC_DocTypeTarget_ID(), get_TrxName());
		
		// Emisor
		MOrgInfo oi = MOrgInfo.get(getCtx(), getAD_Org_ID(), get_TrxName());
		
		if ( (Boolean) oi.get_Value("SRI_IsKeepAccounting"))
			m_obligadocontabilidad = "SI";
		
		if (oi.get_ValueAsString("TaxID").equals(""))
			throw new AdempiereUserError("No existe definicion OrgInfo.Documento: " + oi.toString());
		if (oi.get_ValueAsString("SRI_DocumentCode").equals(""))
			throw new AdempiereUserError("No existe definicion OrgInfo.DocumentCode: " + oi.toString());
		int c_bpartner_id = DB.getSQLValue(get_TrxName(), "SELECT C_BPartner_ID FROM C_BPartner WHERE AD_Client_ID = ? AND TaxId = ? ", getAD_Client_ID(), oi.get_ValueAsString("TaxID"));
		if (c_bpartner_id == -1)
			throw new AdempiereUserError("No existe BP relacioando a OrgInfo.Documento: " + oi.get_ValueAsString("TaxID"));
		if (oi.get_ValueAsString("SRI_OrgCode").equals(""))
			throw new AdempiereUserError("No existe definicion  OrgInfo.SRI_OrgCode: " + oi.toString());
		if (oi.get_ValueAsString("SRI_StoreCode").equals(""))
			throw new AdempiereUserError("No existe definicion  OrgInfo.SRI_StoreCode: " + oi.toString());
		if (oi.get_ValueAsString("SRI_DocumentCode").equals(""))
			throw new AdempiereUserError("No existe definicion  OrgInfo.SRI_DocumentCode: " + oi.toString());
		if (oi.get_ValueAsString("SRI_IsKeepAccounting").equals(""))
			throw new AdempiereUserError("No existe definicion  OrgInfo.SRI_IsKeepAccounting: " + oi.toString());
		if (oi.get_ValueAsString("SRI_TaxPayerCode").equals(""))
			throw new AdempiereUserError("No existe definicion  OrgInfo.SRI_TaxPayerCode: " + oi.toString());

		
		MBPartner bpe = new MBPartner(getCtx(), c_bpartner_id, get_TrxName());
		if ( (Integer) bpe.get_Value("LCO_TaxPayerType_ID") == 1000027)	// Hardcoded
			m_identificacionconsumidor = "999999999999";
		
		// Comprador
		MBPartner bp = new MBPartner(getCtx(), getC_BPartner_ID(), get_TrxName());
		if (!isOnTesting) m_razonsocial = bp.getName();
		
		m_identificacioncomprador = bp.getTaxID();
		
		X_LCO_TaxIdType tt = new X_LCO_TaxIdType(getCtx(), (Integer) bp.get_Value("LCO_TaxIdType_ID"), get_TrxName());
		if (tt.getLCO_TaxIdType_ID() == 1000011)	// Hardcoded F Final
			m_identificacioncomprador = m_identificacionconsumidor;
		
		if (dt.get_ValueAsString("SRI_ShortDocType") == null)
			throw new AdempiereUserError("No existe definicion SRI_ShortDocType: " + dt.toString());
		
		m_accesscode = ""
			+ String.format("%8s", LEC_FE_Utils.getDate(getDateInvoiced(),8))	// fechaEmision
			+ String.format("%2s", dt.get_ValueAsString("SRI_ShortDocType"))	// codDoc
			+ String.format("%13s", (LEC_FE_Utils.fillString(13 - (LEC_FE_Utils.cutString(bpe.getTaxID(), 13)).length(), '0'))
				+ LEC_FE_Utils.cutString(bpe.getTaxID(),13))					// ruc
			+ String.format("%1s", m_tipoambiente)								// ambiente
			+ String.format("%3s", oi.get_ValueAsString("SRI_OrgCode"))			// serie / estab
			+ String.format("%3s", oi.get_ValueAsString("SRI_StoreCode"))		// serie / ptoEmi
			+ String.format("%9s", (LEC_FE_Utils.fillString(9 - (LEC_FE_Utils.cutString(getDocumentNo().substring(8), 9)).length(), '0'))
				+ LEC_FE_Utils.cutString(getDocumentNo().substring(getDocumentNo().lastIndexOf('-')+1),9))// numero / secuencial
			+ String.format("%8s", oi.get_ValueAsString("SRI_DocumentCode"))							// codigo
			+ String.format("%1s", m_tipoemision);								// tipoEmision

			m_accesscode = m_accesscode
				+ String.format("%1s", LEC_FE_Utils.calculateDigitSri(m_accesscode));	// digito
		
		// TODO IsUseContingency
		// if (IsUseContingency) m_tipoclaveacceso = "2";
		
		// New Auto Access Code
		X_SRI_AccessCode ac = new X_SRI_AccessCode (getCtx(), 0, get_TrxName());
		ac.setAD_Org_ID(getAD_Org_ID());
		ac.setValue(m_accesscode);
		ac.setOldValue(null);
		ac.setEnvType(m_tipoambiente);	// Before Save ?
		ac.setCodeAccessType(m_tipoclaveacceso); // Auto Before Save ?
		ac.setSRI_ShortDocType(dt.get_ValueAsString("SRI_ShortDocType"));
		ac.setIsUsed(true);
		
		if (!ac.save()) {
			msg = "No se pudo grabar SRI Access Code";
			throw new AdempiereException(msg);
		}
		
		// New Authorisation
		X_SRI_Authorisation a = new X_SRI_Authorisation (getCtx(), 0, get_TrxName());
		a.setAD_Org_ID(getAD_Org_ID());
		a.setSRI_ShortDocType(dt.get_ValueAsString("SRI_ShortDocType"));
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
					
		String xmlFileName = "SRI_" + m_accesscode + ".xml";
	
		//crea los directorios para los archivos xml
		(new File(folder+File.separator+ "ComprobantesGenerados"+File.separator)).mkdirs();
		//ruta completa del archivo xml
		file_name = folder+File.separator+ "ComprobantesGenerados"+File.separator+xmlFileName;	
		//Stream para el documento xml
		mmDocStream = new FileOutputStream (file_name, false);
		StreamResult streamResult_menu = new StreamResult(new OutputStreamWriter(mmDocStream,"UTF-8"));
		SAXTransformerFactory tf_menu = (SAXTransformerFactory) SAXTransformerFactory.newInstance();					
		try {
			tf_menu.setAttribute("indent-number", new Integer(0));
		} catch (Exception e) {
			// swallow
		}
		TransformerHandler mmDoc = tf_menu.newTransformerHandler();	
		Transformer serializer_menu = mmDoc.getTransformer();	
		serializer_menu.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
		try {
			serializer_menu.setOutputProperty(OutputKeys.INDENT,"yes");
		} catch (Exception e) {
			// swallow
		}
		mmDoc.setResult(streamResult_menu);
		
		mmDoc.startDocument();
		
		AttributesImpl atts = new AttributesImpl();

		// Encabezado
		atts.clear();
		atts.addAttribute("", "", "id", "CDATA", "comprobante");
		atts.addAttribute("", "", "version", "CDATA", "1.0.0");
		mmDoc.startElement("", "", "factura", atts);
		
		atts.clear();
		mmDoc.startElement("","","infoTributaria", atts);
			//ambiente ,Numerico1
			addHeaderElement(mmDoc, "ambiente", m_tipoambiente, atts);
			//tipoEmision ,Numerico1
			addHeaderElement(mmDoc, "tipoEmision", m_tipoemision, atts);
			// razonSocial ,Alfanumerico Max 300
			addHeaderElement(mmDoc, "razonSocial", bpe.getName(), atts);
			// nombreComercial ,Alfanumerico Max 300
			addHeaderElement(mmDoc, "nombreComercial", bpe.getName2(), atts);
			// ruc ,Numerico13
			addHeaderElement(mmDoc, "ruc", (LEC_FE_Utils.fillString(13 - (LEC_FE_Utils.cutString(bpe.getTaxID(), 13)).length(), '0'))
				+ LEC_FE_Utils.cutString(bpe.getTaxID(),13), atts);
			// claveAcceso ,Numérico49
			addHeaderElement(mmDoc, "claveAcceso", a.getValue(), atts);
			// codDoc ,Numerico2
			addHeaderElement(mmDoc, "codDoc", dt.get_ValueAsString("SRI_ShortDocType"), atts);
			// estab ,Numerico3
			addHeaderElement(mmDoc, "estab", oi.get_ValueAsString("SRI_OrgCode"), atts);
			// ptoEmi ,Numerico3
			addHeaderElement(mmDoc, "ptoEmi", oi.get_ValueAsString("SRI_StoreCode"), atts);
			//secuencial ,Numerico9
			addHeaderElement(mmDoc, "secuencial", (LEC_FE_Utils.fillString(9 - (LEC_FE_Utils.cutString(getDocumentNo().substring(getDocumentNo().lastIndexOf('-')+1), 9)).length(), '0'))
				+ LEC_FE_Utils.cutString(getDocumentNo().substring(8),9), atts);
			// dirMatriz ,Alfanumerico Max 300
			addHeaderElement(mmDoc, "dirMatriz", "TODO", atts);
		mmDoc.endElement("","","infoTributaria");
		
		// Comprador
		mmDoc.startElement("","","infoFactura",atts);
			// Fecha8 ddmmaaaa
			addHeaderElement(mmDoc, "fechaEmision", LEC_FE_Utils.getDate(getDateInvoiced(),10), atts);
			// Alfanumerico Max 300
			addHeaderElement(mmDoc, "dirEstablecimiento", "TODO", atts);
			// Numerico3-5
			addHeaderElement(mmDoc, "contribuyenteEspecial", oi.get_ValueAsString("SRI_TaxPayerCode"), atts);
			// Texto2
			addHeaderElement(mmDoc, "obligadoContabilidad", m_obligadocontabilidad, atts);
			// Numerico2
			addHeaderElement(mmDoc, "tipoIdentificacionComprador", (LEC_FE_Utils.fillString(2 - (LEC_FE_Utils.cutString(tt.getLCO_TaxCodeDian(), 2)).length(), '0'))
					+ LEC_FE_Utils.cutString(tt.getLCO_TaxCodeDian(),2), atts);	// LCO_TaxCodeDIAN ?
			// Numerico15 -- Incluye guiones
			addHeaderElement(mmDoc, "guiaRemision", "TODO", atts);
			// Alfanumerico Max 300
			addHeaderElement(mmDoc, "razonSocialComprador", m_razonsocial, atts);
			// Numerico13
			addHeaderElement(mmDoc, "identificacionComprador", (LEC_FE_Utils.fillString(13 - (LEC_FE_Utils.cutString(bp.getTaxID(), 13)).length(), '0'))
					+ LEC_FE_Utils.cutString(bp.getTaxID(),13), atts);
			// Numerico Max 14
			addHeaderElement(mmDoc, "totalSinImpuestos", this.getTotalLines().toString(), atts);
			// Numerico MAx 14
			addHeaderElement(mmDoc, "totalDescuento", Env.ZERO.toString(), atts);
		mmDoc.endElement("","","infoFactura");
		
		// Detalles
		mmDoc.startElement("","","detalles",atts);
		
		StringBuffer sql = new StringBuffer(
	            "SELECT i.C_Invoice_ID, p.value, p.name, COALESCE(p.description, il.description), qtyinvoiced, priceactual, 0.0::numeric, linenetamt "
	            + "FROM C_Invoice i "
	            + "JOIN C_InvoiceLine il ON il.C_Invoice_ID = i.C_Invoice_ID "
	            + "JOIN M_Product p ON p.M_Product_ID = il.M_Product_ID "
	            + "WHERE i.C_Invoice_ID=? "
	            + "ORDER BY il.line");
		
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, getC_Invoice_ID());
			ResultSet rs = pstmt.executeQuery();
			//
			
			while (rs.next())
			{
				mmDoc.startElement("","","detalle",atts);
				
				// Alfanumerico MAx 25
				addHeaderElement(mmDoc, "codigoPrincipal", rs.getString(2), atts);
				// Alfanumerico MAx 25
				addHeaderElement(mmDoc, "codigoAuxiliar", rs.getString(3), atts);
				// Alfanumerico Max 300
				addHeaderElement(mmDoc, "descripcion", rs.getString(4), atts);
				// Numerico Max 14
				addHeaderElement(mmDoc, "cantidad", rs.getBigDecimal(5).toString(), atts);
				// Numerico Max 14
				addHeaderElement(mmDoc, "precioUnitario", rs.getBigDecimal(6).toString(), atts);
				// Numerico Max 14
				addHeaderElement(mmDoc, "descuento", rs.getBigDecimal(7).toString(), atts);
				// Numerico Max 14
				addHeaderElement(mmDoc, "precioTotalSinImpuesto", rs.getBigDecimal(8).toString(), atts);
				
				mmDoc.startElement("","","detallesAdicionales",atts);
					addHeaderElement(mmDoc, "detAdicional", "TODO", atts);
				mmDoc.endElement("","","detallesAdicionales");
				
				//
				mmDoc.startElement("","","impuestos",atts);
					mmDoc.startElement("","","impuesto",atts);
						addHeaderElement(mmDoc, "codigo", "X", atts);
					mmDoc.endElement("","","impuesto");
				mmDoc.endElement("","","impuestos");
				
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
		
		mmDoc.startElement("","","infoAdicional",atts);
			addHeaderElement(mmDoc, "infoAdicional", "TODO", atts);
		mmDoc.endElement("","","infoAdicional");
	
		mmDoc.startElement("", "", "!-- INICIO DE LA FIRMA DIGITAL --", atts);
			// TODO Firmar XML con Certificado
			addHeaderElement(mmDoc, "Signature", "TODO", atts);
		mmDoc.endElement("","","!-- FIN DE LA FIRMA DIGITAL --");
		
		mmDoc.endElement("","","factura");
		
		mmDoc.endDocument();
	
		if (mmDocStream != null) {
			try {
				mmDocStream.close();
			} catch (Exception e2) {}
		}
	
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
				String renamed = folder+File.separator+entry.getName().substring(0,entry.getName().length()-4 )+"_old_"+ LEC_FE_Utils.getDate(null, 19) + ".xml";
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
			msg = "No se pudo craer XML - " + e.getMessage();
			log.severe(msg);
			throw new AdempiereException(msg);
		}
		
		log.warning("@SRI_FileGenerated@ -> " + file_name);
		
		// TODO Enviar Solicitud Autorizacion SRI
		// TODO Procesar Respuesta SRI
		// TODO Enviar Email Cliente
		
		//return null;
		return msg;
	
	} // lecfeinv_SriExportInvoiceXML100
	
	public void addHeaderElement(TransformerHandler mmDoc, String att, String value, AttributesImpl atts) throws Exception {
		if (att != null) {
			mmDoc.startElement("","",att,atts);
			mmDoc.characters(value.toCharArray(),0,value.toCharArray().length);
			mmDoc.endElement("","",att);
		} else {
			throw new AdempiereUserError(att + " empty");
		}
	}
	

}	// LEC_FE_MInvoice