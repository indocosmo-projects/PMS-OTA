package com.indocosmo.pms.web.payment.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.enumerator.PaymentMode;
import com.indocosmo.pms.enumerator.TxnSource;
import com.indocosmo.pms.enumerator.TxnStatus;
import com.indocosmo.pms.enumerator.discount.DiscountFor;
import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.checkIn.model.CheckInHdr;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.common.setttings.Rounding;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.payment.model.Payable;
import com.indocosmo.pms.web.payment.service.PaymentService;
import com.indocosmo.pms.web.reservation.Folio;
import com.indocosmo.pms.web.reservation.service.ReservationService;
import com.indocosmo.pms.web.shiftManagement.model.ShiftManagement;
import com.indocosmo.pms.web.shiftManagement.service.ShiftManagementService;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;
import com.indocosmo.pms.web.tax.model.TaxHdr;
import com.indocosmo.pms.web.transaction.model.Transaction;
import com.indocosmo.pms.web.transaction.service.TxnService;

@Controller
@RequestMapping(value="/payment")
public class PaymentController {
	public static final String PAYMENT_LIST_PAGE_URL="payment/payment_list";
	public static final String DISCOUNT_LIST_PAGE_URL="payment/discount_Paylist";
	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "access_denied/access_denied";

	@Autowired
	TxnService transactionService;

	@Autowired
	PaymentService paymentService;

	@Autowired
	SystemSettingsService systemSettingsService;

	
	
	@Autowired
	ReservationService reservationService;
	@Autowired
	private ShiftManagementService shiftManagementService;
	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	private SysPermissions permissionObj;
	
	public static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	/**
	 * @param checkinNo
	 * @param model
	 * @param session
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public String list(@RequestParam(value="checkInNo") int checkinNo,Model model,HttpSession session) throws ParseException{
		permissionObj = pageAccessPermissionService.getPermission(session,"PMS_RECPN_PAYMNT");
		String pageUrl = PAYMENT_LIST_PAGE_URL;
		if(permissionObj.isCanView() && permissionObj.isIs_view_applicable() ){
			try{
			model.addAttribute("curPagePerObj",permissionObj);
			CheckInHdr checkIn = new CheckInHdr();
			checkIn = transactionService.getcheckinDetails(checkinNo);
			if(!checkIn.isFullySettled()){
			Folio commonTxnIds = new Folio();
			commonTxnIds=transactionService.getFolioDetails(checkinNo);
		//	String folBlnc = checkIn.getFolioBalance();
			String htlDate=(String) session.getAttribute("hotelDate");
			String[] htlDte = htlDate.split(" ");
			String hotelDate = htlDte[0];
			
			
			
			
//			String count=shiftManagementService.isshiftOPen(hotelDate);
//			   model.addAttribute("count", count);
			   
			   
			   
			   
			   
			Payable payable =paymentService.getPaymentDetails(commonTxnIds.getFolio_no());
			payable.setDeposits(Rounding.roundToTwo(Double.parseDouble(payable.getDeposits())));
			payable.setDiscounts(Rounding.roundToTwo(Double.parseDouble(payable.getDiscounts())));
			payable.setOtherCharges(Rounding.roundToTwo(Double.parseDouble(payable.getOtherCharges())));
			payable.setRoomCharges(Rounding.roundToTwo(Double.parseDouble(payable.getRoomCharges())));
			payable.setServiceCharge(Rounding.roundToTwo(Double.parseDouble(payable.getServiceCharge())));
			payable.setTax(Rounding.roundToTwo(Double.parseDouble(payable.getTax())));
			payable.setServiceTax(Rounding.roundToTwo(Double.parseDouble(payable.getServiceTax())));
			payable.setPaidIn(Rounding.roundToTwo(Double.parseDouble(payable.getPaidIn())));
			payable.setTotal(Rounding.roundToTwo(Double.parseDouble(payable.getTotal())));
			
			/*payable.setDeposits(payable.getDeposits());
			payable.setDiscounts(payable.getDiscounts());
			payable.setOtherCharges(payable.getOtherCharges());
			payable.setRoomCharges(payable.getRoomCharges());
			payable.setServiceCharge(payable.getServiceCharge());
			payable.setTax(payable.getTax());
			payable.setServiceTax(payable.getServiceTax());
			payable.setPaidIn(payable.getPaidIn());
			payable.setTotal(payable.getTotal());*/
			
			payable.setCanPay(true);
			if(Double.parseDouble(payable.getTotal())<=0){
				payable.setCanPay(false);
				payable.setTotal(String.valueOf(Math.abs(Double.parseDouble(payable.getTotal()))));
			}
			session.setAttribute("serviceTaxId",commonSettings.getServiceTaxId());
			session.setAttribute("folioBindNo",commonTxnIds.getFolio_bind_no());
			session.setAttribute("folioNo",commonTxnIds.getFolio_no());
			model.addAttribute("checkInNo",checkIn.getCheckInNo());
			model.addAttribute("checkInDate",checkIn.getArrDate());
			model.addAttribute("folioBalance",checkIn.getFolioBalance());
			model.addAttribute("departDate",checkIn.getExpDepartDate());
			model.addAttribute("folioNo",commonTxnIds.getFolio_no());
			model.addAttribute("name",checkIn.getName());
			model.addAttribute("phone",checkIn.getPhone());
			model.addAttribute("email",checkIn.getEmail());
			model.addAttribute("address",checkIn.getAddress());
			model.addAttribute("roomNo",checkIn.getRoomNumber());
			model.addAttribute("roomType",checkIn.getRoomTypeCode());
			model.addAttribute("paymentModeEnum",PaymentMode.values());
			model.addAttribute("corporate",checkIn.getCorporateId());
			model.addAttribute("hotelDate",hotelDate);
			model.addAttribute("payable", payable);
			}else{
				pageUrl= SYDEF_PERMISION_DENIED_PAGE_URL;
			}
			}catch(Exception e){
				e.printStackTrace();
				logger.error("Method : list " + Throwables.getStackTraceAsString(e));
				throw new CustomException();
			}
		}else{
			pageUrl= SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}
	
	@RequestMapping(value="/discountlist",method = RequestMethod.GET)
	public String discountlist(@RequestParam(value="checkInNo") int checkinNo,Model model,HttpSession session) throws ParseException{
		permissionObj = pageAccessPermissionService.getPermission(session,"PMS_RECPN_PAYMNT");
		String pageUrl = DISCOUNT_LIST_PAGE_URL;
		if(permissionObj.isCanView() && permissionObj.isIs_view_applicable() ){
			try{
			model.addAttribute("curPagePerObj",permissionObj);
			CheckInHdr checkIn = new CheckInHdr();
			checkIn = transactionService.getcheckinDetails(checkinNo);
			if(!checkIn.isFullySettled()){
			Folio commonTxnIds = new Folio();
			commonTxnIds=transactionService.getFolioDetails(checkinNo);
	
			String htlDate=(String) session.getAttribute("hotelDate");
			String[] htlDte = htlDate.split(" ");
			String hotelDate = htlDte[0];
	
			Payable payable =paymentService.getPaymentDetails(commonTxnIds.getFolio_no());
			payable.setDeposits(Rounding.roundToTwo(Double.parseDouble(payable.getDeposits())));
			payable.setDiscounts(Rounding.roundToTwo(Double.parseDouble(payable.getDiscounts())));
			payable.setOtherCharges(Rounding.roundToTwo(Double.parseDouble(payable.getOtherCharges())));
			payable.setRoomCharges(Rounding.roundToTwo(Double.parseDouble(payable.getRoomCharges())));
			payable.setServiceCharge(Rounding.roundToTwo(Double.parseDouble(payable.getServiceCharge())));
			payable.setTax(Rounding.roundToTwo(Double.parseDouble(payable.getTax())));
			payable.setServiceTax(Rounding.roundToTwo(Double.parseDouble(payable.getServiceTax())));
			payable.setPaidIn(Rounding.roundToTwo(Double.parseDouble(payable.getPaidIn())));
			payable.setTotal(Rounding.roundToTwo(Double.parseDouble(payable.getTotal())));
			
			
			payable.setCanPay(true);
			if(Double.parseDouble(payable.getTotal())<=0){
				payable.setCanPay(false);
				payable.setTotal(String.valueOf(Math.abs(Double.parseDouble(payable.getTotal()))));
			}
			session.setAttribute("serviceTaxId",commonSettings.getServiceTaxId());
			session.setAttribute("folioBindNo",commonTxnIds.getFolio_bind_no());
			session.setAttribute("folioNo",commonTxnIds.getFolio_no());
			model.addAttribute("checkInNo",checkIn.getCheckInNo());
			model.addAttribute("checkInDate",checkIn.getArrDate());
			model.addAttribute("folioBalance",checkIn.getFolioBalance());
			model.addAttribute("departDate",checkIn.getExpDepartDate());
			model.addAttribute("folioNo",commonTxnIds.getFolio_no());
			model.addAttribute("name",checkIn.getName());
			model.addAttribute("phone",checkIn.getPhone());
			model.addAttribute("email",checkIn.getEmail());
			model.addAttribute("address",checkIn.getAddress());
			model.addAttribute("roomNo",checkIn.getRoomNumber());
			model.addAttribute("roomType",checkIn.getRoomTypeCode());
			model.addAttribute("paymentModeEnum",PaymentMode.values());
			model.addAttribute("corporate",checkIn.getCorporateId());
			model.addAttribute("hotelDate",hotelDate);
			model.addAttribute("payable", payable);
			model.addAttribute("discountForEnum",DiscountFor.values());
			
			}else{
				pageUrl= SYDEF_PERMISION_DENIED_PAGE_URL;
			}
			}catch(Exception e){
				e.printStackTrace();
				logger.error("Method : list " + Throwables.getStackTraceAsString(e));
				throw new CustomException();
			}
		}else{
			pageUrl= SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}

	/**
	 * 
	 * @param pAmount
	 * @param pType
	 * @param pMode
	 * @param pRemarks
	 * @param session
	 * @param accm
	 * @param txn
	 * @param user
	 * @return
	 * @throws ParseException
	 */
	/*@RequestMapping(value="/save", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String save(@RequestParam (value="payAmount")double pAmount,@ModelAttribute ShiftManagement currntShiftId,Model model,
			@RequestParam (value="payType")int pType,
			@RequestParam (value="payMode")int pMode,
			@RequestParam (value="payOption")String pOption,
			@ModelAttribute TaxHdr taxHdr,
			@RequestParam (value="payRemarks")String pRemarks,@RequestParam (value="payFrom")String pFrom,HttpSession session,
			@ModelAttribute User user,
			@ModelAttribute AccountMaster accm) throws ParseException{
		String payStatus="success";
		Gson g = new Gson();
		try{
		String[] htlDte = session.getAttribute("hotelDate").toString().split(" ");
		String hotelDat = htlDte[0];
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = sdf1.parse(hotelDat);
		java.sql.Date hotelDate = new java.sql.Date(date.getTime()); 
		
		
		
		
		
		
		String count=shiftManagementService.isshiftOPen(hotelDat);
	   model.addAttribute("count", count);
		
		
		
		int userId = ((User) session.getAttribute("userForm")).getId();	
		Transaction txn= new Transaction();
		String htlDate2=(String) session.getAttribute("hotelDate");
		String[] htlDte1 = htlDate2.split(" ");
		String hotelDate1 = htlDte1[0];
		currntShiftId=shiftManagementService.getShiftManagementDetails(hotelDate1);
		
		txn=transactionService.getCharges(hotelDate,pAmount,pType,true);
		txn.setAmount(pAmount);
		txn.setNett_amount(txn.getNett_amount());
		txn.setAcc_mst_id(pType);
		accm = transactionService.accMstId(pType);			
		txn.setAcc_mst_code(accm.getCode());
		txn.setTxn_source(TxnSource.FRONTOFFICEPOSTING.getCode());
		txn.setFolio_no(Integer.parseInt(session.getAttribute("folioNo").toString()));
		txn.setFolio_bind_no(Integer.parseInt(session.getAttribute("folioBindNo").toString()));
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String payTime = dateFormat.format(cal.getTime());	
		txn.setTxn_date(hotelDat);
		txn.setTxn_time(payTime);
		taxHdr=transactionService.getTaxCode(accm.getTax_id());
		txn.setTax_id(accm.getTax_id());
		txn.setTax_code(taxHdr.getCode());
		if(pType==5){
			txn.setNett_amount(-(txn.getNett_amount()));
		}
		txn.setPayment_mode(pMode);
		txn.setPayment_option(pOption);
		txn.setTxn_status(TxnStatus.ACTIVE.getCode());
		txn.setRemarks(pRemarks);
		txn.setReceived_from(pFrom);
		txn.setUser_id(userId);
		txn.setShift_id(currntShiftId.getShiftId());

		payStatus = paymentService.save(txn);
		payStatus = "status:"+payStatus;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Method : save " + Throwables.getStackTraceAsString(e));
		}
		return g.toJson(payStatus).toString();
	}
	*/
	@RequestMapping(value="/save", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String save(@RequestParam (value="payments")String payments,@ModelAttribute ShiftManagement currntShiftId,Model model,
			@ModelAttribute TaxHdr taxHdr,HttpSession session,@ModelAttribute User user,@ModelAttribute AccountMaster accm) throws ParseException{
		String payStatus="success";
		Gson g = new Gson();
		try{
		String[] htlDte = session.getAttribute("hotelDate").toString().split(" ");
		String hotelDat = htlDte[0];
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = sdf1.parse(hotelDat);
		java.sql.Date hotelDate = new java.sql.Date(date.getTime()); 
		
		JsonParser parser = new JsonParser();
		JsonArray jsonArray = (JsonArray) parser.parse(payments);
		
		/*String count=shiftManagementService.isshiftOPen(hotelDat);
	   model.addAttribute("count", count);*/
		
		
		Transaction txn=null;
		int userId = ((User) session.getAttribute("userForm")).getId();	
		String htlDate2=(String) session.getAttribute("hotelDate");
		String[] htlDte1 = htlDate2.split(" ");
		String hotelDate1 = htlDte1[0];
		currntShiftId=shiftManagementService.getShiftManagementDetails(hotelDate1);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject json = (JsonObject) jsonArray.get(i);
			String paymentOption= null;
			switch (json.get("pmtMode").getAsInt()) {
			case 1:
				 paymentOption="";
				 break;
		    case 2:
		    	if(json.get("bankCardType") != null)
		    		paymentOption = json.get("bankCardType").getAsString();
		        break;
		    case 3:
		    	if(json.get("bankName") != null)
		    		paymentOption= json.get("bankName").getAsString();
		        break;
		    case 4:
		    	 paymentOption="";
		    	 break;
		    case 5:
		    	if(json.get("companyName") != null)
		    		paymentOption= json.get("companyName").getAsString();
		       break;
		}
		
			txn= new Transaction();
			txn=transactionService.getCharges(hotelDate,json.get("amount").getAsDouble(),json.get("pmtType").getAsInt(),true, json.get("chkInNo").getAsInt());
			txn.setAmount(json.get("amount").getAsDouble());
			txn.setNett_amount(txn.getNett_amount());
			txn.setAcc_mst_id(json.get("pmtType").getAsInt());
			accm = transactionService.accMstId(json.get("pmtType").getAsInt());			
			txn.setAcc_mst_code(accm.getCode());
			txn.setTxn_source(TxnSource.FRONTOFFICEPOSTING.getCode());
			txn.setFolio_no(Integer.parseInt(session.getAttribute("folioNo").toString()));
			txn.setFolio_bind_no(Integer.parseInt(session.getAttribute("folioBindNo").toString()));
			Calendar cal = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String payTime = dateFormat.format(cal.getTime());	
			txn.setTxn_date(hotelDat);
			txn.setTxn_time(payTime);
			taxHdr=transactionService.getTaxCode(accm.getTax_id());
			txn.setTax_id(accm.getTax_id());
			txn.setTax_code(taxHdr.getCode());
			if(json.get("pmtType").getAsInt()==5){
				txn.setNett_amount(-(txn.getNett_amount()));
			}
			txn.setPayment_mode(json.get("pmtMode").getAsInt());
			if((json.get("corporateId")) != null) {
				txn.setCorporate_id(json.get("corporateId").getAsInt());
				String corporateName = paymentService.getCorporateName(json.get("corporateId").getAsInt());
				txn.setCorporate_name(corporateName);
			}
			txn.setPayment_option(paymentOption);
			txn.setTxn_status(TxnStatus.ACTIVE.getCode());
			if(json.get("remarks") != null)
				txn.setRemarks(json.get("remarks").getAsString());
			if(json.get("resvFrom") != null)
			txn.setReceived_from(json.get("resvFrom").getAsString());
			txn.setUser_id(userId);
			txn.setShift_id(currntShiftId.getShiftId());
			payStatus = paymentService.save(txn);

		}
		if(payStatus =="success"){
			payStatus = paymentService.setRoundAdj(txn);
		}
		//payStatus = "status:"+payStatus;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Method : save " + Throwables.getStackTraceAsString(e));
		}
		return g.toJson(payStatus).toString();
	}
	
	@RequestMapping(value="/saveDiscount", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String saveDiscount(@RequestParam (value="payments")String payments,@ModelAttribute ShiftManagement currntShiftId,Model model,
			@ModelAttribute TaxHdr taxHdr,HttpSession session,@ModelAttribute User user,@ModelAttribute AccountMaster accm) throws ParseException{
		String payStatus="success";
		Gson g = new Gson();
		try{
		JsonParser parser = new JsonParser();
		JsonArray discountDetails = (JsonArray) parser.parse(payments);
		payStatus = paymentService.upDateDiscounts(discountDetails);
		
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Method : save " + Throwables.getStackTraceAsString(e));
		}
		return g.toJson(payStatus).toString();
	}
		
	@RequestMapping(value="/bankCardTypes", method = RequestMethod.POST)
	public @ResponseBody String bankCardTypes() throws Exception{	
		
		JsonArray jArray=paymentService.bankCardTypes();
		
		return jArray.toString();
	}
	@RequestMapping(value="/getDiscountDetails", method = RequestMethod.GET)
	public @ResponseBody String getDiscountDetails(HttpServletRequest request,HttpSession session) throws Exception{	
		int checkinNo = Integer.parseInt(request.getParameter("checkinNo"));
		System.out.println(checkinNo);
		Folio commonTxnIds = new Folio();
		commonTxnIds=transactionService.getFolioDetails(checkinNo);
		
		JsonArray jArray=paymentService.getDiscountDetails(checkinNo,commonTxnIds.getFolio_no());
		
		return jArray.toString();
	}
	@RequestMapping(value="/checkDiscount", method = RequestMethod.GET)
	public @ResponseBody String checkDiscount(HttpServletRequest request,HttpSession session) throws Exception{	
		String Status="no";
		Gson g = new Gson();
		int checkinNo = Integer.parseInt(request.getParameter("checkinNo"));
		Folio commonTxnIds = new Folio();
		commonTxnIds=transactionService.getFolioDetails(checkinNo);
		int discoutApplied = paymentService.getDiscount(commonTxnIds.getFolio_no());
		
		 if(discoutApplied >0 ) {
			 Status="yes";
		 }
		return g.toJson(Status).toString();
	}
	@RequestMapping(value = "/companydetails", method = RequestMethod.POST)
	public @ResponseBody String getCompanyDetails() throws Exception{
		JsonArray companyList= new JsonArray();
		companyList=paymentService.getCompanyDetails();
		return companyList.toString();
	}
	@RequestMapping(value="/bankDetails",method=RequestMethod.POST)
	public @ResponseBody String getBankDetails()throws Exception{
		JsonArray bankDetails=paymentService.getBankDetails();
		return bankDetails.toString();
	}
}
