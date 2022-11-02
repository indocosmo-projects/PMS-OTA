package com.indocosmo.pms.web.checkOut.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Transient;

public class CheckOutDetails {
	private int checkinNo;
	private int folioBindNo;
	private int folioNo;
	private int resvNo;
	private byte status; // tinyint
	private String roomNumber;
	private String firstName;
	private String lastName;
	private String corporateName;
	private BigDecimal folioBalance;
	private Date expDepartDate;
	private Date actDepartDate;
	public String getRoomTypeCode() {
		return roomTypeCode;
	}

	public void setRoomTypeCode(String roomTypeCode) {
		this.roomTypeCode = roomTypeCode;
	}

	private Date actDepartTime;// datetime
	private int checkOutBy;
	private Date checkOutAt;// datetime
	private String billingInstruction;
    private int floorId;
    private String roomTypeCode;
    
    
    
    
	
	@Transient
    private boolean isDiscount;
	@Transient
	private String encryCheckinNo;
	@Transient
	private String encryFolioBindNo;
	@Transient
	private String encryFolioNo;
	@Transient
	private String encryResvNo;
	@Transient
	private String encryRoomNo;
	@Transient
	private boolean rcPostStatus;
	@Transient
	private boolean rcDiscntStatus;
	
	public boolean isRcDiscntStatus() {
		return rcDiscntStatus;
	}

	public void setRcDiscntStatus(boolean rcDiscntStatus) {
		this.rcDiscntStatus = rcDiscntStatus;
	}

	public boolean isDiscount() {
		return isDiscount;
	}

	public void setDiscount(boolean isDiscount) {
		this.isDiscount = isDiscount;
	}

	public int getCheckinNo() {
		return checkinNo;
	}

	public void setCheckinNo(int checkinNo) {
		this.checkinNo = checkinNo;
	}

	public int getFolioBindNo() {
		return folioBindNo;
	}

	public void setFolioBindNo(int folioBindNo) {
		this.folioBindNo = folioBindNo;
	}

	public int getFolioNo() {
		return folioNo;
	}

	public void setFolioNo(int folioNo) {
		this.folioNo = folioNo;
	}

	public int getResvNo() {
		return resvNo;
	}

	public void setResvNo(int resvNo) {
		this.resvNo = resvNo;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCorporateName() {
		return corporateName;
	}

	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}

	public BigDecimal getFolioBalance() {
		return folioBalance;
	}

	public void setFolioBalance(BigDecimal folioBalance) {
		this.folioBalance = folioBalance;
	}

	public Date getExpDepartDate() {
		return expDepartDate;
	}

	public void setExpDepartDate(Date expDepartDate) {
		this.expDepartDate = expDepartDate;
	}

	public String getEncryCheckinNo() {
		return encryCheckinNo;
	}

	public void setEncryCheckinNo(String encryCheckinNo) {
		this.encryCheckinNo = encryCheckinNo;
	}

	public String getEncryFolioBindNo() {
		return encryFolioBindNo;
	}

	public void setEncryFolioBindNo(String encryFolioBindNo) {
		this.encryFolioBindNo = encryFolioBindNo;
	}

	public String getEncryFolioNo() {
		return encryFolioNo;
	}

	public void setEncryFolioNo(String encryFolioNo) {
		this.encryFolioNo = encryFolioNo;
	}

	public String getEncryResvNo() {
		return encryResvNo;
	}

	public void setEncryResvNo(String encryResvNo) {
		this.encryResvNo = encryResvNo;
	}

	public String getEncryRoomNo() {
		return encryRoomNo;
	}

	public void setEncryRoomNo(String encryRoomNo) {
		this.encryRoomNo = encryRoomNo;
	}

	public Date getActDepartDate() {
		return actDepartDate;
	}

	public void setActDepartDate(Date actDepartDate) {
		this.actDepartDate = actDepartDate;
	}

	public Date getActDepartTime() {
		return actDepartTime;
	}

	public void setActDepartTime(Date actDepartTime) {
		this.actDepartTime = actDepartTime;
	}

	public int getCheckOutBy() {
		return checkOutBy;
	}

	public void setCheckOutBy(int checkOutBy) {
		this.checkOutBy = checkOutBy;
	}

	public Date getCheckOutAt() {
		return checkOutAt;
	}

	public void setCheckOutAt(Date checkOutAt) {
		this.checkOutAt = checkOutAt;
	}

	public String getBillingInstruction() {
		return billingInstruction;
	}

	public void setBillingInstruction(String billingInstruction) {
		this.billingInstruction = billingInstruction;
	}

	public boolean isRcPostStatus() {
		return rcPostStatus;
	}

	public void setRcPostStatus(boolean rcPostStatus) {
		this.rcPostStatus = rcPostStatus;
	}

	public int getFloorId() {
		return floorId;
	}

	public void setFloorId(int floorId) {
		this.floorId = floorId;
	}
	
}
