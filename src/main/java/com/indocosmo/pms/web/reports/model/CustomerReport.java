package com.indocosmo.pms.web.reports.model;

import javax.persistence.Transient;

/**
 * Gana
 */
public class CustomerReport {

	String first_name;
	String last_name;
	String email;
	String phone;
	String arr_date;
	String room_type_code;
	String room_number;
	String num_nights;
	
	String act_depart_date;
	
	@Transient
	private String dateFormat;
	
	/**
	 * @return the first_name
	 */
	public String getFirst_name() {
		return first_name;
	}
	/**
	 * @return the dateFormat
	 */
	public String getDateFormat() {
		return dateFormat;
	}
	/**
	 * @param dateFormat the dateFormat to set
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	/**
	 * @param first_name the first_name to set
	 */
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	/**
	 * @return the arr_date
	 */
	public String getArr_date() {
		return arr_date;
	}
	/**
	 * @param arr_date the arr_date to set
	 */
	public void setArr_date(String arr_date) {
		this.arr_date = arr_date;
	}
	/**
	 * @return the room_type_code
	 */
	public String getRoom_type_code() {
		return room_type_code;
	}
	/**
	 * @param room_type_code the room_type_code to set
	 */
	public void setRoom_type_code(String room_type_code) {
		this.room_type_code = room_type_code;
	}
	/**
	 * @return the room_number
	 */
	public String getRoom_number() {
		return room_number;
	}
	/**
	 * @param room_number the room_number to set
	 */
	public void setRoom_number(String room_number) {
		this.room_number = room_number;
	}
	/**
	 * @return the act_depart_date
	 */
	public String getAct_depart_date() {
		return act_depart_date;
	}
	/**
	 * @param act_depart_date the act_depart_date to set
	 */
	public void setAct_depart_date(String act_depart_date) {
		this.act_depart_date = act_depart_date;
	}
	/**
	 * @return the last_name
	 */
	public String getLast_name() {
		return last_name;
	}
	/**
	 * @param last_name the last_name to set
	 */
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the num_nights
	 */
	public String getNum_nights() {
		return num_nights;
	}
	/**
	 * @param num_nights the num_nights to set
	 */
	public void setNum_nights(String num_nights) {
		this.num_nights = num_nights;
	}
	
}
