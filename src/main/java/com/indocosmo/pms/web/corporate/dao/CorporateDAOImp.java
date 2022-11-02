package com.indocosmo.pms.web.corporate.dao;

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
import com.indocosmo.pms.web.corporate.model.Corporate;
import com.indocosmo.pms.web.exception.CustomException;

@Repository
public class CorporateDAOImp implements CorporateDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(CorporateDAOImp.class);

	/**
	 * corporate table access for room rate list
	 * 
	 * @param startLimit
	 * @param endLimit
	 * @param advanceSearchMap
	 * @param sortVal
	 * @param simpleSearchMap
	 * @return list of records from corporate table
	 */
	public List<Corporate> list(int startLimit, int endLimit, Map<String, String> advanceSearchMap, String sortVal,
			Map<String, String> simpleSearchMap) throws Exception {
		List corporateList = null;

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

			String query = "select * from  (select * from corporate where  is_deleted='" + false + "' " + criteria
					+ simpleSearch + "  limit " + startLimit + "," + endLimit + ") qry order by " + sortVal;
			Query qry = session.createSQLQuery(query).addEntity(Corporate.class);
			corporateList = qry.list();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return corporateList;
	}

	/**
	 * Total count from corporate table
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
			String hql = "select count(*) from Corporate where isDeleted='" + false + "'" + searchCriteria
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
	 * Corporate save
	 * 
	 * @param corporate
	 *            model
	 */
	public boolean save(Corporate corporate) throws Exception {
		boolean isSave = true;
		Session session = null;

		try {
			session = sessionFactory.getCurrentSession();

			if (corporate.getId() != 0) {
				session.update(corporate);
			} else {
				session.save(corporate);
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
	 * @param corporateId
	 */
	public Corporate getRecord(int corporateId) throws Exception {
		Session session = null;
		Corporate corporate = null;

		try {
			session = sessionFactory.getCurrentSession();
			corporate = (Corporate) session.get(Corporate.class, corporateId);
		} catch (Exception ex) {
			logger.error("Method : getRecord " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		}

		return corporate;
	}

	/**
	 * Delete record from corporate table (soft deletion)
	 * 
	 * @param corporateId
	 */
	public boolean delete(int corporateId) throws Exception {
		boolean isDeleted = true;
		Session session = null;
		Corporate corporate = null;

		try {
			session = sessionFactory.getCurrentSession();
			corporate = (Corporate) session.load(Corporate.class, corporateId);
			corporate.setIsDeleted(true);
			session.update(corporate);
		} catch (Exception ex) {
			logger.error("Method : delete " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			isDeleted = false;
			throw new CustomException();
		}

		return isDeleted;
	}

	/**
	 * code Exist checking in corporate table
	 * 
	 * @param code
	 */
	public boolean codeExist(String code) throws Exception {
		boolean isExist = false;
		// System.out.println("code value is "+ code);

		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Corporate.class);
			criteria.add(Restrictions.eq("code", code));
			criteria.add(Restrictions.eq("isDeleted", false));
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
	 * fetch the corporates from corporate table
	 * 
	 * @keyWord
	 * @return list of corporates according to the search key word.
	 */
	@SuppressWarnings("unchecked")
	public List<Corporate> getCorporates(String keyWord) throws Exception {
		List<Corporate> corporateList = null;

		try {
			corporateList = sessionFactory.getCurrentSession()
					.createQuery("from Corporate where name like '%" + keyWord + "%' and isDeleted = 0").list();
		} catch (Exception e) {
			logger.error("Method : getCorporates, " + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}

		return corporateList;
	}

	public List<Corporate> listOfCorporate(int id, String name) {
		List<Corporate> listOfCorporate = null;

		try {
			Session session = sessionFactory.getCurrentSession();
			if (!name.equals("")) {
				String sql = "from Corporate where name like '%" + name
						+ "%' and corporateClass in(select corporateClass from Corporate where id = '" + id
						+ "') and status = 1 and is_deleted= 0 ";
				Query query = session.createQuery(sql);
				System.out.println("inside"+sql);
				listOfCorporate = query.list();
			} else {
				String sql = "from Corporate where  corporateClass = '" + id + "' and status = 1 and is_deleted= 0 ";
				Query query = session.createQuery(sql);
				System.out.println("inside ffd"+sql);
				listOfCorporate = query.list();
			}

		} catch (Exception ex) {
			logger.error("Method : listOfCorporate " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		}

		return listOfCorporate; 
	}

}