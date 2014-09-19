package org.globalqss.process;

import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.model.MUser;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.globalqss.model.LEC_FE_MRetencion;
import org.globalqss.model.LEC_FE_Validator;

public class SRIGenerateRetencion extends SvrProcess {

	int  m_C_Invoice_ID = 0;
	
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();
		
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("C_Invoice_ID"))
				m_C_Invoice_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		
		if (m_C_Invoice_ID == 0)
			m_C_Invoice_ID = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		
		String msg = null;
		
		MInvoice inv =new MInvoice(getCtx(), m_C_Invoice_ID, get_TrxName());
		
		MUser user = new MUser(inv.getCtx(), inv.getAD_User_ID(), inv.get_TrxName());
		
		if (! LEC_FE_Validator.valideUserMail (user)) {
			msg = "@RequestActionEMailNoTo@";
			return msg;
		}
		
		try
		{
			
			LEC_FE_MRetencion lecfeinvret = new LEC_FE_MRetencion(inv.getCtx(), inv.getC_Invoice_ID(), inv.get_TrxName());
			
			// !isSOTrx()
			msg = lecfeinvret.lecfeinvret_SriExportRetencionXML100();
			
			if (msg != null)
        		throw new AdempiereException(msg);
		
		}
		catch (Exception e)
		{
			msg = "No se pudo obtener autorizacion - " + e.getMessage();
			log.severe(msg);
			throw new AdempiereException(msg);
		}
		
		return msg;
	}

}
