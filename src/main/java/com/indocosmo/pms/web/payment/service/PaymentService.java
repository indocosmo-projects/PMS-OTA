package com.indocosmo.pms.web.payment.service;

import com.google.gson.JsonArray;
import com.indocosmo.pms.web.payment.model.Payable;
import com.indocosmo.pms.web.transaction.model.Transaction;

public interface PaymentService {
public Payable getPaymentDetails(int folioNo);
public String save(Transaction txn) throws Exception;
public JsonArray bankCardTypes()throws Exception;
public JsonArray getCompanyDetails()throws Exception;
public JsonArray getBankDetails()throws Exception;
public String setRoundAdj(Transaction txn)throws Exception;
public String getCorporateName(int corporateId);
public JsonArray getDiscountDetails(int checkinNo, int folioNo)throws Exception;
public int getDiscount(int folio_no)throws Exception;
public String upDateDiscounts(JsonArray discountDetails)throws Exception;
}
