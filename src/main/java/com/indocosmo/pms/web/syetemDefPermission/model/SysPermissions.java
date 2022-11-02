package com.indocosmo.pms.web.syetemDefPermission.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "sysdef_permission")
public class SysPermissions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "system_group")
	private String systemGroup;
	
	@Column(name = "file_path")
	private String filePath;
	
	@Column(name = "is_view_applicable")
	private boolean is_view_applicable;
	 


	@Column(name = "is_add_applicable")
	private boolean is_add_applicable;
	
	@Column(name = "is_edit_applicable")
	private boolean is_edit_applicable;
	
	@Column(name = "is_delete_applicable")
	private boolean is_delete_applicable;
	
	@Column(name = "is_execute_applicable")
	private boolean is_execute_applicable;
	
	@Column(name = "is_export_applicable")
	private boolean is_export_applicable;
	
	/*
	@Column(name = "last_upd_ts",updatable = false)
	private Date last_upd_ts;*/
	
	@Transient
	private boolean canView;
	
	@Transient
	private boolean canAdd;
	
	@Transient
	private boolean canEdit;
	
	@Transient
	private boolean canDelete;
	
	@Transient
	private boolean canExecute;
	
	@Transient
	private boolean canExport;
	
	@Transient
	private int userGroupId;
	
	@Transient
	private int userGroupPermissionId;

	public int getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getSystemGroup() {
		return systemGroup;
	}

	public String getFilePath() {
		return filePath;
	}

	public boolean isIs_view_applicable() {
		return is_view_applicable;
	}

	public boolean isIs_add_applicable() {
		return is_add_applicable;
	}

	public boolean isIs_edit_applicable() {
		return is_edit_applicable;
	}

	public boolean isIs_delete_applicable() {
		return is_delete_applicable;
	}

	public boolean isIs_execute_applicable() {
		return is_execute_applicable;
	}

	public boolean isIs_export_applicable() {
		return is_export_applicable;
	}

	/*public Date getLast_upd_ts() {
		return last_upd_ts;
	}*/

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

	public int getUserGroupId() {
		return userGroupId;
	}

	public int getUserGroupPermissionId() {
		return userGroupPermissionId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSystemGroup(String systemGroup) {
		this.systemGroup = systemGroup;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setIs_view_applicable(boolean is_view_applicable) {
		this.is_view_applicable = is_view_applicable;
	}

	public void setIs_add_applicable(boolean is_add_applicable) {
		this.is_add_applicable = is_add_applicable;
	}

	public void setIs_edit_applicable(boolean is_edit_applicable) {
		this.is_edit_applicable = is_edit_applicable;
	}

	public void setIs_delete_applicable(boolean is_delete_applicable) {
		this.is_delete_applicable = is_delete_applicable;
	}

	public void setIs_execute_applicable(boolean is_execute_applicable) {
		this.is_execute_applicable = is_execute_applicable;
	}

	public void setIs_export_applicable(boolean is_export_applicable) {
		this.is_export_applicable = is_export_applicable;
	}

	/*public void setLast_upd_ts(Date last_upd_ts) {
		this.last_upd_ts = last_upd_ts;
	}*/

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

	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}

	public void setUserGroupPermissionId(int userGroupPermissionId) {
		this.userGroupPermissionId = userGroupPermissionId;
	}
	
	
	
	
	
	
	 
 

 

	

	
	 
	
	 
	
}
