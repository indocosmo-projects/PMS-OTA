package com.indocosmo.pms.web.ota.controller.reservation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.ota.dto.reservation.OTAReservationDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomDetailsDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInfoDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInventoryDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.service.reservation.OTAReservationServiceImpl;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.syetemDefPermission.service.SysPermissionsService;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;  

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
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();   
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		OTAReservationDTO ota = otaReservationServiceImpl.getRetrieveAll(hotel);
//		OTAReservationDTO ota = otaReservationServiceImpl.getRetrieveAllNewReservation(hotel);
		session.setAttribute("refreshdatetime", now); 
		
		return ota;
	}
	
	
	@RequestMapping(value = "/otareservationlistFromDB", method = RequestMethod.GET)
	public OTAReservationDTO getReservationListFromDB(HttpSession session) throws Exception{

		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		OTAReservationDTO ota = otaReservationServiceImpl.getRetrieveAllFromDB(hotel);
		return ota;
		
	}
	
	@RequestMapping(value = "/otabookingreceived", method = RequestMethod.POST)
	public String getBookingReceived(@RequestParam String BookingId,
			@RequestParam String PMS_BookingId, @RequestParam String Status,
			HttpSession session) throws Exception{
		
	
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otaReservationServiceImpl.getBookingReceived(hotel,BookingId,PMS_BookingId,Status);
		
		return response;
		
	}
	

	@RequestMapping(value = "/otaroominformation", method = RequestMethod.POST)
	public OTARoomInfoDTO getRoomInformation(@RequestParam int roomrequired,HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		OTARoomInfoDTO ota = otaReservationServiceImpl.getRoomInformation(hotel,roomrequired);
		return ota;
		
	}
	
	
	@RequestMapping(value = "/otaroominformationFromDB", method = RequestMethod.GET)
	public OTARoomInfoDTO getRoomInformationFromDB(HttpSession session) throws Exception{
		
		OTARoomInfoDTO ota = otaReservationServiceImpl.getRoomInformationFromDB();
		return ota;
		
	}
	
	
	@RequestMapping(value = "/bookingid", method = RequestMethod.GET)
	public OTAReservationDTO getBookingId(HttpSession session) throws Exception{
		OTAReservationDTO ota = otaReservationServiceImpl.getBookingId();
		return ota;	
	}
	
	
	@RequestMapping(value = "/otareservationsingleroom", method = RequestMethod.POST)
	public List<OTARoomDetailsDTO> getOtareservationSingleroom(@RequestParam int reservationid,HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTARoomDetailsDTO> ota = otaReservationServiceImpl.getOtareservationSingleroom(hotel,reservationid);
		return ota;
		
	}
	


	
	
	
	
}
