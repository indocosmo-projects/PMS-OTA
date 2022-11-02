package com.indocosmo.pms.web.reports.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.agingAR.model.AgingAR;
import com.indocosmo.pms.web.corporate.model.Corporate;
import com.indocosmo.pms.web.pettycash.model.PettyCash;
import com.indocosmo.pms.web.reports.model.CustomerGrading;
import com.indocosmo.pms.web.reports.model.CustomerReport;
import com.indocosmo.pms.web.reports.model.DayEndRport;
import com.indocosmo.pms.web.reports.model.GeneralReport;
import com.indocosmo.pms.web.reports.model.IncomeReport;
import com.indocosmo.pms.web.reports.model.NationalityReport;
import com.indocosmo.pms.web.reports.model.ReceptionReport;
import com.indocosmo.pms.web.reports.model.RoomBookingReport;
import com.indocosmo.pms.web.reports.model.TemplateReport;
import com.indocosmo.pms.web.reservation_test.model.ResvHdr;
import com.indocosmo.pms.web.shiftManagement.model.ShiftManagement;
import com.indocosmo.pms.web.transaction.model.Transaction;

public interface ReportsService {
	//digna
	public List<GeneralReport> getGeneralReportList(Date fromDate,Date toDate,int type) throws Exception;
	//digna
	public List<Transaction> getTransactionReportData(String wherePart) throws Exception;
	public List<ShiftManagement> getShiftManagementReportData(String wherePart) throws Exception;
	public List<Transaction> getShiftWiseTransactionReportData(String wherePart) throws Exception;
	public List<Transaction> getShiftWiseTxnTransferData(String wherePart) throws Exception;
	
	public List<ReceptionReport> getRequestData(String wherePart) throws Exception;
	
	public List<ResvHdr> getReservationListData(String wherePart) throws Exception;
	
	public List<ResvHdr> getReservationCancelReportData(String wherePart) throws Exception;
	
	public List<ReceptionReport> getFolioBalanceData(String wherePart) throws Exception;
	public String getCashRegistersClosureReportList(Date fromDate, Date toDate, int cashRegistersClosure)throws Exception;
	public List<ReceptionReport> getOccupancyList(Date fromDate, Date toDate) throws Exception;
	public String getRevenueReport(Date fromDate, Date toDate) throws Exception;
	List<Transaction> getTotalDailyRevenue(Date fromDate, Date toDate)
			throws Exception;
	public List<Transaction> getMonthlyClosure(int getMonth,String year);
	public List<ReceptionReport> getRoomPerDay(Date fromDate, Date toDate);
	public List<ReceptionReport> getRoomPlanReport(Date fromDate, Date toDate);
	public List<NationalityReport> getNationalityReportList(String getMonth, String getYear);
    public List<CustomerReport> getCustomerReportList(String jsonCustSearch,Date fromDate,Date toDate) throws SQLException, Exception;
	public List<IncomeReport> getIncomeReportList(Date fromDate, Date toDate);
    public List<RoomBookingReport> getRoomBookingFrequency(Date fromDate,Date toDate) throws SQLException;
	public List<AgingAR> getCustomerOutstandingDetails(Date fromDate, Date toDate,int id) throws SQLException;
	public List<TemplateReport> getCompanyDetails() throws Exception;
	public List<CustomerGrading> getCustomerGradingDetails(Date fromDate, Date toDate) throws SQLException;
	public JsonArray getTallyData(Date fromDate, Date toDate);
	public List<Corporate> getCorporateNames(Date fromDate, Date toDate);
	public JsonArray getBookingFrequencyList(Date fromDate, Date toDate);
	
	public JsonObject getCompany();
	
	public JsonArray getLedgerData(Date fromDate, Date toDate);
	public List<PettyCash> getPettyCashList(Date fromDate, Date toDate);
	public List<PettyCash> getPettyCashExportList(Date fromDate, Date toDate) throws SQLException;
	public JsonArray getContraData(Date fromDate, Date toDate, int isContra);
	public JsonArray getPettyLedgerData(Date fromDate, Date toDate);
	public JsonArray getCreditCardData(Date fromDate, Date toDate) throws SQLException;
	public List<PettyCash> getCreditCardDataList(Date fromDate, Date toDate) throws SQLException;
	public List<Transaction> getRevenueReportDetail(Date fromDate, Date toDate, int reportSelected) throws SQLException;
	public List<Transaction> getFdRevenueReportDetail(Date fromDate, Date toDate, int reportSelected) throws SQLException;
	public List<Transaction> getPosRevenueReport(Date fromDate, Date toDate) throws SQLException;
	public List<Transaction> getB2BReport(Date fromDate, Date toDate) throws SQLException;
	public List<Transaction> getB2CReport(Date fromDate, Date toDate) throws SQLException;
	public List<DayEndRport> getDayEndDetials(Date currentDate ) throws SQLException, Exception;
	public String getTotalCashPayment(Date currentDate)throws SQLException, Exception;
	public String  getOfficeExpenses(Date currentDate)throws SQLException, Exception;
	public String  getTotalAdvance(Date currentDate)throws SQLException, Exception;
	
	public JsonArray getDayEndSummary(Date currentDate)throws SQLException, Exception;
	public String getFinancialYear(Date fromDate)throws SQLException, Exception;
	public Date getFinancialYearFrom(Date fromDate)throws SQLException, Exception;
}
