package com.indocosmo.pms.web.payment.dao;

import com.google.gson.JsonArray;
import com.indocosmo.pms.web.serviceTax.model.serviceTax;
import com.indocosmo.pms.web.transaction.model.Transaction;

public interface PaymentDAO {
	public serviceTax getserviceTax(int id,double amount);
	public String save(Transaction txn);
	public String setFinalSettleMent(int folioNum) throws Exception;
	public JsonArray bankCardTypes()throws Exception;
	public JsonArray getCompanyDetails()throws Exception;
	public JsonArray getBankDetails()throws Exception;
	public String getCorporateName(int corporateId);
	public JsonArray getDiscountDetails(int checkinNo, int folioNo)throws Exception;
	public int getDiscount(int folio_no)throws Exception;
	public String upDateDiscounts(JsonArray discountDetails)throws Exception;
}
