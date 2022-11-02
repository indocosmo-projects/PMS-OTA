package com.indocosmo.pms.web.housekeeping.dao;

public interface HKDao {
	public boolean updateHk(int roomid, String roomNum, int hkStatus) throws Exception;
}
