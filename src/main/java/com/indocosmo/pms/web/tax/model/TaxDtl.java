package com.indocosmo.pms.web.tax.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tax_dtl")
public class TaxDtl {

	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;

	@ManyToOne
	@JoinColumn(name = "tax_hdr_id")
	private TaxHdr tax_hdr;

	@Column(name = "valid_from")
	private Date validFrom;

	@Column(name = "tax1_pc")
	private BigDecimal tax1Pc;

	@Column(name = "tax2_pc")
	private BigDecimal tax2Pc;

	@Column(name = "tax3_pc")
	private BigDecimal tax3Pc;

	@Column(name = "tax4_pc")
	private BigDecimal tax4Pc;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@JsonBackReference
	public TaxHdr getTaxHdr() {
		return tax_hdr;
	}

	public void setTaxHdr(TaxHdr taxHdr) {
		this.tax_hdr = taxHdr;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public BigDecimal getTax1Pc() {
		return tax1Pc;
	}

	public void setTax1Pc(BigDecimal tax1Pc) {
		this.tax1Pc = tax1Pc;
	}

	public BigDecimal getTax2Pc() {
		return tax2Pc;
	}

	public void setTax2Pc(BigDecimal tax2Pc) {
		this.tax2Pc = tax2Pc;
	}

	public BigDecimal getTax3Pc() {
		return tax3Pc;
	}

	public void setTax3Pc(BigDecimal tax3Pc) {
		this.tax3Pc = tax3Pc;
	}

	public BigDecimal getTax4Pc() {
		return tax4Pc;
	}

	public void setTax4Pc(BigDecimal tax4Pc) {
		this.tax4Pc = tax4Pc;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}