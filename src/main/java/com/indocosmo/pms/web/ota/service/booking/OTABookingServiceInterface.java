package com.indocosmo.pms.web.ota.service.booking;

import java.util.ArrayList;
import java.util.List;

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

public interface OTABookingServiceInterface {
	
	public List<OTABookingDTO> getCheckAvailability(HotelInfo hotel,String checkindate,String checkoutdate,
			String nights,String adults,String child,String rooms,String roomtypeid);
	
	public List<OTAReservation> getSingleBooking(HotelInfo hotel,String bookingid);
	
	public List<OTABookingTrans> getRetrieveArrivalList(HotelInfo hotel,String bookingid,String fromdate,String todate);
	
	public List<OTABookingTrans> getRetrieveDepartureList(HotelInfo hotel,String bookingid,String fromdate, String todate);
	
	public String getChargePostList(HotelInfo hotel, String room, String folio,
			String table, String outlet, String charge, String postingdate, String trandate, String amount, String tax,
			String gross_amount, String voucherno, String posuser);
	
	public String getChargeOnRoom(HotelInfo hotel, String requestid);
	
	public String getPosreceiptno(HotelInfo hotel, String requestid, String voucherno);
	
	public List<OTARoomListDTO> getRetrieveposttoroominformation(HotelInfo hotel);
	
	public List<OTARoomListDTO> getRetrieveposttoroominformationSpecific(HotelInfo hotel,String room);
	
	public String getRoomSalesData(HotelInfo hotel, ArrayList<String> roomid, String fromdate, String todate);
	
	public List<OTACalendarDTO> getReservedroomscalendar(HotelInfo hotel, ArrayList<String> roomid, ArrayList<String> roomcode,String fromdate, String todate);
	
	public List<OTACalendarDTO> getRetrievephysicalrooms(HotelInfo hotel);
	
	public List<OTACalendarDTO> getCheckincheckout(HotelInfo hotel);
	
	public List<OTACalendarDTO> getReservationdetailsofaroom(HotelInfo hotel, String reservation_id);
	
	public List<OTAReservation> getPullhistoricalbookings(HotelInfo hotel, String fromdate, String todate);
	
	public String getCreatebookings(HotelInfo hotel,String rateplanid,
			String ratetypeid, String roomtypeid ,String baserate,
			String extradultrate ,String extrachildrate,String numberadults ,String numberchildren,
			String extrachildage, String title,String firstname ,String lastname ,String gender,
			String specialrequest, String checkindate,String checkoutdate ,String bookingpaymentmode,
			String emailaddress, String sourceid , String mobileno ,String address,String state ,String country ,
			String city,String zipcode,String fax,String device,String languagekey,String paymenttypeunkid);
	
	
	public String getpostcreatebookingsactions(HotelInfo hotel);
	
	public List<OTABookingTrans> getRetrieveabookingbasedonparameters(HotelInfo hotel, String fromdate,  String todate, String email);
	
	public List<OTABookingTrans> getReadabooking(HotelInfo hotel, String reservationnumber);
	
	public String getCancelabooking(HotelInfo hotel, String reservationnumber);
	
	public String getAutosync(HotelInfo hotel, String reservationnumber);
	
	public String getGuestdataupdate(HotelInfo hotel,String reservationid, String firstname, String lastname, String email);
	
	public String getAddPayment(HotelInfo hotel, String reservationid, String paymentid, String currencyid, String payment);
	
	public String getGuestProfile(HotelInfo hotel, String reservationid, String firstname, String lastname, String gender, String type);
	
	public String getGuestCheckIn(HotelInfo hotel, String reservationid, String guestname, String email, String address, String phone,String mobile);
	
	public String getRoomAssignment(HotelInfo hotel, String reservationid, String roomtypeid, String roomid);
	
	public String guestCheckOut(HotelInfo hotel, String reservationid);
	
	public  List<OTAFolioDTO> retrievelistofbills(HotelInfo hotel, String reservationid);
	
	public List<OTABookingTrans> retrievertransactiondetails(HotelInfo hotel, String tranunkid);

	
}
