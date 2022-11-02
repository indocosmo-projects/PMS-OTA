package com.indocosmo.pms.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;
import com.indocosmo.pms.web.exception.CustomException;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class DbConnection {

	public static final Logger logger = LoggerFactory.getLogger(DbConnection.class);

	public Connection getConnection() {
		Connection connection = null;

		ResourceBundle dataBaseProperty = ResourceBundle.getBundle("database");
		String url = dataBaseProperty.getString("jdbc.url");
		String userName = dataBaseProperty.getString("jdbc.username");
		String passWord = dataBaseProperty.getString("jdbc.password");
		String driverClass = dataBaseProperty.getString("jdbc.driverClassName");

		try {
			try {
				Class.forName(driverClass);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			connection = DriverManager.getConnection(url, userName, passWord);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("method:getConnection" + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return connection;
	}

	public void releaseResource(Object resource) {
		try {
			if (resource instanceof Statement) {
				((Statement) resource).close();
			} else if (resource instanceof PreparedStatement) {
				((PreparedStatement) resource).close();
			} else if (resource instanceof CallableStatement) {
				((CallableStatement) resource).close();
			} else if (resource instanceof ResultSet) {
				((ResultSet) resource).close();
			} else if (resource instanceof Connection) {
				((Connection) resource).close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("method: releaseResource" + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
}