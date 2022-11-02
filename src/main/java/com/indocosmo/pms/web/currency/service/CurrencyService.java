package com.indocosmo.pms.web.currency.service;

import java.util.List;
import com.indocosmo.pms.web.general.master.service.MasterService;
import com.indocosmo.pms.web.currency.model.Currency;

public interface CurrencyService extends MasterService<Currency> {
	public String getCurrencySymbol() throws Exception;

	public List<Currency> getCurrencyList() throws Exception;

}
