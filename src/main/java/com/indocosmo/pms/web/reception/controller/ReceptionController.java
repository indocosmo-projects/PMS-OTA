package com.indocosmo.pms.web.reception.controller;

import java.io.File;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.enumerator.CheckinTypes;
import com.indocosmo.pms.enumerator.ReservationStatus;
import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.checkIn.model.CheckInDtl;
import com.indocosmo.pms.web.checkIn.model.CheckInHdr;
import com.indocosmo.pms.web.checkIn.model.CheckInRate;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.corporate.model.Corporate;
import com.indocosmo.pms.web.corporate.service.CorporateService;
import com.indocosmo.pms.web.dashboard.model.RoomDetails;
import com.indocosmo.pms.web.dashboard.service.DashboardService;
import com.indocosmo.pms.web.discount.model.Discount;
import com.indocosmo.pms.web.discount.service.DiscountService;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.reception.service.ReceptionService;
import com.indocosmo.pms.web.reservation_test.model.CheckinDiscount;
import com.indocosmo.pms.web.reservation_test.model.RoomRateDetailsCheck;
import com.indocosmo.pms.web.reservation_test.model.RoomRateEdited;
import com.indocosmo.pms.web.reservation_test.service.ReservationServiceTest;
import com.indocosmo.pms.web.shiftManagement.service.ShiftManagementService;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.systemSettings.model.Company;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;
import com.indocosmo.pms.web.transaction.service.TxnService;

@Controller
@RequestMapping(value = "/reception")
public class ReceptionController {
	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	@Autowired
	private ReceptionService receptionService;
	
	@Autowired
	TxnService transactionService;

	@Autowired
	private DashboardService dashboardService;

	@Autowired
	private SystemSettingsService systemSettingsService;

	@Autowired
	private ReservationServiceTest reservationService;

	@Autowired
	private CorporateService corporateService;

	@Autowired
	private ShiftManagementService shiftManagementService;
	@Autowired
	private DiscountService discountService;

	@Autowired
	private SystemSettingsService systemSettingService;
	
	private SysPermissions permissionObj;

	public static final Logger logger = LoggerFactory.getLogger(ReceptionController.class);

	public static final String RECEPTION_LIST_PAGE_URL = "reception/reception_list_new";

	private static final String NEW_RECEPTION = "reception/reception_new";
	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "access_denied/access_denied";

	/**
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "receptionList", method = RequestMethod.GET)
	public String listRedirect(HttpSession session, Model model) throws Exception {
		session.setAttribute("prevPageUrl", null);
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_RECPN");
		SysPermissions permissionObjDep = pageAccessPermissionService.getPermission(session, "PMS_DEPST");
		SysPermissions permissionObjPay = pageAccessPermissionService.getPermission(session, "PMS_RECPN_PAYMNT");
		SysPermissions permissionObjPost = pageAccessPermissionService.getPermission(session, "PMS_RECPN_POSTNG");
		SysPermissions permissionObjChkOut = pageAccessPermissionService.getPermission(session, "PMS_RECPN_CKHOUT");
		//List<AccountMaster> systemAccList = transactionService.getAccountMasterDetailsSystem(5997);
		String pageUrl = RECEPTION_LIST_PAGE_URL;
		if (permissionObj.isCanView() && permissionObj.isIs_view_applicable()) {
			model.addAttribute("curPagePerObj", permissionObj);
			model.addAttribute("depPagePerObj", permissionObjDep);
			model.addAttribute("payPagePerObj", permissionObjPay);
			model.addAttribute("postPagePerObj", permissionObjPost);
			model.addAttribute("chkOutPagePerObj", permissionObjChkOut);
		//	model.addAttribute("accountMaster", systemAccList);
			String htlDate = (String) session.getAttribute("hotelDate");
			String[] htlDte = htlDate.split(" ");
			String hotelDate = htlDte[0];
			String countshiftRemain = shiftManagementService.shiftcountOndateRemain(hotelDate);
			model.addAttribute("countshiftRemain", countshiftRemain);
			String count = shiftManagementService.isshiftOPen(hotelDate);
			model.addAttribute("count", count);
			session.setAttribute("count1", count);
		
			int LONGSTAY = (int) session.getAttribute("longStay");
			model.addAttribute("longStay", LONGSTAY);
		} else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}
	
	
	@RequestMapping(value = "/invoiceTypeList", method = RequestMethod.POST)
	public @ResponseBody String invoiceTypeList(HttpSession session,HttpServletRequest request) throws Exception {
		
		
		int folioNo = Integer.parseInt(request.getParameter("folioNo"));
		List<AccountMaster> systemAccList = null;
		Gson gson = new Gson();
		try {
			 systemAccList = transactionService.getAccountMasterDetailsSystem(folioNo);
			 
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		return gson.toJson(systemAccList);
	}
	
	@RequestMapping(value = "/getSystemAccMstList", method = RequestMethod.GET)
	public @ResponseBody String getSystemAccMstList(HttpSession session,HttpServletRequest request) throws Exception {
		
		
		List<AccountMaster> systemAccList = null;
		Gson gson = new Gson();
		try {
			 systemAccList = transactionService.getSystemAccMstList();
			 
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		return gson.toJson(systemAccList);
	}
	
	@RequestMapping(value = "/getAccMstList", method = RequestMethod.GET)
	public @ResponseBody String getAccMstList(HttpSession session,HttpServletRequest request) throws Exception {
		
		
		List<AccountMaster> systemAccList = null;
		Gson gson = new Gson();
		try {
			 systemAccList = transactionService.getAccMstList();
			 
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		return gson.toJson(systemAccList);
	}

	
	@RequestMapping(value = "/hkStatus", method = RequestMethod.POST)
	public @ResponseBody String hkStatus(HttpSession session, @RequestBody String data) throws Exception {
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(data).getAsJsonObject();
		int roomType = jsonObject.get("roomType").getAsInt();
		int floor = jsonObject.get("floor").getAsInt();
		List<RoomDetails> roomDtlList = null;
		Gson gson = new Gson();
		try {
			roomDtlList = dashboardService.getRoomDetails(roomType, floor);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		return gson.toJson(roomDtlList);
	}

	/**
	 * @param session
	 * @param request
	 * @param listParams
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getListData", method = RequestMethod.POST)
	public @ResponseBody String getListData(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "listParams", required = true) String listParams) throws Exception {
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonObject jobject = parser.parse(listParams).getAsJsonObject();
		//JsonObject jobj = new JsonObject();
		JsonObject resultObj = new JsonObject();
		try {
			if (jobject.get("pagination").getAsJsonObject().get("selectedTab").getAsString().equals("EXP")) {
				resultObj = reservationService.getListData(jobject);
			} else if (jobject.get("pagination").getAsJsonObject().get("selectedTab").getAsString().equals("INH")) {
				resultObj = receptionService.getListData(jobject);
			}

		} catch (Exception e) {
			logger.error("Method : getListData()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}
		return gson.toJson(resultObj);
	}

	/**
	 * @param init
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "newCheckin")
	public String getNewWalkInPage1(@RequestParam(value = "init", required = false) String init, Model model,
			HttpSession session) throws Exception {
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_RECPN");
		String pageUrl = NEW_RECEPTION;
		if (permissionObj.isCanAdd() && permissionObj.isIs_add_applicable()) {
			SystemSettings systemSettings = systemSettingsService.getSystemSettings();
			model.addAttribute("maxNight", systemSettings.getMaxNightsPerBooking());
			model.addAttribute("maxRooms", systemSettings.getMaxRoomsPerBooking());
			model.addAttribute("checkInTime", systemSettings.getCheckInTime());
			model.addAttribute("checkOutTime", systemSettings.getCheckOutTime());
			model.addAttribute("dateFormat", session.getAttribute("dateFormat").toString());
			model.addAttribute("hotelDate", systemSettings.getHotelDate());
		} else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}

	/**
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mailCheckInSave", method = RequestMethod.POST)
	public @ResponseBody String mailCheckInSave(HttpServletRequest request, HttpSession session,
			@RequestBody String addon) {
	//	boolean isSave = false;
	//	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	//	Gson newGson = new Gson();
		JsonParser parser = new JsonParser();
		JsonObject jobj = parser.parse(addon).getAsJsonObject();
		String checkInIds = jobj.get("data").toString();
		JsonArray jArray = (JsonArray) parser.parse(checkInIds);

		return receptionService.mailCheckInSave(jArray);
	}

	/**
	 * @param session
	 * @param minArrDate
	 * @param maxDepartDate
	 * @param roomTypeId
	 * @param occupancy
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getAvailableRooms", method = RequestMethod.POST)
	public @ResponseBody String getAvailableRooms(HttpSession session, @RequestParam("minArrDate") String minArrDate,
			@RequestParam("maxDepartDate") String maxDepartDate, @RequestParam("roomTypeId") int roomTypeId,
			@RequestParam("occupancy") int occupancy) throws Exception {
		Gson gson = new Gson();
		JsonArray roomList = new JsonArray();
		SimpleDateFormat dateFormatFrom = new SimpleDateFormat(session.getAttribute("dateFormat").toString());
		try {
			Date minArrivalDate = new java.sql.Date(dateFormatFrom.parse(minArrDate).getTime());
			Date maxDepartureDate = new java.sql.Date(dateFormatFrom.parse(maxDepartDate).getTime());
			roomList = reservationService.getAvailableRoomData(minArrivalDate, maxDepartureDate, roomTypeId, occupancy);
		} catch (Exception e) {
			logger.error("Method : getAvailableRooms()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}
		return gson.toJson(roomList);
	}

	/**
	 * @param session
	 * @param jsonData
	 * @return
	 */
	@RequestMapping(value = "saveNewCheckin", method = RequestMethod.POST)
	public @ResponseBody String saveNewCheckin(HttpSession session, @RequestBody String jsonData) {
		List<CheckInHdr> checkinHdrList = new ArrayList<CheckInHdr>();
		List<CheckInDtl> checkinDtlList = new ArrayList<CheckInDtl>();
		List<CheckInRate> checkinRateList = new ArrayList<CheckInRate>();
		SimpleDateFormat dateFormatFrom = new SimpleDateFormat(session.getAttribute("dateFormat").toString());
		boolean isSave = false;
		try {
			JsonParser jparser = new JsonParser();
			JsonObject jobj = jparser.parse(jsonData).getAsJsonObject();
			JsonObject hdrObj = jobj.get("checkinHdr").getAsJsonObject();
			JsonArray dtlArray = jobj.get("checkinDtl").getAsJsonArray();
			
			JsonArray checkinDiscount = jobj.get("checkinDiscnt").getAsJsonArray();
			
			for (JsonElement element : dtlArray) {
				JsonObject chDtlObject = element.getAsJsonObject();
				CheckInHdr checkHdr = new CheckInHdr();
				CheckInDtl checkDtl = new CheckInDtl();
				checkHdr.setArrDate(
						new java.sql.Date(dateFormatFrom.parse(hdrObj.get("minArrDate").getAsString()).getTime()));
				checkHdr.setArrTime(new java.util.Date());
				checkHdr.setExpDepartDate(
						new java.sql.Date(dateFormatFrom.parse(hdrObj.get("maxDepartDate").getAsString()).getTime()));
				checkHdr.setExpDepartTime(new java.util.Date());// to be set
				checkHdr.setResvType(hdrObj.get("resvType").getAsByte());
				checkHdr.setPayment_source(hdrObj.get("payment_source").getAsInt());
				checkHdr.setStatus(ReservationStatus.CHECKIN.getCode());
				checkHdr.setFullySettled(false);
				if (checkHdr.getResvType() == CheckinTypes.CORPORATE.getId()
						|| checkHdr.getResvType() == CheckinTypes.TA.getId()) {
					Corporate crp = corporateService.getRecord(hdrObj.get("corporateId").getAsInt());
					checkHdr.setCorporateId(hdrObj.get("corporateId").getAsInt());
					checkHdr.setCorporateName(crp.getName());
					checkHdr.setCorporateAddress(crp.getAddress());
				}
				checkHdr.setRoomTypeId(chDtlObject.get("roomTypeId").getAsInt());
				checkHdr.setRoomTypeCode(chDtlObject.get("roomTypeCode").getAsString());
				checkHdr.setRoomNumber(chDtlObject.get("roomNumber").getAsString());
				checkHdr.setOccupancy(chDtlObject.get("occupancy").getAsByte());
				checkHdr.setNumAdults(chDtlObject.get("numAdults").getAsByte());
				checkHdr.setNumChildren(chDtlObject.get("numChildren").getAsByte());
				checkHdr.setNumInfants(chDtlObject.get("numInfants").getAsByte());
				checkHdr.setBillingInstruction(hdrObj.get("remarks").getAsString());
				checkHdr.setSpecialRequests(hdrObj.get("specialRequests").getAsString());
				checkHdr.setCheckInBy(((User) session.getAttribute("userForm")).getId());
				checkHdr.setNumNights(hdrObj.get("nights").getAsInt());
				checkHdr.setRateId(chDtlObject.get("rateId").getAsInt());
				checkDtl.setSalutation(chDtlObject.get("salutation").getAsString());
				checkDtl.setFirstName(chDtlObject.get("firstName").getAsString());
				checkDtl.setLastName(chDtlObject.get("lastName").getAsString());
				checkDtl.setGuestName(chDtlObject.get("guestName").getAsString());
				checkDtl.setGender(chDtlObject.get("gender").getAsString());
				checkDtl.setAddress(chDtlObject.get("address").getAsString());
				checkDtl.setEmail(chDtlObject.get("email").getAsString());
				checkDtl.setPhone(chDtlObject.get("phone").getAsString());
				checkDtl.setNationality(chDtlObject.get("nationality").getAsString());
				checkDtl.setState(chDtlObject.get("state").getAsString());
				checkDtl.setPassportNo(chDtlObject.get("passportNo").getAsString());
				if (!(chDtlObject.get("passportExpiryOn").getAsString().equals(""))) {
					checkDtl.setPassportExpiryOn(new java.sql.Date(
							dateFormatFrom.parse(chDtlObject.get("passportExpiryOn").getAsString()).getTime()));
				}
				if (chDtlObject.get("gstno") != null) {
					checkDtl.setGstno(chDtlObject.get("gstno").getAsString());
				} else {
					checkDtl.setGstno("");
				}
				checkDtl.setRemarks(chDtlObject.get("remarks").getAsString());
				checkDtl.setRoomNumber(chDtlObject.get("roomNumber").getAsString());
				RoomRateDetailsCheck roomRateCheck = new RoomRateDetailsCheck();
				roomRateCheck.setArrDate(checkHdr.getArrDate());
				if (chDtlObject.get("discId") != null) {
					roomRateCheck.setDiscId(chDtlObject.get("discId").getAsInt());
					if (chDtlObject.get("openDiscount") != null) {
						roomRateCheck.setOpenDiscount(chDtlObject.get("openDiscount").getAsBigDecimal());
					}
				}
				roomRateCheck.setNumNights(checkHdr.getNumNights());
				roomRateCheck.setNumRooms(1);
				roomRateCheck.setOccupancy(checkHdr.getOccupancy());
				roomRateCheck.setRateId(checkHdr.getRateId());
				JsonObject rateObj = reservationService.getRoomRateDetails(roomRateCheck);
				JsonArray ratePerOcc = rateObj.get("ratePerOcc").getAsJsonArray();
				checkHdr.setRateType(ratePerOcc.get(0).getAsJsonObject().get("rate_type").getAsByte());
				checkHdr.setRateCode(ratePerOcc.get(0).getAsJsonObject().get("rate_code").getAsString());
				if (ratePerOcc.get(0).getAsJsonObject().get("rate_description").isJsonNull()) {
					checkHdr.setRateDescription(null);
				} else {
					checkHdr.setRateDescription(
							ratePerOcc.get(0).getAsJsonObject().get("rate_description").getAsString());
				}
				if (roomRateCheck.getDiscId() != 0) {
					checkHdr.setDiscId(roomRateCheck.getDiscId());
					Discount disc = discountService.getRecord(checkHdr.getDiscId());
					if (disc.getIsPc()) {
						if (disc.getIsOpen()) {
							checkHdr.setDiscPc(roomRateCheck.getOpenDiscount());
						} else {
							checkHdr.setDiscPc(disc.getDiscPc());
						}
					} else {
						if (disc.getIsOpen()) {
							checkHdr.setDiscAmount(roomRateCheck.getOpenDiscount());
						} else {
							checkHdr.setDiscAmount(disc.getDiscAmount());
						}
					}
					checkHdr.setDiscType(disc.getDiscType());
					checkHdr.setDiscCode(disc.getCode());
					checkHdr.setDiscIsPc(disc.getIsPc());
					checkHdr.setDiscIsOpen(disc.getIsOpen());
				}
				checkinHdrList.add(checkHdr);
				checkinDtlList.add(checkDtl);
				for (JsonElement rateJE : ratePerOcc) {
					CheckInRate checkRate = new CheckInRate();
					checkRate.setNight(rateJE.getAsJsonObject().get("night").getAsByte());
					checkRate.setNightDate(new Date(checkHdr.getArrDate().getTime()
							+ (((rateJE.getAsJsonObject().get("night").getAsByte()) - 1)) * 86400000));
					checkRate.setRoomCharge(rateJE.getAsJsonObject().get("charges").getAsBigDecimal());
					checkRate.setIncludeTax(rateJE.getAsJsonObject().get("is_tax_included").getAsBoolean());
					checkRate.setTax1Pc(rateJE.getAsJsonObject().get("tax1_pc").getAsBigDecimal());
					checkRate.setTax2Pc(rateJE.getAsJsonObject().get("tax2_pc").getAsBigDecimal());
					checkRate.setTax3Pc(rateJE.getAsJsonObject().get("tax3_pc").getAsBigDecimal());
					checkRate.setTax4Pc(rateJE.getAsJsonObject().get("tax4_pc").getAsBigDecimal());
					checkRate.setServiceChargePc(rateJE.getAsJsonObject().get("service_charge_pc").getAsBigDecimal());
					checkRate.setTax1(rateJE.getAsJsonObject().get("tax1_amount").getAsBigDecimal());
					checkRate.setTax2(rateJE.getAsJsonObject().get("tax2_amount").getAsBigDecimal());
					checkRate.setTax3(rateJE.getAsJsonObject().get("tax3_amount").getAsBigDecimal());
					checkRate.setTax4(rateJE.getAsJsonObject().get("tax4_amount").getAsBigDecimal());
					checkRate.setServiceCharge(rateJE.getAsJsonObject().get("service_charge_amount").getAsBigDecimal());
					checkRate.setRoomNumber(checkHdr.getRoomNumber());
					checkRate.setRateId(checkHdr.getRateId());
					checkRate.setRoomType(checkHdr.getRoomTypeId());
					checkRate.setOccupancy(checkHdr.getOccupancy());
					checkRate.setDiscId(checkHdr.getDiscId());
					checkRate.setDiscAmount(checkHdr.getDiscAmount());
					checkRate.setDiscPc(checkHdr.getDiscPc());
					checkinRateList.add(checkRate);
				}
			}
			isSave = receptionService.saveNewCheckin(checkinHdrList, checkinDtlList, checkinRateList,checkinDiscount);
		} catch (Exception e) {
			logger.error("Method : saveNewCheckin()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}
		Gson gson = new Gson();
		/*
		 * int varId = 0; for(int i = 0;i<checkinDtlList.size();i++){ varId =
		 * checkinDtlList.get(i).getCheckInNo(); }
		 */
		String saveStatus = "success";

		if (isSave) {
			/* for(CheckInDtl checkinDtl:checkinDtlList){ */
			for (CheckInHdr checkinHdr : checkinHdrList) {

				saveStatus = saveStatus + "_" + checkinHdr.getCheckInNo();
			}
		} else {
			saveStatus = "status:error";
		}
		return gson.toJson(saveStatus).toString();
	}

	/**
	 * @param request
	 * @param session
	 * @param imageFile
	 * @param idproof
	 * @return
	 */
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST)
	public @ResponseBody String saveNewCheckin(MultipartHttpServletRequest request, HttpSession session,
			@RequestParam("imageFile") MultipartFile[] imageFile, @RequestParam("idproof") MultipartFile[] idproof) {
		String rootPath = System.getProperty("catalina.home");
		String imageAbsolutePath = "/pms_uploads/customer_images/";
		String proofAbsolutePath = "/pms_uploads/customer_id/";
		String imageRootDirectory = rootPath + "/webapps/" + imageAbsolutePath;
		String proofRootDirectory = rootPath + "/webapps/" + proofAbsolutePath;
		boolean isSave = false;
		try {
			JsonObject jobj = null;
			JsonArray saveImageArray = new JsonArray();
			JsonArray saveProofArray = new JsonArray();

			if (imageFile.length != 0) {

				for (MultipartFile imgFile : imageFile) {
					jobj = receptionService.getCheckInNO(imgFile.getOriginalFilename());
					imgFile.transferTo(new File(imageRootDirectory + jobj.get("checkin_dtl_no").getAsInt() + "_"
							+ imgFile.getOriginalFilename() + ".png"));
					JsonObject imgobj = new JsonObject();
					imgobj.addProperty("checkin_dtl_no", jobj.get("checkin_dtl_no").getAsString());
					imgobj.addProperty("file", imageAbsolutePath + jobj.get("checkin_dtl_no").getAsInt() + "_"
							+ imgFile.getOriginalFilename() + ".png");
					saveImageArray.add(imgobj);

				}
			}
			if (idproof.length != 0) {
				for (MultipartFile proofFile : idproof) {
					jobj = receptionService
							.getCheckInNO(FilenameUtils.removeExtension(proofFile.getOriginalFilename()));
					proofFile.transferTo(new File(proofRootDirectory + jobj.get("checkin_dtl_no").getAsInt() + "_"
							+ proofFile.getOriginalFilename()));
					JsonObject proofObj = new JsonObject();
					proofObj.addProperty("checkin_dtl_no", jobj.get("checkin_dtl_no").getAsString());
					proofObj.addProperty("file", proofAbsolutePath + jobj.get("checkin_dtl_no").getAsInt() + "_"
							+ proofFile.getOriginalFilename());
					saveProofArray.add(proofObj);
				}
			}
			if (imageFile.length != 0 || idproof.length != 0) {
				isSave = receptionService.uploadCustomerFiles(saveImageArray, saveProofArray);
			}

		} catch (Exception e) {
			logger.error("Method : uploadFile()" + Throwables.getStackTraceAsString(e));
			isSave = false;
			e.printStackTrace();
			throw new CustomException();
		}
		Gson gson = new Gson();
		String saveStatus = "success";

		if (isSave) {
			saveStatus = "status:" + saveStatus;
		} else {
			saveStatus = "status:error";
		}
		return gson.toJson(saveStatus).toString();
	}

	/**
	 * @param session
	 * @param model
	 * @param checkInNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/receptionEdit")
	public String receptionEdit(HttpSession session, Model model, @RequestParam(value = "checkInNo") int checkInNo,
			@RequestParam(value = "status") int status) throws Exception {
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_RECPN");
		String pageUrl = "reception/reception_edit_new";
		if (permissionObj.isCanEdit() && permissionObj.isIs_edit_applicable()) {
			model.addAttribute("dateFormat", session.getAttribute("dateFormat").toString());
			model.addAttribute("hotelDate", session.getAttribute("hotelDate").toString());
			model.addAttribute("checkInNo", checkInNo);
			model.addAttribute("status", status);
		} else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}

	/**
	 * @param session
	 * @param checkinNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getReceptionEditData")
	public @ResponseBody String getReceptionEditData(HttpSession session, @RequestParam("checkinNo") int checkinNo)
			throws Exception {
		Gson gson = new Gson();
		JsonObject jobj = new JsonObject();
		HashMap<String, Object> resultData = null;
		List<CheckInDtl> checkInDtlList;
		CheckInHdr checkInHdr;
		List<CheckinDiscount> checkinDiscountList = null;
		Double folBal;
		int disApliedcnt;
		try {
			resultData = receptionService.getCheckInDetails(checkinNo);
			checkInHdr = (CheckInHdr) resultData.get("checkInHdr");
			checkinDiscountList = (List<CheckinDiscount>) resultData.get("checkDiscList");
			checkInDtlList = (List<CheckInDtl>) resultData.get("checkInDtlList");
			folBal = (Double) resultData.get("folioBal");
			disApliedcnt = (int) resultData.get("disAplied");
			
			jobj.addProperty("checkinHdr", gson.toJson(checkInHdr));
			jobj.add("checkinDtls", gson.toJsonTree(checkInDtlList));
			jobj.addProperty("folBal", folBal);
			jobj.addProperty("disApliedcnt", disApliedcnt);
			
			jobj.add("checkinDiscountList", gson.toJsonTree(checkinDiscountList));
		} catch (Exception e) {
			logger.error("Method : getReceptionEditData()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}
		return jobj.toString();
	}

	/**
	 * @param session
	 * @param receptionData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateReceptionEdit")
	public @ResponseBody String updateReceptionEdit(HttpSession session, @RequestBody String receptionData)
			throws Exception {
		Gson gson = new Gson();
		JsonParser jparser = new JsonParser();
		HashMap<Integer, RoomRateEdited> newRoomRateMap = new HashMap<Integer, RoomRateEdited>();
		JsonObject jobj = jparser.parse(receptionData).getAsJsonObject();
		JsonObject hdrObj = jobj.get("checkinHdr").getAsJsonObject();
		//JsonArray checkinDiscount = jobj.get("checkinDiscnt").getAsJsonArray();
		int newRateId = 0;
		boolean isSave = false;
		try {
			RoomRateEdited roomRateEdited = new RoomRateEdited();

			JsonObject jobject = jparser.parse(receptionData).getAsJsonObject();
			if (!jobject.get("editedAmount").isJsonNull()) {
				JsonObject newEditedAmount = jobject.get("editedAmount").getAsJsonObject();

				roomRateEdited.setCheckinNo(hdrObj.get("checkInNo").getAsInt());
				roomRateEdited.setRateId(newEditedAmount.get("old_id").getAsInt());

				roomRateEdited.setParentRateId(newEditedAmount.get("parent_id").getAsInt());
				newRoomRateMap.put(roomRateEdited.getRateId(), roomRateEdited);

				switch (newEditedAmount.get("occ").getAsString()) {
				case "occ1":
					roomRateEdited.setAmountOcc1(newEditedAmount.get("currentAmount").getAsDouble());
					break;
				case "occ2":
					roomRateEdited.setAmountOcc2(newEditedAmount.get("currentAmount").getAsDouble());
					break;
				case "occ3":
					roomRateEdited.setAmountOcc3(newEditedAmount.get("currentAmount").getAsDouble());
					break;
				case "occ4":
					roomRateEdited.setAmountOcc4(newEditedAmount.get("currentAmount").getAsDouble());
					break;
				}
				List<RoomRateEdited> list = new ArrayList<RoomRateEdited>(newRoomRateMap.values());
				newRateId = receptionService.newRoomRateIds(list);
				hdrObj.addProperty("rateId", newRateId);
			}

			String htlDate = (String) session.getAttribute("hotelDate");
			String[] date = htlDate.split(" ");
			String hotelDate = date[0];

			isSave = receptionService.updateReceptionEdit(jobj, hotelDate);

		} catch (Exception e) {
			logger.error("Method : updateReceptionEdit()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}
		String saveStatus = "success";
		if (isSave) {
			saveStatus = "status:" + saveStatus + "_" + newRateId;
		} else {
			saveStatus = "status:error";
		}
		return gson.toJson(saveStatus).toString();
	}

	@RequestMapping(value = "/getDetailViaPhonenum")
	public @ResponseBody String getDetailViaPhonenum(HttpSession session, @RequestBody String phonenum)
			throws Exception {
		JsonArray jArray = receptionService.customerDetailViaPhone(phonenum);
		return jArray.toString();
	}

	@RequestMapping(value = "/changeRoomStatus")
	public void changeRoomStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final int id = Integer.parseInt(request.getParameter("id"));

		try {
			receptionService.changeRoomStatus(id);
		} catch (Exception e) {
			logger.error("Method : changeRoomStatus()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}

	}
   
	@RequestMapping(value="/printGrcForm")
	public ModelAndView printGrcForm(@RequestParam(value="recpNo") int recpNo, HttpSession session) throws Exception {
		
		JsonObject jsonCompany=new JsonObject();
		JsonObject jsonGrcFormData=new JsonObject();
		JsonArray grcData=receptionService.getGrcFormData(recpNo);
		
		Company company=new Company();
		company = systemSettingService.getcompany();
		jsonCompany.addProperty("companyName", company.getCompanyName());
		jsonCompany.addProperty("companyN", company.getLogoFolder());
		jsonCompany.addProperty("streetName", company.getStreetName());
		jsonCompany.addProperty("cityName", company.getCityName());
		jsonCompany.addProperty("image", (String) session.getAttribute("rootPath"));
		jsonCompany.addProperty("email", company.getEmail());
		jsonCompany.addProperty("url", company.getUrl());
		jsonCompany.addProperty("phone", company.getPhone());
		
		
		
			
			jsonGrcFormData.add("grcDetails",grcData);
			jsonGrcFormData.add("companyDetails", jsonCompany);
			return new ModelAndView("pdfReceptionGrc", "jsonGrcFormData", jsonGrcFormData);
		
		
	}
}
