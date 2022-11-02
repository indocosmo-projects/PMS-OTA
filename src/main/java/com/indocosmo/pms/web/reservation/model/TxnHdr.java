package com.indocosmo.pms.web.reservation.model;

import java.math.BigDecimal;
import java.util.Date;

public class TxnHdr {

	private int txnNo;
	private int folioNo;
	private int folioBindNo;
	private int txnSource;
	private Date txnDate;
	private Date txnTime;
	private int accMstId;
	private String accMstCode;
	private int taxId;
	private String taxCode;
	private boolean isIncludetax;
	private BigDecimal tax1Pc;
	private BigDecimal tax2Pc;
	private BigDecimal tax3Pc;
	private BigDecimal tax4Pc;

	private BigDecimal amount;
	private BigDecimal netAmount;

	private BigDecimal tax1Amount;
	private BigDecimal tax2Amount;
	private BigDecimal tax3Amount;
	private BigDecimal tax4Amount;

	private int paymentMode;
	private int txnStatus;
	private int sourceFolioNo;
	private int destFolioNo;
	private String remarks;
	private int userId;
	private Date lastUpdates;

	public int getTxnNo() {
		return txnNo;
	}

	public void setTxnNo(int txnNo) {
		this.txnNo = txnNo;
	}

	public int getFolioNo() {
		return folioNo;
	}

	public void setFolioNo(int folioNo) {
		this.folioNo = folioNo;
	}

	public int getFolioBindNo() {
		return folioBindNo;
	}

	public void setFolioBindNo(int folioBindNo) {
		this.folioBindNo = folioBindNo;
	}

	public int getTxnSource() {
		return txnSource;
	}

	public void setTxnSource(int txnSource) {
		this.txnSource = txnSource;
	}

	public Date getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}

	public Date getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(Date txnTime) {
		this.txnTime = txnTime;
	}

	public int getAccMstId() {
		return accMstId;
	}

	public void setAccMstId(int accMstId) {
		this.accMstId = accMstId;
	}

	public String getAccMstCode() {
		return accMstCode;
	}

	public void setAccMstCode(String accMstCode) {
		this.accMstCode = accMstCode;
	}

	public int getTaxId() {
		return taxId;
	}

	public void setTaxId(int taxId) {
		this.taxId = taxId;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public boolean isIncludetax() {
		return isIncludetax;
	}

	public void setIncludetax(boolean isIncludetax) {
		this.isIncludetax = isIncludetax;
	}

	public BigDecimal getTax1Pc() {
		return tax1Pc;
	}

	public void setTax1Pc(BigDecimal tax1Pc) {
		this.tax1Pc = tax1Pc;
	}

	public BigDecimal getTax2Pc() {
		return tax2Pc;
	}

	public void setTax2Pc(BigDecimal tax2Pc) {
		this.tax2Pc = tax2Pc;
	}

	public BigDecimal getTax3Pc() {
		return tax3Pc;
	}

	public void setTax3Pc(BigDecimal tax3Pc) {
		this.tax3Pc = tax3Pc;
	}

	public BigDecimal getTax4Pc() {
		return tax4Pc;
	}

	public void setTax4Pc(BigDecimal tax4Pc) {
		this.tax4Pc = tax4Pc;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public BigDecimal getTax1Amount() {
		return tax1Amount;
	}

	public void setTax1Amount(BigDecimal tax1Amount) {
		this.tax1Amount = tax1Amount;
	}

	public BigDecimal getTax2Amount() {
		return tax2Amount;
	}

	public void setTax2Amount(BigDecimal tax2Amount) {
		this.tax2Amount = tax2Amount;
	}

	public BigDecimal getTax3Amount() {
		return tax3Amount;
	}

	public void setTax3Amount(BigDecimal tax3Amount) {
		this.tax3Amount = tax3Amount;
	}

	public BigDecimal getTax4Amount() {
		return tax4Amount;
	}

	public void setTax4Amount(BigDecimal tax4Amount) {
		this.tax4Amount = tax4Amount;
	}

	public int getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(int paymentMode) {
		this.paymentMode = paymentMode;
	}

	public int getTxnStatus() {
		return txnStatus;
	}

	public void setTxnStatus(int txnStatus) {
		this.txnStatus = txnStatus;
	}

	public int getSourceFolioNo() {
		return sourceFolioNo;
	}

	public void setSourceFolioNo(int sourceFolioNo) {
		this.sourceFolioNo = sourceFolioNo;
	}

	public int getDestFolioNo() {
		return destFolioNo;
	}

	public void setDestFolioNo(int destFolioNo) {
		this.destFolioNo = destFolioNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getLastUpdates() {
		return lastUpdates;
	}

	public void setLastUpdates(Date lastUpdates) {
		this.lastUpdates = lastUpdates;
	}

}
