package com.indocosmo.pms.web.checkinRequest.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.enumerator.common.FacilityTypes;
import com.indocosmo.pms.web.checkIn.model.CheckInRequest;
import com.indocosmo.pms.web.checkIn.model.CheckInRequestStatus;
import com.indocosmo.pms.web.checkinRequest.service.CheckinRequestService;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;

@Controller
@RequestMapping(value = "/checkin_request")
public class CheckinRequestController {

	@Autowired
	private CheckinRequestService checkinRequestService;

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	private SysPermissions permissionObj;
	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "access_denied/access_denied";

	/**
	 * @param request
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "addonlist", method = RequestMethod.GET)
	public String dashboard1(HttpServletRequest request, Model model, HttpSession session) throws Exception {
		String pageUrl = "checkin_request/request_list";
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_RECPN_REQUEST");
		if (permissionObj.isCanView() && permissionObj.isIs_view_applicable()) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			model.addAttribute("hotelDate", formatter.format(commonSettings.getHotelDate()));
			model.addAttribute("facilityTypes", FacilityTypes.values());
			model.addAttribute("curPagePerObj", permissionObj);
			model.addAttribute("facilityTypes", FacilityTypes.values());
		} else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;

	}

	/**
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getRoomList", method = RequestMethod.POST)
	public @ResponseBody String getRoomList(HttpServletRequest request, HttpSession session) {
		JsonArray jarray = null;
		try {
			jarray = checkinRequestService.getRoomList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jarray.toString();
	}

	/**
	 * @param request
	 * @param session
	 * @param checkno
	 * @return
	 */
	@RequestMapping(value = "/getRoomRequestList", method = RequestMethod.POST)
	public @ResponseBody String getRoomRequestList(HttpServletRequest request, HttpSession session,
			@RequestParam(value = "checkno") int checkno) {
		JsonArray jarray = null;
		try {
			jarray = checkinRequestService.getAddOnDetails(0, checkno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jarray.toString();
	}

	@RequestMapping(value = "/getListRoomRequest", method = RequestMethod.POST)
	public @ResponseBody String getListRoomRequest(HttpServletRequest request, HttpSession session) {
		JsonArray jarray = null;
		try {
			jarray = checkinRequestService.getListRoomRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jarray.toString();

	}

	/**
	 * @param request
	 * @param session
	 * @param checkno
	 * @return
	 */
	@RequestMapping(value = "/getRequestList", method = RequestMethod.POST)
	public @ResponseBody String getRequestList(HttpServletRequest request, HttpSession session,
			@RequestParam(value = "checkno") int checkno) {
		List<CheckInRequest> chlist = null;
		Gson gson = new Gson();
		try {
			chlist = checkinRequestService.getCheckInRequestList(checkno, "checkin");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(chlist);
	}

	/**
	 * @param request
	 * @param session
	 * @param checkinreqid
	 * @return
	 */
	@RequestMapping(value = "/getCheckinRequestStatusList", method = RequestMethod.POST)
	public @ResponseBody String getCheckinRequestStatusList(HttpServletRequest request, HttpSession session,
			@RequestParam(value = "checkinreqid") int checkinreqid) {
		List<CheckInRequestStatus> checkinRequestStatusList = null;
		Gson gson = new Gson();
		try {
			checkinRequestStatusList = checkinRequestService.getCheckInRequestSatusList(checkinreqid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(checkinRequestStatusList);
	}

	/**
	 * @param request
	 * @param session
	 * @param addon
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody String save(HttpServletRequest request, HttpSession session, @RequestBody String addon) {
		Gson gson = new Gson();
		JsonParser jparser = new JsonParser();
		boolean isReq_dirty = false;
		boolean isSave = true;
		boolean isDelete = true;
		try {
			JsonObject jobj = jparser.parse(addon).getAsJsonObject();
			JsonObject addonObj = jobj.get("addonData").getAsJsonObject();
			JsonObject inactiveList = jobj.get("inactiveList").getAsJsonObject();
			CheckInRequest checkinreq = checkinRequestService.getCheckInRequest(addonObj.get("chreq_id").getAsInt());
			SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
			Date reqTime = localDateFormat.parse(addonObj.get("chreq_req_time").getAsString());

			if (!(localDateFormat.format(checkinreq.getReqTime()).equals(localDateFormat.format(reqTime)))) {
				isReq_dirty = true;
			}
			if (isReq_dirty) {
				checkinreq.setUserId(((User) session.getAttribute("userForm")).getId());
				checkinreq.setIsDeleted(false);
				List<CheckInRequest> requestList = new ArrayList<CheckInRequest>();
				requestList.add(checkinreq);
				isSave = checkinRequestService.saveAddOns(requestList);
			}

			Set<Map.Entry<String, JsonElement>> entries = inactiveList.entrySet();
			for (Map.Entry<String, JsonElement> entry : entries) {
				JsonObject statobj = jparser.parse(entry.getValue().toString()).getAsJsonObject();
				if (statobj.get("status").getAsBoolean() && !(statobj.get("is_deleted").getAsBoolean())
						&& statobj.get("id").getAsInt() == 0) {
					CheckInRequestStatus checkinStatus = new CheckInRequestStatus();
					checkinStatus.setCheckInReequestId(checkinreq.getId());
					checkinStatus.setRemarks("set as inactive");
					checkinStatus.setUserId(((User) session.getAttribute("userForm")).getId());
					isSave = checkinRequestService.saveCheckinRequestStatus(checkinStatus);
				}
				else if (statobj.get("is_deleted").getAsBoolean() && statobj.get("id").getAsInt() != 0) {
					isDelete = checkinRequestService.deleteAddOnStatus(statobj.get("id").getAsInt());
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		String status = "success";
		if (!isSave)
			status = "failure";

		return gson.toJson("status:" + status);
	}

	/**
	 * @param request
	 * @param session
	 * @param checkinreqid
	 * @return
	 */
	@RequestMapping(value = "/cancelAddon", method = RequestMethod.POST)
	public @ResponseBody String cancelAddon(HttpServletRequest request, HttpSession session,
			@RequestParam(value = "checkinreqid") int checkinreqid) {
		boolean isCancelled = true;
		Gson gson = new Gson();
		try {
			isCancelled = checkinRequestService.cancelAddOns(checkinreqid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String status = "success";
		if (!isCancelled)
			status = "failure";

		return gson.toJson("status:" + status);
	}

	/**
	 * @param request
	 * @param session
	 * @param addon
	 * @return
	 */
	@RequestMapping(value = "/saveNew", method = RequestMethod.POST)
	public @ResponseBody String saveNew(HttpServletRequest request, HttpSession session, @RequestBody String addon) {
		boolean isSave = false;
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		JsonParser parser = new JsonParser();
		mapper.setDateFormat(simpleDateformat);
		JsonObject jobj = parser.parse(addon).getAsJsonObject();
		try {
			List<CheckInRequest> chreqlist = mapper.readValue(jobj.get("data").toString(),
					TypeFactory.defaultInstance().constructCollectionType(List.class, CheckInRequest.class));

			for (CheckInRequest reqObj : chreqlist) {
				if (reqObj.getId() == 0) {
					reqObj.setCheckInNo(jobj.get("checkin_no").getAsInt());
					reqObj.setReqCompleted(false);
					reqObj.setIsDeleted(false);
					reqObj.setReqTakenBy(((User) session.getAttribute("userForm")).getId());
					reqObj.setUserId(reqObj.getReqTakenBy());
					reqObj.setReqTakenDate(commonSettings.hotelDate);
					reqObj.setCanceled(false);
				}
			}
			isSave = checkinRequestService.saveAddOns(chreqlist);
		} catch (Exception e) {
			isSave = false;
			e.printStackTrace();
		}
		String status = "success";
		if (!isSave)
			status = "failure";

		return gson.toJson("status:" + status);
	}
}
