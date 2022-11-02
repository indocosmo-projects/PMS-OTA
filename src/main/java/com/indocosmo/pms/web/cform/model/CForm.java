package com.indocosmo.pms.web.cform.model;

import com.indocosmo.pms.web.templates.model.InvoiceTemplate;

public class CForm {

	private String name;
	private String gender;
	private String address;
	private String email;
	private String phone;
	private String nationality;
	private String passportNo;
	private String passport_expiry_on;
	private String arr_date;
	private String arr_time;
	private String num_nights;
	private InvoiceTemplate headerFooter;
	private String act_depart_date;
	private String remarks;
	private String header;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getPassport_expiry_on() {
		return passport_expiry_on;
	}

	public void setPassport_expiry_on(String passport_expiry_on) {
		this.passport_expiry_on = passport_expiry_on;
	}

	public String getArr_date() {
		return arr_date;
	}

	public void setArr_date(String arr_date) {
		this.arr_date = arr_date;
	}

	public String getArr_time() {
		return arr_time;
	}

	public void setArr_time(String arr_time) {
		this.arr_time = arr_time;
	}

	public InvoiceTemplate getHeaderFooter() {
		return headerFooter;
	}

	public void setHeaderFooter(InvoiceTemplate headerFooter) {
		this.headerFooter = headerFooter;
	}

	public String getAct_depart_date() {
		return act_depart_date;
	}

	public void setAct_depart_date(String act_depart_date) {
		this.act_depart_date = act_depart_date;
	}

	public String getNum_nights() {
		return num_nights;
	}

	public void setNum_nights(String num_nights) {
		this.num_nights = num_nights;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

}