package com.indocosmo.pms.web.dashboard.service;

import java.util.List;

import com.google.gson.JsonObject;
import com.indocosmo.pms.web.dashboard.model.RoomDetails;

public interface DashboardService {
	public JsonObject getDetails() throws Exception;

	public JsonObject getListData() throws Exception;

	public List<RoomDetails> getRoomDetails(int roomType, int floor) throws Exception;

	

}
