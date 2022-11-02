package com.indocosmo.pms.web.currency.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.currency.dao.CurrencyDAO;
import com.indocosmo.pms.web.currency.model.Currency;
import com.indocosmo.pms.web.common.Encryption;

@Service
public class CurrencyServiceImp implements CurrencyService {

	@Autowired
	private CurrencyDAO currencyDAO;

	public static final Logger logger = LoggerFactory.getLogger(CurrencyServiceImp.class);

	/**
	 * CurrecyList function
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
		List<Currency> listOfCurrency = currencyDAO.list(startRow, Integer.parseInt(rowLimit), advanceSearchMap,
				sortVal, simpleSearchMap);
		Encryption encryption;

		try {
			encryption = new Encryption();

			for (Currency currrency : listOfCurrency) {
				currrency.setEncryption(encryption.encrypt(Integer.toString(currrency.getId())));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		jqGridListWrapper.setRows(listOfCurrency);
		jqGridListWrapper.setTotal(this.getCount(advanceSearchMap, simpleSearchMap));
		jqGridListWrapper.setStartLimit(startRow);
		jqGridListWrapper.setEndLimit(Integer.parseInt(rowLimit));
		jqGridListWrapper.setPagingStart(pagingStart);

		return jqGridListWrapper;
	}

	/**
	 * Currency save function
	 * 
	 * @param currency
	 *            model
	 * @return true when saving is success/ false when saving is not success
	 */
	@Transactional
	public boolean save(Currency currency) throws Exception {
		boolean iscurrencySave = currencyDAO.save(currency);

		return iscurrencySave;
	}

	/**
	 * Single record access function
	 * 
	 * @param id
	 *            return currency model
	 */
	@Transactional
	public Currency getRecord(int currencyId) throws Exception {
		return currencyDAO.getRecord(currencyId);
	}

	/**
	 * Currency delete function
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public boolean delete(int currencyIds) throws Exception {
		return currencyDAO.delete(currencyIds);
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
		int count = currencyDAO.getCount(searchContent, simpleSearchMap);

		return count;
	}

	/**
	 * Code exist checking function
	 * 
	 * @param code
	 * @return boolean
	 */
	@Transactional
	public boolean codeExist(String code) throws Exception {
		return currencyDAO.codeExist(code);
	}

	/**
	 * get currency symbol from currency table
	 * 
	 */
	public String getCurrencySymbol() throws Exception {
		return currencyDAO.getCurrencySymbol();
	}

	/**
	 * get all the currencies from currency table
	 */
	@Transactional
	public List<Currency> getCurrencyList() throws Exception {
		return currencyDAO.getCurrencyList();
	}
}