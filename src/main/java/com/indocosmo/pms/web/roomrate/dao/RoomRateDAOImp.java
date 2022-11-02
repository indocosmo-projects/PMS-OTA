package com.indocosmo.pms.web.roomrate.dao;

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
import com.indocosmo.pms.web.common.model.RateDist;
import com.indocosmo.pms.web.common.model.RateDtl;
import com.indocosmo.pms.web.common.model.RateHdr;
import com.indocosmo.pms.web.department.model.Department;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.roomType.model.RoomType;

@Repository
public class RoomRateDAOImp implements RoomRateDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(RoomRateDAOImp.class);

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

			String query = "select * from  (select * from rate_hdr where rate_type=2 and is_deleted='" + false + "'"
					+ criteria + simpleSearch + "  limit " + startLimit + "," + endLimit + ") qry order by " + sortVal;
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

			List<RateDtl> editRecordList = rateHdr.getRateDetails();

			for (int i = 0; i < editRecordList.size(); i++) {
				editRecordList.get(i).setIsDeleted(true);
				editRecordList.get(i).setRateHdr(rateHdr);
			}

			List<RateDist> editDistRecordList = rateHdr.getRateDistribution();

			for (int i = 0; i < editDistRecordList.size(); i++) {
				editDistRecordList.get(i).setIsDeleted(true);
				editDistRecordList.get(i).setRateHdr(rateHdr);
			}

			rateHdr.setIsDeleted(true);
			session.update(rateHdr);
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
			String hql = "select count(*) from RateHdr where rateType=2 and isDeleted='" + false + "'" + searchCriteria
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
			criteria.add(Restrictions.eq("rateType", 2));
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
	 * Database access to get record list from rateHdr table
	 * 
	 * @return map containing rates id and name
	 */
	public Map<Integer, String> list() throws Exception {
		Map<Integer, String> rateHdrListMap = new LinkedHashMap<Integer, String>();
		List<RateHdr> rateHdrList = null;

		try {
			Session session = sessionFactory.getCurrentSession();
			String query = "select * from rate_hdr where is_deleted='false'";
			Query qry = session.createSQLQuery(query).addEntity(RateHdr.class);
			rateHdrList = qry.list();

			for (RateHdr rateHdr : rateHdrList) {
				rateHdrListMap.put(rateHdr.getId(), rateHdr.getCode());
			}

		} catch (Exception ex) {
			logger.error("Method : list " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		}

		return rateHdrListMap;
	}

	/**
	 * Database access to get record list from room type table
	 * 
	 * @return map containing room types' id and name
	 */
	public Map<Integer, String> roomTypeListMap() throws Exception {
		Map<Integer, String> roomTypeListMap = new LinkedHashMap<Integer, String>();
		List<RoomType> roomTypeList = null;

		try {
			Session session = sessionFactory.getCurrentSession();
			String query = "select * from room_type where is_deleted='false'";
			Query qry = session.createSQLQuery(query).addEntity(RoomType.class);
			roomTypeList = qry.list();

			for (RoomType roomType : roomTypeList) {
				roomTypeListMap.put(roomType.getId(), roomType.getCode());
			}
		} catch (Exception ex) {
			logger.error("Method : roomTypeListMap " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		}

		return roomTypeListMap;
	}

	/**
	 * Database access to get single record from room type table
	 * 
	 * @param roomTypeId
	 * @return room type model
	 */
	public RoomType roomDetails(int roomTypeId) throws Exception {
		Session session = null;
		RoomType roomDetails = null;

		try {
			session = sessionFactory.getCurrentSession();
			roomDetails = (RoomType) session.get(RoomType.class, roomTypeId);
		} catch (Exception ex) {
			logger.error("Method : roomDetails " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		}

		return roomDetails;
	}

	/**
	 * Database access to get record list from department table
	 * 
	 * @return map containing department types' id and name
	 */

	public Map<Integer, String> departmentTypeListMap() throws Exception {
		Map<Integer, String> departmentTypeListMap = new LinkedHashMap<Integer, String>();
		List<Department> departmentTypeList = null;

		try {
			Session session = sessionFactory.getCurrentSession();
			String query = "select * from department where is_deleted='false'";
			Query qry = session.createSQLQuery(query).addEntity(Department.class);
			departmentTypeList = qry.list();

			for (Department departmentType : departmentTypeList) {
				departmentTypeListMap.put(departmentType.getId(), departmentType.getName());
			}
		} catch (Exception ex) {
			logger.error("Method : departmentTypeListMap " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		}

		return departmentTypeListMap;
	}

}