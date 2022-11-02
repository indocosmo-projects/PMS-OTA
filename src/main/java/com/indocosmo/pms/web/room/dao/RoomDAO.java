package com.indocosmo.pms.web.room.dao;

import java.util.List;
import java.util.Map;

import com.indocosmo.pms.web.floor.model.Floor;
import com.indocosmo.pms.web.room.model.Room;
import com.indocosmo.pms.web.room.model.RoomFeature;

public interface RoomDAO {
	public boolean save(Room room, boolean newRecord) throws Exception;

	public boolean delete(int id) throws Exception;

	public Room getRecord(int id) throws Exception;
	
	public Room getRecord(String id) throws Exception;

	public List<Room> list(int startLimit, int endLimit, Map<String, String> advanceSearch, String sortVal,
			Map<String, String> simpleSearchMap) throws Exception;

	public int getCount(Map<String, String> searchContent, Map<String, String> simpleSearchMap) throws Exception;

	public boolean roomNumExist(String roomNo) throws Exception;

	public List<RoomFeature> getRoomFeatures() throws Exception;

	public List<RoomFeature> getRoomFeatures(String features) throws Exception;

	public boolean featureExist(RoomFeature roomFeature) throws Exception;

	public boolean addFeature(RoomFeature roomFeature) throws Exception;

	public boolean deleteFeature(String id) throws Exception;

	public List<Floor> getFloor() throws Exception;

}
