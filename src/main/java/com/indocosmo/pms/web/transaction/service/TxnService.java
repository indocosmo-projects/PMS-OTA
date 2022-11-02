package com.indocosmo.pms.web.transaction.service;

import java.sql.Date;
import java.util.List;

import org.json.simple.JSONObject;

import com.google.gson.JsonArray;
import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.checkIn.model.CheckInHdr;
import com.indocosmo.pms.web.reservation.Folio;
import com.indocosmo.pms.web.tax.model.TaxHdr;
import com.indocosmo.pms.web.transaction.model.Transaction;

public interface TxnService {

	public String deleteRecord(int txnNo);

	public TaxHdr getTaxCode(int taxId);

	public TaxHdr getTaxCode(double amount, boolean isBase);

	public AccountMaster accMstId(int accMstId);

	public Folio getFolioDetails(int checkinNo);

	public CheckInHdr getcheckinDetails(int checkinNo);

	public List<AccountMaster> getSystemAccMstList();
	
	public List<AccountMaster> getAccMstList();
	
	public List<AccountMaster> getAccountMasterDetails();
	
	
	public List<AccountMaster> getAccountMasterDetailsSystem(int folioNo);

	public Transaction getCharges(Date hotelDate, double amount, int accMst, boolean isBase, int chkInNo);

	public String save(Transaction txn);

	public String update(Transaction txn);

	public JsonArray getRoomList();

	public JsonArray getTransactionDetails(int folioNo);

	public String newTransafer(int sourseFolioNO, Transaction txn, JSONObject selectedobj);

	public Object getOrderDetails(Integer txnNo);

	public List<Object> getAccountTypeName() throws Exception;

	public String editAcessValue(String string) throws Exception;

	public List<AccountMaster> getPosAccountMasterDetails() throws Exception;

	public String updatePOSAccName(int txnId, String posAccMStName)throws Exception;

}