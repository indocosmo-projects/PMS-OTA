package com.indocosmo.pms.web.roomType.service;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.indocosmo.pms.web.roomType.model.RoomType;

public interface RoomTypeService {
	public List<RoomType> getRoomTypeLists();

	public boolean save(RoomType newrType) throws Exception;

	public RoomType getAvailRooms(int id) throws Exception;

	public boolean isCodeExists(String code);

	public boolean delete(int id);

	public JsonObject getRoomTypeImages(int id);

	public Map<Integer, String> getRoomTypeList();

	public RoomType getRecord(int id) throws Exception;

	public int canDelete(int id);

}
