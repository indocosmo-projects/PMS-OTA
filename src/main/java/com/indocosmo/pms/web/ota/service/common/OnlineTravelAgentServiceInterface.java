package com.indocosmo.pms.web.ota.service.common;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import com.google.gson.JsonObject;
import com.indocosmo.pms.web.ota.dto.hotel.HotelInfoDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;

public interface OnlineTravelAgentServiceInterface {
	
	public  JSONObject getUrlContents(String url, String xmldata);
	
	public JsonObject Post_JSON( String query_url,String json);
	
	public JsonObject  Post_JSON_Header(String query_url,String json, HotelInfo hotel);
	
	public ResponseEntity<String> getUpdateRoomRates();
	
}
