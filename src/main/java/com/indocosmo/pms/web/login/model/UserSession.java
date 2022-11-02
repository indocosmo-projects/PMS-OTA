package com.indocosmo.pms.web.login.model;

import java.util.HashMap;

import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;

public class UserSession {

	private int id;

	private String loginId;

	private String name;

	private int userGroupId;

	private String password;

	private boolean isAdmin;

	public boolean isCashier() {
		return isCashier;
	}

	public void setCashier(boolean isCashier) {
		this.isCashier = isCashier;
	}

	private boolean isCashier;

	private int isActive;

	private String email;

	private String lastloginDate;

	private int isDeleted;

	private HashMap<String, SysPermissions> permissionsList;

	public String getEmail() {
		return email;
	}

	public int getId() {
		return id;
	}

	public int getIsActive() {
		return isActive;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public String getLastloginDate() {
		return lastloginDate;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public HashMap<String, SysPermissions> getPermissionsList() {
		return permissionsList;
	}

	public int getUserGroupId() {
		return userGroupId;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setLastloginDate(String lastloginDate) {
		this.lastloginDate = lastloginDate;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPermissionsList(HashMap<String, SysPermissions> permissionsList) {
		this.permissionsList = permissionsList;
	}

	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}

}
