package com.indocosmo.pms.web.reports.model;

import java.sql.Date;

public class GeneralReport {
	private int report_no;
	private String room_no;
	private String room_type;
	private String checkin_date;
	private String checkout_date;
	private String expCheckout;
	private String gust_name;
	private String pax_resv;
	private String meal_plan;
	private String deposit;
	private String tarif;
	private static int type;
	
	public int getReport_no() {
		return report_no;
	}
	public void setReport_no(int report_no) {
		this.report_no = report_no;
	}
	public String getRoom_no() {
		return room_no;
	}
	public void setRoom_no(String room_no) {
		this.room_no = room_no;
	}
	public String getRoom_type() {
		return room_type;
	}
	public void setRoom_type(String room_type) {
		this.room_type = room_type;
	}
	public String getCheckin_date() {
		return checkin_date;
	}
	public void setCheckin_date(String checkin_date) {
		this.checkin_date = checkin_date;
	}
	public String getCheckout_date() {
		return checkout_date;
	}
	public void setCheckout_date(String checkout_date) {
		this.checkout_date = checkout_date;
	}
	public String getGust_name() {
		return gust_name;
	}
	public void setGust_name(String gust_name) {
		this.gust_name = gust_name;
	}
	public String getPax_resv() {
		return pax_resv;
	}
	public void setPax_resv(String pax_resv) {
		this.pax_resv = pax_resv;
	}
	public String getMeal_plan() {
		return meal_plan;
	}
	public void setMeal_plan(String meal_plan) {
		this.meal_plan = meal_plan;
	}
	public String getDeposit() {
		return deposit;
	}
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	public String getTarif() {
		// TODO Auto-generated method stub
		return tarif;
	}
	
	public void setTarif(String tarif) {
		this.tarif = tarif;
	}
	public static int getType() {
		// TODO Auto-generated method stub
		return type;
	}
	public static void setType(int new_type) {
		type = new_type;
		
	}
	public void setExpCheckout_date(String expCheckout) {
		this.expCheckout =expCheckout;
		
	}
	public  String getExpCheckout_date() {
		// TODO Auto-generated method stub
		return expCheckout;
	}
}
