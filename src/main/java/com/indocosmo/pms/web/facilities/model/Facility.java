package com.indocosmo.pms.web.facilities.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "facilities")
public class Facility {
	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "facility_type")
	private int facilityType;

	@Column(name = "is_payable")
	private boolean isPayable;

	@Column(name = "amount")
	private String amount;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "acc_mst_id")
	private int accMstId;

	@Column(name = "is_system")
	private boolean isSystem;

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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the facilityType
	 */
	public int getFacilityType() {
		return facilityType;
	}

	/**
	 * @param facilityType
	 *            the facilityType to set
	 */
	public void setFacilityType(int facilityType) {
		this.facilityType = facilityType;
	}

	/**
	 * @return the isPayable
	 */
	public boolean isPayable() {
		return isPayable;
	}

	/**
	 * @param isPayable
	 *            the isPayable to set
	 */
	public void setPayable(boolean isPayable) {
		this.isPayable = isPayable;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the isDeleted
	 */
	public boolean isDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted
	 *            the isDeleted to set
	 */
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
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
	 * @return the accMstId
	 */
	public int getAccMstId() {
		return accMstId;
	}

	/**
	 * @param accMstId
	 *            the accMstId to set
	 */
	public void setAccMstId(int accMstId) {
		this.accMstId = accMstId;
	}

	/**
	 * @return the isSystem
	 */
	public boolean isSystem() {
		return isSystem;
	}

	/**
	 * @param isSystem
	 *            the isSystem to set
	 */
	public void setSystem(boolean isSystem) {
		this.isSystem = isSystem;
	}

}
