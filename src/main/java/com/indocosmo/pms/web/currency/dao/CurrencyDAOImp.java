package com.indocosmo.pms.web.currency.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.currency.model.Currency;
import com.indocosmo.pms.web.exception.CustomException;

@Repository
public class CurrencyDAOImp implements CurrencyDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(CurrencyDAOImp.class);

	/**
	 * currency table access for currency list
	 * 
	 * @param startLimit
	 * @param endLimit
	 * @param advanceSearchMap
	 * @param sortVal
	 * @param simpleSearchMap
	 * @return list of records from currency table
	 */
	@SuppressWarnings("unchecked")
	public List<Currency> list(int startLimit, int endLimit, Map<String, String> advanceSearchMap, String sortVal,
			Map<String, String> simpleSearchMap) throws Exception {
		List<Currency> currencyList = null;

		try {
			Session session = sessionFactory.getCurrentSession();
			String advanceSearch = "";

			for (Map.Entry<String, String> searchC : advanceSearchMap.entrySet()) {
				advanceSearch += "and " + searchC.getKey() + " like '%" + searchC.getValue() + "%'";
			}

			String simpleSearch = "";

			for (Map.Entry<String, String> simpleSearchVal : simpleSearchMap.entrySet()) {
				if (simpleSearch.equals("") || simpleSearch == null) {
					simpleSearch += " and (" + simpleSearchVal.getKey() + " like '%" + simpleSearchVal.getValue()
							+ "%'";
				} else {
					simpleSearch += " OR " + simpleSearchVal.getKey() + " like '%" + simpleSearchVal.getValue() + "%'";
				}
			}

			if (!simpleSearch.equals("")) {
				simpleSearch += ")";
			}

			if (sortVal == null) {
				sortVal = "id asc";
			}

			String query = "select * from  (select * from currency where  is_deleted='" + false + "' " + advanceSearch
					+ simpleSearch + "  limit " + startLimit + "," + endLimit + ") qry order by " + sortVal;
			Query qry = session.createSQLQuery(query).addEntity(Currency.class);
			currencyList = qry.list();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return currencyList;
	}

	/**
	 * Currency save
	 * 
	 * @param currency
	 *            model
	 */
	public boolean save(Currency currency) throws Exception {
		boolean isSave = true;
		Session session = null;

		try {
			session = sessionFactory.getCurrentSession();

			if (currency.getId() != 0) {
				session.update(currency);
			} else {
				session.save(currency);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : save  , " + Throwables.getStackTraceAsString(ex));
			isSave = false;
			throw new CustomException();
		}

		return isSave;
	}

	/**
	 * Database access to get single record from currency table
	 * 
	 * @param currencyId
	 */
	public Currency getRecord(int id) throws Exception {
		Session session = null;
		Currency currency = null;

		try {
			session = sessionFactory.getCurrentSession();
			currency = (Currency) session.get(Currency.class, id);
		} catch (Exception ex) {
			logger.error("Method : getRecord " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		}

		return currency;
	}

	/**
	 * Delete record from currency table (soft deletion)
	 * 
	 * @param currencyIds
	 */
	public boolean delete(int id) throws Exception {
		boolean isDeleted = true;
		Session session = null;
		Currency currency = null;

		try {
			session = sessionFactory.getCurrentSession();
			currency = (Currency) session.load(Currency.class, id);
			currency.setDeleted(true);
			session.update(currency);
		} catch (Exception ex) {
			logger.error("Method : delete " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			isDeleted = false;
			throw new CustomException();
		}

		return isDeleted;
	}

	/**
	 * Total count from currency table
	 * 
	 * @param advanceSearchMap
	 * @param simpleSearchMap
	 */
	public int getCount(Map<String, String> advanceSearchMap, Map<String, String> simpleSearchMap) throws Exception {
		int count = 0;
		String advanceSearch = "";

		if (!advanceSearchMap.isEmpty()) {
			for (Map.Entry<String, String> mapValue : advanceSearchMap.entrySet()) {
				advanceSearch += " and " + mapValue.getKey() + " like '%" + mapValue.getValue() + "%'";
			}
		}

		String simpleSearch = "";

		for (Map.Entry<String, String> simpleSearchVal : simpleSearchMap.entrySet()) {
			if (simpleSearch.equals("") || simpleSearch == null) {
				simpleSearch += " and (" + simpleSearchVal.getKey() + " like '%" + simpleSearchVal.getValue() + "%'";
			} else {
				simpleSearch += " OR " + simpleSearchVal.getKey() + " like '%" + simpleSearchVal.getValue() + "%'";
			}
		}

		if (!simpleSearch.equals("")) {
			simpleSearch += ")";
		}

		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "select count(*) from Currency where isDeleted='" + false + "'" + advanceSearch + simpleSearch;
			count = Integer.parseInt(session.createQuery(hql).list().get(0).toString());
		} catch (Exception ex) {
			logger.error("Method : getCount " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		}

		return count;
	}

	/**
	 * code Exist checking in currency table
	 * 
	 * @param code
	 */
	public boolean codeExist(String code) throws Exception {
		boolean isExist = false;

		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Currency.class);
			criteria.add(Restrictions.eq("code", code));
			criteria.add(Restrictions.eq("isDeleted", false));
			@SuppressWarnings("rawtypes")
			List listOfData = criteria.list();

			if (!listOfData.isEmpty()) {
				isExist = true;
			}

		} catch (Exception ex) {
			logger.error("Method : codeExist " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		}

		return isExist;
	}

	/**
	 * get currency symbol from currency table
	 * 
	 */
	public String getCurrencySymbol() throws Exception {
		Connection connection = new DbConnection().getConnection();
		CallableStatement ps;
		ResultSet resultSet;
		String sysmbol = "";

		String sql = "SELECT currency.symbol FROM currency INNER JOIN system_setting ON (currency.id=system_setting.base_currency_id) WHERE currency.is_deleted = 0";

		try {
			ps = connection.prepareCall(sql);
			resultSet = ps.executeQuery();

			if (resultSet.next()) {
				sysmbol = resultSet.getString(1);
			}
		} catch (Exception ex) {
			logger.error("Method : getCurrencySymbol " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}

		return sysmbol;
	}

	/**
	 * get the list of currencies from currency table
	 */
	@SuppressWarnings("unchecked")
	public List<Currency> getCurrencyList() throws Exception {
		List<Currency> currencyList = new ArrayList<Currency>();

		try {
			currencyList = sessionFactory.getCurrentSession().createQuery("from Currency where isDeleted=0").list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getCurrencyList ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return currencyList;
	}

}