package com.indocosmo.pms.web.userGroup.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.indocosmo.pms.util.DbConnection;

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
import com.indocosmo.pms.web.userGroup.model.UserGroup;

@Repository
public class UserGroupDAOImpl implements UserGroupDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(UserGroupDAOImpl.class);

	public List<UserGroup> getUserGroupList() {
		List<UserGroup> userGroupList = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			String query = "select * from user_groups where is_deleted=0";
			Query qry = session.createSQLQuery(query).addEntity(UserGroup.class);
			userGroupList = qry.list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : save," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return userGroupList;
	}

	public boolean save(UserGroup userGroup) {
		boolean isSave = true;

		try {
			Session session = sessionFactory.getCurrentSession();

			if (userGroup.getId() != 0) {
				session.update(userGroup);
			} else {
				session.save(userGroup);
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
		//	String sql = "Select * from users WHERE user_group_id=" + id;
			Session session = sessionFactory.getCurrentSession();
			UserGroup userGroup = (UserGroup) session.load(UserGroup.class, id);
			userGroup.setDeleted(true);
			session.update(userGroup);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : delete  , " + Throwables.getStackTraceAsString(ex));
			isDeleted = false;
			throw new CustomException();
		}

		return isDeleted;
	}

	/**
	 * code Exist checking in users table
	 * 
	 * @param code
	 * @return true when code is exist / false when code is not exist
	 */
	public boolean codeExist(String code) throws Exception {
		boolean isCodeExist = false;

		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(UserGroup.class);
			criteria.add(Restrictions.eq("code", code));
			criteria.add(Restrictions.eq("isDeleted", false));

			@SuppressWarnings("rawtypes")
			List listOfData = criteria.list();

			if (!listOfData.isEmpty()) {
				isCodeExist = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : codeExist  , " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return isCodeExist;
	}

	public int getCount(int id) {
		int count = 0;
		DbConnection dbConnection = null;
		ResultSet rs = null;
		dbConnection = new DbConnection();
		PreparedStatement countQuery = null;
		Connection connection = dbConnection.getConnection();
		String sql = "SELECT COUNT(users.user_group_id) as count FROM users WHERE users.is_deleted=0 AND user_group_id="
				+ id;
		try {
			countQuery = connection.prepareStatement(sql);
			rs = countQuery.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(countQuery);
			dbConnection.releaseResource(rs);
		}
		return count;
	}
}
