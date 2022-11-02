package com.indocosmo.pms.web.deposit.service;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.checkOut.model.Invoice;
import com.indocosmo.pms.web.reservation.Folio;
import com.indocosmo.pms.web.transaction.model.Transaction;

public interface DepositService {
	public JsonArray listOfTransaction(int id, Map<String, String> advanceSearchMap, String dateFormat,
			Map<String, String> simpleSearchMap, int limit, String sort, String sortValue);

	public List<Transaction> getDepositList(String resvNo, String sortCol, String sortStatus);

	public Folio folioObj(Transaction txnForDeposit);

	public int txnNo();

	public AccountMaster getAccdetails(int accId);

	public int saveDeposit(Transaction saveDeposit);

	public double getTotalPayable(int id);

	public double getTotalNetDeposit(String resvNo, String chkinNo);

	public Invoice getDetails(Transaction deposit);

	public JsonArray getCorpName();

	public Integer findFolio(int folioBindNo) throws Exception;

}
