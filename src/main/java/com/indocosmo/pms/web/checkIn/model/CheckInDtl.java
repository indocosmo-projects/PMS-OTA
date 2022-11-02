package com.indocosmo.pms.web.checkIn.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CheckInDtl {
	private int checkinDtlNo;
	private int checkInNo;
	private int guestId;
	private boolean isSharer;
	private String salutation;
	private String firstName;
	private String lastName;
	private String guestName;

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	private String gender;
	private String address;
	private String email;
	private String phone;
	private String nationality;
	private String state;
	private String passportNo;
	private String customerImage;
	private String customerIdProof;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date passportExpiryOn;
	private String remarks;
	private String roomNumber;
	private String roomTypeCode;
	private int rateId;
	private String encrptRateId;
	private String rateCode;
	private int resvRoomNo;
	private byte status; // tinyint
	private int resvDtlNo;
	private int roomTypeId;
	private Date arrDate;
	private byte occupancy;// tiny
	private Date departDate;
	private String gstno;

	private Boolean isDeleted = false;

	public int getCheckinDtlNo() {
		return checkinDtlNo;
	}

	public void setCheckinDtlNo(int checkinDtlNo) {
		this.checkinDtlNo = checkinDtlNo;
	}

	public int getCheckInNo() {
		return checkInNo;
	}

	public void setCheckInNo(int checkInNo) {
		this.checkInNo = checkInNo;
	}

	public int getGuestId() {
		return guestId;
	}

	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}

	public boolean getIsSharer() {
		return isSharer;
	}

	public void setIsSharer(boolean isSharer) {
		this.isSharer = isSharer;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public Date getPassportExpiryOn() {
		return passportExpiryOn;
	}

	public void setPassportExpiryOn(Date passportExpiryOn) {
		this.passportExpiryOn = passportExpiryOn;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getRoomTypeCode() {
		return roomTypeCode;
	}

	public void setRoomTypeCode(String roomTypeCode) {
		this.roomTypeCode = roomTypeCode;
	}

	public int getRateId() {
		return rateId;
	}

	public void setRateId(int rateId) {
		this.rateId = rateId;
	}

	public String getEncrptRateId() {
		return encrptRateId;
	}

	public void setEncrptRateId(String encrptRateId) {
		this.encrptRateId = encrptRateId;
	}

	public String getRateCode() {
		return rateCode;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	public int getResvRoomNo() {
		return resvRoomNo;
	}

	public void setResvRoomNo(int resvRoomNo) {
		this.resvRoomNo = resvRoomNo;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public int getResvDtlNo() {
		return resvDtlNo;
	}

	public void setResvDtlNo(int resvDtlNo) {
		this.resvDtlNo = resvDtlNo;
	}

	public int getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public Date getArrDate() {
		return arrDate;
	}

	public void setArrDate(Date arrDate) {
		this.arrDate = arrDate;
	}

	public byte getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(byte occupancy) {
		this.occupancy = occupancy;
	}

	public Date getDepartDate() {
		return departDate;
	}

	public void setDepartDate(Date departDate) {
		this.departDate = departDate;
	}

	/**
	 * @return the customerImage
	 */
	public String getCustomerImage() {
		return customerImage;
	}

	/**
	 * @param customerImage
	 *            the customerImage to set
	 */
	public void setCustomerImage(String customerImage) {
		this.customerImage = customerImage;
	}

	/**
	 * @return the customerIdProof
	 */
	public String getCustomerIdProof() {
		return customerIdProof;
	}

	/**
	 * @param customerIdProof
	 *            the customerIdProof to set
	 */
	public void setCustomerIdProof(String customerIdProof) {
		this.customerIdProof = customerIdProof;
	}

	/**
	 * @param isSharer
	 *            the isSharer to set
	 */
	public void setSharer(boolean isSharer) {
		this.isSharer = isSharer;
	}

	/**
	 * @return the isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted
	 *            the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getGstno() {
		return gstno;
	}

	public void setGstno(String gstno) {
		this.gstno = gstno;
	}

}
