package com.indocosmo.pms.web.request.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.checkIn.model.CheckInRequest;
import com.indocosmo.pms.web.checkIn.model.CheckInRequestStatus;

public interface RequestService {

	public JsonArray getRoomLists();
	public JsonArray getFacilities();
	public boolean saveAdd(List<CheckInRequest> chreqlist)throws Exception;
	public boolean delete(int id);
	public boolean update(CheckInRequestStatus checkInRequestStatus) throws Exception;
	public JsonObject getSearchData(JsonObject jobj, Map<String, String> searchMap) throws Exception;
	public String isRequestExist(int checkinnumber, int facilityid, String requstDate, String reqTime)throws Exception;

}
