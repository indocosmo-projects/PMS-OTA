package com.indocosmo.pms.web.currency.model;

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
@Table(name = "currency")
public class Currency {

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

	@Column(name = "symbol")
	private String symbol;

	@Column(name = "fraction_name")
	private String fractionName;

	@Column(name = "fraction_symbol")
	private String fractionSymbol;

	@Column(name = "decimal_places")
	private int decimalPlaces;

	@Column(name = "exchange_rate")
	private BigDecimal exchangeRate;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	@Column(name = "is_system")
	private boolean is_System;

	public boolean isIs_System() {
		return is_System;
	}

	public void setIs_System(boolean is_System) {
		this.is_System = is_System;
	}

	@Column(name = "last_upd_ts", updatable = false)
	private Date lastUpdates;

	@Transient
	private String encryption;

	@Transient
	private String dateFormat;

	@Transient
	private int rowCount;

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

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getFractionName() {
		return fractionName;
	}

	public void setFractionName(String fractionName) {
		this.fractionName = fractionName;
	}

	public String getFractionSymbol() {
		return fractionSymbol;
	}

	public void setFractionSymbol(String fractionSymbol) {
		this.fractionSymbol = fractionSymbol;
	}

	public int getDecimalPlaces() {
		return decimalPlaces;
	}

	public void setDecimalPlaces(int decimalPlaces) {
		this.decimalPlaces = decimalPlaces;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getLastUpdates() {
		return lastUpdates;
	}

	public void setLastUpdates(Date lastUpdates) {
		this.lastUpdates = lastUpdates;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public String getEncryption() {
		return encryption;
	}

	public void setEncryption(String encryption) {

		this.encryption = encryption;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

}
