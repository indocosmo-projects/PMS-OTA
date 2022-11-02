package com.indocosmo.pms.web.settlement.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.settlement.dao.SettlementDAO;
import com.indocosmo.pms.web.settlement.model.Settlement;

@Service
@Transactional
public class SettlementServiceImp implements SettlementService {

	@Autowired
	SettlementDAO settlementDAO;

	@Override
	public List<Settlement> getSettlementList(Date startDate, Date endDate, Integer corporate, JsonObject paginationObj) {
		
		return settlementDAO.getSettlementList(startDate, endDate, corporate, paginationObj);
	}

	@Override
	public List<Settlement> getInvoiceDetails(int corporate) {
		
		return settlementDAO.getInvoiceDetails(corporate);
	}

	@Override
	public boolean save(JsonArray settlementArray) {
		
		return settlementDAO.save(settlementArray);
	}

	@Override
	public JsonArray getCorporates() throws SQLException {
		
		return settlementDAO.getCorporates();
	}

	@Override
	public boolean saveNewPayment(JsonObject newPaymentObject, int currentUser) {
		
		return settlementDAO.saveNewPayment(newPaymentObject, currentUser);
	}

	@Override
	public int getVoucherNo() throws SQLException {
		
		return settlementDAO.getVoucherNo();
	}

	@Override
	public boolean saveAllocation(JsonObject settlementObject) {
		
		return settlementDAO.saveAllocation(settlementObject);
	}

	@Override
	public boolean updatePayment(JsonObject newPaymentObject, int currentUser) {
		
		return settlementDAO.updatePayment(newPaymentObject, currentUser);
	}

	@Override
	public double getOutstanding(int corporate) {
		
		return settlementDAO.getOutstanding(corporate);
	}

	@Override
	public JsonObject getReceiptData(String voucherNo) {
		
		return settlementDAO.getReceiptData(voucherNo);
	}
	

}