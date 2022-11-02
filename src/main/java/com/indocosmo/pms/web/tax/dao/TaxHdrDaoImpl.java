package com.indocosmo.pms.web.tax.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.tax.model.TaxHdr;

@Repository
@Transactional
public class TaxHdrDaoImpl implements TaxHdrDao {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(TaxHdrDaoImpl.class);

	/**
	 * tax_hdr table access for tax list
	 * 
	 * @param startLimit
	 * @param endLimit
	 * @param advanceSearchMap
	 * @param sortVal
	 * @param simpleSearchMap
	 * @return list of records from tax_hdr table
	 */
	@SuppressWarnings("unchecked")
	public List<TaxHdr> taxHdrList(int startLimit, int endLimit, Map<String, String> advanceSearchMap, String sortVal,
			Map<String, String> simpleSearchMap) throws Exception {
		@SuppressWarnings("rawtypes")
		List taxHdrList = null;

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
					simpleSearch += " and " + simpleSearchVal.getKey() + " like '%" + simpleSearchVal.getValue() + "%'";
				} else {
					simpleSearch += " or " + simpleSearchVal.getKey() + " like '%" + simpleSearchVal.getValue() + "%'";
				}
			}

			if (sortVal == null) {
				sortVal = "id asc";
			}

			String query = "select * from  (select * from tax_hdr where is_deleted='" + false + "' " + criteria
					+ simpleSearch + "  limit " + startLimit + "," + endLimit + ") qry order by " + sortVal;

			Query qry = session.createSQLQuery(query).addEntity(TaxHdr.class);
			taxHdrList = qry.list();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return taxHdrList;
	}

	/**
	 * Total count from tax_hdr table
	 * 
	 * @param advanceSearchMap
	 * @param simpleSearchMap
	 * @return total count records from tax_hdr table
	 */
	public int totalRecord(Map<String, String> searchContent, Map<String, String> simpleSearchMap) throws Exception {
		int count = 0;
		String searchCriteria = "";

		if (!searchContent.isEmpty()) {
			for (Map.Entry<String, String> mapValue : searchContent.entrySet()) {
				searchCriteria += " and " + mapValue.getKey() + " like '%" + mapValue.getValue() + "%'";
			}
		}

		String simpleSearch = "";

		if (!simpleSearchMap.isEmpty()) {
			for (Map.Entry<String, String> simpleSearchVal : simpleSearchMap.entrySet()) {
				if (simpleSearch.equals("") || simpleSearch == null) {
					simpleSearch += " and " + simpleSearchVal.getKey() + " like '%" + simpleSearchVal.getValue() + "%'";
				} else {
					simpleSearch += " or " + simpleSearchVal.getKey() + " like '%" + simpleSearchVal.getValue() + "%'";
				}
			}
		}

		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "select count(*) from TaxHdr where isDeleted='" + false + "'" + searchCriteria + simpleSearch;
			count = Integer.parseInt(session.createQuery(hql).list().get(0).toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return count;
	}

	/**
	 * Total records from system_setting table
	 * 
	 * @return list of records from system_setting table
	 */
	@SuppressWarnings("unchecked")
	public List<SystemSettings> getSystemSettingData() throws Exception {
		String sql = "select s.tax1Name,s.tax2Name,s.tax3Name,s.tax4Name from SystemSettings s where s.id=1";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		List<SystemSettings> systeDtlList = query.list();

		return systeDtlList;
	}

	/**
	 * Tax module Single record access function
	 * 
	 * @param tax
	 *            TaxHdrModel
	 * @param model
	 * @param id
	 *            primary key of table (encrypted)
	 * @return if the record is get the page redirect to edit page
	 */
	public TaxHdr getEditRecord(int taxHdrId) throws Exception {
		Session session = null;
		TaxHdr editRecordList;

		try {
			session = sessionFactory.getCurrentSession();
			editRecordList = (TaxHdr) session.get(TaxHdr.class, taxHdrId);
			editRecordList.getTaxDetails().size();
		} catch (Exception ex) {
			logger.error("Method : getRecord " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		}

		return editRecordList;
	}

	/**
	 * Tax module save and update function
	 * 
	 * @param taxHdrForm
	 *            TaxHdrModel
	 * @return if record added successfully page redirect to taxList / not success
	 *         it will redirect to edit page
	 */
	public boolean save(TaxHdr taxHdrForm) throws Exception {
		boolean isSave = true;
		Session session = null;

		try {
			session = sessionFactory.getCurrentSession();

			if (taxHdrForm.getId() != 0) {
				session.update(taxHdrForm);
			} else {
				session.save(taxHdrForm);
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
	 * not completed review Delete record from table (soft deletion)
	 * 
	 * @param
	 * 
	 */
	public void delete(int id, String table) throws Exception {
		Query query = sessionFactory.getCurrentSession()
				.createQuery("update " + table + " t set t.isDeleted=1 where id=" + id);
		query.executeUpdate();
	}

	/**
	 * code Exist checking in TaxHdr table
	 * 
	 * @param code
	 * @return true if code is exist/false if code is not exist
	 */
	public boolean isCodeExists(String code) throws Exception {
		try {
			Query query = sessionFactory.getCurrentSession()
					.createQuery("select id from TaxHdr where code='" + code + "' and isDeleted=0");

			if (query.list().size() > 0)
				return true;
		} catch (Exception ex) {
			logger.error("Method : codeExist " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		}

		return false;
	}

	/**
	 * To get dropdown list from tax table
	 * 
	 * @return map
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getTaxMap() {
		Map<Integer, String> accountListMap = new LinkedHashMap<Integer, String>();
		List<TaxHdr> taxHdrList = null;

		try {
			Session session = sessionFactory.getCurrentSession();
			String query = "select * from tax_hdr where is_deleted=0";
			Query qry = session.createSQLQuery(query).addEntity(TaxHdr.class);
			taxHdrList = qry.list();

			for (TaxHdr taxHdr : taxHdrList) {
				accountListMap.put(taxHdr.getId(), taxHdr.getCode());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list  ," + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return accountListMap;
	}
}