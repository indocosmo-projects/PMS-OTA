package com.indocosmo.pms.web.deposit.controller;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.indocosmo.pms.enumerator.AccMst;
import com.indocosmo.pms.enumerator.PaymentMode;
import com.indocosmo.pms.enumerator.TxnSource;
import com.indocosmo.pms.enumerator.TxnStatus;
import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.checkIn.model.CheckInDtl;
import com.indocosmo.pms.web.checkIn.model.CheckInHdr;
import com.indocosmo.pms.web.checkOut.model.Invoice;
import com.indocosmo.pms.web.checkOut.service.CheckOutService;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.deposit.service.DepositService;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.reception.service.ReceptionService;
import com.indocosmo.pms.web.reservation.Folio;
import com.indocosmo.pms.web.reservation.model.ResvDtl;
import com.indocosmo.pms.web.reservation.model.ResvHdr;
import com.indocosmo.pms.web.reservation.model.RoomAvailability;
import com.indocosmo.pms.web.reservation.model.TxnHdr;
import com.indocosmo.pms.web.reservation.service.ReservationService;
import com.indocosmo.pms.web.shiftManagement.model.ShiftManagement;
import com.indocosmo.pms.web.shiftManagement.service.ShiftManagementService;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;
import com.indocosmo.pms.web.templates.model.InvoiceTemplate;
import com.indocosmo.pms.web.templates.service.TemplateService;
import com.indocosmo.pms.web.transaction.model.Transaction;
import com.indocosmo.pms.web.transaction.service.TxnService;

@Controller
@RequestMapping(value = "/deposit")
public class DepositController {

	@Autowired
	private ReceptionService receptionService;

	@Autowired
	ReservationService reservationService;
	@Autowired
	ReservationService resrvation;

	@Autowired
	private ShiftManagementService shiftManagementService;
	@Autowired
	DepositService depositService;

	@Autowired
	TxnService transactionService;

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	@Autowired
	TemplateService templateService;

	@Autowired
	SystemSettingsService systemSettingsService;

	private SysPermissions permissionObj;

	/*
	 * ServletRequest request,
	 * 
	 * Get the IP address of client machine. String ipAddress =
	 * request.getRemoteAddr();
	 * 
	 * System.out.println("IP "+ ipAddress );
	 */

	@RequestMapping(value = "depositEdit", produces = "application/json", method = RequestMethod.GET)
	public String depositEdit(@ModelAttribute TxnHdr txnHdr, Model model,
			@ModelAttribute RoomAvailability roomAvailability,
			@RequestParam(value = "reservationId", required = false) String reservationId,
			@RequestParam(value = "checkInNo", required = false) String checkIn, HttpSession session) {

		String Url = "access_denied/access_denied";
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_DEPST");

		if ((permissionObj.isCanView()) && (permissionObj.isIs_view_applicable())) {

			HashMap<String, Object> resultData = null;
			int id = 0, foliobindNo = 0;
			double netDeposit = 0, totalDeposit = 0;
			List<ResvDtl> resvDtlList = null;
			ResvHdr resvHdr1 = new ResvHdr();
	//		HashMap<String, Object> resultDataChkIn = null;
			List<CheckInDtl> checkInDtlList;
			CheckInHdr checkInHdr = null;
			double totalNetDeposit;
			totalNetDeposit = depositService.getTotalNetDeposit(reservationId, checkIn);
			model.addAttribute("totalNetDeposit", totalNetDeposit);
			Double folBal;
			int depositFrom = 0; // 1 -> reservation , 2 -> reception

			try {

				if (reservationId != null && !reservationId.isEmpty()) {
					id = Integer.parseInt(reservationId);
					resultData = reservationService.getResvRecord(id);
					resvHdr1 = (ResvHdr) resultData.get("resvHdr");
					roomAvailability = reservationService.getRoomDetails(id);
					session.setAttribute("roomInfo", roomAvailability);
					resvDtlList = reservationService.getTotalPayable(id);
					model.addAttribute("folioNo","folioBindNo_" +resvHdr1.getFolioBindNo());
					for (ResvDtl resvDtl1 : resvDtlList) {

						netDeposit += reservationService.getNetAmount(resvDtl1);
					}

					depositFrom = 1;
					session.setAttribute("backUrl", "/reservation_test/tools?reservationNo=" + reservationId);
				} else if (checkIn != null && !checkIn.isEmpty()) {
					int checkInNo = Integer.parseInt(checkIn);
					resultData = receptionService.getCheckInDetails(checkInNo);
					folBal = (Double) resultData.get("folioBal");
					checkInHdr = (CheckInHdr) resultData.get("checkInHdr");
					checkInDtlList = (List<CheckInDtl>) resultData.get("checkInDtlList");
					resvHdr1.setResvByFirstName(checkInDtlList.get(0).getFirstName());
					resvHdr1.setResvByLastName(checkInDtlList.get(0).getLastName());
					resvHdr1.setResvByMail(checkInDtlList.get(0).getEmail());
					resvHdr1.setResvByPhone(checkInDtlList.get(0).getPhone());
					resvHdr1.setResvByAddress(checkInDtlList.get(0).getAddress());
					resvHdr1.setNumAdults(checkInHdr.getNumAdults());
					resvHdr1.setFolioBindNo(checkInHdr.getFolioBindNo());
					long numNights = TimeUnit.DAYS.convert(
							(checkInHdr.getExpDepartDate().getTime() - checkInHdr.getArrDate().getTime()),
							TimeUnit.MILLISECONDS);

					model.addAttribute("checkInHdr", checkInHdr);
					model.addAttribute("folioBalance", folBal);
					model.addAttribute("numNights", numNights);
					model.addAttribute("folioNo","folioNo_" +depositService.findFolio(Integer.parseInt(checkIn)));
					depositFrom = 2;
					session.setAttribute("backUrl", "/reception/receptionList");
				}
				/*
				 * else{ System.out.println("Deposit else ");
				 * 
				 * }
				 */

				foliobindNo = resvHdr1.getFolioBindNo();
				totalDeposit = reservationService.getDepositAmount(foliobindNo);
				DecimalFormat df = new DecimalFormat("#.##");
				df.format(netDeposit);

				model.addAttribute("totalDeposit", totalDeposit);
				model.addAttribute("resHdr", resvHdr1);
			//	model.addAttribute("folioNo",depositService.findFolio(Integer.parseInt(checkIn)));
				model.addAttribute("totalPayable", df.format(netDeposit));
				model.addAttribute("roomAvailability", roomAvailability);
				model.addAttribute("txnHdr", txnHdr);
				model.addAttribute("transactionMode", PaymentMode.getPaymentModemap());
				model.addAttribute("depositFrom", depositFrom);
				model.addAttribute("reservationId", (reservationId != null ? reservationId : 0));

			} catch (Exception e) {

				e.printStackTrace();
			}

			model.addAttribute("curPagePerObj", permissionObj);

			Url = "deposit/deposit_edit";
		}

		return Url;
	}

	@RequestMapping(value = "/paymentList", method = RequestMethod.GET)
	public @ResponseBody List<Transaction> paymentList(Model model, HttpSession session,
			@RequestParam(value = "folioBindNo") String folioBindNo, @RequestParam(value = "sortCol") String sortCol,
			@RequestParam(value = "sortStatus") String sortStatus) throws Exception {
		List<Transaction> depositList = null;

		AccMst i = AccMst.DEPOSIT;
		int accId = i.getSysdef_acc_type_id();
       
		// System.out.println("accId="+accId);

		depositList = depositService.getDepositList(folioBindNo, sortCol, sortStatus);

		return depositList;
	}

	@RequestMapping(value = "depositListJson", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody String depositListjson(@RequestParam(value = "ids") int id,
			@RequestParam(value = "advanceSearch", required = false) String advanceSearch, HttpSession session,
			@RequestParam(value = "simpleSearch", required = false) String simpleSearch,
			@RequestParam(value = "rowLimit", required = false) int limit, @RequestParam(value = "sort") String sort,
			@RequestParam(value = "sortValue") String sortValue) {
		Map<String, String> advanceSearchMap = new HashMap<String, String>();

		if (advanceSearch != null && !advanceSearch.equals("null") && !advanceSearch.equals("")) {
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				advanceSearchMap = objectMapper.readValue(advanceSearch, new TypeReference<HashMap<String, String>>() {
				});
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new CustomException();
			}
		}

		Map<String, String> simpleSearchMap = new HashMap<String, String>();

		if (simpleSearch != null && !simpleSearch.equals("")) {
			simpleSearchMap.put("txn.nett_amount", simpleSearch);
		}

		return depositService.listOfTransaction(id, advanceSearchMap, session.getAttribute("dateFormat").toString(),
				simpleSearchMap, limit, sort, sortValue).toString();
	}

	@RequestMapping(value = "/addDeposit", method = RequestMethod.POST)
	public @ResponseBody int addBrand(@RequestBody String deposit, HttpSession session,
			@ModelAttribute ShiftManagement currntShiftId) {
		int depositSubmissionStatus = 0;
		Gson gson = new GsonBuilder().setDateFormat("dd-mm-yyyy").create();

		try {

			Transaction txnForDeposit = gson.fromJson(deposit, Transaction.class);
			String htlDate = (String) session.getAttribute("hotelDate");
			String[] htlDte = htlDate.split(" ");
			String hotelDate = htlDte[0];

			// Date Time
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String confTime = dateFormat.format(cal.getTime());

			Folio folio = null;
			folio = new Folio();
			folio = depositService.folioObj(txnForDeposit);

			// txnNo
			int txn_no = depositService.txnNo();

			AccMst i = AccMst.DEPOSIT;
			int accId = i.getSysdef_acc_type_id();

			AccountMaster acm = null;
			acm = depositService.getAccdetails(accId);
			int accMstId = acm.getId();
			String accMstCode = acm.getCode();
			int tax_id = acm.getTax_id();
			// Tax Code
			int userId = ((User) session.getAttribute("userForm")).getId();

			txnForDeposit.setTxn_no(txn_no);
			txnForDeposit.setFolio_no(folio.getFolio_no());
			txnForDeposit.setFolio_bind_no(folio.getFolio_bind_no());
			txnForDeposit.setTxn_source(TxnSource.FRONTOFFICEPOSTING.getCode());
			txnForDeposit.setTxn_date(hotelDate);
			txnForDeposit.setTxn_time(confTime);
			txnForDeposit.setAcc_mst_id(accMstId);
			txnForDeposit.setAcc_mst_code(accMstCode);
			txnForDeposit.setTax_id(tax_id);
			txnForDeposit.setTax_code("NO-TAX");
			txnForDeposit.setInclude_tax(false);
			txnForDeposit.setTax1_pc(0.0);
			txnForDeposit.setTax2_pc(0.0);
			txnForDeposit.setTax3_pc(0.0);
			txnForDeposit.setTax4_pc(0.0);
			txnForDeposit.setTax1_amount(0.0);
			txnForDeposit.setTax2_amount(0.0);
			txnForDeposit.setTax3_amount(0.0);
			txnForDeposit.setTax4_amount(0.0);
			txnForDeposit.setNett_amount(txnForDeposit.getAmount());
			txnForDeposit.setSource_folio_no(0);
			txnForDeposit.setDest_folio_no(0);
			txnForDeposit.setUser_id(userId);
			txnForDeposit.setTxn_status(TxnStatus.ACTIVE.getCode());
			String htlDate2 = (String) session.getAttribute("hotelDate");
			String[] htlDte1 = htlDate2.split(" ");
			String hotelDate1 = htlDte1[0];
			currntShiftId = shiftManagementService.getShiftManagementDetails(hotelDate1);
			txnForDeposit.setShift_id(currntShiftId.getShiftId());
			// System.out.println("txnForDeposit="+g.toJson(txnForDeposit));
			depositSubmissionStatus = depositService.saveDeposit(txnForDeposit);

		} catch (Exception e) {
			// System.out.println("error="+e);
		}

		return depositSubmissionStatus;
	}

	/*@RequestMapping(value = "/print", method = RequestMethod.GET)
	public ModelAndView print(@RequestParam(value = "deposit") String deposit, HttpServletRequest request,
			HttpSession session) {
		Transaction txnForDeposit = null;
		try {
			String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(),
					request.getServerPort());
			Gson gson = new GsonBuilder().setDateFormat("dd-mm-yyyy").create();
			InvoiceTemplate invTmpl = new InvoiceTemplate();
			invTmpl = templateService.getInvoiceTemplateDtls();
			invTmpl.setHdrLogoUrl(baseUrl +(String) session.getAttribute("rootPath")+ "/resources/common/images/logos/invoicelogo.png");
			invTmpl.setTitle("RECEIPT");

			txnForDeposit = gson.fromJson(deposit, Transaction.class);
			txnForDeposit = depositService.getDetails(txnForDeposit);
			txnForDeposit.setHeaderFooter(invTmpl);
			txnForDeposit.setSystemSettings(systemSettingsService.getSystemSettings());
		} catch (Exception e) {
			// System.out.println("error="+e);
		}
		return new ModelAndView("pdfView", "deposit", txnForDeposit);
	}*/
	
	
	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public ModelAndView print(@RequestParam(value = "deposit") String deposit, HttpServletRequest request,
			HttpSession session)  throws Exception {
		String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(),
				request.getServerPort());
		Invoice invoice = null;
		String pdfName = "";
		Transaction txnForDeposit = null;		
		//CheckOutService checkOutService;
		Gson gson = new GsonBuilder().setDateFormat("dd-mm-yyyy").create();
		txnForDeposit = gson.fromJson(deposit, Transaction.class);
		invoice = depositService.getDetails(txnForDeposit);
    	InvoiceTemplate invTmpl = new InvoiceTemplate();
		invTmpl = templateService.getInvoiceTemplateDtls();
		String  companyN = (String) session.getAttribute("companyN");
		invTmpl.setHdrLogoUrl(baseUrl +(String) session.getAttribute("rootPath")+ "/resources/common/images/logos_"+companyN+"/invoicelogo.png");
		
		SystemSettings systemSettings = systemSettingsService.getSystemSettings();
		invTmpl.setTitle("RECEIPT");
	/*	invoice.setFolioNo(folioNo);*/
		invoice.setMailType(0);
		invoice.setHeaderFooter(invTmpl);
		invoice.setSystemseting(systemSettings);
		invoice.setPrintMode(1);
		//invoice.setBillDetails(billDtls);
		invoice.setConstNationality("India");
		invoice.setConstState("Kerala");
		

	
		pdfName = "pdfViewDeposit";
		return new ModelAndView(pdfName, "listInvoice", invoice);
		
	}


	@RequestMapping(value = "/getCorpName")
	public @ResponseBody String getCorpName(HttpSession session) throws Exception {
		JsonArray jArray = depositService.getCorpName();
		return jArray.toString();
	}

}