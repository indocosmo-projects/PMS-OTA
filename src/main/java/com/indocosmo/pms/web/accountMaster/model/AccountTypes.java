package com.indocosmo.pms.web.accountMaster.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sysdef_acc_type")
public class AccountTypes {

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

	@Column(name = "can_inherit_from")
	private boolean can_inherit_from;

	@Column(name = "can_have_tax")
	private boolean can_have_tax;

	@Column(name = "last_upd_ts")
	private Date last_upd_ts;

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

	public boolean isCan_inherit_from() {
		return can_inherit_from;
	}

	public void setCan_inherit_from(boolean can_inherit_from) {
		this.can_inherit_from = can_inherit_from;
	}

	public boolean isCan_have_tax() {
		return can_have_tax;
	}

	public void setCan_have_tax(boolean can_have_tax) {
		this.can_have_tax = can_have_tax;
	}

	public Date getLast_upd_ts() {
		return last_upd_ts;
	}

	public void setLast_upd_ts(Date last_upd_ts) {
		this.last_upd_ts = last_upd_ts;
	}
}
