package com.indocosmo.pms.web.discount.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
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
import com.indocosmo.pms.enumerator.discount.DiscountType;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.discount.model.Discount;

@Repository
public class DiscountDAOImp implements DiscountDAO {

	DbConnection dbConnection = null;

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(DiscountDAOImp.class);

	/**
	 * discount table access for discount list
	 * 
	 * @param startLimit
	 * @param endLimit
	 * @param advanceSearchMap
	 * @param sortVal
	 * @param simpleSearchMap
	 * @return list of records from discount table
	 */

	public DiscountDAOImp() {
		dbConnection = new DbConnection();
	}

	@SuppressWarnings("unchecked")
	public List<Discount> list(int startLimit, int endLimit, Map<String, String> advanceSearchMap, String sortVal,
			Map<String, String> simpleSearchMap) throws Exception {
		List<Discount> DiscountList = null;

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

			String query = "select * from  (select * from discount where is_deleted='" + false + "' " + advanceSearch
					+ simpleSearch + "  limit " + startLimit + "," + endLimit + ") qry order by " + sortVal;

			Query qry = session.createSQLQuery(query).addEntity(Discount.class);
			DiscountList = qry.list();
			for (Discount discount : DiscountList) {
				if (discount.getIsPc() == true) {
					discount.setCalcMode("Percentage");
					discount.setCalAmount(discount.getDiscPc());
				} else {
					discount.setCalcMode("Absolute amount");
					discount.setCalAmount(discount.getDiscAmount());
				}
				if (discount.getDiscountFor() == 1 ){
					discount.setDiscountForName("ROOM");
				} 
				else if (discount.getDiscountFor() == 2 ){
					discount.setDiscountForName("FOOD");
				} 
				else {
					discount.setDiscountForName("ROOM AND FOOD");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return DiscountList;
	}

	/**
	 * Discount save
	 * 
	 * @param discount
	 *            model
	 * @return boolean value
	 */
	public boolean save(Discount discount) throws Exception {
		boolean isSave = true;
		Session session = null;

		try {
			session = sessionFactory.getCurrentSession();

			if (discount.getId() != 0) {
				session.update(discount);
			} else {
				session.save(discount);
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
	 * Database access to get single record from discount table
	 * 
	 * @param discountId
	 * @return discount model
	 */
	public Discount getRecord(int discountId) throws Exception {
		Session session = null;
		Discount discount = null;

		try {
			session = sessionFactory.getCurrentSession();
			discount = (Discount) session.get(Discount.class, discountId);
		} catch (Exception ex) {
			logger.error("Method : getRecord " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		}

		return discount;
	}

	/**
	 * Delete record from discount table (soft deletion)
	 * 
	 * @param discountIds
	 */
	public boolean delete(String discountIds) throws Exception {
		boolean isDeleted = true;
		Session session = null;
		Discount discount = null;

		try {
			session = sessionFactory.getCurrentSession();
			discount = (Discount) session.load(Discount.class, Integer.parseInt(discountIds));
			discount.setIsDeleted(true);
			session.update(discount);
		} catch (Exception ex) {
			logger.error("Method : delete " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			isDeleted = false;
			throw new CustomException();
		}

		return isDeleted;
	}

	/**
	 * Total count from discount table
	 * 
	 * @param advanceSearchMap
	 * @param simpleSearchMap
	 */
	public int getCount(Map<String, String> searchContent, Map<String, String> simpleSearchMap) throws Exception {
		int count = 0;
		String searchCriteria = "";

		if (!searchContent.isEmpty()) {
			for (Map.Entry<String, String> mapValue : searchContent.entrySet()) {
				searchCriteria += " and " + mapValue.getKey() + " like '%" + mapValue.getValue() + "%'";
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
			String hql = "select count(*) from Discount where isDeleted='" + false + "'" + searchCriteria
					+ simpleSearch;

			count = Integer.parseInt(session.createQuery(hql).list().get(0).toString());
		} catch (Exception ex) {
			logger.error("Method : getCount " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		}

		return count;
	}

	/**
	 * Database access to get single record from SystemSetting table
	 * 
	 * @param systemSettingsId
	 */
	public SystemSettings getSystemSetting(int systemSettingsId) throws Exception {
		Session session = null;
		SystemSettings systemSettings = null;

		try {
			session = sessionFactory.getCurrentSession();
			systemSettings = (SystemSettings) session.get(SystemSettings.class, systemSettingsId);
		} catch (Exception ex) {
			logger.error("Method : getSystemSetting " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		}

		return systemSettings;
	}

	/**
	 * code Exist checking in discount table
	 * 
	 * @param code
	 */
	public boolean codeExist(String code) throws Exception {

		boolean isExist = false;

		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Discount.class);
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
	 * return all discounts
	 */
	@SuppressWarnings("unchecked")
	public List<Discount> getDiscounts(DiscountType discountType) throws Exception {
		List<Discount> discountList = null;

		try {
			discountList = sessionFactory.getCurrentSession()
					.createQuery("from Discount where isDeleted = 0 and discType = " + discountType.getCode()).list();
		} catch (Exception e) {
			logger.error("Method : getDiscounts, " + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}

		return discountList;
	}

	@SuppressWarnings("unchecked")
	public List<Discount> getDiscounts(String rateId) throws Exception {
		List<Discount> discountList = null;

		try {
			String query = "from Discount where isDeleted = 0 and discType = 1 and rateId = " + rateId;
			discountList = sessionFactory.getCurrentSession().createQuery(query).list();
		} catch (Exception e) {
			logger.error("Method : getDiscounts, " + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}

		return discountList;
	}

	public List<Discount> getDiscountsByRateId(String rateId, Date sqlSDate) {
		// TODO Auto-generated method stub
		List<Discount> discountList = null;
		discountList = new ArrayList<Discount>();
		Connection connection = dbConnection.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		// ResultSetMapper<Discount> resultSetMapper = new ResultSetMapper<Discount>();
		try {
			// String query = "from Discount where isDeleted = 0 and discType = 1 and rateId
			// = " + rateId;

			String query = "select * from discount where is_deleted = 0 and disc_type = 1 and rate_id =" + rateId
					+ " AND valid_from <= '" + sqlSDate + "' " + "and  IF(ISNULL(valid_to),valid_from,valid_to >= '"
					+ sqlSDate + "') ";
			// System.out.println("query="+query);

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			/*
			 * discountList = resultSetMapper.mapRersultSetToObject(resultSet,
			 * Discount.class); // print out the list retrieved from database
			 * if(discountList != null){ for(Discount pojo : discountList){
			 * System.out.println(pojo); } }
			 */

			while (resultSet.next()) {
				Discount dis = new Discount();
				// dis.setCode(resultSet.getString("amount"));

				dis.setId(resultSet.getInt("id"));
				dis.setCode(resultSet.getString("code"));
				dis.setName(resultSet.getString("name"));
				dis.setDescription(resultSet.getString("desCription"));
				dis.setValidFrom(resultSet.getDate("valid_from"));
				dis.setDiscType(resultSet.getInt("disc_type"));
				dis.setIsPc(resultSet.getBoolean("is_pc"));
				dis.setRateId(resultSet.getInt("rate_id"));

				if (resultSet.getBoolean("is_pc")) {
					dis.setDiscPc(resultSet.getBigDecimal("disc_pc"));
				} else {
					dis.setDiscAmount(resultSet.getBigDecimal("disc_amount"));
				}

				dis.setIsOpen(resultSet.getBoolean("is_open"));

				discountList.add(dis);
			}

			// discountList = sessionFactory.getCurrentSession().createQuery(query).list();

		} catch (Exception e) {
			logger.error("Method : getDiscounts, " + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}
		

		return discountList;
	}
}