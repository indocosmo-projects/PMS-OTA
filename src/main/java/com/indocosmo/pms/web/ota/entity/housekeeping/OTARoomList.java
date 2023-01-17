package com.indocosmo.pms.web.ota.entity.housekeeping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "otaroomlist")
public class OTARoomList {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "hotel_code")
	private String hotel_code;
	
	@Column(name = "roomid")
	private String  roomid;
	
	@Column(name = "unitid")
	private String  unitid;
	
	@Column(name = "roomname")
	private String  roomname;
	
	@Column(name = "roomtypeid")
	private String  roomtypeid;
	
	@Column(name = "roomtypename")
	private String  roomtypename;
	
	@Column(name = "isblocked")
	private String  isblocked;
	
	@Column(name = "hkstatus")
	private String  hkstatus;
	
	@Column(name = "hkremarks")
	private String  hkremarks;
	
	@Column(name = "roomstatus")
	private String  roomstatus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHotel_code() {
		return hotel_code;
	}

	public void setHotel_code(String hotel_code) {
		this.hotel_code = hotel_code;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getUnitid() {
		return unitid;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	public String getRoomname() {
		return roomname;
	}

	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}

	public String getRoomtypeid() {
		return roomtypeid;
	}

	public void setRoomtypeid(String roomtypeid) {
		this.roomtypeid = roomtypeid;
	}

	public String getRoomtypename() {
		return roomtypename;
	}

	public void setRoomtypename(String roomtypename) {
		this.roomtypename = roomtypename;
	}

	public String getIsblocked() {
		return isblocked;
	}

	public void setIsblocked(String isblocked) {
		this.isblocked = isblocked;
	}

	public String getHkstatus() {
		return hkstatus;
	}

	public void setHkstatus(String hkstatus) {
		this.hkstatus = hkstatus;
	}

	public String getHkremarks() {
		return hkremarks;
	}

	public void setHkremarks(String hkremarks) {
		this.hkremarks = hkremarks;
	}

	public String getRoomstatus() {
		return roomstatus;
	}

	public void setRoomstatus(String roomstatus) {
		this.roomstatus = roomstatus;
	}
	
  
}
