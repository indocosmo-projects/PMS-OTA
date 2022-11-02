package com.indocosmo.pms.web.corporate.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Throwables;
import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.corporate.dao.CorporateDAO;
import com.indocosmo.pms.web.corporate.model.Corporate;
import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;
import com.indocosmo.pms.web.exception.CustomException;

@Service
public class CorporateServiceImp implements CorporateService {
	
	@Autowired
	private CorporateDAO corporateDAO;
	
	public static final Logger logger=LoggerFactory.getLogger(CorporateServiceImp.class);
		
	/**
	 * Corporate List function
	 * @param currentPage
	 * @param rowLimit
	 * @param pagingStart
	 * @param advanceSearch
	 * @param sortVal      
	 * @param simpleSearch
	 * @return  jsonData
	 * @throws Exception  
	 **/
	@Transactional
	public JqGridListWrapperDTO list(String currentPage,
			String rowLimit, String pagingStart,
			Map<String, String> advanceSearchMap, String sortVal,
			Map<String, String> simpleSearchMap) throws Exception {
		int startRow = (Integer.parseInt(currentPage) - 1) * Integer.parseInt(rowLimit);
		JqGridListWrapperDTO jqGridListWrapper = new JqGridListWrapperDTO();
		jqGridListWrapper.setPage(Integer.parseInt(currentPage));

		List<Corporate> listOfCorporate = corporateDAO.list(startRow,Integer.parseInt(rowLimit), advanceSearchMap, sortVal,simpleSearchMap);
		Encryption encryption;

		try {
			encryption = new Encryption();
			
			for(Corporate CorporateObj : listOfCorporate) {
				CorporateObj.setEncryption(encryption.encrypt(Integer.toString(CorporateObj.getId())));
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}  
		
		jqGridListWrapper.setRows(listOfCorporate);
		
		try {
			jqGridListWrapper.setTotal(this.getCount(advanceSearchMap, simpleSearchMap));
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		} 
		
		jqGridListWrapper.setStartLimit(startRow);
		jqGridListWrapper.setEndLimit(Integer.parseInt(rowLimit));
		jqGridListWrapper.setPagingStart(pagingStart);

		return jqGridListWrapper;
	}

	/**
	 * Corporate save function
	 * @param Corporate model
	 * @return true when saving is success/ false when saving is not success
	 */
	@Transactional
	public boolean save(Corporate corporate) throws Exception {
		boolean isRateHdrSave = corporateDAO.save(corporate);

		return isRateHdrSave;
	}

	/**
	 * Single record access function
	 * @param corporateId 
	 * @return Corporate model
	 * @throws Exception 
	 */
	@Transactional
	public Corporate getRecord(int corporateId) throws Exception {
		Corporate corporate = corporateDAO.getRecord(corporateId);

		return corporate;
	}

	/**
	 * corporate delete function
	 * @param corporateIds 
	 * @return boolean
	 * @throws Exception 
	 */
	@Transactional
	public boolean delete(int corporateIds) throws Exception {
		boolean isDeleted = corporateDAO.delete(corporateIds);

		return isDeleted;
	}

	/**
	 * total count function
	 * @param simpleSearchMap
	 * @param advanceSearchMap
	 * @return total count
	 * @throws Exception 
	 */
	@Transactional
	public int getCount(Map<String, String> searchContent,
			Map<String, String> simpleSearchMap) throws Exception {
		int count = corporateDAO.getCount(searchContent, simpleSearchMap);
		
		return count;
	}


	/**
	 * Code exist checking function
	 * @param code
	 * @return boolean 
	 */
	@Transactional
	public boolean codeExist(String code) throws Exception {
		return corporateDAO.codeExist(code);
	}

	@Transactional
	public List<Corporate> getCorporates(String keyWord) throws Exception {
		return corporateDAO.getCorporates(keyWord);
	}

	@Transactional
	public List<Corporate> listOfCorporate(int id, String name) {
		return corporateDAO.listOfCorporate(id, name);
	}


	
}