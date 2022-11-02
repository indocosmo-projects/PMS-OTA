package com.indocosmo.pms.web.rackrate.dao;

import java.util.LinkedHashMap;
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
import com.indocosmo.pms.enumerator.RateType;
import com.indocosmo.pms.web.common.model.RateHdr;
import com.indocosmo.pms.web.common.model.RatePeriod;
import com.indocosmo.pms.web.exception.CustomException;

@Repository
public class RackRateDAOImpl implements RackRateDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(RackRateDAOImpl.class);

	/**
	 * rateHdr table access for room rate list
	 * 
	 * @param startLimit
	 * @param endLimit
	 * @param advanceSearchMap
	 * @param sortVal
	 * @param simpleSearchMap
	 * @return list of records from rateHdr table
	 */
	public List<RateHdr> list(int startLimit, int endLimit, Map<String, String> advanceSearchMap, String sortVal,
			Map<String, String> simpleSearchMap) throws Exception {
		List rateHdrList = null;

		try {
			Session session = sessionFactory.getCurrentSession();
			String criteria = "";

			for (Map.Entry<String, String> searchC : advanceSearchMap.entrySet()) {
				criteria += "and " + searchC.getKey() + " like '%" + searchC.getValue() + "%'";
			}

			/*
			 * for form simple search
			 */
			String simpleSearch = "";

			for (Map.Entry<String, String> simpleSearchVal : simpleSearchMap.entrySet()) {
				if (simpleSearch.equals("") || simpleSearch == null) {
					simpleSearch += " and (" + simpleSearchVal.getKey() + " like '%" + simpleSearchVal.getValue()
							+ "%'";
				} else {
					simpleSearch += " or " + simpleSearchVal.getKey() + " like '%" + simpleSearchVal.getValue() + "%'";
				}
			}

			if (!simpleSearch.equals("")) {
				simpleSearch += ")";
			}

			if (sortVal == null) {
				sortVal = "id asc";
			}

			String query = "select * from  (select * from rate_hdr where rate_type=" + RateType.RACKRATE.getCode()
					+ " and is_deleted=0 " + criteria + simpleSearch + "  limit " + startLimit + "," + endLimit
					+ ") qry order by " + sortVal;
			Query qry = session.createSQLQuery(query).addEntity(RateHdr.class);
			rateHdrList = qry.list();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return rateHdrList;
	}

	/**
	 * Room Rate save
	 * 
	 * @param rateHdr
	 *            model
	 */
	public boolean save(RateHdr rateHdr) throws Exception {
		boolean isSave = true;
		Session session = null;

		try {
			session = sessionFactory.getCurrentSession();

			if (rateHdr.getId() != 0) {
				session.update(rateHdr);
			} else {
				session.save(rateHdr);
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
	 * Database access to get single record from RateHdr table
	 * 
	 * @param rateHdrId
	 */
	public RateHdr getRecord(int rateHdrId) throws Exception {
		Session session = null;
		RateHdr rateHdr = null;

		try {
			session = sessionFactory.getCurrentSession();
			rateHdr = (RateHdr) session.get(RateHdr.class, rateHdrId);
			rateHdr.getRateDetails().size();
			rateHdr.getRateDistribution().size();
		} catch (Exception ex) {
			logger.error("Method : getRecord " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		}

		return rateHdr;
	}

	/**
	 * Delete record from RateHdr table (soft deletion)
	 * 
	 * @param rateHdrIds
	 */
	public boolean delete(int rateHdrIds) throws Exception {
		boolean isDeleted = true;
		Session session = null;
		RateHdr rateHdr = null;

		try {
			session = sessionFactory.getCurrentSession();
			rateHdr = (RateHdr) session.load(RateHdr.class, rateHdrIds);
			rateHdr.setIsDeleted(true);
			session.update(rateHdr);

			String hql1 = "update RateDtl set isDeleted=1 where rate_hdr=" + rateHdrIds;
			String hql2 = "update RateDist set isDeleted=1 where rate_hdr=" + rateHdrIds;

			session.createQuery(hql1).executeUpdate();
			session.createQuery(hql2).executeUpdate();
		} catch (Exception ex) {
			logger.error("Method : delete " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			isDeleted = false;
			throw new CustomException();
		}

		return isDeleted;
	}

	/**
	 * Total count from rateHdr table
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
			String hql = "select count(*) from RateHdr where rateType=" + RateType.RACKRATE.getCode()
					+ " and isDeleted='" + false + "'" + searchCriteria + simpleSearch;
			count = Integer.parseInt(session.createQuery(hql).list().get(0).toString());
		} catch (Exception ex) {
			logger.error("Method : getCount " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		}

		return count;
	}

	/**
	 * code Exist checking in rateHdr table
	 * 
	 * @param code
	 */
	public boolean codeExist(String code) throws Exception {
		boolean isExist = false;

		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(RateHdr.class);
			criteria.add(Restrictions.eq("code", code));
			criteria.add(Restrictions.eq("rateType", RateType.RACKRATE.getCode()));
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

	public Map<Integer, String> getRatePeriods() throws Exception {
		Map<Integer, String> rateListMap = new LinkedHashMap<Integer, String>();
		List<RatePeriod> rateList = null;

		try {
			Session session = sessionFactory.getCurrentSession();
			String sql = "from RatePeriod where deleted=" + false;
			Query query = session.createQuery(sql);
			rateList = query.list();

			for (RatePeriod rate : rateList) {
				rateListMap.put(rate.getId(), rate.getCode());
			}

		} catch (Exception ex) {
			logger.error("Method : getRatePeriods " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		}

		return rateListMap;
	}

	/**
	 * fetch the rack rates corresponding to the rooms
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getRackRates(int roomTypeId) throws Exception {
		List<Object[]> rackRates = null;

		try {
			String sqlQuery = "SELECT rate_period.from_date, rate_period.to_date,"
					+ "coalesce(rack_rate.meal_plan, '') as meal_plan,rack_rate.id FROM rate_period LEFT OUTER JOIN ("
					+ "SELECT * FROM rate_hdr WHERE rate_hdr.is_deleted = 0 "
					+ "AND rate_hdr.rate_type = 1 AND rate_hdr.room_type_id = :roomType"
					+ ") AS rack_rate ON rate_period.id = rack_rate.rate_period_id "
					+ "WHERE rate_period.is_deleted = 0";

			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery).setParameter("roomType",
					roomTypeId);
			rackRates = query.list();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getRackRates, " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return rackRates;
	}

}