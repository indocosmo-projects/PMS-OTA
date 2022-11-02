package com.indocosmo.pms.web.reservation_test.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import com.indocosmo.pms.web.checkIn.model.CheckInRequest;


public class ResvRoom {

	private int resvRoomNo;
	private int resvDtlNo;
	private String roomNumber;
	private byte roomStatus;
	private boolean isNoShow;
	private Date noshowDate;
	private Date noshowTime;
	private int noshowBy;
	private String noshowReason;
	private int guestId;
	private String firstName;
	private String lastName;
	private String guestName;
	private String gender;
	private String address;
	private String email;
	private String phone;
	private String nationality;
	private String passportNo;
	private Date passportExpiryOn;
	private String gstno;
	private String customerImage;
	private String customerIdProof;
	private String remarks;
	private int numAdults;
	private int numChildren;
	private int numInfants;
	private String cancelReason;
	
	@Transient
	private int roomTypeId;
	
	@Transient
	private int rateId;
	
	@Transient
	private int occupancy;
	
	@Transient
	private Boolean isDeleted;
	
	@Transient
	private List<CheckInRequest> checkinRequest;
	private String salutation;
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the checkinRequest
	 */
	public List<CheckInRequest> getCheckinRequest() {
		return checkinRequest;
	}

	/**
	 * @return the customerIdProof
	 */
	public String getCustomerIdProof() {
		return customerIdProof;
	}

	/**
	 * @return the customerImage
	 */
	public String getCustomerImage() {
		return customerImage;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @return the guestId
	 */
	public int getGuestId() {
		return guestId;
	}

	

	/**
	 * @return the isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * @return the noshowBy
	 */
	public int getNoshowBy() {
		return noshowBy;
	}

	/**
	 * @return the noshowDate
	 */
	public Date getNoshowDate() {
		return noshowDate;
	}

	/**
	 * @return the noshowReason
	 */
	public String getNoshowReason() {
		return noshowReason;
	}

	/**
	 * @return the noshowTime
	 */
	public Date getNoshowTime() {
		return noshowTime;
	}

	/**
	 * @return the numAdults
	 */
	public int getNumAdults() {
		return numAdults;
	}

	/**
	 * @return the numChildren
	 */
	public int getNumChildren() {
		return numChildren;
	}

	/**
	 * @return the numInfants
	 */
	public int getNumInfants() {
		return numInfants;
	}

	/**
	 * @return the occupancy
	 */
	public int getOccupancy() {
		return occupancy;
	}

	/**
	 * @return the passportExpiryOn
	 */
	public Date getPassportExpiryOn() {
		return passportExpiryOn;
	}

	/**
	 * @return the passportNo
	 */
	public String getPassportNo() {
		return passportNo;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @return the rateId
	 */
	public int getRateId() {
		return rateId;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @return the resvDtlNo
	 */
	public int getResvDtlNo() {
		return resvDtlNo;
	}

	/**
	 * @return the resvRoomNo
	 */
	public int getResvRoomNo() {
		return resvRoomNo;
	}

	/**
	 * @return the roomNumber
	 */
	public String getRoomNumber() {
		return roomNumber;
	}

	/**
	 * @return the roomStatus
	 */
	public byte getRoomStatus() {
		return roomStatus;
	}


	/**
	 * @return the roomTypeId
	 */
	public int getRoomTypeId() {
		return roomTypeId;
	}

	/**
	 * @return the isNoShow
	 */
	public boolean isNoShow() {
		return isNoShow;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @param checkinRequest the checkinRequest to set
	 */
	public void setCheckinRequest(List<CheckInRequest> checkinRequest) {
		this.checkinRequest = checkinRequest;
	}

	/**
	 * @param customerIdProof the customerIdProof to set
	 */
	public void setCustomerIdProof(String customerIdProof) {
		this.customerIdProof = customerIdProof;
	}

	/**
	 * @param customerImage the customerImage to set
	 */
	public void setCustomerImage(String customerImage) {
		this.customerImage = customerImage;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @param guestId the guestId to set
	 */
	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param nationality the nationality to set
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * @param isNoShow the isNoShow to set
	 */
	public void setNoShow(boolean isNoShow) {
		this.isNoShow = isNoShow;
	}

	/**
	 * @param noshowBy the noshowBy to set
	 */
	public void setNoshowBy(int noshowBy) {
		this.noshowBy = noshowBy;
	}

	/**
	 * @param noshowDate the noshowDate to set
	 */
	public void setNoshowDate(Date noshowDate) {
		this.noshowDate = noshowDate;
	}

	/**
	 * @param noshowReason the noshowReason to set
	 */
	public void setNoshowReason(String noshowReason) {
		this.noshowReason = noshowReason;
	}

	/**
	 * @param noshowTime the noshowTime to set
	 */
	public void setNoshowTime(Date noshowTime) {
		this.noshowTime = noshowTime;
	}

	/**
	 * @param numAdults the numAdults to set
	 */
	public void setNumAdults(int numAdults) {
		this.numAdults = numAdults;
	}

	/**
	 * @param numChildren the numChildren to set
	 */
	public void setNumChildren(int numChildren) {
		this.numChildren = numChildren;
	}

	/**
	 * @param numInfants the numInfants to set
	 */
	public void setNumInfants(int numInfants) {
		this.numInfants = numInfants;
	}

	/**
	 * @param occupancy the occupancy to set
	 */
	public void setOccupancy(int occupancy) {
		this.occupancy = occupancy;
	}

	/**
	 * @param passportExpiryOn the passportExpiryOn to set
	 */
	public void setPassportExpiryOn(Date passportExpiryOn) {
		this.passportExpiryOn = passportExpiryOn;
	}

	/**
	 * @param passportNo the passportNo to set
	 */
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @param rateId the rateId to set
	 */
	public void setRateId(int rateId) {
		this.rateId = rateId;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @param resvDtlNo the resvDtlNo to set
	 */
	public void setResvDtlNo(int resvDtlNo) {
		this.resvDtlNo = resvDtlNo;
	}

	/**
	 * @param resvRoomNo the resvRoomNo to set
	 */
	public void setResvRoomNo(int resvRoomNo) {
		this.resvRoomNo = resvRoomNo;
	}

	/**
	 * @param roomNumber the roomNumber to set
	 */
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	/**
	 * @param roomStatus the roomStatus to set
	 */
	public void setRoomStatus(byte roomStatus) {
		this.roomStatus = roomStatus;
	}

	/**
	 * @param roomTypeId the roomTypeId to set
	 */
	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	
	public String getCancelReason() {
		return cancelReason;
	}
	
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getGstno() {
		return gstno;
	}

	public void setGstno(String gstno) {
		this.gstno = gstno;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
		
	}

	public String getSalutation() {
		return salutation;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	

}
