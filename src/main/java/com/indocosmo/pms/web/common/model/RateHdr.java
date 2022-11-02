package com.indocosmo.pms.web.common.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OrderBy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.indocosmo.pms.enumerator.common.RateTypes;
import com.indocosmo.pms.web.roomType.model.RoomType;
import com.indocosmo.pms.web.season.model.Seasonhdr;

@Entity
@Table(name = "rate_hdr")
public class RateHdr {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "rate_type")
	private int rateType;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "corporate_id")
	private int corporateId;

	@Transient
	private String corporateCode;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "agreement_ref")
	private String agreementRef;

	@Column(name = "rate_period_id")
	private int ratePeriodId;

	@Column(name = "validity_from")
	private Date validityFrom;

	@Column(name = "validity_to")
	private Date validityTo;

	@Column(name = "room_type_id")
	private int roomTypeId;

	@ManyToOne
	@JoinColumn(name = "room_type_id", insertable = false, updatable = false)
	private RoomType roomType;

	@Column(name = "meal_plan")
	private String mealPlan;

	@Column(name = "is_tax_included")
	private boolean isTaxIncluded;

	@Column(name = "is_system")
	private boolean isSystem;

	@Column(name = "is_active")
	private boolean isActive = true;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	@Column(name = "last_upd_ts")
	private Date lastUpdTs;

	@OneToMany(mappedBy = "rate_hdr", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy(clause = "rate_hdr_id")
	private List<RateDtl> rateDetails = new ArrayList<RateDtl>();

	@OneToMany(mappedBy = "rate_hdr", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy(clause = "rate_hdr_id")
	private List<RateDist> rateDistribution = new ArrayList<RateDist>();

	@Transient
	private List<Seasonhdr> season = new ArrayList<Seasonhdr>();

	@Transient
	private String encryption;

	@Transient
	private int rowCount;

	@Transient
	private String dateFormat;

	@Column(name = "is_open")
	private boolean isOpen;

	@Column(name = "is_custom")
	private boolean isCustom;

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public List<Seasonhdr> getSeason() {
		return season;
	}

	public void setSeason(List<Seasonhdr> season) {
		this.season = season;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RateTypes getRateType() {
		for (RateTypes e : RateTypes.values()) {
			if (rateType == e.getRateType())
				return e;
		}

		return null;
	}

	public void setRateType(RateTypes rateType) {
		this.rateType = rateType.getRateType();
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

	public int getCorporateId() {
		return corporateId;
	}

	public void setCorporateId(int corporateId) {
		this.corporateId = corporateId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAgreementRef() {
		return agreementRef;
	}

	public void setAgreementRef(String agreementRef) {
		this.agreementRef = agreementRef;
	}

	public int getRatePeriodId() {
		return ratePeriodId;
	}

	public void setRatePeriodId(int ratePeriodId) {
		this.ratePeriodId = ratePeriodId;
	}

	public Date getValidityFrom() {
		return validityFrom;
	}

	public void setValidityFrom(Date validityFrom) {
		this.validityFrom = validityFrom;
	}

	public Date getValidityTo() {
		return validityTo;
	}

	public void setValidityTo(Date validityTo) {
		this.validityTo = validityTo;
	}

	public int getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public String getMealPlan() {
		return mealPlan;
	}

	public void setMealPlan(String mealPlan) {
		this.mealPlan = mealPlan;
	}

	public boolean getIsTaxIncluded() {
		return isTaxIncluded;
	}

	public void setIsTaxIncluded(boolean isTaxIncluded) {
		this.isTaxIncluded = isTaxIncluded;
	}

	public boolean getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(boolean isSystem) {
		// System.out.println("enter set");
		this.isSystem = isSystem;
	}

	public Date getLastUpdTs() {
		return lastUpdTs;
	}

	public void setLastUpdTs(Date lastUpdTs) {
		this.lastUpdTs = lastUpdTs;
	}

	@JsonBackReference
	public List<RateDtl> getRateDetails() {
		return rateDetails;
	}

	public void setRateDetails(List<RateDtl> rateDetails) {
		this.rateDetails = rateDetails;
	}

	@JsonBackReference
	public List<RateDist> getRateDistribution() {
		return rateDistribution;
	}

	public void setRateDistribution(List<RateDist> rateDistribution) {
		this.rateDistribution = rateDistribution;
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

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public boolean isCustom() {
		return isCustom;
	}

	public void setCustom(boolean isCustom) {
		this.isCustom = isCustom;
	}

	public String getCorporateCode() {
		return corporateCode;
	}

	public void setCorporateCode(String corporateCode) {
		this.corporateCode = corporateCode;
	}

}
