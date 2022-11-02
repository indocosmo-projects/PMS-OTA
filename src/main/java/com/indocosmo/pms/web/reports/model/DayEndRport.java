package com.indocosmo.pms.web.reports.model;

import java.util.Date;

public class DayEndRport {


	public String getRoom_tpye() {
		return room_tpye;
	}
	public Date getTxn_date() {
		return txn_date;
	}
	public void setTxn_date(Date txn_date) {
		this.txn_date = txn_date;
	}
	public void setRoom_tpye(String room_tpye) {
		this.room_tpye = room_tpye;
	}
	
	private String guestName;
	private String firstName;
	private String lastName;
	private String room_tpye;
	private Date txn_date;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	private String roomNumber;
	
	private Date rptDate;
	
	public Date getRptDate() {
		return rptDate;
	}
	public void setRptDate(Date rptDate) {
		this.rptDate = rptDate;
	}

	private String bookingMethod;
	private String accMstCode;
	public String getAccMstCode() {
		return accMstCode;
	}
	public void setAccMstCode(String accMstCode) {
		this.accMstCode = accMstCode;
	}
	private String checkinDate;
	private String checkinTime;
	private int pax;
	private String checkOutDate;
	private String checkOutTime;
	private String perticulars;
	private String paymentMode;
	private String invoiceNo;
	/*private double roomCharge;
	private double otherCharge;
	private double totalCharge;
	private double amountPaid;
	private double refund;
	private double balance;*/
	private double totalCashPayment;
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	private double totalNonCashPayment;
	private double pettyCashAdvance;
	private double expenseTotal;
	private double cashToOffice;
	private double cashBal;
	private double amount;
	private double baseAmount;
	private double nettAmount;
	private int folioNo;
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public String getBookingMethod() {
		return bookingMethod;
	}
	public void setBookingMethod(String bookingMethod) {
		this.bookingMethod = bookingMethod;
	}
	public String getCheckinDate() {
		return checkinDate;
	}
	public void setCheckinDate(String checkinDate) {
		this.checkinDate = checkinDate;
	}
	public int getPax() {
		return pax;
	}
	public void setPax(int pax) {
		this.pax = pax;
	}
	public String getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	public String getCheckinTime() {
		return checkinTime;
	}
	public void setCheckinTime(String checkinTime) {
		this.checkinTime = checkinTime;
	}
	public String getCheckOutTime() {
		return checkOutTime;
	}
	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}
	public String getPerticulars() {
		return perticulars;
	}
	public void setPerticulars(String perticulars) {
		this.perticulars = perticulars;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public double getTotalCashPayment() {
		return totalCashPayment;
	}
	public void setTotalCashPayment(double totalCashPayment) {
		this.totalCashPayment = totalCashPayment;
	}
	public double getTotalNonCashPayment() {
		return totalNonCashPayment;
	}
	public void setTotalNonCashPayment(double totalNonCashPayment) {
		this.totalNonCashPayment = totalNonCashPayment;
	}
	public double getPettyCashAdvance() {
		return pettyCashAdvance;
	}
	public void setPettyCashAdvance(double pettyCashAdvance) {
		this.pettyCashAdvance = pettyCashAdvance;
	}
	public double getExpenseTotal() {
		return expenseTotal;
	}
	public void setExpenseTotal(double expenseTotal) {
		this.expenseTotal = expenseTotal;
	}
	public double getCashToOffice() {
		return cashToOffice;
	}
	public void setCashToOffice(double cashToOffice) {
		this.cashToOffice = cashToOffice;
	}
	public double getCashBal() {
		return cashBal;
	}
	public void setCashBal(double cashBal) {
		this.cashBal = cashBal;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getBaseAmount() {
		return baseAmount;
	}
	public void setBaseAmount(double baseAmount) {
		this.baseAmount = baseAmount;
	}
	public double getNettAmount() {
		return nettAmount;
	}
	public void setNettAmount(double nettAmount) {
		this.nettAmount = nettAmount;
	}
	public int getFolioNo() {
		return folioNo;
	}
	public void setFolioNo(int folioNo) {
		this.folioNo = folioNo;
	}

	
	
	
	
	


}
