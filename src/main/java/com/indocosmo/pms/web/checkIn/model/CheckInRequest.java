package com.indocosmo.pms.web.checkIn.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "checkin_request")
public class CheckInRequest {
	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;

	@Column(name = "checkin_no")
	private int checkInNo;

	@Column(name = "resv_room_no")
	private int resvRoomNo;

	@Column(name = "is_one_time_request")
	private int oneTimeReq;

	@Column(name = "req_date")
	private Date reqDate;

	@Column(name = "req_Todate")
	private Date reqTodate;

	@Column(name = "req_time")
	private Timestamp reqTime;

	@Column(name = "facility_id")
	private int facilityId;

	@Column(name = "arrangement_by")
	private int arrangementBy;

	@Column(name = "req_taken_by")
	private int reqTakenBy;

	@Column(name = "req_taken_date")
	private Date reqTakenDate;

	@Column(name = "req_remarks")
	private String reqRemarks;

	@Column(name = "is_req_completed")
	private boolean isReqCompleted;

	@Column(name = "is_canceled")
	private Boolean canceled;

	@Column(name = "user_id")
	private int userId;

	/*
	 * @Column(name = "facility_provider") private int provider;
	 */

	@Transient
	private int provider;

	@Transient
	private List<String> inactiveDateRequest;

	@Transient
	private Boolean isDeleted;

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
	 * @return the checkInNo
	 */
	public int getCheckInNo() {
		return checkInNo;
	}

	/**
	 * @param checkInNo
	 *            the checkInNo to set
	 */
	public void setCheckInNo(int checkInNo) {
		this.checkInNo = checkInNo;
	}

	/**
	 * @return the resvRoomNo
	 */
	public int getResvRoomNo() {
		return resvRoomNo;
	}

	/**
	 * @param resvRoomNo
	 *            the resvRoomNo to set
	 */
	public void setResvRoomNo(int resvRoomNo) {
		this.resvRoomNo = resvRoomNo;
	}

	public int getOneTimeReq() {
		return oneTimeReq;
	}

	public void setOneTimeReq(int oneTimeReq) {
		this.oneTimeReq = oneTimeReq;
	}

	/**
	 * @return the reqDate
	 */
	public Date getReqDate() {
		return reqDate;
	}

	/**
	 * @param reqDate
	 *            the reqDate to set
	 */
	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}

	public Timestamp getReqTime() {
		return reqTime;
	}

	public void setReqTime(Timestamp reqTime) {
		this.reqTime = reqTime;
	}

	/**
	 * @return the facilityId
	 */
	public int getFacilityId() {
		return facilityId;
	}

	/**
	 * @param facilityId
	 *            the facilityId to set
	 */
	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}

	/**
	 * @return the arrangementBy
	 */
	public int getArrangementBy() {
		return arrangementBy;
	}

	/**
	 * @param arrangementBy
	 *            the arrangementBy to set
	 */
	public void setArrangementBy(int arrangementBy) {
		this.arrangementBy = arrangementBy;
	}

	/**
	 * @return the reqTakenBy
	 */
	public int getReqTakenBy() {
		return reqTakenBy;
	}

	/**
	 * @param reqTakenBy
	 *            the reqTakenBy to set
	 */
	public void setReqTakenBy(int reqTakenBy) {
		this.reqTakenBy = reqTakenBy;
	}

	/**
	 * @return the reqTakenDate
	 */
	public Date getReqTakenDate() {
		return reqTakenDate;
	}

	/**
	 * @param reqTakenDate
	 *            the reqTakenDate to set
	 */
	public void setReqTakenDate(Date reqTakenDate) {
		this.reqTakenDate = reqTakenDate;
	}

	/**
	 * @return the reqRemarks
	 */
	public String getReqRemarks() {
		return reqRemarks;
	}

	/**
	 * @param reqRemarks
	 *            the reqRemarks to set
	 */
	public void setReqRemarks(String reqRemarks) {
		this.reqRemarks = reqRemarks;
	}

	/**
	 * @return the isReqCompleted
	 */
	public boolean isReqCompleted() {
		return isReqCompleted;
	}

	/**
	 * @param isReqCompleted
	 *            the isReqCompleted to set
	 */
	public void setReqCompleted(boolean isReqCompleted) {
		this.isReqCompleted = isReqCompleted;
	}

	/**
	 * @return the canceled
	 */
	public Boolean getCanceled() {
		return canceled;
	}

	/**
	 * @param canceled
	 *            the canceled to set
	 */
	public void setCanceled(Boolean canceled) {
		this.canceled = canceled;
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

	public Date getReqTodate() {
		return reqTodate;
	}

	public void setReqTodate(Date reqTodate) {
		this.reqTodate = reqTodate;
	}

	public List<String> getInactiveDateRequest() {
		return inactiveDateRequest;
	}

	public void setInactiveDateRequest(List<String> inactiveDateRequest) {
		this.inactiveDateRequest = inactiveDateRequest;
	}

	public int getProvider() {
		return provider;
	}

	public void setProvider(int provider) {
		this.provider = provider;
	}

}
