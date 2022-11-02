package com.indocosmo.pms.web.ota.service.common;

import org.springframework.http.ResponseEntity;

import com.google.gson.JsonObject;
import com.indocosmo.pms.web.ota.dto.hotel.HotelInfoDTO;

public interface OnlineTravelAgentServiceInterface {
	
	public ResponseEntity<String> getUrlContents(String url, String xmldata);
	
	public JsonObject Post_JSON( String query_url,String json);
	
	public ResponseEntity<String> getHotelList();
	
	public ResponseEntity<String> getHotelDetails();
	
	public ResponseEntity<String> getUpdateRoomRates();
	
	public ResponseEntity<String> getInventory();
	
}
