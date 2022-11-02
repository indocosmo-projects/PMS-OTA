package com.indocosmo.pms.web.systemSettings.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.indocosmo.pms.web.common.model.FinancialYear;
import com.indocosmo.pms.web.common.model.RatePeriod;
import com.indocosmo.pms.web.systemSettings.dao.SystemSettingsDao;
import com.indocosmo.pms.web.systemSettings.model.Company;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;

@Service
@Transactional
public class SystemSettingsServiceImpl implements SystemSettingsService {

	@Autowired
	private SystemSettingsDao systemSettingsDao;

	public static final Logger logger = LoggerFactory.getLogger(SystemSettingsServiceImpl.class);

	/**
	 * get the system settings record from system_settings table
	 */

	public SystemSettings getSystemSettings() throws Exception {
		return systemSettingsDao.getSystemSettings();
	}

	public Company getcompany() throws Exception {
		return systemSettingsDao.getcompany();
	}

	/**
	 * get all the rate periods from rate_period table
	 */
	public List<RatePeriod> getRatePeriods() throws Exception {
		return systemSettingsDao.getRatePeriods();
	}

	/**
	 * update the system settings. save/update rate periods
	 */
	public boolean save(SystemSettings systemSettings, FinancialYear financialYear, List<Company> company)
			throws Exception {
		return systemSettingsDao.save(systemSettings, financialYear, company);
	}

	/**
	 * returning list of System settings
	 */
	public List<SystemSettings> list() {
		return systemSettingsDao.list();
	}

	/**
	 * returns the System settings model that contains the date format
	 */
	public SystemSettings getDateFormat(int systemSettingsId) throws Exception {
		SystemSettings systemSettings = systemSettingsDao.getSystemSetting(systemSettingsId);

		return systemSettings;
	}

	public RatePeriod getRatePeriod(int id) throws Exception {
		return systemSettingsDao.getRatePeriod(id);
	}

	public double getRoundingRule() {
		return systemSettingsDao.getRoundingRule();
	}

	public Map<Integer, String> getBillRoundings() {
		return systemSettingsDao.getBillRoundings();
	}

	@Transactional
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return systemSettingsDao.delete(id);
	}

	public int selectid(String code) throws Exception {
		// TODO Auto-generated method stub
		return systemSettingsDao.selectid(code.toString());
	}

	@Override
	public int getCount(int id) {
		// TODO Auto-generated method stub
		return systemSettingsDao.getCount(id);
	}

	@Override
	public List<FinancialYear> getFinancialYears() throws Exception {
		return systemSettingsDao.getFinancialYears();
	}

}