package com.indocosmo.pms.web.reservation.model;

import java.math.BigDecimal;
import java.util.Date;

import com.indocosmo.pms.enumerator.RoomOccupancyStatus;

public class ResvDtl implements Cloneable {

	private int rowIndex;

	private int resvDtlNo;

	private int resvNo;

	private int roomTypeId;

	private String roomTypeCode;

	private Date arrDate;

	private Date departDate;

	private byte numNights;// tinyint

	private short numRooms; // smallint

	private byte rateType; // tinyint

	private int rateId;

	private String rateCode;

	private String rateDescription;

	private byte occupancy;// tiny

	private int discId;

	private String discCode;

	private boolean discIsPc;

	private boolean discIsOpen;

	private BigDecimal discAmount;

	private BigDecimal discPc;

	private int isDirty;

	private int isDeleted;

	private String beanStatus; // Nc = new , C = change , D = delete

	private String combinedStatus; // C = combined , CD = combine delete

	private int chngeresvDtlNo;

	public String getCombinedStatus() {
		return combinedStatus;
	}

	public void setCombinedStatus(String combinedStatus) {
		this.combinedStatus = combinedStatus;
	}

	public int getChngeresvDtlNo() {
		return chngeresvDtlNo;
	}

	public void setChngeresvDtlNo(int chngeresvDtlNo) {
		this.chngeresvDtlNo = chngeresvDtlNo;
	}

	// private int room

	public String getBeanStatus() {
		return beanStatus;
	}

	public void setBeanStatus(String beanStatus) {
		this.beanStatus = beanStatus;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getIsDirty() {
		return isDirty;
	}

	public void setIsDirty(int isDirty) {
		this.isDirty = isDirty;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public int getResvDtlNo() {
		return resvDtlNo;
	}

	public void setResvDtlNo(int resvDtlNo) {
		this.resvDtlNo = resvDtlNo;
	}

	public int getResvNo() {
		return resvNo;
	}

	public void setResvNo(int resvNo) {
		this.resvNo = resvNo;
	}

	public int getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public String getRoomTypeCode() {
		return roomTypeCode;
	}

	public void setRoomTypeCode(String roomTypeCode) {
		this.roomTypeCode = roomTypeCode;
	}

	public Date getArrDate() {
		return arrDate;
	}

	public void setArrDate(Date arrDate) {
		this.arrDate = arrDate;
	}

	public Date getDepartDate() {
		return departDate;
	}

	public void setDepartDate(Date departDate) {
		this.departDate = departDate;
	}

	public byte getNumNights() {
		return numNights;
	}

	public void setNumNights(byte numNights) {
		this.numNights = numNights;
	}

	public short getNumRooms() {
		return numRooms;
	}

	public void setNumRooms(short numRooms) {
		this.numRooms = numRooms;
	}

	public byte getRateType() {
		return rateType;
	}

	public void setRateType(byte rateType) {
		this.rateType = rateType;
	}

	public int getRateId() {
		return rateId;
	}

	public void setRateId(int rateId) {
		this.rateId = rateId;
	}

	public String getRateCode() {
		return rateCode;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	public String getRateDescription() {
		return rateDescription;
	}

	public void setRateDescription(String rateDescription) {
		this.rateDescription = rateDescription;
	}

	public byte getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(byte occupancy) {
		this.occupancy = occupancy;
	}

	public int getDiscId() {
		return discId;
	}

	public void setDiscId(int discId) {
		this.discId = discId;
	}

	public String getDiscCode() {
		return discCode;
	}

	public void setDiscCode(String discCode) {
		this.discCode = discCode;
	}

	public boolean getDiscIsPc() {
		return discIsPc;
	}

	public void setDiscIsPc(boolean discIsPc) {
		this.discIsPc = discIsPc;
	}

	public boolean getDiscIsOpen() {
		return discIsOpen;
	}

	public void setDiscIsOpen(boolean discIsOpen) {
		this.discIsOpen = discIsOpen;
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

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	

}
