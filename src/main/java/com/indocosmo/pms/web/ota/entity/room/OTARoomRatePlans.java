package com.indocosmo.pms.web.ota.entity.room;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "otaroomrateplans")
public class OTARoomRatePlans {
	
	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;
	
	@Column(name = "rateplanid")
	private String rateplanid;
	
	@Column(name = "roomname")
	private String roomname;
	
	@Column(name = "roomtypeid")
	private String roomtypeid;
	
	@Column(name = "roomtype")
	private String roomtype;
	
	@Column(name = "ratetypeid")
	private String ratetypeid;
	
	@Column(name = "ratetype")
	private String ratetype;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRateplanid() {
		return rateplanid;
	}

	public void setRateplanid(String rateplanid) {
		this.rateplanid = rateplanid;
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

	public String getRoomtype() {
		return roomtype;
	}

	public void setRoomtype(String roomtype) {
		this.roomtype = roomtype;
	}

	public String getRatetypeid() {
		return ratetypeid;
	}

	public void setRatetypeid(String ratetypeid) {
		this.ratetypeid = ratetypeid;
	}

	public String getRatetype() {
		return ratetype;
	}

	public void setRatetype(String ratetype) {
		this.ratetype = ratetype;
	}


}
