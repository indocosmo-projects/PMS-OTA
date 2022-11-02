package com.indocosmo.pms.web.season.model;

import java.sql.Date;
import java.util.ArrayList;
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
@Table(name = "season_hdr")
public class Seasonhdr {

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

	@Column(name = "color_code")
	private String colorCode;

	@Column(name = "start_month")
	private Integer startMonth;

	@Column(name = "start_day")
	private Integer startDay;

	@Column(name = "end_month")
	private Integer endMonth;

	@Column(name = "end_day")
	private Integer endDay;

	@Column(name = "is_system")
	private boolean system;

	@Column(name = "is_deleted")
	private boolean deleted;

	@Column(name = "last_upd_ts", updatable = false)
	private Date lastUpdated;

	@Transient
	private String encryption;

	@OneToMany(mappedBy = "season_hdr", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Seasondtl> seasondetails = new ArrayList<Seasondtl>();

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

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	public Integer getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(Integer startMonth) {
		this.startMonth = startMonth;
	}

	public Integer getStartDay() {
		return startDay;
	}

	public void setStartDay(Integer startDay) {
		this.startDay = startDay;
	}

	public Integer getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(Integer endMonth) {
		this.endMonth = endMonth;
	}

	public Integer getEndDay() {
		return endDay;
	}

	public void setEndDay(Integer endDay) {
		this.endDay = endDay;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@JsonBackReference
	public List<Seasondtl> getSeasondetails() {
		return seasondetails;
	}

	public void setSeasondetails(List<Seasondtl> seasondetails) {
		this.seasondetails = seasondetails;
	}

	public String getEncryption() {
		return encryption;
	}

	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isSystem() {
		return system;
	}

	public void setSystem(boolean system) {
		this.system = system;
	}
}