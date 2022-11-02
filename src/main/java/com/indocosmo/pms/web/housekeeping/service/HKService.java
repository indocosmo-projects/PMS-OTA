package com.indocosmo.pms.web.housekeeping.service;

public interface HKService {
	public boolean updateHk(int roomid, String roomNum, int hkStatus) throws Exception;
}
