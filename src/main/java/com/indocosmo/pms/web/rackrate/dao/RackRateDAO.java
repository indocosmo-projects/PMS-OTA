package com.indocosmo.pms.web.rackrate.dao;

import java.util.List;
import java.util.Map;

import com.indocosmo.pms.web.common.model.RateHdr;
import com.indocosmo.pms.web.general.master.dao.MasterDao;

public interface RackRateDAO extends MasterDao<RateHdr> {
	public Map<Integer, String> getRatePeriods() throws Exception;

	public List<Object[]> getRackRates(int roomTypeId) throws Exception;
}
