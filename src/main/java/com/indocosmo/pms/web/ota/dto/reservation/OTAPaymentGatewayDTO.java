package com.indocosmo.pms.web.ota.dto.reservation;

public class OTAPaymentGatewayDTO {
	
	private int id;
			
	private String paymenttypeunkid;
	
	private String hotel_code;
	
	private String shortcode;
	
	private String paymenttype;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPaymenttypeunkid() {
		return paymenttypeunkid;
	}

	public void setPaymenttypeunkid(String paymenttypeunkid) {
		this.paymenttypeunkid = paymenttypeunkid;
	}

	public String getHotel_code() {
		return hotel_code;
	}

	public void setHotel_code(String hotel_code) {
		this.hotel_code = hotel_code;
	}

	public String getShortcode() {
		return shortcode;
	}

	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}

	public String getPaymenttype() {
		return paymenttype;
	}

	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	
	
}
