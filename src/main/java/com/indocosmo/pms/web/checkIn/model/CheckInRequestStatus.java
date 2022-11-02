package com.indocosmo.pms.web.checkIn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "checkin_request_status")
public class CheckInRequestStatus {

	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;

	@Column(name = "checkin_request_id")
	private int checkInReequestId;

	@Column(name = "date")
	private String date;

	@Column(name = "process_status")
	private int processStatus;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "req_time")
	private String reqTime;

	@Column(name = "is_req_completed")
	private boolean isReqCompleted;

	@Column(name = "is_canceled")
	private Boolean canceled;

	@Column(name = "facility_provider")
	private int provider;

	@Column(name = "checkinhdr_remark")
	private String checkinhdrremark;

	@Transient
	private int checkInNo;

	@Transient
	private int facilityId;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the checkInReequestId
	 */
	public int getCheckInReequestId() {
		return checkInReequestId;
	}

	/**
	 * @param checkInReequestId
	 *            the checkInReequestId to set
	 */
	public void setCheckInReequestId(int checkInReequestId) {
		this.checkInReequestId = checkInReequestId;
	}

	/**
	 * @return the date
	 */

	public int getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(int processStatus) {
		this.processStatus = processStatus;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public boolean isReqCompleted() {
		return isReqCompleted;
	}

	public void setReqCompleted(boolean isReqCompleted) {
		this.isReqCompleted = isReqCompleted;
	}

	public Boolean getCanceled() {
		return canceled;
	}

	public void setCanceled(Boolean canceled) {
		this.canceled = canceled;
	}

	public int getProvider() {
		return provider;
	}

	public void setProvider(int provider) {
		this.provider = provider;
	}

	public String getCheckinhdrremark() {
		return checkinhdrremark;
	}

	public void setCheckinhdrremark(String checkinhdrremark) {
		this.checkinhdrremark = checkinhdrremark;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getCheckInNo() {
		return checkInNo;
	}

	public void setCheckInNo(int checkInNo) {
		this.checkInNo = checkInNo;
	}

	public int getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}

}
