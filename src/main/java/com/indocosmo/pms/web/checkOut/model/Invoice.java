package com.indocosmo.pms.web.checkOut.model;

import java.util.HashMap;
import java.util.List;
import javax.persistence.Transient;
import com.indocosmo.pms.web.serviceTax.model.serviceTax;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.templates.model.InvoiceTemplate;
import com.indocosmo.pms.web.transaction.model.Transaction;

public class Invoice {

	private String salutation;
	private String name;
	private String guestName;
	private String email;
	private String phone;
	private String address;
	private String state;
	private String nationality;
	private String room_number;
	private String gstno;
	private String rate_description;
	private String checkInDate;
	private String arr_time;
	private String act_depart_time;
	private String checkOutDate;
	private String tax1Code;
	private String tax2Code;
	private String tax3Code;
	private String tax4Code;
	private double tax1;
	private double tax2;
	private double tax3;
	private double tax4;
	private double tax1_pc;
	private double tax2_pc;
	private double tax3_pc;
	private double tax4_pc;
	private List<?> txnList;
	private List<?> txnListInvoice;
	private List<Transaction> txnListSummary;
	private double discount;
	private double deposit;
	private double payments;
	private double serviceCharge;
	private serviceTax seriveTax;
	private double roundAdjust;
	private double balance;
	private double total;
	private double totalWithoutDisc;
	private Integer folioNo;
    private Integer mailType;
    private String reportName;
    private String grcNo;
	public String getGrcNo() {
		return grcNo;
	}

	public void setGrcNo(String grcNo) {
		this.grcNo = grcNo;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	private double tax_total;

	public double getTax_total() {
		return tax_total;
	}

	public Integer getFolioNo() {
		return folioNo;
	}

	public void setFolioNo(Integer folioNo) {
		this.folioNo = folioNo;
	}

	public Integer getMailType() {
		return mailType;
	}

	public void setMailType(Integer mailType) {
		this.mailType = mailType;
	}



	public void setTax_total(double tax_total) {
		this.tax_total = tax_total;
	}

	public double getBase_amount() {
		return base_amount;
	}

	public void setBase_amount(double base_amount) {
		this.base_amount = base_amount;
	}

	public double getTax1_amount() {
		return tax1_amount;
	}

	public void setTax1_amount(double tax1_amount) {
		this.tax1_amount = tax1_amount;
	}

	public double getTax2_amount() {
		return tax2_amount;
	}

	public void setTax2_amount(double tax2_amount) {
		this.tax2_amount = tax2_amount;
	}

	public double getTax3_amount() {
		return tax3_amount;
	}

	public void setTax3_amount(double tax3_amount) {
		this.tax3_amount = tax3_amount;
	}

	private double grandTotal;
	private HashMap<Integer, Object> taxList;
	private InvoiceTemplate headerFooter;
	private SystemSettings systemseting;
	private double foodExpense;
	private double foodRefund;
	private double tax3_pc_kfc;

	private double base_amount;
	private double tax1_amount;
	private double tax2_amount;
	private double tax3_amount;
	@Transient
	private List<?> posDetails;

	@Transient
	private Object refundDetails;

	@Transient
	private List<?> paymentList;

	@Transient
	private String currencySymbol;
	@Transient
	private String invoiceNo;
	private int hsnCode;

	@Transient
	private int printMode;

	@Transient
	private int billDetails;

	@Transient
	private String constState;

	@Transient
	private String constNationality;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String getTax1Code() {
		return tax1Code;
	}

	public void setTax1Code(String tax1Code) {
		this.tax1Code = tax1Code;
	}

	public String getTax2Code() {
		return tax2Code;
	}

	public void setTax2Code(String tax2Code) {
		this.tax2Code = tax2Code;
	}

	public String getTax3Code() {
		return tax3Code;
	}

	public void setTax3Code(String tax3Code) {
		this.tax3Code = tax3Code;
	}

	public String getTax4Code() {
		return tax4Code;
	}

	public void setTax4Code(String tax4Code) {
		this.tax4Code = tax4Code;
	}

	public double getTax1() {
		return tax1;
	}

	public void setTax1(double tax1) {
		this.tax1 = tax1;
	}

	public double getTax2() {
		return tax2;
	}

	public void setTax2(double tax2) {
		this.tax2 = tax2;
	}

	public double getTax3() {
		return tax3;
	}

	public void setTax3(double tax3) {
		this.tax3 = tax3;
	}

	public double getTax4() {
		return tax4;
	}

	public void setTax4(double tax4) {
		this.tax4 = tax4;
	}

	public List<?> getTxnList() {
		return txnList;
	}

	public void setTxnList(List<?> txnList) {
		this.txnList = txnList;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	public double getPayments() {
		return payments;
	}

	public void setPayments(double payments) {
		this.payments = payments;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getTotalWithoutDisc() {
		return totalWithoutDisc;
	}

	public void setTotalWithoutDisc(double totalWithoutDisc) {
		this.totalWithoutDisc = totalWithoutDisc;
	}

	public double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public HashMap<Integer, Object> getTaxList() {
		return taxList;
	}

	public void setTaxList(HashMap<Integer, Object> taxList2) {
		this.taxList = taxList2;
	}

	public InvoiceTemplate getHeaderFooter() {
		return headerFooter;
	}

	public void setHeaderFooter(InvoiceTemplate headerFooter) {
		this.headerFooter = headerFooter;
	}

	public double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public serviceTax getSeriveTax() {
		return seriveTax;
	}

	public void setSeriveTax(serviceTax seriveTax) {
		this.seriveTax = seriveTax;
	}

	public double getRoundAdjust() {
		return roundAdjust;
	}

	public void setRoundAdjust(double roundAdjust) {
		this.roundAdjust = roundAdjust;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getGstno() {
		return gstno;
	}

	public void setGstno(String gstno) {
		this.gstno = gstno;
	}

	public String getArr_time() {
		return arr_time;
	}

	public void setArr_time(String arr_time) {
		this.arr_time = arr_time;
	}

	public String getAct_depart_time() {
		return act_depart_time;
	}

	public void setAct_depart_time(String act_depart_time) {
		this.act_depart_time = act_depart_time;
	}

	public SystemSettings getSystemseting() {
		return systemseting;
	}

	public void setSystemseting(SystemSettings systemseting) {
		this.systemseting = systemseting;
	}

	public String getRoom_number() {
		return room_number;
	}

	public void setRoom_number(String room_number) {
		this.room_number = room_number;
	}

	public String getRate_description() {
		return rate_description;
	}

	public void setRate_description(String rate_description) {
		this.rate_description = rate_description;
	}

	public List<Transaction> getTxnListSummary() {
		return txnListSummary;
	}

	public void setTxnListSummary(List<Transaction> txnListSummary) {
		this.txnListSummary = txnListSummary;
	}

	public List<?> getTxnListInvoice() {
		return txnListInvoice;
	}

	public void setTxnListInvoice(List<?> txnListInvoice) {
		this.txnListInvoice = txnListInvoice;
	}

	public double getTax1_pc() {
		return tax1_pc;
	}

	public void setTax1_pc(double tax1_pc) {
		this.tax1_pc = tax1_pc;
	}

	public double getTax2_pc() {
		return tax2_pc;
	}

	public void setTax2_pc(double tax2_pc) {
		this.tax2_pc = tax2_pc;
	}

	public double getTax3_pc() {
		return tax3_pc;
	}

	public void setTax3_pc(double tax3_pc) {
		this.tax3_pc = tax3_pc;
	}

	public double getTax4_pc() {
		return tax4_pc;
	}

	public void setTax4_pc(double tax4_pc) {
		this.tax4_pc = tax4_pc;
	}

	public int getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(int hsnCode) {
		this.hsnCode = hsnCode;
	}

	/**
	 * @return the salutation
	 */
	public String getSalutation() {
		return salutation;
	}

	/**
	 * @param salutation
	 *            the salutation to set
	 */
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public int getPrintMode() {
		return printMode;
	}

	public void setPrintMode(int printMode) {
		this.printMode = printMode;
	}

	public String getConstState() {
		return constState;
	}

	public void setConstState(String constState) {
		this.constState = constState;
	}

	public String getConstNationality() {
		return constNationality;
	}

	public void setConstNationality(String constNationality) {
		this.constNationality = constNationality;
	}

	public double getFoodExpense() {
		return foodExpense;
	}

	public void setFoodExpense(double foodExpense) {
		this.foodExpense = foodExpense;
	}

	public double getFoodRefund() {
		return foodRefund;
	}

	public void setFoodRefund(double foodRefund) {
		this.foodRefund = foodRefund;
	}

	public List<?> getPosDetails() {
		return posDetails;
	}

	public void setPosDetails(List<?> posDetails) {
		this.posDetails = posDetails;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public int getBillDetails() {
		return billDetails;
	}

	public void setBillDetails(int billDetails) {
		this.billDetails = billDetails;
	}

	public Object getRefundDetails() {
		return refundDetails;
	}

	public void setRefundDetails(Object refundDetails) {
		this.refundDetails = refundDetails;
	}

	public List<?> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<?> paymentList) {
		this.paymentList = paymentList;
	}

	public double getTax3_pc_kfc() {
		return tax3_pc_kfc;
	}

	public void setTax3_pc_kfc(double tax3_pc_kfc) {
		this.tax3_pc_kfc = tax3_pc_kfc;
	}

	
   
}