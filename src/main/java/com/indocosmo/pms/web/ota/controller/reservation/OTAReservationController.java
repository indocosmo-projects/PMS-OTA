package com.indocosmo.pms.web.ota.controller.reservation;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.JsonObject;
import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.ota.dto.hotel.HotelInfoDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTAReservationDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.reservation.OTAReservation;
import com.indocosmo.pms.web.ota.service.reservation.OTAReservationServiceImpl;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.syetemDefPermission.service.SysPermissionsService;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;

@RestController
@RequestMapping(value = "/otadata")
public class OTAReservationController {
	
	@Autowired
	private OTAReservationServiceImpl otaReservationServiceImpl;
	
	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "exception/exception";
	public static final String SYDEF_OTA_PERMISION_DENIED_PAGE_URL =  "exception/exceptionota";
	
	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;
	
	@Autowired
	private SystemSettingsService systemSettingService;
	
	@Autowired
	private SysPermissionsService sysPermissionsService;
	
	private SysPermissions permissionObj;
	

	
	
	@RequestMapping(value = "/otareservationlist", method = RequestMethod.GET)
	public OTAReservationDTO getReservationList(HttpSession session) throws Exception{

		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		OTAReservationDTO ota = otaReservationServiceImpl.getRetrieveAll(hotel);
		return ota;
		
	}
	
	@RequestMapping(value = "/otareservationlistFromDB", method = RequestMethod.GET)
	public OTAReservationDTO getReservationListFromDB(HttpSession session) throws Exception{

		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		OTAReservationDTO ota = otaReservationServiceImpl.getRetrieveAllFromDB(hotel);
		return ota;
		
	}
	
	@RequestMapping(value = "/otabookingreceived", method = RequestMethod.POST)
	public HotelInfoDTO getBookingReceived(@RequestParam String BookingId,
			@RequestParam String PMS_BookingId, @RequestParam String Status,
			HttpSession session) throws Exception{
		
		System.out.println("=====>dfo");
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		HotelInfoDTO hotelinfo = otaReservationServiceImpl.getBookingReceived(hotel,BookingId,PMS_BookingId,Status);
		return hotelinfo;
		
	}
	
	
	
	@RequestMapping(value = "/otaroominformation", method = RequestMethod.GET)
	public OTAReservationDTO getRoomInformation(HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		OTAReservationDTO ota = otaReservationServiceImpl.getRoomInformation(hotel);
		return ota;
		
	}
	
	
	
}
