package com.indocosmo.pms.web.ota.service.configuration;

import java.util.List;

import com.indocosmo.pms.web.ota.dto.hotel.HotelInfoDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTACurrencyDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTAPaymentGatewayDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTAPaymethodsDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomTypeDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.room.OTARoomRoomTypes;

public interface OTAConfigurationServiceInterface {

	public List<HotelInfo> getCheckhotelauthentication(String authkey);
	
	public List<HotelInfoDTO> getRetrievehotelinformation(String hotelcode, String authkey);
	
	public List<HotelInfoDTO> getRetrievehotelamenities(String hotelcode, String authkey);
	
	public List<OTARoomTypeDTO> getRetrieveroomtypes(String hotelcode, String authkey, String publishtoweb);
	
	public List<OTARoomTypeDTO> getRetrievesalutationsandcountry(String hotelcode, String authkey);
	
	public List<OTARoomTypeDTO> getRetrieveextrasratebasedonparameters(String hotelcode, String authkey, String checkindate,
			String checkoutdate, String extrachargeid, String extraitem);
	
	public List<OTARoomTypeDTO> getVerifytravelagent(String hotelcode, String authkey, String username, String password,
			String groupcode);
	
	public List<OTAPaymentGatewayDTO> getPaymentgateways(String hotelcode, String authkey);
	
	public List<OTACurrencyDTO> getRetrievecurrency(String hotelcode, String authkey);
	
	public List<OTAPaymethodsDTO> getRetrievepaymethods(String hotelcode, String authkey);
	
	public List<OTAPaymethodsDTO> getRetrieveidentitytype(String hotelcode, String authkey);
	
	public List<OTARoomRoomTypes> getRetrieveroomlist(String hotelcode, String authkey,String fromdate, String todate);
	
	
}
