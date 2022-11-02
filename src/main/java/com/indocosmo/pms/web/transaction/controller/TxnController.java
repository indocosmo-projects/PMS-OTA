package com.indocosmo.pms.web.transaction.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.indocosmo.pms.enumerator.PaymentMode;
import com.indocosmo.pms.enumerator.TxnSource;
import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.checkIn.model.CheckInHdr;
import com.indocosmo.pms.web.checkIn.model.CheckInRate;
import com.indocosmo.pms.web.checkIn.service.CheckInService;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.reservation.Folio;
import com.indocosmo.pms.web.reservation.service.ReservationService;
import com.indocosmo.pms.web.shiftManagement.model.ShiftManagement;
import com.indocosmo.pms.web.shiftManagement.service.ShiftManagementService;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;
import com.indocosmo.pms.web.tax.model.TaxHdr;
import com.indocosmo.pms.web.transaction.model.Transaction;
import com.indocosmo.pms.web.transaction.service.TxnService;

@Controller
@RequestMapping(value = "/transaction")
public class TxnController {

	public static final String TRANSACTION_LIST_PAGE_URL = "transaction/transactions_list";
	public static final String TRANSACTION_EDIT_PAGE_URL = "transaction/transactions_edit";
	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "access_denied/access_denied";

	@Autowired
	CheckInService checkInService;
	@Autowired
	TxnService transactionService;

	@Autowired
	ReservationService reservationService;

	@Autowired
	SystemSettingsService systemSettingsService;

	@Autowired
	private ShiftManagementService shiftManagementService;

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	private SysPermissions permissionObj;

	public static final Logger logger = LoggerFactory.getLogger(TxnController.class);

	/*
	 * method to redirect to list page and folioNo is setted as model attribute(for
	 * the first time during posting)
	 */
	@RequestMapping(value = "/recieptDetails")
	public String recieptDetails(@RequestParam(value = "checkinNo") int checkinNo,
			@RequestParam(value = "folioBindNo", required = false) String folioBindNo, HttpSession session, Model model)
			throws Exception {
		int chkFolioBindNo = 0;
		if (folioBindNo != null && folioBindNo != "") {
			chkFolioBindNo = Integer.parseInt(folioBindNo);
		}
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_RECPN_POSTNG");
		String pageUrl = TRANSACTION_LIST_PAGE_URL;
		if (permissionObj.isCanView() && permissionObj.isIs_view_applicable()) {
			model.addAttribute("curPagePerObj", permissionObj);
			double totalDeposit = 0.0;
			SystemSettings systemSettings = systemSettingsService.getSystemSettings();
			List<AccountMaster> accList = transactionService.getAccountMasterDetails();
			List<AccountMaster> posAccountMaster = transactionService.getPosAccountMasterDetails();
			
			CheckInHdr checkIn = new CheckInHdr();
			checkIn = transactionService.getcheckinDetails(checkinNo);
			Folio commonTxnIds = new Folio();
			commonTxnIds = transactionService.getFolioDetails(checkinNo);
			List<CheckInRate> checkInRoomCharge = checkInService.getRoomCharge(checkinNo);
		//	JsonObject jobject = new JsonObject();
			String folBlnc = checkIn.getFolioBalance();
			String htlDate = (String) session.getAttribute("hotelDate");
			String[] htlDte = htlDate.split(" ");
			String hotelDate = htlDte[0];

			String hotelDateshift = systemSettings.getHotelDate().toString();
			String count = shiftManagementService.isshiftOPen(hotelDateshift);
			model.addAttribute("count", count);

			totalDeposit = reservationService.getDepositAmount(commonTxnIds.getFolio_bind_no());
			session.setAttribute("folioBalance", folBlnc);
			session.setAttribute("txnfolioBindNo", commonTxnIds.getFolio_bind_no());
			session.setAttribute("txnfolioNo", commonTxnIds.getFolio_no());
			model.addAttribute("checkInNo", checkIn.getCheckInNo());
			model.addAttribute("isFullySettled", checkIn.isFullySettled());
			model.addAttribute("checkInDate", checkIn.getArrDate());
			model.addAttribute("folioBalance", checkIn.getFolioBalance());
			model.addAttribute("departDate", checkIn.getExpDepartDate());
			model.addAttribute("folioNo", commonTxnIds.getFolio_no());
			model.addAttribute("name", checkIn.getName());
			model.addAttribute("phone", checkIn.getPhone());
			model.addAttribute("email", checkIn.getEmail());
			model.addAttribute("address", checkIn.getAddress());
			model.addAttribute("roomNo", checkIn.getRoomNumber());
			model.addAttribute("roomType", checkIn.getRoomTypeCode());
			model.addAttribute("totalDeposit", totalDeposit);
			model.addAttribute("paymentModeEnum", PaymentMode.values());
			model.addAttribute("hotelDate", hotelDate);
			model.addAttribute("accountMaster", accList);
			model.addAttribute("posAccountMaster", posAccountMaster);
			model.addAttribute("RoomChargeCheckIn", checkInRoomCharge);
			model.addAttribute("systemSettings", systemSettings);
			model.addAttribute("checkOutFolioBindNo", chkFolioBindNo);
			model.addAttribute("txn_edit_after_payment", transactionService.editAcessValue("txn_edit_after_payment"));
		} else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}

		return pageUrl;
	}

	@RequestMapping(value = "/transactionList", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String transactionList(@RequestParam(value = "folioNo") int folioNo) {
		// System.out.println(folioNo);
		JsonArray paymentJson = new JsonArray();
		paymentJson = transactionService.getTransactionDetails(folioNo);
		return paymentJson.toString();
	}

	@RequestMapping(value = "/getCharges", method = RequestMethod.POST)
	public @ResponseBody Transaction getCharges(@RequestParam(value = "amount") double amount,
			@RequestParam(value = "accMstId") int accMst, @RequestParam(value = "isBase") boolean isBase,
			@RequestParam(value = "chkInNo") int chkInNo, HttpSession session) throws Exception {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = sdf1.parse(session.getAttribute("hotelDate").toString());
		java.sql.Date hotelDate = new Date(date.getTime());
		Transaction txn = null;
		txn = transactionService.getCharges(hotelDate, amount, accMst, isBase, chkInNo);
		return txn;
	}

	@RequestMapping(value = "/saveTxn", method = RequestMethod.POST)
	public @ResponseBody String saveTxn(@ModelAttribute AccountMaster accm,
			@ModelAttribute ShiftManagement currntShiftId, @ModelAttribute TaxHdr taxHdr,
			@RequestParam(value = "txnDate") String txnDate,
			@RequestParam(value = "amount") double amount, @RequestParam(value = "accMstId") int accMst,
			@RequestParam(value = "isBase") boolean isBase, @RequestParam(value = "isAdj") boolean isAdj,
			@RequestParam(value = "remarks") String remarks, @RequestParam(value = "chkInNo") int chkInNo,
			HttpSession session) throws Exception {
		Gson g = new Gson();
		String status = "success";
		String[] htlDte = session.getAttribute("hotelDate").toString().split(" ");
		String hotelDat = htlDte[0];
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = sdf1.parse(hotelDat);
		java.sql.Date hotelDate = new Date(date.getTime());
		int userId = ((User) session.getAttribute("userForm")).getId();
		Transaction txn = null;
		txn = transactionService.getCharges(hotelDate, amount, accMst, isBase, chkInNo);
		txn.setAmount(amount);
		
		//txn.setNett_amount(-(txn.getNett_amount()));
		txn.setAcc_mst_id(accMst);
		String htlDate = (String) session.getAttribute("hotelDate");
		String[] htlDte1 = htlDate.split(" ");
		String hotelDate1 = htlDte1[0];
		currntShiftId = shiftManagementService.getShiftManagementDetails(hotelDate1);
		txn.setShift_id(currntShiftId.getShiftId());
		accm = transactionService.accMstId(accMst);
		if(accm.getCode().toString().equals("POS-REFUND") ||accm.getCode().toString().equals("REFUND" )||  accm.getCode().toString().equals("COMPLEMENTARY")) {
			txn.setNett_amount(Math.abs(txn.getNett_amount()));
		}
		else {
			txn.setNett_amount(-(txn.getNett_amount()));
		}
		txn.setAcc_mst_code(accm.getCode());
		txn.setTxn_source(TxnSource.FRONTOFFICEPOSTING.getCode());
		txn.setFolio_no(Integer.parseInt(session.getAttribute("txnfolioNo").toString()));
		txn.setFolio_bind_no(Integer.parseInt(session.getAttribute("txnfolioBindNo").toString()));
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String payTime = dateFormat.format(cal.getTime());
		txn.setTxn_date(hotelDat);
		if(txnDate !=null && txnDate != "" ) {
			txn.setTxn_date(txnDate);
		}
		
		txn.setTxn_time(payTime);
		taxHdr = transactionService.getTaxCode(accm.getTax_id());
		if (accMst != 1) {
			txn.setTax_id(accm.getTax_id());
			txn.setTax_code(taxHdr.getCode());
		}
		boolean incTax = true;
		if (isBase) {
			incTax = false;
		}
		txn.setInclude_tax(incTax);
		txn.setPayment_mode(0);
		txn.setTxn_status(1);
		txn.setIs_adjust(isAdj);
		txn.setRemarks(remarks);
		txn.setUser_id(userId);
		status = transactionService.save(txn);
		status = "status:" + status;
		return g.toJson(status).toString();
	}

	@RequestMapping(value = "/updateTxn", method = RequestMethod.POST)
	public @ResponseBody String update(@ModelAttribute AccountMaster accm,
			@ModelAttribute ShiftManagement currntShiftId, @ModelAttribute TaxHdr taxHdr,
			@RequestParam(value = "txnDate") String txnDate,
			@RequestParam(value = "amount") double amount, @RequestParam(value = "accMstId") int accMst,
			@RequestParam(value = "isBase") boolean isBase, @RequestParam(value = "isAdj") boolean isAdj,
			@RequestParam(value = "remarks") String remarks, @RequestParam(value = "txnId") int txnId,
			@RequestParam(value = "chkInNo") int chkInNo, HttpSession session) throws Exception {
		Gson g = new Gson();
		String status = "success";
		String[] htlDte = session.getAttribute("hotelDate").toString().split(" ");
		String hotelDat = htlDte[0];
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = sdf1.parse(hotelDat);
		java.sql.Date hotelDate = new Date(date.getTime());
		int userId = ((User) session.getAttribute("userForm")).getId();
		Transaction txn = null;
		txn = transactionService.getCharges(hotelDate, amount, accMst, isBase, chkInNo);
		if(accMst == 4) {
			txn.setAmount(amount);
			txn.setNett_amount((txn.getNett_amount()));
		}
		else {
			txn.setAmount(amount);
			txn.setNett_amount(-(txn.getNett_amount()));
		}
		
		String htlDate = (String) session.getAttribute("hotelDate");
		String[] htlDte1 = htlDate.split(" ");
		String hotelDate1 = htlDte1[0];
		currntShiftId = shiftManagementService.getShiftManagementDetails(hotelDate1);
		txn.setShift_id(currntShiftId.getShiftId());
		txn.setAcc_mst_id(accMst);
		accm = transactionService.accMstId(accMst);
		txn.setAcc_mst_code(accm.getCode());
		taxHdr = transactionService.getTaxCode(accm.getTax_id());
		if (accMst != 1) {
			txn.setTax_id(accm.getTax_id());
			txn.setTax_code(taxHdr.getCode());
		}
		boolean incTax = true;
		if (isBase) {
			incTax = false;
		}
		txn.setInclude_tax(incTax);
		txn.setPayment_mode(0);
		txn.setIs_adjust(isAdj);
		txn.setRemarks(remarks);
		txn.setUser_id(userId);
		txn.setTxn_no(txnId);
		if(txnDate !=null && txnDate != "" ) {
			txn.setTxn_date(txnDate);
		}
		status = transactionService.update(txn);
		status = "status:" + status;
		return g.toJson(status).toString();
	}

	
	@RequestMapping(value = "/updateTxnPos", method = RequestMethod.POST)
	public @ResponseBody String update(@ModelAttribute AccountMaster accm,
			@ModelAttribute ShiftManagement currntShiftId, @ModelAttribute TaxHdr taxHdr,
			@RequestParam(value = "posAccMStName") String posAccMStName, @RequestParam(value = "txnId") int txnId, HttpSession session) throws Exception {
		Gson g = new Gson();
		String status = "success";
		status = transactionService.updatePOSAccName(txnId,posAccMStName);
		status = "status:" + status;
		return g.toJson(status).toString();
	}

	
	@RequestMapping(value = "/deleteTxn", method = RequestMethod.POST)
	public @ResponseBody String deleteTxn(@RequestParam(value = "txnNo") int txnNo, HttpSession session, Model model)
			throws Exception {
		Gson g = new Gson();
		String status = "success";
		status = transactionService.deleteRecord(txnNo);
		status = "status:" + status;
		return g.toJson(status).toString();
	}

	/// transfer

	@RequestMapping(value = "transfer")
	public String transfer(Model model, HttpSession session,
			@RequestParam(value = "folioNo", required = false) String folioNum,
			@RequestParam(value = "folioBindNo", required = false) String folioBindNum) throws Exception {
		int folioNo = 0, folioBindNo = 0;
		if (folioNum != "" && folioNum != null) {
			folioNo = Integer.parseInt(folioNum);
			folioBindNo = Integer.parseInt(folioBindNum);
		}
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_TRANSATION");
		model.addAttribute("curPagePerObj", permissionObj);
		model.addAttribute("folioNo", folioNo);
		model.addAttribute("folioBindNo", folioBindNo);
		SystemSettings systemSettings = systemSettingsService.getSystemSettings();
		String hotelDateshift = systemSettings.getHotelDate().toString();
		String count = shiftManagementService.isshiftOPen(hotelDateshift);
		model.addAttribute("count", count);

		return ((permissionObj.isCanExecute() && permissionObj.isIs_execute_applicable())
				? "transaction/transaction_transfer"
				: "access_denied/access_denied");
	}

	@RequestMapping(value = "roomDetails", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String roomDetails() {
		JsonArray roomList = new JsonArray();
		roomList = transactionService.getRoomList();
		return roomList.toString();
	}

	@RequestMapping(value = "transactionDetails", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String transactionDetails(@RequestParam(value = "folioNo") int folioNO) {
		JsonArray transactionListArray;
		transactionListArray = transactionService.getTransactionDetails(folioNO);
		return transactionListArray.toString();
	}

	@RequestMapping(value = "newTransafer", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String newTransafer(@RequestParam(value = "sourceFolio") int sourseFolioNO,
			@RequestParam(value = "destFolio") int destFolioNO,
			@RequestParam(value = "destFolioBind") int destFolioBind,
			@RequestParam(value = "selectedData") String selectedData, @ModelAttribute ShiftManagement currntShiftId,
			HttpSession session) throws ParseException {

		Gson g = new Gson();
		String status = "success";
		String htlDate = (String) session.getAttribute("hotelDate");
		String[] htlDte = htlDate.split(" ");
		String hotelDate = htlDte[0];
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String payTime = dateFormat.format(cal.getTime());

		Transaction txn = new Transaction();
		currntShiftId = shiftManagementService.getShiftManagementDetails(hotelDate);
		txn.setShift_id(currntShiftId.getShiftId());
		txn.setFolio_no(destFolioNO);
		txn.setFolio_bind_no(destFolioBind);
		txn.setTxn_date(hotelDate);
		txn.setTxn_time(payTime);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(selectedData);
		JSONObject jsonObject = (JSONObject) obj;
		status = transactionService.newTransafer(sourseFolioNO, txn, jsonObject);
		status = "status:" + status;
		return g.toJson(status).toString();
	}

	@RequestMapping(value = "posOrderDetails")
	public ModelAndView posOrderDetails(@RequestParam(value = "txnNo") Integer txnNo) {

		Object orderDetails = transactionService.getOrderDetails(txnNo);

		return new ModelAndView("pdfViewPosBillPreview", "orderData", orderDetails);
	}

	@RequestMapping(value = "getAccountTypeName")
	public @ResponseBody List<Object> getAccountTypeName() throws Exception {

		List<Object> accMstrList = new ArrayList<>();
		accMstrList = transactionService.getAccountTypeName();

		return accMstrList;
	}

}