package com.indocosmo.pms.web.deposit.dao;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.reservation.Folio;
import com.indocosmo.pms.web.transaction.model.Transaction;

public interface DepositDAO {
	public JsonArray listOfTransaction(int id, Map<String, String> advanceSearchMap, String dateFormat,
			Map<String, String> simpleSearchMap, int limit, String sort, String sortValue);

	public List<Transaction> getDepositList(String resvNo, String sortCol, String sortStatus);

	public Folio getFolioObj(Transaction txnForDeposit);

	public int gettxnNo();

	public AccountMaster getAccMst(int accId);

	public int getSaveDeposit(Transaction saveDeposit);

	public double getTotalPayable(int id);

	public double getTotalNetDeposit(String resvNo, String chkinNo);

	public List<Transaction> getDetails(Transaction deposit);

	public JsonArray getCorpName();

	public Integer findFolio(int folioBindNo) throws Exception;

}
