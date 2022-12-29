package com.indocosmo.pms.web.ota.service.ratesandavailability;

import java.util.List;

import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInfoDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInventoryDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;

public interface OTARatesAndAvailabilityServiceInterface {
	
	public String getUpdateRoomInventory(HotelInfo hotel,OTARoomInventoryDTO otaroominv);
	
	public String updateLinearRateinventory(HotelInfo hotel,OTARoomInventoryDTO otaroominv);
	
	public String getUpdateNonLinearRate(HotelInfo hotel,OTARoomInventoryDTO otaroominv);
	
	public OTARoomInfoDTO getRetrieveroomratessourcedetails(HotelInfo hotel) ;
	
	public String getupdatemaxnights(HotelInfo hotel, String rateplanid, String fromdate, String todate,String maxnight);
	
	public String getupdateminnights(HotelInfo hotel, String rateplanid, String fromdate, String todate,String minnight);
	
	public String getupdatestopsell(HotelInfo hotel, String rateplanid, String fromdate, String todate,String stopsell);
	
	public String getupdatecoa(HotelInfo hotel, String rateplanid, String fromdate, String todate,String coa);
	
	public String getupdatecod(HotelInfo hotel, String rateplanid, String fromdate, String todate,String cod);
	
	public List<OTARoomInventoryDTO> getRetrieveRoomInventory(HotelInfo hotel, String fdate, String tdate);
	
	public List<OTARoomInventoryDTO> getRetrieveroomrates(HotelInfo hotel, String fromdate, String todate);
	
	
}
