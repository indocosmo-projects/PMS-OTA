package com.indocosmo.pms.web.tax.service;

import java.util.List;
import java.util.Map;

import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.tax.model.TaxHdr;

public interface TaxHdrService {
	public JqGridListWrapperDTO taxHdrList(String currentPage, String rowLimit, String pagingStart,
			Map<String, String> advanceSearchMap, String sortVal, Map<String, String> simpleSearchMap);

	public List<SystemSettings> getSystemSettingData() throws Exception;

	public TaxHdr getEditRecord(int id) throws Exception;

	public boolean save(TaxHdr tax) throws Exception;

	public void delete(int id, String table) throws Exception;

	public boolean isCodeExists(String code) throws Exception;

	public int getCount(Map<String, String> advanceSearchMap, Map<String, String> simpleSearchMap) throws Exception;

	public Map<Integer, String> getTaxMap() throws Exception;
}
