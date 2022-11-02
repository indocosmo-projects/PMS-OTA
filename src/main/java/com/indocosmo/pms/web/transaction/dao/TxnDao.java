package com.indocosmo.pms.web.transaction.dao;

import java.sql.Date;
import java.util.List;

import org.json.simple.JSONObject;

import com.google.gson.JsonArray;
import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.checkIn.model.CheckInHdr;
import com.indocosmo.pms.web.reservation.Folio;
import com.indocosmo.pms.web.tax.model.TaxHdr;
import com.indocosmo.pms.web.transaction.model.Transaction;

public interface TxnDao {

	public String deleteRecord(int txnNo);

	public TaxHdr getTaxCode(int taxId);

	public TaxHdr getTaxCode(double amount, boolean isBase);

	public AccountMaster getAccMstVal(int accMstId);

	public Folio getFolioDetails(int checkinNo);

	public CheckInHdr getcheckinDetails(int checkinNo);

	public List<AccountMaster> getAccountMasterDetails();

	public Transaction getCharges(Date hotelDate, double amount, int accMst, boolean isBase, int chkInNo);

	public String save(Transaction txn);

	public String update(Transaction txn);

	public JsonArray getRoomList();

	public JsonArray getTransactionDetails(int folioNo);

	public String newTransafer(int sourseFolioNO, Transaction txn, JSONObject selectedobj);

	public Object getOrderDetails(Integer txnNo);

	public List<Object> getAccountTypeName() throws Exception;

	public List<AccountMaster> getAccountMasterDetailsSystem(int folio);

	public List<AccountMaster> getSystemAccMstList();

	public List<AccountMaster> getAccMstList();

	public String editAcessValue(String code) throws Exception;

	public List<AccountMaster> getPosAccountMasterDetails() throws Exception;

	public String updatePOSAccName(int txnId, String posAccMStName)throws Exception;
}