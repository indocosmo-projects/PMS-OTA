package com.indocosmo.pms.web.ota.controller.booking;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indocosmo.pms.web.ota.dto.booking.OTABookingDTO;
import com.indocosmo.pms.web.ota.dto.booking.OTARoomListDTO;
import com.indocosmo.pms.web.ota.dto.booking.OTARoomReportDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.others.OTAGuestStatics;
import com.indocosmo.pms.web.ota.entity.reservation.OTABookingTrans;
import com.indocosmo.pms.web.ota.entity.reservation.OTAReservation;
import com.indocosmo.pms.web.ota.service.booking.OTABookingServiceImpl;

@RestController
@RequestMapping(value = "/otabooking")
public class OTABookingController {

	@Autowired
	private OTABookingServiceImpl otabookingserviceimpl;

	
	@RequestMapping(value = "/otacheckavailability", method = RequestMethod.POST)
	public List<OTABookingDTO> getCheckAvailability(
			@RequestParam String checkindate,@RequestParam String checkoutdate,
			@RequestParam String nights,@RequestParam String adults,@RequestParam String child,
			@RequestParam String rooms,@RequestParam String roomtypeid,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTABookingDTO> otabookingdto = otabookingserviceimpl.getCheckAvailability(hotel,checkindate,checkoutdate,nights,adults,child,rooms,roomtypeid);
		
		return otabookingdto;
		
	}
	
	
	
	@RequestMapping(value = "/otaretrievesinglebooking", method = RequestMethod.POST)
	public List<OTAReservation> getSingleBooking(@RequestParam String bookingid, HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTAReservation> otareservation = otabookingserviceimpl.getSingleBooking(hotel,bookingid);
		
		return otareservation;
		
	}
	
	
	@RequestMapping(value = "/otaretrievearrivallist", method = RequestMethod.POST)
	public List<OTABookingTrans> getRetrieveArrivalList(@RequestParam String bookingid,
			@RequestParam String fromdate,@RequestParam String todate,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTABookingTrans> otabookingtrans = otabookingserviceimpl.getRetrieveArrivalList(hotel,bookingid,fromdate,todate);
		
		return otabookingtrans;
		
	}
	
	@RequestMapping(value = "/otaretrievedeparturelist", method = RequestMethod.POST)
	public List<OTABookingTrans> getRetrieveDepartureList(@RequestParam String bookingid,
			@RequestParam String fromdate,@RequestParam String todate,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTABookingTrans> otabookingtrans = otabookingserviceimpl.getRetrieveDepartureList(hotel,bookingid,fromdate,todate);
		
		return otabookingtrans;
		
	}
	
	
	@RequestMapping(value = "/otachargepost", method = RequestMethod.POST)
	public String getChargePostList(
			@RequestParam String room,@RequestParam String folio,@RequestParam String table,@RequestParam String outlet,
			@RequestParam String charge,@RequestParam String postingdate,@RequestParam String trandate,
			@RequestParam String amount,@RequestParam String tax,@RequestParam String gross_amount,
			@RequestParam String voucherno,@RequestParam String posuser,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otabookingserviceimpl.getChargePostList(hotel,room,
				folio,table,outlet,charge,postingdate,trandate,amount,tax,gross_amount,voucherno,posuser);
		
		return response;
		
	}
	
	
	@RequestMapping(value = "/otachargeonroom", method = RequestMethod.POST)
	public String getChargeOnRoom(@RequestParam String requestid,HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otabookingserviceimpl.getChargeOnRoom(hotel, requestid);
		
		return response;
		
	}
	
	
	@RequestMapping(value = "/otaposreceiptno", method = RequestMethod.POST)
	public String getPosreceiptno(@RequestParam String requestid,@RequestParam String voucherno,HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otabookingserviceimpl.getPosreceiptno(hotel, requestid, voucherno);
		
		return response;
		
	}
	
	
	@RequestMapping(value = "/otaretrieveposttoroominformation", method = RequestMethod.GET)
	public List<OTARoomListDTO> getRetrieveposttoroominformation(HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTARoomListDTO> otaroomlistdto = otabookingserviceimpl.getRetrieveposttoroominformation(hotel);
		
		return otaroomlistdto;
		
	}
	
	
	@RequestMapping(value = "/otaretrieveposttoroominformationspecific", method = RequestMethod.POST)
	public List<OTARoomListDTO> getRetrieveposttoroominformationSpecific(@RequestParam String room,HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTARoomListDTO> otaroomlistdto = otabookingserviceimpl.getRetrieveposttoroominformationSpecific(hotel,room);
		
		return otaroomlistdto;
		
	}
	
	
	@RequestMapping(value = "/otaroomsalesdata", method = RequestMethod.POST)
	public List<OTARoomReportDTO> getRoomSalesData(@RequestParam ArrayList<String> roomid,
			@RequestParam String fromdate,@RequestParam String todate,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTARoomReportDTO> otaroomreport = otabookingserviceimpl.getRoomSalesData(hotel,roomid,fromdate,todate);
		
		return otaroomreport;
		
	}
	
	
	
	
	
	
}
