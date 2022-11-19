package com.indocosmo.pms.web.ota.entity.room;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "otaroomroomtypes")
public class OTARoomRoomTypes {
	
	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;
	
	@Column(name = "roomtypesid")
	private String roomtypesid;
	
	@Column(name = "roomtypename")
	private String roomtypename;
	
	@Column(name = "roomid")
	private String roomid ;
	
	@Column(name = "roomname")
	private String roomname;

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

	public String getRoomtypename() {
		return roomtypename;
	}

	public void setRoomtypename(String roomtypename) {
		this.roomtypename = roomtypename;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getRoomname() {
		return roomname;
	}

	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}

	
}
