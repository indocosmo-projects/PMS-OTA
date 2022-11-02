package com.indocosmo.pms.web.nightAudit.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.indocosmo.pms.enumerator.ReservationStatus;
import com.indocosmo.pms.enumerator.TxnSource;
import com.indocosmo.pms.enumerator.TxnStatus;
import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.checkIn.model.CheckInHdr;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.nightAudit.service.NightAuditService;
import com.indocosmo.pms.web.reservation_test.model.ResvHdr;
import com.indocosmo.pms.web.reservation_test.model.ResvRoom;
import com.indocosmo.pms.web.reservation_test.service.ReservationServiceTest;
import com.indocosmo.pms.web.shiftManagement.service.ShiftManagementService;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;
import com.indocosmo.pms.web.tax.model.TaxHdr;
import com.indocosmo.pms.web.transaction.model.Transaction;
import com.indocosmo.pms.web.transaction.service.TxnService;

@Controller
@RequestMapping(value="/nightAudit")
public class NightAuditController {

	@Autowired 
	NightAuditService nightAuditService;

	@Autowired
	SystemSettingsService systemSettingsService;

	@Autowired
	TxnService transactionService;
	@Autowired
	private ShiftManagementService shiftManagementService;

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	@Autowired 
	private ReservationServiceTest reservationService;

	private SysPermissions permissionObj;

	public static final String AUDIT_PAGE1_URL="night_audit/night_audit_page1";
	public static final String AUDIT_PAGE2_URL="night_audit/night_audit_page2";
	public static final String AUDIT_PAGE3_URL="night_audit/night_audit_page3";
	public static final String AUDIT_PAGE4_URL="night_audit/night_audit_page4";
	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "access_denied/access_denied";
	SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * List redirect function
	 * @return page redirect 
	 * @throws Exception 
	 */
	@RequestMapping(value="audit")
	public String getWizardPage(HttpSession session,Model model) throws Exception{
		SystemSettings systemSettings=systemSettingsService.getSystemSettings();
		int naStage=systemSettings.getNightAuditStage();
		session.setAttribute("NightAuditStage",naStage);
		session.setAttribute("prevPageUrl", "/nightAudit/audit");
		String url="";
		permissionObj = pageAccessPermissionService.getPermission(session,"PMS_NIGHT_AUDT");
		if(permissionObj.isCanExecute() && permissionObj.isIs_execute_applicable() ){
			model.addAttribute("curPagePerObj",permissionObj);
			if(naStage==0){
				String htlDate=(String) session.getAttribute("hotelDate");
				String[] htlDte = htlDate.split(" ");
				String hotelDate = htlDte[0];
				String countshiftRemain=shiftManagementService.shiftcountOndateRemain(hotelDate);
				model.addAttribute("countshiftRemain", countshiftRemain);
				JsonObject countObject=nightAuditService.getReceptionCount(hotelDate);
				String count=shiftManagementService.isshiftOPen(hotelDate);
				model.addAttribute("count", count);
				model.addAttribute("expArrival",countObject.get("expArrival"));
				model.addAttribute("expDepart",countObject.get("expDepart"));
				model.addAttribute("noShow",countObject.get("noShow"));
				model.addAttribute("inHouse",countObject.get("inHouse"));
				url= AUDIT_PAGE1_URL;
			}else if(naStage==1){
//				String htlDate=(String) session.getAttribute("hotelDate");
//				String hotelDate =htlDate; 
//				model.addAttribute("hotelDate",hotelDate);
//				String count=shiftManagementService.isshiftOPen(hotelDate);
//				 model.addAttribute("count", count);
//				 String countshiftRemain=shiftManagementService.shiftcountOndateRemain(hotelDate);
//					model.addAttribute("countshiftRemain", countshiftRemain);
				String htlDate=(String) session.getAttribute("hotelDate");
				String[] htlDte = htlDate.split(" ");
				String hotelDate = htlDte[0];
				String countshiftRemain=shiftManagementService.shiftcountOndateRemain(hotelDate);
				model.addAttribute("countshiftRemain", countshiftRemain);
				String count=shiftManagementService.isshiftOPen(hotelDate);
				 model.addAttribute("count", count);
				 session.setAttribute("count1", count);

				url= AUDIT_PAGE2_URL;
			}else if(naStage==2){
				String htlDate=(String) session.getAttribute("hotelDate");
				String[] htlDte = htlDate.split(" ");
				String hotelDate = htlDte[0];
				String countshiftRemain=shiftManagementService.shiftcountOndateRemain(hotelDate);
				model.addAttribute("countshiftRemain", countshiftRemain);
				String count=shiftManagementService.isshiftOPen(hotelDate);
				 model.addAttribute("count", count);
				 
				url= AUDIT_PAGE3_URL;
			}else if(naStage==3){
				url= AUDIT_PAGE4_URL;
			}
		}else{
			url= SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return url;
	}

	@RequestMapping(value="getWizardPage2")
	public String getWizardPage2(HttpSession session,Model model) throws ParseException{
		int naStage= Integer.parseInt(session.getAttribute("NightAuditStage").toString());
		String pageUrl= AUDIT_PAGE2_URL;
		permissionObj = pageAccessPermissionService.getPermission(session,"PMS_NIGHT_AUDT");
		if(permissionObj.isCanExecute() && permissionObj.isIs_execute_applicable()){
			model.addAttribute("curPagePerObj",permissionObj);
			if(naStage==0){
				nightAuditService.setNightAuditStage(1);
				session.setAttribute("NightAuditStage",1);
				String htlDate=(String) session.getAttribute("hotelDate");
				String[] htlDte = htlDate.split(" ");
				String hotelDate = htlDte[0];
				model.addAttribute("hotelDate",hotelDate);
			}else{
				pageUrl="redirect:/reception/receptionList";
			}
		}else{
			pageUrl= SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}

	@RequestMapping(value="getWizardPage3")
	public String getWizardPage3(HttpSession session,Model model,@RequestParam (value="newHotelDate") String newHotelDate) throws Exception{
		int naStage= Integer.parseInt(session.getAttribute("NightAuditStage").toString());
		String pageUrl= AUDIT_PAGE3_URL;
		permissionObj = pageAccessPermissionService.getPermission(session,"PMS_NIGHT_AUDT");
		try{
			if(permissionObj.isCanExecute() && permissionObj.isIs_execute_applicable()){
				model.addAttribute("curPagePerObj",permissionObj);
				if(naStage==1){
					nightAuditService.setNightAuditStage(2);
					session.setAttribute("NightAuditStage",2);
					Date hotdt = simpleDateformat.parse(newHotelDate);
					String newDate=simpleDateformat.format(hotdt);
					boolean isSave=nightAuditService.changeHotelDate(newDate);
					if(isSave){
						session.setAttribute("hotelDate",newDate);
						session.setAttribute("hotelDateEpoch",simpleDateformat.parseObject(newDate));
						commonSettings.setHotelDate(hotdt);

						List<ResvHdr> resCancelList = nightAuditService.getRecordToCancel();
						for(ResvHdr resv :resCancelList){
							resv.setCancelBy( ((User) session.getAttribute("userForm")).getId());
							resv.setStatus(ReservationStatus.CANCELLED.getCode());
							//resv.setCancelDate(commonSettings.getHotelDate());
							resv.setCancelDate(newDate);
							resv.setCancelTime(new Date());
							resv.setCancelReason("night audit automatic cancellation for unconfirmed reservation");		
							List<ResvRoom> resvRoomList = null;
							isSave = reservationService.cancellationSave(resv,resvRoomList,0,0);
						}
					}
					if(isSave){
						isSave=nightAuditService.cancelNonNoShowRooms(((User) session.getAttribute("userForm")).getId());
					}
					model.addAttribute("currency",session.getAttribute("currencySymbol").toString());
				}else{
					pageUrl="redirect:/reception/receptionList";
				}
			}else{
				pageUrl= SYDEF_PERMISION_DENIED_PAGE_URL;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageUrl;
	}

	@RequestMapping(value="getWizardPage4")
	public String getWizardPage4(HttpSession session,Model model){
		int naStage= Integer.parseInt(session.getAttribute("NightAuditStage").toString());
		String pageUrl= AUDIT_PAGE4_URL;
		permissionObj = pageAccessPermissionService.getPermission(session,"PMS_NIGHT_AUDT");
		if(permissionObj.isCanExecute() && permissionObj.isIs_execute_applicable()){
			model.addAttribute("curPagePerObj",permissionObj);
			if(naStage==2){
				nightAuditService.setNightAuditStage(3);
				session.setAttribute("NightAuditStage",3);
				model.addAttribute("currency",session.getAttribute("currencySymbol").toString());
			}else{
				pageUrl="redirect:/reception/receptionList";
			}
		}else{
			pageUrl= SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}

	@RequestMapping(value="finishNightAudit")
	public String finishNightAudit(HttpSession session){
		String pageUrl= "redirect:/reception/receptionList";
		permissionObj = pageAccessPermissionService.getPermission(session,"PMS_NIGHT_AUDT");
		if(permissionObj.isCanExecute() && permissionObj.isIs_execute_applicable()){
			nightAuditService.setNightAuditStage(0);
			session.setAttribute("NightAuditStage",0);
		}else{
			pageUrl= SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}

	/**
	 * No Show list function
	 * @return noshowJson
	 */
	@RequestMapping(value="expectedArrivals")
	public @ResponseBody String getExpectedArrivals(){
		JsonArray noshowJson=new JsonArray();
		noshowJson = nightAuditService.getArrivalList();
		return noshowJson.toString();
	}

	/**
	 * Expected Departure List function
	 * @return departJson
	 */
	@RequestMapping(value="expDepartDetails")
	public @ResponseBody String getExpDepartList(){
		JsonArray departJson=new JsonArray();
		departJson = nightAuditService.getDepartList();
		return departJson.toString();
	}

	@RequestMapping(value="inHouseList")
	public @ResponseBody String getInHouseList(HttpSession session) throws ParseException{
		JsonArray inHouseArray=new JsonArray();
		String htlDate=(String) session.getAttribute("hotelDate");
		String prevHotelDate=changeDateByDay(htlDate,-1);
		inHouseArray=nightAuditService.getInHouseList(prevHotelDate);
		return inHouseArray.toString();
	}
	@RequestMapping(value="getCurrentRoomRate",method= RequestMethod.POST,produces="application/json")
	public @ResponseBody String getCurrentRoomRate(HttpSession session,@RequestParam(value="checkinNo")int checkInNo) throws ParseException{
		JsonObject rateObj=new JsonObject();
		String htlDate=(String) session.getAttribute("hotelDate");
		String[] htlDte = htlDate.split(" ");
		String hotelDate = htlDte[0];
		String prevHotelDate=changeDateByDay(hotelDate,-1);
		rateObj=nightAuditService.getRoomRateForCurrentDate(prevHotelDate,checkInNo);
		return rateObj.toString();		
	}

	@RequestMapping(value="postNightAudit")
	public @ResponseBody String postNightAudit(@ModelAttribute Transaction txn,HttpSession session,@ModelAttribute AccountMaster accm,@ModelAttribute TaxHdr taxHdr) throws ParseException{
		String status="success";
		JsonArray inHouseArray=new JsonArray();
		JsonObject rateObj=new JsonObject();
		JsonObject jsonObj=new JsonObject();
		int userId = ((User) session.getAttribute("userForm")).getId();	
		String htlDate=(String) session.getAttribute("hotelDate");
		String[] htlDte = htlDate.split(" ");
		String hotelDate = htlDte[0];
		String prevHotelDate=changeDateByDay(hotelDate,-1);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String payTime = dateFormat.format(cal.getTime());	
		txn.setTxn_source(TxnSource.NIGHTAUDITAUTOPOSTING.getCode());
		txn.setTxn_date(prevHotelDate);
		txn.setTxn_time(payTime);
		txn.setTxn_status(TxnStatus.ACTIVE.getCode());
		txn.setRemarks("Night Audit");
		txn.setUser_id(userId);
		txn.setShift_id(nightAuditService.getlastShiftDetail(prevHotelDate));
		inHouseArray=nightAuditService.getInHouseList(prevHotelDate);
		for (JsonElement je : inHouseArray) {
			accm = transactionService.accMstId(1);	
			
			
			
			
			txn.setAcc_mst_code(accm.getCode());
			txn.setAcc_mst_id(1);
			JsonObject inHouseObj=je.getAsJsonObject();
	        //taxHdr=transactionService.getTaxCode(accm.getTax_id());
			/*< 2320 digna 20180622 begin*/
			boolean isBase = true;
			if(inHouseObj.get("inctax").getAsInt() == 1){
				isBase = false;	
			}
			taxHdr=transactionService.getTaxCode(inHouseObj.get("roomCharge").getAsDouble(),isBase);
			/*2320 digna 20180622 end >*/
			txn.setTax_id(taxHdr.getId());
			txn.setTax_code(taxHdr.getCode());
			txn.setFolio_no(inHouseObj.get("folioNo").getAsInt());
			txn.setFolio_bind_no(inHouseObj.get("folioBindNo").getAsInt());
			jsonObj=nightAuditService.getRoomRateForCurrentDate(prevHotelDate,inHouseObj.get("checkinNo").getAsInt());
			rateObj=jsonObj.get("expected").getAsJsonObject();
			txn.setIs_confirmed(true);
			txn.setTax1_amount(rateObj.get("tax1Amt").getAsDouble());
			txn.setTax2_amount(rateObj.get("tax2Amt").getAsDouble());
			txn.setTax3_amount(rateObj.get("tax3Amt").getAsDouble());
			txn.setTax4_amount(rateObj.get("tax4Amt").getAsDouble());
			txn.setServiceCharge(rateObj.get("serviceCharge").getAsBigDecimal());
			txn.setTax1_pc(rateObj.get("tax1Pc").getAsDouble());
			txn.setTax2_pc(rateObj.get("tax2Pc").getAsDouble());
			txn.setTax3_pc(rateObj.get("tax3Pc").getAsDouble());
			txn.setTax4_pc(rateObj.get("tax4Pc").getAsDouble());
			txn.setServiceChargePc(rateObj.get("serviceChargePc").getAsBigDecimal());
			txn.setAmount(inHouseObj.get("roomCharge").getAsDouble());
			txn.setBase_amount(rateObj.get("roomCharge").getAsDouble());
			txn.setNett_amount((rateObj.get("finalRoomCharge").getAsDouble())*-1);
			status=nightAuditService.postNightAudit(txn);
			
			if(status=="success" && rateObj.get("discId").getAsInt()!=0){
				accm = transactionService.accMstId(6);			
				txn.setAcc_mst_code(accm.getCode());
				txn.setAcc_mst_id(6);
				//taxHdr=transactionService.getTaxCode(accm.getTax_id());
				//taxHdr=transactionService.getTaxCode(inHouseObj.get("roomCharge").getAsDouble(),txn.isInclude_tax());
				txn.setTax_id(taxHdr.getId());
				txn.setTax_code(taxHdr.getCode());
				txn.setTax1_amount(-(rateObj.get("finalDiscAmt").getAsDouble()*(rateObj.get("tax1Pc").getAsDouble()/100)));
				txn.setTax2_amount(-(rateObj.get("finalDiscAmt").getAsDouble()*(rateObj.get("tax2Pc").getAsDouble()/100)));
				txn.setTax3_amount(-(rateObj.get("finalDiscAmt").getAsDouble()*(rateObj.get("tax3Pc").getAsDouble()/100)));
				txn.setTax4_amount(-(rateObj.get("finalDiscAmt").getAsDouble()*(rateObj.get("tax4Pc").getAsDouble()/100)));
				txn.setServiceCharge(BigDecimal.valueOf(0.0));
				txn.setServiceChargePc(BigDecimal.valueOf(0.0));
				txn.setTax1_pc(rateObj.get("tax1Pc").getAsDouble());
				txn.setTax2_pc(rateObj.get("tax2Pc").getAsDouble());
				txn.setTax3_pc(rateObj.get("tax3Pc").getAsDouble());
				txn.setTax4_pc(rateObj.get("tax4Pc").getAsDouble());
				txn.setAmount(rateObj.get("finalDiscAmt").getAsDouble());
				txn.setBase_amount(rateObj.get("finalDiscAmt").getAsDouble());
				txn.setNett_amount(rateObj.get("finalDiscAmt").getAsDouble());
				txn.setInclude_tax(true);
				status=nightAuditService.postNightAudit(txn);
				if(status=="success" && taxHdr.getId()!=1){
					accm = transactionService.accMstId(11);	
					txn.setAcc_mst_code(accm.getCode());
					txn.setAcc_mst_id(11);
					taxHdr=transactionService.getTaxCode(1);//taxid=1=notax
					txn.setTax_id(1);
					txn.setTax_code(taxHdr.getCode());
					txn.setTax1_amount(0.0);
					txn.setTax2_amount(0.0);
					txn.setTax3_amount(0.0);
					txn.setTax4_amount(0.0);
					txn.setServiceCharge(BigDecimal.valueOf(0.0));
					txn.setServiceChargePc(BigDecimal.valueOf(0.0));
					txn.setTax1_pc(0);
					txn.setTax2_pc(0);
					txn.setTax3_pc(0);
					txn.setTax4_pc(0);
					/*< 2317 digna 20180621 begin */
					double taxAdjAmount=Math.round((rateObj.get("finalDiscAmt").getAsDouble()*(rateObj.get("tax1Pc").getAsDouble()/100))*100.0)/100.0
							+Math.round((rateObj.get("finalDiscAmt").getAsDouble()*(rateObj.get("tax2Pc").getAsDouble()/100))*100.0)/100.0
							+Math.round((rateObj.get("finalDiscAmt").getAsDouble()*(rateObj.get("tax3Pc").getAsDouble()/100))*100.0)/100.0
							+Math.round((rateObj.get("finalDiscAmt").getAsDouble()*(rateObj.get("tax4Pc").getAsDouble()/100))*100.0)/100.0;
					/* 2317 digna 20180621 end >*/
					txn.setAmount(taxAdjAmount);
					txn.setBase_amount(taxAdjAmount);
					txn.setNett_amount(taxAdjAmount);
					txn.setInclude_tax(false);
					status=nightAuditService.postNightAudit(txn);
				}
			}
		}
		if(status.equals("success")){
			status=nightAuditService.confirmTransactions(txn.getTxn_date(),txn.getUser_id());
		}
		Gson g = new Gson();
		status = "status:"+status;
		return g.toJson(status).toString();
	}

	@RequestMapping(value="getNATransactions")
	public @ResponseBody String getNATransactions(HttpSession session) throws ParseException{
		JsonArray txnArray=new JsonArray();
		String htlDate=(String) session.getAttribute("hotelDate");
		String[] htlDte = htlDate.split(" ");
		String hotelDate = htlDte[0];
		String prevHotelDate=changeDateByDay(hotelDate,-1);
		txnArray=nightAuditService.getNightAuditTransactions(prevHotelDate);
		return txnArray.toString();
	}

	public String changeDateByDay(String hotDate,int days) throws ParseException{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date currDate,newDate;
		currDate = df.parse(hotDate);
		Calendar c = Calendar.getInstance(); 
		c.setTime(currDate); 
		c.add(Calendar.DATE,days);
		newDate = c.getTime();
		return df.format(newDate);
	}
	@RequestMapping(value="extendOneDay", method = RequestMethod.POST)
	public  @ResponseBody String extendOneDay(HttpSession session,@RequestBody String stayData)throws Exception{

		Gson gson = new Gson();
		boolean isSave=false;
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stayData);
		JSONObject jsonObject = (JSONObject) obj;
		List<CheckInHdr> chkinHdrs=new ArrayList<CheckInHdr>();
		try{
			for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();){
				String key = (String) iterator.next();
				
				CheckInHdr chkinHdr=new CheckInHdr();
				chkinHdr.setCheckInNo(Integer.parseInt(key));
				chkinHdr.setDayNum(1);
				chkinHdrs.add(chkinHdr);
          
			}
			isSave=nightAuditService.extendStayOneNight(chkinHdrs);
		}catch(Exception e){
			e.printStackTrace();
			throw new CustomException();
		}
		String saveStatus="success";
		if(isSave){
			saveStatus="status:"+saveStatus;
		}else{
			saveStatus="status:error";
		}
		return gson.toJson(saveStatus).toString();
	}
}
