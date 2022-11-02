package com.indocosmo.pms.web.user.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.userGroup.model.UserGroup;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	public List<User> getUserList() {
		List<User> userGroupList = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			String query = "select * from users where is_deleted=0 and is_admin!=1";
			Query qry = session.createSQLQuery(query).addEntity(User.class);
			userGroupList = qry.list();
			for (User user : userGroupList) {
				String userGroupName = getUserGroupName(user.getUserGroupId());
				user.setUserGroup(userGroupName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : save," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return userGroupList;
	}

	public boolean save(User user) {
		boolean isSave = true;

		try {
			Session session = sessionFactory.getCurrentSession();

			if (user.getId() != 0) {
				session.update(user);
			} else {
				session.save(user);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : save  , " + Throwables.getStackTraceAsString(ex));
			isSave = false;
			throw new CustomException();
		}

		return isSave;
	}

	public boolean delete(int id) {
		boolean isDeleted = true;

		try {
			Session session = sessionFactory.getCurrentSession();
			User user = (User) session.load(User.class, id);
			user.setDeleted(true);
			session.update(user);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : delete  , " + Throwables.getStackTraceAsString(ex));
			isDeleted = false;
			throw new CustomException();
		}

		return isDeleted;
	}

	/**
	 * user Exist checking in users table
	 * 
	 * @param user
	 * @return true when code is exist / false when code is not exist
	 */
	public boolean userExist(String user) throws Exception {
		boolean isUserExist = false;

		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("loginId", user));
			criteria.add(Restrictions.eq("isDeleted", false));

			@SuppressWarnings("rawtypes")
			List listOfData = criteria.list();

			if (!listOfData.isEmpty()) {
				isUserExist = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : codeExist  , " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return isUserExist;
	}

	public List<User> getUser() {
		List<User> userGrop = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			String query = "select * from users where is_deleted=0";
			Query qry = session.createSQLQuery(query).addEntity(User.class);
			userGrop = qry.list();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : save," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return userGrop;
	}

	public String getUserGroupName(int id) {
		Session session = sessionFactory.getCurrentSession();
		UserGroup userGroup = (UserGroup) session.get(UserGroup.class, id);
		return userGroup.getName();
	}
}