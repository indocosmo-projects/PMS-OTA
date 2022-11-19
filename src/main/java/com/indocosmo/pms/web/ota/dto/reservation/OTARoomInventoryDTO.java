package com.indocosmo.pms.web.ota.dto.reservation;

public class OTARoomInventoryDTO {
	
	private int id;
	
	private String roomtypeid;
	
	private String availability;
	
	private String fromdate;
	
	private String todate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoomtypeid() {
		return roomtypeid;
	}

	public void setRoomtypeid(String roomtypeid) {
		this.roomtypeid = roomtypeid;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}
	

}
