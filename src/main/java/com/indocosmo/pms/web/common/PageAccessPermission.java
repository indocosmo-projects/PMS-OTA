package com.indocosmo.pms.web.common;

import java.util.HashMap;
import javax.servlet.http.HttpSession;
import com.indocosmo.pms.web.login.model.UserSession;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;

public class PageAccessPermission {

	private static PageAccessPermission instance = null;

	protected PageAccessPermission() {
	}

	// Lazy Initialization (If required then only)
	public static PageAccessPermission getInstance() {
		if (instance == null) {
			// Thread Safe. Might be costly operation in some case
			synchronized (PageAccessPermission.class) {
				if (instance == null) {
					instance = new PageAccessPermission();
				}
			}
		}
		return instance;
	}

	// get page permission object
	public SysPermissions getPermission(HttpSession session, String code) throws Exception {
		UserSession userSessionData = (UserSession) session.getAttribute("userSession");
		HashMap<String, SysPermissions> permissionsList = userSessionData.getPermissionsList();
		SysPermissions sysPermissionsObj = null;
		if (permissionsList.containsKey(code)) {
			sysPermissionsObj = permissionsList.get(code);
		}

		return sysPermissionsObj;
	}

	/*
	 * public static void main(String args[]){ public SysPermissions p_list =
	 * PageAccessPermission.getInstance().getPermission(session,"MST_DISCUNT"); }
	 */

}