package com.indocosmo.pms.web.reservation_test.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.base.Throwables;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.enumerator.ReservationStatus;
import com.indocosmo.pms.enumerator.TxnSource;
import com.indocosmo.pms.enumerator.discount.DiscountType;
import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.corporate.model.Corporate;
import com.indocosmo.pms.web.corporate.service.CorporateService;
import com.indocosmo.pms.web.discount.model.Discount;
import com.indocosmo.pms.web.discount.service.DiscountService;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.payment.service.PaymentService;
import com.indocosmo.pms.web.reservation.controller.ReservationController;
import com.indocosmo.pms.web.reservation_test.model.AvailableRoomTypes;
import com.indocosmo.pms.web.reservation_test.model.CheckinDiscount;
import com.indocosmo.pms.web.reservation_test.model.ResvDtl;
import com.indocosmo.pms.web.reservation_test.model.ResvHdr;
import com.indocosmo.pms.web.reservation_test.model.ResvRoom;
import com.indocosmo.pms.web.reservation_test.model.ResvRoomRatePlans;
import com.indocosmo.pms.web.reservation_test.model.RoomRateDetailsCheck;
import com.indocosmo.pms.web.reservation_test.model.RoomRateEdited;
import com.indocosmo.pms.web.reservation_test.service.ReservationServiceTest;
import com.indocosmo.pms.web.roomType.service.RoomTypeService;
import com.indocosmo.pms.web.shiftManagement.model.ShiftManagement;
import com.indocosmo.pms.web.shiftManagement.service.ShiftManagementService;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.systemSettings.model.Company;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;
import com.indocosmo.pms.web.tax.model.TaxHdr;
import com.indocosmo.pms.web.transaction.model.Transaction;
import com.indocosmo.pms.web.transaction.service.TxnService;

@Controller
@RequestMapping(value = "/reservation_test")
public class ReservationControllerTest {

	@Autowired
	ReservationServiceTest reservationService;

	@Autowired
	SystemSettingsService systemSettingsService;

	@Autowired
	DiscountService discountService;

	@Autowired
	CorporateService corporateService;

	@Autowired
	RoomTypeService roomTypeService;

	@Autowired
	private ShiftManagementService shiftManagementService;

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	@Autowired
	TxnService transactionService;

	@Autowired
	PaymentService paymentService;

	@Autowired
	private SystemSettingsService systemSettingService;

	private SysPermissions permissionObj;

	public static final String RESERVATION_LIST_PAGE_URL = "reservation_test/reservation_list_new";
	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "access_denied/access_denied";
	
	
	public static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

	/**
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */

	
	@RequestMapping(value = "reservationList", method = RequestMethod.GET)
	public String listRedirect(Model model, HttpSession session, HttpServletRequest request) throws Exception {
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_RESV");
		model.addAttribute("curPagePerObj", permissionObj);
		String htlDate = (String) session.getAttribute("hotelDate");
		String[] htlDte = htlDate.split(" ");
		String hotelDate = htlDte[0];
		String countshiftRemain = shiftManagementService.shiftcountOndateRemain(hotelDate);
		model.addAttribute("countshiftRemain", countshiftRemain);
		String count = shiftManagementService.isshiftOPen(hotelDate);
		model.addAttribute("count", count);
		session.setAttribute("count1", count);
		return ((permissionObj.isCanView() && permissionObj.isIs_view_applicable()) ? RESERVATION_LIST_PAGE_URL
				: SYDEF_PERMISION_DENIED_PAGE_URL);
	}

	/**
	 * @param session
	 * @param request
	 * @param listParams
	 * @return
	 * @throws Exception
	 */
	/*
	 * @RequestMapping(value = "getListData", method = RequestMethod.POST)
	 * public @ResponseBody String getListData(HttpSession
	 * session,HttpServletRequest request,@RequestParam(value = "listParams",
	 * required = true) String listParams) throws Exception { Gson gson = new
	 * Gson(); JsonParser parser = new JsonParser(); JsonObject jobject =
	 * parser.parse(listParams).getAsJsonObject(); JsonObject jobj = new
	 * JsonObject(); JsonObject resultObj =new JsonObject(); Map<String, String>
	 * simpleSearchMap = new HashMap<String, String>(); Map<String, String>
	 * advanceSearchMap = new HashMap<String, String>(); try{
	 * jobj.add("sort",jobject.get("sort").getAsJsonObject()); JsonObject searchObj
	 * = jobject.get("search").getAsJsonObject();
	 * jobj.add("pagination",jobject.get("pagination").getAsJsonObject());
	 * if(searchObj.get("comnSearch").getAsBoolean()){ if
	 * (!searchObj.get("comnSearchValue").isJsonNull() &&
	 * !searchObj.get("comnSearchValue").getAsString().equals("")) {
	 * simpleSearchMap.put("resv_status_xlt",
	 * searchObj.get("comnSearchValue").getAsString());
	 * simpleSearchMap.put("corporate_name",
	 * searchObj.get("comnSearchValue").getAsString());
	 * simpleSearchMap.put("reserved_by",
	 * searchObj.get("comnSearchValue").getAsString());
	 * simpleSearchMap.put("resv_status_xlt",
	 * searchObj.get("comnSearchValue").getAsString()); } }else
	 * if(searchObj.get("advSearch").getAsBoolean()){ if(
	 * searchObj.get("resvNo").getAsJsonObject().get("searchable").getAsBoolean()){
	 * advanceSearchMap.put("resv_no",
	 * searchObj.get("resvNo").getAsJsonObject().get("searchValue").getAsString());
	 * } if(
	 * searchObj.get("resvBy").getAsJsonObject().get("searchable").getAsBoolean()){
	 * advanceSearchMap.put("reserved_by",
	 * searchObj.get("resvBy").getAsJsonObject().get("searchValue").getAsString());
	 * } if(
	 * searchObj.get("resvDate").getAsJsonObject().get("searchable").getAsBoolean())
	 * { advanceSearchMap.put("resv_date",
	 * searchObj.get("resvDate").getAsJsonObject().get("searchValue").getAsString())
	 * ; } if(
	 * searchObj.get("arrDate").getAsJsonObject().get("searchable").getAsBoolean()){
	 * advanceSearchMap.put("arr_date",
	 * searchObj.get("arrDate").getAsJsonObject().get("searchValue").getAsString());
	 * } if(
	 * searchObj.get("resvStatus").getAsJsonObject().get("searchable").getAsBoolean(
	 * )){ advanceSearchMap.put("resv_status_xlt",
	 * searchObj.get("resvStatus").getAsJsonObject().get("searchValue").getAsString(
	 * )); } }
	 * jobj.addProperty("common",searchObj.get("comnSearch").getAsBoolean());
	 * resultObj =
	 * reservationService.getListData(jobj,advanceSearchMap,simpleSearchMap);
	 * }catch(Exception e){ e.printStackTrace(); throw new CustomException(); }
	 * return gson.toJson(resultObj); }
	 */
	 
	@RequestMapping(value = "getListData", method = RequestMethod.POST)
	public @ResponseBody String getListData(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "listParams", required = true) String listParams) throws Exception {
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonObject jobject = parser.parse(listParams).getAsJsonObject();
		JsonObject resultObj = new JsonObject();
		try {
			resultObj = reservationService.getListData(jobject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		return gson.toJson(resultObj);
	}

	/**
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "newReservation", method = RequestMethod.GET)
	public String newReservation(Model model, HttpSession session, HttpServletRequest request) throws Exception {
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_RESV");
		model.addAttribute("dateFormat", session.getAttribute("dateFormat").toString());
		SystemSettings systemSettings = systemSettingsService.getSystemSettings();
		model.addAttribute("maxNight", systemSettings.getMaxNightsPerBooking());
		model.addAttribute("maxRooms", systemSettings.getMaxRoomsPerBooking());
		model.addAttribute("checkInTime", systemSettings.getCheckInTime());
		model.addAttribute("checkOutTime", systemSettings.getCheckOutTime());
		model.addAttribute("hotelDate", systemSettings.getHotelDate());
		model.addAttribute("confirmbefore", systemSettings.getConfirmBefore());
		model.addAttribute("dateFormat", session.getAttribute("dateFormat").toString());
		return ((permissionObj.isCanAdd() && permissionObj.isIs_add_applicable()) ? "reservation_test/reservation_new"
				: SYDEF_PERMISION_DENIED_PAGE_URL);
	}

	/**
	 * @param session
	 * @param request
	 * @param resvHdr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getRoomAvailability", method = RequestMethod.POST)
	public @ResponseBody String getRoomAvailability(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "resvHdr", required = true) String resvHdr) throws Exception {
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonObject jobj = parser.parse(resvHdr).getAsJsonObject();
		SimpleDateFormat dateFormatFrom = new SimpleDateFormat(session.getAttribute("dateFormat").toString());
		Date chInDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("minArrDate").getAsString()).getTime());
		int night = jobj.get("nights").getAsInt();
		int rooms = jobj.get("numRooms").getAsInt();

		List<AvailableRoomTypes> available = reservationService.getRoomAvailability(chInDate, night, rooms);

		return gson.toJson(available);
	}

	/**
	 * @param session
	 * @param request
	 * @param roomTypeDtls
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getRoomRatePlans", method = RequestMethod.POST)
	public @ResponseBody String getRoomRatePlans(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "roomTypeDtls", required = true) String roomTypeDtls) throws Exception {
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonObject jobj = parser.parse(roomTypeDtls).getAsJsonObject();
		SimpleDateFormat dateFormatFrom = new SimpleDateFormat(session.getAttribute("dateFormat").toString());
		Date arrDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("arrDate").getAsString()).getTime());
		int night = jobj.get("nights").getAsInt();
		String roomType = jobj.get("roomType").getAsString();
		int rateid = jobj.get("rate_id").getAsInt();
		int trCrp_id = 0;
		if (null != jobj.get("trCrp_id")) {
			trCrp_id = jobj.get("trCrp_id").getAsInt();
		}
		List<ResvRoomRatePlans> ratePlans = reservationService.getRoomRates(arrDate, night, trCrp_id, roomType, rateid);
		JsonObject jobject = new JsonObject();
		jobject.add("ratePlans", gson.toJsonTree(ratePlans));
		if (ratePlans.size() != 0) {
			JsonObject images = roomTypeService.getRoomTypeImages(ratePlans.get(0).getRoomTypeId());
			jobject.add("images", images);
		}
		return jobject.toString();
	}

	@RequestMapping(value = "/getnewRoomRateDetails", method = RequestMethod.POST)
	public @ResponseBody String saveNew(HttpServletRequest request, HttpSession session, @RequestBody String addon)
			throws Exception {
		JsonArray jArray = null;
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonObject jobj = parser.parse(addon).getAsJsonObject();
		HashMap<Integer, RoomRateEdited> newRoomRateMap = new HashMap<Integer, RoomRateEdited>();
		try {
			for (JsonElement element : jobj.getAsJsonArray("data")) {

				JsonObject jobject = element.getAsJsonObject();
				RoomRateEdited roomRateEdited = null;
				if (newRoomRateMap.containsKey(jobject.get("rateId").getAsInt())) {

					roomRateEdited = newRoomRateMap.get(jobject.get("rateId").getAsInt());

				} else {

					roomRateEdited = new RoomRateEdited();
					roomRateEdited.setRateId(jobject.get("rateId").getAsInt());
					newRoomRateMap.put(roomRateEdited.getRateId(), roomRateEdited);

				}
				switch (jobject.get("occ").getAsString()) {
				case "occ1":
					roomRateEdited.setAmountOcc1(jobject.get("newAmount").getAsDouble());
					break;
				case "occ2":
					roomRateEdited.setAmountOcc2(jobject.get("newAmount").getAsDouble());
					break;
				case "occ3":
					roomRateEdited.setAmountOcc3(jobject.get("newAmount").getAsDouble());
					break;
				case "occ4":
					roomRateEdited.setAmountOcc4(jobject.get("newAmount").getAsDouble());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<RoomRateEdited> list = new ArrayList<RoomRateEdited>(newRoomRateMap.values());
		jArray = reservationService.updateNewRoomRates(list);
		return gson.toJson(jArray);

	}

	/**
	 * @param session
	 * @param request
	 * @param roomRateDetails
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getRoomRateDetails", method = RequestMethod.POST)
	public @ResponseBody String getRoomRateDetails(HttpSession session, HttpServletRequest request,
			@RequestBody String roomRateDetails) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Gson gson = new Gson();
		JsonArray jArray = new JsonArray();
		SimpleDateFormat dateFormatFrom = new SimpleDateFormat(session.getAttribute("dateFormat").toString());
		mapper.setDateFormat(dateFormatFrom);
		try {
			List<RoomRateDetailsCheck> roomRateDetailsList = mapper.readValue(roomRateDetails,
					TypeFactory.defaultInstance().constructCollectionType(List.class, RoomRateDetailsCheck.class));
			for (RoomRateDetailsCheck rateDtls : roomRateDetailsList) {
				JsonObject rateObj = reservationService.getRoomRateDetails(rateDtls);
				jArray.add(rateObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		return gson.toJson(jArray);
	}

	@RequestMapping(value = "/getRoomRateForAllOccupancy", method = RequestMethod.POST)
	public @ResponseBody String getRoomRateForAllOccupancy(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "occupancyRateDtl", required = true) String occupancyRateDtl) throws Exception {
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonObject jobj = parser.parse(occupancyRateDtl).getAsJsonObject();
		SimpleDateFormat dateFormatFrom = new SimpleDateFormat(session.getAttribute("dateFormat").toString());
		Date arrDate = new java.sql.Date(dateFormatFrom.parse(jobj.get("arrDate").getAsString()).getTime());
		int night = jobj.get("nights").getAsInt();
		String roomType = jobj.get("roomType").getAsString();
		int rateid = jobj.get("rate_id").getAsInt();

		List<RoomRateDetailsCheck> occupancyRate = reservationService.getRoomRateForAllOccupancy(arrDate, night, rateid,
				roomType);
		JsonObject jobject = new JsonObject();
		jobject.add("occupancyRate", gson.toJsonTree(occupancyRate));
		jobject.add("roomRateActualDetail", gson.toJsonTree(occupancyRate));

		return jobject.toString();

	}

	/**
	 * @param session
	 * @param request
	 * @param rateIds
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getDiscountDetails", method = RequestMethod.POST)
	public @ResponseBody String getDiscountDetails(HttpSession session, HttpServletRequest request,
			@RequestBody String[] rateIds) throws Exception {
		Gson gson = new Gson();
		JsonArray planBasedArray = new JsonArray();
		JsonObject jobj = new JsonObject();
		try {
			for (String rateId : rateIds) {
				List<Discount> discountList = discountList = discountService.getDiscounts(rateId);
				if (discountList.size() != 0) {
					JsonElement element = gson.toJsonTree(discountList, new TypeToken<List<Discount>>() {
					}.getType());
					for (JsonElement el : element.getAsJsonArray()) {
						planBasedArray.add(el);
					}
				}
			}
			List<Discount> generaldiscountList = discountService.getDiscounts(DiscountType.GENERALDISCOUNT);
			jobj.add("general", gson.toJsonTree(generaldiscountList));
			jobj.add("plan", planBasedArray);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getDiscountDetails()" + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return gson.toJson(jobj);
	}

	/**
	 * @param session
	 * @param request
	 * @param corpClass
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getTACorpList", method = RequestMethod.POST)
	public @ResponseBody String getTACorpList(HttpSession session, HttpServletRequest request,
			@RequestBody int corpClass) throws Exception {
		Gson gson = new Gson();
		/*
		 * int cClass=1; if(corpClass==2){ cClass=2; }
		 */
		List<Corporate> corpList = corporateService.listOfCorporate(corpClass, "");
		return gson.toJson(corpList);
	}

	/**
	 * @param session
	 * @param request
	 * @param reservationNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "mailReservationSave", method = RequestMethod.POST)
	public @ResponseBody String mailReservationSave(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "reservationNo") int resvNo) throws Exception {
		return reservationService.mailReservationSave(resvNo);
	}

	/**
	 * @param session
	 * @param request
	 * @param resvNum
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "mailNoshowSave", method = RequestMethod.POST)
	public @ResponseBody String mailNoshowSave(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "resvNum") int resvNo) throws Exception {
		return reservationService.mailNoshowSave(resvNo);
	}

	/**
	 * @param session
	 * @param resvData
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "saveNewReservation", method = RequestMethod.POST)
	public @ResponseBody String saveReservation(HttpSession session, @RequestBody String resvData) {
		Gson gson = new Gson();
		SimpleDateFormat dateFormatFrom = new SimpleDateFormat("yyyy-MM-dd");
	//	LocalTime timeFormat=LocalTime.parse(session.getAttribute("timeFormat").toString());
		boolean isSave = false;
		JsonParser parser = new JsonParser();
		ObjectMapper mapper = new ObjectMapper();
		JsonObject jobj = parser.parse(resvData).getAsJsonObject();
      //  String dateFormat=parser.getClass();
		if (jobj.get("resvHdr").getAsJsonObject().get("resvByFirstName") instanceof JsonObject) {
			String resvByFirstName = jobj.get("resvHdr").getAsJsonObject().get("resvByFirstName").getAsJsonObject()
					.get("display").getAsString();
			jobj.get("resvHdr").getAsJsonObject().remove("resvByFirstName");
			jobj.get("resvHdr").getAsJsonObject().addProperty("resvByFirstName", resvByFirstName);
			// checkDtl.setFirstName(chDtlObject.get("firstName").getAsJsonObject().get("display").getAsString());

		}
		
		JsonArray checkinDiscount = jobj.get("checkinDiscnt").getAsJsonArray();
		
		ResvHdr resvHdr = new ResvHdr();
		try {
			String resvdetails = null;
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapper.setDateFormat(dateFormatFrom);
			resvHdr = mapper.readValue(jobj.get("resvHdr").toString(), ResvHdr.class);
			
			resvdetails=mapper.setDateFormat(dateFormatFrom).writeValueAsString(resvHdr);
			if (resvHdr.getCorporateId() != 0) {
				Corporate crp = corporateService.getRecord(resvHdr.getCorporateId());
				resvHdr.setCorporateName(crp.getName());
				resvHdr.setCorporateAddress(crp.getAddress());
			}
			resvHdr.setResvTakenBy(((User) session.getAttribute("userForm")).getId());
			List<ResvDtl> resvDtlList = mapper.readValue(jobj.get("resvDtls").toString(),
					TypeFactory.defaultInstance().constructCollectionType(List.class, ResvDtl.class));
			List<RoomRateDetailsCheck> roomRateDetailsList = mapper.readValue(jobj.get("resvDtls").toString(),
					TypeFactory.defaultInstance().constructCollectionType(List.class, RoomRateDetailsCheck.class));
			isSave = reservationService.save(resvdetails, resvDtlList,checkinDiscount);

			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : saveNewReservation()" + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		String saveStatus = "success";

		if (isSave) {
			saveStatus = "status:" + saveStatus + "_" + resvHdr.getResvNo();
		} else {
			saveStatus = "status:error";
		}
		return gson.toJson(saveStatus).toString();
	}

	/**
	 * @param session
	 * @param request
	 * @param startDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getCalendarData", method = RequestMethod.POST)
	public @ResponseBody String getCalendarData(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "startDate") String startDate) throws Exception {
	//	ObjectMapper mapper = new ObjectMapper();
		Gson gson = new Gson();
		JsonArray jArray = null;
		SimpleDateFormat dateFormatFrom = new SimpleDateFormat("yyyy-MM-dd");
		try {
			jArray = reservationService.getCalendarData(new Date(dateFormatFrom.parse(startDate).getTime()), 7);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		return gson.toJson(jArray);
	}

	/**
	 * @param session
	 * @param model
	 * @param resvHdr
	 * @param resvNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reservationEdit")
	public String reservationEdit(HttpSession session, Model model, @ModelAttribute ResvHdr resvHdr,
			@RequestParam(value = "reservationNo") int resvNo) throws Exception {
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_RESV");
		model.addAttribute("dateFormat", session.getAttribute("dateFormat").toString());
		SystemSettings systemSettings = systemSettingsService.getSystemSettings();
		model.addAttribute("reservationNo", resvNo);
		model.addAttribute("maxNight", systemSettings.getMaxNightsPerBooking());
		model.addAttribute("maxRooms", systemSettings.getMaxRoomsPerBooking());
		return ((permissionObj.isCanEdit() && permissionObj.isIs_edit_applicable())
				? "reservation_test/reservation_edit_new"
				: SYDEF_PERMISION_DENIED_PAGE_URL);
	}

	/**
	 * @param session
	 * @param model
	 * @param resvNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getReservationRecord", method = RequestMethod.POST)
	public @ResponseBody String getReservationRecord(HttpSession session, Model model,
			@RequestParam(value = "reservationNo") int resvNo) throws Exception {
		HashMap<String, Object> resultData = null;
		ResvHdr resvHdr = null;
		List<ResvDtl> resvDtlList = null;
		List<ResvRoom> resvRoomList = null;
		List<CheckinDiscount> checkinDiscountList = null;
		Gson gson = new Gson();
		JsonObject jObject = new JsonObject();
		try {
			resultData = reservationService.getResvRecord(resvNo);
			resvHdr = (ResvHdr) resultData.get("resvHdr");
			checkinDiscountList = (List<CheckinDiscount>) resultData.get("checkDiscList");
			resvDtlList = (List<ResvDtl>) resultData.get("resvDtlList");
			resvRoomList = (List<ResvRoom>) resultData.get("resvRoomList");
			jObject.addProperty("resvHdr", gson.toJson(resvHdr));
			jObject.add("resvDtl", gson.toJsonTree(resvDtlList));
			jObject.add("resvRoom", gson.toJsonTree(resvRoomList));
			jObject.add("checkinDiscountList", gson.toJsonTree(checkinDiscountList));
			model.addAttribute("resvHdr", resvHdr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		return jObject.toString();
	}

	/**
	 * @param session
	 * @param resvData
	 * @return
	 */
	@RequestMapping(value = "saveReservationEdit", method = RequestMethod.POST)
	public @ResponseBody String saveReservationEdit(HttpSession session, @RequestBody String resvData) {
		Gson gson = new Gson();
		boolean isSave = false;
		JsonParser parser = new JsonParser();
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat simpleDateformat = new SimpleDateFormat(session.getAttribute("dateFormat").toString());
		JsonObject jobj = parser.parse(resvData).getAsJsonObject();
		ResvHdr resvHdrNew = new ResvHdr();
		try {
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapper.setDateFormat(simpleDateformat);
			JsonObject changesObj = jobj.get("changes").getAsJsonObject();
			resvHdrNew = mapper.readValue(jobj.get("resv").getAsJsonObject().get("hdr").toString(), ResvHdr.class);
			JsonArray checkinDiscount = jobj.get("checkinDiscnt").getAsJsonArray();
			if (resvHdrNew.getCorporateId() != 0) {
				Corporate crp = corporateService.getRecord(resvHdrNew.getCorporateId());
				resvHdrNew.setCorporateName(crp.getName());
				resvHdrNew.setCorporateAddress(crp.getAddress());
			}
			resvHdrNew.setResvTakenBy(((User) session.getAttribute("userForm")).getId());
			List<RoomRateDetailsCheck> resvDtlListNew = mapper.readValue(jobj.get("resvDtls").toString(),
					TypeFactory.defaultInstance().constructCollectionType(List.class, RoomRateDetailsCheck.class));
			List<ResvRoom> resvRoomListNew = mapper.readValue(jobj.get("resv").getAsJsonObject().get("room").toString(),
					TypeFactory.defaultInstance().constructCollectionType(List.class, ResvRoom.class));

			isSave = reservationService.saveReservationEdit(resvHdrNew, resvDtlListNew, resvRoomListNew, changesObj,checkinDiscount);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : saveNewReservation()" + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		String saveStatus = "success";
		if (isSave) {
			saveStatus = "status:" + saveStatus;
		} else {
			saveStatus = "status:error";
		}
		return gson.toJson(saveStatus).toString();
	}

	/**
	 * @param model
	 * @param resvId
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/changeArrivalDate", method = RequestMethod.GET)
	public String changeArrivalDate(Model model, @RequestParam(value = "reservationNo") int resvId, HttpSession session)
			throws Exception {
		String pageUrl = "reservation_test/reservation_change_arrival_date";
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_RESV");
		if (permissionObj.isCanEdit() && permissionObj.isIs_edit_applicable()) {
			model.addAttribute("dateFormat", session.getAttribute("dateFormat").toString());
			model.addAttribute("reservationNo", resvId);
		} else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}

	/**
	 * @param resvId
	 * @return
	 */
	@RequestMapping(value = "getResvDtlList", method = RequestMethod.POST)
	public @ResponseBody String getResvDtlList(@RequestParam(value = "reservationNo") int resvId) {
		JsonObject jobj = new JsonObject();
		Gson gson = new Gson();
		try {
			jobj = reservationService.getResvDtlList(resvId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		return gson.toJson(jobj);
	}

	/**
	 * @param resvNo
	 * @param newArrivalDate
	 * @param discType
	 * @param noNight
	 * @param resvType
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getNewArrivalDateData", method = RequestMethod.POST)
	public @ResponseBody String getNewArrivalDateData(@RequestParam(value = "resvNo", required = true) int resvNo,
			@RequestParam(value = "newArrivalDate", required = true) String newArrivalDate,
			@RequestParam(value = "discType") int discType, @RequestParam(value = "noNight") int noNight,
			@RequestParam(value = "resvType") int resvType, HttpSession session) {

		JsonObject roomRates = new JsonObject();
		SimpleDateFormat simpleDateformat = new SimpleDateFormat(session.getAttribute("dateFormat").toString());
		try {
			java.sql.Date sqlDate = new java.sql.Date(simpleDateformat.parse(newArrivalDate).getTime());
			roomRates = reservationService.getNewArrivalDateData(resvNo, sqlDate, discType, noNight, resvType);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getRoomRates " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return roomRates.toString();
	}

	/**
	 * @param roomRateDetails
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveChangeArrivalDate", method = RequestMethod.POST)
	public @ResponseBody String saveChangeArrivalDate(@RequestBody String roomRateDetails, HttpSession session)
			throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonObject jobj = parser.parse(roomRateDetails).getAsJsonObject();
		int resvNo = jobj.get("resvNo").getAsInt();
		SimpleDateFormat dateFormatFrom = new SimpleDateFormat(session.getAttribute("dateFormat").toString());
		java.util.Date arrDate = dateFormatFrom.parse(jobj.get("arrDate").getAsString());
		mapper.setDateFormat(dateFormatFrom);
		boolean isSave = false;
		try {
			List<RoomRateDetailsCheck> roomRateDetailsList = mapper.readValue(jobj.get("rateDtl").toString(),
					TypeFactory.defaultInstance().constructCollectionType(List.class, RoomRateDetailsCheck.class));
			isSave = reservationService.saveChangeArrivalDate(resvNo, arrDate, roomRateDetailsList,
					jobj.get("disc").getAsJsonArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String saveStatus = "success";

		if (isSave) {
			saveStatus = "status:" + saveStatus;
		} else {
			saveStatus = "status:error";
		}
		return gson.toJson(saveStatus).toString();
	}

	/**
	 * @param model
	 * @param resvId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "tools", method = RequestMethod.GET)
	public String tools(Model model, @RequestParam(value = "reservationNo") int resvId, HttpSession session) {
		String pageUrl = "reservation_test/reservation_tools";
		SysPermissions permissionObjRes = pageAccessPermissionService.getPermission(session, "PMS_RESV");
		model.addAttribute("reservationPagePerObj", permissionObjRes);
		SysPermissions permissionObjResChkIn = pageAccessPermissionService.getPermission(session, "PMS_RESV_CHKIN");
		model.addAttribute("resCheckInPagePerObj", permissionObjResChkIn);
		SysPermissions permissionObjdeposit = pageAccessPermissionService.getPermission(session, "PMS_DEPST");
		model.addAttribute("deopsitPagePerObj", permissionObjdeposit);
		if (permissionObjRes.isCanView() && permissionObjRes.isIs_view_applicable()) {
			model.addAttribute("resvNo", resvId);
		} else {
			pageUrl = "access_denied/access_denied";
		}
		return pageUrl;
	}

	@RequestMapping(value = "getReservGuestData")

	public @ResponseBody String getReservGuestData(@RequestParam(value = "resvNO") int resvNo) {
		JsonObject jobj = reservationService.getResvRecordDetails(resvNo);
		return jobj.toString();
	}

	@RequestMapping(value = "getPickUpData")
	public @ResponseBody String getPickUpData(@RequestParam(value = "resvNO") int resvNo) {
		JsonObject jobj = reservationService.getResvRecordDetails(resvNo);
		return jobj.toString();
	}

	/**
	 * @param session
	 * @param model
	 * @param resvHdr
	 * @param resvNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reservationNoShow")
	public String reservationNoShow(HttpSession session, Model model, @ModelAttribute ResvHdr resvHdr,
			@RequestParam(value = "reservationNo") int resvNo) throws Exception {
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_RESV");
		model.addAttribute("dateFormat", session.getAttribute("dateFormat").toString());
		SystemSettings systemSettings = systemSettingsService.getSystemSettings();
		ResvHdr resHdr = reservationService.getResvHdr(resvNo);
		model.addAttribute("resHdr", resHdr);
		model.addAttribute("reservationNo", resvNo);
		model.addAttribute("maxNight", systemSettings.getMaxNightsPerBooking());
		model.addAttribute("maxRooms", systemSettings.getMaxRoomsPerBooking());
		return ((permissionObj.isCanEdit() && permissionObj.isIs_edit_applicable()) ? "noshow/noshow"
				: SYDEF_PERMISION_DENIED_PAGE_URL);
	}

	@RequestMapping(value = "saveNoShow", method = RequestMethod.POST)
	public @ResponseBody String saveNoShow(Model model, HttpSession session, @RequestBody String noshowData,
			@ModelAttribute AccountMaster accm, @ModelAttribute ShiftManagement currntShiftId,
			@ModelAttribute TaxHdr taxHdr) throws Exception {
		int userId = ((User) session.getAttribute("userForm")).getId();
		String[] htlDte = session.getAttribute("hotelDate").toString().split(" ");
		String hotelDat = htlDte[0];
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = sdf1.parse(hotelDat);
		java.sql.Date hotlDate = new java.sql.Date(date.getTime());
		ObjectMapper mapper = new ObjectMapper();
		JsonParser parser = new JsonParser();
		JsonObject jobj = parser.parse(noshowData).getAsJsonObject();
		int checkStatus = jobj.get("checkStatus").getAsInt();
		model.addAttribute("checkStatus", checkStatus);
		int resvNo = jobj.get("resvNo").getAsInt();
		String depoAmount = jobj.get("deposit").getAsString();
		int depositAmount = Integer.parseInt(depoAmount.trim());
		String percentag = jobj.get("percentage").getAsString();
		int percentage = Integer.parseInt(percentag.trim());
		int penalty = (depositAmount * percentage) / 100;
		int pType = 2;
		ResvHdr resHdr = reservationService.getResvHdr(resvNo);
		model.addAttribute("resHdr", resHdr);
		Transaction txn = new Transaction();
		txn = transactionService.getCharges(hotlDate, penalty, pType, true, 0);
		txn.setAmount(penalty);
		txn.setNett_amount(txn.getNett_amount());
		txn.setAcc_mst_id(pType);
		accm = transactionService.accMstId(pType);
		txn.setAcc_mst_code(accm.getCode());
		txn.setTxn_source(TxnSource.FRONTOFFICEPOSTING.getCode());
		txn.setFolio_no(resHdr.getFolioNo());
		txn.setFolio_bind_no(resHdr.getFolioBindNO());
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String payTime = dateFormat.format(cal.getTime());
		txn.setTxn_date(hotelDat);
		txn.setTxn_time(payTime);
		taxHdr = transactionService.getTaxCode(accm.getTax_id());
		txn.setTax_id(accm.getTax_id());
		txn.setTax_code(taxHdr.getCode());
		int noShowCount = jobj.get("numberNoShow").getAsInt();
		model.addAttribute("noShowCount", noShowCount);
		if (percentage != 0 && depositAmount != 0) {
			String hotedate = (String) session.getAttribute("hotelDate");
			String[] htlDte1 = hotedate.split(" ");
			String hotelDate1 = htlDte1[0];
			currntShiftId = shiftManagementService.getShiftManagementDetails(hotelDate1);
			txn.setShift_id(currntShiftId.getShiftId());
			txn.setTxn_status(1);
			txn.setUser_id(userId);
		//	String saveTransaction = transactionService.save(txn);
		}
		Gson gson = new Gson();
		boolean isSave = false;
		try {
			List<ResvRoom> resvRoomList = mapper.readValue(jobj.get("room").toString(),
					TypeFactory.defaultInstance().constructCollectionType(List.class, ResvRoom.class));
			for (ResvRoom resvRoom : resvRoomList) {
				resvRoom.setNoshowDate(commonSettings.getHotelDate());
				resvRoom.setNoshowTime(new java.util.Date());
				resvRoom.setNoshowBy(((User) session.getAttribute("userForm")).getId());
				resvRoom.setRoomStatus(resvRoom.getRoomStatus());
			}
			isSave = reservationService.saveNoShow(resvNo, resvRoomList, noShowCount, checkStatus);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		String saveStatus = "success";

		if (isSave) {
			saveStatus = "status:" + saveStatus + "_" + resvNo;
		} else {
			saveStatus = "status:error";
		}
		return gson.toJson(saveStatus).toString();
	}

	@RequestMapping(value = "/resvCancellation")
	public String cancelResrvation(Model model, @RequestParam(value = "resrvId") int resvNo, HttpSession session,
			HttpServletResponse response) throws Exception {

		String pageUrl = "reservation_test/reservation_cancellation";
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_RESV");
		if (permissionObj.isCanDelete() && permissionObj.isIs_delete_applicable()) {
			java.util.Date date = commonSettings.getHotelDate();
			String dateFormat = (String) session.getAttribute("dateFormat");
			SimpleDateFormat dateAndTime = new SimpleDateFormat(" " + dateFormat + " hh:mm:ss ");
			String dateTime = dateAndTime.format(date);
			ResvHdr resHdr = reservationService.getResvHdr(resvNo);
			/* model.addAttribute("reasonTypes",ReasonTypes.values()); */
			model.addAttribute("dateTime", dateTime);
			model.addAttribute("resHdr", resHdr);
			session.setAttribute("resvStatus", resHdr.getStatus());

		} else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;

	}

	@RequestMapping(value = "/resvCancellationSave", method = RequestMethod.POST)
	public @ResponseBody void save(Model model, @ModelAttribute ShiftManagement currntShiftId,
			@RequestParam(value = "cancelJson") String cancelJson, @ModelAttribute TaxHdr taxHdr,
			@ModelAttribute AccountMaster accm, HttpSession session, @ModelAttribute ResvHdr resvHdr,
			HttpServletResponse response) throws Exception {
		boolean saveCancellationDetails = false;
	//	String exceptionMessage = "";
		try {
//			Gson gson = new Gson();
			int userId = ((User) session.getAttribute("userForm")).getId();
			JsonParser jsonParser = new JsonParser();
			ObjectMapper mapper = new ObjectMapper();
		//	JsonParser parser = new JsonParser();
			JsonObject jobj = jsonParser.parse(cancelJson).getAsJsonObject();
//			SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
			String hotelDate = (String) session.getAttribute("hotelDate");
	//		java.util.Date cancelDate = formatter.parse(hotelDate);
		//	java.sql.Date sqlCancelDate = new java.sql.Date(cancelDate.getTime());
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar calender = Calendar.getInstance();
			String cancelTime = dateFormat.format(calender.getTime());
			/* String reason_type=jobj.get("reasonTypes").getAsString(); */
			String reason = jobj.get("reason").getAsString();
			List<ResvRoom> resvRoomList = mapper.readValue(jobj.get("room").toString(),
					TypeFactory.defaultInstance().constructCollectionType(List.class, ResvRoom.class));
			for (ResvRoom resvRoom : resvRoomList) {
				resvRoom.getRoomStatus();
				resvRoom.getResvRoomNo();
				resvRoom.getCancelReason();
			}
			int noCancelCount = jobj.get("noNoCancel").getAsInt();
			model.addAttribute("noCancelCount", noCancelCount);
			int checkStatus = jobj.get("checkStatus").getAsInt();
			model.addAttribute("checkStatus", checkStatus);
			String reserversionNum = jobj.get("resvNo").getAsString();
			int resvNo = Integer.parseInt(reserversionNum.trim());
			String depoAmount = jobj.get("deposit").getAsString();
			int depositAmount = Integer.parseInt(depoAmount.trim());
			String percentag = jobj.get("percentage").getAsString();
			int percentage = Integer.parseInt(percentag.trim());
			int penalty = (depositAmount * percentage) / 100;
			int pType = 2;
			ResvHdr resHdr = reservationService.getResvHdr(resvNo);
			model.addAttribute("resHdr", resHdr);
			resvHdr.setCancelBy(userId);
			resvHdr.setResvNo(resvNo);
			resvHdr.setStatus(ReservationStatus.CANCELLED.getCode());
			resvHdr.setCancelDate(hotelDate);
			resvHdr.setCancelTime(dateFormat.parse(cancelTime));
			resvHdr.setCancelReason(reason);
			String[] htlDte = session.getAttribute("hotelDate").toString().split(" ");
			String hotelDat = htlDte[0];
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date = sdf1.parse(hotelDat);
			java.sql.Date hotlDate = new java.sql.Date(date.getTime());
			Transaction txn = new Transaction();
			txn = transactionService.getCharges(hotlDate, penalty, pType, true, 0);
			txn.setAmount(penalty);
			txn.setNett_amount(txn.getNett_amount());
			txn.setAcc_mst_id(pType);
			accm = transactionService.accMstId(pType);
			txn.setAcc_mst_code(accm.getCode());
			txn.setTxn_source(TxnSource.FRONTOFFICEPOSTING.getCode());
			txn.setFolio_no(resHdr.getFolioNo());
			txn.setFolio_bind_no(resHdr.getFolioBindNO());
			Calendar cal = Calendar.getInstance();
			String payTime = dateFormat.format(cal.getTime());
			txn.setTxn_date(hotelDat);
			txn.setTxn_time(payTime);
			taxHdr = transactionService.getTaxCode(accm.getTax_id());
			txn.setTax_id(accm.getTax_id());
			txn.setTax_code(taxHdr.getCode());
			if (percentage != 0 && depositAmount != 0) {
				String htlDate = (String) session.getAttribute("hotelDate");
				String[] htlDte1 = htlDate.split(" ");
				String hotelDate1 = htlDte1[0];
				currntShiftId = shiftManagementService.getShiftManagementDetails(hotelDate1);
				txn.setShift_id(currntShiftId.getShiftId());
				txn.setTxn_status(1);
				txn.setUser_id(userId);
			//	String saveTransaction = transactionService.save(txn);
			}
			saveCancellationDetails = reservationService.cancellationSave(resvHdr, resvRoomList, noCancelCount,
					checkStatus);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		String saveStatus = "success";

		if (saveCancellationDetails) {
			saveStatus = saveStatus + "_" + resvHdr.getResvNo();
		} else {
			saveStatus = "status:error";
		}
		response.getWriter().print(saveStatus);
	}

	@RequestMapping(value = "mailCancellation", method = RequestMethod.POST)
	public @ResponseBody String mailCancellation(Model model, HttpSession session, HttpServletRequest request,
			@RequestParam(value = "resvNum") int resvNo, @RequestParam(value = "cancelJson") String cancelJson)
			throws Exception {
		JsonParser jsonParser = new JsonParser();
		JsonObject jobj = jsonParser.parse(cancelJson).getAsJsonObject();
		int noCancelCount = jobj.get("noNoCancel").getAsInt();
		model.addAttribute("noCancelCount", noCancelCount);
		int nightCount = jobj.get("nightCount").getAsInt();
		model.addAttribute("nightCount", nightCount);
		String mailcancellation = reservationService.getMailCancellation(resvNo, noCancelCount, nightCount);
		return mailcancellation;
	}

	@RequestMapping(value = "getCountries", method = RequestMethod.POST)
	public @ResponseBody String getCountries(HttpSession session, HttpServletRequest request) throws Exception {

		JsonArray jArray = reservationService.getCountriesList();
		return jArray.toString();
	}

	@RequestMapping(value = "/getDetailViaPhonenumResv")
	public @ResponseBody String getDetailViaPhonenumResv(HttpSession session, @RequestBody String phonenum)
			throws Exception {
		JsonArray jArray = reservationService.detailViaPhonenumResv(phonenum);
		return jArray.toString();
	}

	@RequestMapping(value = "reservationStatus", method = RequestMethod.POST)
	public @ResponseBody String reservationStatus(Model model, HttpSession session, HttpServletRequest request)
			throws Exception {
		JsonArray jArray = reservationService.getResvStatus();
		return jArray.toString();

	}

	@RequestMapping(value = "/getCustomerData", method = RequestMethod.POST)
	public @ResponseBody String getCustomerDetails(HttpServletRequest request, @RequestBody String customerData)
			throws SQLException {

		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(customerData).getAsJsonObject();
		String salutation = jsonObject.get("salutation").getAsString();
		String customerFirst = jsonObject.get("firstName").getAsString();
		String customerLast = jsonObject.get("lastName").getAsString();
		JsonArray jsonArray = new JsonArray();
		Gson gsonCust = new Gson();
		jsonArray = reservationService.getCustomerData(salutation, customerFirst, customerLast);
		return gsonCust.toJson(jsonArray);

	}

	@RequestMapping(value = "/deletePickUp")
	public void deletePickUp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final int resvNo = Integer.parseInt(request.getParameter("resvNo"));

		try {
			reservationService.deletePickUp(resvNo);
		} catch (Exception e) {
			logger.error("Method : deletePickUp()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}

	}

	@RequestMapping(value = "/printGrc")
	public ModelAndView printGrc(@RequestParam(value = "resvNo") int resvNo, HttpSession session) throws Exception {
		JsonObject companyData = new JsonObject();
		JsonObject grcData = new JsonObject();
		JsonArray rsvnData = reservationService.getGrcData(resvNo);
		Company company = new Company();
		company = systemSettingService.getcompany();
		companyData.addProperty("companyName", company.getCompanyName());
		companyData.addProperty("companyN", company.getLogoFolder());
		companyData.addProperty("streetName", company.getStreetName());
		companyData.addProperty("cityName", company.getCityName());
		companyData.addProperty("image", (String) session.getAttribute("rootPath"));
		companyData.addProperty("email", company.getEmail());
		companyData.addProperty("url", company.getUrl());
		companyData.addProperty("phone", company.getPhone());
		grcData.add("resvnJson", rsvnData);
		grcData.add("companyData", companyData);

		return new ModelAndView("pdfGrcReport", "grcData", grcData);
	}
	@RequestMapping(value="/printBookingVoucher")
	public ModelAndView printBookingVoucher(@RequestParam(value="resvNo") int resvNo,HttpSession session) throws Exception{
		JsonObject companyData = new JsonObject();
		JsonObject grcData = new JsonObject();
		JsonArray rsvnData =  reservationService.getBookingData(resvNo);
		Company company = new Company();
		company = systemSettingService.getcompany();
		SystemSettings systemSettings = systemSettingsService.getSystemSettings();
		companyData.addProperty("companyName", company.getCompanyName());
		companyData.addProperty("companyN", company.getLogoFolder());
		companyData.addProperty("streetName", company.getStreetName());
		companyData.addProperty("cityName", company.getCityName());
		companyData.addProperty("image", (String) session.getAttribute("rootPath"));
		companyData.addProperty("email", company.getEmail());
		companyData.addProperty("url", company.getUrl());
		companyData.addProperty("phone", company.getPhone());
		companyData.addProperty("resv_confirm_head", company.getResvConfirmHead());
		companyData.addProperty("resv_confirm_text1", company.getResvConfirmText1());
		companyData.addProperty("resv_confirm_text2", company.getResvConfirmText2());
		companyData.addProperty("resv_confirm_text3", company.getResvConfirmText3());
		companyData.addProperty("resv_confirm_text4", company.getResvConfirmText4());
		companyData.addProperty("resv_confirm_text5", company.getResvConfirmText5());
		companyData.addProperty("dateform", systemSettings.getDateFormat());
		companyData.addProperty("timeform", systemSettings.getTimeFormat());
		
		grcData.add("resvnJson", rsvnData);
		grcData.add("companyData", companyData);
		
		return new ModelAndView("pdfBookingVoucherReport", "grcData", grcData);
	}
	

}
