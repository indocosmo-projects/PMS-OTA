package com.indocosmo.pms.web.accountMaster.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Throwables;
import com.indocosmo.pms.web.accountMaster.dao.AccmasterDAO;
import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;
import com.indocosmo.pms.web.exception.CustomException;

@Service
public class AccountmasterServiceImp implements AccountmasterService {

	@Autowired
	private AccmasterDAO accmasterDAO;

	public static final Logger logger = LoggerFactory.getLogger(AccountmasterServiceImp.class);

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean save(AccountMaster accountmaster) throws Exception {
		boolean isSave = accmasterDAO.save(accountmaster);

		return isSave;
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
		int record = accmasterDAO.getCount(searchContent, simpleSearchMap);

		return record;
	}

	/**
	 * @Transactional AccountMaster delete function
	 * @param id
	 * @return
	 */
	@Transactional
	public boolean delete(int id) throws Exception {
		boolean isDeleted = accmasterDAO.delete(id);

		return isDeleted;
	}

	/**
	 * To show dropdown list from System defined account type
	 * 
	 * @return map
	 */
	@Transactional
	public Map<Integer, String> getAccountType() throws Exception {
		Map<Integer, String> listoftype = accmasterDAO.getAccountType();

		return listoftype;
	}

	/**
	 * Code exist checking function
	 * 
	 * @param code
	 * @return boolean
	 */
	@Transactional
	public boolean codeExist(String code) throws Exception {
		return accmasterDAO.codeExist(code);
	}

	/**
	 * Single record access function
	 * 
	 * @param id
	 *            return Account master model
	 */
	@Transactional
	public AccountMaster getRecord(int id) throws Exception {
		return accmasterDAO.getRecord(id);
	}

	/**
	 * AccountMasterList function
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
		List<AccountMaster> accountList = accmasterDAO.list(startRow, Integer.parseInt(rowLimit), advanceSearchMap,
				sortVal, simpleSearchMap);
		Encryption encryption;

		try {
			encryption = new Encryption();

			for (AccountMaster accmaster : accountList) {
				accmaster.setEncryption(encryption.encrypt(Integer.toString(accmaster.getId())));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		jqGridListWrapper.setRows(accountList);
		jqGridListWrapper.setTotal(this.getCount(advanceSearchMap, simpleSearchMap));
		jqGridListWrapper.setStartLimit(startRow);
		jqGridListWrapper.setEndLimit(Integer.parseInt(rowLimit));
		jqGridListWrapper.setPagingStart(pagingStart);

		return jqGridListWrapper;
	}
}
