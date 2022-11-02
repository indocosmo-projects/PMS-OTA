package com.indocosmo.pms.web.checkOut.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.checkOut.model.CheckOutDetails;
import com.indocosmo.pms.web.transaction.model.Transaction;

public interface CheckOutDAO {
	public List<CheckOutDetails> getCheckInDetailsByFolioBindNO(int folioBindNO) throws Exception;

	public String doCheckOut(List<CheckOutDetails> checkOutDetailsList, JsonObject billAddressObj) throws Exception;

	public List<Transaction> getTxnDetails(int folioNo);

	public JsonObject getCustomerDetails(int folioNo) throws Exception;

	public JsonArray getCheckInCheckOutData(String wherePart) throws Exception;

	public List<Transaction> getInvoiceGroupDetails(int folioNo);

	public List<Transaction> getTxnSummary(int folioNo);

	public List<Transaction> getInvoiceDetails(int folioNo);

	public List<Transaction> getPaymentList(int folioNo);

	public JsonObject loadAddressWithCheckinNo(int checkinNo);

	public List<Transaction> getTxnDetailsSeparate(int folioNo, int systemAccType);

	public List<Transaction> getTxnDetailsSeparateGrop(int folioNo, int systemAccType);

	public List<Transaction> getTxnSummarySeparate(int folioNo, int sysAccType);

	public String getReportName(int sysAccType);

	public List<Transaction> getInvoiceGroupDetailsCombined(int folioNo);
}
