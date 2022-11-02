
package com.indocosmo.pms.web.common.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "rate_dist")
public class RateDist {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	@JoinColumn(name = "rate_hdr_id")
	private RateHdr rate_hdr;

	@Column(name = "department_id")
	private int departmentId;

	@Column(name = "single_pc")
	private BigDecimal singlePc;

	@Column(name = "double_pc")
	private BigDecimal doublePc;

	@Column(name = "triple_pc")
	private BigDecimal triplePc;

	@Column(name = "quad_pc")
	private BigDecimal quadPc;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@JsonBackReference
	public RateHdr getRateHdr() {
		return rate_hdr;
	}

	public void setRateHdr(RateHdr rateHdr) {
		this.rate_hdr = rateHdr;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public RateHdr getRate_hdr() {
		return rate_hdr;
	}

	public void setRate_hdr(RateHdr rate_hdr) {
		this.rate_hdr = rate_hdr;
	}

	public BigDecimal getSinglePc() {
		return singlePc;
	}

	public void setSinglePc(BigDecimal singlePc) {
		this.singlePc = singlePc;
	}

	public BigDecimal getDoublePc() {
		return doublePc;
	}

	public void setDoublePc(BigDecimal doublePc) {
		this.doublePc = doublePc;
	}

	public BigDecimal getTriplePc() {
		return triplePc;
	}

	public void setTriplePc(BigDecimal triplePc) {
		this.triplePc = triplePc;
	}

	public BigDecimal getQuadPc() {
		return quadPc;
	}

	public void setQuadPc(BigDecimal quadPc) {
		this.quadPc = quadPc;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
