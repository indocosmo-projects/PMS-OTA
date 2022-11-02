package com.indocosmo.pms.web.common;

import javax.servlet.http.HttpSession;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;

public interface PageAccessPermissionService {
	SysPermissions getPermission(HttpSession session, String string);
}