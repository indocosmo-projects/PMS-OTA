package com.indocosmo.pms.web.reservation.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "resv_rate")
public class ResvRateModel {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "resv_room_no")
	private int resvRoomNo;

	@Column(name = "night")
	private byte night;// tiny

	@Column(name = "room_charge")
	private BigDecimal roomCharge;

	@Column(name = "include_tax")
	private boolean includeTax;

	@Column(name = "tax1_pc")
	private BigDecimal tax1Pc;

	@Column(name = "tax2_pc")
	private BigDecimal tax2Pc;

	@Column(name = "tax3_pc")
	private BigDecimal tax3Pc;

	@Column(name = "tax4_pc")
	private BigDecimal tax4Pc;

	@Column(name = "tax1")
	private BigDecimal tax1;

	@Column(name = "tax2")
	private BigDecimal tax2;

	@Column(name = "tax3")
	private BigDecimal tax3;

	@Column(name = "tax4")
	private BigDecimal tax4;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getResvRoomNo() {
		return resvRoomNo;
	}

	public void setResvRoomNo(int resvRoomNo) {
		this.resvRoomNo = resvRoomNo;
	}

	public byte getNight() {
		return night;
	}

	public void setNight(byte night) {
		this.night = night;
	}

	public BigDecimal getRoomCharge() {
		return roomCharge;
	}

	public void setRoomCharge(BigDecimal roomCharge) {
		this.roomCharge = roomCharge;
	}

	public boolean getIncludeTax() {
		return includeTax;
	}

	public void setIncludeTax(boolean includeTax) {
		this.includeTax = includeTax;
	}

	public BigDecimal getTax1Pc() {
		return tax1Pc;
	}

	public void setTax1Pc(BigDecimal tax1Pc) {
		this.tax1Pc = tax1Pc;
	}

	public BigDecimal getTax2Pc() {
		return tax2Pc;
	}

	public void setTax2Pc(BigDecimal tax2Pc) {
		this.tax2Pc = tax2Pc;
	}

	public BigDecimal getTax3Pc() {
		return tax3Pc;
	}

	public void setTax3Pc(BigDecimal tax3Pc) {
		this.tax3Pc = tax3Pc;
	}

	public BigDecimal getTax4Pc() {
		return tax4Pc;
	}

	public void setTax4Pc(BigDecimal tax4Pc) {
		this.tax4Pc = tax4Pc;
	}

	public BigDecimal getTax1() {
		return tax1;
	}

	public void setTax1(BigDecimal tax1) {
		this.tax1 = tax1;
	}

	public BigDecimal getTax2() {
		return tax2;
	}

	public void setTax2(BigDecimal tax2) {
		this.tax2 = tax2;
	}

	public BigDecimal getTax3() {
		return tax3;
	}

	public void setTax3(BigDecimal tax3) {
		this.tax3 = tax3;
	}

	public BigDecimal getTax4() {
		return tax4;
	}

	public void setTax4(BigDecimal tax4) {
		this.tax4 = tax4;
	}

}
