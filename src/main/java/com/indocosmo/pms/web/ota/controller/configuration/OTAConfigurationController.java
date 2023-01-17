package com.indocosmo.pms.web.ota.controller.configuration;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indocosmo.pms.web.ota.dto.booking.OTABookingDTO;
import com.indocosmo.pms.web.ota.dto.hotel.HotelInfoDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTACurrencyDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTAPaymentGatewayDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTAPaymethodsDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomTypeDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.room.OTARoomRoomTypes;
import com.indocosmo.pms.web.ota.service.booking.OTABookingServiceImpl;
import com.indocosmo.pms.web.ota.service.configuration.OTAConfigurationServiceImpl;

@RestController
@RequestMapping(value = "/otaconfiguration")
public class OTAConfigurationController {
	
	@Autowired
	private OTAConfigurationServiceImpl otaconfigurationserviceImpl;
	
	
	@RequestMapping(value = "/otacheckhotelauthentication", method = RequestMethod.POST)
	public List<HotelInfo> getCheckhotelauthentication(@RequestParam String authkey,HttpSession session) throws Exception{
		
		List<HotelInfo> otahotelinfo = otaconfigurationserviceImpl.getCheckhotelauthentication(authkey);
		
		return otahotelinfo;
		
	}
	
	
	@RequestMapping(value = "/otaretrievehotelinformation", method = RequestMethod.POST)
	public List<HotelInfoDTO> getRetrievehotelinformation(@RequestParam String authkey,@RequestParam String hotelcode,HttpSession session) throws Exception{
		
		List<HotelInfoDTO> otahotelinfo = otaconfigurationserviceImpl.getRetrievehotelinformation(hotelcode,authkey);
		
		return otahotelinfo;
		
	}
	
	
	@RequestMapping(value = "/otaretrievehotelamenities", method = RequestMethod.POST)
	public List<HotelInfoDTO> getRetrievehotelamenities(@RequestParam String authkey,@RequestParam String hotelcode,HttpSession session) throws Exception{
		
		List<HotelInfoDTO> otahotelinfo = otaconfigurationserviceImpl.getRetrievehotelamenities(hotelcode,authkey);
		
		return otahotelinfo;
		
	}
	
	
	@RequestMapping(value = "/otaretrieveroomtypes", method = RequestMethod.POST)
	public List<OTARoomTypeDTO> getRetrieveroomtypes(@RequestParam String authkey,@RequestParam String hotelcode,
			@RequestParam String publishtoweb,HttpSession session) throws Exception{
		
		List<OTARoomTypeDTO> otaroomtypedto = otaconfigurationserviceImpl.getRetrieveroomtypes(hotelcode,authkey,publishtoweb);
		
		return otaroomtypedto;
		
	}
	
	
	@RequestMapping(value = "/otaretrievesalutationsandcountry", method = RequestMethod.POST)
	public List<OTARoomTypeDTO> getRetrievesalutationsandcountry(@RequestParam String authkey,@RequestParam String hotelcode,
			HttpSession session) throws Exception{
		
		List<OTARoomTypeDTO> otaroomtypedto = otaconfigurationserviceImpl.getRetrievesalutationsandcountry(hotelcode,authkey);
		
		return otaroomtypedto;
		
	}
	
	
	@RequestMapping(value = "/otaretrieveextrasratebasedonparameters", method = RequestMethod.POST)
	public List<OTARoomTypeDTO> getRetrieveextrasratebasedonparameters(@RequestParam String authkey,@RequestParam String hotelcode,
			@RequestParam String checkindate,@RequestParam String checkoutdate,@RequestParam String extrachargeid,@RequestParam String extraitem,
			HttpSession session) throws Exception{
		
		List<OTARoomTypeDTO> otaroomtypedto = otaconfigurationserviceImpl.getRetrieveextrasratebasedonparameters(hotelcode, authkey,
				checkindate, checkoutdate, extrachargeid, extraitem);
		
		return otaroomtypedto;
		
	}
	
	
	@RequestMapping(value = "/otaverifytravelagent", method = RequestMethod.POST)
	public List<OTARoomTypeDTO> getVerifytravelagent(@RequestParam String authkey,@RequestParam String hotelcode,
			@RequestParam String username,@RequestParam String password,@RequestParam String groupcode,
			HttpSession session) throws Exception{
		
		List<OTARoomTypeDTO> otaroomtypedto = otaconfigurationserviceImpl.getVerifytravelagent(hotelcode, authkey,
				username, password, groupcode);
		
		return otaroomtypedto;
		
	}
	
	
	
	@RequestMapping(value = "/otapaymentgateways", method = RequestMethod.POST)
	public List<OTAPaymentGatewayDTO> getPaymentgateways(@RequestParam String authkey,@RequestParam String hotelcode,
			HttpSession session) throws Exception{
		
		List<OTAPaymentGatewayDTO> otapaymentgatewaydto = otaconfigurationserviceImpl.getPaymentgateways(hotelcode, authkey);
		
		return otapaymentgatewaydto;
		
	}
	
	
	@RequestMapping(value = "/otaretrievecurrency", method = RequestMethod.POST)
	public List<OTACurrencyDTO> getRetrievecurrency(@RequestParam String authkey,@RequestParam String hotelcode,
			HttpSession session) throws Exception{
		
		List<OTACurrencyDTO> otaroomtypedto = otaconfigurationserviceImpl.getRetrievecurrency(hotelcode, authkey);
		
		return otaroomtypedto;
		
	}
	
	
	@RequestMapping(value = "/otaretrievepaymethods", method = RequestMethod.POST)
	public List<OTAPaymethodsDTO> getRetrievepaymethods(@RequestParam String authkey,@RequestParam String hotelcode,
			HttpSession session) throws Exception{
		
		List<OTAPaymethodsDTO> otaroomtypedto = otaconfigurationserviceImpl.getRetrievepaymethods(hotelcode, authkey);
		
		return otaroomtypedto;
		
	}
	

	@RequestMapping(value = "/otaretrieveidentitytype", method = RequestMethod.POST)
	public List<OTAPaymethodsDTO> getRetrieveidentitytype(@RequestParam String authkey,@RequestParam String hotelcode,
			HttpSession session) throws Exception{
		
		List<OTAPaymethodsDTO> otaroomtypedto = otaconfigurationserviceImpl.getRetrieveidentitytype(hotelcode, authkey);
		
		return otaroomtypedto;
		
	}
	
	
	@RequestMapping(value = "/otaretrieveavailableroomlist", method = RequestMethod.POST)
	public List<OTARoomRoomTypes> getRetrieveavailableroomlist(@RequestParam String authkey,@RequestParam String hotelcode,
			@RequestParam String fromdate,@RequestParam String todate,
			HttpSession session) throws Exception{
		
		List<OTARoomRoomTypes> otaroomtypedto = otaconfigurationserviceImpl.getRetrieveroomlist(hotelcode, authkey,fromdate,todate);
		
		return otaroomtypedto;
		
	}
	
	
	
}
