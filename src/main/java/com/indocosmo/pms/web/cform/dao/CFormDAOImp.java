package com.indocosmo.pms.web.cform.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonObject;
import com.indocosmo.pms.util.DbConnection;

@Repository
public class CFormDAOImp implements CFormDAO {
	public static final Logger logger = LoggerFactory.getLogger(CFormDAOImp.class);

	private DbConnection dbConnection = null;

	public CFormDAOImp() {
		dbConnection = new DbConnection();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pms.web.cform.dao.CFormDAO#getDetailsCustomer(int)
	 */
	public JsonObject getDetailsCustomer(int folioNo) {

		Connection connection = dbConnection.getConnection();
		Statement statement = null;
		ResultSet rs = null;
		JsonObject jsonObject = null;
		String sqlPaymentList = "SELECT cd.first_name,cd.last_name,cd.email,cd.phone,cd.address,ch.room_number,ch.arr_date,	IFNULL(ch.act_depart_date,'') AS act_depart_date,	IFNULL(ch.invoice_no,'') AS  invoice_no,cd.gender,IFNULL(cd.nationality,'') AS nationality,	IFNULL(cd.passport_no,'') AS passport_no,IFNULL(cd.passport_expiry_on,'') AS passport_expiry_on,IFNULL(cd.remarks,'') AS remarks,ch.arr_time,IFNULL(ch.num_nights,'')AS num_nights FROM checkin_dtl cd INNER JOIN checkin_hdr ch ON ch.checkin_no = cd.checkin_no INNER JOIN folio f ON f.checkin_no = ch.checkin_no WHERE f.folio_no ="
				+ folioNo;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(sqlPaymentList);
			while (rs.next()) {
				jsonObject = new JsonObject();
				jsonObject.addProperty("name", rs.getString("first_name") + " " + rs.getString("last_name"));
				jsonObject.addProperty("email", rs.getString("email"));
				jsonObject.addProperty("phone", rs.getString("phone"));
				jsonObject.addProperty("roomNumber", rs.getString("room_number"));
				jsonObject.addProperty("address", rs.getString("address"));
				jsonObject.addProperty("arrDate", rs.getString("arr_date"));
				jsonObject.addProperty("depDate", rs.getString("act_depart_date"));
				jsonObject.addProperty("invoiceNo", rs.getInt("invoice_no"));
				jsonObject.addProperty("gender", rs.getString("gender"));
				jsonObject.addProperty("nationality", rs.getString("nationality"));
				jsonObject.addProperty("passport_no", rs.getString("passport_no"));
				jsonObject.addProperty("passport_expiry_on", rs.getString("passport_expiry_on"));
				jsonObject.addProperty("remarks", rs.getString("remarks"));
				jsonObject.addProperty("arr_time", rs.getString("arr_time"));
				jsonObject.addProperty("num_nights", rs.getString("num_nights"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
		}
		return jsonObject;
	}
}
