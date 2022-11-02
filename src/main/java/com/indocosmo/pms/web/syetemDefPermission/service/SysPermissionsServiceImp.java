package com.indocosmo.pms.web.syetemDefPermission.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indocosmo.pms.web.syetemDefPermission.dao.SysPermissionsDAO;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.syetemDefPermission.model.UserGroupPermission;

@Service
public class SysPermissionsServiceImp implements SysPermissionsService {

	
	@Autowired
	SysPermissionsDAO sysPermissionsDAO;
	
	@Transactional
	public List<SysPermissions> getPermissionsList() {
		// TODO Auto-generated method stub
		return sysPermissionsDAO.getPermissionsList();
	}

	@Transactional
	public List<UserGroupPermission> getPermissionsGrupList(int gid) {
		// TODO Auto-generated method stub
		return sysPermissionsDAO.getPermissionsGrupList(gid);
	}

	@Transactional
	public int groupPermissionListUpdate(
			List<UserGroupPermission> userGroupPermissionList) {
		// TODO Auto-generated method stub
		return sysPermissionsDAO.groupPermissionListUpdate(userGroupPermissionList);
	}

}
