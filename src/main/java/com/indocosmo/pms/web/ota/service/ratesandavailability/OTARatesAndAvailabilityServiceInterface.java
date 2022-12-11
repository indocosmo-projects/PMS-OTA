package com.indocosmo.pms.web.ota.service.ratesandavailability;

import java.util.List;

import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInventoryDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;

public interface OTARatesAndAvailabilityServiceInterface {
	
	public List<OTARoomInventoryDTO> getRetrieveRoomInventory(HotelInfo hotel, String fdate, String tdate);
	
	public String updateLinearRateinventory(HotelInfo hotel,OTARoomInventoryDTO otaroominv);
	
}
