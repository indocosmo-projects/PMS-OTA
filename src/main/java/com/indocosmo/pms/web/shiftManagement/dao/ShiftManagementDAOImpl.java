package com.indocosmo.pms.web.shiftManagement.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

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
import com.indocosmo.pms.web.shiftManagement.model.ShiftManagement;

@Repository
public class ShiftManagementDAOImpl implements ShiftManagementDAO {
	@Autowired
	private SessionFactory sessionFactory;

	DbConnection dbConnection = null;

	public ShiftManagementDAOImpl() {
		dbConnection = new DbConnection();
	}

	private static final Logger logger = LoggerFactory.getLogger(ShiftManagementDAOImpl.class);

	@Override
	public String getPassWord(int userId) {

		Connection connection = dbConnection.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		String passw = "";
		String sql = "select password from users where id=" + userId;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				passw = resultSet.getString("password");

			}

		} catch (Exception e) {
			logger.error("Method : getPassWord()" + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
		}

		return passw;
	}

	@Override
	public boolean saveOpenShift(ShiftManagement shiftmanagement) throws Exception {

		boolean isSave = true;

		try {
			Session session = sessionFactory.getCurrentSession();

			if (shiftmanagement.getId() != 0) {
				session.update(shiftmanagement);
			} else {
				session.save(shiftmanagement);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : save, " + Throwables.getStackTraceAsString(ex));
			isSave = false;
			throw new CustomException();
		}

		return isSave;
	}

	public JsonArray getcurrentUserShift() throws Exception {

		JsonArray jArray = new JsonArray();
		JsonObject jObj = null;
		ResultSet rs = null;
		PreparedStatement currentshifts = null;
	//	Encryption encryption = new Encryption();
		Connection connection = dbConnection.getConnection();
		String query = "SELECT ush.*, us.`login_id`as loginuser,sht.`code` AS shiftCode FROM user_shift ush INNER JOIN users us ON ush.user_id = us.id INNER JOIN shift sht ON ush.shift_id = sht.id WHERE closing_date IS NULL AND closing_time IS NULL";

		try {

			currentshifts = connection.prepareStatement(query);
			rs = currentshifts.executeQuery();
			while (rs.next()) {
				jObj = new JsonObject();

				jObj.addProperty("usershiftid", rs.getInt("id"));
				jObj.addProperty("user_id", rs.getInt("user_id"));
				jObj.addProperty("shift_id", rs.getInt("shift_id"));
				jObj.addProperty("opening_float", rs.getBigDecimal("opening_float"));
				jObj.addProperty("opening_date", rs.getString("opening_date"));
				jObj.addProperty("opening_time", rs.getString("opening_time").substring(0, 19));
				jObj.addProperty("loginuser", rs.getString("loginuser"));
				jObj.addProperty("shiftCode", rs.getString("shiftCode"));
				jArray.add(jObj);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(currentshifts);
			dbConnection.releaseResource(rs);
		}
		return jArray;
	}

	public JsonArray currentShiftDetails(String hotelDate) throws Exception {
		// TODO Auto-generated method stub
		JsonArray jArray = new JsonArray();
		JsonObject jObj = null;
		ResultSet rs = null;
		PreparedStatement currentShiftnumber = null;
	//	Encryption encryption = new Encryption();
		Connection connection = dbConnection.getConnection();
		String query = "SELECT shift.id, shift.`code`, shift.`name` FROM shift  WHERE shift.is_deleted = 0 AND shift.id NOT IN(SELECT  IFNULL(shift_id,0) FROM user_shift where opening_date ='"
				+ hotelDate + "' and closing_date IS NOT NULL)";
		try {
			currentShiftnumber = connection.prepareStatement(query);
			rs = currentShiftnumber.executeQuery();
			while (rs.next()) {
				jObj = new JsonObject();
				jObj.addProperty("id", rs.getInt("id"));
				jObj.addProperty("code", rs.getString("code"));
				jObj.addProperty("name", rs.getString("name"));
				jArray.add(jObj);

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(currentShiftnumber);
			dbConnection.releaseResource(rs);
		}
		return jArray;
	}

	public String isshiftOPen(String hotelDate) throws Exception {
		String count = "0";

		ResultSet rs = null;
		PreparedStatement isshift = null;
		//Encryption encryption = new Encryption();
		Connection connection = dbConnection.getConnection();

		String query = "SELECT count(id ) as count FROM user_shift  where closing_time is   NULL and opening_date='"
				+ hotelDate + "'";
		try {
			isshift = connection.prepareStatement(query);
			rs = isshift.executeQuery();
			while (rs.next()) {

				count = rs.getString("count");

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(isshift);
			dbConnection.releaseResource(rs);
		}
		return count;
	}

	public JsonArray getListShift(String hotelDate) throws Exception {

		JsonArray jarray = new JsonArray();
		JsonObject jObj = null;
		ResultSet rs = null;
		PreparedStatement usershiftDetails = null;
	//	Encryption encryption = new Encryption();
		Connection connection = dbConnection.getConnection();
		// String query="select ush.*,us.`login_id` as loginusers,sht.`code` AS
		// CodeShift from user_shift ush INNER JOIN users us ON ush.user_id = us.id
		// INNER JOIN shift sht ON ush.shift_id=sht.id WHERE
		// opening_date='"+hotelDate+"' AND sht.is_deleted=0";
		String query = "select ush.*,us.`login_id` as loginusers,sht.`code` AS CodeShift from user_shift  ush INNER JOIN users us ON ush.user_id = us.id INNER JOIN shift sht ON ush.shift_id=sht.id WHERE opening_date='"
				+ hotelDate + "'";
		try {

			usershiftDetails = connection.prepareStatement(query);
			rs = usershiftDetails.executeQuery();

			while (rs.next()) {
				jObj = new JsonObject();
				jObj.addProperty("id", rs.getInt("id"));
				jObj.addProperty("user_id", rs.getInt("user_id"));
				jObj.addProperty("shift_id", rs.getInt("shift_id"));
				jObj.addProperty("opening_float", rs.getString("opening_float"));
				jObj.addProperty("opening_date", rs.getString("opening_date"));
				jObj.addProperty("opening_time", rs.getString("opening_time"));
				// jObj.addProperty("opening_time",
				// rs.getString("opening_time").substring(0,19));

				jObj.addProperty("closing_date", rs.getString("closing_date"));
				jObj.addProperty("closing_time", rs.getString("closing_time"));

				// jObj.addProperty("closing_time",
				// rs.getString("closing_time").substring(0,19));
				jObj.addProperty("loginusers", rs.getString("loginusers"));
				jObj.addProperty("CodeShift", rs.getString("CodeShift"));

				jarray.add(jObj);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(usershiftDetails);
			dbConnection.releaseResource(rs);
		}
		return jarray;
	}

	public ShiftManagement getShiftManagementDetails(String hotelDate) {

		ShiftManagement shiftpresentdetails = null;
		Connection connection = dbConnection.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		String sql = "SELECT shift_id   FROM user_shift  where closing_time is   NULL AND opening_date='" + hotelDate
				+ "'";

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				shiftpresentdetails = new ShiftManagement();
				shiftpresentdetails.setShiftId(resultSet.getInt("shift_id"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Method: getAccMstVal, " + Throwables.getStackTraceAsString(e));
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);

		}

		return shiftpresentdetails;
	}

	public String shiftcountOndateRemain(String hotelDate) throws Exception {
		String countremaingShift = "0";

		ResultSet rs = null;
		PreparedStatement isshift = null;
	//	Encryption encryption = new Encryption();
		Connection connection = dbConnection.getConnection();

		String query = "SELECT count(shift.id) as count FROM shift  WHERE shift.is_deleted = 0 AND shift.id NOT IN(SELECT  IFNULL(shift_id,0) FROM user_shift where opening_date ='"
				+ hotelDate + "' and closing_date IS NOT NULL)";

		try {
			isshift = connection.prepareStatement(query);
			rs = isshift.executeQuery();
			while (rs.next()) {

				countremaingShift = rs.getString("count");

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(isshift);
			dbConnection.releaseResource(rs);

		}
		return countremaingShift;
	}

	public JsonArray AllShiftDetails() throws Exception {
		// TODO Auto-generated method stub
		JsonArray jArray = new JsonArray();
		JsonObject jObj = null;
		ResultSet rs = null;
		PreparedStatement AllShift = null;

		Connection connection = dbConnection.getConnection();
		String query = "select  *  from shift where is_deleted=0 ORDER BY `code` DESC";

		try {
			AllShift = connection.prepareStatement(query);
			rs = AllShift.executeQuery();
			while (rs.next()) {
				jObj = new JsonObject();
				jObj.addProperty("id", rs.getInt("id"));
				jObj.addProperty("code", rs.getString("code"));
				jObj.addProperty("name", rs.getString("name"));
				jObj.addProperty("description", rs.getString("description"));

				jArray.add(jObj);

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(AllShift);
			dbConnection.releaseResource(rs);

		}
		return jArray;
	}

	public JsonArray allShiftDetails() throws Exception {
		// TODO Auto-generated method stub
		JsonArray jArray = new JsonArray();
		JsonObject jObj = null;
		ResultSet rs = null;
		PreparedStatement AllShift = null;
//		Encryption encryption = new Encryption();
		Connection connection = dbConnection.getConnection();
		String query = "select  *  from shift where is_deleted=0 ORDER BY `code` DESC";

		try {
			AllShift = connection.prepareStatement(query);
			rs = AllShift.executeQuery();
			while (rs.next()) {
				jObj = new JsonObject();
				jObj.addProperty("id", rs.getInt("id"));
				jObj.addProperty("code", rs.getString("code"));
				jObj.addProperty("name", rs.getString("name"));
				jObj.addProperty("description", rs.getString("description"));

				jArray.add(jObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(AllShift);
			dbConnection.releaseResource(rs);

		}
		return jArray;
	}

}