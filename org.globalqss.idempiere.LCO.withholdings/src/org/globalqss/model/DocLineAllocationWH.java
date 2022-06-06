package org.globalqss.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.acct.Doc;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.model.PO;
import org.compiere.util.DB;

public class DocLineAllocationWH extends org.compiere.acct.DocLine {

	public DocLineAllocationWH(PO po, Doc doc) {
		super(po, doc);
		// TODO Auto-generated constructor stub
	}

	/**
	 * DocLine_Allocation
	 * 
	 * @param line allocation line
	 * @param doc  header
	 */
	public DocLineAllocationWH(MAllocationLine line, Doc doc) {
		super(line, doc);
		m_C_Payment_ID = line.getC_Payment_ID();
		m_C_CashLine_ID = line.getC_CashLine_ID();
		m_C_Invoice_ID = line.getC_Invoice_ID();
		m_C_Order_ID = line.getC_Order_ID();
		// adaxa-pb
		Object obj = line.get_Value("C_Charge_ID");
		if (obj != null)
			m_C_Charge_ID = (Integer) line.get_Value("C_Charge_ID");
		else
			m_C_Charge_ID = 0;
		// end adaxa-pb
		setAmount(line.getAmount());
		m_DiscountAmt = line.getDiscountAmt();
		m_WriteOffAmt = line.getWriteOffAmt();
		m_OverUnderAmt = line.getOverUnderAmt();
		// Get Payment Conversion Rate
		if (line.getC_Payment_ID() != 0) {
			MPayment payment = new MPayment(doc.getCtx(), line.getC_Payment_ID(), doc.getTrxName());
			int C_ConversionType_ID = payment.getC_ConversionType_ID();
			this.setC_ConversionType_ID(C_ConversionType_ID);
		}

		// Get Payment Conversion Rate
		if (line.getC_Invoice_ID() != 0) {
			MInvoice invoice = new MInvoice(doc.getCtx(), line.getC_Invoice_ID(), doc.getTrxName());
			if (invoice.isOverrideCurrencyRate())
				this.setCurrencyRate(invoice.getCurrencyRate());

		}

	} // DocLine_Allocation

	private int m_C_Invoice_ID;
	private int m_C_Payment_ID;
	private int m_C_CashLine_ID;
	private int m_C_Order_ID;
	private int m_C_Charge_ID; // adaxa-pb
	private BigDecimal m_DiscountAmt;
	private BigDecimal m_WriteOffAmt;
	private BigDecimal m_OverUnderAmt;

	/**
	 * Get Invoice C_Currency_ID
	 * 
	 * @return 0 if no invoice -1 if not found
	 */
	public int getInvoiceC_Currency_ID() {
		if (m_C_Invoice_ID == 0)
			return 0;
		String sql = "SELECT C_Currency_ID " + "FROM C_Invoice " + "WHERE C_Invoice_ID=?";
		return DB.getSQLValue(null, sql, m_C_Invoice_ID);

	} // getInvoiceC_Currency_ID

	/**
	 * String Representation
	 * 
	 * @return info
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("DocLine_Allocation[");
		sb.append(get_ID()).append(",Amt=").append(getAmtSource()).append(",Discount=").append(getDiscountAmt())
				.append(",WriteOff=").append(getWriteOffAmt()).append(",OverUnderAmt=").append(getOverUnderAmt())
				.append(" - C_Payment_ID=").append(m_C_Payment_ID).append(",C_CashLine_ID=").append(m_C_CashLine_ID)
				.append(",C_Invoice_ID=").append(m_C_Invoice_ID).append("]");
		return sb.toString();
	} // toString

	/**
	 * @return Returns the c_Order_ID.
	 */
	public int getC_Order_ID() {
		return m_C_Order_ID;
	}

	/**
	 * @return Returns the discountAmt.
	 */
	public BigDecimal getDiscountAmt() {
		return m_DiscountAmt;
	}

	/**
	 * @return Returns the overUnderAmt.
	 */
	public BigDecimal getOverUnderAmt() {
		return m_OverUnderAmt;
	}

	/**
	 * @return Returns the writeOffAmt.
	 */
	public BigDecimal getWriteOffAmt() {
		return m_WriteOffAmt;
	}

	/**
	 * @return Returns the c_CashLine_ID.
	 */
	public int getC_CashLine_ID() {
		return m_C_CashLine_ID;
	}

	/**
	 * @return Returns the c_Invoice_ID.
	 */
	public int getC_Invoice_ID() {
		return m_C_Invoice_ID;
	}

	/**
	 * @return Returns the c_Payment_ID.
	 */
	public int getC_Payment_ID() {
		return m_C_Payment_ID;
	}

	/**
	 * adaxa-pb
	 * 
	 * @return Returns the C_Charge_ID.
	 */
	public int getC_Charge_ID() {
		return m_C_Charge_ID;
	}

	@Override
	public Timestamp getDateConv() {
		if (getC_Payment_ID() > 0) {
			MPayment payment = new MPayment(p_po.getCtx(), getC_Payment_ID(), p_po.get_TrxName());
			return payment.getDateAcct(); // use payment date
		}
		return super.getDateConv();
	}

}
