package com.indocosmo.pms.web.currency.dao;

import java.util.List;

import com.indocosmo.pms.web.currency.model.Currency;
import com.indocosmo.pms.web.general.master.dao.MasterDao;

public interface CurrencyDAO extends MasterDao<Currency> {
	public String getCurrencySymbol() throws Exception;

	public List<Currency> getCurrencyList() throws Exception;

}
