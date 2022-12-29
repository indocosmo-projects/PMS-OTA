package com.indocosmo.pms.web.ota.service.booking;

import java.util.ArrayList;
import java.util.List;

import com.indocosmo.pms.web.ota.dto.booking.OTABookingDTO;
import com.indocosmo.pms.web.ota.dto.booking.OTARoomListDTO;
import com.indocosmo.pms.web.ota.dto.booking.OTARoomReportDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.others.OTAGuestStatics;
import com.indocosmo.pms.web.ota.entity.reservation.OTABookingTrans;
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
	
	public List<OTARoomReportDTO> getRoomSalesData(HotelInfo hotel, ArrayList<String> roomid, String fromdate, String todate);
	
	
	
}
