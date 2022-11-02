package com.indocosmo.pms.web.login.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "users")
public class User {
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private int id;

	@Column(name = "LOGIN_ID")
	private String loginId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "USER_GROUP_ID")
	private int userGroupId;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "IS_ACTIVE")
	private boolean isActive;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "LASTLOGIN_DATE")
	private String lastloginDate;

	@Column(name = "IS_DELETED")
	private boolean isDeleted;

	@Column(name = "is_admin")
	private boolean isAdmin;

	@Column(name = "is_cashier")
	private boolean isCashier;

	@Transient
	private String userGroup;

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Column(name = "is_system")
	private int isSystem;

	@Transient
	private String perPage;

	@Transient
	private String txtSearch;

	@Transient
	private String pageNumber;

	@Transient
	private int totalRecords;

	@Transient
	private List<String> pageSizes;

	@Transient
	private int endLimit;

	@Transient
	private int startLimit;

	@Transient
	private int currentPage;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public boolean getIsCashier() {
		return isCashier;
	}

	public void setIsCashier(boolean isCashier) {
		this.isCashier = isCashier;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean getIsCashier(boolean isCashier) {
		return isCashier;
	}

	public boolean isCashier() {
		return isCashier;
	}

	public void setCashier(boolean isCashier) {
		this.isCashier = isCashier;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastloginDate() {
		return lastloginDate;
	}

	public void setLastloginDate(String lastloginDate) {
		this.lastloginDate = lastloginDate;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(int isSystem) {
		this.isSystem = isSystem;
	}

	public String getPerPage() {
		return perPage;
	}

	public void setPerPage(String perPage) {
		this.perPage = perPage;
	}

	public String getTxtSearch() {
		return txtSearch;
	}

	public void setTxtSearch(String txtSearch) {
		this.txtSearch = txtSearch;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<String> getPageSizes() {
		return pageSizes;
	}

	public void setPageSizes(List<String> pageSizes) {
		this.pageSizes = pageSizes;
	}

	public int getEndLimit() {
		return endLimit;
	}

	public void setEndLimit(int endLimit) {
		this.endLimit = endLimit;
	}

	public int getStartLimit() {
		return startLimit;
	}

	public void setStartLimit(int startLimit) {
		this.startLimit = startLimit;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

}
