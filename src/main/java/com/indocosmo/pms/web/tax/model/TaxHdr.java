package com.indocosmo.pms.web.tax.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tax_hdr")
public class TaxHdr {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "tax_indicator")
	private String indicator;

	@Column(name = "is_system")
	private boolean isSystem;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	@Column(name = "last_upd_ts", updatable = false)
	private Date lastUpdTs;

	@OneToMany(mappedBy = "tax_hdr", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TaxDtl> taxDetails = new ArrayList<TaxDtl>();

	@Transient
	private String encryption;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public boolean isSystem() {
		return isSystem;
	}

	public void setSystem(boolean isSystem) {
		this.isSystem = isSystem;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getLastUpdTs() {
		return lastUpdTs;
	}

	public void setLastUpdTs(Date lastUpdTs) {
		this.lastUpdTs = lastUpdTs;
	}

	public String getEncryption() {
		return encryption;
	}

	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}

	@JsonBackReference
	public List<TaxDtl> getTaxDetails() {
		return taxDetails;
	}

	public void setTaxDetails(List<TaxDtl> taxDetails) {
		this.taxDetails = taxDetails;
	}
}