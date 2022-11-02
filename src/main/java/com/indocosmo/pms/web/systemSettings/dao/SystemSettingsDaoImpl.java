package com.indocosmo.pms.web.systemSettings.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.common.model.FinancialYear;
import com.indocosmo.pms.web.common.model.RatePeriod;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.systemSettings.model.Company;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;

@Repository
public class SystemSettingsDaoImpl implements SystemSettingsDao {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(SystemSettingsDaoImpl.class);

	/**
	 * get the record for system_setting table
	 */
	@SuppressWarnings("unchecked")
	public SystemSettings getSystemSettings() throws Exception {

		SystemSettings systemSettings = new SystemSettings();

		try {
			List<SystemSettings> settingsList = sessionFactory.getCurrentSession().createQuery("from SystemSettings")
					.list();

			if (settingsList.size() > 0) {
				systemSettings = settingsList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getSystemSettings ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return systemSettings;
	}

	@SuppressWarnings("unchecked")
	public Company getcompany() throws Exception {

		Company company = new Company();

		try {
			List<Company> companyList = sessionFactory.getCurrentSession().createQuery("from Company").list();

			if (companyList.size() > 0) {
				company = companyList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getSystemSettings ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return company;
	}

	/**
	 * get all the rate periods from rate_period table
	 */
	@SuppressWarnings("unchecked")
	public List<RatePeriod> getRatePeriods() throws Exception {

		List<RatePeriod> ratePeriodList = new ArrayList<RatePeriod>();

		try {

			ratePeriodList = sessionFactory.getCurrentSession().createQuery("from RatePeriod where deleted=0").list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getRatePeriods ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return ratePeriodList;
	}

	/**
	 * update the system settings and save/update the rate periods also
	 */
	public boolean save(SystemSettings systemSettings, FinancialYear financialYear, List<Company> company)
			throws Exception {
		// Below code is only for temporary purpose. Don't know the usage of hotelDate.
		// Date hotelDate = new Date();

		// systemSettings.setHotelDate(hotelDate);
		boolean isSave = true;

		try {
			sessionFactory.getCurrentSession().update(systemSettings);

			// sessionFactory.getCurrentSession().update(company);
			if (financialYear != null)
				sessionFactory.getCurrentSession().save(financialYear);

			for (Company compny : company) {
				if (compny.getId() == 1) {
					sessionFactory.getCurrentSession().update(compny);
				}
			}
		} catch (Exception e) {
			isSave = false;
			e.printStackTrace();
			logger.error("Method : save ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return isSave;
	}

	/**
	 * returns the list of system settings
	 */
	@SuppressWarnings("unchecked")
	public List<SystemSettings> list() {
		List<SystemSettings> listOfSystemSettings = null;

		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "From SystemSettings";
			Query qry = session.createQuery(hql);
			listOfSystemSettings = qry.list();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : maxNights, " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return listOfSystemSettings;
	}

	/**
	 * Fetch the system settings model
	 */
	public SystemSettings getSystemSetting(int systemSettingsId) throws Exception {
		Session session = null;
		SystemSettings systemSettings = null;

		try {
			session = sessionFactory.getCurrentSession();
			systemSettings = (SystemSettings) session.get(SystemSettings.class, systemSettingsId);
		} catch (Exception ex) {
			logger.error("Method : getSystemSetting ," + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		}

		return systemSettings;
	}

	public RatePeriod getRatePeriod(int id) throws Exception {
		RatePeriod ratePeriod = null;

		try {
			Session session = sessionFactory.getCurrentSession();
			ratePeriod = (RatePeriod) session.get(RatePeriod.class, id);
		} catch (Exception ex) {
			logger.error("Method : getRatePeriod ," + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		}

		return ratePeriod;
	}

	public double getRoundingRule() {
		double rounding = 0.0;
		DbConnection db = new DbConnection();
		Connection con = db.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String sql = "select rd.round_to from rounding rd inner join system_setting sys on  rd.id=sys.bill_rounding where rd.is_deleted=0";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				rounding = rs.getDouble("round_to");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.releaseResource(con);
			db.releaseResource(st);
			db.releaseResource(rs);
		}
		return rounding;
	}

	public Map<Integer, String> getBillRoundings() {
		Map<Integer, String> billRound = new LinkedHashMap<Integer, String>();
		DbConnection db = new DbConnection();
		Connection con = db.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String sql = "select rounding.id,rounding.code from rounding where rounding.is_deleted=0";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				billRound.put(rs.getInt("id"), rs.getString("code"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.releaseResource(con);
			db.releaseResource(st);
			db.releaseResource(rs);
		}
		return billRound;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		boolean isDeleted = true;
		try {
			Session session = sessionFactory.getCurrentSession();
			RatePeriod ratePeriod = (RatePeriod) session.load(RatePeriod.class, id);
			ratePeriod.setDeleted(isDeleted);
			session.update(ratePeriod);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("Method : delete  , " + Throwables.getStackTraceAsString(e));
			isDeleted = false;
			throw new CustomException();
		}
		return isDeleted;
	}

	public int selectid(String code) throws Exception {
		DbConnection db = new DbConnection();
		Connection con = db.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		int id = 0;
		String sql = "SELECT id  from rate_period where is_deleted=0 and `code`='" + code + "'";
		try {
			statement = con.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				id = resultSet.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Method: getAccMstVal, " + Throwables.getStackTraceAsString(e));
		}
		finally {
			db.releaseResource(con);
			db.releaseResource(statement);
			db.releaseResource(resultSet);
		}
		return id;

	}

	@Override
	public int getCount(int id) {
		int count = 0;
		String sql = "SELECT COUNT(id) AS `count` FROM rate_hdr WHERE rate_period_id=" + id
				+ " AND is_deleted=0 AND is_active=1";
		DbConnection db = new DbConnection();
		Connection connection = db.getConnection();
		ResultSet rs = null;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			db.releaseResource(rs);
			db.releaseResource(statement);
			db.releaseResource(connection);
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FinancialYear> getFinancialYears() throws Exception {
		List<FinancialYear> financialYearList = new ArrayList<FinancialYear>();

		try {

			financialYearList = sessionFactory.getCurrentSession().createQuery("from FinancialYear").list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getRatePeriods ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return financialYearList;
	}
}