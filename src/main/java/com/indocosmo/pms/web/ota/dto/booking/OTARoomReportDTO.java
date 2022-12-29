package com.indocosmo.pms.web.ota.dto.booking;

public class OTARoomReportDTO {
	
	private int id;
	
	private String room_nights ;
	
	private String room_sold ;
	
	private String adr;
	
	private String room_charges;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoom_nights() {
		return room_nights;
	}

	public void setRoom_nights(String room_nights) {
		this.room_nights = room_nights;
	}

	public String getRoom_sold() {
		return room_sold;
	}

	public void setRoom_sold(String room_sold) {
		this.room_sold = room_sold;
	}

	public String getAdr() {
		return adr;
	}

	public void setAdr(String adr) {
		this.adr = adr;
	}

	public String getRoom_charges() {
		return room_charges;
	}

	public void setRoom_charges(String room_charges) {
		this.room_charges = room_charges;
	}
	
	
	
}
