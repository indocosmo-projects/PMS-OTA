package com.indocosmo.pms.web.agingAR.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.google.gson.JsonObject;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.agingAR.model.AgingAR;
import com.indocosmo.pms.web.checkOut.dao.CheckOutDAOImp;
import com.indocosmo.pms.web.exception.CustomException;

@Repository
public class AgingARDAOImp implements AgingARDAO {

	public static final Logger logger = LoggerFactory.getLogger(CheckOutDAOImp.class);

	private DbConnection dbConnection = null;

	public AgingARDAOImp() {
		dbConnection = new DbConnection();
	}

 
	public List<AgingAR> getAgingARList() {
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet resultSet = null;
		List<AgingAR> agingARList = new ArrayList<AgingAR>();
		AgingAR agingAR = new AgingAR();
		try {
			String procedure = "call getARReport()";
			con = dbConnection.getConnection();
			stmt = con.prepareCall(procedure);

			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				agingAR = new AgingAR();
				agingAR.setCorporate_id(resultSet.getInt("corporate_id"));
				agingAR.setCorporate_name(resultSet.getString("corporate_name"));
				agingAR.setBalance(resultSet.getInt("balance"));
				agingAR.setBalance30(resultSet.getInt("balance30"));
				agingAR.setBalance60(resultSet.getInt("balance60"));
				agingAR.setBalance90(resultSet.getInt("balance90"));
				agingAR.setBalance120(resultSet.getInt("balance120"));
				agingARList.add(agingAR);
			}
		} catch (Exception e) {
			logger.error("Method : getARReport()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(stmt);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(con);
		}
		return agingARList;
	}

 
	public List<AgingAR> getInvoiceDetails(JsonObject agingObj) {
		Connection con = null;
		Statement smt = null;
		ResultSet rs = null;
		List<AgingAR> list = new ArrayList<>();

		int corporate_id = agingObj.get("corporate_id").getAsInt();
		int id = agingObj.get("id").getAsInt();

		String query = "SELECT tbl.corporate_id,tbl.corporate_name,tbl.invoice_date,tbl.invoice_no,tbl.days,tbl.payable_amount"
				+ " FROM" + " ( SELECT "
				+ " corporate_id,corporate_name,invoice_date,invoice_no,days,sum(payable_amount) - sum(paid_amount) AS payable_amount "
				+ " FROM " + " v_creditors_list " + " WHERE corporate_id =" + corporate_id
				+ " AND DATEDIFF(get_hoteldate(), txn_date) >= (SELECT start_day FROM ar_aging_range WHERE id =  " + id
				+ ")" + " AND DATEDIFF(NOW(), txn_date) <= (SELECT end_day FROM ar_aging_range WHERE id = " + id + ")"
				+ " GROUP BY invoice_no ) tbl WHERE tbl.payable_amount > 0" + " ORDER BY invoice_date,invoice_no";
		try {
			con = dbConnection.getConnection();
			smt = con.createStatement();
			rs = smt.executeQuery(query);

			while (rs.next()) {
				AgingAR agingAR = new AgingAR();
				agingAR.setCorporate_id(rs.getInt("corporate_id"));
				agingAR.setInvoice_date(rs.getString("invoice_date"));
				agingAR.setInvoice_no(rs.getString("invoice_no"));
				agingAR.setDays(rs.getInt("days"));
				agingAR.setAmount(rs.getFloat("payable_amount"));
				list.add(agingAR);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Method : getAgingARList " + Throwables.getStackTraceAsString(e));
		}
		finally {
			dbConnection.releaseResource(smt);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(con);
		}
		return list;
	}

}