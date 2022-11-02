package com.indocosmo.pms.web.reservation.model;

import java.util.Date;

import com.indocosmo.pms.enumerator.ReservationStatus;

public class ResvRoom {

	private int rowIndex;

	private int dtlTypeIndex;

	private int resvRoomNo;

	private String roomNumber;

	private int resvDtlNo;

	private boolean isNoshow;

	private Date noshowOn; // datetime

	private int noshowBy;

	private String noShowReason;

	private String salutation;

	private String firstName;

	private String lastName;

	private String gender;

	private String address;

	private String email;

	private String phone;

	private String nationality;

	private String passportNo;

	private Date passportExpiryOn;

	private String remarks;

	private byte roomStatus;

	private int isDirty;

	public int getIsDirty() {
		return isDirty;
	}

	public void setIsDirty(int isDirty) {
		this.isDirty = isDirty;
	}

	public int getDtlTypeIndex() {
		return dtlTypeIndex;
	}

	public void setDtlTypeIndex(int dtlTypeIndex) {
		this.dtlTypeIndex = dtlTypeIndex;
	}

	public int getResvRoomNo() {
		return resvRoomNo;
	}

	public void setResvRoomNo(int resvRoomNo) {
		this.resvRoomNo = resvRoomNo;
	}

	public int getResvDtlNo() {
		return resvDtlNo;
	}

	public void setResvDtlNo(int resvDtlNo) {
		this.resvDtlNo = resvDtlNo;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public boolean getisNoshow() {
		return isNoshow;
	}

	public void setIsNoshow(boolean isNoshow) {
		this.isNoshow = isNoshow;
	}

	public Date getNoshowOn() {
		return noshowOn;
	}

	public void setNoshowOn(Date noshowOn) {
		this.noshowOn = noshowOn;
	}

	public int getNoshowBy() {
		return noshowBy;
	}

	public void setNoshowBy(int noshowBy) {
		this.noshowBy = noshowBy;
	}

	public String getNoShowReason() {
		return noShowReason;
	}

	public void setNoShowReason(String noShowReason) {
		this.noShowReason = noShowReason;
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

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public byte getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(ReservationStatus reservationStatus) {
		this.roomStatus = reservationStatus.getCode();
	}

}
