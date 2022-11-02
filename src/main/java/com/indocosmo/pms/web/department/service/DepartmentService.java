package com.indocosmo.pms.web.department.service;

import com.indocosmo.pms.web.general.master.service.MasterService;

import java.util.Map;

import com.indocosmo.pms.web.department.model.Department;

public interface DepartmentService extends MasterService<Department> {

	public Map<Integer, String> getNameIdMap() throws Exception;
}
