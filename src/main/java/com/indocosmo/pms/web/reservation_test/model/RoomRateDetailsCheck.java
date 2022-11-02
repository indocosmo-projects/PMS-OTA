package com.indocosmo.pms.web.reservation_test.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Transient;

public class RoomRateDetailsCheck {
	Date arrDate;
	int rateId;
	int occupancy;
	int numRooms;
	int numNights;
	int discId;
	BigDecimal openDiscount;
	Date occupancyDate;
	int dayCount;
	double occ1Rate;
	double occ2Rate;
	double occ3Rate;
	double occ4Rate;
	private String roomTypeCode;
	private int roomTypeId;
	private boolean isOpen;
	private String rateCode;
	private boolean occ1;
	private boolean occ2;
	private boolean occ3;
	private boolean occ4;
	
	@Transient
	int resvDtlNo;
	
	public Date getArrDate() {
		return arrDate;
	}
	public void setArrDate(Date arrDate) {
		this.arrDate = arrDate;
	}
	public int getRateId() {
		return rateId;
	}
	public void setRateId(int rateId) {
		this.rateId = rateId;
	}
	public int getOccupancy() {
		return occupancy;
	}
	public void setOccupancy(int occupancy) {
		this.occupancy = occupancy;
	}
	public int getNumRooms() {
		return numRooms;
	}
	public void setNumRooms(int numRooms) {
		this.numRooms = numRooms;
	}
	public int getNumNights() {
		return numNights;
	}
	public void setNumNights(int numNights) {
		this.numNights = numNights;
	}
	public int getDiscId() {
		return discId;
	}
	public void setDiscId(int discId) {
		this.discId = discId;
	}
	public BigDecimal getOpenDiscount() {
		return openDiscount;
	}
	public void setOpenDiscount(BigDecimal openDiscount) {
		this.openDiscount = openDiscount;
	}
	/**
	 * @return the resvDtlNo
	 */
	public int getResvDtlNo() {
		return resvDtlNo;
	}
	/**
	 * @param resvDtlNo the resvDtlNo to set
	 */
	public void setResvDtlNo(int resvDtlNo) {
		this.resvDtlNo = resvDtlNo;
	}
	public Date getOccupancyDate() {
		return occupancyDate;
	}
	public void setOccupancyDate(Date occupancyDate) {
		this.occupancyDate = occupancyDate;
	}
	public int getDayCount() {
		return dayCount;
	}
	public void setDayCount(int dayCount) {
		this.dayCount = dayCount;
	}
	public double getOcc1Rate() {
		return occ1Rate;
	}
	public void setOcc1Rate(double occ1Rate) {
		this.occ1Rate = occ1Rate;
	}
	public double getOcc2Rate() {
		return occ2Rate;
	}
	public void setOcc2Rate(double occ2Rate) {
		this.occ2Rate = occ2Rate;
	}
	public double getOcc3Rate() {
		return occ3Rate;
	}
	public void setOcc3Rate(double occ3Rate) {
		this.occ3Rate = occ3Rate;
	}
	public double getOcc4Rate() {
		return occ4Rate;
	}
	public void setOcc4Rate(double occ4Rate) {
		this.occ4Rate = occ4Rate;
	}
	public String getRoomTypeCode() {
		return roomTypeCode;
	}
	public void setRoomTypeCode(String roomTypeCode) {
		this.roomTypeCode = roomTypeCode;
	}
	public int getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public boolean isOpen() {
		return isOpen;
	}
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	public String getRateCode() {
		return rateCode;
	}
	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}
	public boolean isOcc1() {
		return occ1;
	}
	public void setOcc1(boolean occ1) {
		this.occ1 = occ1;
	}
	public boolean isOcc2() {
		return occ2;
	}
	public void setOcc2(boolean occ2) {
		this.occ2 = occ2;
	}
	public boolean isOcc3() {
		return occ3;
	}
	public void setOcc3(boolean occ3) {
		this.occ3 = occ3;
	}
	public boolean isOcc4() {
		return occ4;
	}
	public void setOcc4(boolean occ4) {
		this.occ4 = occ4;
	}
	
}
