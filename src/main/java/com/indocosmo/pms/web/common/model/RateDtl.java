
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
@Table(name = "rate_dtl")
public class RateDtl {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	@JoinColumn(name = "rate_hdr_id")
	private RateHdr rate_hdr;

	@Column(name = "season_id")
	private int seasonId;

	@Column(name = "single_rate")
	private BigDecimal singleRate;

	@Column(name = "single_special_rate")
	private BigDecimal singleSpecialRate;

	@Column(name = "double_rate")
	private BigDecimal doubleRate;

	@Column(name = "double_special_rate")
	private BigDecimal doubleSpecialRate;

	@Column(name = "triple_rate")
	private BigDecimal tripleRate;

	@Column(name = "triple_special_rate")
	private BigDecimal tripleSpecialRate;

	@Column(name = "quad_rate")
	private BigDecimal quadRate;

	@Column(name = "quad_special_rate")
	private BigDecimal quadSpecialRate;

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

	public int getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(int seasonId) {
		this.seasonId = seasonId;
	}

	public RateHdr getRate_hdr() {
		return rate_hdr;
	}

	public void setRate_hdr(RateHdr rate_hdr) {
		this.rate_hdr = rate_hdr;
	}

	public BigDecimal getSingleRate() {
		return singleRate;
	}

	public void setSingleRate(BigDecimal singleRate) {
		this.singleRate = singleRate;
	}

	public BigDecimal getSingleSpecialRate() {
		return singleSpecialRate;
	}

	public void setSingleSpecialRate(BigDecimal singleSpecialRate) {
		this.singleSpecialRate = singleSpecialRate;
	}

	public BigDecimal getDoubleRate() {
		return doubleRate;
	}

	public void setDoubleRate(BigDecimal doubleRate) {
		this.doubleRate = doubleRate;
	}

	public BigDecimal getDoubleSpecialRate() {
		return doubleSpecialRate;
	}

	public void setDoubleSpecialRate(BigDecimal doubleSpecialRate) {
		this.doubleSpecialRate = doubleSpecialRate;
	}

	public BigDecimal getTripleRate() {
		return tripleRate;
	}

	public void setTripleRate(BigDecimal tripleRate) {
		this.tripleRate = tripleRate;
	}

	public BigDecimal getTripleSpecialRate() {
		return tripleSpecialRate;
	}

	public void setTripleSpecialRate(BigDecimal tripleSpecialRate) {
		this.tripleSpecialRate = tripleSpecialRate;
	}

	public BigDecimal getQuadRate() {
		return quadRate;
	}

	public void setQuadRate(BigDecimal quadRate) {
		this.quadRate = quadRate;
	}

	public BigDecimal getQuadSpecialRate() {
		return quadSpecialRate;
	}

	public void setQuadSpecialRate(BigDecimal quadSpecialRate) {
		this.quadSpecialRate = quadSpecialRate;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
