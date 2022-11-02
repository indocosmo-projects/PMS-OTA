package com.indocosmo.pms.web.syetemDefPermission.model;

import java.util.List;

public class SysPermissionsByGroup {
	
	private String sysgrpLabel;
	private List<SysPermissions> functionName;
	
	public String getSysgrpLabel() {
		return sysgrpLabel;
	}
	public List<SysPermissions> getFunctionName() {
		return functionName;
	}
	public void setSysgrpLabel(String sysgrpLabel) {
		this.sysgrpLabel = sysgrpLabel;
	}
	public void setFunctionName(List<SysPermissions> functionName) {
		this.functionName = functionName;
	}


}
