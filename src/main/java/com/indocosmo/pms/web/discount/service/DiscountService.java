
package com.indocosmo.pms.web.discount.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.indocosmo.pms.enumerator.discount.DiscountType;
import com.indocosmo.pms.web.discount.model.Discount;
import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;

public interface DiscountService {
	public JqGridListWrapperDTO list(String currentPage, String rowLimit, String pagingStart,
			Map<String, String> advanceSearchMap, String sortVal, Map<String, String> simpleSearchMap) throws Exception;

	public boolean save(Discount discount) throws Exception;

	public Discount getRecord(int id) throws Exception;

	public boolean delete(String id) throws Exception;

	public int getCount(Map<String, String> advanceSearchMap, Map<String, String> simpleSearchMap) throws Exception;

	public boolean codeExist(String code) throws Exception;

	public List<Discount> getDiscounts(DiscountType discountType) throws Exception;

	public List<Discount> getDiscounts(String rateId) throws Exception;

	public List<Discount> getDiscountsByRateId(String rateId, Date sqlSDate);

}
