package com.indocosmo.pms.web.accountMaster.dao;

import java.util.Map;

import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.general.master.dao.MasterDao;

public interface AccmasterDAO extends MasterDao<AccountMaster> {

	public Map<Integer, String> getAccountType();

}
