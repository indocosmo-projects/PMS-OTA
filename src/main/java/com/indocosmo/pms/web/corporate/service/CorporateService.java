package com.indocosmo.pms.web.corporate.service;

import java.util.List;

import com.indocosmo.pms.web.corporate.model.Corporate;
import com.indocosmo.pms.web.general.master.service.MasterService;

public interface CorporateService extends MasterService<Corporate>{
	public List<Corporate> getCorporates(String keyWord) throws Exception;
	public List<Corporate> listOfCorporate(int id, String name);
}
