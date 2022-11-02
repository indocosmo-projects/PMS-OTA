package com.indocosmo.pms.web.user.service;

import java.util.List;

import com.indocosmo.pms.web.login.model.User;

public interface UserService {
	public List<User> getUserList();

	public boolean save(User user);

	public boolean delete(int id);

	public boolean userExist(String code) throws Exception;

	public List<User> getUser();
}
