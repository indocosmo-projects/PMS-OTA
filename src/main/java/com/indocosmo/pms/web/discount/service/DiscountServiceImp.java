package com.indocosmo.pms.web.discount.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.indocosmo.pms.enumerator.discount.DiscountType;
import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.discount.dao.DiscountDAO;
import com.indocosmo.pms.web.discount.model.Discount;
import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;
import com.indocosmo.pms.web.exception.CustomException;

@Service
public class DiscountServiceImp implements DiscountService {

	@Autowired
	private DiscountDAO discountDAO;

	public static final Logger logger = LoggerFactory.getLogger(DiscountServiceImp.class);

	/**
	 * DiscountList function
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
		List<Discount> listOfDiscount = discountDAO.list(startRow, Integer.parseInt(rowLimit), advanceSearchMap,
				sortVal, simpleSearchMap);

		Encryption encryption;

		try {
			encryption = new Encryption();

			for (Discount discount : listOfDiscount) {
				discount.setEncryption(encryption.encrypt(Integer.toString(discount.getId())));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		jqGridListWrapper.setRows(listOfDiscount);
		jqGridListWrapper.setTotal(this.getCount(advanceSearchMap, simpleSearchMap));
		jqGridListWrapper.setStartLimit(startRow);
		jqGridListWrapper.setEndLimit(Integer.parseInt(rowLimit));
		jqGridListWrapper.setPagingStart(pagingStart);

		return jqGridListWrapper;
	}

	/**
	 * Discount save function
	 * 
	 * @param Discount
	 *            model
	 * @return true when saving is success/ false when saving is not success
	 */
	@Transactional
	public boolean save(Discount discount) throws Exception {
		boolean isdiscountSave = discountDAO.save(discount);

		return isdiscountSave;
	}

	/**
	 * Single record access function
	 * 
	 * @param id
	 *            return Discount model
	 */
	@Transactional
	public Discount getRecord(int id) throws Exception {
		return discountDAO.getRecord(id);
	}

	/**
	 * Discount delete function
	 * 
	 * @param id
	 * @return true when deletion is success/ false when deletion is not success
	 */
	@Transactional
	public boolean delete(String id) throws Exception {
		boolean isDeleted = discountDAO.delete(id);

		return isDeleted;
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
		int count = discountDAO.getCount(advanceSearchMap, simpleSearchMap);

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
		return discountDAO.codeExist(code);
	}

	/**
	 * return discounts based on discount type
	 */
	@Transactional
	public List<Discount> getDiscounts(DiscountType discountType) throws Exception {
		return discountDAO.getDiscounts(discountType);
	}

	/**
	 * return discounts based on rate
	 */
	@Transactional
	public List<Discount> getDiscounts(String rateId) throws Exception {
		return discountDAO.getDiscounts(rateId);
	}

	/**
	 * return discounts based on rate
	 */
	@Transactional
	public List<Discount> getDiscountsByRateId(String rateId, Date sqlSDate) {
		// TODO Auto-generated method stub
		return discountDAO.getDiscountsByRateId(rateId, sqlSDate);
	}
}