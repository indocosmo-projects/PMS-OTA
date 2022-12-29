package com.indocosmo.pms.web.ota.service.ota_rms;

import com.indocosmo.pms.web.ota.dto.reservation.OTAPushReservationDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInfoDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInventoryDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;

public interface OTAOTA_RmsServiceInterface {
	
	public OTARoomInfoDTO getRequestroominformation(HotelInfo hotel);
	
	public String getPushinventory(HotelInfo hotel,OTARoomInventoryDTO otaroominv);

	public String getPushlinearrates(HotelInfo hotel, OTARoomInventoryDTO otaroominv);
	
	public String getPushNonlinearrates(HotelInfo hotel, OTARoomInventoryDTO otaroominv);
	
	public String getPushminimumnights(HotelInfo hotel, OTARoomInventoryDTO otaroominv);
	
	public String getPushstopsell(HotelInfo hotel, OTARoomInventoryDTO otaroominv);
	
	public String getPushcloseonarrival(HotelInfo hotel, OTARoomInventoryDTO otaroominv);
	
	public String getPushcloseondeparture(HotelInfo hotel, OTARoomInventoryDTO otaroominv);
	
	public String getBookingstoezee(HotelInfo hotel, OTAPushReservationDTO otapushreservationdto);
	
}
