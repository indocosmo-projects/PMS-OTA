package com.indocosmo.pms.web.ota.entity.hotel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hoteldetails")
public class HotelInfo {
	
	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;
	
	@Column(name = "hotelcode")
	private String hotelcode;
	
	@Column(name = "username")
	private String username;


	@Column(name = "hotelname")
	private String hotelname;

	@Column(name = "authkey")
	private String authkey;

	@Column(name = "is_deleted")
	private int isdeleted;

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(int isdeleted) {
		this.isdeleted = isdeleted;
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

	public String getAuthkey() {
		return authkey;
	}

	public void setAuthkey(String authkey) {
		this.authkey = authkey;
	}	
	
}
