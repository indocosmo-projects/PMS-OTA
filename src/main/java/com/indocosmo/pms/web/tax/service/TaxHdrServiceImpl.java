package com.indocosmo.pms.web.tax.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.tax.dao.TaxHdrDao;
import com.indocosmo.pms.web.tax.model.TaxDtl;
import com.indocosmo.pms.web.tax.model.TaxHdr;

@Service
public class TaxHdrServiceImpl implements TaxHdrService {

	@Autowired
	private TaxHdrDao taxHdrDao;

	public static final Logger logger = LoggerFactory.getLogger(TaxHdrServiceImpl.class);

	/**
	 * Tax List function
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
	public JqGridListWrapperDTO taxHdrList(String currentPage, String rowLimit, String pagingStart,
			Map<String, String> advanceSearchMap, String sortVal, Map<String, String> simpleSearchMap) {
		int startRow = (Integer.parseInt(currentPage) - 1) * Integer.parseInt(rowLimit);
		JqGridListWrapperDTO jqGridListWrapper = new JqGridListWrapperDTO();
		jqGridListWrapper.setPage(Integer.parseInt(currentPage));

		List<TaxHdr> listOfTaxHdr = null;

		try {
			listOfTaxHdr = taxHdrDao.taxHdrList(startRow, Integer.parseInt(rowLimit), advanceSearchMap, sortVal,
					simpleSearchMap);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		Encryption encryption;

		try {
			listOfTaxHdr = taxHdrDao.taxHdrList(startRow, Integer.parseInt(rowLimit), advanceSearchMap, sortVal,
					simpleSearchMap);
			encryption = new Encryption();

			for (TaxHdr taxHdrObj : listOfTaxHdr) {
				taxHdrObj.setEncryption(encryption.encrypt(Integer.toString(taxHdrObj.getId())));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		jqGridListWrapper.setRows(listOfTaxHdr);

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
	 * total count function
	 * 
	 * @param simpleSearchMap
	 * @param advanceSearchMap
	 * @return total count
	 */
	@Transactional
	public int totalRecord(Map<String, String> searchContent, Map<String, String> simpleSearchMap) throws Exception {
		int count = taxHdrDao.totalRecord(searchContent, simpleSearchMap);

		return count;
	}

	/**
	 * Tax module save and update function
	 * 
	 * @param taxHdrForm
	 *            TaxHdrModel
	 * @return if record added successfully page redirect to taxList / not success
	 *         it will redirect to edit page
	 */
	@Transactional
	public boolean save(TaxHdr taxHdrForm) throws Exception {
		List<TaxDtl> editRecordList = taxHdrForm.getTaxDetails();

		for (int i = 0; i < editRecordList.size(); i++) {
			editRecordList.get(i).setTaxHdr(taxHdrForm);
		}

		boolean istaxSave = taxHdrDao.save(taxHdrForm);

		return istaxSave;
	}

	/**
	 * SystemSetting tax function
	 * 
	 * @param
	 * @return system setting tax
	 */
	@Transactional
	public List<SystemSettings> getSystemSettingData() throws Exception {
		return taxHdrDao.getSystemSettingData();
	}

	/**
	 * Tax delete function
	 * 
	 * @param id
	 * @return true when deletion is success / false if deletion is not success
	 */
	@Transactional
	public void delete(int id, String table) throws Exception {
		taxHdrDao.delete(id, table);
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
	@Transactional
	public TaxHdr getEditRecord(int taxHdrId) throws Exception {
		TaxHdr taxHdr = taxHdrDao.getEditRecord(taxHdrId);

		List<TaxDtl> editTaxDtlList = taxHdr.getTaxDetails();

		for (int i = 0; i < editTaxDtlList.size(); i++) {
			if (editTaxDtlList.get(i).getIsDeleted() == true) {
				editTaxDtlList.remove(i);
				i--;
			}
		}

		return taxHdr;
	}

	/**
	 * Code exist checking function
	 * 
	 * @param code
	 * @return true if code is exist/ false if code is not exist
	 */
	@Transactional
	public boolean isCodeExists(String code) throws Exception {
		return taxHdrDao.isCodeExists(code);
	}

	/**
	 * total count function
	 * 
	 * @param simpleSearchMap
	 * @param advanceSearchMap
	 * @return total count
	 */
	@Transactional
	public int getCount(Map<String, String> advanceSearchMap, Map<String, String> simpleSearchMap) throws Exception {
		int count = taxHdrDao.totalRecord(advanceSearchMap, simpleSearchMap);

		return count;
	}

	/**
	 * To show dropdown list from Tax
	 * 
	 * @return map
	 */
	@Transactional
	public Map<Integer, String> getTaxMap() throws Exception {
		Map<Integer, String> listofmap = taxHdrDao.getTaxMap();

		return listofmap;
	}
}