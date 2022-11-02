package com.indocosmo.pms.web.housekeeping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.indocosmo.pms.web.housekeeping.dao.HKDao;

@Service
public class HKServiceImpl implements HKService {

	@Autowired
	HKDao hkDao;

	@Transactional
	public boolean updateHk(int roomid, String roomNum, int hkStatus) throws Exception {
		return hkDao.updateHk(roomid, roomNum, hkStatus);
	}

}
