package com.indocosmo.pms.web.dashboard.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.checkIn.model.CheckInRequestStatus;
import com.indocosmo.pms.web.checkinRequest.service.CheckinRequestService;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.dashboard.model.RoomDetails;
import com.indocosmo.pms.web.dashboard.service.DashboardService;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.systemSettings.model.Company;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;
import com.indocosmo.pms.web.templates.service.TemplateService;

@Controller
@RequestMapping(value = "/dashboard2")
public class DashboardController2 {

	@Autowired
	private SystemSettingsService systemSettingService;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private DashboardService dashboardService;

	@Autowired
	private CheckinRequestService checkinRequestService;

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	private SysPermissions permissionObj;

	
	public static final String DASHBOARD_HOME_PAGE_URL2 = "dashboard/dashboard_index2";
	public static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "access_denied/access_denied";

	/**
	 * Dashboard Home page
	 * 
	 * @return page redirect to dashboard/index
	 */

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String dashboard12(HttpServletRequest request, Model model, HttpSession session) throws Exception {
		String pageUrl = DASHBOARD_HOME_PAGE_URL2;
		permissionObj = pageAccessPermissionService.getPermission(session, "DASHBOARD");
		SysPermissions permissionObjRequest = pageAccessPermissionService.getPermission(session, "PMS_RECPN_REQUEST");
		if (permissionObj.isCanView() && permissionObj.isIs_view_applicable()) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			/*
			 * InvoiceTemplate tmplt=new InvoiceTemplate();
			 * tmplt=templateService.getInvoiceTemplateDtls();
			 */
			model.addAttribute("requestPerObj", permissionObjRequest);
			model.addAttribute("curPagePerObj", permissionObj);
			model.addAttribute("hotelDate", formatter.format(commonSettings.getHotelDate()));
			/*
			 * model.addAttribute("hdr1",tmplt.getHdrLine1()); model.addAttribute("hdr2",
			 * tmplt.getHdrLine2()); model.addAttribute("hdr3", tmplt.getHdrLine3());
			 * model.addAttribute("hdr4", tmplt.getHdrLine4());
			 */
			Company company = new Company();
			company = systemSettingService.getcompany();
			model.addAttribute("companyName", company.getCompanyName());
			model.addAttribute("buildingName", company.getBuildingName());
			model.addAttribute("streetName", company.getStreetName());
			model.addAttribute("cityName", company.getCityName());
			model.addAttribute("stateName", company.getStateName());
			model.addAttribute("countryName", company.getCountryName());
			model.addAttribute("gstNo", company.getGstNo());
		} else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;

	}

	

	@RequestMapping(value = "/getDetails", method = RequestMethod.POST)
	public @ResponseBody String getDetails(HttpServletRequest request, HttpSession session) throws Exception {
		int availableRooms = 0;
		JsonObject jobj = dashboardService.getDetails();
		JsonObject dashboardObj = jobj.get("dashboard_data").getAsJsonObject();
		JsonArray availRoomArray = dashboardObj.get("avail_room_list").getAsJsonArray();
		for (Object obj : availRoomArray) {
			JsonObject availObj = (JsonObject) obj;
			availableRooms = availableRooms + availObj.get("availRoom").getAsInt();
		}
		session.setAttribute("totalAvailableRooms", availableRooms);
		return jobj.toString();
	}

	@RequestMapping(value = "/getAddOnDetails", method = RequestMethod.POST)
	public @ResponseBody String getAddOnDetails(HttpServletRequest request) throws Exception {
		JsonArray addonList = checkinRequestService.getAddOnDetails(0, 0);
		return addonList.toString();
	}

	@RequestMapping(value = "/getListData", method = RequestMethod.POST)
	public @ResponseBody String getListData(HttpServletRequest request) throws Exception {
		JsonObject jobj = dashboardService.getListData();
		return jobj.toString();
	}

	@RequestMapping(value = "/processAddon", method = RequestMethod.POST)
	public @ResponseBody String processAddon(HttpServletRequest request, HttpSession session,
			@RequestParam(value = "addon") String addon) throws Exception {
		String status = "failed";
		Gson gson = new Gson();
		try {
			CheckInRequestStatus reqStatus = gson.fromJson(addon, CheckInRequestStatus.class);
			reqStatus.setDate(commonSettings.getHotelDate().toString());
			reqStatus.setProcessStatus(1);
			reqStatus.setUserId(((User) session.getAttribute("userForm")).getId());
			status = checkinRequestService.processAddon(reqStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson("status:" + status);
	}

	@RequestMapping(value = "/getRoomDetails", method = RequestMethod.POST)
	public @ResponseBody String getRoomDetails() {

		int roomType = 0;
		int floor = 0;
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

}