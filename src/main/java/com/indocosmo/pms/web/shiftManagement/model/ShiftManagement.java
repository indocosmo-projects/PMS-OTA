package com.indocosmo.pms.web.shiftManagement.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user_shift")
public class ShiftManagement {
	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "shift_id")
	private int shiftId;

	@Column(name = "opening_float")
	private BigDecimal openingFloat;

	@Column(name = "opening_date")
	private String openingDate;

	@Column(name = "opening_time")
	private String openingTime;

	@Column(name = "closing_date")
	private String closingDate;

	@Column(name = "closing_time")
	private String closingTime;

	@Transient
	private String CodeShift;

	@Transient
	private String loginusers;

	@Transient
	private String encryption;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getShiftId() {
		return shiftId;
	}

	public void setShiftId(int shiftId) {
		this.shiftId = shiftId;
	}

	public BigDecimal getOpeningFloat() {
		return openingFloat;
	}

	public void setOpeningFloat(BigDecimal openingFloat) {
		this.openingFloat = openingFloat;
	}

	public String getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(String openingDate) {
		this.openingDate = openingDate;
	}

	public String getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(String openingTime) {
		this.openingTime = openingTime;
	}

	public String getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}

	public String getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(String closingTime) {
		this.closingTime = closingTime;
	}

	public String getEncryption() {
		return encryption;
	}

	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}

	public String getCodeShift() {
		return CodeShift;
	}

	public void setCodeShift(String codeShift) {
		CodeShift = codeShift;
	}

	public String getLoginusers() {
		return loginusers;
	}

	public void setLoginusers(String loginusers) {
		this.loginusers = loginusers;
	}

}
