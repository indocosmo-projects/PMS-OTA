package com.indocosmo.pms.web.rackrate.service;

import java.util.List;
import java.util.Map;

import com.indocosmo.pms.web.common.model.RateHdr;
import com.indocosmo.pms.web.general.master.service.MasterService;

public interface RackRateService extends MasterService<RateHdr> {
	public Map<Integer, String> getRatePeriods() throws Exception;

	public List<Object[]> getRackRates(int roomTypeId) throws Exception;
}
