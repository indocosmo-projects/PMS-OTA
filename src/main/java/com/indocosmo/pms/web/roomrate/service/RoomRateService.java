package com.indocosmo.pms.web.roomrate.service;

import java.util.Map;

import com.indocosmo.pms.web.common.model.RateHdr;
import com.indocosmo.pms.web.general.master.service.MasterService;
import com.indocosmo.pms.web.roomType.model.RoomType;

public interface RoomRateService extends MasterService<RateHdr> {
	public Map<Integer, String> list() throws Exception;

	public Map<Integer, String> roomTypeListMap() throws Exception;

	public Map<Integer, String> departmentTypeListMap() throws Exception;

	public RoomType roomDetails(int roomId) throws Exception;
}
