package com.indocosmo.pms.web.ota.service.reservation;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.ota.dto.hotel.HotelInfoDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTAReservationDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.reservation.OTABookingTrans;
import com.indocosmo.pms.web.ota.entity.reservation.OTACancelReservation;
import com.indocosmo.pms.web.ota.entity.reservation.OTARentalInfo;
import com.indocosmo.pms.web.ota.entity.reservation.OTAReservation;
import com.indocosmo.pms.web.ota.entity.reservation.OTATaxDeatil;

public interface OTAReservationServiceInterface {
	
	public OTAReservationDTO getRetrieveAll(HotelInfo hotel) throws Exception;
	
	public HotelInfoDTO getBookingReceived(HotelInfo hotel,String BookingId,String PMS_BookingId,String Status);
	
	public OTAReservationDTO tolistBookingTrans(JsonArray BookingTransarr, int reservationId);
	
	public List<OTARentalInfo> tolistRentalInfo(JsonArray otarentalinfoarr, int reservationId);
	
	public List<OTATaxDeatil> tolistTaxDeatil(JsonArray otabooking, int reservationId);
	
	public List<OTACancelReservation> tolistCancelReservation(JsonArray cancelReservation);
	
	public OTAReservationDTO getRetrieveAllFromDB(HotelInfo hotel);
	
	public OTAReservationDTO getRoomInformation(HotelInfo hotel);
	
}
