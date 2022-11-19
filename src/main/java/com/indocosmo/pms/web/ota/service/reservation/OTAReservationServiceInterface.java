package com.indocosmo.pms.web.ota.service.reservation;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.ota.dto.hotel.HotelInfoDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTAReservationDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomDetailsDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInfoDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInventoryDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.reservation.OTACancelReservation;
import com.indocosmo.pms.web.ota.entity.reservation.OTARentalInfo;
import com.indocosmo.pms.web.ota.entity.reservation.OTATaxDeatil;
import com.indocosmo.pms.web.ota.entity.room.OTARoomRatePlans;
import com.indocosmo.pms.web.ota.entity.room.OTARoomRateTypes;
import com.indocosmo.pms.web.ota.entity.room.OTARoomRoomTypes;

public interface OTAReservationServiceInterface {
	
	public OTAReservationDTO getRetrieveAll(HotelInfo hotel) throws Exception;
	
	public OTAReservationDTO getRetrieveAllNewReservation(HotelInfo hotel) throws Exception;
	
	public HotelInfoDTO getBookingReceived(HotelInfo hotel,String BookingId,String PMS_BookingId,String Status);
	
	public OTAReservationDTO tolistBookingTrans(JsonArray BookingTransarr, int reservationId);
	
	public List<OTARentalInfo> tolistRentalInfo(JsonArray otarentalinfoarr, int reservationId);
	
	public List<OTATaxDeatil> tolistTaxDeatil(JsonArray otabooking, int reservationId);
	
	public List<OTACancelReservation> tolistCancelReservation(JsonArray cancelReservation);
	
	public OTAReservationDTO getRetrieveAllFromDB(HotelInfo hotel);
	
	public OTARoomInfoDTO getRoomInformation(HotelInfo hotel,int roomrequired);
	
	public OTAReservationDTO getInventory(HotelInfo hotel);
	
	public OTAReservationDTO getBookingId();
	
	public List<OTARoomRoomTypes> toListRoomTypes(JsonObject jobjroomtypes);
	
	public List<OTARoomRateTypes> toListRateTypes(JsonObject jobjratetypes);
	
	public List<OTARoomRatePlans> toListRatePlans(JsonObject jobjrateplans);
	
	public OTARoomInfoDTO getRoomInformationFromDB();
	
	public List<OTARoomDetailsDTO> getOtareservationSingleroom(HotelInfo hotel, int reservationid);
	
	public List<OTARoomInventoryDTO> getRetrieveRoomInventory(HotelInfo hotel);
	
}
