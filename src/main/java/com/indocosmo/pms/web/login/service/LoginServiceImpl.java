package com.indocosmo.pms.web.login.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.indocosmo.pms.web.login.dao.LoginDAO;
import com.indocosmo.pms.web.login.model.User;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginDAO loginDao;

	public static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Transactional
	public List checkLogin(User userForm) throws Exception {
		List users = null;
		users = loginDao.checkLogin(userForm);
		return users;
	}
}
