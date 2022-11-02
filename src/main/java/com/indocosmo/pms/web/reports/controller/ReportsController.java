package com.indocosmo.pms.web.reports.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.agingAR.model.AgingAR;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.corporate.model.Corporate;
import com.indocosmo.pms.web.currency.model.Currency;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.pettycash.model.PettyCash;
import com.indocosmo.pms.web.pettycash.service.PettyCashService;
import com.indocosmo.pms.web.reception.controller.ReceptionController;
import com.indocosmo.pms.web.reports.model.CashRegistersClosureReportTemplate;
import com.indocosmo.pms.web.reports.model.CustomerGrading;
import com.indocosmo.pms.web.reports.model.CustomerReport;
import com.indocosmo.pms.web.reports.model.DayEndRport;
import com.indocosmo.pms.web.reports.model.GeneralReport;
import com.indocosmo.pms.web.reports.model.IncomeReport;
import com.indocosmo.pms.web.reports.model.NationalityReport;
import com.indocosmo.pms.web.reports.model.ReceptionReport;
import com.indocosmo.pms.web.reports.model.ReportTemplate;
import com.indocosmo.pms.web.reports.model.RoomBookingReport;
import com.indocosmo.pms.web.reports.model.TemplateReport;
import com.indocosmo.pms.web.reports.report_designs.TallyExportXml;
import com.indocosmo.pms.web.reports.service.ReportsService;
import com.indocosmo.pms.web.reservation_test.model.ResvHdr;
import com.indocosmo.pms.web.shiftManagement.model.ShiftManagement;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;
import com.indocosmo.pms.web.transaction.model.Transaction;

@Controller
@RequestMapping(value = "report")
public class ReportsController {

	@Autowired
	SystemSettingsService systemSettingsService;

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	@Autowired
	private ReportsService reportService;
	
	@Autowired
	PettyCashService  pettyCashService;
	

	public static final Logger logger = LoggerFactory.getLogger(ReceptionController.class);
	// digna
	public static final int EXPECTED_ARRIVAL = 0;
	public static final int ACTUAL_ARRIVAL = 1;
	public static final int INHOUSE = 2;
	public static final int EXPECTED_DEPATURE = 3;
	public static final int ACTUAL_DEPATURE = 4;
	public static final int CASH_REGISTERS_CLOSURE = 17;
	public static final int OCCUPANCY_LIST = 18;
	public static final int DAILY_REVENUE = 19;
	// digna
	public static final String REPORT_LIST_PAGE_URL = "reports/reports";
	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "access_denied/access_denied";

	/**
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "reportList", method = RequestMethod.GET)
	public String listRedirect(HttpSession session, Model model) throws Exception {
		SysPermissions permissionObjExpArr = pageAccessPermissionService.getPermission(session, "REPORTS_EXP_ARR");
		SysPermissions permissionObjActArr = pageAccessPermissionService.getPermission(session, "REPORTS_ACT_ARR");
		SysPermissions permissionObjExpDep = pageAccessPermissionService.getPermission(session, "REPORTS_EXP_DEP");
		SysPermissions permissionObjActDep = pageAccessPermissionService.getPermission(session, "REPORTS_ACT_DEP");
		SysPermissions permissionObjInHouse = pageAccessPermissionService.getPermission(session, "REPORTS_INHOUSE");
		SysPermissions permissionObjResv = pageAccessPermissionService.getPermission(session, "REPORTS_RESERV");
		SysPermissions permissionObjResvCancel = pageAccessPermissionService.getPermission(session,
				"REPORTS_RESV_CANCEL");
		SysPermissions permissionObjTxn = pageAccessPermissionService.getPermission(session, "REPORTS_TXN");
		SysPermissions permissionObjTxnTrnsfr = pageAccessPermissionService.getPermission(session,
				"REPORTS_TXN_TRNSFR");
		SysPermissions permissionObjTxnDeleted = pageAccessPermissionService.getPermission(session,
				"REPORTS_TXN_DELETED");
		SysPermissions permissionObjCashRegisterClosure = pageAccessPermissionService.getPermission(session,
				"REPORTS_CSH_REG");
		SysPermissions permissionObjFolio = pageAccessPermissionService.getPermission(session, "REPORTS_FOLIO");
		SysPermissions permissionObjShift = pageAccessPermissionService.getPermission(session, "REPORTS_SHIFT");
		SysPermissions permissionObjShiftTxn = pageAccessPermissionService.getPermission(session, "REPORTS_SHIFT_TXN");
		SysPermissions permissionObjShiftWiseTxnTranfr = pageAccessPermissionService.getPermission(session,
				"REPORTS_SHIFT_TXN_TR");
		SysPermissions permissionObjRequest = pageAccessPermissionService.getPermission(session, "REPORTS_REQUEST");
		SysPermissions permissionObjOccupancy = pageAccessPermissionService.getPermission(session, "REPORTS_OCCUPANCY");
		SysPermissions permissionObjRoomsPerDay = pageAccessPermissionService.getPermission(session,
				"REPORTS_ROOM_PER_DAY");
		SysPermissions permissionObjPlanAndRoom = pageAccessPermissionService.getPermission(session,
				"REPORTS_PLAN_ROOM");
		SysPermissions permissionObjDailyRevenue = pageAccessPermissionService.getPermission(session,
				"REPORTS_DLY_REVENUE");
		SysPermissions permissionObjMonthlyClosureReport = pageAccessPermissionService.getPermission(session,
				"REPORTS_MNTHLY_CLO");
		SysPermissions permissionObjNationalityStatisticsReport = pageAccessPermissionService.getPermission(session,
				"REPORTS_NATIONALITY");
		SysPermissions permissionObjCustomerReport = pageAccessPermissionService.getPermission(session,
				"REPORTS_CUSTOMER");
		SysPermissions permissionObjDailyIncome = pageAccessPermissionService.getPermission(session,
				"REPORTS_DLY_INCOME");
		SysPermissions permissionRoomBookingFrequency = pageAccessPermissionService.getPermission(session,
				"REPORTS_ROOM_BOOKING");
		SysPermissions permissionObjCustomerOutstanding = pageAccessPermissionService.getPermission(session,
				"REPORTS_CST_OUTSTAND");
		SysPermissions permissionObjCustomerGrading = pageAccessPermissionService.getPermission(session,
				"REPORTS_CST_GRADE");
		SysPermissions permissionObjBookingFrequency = pageAccessPermissionService.getPermission(session,
				"REPORTS_CST_GRADE");
		SysPermissions permissionObjTallyExport = pageAccessPermissionService.getPermission(session,
				"REPORTS_TALLY_EXPORT");
		SysPermissions permissionObjCorporateList = pageAccessPermissionService.getPermission(session,
				"REPORTS_CORP_LIST");
		SysPermissions permissionObjGuestAnalysis = pageAccessPermissionService.getPermission(session,
				"REPORTS_GUEST_ANALYS");
		SysPermissions permissionObjPettyList = pageAccessPermissionService.getPermission(session,
				"REPORTS_PETTY_LIST");
		SysPermissions permissionContraList = pageAccessPermissionService.getPermission(session,
				"REPORTS_CONTRA");
		SysPermissions permissionPaymentList = pageAccessPermissionService.getPermission(session,
				"REPORTS_PAYMENT");
		SysPermissions permissionPettyLedgerList = pageAccessPermissionService.getPermission(session,
				"REPORTS_PETTY_LEDGER");
		SysPermissions permissionPettyExportList = pageAccessPermissionService.getPermission(session,
				"REPORTS_PETTY_EXPORT");
		SysPermissions permissionCardExportList = pageAccessPermissionService.getPermission(session,
				"REPORTS_CARD_EXPORT");
		SysPermissions permissionCreditCardReports = pageAccessPermissionService.getPermission(session,
				"REPORTS_CREDITCARD");
		SysPermissions permissionDetailRevenueReports = pageAccessPermissionService.getPermission(session,
				"REPORTS_DET_REV");
		SysPermissions permissionFoodRevenueReports = pageAccessPermissionService.getPermission(session,
				"REPORTS_DET_FDREV");
		SysPermissions permissionPosRevenueReports = pageAccessPermissionService.getPermission(session,
				"REPORTS_DET_POSREV");
		SysPermissions permissionB2BReports = pageAccessPermissionService.getPermission(session,
				"REPORTS_B2B");
		SysPermissions permissionB2CReports = pageAccessPermissionService.getPermission(session,
				"REPORTS_B2C");
		SysPermissions permissionDAYENDReports = pageAccessPermissionService.getPermission(session,
				"REPORTS_DAYEND");
		String pageUrl = REPORT_LIST_PAGE_URL;
		if ((permissionObjExpArr.isCanView() && permissionObjExpArr.isIs_view_applicable())
				|| (permissionObjActArr.isCanView() && permissionObjActArr.isIs_view_applicable())
				|| (permissionObjExpDep.isCanView() && permissionObjExpDep.isIs_view_applicable())
				|| (permissionObjActDep.isCanView() && permissionObjActDep.isIs_view_applicable())
				|| (permissionObjInHouse.isCanView() && permissionObjInHouse.isIs_view_applicable())
				|| (permissionObjResv.isCanView() && permissionObjResv.isIs_view_applicable())
				|| (permissionObjResvCancel.isCanView() && permissionObjResvCancel.isIs_view_applicable())
				|| (permissionObjTxn.isCanView() && permissionObjTxn.isIs_view_applicable())
				|| (permissionObjTxnTrnsfr.isCanView() && permissionObjTxnTrnsfr.isIs_view_applicable())
				|| (permissionObjTxnDeleted.isCanView() && permissionObjTxnDeleted.isIs_view_applicable())
				|| (permissionObjCashRegisterClosure.isCanView()
						&& permissionObjCashRegisterClosure.isIs_view_applicable())
				|| (permissionObjFolio.isCanView() && permissionObjFolio.isIs_view_applicable())
				|| (permissionObjShift.isCanView() && permissionObjShift.isIs_view_applicable())
				|| (permissionObjShiftTxn.isCanView() && permissionObjShiftTxn.isIs_view_applicable())
				|| (permissionObjShiftWiseTxnTranfr.isCanView()
						&& permissionObjShiftWiseTxnTranfr.isIs_view_applicable())
				|| (permissionObjRequest.isCanView() && permissionObjRequest.isIs_view_applicable())
				|| (permissionObjOccupancy.isCanView() && permissionObjOccupancy.isIs_view_applicable())
				|| (permissionObjRoomsPerDay.isCanView() && permissionObjRoomsPerDay.isIs_view_applicable())
				|| (permissionObjPlanAndRoom.isCanView() && permissionObjPlanAndRoom.isIs_view_applicable())
				|| (permissionObjDailyRevenue.isCanView() && permissionObjDailyRevenue.isIs_view_applicable())
				|| (permissionObjMonthlyClosureReport.isCanView()
						&& permissionObjMonthlyClosureReport.isIs_view_applicable())
				|| (permissionObjNationalityStatisticsReport.isCanView()
						&& permissionObjNationalityStatisticsReport.isIs_view_applicable())
				|| (permissionObjCustomerReport.isCanView() && permissionObjCustomerReport.isIs_view_applicable())
				|| (permissionRoomBookingFrequency.isCanView() && permissionRoomBookingFrequency.isIs_view_applicable())
				|| (permissionObjDailyIncome.isCanView() && permissionObjDailyIncome.isIs_view_applicable())
				|| (permissionObjCustomerOutstanding.isCanView()
						&& permissionObjCustomerOutstanding.isIs_view_applicable())
				 || (permissionContraList.isCanView() && permissionContraList.isIs_view_applicable())
				 || (permissionPaymentList.isCanView() && permissionPaymentList.isIs_view_applicable())
				 || (permissionObjPettyList.isCanView() && permissionObjPettyList.isIs_view_applicable())
				 || (permissionPettyLedgerList.isCanView() && permissionPettyLedgerList.isIs_view_applicable())
				|| (permissionObjBookingFrequency.isCanView() && permissionObjCustomerGrading.isIs_view_applicable())
				|| (permissionObjTallyExport.isCanView() && permissionObjTallyExport.isIs_view_applicable())
				|| (permissionObjCorporateList.isCanView() && permissionObjCorporateList.isIs_view_applicable())
				|| (permissionObjGuestAnalysis.isCanView() && permissionObjGuestAnalysis.isIs_view_applicable())
				||	(permissionPettyExportList.isCanView() && permissionPettyExportList.isIs_view_applicable()) 
				||	(permissionCardExportList.isCanView() && permissionCardExportList.isIs_view_applicable()) 
				||	(permissionCreditCardReports.isCanView() && permissionCreditCardReports.isIs_view_applicable())
				||	(permissionDetailRevenueReports.isCanView() && permissionDetailRevenueReports.isIs_view_applicable())
				||	(permissionFoodRevenueReports.isCanView() && permissionFoodRevenueReports.isIs_view_applicable())
				||	(permissionPosRevenueReports.isCanView() && permissionPosRevenueReports.isIs_view_applicable())
				||	(permissionB2BReports.isCanView() && permissionB2BReports.isIs_view_applicable())
				||	(permissionB2CReports.isCanView() && permissionB2CReports.isIs_view_applicable())
				||	(permissionDAYENDReports.isCanView() && permissionDAYENDReports.isIs_view_applicable())
				
				)
		{
			model.addAttribute("curPagePerExpArrObj", permissionObjExpArr);
			model.addAttribute("curPagePerActArrObj", permissionObjActArr);
			model.addAttribute("curPagePerExpDepObj", permissionObjExpDep);
			model.addAttribute("curPagePerActDepObj", permissionObjActDep);
			model.addAttribute("curPagePerInHouseObj", permissionObjInHouse);
			model.addAttribute("curPagePerResvObj", permissionObjResv);
			model.addAttribute("curPagePerResvCancelObj", permissionObjResvCancel);
			model.addAttribute("curPagePerTxnObj", permissionObjTxn);
			model.addAttribute("curPagePerTxnTrnsfrObj", permissionObjTxnTrnsfr);
			model.addAttribute("curPagePerTxnDeltdObj", permissionObjTxnDeleted);
			model.addAttribute("curPagePerCashRegisterClosureObj", permissionObjCashRegisterClosure);
			model.addAttribute("curPagePerFolioObj", permissionObjFolio);
			model.addAttribute("curPagePerShiftObj", permissionObjShift);
			model.addAttribute("curPagePerShiftTxnObj", permissionObjShiftTxn);
			model.addAttribute("curPagePerShiftTxnTransfrObj", permissionObjShiftWiseTxnTranfr);
			model.addAttribute("curPagePerRequestObj", permissionObjRequest);
			model.addAttribute("curPagePerOccupancyObj", permissionObjOccupancy);
			model.addAttribute("curPgePerRoomsPerDayObj", permissionObjRoomsPerDay);
			model.addAttribute("curPagePerPlanRoomObj", permissionObjPlanAndRoom);
			model.addAttribute("curPageDailyRevenueObj", permissionObjDailyRevenue);
			model.addAttribute("curPageMonthlyReportObj", permissionObjMonthlyClosureReport);
			model.addAttribute("curPageNationalityReportObj", permissionObjNationalityStatisticsReport);
			model.addAttribute("curPageCustomerObj", permissionObjCustomerReport);
			model.addAttribute("curPageRoomBooking", permissionRoomBookingFrequency);
			model.addAttribute("curPageIncomeObj", permissionObjDailyIncome);
			model.addAttribute("curPageCustomerOutstanding", permissionObjCustomerOutstanding);
			model.addAttribute("curPageCustomerGrading", permissionObjCustomerGrading);
			model.addAttribute("curPageBookingFrequency", permissionObjBookingFrequency);
			model.addAttribute("curPageTallyExport", permissionObjTallyExport);
			model.addAttribute("curPageCorporateList", permissionObjCorporateList);
			model.addAttribute("curPageContraList", permissionContraList);
			model.addAttribute("curPagePaymentList", permissionPaymentList);
			model.addAttribute("curPagePettyList", permissionObjPettyList);
			model.addAttribute("curPagePettyLedgerList", permissionPettyLedgerList);
			model.addAttribute("curPageGuestAnalysis", permissionObjGuestAnalysis);
			model.addAttribute("curPagePaymentExportList", permissionPettyExportList);
			model.addAttribute("curPageCardExportList", permissionCardExportList);
			model.addAttribute("curPageCreditCardReportObj", permissionCreditCardReports);
			model.addAttribute("curPageDetailRevenueReportObj", permissionDetailRevenueReports);
			model.addAttribute("curPageFoodRevenueReportObj", permissionFoodRevenueReports);
			model.addAttribute("curPagePosRevenueReportObj", permissionPosRevenueReports);
			model.addAttribute("curPageB2BReportObj", permissionB2BReports);
			model.addAttribute("curPageB2CReportObj", permissionB2CReports);
			model.addAttribute("curPageDAYENDReportObj", permissionDAYENDReports);

		} else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}

	/**
	 * @param inpData
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "print", method = RequestMethod.GET)
	public ModelAndView PrintInvoice(@RequestParam(value = "inpData") String inpData, HttpSession session,
			HttpServletRequest request) throws Exception {
		SimpleDateFormat dateFormatFrom = new SimpleDateFormat(session.getAttribute("dateFormat").toString());
		SystemSettings systemSettings = systemSettingsService.getSystemSettings();
		String dateFormat = systemSettings.getDateFormat();
		Currency curr = (Currency) session.getAttribute("currencyObject");
		DateFormat simpleDateFormatHotelDate = new SimpleDateFormat(dateFormat);
		JsonParser parser = new JsonParser();
		JsonObject jobj = parser.parse(inpData).getAsJsonObject();
		List<ResvHdr> resvHdrList = null;
		List<ReceptionReport> receptionList = null;
		List<Transaction> transactionList = null;
		List<Transaction> shiftWiseTransactionList = null;
		List<ShiftManagement> shiftManagementList = null;
		List<Transaction> transactionTransfrList = null;
		List<Transaction> revenueReportDetail = null;
		List<ReceptionReport> requestList = null;
		List<Transaction> B2BReport = null;
		List<Transaction> B2CReport = null;
		List<DayEndRport> DayEndReportList = null;

		List<TemplateReport> reportTemplateList = null;
		String revenueReport = null;
		String xmlDocument = null;
		List<Transaction> monthlyReport = null;
		List<ReceptionReport> occupancyReport = null;
		List<GeneralReport> generalReportList = null;
		List<ReceptionReport> roomPerDay = null;
		List<ReceptionReport> roomPlanList = null;
		List<NationalityReport> nationalityReportList = null;
		List<CustomerReport> customerReport = null;
		List<IncomeReport> incomeReportList = null;
		List<RoomBookingReport> roomBookingFrequency = null;
		List<Corporate> corporateList = null;
		List<AgingAR> customerOutstandingList = null;
		List<CustomerGrading> customerGradingList = null;
		List<PettyCash> pettyExpenseList=null;
		List<PettyCash> pettyExportList=null;
		List<PettyCash> creditCardExportList=null;
		Date fromDate = null;
		Date toDate = null;
		int getMonth;
		ModelAndView mv = null;
		String wherePart = "";
		int report_id = jobj.get("repId").getAsInt();
		int reportType = jobj.get("reportType").getAsInt();
		int corpId = jobj.get("corpId").getAsInt();
		int reportSelected = jobj.get("reportSelected").getAsInt();
		ReportTemplate reportTempl = getReportTemplate(report_id);
		TemplateReport tr = new TemplateReport();

		Integer[] range_show_reports = { 1, 2, 3, 4, 5, 24 };
		try {
			tr.setHotelName(session.getAttribute("companyName").toString());

			reportTemplateList = reportService.getCompanyDetails();
			tr.setName(reportTemplateList.get(0).getName());
			tr.setBuilding(reportTemplateList.get(0).getBuilding());
			tr.setStreet(reportTemplateList.get(0).getStreet());
			tr.setCity(reportTemplateList.get(0).getCity());
			tr.setState(reportTemplateList.get(0).getState());
			tr.setCountry(reportTemplateList.get(0).getCountry());
			tr.setGst(reportTemplateList.get(0).getGst());

			reportTempl.setGeneralReportList(generalReportList);

			tr.setPrintBy(((User) session.getAttribute("userForm")).getName());
			tr.setPrintDate(new Date(commonSettings.getHotelDate().getTime()));
			tr.setReportType(1);
			if (Arrays.asList(range_show_reports).contains(report_id)) {
				if (jobj.get("inpGroup").getAsString().equals("today")) {
					fromDate = new Date(commonSettings.getHotelDate().getTime());
					toDate = new Date(commonSettings.getHotelDate().getTime());
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate))));
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate))));
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
					tr.setFilterDetails(
							"Date Between ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate)))
							.concat(" to ").concat(String.valueOf(simpleDateFormatHotelDate.format(toDate))));
				}

			}

			switch (report_id) {

			case 1:
				generalReportList = reportService.getGeneralReportList(fromDate, toDate, EXPECTED_ARRIVAL);
				tr.setHeader("EXPECTED ARRIVALS");
				reportTempl.setGeneralReportList(generalReportList);
				if (reportType == 1) {
					mv = new ModelAndView("pdfViewGeneralReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setReportname("EXPECTED ARRIVALS");
					mv = new ModelAndView("pdfViewGeneralReportExcel", "reportTemplate", reportTempl);
				}
				break;
			case 2:
				generalReportList = reportService.getGeneralReportList(fromDate, toDate, ACTUAL_ARRIVAL);
				tr.setHeader("ACTUAL ARRIVALS");
				reportTempl.setGeneralReportList(generalReportList);
				if (reportType == 1) {
					mv = new ModelAndView("pdfViewGeneralReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setReportname("EXPECTED ARRIVALS");
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setReportname("ACTUAL ARRIVALS");
					mv = new ModelAndView("pdfViewGeneralReportExcel", "reportTemplate", reportTempl);
				}
				break;
			case 3:
				generalReportList = reportService.getGeneralReportList(fromDate, toDate, EXPECTED_DEPATURE);
				tr.setHeader("EXPECTED DEPARTURE");
				reportTempl.setGeneralReportList(generalReportList);
				if (reportType == 1) {
					mv = new ModelAndView("pdfViewExpectedDepartureReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setReportname("EXPECTED DEPARTURE");
					mv = new ModelAndView("pdfViewExpectedDepartureReportExcel", "reportTemplate", reportTempl);
				}
				break;
			case 4:
				generalReportList = reportService.getGeneralReportList(fromDate, toDate, ACTUAL_DEPATURE);
				tr.setHeader("ACTUAL DEPARTURE");
				reportTempl.setGeneralReportList(generalReportList);
				if (reportType == 1) {
					mv = new ModelAndView("pdfViewGeneralReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setReportname("EXPECTED ARRIVALS");
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setReportname("ACTUAL DEPARTURE");
					mv = new ModelAndView("pdfViewGeneralReportExcel", "reportTemplate", reportTempl);
				}
				break;
			case 5:
				generalReportList = reportService.getGeneralReportList(fromDate, toDate, INHOUSE);
				tr.setHeader("IN-HOUSE");
				reportTempl.setGeneralReportList(generalReportList);
				if (reportType == 1) {
					mv = new ModelAndView("pdfViewGeneralReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setReportname("EXPECTED ARRIVALS");
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setReportname("IN-HOUSE");
					mv = new ModelAndView("pdfViewGeneralReportExcel", "reportTemplate", reportTempl);
				}
				break;

			case 6:
				if (jobj.get("inpGroup").getAsString().equals("today")) {
					Date today = new Date(commonSettings.getHotelDate().getTime());
					wherePart = "resv_date = '" + today.toString() + "'";
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(today))));
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					Date ondate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					wherePart = "resv_date = '" + ondate.toString() + "'";
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(ondate))));
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					Date fromdate = new java.sql.Date(
							dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					Date todate = new java.sql.Date(
							dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
					wherePart = "resv_date >= '" + fromdate.toString() + "' and resv_date <= '" + todate.toString()
					+ "' ";
					tr.setFilterDetails(
							"Date Between ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromdate)))
							.concat(" to ").concat(String.valueOf(simpleDateFormatHotelDate.format(todate))));
				}
				resvHdrList = reportService.getReservationListData(wherePart);
				tr.setHeader("RESERVATION LIST");
				reportTempl.setResvHdrList(resvHdrList);
				if (reportType == 1) {
					mv = new ModelAndView("pdfViewReservationReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setReportname("RESERVATION LIST");
					mv = new ModelAndView("pdfViewReservationReportExcel", "reportTemplate", reportTempl);
				}
				break;
			case 7:
				if (jobj.get("inpGroup").getAsString().equals("today")) {
					Date today = new Date(commonSettings.getHotelDate().getTime());
					wherePart = "resv_hdr.cancel_date = '" + today.toString() + "' and resv_hdr.status=2";
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(today))));
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					Date ondate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					wherePart = "resv_hdr.cancel_date = '" + ondate.toString() + "' and resv_hdr.status=2";
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(ondate))));
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					Date fromdate = new java.sql.Date(
							dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					Date todate = new java.sql.Date(
							dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
					wherePart = "resv_hdr.cancel_date >= '" + fromdate.toString() + "' and resv_hdr.cancel_date <= '"
							+ todate.toString() + "' and resv_hdr.status=2";
					tr.setFilterDetails(
							"Date Between ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromdate)))
							.concat(" to ").concat(String.valueOf(simpleDateFormatHotelDate.format(todate))));
				}
				resvHdrList = reportService.getReservationCancelReportData(wherePart);
				tr.setHeader("CANCELLED RESERVATIONS");
				reportTempl.setResvHdrList(resvHdrList);
				if (reportType == 1) {
					mv = new ModelAndView("pdfViewCancellationReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setReportname("CANCELLED RESERVATION");
					mv = new ModelAndView("pdfViewCancellationReportExcel", "reportTemplate", reportTempl);
				}
				break;
			case 8:
				if (jobj.get("inpGroup").getAsString().equals("today")) {
					Date today = new Date(commonSettings.getHotelDate().getTime());
					wherePart = "txn.txn_date = '" + today.toString() + "' and txn.txn_status=1";
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(today))));
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					Date ondate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					wherePart = "txn.txn_date = '" + ondate.toString() + "' and txn.txn_status=1";
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(ondate))));
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					Date fromdate = new java.sql.Date(
							dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					Date todate = new java.sql.Date(
							dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
					wherePart = "txn.txn_date >= '" + fromdate.toString() + "' and txn.txn_date <= '"
							+ todate.toString() + "' and txn.txn_status=1";
					tr.setFilterDetails(
							"Date Between ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromdate)))
							.concat(" to ").concat(String.valueOf(simpleDateFormatHotelDate.format(todate))));
				}
				transactionList = reportService.getTransactionReportData(wherePart);
				tr.setHeader("TRANSACTION LIST");
				reportTempl.setTransactionList(transactionList);
				if (reportType == 1) {
					mv = new ModelAndView("pdfViewTransactionReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setReportname("TRANSACTION LIST");
					mv = new ModelAndView("pdfViewTransactionReportExcel", "reportTemplate", reportTempl);
				}
				break;
			case 9:
				if (jobj.get("inpGroup").getAsString().equals("today")) {
					Date today = new Date(commonSettings.getHotelDate().getTime());
					wherePart = "tbltrfr.txn_date = '" + today.toString() + "' and txn.txn_status=2";
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(today))));
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					Date ondate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					wherePart = "tbltrfr.txn_date = '" + ondate.toString() + "' and txn.txn_status=2";
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(ondate))));
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					Date fromdate = new java.sql.Date(
							dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					Date todate = new java.sql.Date(
							dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
					wherePart = "tbltrfr.txn_date >= '" + fromdate.toString() + "' and tbltrfr.txn_date <= '"
							+ todate.toString() + "' and txn.txn_status=2";
					tr.setFilterDetails(
							"Date Between ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromdate)))
							.concat(" to ").concat(String.valueOf(simpleDateFormatHotelDate.format(todate))));
				}
				transactionList = reportService.getTransactionReportData(wherePart);
				tr.setHeader("TRANSACTION TRANSFER LIST");
				reportTempl.setTransactionList(transactionList);
				if (reportType == 1) {
					mv = new ModelAndView("pdfViewTransactionTransferReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setReportname("TRANSACTION TRANSFER LIST");
					mv = new ModelAndView("pdfViewTransactionTransferReportExcel", "reportTemplate", reportTempl);
				}

				break;
			case 10:
				if (jobj.get("inpGroup").getAsString().equals("inhouse")) {
					wherePart = "checkin_hdr.status=5";
					tr.setFilterDetails("IN-HOUSE ");
				} else if (jobj.get("inpGroup").getAsString().equals("room")) {
					wherePart = "checkin_hdr.status=5 and checkin_hdr.room_number=" + jobj.get("input").getAsString();
					tr.setFilterDetails("For Room ".concat(jobj.get("input").getAsString()));
				}
				receptionList = reportService.getFolioBalanceData(wherePart);
				tr.setHeader("FOLIO BALANCE");
				tr.setReportType(0);
				reportTempl.setReceptionList(receptionList);
				if (reportType == 1) {
					mv = new ModelAndView("pdfViewFolioBalanceReport", "reportTemplate", reportTempl);
				} else {

					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setReportname("FOLIO BALANCE");
					mv = new ModelAndView("pdfViewFolioBalanceReportExcel", "reportTemplate", reportTempl);
				}
				break;
			case 11:
				String monthSelected = jobj.get("monthId").getAsString();
				String year = jobj.get("yearId").getAsString();
				nationalityReportList = reportService.getNationalityReportList(monthSelected, year);
				tr.setHeader("Country/Nationality Statistics by Month");
				String selectedMonth = "Month of";
				switch (Integer.parseInt(monthSelected)) {
				case 1:
					tr.setFilterDetails(selectedMonth + " January " + year);
					break;
				case 2:
					tr.setFilterDetails(selectedMonth + " February " + year);
					break;
				case 3:
					tr.setFilterDetails(selectedMonth + " March " + year);
					break;
				case 4:
					tr.setFilterDetails(selectedMonth + " April " + year);
					break;
				case 5:
					tr.setFilterDetails(selectedMonth + " May " + year);
					break;
				case 6:
					tr.setFilterDetails(selectedMonth + " June " + year);
					break;
				case 7:
					tr.setFilterDetails(selectedMonth + " July " + year);
					break;
				case 8:
					tr.setFilterDetails(selectedMonth + " August " + year);
					break;
				case 9:
					tr.setFilterDetails(selectedMonth + " September " + year);
					break;
				case 10:
					tr.setFilterDetails(selectedMonth + " October " + year);
					break;
				case 11:
					tr.setFilterDetails(selectedMonth + " November " + year);
					break;
				case 12:
					tr.setFilterDetails(selectedMonth + " December " + year);
					break;
				}
				reportTempl.setNationalityReportList(nationalityReportList);
				if (reportType == 1) {
					mv = new ModelAndView("pdfNationalityReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setReportname(tr.getHeader());
					mv = new ModelAndView("pdfNationalityReportExcel", "reportTemplate", reportTempl);
				}
				break;
			case 12:
				if (jobj.get("inpGroup").getAsString().equals("today")) {
					Date today = new Date(commonSettings.getHotelDate().getTime());
					wherePart = "opening_date = '" + today.toString() + "'";
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(today))));
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					Date ondate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					wherePart = "opening_date = '" + ondate.toString() + "'";
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(ondate))));
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					Date fromdate = new java.sql.Date(
							dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					Date todate = new java.sql.Date(
							dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
					wherePart = "opening_date >= '" + fromdate.toString() + "' and opening_date <= '"
							+ todate.toString() + "'";
					tr.setFilterDetails(
							"Date Between ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromdate)))
							.concat(" to ").concat(String.valueOf(simpleDateFormatHotelDate.format(todate))));
				}
				shiftManagementList = reportService.getShiftManagementReportData(wherePart);
				tr.setHeader("SHIFT DETAILS");
				reportTempl.setShiftManagementList(shiftManagementList);
				mv = new ModelAndView("pdfViewShiftManagementReport", "reportTemplate", reportTempl);
				break;
			case 13:
				if (jobj.get("inpGroup").getAsString().equals("ondate")
						&& (jobj.get("shiftid").getAsString().equals(null)
								|| jobj.get("shiftid").getAsString().length() == 0)
						&& (jobj.get("userid").getAsString().equals(null)
								|| jobj.get("userid").getAsString().length() == 0)) {
					Date ondate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					wherePart = "txn.txn_date = '" + ondate.toString() + "' and txn_status=1";
					tr.setFilterDetails("Date :".concat(String.valueOf(simpleDateFormatHotelDate.format(ondate))));

				} else if (jobj.get("inpGroup").getAsString().equals("ondate")
						&& (jobj.get("shiftid").getAsString() != null || jobj.get("shiftid").getAsString().length() > 0)
						&& (jobj.get("userid").getAsString().equals(null)
								|| jobj.get("userid").getAsString().length() == 0)) {
					Date ondate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					wherePart = "txn.txn_date = '" + ondate.toString() + "' and txn.shift_id='"
							+ jobj.get("shiftid").getAsString() + "' and txn_status=1";
					tr.setFilterDetails("On Date :".concat(String.valueOf(simpleDateFormatHotelDate.format(ondate))));
					tr.setFilterDetails("Shift Id :".concat(jobj.get("shiftid").getAsString()));
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")
						&& (jobj.get("userid").getAsString() != null || jobj.get("userid").getAsString().length() > 0)
						&& (jobj.get("shiftid").getAsString().equals(null)
								|| jobj.get("shiftid").getAsString().length() == 0)) {
					Date ondate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					wherePart = "txn.txn_date = '" + ondate.toString() + "' and txn.user_id='"
							+ jobj.get("userid").getAsString() + "' and txn_status=1";
					tr.setFilterDetails("On Date :".concat(String.valueOf(simpleDateFormatHotelDate.format(ondate))));
					tr.setFilterDetails("User Id :".concat(jobj.get("userid").getAsString()));
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")
						&& (jobj.get("shiftid").getAsString() != null || jobj.get("shiftid").getAsString().length() > 0)
						&& (jobj.get("userid").getAsString() != null
						|| jobj.get("userid").getAsString().length() > 0)) {
					Date ondate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					wherePart = "txn.txn_date = '" + ondate.toString() + "' and txn.user_id='"
							+ jobj.get("userid").getAsString() + "' and txn.shift_id='"
							+ jobj.get("shiftid").getAsString() + "' and txn_status=1";
					tr.setFilterDetails("On Date :".concat(String.valueOf(simpleDateFormatHotelDate.format(ondate))));
					tr.setFilterDetails("User Id :".concat(jobj.get("userid").getAsString()));
					tr.setFilterDetails("Shift Id :".concat(jobj.get("shiftid").getAsString()));
				}
				shiftWiseTransactionList = reportService.getShiftWiseTransactionReportData(wherePart);
				tr.setHeader("SHIFT WISE TRANSACTION DETAIL");
				reportTempl.setShiftWiseTransactionList(shiftWiseTransactionList);
				if (reportType == 1) {
					mv = new ModelAndView("pdfViewshiftWiseTransactionReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setReportname("SHIFT WISE TRANSACTION DETAIL");
					mv = new ModelAndView("pdfViewShiftWiseTransactionReportExcel", "reportTemplate", reportTempl);
				}
				break;
			case 14:
				if (jobj.get("inpGroup").getAsString().equals("today")) {
					Date today = new Date(commonSettings.getHotelDate().getTime());
					wherePart = "txn.txn_date = '" + today.toString() + "' and txn.txn_status=0";
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(today))));
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					Date ondate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					wherePart = "txn.txn_date = '" + ondate.toString() + "' and txn.txn_status=0";
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(ondate))));
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					Date fromdate = new java.sql.Date(
							dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					Date todate = new java.sql.Date(
							dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
					wherePart = "txn.txn_date >= '" + fromdate.toString() + "' and txn.txn_date <= '"
							+ todate.toString() + "' and txn.txn_status=0";
					tr.setFilterDetails(
							"Date Between ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromdate)))
							.concat(" to ").concat(String.valueOf(simpleDateFormatHotelDate.format(todate))));
				}
				transactionList = reportService.getTransactionReportData(wherePart);
				tr.setHeader("TRANSACTION DELETED LIST");
				reportTempl.setTransactionList(transactionList);
				if (reportType == 1) {
					mv = new ModelAndView("pdfViewTransactionReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setReportname("TRANSACTION DELETED LIST");
					mv = new ModelAndView("pdfViewTransactionReportExcel", "reportTemplate", reportTempl);
				}
				break;
			case 15:
				if (jobj.get("inpGroup").getAsString().equals("today")
						&& (jobj.get("shiftid").getAsString().equals(null)
								|| jobj.get("shiftid").getAsString().length() == 0)
						&& (jobj.get("userid").getAsString().equals(null)
								|| jobj.get("userid").getAsString().length() == 0)) {
					Date today = new Date(commonSettings.getHotelDate().getTime());
					wherePart = "tbltrfr.txn_date = '" + today.toString() + "' and txn.txn_status=2";
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(today))));
				} else if (jobj.get("inpGroup").getAsString().equals("today")
						&& (jobj.get("shiftid").getAsString() != null || jobj.get("shiftid").getAsString().length() > 0)
						&& (jobj.get("userid").getAsString().equals(null)
								|| jobj.get("userid").getAsString().length() == 0)) {
					Date today = new Date(commonSettings.getHotelDate().getTime());
					wherePart = "tbltrfr.txn_date = '" + today.toString() + "' and txn.shift_id='"
							+ jobj.get("shiftid").getAsString() + "'and txn.txn_status=2";
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(today))));

				} else if (jobj.get("inpGroup").getAsString().equals("today")
						&& (jobj.get("userid").getAsString() != null || jobj.get("userid").getAsString().length() > 0)
						&& (jobj.get("shiftid").getAsString().equals(null)
								|| jobj.get("shiftid").getAsString().length() == 0)) {
					Date today = new Date(commonSettings.getHotelDate().getTime());
					wherePart = "tbltrfr.txn_date = '" + today.toString() + "' and txn.user_id='"
							+ jobj.get("userid").getAsString() + "' and txn.txn_status=2";
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(today))));
					tr.setFilterDetails("User Id :".concat(jobj.get("userid").getAsString()));
				} else if (jobj.get("inpGroup").getAsString().equals("today")
						&& (jobj.get("shiftid").getAsString() != null || jobj.get("shiftid").getAsString().length() > 0)
						&& (jobj.get("userid").getAsString() != null
						|| jobj.get("userid").getAsString().length() > 0)) {
					Date today = new Date(commonSettings.getHotelDate().getTime());
					wherePart = "tbltrfr.txn_date = '" + today.toString() + "' and txn.user_id='"
							+ jobj.get("userid").getAsString() + "' and txn.shift_id='"
							+ jobj.get("shiftid").getAsString() + "' and txn.txn_status=2";
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(today))));
					tr.setFilterDetails("User Id :".concat(jobj.get("userid").getAsString()));
					tr.setFilterDetails("Shift Id :".concat(jobj.get("shiftid").getAsString()));

				}
				if (jobj.get("inpGroup").getAsString().equals("ondate")
						&& (jobj.get("shiftid").getAsString().equals(null)
								|| jobj.get("shiftid").getAsString().length() == 0)
						&& (jobj.get("userid").getAsString().equals(null)
								|| jobj.get("userid").getAsString().length() == 0)) {
					Date ondate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					wherePart = "tbltrfr.txn_date = '" + ondate.toString() + "' and txn.txn_status=2";
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(ondate))));
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")
						&& (jobj.get("shiftid").getAsString() != null || jobj.get("shiftid").getAsString().length() > 0)
						&& (jobj.get("userid").getAsString().equals(null)
								|| jobj.get("userid").getAsString().length() == 0)) {
					Date ondate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					wherePart = "tbltrfr.txn_date = '" + ondate.toString() + "' and txn.shift_id='"
							+ jobj.get("shiftid").getAsString() + "' and txn_status=2";
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(ondate))));
					tr.setFilterDetails("Shift Id :".concat(jobj.get("shiftid").getAsString()));
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")
						&& (jobj.get("userid").getAsString() != null || jobj.get("userid").getAsString().length() > 0)
						&& (jobj.get("shiftid").getAsString().equals(null)
								|| jobj.get("shiftid").getAsString().length() == 0)) {

					Date ondate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					wherePart = "tbltrfr.txn_date = '" + ondate.toString() + "' and txn.user_id='"
							+ jobj.get("userid").getAsString() + "' and txn.txn_status=2";
					tr.setFilterDetails("Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(ondate))));
					tr.setFilterDetails("User Id :".concat(jobj.get("userid").getAsString()));
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")
						&& (jobj.get("shiftid").getAsString() != null || jobj.get("shiftid").getAsString().length() > 0)
						&& (jobj.get("userid").getAsString() != null
						|| jobj.get("userid").getAsString().length() > 0)) {
					Date ondate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					wherePart = "tbltrfr.txn_date = '" + ondate.toString() + "' and txn.user_id='"
							+ jobj.get("userid").getAsString() + "' and txn.shift_id='"
							+ jobj.get("shiftid").getAsString() + "' and txn.txn_status=2";
					tr.setFilterDetails("Date :".concat(String.valueOf(simpleDateFormatHotelDate.format(ondate))));
					tr.setFilterDetails("User Id :".concat(jobj.get("userid").getAsString()));
					tr.setFilterDetails("Shift Id :".concat(jobj.get("shiftid").getAsString()));
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")
						&& (jobj.get("shiftid").getAsString().equals(null)
								|| jobj.get("shiftid").getAsString().length() == 0)
						&& (jobj.get("userid").getAsString().equals(null)
								|| jobj.get("userid").getAsString().length() == 0)) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					Date fromdate = new java.sql.Date(
							dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					Date todate = new java.sql.Date(
							dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
					wherePart = "tbltrfr.txn_date >= '" + fromdate.toString() + "' and tbltrfr.txn_date <= '"
							+ todate.toString() + "' and txn.txn_status=2";
					tr.setFilterDetails(
							"Date Between ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromdate)))
							.concat(" to ").concat(String.valueOf(simpleDateFormatHotelDate.format(todate))));
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")
						&& (jobj.get("shiftid").getAsString() != null || jobj.get("shiftid").getAsString().length() > 0)
						&& (jobj.get("userid").getAsString().equals(null)
								|| jobj.get("userid").getAsString().length() == 0)) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					Date fromdate = new java.sql.Date(
							dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					Date todate = new java.sql.Date(
							dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
					wherePart = "tbltrfr.txn_date >= '" + fromdate.toString() + "' and tbltrfr.txn_date <= '"
							+ todate.toString() + "' and txn.shift_id='" + jobj.get("shiftid").getAsString()
							+ "' and txn_status=2";
					tr.setFilterDetails(
							"Date Between ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromdate)))
							.concat(" to ").concat(String.valueOf(simpleDateFormatHotelDate.format(todate))));
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")
						&& (jobj.get("userid").getAsString() != null || jobj.get("userid").getAsString().length() > 0)
						&& (jobj.get("shiftid").getAsString().equals(null)
								|| jobj.get("shiftid").getAsString().length() == 0)) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					Date fromdate = new java.sql.Date(
							dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					Date todate = new java.sql.Date(
							dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
					wherePart = "tbltrfr.txn_date >= '" + fromdate.toString() + "' and tbltrfr.txn_date <= '"
							+ todate.toString() + "' and txn.user_id='" + jobj.get("userid").getAsString()
							+ "' and txn.txn_status=2";
					tr.setFilterDetails(
							"Date Between ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromdate)))
							.concat(" to ").concat(String.valueOf(simpleDateFormatHotelDate.format(todate))));
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")
						&& (jobj.get("shiftid").getAsString() != null || jobj.get("shiftid").getAsString().length() > 0)
						&& (jobj.get("userid").getAsString() != null
						|| jobj.get("userid").getAsString().length() > 0)) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					Date fromdate = new java.sql.Date(
							dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					Date todate = new java.sql.Date(
							dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
					wherePart = "tbltrfr.txn_date >= '" + fromdate.toString() + "' and tbltrfr.txn_date <= '"
							+ todate.toString() + "' and txn.user_id='" + jobj.get("userid").getAsString()
							+ "' and txn.shift_id='" + jobj.get("shiftid").getAsString() + "' and txn.txn_status=2";
					tr.setFilterDetails(
							"Date Between ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromdate)))
							.concat(" to ").concat(String.valueOf(simpleDateFormatHotelDate.format(todate))));
				}
				transactionTransfrList = reportService.getShiftWiseTxnTransferData(wherePart);
				tr.setHeader("SHIFT WISE TRANSACTION TRANSFER LIST");
				reportTempl.setTransactionTransfrList(transactionTransfrList);
				if (reportType == 1) {
					mv = new ModelAndView("pdfViewshiftWiseTxnTransfrReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setReportname("SHIFT WISE TRANSACTION TRANSFER LIST");
					mv = new ModelAndView("pdfViewshiftWiseTxnTransferReportExcel", "reportTemplate", reportTempl);
				}
				break;
			case 16:
				if (jobj.get("inpGroup").getAsString().equals("today")) {
					Date today = new Date(commonSettings.getHotelDate().getTime());
					wherePart = "checkin_request.req_date = '" + today.toString()
					+ "' and checkin_request.is_canceled='0'";
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(today))));
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					Date ondate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					wherePart = "checkin_request.req_date = '" + ondate.toString()
					+ "' and checkin_request.is_canceled='0'";
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(ondate))));
				}

				requestList = reportService.getRequestData(wherePart);
				tr.setHeader("REQUEST");
				reportTempl.setRequestList(requestList);
				if (reportType == 1) {
					mv = new ModelAndView("pdfViewRequestReport", "reportTemplate", reportTempl);
				} else {

					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setReportname("REQUEST");
					mv = new ModelAndView("pdfViewRequestReportExcel", "reportTemplate", reportTempl);
				}
				break;
			case 17:
				if (jobj.get("inpGroup").getAsString().equals("today")) {
					fromDate = new Date(commonSettings.getHotelDate().getTime());
					toDate = new Date(commonSettings.getHotelDate().getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
				}
				xmlDocument = reportService.getCashRegistersClosureReportList(fromDate, toDate, CASH_REGISTERS_CLOSURE);
				tr.setHeader("");
				tr.setHeaderHeight(10.0f);
				CashRegistersClosureReportTemplate crcTpl = ((CashRegistersClosureReportTemplate) reportTempl);
				crcTpl.setXmlDocument(xmlDocument);
				crcTpl.setCurrencySymbol(curr.getSymbol());
				reportTempl.setDateFormat(dateFormat);
				if (reportType == 1) {
					mv = new ModelAndView("pdfViewCashRegistersClosureReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setReportname("CASH REGISTERS CLOSURE REPORT");
					reportTempl.setDateFilter(tr.getFilterDetails());
					mv = new ModelAndView("pdfViewCashRegistersClosureReportExcel", "reportTemplate", reportTempl);
				}
				break;
			case 18:
				if (jobj.get("inpGroup").getAsString().equals("today")) {
					fromDate = new Date(commonSettings.getHotelDate().getTime());
					toDate = new Date(commonSettings.getHotelDate().getTime());
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate))));
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate))));
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
					tr.setFilterDetails(
							"Date Between ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate)))
							.concat(" to ").concat(String.valueOf(simpleDateFormatHotelDate.format(toDate))));
				}
				occupancyReport = reportService.getOccupancyList(fromDate, toDate);
				tr.setHeader("OCCUPANCY LIST");
				reportTempl.setOccupancyReportList(occupancyReport);
				if (reportType == 1) {
					mv = new ModelAndView("pdfViewOccupancyReportList", "reportTemplate", reportTempl);
				} else {
					reportTempl.setReportname("OCCUPANCY LIST");
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setDateFilter(tr.getFilterDetails());
					mv = new ModelAndView("pdfViewOccupancyReportListExcel", "reportTemplate", reportTempl);
				}
				break;

			case 19:
				if (jobj.get("inpGroup").getAsString().equals("today")) {
					fromDate = new Date(commonSettings.getHotelDate().getTime());
					toDate = new Date(commonSettings.getHotelDate().getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
				}
				revenueReport = reportService.getRevenueReport(fromDate, toDate);
				reportTempl.setDateFormat(dateFormat);
				reportTempl.setRevenueReport(revenueReport);
				// tr.setHeader("DAILY REVENUE REPORT");
				// CashRegistersClosureReportTemplate currTempl = new
				// CashRegistersClosureReportTemplate();
				// currTempl.setCurrencySymbol(curr.getSymbol());
				reportTempl.setSymbol(curr.getSymbol());
				if (reportType == 1) {
					// reportTempl.setDateFilter(tr.getFilterDetails());
					mv = new ModelAndView("pdfViewRevenueReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					mv = new ModelAndView("pdfViewRevenueReportExcel", "reportTemplate", reportTempl);
				}

				break;

			case 20:
				getMonth = jobj.get("monthId").getAsInt();
				year = jobj.get("yearId").getAsString();
				monthlyReport = reportService.getMonthlyClosure(getMonth, year);
				tr.setHeader("MONTHLY CLOSURE REPORT");
				String month = "Month of";
				switch (getMonth) {
				case 1:
					tr.setFilterDetails(month + " January " + year);
					break;
				case 2:
					tr.setFilterDetails(month + " February " + year);
					break;
				case 3:
					tr.setFilterDetails(month + " March " + year);
					break;
				case 4:
					tr.setFilterDetails(month + " April " + year);
					break;
				case 5:
					tr.setFilterDetails(month + " May " + year);
					break;
				case 6:
					tr.setFilterDetails(month + " June " + year);
					break;
				case 7:
					tr.setFilterDetails(month + " July " + year);
					break;
				case 8:
					tr.setFilterDetails(month + " August " + year);
					break;
				case 9:
					tr.setFilterDetails(month + " September " + year);
					break;
				case 10:
					tr.setFilterDetails(month + " October " + year);
					break;
				case 11:
					tr.setFilterDetails(month + " November " + year);
					break;
				case 12:
					tr.setFilterDetails(month + " December " + year);
					break;
				}
				reportTempl.setMonthlyClosureReport(monthlyReport);
				if (reportType == 1) {
					mv = new ModelAndView("pdfMonthlyClosureReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setReportname("MONTHLY CLOSURE REPORT");
					reportTempl.setDateFilter(tr.getFilterDetails());
					mv = new ModelAndView("pdfMonthlyClosureReportExcel", "reportTemplate", reportTempl);
				}
				break;
			case 21:
				if (jobj.get("inpGroup").getAsString().equals("today")) {
					fromDate = new Date(commonSettings.getHotelDate().getTime());
					toDate = new Date(commonSettings.getHotelDate().getTime());
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate))));
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate))));
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
					tr.setFilterDetails(
							"Date Between ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate)))
							.concat(" to ").concat(String.valueOf(simpleDateFormatHotelDate.format(toDate))));
				}
				roomPerDay = reportService.getRoomPerDay(fromDate, toDate);
				tr.setHeader("ROOM PER DAY REPORT");
				reportTempl.setDateFormat(dateFormat);
				reportTempl.setRoomsPerDayList(roomPerDay);
				if (reportType == 1) {
					mv = new ModelAndView("pdfRoomsPerDayReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setReportname("ROOM PER DAY REPORT");
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setDateFilter(tr.getFilterDetails());
					mv = new ModelAndView("pdfRoomsPerDayReportExcel", "reportTemplate", reportTempl);
				}
				break;
			case 22:
				if (jobj.get("inpGroup").getAsString().equals("today")) {
					fromDate = new Date(commonSettings.getHotelDate().getTime());
					toDate = new Date(commonSettings.getHotelDate().getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
				}
				roomPlanList = reportService.getRoomPlanReport(fromDate, toDate);
				reportTempl.setRoomPlanListReport(roomPlanList);
				reportTempl.setDateFormat(dateFormat);
				if (reportType == 1) {
					mv = new ModelAndView("pdfRoomsPlanReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setReportname("ROOM AND RATE PLAN");
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					mv = new ModelAndView("pdfRoomsPlanReportExcel", "reportTemplate", reportTempl);
				}
				break;

			case 23:
				String jsonCustSearch = "";
				if (null != jobj.get("searchType")) {
					jsonCustSearch = jobj.get("searchType").getAsString();
				}
				JsonObject jsonDtBtwn = jobj.get("input").getAsJsonObject();
				fromDate = new java.sql.Date(dateFormatFrom.parse(jsonDtBtwn.get("dateFrom").getAsString()).getTime());
				toDate = new java.sql.Date(dateFormatFrom.parse(jsonDtBtwn.get("dateTo").getAsString()).getTime());
				wherePart = "checkin_hdr.arr_date >= '" + fromDate.toString() + "' and checkin_hdr.arr_date <= '"
						+ toDate.toString() + "'";
				customerReport = reportService.getCustomerReportList(jsonCustSearch, fromDate, toDate);
				tr.setFilterDetails("Date Between ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate)))
						.concat(" to ").concat(String.valueOf(simpleDateFormatHotelDate.format(toDate))));
				tr.setHeader("CUSTOMER HISTORY REPORT");
				reportTempl.setCustomerReport(customerReport);
				reportTempl.setDateFormat(dateFormat);
				if (reportType == 1) {
					tr.getFilterDetails();
					mv = new ModelAndView("pdfViewCustomerReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setReportname("CUSTOMER HISTORY REPORT");
					mv = new ModelAndView("pdfViewCustomerReportExcel", "reportTemplate", reportTempl);
				}
				break;
			case 24:
				incomeReportList = reportService.getIncomeReportList(fromDate, toDate);
				tr.setHeader("COLLECTION REGISTER REPORT");
				tr.setReportType(0);
				reportTempl.setIncomeReportList(incomeReportList);
				if (reportType == 1) {
					mv = new ModelAndView("pdfViewIncomeReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setReportname("COLLECTION REGISTER REPORT");
					mv = new ModelAndView("pdfViewIncomeReportExcel", "reportTemplate", reportTempl);
				}
				break;

			case 25:

				JsonObject jsonDtbtwn = jobj.get("input").getAsJsonObject();
				fromDate = new java.sql.Date(dateFormatFrom.parse(jsonDtbtwn.get("dateFrom").getAsString()).getTime());
				toDate = new java.sql.Date(dateFormatFrom.parse(jsonDtbtwn.get("dateTo").getAsString()).getTime());

				roomBookingFrequency = reportService.getRoomBookingFrequency(fromDate, toDate);
				tr.setFilterDetails("Date Between ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate)))
						.concat(" to ").concat(String.valueOf(simpleDateFormatHotelDate.format(toDate))));
				tr.setHeader("ROOM BOOKING FREQUENCY REPORT");
				reportTempl.setRoomBookingFrequency(roomBookingFrequency);
				reportTempl.setDateFormat(dateFormat);
				if (reportType == 1) {
					reportTempl.setDateFilter(tr.getFilterDetails());
					mv = new ModelAndView("pdfViewRoomBookingFrequencyReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setReportname("ROOM BOOKING FREQUENCY REPORT");
					mv = new ModelAndView("pdfViewRoomBookingFrequencyReportExcel", "reportTemplate", reportTempl);
				}

				break;

			case 26:

				if (jobj.get("inpGroup").getAsString().equals("today")) {
					fromDate = new Date(commonSettings.getHotelDate().getTime());
					toDate = new Date(commonSettings.getHotelDate().getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
				}
				System.out.println("inside "+toDate+" " +fromDate);
				customerOutstandingList = reportService.getCustomerOutstandingDetails(fromDate, toDate, corpId);
				
				tr.setFilterDetails("Date Between ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate)))
						.concat(" to ").concat(String.valueOf(simpleDateFormatHotelDate.format(toDate))).concat("and")
						.concat(String.valueOf(corpId)));
				tr.setHeader("CUSTOMER OUTSTANDING REPORT");
				reportTempl.setCustomerOutstandingList(customerOutstandingList);
				reportTempl.setDateFormat(dateFormat);
				
				System.out.println(reportTempl.getDateFormat());
				System.out.println(reportTempl.getCustomerOutstandingList().get(1).getCorporate_name());
				
				if (reportType == 1) {
					mv = new ModelAndView("pdfCustomerOutstandingReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setReportname("CUSTOMER OUTSTANDING REPORT");
					mv = new ModelAndView("pdfRoomsPlanReportExcel", "reportTemplate", reportTempl);
				}

				break;

			case 27:

				if (jobj.get("inpGroup").getAsString().equals("today")) {
					fromDate = new Date(commonSettings.getHotelDate().getTime());
					toDate = new Date(commonSettings.getHotelDate().getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
				}
				customerGradingList = reportService.getCustomerGradingDetails(fromDate, toDate);
				tr.setFilterDetails("Date Between ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate)))
						.concat(" to ").concat(String.valueOf(simpleDateFormatHotelDate.format(toDate))));
				tr.setHeader("CUSTOMER GRADING REPORT");
				reportTempl.setCustomerGradingList(customerGradingList);
				reportTempl.setDateFormat(dateFormat);
				if (reportType == 1) {
					mv = new ModelAndView("pdfCustomerGradingReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setReportname("CUSTOMER OUTSTANDING REPORT");
					mv = new ModelAndView("pdfCustomerGradingReportExcel", "reportTemplate", reportTempl);

				}

				break;

			case 29:

				JsonObject objDtbtwn = jobj.get("input").getAsJsonObject();
				fromDate = new java.sql.Date(dateFormatFrom.parse(objDtbtwn.get("dateFrom").getAsString()).getTime());
				toDate = new java.sql.Date(dateFormatFrom.parse(objDtbtwn.get("dateTo").getAsString()).getTime());
				// wherePart = "checkin_hdr.arr_date >= '"+ fromDate.toString()+"' and
				// checkin_hdr.arr_date <= '"+ toDate.toString()+"'";
				corporateList = reportService.getCorporateNames(fromDate, toDate);
				tr.setFilterDetails("Date Between ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate)))
						.concat(" to ").concat(String.valueOf(simpleDateFormatHotelDate.format(toDate))));
				tr.setHeader("CORPORATE LIST");
				reportTempl.setDateFormat(dateFormat);

				reportTempl.setDateFilter(tr.getFilterDetails());
				reportTempl.setReportname("CORPORATE LIST");
				mv = new ModelAndView("excelCorporateList", "reportTemplate", corporateList);

				break;

			case 30:

				if (jobj.get("inpGroup").getAsString().equals("today")) {
					fromDate = new Date(commonSettings.getHotelDate().getTime());
					toDate = new Date(commonSettings.getHotelDate().getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
				}
				JsonArray BookingFrequencyList = reportService.getBookingFrequencyList(fromDate, toDate);
				if (reportType == 1) {
					mv = new ModelAndView("pdfFrequentGuestAnalysisReport", "bookingData", BookingFrequencyList);
				} else {
					JsonObject companyList = reportService.getCompany();
					mv = new ModelAndView("pdfFrequentGuestAnalysisReportExcel", "bookingData", BookingFrequencyList);
					mv.addObject("companyData", companyList);
				}
				break;
			case 31:
				if (jobj.get("inpGroup").getAsString().equals("today")) {
					fromDate = new Date(commonSettings.getHotelDate().getTime());
					toDate = new Date(commonSettings.getHotelDate().getTime());
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate))));
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate))));
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
					tr.setFilterDetails(
							"Date Between ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate)))
							.concat(" to ").concat(String.valueOf(simpleDateFormatHotelDate.format(toDate))));
				}
				pettyExpenseList=reportService.getPettyCashList(fromDate,toDate);
				tr.setHeader("PETTY CASH EXPENSE LIST");
				reportTempl.setDateFormat(dateFormat);
				reportTempl.setPettyCashList(pettyExpenseList);
				
				if (reportType == 1) {
					mv = new ModelAndView("pdfViewPettyCashExpenseReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setReportname("PETTY EXPENSE REPORT");
					mv = new ModelAndView("pdfPettyCashReportExcel", "reportTemplate", reportTempl);
					//mv.addObject("companyData", companyList);
				}
				break;
				
			case 35:
				if (jobj.get("inpGroup").getAsString().equals("today")) {
					fromDate = new Date(commonSettings.getHotelDate().getTime());
					toDate = new Date(commonSettings.getHotelDate().getTime());
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate))));
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate))));
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
					tr.setFilterDetails(
							"Date Between ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate)))
							.concat(" to ").concat(String.valueOf(simpleDateFormatHotelDate.format(toDate))));
				}
				pettyExportList=reportService.getPettyCashExportList(fromDate,toDate);
				tr.setHeader("PETTY CASH BOOK");
				reportTempl.setDateFormat(dateFormat);
				reportTempl.setPettyCashList(pettyExportList);
				
				if (reportType == 1) {
					mv = new ModelAndView("pdfViewPettyCashExportReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setReportname("PETTY CASH BOOK");
					mv = new ModelAndView("pdfPettyCashExportReportExcel", "reportTemplate", reportTempl);
					//mv.addObject("companyData", companyList);
				}
				break;
				
				
			case 37:
				if (jobj.get("inpGroup").getAsString().equals("today")) {
					fromDate = new Date(commonSettings.getHotelDate().getTime());
					toDate = new Date(commonSettings.getHotelDate().getTime());
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate))));
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate))));
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
					tr.setFilterDetails(
							"Date Between ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate)))
							.concat(" to ").concat(String.valueOf(simpleDateFormatHotelDate.format(toDate))));
				}
				creditCardExportList=reportService.getCreditCardDataList(fromDate,toDate);
				tr.setHeader("Icici Credit Card");
				reportTempl.setDateFormat(dateFormat);
				reportTempl.setPettyCashList(creditCardExportList);
				
				if (reportType == 1) {
					mv = new ModelAndView("pdfViewCreditCardExportReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					reportTempl.setDateFilter(tr.getFilterDetails());
					reportTempl.setReportname("Icici Credit Card");
					mv = new ModelAndView("pdfViewCreditCardExportReportExcel", "reportTemplate", reportTempl);
					//mv.addObject("companyData", companyList);
				}
				break;
			
			
				
			case 38:
			
				if (jobj.get("inpGroup").getAsString().equals("today")) {
					fromDate = new Date(commonSettings.getHotelDate().getTime());
					toDate = new Date(commonSettings.getHotelDate().getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
				}
				
				revenueReportDetail = reportService.getRevenueReportDetail(fromDate, toDate,reportSelected);
				reportTempl.setDateFormat(dateFormat);
				reportTempl.setRevenueReportDetail(revenueReportDetail);
			
				reportTempl.setSymbol(curr.getSymbol());
				if (reportType == 1) {
					
					mv = new ModelAndView("pdfViewRevenueReportDetail", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					mv = new ModelAndView("pdfViewRevenueReportDetailExcel", "reportTemplate", reportTempl);
				}

				break;

			
			case 39:
			
				if (jobj.get("inpGroup").getAsString().equals("today")) {
					fromDate = new Date(commonSettings.getHotelDate().getTime());
					toDate = new Date(commonSettings.getHotelDate().getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
				}
				revenueReportDetail = reportService.getFdRevenueReportDetail(fromDate, toDate,reportSelected);
				reportTempl.setDateFormat(dateFormat);
				reportTempl.setRevenueReportDetail(revenueReportDetail);
			
				reportTempl.setSymbol(curr.getSymbol());
				if (reportType == 1) {
					
					mv = new ModelAndView("pdfViewFdRevenueReportDetail", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					mv = new ModelAndView("pdfViewFdRevenueReportDetailExcel", "reportTemplate", reportTempl);
				}

				break;

			
			
			case 40:
			
				if (jobj.get("inpGroup").getAsString().equals("today")) {
					fromDate = new Date(commonSettings.getHotelDate().getTime());
					toDate = new Date(commonSettings.getHotelDate().getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
				}
				revenueReportDetail = reportService.getPosRevenueReport(fromDate, toDate);
				reportTempl.setDateFormat(dateFormat);
				reportTempl.setRevenueReportDetail(revenueReportDetail);
				// tr.setHeader("DAILY REVENUE REPORT");
				// CashRegistersClosureReportTemplate currTempl = new
				// CashRegistersClosureReportTemplate();
				// currTempl.setCurrencySymbol(curr.getSymbol());
				reportTempl.setSymbol(curr.getSymbol());
				if (reportType == 1) {
					// reportTempl.setDateFilter(tr.getFilterDetails());
					mv = new ModelAndView("pdfViewPOSRevenueReportDetail", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					mv = new ModelAndView("pdfViewPOSRevenueReportDetailExcel", "reportTemplate", reportTempl);
				}

				break;
				
				
			case 41:
				
				if (jobj.get("inpGroup").getAsString().equals("today")) {
					fromDate = new Date(commonSettings.getHotelDate().getTime());
					toDate = new Date(commonSettings.getHotelDate().getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
				}
				B2BReport = reportService.getB2BReport(fromDate, toDate);
				reportTempl.setDateFormat(dateFormat);
				reportTempl.setB2BReport(B2BReport);
				// tr.setHeader("DAILY REVENUE REPORT");
				// CashRegistersClosureReportTemplate currTempl = new
				// CashRegistersClosureReportTemplate();
				// currTempl.setCurrencySymbol(curr.getSymbol());
				reportTempl.setSymbol(curr.getSymbol());
				if (reportType == 1) {
					// reportTempl.setDateFilter(tr.getFilterDetails());
					mv = new ModelAndView("pdfViewB2BReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					mv = new ModelAndView("pdfViewB2BReportExcel", "reportTemplate", reportTempl);
				}

				break;
				
			case 42:
				
				if (jobj.get("inpGroup").getAsString().equals("today")) {
					fromDate = new Date(commonSettings.getHotelDate().getTime());
					toDate = new Date(commonSettings.getHotelDate().getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
					JsonObject jobjInp = jobj.get("input").getAsJsonObject();
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
				}
				B2CReport = reportService.getB2CReport(fromDate, toDate);
				reportTempl.setDateFormat(dateFormat);
				reportTempl.setB2CReport(B2CReport);
				// tr.setHeader("DAILY REVENUE REPORT");
				// CashRegistersClosureReportTemplate currTempl = new
				// CashRegistersClosureReportTemplate();
				// currTempl.setCurrencySymbol(curr.getSymbol());
				reportTempl.setSymbol(curr.getSymbol());
				if (reportType == 1) {
					// reportTempl.setDateFilter(tr.getFilterDetails());
					mv = new ModelAndView("pdfViewB2CReport", "reportTemplate", reportTempl);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					mv = new ModelAndView("pdfViewB2CReportExcel", "reportTemplate", reportTempl);
				}

				break;
				
			case 43:

				if (jobj.get("inpGroup").getAsString().equals("today")) {
					fromDate = new Date(commonSettings.getHotelDate().getTime());
					toDate = new Date(commonSettings.getHotelDate().getTime());
				} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
					fromDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
					toDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
				}
			//	tr.setFilterDetails("On Date ".concat(String.valueOf(simpleDateFormatHotelDate.format(fromDate))));
				  
				reportTempl.setDayEndReport(reportService.getDayEndDetials(fromDate));
			
				JsonArray summaryData = reportService.getDayEndSummary(fromDate);
				JsonObject obj = (JsonObject) summaryData.get(0);
				reportTempl.setOpeningBal(obj.get("cash_opening_balance").getAsDouble());
				reportTempl.setCashPaymentTotal(obj.get("total_cash_payment").getAsDouble());
				reportTempl.setNonCashPaymentTotal(obj.get("total_non_cash_payment").getAsDouble());
				reportTempl.setBookingCashPaymentTotal(obj.get("total_cash_booking").getAsDouble());
				reportTempl.setBookingNonCashPaymentTotal(obj.get("total_non_cash_booking").getAsDouble());
				reportTempl.setPettyExpenseTotal(obj.get("total_petty_expense").getAsDouble());
				reportTempl.setCashToOfficeTotal(obj.get("total_cash_to_office").getAsDouble());
				reportTempl.setDepositTotal(obj.get("total_deposit").getAsDouble());
				reportTempl.setFoodCostTotal(obj.get("total_food_cost").getAsDouble());
				reportTempl.setPettyclosingAmount(obj.get("petty_closing_amount").getAsDouble());
				reportTempl.setRefundTotal(obj.get("refund_total").getAsDouble());
				reportTempl.setComplementaryTotal(obj.get("complementary_total").getAsDouble());
				reportTempl.setContraTotal(obj.get("total_contra").getAsDouble());
				
				reportTempl.setDateFormat(dateFormat);
				if (reportType == 1) {
					mv = new ModelAndView("pdfViewDayEndReport", "reportTemplate", reportTempl);
				//	mv.addObject("companyData", companyList);
				} else {
					reportTempl.setCompanyname(tr.getName());
					reportTempl.setBuilding(tr.getBuilding());
					reportTempl.setStreet(tr.getStreet());
					reportTempl.setCity(tr.getCity());
					//reportTempl.setDateFilter(tr.getFilterDetails());
					//reportTempl.setReportname("DAY END REPORT");

					reportTempl.setReportname("");
					mv = new ModelAndView("pdfViewDayEndReportExcel", "reportTemplate", reportTempl);

				}
				break;

			
			}

			reportTempl.setHaveHeaderFooter(true);
			reportTempl.setReportHeaderFooter(tr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping(value = "tallyExport", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> TallyExport(@RequestParam(value = "inpData") String inpData,
			HttpSession session, HttpServletRequest request) throws Exception {
		SimpleDateFormat dateFormatFrom = new SimpleDateFormat(session.getAttribute("dateFormat").toString());
		JsonParser parser = new JsonParser();
		JsonObject jobj = parser.parse(inpData).getAsJsonObject();
		Date fromDate = null;
		Date toDate = null;
		String absolutePath = "";
		int reportId = jobj.get("repId").getAsInt();
		
       
        
		if (jobj.get("inpGroup").getAsString().equals("today")) {
			fromDate = new Date(commonSettings.getHotelDate().getTime());
			toDate = new Date(commonSettings.getHotelDate().getTime());
		} else if (jobj.get("inpGroup").getAsString().equals("ondate")) {
			fromDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
			toDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("input").getAsString()).getTime());
		} else if (jobj.get("inpGroup").getAsString().equals("datebtwn")) {
			JsonObject jobjInp = jobj.get("input").getAsJsonObject();
			fromDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateFrom").getAsString()).getTime());
			toDate = new java.sql.Date(dateFormatFrom.parse(jobjInp.get("dateTo").getAsString()).getTime());
		}
		String companyName = session.getAttribute("companyName").toString();
		String finaY = reportService.getFinancialYear(fromDate);
		Date finaYFrom = reportService.getFinancialYearFrom(fromDate);
		
		SimpleDateFormat simpleformat = new SimpleDateFormat("dd-MMMM-yyyy");
	    
		
	     String titleHead = "";
	     if(finaY!="" && finaY!=null) {
	    	 String str = simpleformat.format(finaYFrom);
		 titleHead = companyName + " " +"F.Y"+" "+finaY+"-(from "+str+")";
	     }
		System.out.println(titleHead);
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream input = null;
		Properties prop = new Properties();
		input = loader.getResourceAsStream("/export.properties");
		prop.load(input);
		File createFold=new File(prop.getProperty("exportTallyPath"));
		// if the directory does not exist, create it
		if(!createFold.exists()) {
			createFold.getParentFile().mkdirs();
			createFold.mkdir();
		}
		if (reportId == 28) {
			JsonArray tallyData = reportService.getTallyData(fromDate, toDate);
			TallyExportXml tallyExportXml = new TallyExportXml();
			absolutePath = tallyExportXml.tallyExport(tallyData, prop,titleHead);
		} else if (reportId == 29) {
			JsonArray ledgerData = reportService.getLedgerData(fromDate, toDate);
			TallyExportXml tallyExportXml = new TallyExportXml();
			absolutePath = tallyExportXml.ledgerExport(ledgerData, prop);
		}else if(reportId==32) {
			 int isContra=1;
			JsonArray contraData=reportService.getContraData(fromDate, toDate,isContra);
			TallyExportXml tallyExportXml = new TallyExportXml();
			absolutePath = tallyExportXml.contraExport(contraData, prop);
		}else if(reportId==33) {
			int isContra=0;
			JsonArray contraData=reportService.getContraData(fromDate, toDate,isContra);
			TallyExportXml tallyExportXml = new TallyExportXml();
			absolutePath = tallyExportXml.PaymentExport(contraData, prop);
		}else if(reportId==34) {
			JsonArray contraData=reportService.getPettyLedgerData(fromDate, toDate);
			TallyExportXml tallyExportXml = new TallyExportXml();
			absolutePath = tallyExportXml.PettyLedgerExport(contraData, prop);
		}
		
		else if(reportId==36) {
			JsonArray contraData=reportService.getCreditCardData(fromDate, toDate);
			TallyExportXml tallyExportXml = new TallyExportXml();
			absolutePath = tallyExportXml.CreditCardExport(contraData, prop);
		}

		System.out.println(absolutePath);
		File file = new File(absolutePath);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		final HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, file.getName());
		headers.add("Access_Control_Expose_Headers", HttpHeaders.CONTENT_DISPOSITION);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, file.getName()).headers(headers)
				.contentType(MediaType.TEXT_PLAIN).contentLength(file.length()).body(resource);
	}

	private ReportTemplate getReportTemplate(int report_id) {

		ReportTemplate rptTemplate = null;
		switch (report_id) {
		case 17:
			rptTemplate = new CashRegistersClosureReportTemplate();
			break;
		default:
			rptTemplate = new ReportTemplate();
		}
		return rptTemplate;
	}

	@RequestMapping(value = "PettyReport", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> PettyReport(@RequestParam(value = "inpData") String inpData,
			HttpSession session, HttpServletRequest request) throws Exception {

		return null;
	}
}
