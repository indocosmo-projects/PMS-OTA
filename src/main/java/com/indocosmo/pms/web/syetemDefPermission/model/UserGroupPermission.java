package com.indocosmo.pms.web.syetemDefPermission.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_group_permission")
public class UserGroupPermission {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "user_group_id")
	private int userGroupId;
	
	@Column(name = "code")
	private String code;
	
 

	@Column(name = "sysdef_permission_id")
	private int sysdefPermissionId;
	
	
	
	@Column(name = "can_view")
	private boolean canView;
	
	@Column(name = "can_add")
	private boolean canAdd;
	
	@Column(name = "can_edit")
	private boolean canEdit;
	
	@Column(name = "can_delete")
	private boolean canDelete;
	
	@Column(name = "can_execute")
	private boolean canExecute;
	
	
	@Column(name = "can_export")
	private boolean canExport;
	
	
	@Column(name = "is_system")
	private boolean isSystem;
	
	
	@Column(name = "last_upd_ts",updatable = false)
	private Date lastUpdTs;


	public int getId() {
		return id;
	}


	public int getUserGroupId() {
		return userGroupId;
	}


	public int getSysdefPermissionId() {
		return sysdefPermissionId;
	}


	public boolean isCanView() {
		return canView;
	}


	public boolean isCanAdd() {
		return canAdd;
	}


	public boolean isCanEdit() {
		return canEdit;
	}


	public boolean isCanDelete() {
		return canDelete;
	}


	public boolean isCanExecute() {
		return canExecute;
	}


	public boolean isCanExport() {
		return canExport;
	}


	public boolean isSystem() {
		return isSystem;
	}


	public Date getLastUpdTs() {
		return lastUpdTs;
	}


	public void setId(int id) {
		this.id = id;
	}


	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}


	public void setSysdefPermissionId(int sysdefPermissionId) {
		this.sysdefPermissionId = sysdefPermissionId;
	}


	public void setCanView(boolean canView) {
		this.canView = canView;
	}


	public void setCanAdd(boolean canAdd) {
		this.canAdd = canAdd;
	}


	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}


	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}


	public void setCanExecute(boolean canExecute) {
		this.canExecute = canExecute;
	}


	public void setCanExport(boolean canExport) {
		this.canExport = canExport;
	}


	public void setSystem(boolean isSystem) {
		this.isSystem = isSystem;
	}


	public void setLastUpdTs(Date lastUpdTs) {
		this.lastUpdTs = lastUpdTs;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}
	

	
	 
	
	 
	
}
