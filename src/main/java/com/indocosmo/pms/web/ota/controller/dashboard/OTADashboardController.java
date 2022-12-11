package com.indocosmo.pms.web.ota.controller.dashboard;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

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
//	public static final String ONLINE_TRAVELAGENT_DASHBOARDPAGE_URL = "online_travel_agent/dashboard/online_travel_agent_dashboard";
	public static final String ONLINE_TRAVELAGENT_DASHBOARDPAGE_URL = "online_travel_agent/reservation/ota_reservation";
	public static final String ONLINE_TRAVELAGENT_RATESANDAVAILABILITY_URL = "online_travel_agent/ratesandavailability/otaratesandavailability";
	public static final String ONLINE_TRAVELAGENT_BOOKINGS_URL = "online_travel_agent/bookings/ota_booking";
	public static final String ONLINE_TRAVELAGENT_FINANCE_URL = "online_travel_agent/finance/otafinance";
	public static final String ONLINE_TRAVELAGENT_HOUSEKEEPING_URL = "online_travel_agent/housekeeping/otahousekeeping";
	public static final String ONLINE_TRAVELAGENT_OTARMS_URL = "online_travel_agent/ota_rms/ota_ota_rms";
	public static final String ONLINE_TRAVELAGENT_OTHERS_URL = "online_travel_agent/otaothers/ota_others";
	public static final String ONLINE_TRAVELAGENT_CONFIGURATION_URL = "online_travel_agent/configuration/ota_configuration";

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
		if (permissionObj.isIs_view_applicable() && iscanView.equals("true")==true && otalogin.equals("true")==true) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			model.addAttribute("requestPerObj", permissionObjRequest);
			model.addAttribute("curPagePerObj", permissionObj);
			model.addAttribute("hotelDate", formatter.format(commonSettings.getHotelDate()));
			
			HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
			model.addAttribute("name", hotel.getHotelname());
			LocalDateTime now =  (LocalDateTime) session.getAttribute("refreshdatetime");
			model.addAttribute("refreshdatetime", now);
			
		} else {
			pageUrl = SYDEF_OTA_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}	
	
	
	

	@RequestMapping(value = "/otaratesandavailability", method = RequestMethod.GET)
	public void getRatesAndAvailability(HttpServletRequest request, Model model, HttpSession session
			,HttpServletResponse response) throws Exception{
		response.sendRedirect("/pms/onlinetravelagent/otaratesandavailabilitypage");
	}
	
	@RequestMapping(value = "/otaratesandavailabilitypage", method = RequestMethod.GET)
	public String ratesAndAvailabilityView(HttpServletRequest request, Model model, HttpSession session) throws Exception {
		String pageUrl = ONLINE_TRAVELAGENT_RATESANDAVAILABILITY_URL;
		permissionObj = pageAccessPermissionService.getPermission(session, "ONLINE_TRAVEL_AGENT");
		SysPermissions permissionObjRequest = pageAccessPermissionService.getPermission(session, "ONLINE_TRAVEL_AGENT");
		String iscanView = session.getAttribute("iscanview").toString();
		String otalogin = session.getAttribute("otalogin").toString();
		if (permissionObj.isIs_view_applicable() && iscanView.equals("true")==true && otalogin.equals("true")==true) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			model.addAttribute("requestPerObj", permissionObjRequest);
			model.addAttribute("curPagePerObj", permissionObj);
			model.addAttribute("hotelDate", formatter.format(commonSettings.getHotelDate()));
			
			HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
			model.addAttribute("name", hotel.getHotelname());
			LocalDateTime now =  (LocalDateTime) session.getAttribute("refreshdatetime");
			model.addAttribute("refreshdatetime", now);
			
		} else {
			pageUrl = SYDEF_OTA_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}	
	
	
	
	
	
	@RequestMapping(value = "/otabookings", method = RequestMethod.GET)
	public void bookings(HttpServletRequest request, Model model, HttpSession session
			,HttpServletResponse response) throws Exception{
		response.sendRedirect("/pms/onlinetravelagent/otabookingspage");
	}
	
	@RequestMapping(value = "/otabookingspage", method = RequestMethod.GET)
	public String otabookingsView(HttpServletRequest request, Model model, HttpSession session) throws Exception {
		String pageUrl = ONLINE_TRAVELAGENT_BOOKINGS_URL;
		permissionObj = pageAccessPermissionService.getPermission(session, "ONLINE_TRAVEL_AGENT");
		SysPermissions permissionObjRequest = pageAccessPermissionService.getPermission(session, "ONLINE_TRAVEL_AGENT");
		String iscanView = session.getAttribute("iscanview").toString();
		String otalogin = session.getAttribute("otalogin").toString();
		if (permissionObj.isIs_view_applicable() && iscanView.equals("true")==true && otalogin.equals("true")==true) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			model.addAttribute("requestPerObj", permissionObjRequest);
			model.addAttribute("curPagePerObj", permissionObj);
			model.addAttribute("hotelDate", formatter.format(commonSettings.getHotelDate()));
			
			HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
			model.addAttribute("name", hotel.getHotelname());
			LocalDateTime now =  (LocalDateTime) session.getAttribute("refreshdatetime");
			model.addAttribute("refreshdatetime", now);
			
		} else {
			pageUrl = SYDEF_OTA_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}	
	
	
	
	
	@RequestMapping(value = "/otafinance", method = RequestMethod.GET)
	public void finance(HttpServletRequest request, Model model, HttpSession session
			,HttpServletResponse response) throws Exception{
		response.sendRedirect("/pms/onlinetravelagent/otafinancepage");
	}
	
	@RequestMapping(value = "/otafinancepage", method = RequestMethod.GET)
	public String otaotafinanceView(HttpServletRequest request, Model model, HttpSession session) throws Exception {
		String pageUrl = ONLINE_TRAVELAGENT_FINANCE_URL;
		permissionObj = pageAccessPermissionService.getPermission(session, "ONLINE_TRAVEL_AGENT");
		SysPermissions permissionObjRequest = pageAccessPermissionService.getPermission(session, "ONLINE_TRAVEL_AGENT");
		String iscanView = session.getAttribute("iscanview").toString();
		String otalogin = session.getAttribute("otalogin").toString();
		if (permissionObj.isIs_view_applicable() && iscanView.equals("true")==true && otalogin.equals("true")==true) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			model.addAttribute("requestPerObj", permissionObjRequest);
			model.addAttribute("curPagePerObj", permissionObj);
			model.addAttribute("hotelDate", formatter.format(commonSettings.getHotelDate()));
			
			HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
			model.addAttribute("name", hotel.getHotelname());
			LocalDateTime now =  (LocalDateTime) session.getAttribute("refreshdatetime");
			model.addAttribute("refreshdatetime", now);
			
		} else {
			pageUrl = SYDEF_OTA_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}	
	
	
	
	
	@RequestMapping(value = "/otahousekeeping", method = RequestMethod.GET)
	public void housekeeping(HttpServletRequest request, Model model, HttpSession session
			,HttpServletResponse response) throws Exception{
		response.sendRedirect("/pms/onlinetravelagent/otahousekeepingpage");
	}
	
	@RequestMapping(value = "/otahousekeepingpage", method = RequestMethod.GET)
	public String otahousekeepingView(HttpServletRequest request, Model model, HttpSession session) throws Exception {
		String pageUrl = ONLINE_TRAVELAGENT_HOUSEKEEPING_URL;
		permissionObj = pageAccessPermissionService.getPermission(session, "ONLINE_TRAVEL_AGENT");
		SysPermissions permissionObjRequest = pageAccessPermissionService.getPermission(session, "ONLINE_TRAVEL_AGENT");
		String iscanView = session.getAttribute("iscanview").toString();
		String otalogin = session.getAttribute("otalogin").toString();
		if (permissionObj.isIs_view_applicable() && iscanView.equals("true")==true && otalogin.equals("true")==true) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			model.addAttribute("requestPerObj", permissionObjRequest);
			model.addAttribute("curPagePerObj", permissionObj);
			model.addAttribute("hotelDate", formatter.format(commonSettings.getHotelDate()));
			
			HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
			model.addAttribute("name", hotel.getHotelname());
			LocalDateTime now =  (LocalDateTime) session.getAttribute("refreshdatetime");
			model.addAttribute("refreshdatetime", now);
			
		} else {
			pageUrl = SYDEF_OTA_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}	
	
	
	
	
	@RequestMapping(value = "/otaotarms", method = RequestMethod.GET)
	public void otarms(HttpServletRequest request, Model model, HttpSession session
			,HttpServletResponse response) throws Exception{
		response.sendRedirect("/pms/onlinetravelagent/otaotarmspage");
	}
	
	@RequestMapping(value = "/otaotarmspage", method = RequestMethod.GET)
	public String otarmsView(HttpServletRequest request, Model model, HttpSession session) throws Exception {
		String pageUrl = ONLINE_TRAVELAGENT_OTARMS_URL;
		permissionObj = pageAccessPermissionService.getPermission(session, "ONLINE_TRAVEL_AGENT");
		SysPermissions permissionObjRequest = pageAccessPermissionService.getPermission(session, "ONLINE_TRAVEL_AGENT");
		String iscanView = session.getAttribute("iscanview").toString();
		String otalogin = session.getAttribute("otalogin").toString();
		if (permissionObj.isIs_view_applicable() && iscanView.equals("true")==true && otalogin.equals("true")==true) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			model.addAttribute("requestPerObj", permissionObjRequest);
			model.addAttribute("curPagePerObj", permissionObj);
			model.addAttribute("hotelDate", formatter.format(commonSettings.getHotelDate()));
			
			HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
			model.addAttribute("name", hotel.getHotelname());
			LocalDateTime now =  (LocalDateTime) session.getAttribute("refreshdatetime");
			model.addAttribute("refreshdatetime", now);
			
		} else {
			pageUrl = SYDEF_OTA_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}	
	
	
	
	
	
	@RequestMapping(value = "/otaothers", method = RequestMethod.GET)
	public void otaothers(HttpServletRequest request, Model model, HttpSession session
			,HttpServletResponse response) throws Exception{
		response.sendRedirect("/pms/onlinetravelagent/otaotherspage");
	}
	
	@RequestMapping(value = "/otaotherspage", method = RequestMethod.GET)
	public String otaothersView(HttpServletRequest request, Model model, HttpSession session) throws Exception {
		String pageUrl = ONLINE_TRAVELAGENT_OTHERS_URL;
		permissionObj = pageAccessPermissionService.getPermission(session, "ONLINE_TRAVEL_AGENT");
		SysPermissions permissionObjRequest = pageAccessPermissionService.getPermission(session, "ONLINE_TRAVEL_AGENT");
		String iscanView = session.getAttribute("iscanview").toString();
		String otalogin = session.getAttribute("otalogin").toString();
		if (permissionObj.isIs_view_applicable() && iscanView.equals("true")==true && otalogin.equals("true")==true) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			model.addAttribute("requestPerObj", permissionObjRequest);
			model.addAttribute("curPagePerObj", permissionObj);
			model.addAttribute("hotelDate", formatter.format(commonSettings.getHotelDate()));
			
			HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
			model.addAttribute("name", hotel.getHotelname());
			LocalDateTime now =  (LocalDateTime) session.getAttribute("refreshdatetime");
			model.addAttribute("refreshdatetime", now);
			
		} else {
			pageUrl = SYDEF_OTA_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}	
	
	

	
	@RequestMapping(value = "/otaconfiguration", method = RequestMethod.GET)
	public void otaconfiguration(HttpServletRequest request, Model model, HttpSession session
			,HttpServletResponse response) throws Exception{
		response.sendRedirect("/pms/onlinetravelagent/otaconfigurationpage");
	}
	
	@RequestMapping(value = "/otaconfigurationpage", method = RequestMethod.GET)
	public String otaconfigurationView(HttpServletRequest request, Model model, HttpSession session) throws Exception {
		String pageUrl = ONLINE_TRAVELAGENT_CONFIGURATION_URL;
		permissionObj = pageAccessPermissionService.getPermission(session, "ONLINE_TRAVEL_AGENT");
		SysPermissions permissionObjRequest = pageAccessPermissionService.getPermission(session, "ONLINE_TRAVEL_AGENT");
		String iscanView = session.getAttribute("iscanview").toString();
		String otalogin = session.getAttribute("otalogin").toString();
		if (permissionObj.isIs_view_applicable() && iscanView.equals("true")==true && otalogin.equals("true")==true) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			model.addAttribute("requestPerObj", permissionObjRequest);
			model.addAttribute("curPagePerObj", permissionObj);
			model.addAttribute("hotelDate", formatter.format(commonSettings.getHotelDate()));
			
			HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
			model.addAttribute("name", hotel.getHotelname());
			LocalDateTime now =  (LocalDateTime) session.getAttribute("refreshdatetime");
			model.addAttribute("refreshdatetime", now);
			
		} else {
			pageUrl = SYDEF_OTA_PERMISION_DENIED_PAGE_URL;
		}
		
		return pageUrl;
	}	
	
	
	
	
	
	
}
