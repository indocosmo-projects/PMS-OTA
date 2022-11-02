package com.indocosmo.pms.web.checkOut.model;

import java.util.List;

import com.indocosmo.pms.web.transaction.model.Transaction;

public class InvoiceTaxSummary {

	private List<Transaction> txnListInvoice;
	private List<Transaction> txnListSummary;

	public List<Transaction> getTxnListInvoice() {
		return txnListInvoice;
	}

	public void setTxnListInvoice(List<Transaction> txnListInvoice) {
		this.txnListInvoice = txnListInvoice;
	}

	public List<Transaction> getTxnListSummary() {
		return txnListSummary;
	}

	public void setTxnListSummary(List<Transaction> txnListSummary) {
		this.txnListSummary = txnListSummary;
	}

}