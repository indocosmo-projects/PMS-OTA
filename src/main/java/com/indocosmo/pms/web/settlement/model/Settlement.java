package com.indocosmo.pms.web.settlement.model;


import java.util.Date;

public class Settlement {
	
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private int voucher_no;
	private Date payment_date;
	private int corporate_id;
	private String corporate_name;
	private int payment_type;
	private String payment_mode;
	private String reference_no;
	private Date reference_date;
	private String reference_name;
	private double payment_amount;
	private double allocate_amount;
	private String remarks;
	private String invoice_date;
	private String invoice_no;
	private int days;
	private double invoiceAmount;
	private double paidAmount;
	private double balance;
	private int count;
	
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getVoucher_no() {
		return voucher_no;
	}
	public void setVoucher_no(int voucher_no) {
		this.voucher_no = voucher_no;
	}
	public Date getPayment_date() {
		return payment_date;
	}
	public void setPayment_date(Date payment_date) {
		this.payment_date = payment_date;
	}
	public int getCorporate_id() {
		return corporate_id;
	}
	public void setCorporate_id(int corporate_id) {
		this.corporate_id = corporate_id;
	}
	public String getCorporate_name() {
		return corporate_name;
	}
	public void setCorporate_name(String corporate_name) {
		this.corporate_name = corporate_name;
	}
	public int getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(int payment_type) {
		this.payment_type = payment_type;
	}
	public String getPayment_mode() {
		return payment_mode;
	}
	public void setPayment_mode(String payment_mode) {
		this.payment_mode = payment_mode;
	}
	public String getReference_no() {
		return reference_no;
	}
	public void setReference_no(String reference_no) {
		this.reference_no = reference_no;
	}
	public Date getReference_date() {
		return reference_date;
	}
	public void setReference_date(Date reference_date) {
		this.reference_date = reference_date;
	}
	public String getReference_name() {
		return reference_name;
	}
	public void setReference_name(String reference_name) {
		this.reference_name = reference_name;
	}
	public double getPayment_amount() {
		return payment_amount;
	}
	public void setPayment_amount(double payment_amount) {
		this.payment_amount = payment_amount;
	}
	public double getAllocate_amount() {
		return allocate_amount;
	}
	public void setAllocate_amount(double allocate_amount) {
		this.allocate_amount = allocate_amount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getInvoice_date() {
		return invoice_date;
	}
	public void setInvoice_date(String invoice_date) {
		this.invoice_date = invoice_date;
	}
	public String getInvoice_no() {
		return invoice_no;
	}
	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public double getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
		
}