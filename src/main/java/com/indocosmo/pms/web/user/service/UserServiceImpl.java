package com.indocosmo.pms.web.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.user.dao.UserDAO;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDAO userDAO;

	@Transactional
	public List<User> getUserList() {
		return userDAO.getUserList();
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean save(User user) {
		boolean isSave = false;
		isSave = userDAO.save(user);
		return isSave;
	}

	@Transactional
	public boolean delete(int id) {
		boolean isDelete = userDAO.delete(id);
		return isDelete;
	}

	@Transactional
	public boolean userExist(String code) throws Exception {
		return userDAO.userExist(code);
	}

	@Transactional
	public List<User> getUser() {
		// TODO Auto-generated method stub
		return userDAO.getUser();
	}
}
