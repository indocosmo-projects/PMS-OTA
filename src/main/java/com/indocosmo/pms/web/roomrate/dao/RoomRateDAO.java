package com.indocosmo.pms.web.roomrate.dao;

import java.util.Map;

import com.indocosmo.pms.web.common.model.RateHdr;
import com.indocosmo.pms.web.general.master.dao.MasterDao;
import com.indocosmo.pms.web.roomType.model.RoomType;

public interface RoomRateDAO extends MasterDao<RateHdr> {
	public Map<Integer, String> list() throws Exception;

	public Map<Integer, String> roomTypeListMap() throws Exception;

	public RoomType roomDetails(int roomId) throws Exception;

	public Map<Integer, String> departmentTypeListMap() throws Exception;
}
