package com.indocosmo.pms.web.systemSettings.service;

import java.util.List;
import java.util.Map;

import com.indocosmo.pms.web.common.model.FinancialYear;
import com.indocosmo.pms.web.common.model.RatePeriod;
import com.indocosmo.pms.web.systemSettings.model.Company;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;

public interface SystemSettingsService {

	public SystemSettings getSystemSettings() throws Exception;

	public Company getcompany() throws Exception;

	public boolean save(SystemSettings systemSettings, FinancialYear financialYear, List<Company> company)
			throws Exception;

	public List<RatePeriod> getRatePeriods() throws Exception;

	public List<SystemSettings> list();

	public SystemSettings getDateFormat(int systemSettingsId) throws Exception;

	public RatePeriod getRatePeriod(int id) throws Exception;

	public double getRoundingRule();

	public Map<Integer, String> getBillRoundings();

	public boolean delete(int id);

	public int selectid(String code) throws Exception;

	public int getCount(int id);

	public List<FinancialYear> getFinancialYears() throws Exception;
}
