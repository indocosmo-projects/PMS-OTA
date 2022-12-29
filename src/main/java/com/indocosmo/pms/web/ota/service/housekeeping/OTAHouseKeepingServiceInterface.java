package com.indocosmo.pms.web.ota.service.housekeeping;

import java.util.List;

import com.indocosmo.pms.web.ota.dto.housekeeping.OTAHouseStatusDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInventoryDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.housekeeping.OTARoomList;

public interface OTAHouseKeepingServiceInterface {
	
	public OTAHouseStatusDTO getRetrieveinhouseroomstatus(HotelInfo hotel);
	
	public OTAHouseStatusDTO getRetrieveinhouseroomstatusDB();
	
	public String getUpdateroomstatus(HotelInfo hotel,String roomid,String unitid,String hkstatus,String hkremarks);
	
	public String getBlockroom(HotelInfo hotel,String roomid,String roomtypeid,String fromdate,String todate,String reason);
	
	public String getUnblockroom(HotelInfo hotel,String roomid,String roomtypeid,String fromdate,String todate);
	
	public List<OTARoomList> getRoomidDB();
}
