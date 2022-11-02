package com.indocosmo.pms.web.housekeeping.controller;

import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.dashboard.model.RoomDetails;
import com.indocosmo.pms.web.dashboard.service.DashboardService;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.housekeeping.service.HKService;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;

@Controller
@RequestMapping("/housekeeping")
public class HKController {

	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "access_denied/access_denied";

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;
	@Autowired
	private HKService hkService;

	@Autowired
	private DashboardService dashboardService;

	private SysPermissions permissionObj;

	@RequestMapping("/houseKeepingList")
	public String houseKeepingList(Model model, HttpSession session) throws Exception {
		String pageURL = "/house_keeping/hk_list";
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_HK");
		if (permissionObj.isCanView() && permissionObj.isIs_view_applicable()) {
			model.addAttribute("curPagePerObj", permissionObj);
		} else {
			pageURL = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageURL;
	}

	@RequestMapping(value = "/hkStatus", method = RequestMethod.POST)
	public @ResponseBody String hkStatus(HttpSession session, @RequestBody String data) throws Exception {
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(data).getAsJsonObject();
		int roomType = jsonObject.get("roomType").getAsInt();
		int floor = jsonObject.get("floor").getAsInt();
		List<RoomDetails> roomDtlList = null;
		List<RoomDetails> resvRoomStatusList = null;
		Gson gson = new Gson();
		try {
			roomDtlList = dashboardService.getRoomDetails(roomType, floor);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		
		return gson.toJson(roomDtlList);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(@RequestParam("id") int roomid, @RequestParam("number") String roomNum,
			@RequestParam("status") int hkStatus, HttpSession session) throws Exception {
		String saveStatus = "success";
		Gson g = new Gson();
		try {
			boolean isSave = hkService.updateHk(roomid, roomNum, hkStatus);
			if (isSave) {
				saveStatus = "status:" + saveStatus;
			} else {
				saveStatus = "status:error";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return g.toJson(saveStatus).toString();
	}
}
