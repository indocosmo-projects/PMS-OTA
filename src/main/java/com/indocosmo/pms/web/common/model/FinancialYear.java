
package com.indocosmo.pms.web.common.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "financial_year")
public class FinancialYear {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "fin_code")
	private String finCode;

	@Column(name = "from_date")
	private Date fromDate;

	@Column(name = "to_date")
	private Date toDate;

	@Column(name = "use_code")
	private boolean useCode;

	@Column(name = "use_prefix")
	private boolean usePrefix;

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

	public String getFinCode() {
		return finCode;
	}

	public void setFinCode(String finCode) {
		this.finCode = finCode;
	}

	public boolean isUseCode() {
		return useCode;
	}

	public void setUseCode(boolean useCode) {
		this.useCode = useCode;
	}

	public boolean isUsePrefix() {
		return usePrefix;
	}

	public void setUsePrefix(boolean usePrefix) {
		this.usePrefix = usePrefix;
	}

	/**
	 * @return the isSystem
	 */

}
