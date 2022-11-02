package com.indocosmo.pms.web.reports.model;

import java.util.List;

import com.indocosmo.pms.web.agingAR.model.AgingAR;
import com.indocosmo.pms.web.pettycash.model.PettyCash;
import com.indocosmo.pms.web.reservation_test.model.ResvHdr;
import com.indocosmo.pms.web.shiftManagement.model.ShiftManagement;
import com.indocosmo.pms.web.transaction.model.Transaction;

public class ReportTemplate {
	private TemplateReport reportHeaderFooter;
	private List<ResvHdr> resvHdrList;
	private List<ReceptionReport> receptionList;
	private List<Transaction> transactionList;
	private boolean haveHeaderFooter;
	private List<ShiftManagement> shiftManagementList;
	private List<Transaction> shiftWiseTransactionList;
	private List<Transaction> transactionTransfrList;
	private List<ReceptionReport> requestList;
	//digna
	private List<GeneralReport> generalReportList;
	//private String xmlDocument;
	private String dateFormat;
	private String symbol;
	//digna
	private List<ReceptionReport> OccupancyReportList;
	private String revenueReport;
	private List<Transaction> revenueReportDetail;
	private List<Transaction> B2BReport;
	
	

	private List<Transaction> monthlyClosureReport;
	private List<ReceptionReport> RoomsPerDayList;
	private List<ReceptionReport> roomPlanListReport;
	private List<NationalityReport> nationalityReportList;
	private List<CustomerReport> customerReport;
	//athira
	private String reportname;
	private String dateFilter;
	private List<IncomeReport> incomeReportList;
	private List<RoomBookingReport> roomBookingFrequency;
	private List<CustomerGrading> customerGradingList;
	private List<AgingAR> customerOutstandingList;
	private String building;
	private String street;
	private String city;
	private String companyname;
	private List<PettyCash> pettyCashList;
	private Double openingBal;
	
	
private String totalCashPayment;
private String officeExpenses;
private String totalAdvance;
//for DAY END REPORT
private Double cashPaymentTotal;
private Double refundTotal;
private Double complementaryTotal;
private Double contraTotal;
public Double getContraTotal() {
	return contraTotal;
}
public void setContraTotal(Double contraTotal) {
	this.contraTotal = contraTotal;
}
public Double getRefundTotal() {
	return refundTotal;
}
public void setRefundTotal(Double refundTotal) {
	this.refundTotal = refundTotal;
}
public Double getComplementaryTotal() {
	return complementaryTotal;
}
public void setComplementaryTotal(Double complementaryTotal) {
	this.complementaryTotal = complementaryTotal;
}

private Double nonCashPaymentTotal;	
private Double bookingCashPaymentTotal;
private Double bookingNonCashPaymentTotal;
private Double pettyExpenseTotal;
private Double cashToOfficeTotal;
private Double depositTotal;
private Double foodCostTotal;
private Double pettyclosingAmount;
	
public Double getCashPaymentTotal() {
	return cashPaymentTotal;
}
public void setCashPaymentTotal(Double cashPaymentTotal) {
	this.cashPaymentTotal = cashPaymentTotal;
}
public Double getNonCashPaymentTotal() {
	return nonCashPaymentTotal;
}
public void setNonCashPaymentTotal(Double nonCashPaymentTotal) {
	this.nonCashPaymentTotal = nonCashPaymentTotal;
}
public Double getBookingCashPaymentTotal() {
	return bookingCashPaymentTotal;
}
public void setBookingCashPaymentTotal(Double bookingCashPaymentTotal) {
	this.bookingCashPaymentTotal = bookingCashPaymentTotal;
}
public Double getBookingNonCashPaymentTotal() {
	return bookingNonCashPaymentTotal;
}
public void setBookingNonCashPaymentTotal(Double bookingNonCashPaymentTotal) {
	this.bookingNonCashPaymentTotal = bookingNonCashPaymentTotal;
}
public Double getPettyExpenseTotal() {
	return pettyExpenseTotal;
}
public void setPettyExpenseTotal(Double pettyExpenseTotal) {
	this.pettyExpenseTotal = pettyExpenseTotal;
}
public Double getCashToOfficeTotal() {
	return cashToOfficeTotal;
}
public void setCashToOfficeTotal(Double cashToOfficeTotal) {
	this.cashToOfficeTotal = cashToOfficeTotal;
}
public Double getDepositTotal() {
	return depositTotal;
}
public void setDepositTotal(Double depositTotal) {
	this.depositTotal = depositTotal;
}
public Double getFoodCostTotal() {
	return foodCostTotal;
}
public void setFoodCostTotal(Double foodCostTotal) {
	this.foodCostTotal = foodCostTotal;
}
public Double getPettyclosingAmount() {
	return pettyclosingAmount;
}
public void setPettyclosingAmount(Double pettyclosingAmount) {
	this.pettyclosingAmount = pettyclosingAmount;
}


	public Double getOpeningBal() {
	return openingBal;
}
public void setOpeningBal(Double openingBal) {
	this.openingBal = openingBal;
}
public String getTotalCashPayment() {
	return totalCashPayment;
}
public void setTotalCashPayment(String totalCashPayment) {
	this.totalCashPayment = totalCashPayment;
}
public String getOfficeExpenses() {
	return officeExpenses;
}
public void setOfficeExpenses(String officeExpenses) {
	this.officeExpenses = officeExpenses;
}
public String getTotalAdvance() {
	return totalAdvance;
}
public void setTotalAdvance(String totalAdvance) {
	this.totalAdvance = totalAdvance;
}
	
	public List<Transaction> getB2BReport() {
		return B2BReport;
	}
	public void setB2BReport(List<Transaction> b2bReport) {
		B2BReport = b2bReport;
	}
	public List<Transaction> getB2CReport() {
		return B2CReport;
	}
	public void setB2CReport(List<Transaction> b2cReport) {
		B2CReport = b2cReport;
	}

	private List<Transaction> B2CReport;
	private List<DayEndRport> DayEndReport;
	
	public List<DayEndRport> getDayEndReport() {
		return DayEndReport;
	}
	public void setDayEndReport(List<DayEndRport> dayEndReport) {
		DayEndReport = dayEndReport;
	}
	public List<Transaction> getRevenueReportDetail() {
		return revenueReportDetail;
	}
	public void setRevenueReportDetail(List<Transaction> revenueReportDetail) {
		this.revenueReportDetail = revenueReportDetail;
	}
	
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getDateFilter() {
		return dateFilter;
	}
	public void setDateFilter(String dateFilter) {
		this.dateFilter = dateFilter;
	}
	public String getReportname() {
		return reportname;
	}
	public void setReportname(String reportname) {
		this.reportname = reportname;
	}
	/**
	 * @return the reportHeaderFooter
	 */
	public TemplateReport getReportHeaderFooter() {
		return reportHeaderFooter;
	}
	public List<Transaction> getShiftWiseTransactionList() {
		return shiftWiseTransactionList;
	}
	public void setShiftWiseTransactionList(List<Transaction> shiftWiseTransactionList) {
		this.shiftWiseTransactionList = shiftWiseTransactionList;
	}
	/**
	 * @param reportHeaderFooter the reportHeaderFooter to set
	 */
	public void setReportHeaderFooter(TemplateReport reportHeaderFooter) {
		this.reportHeaderFooter = reportHeaderFooter;
	}
	/**
	 * @return the resvHdrList
	 */
	public List<ResvHdr> getResvHdrList() {
		return resvHdrList;
	}
	/**
	 * @param resvHdrList the resvHdrList to set
	 */
	public void setResvHdrList(List<ResvHdr> resvHdrList) {
		this.resvHdrList = resvHdrList;
	}
	/**
	 * @return the receptionList
	 */
	public List<ReceptionReport> getReceptionList() {
		return receptionList;
	}
	/**
	 * @param receptionList the receptionList to set
	 */
	public void setReceptionList(List<ReceptionReport> receptionList) {
		this.receptionList = receptionList;
	}
	
	public List<Transaction> getTransactionTransfrList() {
		return transactionTransfrList;
	}
	public void setTransactionTransfrList(List<Transaction> transactionTransfrList) {
		this.transactionTransfrList = transactionTransfrList;
	}
	/**
	 * @return the haveHeaderFooter
	 */
	public boolean isHaveHeaderFooter() {
		return haveHeaderFooter;
	}
	/**
	 * @param haveHeaderFooter the haveHeaderFooter to set
	 */
	public void setHaveHeaderFooter(boolean haveHeaderFooter) {
		this.haveHeaderFooter = haveHeaderFooter;
	}
	/**
	 * @return the transactionList
	 */
	public List<Transaction> getTransactionList() {
		return transactionList;
	}
	/**
	 * @param transactionList the transactionList to set
	 */
	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionList = transactionList;
	}
	
	public void setShiftManagementList(List<ShiftManagement> shiftManagementList) {
		this.shiftManagementList = shiftManagementList;
	}
	
	public List<ShiftManagement> getShiftManagementList() {
		return shiftManagementList;
	}
	public List<ReceptionReport> getRequestList() {
		return requestList;
	}
	public void setRequestList(List<ReceptionReport> requestList) {
		this.requestList = requestList;
	}
	//digna
	public void setGeneralReportList(List<GeneralReport> generalReportList) {
		this.generalReportList = generalReportList;
		
	}
	public List<GeneralReport> getGeneralReportList() {
		return generalReportList;

	}
//	public String getXmlDocument() {
//		return xmlDocument;
//	}
//	public void setXmlDocument(String xmlDocument) {
//		this.xmlDocument = xmlDocument;
//	}
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public List<ReceptionReport> getOccupancyReportList() {
		return OccupancyReportList;
	}
	public void setOccupancyReportList(List<ReceptionReport> OccupancyReportList) {
		this.OccupancyReportList = OccupancyReportList;
	}
	
	public String getRevenueReport() {
		return revenueReport;
	}
	public void setRevenueReport(String revenueReport) {
		this.revenueReport = revenueReport;
	}
	
	public List<Transaction> getMonthlyClosureReport() {
		return monthlyClosureReport;
	}
	public void setMonthlyClosureReport(List<Transaction> monthlyReport) {
		this.monthlyClosureReport = monthlyReport;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public List<ReceptionReport> getRoomsPerDayList() {
		return RoomsPerDayList;
	}
	public void setRoomsPerDayList(List<ReceptionReport> roomsPerDayList) {
		RoomsPerDayList = roomsPerDayList;
	}
	public List<ReceptionReport> getRoomPlanListReport() {
		return roomPlanListReport;
	}
	public void setRoomPlanListReport(List<ReceptionReport> roomPlanListReport) {
		this.roomPlanListReport = roomPlanListReport;
	}
	public void setNationalityReportList(
			List<NationalityReport> nationalityReportList) {
		this.nationalityReportList = nationalityReportList;
		// TODO Auto-generated method stub
		
	}
	public List<NationalityReport> getNationalityReportList() {
		return nationalityReportList;
	}
	
	/**
	 * @return the customerReport
	 */
	public List<CustomerReport> getCustomerReport() {
		return customerReport;
	}
	/**
	 * @param customerReport the customerReport to set
	 */
	public void setCustomerReport(List<CustomerReport> customerReport) {
		this.customerReport = customerReport;
	}
	public List<CustomerGrading> getCustomerGradingList() {
		return customerGradingList;
	}
	public void setCustomerGradingList(List<CustomerGrading> customerGradingList) {
		this.customerGradingList = customerGradingList;
	}
	public void setIncomeReportList(List<IncomeReport> incomeReportList) {
		this.incomeReportList = incomeReportList;
		
	}
	public List<IncomeReport> getIncomeReport() {
		return incomeReportList;
	}
	/**
	 * @return the roomBookingFrequency
	 */
	public List<RoomBookingReport> getRoomBookingFrequency() {
		return roomBookingFrequency;
	}
	/**
	 * @param roomBookingFrequency the roomBookingFrequency to set
	 */
	public void setRoomBookingFrequency(List<RoomBookingReport> roomBookingFrequency) {
		this.roomBookingFrequency = roomBookingFrequency;
	}
	public List<AgingAR> getCustomerOutstandingList() {
		return customerOutstandingList;
	}
	public void setCustomerOutstandingList(List<AgingAR> customerOutstandingList) {
		this.customerOutstandingList = customerOutstandingList;
	}
	public List<IncomeReport> getIncomeReportList() {
		return incomeReportList;
	}
	public List<PettyCash> getPettyCashList() {
		return pettyCashList;
	}
	
	public void setPettyCashList(List<PettyCash> pettyCashList) {
		this.pettyCashList = pettyCashList;
	}
	
	
}
