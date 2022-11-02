package com.indocosmo.pms.web.dashboard.model;

import java.sql.Date;

public class RoomDetails {
	int roomid;
	String roomNumber;
	String roomName;
	int invStatus;
	String invStatusName;
	int hkStatus;
	String hkStatusName;
	int occStatus;
	String occStatusName;
	int roomTypeId;
	String roomTypeCode;
	int checkinNo;
	Date arrivalDate;
	Date expDepartDate;
	Date actualDepartDate;
	int checkinStatus;
	int resvStatus;
	
	
	public int getResvStatus() {
		return resvStatus;
	}

	public void setResvStatus(int resvStatus) {
		this.resvStatus = resvStatus;
	}

	

	

	/**
	 * @return the roomNumber
	 */
	public String getRoomNumber() {
		return roomNumber;
	}

	/**
	 * @param roomNumber
	 *            the roomNumber to set
	 */
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	/**
	 * @return the roomName
	 */
	public String getRoomName() {
		return roomName;
	}

	/**
	 * @param roomName
	 *            the roomName to set
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	/**
	 * @return the invStatus
	 */
	public int getInvStatus() {
		return invStatus;
	}

	/**
	 * @param invStatus
	 *            the invStatus to set
	 */
	public void setInvStatus(int invStatus) {
		this.invStatus = invStatus;
	}

	/**
	 * @return the invStatusName
	 */
	public String getInvStatusName() {
		return invStatusName;
	}

	/**
	 * @param invStatusName
	 *            the invStatusName to set
	 */
	public void setInvStatusName(String invStatusName) {
		this.invStatusName = invStatusName;
	}

	/**
	 * @return the hkStatus
	 */
	public int getHkStatus() {
		return hkStatus;
	}

	/**
	 * @param hkStatus
	 *            the hkStatus to set
	 */
	public void setHkStatus(int hkStatus) {
		this.hkStatus = hkStatus;
	}

	/**
	 * @return the hkStatusName
	 */
	public String getHkStatusName() {
		return hkStatusName;
	}

	/**
	 * @param hkStatusName
	 *            the hkStatusName to set
	 */
	public void setHkStatusName(String hkStatusName) {
		this.hkStatusName = hkStatusName;
	}

	/**
	 * @return the occStatus
	 */
	public int getOccStatus() {
		return occStatus;
	}

	/**
	 * @param occStatus
	 *            the occStatus to set
	 */
	public void setOccStatus(int occStatus) {
		this.occStatus = occStatus;
	}

	/**
	 * @return the occStatusName
	 */
	public String getOccStatusName() {
		return occStatusName;
	}

	/**
	 * @param occStatusName
	 *            the occStatusName to set
	 */
	public void setOccStatusName(String occStatusName) {
		this.occStatusName = occStatusName;
	}

	/**
	 * @return the roomTypeId
	 */
	public int getRoomTypeId() {
		return roomTypeId;
	}

	/**
	 * @param roomTypeId
	 *            the roomTypeId to set
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
	 * @param roomTypeCode
	 *            the roomTypeCode to set
	 */
	public void setRoomTypeCode(String roomTypeCode) {
		this.roomTypeCode = roomTypeCode;
	}

	/**
	 * @return the checkinNo
	 */
	public int getCheckinNo() {
		return checkinNo;
	}

	/**
	 * @param checkinNo
	 *            the checkinNo to set
	 */
	public void setCheckinNo(int checkinNo) {
		this.checkinNo = checkinNo;
	}

	/**
	 * @return the arrivalDate
	 */
	public Date getArrivalDate() {
		return arrivalDate;
	}

	/**
	 * @param arrivalDate
	 *            the arrivalDate to set
	 */
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	/**
	 * @return the expDepartDate
	 */
	public Date getExpDepartDate() {
		return expDepartDate;
	}

	/**
	 * @param expDepartDate
	 *            the expDepartDate to set
	 */
	public void setExpDepartDate(Date expDepartDate) {
		this.expDepartDate = expDepartDate;
	}

	/**
	 * @return the actualDepartDate
	 */
	public Date getActualDepartDate() {
		return actualDepartDate;
	}

	/**
	 * @param actualDepartDate
	 *            the actualDepartDate to set
	 */
	public void setActualDepartDate(Date actualDepartDate) {
		this.actualDepartDate = actualDepartDate;
	}

	/**
	 * @return the checkinStatus
	 */
	public int getCheckinStatus() {
		return checkinStatus;
	}

	/**
	 * @param checkinStatus
	 *            the checkinStatus to set
	 */
	public void setCheckinStatus(int checkinStatus) {
		this.checkinStatus = checkinStatus;
	}

	public int getRoomid() {
		return roomid;
	}

	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}

}
