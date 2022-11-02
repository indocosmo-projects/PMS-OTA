package com.indocosmo.pms.web.checkIn.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.enumerator.ReservationStatus;
import com.indocosmo.pms.enumerator.common.FacilityTypes;
import com.indocosmo.pms.web.checkIn.model.CheckInDtl;
import com.indocosmo.pms.web.checkIn.model.CheckInHdr;
import com.indocosmo.pms.web.checkIn.model.CheckInRate;
import com.indocosmo.pms.web.checkIn.model.CheckInRequest;
import com.indocosmo.pms.web.checkIn.model.CheckinType;
import com.indocosmo.pms.web.checkIn.model.PaymentType;
import com.indocosmo.pms.web.checkIn.service.CheckInService;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.reservation_test.model.ResvDtl;
import com.indocosmo.pms.web.reservation_test.model.ResvHdr;
import com.indocosmo.pms.web.reservation_test.model.ResvRate;
import com.indocosmo.pms.web.reservation_test.model.ResvRoom;
import com.indocosmo.pms.web.reservation_test.service.ReservationServiceTest;
import com.indocosmo.pms.web.shiftManagement.service.ShiftManagementService;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;

@Controller
@RequestMapping(value = "/checkIn")
public class CheckInController {

	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "access_denied/access_denied";

	@Autowired
	ReservationServiceTest reservationService;

	@Autowired
	CheckInService checkInService;

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	@Autowired
	private ShiftManagementService shiftManagementService;

	private SysPermissions permissionObj;

	public static final Logger logger = LoggerFactory.getLogger(CheckInController.class);

	/**
	 * @param resvId
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "checkInEdit", method = RequestMethod.GET)
	public String editReDirect(Model model, HttpSession session, @RequestParam("resvId") int resvId,
			@RequestParam("currentLocation") String currentLocation) throws Exception {
		String pageUrl = "check_in/check_in_edit";
		if (currentLocation.equals("reservation")) {
			session.setAttribute("backUrl", "../reservation_test/tools?reservationNo=" + resvId);
		} else {
			session.setAttribute("backUrl", "../nightAudit/audit");
		}
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_RESV_CHKIN");
		model.addAttribute("curPagePerObj", permissionObj);
		if (permissionObj.isCanView() && permissionObj.isIs_view_applicable()) {
			HashMap<String, Object> resultData = null;
			ResvHdr resvHdr = reservationService.getResvHdr(resvId);
			model.addAttribute("checkInSessionResvHdr", resvHdr);
			model.addAttribute("facilityTypes", FacilityTypes.values());
			String htlDate = (String) session.getAttribute("hotelDate");
			String[] htlDte = htlDate.split(" ");
			String hotelDate = htlDte[0];
			String count = shiftManagementService.isshiftOPen(hotelDate);
			model.addAttribute("count", count);
			session.setAttribute("count1", count);
		} else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}

	/**
	 * @param resvId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getCheckInData", method = RequestMethod.POST)
	public @ResponseBody String getCheckInData(@RequestParam("resvId") int resvId) throws Exception {
		JsonArray jArray = checkInService.getCheckInData(resvId);
		return jArray.toString();
	}

	/**
	 * @param resvRoomNo
	 * @param minArrDate
	 * @param maxDepartDate
	 * @param roomTypeId
	 * @param occupancy
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getCheckInDetails", method = RequestMethod.POST)
	public @ResponseBody String getCheckInDetails(@RequestParam("resvRoomNo") int resvRoomNo,
			@RequestParam("minArrDate") String minArrDate, @RequestParam("maxDepartDate") String maxDepartDate,
			@RequestParam("roomTypeId") int roomTypeId, @RequestParam("occupancy") int occupancy) throws Exception {
		JsonObject jobj = checkInService.getCheckInDetails(resvRoomNo, minArrDate, maxDepartDate, roomTypeId,
				occupancy);
		return jobj.toString();
	}
	
	
	
	@RequestMapping(value = "/changeRoomStatus")
	public void changeRoomStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final int id = Integer.parseInt(request.getParameter("id"));

		try {
			checkInService.changeRoomStatus(id);
		} catch (Exception e) {
			logger.error("Method : changeRoomStatus()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}

	}
	
	
	
	
	@RequestMapping(value = "/updateGroupCheckin", method = RequestMethod.POST)
	public @ResponseBody String updateGroupCheckin(MultipartHttpServletRequest request,
	@RequestParam(value = "roomNumber", required = true) String roomNumber,
	@RequestParam(value = "reservRoomId", required = true) String reservRoomId, HttpSession session)
			throws Exception {
		ResvRoom resvRoom = null;
		String returnValue= "success";
		JsonParser parser = new JsonParser();
		JsonArray reservRoomIdobj = parser.parse(reservRoomId).getAsJsonArray();
		JsonArray roomNumberjobj = parser.parse(roomNumber).getAsJsonArray();
		
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
		try {
//			System.out.println(roomNumberAr);
//			System.out.println(reservRoomIdAr);
			int roomId = 0 ;
			String roomnumber = "";
			for(int i = 0;i<reservRoomIdobj.size();i++) {
				 roomId = reservRoomIdobj.get(i).getAsInt();
				 roomnumber = roomNumberjobj.get(i).getAsString();
				 checkInService.updateGroupCheckin(roomId,roomnumber);
			}
			
		
	} catch (Exception e) {
		returnValue = "error";
		e.printStackTrace();
		logger.error("Method : updateCheckInRoomDtls " + Throwables.getStackTraceAsString(e));
		throw new CustomException();
	}
	return gson.toJson("status:" + returnValue);
}
   

	/**
	 * @param request
	 * @param idproof
	 * @param customerimg
	 * @param editData
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/updateCheckInRoom", method = RequestMethod.POST)
	public @ResponseBody String updateCheckInRoomDtls(MultipartHttpServletRequest request,
			@RequestParam(value = "idproof", required = false) MultipartFile idproof,
			@RequestParam(value = "customerimg", required = false) MultipartFile customerimg,
			@RequestParam(value = "editData", required = true) String editData, HttpSession session) {
		ResvRoom resvRoom = null;
		String returnValue = "success";
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		String rootPath = System.getProperty("catalina.home");
		String imageAbsolutePath = "/pms_uploads/customer_images/";
		String proofAbsolutePath = "/pms_uploads/customer_id/";
		String imageRootDirectory = rootPath + "/webapps/" + imageAbsolutePath;
		String proofRootDirectory = rootPath + "/webapps/" + proofAbsolutePath;
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			JsonParser parser = new JsonParser();
			mapper.setDateFormat(simpleDateformat);
			JsonObject jobj = parser.parse(editData).getAsJsonObject();
			resvRoom = mapper.readValue(jobj.get("resvRoom").toString(), ResvRoom.class);

			for (CheckInRequest reqObj : resvRoom.getCheckinRequest()) {
				if (reqObj.getId() == 0) {
					reqObj.setResvRoomNo(resvRoom.getResvRoomNo());
					reqObj.setReqCompleted(false);
					reqObj.setIsDeleted(false);
					reqObj.setReqTakenBy(((User) session.getAttribute("userForm")).getId());
					reqObj.setUserId(reqObj.getReqTakenBy());
					reqObj.setReqTakenDate(commonSettings.hotelDate);
					reqObj.setCanceled(false);
				}
			}

			if (idproof != null) {
				File dir = new File(proofRootDirectory);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				File serverFile = new File(dir.getAbsolutePath() + File.separator + resvRoom.getResvRoomNo()
						+ idproof.getOriginalFilename());
				idproof.transferTo(serverFile);
				resvRoom.setCustomerIdProof(
						proofAbsolutePath + resvRoom.getResvRoomNo() + idproof.getOriginalFilename());
			}
			if (customerimg != null) {
				File dir = new File(imageRootDirectory);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				File serverFile = new File(dir.getAbsolutePath() + File.separator + resvRoom.getResvRoomNo() + "_"
						+ customerimg.getOriginalFilename());
				customerimg.transferTo(serverFile);
				resvRoom.setCustomerImage(
						imageAbsolutePath + resvRoom.getResvRoomNo() + "_" + customerimg.getOriginalFilename());
			}
			returnValue = reservationService.updateResvRoomAndRequests(resvRoom);
		} catch (Exception e) {
			returnValue = "error";
			e.printStackTrace();
			logger.error("Method : updateCheckInRoomDtls " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return gson.toJson("status:" + returnValue);
	}

	/**
	 * @param checkInDtlsJson
	 * @param resvId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/saveRecord", method = RequestMethod.POST)
	public @ResponseBody String save(@RequestParam(value = "checkInDtls", required = true) String checkInDtlsJson,
			@RequestParam(value = "resvId", required = true) int resvId, HttpSession session) {
		String result = "error";
		int roomType = 0;
		byte occupancy = 0;
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy-MM-dd");
		JsonParser parser = new JsonParser();
		JsonObject jobj = parser.parse(checkInDtlsJson).getAsJsonObject();
		JsonArray selected = jobj.get("selected").getAsJsonArray();
		HashMap<String, Object> resultData = null;
		ResvHdr resvHdr;
		List<ResvDtl> resvDtlList;
		List<ResvRoom> resvRoomList;
		List<ResvRate> resvRateList;
		CheckInHdr checkInHdr = new CheckInHdr();
		CheckInDtl checkInDtl = null;
		CheckInRate checkInRate = null;
		List<CheckInRate> checkInRateList = null;
		try {
			resultData = reservationService.getResvRecord(resvId);
			resvHdr = (ResvHdr) resultData.get("resvHdr");
			resvDtlList = (List<ResvDtl>) resultData.get("resvDtlList");
			resvRoomList = (List<ResvRoom>) resultData.get("resvRoomList");
			resvRateList = (List<ResvRate>) resultData.get("resvRateList");
			int totalCheckIns = checkInService.getTotalCheckInRooms(resvId);
			checkInHdr.setResvNo(resvHdr.getResvNo());
			checkInHdr.setFolioBindNo(resvHdr.getFolioBindNO());
			checkInHdr.setFolioNo(resvHdr.getFolioNo());
			checkInHdr.setStatus((byte) ReservationStatus.CHECKIN.getCode());
			checkInHdr.setFullySettled(false);
			checkInHdr.setResvStatus(resvHdr.getStatus());
			checkInHdr.setResvType(resvHdr.getResvType());
			checkInHdr.setCorporateId(resvHdr.getCorporateId());
			checkInHdr.setCorporateName(resvHdr.getCorporateName());
			checkInHdr.setCorporateAddress(resvHdr.getCorporateAddress());
			checkInHdr.setBillingInstruction(resvHdr.getBillingInstruction());
			checkInHdr.setSpecialRequests(resvHdr.getSpecialRequests());
			checkInHdr.setCheckInBy(((User) session.getAttribute("userForm")).getId());
			checkInHdr.setArrTime((Date) session.getAttribute("checkOutTime"));

			checkInHdr.setExpDepartTime(new java.util.Date());
			checkInHdr.setNoOfRoomsToCheckIn(resvHdr.getNumRooms() - totalCheckIns);

			for (int i = 0; i < selected.size(); i++) {
				for (ResvRoom resvRoom : resvRoomList) {
					if (selected.get(i).getAsInt() == resvRoom.getResvRoomNo()) {
						for (ResvDtl resvdtl : resvDtlList) {
							checkInDtl = new CheckInDtl();
							if (resvRoom.getResvDtlNo() == resvdtl.getResvDtlNo()) {
								checkInHdr.setRoomTypeId(resvdtl.getRoomTypeId());
								checkInHdr.setRoomTypeCode(resvdtl.getRoomTypeCode());
								checkInHdr.setRoomNumber(String.valueOf(resvRoom.getRoomNumber()));
								checkInHdr.setArrDate(
										simpleDateformat.parse(session.getAttribute("hotelDate").toString()));
								checkInHdr.setExpDepartDate(resvdtl.getDepartDate());
								checkInHdr.setRateType(resvdtl.getRateType());
								checkInHdr.setRateId(resvdtl.getRateId());
								checkInHdr.setRateCode(resvdtl.getRateCode());
								checkInHdr.setRateDescription(resvdtl.getRateDescription());
								checkInHdr.setOccupancy(resvdtl.getOccupancy());
								checkInHdr.setDiscType(resvHdr.getDiscType());
								checkInHdr.setDiscId(resvdtl.getDiscId());
								checkInHdr.setDiscCode(resvdtl.getDiscCode());
								checkInHdr.setDiscIsPc(resvdtl.isDiscIsPc());
								checkInHdr.setDiscAmount(BigDecimal.valueOf(resvdtl.getDiscAmount()));
								checkInHdr.setDiscPc(BigDecimal.valueOf(resvdtl.getDiscPc()));
								checkInHdr.setNumAdults((byte) resvRoom.getNumAdults());
								checkInHdr.setNumChildren((byte) resvRoom.getNumChildren());
								checkInHdr.setNumChildren((byte) resvRoom.getNumInfants());
								checkInHdr.setNumNights(resvdtl.getNumNights());

								if(resvRoom.getSalutation()== null || resvRoom.getSalutation()== "") {
									checkInDtl.setSalutation(resvHdr.getSelectedSalutation());
								}
								else {
									checkInDtl.setSalutation(resvRoom.getSalutation());
								}
								
								
								if(resvRoom.getState()== null || resvRoom.getState()== "") {
									checkInDtl.setState("Kerala");
								}
								else {
									checkInDtl.setState(resvRoom.getState());
								}
								

								checkInDtl.setResvRoomNo(selected.get(i).getAsInt());
								checkInDtl.setRoomNumber(String.valueOf(resvRoom.getRoomNumber()));
								if(resvRoom.getFirstName()== null ||resvRoom.getFirstName()== "") {
									checkInDtl.setFirstName(resvHdr.getResvByFirstName());
								}
								else {
									checkInDtl.setFirstName(resvRoom.getFirstName());
									
								}
								if(resvRoom.getLastName()== null || resvRoom.getLastName()== "") {
									checkInDtl.setLastName(resvHdr.getResvByLastName());
								}
								else {
									checkInDtl.setLastName(resvRoom.getLastName());
									
								}
								
								
								
								if(resvRoom.getGender()== null || resvRoom.getGender()== "") {
									checkInDtl.setGender("Male");
								}
								else {
									checkInDtl.setGender(resvRoom.getGender());
								}
								checkInDtl.setGuestName(resvRoom.getGuestName());
								checkInDtl.setAddress(resvRoom.getAddress());
								checkInDtl.setEmail(resvRoom.getEmail());
								checkInDtl.setPhone(resvRoom.getPhone());
								if(resvRoom.getNationality() == null || resvRoom.getNationality() == "") {
									checkInDtl.setNationality("India");
								}
								else {
									checkInDtl.setNationality(resvRoom.getNationality());
								}
								
								checkInDtl.setPassportNo(resvRoom.getPassportNo());
								checkInDtl.setPassportExpiryOn(resvRoom.getPassportExpiryOn());
								checkInDtl.setGstno(resvRoom.getGstno());
								checkInDtl.setRemarks(resvRoom.getRemarks());
								checkInDtl.setGuestId(resvHdr.getGuestId());
								checkInDtl.setCustomerIdProof(resvRoom.getCustomerIdProof());
								checkInDtl.setCustomerImage(resvRoom.getCustomerImage());
								checkInDtl.setIsSharer(false);
								occupancy = checkInDtl.getOccupancy();
								roomType = checkInDtl.getRoomTypeId();
								break;
							}
						}
					}
				}
				checkInRateList = new ArrayList<CheckInRate>();
				for (ResvRate resvRate : resvRateList) {
					if (selected.get(i).getAsInt() == resvRate.getResvRoomNo()) {
						checkInRate = new CheckInRate();
						checkInRate.setNight(resvRate.getNight());
						checkInRate.setRoomCharge(resvRate.getRoomCharge());
						checkInRate.setIncludeTax(resvRate.isIncludeTax());
						checkInRate.setTax1Pc(resvRate.getTax1Pc());
						checkInRate.setTax2Pc(resvRate.getTax2Pc());
						checkInRate.setTax3Pc(resvRate.getTax3Pc());
						checkInRate.setTax4Pc(resvRate.getTax4Pc());
						checkInRate.setServiceChargePc(resvRate.getServiceChargePc());
						checkInRate.setTax1(resvRate.getTax1());
						checkInRate.setTax2(resvRate.getTax2());
						checkInRate.setTax3(resvRate.getTax3());
						checkInRate.setTax4(resvRate.getTax4());
						checkInRate.setServiceCharge(resvRate.getServiceCharge());
						checkInRate.setRoomType(roomType);
						checkInRate.setOccupancy(occupancy);
						checkInRateList.add(checkInRate);
					}
				}
				result = checkInService.save(checkInHdr, checkInDtl, checkInRateList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : save " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return result;
	}

	/**
	 * @param resvRoomNo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getReservationRateDetails", method = RequestMethod.POST)
	public @ResponseBody String getReservationRateDetails(
			@RequestParam(value = "resvRoomNo", required = true) int resvRoomNo, HttpSession session) throws Exception {
		JsonObject jobj = checkInService.getReservationRateDetails(resvRoomNo);

		return jobj.toString();
	}

	@RequestMapping(value = "getCheckinTypes", method = RequestMethod.POST)
	public @ResponseBody String getCheckinTypes(HttpSession session, HttpServletRequest request) throws Exception {
		Gson gson = new Gson();
		List<CheckinType> checkinList = checkInService.getCheckinTypes();
		return gson.toJson(checkinList);
	}

	@RequestMapping(value = "getPaymentTypes", method = RequestMethod.POST)
	public @ResponseBody String getPaymentTypes(HttpSession session, HttpServletRequest request) {
		Gson gson = new Gson();
		List<PaymentType> paymentTypes = checkInService.getPaymentTypes();
		return gson.toJson(paymentTypes);

	}

	@RequestMapping(value = "getCustomers", method = RequestMethod.POST)
	public @ResponseBody String getCustomers(HttpSession session, HttpServletRequest request) throws Exception {

		JsonArray jArray = checkInService.getCustomers();
		return jArray.toString();
	}

	@RequestMapping(value = "loadData", method = RequestMethod.POST)
	public @ResponseBody String loadData(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "data") String customer) throws Exception {

		JsonArray jArray = checkInService.loadData(customer);
		return jArray.toString();
	}

	@RequestMapping(value = "loadDataByMail", method = RequestMethod.POST)
	public @ResponseBody String loadDataByMail(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "data") String mail) throws Exception {

		JsonObject jobj = checkInService.loadDataByMail(mail);
		return jobj.toString();
	}

}