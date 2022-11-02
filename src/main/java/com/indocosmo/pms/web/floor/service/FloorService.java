package com.indocosmo.pms.web.floor.service;

import java.util.Map;

import com.indocosmo.pms.web.floor.model.Floor;
import com.indocosmo.pms.web.general.master.service.MasterService;

public interface FloorService extends MasterService<Floor>{
	public Map<Integer, String> getNameIdMap() throws Exception;
	public Floor canDelete(int id);
	public int getCountOfFloor(int id);
	
}
