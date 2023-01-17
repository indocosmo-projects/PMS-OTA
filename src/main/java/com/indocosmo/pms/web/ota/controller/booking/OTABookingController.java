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
import com.indocosmo.pms.web.ota.dto.booking.OTACalendarDTO;
import com.indocosmo.pms.web.ota.dto.booking.OTAFolioDTO;
import com.indocosmo.pms.web.ota.dto.booking.OTARoomListDTO;
import com.indocosmo.pms.web.ota.dto.booking.OTARoomReportDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.others.OTAGuestStatics;
import com.indocosmo.pms.web.ota.entity.reservation.OTABookingTrans;
import com.indocosmo.pms.web.ota.entity.reservation.OTACancelReservation;
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
	public String getRoomSalesData(@RequestParam ArrayList<String> roomid,
			@RequestParam String fromdate,@RequestParam String todate,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otabookingserviceimpl.getRoomSalesData(hotel,roomid,fromdate,todate);
		
		return response;
		
	}
	
	
	@RequestMapping(value = "/otareservedroomscalendar", method = RequestMethod.POST)
	public List<OTACalendarDTO> getReservedroomscalendar(@RequestParam ArrayList<String> roomid,
			@RequestParam ArrayList<String> roomcode,@RequestParam String fromdate,@RequestParam String todate,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTACalendarDTO> otacalendarDTO = otabookingserviceimpl.getReservedroomscalendar(hotel,roomid,roomcode,fromdate,todate);
		
		return otacalendarDTO;
		
	}
	
	

	@RequestMapping(value = "/otaretrievephysicalrooms", method = RequestMethod.GET)
	public List<OTACalendarDTO> getRetrievephysicalrooms(HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTACalendarDTO> otacalendarDTO = otabookingserviceimpl.getRetrievephysicalrooms(hotel);
		
		return otacalendarDTO;
		
	}
	
	
	
	@RequestMapping(value = "/otacheckincheckout", method = RequestMethod.GET)
	public List<OTACalendarDTO> getCheckincheckout(HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTACalendarDTO> otacalendarDTO = otabookingserviceimpl.getCheckincheckout(hotel);
		
		return otacalendarDTO;
		
	}
	
	
	@RequestMapping(value = "/otareservationdetailsofaroom", method = RequestMethod.POST)
	public List<OTACalendarDTO> getReservationdetailsofaroom(@RequestParam String reservation_id,HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTACalendarDTO> otacalendarDTO = otabookingserviceimpl.getReservationdetailsofaroom(hotel,reservation_id);
		
		return otacalendarDTO;
		
	}
	
	
	@RequestMapping(value = "/otapullhistoricalbookings", method = RequestMethod.POST)
	public List<OTAReservation> getPullhistoricalbookings(@RequestParam String fromdate,
			@RequestParam String todate,HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTAReservation> otareservationlist = otabookingserviceimpl.getPullhistoricalbookings(hotel,fromdate,todate);
		
		return otareservationlist;
		
	}
	
	
	@RequestMapping(value = "/otacreateabooking", method = RequestMethod.POST)
	public String getCreatebookings(
			@RequestParam String rateplanid,
			@RequestParam String ratetypeid, @RequestParam String roomtypeid , @RequestParam String baserate,
			@RequestParam String extradultrate ,@RequestParam String extrachildrate,@RequestParam String numberadults ,@RequestParam String numberchildren,
			@RequestParam String extrachildage,@RequestParam String title,@RequestParam String firstname ,@RequestParam String lastname ,@RequestParam String gender,
			@RequestParam String specialrequest,@RequestParam String checkindate,@RequestParam String checkoutdate ,@RequestParam String bookingpaymentmode,
			@RequestParam String emailaddress,@RequestParam String sourceid ,@RequestParam String mobileno ,@RequestParam String address,@RequestParam String state ,@RequestParam String country ,
			@RequestParam String city,@RequestParam String zipcode,@RequestParam String fax,@RequestParam String device,@RequestParam String languagekey,@RequestParam String paymenttypeunkid,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otabookingserviceimpl.getCreatebookings(hotel,rateplanid,ratetypeid, roomtypeid,baserate, extradultrate ,extrachildrate,numberadults ,numberchildren, extrachildage,
				title, firstname ,lastname ,gender,specialrequest,checkindate,checkoutdate ,bookingpaymentmode,emailaddress,sourceid ,mobileno , address, state , country ,
		    	city,zipcode,fax,device,languagekey, paymenttypeunkid);
		
		return response;
		
	}
	
	
	
	@RequestMapping(value = "/otapostcreatebookingsactions", method = RequestMethod.GET)
	public String getpostcreatebookingsactions(HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otabookingserviceimpl.getpostcreatebookingsactions(hotel);
		
		return response;
		
	}
	
	
	
	@RequestMapping(value = "/otaretrieveabookingbasedonparameters", method = RequestMethod.POST)
	public List<OTABookingTrans> getRetrieveabookingbasedonparameters(@RequestParam String fromdate,@RequestParam String todate,@RequestParam String email, HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTABookingTrans> otabookingtrans = otabookingserviceimpl.getRetrieveabookingbasedonparameters(hotel, fromdate, todate, email);
		
		return otabookingtrans;
		
	}
	
	
	@RequestMapping(value = "/otareadabooking", method = RequestMethod.POST)
	public List<OTABookingTrans> getReadabooking(@RequestParam String reservationnumber, HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTABookingTrans> otabookingtrans = otabookingserviceimpl.getReadabooking(hotel, reservationnumber);
		
		return otabookingtrans;
		
	}
	
	
	@RequestMapping(value = "/otacancelabooking", method = RequestMethod.POST)
	public String getCancelabooking(@RequestParam String reservationnumber, HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otabookingserviceimpl.getCancelabooking(hotel, reservationnumber);
		
		return response;
		
	}
	
	
	@RequestMapping(value = "/otaautosync", method = RequestMethod.POST)
	public String getAutosync(@RequestParam String reservationnumber, HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otabookingserviceimpl.getAutosync(hotel, reservationnumber);
		
		return response;
	}
	
	
	@RequestMapping(value = "/otaguestdataupdate", method = RequestMethod.POST)
	public String getGuestdataupdate(@RequestParam String reservationid,@RequestParam String firstname,
			@RequestParam String lastname,@RequestParam String email,HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otabookingserviceimpl.getGuestdataupdate(hotel, reservationid, firstname, lastname, email);
		
		return response;
	}
	
	@RequestMapping(value = "/otaaddpayment", method = RequestMethod.POST)
	public String getAddPayment(@RequestParam String reservationid,@RequestParam String paymentid,
			@RequestParam String currencyid,@RequestParam String payment, HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otabookingserviceimpl.getAddPayment(hotel, reservationid, paymentid, currencyid, payment);
		
		return response;
	}
	
	
	@RequestMapping(value = "/otaguestprofile", method = RequestMethod.POST)
	public String getGuestProfile(@RequestParam String reservationid,@RequestParam String firstname,
			@RequestParam String lastname,@RequestParam String gender,@RequestParam String type, HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otabookingserviceimpl.getGuestProfile(hotel, reservationid, firstname, lastname, gender, type);
		
		return response;
	}
	
	@RequestMapping(value = "/otaguestcheckIn", method = RequestMethod.POST)
	public String getGuestCheckIn(@RequestParam String reservationid,@RequestParam String guestname,
			@RequestParam String email,@RequestParam String address,@RequestParam String phone,
			@RequestParam String mobile, HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otabookingserviceimpl.getGuestCheckIn(hotel, reservationid, guestname, email, address, phone, mobile);
		
		return response;
	}
	
	
	@RequestMapping(value = "/otaroomassignment", method = RequestMethod.POST)
	public String getRoomAssignment(@RequestParam String reservationid,@RequestParam String roomtypeid,
			@RequestParam String roomid, HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otabookingserviceimpl.getRoomAssignment(hotel, reservationid, roomtypeid, roomid) ;
		
		return response;
	}
	
	
	@RequestMapping(value = "/otaguestcheckout", method = RequestMethod.POST)
	public String guestCheckOut(@RequestParam String reservationid, HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		String response = otabookingserviceimpl.guestCheckOut(hotel, reservationid) ;
		
		return response;
	}
	
	
	@RequestMapping(value = "/otaretrievelistofbills", method = RequestMethod.POST)
	public  List<OTAFolioDTO> retrievelistofbills(@RequestParam String reservationid, HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		 List<OTAFolioDTO> otafoliodtoList = otabookingserviceimpl.retrievelistofbills(hotel, reservationid) ;
		
		return otafoliodtoList;
	}
	
	
	@RequestMapping(value = "/otaretrievertransactiondetails", method = RequestMethod.POST)
	public List<OTABookingTrans> retrievertransactiondetails(@RequestParam String tranunkid, HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTABookingTrans> otabookingtransList = otabookingserviceimpl.retrievertransactiondetails(hotel, tranunkid) ;
		
		return otabookingtransList;
	}
	
	
	
	
	
}
