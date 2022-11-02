package com.indocosmo.pms.web.tax.dao;

import java.util.List;
import java.util.Map;

import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.tax.model.TaxHdr;

public interface TaxHdrDao {
	public List<TaxHdr> taxHdrList(int startLimit, int endLimit, Map<String, String> advanceSearhMap, String sortVal,
			Map<String, String> simpleSearchMap) throws Exception;

	public int totalRecord(Map<String, String> advanceSearchMap, Map<String, String> simpleSearchMap) throws Exception;

	public List<SystemSettings> getSystemSettingData() throws Exception;

	public TaxHdr getEditRecord(int id) throws Exception;

	public boolean save(TaxHdr taxHdrForm) throws Exception;

	public void delete(int id, String table) throws Exception;

	public boolean isCodeExists(String code) throws Exception;

	public Map<Integer, String> getTaxMap();
}
