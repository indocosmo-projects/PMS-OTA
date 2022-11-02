package com.indocosmo.pms.web.login.service;

import java.util.List;

import com.indocosmo.pms.web.login.model.User;

public interface LoginService {
	public List checkLogin(User userForm) throws Exception;
}
