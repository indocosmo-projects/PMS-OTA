package com.indocosmo.pms.web.accountMaster.dao;

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
import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.accountMaster.model.AccountTypes;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.tax.model.TaxHdr;

@Repository
public class AccountmasterDAOImp implements AccmasterDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(AccountmasterDAOImp.class);

	/**
	 * AccountMaster save
	 * 
	 * @param AccountMaster
	 *            model
	 * @return true when saving is success/ false when saving is not success
	 */
	public boolean save(AccountMaster accmaster) throws Exception {
		boolean isSave = true;

		try {
			Session session = sessionFactory.getCurrentSession();

			if (accmaster.getId() != 0) {
				session.update(accmaster);
			} else {
				session.save(accmaster);
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
	 * Delete record from Account Master table (soft deletion)
	 * 
	 * @param id
	 * @return true when saving is success/ false when saving is not success
	 */
	public boolean delete(int id) throws Exception {
		boolean isDeleted = true;

		try {
			Session session = sessionFactory.getCurrentSession();
			AccountMaster accmaster = (AccountMaster) session.load(AccountMaster.class, id);
			accmaster.setIs_deleted(true);
			session.update(accmaster);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : delete  , " + Throwables.getStackTraceAsString(ex));
			isDeleted = false;
			throw new CustomException();
		}

		return isDeleted;
	}

	/**
	 * To get list from system defined account type
	 * 
	 * @return map
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getAccountType() {
		Map<Integer, String> accountTypeMap = new LinkedHashMap<Integer, String>();
		List<AccountTypes> typeList = null;

		try {
			Session session = sessionFactory.getCurrentSession();
			String query = "select * from sysdef_acc_type";
			Query qry = session.createSQLQuery(query).addEntity(AccountTypes.class);
			typeList = qry.list();

			for (AccountTypes acctype : typeList) {
				accountTypeMap.put(acctype.getId(), acctype.getName());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list  ," + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return accountTypeMap;
	}

	/**
	 * code Exist checking in AccountMaster table
	 * 
	 * @param code
	 * @return true when code is exist / false when code is not exist
	 */
	public boolean codeExist(String code) {
		boolean isExist = false;

		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(AccountMaster.class);
			criteria.add(Restrictions.eq("code", code));
			criteria.add(Restrictions.eq("is_deleted", false));

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
	 * Database access to get single record from AccountMaster table
	 * 
	 * @param Id
	 * @return AccountMaster model
	 */
	public AccountMaster getRecord(int id) throws Exception {
		AccountMaster accountmaster = null;

		try {
			Session session = sessionFactory.getCurrentSession();
			accountmaster = (AccountMaster) session.get(AccountMaster.class, id);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getRecord  , " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return accountmaster;
	}

	/**
	 * Total count from Account master table
	 * 
	 * @param advanceSearchMap
	 * @param simpleSearchMap
	 * @return total count records from AccountMaster table
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
			String hql = "select count(*) from AccountMaster where is_deleted='" + false + "'" + advanceSearch
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
	 * AccountMaster table access for department list
	 * 
	 * @param startLimit
	 * @param endLimit
	 * @param advanceSearchMap
	 * @param sortVal
	 * @param simpleSearchMap
	 * @return list of records from AccountMaster table
	 */
	@SuppressWarnings("unchecked")
	public List<AccountMaster> list(int startLimit, int endLimit, Map<String, String> advanceSearchMap, String sortVal,
			Map<String, String> simpleSearchMap) throws Exception {
		List<AccountMaster> accmasterList = null;

		try {
			Session session = sessionFactory.getCurrentSession();
			String advanceSearc = "";

			for (Map.Entry<String, String> searchC : advanceSearchMap.entrySet()) {
				advanceSearc += "and " + searchC.getKey() + " like '%" + searchC.getValue() + "%'";
			}

			// for form simple search

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

			String query = "select * from  (select * from acc_mst where  is_deleted='" + false + "' " + advanceSearc
					+ simpleSearch + "  limit " + startLimit + "," + endLimit + ") qry order by " + sortVal;

			Query qry = session.createSQLQuery(query).addEntity(AccountMaster.class);
			accmasterList = qry.list();

			for (AccountMaster accountMaster : accmasterList) {
				String taxCode = getTaxcode(accountMaster.getTax_id());
				accountMaster.setTax_code(taxCode);

				String sys_def_acc_code = getSysDefAcc(accountMaster.getSysdef_acc_type_id());
				accountMaster.setSysdef_acc_type(sys_def_acc_code);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list  , " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return accmasterList;
	}

	public String getTaxcode(int id) {
		Session session = sessionFactory.getCurrentSession();
		TaxHdr taxHdr = (TaxHdr) session.get(TaxHdr.class, id);
		return taxHdr.getCode();
	}

	public String getSysDefAcc(int id) {
		Session session = sessionFactory.getCurrentSession();
		AccountTypes accountTypes = (AccountTypes) session.get(AccountTypes.class, id);
		return accountTypes.getName();
	}
}