package com.indocosmo.pms.web.syetemDefPermission.dao;

import java.util.List;

import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.syetemDefPermission.model.UserGroupPermission;

public interface SysPermissionsDAO {

	List<SysPermissions> getPermissionsList();

	List<UserGroupPermission> getPermissionsGrupList(int gid);

	int groupPermissionListUpdate(
			List<UserGroupPermission> userGroupPermissionList);

}
