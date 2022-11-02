package com.indocosmo.pms.web.reservation.model;

import java.math.BigDecimal;

public class ResvRate {

	private int roomTypeIndex;

	private int id;

	private int resvRoomNo;

	private byte night;// tiny

	private BigDecimal roomCharge;

	private boolean includeTax;

	private BigDecimal serviceChargePc;

	private BigDecimal tax1Pc;

	private BigDecimal tax2Pc;

	private BigDecimal tax3Pc;

	private BigDecimal tax4Pc;

	private BigDecimal serviceCharge;

	private BigDecimal tax1;

	private BigDecimal tax2;

	private BigDecimal tax3;

	private BigDecimal tax4;

	private BigDecimal netAmount;

	private BigDecimal baseAdjustment;

	private BigDecimal baseAmount;

	private int rateStatus;

	public BigDecimal getBaseAmount() {
		return baseAmount;
	}

	public void setBaseAmount(BigDecimal baseAmount) {
		this.baseAmount = baseAmount;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public BigDecimal getBaseAdjustment() {
		return baseAdjustment;
	}

	public void setBaseAdjustment(BigDecimal baseAdjustment) {
		this.baseAdjustment = baseAdjustment;
	}

	public int getRoomTypeIndex() {
		return roomTypeIndex;
	}

	public void setRoomTypeIndex(int roomTypeIndex) {
		this.roomTypeIndex = roomTypeIndex;
	}

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

	public int getRateStatus() {
		return rateStatus;
	}

	public void setRateStatus(int rateStatus) {
		this.rateStatus = rateStatus;
	}

	public BigDecimal getServiceChargePc() {
		return serviceChargePc;
	}

	public void setServiceChargePc(BigDecimal serviceChargePc) {
		this.serviceChargePc = serviceChargePc;
	}

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

}
