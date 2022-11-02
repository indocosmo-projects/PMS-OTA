package com.indocosmo.pms.web.checkOut.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.enumerator.ReservationStatus;
import com.indocosmo.pms.web.checkOut.model.Invoice;
import com.indocosmo.pms.web.checkOut.model.CheckOutDetails;
import com.indocosmo.pms.web.checkOut.service.CheckOutService;
import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.currency.model.Currency;
import com.indocosmo.pms.web.currency.service.CurrencyService;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;
import com.indocosmo.pms.web.templates.model.InvoiceTemplate;
import com.indocosmo.pms.web.templates.service.TemplateService;

@Controller
@RequestMapping(value = "/checkOut")
public class CheckOutController {

	public static final Logger logger = LoggerFactory.getLogger(CheckOutController.class);

	@Autowired
	CurrencyService currencyService;

	@Autowired
	SystemSettingsService systemSettingsService;

	@Autowired
	CheckOutService checkOutService;

	@Autowired
	TemplateService templateService;

	/**
	 * @param folioBindNO
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "checkOutEdit", method = RequestMethod.GET)
	public String editReDirect(@RequestParam("folioBindNo") int folioBindNO, HttpSession session, Model model)
			throws Exception {
		model.addAttribute("folioBindNo", folioBindNO);
		return "check_out/check_out_edit";
	}

	/**
	 * @param folioBindNO
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getCheckOutDetails", method = RequestMethod.POST)
	public @ResponseBody String getCheckOutDetails(@RequestParam("folioBindNo") int folioBindNO, HttpSession session,
			Model model) throws Exception {
		List<CheckOutDetails> checkOutDetailsList = checkOutService.getCheckInDetailsByFolioBindNO(folioBindNO);
		String json = new Gson().toJson(checkOutDetailsList);
		return json;
	}

	/**
	 * @param CheckOutDetailsJson
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/updateCheckIn", method = RequestMethod.POST)
	public ResponseEntity<String> updateCheckOutDtls(
			@RequestParam(value = "checkOutDtls", required = true) String CheckOutDetailsJson, HttpSession session) {

		JsonParser parser = new JsonParser();
		JsonArray CheckOutDetailsJsonArray = parser.parse(CheckOutDetailsJson.toString()).getAsJsonArray();
		JsonObject jobj = CheckOutDetailsJsonArray.get(0).getAsJsonObject();
		JsonObject billAddressObj = jobj.get("billingAddress").getAsJsonObject();
		JsonArray CheckOutDetailsArray = jobj.get("checkOutsDtls").getAsJsonArray();
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy-MM-dd");
		List<CheckOutDetails> checkOutDetailsList = null;
		ObjectMapper mapper = new ObjectMapper();
		String isSave = "error";
		String resultStatus = "success";
		try {
			Encryption encryption = new Encryption();
			checkOutDetailsList = mapper.readValue(CheckOutDetailsArray.toString(),
					TypeFactory.defaultInstance().constructCollectionType(List.class, CheckOutDetails.class));
			for (CheckOutDetails checkOutDetails : checkOutDetailsList) {
				checkOutDetails.setCheckinNo(Integer.parseInt(encryption.decrypt(checkOutDetails.getEncryCheckinNo())));
				checkOutDetails
				.setFolioBindNo(Integer.parseInt(encryption.decrypt(checkOutDetails.getEncryFolioBindNo())));
				checkOutDetails.setFolioNo(Integer.parseInt(encryption.decrypt(checkOutDetails.getEncryFolioNo())));
				checkOutDetails.setResvNo(Integer.parseInt(encryption.decrypt(checkOutDetails.getEncryResvNo())));
				checkOutDetails.setRoomNumber(encryption.decrypt(checkOutDetails.getEncryRoomNo()));
				checkOutDetails.setStatus(ReservationStatus.CHECKOUT.getCode());
				checkOutDetails.setActDepartDate(simpleDateformat.parse(session.getAttribute("hotelDate").toString()));
				checkOutDetails.setActDepartTime(new Date());
				checkOutDetails.setCheckOutBy(((User) session.getAttribute("userForm")).getId());
				checkOutDetails.setCheckOutAt(new Date());
			}

			isSave = checkOutService.doCheckOut(checkOutDetailsList, billAddressObj);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : updateCheckOutDtls " + Throwables.getStackTraceAsString(e));
			resultStatus = ((CustomException) e).getErrorMessage();
			// throw new CustomException();
		}
		Gson g = new Gson();
		if (isSave == "success") {
			for (CheckOutDetails checkOutDetails : checkOutDetailsList) {
				resultStatus = resultStatus + "_" + checkOutDetails.getFolioNo();
			}
		}
		return new ResponseEntity<>(g.toJson(resultStatus).toString(), HttpStatus.OK);
	}

	@RequestMapping(value = "mailCheckOut", method = RequestMethod.POST)
	public @ResponseBody String mailCheckOut(HttpSession session, HttpServletRequest request, @RequestBody String addon)
			throws Exception {

		// boolean isSave = false;
		// Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		// Gson newGson = new Gson();
		JsonParser parser = new JsonParser();
		JsonObject jobj = parser.parse(addon).getAsJsonObject();
		String checkoutIds = jobj.get("data").toString();
		JsonArray jArray = (JsonArray) parser.parse(checkoutIds);
		String mailCheckOut = checkOutService.getmailCheckOut(jArray);
		// String smsCheckOut = checkOutService.sendSms(jArray);

		return mailCheckOut;
	}

	@RequestMapping(value = "addressWithCheckinNo", method = RequestMethod.POST)
	public @ResponseBody String loadAddressWithCheckinNo(@RequestParam(value = "checkinNo") String checkinNo)
			throws NumberFormatException, Exception {
		Encryption encryption = new Encryption();
		Integer checkin_no = Integer.parseInt(encryption.decrypt(checkinNo));
		JsonObject jobj = checkOutService.loadAddressWithCheckinNo(checkin_no);

		return jobj.toString();
	}

	/**
	 * @param folioNo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/invoice", method = RequestMethod.GET)
	public ModelAndView PrintInvoice(@RequestParam(value = "folioNo") int folioNo,
			@RequestParam(value = "printMode") int printMode, @RequestParam(value = "billDtls") int billDtls,
			@RequestParam(value = "mailType") int mailType,HttpServletRequest request, HttpSession session) throws Exception {
		String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(),
				request.getServerPort());
		Invoice invoice = null;
		String pdfName = "";
		// Currency curr = (Currency) session.getAttribute("currencyObject");
		invoice = checkOutService.getTxnDetails(folioNo, session, billDtls);
		InvoiceTemplate invTmpl = new InvoiceTemplate();
		invTmpl = templateService.getInvoiceTemplateDtls();
		String  companyN = (String) session.getAttribute("companyN");
	
		invTmpl.setHdrLogoUrl(baseUrl+(String) session.getAttribute("rootPath") + "/resources/common/images/logos_"+companyN+"/invoicelogo.png");
		SystemSettings systemSettings = systemSettingsService.getSystemSettings();
		// Currency currency =
		// currencyService.getRecord(systemSettings.getBaseCurrencyId());
		invoice.setFolioNo(folioNo);
		invoice.setMailType(mailType);
		invoice.setHeaderFooter(invTmpl);
		invoice.setSystemseting(systemSettings);
		invoice.setPrintMode(printMode);
		invoice.setBillDetails(billDtls);
		invoice.setConstNationality("India");
		invoice.setConstState("Kerala");
		invoice.setTxnListSummary(checkOutService.getTxnSummary(folioNo, session, billDtls));

		if (billDtls == 0) {
			pdfName = "pdfView";
		} else {
			pdfName = "pdfViewDetailed";
		}

		return new ModelAndView(pdfName, "listInvoice", invoice);
	}

	@RequestMapping(value = "/invoice2", method = RequestMethod.GET)
	public ModelAndView PrintInvoiceNew(@RequestParam(value = "folioNo") int folioNo,
			@RequestParam(value = "printMode") int printMode, 
			@RequestParam(value = "sysAccType") int sysAccType,
			@RequestParam(value = "billDtls") int billDtls,
			@RequestParam(value = "billSeparate") int billSeparate,
			@RequestParam(value = "mailType") int mailType,HttpServletRequest request, HttpSession session) throws Exception {
		String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(),
				request.getServerPort());
		Invoice invoice = null;
		String pdfName = "";
		if (billSeparate == 0) {
			invoice = checkOutService.getTxnDetailsCombined(folioNo, session, billDtls);
			invoice.setTxnListSummary(checkOutService.getTxnSummary(folioNo, session, billDtls));

		}
		else {
			invoice = checkOutService.getTxnDetailsSeparate(folioNo, session, billDtls,sysAccType);
			invoice.setTxnListSummary(checkOutService.getTxnSummarySeparate(folioNo, session, billDtls,sysAccType));

		}
		InvoiceTemplate invTmpl = new InvoiceTemplate();
		invTmpl = templateService.getInvoiceTemplateDtls();
		String  companyN = (String) session.getAttribute("companyN");
		invTmpl.setHdrLogoUrl(baseUrl +(String) session.getAttribute("rootPath")+ "/resources/common/images/logos_"+companyN+"/invoicelogo.png");
		
		SystemSettings systemSettings = systemSettingsService.getSystemSettings();
		if(sysAccType==0) {
			invTmpl.setTitle("INVOICE");
		}
		else {
		invTmpl.setTitle(checkOutService.getReportName(sysAccType));
		}
		invoice.setFolioNo(folioNo);
		invoice.setMailType(mailType);
		invoice.setHeaderFooter(invTmpl);
		invoice.setSystemseting(systemSettings);
		invoice.setPrintMode(printMode);
		invoice.setBillDetails(billDtls);
		invoice.setConstNationality("India");
		invoice.setConstState("Kerala");
		

	if (billSeparate == 0) {
		pdfName = "pdfViewDetailedCombined";
		} else {
			pdfName = "pdfViewDetailedSeparated";
		}
		
		return new ModelAndView(pdfName, "listInvoice", invoice);
		
	}


	public String getCurrency() throws Exception {

		String currency=currencyService.getCurrencySymbol();
		return currency;
	}
	
}