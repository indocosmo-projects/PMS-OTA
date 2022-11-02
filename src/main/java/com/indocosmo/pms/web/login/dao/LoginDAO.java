package com.indocosmo.pms.web.login.dao;

import java.util.List;

import com.indocosmo.pms.web.login.model.User;

public interface LoginDAO {
	public List checkLogin(User userForm) throws Exception;
}
