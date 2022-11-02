package com.indocosmo.pms.web.reservation;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


//@Entity
//@Table(name="checkin_rate")
public class CheckinRate {
	
	@Column(name="id")
	private int id;
	
	@Column(name="checkin_dtl_no")
	private int checkin_dtl_no;
	
	@Column(name="night")
	private int night;//tiny
	
	@Column(name="room_charge")
	private BigDecimal room_charge;
	
	@Column(name="include_tax")
	private boolean include_tax;
	
	@Column(name="tax1_pc")
	private BigDecimal tax1_pc;

	@Column(name="tax2_pc")
	private BigDecimal tax2_pc;
	
	@Column(name="tax3_pc")
	private BigDecimal tax3_pc;
	
	@Column(name="tax4_pc")
	private BigDecimal tax4_pc;
	
	@Column(name="tax1")
	private BigDecimal tax1;
	
	@Column(name="tax2")
	private BigDecimal tax2;

	@Column(name="tax3")
	private BigDecimal tax3;

	@Column(name="tax4")
	private BigDecimal tax4;
	

}
