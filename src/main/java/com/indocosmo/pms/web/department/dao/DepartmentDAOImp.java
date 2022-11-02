package com.indocosmo.pms.web.department.dao;

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
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.department.dao.DepartmentDAO;
import com.indocosmo.pms.web.department.model.Department;

@Repository
public class DepartmentDAOImp implements DepartmentDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(DepartmentDAOImp.class);

	/**
	 * Department save
	 * 
	 * @param Department
	 *            model
	 * @return true when saving is success/ false when saving is not success
	 */
	public boolean save(Department department) throws Exception {
		boolean isSave = true;

		try {
			Session session = sessionFactory.getCurrentSession();

			if (department.getId() != 0) {
				session.update(department);
			} else {
				session.save(department);
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
	 * Delete record from currency table (soft deletion)
	 * 
	 * @param id
	 * @return true when saving is success/ false when saving is not success
	 */
	public boolean delete(int id) throws Exception {
		boolean isDeleted = true;

		try {
			Session session = sessionFactory.getCurrentSession();
			Department department = (Department) session.load(Department.class, id);
			department.setDeleted(true);
			session.update(department);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : delete  , " + Throwables.getStackTraceAsString(ex));
			isDeleted = false;
			throw new CustomException();
		}

		return isDeleted;
	}

	/**
	 * Database access to get single record from department table
	 * 
	 * @param Id
	 * @return department model
	 */
	public Department getRecord(int id) throws Exception {
		Department department = null;

		try {
			Session session = sessionFactory.getCurrentSession();
			department = (Department) session.get(Department.class, id);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getRecord  , " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return department;
	}

	/**
	 * department table access for department list
	 * 
	 * @param startLimit
	 * @param endLimit
	 * @param advanceSearchMap
	 * @param sortVal
	 * @param simpleSearchMap
	 * @return list of records from department table
	 */
	@SuppressWarnings("unchecked")
	public List<Department> list(int startLimit, int endLimit, Map<String, String> advanceSearchMap, String sortVal,
			Map<String, String> simpleSearchMap) throws Exception {
		List<Department> departmentList = null;

		try {
			Session session = sessionFactory.getCurrentSession();
			String advanceSearc = "";

			for (Map.Entry<String, String> searchC : advanceSearchMap.entrySet()) {
				advanceSearc += "and " + searchC.getKey() + " like '%" + searchC.getValue() + "%'";
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
					simpleSearch += " OR " + simpleSearchVal.getKey() + " like '%" + simpleSearchVal.getValue() + "%'";
				}
			}

			if (!simpleSearch.equals("")) {
				simpleSearch += ")";
			}

			if (sortVal == null) {
				sortVal = "id asc";
			}

			String query = "select * from  (select * from department where  is_deleted='" + false + "' " + advanceSearc
					+ simpleSearch + "  limit " + startLimit + "," + endLimit + ") qry order by " + sortVal;

			Query qry = session.createSQLQuery(query).addEntity(Department.class);
			departmentList = qry.list();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list  , " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return departmentList;
	}

	/**
	 * Total count from department table
	 * 
	 * @param advanceSearchMap
	 * @param simpleSearchMap
	 * @return total count records from department table
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
			String hql = "select count(*) from Department where isDeleted='" + false + "'" + advanceSearch
					+ simpleSearch;
			count = Integer.parseInt(session.createQuery(hql).list().get(0).toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getCount  , " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return count;
	}

	/**
	 * code Exist checking in Department table
	 * 
	 * @param code
	 * @return true when code is exist / false when code is not exist
	 */
	public boolean codeExist(String code) throws Exception {
		boolean isCodeExist = false;

		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Department.class);
			criteria.add(Restrictions.eq("code", code));
			criteria.add(Restrictions.eq("isDeleted", false));

			@SuppressWarnings("rawtypes")
			List listOfData = criteria.list();

			if (!listOfData.isEmpty()) {
				isCodeExist = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : codeExist  , " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return isCodeExist;
	}

	/**
	 * returns a map contains <id, name>
	 */
	public Map<Integer, String> getNameIdMap() throws Exception {
		LinkedHashMap<Integer, String> nameIdMap = new LinkedHashMap<Integer, String>();

		try {
			@SuppressWarnings("unchecked")
			List<Department> departments = sessionFactory.getCurrentSession()
					.createQuery("from Department where isDeleted=0").list();

			for (Department department : departments) {
				nameIdMap.put(department.getId(), department.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getNameIdMap, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return nameIdMap;
	}
}