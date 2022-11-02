package com.indocosmo.pms.web.ota.entity.reservation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "otacancelreservation")
public class OTACancelReservation {
	
	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;
	
	@Column(name = "locationid")
	private String locationid ;
	
	@Column(name = "reservationid")
	private int reservationid;
	
	@Column(name = "status")
    private String status ;
	
	@Column(name = "canceldatetime")
    private String canceldatetime ;
	
	@Column(name = "remark")
    private String remark ;
	
	@Column(name = "voucherno")
    private String voucherno ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public int getReservationid() {
		return reservationid;
	}

	public void setReservationid(int reservationid) {
		this.reservationid = reservationid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCanceldatetime() {
		return canceldatetime;
	}

	public void setCanceldatetime(String canceldatetime) {
		this.canceldatetime = canceldatetime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVoucherno() {
		return voucherno;
	}

	public void setVoucherno(String voucherno) {
		this.voucherno = voucherno;
	}
    	
   
}
