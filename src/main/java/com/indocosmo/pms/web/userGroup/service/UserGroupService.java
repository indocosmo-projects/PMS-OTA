package com.indocosmo.pms.web.userGroup.service;

import java.util.List;

import com.indocosmo.pms.web.userGroup.model.UserGroup;

public interface UserGroupService {

	public List<UserGroup> getUserGroupList();

	public boolean save(UserGroup userGroup);

	public boolean delete(int id);

	public boolean codeExist(String code) throws Exception;

	public int canDelete(int id);
}
