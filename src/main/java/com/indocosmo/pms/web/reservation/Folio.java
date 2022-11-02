package com.indocosmo.pms.web.reservation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


//@Entity
//@Table(name="folio")
public class Folio {
	
	@Column(name="folio_no")
	private int folio_no;
	
	@Column(name="folio_bind_no")
	private int folio_bind_no;
	
	@Column(name="resv_no")
	private int resv_no;
	
	@Column(name="checkin_no")
	private int checkin_no;

	public int getFolio_no() {
		return folio_no;
	}

	public void setFolio_no(int folio_no) {
		this.folio_no = folio_no;
	}

	public int getFolio_bind_no() {
		return folio_bind_no;
	}

	public void setFolio_bind_no(int folio_bind_no) {
		this.folio_bind_no = folio_bind_no;
	}

	public int getResv_no() {
		return resv_no;
	}

	public void setResv_no(int resv_no) {
		this.resv_no = resv_no;
	}

	public int getCheckin_no() {
		return checkin_no;
	}

	public void setCheckin_no(int checkin_no) {
		this.checkin_no = checkin_no;
	}
	
	

}
