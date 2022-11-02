package com.indocosmo.pms.web.userGroup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.indocosmo.pms.web.userGroup.dao.UserGroupDAO;
import com.indocosmo.pms.web.userGroup.model.UserGroup;

@Service
public class UserGroupServiceImpl implements UserGroupService {

	@Autowired
	UserGroupDAO userGroupDAO;

	@Transactional
	public List<UserGroup> getUserGroupList() {
		return userGroupDAO.getUserGroupList();
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean save(UserGroup userGroup) {
		boolean isSave = userGroupDAO.save(userGroup);
		return isSave;
	}

	@Transactional
	public boolean delete(int id) {
		boolean isDelete = userGroupDAO.delete(id);
		return isDelete;
	}

	@Transactional
	public boolean codeExist(String code) throws Exception {
		return userGroupDAO.codeExist(code);
	}

	@Transactional
	public int canDelete(int id) {
		int isCount = userGroupDAO.getCount(id);
		return isCount;
	}
}
