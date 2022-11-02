package com.indocosmo.pms.web.common;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.indocosmo.pms.web.login.model.UserSession;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;

@Service
public class PageAccessPermissionServiceIMP implements PageAccessPermissionService {

	@Override
	public SysPermissions getPermission(HttpSession session, String code) {
		// TODO Auto-generated method stub
		UserSession userSessionData = (UserSession) session.getAttribute("userSession");
		HashMap<String, SysPermissions> permissionsList = userSessionData.getPermissionsList();
		SysPermissions sysPermissionsObj = null;
		if (permissionsList.containsKey(code)) {
			sysPermissionsObj = permissionsList.get(code);
		}

		return sysPermissionsObj;
	}

}