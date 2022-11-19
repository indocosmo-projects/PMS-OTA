package com.indocosmo.pms.web.ota.entity.room;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "otaroomratetypes")
public class OTARoomRateTypes {
	
	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id ;
	
	@Column(name = "roomtypesid")
	private String roomtypesid ;
	
	@Column(name = "roomtypesname")
	private String roomtypesname;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoomtypesid() {
		return roomtypesid;
	}

	public void setRoomtypesid(String roomtypesid) {
		this.roomtypesid = roomtypesid;
	}

	public String getRoomtypesname() {
		return roomtypesname;
	}

	public void setRoomtypesname(String roomtypesname) {
		this.roomtypesname = roomtypesname;
	}

	
}
