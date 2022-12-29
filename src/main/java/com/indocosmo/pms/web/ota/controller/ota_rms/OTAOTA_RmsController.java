package com.indocosmo.pms.web.ota.controller.ota_rms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.web.ota.dto.reservation.OTAPushReservationDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInfoDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInventoryDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.service.ota_rms.OTAOTA_RmsServiceImpl;
import com.indocosmo.pms.web.ota.service.reservation.OTAReservationServiceImpl;

@RestController
@RequestMapping(value = "/otarms")
public class OTAOTA_RmsController {
	
	@Autowired
	private OTAReservationServiceImpl otaReservationServiceImpl;
	
	@Autowired
	private OTAOTA_RmsServiceImpl otaota_rmsserviceimpl;
	
		
	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "exception/exception";
	public static final String SYDEF_OTA_PERMISION_DENIED_PAGE_URL =  "exception/exceptionota";
	

	@RequestMapping(value = "/otarequestroominformation", method = RequestMethod.GET)
	public OTARoomInfoDTO getRequestroominformation(HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		OTARoomInfoDTO ota = otaota_rmsserviceimpl.getRequestroominformation(hotel);
		return ota;
	}
	
	@RequestMapping(value = "/otapushinventory", method = RequestMethod.POST)
	public String getPushinventory(@RequestParam String roomtypeId,@RequestParam String fromdate,
			@RequestParam String todate,@RequestParam String count,
			HttpSession session) throws Exception{
		
		
		OTARoomInventoryDTO otaroominv = new OTARoomInventoryDTO();
		otaroominv.setRoomtypeid(roomtypeId);
		otaroominv.setFromdate(fromdate);
		otaroominv.setTodate(todate);
		otaroominv.setBase(count);
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otaota_rmsserviceimpl.getPushinventory(hotel, otaroominv);
		return response;
		
	}
	
	
	@RequestMapping(value = "/otapushlinearrates", method = RequestMethod.POST)
	public String getPushlinearrates(@RequestParam String roomtypeId,@RequestParam String ratetypeId,@RequestParam String fromdate,
			@RequestParam String todate,@RequestParam String count,@RequestParam String extraadult,@RequestParam String extrachild,
			HttpSession session) throws Exception{
		
		OTARoomInventoryDTO otaroominv = new OTARoomInventoryDTO();
		otaroominv.setRoomtypeid(roomtypeId);
		otaroominv.setRatetypeid(ratetypeId);
		otaroominv.setFromdate(fromdate);
		otaroominv.setTodate(todate);
		otaroominv.setBase(count);
		otaroominv.setExtrachild(extrachild);
		otaroominv.setExtraadult(extraadult);
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otaota_rmsserviceimpl.getPushlinearrates(hotel, otaroominv);
		
		return response;
		     
	}
	
	
	@RequestMapping(value = "/otapushnonlinearrates", method = RequestMethod.POST)
	public String getPushNonlinearrates(@RequestParam String roomtypeId,@RequestParam String ratetypeId,@RequestParam String fromdate,
			@RequestParam String todate,@RequestParam  String adults,@RequestParam  String childs,
			HttpSession session) throws Exception{
		

		OTARoomInventoryDTO otaroominv = new OTARoomInventoryDTO();
		otaroominv.setRoomtypeid(roomtypeId);
		otaroominv.setRatetypeid(ratetypeId);
		otaroominv.setFromdate(fromdate);
		otaroominv.setTodate(todate);
		otaroominv.setExtraadult(adults);
		otaroominv.setExtrachild(childs);
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otaota_rmsserviceimpl.getPushNonlinearrates(hotel, otaroominv);
		
		return response;
		     
	}
	
	
	
	@RequestMapping(value = "/otapushminimumnights", method = RequestMethod.POST)
	public String getPushminimumnights(@RequestParam String roomtypeId,@RequestParam String ratetypeId,
			@RequestParam String rateplanId,	@RequestParam  String count,
			@RequestParam String fromdate,@RequestParam String todate,
			HttpSession session) throws Exception{
		

		OTARoomInventoryDTO otaroominv = new OTARoomInventoryDTO();
		otaroominv.setRoomtypeid(roomtypeId);
		otaroominv.setRatetypeid(ratetypeId);
		otaroominv.setRateplanid(rateplanId);
		otaroominv.setFromdate(fromdate);
		otaroominv.setTodate(todate);
		otaroominv.setBase(count);
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otaota_rmsserviceimpl.getPushminimumnights(hotel, otaroominv);
		
		return response;   
	}
	
	
	
	@RequestMapping(value = "/otapushstopsell", method = RequestMethod.POST)
	public String getPushstopcell(@RequestParam String roomtypeId,@RequestParam String ratetypeId,
			@RequestParam String rateplanId,	@RequestParam  String count,
			@RequestParam String fromdate,@RequestParam String todate,
			HttpSession session) throws Exception{
		

		OTARoomInventoryDTO otaroominv = new OTARoomInventoryDTO();
		otaroominv.setRoomtypeid(roomtypeId);
		otaroominv.setRatetypeid(ratetypeId);
		otaroominv.setRateplanid(rateplanId);
		otaroominv.setFromdate(fromdate);
		otaroominv.setTodate(todate);
		otaroominv.setBase(count);
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otaota_rmsserviceimpl.getPushstopsell(hotel, otaroominv);
		
		return response;   
	}
	
	
	@RequestMapping(value = "/otapushcloseonarrival", method = RequestMethod.POST)
	public String getPushcloseonarrival(@RequestParam String roomtypeId,@RequestParam String ratetypeId,
			@RequestParam String rateplanId,	@RequestParam  String count,
			@RequestParam String fromdate,@RequestParam String todate,
			HttpSession session) throws Exception{
		

		OTARoomInventoryDTO otaroominv = new OTARoomInventoryDTO();
		otaroominv.setRoomtypeid(roomtypeId);
		otaroominv.setRatetypeid(ratetypeId);
		otaroominv.setRateplanid(rateplanId);
		otaroominv.setFromdate(fromdate);
		otaroominv.setTodate(todate);
		otaroominv.setBase(count);
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otaota_rmsserviceimpl.getPushcloseonarrival(hotel, otaroominv);
		
		return response;   
	}
	
	
	
	@RequestMapping(value = "/otapushcloseondeparture", method = RequestMethod.POST)
	public String getPushcloseondeparture(@RequestParam String roomtypeId,@RequestParam String ratetypeId,
			@RequestParam String rateplanId,	@RequestParam  String count,
			@RequestParam String fromdate,@RequestParam String todate,
			HttpSession session) throws Exception{
		

		OTARoomInventoryDTO otaroominv = new OTARoomInventoryDTO();
		otaroominv.setRoomtypeid(roomtypeId);
		otaroominv.setRatetypeid(ratetypeId);
		otaroominv.setRateplanid(rateplanId);
		otaroominv.setFromdate(fromdate);
		otaroominv.setTodate(todate);
		otaroominv.setBase(count);
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otaota_rmsserviceimpl.getPushcloseondeparture(hotel, otaroominv);
		
		return response;   
	}
	
	
	
	
	@RequestMapping(value = "/otagetbookingstoezee", method = RequestMethod.POST)
	public String getBookingstoezee(@RequestParam String subbookingid,
			@RequestParam String ratetypeid, @RequestParam String ratetype,
			@RequestParam String roomtypecode ,@RequestParam String roomtypename,
			@RequestParam String start, @RequestParam String end,
			@RequestParam String totalrate ,@RequestParam String totaldiscount,
			@RequestParam String totalextracharge, @RequestParam String totaltax,
			@RequestParam String totalpayment ,@RequestParam String salutation,
			@RequestParam String firstname, @RequestParam String lastname,
			@RequestParam String gender ,@RequestParam String address,
			@RequestParam String city, @RequestParam String state,
			@RequestParam String country ,@RequestParam String zipcode, 
			@RequestParam String phone, @RequestParam String mobile,
			@RequestParam String fax ,@RequestParam String email,
		    @RequestParam String transportationmode, @RequestParam String vehicle, 
		    @RequestParam String pickupdate ,@RequestParam String pickuptime,
			@RequestParam String comment, @RequestParam String effectivedate,
			@RequestParam String adult ,@RequestParam String child,   
			@RequestParam String rent ,@RequestParam String extracharge,
			@RequestParam String tax, @RequestParam String discount,
			@RequestParam String bookingid ,@RequestParam String status,
			@RequestParam String source, @RequestParam String code,
			@RequestParam String ccno ,@RequestParam String cctype,
			@RequestParam String ccexpirydate, @RequestParam String cardholdersname,     
			HttpSession session) throws Exception{
		
			String response = "";
			
			OTAPushReservationDTO otapushreservationdto = new OTAPushReservationDTO();
			
			 otapushreservationdto.setSubbookingid(subbookingid);
		     otapushreservationdto.setRatetypeid(ratetypeid);
			 otapushreservationdto.setRatetype(ratetype);
			 otapushreservationdto.setRoomtypecode(roomtypecode);
			 otapushreservationdto.setRoomtypename(roomtypename);
			 otapushreservationdto.setStart(start);
			 otapushreservationdto.setEnd(end);
			 otapushreservationdto.setTotalrate(totalrate);
			 otapushreservationdto.setTotaldiscount(totaldiscount);
			 otapushreservationdto.setTotalextracharge(totalextracharge);
			 otapushreservationdto.setTotaltax(totaltax);
			 otapushreservationdto.setTotalpayment(totalpayment);
			 otapushreservationdto.setSalutation(salutation);
			 otapushreservationdto.setFirstname(firstname);
			 otapushreservationdto.setLastname(lastname);
			 otapushreservationdto.setGender(gender);
			 otapushreservationdto.setAddress(address);
			 otapushreservationdto.setCity(city);
			 otapushreservationdto.setState(state);
			 otapushreservationdto.setCountry(country);
			 otapushreservationdto.setZipcode(zipcode);
			 otapushreservationdto.setPhone(phone);
			 otapushreservationdto.setMobile(mobile);
			 otapushreservationdto.setFax(fax);
			 otapushreservationdto.setEmail(email);
			 otapushreservationdto.setTransportationmode(transportationmode);
			 otapushreservationdto.setVehicle(vehicle);  
			 otapushreservationdto.setPickupdate(pickupdate);
			 otapushreservationdto.setPickuptime(pickuptime);
			 otapushreservationdto.setComment(comment);
			 otapushreservationdto.setEffectivedate(effectivedate);
			 otapushreservationdto.setAdult(adult);
			 otapushreservationdto.setChild(child);
			 otapushreservationdto.setRent(rent);
			 otapushreservationdto.setExtracharge(extracharge);
			 otapushreservationdto.setTax(tax);
			 otapushreservationdto.setDiscount(discount);
			 otapushreservationdto.setBookingid(bookingid);
			 otapushreservationdto.setStatus(status);
			 otapushreservationdto.setSource(source);
			 otapushreservationdto.setCode(code);
			 otapushreservationdto.setCcno(ccno);
			 otapushreservationdto.setCctype(cctype);
			 otapushreservationdto.setCcexpirydate(ccexpirydate);
			 otapushreservationdto.setCardholdersname(cardholdersname);  
			
			
			HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
			response = otaota_rmsserviceimpl.getBookingstoezee(hotel, otapushreservationdto);
		
		return response;   
	}
	
	
	
	
	
}
