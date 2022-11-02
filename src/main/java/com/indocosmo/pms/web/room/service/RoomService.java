package com.indocosmo.pms.web.room.service;

import java.util.List;
import java.util.Map;

import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;
import com.indocosmo.pms.web.floor.model.Floor;
import com.indocosmo.pms.web.room.model.Room;
import com.indocosmo.pms.web.room.model.RoomFeature;

public interface RoomService {
	public boolean save(Room room, boolean newRecord) throws Exception;

	public boolean delete(int id) throws Exception;

	public Room getRecord(String roomId) throws Exception;

	public JqGridListWrapperDTO list(String currentPage, String rowLimit, String pagingStart,
			Map<String, String> advanceSearchMap, String sortVal, Map<String, String> simpleSearchMap) throws Exception;

	public int getCount(Map<String, String> searchContent, Map<String, String> simpleSearchMap) throws Exception;

	public boolean roomNumExist(String roomNo) throws Exception;

	public List<RoomFeature> getRoomFeatures() throws Exception;

	public boolean featureExist(RoomFeature roomFeature) throws Exception;

	public boolean addFeature(RoomFeature roomFeature) throws Exception;

	public boolean deleteFeature(String id) throws Exception;

	public List<Floor> getFloor() throws Exception;
}
