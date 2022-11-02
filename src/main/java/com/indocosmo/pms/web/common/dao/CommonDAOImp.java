package com.indocosmo.pms.web.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.common.model.CommunicationDetails;

@Repository
public class CommonDAOImp implements CommonDAO {

	DbConnection dbConnection = null;

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(CommonDAOImp.class);

	/**
	 * discount table access for discount list
	 * 
	 * @param startLimit
	 * @param endLimit
	 * @param advanceSearchMap
	 * @param sortVal
	 * @param simpleSearchMap
	 * @return list of records from discount table
	 */

	public CommonDAOImp() {
		dbConnection = new DbConnection();
	}

	@Override
	public Boolean getStatus(String table, String fieldName, int taxTypeId, String getFieldName) {
		// TODO Auto-generated method stub
		Boolean taxStatus = true;
		PreparedStatement preparedStatement = null;
		Connection connection = (Connection) dbConnection.getConnection();
		ResultSet resultSet = null;

		try {
			String sql = "select * FROM " + table + "  where " + fieldName + "=" + taxTypeId;
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				taxStatus = resultSet.getBoolean(getFieldName);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return taxStatus;
	}

	@Override
	public int getCount(String tableName, String fieldName, int value, int id) {
		// TODO Auto-generated method stub
		int count = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "";
			if (id != 0) {
				hql = "select count(*) from " + tableName + " where " + fieldName + "=" + value + " AND is_deleted='"
						+ false + "' AND id !=" + id;

			} else {
				hql = "select count(*) from " + tableName + " where " + fieldName + "=" + value + " AND is_deleted='"
						+ false + "'";
			}
			count = Integer.parseInt(session.createQuery(hql).list().get(0).toString());

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getCount  , " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return count;
	}

	public boolean saveCommunicationDetails(CommunicationDetails commDtls) throws Exception {
		boolean isSave = true;
		try {
			Session session = sessionFactory.getCurrentSession();
			if (commDtls.getId() != 0) {
				session.update(commDtls);
			} else {
				session.save(commDtls);
			}
		} catch (Exception e) {
			isSave = false;
			e.printStackTrace();
			logger.error("Method : save ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return isSave;
	}

	public boolean saveCommunicationDetailsMail(CommunicationDetails commDtlMail) throws Exception {
		boolean isSave = true;
		try {
			Session session = sessionFactory.getCurrentSession();
			if (commDtlMail.getId() != 0) {
				session.update(commDtlMail);
			} else {
				session.save(commDtlMail);

			}
		} catch (Exception e) {
			isSave = false;
			e.printStackTrace();
			logger.error("Method : save ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return isSave;
	}

	public List<CommunicationDetails> getCommunicationData(int id, String group) throws Exception {
		List<CommunicationDetails> communicationList = null;
		String wherePart = "";
		if (group.equals("checkIn")) {
			wherePart = " where checkIn_no=" + id;
		} else {
			wherePart = " where resv_no=" + id;
		}
		try {
			Session session = sessionFactory.getCurrentSession();
			String query = "select * from communication_dtl" + wherePart + "";
			Query qry = session.createSQLQuery(query).addEntity(CommunicationDetails.class);
			communicationList = qry.list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getCommunicationData," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return communicationList;
	}

	public JsonArray getStateList(String nationality) throws Exception {
		JsonArray jArray = new JsonArray();
		JsonObject jObj = null;
		ResultSet rs = null;
		PreparedStatement AllShift = null;
		Connection connection = dbConnection.getConnection();
		String query = "select * from states where country_id=" + nationality;

		try {
			AllShift = connection.prepareStatement(query);
			rs = AllShift.executeQuery();
			while (rs.next()) {
				jObj = new JsonObject();
				jObj.addProperty("id", rs.getInt("id"));
				jObj.addProperty("name", rs.getString("name"));
				jArray.add(jObj);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(AllShift);
		}
		return jArray;
	}

}