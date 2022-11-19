package com.indocosmo.pms.web.ota.controller.login;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.ota.dto.hotel.HotelInfoDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.service.login.OTALoginServiceImpl;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.syetemDefPermission.service.SysPermissionsService;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;

@Controller
@RequestMapping(value = "/onlinetravelagent")
public class OTALoginController {
	
	public static final String ONLINE_TRAVELAGENT_PAGE_LOGIN = "online_travel_agent/login/online_travelagent_login";
//	public static final String ONLINE_TRAVELAGENT_DASHBOARDPAGE_URL = "online_travel_agent/dashboard/online_travel_agent_dashboard";
	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "exception/exception";
	public static final String SYDEF_OTA_PERMISION_DENIED_PAGE_URL =  "exception/exceptionota";
	
	@Autowired
	private OTALoginServiceImpl otaLoginServiceImpl;
	
	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;
	
	@Autowired
	private SystemSettingsService systemSettingService;
	
	@Autowired
	private SysPermissionsService sysPermissionsService;
	
	private SysPermissions permissionObj;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String pageView(HttpServletRequest request, Model model, HttpSession session) throws Exception {
		String pageUrl = ONLINE_TRAVELAGENT_PAGE_LOGIN;
		
		HotelInfo hotel = new HotelInfo();
		model.addAttribute("hotel", hotel);
		session.setAttribute("iscanview", false);
		session.setAttribute("otalogin", false);
		
		permissionObj = pageAccessPermissionService.getPermission(session, "DASHBOARD");
		SysPermissions permissionObjRequest = pageAccessPermissionService.getPermission(session, "PMS_RECPN_REQUEST");
		String otalogin = session.getAttribute("otalogin").toString();
			if (permissionObj.isCanView() && permissionObj.isIs_view_applicable() && otalogin.equals("false")==true ) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

				model.addAttribute("requestPerObj", permissionObjRequest);
				model.addAttribute("curPagePerObj", permissionObj);
				model.addAttribute("hotelDate", formatter.format(commonSettings.getHotelDate()));
				session.setAttribute("iscanview", false);
				session.setAttribute("otalogin", false);
				
			} else {
				pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
			}
		
		return pageUrl;
	}	
	
	

	@RequestMapping(value = "/onlinetravelagentLogin", method = RequestMethod.POST)
	public @ResponseBody String gethotelinfo(@RequestParam("name") String name, @RequestParam("password") String password,
			Model model, HttpSession session,HttpServletResponse response,
			HttpServletRequest request) throws Exception{
		String redirectOTAUrlhomeUrl = "/";
		
		HotelInfoDTO hotelDTO = otaLoginServiceImpl.gethotelinfo(name, password);	
		
		if(hotelDTO.getStatus().equals("200")) {
			redirectOTAUrlhomeUrl = "/online_travel_agent_dashboard";
			
			session.setAttribute("iscanview", true);
			session.setAttribute("otalogin", true);
			HotelInfo hotel = new HotelInfo();
			hotel.setHotelname(hotelDTO.getHotelname());
			hotel.setAuthkey(hotelDTO.getPassword());
			hotel.setHotelcode(hotelDTO.getHotelcode());
			hotel.setUsername(hotelDTO.getUsername());
			session.setAttribute("hotel", hotel);
				
		}else {
			redirectOTAUrlhomeUrl = "/";
		}
		
		return redirectOTAUrlhomeUrl;
	}
	
	
	@RequestMapping(value = "/otalogout", method = RequestMethod.GET)
	public String logout(Model model, HttpSession session,HttpServletResponse response) throws IOException, ServletException {
		String pageUrl = ONLINE_TRAVELAGENT_PAGE_LOGIN;
		HotelInfo hotel = new HotelInfo();
		model.addAttribute("hotel", hotel);
		session.setAttribute("iscanview", false);
		
		return pageUrl;
	}
	
}
