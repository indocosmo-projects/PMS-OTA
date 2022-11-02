package com.indocosmo.pms.web.accountMaster.service;

import java.util.Map;

import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.general.master.service.MasterService;

public interface AccountmasterService extends MasterService<AccountMaster> {

	public Map<Integer, String> getAccountType() throws Exception;

}
