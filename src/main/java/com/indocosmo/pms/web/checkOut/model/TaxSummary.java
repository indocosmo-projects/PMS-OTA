package com.indocosmo.pms.web.checkOut.model;

public class TaxSummary {
	private int taxId;
	private String taxCode;
	private String taxIndicator;
	private double amount;
	private double tax1Amount;
	private double tax2Amount;
	private double tax3Amount;
	private double tax4Amount;
	private double taxAmount;
	private double taxPc;

	public int getTaxId() {
		return taxId;
	}

	public void setTaxId(int taxId) {
		this.taxId = taxId;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public String getTaxIndicator() {
		return taxIndicator;
	}

	public void setTaxIndicator(String taxIndicator) {
		this.taxIndicator = taxIndicator;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public double getTaxPc() {
		return taxPc;
	}

	public void setTaxPc(double taxPc) {
		this.taxPc = taxPc;
	}

	public double getTax1Amount() {
		return tax1Amount;
	}

	public void setTax1Amount(double tax1Amount) {
		this.tax1Amount = tax1Amount;
	}

	public double getTax2Amount() {
		return tax2Amount;
	}

	public void setTax2Amount(double tax2Amount) {
		this.tax2Amount = tax2Amount;
	}

	public double getTax3Amount() {
		return tax3Amount;
	}

	public void setTax3Amount(double tax3Amount) {
		this.tax3Amount = tax3Amount;
	}

	public double getTax4Amount() {
		return tax4Amount;
	}

	public void setTax4Amount(double tax4Amount) {
		this.tax4Amount = tax4Amount;
	}
}
