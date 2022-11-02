package com.indocosmo.pms.web.reservation.controller;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
import com.indocosmo.pms.enumerator.ReservationStatus;
import com.indocosmo.pms.enumerator.RoomOccupancyStatus;
import com.indocosmo.pms.web.accountMaster.service.AccountmasterService;
import com.indocosmo.pms.web.checkIn.service.CheckInService;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.corporate.model.Corporate;
import com.indocosmo.pms.web.corporate.service.CorporateService;
import com.indocosmo.pms.web.discount.service.DiscountService;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.reservation.model.Cancelreason;
import com.indocosmo.pms.web.reservation.model.ResvDtl;
import com.indocosmo.pms.web.reservation.model.ResvHdr;
import com.indocosmo.pms.web.reservation.model.ResvRoom;
import com.indocosmo.pms.web.reservation.model.RoomAvailability;
import com.indocosmo.pms.web.reservation.service.ReservationService;
import com.indocosmo.pms.web.room.model.Room;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;

@Controller
@RequestMapping(value = "/reservation")
public class ReservationController {

	public static final String RESERVATION_LIST_PAGE_URL = "reservation/reservation_list";
	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "access_denied/access_denied";

	public static final Logger logger = LoggerFactory.getLogger(ReservationController.class);
	@Autowired
	SystemSettingsService systemSettingsService;

	@Autowired
	ReservationService reservationService;

	@Autowired
	CorporateService corporateService;

	@Autowired
	DiscountService discountService;

	private List<ResvRoom> sessionRoomList;

	@Autowired
	AccountmasterService accountMasterService;

	@Autowired
	CheckInService checkInService;

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	private SysPermissions permissionObj;

	/**
	 * 
	 * @param ids
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/CorporateTaInfo", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Corporate> corporateTaInfo(@RequestParam(value = "id") int ids,
			@RequestParam(value = "name") String name) throws Exception {

		List<Corporate> corporateTa = corporateService.listOfCorporate(ids, name);

		return corporateTa;
	}

	/**
	 * 
	 * @param roomAvailability
	 * @param resHdr
	 * @param model
	 * @param resrvId
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/resvCancellation", method = RequestMethod.GET)
	public String cancelResrvation(@ModelAttribute RoomAvailability roomAvailability, @ModelAttribute ResvHdr resHdr,
			Model model, @RequestParam(value = "resrvId") int resrvId, HttpSession session) throws Exception {
		String pageUrl = "reservation/reservation_cancellation_edit";
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_RESV");
		if (permissionObj.isCanDelete() && permissionObj.isIs_delete_applicable()) {

			Date date = new Date();
			String dateFormat = (String) session.getAttribute("dateFormat");
			SimpleDateFormat dateAndTime = new SimpleDateFormat(" " + dateFormat + " hh:mm:ss ");
			String dateTime = dateAndTime.format(date);
			roomAvailability = reservationService.getRoomDetails(resrvId);
			resHdr = reservationService.getPersonalDetails(resrvId);
			model.addAttribute("dateTime", dateTime);
			model.addAttribute("roomAvailability", roomAvailability);
			model.addAttribute("resHdr", resHdr);
			session.setAttribute("resvStatus", resHdr.getStatus());
		} else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;

	}

	/**
	 * 
	 * @param cancellationDetails
	 * @param session
	 * @param creason
	 * @param resvHdr
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reason", method = RequestMethod.POST)
	public @ResponseBody String dateVerification(
			@RequestParam(value = "cancellationDetails") String cancellationDetails, HttpSession session,
			@ModelAttribute Cancelreason creason, @ModelAttribute ResvHdr resvHdr, @ModelAttribute User user)
			throws Exception {
		try {
		//	SimpleDateFormat sd = new SimpleDateFormat(" dd-MM-yyyy   hh:mm:ss ");

			int userId = ((User) session.getAttribute("userForm")).getId();

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(cancellationDetails);

			String reasonType = (String) jsonObject.get("reason");
			// System.out.println("reason type is" + reasonType);
			String reason = (String) jsonObject.get("explnation");
			// System.out.println("reason is" + reason);
			String resvNum = (String) jsonObject.get("reserId");
			// System.out.println("resveration number is" + resvNum);
			// System.out.println("before formatter");
			SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");

			String hotelDate = (String) session.getAttribute("hotelDate");
			// System.out.println("hotel date is" + hotelDate);
			Date cancelDate = formatter.parse(hotelDate);
			java.sql.Date sqlCancelDate = new java.sql.Date(cancelDate.getTime());
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String cancelTime = dateFormat.format(cal.getTime());
			int resvNo = Integer.parseInt(resvNum.trim());

			resvHdr.setResvNo(resvNo);
			resvHdr.setCancelledBy(userId);
			resvHdr.setStatus(ReservationStatus.CANCELLED);
			resvHdr.setCancel_date(sqlCancelDate);
			resvHdr.setCancelTime(cancelTime);
			resvHdr.setCancelledReason(reason);

			creason.setReason_type(reasonType);
			creason.setReason(reason);
	//		boolean saveCancellationDetails = reservationService.cancellationSave(resvHdr);
	//		boolean saveReason = reservationService.reasonSave(creason);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : Cancelreason " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return null;
	}

	/**
	 * 
	 * @param reHdr
	 * @param roomAvailability
	 * @param resHdr
	 * @param resvDtl
	 * @param model
	 * @param resrvId
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/resvConfirm", method = RequestMethod.GET)
	public String confirmResrvation(@ModelAttribute ResvHdr reHdr, @ModelAttribute RoomAvailability roomAvailability,
			@ModelAttribute ResvHdr resHdr, @ModelAttribute ResvDtl resvDtl, Model model,
			@RequestParam(value = "reservationId") int resrvId, HttpSession session) throws Exception {
		String pageUrl = "reservation/reservation_confirm_edit";
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_RESV");
		if (permissionObj.isCanEdit() && permissionObj.isIs_edit_applicable()) {

			List<ResvDtl> resvDtlList = null;
			try {
				Date date = new Date();
				String dateFormat = (String) session.getAttribute("dateFormat");
				SimpleDateFormat dateAndTime = new SimpleDateFormat(" " + dateFormat + " hh:mm:ss ");
				String dateTime = dateAndTime.format(date);
				double totalDeposit = reservationService.getDepositAmount(resrvId);
				resvDtlList = reservationService.getTotalPayable(resrvId);
				double netDeposit = 0;
				for (ResvDtl resvDtl1 : resvDtlList) {

					netDeposit += reservationService.getNetAmount(resvDtl1);

				}

				DecimalFormat df = new DecimalFormat("#.##");

				df.format(netDeposit);

				roomAvailability = reservationService.getRoomDetails(resrvId);
				resHdr = reservationService.getPersonalDetails(resrvId);
	//			int foliobindNo = resHdr.getFolioBindNo();
				session.setAttribute("roomInfo", resHdr);
				model.addAttribute("totalPayable", netDeposit);
				model.addAttribute("resvDtl", resvDtl);
				model.addAttribute("totalDeposit", totalDeposit);
				model.addAttribute("dateTime", dateTime);
				model.addAttribute("roomAvailability", roomAvailability);
				model.addAttribute("resHdr", resHdr);
				session.setAttribute("backUrl", "../reservation_test/tools?reservationNo="+resrvId);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Method : confirmResrvation " + Throwables.getStackTraceAsString(e));
				throw new CustomException();
			}
		} else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;

	}

	/**
	 * @param session
	 * @param request
	 * @param reservationNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "mailReservationConfirm", method = RequestMethod.POST)
	public @ResponseBody String mailReservationConfirm(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "reservationNo") int resvNo) throws Exception {
		return reservationService.mailConfirmationSave(resvNo);
	}

	/**
	 * 
	 * @param confirmationDetails
	 * @param session
	 * @param resvHdr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmSubmit", method = RequestMethod.POST)
	public @ResponseBody String confirmSubmit(@RequestParam(value = "confirmationDetails") String confirmationDetails,
			HttpSession session, @ModelAttribute ResvHdr resvHdr) throws Exception {

		int userId = ((User) session.getAttribute("userForm")).getId();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
		String hotelDate = (String) session.getAttribute("hotelDate");
		Date confirmDate = formatter.parse(hotelDate);
		java.sql.Date sqlConfirmDate = new java.sql.Date(confirmDate.getTime());
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String confTime = dateFormat.format(cal.getTime());
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(confirmationDetails);
		String conRefNo = (String) jsonObject.get("confirmReference");
		String resvNum = (String) jsonObject.get("reserId");
		int resvNo = Integer.parseInt(resvNum.trim());
		resvHdr.setResvNo(resvNo);
		resvHdr.setConfBy(userId);
		resvHdr.setStatus(ReservationStatus.CONFIRMED);
		resvHdr.setConfRefNo(conRefNo);
		resvHdr.setConfDate(sqlConfirmDate);
		resvHdr.setConfTime(confTime);
		reservationService.confirmation(resvHdr);

		return "success" + "_" + resvHdr.getResvNo();
	}

	/**
	 * 
	 * @param resvId
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "showDeposits", method = RequestMethod.GET)
	public String showDeposits(@RequestParam("resvId") int resvId, HttpSession session, Model model) {

		return "reservation/deposit_list";
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getRoomSearchModal", method = RequestMethod.POST)
	public String getRoomSearchModal() throws Exception {
		return "room_search/room_search";
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getCorpSearchModal", method = RequestMethod.POST)
	public String getCorpSearchModal() throws Exception {
		return "corporate_ta_search/corporate_ta_search";
	}

	/**
	 * Fetch the available rooms
	 * 
	 * @param arrivalDate
	 *            in millisecond string
	 * @param departDate
	 *            in millisecond string
	 * @param roomType
	 * @param occupancy
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAvailableRooms", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Room> getAvailableRooms(@RequestParam(value = "arrivalDate") String arrivalDate,
			@RequestParam(value = "departDate") String departDate, @RequestParam(value = "roomType") String roomType,
			@RequestParam(value = "occupancy") String occupancy) throws Exception {
		int rmType = 0, occ = 0;
		if (roomType != null && !roomType.equals("")) {
			rmType = Integer.parseInt(roomType);
		}
		if (occupancy != null && !occupancy.equals("")) {
			occ = Integer.parseInt(occupancy);
		}
		java.sql.Date arrDate = new java.sql.Date(Long.parseLong(arrivalDate));
		java.sql.Date depDate = new java.sql.Date(Long.parseLong(departDate));

		return reservationService.getAvailableRooms(arrDate, depDate, rmType, occ);
	}

	//// noshow

	@RequestMapping(value = "/noshow", method = RequestMethod.GET)
	public String noshow(Model model, @RequestParam(value = "reservationNo") int resvId, HttpSession session)
			throws Exception {
		String pageUrl = "noshow/noshow_list";

		SysPermissions permissionObjRes = pageAccessPermissionService.getPermission(session, "PMS_RESV");

		if (permissionObjRes.isCanEdit() && permissionObjRes.isIs_edit_applicable()) {
			HashMap<String, Object> resultData = null;
			List<ResvDtl> resvDtlList;
			ResvHdr resvHdr;
			List<ResvDtl> resvDtlList1 = null;
			List<ResvRoom> resvRoomList;
			List<Integer> distRoomType = new ArrayList<Integer>();
			double netDeposit = 0, totalDeposit = 0;
			int foliobindNo = 0;
			resultData = reservationService.getResvRecord(resvId);
			resvHdr = (ResvHdr) resultData.get("resvHdr");
			resvDtlList = (List<ResvDtl>) resultData.get("resvDtlList");
			resvRoomList = (List<ResvRoom>) resultData.get("resvRoomList");

			for (ResvDtl resvRoomType : resvDtlList) {
				if (!distRoomType.contains(resvRoomType.getRoomTypeId())) {
					distRoomType.add(resvRoomType.getRoomTypeId());
				}
			}
			resvDtlList1 = reservationService.getTotalPayable(resvId);
			for (ResvDtl resvDtl1 : resvDtlList1) {
				netDeposit += reservationService.getNetAmount(resvDtl1);
			}
			foliobindNo = resvHdr.getFolioBindNo();
			totalDeposit = reservationService.getDepositAmount(foliobindNo);
			DecimalFormat df = new DecimalFormat("#.##");
			df.format(netDeposit);

			model.addAttribute("totalDeposit", totalDeposit);
			model.addAttribute("totalPayable", df.format(netDeposit));
			model.addAttribute("DateFormat", session.getAttribute("dateFormat").toString());
			model.addAttribute("resvEditHdr", resvHdr);
			model.addAttribute("resvEditDtl", resvDtlList);
			model.addAttribute("resvEditRoom", resvRoomList);
			model.addAttribute("resvEditRoomTypeList", distRoomType);
			model.addAttribute("resvId", resvId);
		} else {
			pageUrl = "access_denied/access_denied";
		}
		return pageUrl;
	}

	@RequestMapping(value = "UpdateNoshow", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String UpdateNoshow(@RequestParam(value = "resv_no") int resv_no,
			@RequestParam(value = "slctdroom") String slectroom, @RequestParam(value = "remarks") String remarks,
			@RequestParam(value = "status") int selectstatus, HttpSession session) throws Exception {

		ResvHdr resvHdr = new ResvHdr();
		String[] slctdtlno = slectroom.split(",");
		String result = "fail";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cancel_reason = remarks;
		Calendar cal = Calendar.getInstance();
		String cancel_date = dateFormat.format(cal.getTime());
		int cancel_by = ((User) session.getAttribute("userForm")).getId();
		Date dt = new Date();
		resvHdr.setCancel_date(dt);
		resvHdr.setCancelTime(cancel_date);
		resvHdr.setCancelledReason(cancel_reason);
		resvHdr.setCancelledBy(cancel_by);
		result = reservationService.UpdateNoshow(resv_no, slctdtlno, selectstatus, resvHdr);

		return result;
	}

	@RequestMapping(value = "updateCutOffDate")
	public @ResponseBody int updateCutOffDate(@RequestParam(value = "resvNO") int resvNo,
			@RequestParam(value = "cutOffDate") String cutOffDateEpoch) {

		ResvHdr resvHdr = new ResvHdr();
		java.sql.Date cutOffDate = new java.sql.Date(Long.parseLong(cutOffDateEpoch));
		resvHdr.setResvNo(resvNo);
		resvHdr.setCutOffDate(cutOffDate);
		int status = reservationService.updateCutOffDate(resvHdr);

		return status;
	}

	@RequestMapping(value = "updatePickUpDetails")
	public @ResponseBody int updatePickUpDetails(@RequestParam(value = "resvNO") int resvNo,
			@RequestParam(value = "pickupNeeded") boolean pickupNeeded,
			@RequestParam(value = "pickupDate") String pickupDateEpoch,
			@RequestParam(value = "pickupLocation") String pickupLocation,
			@RequestParam(value = "pickupSeats") String pickupSeats,
			@RequestParam(value = "pickupRemarks") String pickupRemarks,
			@RequestParam(value = "pickupTime") String pickupTime) {
		int status = 0;
		ResvHdr resvHdr = new ResvHdr();
		try {

			resvHdr.setResvNo(resvNo);
			resvHdr.setPickupNeeded(pickupNeeded);
			if (pickupNeeded) {
				java.sql.Date pickupDate = new java.sql.Date(Long.parseLong(pickupDateEpoch));
				resvHdr.setPickupDate(pickupDate);
				resvHdr.setPickupLocation(pickupLocation);
				resvHdr.setPickupSeats(Integer.parseInt(pickupSeats));
				resvHdr.setPickupRemarks(pickupRemarks);
				resvHdr.setPickupTime(pickupTime);
			}

			status = reservationService.updatePickUpDetails(resvHdr);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : updatePickUpDetails" + Throwables.getStackTraceAsString(e));
		}
		return status;
	}

}