package com.indocosmo.pms.web.transaction.service;

import java.sql.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.checkIn.model.CheckInHdr;
import com.indocosmo.pms.web.reservation.Folio;
import com.indocosmo.pms.web.tax.model.TaxHdr;
import com.indocosmo.pms.web.transaction.dao.TxnDao;
import com.indocosmo.pms.web.transaction.model.Transaction;

@Service
@Transactional
public class TxnServiceImpl implements TxnService {

	@Autowired
	TxnDao transactionDAO;

	public String deleteRecord(int txnNo) {
		return transactionDAO.deleteRecord(txnNo);
	}

	public TaxHdr getTaxCode(int taxId) {
		return transactionDAO.getTaxCode(taxId);
	}

	public AccountMaster accMstId(int accMstId) {
		return transactionDAO.getAccMstVal(accMstId);
	}

	public Folio getFolioDetails(int checkinNo) {
		return transactionDAO.getFolioDetails(checkinNo);
	}

	public CheckInHdr getcheckinDetails(int checkinNo) {
		return transactionDAO.getcheckinDetails(checkinNo);
	}

	public List<AccountMaster> getAccountMasterDetails() {
		return transactionDAO.getAccountMasterDetails();
	}
	public List<AccountMaster> getPosAccountMasterDetails() throws Exception {
		return transactionDAO.getPosAccountMasterDetails();
	}
	public List<AccountMaster> getSystemAccMstList() {
		return transactionDAO.getSystemAccMstList();
	}
	
	public List<AccountMaster> getAccMstList() {
		return transactionDAO.getAccMstList();
	}
	
	public List<AccountMaster> getAccountMasterDetailsSystem(int folio) {
		return transactionDAO.getAccountMasterDetailsSystem(folio);
	}

	public Transaction getCharges(Date hotelDate, double amount, int accMst, boolean isBase, int chkInNo) {
		return transactionDAO.getCharges(hotelDate, amount, accMst, isBase, chkInNo);
	}

	public String update(Transaction txn) {
		return transactionDAO.update(txn);
	}

	public String save(Transaction txn) {
		return transactionDAO.save(txn);
	}

	// Transaction
	public JsonArray getRoomList() {
		return transactionDAO.getRoomList();
	}

	public JsonArray getTransactionDetails(int folioNo) {
		return transactionDAO.getTransactionDetails(folioNo);
	}

	public String newTransafer(int sourseFolioNO, Transaction txn, JSONObject selectedobj) {
		return transactionDAO.newTransafer(sourseFolioNO, txn, selectedobj);
	}

	public TaxHdr getTaxCode(double amount, boolean isBase) {
		return transactionDAO.getTaxCode(amount, isBase);
	}

	@Override
	public Object getOrderDetails(Integer txnNo) {

		return transactionDAO.getOrderDetails(txnNo);
	}

	@Override
	public List<Object> getAccountTypeName() throws Exception {

		return transactionDAO.getAccountTypeName();
	}

	@Override
	public String editAcessValue(String code) throws Exception {
		// TODO Auto-generated method stub
		return transactionDAO.editAcessValue(code);
	}

	@Override
	public String updatePOSAccName(int txnId, String posAccMStName) throws Exception {
		// TODO Auto-generated method stub
		return transactionDAO.updatePOSAccName(txnId,posAccMStName);
	}

}