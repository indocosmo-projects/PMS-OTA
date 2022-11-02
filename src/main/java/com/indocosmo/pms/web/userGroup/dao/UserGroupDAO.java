package com.indocosmo.pms.web.userGroup.dao;

import java.util.List;

import com.indocosmo.pms.web.userGroup.model.UserGroup;

public interface UserGroupDAO {
	public List<UserGroup> getUserGroupList();

	public boolean save(UserGroup userGroup);

	public boolean delete(int id);

	public boolean codeExist(String code) throws Exception;

	public int getCount(int id);
}
