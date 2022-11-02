package com.indocosmo.pms.web.discount.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "discount")
public class Discount {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "valid_from")
	private Date validFrom;

	@Column(name = "valid_to")
	private Date validTo;

	@Column(name = "disc_type")
	private int discType;

	@Column(name = "is_pc")
	private boolean isPc;

	@Column(name = "rate_id")
	private int rateId;

	@Column(name = "disc_amount")
	private BigDecimal discAmount;

	@Column(name = "disc_pc")
	private BigDecimal discPc;

	@Column(name = "is_open")
	private boolean isOpen;

	@Column(name = "is_system")
	private boolean isSystem;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	@Column(name = "last_upd_ts", updatable = false)
	private Date lastUpdates;
	
	@Column(name = "discount_for")
	private int discountFor;
	

	public int getDiscountFor() {
		return discountFor;
	}

	public void setDiscountFor(int discountFor) {
		this.discountFor = discountFor;
	}

	public String getDiscountForName() {
		return discountForName;
	}

	public void setDiscountForName(String discountForName) {
		this.discountForName = discountForName;
	}

	@Transient
	private String discountForName;

	@Transient
	private String encryption;

	@Transient
	private int rowCount;

	@Transient
	private String dateFormat;

	@Transient
	private String calcMode;

	@Transient
	private BigDecimal calAmount;

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

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

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public int getDiscType() {
		return discType;
	}

	public void setDiscType(Integer discType) {
		this.discType = discType;
	}

	public boolean getIsPc() {
		return isPc;
	}

	public void setIsPc(boolean isPc) {
		this.isPc = isPc;
	}

	public int getRateId() {
		return rateId;
	}

	public void setRateId(int rateId) {
		// System.out.print("in model"+rateId);
		this.rateId = rateId;
	}

	public BigDecimal getDiscAmount() {
		return discAmount;
	}

	public void setDiscAmount(BigDecimal discAmount) {
		this.discAmount = discAmount;
	}

	public BigDecimal getDiscPc() {
		return discPc;
	}

	public void setDiscPc(BigDecimal discPc) {
		this.discPc = discPc;
	}

	public boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public boolean isSystem() {
		return isSystem;
	}

	public void setSystem(boolean isSystem) {
		this.isSystem = isSystem;
	}

	public boolean setIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getLastUpdates() {
		return lastUpdates;
	}

	public void setLastUpdates(Date lastUpdates) {
		this.lastUpdates = lastUpdates;
	}

	public String getEncryption() {
		return encryption;
	}

	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public String getCalcMode() {
		return calcMode;
	}

	public void setCalcMode(String calcMode) {
		this.calcMode = calcMode;
	}

	public BigDecimal getCalAmount() {
		return calAmount;
	}

	public void setCalAmount(BigDecimal calAmount) {
		this.calAmount = calAmount;
	}
}
