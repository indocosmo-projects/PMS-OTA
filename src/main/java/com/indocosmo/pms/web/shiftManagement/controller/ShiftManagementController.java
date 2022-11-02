package com.indocosmo.pms.web.shiftManagement.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.shiftManagement.model.ShiftManagement;
import com.indocosmo.pms.web.shiftManagement.service.ShiftManagementService;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;

@Controller
@RequestMapping("/shiftManagement")
public class ShiftManagementController {
	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "access_denied/access_denied";

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	@Autowired
	SystemSettingsService systemSettingsService;

	@Autowired
	private ShiftManagementService shiftManagementService;

	private SysPermissions permissionObj;

	@RequestMapping(value = "/openshift", method = RequestMethod.GET)
	public String getOPenShiftManagementRecord(Model model, HttpSession session, HttpServletResponse response)
			throws Exception {
		String pageUrl = "/shiftManagement/shiftManagementOpenShift_edit";
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_OPENSHIFT");
		if (permissionObj.isCanView() && permissionObj.isIs_view_applicable()) {
			model.addAttribute("curPagePerObj", permissionObj);
		} else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;

	}

	@RequestMapping(value = "/closeshift", method = RequestMethod.GET)
	public String getCloseShiftManagementRecord(Model model, HttpSession session, HttpServletResponse response)
			throws Exception {

		String pageUrl = "/shiftManagement/shiftManagementCloseShift_edit";
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_CLOSESHIFT");
		if (permissionObj.isCanView() && permissionObj.isIs_view_applicable()) {
			model.addAttribute("curPagePerObj", permissionObj);
		} else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;

	}

	@RequestMapping(value = "/openShiftsave", method = RequestMethod.POST)
	public @ResponseBody void openShiftsave(@RequestParam(value = "openShiftJson") String openShiftJson,
			HttpSession session, HttpServletResponse response) throws Exception {

		Gson g = new Gson();
		final ShiftManagement shiftmanagement = g.fromJson(openShiftJson, ShiftManagement.class);
		int userId = ((User) session.getAttribute("userForm")).getId();
		SystemSettings systemSettings = systemSettingsService.getSystemSettings();
		final String hotelDate = systemSettings.getHotelDate().toString();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date openingdate = new Date();
		String openingtime = dateFormat.format(openingdate);
		JsonParser jparser = new JsonParser();
		JsonObject jobj = jparser.parse(openShiftJson).getAsJsonObject();
		String hotelDate1 = systemSettings.getHotelDate().toString();
		shiftmanagement.setOpeningDate(hotelDate);
		shiftmanagement.setOpeningTime(openingtime);
		shiftmanagement.setUserId(userId);
		shiftmanagement.setOpeningFloat(jobj.get("floatingamount").getAsBigDecimal());
		shiftmanagement.setShiftId(jobj.get("Shifts").getAsInt());
		String status = "success";

		if (shiftManagementService.isshiftOPen(hotelDate).equals("0")) {
			if (jobj.get("passWord").getAsString().equals(shiftManagementService.getPassWord(userId))) {
				boolean isSave = shiftManagementService.saveOpenShift(shiftmanagement);
				if (isSave) {
					status = "status:" + status;
					JsonObject jObj = new JsonObject();
					JsonArray jArray1 = shiftManagementService.getListShift(hotelDate1);
					Gson gson = new Gson();
					String json = gson.toJson(jArray1);
					jObj.addProperty("tablDta", json);
					response.getWriter().print(jObj);
				} else {
					status = "status_error_save";
					response.getWriter().print(status);
				}
			} else {
				status = "status_error_password";
				response.getWriter().print(status);
			}
		} else {
			status = "status_error_shiftOpen";
			response.getWriter().print(status);
		}
	}

	@RequestMapping(value = "getcurrentUserShift")
	public @ResponseBody String getcurrentUserShift() throws Exception {
		JsonArray jArray = shiftManagementService.getcurrentUserShift();
		return jArray.toString();
	}

	@RequestMapping(value = "/currentShiftupdate", method = RequestMethod.POST)
	public @ResponseBody String currentShiftUpdate(@RequestParam(value = "currentShiftJson") String currentShiftJson,
			HttpSession session) throws Exception {

		Gson g = new Gson();
		final ShiftManagement shiftmanagement = g.fromJson(currentShiftJson, ShiftManagement.class);
		final int userId = ((User) session.getAttribute("userForm")).getId();
		final SystemSettings systemSettings = systemSettingsService.getSystemSettings();
		final String hotelDate = systemSettings.getHotelDate().toString();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date closingdate = new Date();
		String closingtime = dateFormat.format(closingdate);
		JsonParser jparser = new JsonParser();
		JsonObject jobj = jparser.parse(currentShiftJson).getAsJsonObject();

		shiftmanagement.setClosingDate(hotelDate);
		shiftmanagement.setClosingTime(closingtime);
		shiftmanagement.setId(jobj.get("id").getAsInt());
		shiftmanagement.setOpeningDate(jobj.get("opening_date").getAsString());
		shiftmanagement.setOpeningFloat(jobj.get("opening_float").getAsBigDecimal());
		shiftmanagement.setOpeningTime(jobj.get("opening_time").getAsString());
		shiftmanagement.setShiftId(jobj.get("shift_id").getAsInt());
		shiftmanagement.setUserId(jobj.get("user_id").getAsInt());

		String saveStatus = "success";
		if (jobj.get("passWord").getAsString().equals(shiftManagementService.getPassWord(userId))) {
			boolean isSave = shiftManagementService.saveOpenShift(shiftmanagement);
			if (isSave) {
				saveStatus = "status:" + saveStatus;
			} else {
				saveStatus = "status:error";
			}
		}

		return g.toJson(saveStatus).toString();
	}

	@RequestMapping(value = "/currentShiftDetails", method = RequestMethod.POST)
	public @ResponseBody String currentShiftDetails() throws Exception {

		final ShiftManagement shiftmanagement = new ShiftManagement();
		final SystemSettings systemSettings = systemSettingsService.getSystemSettings();
		final String hotelDate = systemSettings.getHotelDate().toString();

		shiftmanagement.setClosingDate(hotelDate);
		JsonArray jArray = shiftManagementService.currentShiftDetails(hotelDate);

		return jArray.toString();
	}

	@RequestMapping(value = "/getListShift", method = RequestMethod.POST)
	public @ResponseBody String getListShift() throws Exception {

		final ShiftManagement shiftmanagement = new ShiftManagement();
		final SystemSettings systemSettings = systemSettingsService.getSystemSettings();
		final String hotelDate = systemSettings.getHotelDate().toString();

		shiftmanagement.setClosingDate(hotelDate);
		JsonArray jArray = shiftManagementService.getListShift(hotelDate);

		return jArray.toString();
	}

	@RequestMapping(value = "/allShiftDetails")
	public @ResponseBody String allShiftDetails() throws Exception {

		JsonArray jArray = shiftManagementService.allShiftDetails();

		return jArray.toString();
	}
}
