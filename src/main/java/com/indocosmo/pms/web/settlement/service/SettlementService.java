package com.indocosmo.pms.web.settlement.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.settlement.model.Settlement;

public interface SettlementService {

	List<Settlement> getSettlementList(Date startDate, Date endDate, Integer corporate, JsonObject paginationObj);

	List<Settlement> getInvoiceDetails(int corporate);

	boolean save(JsonArray settlementArray);

	JsonArray getCorporates() throws SQLException;

	boolean saveNewPayment(JsonObject newPaymentObject, int currentUser);

	int getVoucherNo() throws SQLException;

	boolean saveAllocation(JsonObject settlementObject);

	boolean updatePayment(JsonObject newPaymentObject, int currentUser);

	double getOutstanding(int corporate);

	JsonObject getReceiptData(String voucherNo);

}
