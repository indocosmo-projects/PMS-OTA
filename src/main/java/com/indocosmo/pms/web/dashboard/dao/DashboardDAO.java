package com.indocosmo.pms.web.dashboard.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.dashboard.model.RoomDetails;

public interface DashboardDAO {

	public JsonObject getDataForDashBoard() throws Exception;

	public JsonArray getLastReservationData(String wherePart) throws Exception;

	public List<RoomDetails> getRoomDetails(int roomType, int floor) throws Exception;

	
}
