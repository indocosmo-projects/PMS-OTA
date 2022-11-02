package com.indocosmo.pms.web.syetemDefPermission.service;


import java.util.List;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.syetemDefPermission.model.UserGroupPermission;

public interface SysPermissionsService {

	List<SysPermissions> getPermissionsList();

	List<UserGroupPermission> getPermissionsGrupList(int gid);

	int groupPermissionListUpdate(
			List<UserGroupPermission> userGroupPermissionList);

}
