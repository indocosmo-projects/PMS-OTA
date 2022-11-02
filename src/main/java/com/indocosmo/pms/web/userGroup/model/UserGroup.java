package com.indocosmo.pms.web.userGroup.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_groups")
public class UserGroup {
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

	@Column(name = "is_deleted")
	private boolean isDeleted;

	@Column(name = "is_system")
	private boolean isSystem;

	@Column(name = "last_upd_ts", updatable = false)
	private Date lastUpdates;

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

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public boolean isIsSystem() {
		return isSystem;
	}

	public void setIsSystem(boolean is_System) {
		this.isSystem = is_System;
	}

	public Date getLastUpdates() {
		return lastUpdates;
	}

	public void setLastUpdates(Date lastUpdates) {
		this.lastUpdates = lastUpdates;
	}

}
