package com.indocosmo.pms.web.checkIn.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Transient;
import com.indocosmo.pms.enumerator.ReservationType;

public class CheckInHdr {
	private int checkInNo;
	private int resvNo;
	private int folioBindNo;
	private int folioNo;
	private byte status; // tinyint
	private boolean isFullySettled; // tinyint
	private byte resvType;// tiny
	private ReservationType resvTypeEnum;
	private int corporateId;
	private String corporateName;
	private String corporateAddress;
	private int roomTypeId;
	private String roomTypeCode;
	private String roomNumber;
	private Date arrDate;
	private Date arrTime;// datetime
	private Date expDepartDate;
	private Date expDepartTime;// datetime
	private Date actDepartDate;
	private Date actDepartTime;// datetime
	private byte rateType; // tinyint
	private int rateId;
	private String rateCode;
	private String resvByMail;
	private String rateDescription;
	private byte occupancy;// tiny
	private byte numAdults;
	private byte numChildren;
	private byte numInfants;
	private int discId;
	private int discType;
	private String discCode;
	private boolean discIsPc;
	private BigDecimal discAmount;
	private BigDecimal discPc;
	private String billingInstruction;
	private String specialRequests;
	private boolean discIsOpen;
	private int checkInBy;
	private int checkOutBy;
	private Date checkOutAt;// datetime
	private int guestId;
	private byte resvStatus; // tinyint
	private String folioBalance;
	private String name;
	private String phone;
	private String email;
	private String address;
	private int numNights;
	private int checkoutInviceNo;
	private int payment_source;
	private String gstno;
	private String nationality;
	private String state;
	@Transient
	private String firstName;
	@Transient
	private String lastName;

	public int getPayment_source() {
		return payment_source;
	}

	public void setPayment_source(int payment_source) {
		this.payment_source = payment_source;
	}

	@Transient
	private int dayNum;

	public int getDayNum() {
		return dayNum;
	}

	public void setDayNum(int dayNum) {
		this.dayNum = dayNum;
	}

	private int noOfRoomsToCheckIn;// for checkin purpose only

	public Date getActDepartDate() {
		return actDepartDate;
	}

	public Date getActDepartTime() {
		return actDepartTime;
	}

	public String getAddress() {
		return address;
	}

	public Date getArrDate() {
		return arrDate;
	}

	public Date getArrTime() {
		return arrTime;
	}

	public String getBillingInstruction() {
		return billingInstruction;
	}

	public int getCheckInBy() {
		return checkInBy;
	}

	/*
	 * public int getCheckInNo() { return checkInNo; }
	 */

	public int getCheckInNo() {
		return checkInNo;
	}

	/**
	 * @param resvNo
	 *            the resvNo to set
	 */
	public void setCheckInNo(int checkInNo) {
		this.checkInNo = checkInNo;
	}

	public Date getCheckOutAt() {
		return checkOutAt;
	}

	public int getCheckOutBy() {
		return checkOutBy;
	}

	public String getCorporateAddress() {
		return corporateAddress;
	}

	public int getCorporateId() {
		return corporateId;
	}

	public String getCorporateName() {
		return corporateName;
	}

	public BigDecimal getDiscAmount() {
		return discAmount;
	}

	public String getDiscCode() {
		return discCode;
	}

	public int getDiscId() {
		return discId;
	}

	public boolean getDiscIsOpen() {
		return discIsOpen;
	}

	public boolean getDiscIsPc() {
		return discIsPc;
	}

	public BigDecimal getDiscPc() {
		return discPc;
	}

	public int getDiscType() {
		return discType;
	}

	public String getEmail() {
		return email;
	}

	public Date getExpDepartDate() {
		return expDepartDate;
	}

	public Date getExpDepartTime() {
		return expDepartTime;
	}

	public String getFolioBalance() {
		return folioBalance;
	}

	public int getFolioBindNo() {
		return folioBindNo;
	}

	public int getFolioNo() {
		return folioNo;
	}

	public int getGuestId() {
		return guestId;
	}

	public String getName() {
		return name;
	}

	public int getNoOfRoomsToCheckIn() {
		return noOfRoomsToCheckIn;
	}

	public byte getNumAdults() {
		return numAdults;
	}

	public byte getNumChildren() {
		return numChildren;
	}

	/**
	 * @return the numInfants
	 */
	public byte getNumInfants() {
		return numInfants;
	}

	/**
	 * @return the numNights
	 */
	public int getNumNights() {
		return numNights;
	}

	public byte getOccupancy() {
		return occupancy;
	}

	public String getPhone() {
		return phone;
	}

	public String getRateCode() {
		return rateCode;
	}

	public String getRateDescription() {
		return rateDescription;
	}

	public int getRateId() {
		return rateId;
	}

	public byte getRateType() {
		return rateType;
	}

	public int getResvNo() {
		return resvNo;
	}

	public byte getResvStatus() {
		return resvStatus;
	}

	public byte getResvType() {
		return resvType;
	}

	public ReservationType getResvTypeEnum() {
		return resvTypeEnum;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public String getRoomTypeCode() {
		return roomTypeCode;
	}

	public int getRoomTypeId() {
		return roomTypeId;
	}

	public String getSpecialRequests() {
		return specialRequests;
	}

	public byte getStatus() {
		return status;
	}

	public boolean isFullySettled() {
		return isFullySettled;
	}

	public void setActDepartDate(Date actDepartDate) {
		this.actDepartDate = actDepartDate;
	}

	public void setActDepartTime(Date actDepartTime) {
		this.actDepartTime = actDepartTime;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setArrDate(Date arrDate) {
		this.arrDate = arrDate;
	}

	public void setArrTime(Date arrTime) {
		this.arrTime = arrTime;
	}

	public void setBillingInstruction(String billingInstruction) {
		this.billingInstruction = billingInstruction;
	}

	public void setCheckInBy(int checkInBy) {
		this.checkInBy = checkInBy;
	}

	/*
	 * public void setCheckInNo(int checkInNo) { this.checkInNo = checkInNo; }
	 */

	public void setCheckOutAt(Date checkOutAt) {
		this.checkOutAt = checkOutAt;
	}

	public void setCheckOutBy(int checkOutBy) {
		this.checkOutBy = checkOutBy;
	}

	public void setCorporateAddress(String corporateAddress) {
		this.corporateAddress = corporateAddress;
	}

	public void setCorporateId(int corporateId) {
		this.corporateId = corporateId;
	}

	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}

	public void setDiscAmount(BigDecimal discAmount) {
		this.discAmount = discAmount;
	}

	public void setDiscCode(String discCode) {
		this.discCode = discCode;
	}

	public void setDiscId(int discId) {
		this.discId = discId;
	}

	public void setDiscIsOpen(boolean discIsOpen) {
		this.discIsOpen = discIsOpen;
	}

	public void setDiscIsPc(boolean discIsPc) {
		this.discIsPc = discIsPc;
	}

	public void setDiscPc(BigDecimal discPc) {
		this.discPc = discPc;
	}

	public void setDiscType(int discType) {
		this.discType = discType;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setExpDepartDate(Date expDepartDate) {
		this.expDepartDate = expDepartDate;
	}

	public void setExpDepartTime(Date expDepartTime) {
		this.expDepartTime = expDepartTime;
	}

	public void setFolioBalance(String folioBalance) {
		this.folioBalance = folioBalance;
	}

	public void setFolioBindNo(int folioBindNo) {
		this.folioBindNo = folioBindNo;
	}

	public void setFolioNo(int folioNo) {
		this.folioNo = folioNo;
	}

	public void setFullySettled(boolean isFullySettled) {
		this.isFullySettled = isFullySettled;
	}

	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNoOfRoomsToCheckIn(int noOfRoomsToCheckIn) {
		this.noOfRoomsToCheckIn = noOfRoomsToCheckIn;
	}

	public void setNumAdults(byte numAdults) {
		this.numAdults = numAdults;
	}

	public void setNumChildren(byte numChildren) {
		this.numChildren = numChildren;
	}

	/**
	 * @param numInfants
	 *            the numInfants to set
	 */
	public void setNumInfants(byte numInfants) {
		this.numInfants = numInfants;
	}

	/**
	 * @param numNights
	 *            the numNights to set
	 */
	public void setNumNights(int numNights) {
		this.numNights = numNights;
	}

	public void setOccupancy(byte occupancy) {
		this.occupancy = occupancy;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	public void setRateDescription(String rateDescription) {
		this.rateDescription = rateDescription;
	}

	public void setRateId(int rateId) {
		this.rateId = rateId;
	}

	public void setRateType(byte rateType) {
		this.rateType = rateType;
	}

	public void setResvNo(int resvNo) {
		this.resvNo = resvNo;
	}

	public void setResvStatus(byte resvStatus) {
		this.resvStatus = resvStatus;
	}

	public void setResvType(byte resvType) {
		this.resvType = resvType;
	}

	public void setResvTypeEnum(ReservationType resvTypeEnum) {
		resvType = (byte) resvTypeEnum.getId();
		this.resvTypeEnum = resvTypeEnum;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public void setRoomTypeCode(String roomTypeCode) {
		this.roomTypeCode = roomTypeCode;
	}

	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public void setSpecialRequests(String specialRequests) {
		this.specialRequests = specialRequests;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(byte status) {
		this.status = status;
	}

	public int getCheckoutInviceNo() {
		return checkoutInviceNo;
	}

	public void setCheckoutInviceNo(int checkoutInviceNo) {
		this.checkoutInviceNo = checkoutInviceNo;
	}

	/**
	 * @return the resvByMail
	 */
	public String getResvByMail() {
		return resvByMail;
	}

	/**
	 * @param resvByMail
	 *            the resvByMail to set
	 */
	public void setResvByMail(String resvByMail) {
		this.resvByMail = resvByMail;
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

	public String getGstno() {
		return gstno;
	}

	public void setGstno(String gstno) {
		this.gstno = gstno;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}