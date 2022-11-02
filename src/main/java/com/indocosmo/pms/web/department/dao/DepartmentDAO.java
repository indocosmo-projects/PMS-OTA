package com.indocosmo.pms.web.department.dao;

import java.util.Map;

import com.indocosmo.pms.web.department.model.Department;
import com.indocosmo.pms.web.general.master.dao.MasterDao;

public interface DepartmentDAO extends MasterDao<Department> {

	public Map<Integer, String> getNameIdMap() throws Exception;

}
