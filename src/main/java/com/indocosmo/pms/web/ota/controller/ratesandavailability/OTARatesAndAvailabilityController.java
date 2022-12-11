package com.indocosmo.pms.web.ota.controller.ratesandavailability;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInventoryDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.service.ratesandavailability.OTARatesAndAvailabilityServiceImpl;

@RestController
@RequestMapping(value = "/otaratesandavailability")
public class OTARatesAndAvailabilityController {
	
	@Autowired
	private OTARatesAndAvailabilityServiceImpl OTARatesAndAvailabilityServiceImpl;
	
	
	@RequestMapping(value = "/otaretrieveroominventory", method = RequestMethod.POST)
	public List<OTARoomInventoryDTO> getRetrieveRoomInventory(@RequestParam String fdate,@RequestParam String tdate,HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTARoomInventoryDTO>  otaroominventorydto = OTARatesAndAvailabilityServiceImpl.getRetrieveRoomInventory(hotel,fdate,tdate);
		
		return otaroominventorydto;
		
	}
	
	
	
	@RequestMapping(value = "/otaupdatelinearrateinventory", method = RequestMethod.POST)
	public String getInventory(@RequestParam ArrayList<String> contactid ,@RequestParam String roomtypeid ,@RequestParam String ratetypeid ,
			@RequestParam String fromdate ,@RequestParam String todate ,@RequestParam String base ,@RequestParam String extraadult ,
			@RequestParam String extrachild ,HttpSession session) throws Exception{
		
		OTARoomInventoryDTO otaroominv = new OTARoomInventoryDTO();
		otaroominv.setContactids(contactid);
		otaroominv.setRoomtypeid(roomtypeid);
		otaroominv.setRatetypeid(ratetypeid);
		otaroominv.setFromdate(fromdate);
		otaroominv.setTodate(todate);
		otaroominv.setBase(base);
		otaroominv.setExtraadult(extraadult);
		otaroominv.setExtrachild(extrachild);
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String msg = OTARatesAndAvailabilityServiceImpl.updateLinearRateinventory(hotel,otaroominv);
		return msg;
		
	}
	
	
	
	
}
