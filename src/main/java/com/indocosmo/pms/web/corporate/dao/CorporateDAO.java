package com.indocosmo.pms.web.corporate.dao;

import java.util.List;
import com.indocosmo.pms.web.corporate.model.Corporate;
import com.indocosmo.pms.web.general.master.dao.MasterDao;

public interface CorporateDAO extends MasterDao<Corporate>{
	
	public List<Corporate> getCorporates(String keyWord) throws Exception;
	public List<Corporate> listOfCorporate(int id, String name);
	
}
