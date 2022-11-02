package com.indocosmo.pms.web.ota.dto.hotel;

public class HotelInfoDTO {
	
	private int id;
	
	private String hotelcode;
	
	private String hotelname;
	
	private String successmsg;
	
	private String status;
	
	private String errormsg;
	
	private String errorcode;
	
	private String password;
	
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHotelcode() {
		return hotelcode;
	}

	public void setHotelcode(String hotelcode) {
		this.hotelcode = hotelcode;
	}

	public String getHotelname() {
		return hotelname;
	}

	public void setHotelname(String hotelname) {
		this.hotelname = hotelname;
	}

	public String getSuccessmsg() {
		return successmsg;
	}

	public void setSuccessmsg(String successmsg) {
		this.successmsg = successmsg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	
	
}
