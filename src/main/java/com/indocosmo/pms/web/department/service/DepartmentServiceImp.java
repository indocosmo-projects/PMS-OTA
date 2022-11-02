package com.indocosmo.pms.web.department.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.indocosmo.pms.web.department.dao.DepartmentDAO;
import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.department.model.Department;
import com.indocosmo.pms.web.common.Encryption;

@Service
@Transactional
public class DepartmentServiceImp implements DepartmentService {

	@Autowired
	private DepartmentDAO departmentDAO;

	public static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImp.class);

	/**
	 * Department save function
	 * 
	 * @param currency
	 *            model
	 * @return
	 */
	@Transactional
	public boolean save(Department department) throws Exception {
		boolean isSave = departmentDAO.save(department);

		return isSave;
	}

	/**
	 * Department delete function
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public boolean delete(int id) throws Exception {
		boolean isDeleted = departmentDAO.delete(id);

		return isDeleted;
	}

	/**
	 * Single record access function
	 * 
	 * @param id
	 *            return currency model
	 */
	@Transactional
	public Department getRecord(int id) throws Exception {
		return departmentDAO.getRecord(id);
	}

	/**
	 * DepartmentList function
	 * 
	 * @param currentPage
	 * @param rowLimit
	 * @param pagingStart
	 * @param advanceSearch
	 * @param sortVal
	 * @param simpleSearch
	 * @return jsonData
	 **/
	@Transactional
	public JqGridListWrapperDTO list(String currentPage, String rowLimit, String pagingStart,
			Map<String, String> advanceSearchMap, String sortVal, Map<String, String> simpleSearchMap)
			throws Exception {
		int startRow = (Integer.parseInt(currentPage) - 1) * Integer.parseInt(rowLimit);
		JqGridListWrapperDTO jqGridListWrapper = new JqGridListWrapperDTO();
		jqGridListWrapper.setPage(Integer.parseInt(currentPage));
		List<Department> departmentList = departmentDAO.list(startRow, Integer.parseInt(rowLimit), advanceSearchMap,
				sortVal, simpleSearchMap);
		Encryption encryption;

		try {
			encryption = new Encryption();

			for (Department department : departmentList) {
				department.setEncryption(encryption.encrypt(Integer.toString(department.getId())));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		jqGridListWrapper.setRows(departmentList);
		jqGridListWrapper.setTotal(this.getCount(advanceSearchMap, simpleSearchMap));
		jqGridListWrapper.setStartLimit(startRow);
		jqGridListWrapper.setEndLimit(Integer.parseInt(rowLimit));
		jqGridListWrapper.setPagingStart(pagingStart);

		return jqGridListWrapper;
	}

	/**
	 * total count function
	 * 
	 * @param simpleSearchMap
	 * @param advanceSearchMap
	 * @return total count
	 */
	@Transactional
	public int getCount(Map<String, String> searchContent, Map<String, String> simpleSearchMap) throws Exception {
		int record = departmentDAO.getCount(searchContent, simpleSearchMap);

		return record;
	}

	/**
	 * Code exist checking function
	 * 
	 * @param code
	 * @return boolean
	 */

	public boolean codeExist(String code) throws Exception {
		return departmentDAO.codeExist(code);
	}

	/**
	 * returns a map as <id, name>
	 */
	@Transactional
	public Map<Integer, String> getNameIdMap() throws Exception {
		return departmentDAO.getNameIdMap();
	}
}

/*
 * package com.indocosmo.pms.web.department.service;
 * 
 * import java.util.List; import java.util.Map;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Service;
 * 
 * import com.indocosmo.pms.web.department.dao.DepartmentDAO; import
 * com.indocosmo.pms.web.department.model.Department; import
 * com.indocosmo.pms.web.general.QueryResult;
 * 
 * 
 * 
 * @Service public class DepartmentServiceImp implements DepartmentService {
 * 
 * @Autowired private DepartmentDAO departmentDao;
 * 
 * 
 * public void save(Department department) throws Exception{
 * 
 * departmentDao.save(department); }
 * 
 * 
 * public Object deleteOnCondition(int id) throws Exception {
 * 
 * return departmentDao.deleteOnCondition(id); }
 * 
 * 
 * @Override public Object getRecord(Integer id) throws Exception {
 * 
 * return departmentDao.getRecord(id); }
 * 
 * 
 * @Override public Integer getCount(String code, Map<String, String> filterMap)
 * throws Exception {
 * 
 * return departmentDao.getCount(code, filterMap); }
 * 
 * 
 * @Override public QueryResult getList(List<Map<String, String>>
 * filterMapArray, Map<String, String> scopeMap, List<Map<String, String>>
 * sortMapArray) throws Exception {
 * 
 * return departmentDao.getList(filterMapArray, scopeMap, sortMapArray); }
 * 
 * @Override public void delete(int id) throws Exception {
 * 
 * }
 * 
 * }
 */