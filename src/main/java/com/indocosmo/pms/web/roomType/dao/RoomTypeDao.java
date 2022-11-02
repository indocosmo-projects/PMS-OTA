package com.indocosmo.pms.web.roomType.dao;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.indocosmo.pms.web.roomType.model.RoomType;

public interface RoomTypeDao {
	public List<RoomType> getRoomTypeLists();

	public boolean save(RoomType rType) throws Exception;

	public boolean delete(int id);

	public boolean isCodeExists(String code);

	public RoomType getAvailRooms(int id) throws Exception;

	public JsonObject getRoomTypeImages(int id);

	public Map<Integer, String> getRoomTypeList();

	public RoomType getRecord(int id) throws Exception;

	public int getCount(int id);
}
