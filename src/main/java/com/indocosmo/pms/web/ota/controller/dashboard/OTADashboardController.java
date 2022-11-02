package com.indocosmo.pms.web.ota.controller.dashboard;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.service.login.OTALoginServiceImpl;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.syetemDefPermission.service.SysPermissionsService;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;

@Controller
@RequestMapping(value = "/onlinetravelagent")
public class OTADashboardController {
	
//	public static final String ONLINE_TRAVELAGENT_PAGE_LOGIN = "online_travel_agent/login/online_travelagent_login";
	public static final String ONLINE_TRAVELAGENT_DASHBOARDPAGE_URL = "online_travel_agent/dashboard/online_travel_agent_dashboard";
	public static final String ONLINE_TRAVELAGENT_RESERVATIONPAGE_URL = "online_travel_agent/reservation/ota_reservation";
	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "exception/exception";
	public static final String SYDEF_OTA_PERMISION_DENIED_PAGE_URL =  "exception/exceptionota";
	
	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;
	
	@Autowired
	private SystemSettingsService systemSettingService;
	
	@Autowired
	private SysPermissionsService sysPermissionsService;
	
	private SysPermissions permissionObj;

	@RequestMapping(value = "/online_travel_agent_dashboard", method = RequestMethod.GET)
	public String dashboardView(HttpServletRequest request, Model model, HttpSession session) throws Exception {
		String pageUrl = ONLINE_TRAVELAGENT_DASHBOARDPAGE_URL;
		permissionObj = pageAccessPermissionService.getPermission(session, "ONLINE_TRAVEL_AGENT");
		SysPermissions permissionObjRequest = pageAccessPermissionService.getPermission(session, "ONLINE_TRAVEL_AGENT");
		String iscanView = session.getAttribute("iscanview").toString();
		String otalogin = session.getAttribute("otalogin").toString();
		System.out.println("otalogin---->"+otalogin);
		if (permissionObj.isIs_view_applicable() && iscanView.equals("true")==true && otalogin.equals("true")==true) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			model.addAttribute("requestPerObj", permissionObjRequest);
			model.addAttribute("curPagePerObj", permissionObj);
			model.addAttribute("hotelDate", formatter.format(commonSettings.getHotelDate()));
			
			HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
			model.addAttribute("name", hotel.getHotelname());
			
		} else {
			pageUrl = SYDEF_OTA_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}	
	
	
	@RequestMapping(value = "/otareservation", method = RequestMethod.GET)
	public void getReservation(HttpServletRequest request, Model model, HttpSession session
			,HttpServletResponse response) throws Exception{
		response.sendRedirect("/pms/onlinetravelagent/otareservationpage");
	}
	
	@RequestMapping(value = "/otareservationpage", method = RequestMethod.GET)
	public String reservationView(HttpServletRequest request, Model model, HttpSession session) throws Exception {
		String pageUrl = ONLINE_TRAVELAGENT_RESERVATIONPAGE_URL;
		permissionObj = pageAccessPermissionService.getPermission(session, "ONLINE_TRAVEL_AGENT");
		SysPermissions permissionObjRequest = pageAccessPermissionService.getPermission(session, "ONLINE_TRAVEL_AGENT");
		String iscanView = session.getAttribute("iscanview").toString();
		String otalogin = session.getAttribute("otalogin").toString();
		System.out.println("otalogin---->"+otalogin);
		if (permissionObj.isIs_view_applicable() && iscanView.equals("true")==true && otalogin.equals("true")==true) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			model.addAttribute("requestPerObj", permissionObjRequest);
			model.addAttribute("curPagePerObj", permissionObj);
			model.addAttribute("hotelDate", formatter.format(commonSettings.getHotelDate()));
			
			HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
			model.addAttribute("name", hotel.getHotelname());
			
		} else {
			pageUrl = SYDEF_OTA_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}	
}
