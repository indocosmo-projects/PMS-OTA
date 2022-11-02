package com.indocosmo.pms.web.floor.dao;

import java.util.Map;

import com.indocosmo.pms.web.floor.model.Floor;
import com.indocosmo.pms.web.general.master.dao.MasterDao;

public interface FloorDAO extends  MasterDao<Floor>{
	
	public Map<Integer, String> getNameIdMap() throws Exception;
	public Floor canDelete(int id);
	public int getCountOfFloor(int id);
}
