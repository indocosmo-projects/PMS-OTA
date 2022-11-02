
package com.indocosmo.pms.web.common.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rate_period")
public class RatePeriod {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "code")
	private String code;

	@Column(name = "from_date")
	private Date fromDate;

	@Column(name = "to_date")
	private Date toDate;

	@Column(name = "is_system")
	private boolean system;

	@Column(name = "is_deleted")
	private boolean deleted;

	@Column(name = "last_upd_ts")
	private Date lastUpdTs;

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
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the isSystem
	 */
	public boolean isSystem() {
		return system;
	}

	/**
	 * @param isSystem
	 *            the isSystem to set
	 */
	public void setSystem(boolean system) {
		this.system = system;
	}

	/**
	 * @return the isDeleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * @param isDeleted
	 *            the isDeleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the lastUpdTs
	 */
	public Date getLastUpdTs() {
		return lastUpdTs;
	}

	/**
	 * @param lastUpdTs
	 *            the lastUpdTs to set
	 */
	public void setLastUpdTs(Date lastUpdTs) {
		this.lastUpdTs = lastUpdTs;
	}
}
