package com.indocosmo.pms.web.accountMaster.model;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "acc_mst")
public class AccountMaster {

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

	@Column(name = "credit_days")
	private int creditDays;

	@Column(name = "sysdef_acc_type_id")
	private int sysdef_acc_type_id;

	@Column(name = "tax_id")
	private int tax_id;

	@Transient
	private String tax_code;

	@Transient
	private String sysdef_acc_type;

	@Column(name = "is_tax_included")
	private boolean is_tax_included;

	@Column(name = "service_charge")
	private BigDecimal serviceCharge;

	@Column(name = "hsn_code")
	private int hsn_code;

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public int getCreditDays() {
		return creditDays;
	}

	public void setCreditDays(int creditDays) {
		this.creditDays = creditDays;
	}

	@Column(name = "is_system")
	private boolean is_system;

	@Column(name = "is_deleted")
	private boolean is_deleted;

	@Column(name = "last_upd_ts", updatable = false)
	private Date last_upd_ts;

	@Transient
	private String encryption;

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

	public int getSysdef_acc_type_id() {
		return sysdef_acc_type_id;
	}

	public void setSysdef_acc_type_id(int sysdef_acc_type_id) {
		this.sysdef_acc_type_id = sysdef_acc_type_id;
	}

	public int getTax_id() {
		return tax_id;
	}

	public void setTax_id(int tax_id) {
		this.tax_id = tax_id;
	}

	public boolean isIs_tax_included() {
		return is_tax_included;
	}

	public void setIs_tax_included(boolean is_tax_included) {
		this.is_tax_included = is_tax_included;
	}

	public boolean isIs_system() {
		return is_system;
	}

	public void setIs_system(boolean is_system) {
		this.is_system = is_system;
	}

	public boolean isIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	public Date getLast_upd_ts() {
		return last_upd_ts;
	}

	public void setLast_upd_ts(Date last_upd_ts) {
		this.last_upd_ts = last_upd_ts;
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

	public int getHsn_code() {
		return hsn_code;
	}

	public void setHsn_code(int hsn_code) {
		this.hsn_code = hsn_code;
	}

	public String getTax_code() {
		return tax_code;
	}

	public void setTax_code(String tax_code) {
		this.tax_code = tax_code;
	}

	public String getSysdef_acc_type() {
		return sysdef_acc_type;
	}

	public void setSysdef_acc_type(String sysdef_acc_type) {
		this.sysdef_acc_type = sysdef_acc_type;
	}
}
