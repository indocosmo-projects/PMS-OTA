package com.indocosmo.pms.web.agingAR.model;

import java.util.Date;

public class AgingAR {
	private int corporate_id;
	private String corporate_name;
	private float balance;
	private float balance30;
	private float balance60;
	private float balance90;
	private float balance120;
	private Date txn_date;
	private String invoice_date;
	private String invoice_no;
	private int days;
	private float amount;
	private float opening_amount;

	public float getOpening_amount() {
		return opening_amount;
	}

	public void setOpening_amount(float opening_amount) {
		this.opening_amount = opening_amount;
	}

	public String getInvoice_date() {
		return invoice_date;
	}

	public void setInvoice_date(String invoice_date) {
		this.invoice_date = invoice_date;
	}

	public String getInvoice_no() {
		return invoice_no;
	}

	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public int getCorporate_id() {
		return corporate_id;
	}

	public void setCorporate_id(int corporate_id) {
		this.corporate_id = corporate_id;
	}

	public String getCorporate_name() {
		return corporate_name;
	}

	public void setCorporate_name(String corporate_name) {
		this.corporate_name = corporate_name;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public float getBalance30() {
		return balance30;
	}

	public void setBalance30(float balance30) {
		this.balance30 = balance30;
	}

	public float getBalance60() {
		return balance60;
	}

	public void setBalance60(float balance60) {
		this.balance60 = balance60;
	}

	public float getBalance90() {
		return balance90;
	}

	public void setBalance90(float balance90) {
		this.balance90 = balance90;
	}

	public float getBalance120() {
		return balance120;
	}

	public void setBalance120(float balance120) {
		this.balance120 = balance120;
	}

	public Date getTxn_date() {
		return txn_date;
	}

	public void setTxn_date(Date txn_date) {
		this.txn_date = txn_date;
	}

}