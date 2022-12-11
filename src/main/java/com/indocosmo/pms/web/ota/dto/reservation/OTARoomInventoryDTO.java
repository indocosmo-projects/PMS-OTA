package com.indocosmo.pms.web.ota.dto.reservation;

import java.util.ArrayList;

public class OTARoomInventoryDTO {
	
	private int id;
	
	private String roomtypeid;
	
	private String availability;
	
	private String fromdate;
	
	private String todate;
	
	private String base;
	
	private String extraadult;
	
	private String extrachild;
	
	private String ratetypeid;
	
	private String rateplanid;
	

	private ArrayList<String> contactids;

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

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getExtraadult() {
		return extraadult;
	}

	public void setExtraadult(String extraadult) {
		this.extraadult = extraadult;
	}

	public String getExtrachild() {
		return extrachild;
	}

	public void setExtrachild(String extrachild) {
		this.extrachild = extrachild;
	}

	public String getRatetypeid() {
		return ratetypeid;
	}

	public void setRatetypeid(String ratetypeid) {
		this.ratetypeid = ratetypeid;
	}

	public ArrayList<String> getContactids() {
		return contactids;
	}

	public void setContactids(ArrayList<String> contactids) {
		this.contactids = contactids;
	}
	
	
	public String getRateplanid() {
		return rateplanid;
	}

	public void setRateplanid(String rateplanid) {
		this.rateplanid = rateplanid;
	}
	
}
