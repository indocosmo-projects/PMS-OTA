package com.indocosmo.pms.web.checkIn.model;

import java.math.BigDecimal;
import java.util.Date;

public class CheckInRate {
	private int id;
	private int checkinDtlNo;
	private byte night;// tiny
	private Date nightDate;
	private BigDecimal roomCharge;
	private boolean includeTax;
	private BigDecimal tax1Pc;
	private BigDecimal tax2Pc;
	private BigDecimal tax3Pc;
	private BigDecimal tax4Pc;
	private BigDecimal tax1;
	private BigDecimal tax2;
	private BigDecimal tax3;
	private BigDecimal tax4;
	private BigDecimal serviceChargePc;
	private BigDecimal serviceCharge;
	private int rateId;
	private int roomType;
	private byte occupancy;
	private int discId;
	private BigDecimal discAmount;
	private BigDecimal discPc;
	private int resvRoomNo;
	private String roomNumber;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCheckinDtlNo() {
		return checkinDtlNo;
	}

	public void setCheckinDtlNo(int checkinDtlNo) {
		this.checkinDtlNo = checkinDtlNo;
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

	public int getResvRoomNo() {
		return resvRoomNo;
	}

	public void setResvRoomNo(int resvRoomNo) {
		this.resvRoomNo = resvRoomNo;
	}

	public int getRateId() {
		return rateId;
	}

	public void setRateId(int rateId) {
		this.rateId = rateId;
	}

	public int getDiscId() {
		return discId;
	}

	public void setDiscId(int discId) {
		this.discId = discId;
	}

	public BigDecimal getDiscAmount() {
		return discAmount;
	}

	public void setDiscAmount(BigDecimal discAmount) {
		this.discAmount = discAmount;
	}

	public BigDecimal getDiscPc() {
		return discPc;
	}

	public void setDiscPc(BigDecimal discPc) {
		this.discPc = discPc;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	/**
	 * @return the roomType
	 */
	public int getRoomType() {
		return roomType;
	}

	/**
	 * @param roomType
	 *            the roomType to set
	 */
	public void setRoomType(int roomType) {
		this.roomType = roomType;
	}

	/**
	 * @return the occupancy
	 */
	public byte getOccupancy() {
		return occupancy;
	}

	/**
	 * @param occupancy
	 *            the occupancy to set
	 */
	public void setOccupancy(byte occupancy) {
		this.occupancy = occupancy;
	}

	/**
	 * @return the nightDate
	 */
	public Date getNightDate() {
		return nightDate;
	}

	/**
	 * @param nightDate
	 *            the nightDate to set
	 */
	public void setNightDate(Date nightDate) {
		this.nightDate = nightDate;
	}
}