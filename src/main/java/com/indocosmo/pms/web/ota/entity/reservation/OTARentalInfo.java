package com.indocosmo.pms.web.ota.entity.reservation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "otarentalinfo")
public class OTARentalInfo {
		
	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;
	
	@Column(name = "effectivedate")
	private String effectivedate;
	
	@Column(name = "packagecode")
    private String packagecode;
	
	@Column(name = "packagename")
	private String packagename;
	
	@Column(name = "roomtypecode")
	private String roomtypecode;
	
	@Column(name = "roomtypename")
	private String roomtypename;
	
	@Column(name = "adult")
	private String adult;
   
	@Column(name = "child")
	private String child;
 
	@Column(name = "rentpretax")
	private String rentpretax;
	
	@Column(name = "rent")
	private String rent;
	
	@Column(name = "discount")
	private String discount;
	
	@Column(name = "reservationid")
	private int reservationid;

	public int getReservationid() {
		return reservationid;
	}

	public void setReservationid(int reservationid) {
		this.reservationid = reservationid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEffectivedate() {
		return effectivedate;
	}

	public void setEffectivedate(String effectivedate) {
		this.effectivedate = effectivedate;
	}

	public String getPackagecode() {
		return packagecode;
	}

	public void setPackagecode(String packagecode) {
		this.packagecode = packagecode;
	}

	public String getPackagename() {
		return packagename;
	}

	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}

	public String getRoomtypecode() {
		return roomtypecode;
	}

	public void setRoomtypecode(String roomtypecode) {
		this.roomtypecode = roomtypecode;
	}

	public String getRoomtypename() {
		return roomtypename;
	}

	public void setRoomtypename(String roomtypename) {
		this.roomtypename = roomtypename;
	}

	public String getAdult() {
		return adult;
	}

	public void setAdult(String adult) {
		this.adult = adult;
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getRentpretax() {
		return rentpretax;
	}

	public void setRentpretax(String rentpretax) {
		this.rentpretax = rentpretax;
	}

	public String getRent() {
		return rent;
	}

	public void setRent(String rent) {
		this.rent = rent;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	
    	 
}
