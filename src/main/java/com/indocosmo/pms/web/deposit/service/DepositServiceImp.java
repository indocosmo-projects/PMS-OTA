package com.indocosmo.pms.web.deposit.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.checkOut.model.Invoice;
import com.indocosmo.pms.web.deposit.dao.DepositDAO;
import com.indocosmo.pms.web.reservation.Folio;
import com.indocosmo.pms.web.transaction.model.Transaction;

@Service
public class DepositServiceImp implements DepositService {

	@Autowired
	DepositDAO depositDAO;

	public JsonArray listOfTransaction(int id, Map<String, String> advanceSearchMap, String dateFormat,
			Map<String, String> simpleSearchMap, int limit, String sort, String sortValue) {
		return depositDAO.listOfTransaction(id, advanceSearchMap, dateFormat, simpleSearchMap, limit, sort, sortValue);
	}

	public List<Transaction> getDepositList(String folioBindNo, String sortCol, String sortStatus) {
		return depositDAO.getDepositList(folioBindNo, sortCol, sortStatus);
	}

	public Folio folioObj(Transaction txnForDeposit) {
		return depositDAO.getFolioObj(txnForDeposit);
	}

	public int txnNo() {
		return depositDAO.gettxnNo();
	}

	public AccountMaster getAccdetails(int accId) {
		return depositDAO.getAccMst(accId);
	}

	public int saveDeposit(Transaction saveDeposit) {
		return depositDAO.getSaveDeposit(saveDeposit);
	}

	public double getTotalPayable(int id) {
		return depositDAO.getTotalPayable(id);
	}

	public double getTotalNetDeposit(String resvnNo, String chkinNo) {
		return depositDAO.getTotalNetDeposit(resvnNo, chkinNo);
	}

	@Override
	public Invoice  getDetails(Transaction deposit) {
		// TODO Auto-generated method stub
		Invoice invoice = new Invoice();
		List<Transaction> txnList = depositDAO.getDetails(deposit);
		invoice.setTxnList(txnList);
		return invoice;
	}

	@Override
	public JsonArray getCorpName() {
		// TODO Auto-generated method stub
		return depositDAO.getCorpName();
	}

	@Override
	public Integer findFolio(int folioBindNo) throws Exception {
		// TODO Auto-generated method stub
		return depositDAO.findFolio(folioBindNo);
	}
}