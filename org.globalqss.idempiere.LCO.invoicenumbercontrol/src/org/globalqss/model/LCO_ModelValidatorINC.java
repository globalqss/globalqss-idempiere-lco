package org.globalqss.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.adempiere.base.event.AbstractEventHandler;
import org.adempiere.base.event.IEventManager;
import org.adempiere.base.event.IEventTopics;
import org.adempiere.base.event.LoginEventData;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MRole;
import org.compiere.model.MSysConfig;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.osgi.service.event.Event;

/**
 *	Validator or Localization Colombia (Invoice Number Control)
 *	
 *  @author Carlos Augusto Sanchez - globalqss - Quality Systems & Solutions - http://globalqss.com 
 */
/**
 * @author Carlos Augusto Sanchez
 *
 */
public class LCO_ModelValidatorINC extends AbstractEventHandler
{

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(LCO_ModelValidatorINC.class);
	
	/**
	 *	Initialize Validation
	 */
	@Override
	protected void initialize() {
		
		//	Documents to be monitored
		registerTableEvent(IEventTopics.DOC_BEFORE_COMPLETE, MInvoice.Table_Name);

		// Tables to be monitored
		registerTableEvent(IEventTopics.PO_BEFORE_CHANGE, X_LCO_PrintedFormControl.Table_Name);
		registerTableEvent(IEventTopics.PO_BEFORE_NEW, X_LCO_PrintedFormControl.Table_Name);
		registerTableEvent(IEventTopics.PO_BEFORE_CHANGE, X_LCO_InvoiceWithholding.Table_Name);
		registerTableEvent(IEventTopics.PO_BEFORE_NEW, X_LCO_InvoiceWithholding.Table_Name);
		
		registerEvent(IEventTopics.AFTER_LOGIN);
		
	}//	initialize
	
    /**
     *	Model Change of a monitored Table or Document
     *  @param event
     *	@exception Exception if the recipient wishes the change to be not accept.
     */
	@Override
	protected void doHandleEvent(Event event) {
		
		String type = event.getTopic();
		
		if (type.equals(IEventTopics.AFTER_LOGIN)) {
			log.info("Type: " + type);
			// on login set context variable ##LCO_CPF_USE_ON_WITHHOLDING
			LoginEventData loginData = (LoginEventData) event.getProperty(IEventManager.EVENT_DATA);
		    boolean isPrintedFormControlActive = MSysConfig.getBooleanValue("QSSLCO_IsPrintedFormControlActiveInvoice", true,loginData.getAD_Client_ID());
			Env.setContext(Env.getCtx(), "#LCO_CPF_USE_ON_INVOICE", isPrintedFormControlActive);
			
			isPrintedFormControlActive = MSysConfig.getBooleanValue("QSSLEC_IsPrintedFormControlActiveWithholding", true,loginData.getAD_Client_ID());
			Env.setContext(Env.getCtx(), "#LEC_CPF_USE_ON_WITHHOLDIGS", isPrintedFormControlActive);
			return;
		}
		
		PO po = getPO(event);
		log.info(po.get_TableName() + " Type: "+type);
		String msg;
			
		if (po.get_TableName().equals(X_LCO_PrintedFormControl.Table_Name) 
				&& ( type.equals(IEventTopics.PO_BEFORE_CHANGE) 
						|| type.equals(IEventTopics.PO_BEFORE_NEW)))
		{
			
			X_LCO_PrintedFormControl cpf = (X_LCO_PrintedFormControl) po;
		    
			msg = validatePrintedFormCreation(cpf);
			if (msg != null)
				throw new RuntimeException(msg);
		}
		
		if (po.get_TableName().equals(MInvoice.Table_Name) && type.equals(IEventTopics.DOC_BEFORE_COMPLETE)) {
			
			msg = validatePrintedFormOnInvoice((MInvoice) po);
			if (msg != null)
				throw new RuntimeException(msg);
		}
		

		if (! MSysConfig.getBooleanValue("LCO_USE_WITHHOLDINGS", true, Env.getAD_Client_ID(Env.getCtx())))
			return;
		
		if (po.get_TableName().equals(X_LCO_InvoiceWithholding.Table_Name) 
				&& ( type.equals(IEventTopics.PO_BEFORE_CHANGE) 
						|| type.equals(IEventTopics.PO_BEFORE_NEW))){
			
			X_LCO_InvoiceWithholding invwhi = (X_LCO_InvoiceWithholding) po;
			msg = validatePrintedFormOnWithholding(invwhi);
			if (msg != null)
				throw new RuntimeException(msg);
		}
	}//	doHandleEvent
	
	/**
	 * It validates printed form on withholding when saving or changing it
	 * @param invoiceWithholding
	 * @return
	 */
    private String validatePrintedFormOnWithholding(X_LCO_InvoiceWithholding invoiceWithholding){
    	
    	String msg = null;
		
    	final boolean isPrintedFormControlActive = MSysConfig.getBooleanValue("QSSLEC_IsPrintedFormControlActiveWithholding", true, MRole.getDefault().getAD_Client_ID());
		boolean isActive = invoiceWithholding.isActive();
		if(!isPrintedFormControlActive || !isActive)
			return msg;
    	
		final boolean isPrefixMandatory = MSysConfig.getBooleanValue("QSSLCO_IsPrefixMandatory", true, MRole.getDefault().getAD_Client_ID());
		final int prefixLengthExpected = Integer.valueOf(MSysConfig.getValue("QSSLCO_PrefixLength", null, MRole.getDefault().getAD_Client_ID()));
		
	    final int docNoLengthExpected = Integer.valueOf(MSysConfig.getValue("QSSLCO_DocNoLength", null, MRole.getDefault().getAD_Client_ID()));
		final int docNoLengthOptionalExpected = Integer.valueOf(MSysConfig.getValue("QSSLEC_DocNoLengthOptional", null, MRole.getDefault().getAD_Client_ID()));
		final int docNoLengthEntered = invoiceWithholding.getDoc().getDocumentNo().length();
		
		boolean docNoLengthOptinalActive = MSysConfig.getBooleanValue("QSSLEC_DocNoLengthOptinalActive", true, MRole.getDefault().getAD_Client_ID());
		
		MInvoice m_invoice = new MInvoice (invoiceWithholding.getCtx(), invoiceWithholding.getC_Invoice_ID(), invoiceWithholding.get_TrxName());
		if (docNoLengthEntered == docNoLengthExpected || (docNoLengthEntered == docNoLengthOptionalExpected && docNoLengthOptinalActive)){
			
			boolean isSOTrx = m_invoice.isSOTrx();
			String sqlCount = "";
			String sqlInfo = "";
			boolean isWithholding = true;
			
			if (isSOTrx){
				sqlCount = getSqlToValidatePrintedForm(isSOTrx, isWithholding, true, true, isPrefixMandatory).toString();
				sqlInfo =  getSqlToValidatePrintedForm(isSOTrx, isWithholding, true, false, isPrefixMandatory).toString();
			}
			else{
				sqlCount = getSqlToValidatePrintedForm(isSOTrx, isWithholding, true, true, isPrefixMandatory).toString();
				sqlInfo =  getSqlToValidatePrintedForm(isSOTrx, isWithholding, true, false, isPrefixMandatory).toString();
		    }
				PreparedStatement pstmt = DB.prepareStatement(sqlCount,invoiceWithholding.get_TrxName());
				ResultSet rs = null;
				try {
					int index=1;
					if(!isWithholding)
						pstmt.setInt(index++, m_invoice.getC_DocTypeTarget_ID());
					
					if (isSOTrx)
						pstmt.setInt(index++, invoiceWithholding.getAD_Org_ID());
		            else
		            	pstmt.setInt(index++, m_invoice.getC_BPartner_ID());
	
					if(isPrefixMandatory) 
						pstmt.setString(index++, invoiceWithholding.getDoc().getDocumentNo().toString().substring(0, prefixLengthExpected));
					
					pstmt.setInt(index++, Integer.valueOf(invoiceWithholding.getDoc().getDocumentNo().toString().substring(prefixLengthExpected, docNoLengthEntered)));
					pstmt.setTimestamp(index++, invoiceWithholding.getDateAcct());
			
					log.fine(sqlCount);
					rs = pstmt.executeQuery();
					rs.next();
					if (rs.getInt(1) != 1)
						return Msg.getMsg(invoiceWithholding.getCtx(), "LCO_NoMatchWithPrintedForms");
					else{
						pstmt = DB.prepareStatement(sqlInfo,invoiceWithholding.get_TrxName());
						
						index=1;
						if (!isWithholding)
							pstmt.setInt(index++, m_invoice.getC_DocTypeTarget_ID());
						
						if (isSOTrx)
							pstmt.setInt(index++, invoiceWithholding.getAD_Org_ID());
			            else
			            	pstmt.setInt(index++, m_invoice.getC_BPartner_ID());

						if(isPrefixMandatory) 
							pstmt.setString(index++, invoiceWithholding.getDoc().getDocumentNo().substring(0, prefixLengthExpected)); //serial
						
						pstmt.setInt(index++, Integer.valueOf(invoiceWithholding.getDoc().getDocumentNo().substring(prefixLengthExpected, docNoLengthEntered)));//sequence
						pstmt.setTimestamp(index++,  invoiceWithholding.getDateAcct());//date
						
						rs = null;
						log.fine(sqlInfo);
						rs = pstmt.executeQuery();
					    rs.next();

					    String sqlUpdateLCO_PrintedFormControlID = getSqlToUpdateWithholdingWithPrintedFormInfo(1).toString();
						pstmt = DB.prepareStatement(sqlUpdateLCO_PrintedFormControlID,invoiceWithholding.get_TrxName());
						
						index=1;
						pstmt.setInt(index++, rs.getInt(1));
						pstmt.setInt(index++, invoiceWithholding.getC_Invoice_ID());
						pstmt.setInt(index++, invoiceWithholding.getLCO_InvoiceWithholding_ID());
						
						log.fine(sqlUpdateLCO_PrintedFormControlID);
						
						int update = pstmt.executeUpdate();
						if (update == -1)
							return Msg.getMsg(invoiceWithholding.getCtx(), "LCO_ErrorUpdatingPrintedFormControlOnWithholding" +"-"+ invoiceWithholding.getDoc().getDocumentNo());
						
						String sqlUpdateDocNo =	getSqlToUpdateWithholdingWithPrintedFormInfo(2).toString();
						pstmt = DB.prepareStatement(sqlUpdateDocNo,invoiceWithholding.get_TrxName());
						
						index=1;
						pstmt.setInt(index++, rs.getInt(1));
						pstmt.setInt(index++, invoiceWithholding.getC_Invoice_ID());
						pstmt.setInt(index++, invoiceWithholding.getLCO_InvoiceWithholding_ID());
						
						log.fine(sqlUpdateDocNo);
						
						update = pstmt.executeUpdate();
						if (update == -1)
							return Msg.getMsg(invoiceWithholding.getCtx(), "LCO_ErrorUpdatingPrintedFormControlOnWithholding" +"-"+ invoiceWithholding.getDoc().getDocumentNo());
						
					}
				 }catch (SQLException e) {
				    log.log(Level.SEVERE, Msg.getMsg(invoiceWithholding.getCtx(), "LCO_ValidatePrintedFormOnWithholdingError"), e);
					msg = e.getMessage() + Msg.getMsg(invoiceWithholding.getCtx(), "LCO_ValidatePrintedFormOnWithholdingError") + invoiceWithholding.get_ID();
				 }finally {
					DB.close(rs, pstmt);
					rs = null;
					pstmt = null;
			 }
		}
		else return Msg.getMsg(invoiceWithholding.getCtx(), "LCO_DocumentLengthInvalid");
    	
    	return msg;
    }
    
    /**
     * It validates printed form on invoices when it is being completed
     * @param m_invoice
     * @return
     */
	private String validatePrintedFormOnInvoice(MInvoice m_invoice) {
		
		String msg = null;
		final boolean isPrintedFormControlActive = MSysConfig.getBooleanValue("QSSLCO_IsPrintedFormControlActiveInvoice", true, MRole.getDefault().getAD_Client_ID());
		if (!isPrintedFormControlActive)
			return msg;
		
		if (m_invoice.getC_Invoice_ID() == 0)
			return null;
		
		if(!m_invoice.getDocStatus().equals("IP"))
			return null;
		
		if(!m_invoice.getDocAction().equals("CO"))
			return null;
	    
		final boolean isPrefixMandatory = MSysConfig.getBooleanValue("QSSLCO_IsPrefixMandatory", true, MRole.getDefault().getAD_Client_ID());
		final int prefixLengthExpected = Integer.valueOf(MSysConfig.getValue("QSSLCO_PrefixLength", null, MRole.getDefault().getAD_Client_ID()));
		
	    final int docNoLengthExpected = Integer.valueOf(MSysConfig.getValue("QSSLCO_DocNoLength", null, MRole.getDefault().getAD_Client_ID()));
		final int docNoLengthOptionalExpected = Integer.valueOf(MSysConfig.getValue("QSSLEC_DocNoLengthOptional", null, MRole.getDefault().getAD_Client_ID()));
		final int docNoLengthEntered = m_invoice.getDocumentNo().length();
		
		boolean docNoLengthOptinalActive = MSysConfig.getBooleanValue("QSSLEC_DocNoLengthOptinalActive", true, MRole.getDefault().getAD_Client_ID());
			
		if (docNoLengthEntered == docNoLengthExpected || (docNoLengthEntered == docNoLengthOptionalExpected && docNoLengthOptinalActive)){
		
			MDocType dt = new MDocType(m_invoice.getCtx(), m_invoice.getC_DocTypeTarget_ID(), m_invoice.get_TrxName());
			boolean isSOTrx = dt.get_ValueAsBoolean("issotrx");
			
			String sqlCount = "";
			String sqlInfo = "";
			boolean isWithholding = false;
			if (isSOTrx){
				sqlCount = getSqlToValidatePrintedForm(isSOTrx, isWithholding, true, true, isPrefixMandatory).toString();
				sqlInfo =  getSqlToValidatePrintedForm(isSOTrx, isWithholding, true, false, isPrefixMandatory).toString();
			}
			else{
				
				sqlCount = getSqlToValidatePrintedForm(isSOTrx, isWithholding, true, true, isPrefixMandatory).toString();
				sqlInfo =  getSqlToValidatePrintedForm(isSOTrx, isWithholding, true, false, isPrefixMandatory).toString();
		    }
				PreparedStatement pstmt = DB.prepareStatement(sqlCount,m_invoice.get_TrxName());
				ResultSet rs = null;
				try {
					int index=1;
					if(!isWithholding)
						pstmt.setInt(index++, m_invoice.getC_DocTypeTarget_ID());

					
					if (isSOTrx)
						pstmt.setInt(index++, m_invoice.getAD_Org_ID());
		            else
		            	pstmt.setInt(index++, m_invoice.getC_BPartner_ID());

					if(isPrefixMandatory) 
						pstmt.setString(index++, m_invoice.getDocumentNo().toString().substring(0, prefixLengthExpected));
					
					pstmt.setInt(index++, Integer.valueOf(m_invoice.getDocumentNo().toString().substring(prefixLengthExpected, docNoLengthEntered)));
					pstmt.setTimestamp(index++, m_invoice.getDateInvoiced());
			
					log.fine(sqlCount);
					rs = pstmt.executeQuery();
					rs.next();
					if (rs.getInt(1) != 1)
						return Msg.getMsg(m_invoice.getCtx(), "LCO_NoMatchWithPrintedForms");
					else{
						pstmt = DB.prepareStatement(sqlInfo,m_invoice.get_TrxName());
						
						index=1;
						if(!isWithholding)
							pstmt.setInt(index++, m_invoice.getC_DocTypeTarget_ID());
						
						if (isSOTrx)
							pstmt.setInt(index++, m_invoice.getAD_Org_ID());
			            else
			            	pstmt.setInt(index++, m_invoice.getC_BPartner_ID());

						if(isPrefixMandatory) 
							pstmt.setString(index++, m_invoice.getDocumentNo().toString().substring(0, prefixLengthExpected)); //serial
						
						pstmt.setInt(index++, Integer.valueOf(m_invoice.getDocumentNo().toString().substring(prefixLengthExpected, docNoLengthEntered)));//sequence
						pstmt.setTimestamp(index++, m_invoice.getDateInvoiced());//date
						
						rs = null;
						log.fine(sqlInfo);
						rs = pstmt.executeQuery();
					    rs.next();
					   
						String sqlUpdate = getSqlToUpdateInvoiceWithPrintedFormInfo().toString();
						pstmt = DB.prepareStatement(sqlUpdate,m_invoice.get_TrxName());
						
						index=1;
						pstmt.setInt(index++, rs.getInt(1));
						pstmt.setInt(index++, m_invoice.getC_Invoice_ID());
						
						log.fine(sqlUpdate);
						
						int update = pstmt.executeUpdate();
						if (update == -1)
							return Msg.getMsg(m_invoice.getCtx(), "LCO_ErrorUpdatingPrintedFormControlOnInvoice" +"-"+ m_invoice.getDocumentNo());
						
					}
				 }catch (SQLException e) {
				    log.log(Level.SEVERE, Msg.getMsg(m_invoice.getCtx(), "LCO_ValidatePrintedFormOnInvoiceError"), e);
					msg = e.getMessage() + Msg.getMsg(m_invoice.getCtx(), "LCO_ValidatePrintedFormOnInvoiceError") + m_invoice.get_ID();
				 }finally {
					DB.close(rs, pstmt);
					rs = null;
					pstmt = null;
			 }
		}
		else return Msg.getMsg(m_invoice.getCtx(), "LCO_DocumentLengthInvalid");
		return msg;
	}
	
	/**
	 * Returns sql for updating invoice
	 * @return
	 */
	
	private StringBuffer getSqlToUpdateInvoiceWithPrintedFormInfo(){
		
		StringBuffer sql = new StringBuffer("UPDATE C_Invoice ");
					 sql.append("SET LCO_PrintedFormControl_ID = ? ");
					 sql.append("WHERE C_Invoice_ID = ? ");
				
		return sql;
	}
	
	/**
	 * Returns sql for updating withholding 
	 * @param query
	 * @return
	 */
	private StringBuffer getSqlToUpdateWithholdingWithPrintedFormInfo(int query){
		
		StringBuffer sql = new StringBuffer("UPDATE LCO_InvoiceWithholding ");
		
				if(query == 1)
					 sql.append("SET LCO_PrintedFormControl_ID = ? ");
				else sql.append("SET DocumentNo = ? ");
		
		             sql.append("WHERE C_Invoice_ID = ? ");
		             sql.append("WHERE AND LCO_InvoiceWithholding_ID = ? ");
			 
		return sql;
	}
		
    /**
     * This method validates creation of printed forms on the organization windows
     * @param cpf
     * @return
     */
	private String validatePrintedFormCreation(X_LCO_PrintedFormControl cpf) {
		
		String msg = null;
		final boolean isPrefixMandatory = MSysConfig.getBooleanValue("QSSLCO_IsPrefixMandatory", true, MRole.getDefault().getAD_Client_ID());
		
		final int prefixLengthExpected = Integer.valueOf(MSysConfig.getValue("QSSLCO_PrefixLength", null, MRole.getDefault().getAD_Client_ID()));
		int prefixLengthEntered = 0;
		if (cpf.getPrefix() !=  null)  
			prefixLengthEntered = cpf.getPrefix().toString().length();
		
		if (cpf.getC_DocTypeTarget_ID() <= 0 && !cpf.isWithholding()) 
			return Msg.getMsg(cpf.getCtx(), "LCO_TypeOfPrintedFormControlRequired");;
		
		if (prefixLengthEntered != prefixLengthExpected && isPrefixMandatory)
			return Msg.getMsg(cpf.getCtx(), "LCO_PrefixLengthInadequate");
		
		if (cpf.is_ValueChanged("InitialSequence") || cpf.is_ValueChanged("FinalSequence") || (cpf.is_ValueChanged("IsActive") && cpf.isActive())){
			
			int initialSequence = Integer.parseInt(cpf.getInitialSequence());
			int finalSequence = Integer.parseInt(cpf.getFinalSequence());
			if (finalSequence <= initialSequence)
				return Msg.getMsg(cpf.getCtx(), "LCO_InvalidSequences");
			
			int comparisonDates = cpf.getValidFrom().compareTo(cpf.getValidUntil());
			if (comparisonDates >= 0)
				return Msg.getMsg(cpf.getCtx(), "LCO_InvalidDates");
			
			MDocType dt = new MDocType(cpf.getCtx(), cpf.getC_DocTypeTarget_ID(), cpf.get_TrxName());
			
			boolean isSOTrx = dt.get_ValueAsBoolean("IsSOTrx");
			String sql =getSqlToValidatePrintedForm(isSOTrx, cpf.isWithholding(), false, true, isPrefixMandatory).toString();
			
			PreparedStatement pstmt = DB.prepareStatement(sql,cpf.get_TrxName());
			ResultSet rs = null;
			try {
				int index=1;
				if(!cpf.isWithholding())
					pstmt.setInt(index++,cpf.getC_DocTypeTarget_ID());
				
				if (isSOTrx)
					pstmt.setInt(index++, cpf.getAD_Org_ID());
	             else
	            	pstmt.setInt(index++, cpf.getC_BPartner_ID());
				
				if(isPrefixMandatory) 
					pstmt.setString(index++, cpf.getPrefix());
				
				pstmt.setInt(index++, Integer.valueOf(cpf.getInitialSequence()));
				pstmt.setInt(index++, Integer.valueOf(cpf.getFinalSequence()));
				
				log.fine(sql);
				rs = pstmt.executeQuery();
				rs.next();
				if (rs.getInt(1) > 0) {
					msg = Msg.getMsg(cpf.getCtx(), "LCO_SequenceAlreadyExists");
				return msg;
				}
			 }catch (SQLException e) {
				log.log(Level.SEVERE, Msg.getMsg(cpf.getCtx(), "LCO_ValidatePrintedFormCreationError"), e);
				msg = e.getMessage() + Msg.getMsg(cpf.getCtx(), "LCO_ValidatePrintedFormCreationError") + cpf.get_ID();
			 }finally {
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
		 }
	   }
		return msg;
	}
	
	/**
	 * This method creates the sql sentence for checking printed forms on invoices and withholdings
	 * @param isSOTrx 
	 * @param isWithholding
	 * @param fromInvoiceOrWithholding
	 * @param isHeaderCount
	 * @param isPrefixMandatory
	 * @return
	 */
	private StringBuffer getSqlToValidatePrintedForm(boolean isSOTrx, boolean isWithholding, boolean fromInvoiceOrWithholding, boolean isHeaderCount, boolean isPrefixMandatory){
		StringBuffer sql = null;
		if(!isHeaderCount){
			sql = new StringBuffer("SELECT LCO_PrintedFormControl_ID ");
		 }
		else{
			sql = new StringBuffer("SELECT count(LCO_PrintedFormControl_ID) as count ");
  		}
		             sql.append("FROM LCO_PrintedFormControl x ");
		             if (!isWithholding)
		            	 sql.append(", c_doctype dt ");
		             sql.append("WHERE x.isactive='Y' ");
		             
		             if (!isWithholding){
		            	 sql.append("AND dt.c_doctype_id = x.c_doctypetarget_id ");
		            	 sql.append("AND x.c_doctypetarget_id = ? "); //parameter
		            	 if (isSOTrx)
		            		 sql.append("AND dt.issotrx = 'Y' ");
		            	 else
		            		 sql.append("AND dt.issotrx = 'N' ");
		                 sql.append("AND x.isWithholding = 'N' ");
		             }else{
		            	 sql.append("AND x.isWithholding = 'Y' ");
		             }
		             
		             if (isSOTrx)
		            	 sql.append(" AND x.c_bpartner_id IS NULL AND x.ad_org_id=? ");
		             else
		            	 sql.append(" AND x.c_bpartner_id=? ");
		             
		             if (isPrefixMandatory) 
		            	 sql.append("AND x.prefix = ? "); //parameter
		             
		             if (!fromInvoiceOrWithholding){
			             sql.append("AND ((? "); //parameter: Initial Sequence
						 sql.append("BETWEEN x.initialsequence::int and x.finalsequence::int) ");
						 sql.append("OR (? ");    //parameter: Final Sequence
						 sql.append("BETWEEN x.initialsequence::int and x.finalsequence::int)) ");
		             }else{
		            	 sql.append("AND (? "); //parameter: Initial Sequence
						 sql.append("BETWEEN x.initialsequence::int and x.finalsequence::int) ");
		             }
					 if (fromInvoiceOrWithholding){
			             sql.append("AND ? BETWEEN x.validfrom AND x.validuntil ");
					 }

		return sql;
	}

}
