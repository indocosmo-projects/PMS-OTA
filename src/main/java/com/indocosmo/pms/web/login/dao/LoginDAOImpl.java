package com.indocosmo.pms.web.login.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.login.model.User;

@Repository
public class LoginDAOImpl implements LoginDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(LoginDAOImpl.class);

	public List checkLogin(User userForm) throws Exception {
		String hql;
		Query query = null;
		List users = null;

		try {
			hql = "from User u where u.loginId=? and u.password=? and u.isActive=1 and u.isDeleted=0 ";
			query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setString(0, userForm.getName());
			query.setString(1, userForm.getPassword());
			users = query.list();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : checkLogin  , " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return users;
	}
}