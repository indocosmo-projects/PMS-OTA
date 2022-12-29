package com.indocosmo.pms.web.ota.controller.housekeeping;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indocosmo.pms.web.ota.dto.housekeeping.OTAHouseStatusDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInventoryDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.housekeeping.OTARoomList;
import com.indocosmo.pms.web.ota.service.housekeeping.OTAHouseKeepingServiceImpl;

@RestController
@RequestMapping(value = "/otahousekeeping")
public class OTAHouseKeepingController {
	
	@Autowired
	private OTAHouseKeepingServiceImpl otahousekeepingServiceImpl;
	
	
	@RequestMapping(value = "/otaretrieveinhouseroomstatus", method = RequestMethod.GET)
	public OTAHouseStatusDTO getRetrieveinhouseroomstatus(HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		OTAHouseStatusDTO  otahousestatus = otahousekeepingServiceImpl.getRetrieveinhouseroomstatus(hotel);
		
		return otahousestatus;
		
	}
	
	
	@RequestMapping(value = "/otaupdateroomstatus", method = RequestMethod.POST)
	public String getUpdateroomstatus(@RequestParam String roomid,
			@RequestParam String unitid,@RequestParam String hkstatus,@RequestParam String hkremarks,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otahousekeepingServiceImpl.getUpdateroomstatus(hotel, roomid, unitid, hkstatus, hkremarks);
		
		return response;
		
	}
	
	
	@RequestMapping(value = "/otablockroom", method = RequestMethod.POST)
	public String getBlockroom(
			@RequestParam String roomid,@RequestParam String roomtypeid,@RequestParam String fromdate,
			@RequestParam String todate,@RequestParam String reason,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otahousekeepingServiceImpl.getBlockroom(hotel, roomid, roomtypeid, fromdate, todate, reason);
		
		return response;
		
	}
	

	@RequestMapping(value = "/otaunblockroom", method = RequestMethod.POST)
	public String getUnblockroom(
			@RequestParam String roomid,@RequestParam String roomtypeid,@RequestParam String fromdate,
			@RequestParam String todate,HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response  = otahousekeepingServiceImpl.getUnblockroom(hotel, roomid, roomtypeid, fromdate, todate);
		
		return response;
		
	}
	
	

	@RequestMapping(value = "/otaroomidDB", method = RequestMethod.GET)
	public List<OTARoomList> getRoomidDB(HttpSession session) throws Exception{
		
		List<OTARoomList> otaroom = otahousekeepingServiceImpl.getRoomidDB();
		return otaroom;
		
	}
	
	
	@RequestMapping(value = "/otaretrieveinhouseroomstatusDB", method = RequestMethod.GET)
	public OTAHouseStatusDTO getRetrieveinhouseroomstatusDB(HttpSession session) throws Exception{
		
		OTAHouseStatusDTO otahousestatusdto = otahousekeepingServiceImpl.getRetrieveinhouseroomstatusDB();
		return otahousestatusdto;
		
	}
	
	
	
}
