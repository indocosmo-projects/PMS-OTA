package com.indocosmo.pms.web.reservation_test.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Transient;

public class ResvDtl {

	private int resvDtlNo;
	private int resvNo;
	private int roomTypeId;
	private String roomTypeCode;
	private Date arrDate;
	private Date departDate;	
	private byte numNights;
	private byte numRooms;
	private byte rateType;
	private int rateId;
	private String rateCode;
	private String rateDescription;
	private byte occupancy;
	private int discId;
	private String discCode;
	private boolean discIsPc;
	private boolean discIsOpen;
	private double discAmount;
	private double discPc;
    private String guestName;
    
	@Transient
	BigDecimal openDiscount;

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

	/**
	 * @return the resvNo
	 */
	public int getResvNo() {
		return resvNo;
	}

	/**
	 * @param resvNo the resvNo to set
	 */
	public void setResvNo(int resvNo) {
		this.resvNo = resvNo;
	}

	/**
	 * @return the roomTypeId
	 */
	public int getRoomTypeId() {
		return roomTypeId;
	}

	/**
	 * @param roomTypeId the roomTypeId to set
	 */
	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	/**
	 * @return the roomTypeCode
	 */
	public String getRoomTypeCode() {
		return roomTypeCode;
	}

	/**
	 * @param roomTypeCode the roomTypeCode to set
	 */
	public void setRoomTypeCode(String roomTypeCode) {
		this.roomTypeCode = roomTypeCode;
	}

	/**
	 * @return the arrDate
	 */
	public Date getArrDate() {
		return arrDate;
	}

	/**
	 * @param arrDate the arrDate to set
	 */
	public void setArrDate(Date arrDate) {
		this.arrDate = arrDate;
	}

	/**
	 * @return the departDate
	 */
	public Date getDepartDate() {
		return departDate;
	}

	/**
	 * @param departDate the departDate to set
	 */
	public void setDepartDate(Date departDate) {
		this.departDate = departDate;
	}

	/**
	 * @return the numNights
	 */
	public byte getNumNights() {
		return numNights;
	}

	/**
	 * @param numNights the numNights to set
	 */
	public void setNumNights(byte numNights) {
		this.numNights = numNights;
	}

	/**
	 * @return the numRooms
	 */
	public byte getNumRooms() {
		return numRooms;
	}

	/**
	 * @param numRooms the numRooms to set
	 */
	public void setNumRooms(byte numRooms) {
		this.numRooms = numRooms;
	}

	/**
	 * @return the rateType
	 */
	public byte getRateType() {
		return rateType;
	}

	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(byte rateType) {
		this.rateType = rateType;
	}

	/**
	 * @return the rateId
	 */
	public int getRateId() {
		return rateId;
	}

	/**
	 * @param rateId the rateId to set
	 */
	public void setRateId(int rateId) {
		this.rateId = rateId;
	}

	/**
	 * @return the rateCode
	 */
	public String getRateCode() {
		return rateCode;
	}

	/**
	 * @param rateCode the rateCode to set
	 */
	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	/**
	 * @return the rateDescription
	 */
	public String getRateDescription() {
		return rateDescription;
	}

	/**
	 * @param rateDescription the rateDescription to set
	 */
	public void setRateDescription(String rateDescription) {
		this.rateDescription = rateDescription;
	}

	/**
	 * @return the occupancy
	 */
	public byte getOccupancy() {
		return occupancy;
	}

	/**
	 * @param occupancy the occupancy to set
	 */
	public void setOccupancy(byte occupancy) {
		this.occupancy = occupancy;
	}

	/**
	 * @return the discId
	 */
	public int getDiscId() {
		return discId;
	}

	/**
	 * @param discId the discId to set
	 */
	public void setDiscId(int discId) {
		this.discId = discId;
	}

	/**
	 * @return the discCode
	 */
	public String getDiscCode() {
		return discCode;
	}

	/**
	 * @param discCode the discCode to set
	 */
	public void setDiscCode(String discCode) {
		this.discCode = discCode;
	}

	/**
	 * @return the discIsPc
	 */
	public boolean isDiscIsPc() {
		return discIsPc;
	}

	/**
	 * @param discIsPc the discIsPc to set
	 */
	public void setDiscIsPc(boolean discIsPc) {
		this.discIsPc = discIsPc;
	}

	/**
	 * @return the discIsOpen
	 */
	public boolean isDiscIsOpen() {
		return discIsOpen;
	}

	/**
	 * @param discIsOpen the discIsOpen to set
	 */
	public void setDiscIsOpen(boolean discIsOpen) {
		this.discIsOpen = discIsOpen;
	}

	/**
	 * @return the discAmount
	 */
	public double getDiscAmount() {
		return discAmount;
	}

	/**
	 * @param discAmount the discAmount to set
	 */
	public void setDiscAmount(double discAmount) {
		this.discAmount = discAmount;
	}

	/**
	 * @return the discPc
	 */
	public double getDiscPc() {
		return discPc;
	}

	/**
	 * @param discPc the discPc to set
	 */
	public void setDiscPc(double discPc) {
		this.discPc = discPc;
	}

	/**
	 * @return the openDiscount
	 */
	public BigDecimal getOpenDiscount() {
		return openDiscount;
	}

	/**
	 * @param openDiscount the openDiscount to set
	 */
	public void setOpenDiscount(BigDecimal openDiscount) {
		this.openDiscount = openDiscount;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}


}
