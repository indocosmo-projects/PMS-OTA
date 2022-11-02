package com.indocosmo.pms.web.reservation_test.model;

import java.math.BigDecimal;
import java.util.Date;

public class ResvRate {

	private int id;
	private int resvRoomNo;
	private Date nightDate;
	private Byte night;
	private  BigDecimal roomCharge;
	private  boolean includeTax;
	private  BigDecimal tax1Pc;
	private  BigDecimal tax2Pc;
	private  BigDecimal tax3Pc;
	private  BigDecimal tax4Pc;
	private  BigDecimal serviceChargePc;
	private  BigDecimal tax1;
	private  BigDecimal tax2;
	private  BigDecimal tax3;
	private  BigDecimal tax4;
	private  BigDecimal serviceCharge;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the resvRoomNo
	 */
	public int getResvRoomNo() {
		return resvRoomNo;
	}

	/**
	 * @param resvRoomNo the resvRoomNo to set
	 */
	public void setResvRoomNo(int resvRoomNo) {
		this.resvRoomNo = resvRoomNo;
	}

	/**
	 * @return the nightDate
	 */
	public Date getNightDate() {
		return nightDate;
	}

	/**
	 * @param nightDate the nightDate to set
	 */
	public void setNightDate(Date nightDate) {
		this.nightDate = nightDate;
	}

	/**
	 * @return the night
	 */
	public Byte getNight() {
		return night;
	}

	/**
	 * @param night the night to set
	 */
	public void setNight(Byte night) {
		this.night = night;
	}

	/**
	 * @return the roomCharge
	 */
	public BigDecimal getRoomCharge() {
		return roomCharge;
	}

	/**
	 * @param roomCharge the roomCharge to set
	 */
	public void setRoomCharge(BigDecimal roomCharge) {
		this.roomCharge = roomCharge;
	}

	/**
	 * @return the includeTax
	 */
	public boolean isIncludeTax() {
		return includeTax;
	}

	/**
	 * @param includeTax the includeTax to set
	 */
	public void setIncludeTax(boolean includeTax) {
		this.includeTax = includeTax;
	}

	/**
	 * @return the tax1Pc
	 */
	public BigDecimal getTax1Pc() {
		return tax1Pc;
	}

	/**
	 * @param tax1Pc the tax1Pc to set
	 */
	public void setTax1Pc(BigDecimal tax1Pc) {
		this.tax1Pc = tax1Pc;
	}

	/**
	 * @return the tax2Pc
	 */
	public BigDecimal getTax2Pc() {
		return tax2Pc;
	}

	/**
	 * @param tax2Pc the tax2Pc to set
	 */
	public void setTax2Pc(BigDecimal tax2Pc) {
		this.tax2Pc = tax2Pc;
	}

	/**
	 * @return the tax3Pc
	 */
	public BigDecimal getTax3Pc() {
		return tax3Pc;
	}

	/**
	 * @param tax3Pc the tax3Pc to set
	 */
	public void setTax3Pc(BigDecimal tax3Pc) {
		this.tax3Pc = tax3Pc;
	}

	/**
	 * @return the tax4Pc
	 */
	public BigDecimal getTax4Pc() {
		return tax4Pc;
	}

	/**
	 * @param tax4Pc the tax4Pc to set
	 */
	public void setTax4Pc(BigDecimal tax4Pc) {
		this.tax4Pc = tax4Pc;
	}

	/**
	 * @return the serviceChargePc
	 */
	public BigDecimal getServiceChargePc() {
		return serviceChargePc;
	}

	/**
	 * @param serviceChargePc the serviceChargePc to set
	 */
	public void setServiceChargePc(BigDecimal serviceChargePc) {
		this.serviceChargePc = serviceChargePc;
	}

	/**
	 * @return the tax1
	 */
	public BigDecimal getTax1() {
		return tax1;
	}

	/**
	 * @param tax1 the tax1 to set
	 */
	public void setTax1(BigDecimal tax1) {
		this.tax1 = tax1;
	}

	/**
	 * @return the tax2
	 */
	public BigDecimal getTax2() {
		return tax2;
	}

	/**
	 * @param tax2 the tax2 to set
	 */
	public void setTax2(BigDecimal tax2) {
		this.tax2 = tax2;
	}

	/**
	 * @return the tax3
	 */
	public BigDecimal getTax3() {
		return tax3;
	}

	/**
	 * @param tax3 the tax3 to set
	 */
	public void setTax3(BigDecimal tax3) {
		this.tax3 = tax3;
	}

	/**
	 * @return the tax4
	 */
	public BigDecimal getTax4() {
		return tax4;
	}

	/**
	 * @param tax4 the tax4 to set
	 */
	public void setTax4(BigDecimal tax4) {
		this.tax4 = tax4;
	}

	/**
	 * @return the serviceCharge
	 */
	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	/**
	 * @param serviceCharge the serviceCharge to set
	 */
	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
}
