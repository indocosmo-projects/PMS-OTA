package com.indocosmo.pms.web.reports.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.agingAR.model.AgingAR;
import com.indocosmo.pms.web.corporate.model.Corporate;
import com.indocosmo.pms.web.pettycash.model.PettyCash;
import com.indocosmo.pms.web.reports.dao.ReportsDao;
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

@Service
public class ReportsServiceImpl implements ReportsService {

	@Autowired
	ReportsDao reportsDao;
		
	
	@Transactional
	public List<ResvHdr> getReservationCancelReportData(String wherePart) throws Exception{
		return reportsDao.getReservationCancelReportData(wherePart);
	}
	
	@Transactional
	public List<ResvHdr> getReservationListData(String wherePart) throws Exception{
		return reportsDao.getReservationListData(wherePart);
	}
	
	//digna

		@Override
		public List<GeneralReport> getGeneralReportList(Date fromDate , Date toDate, int type) {
			// TODO Auto-generated method stub
			return reportsDao.getGeneralReportList(fromDate,toDate, type);
		}
	//digna
	@Transactional
	public List<Transaction> getTransactionReportData(String wherePart) throws Exception{
		return reportsDao.getTransactionReportData(wherePart);
	}
	
	@Transactional
	public List<Transaction> getShiftWiseTxnTransferData(String wherePart) throws Exception{
		return reportsDao.getShiftWiseTxnTransferData(wherePart);
	}
	@Override
	public List<ShiftManagement> getShiftManagementReportData(String wherePart) throws Exception {
		// TODO Auto-generated method stub
		return reportsDao.getShiftManagementReportData(wherePart);
	}
	
	@Transactional
	public List<Transaction> getShiftWiseTransactionReportData(String wherePart) throws Exception{
		return reportsDao.getShiftWiseTransactionReportData(wherePart);
	}
	
	@Transactional
	public List<ReceptionReport> getRequestData(String wherePart) throws Exception{
		return reportsDao.getRequestData(wherePart);
	}


	@Override
	public List<ReceptionReport> getFolioBalanceData(String wherePart) throws Exception {
		// TODO Auto-generated method stub
		return reportsDao.getFolioBalanceData(wherePart);
	}

	@Transactional
	public String getCashRegistersClosureReportList(Date fromDate, Date toDate, int cashRegistersClosure)throws Exception {
		return reportsDao.getCashRegistersClosureReportList(fromDate, toDate, cashRegistersClosure);
	}
	
	public List<ReceptionReport> getOccupancyList(Date fromDate,Date toDate) throws Exception{
		return reportsDao.getOccupancyDetails(fromDate,toDate);
	}

	@Override
	public String getRevenueReport(Date fromDate, Date toDate) throws Exception{
		// TODO Auto-generated method stub
		return reportsDao.getDailyRevenueList(fromDate,toDate);
	}
	
	@Override
	public List<Transaction> getTotalDailyRevenue(Date fromDate, Date toDate) throws Exception{
		// TODO Auto-generated method stub
		return reportsDao.getTotalDailyRevenueList(fromDate,toDate);
	}
	
	public List<Transaction> getMonthlyClosure(int getMonth,String year){
		return reportsDao.getMonthlyClosure(getMonth,year);
	}

	@Override
	public List<ReceptionReport> getRoomPerDay(Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return reportsDao.getRoomPerDayList(fromDate,toDate);
	}

	@Override
	public List<ReceptionReport> getRoomPlanReport(Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return reportsDao.getRoomPlanList(fromDate,toDate);
	}

	@Override
	public List<NationalityReport> getNationalityReportList(String getMonth, String getYear) {
		// TODO Auto-generated method stub
		return reportsDao.getNationalityReportList(getMonth,getYear);
	}

	@Override
	public List<CustomerReport> getCustomerReportList(String customerList, Date fromDate, Date toDate) throws Exception {
		// TODO Auto-generated method stub
		return reportsDao.getCustomerReportList(customerList, fromDate, toDate);
	}

	@Override
	public List<IncomeReport> getIncomeReportList(Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return reportsDao.getIncomeReportList( fromDate, toDate);
	}

	@Override
	public List<RoomBookingReport> getRoomBookingFrequency(Date fromDate, Date toDate) throws SQLException {
		// TODO Auto-generated method stub
		return reportsDao.getRoomFrequencyList(fromDate, toDate);
	}

	@Override
	public List<AgingAR> getCustomerOutstandingDetails(Date fromDate, Date toDate,int id) throws SQLException {
		
		return reportsDao.getCustomerOutstandingDetails(fromDate, toDate, id);
	}

	@Override
	public List<TemplateReport> getCompanyDetails() throws Exception {
		return reportsDao.getCompanyDetails();
	}


	@Override
	public List<CustomerGrading> getCustomerGradingDetails(Date fromDate, Date toDate) throws SQLException {
		
		return reportsDao.getCustomerGradingDetails(fromDate, toDate);
	}

	@Override
	public JsonArray getTallyData(Date fromDate, Date toDate) {
		
		return reportsDao.getTallyData(fromDate, toDate);
	}

	@Override
	public List<Corporate> getCorporateNames(Date fromDate, Date toDate) {
	
		return reportsDao.getCorporateNames(fromDate, toDate);
	}

	@Override
	public JsonArray getBookingFrequencyList(Date fromDate, Date toDate) {
		
		return reportsDao.getBookingFrequencyList(fromDate, toDate);
	}

	@Override
	public JsonArray getLedgerData(Date fromDate, Date toDate) {
		
		return reportsDao.getLedgerData(fromDate, toDate);
	}

	@Override
	public JsonObject getCompany() {
		// TODO Auto-generated method stub
		return reportsDao.getCompany();
	}

	@Transactional
	@Override
	public List<PettyCash> getPettyCashList(Date fromDate, Date toDate) {
		
		return reportsDao.getPettyCashList(fromDate,toDate);
	}
	
	@Override
	public List<PettyCash> getPettyCashExportList(Date fromDate, Date toDate) throws SQLException {
		
		return reportsDao.getPettyCashExportList(fromDate,toDate);
	}
	@Override
	public List<PettyCash> getCreditCardDataList(Date fromDate, Date toDate) throws SQLException{
		
		return reportsDao.getCreditCardDataList(fromDate,toDate) ;
	}

	@Override
	public JsonArray getContraData(Date fromDate, Date toDate,int isContra) {		
		return reportsDao.getContraData(fromDate,toDate,isContra);
	}

	@Override
	public JsonArray getPettyLedgerData(Date fromDate, Date toDate) {
		return  reportsDao.getPettyLedgerData(fromDate,toDate);
	}
	@Override
	public JsonArray getCreditCardData(Date fromDate, Date toDate) throws SQLException {
		return  reportsDao.getCreditCardData(fromDate,toDate);
	}

	@Override
	public List<Transaction> getRevenueReportDetail(Date fromDate, Date toDate,int reportSelected) throws SQLException {
		// TODO Auto-generated method stub
		return reportsDao.getRevenueReportDetail(fromDate,toDate,reportSelected);
	}

	@Override
	public List<Transaction> getFdRevenueReportDetail(Date fromDate, Date toDate, int reportSelected)
			throws SQLException {
		return reportsDao.getFdRevenueReportDetail(fromDate,toDate,reportSelected);
		
	}

	@Override
	public List<Transaction> getPosRevenueReport(Date fromDate, Date toDate) throws SQLException {
		// TODO Auto-generated method stub
		return reportsDao.getPosRevenueReport(fromDate,toDate);
	}
	
	@Override
	public List<Transaction> getB2BReport(Date fromDate, Date toDate) throws SQLException {
		// TODO Auto-generated method stub
		return reportsDao.getB2BReport(fromDate,toDate);
	}
	@Override
	public List<Transaction> getB2CReport(Date fromDate, Date toDate) throws SQLException {
		// TODO Auto-generated method stub
		return reportsDao.getB2CReport(fromDate,toDate);
	}
	
	@Override
	public List<DayEndRport> getDayEndDetials(Date currentDate) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return reportsDao.getDayEndDetials(currentDate);
	}

	@Override
	public String getTotalCashPayment(Date currentDate) throws SQLException, Exception {
		return reportsDao.getTotalCashPayment(currentDate);
	}

	@Override
	public String getOfficeExpenses(Date currentDate) throws SQLException, Exception {
		// TODO Auto-generated method stub
		
		return reportsDao.getOfficeExpenses(currentDate);
	}

	@Override
	public String getTotalAdvance(Date currentDate) throws SQLException, Exception {
		return reportsDao.getTotalAdvance(currentDate);
	}

	@Override
	public JsonArray getDayEndSummary(Date currentDate) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return reportsDao.getDayEndSummary(currentDate);
	}

	@Override
	public String getFinancialYear(Date fromDate) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return reportsDao.getFinancialYear(fromDate);
	}

	@Override
	public Date getFinancialYearFrom(Date fromDate) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return reportsDao.getFinancialYearFrom(fromDate);
	}
	



}

