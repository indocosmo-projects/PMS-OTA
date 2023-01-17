package com.indocosmo.pms.web.ota.controller.ratesandavailability;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInfoDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInventoryDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.service.ratesandavailability.OTARatesAndAvailabilityServiceImpl;

@RestController
@RequestMapping(value = "/otaratesandavailability")
public class OTARatesAndAvailabilityController {
	
	@Autowired
	private OTARatesAndAvailabilityServiceImpl OTARatesAndAvailabilityServiceImpl;
	
	
	@RequestMapping(value = "/otaupdateroominventory", method = RequestMethod.POST)
	public String getUpdateRoomInventory(@RequestParam String roomtypeid ,@RequestParam String base ,
			@RequestParam String fromdate ,@RequestParam String todate ,
			HttpSession session) throws Exception{
		
		OTARoomInventoryDTO otaroominv = new OTARoomInventoryDTO();
		otaroominv.setRoomtypeid(roomtypeid);
		otaroominv.setFromdate(fromdate);
		otaroominv.setTodate(todate);
		otaroominv.setBase(base);
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String msg = OTARatesAndAvailabilityServiceImpl.getUpdateRoomInventory(hotel,otaroominv);
		return msg;
		
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
	
	
	@RequestMapping(value = "/otaupdatenonlinearrate", method = RequestMethod.POST)
	public String getUpdateNonLinearRate(@RequestParam ArrayList<String> contactid ,@RequestParam String roomtypeid ,@RequestParam String ratetypeid ,
			@RequestParam String fromdate ,@RequestParam String todate ,@RequestParam String base ,@RequestParam String extraadult ,
			@RequestParam String extrachild ,@RequestParam String adultrates ,@RequestParam String childrates ,	
			HttpSession session) throws Exception{
		
		OTARoomInventoryDTO otaroominv = new OTARoomInventoryDTO();
		otaroominv.setContactids(contactid);
		otaroominv.setRoomtypeid(roomtypeid);
		otaroominv.setRatetypeid(ratetypeid);
		otaroominv.setFromdate(fromdate);
		otaroominv.setTodate(todate);
		otaroominv.setBase(base);
		otaroominv.setExtraadult(extraadult);
		otaroominv.setExtrachild(extrachild);
		otaroominv.setAdultrates(adultrates);
		otaroominv.setChildrates(childrates);
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String msg = OTARatesAndAvailabilityServiceImpl.getUpdateNonLinearRate(hotel,otaroominv);
		
		return msg;
		
	}
	
	
	@RequestMapping(value = "/otaretrieveroomratessourcedetails", method = RequestMethod.GET)
	public OTARoomInfoDTO getRetrieveroomratessourcedetails(HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		OTARoomInfoDTO otaroominfdto = OTARatesAndAvailabilityServiceImpl.getRetrieveroomratessourcedetails(hotel);
		return otaroominfdto;
		
	}
	
	@RequestMapping(value = "/otaupdatemaxnights", method = RequestMethod.POST)
	public String getupdatemaxnights(@RequestParam String rateplanid ,@RequestParam String fromdate ,
			@RequestParam String todate ,@RequestParam String maxnight ,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = OTARatesAndAvailabilityServiceImpl.getupdatemaxnights(hotel,rateplanid,fromdate,todate,maxnight);
		return response;
		
	}
	
	@RequestMapping(value = "/otaupdateminnights", method = RequestMethod.POST)
	public String getupdateminnights(@RequestParam String rateplanid ,@RequestParam String fromdate ,
			@RequestParam String todate ,@RequestParam String minnight ,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = OTARatesAndAvailabilityServiceImpl.getupdateminnights(hotel,rateplanid,fromdate,todate,minnight);
		return response;
		
	}
	
	@RequestMapping(value = "/otaupdatestopsell", method = RequestMethod.POST)
	public String getupdatestopsell(@RequestParam String rateplanid ,@RequestParam String fromdate ,
			@RequestParam String todate ,@RequestParam String stopsell ,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = OTARatesAndAvailabilityServiceImpl.getupdatestopsell(hotel,rateplanid,fromdate,todate,stopsell);
		return response;
		
	}
	
	
	@RequestMapping(value = "/otaupdatecoa", method = RequestMethod.POST)
	public String getupdatecoa(@RequestParam String rateplanid ,@RequestParam String fromdate ,
			@RequestParam String todate ,@RequestParam String coa ,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = OTARatesAndAvailabilityServiceImpl.getupdatecoa(hotel,rateplanid,fromdate,todate,coa);
		return response;
		
	}
	
	
	@RequestMapping(value = "/otaupdatecod", method = RequestMethod.POST)
	public String getupdatecod(@RequestParam String rateplanid ,@RequestParam String fromdate ,
			@RequestParam String todate ,@RequestParam String cod ,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = OTARatesAndAvailabilityServiceImpl.getupdatecod(hotel,rateplanid,fromdate,todate,cod);
		return response;
		
	}
	
	
	@RequestMapping(value = "/otaretrieveroominventory", method = RequestMethod.POST)
	public List<OTARoomInventoryDTO> getRetrieveRoomInventory(@RequestParam String fdate,@RequestParam String tdate,HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTARoomInventoryDTO>  otaroominventorydto = OTARatesAndAvailabilityServiceImpl.getRetrieveRoomInventory(hotel,fdate,tdate);
		
		return otaroominventorydto;
		
	}
	
	
	@RequestMapping(value = "/otaretrieveroomrates", method = RequestMethod.POST)
	public List<OTARoomInventoryDTO> getRetrieveroomrates(@RequestParam String fromdate ,@RequestParam String todate ,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTARoomInventoryDTO> otaroominventorydto = OTARatesAndAvailabilityServiceImpl.getRetrieveroomrates(hotel,fromdate,todate);
		return otaroominventorydto;
		
	}
	
	
}
