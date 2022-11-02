package com.indocosmo.pms.web.roomType.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;
import com.indocosmo.pms.web.roomType.dao.RoomTypeDao;
import com.indocosmo.pms.web.roomType.model.RoomType;

@Service
@Transactional
public class RoomTypeServiceImpl implements RoomTypeService {

	@Autowired
	RoomTypeDao roomtypedao;

	@Transactional
	public List<RoomType> getRoomTypeLists() {
		// TODO Auto-generated method stub
		return roomtypedao.getRoomTypeLists();
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean save(RoomType rType) throws Exception {
		// TODO Auto-generated method stub
		return roomtypedao.save(rType);
	}

	@Transactional
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return roomtypedao.delete(id);
	}

	@Transactional
	public boolean isCodeExists(String code) {
		// TODO Auto-generated method stub
		return roomtypedao.isCodeExists(code);
	}

	@Transactional
	public RoomType getAvailRooms(int id) throws Exception {
		// TODO Auto-generated method stub
		return roomtypedao.getAvailRooms(id);
	}

	@Transactional
	public JsonObject getRoomTypeImages(int id) {
		return roomtypedao.getRoomTypeImages(id);
	}

	@Transactional
	public Map<Integer, String> getRoomTypeList() {
		return roomtypedao.getRoomTypeList();
	}

	@Transactional
	public RoomType getRecord(int id) throws Exception {
		return roomtypedao.getRecord(id);
	}

	public int canDelete(int id) {
		int isCount = roomtypedao.getCount(id);
		return isCount;

	}
}