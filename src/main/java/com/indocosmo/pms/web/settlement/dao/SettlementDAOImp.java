package com.indocosmo.pms.web.settlement.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.enumerator.PaymentMode;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.checkOut.dao.CheckOutDAOImp;
import com.indocosmo.pms.web.settlement.model.Settlement;

@Repository
public class SettlementDAOImp implements SettlementDAO {

	public static final Logger logger = LoggerFactory.getLogger(CheckOutDAOImp.class);

	private DbConnection dbConnection = null;

	public SettlementDAOImp() {
		dbConnection = new DbConnection();
	}

	@Override
	public List<Settlement> getSettlementList(Date startDate, Date endDate, Integer corporate, JsonObject paginationObj) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List<Settlement> list = new ArrayList<>();
		Integer offset = paginationObj.get("offset").getAsInt();
		Integer limit = paginationObj.get("limit").getAsInt();
		String corporatePart = "";
		if(corporate != 0) {
			 corporatePart = " AND corporate_id = ?";
		}
		
		String query = "SELECT " + 
				" id, " +
				" voucher_no, " + 
				" payment_date, " + 
				" corporate_id, " + 
				" corporate_name, " + 
				" payment_type, " + 
				" reference_no, " + 
				" reference_date, " + 
				" reference_name, " + 
				" payment_amount, " + 
				" allocate_amount, " + 
				" remarks "+
				" FROM " + 
				" payment_register_hdr " + 
				" WHERE payment_date  BETWEEN ? AND ?" + 
				corporatePart+
				" ORDER BY" + 
				" voucher_no DESC"+
				" LIMIT ? OFFSET ?";
		
		String countQuery = "SELECT" + 
				" COUNT(*) AS count" + 
				" FROM" + 
				" payment_register_hdr" + 
				" WHERE" + 
				" payment_date BETWEEN ?" + 
				" AND ?" + 
				corporatePart  + 
				" ORDER BY" + 
				" voucher_no DESC";
		try {
			con = dbConnection.getConnection();
			PreparedStatement countSmt = con.prepareStatement(countQuery);
			countSmt.setDate(1, startDate);
			countSmt.setDate(2, endDate);
			if(corporate != 0) {
				countSmt.setInt(3, corporate);
			}
			ResultSet countRs = countSmt.executeQuery();
			Integer count = 0;
			while(countRs.next()) {
				count = countRs.getInt("count");
				Settlement set = new Settlement();
				set.setCount(count);
				list.add(set);
			}
			
			psmt = con.prepareStatement(query);
			psmt.setDate(1, startDate);
			psmt.setDate(2, endDate);
			if(corporate == 0) {
				psmt.setInt(3, limit);
				psmt.setInt(4, offset);
			}else {
				psmt.setInt(3, corporate);
				psmt.setInt(4, limit);
				psmt.setInt(5, offset);
			}
			
			rs = psmt.executeQuery();
			while (rs.next()) {
				Settlement settlement = new Settlement();
				settlement.setId(rs.getInt("id"));
				settlement.setVoucher_no(rs.getInt("voucher_no"));
				settlement.setPayment_date(rs.getDate("payment_date"));
				settlement.setCorporate_id(rs.getInt("corporate_id"));
				settlement.setCorporate_name(rs.getString("corporate_name"));
				settlement.setPayment_type(rs.getInt("payment_type"));
				settlement.setPayment_mode(PaymentMode.get(rs.getInt("payment_type")).getPaymentMode());
				settlement.setReference_no(rs.getString("reference_no"));
				settlement.setReference_date(rs.getDate("reference_date"));
				settlement.setReference_name(rs.getString("reference_name"));
				settlement.setPayment_amount(rs.getDouble("payment_amount"));
				settlement.setAllocate_amount(rs.getDouble("allocate_amount"));
				settlement.setRemarks(rs.getString("remarks"));
				
				list.add(settlement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Method : getSettlementList " + Throwables.getStackTraceAsString(e));
		}
		finally {
			dbConnection.releaseResource(psmt);
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(rs);
		}

		return list;
	}

	@Override
	public List<Settlement> getInvoiceDetails(int corporate) {
		Connection con = null;
		Statement smt = null;
		ResultSet rs = null;
		List<Settlement> list = new ArrayList<>();

		String query = "SELECT" + 
				"	corporate_id," + 
				"	corporate_name," + 
				"	invoice_date," + 
				"	invoice_no," + 
				"	days," + 
				"	sum(payable_amount) AS invoice_amount," + 
				"	sum(paid_amount) AS paid_amount," + 
				"	sum(payable_amount) - sum(paid_amount) AS payable_amount" + 
				"	FROM" + 
				"	v_creditors_list" + 
				"	WHERE" + 
				"	corporate_id = " +corporate+
				"	GROUP BY" + 
				"	invoice_no" + 
				"	ORDER BY" + 
				"	invoice_date," + 
				"	invoice_no"; 
		try {
			con = dbConnection.getConnection();
			smt = con.createStatement();
			rs = smt.executeQuery(query);
			while (rs.next()) {
				Settlement settlement = new Settlement();
				settlement.setCorporate_id(rs.getInt("corporate_id"));
				settlement.setInvoice_date(rs.getString("invoice_date"));
				settlement.setInvoice_no(rs.getString("invoice_no"));
				settlement.setDays(rs.getInt("days"));
				settlement.setInvoiceAmount(rs.getDouble("invoice_amount"));
				settlement.setPaidAmount(rs.getDouble("paid_amount"));
				settlement.setBalance(rs.getDouble("payable_amount"));
				list.add(settlement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Method : getSettlementList " + Throwables.getStackTraceAsString(e));
		}
		finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(smt);
			dbConnection.releaseResource(rs);
		}

		return list;
	}

	@Override
	public boolean save(JsonArray settlementArray) {
		boolean isSave = false;
		Connection connection = dbConnection.getConnection();
		PreparedStatement paymentHdrPs = null;
		PreparedStatement paymentDtlPs = null;
		ResultSet rsPaymentHdr = null;
	//	ResultSet rsPaymentDtl = null;
		String queryPaymentHdr = "INSERT INTO payment_register_hdr(payment_date,"
				+ " corporate_id, corporate_name, amount, payment_type,"
				+ " reference_no, reference_date, reference_name," + " is_deleted)" + " VALUES(?,?,?,?,?,?,?,?,?)";
		String queryPaymentDtl = "INSERT INTO payment_register_dtl(payment_register_hdr_id,"
				+ " txn_no, invoice_no, invoice_date, settle_amount, is_deleted)" + " VALUES(?,?,?,?,?,?)";
		try {
			connection.setAutoCommit(false);

			if (!settlementArray.equals(null)) {
				for (int i = 0; i < settlementArray.size(); i++) {
					JsonObject jobj = settlementArray.get(i).getAsJsonObject();
					String generatedColumns[] = { "ID" };
					paymentHdrPs = connection.prepareStatement(queryPaymentHdr, generatedColumns);
					JsonObject settlementObj = jobj.get("settlement").getAsJsonObject();

					if (settlementObj.get("payment_amount").getAsDouble() > 0) {
						paymentHdrPs.setString(1, settlementObj.get("txn_date").getAsString());
						paymentHdrPs.setInt(2, settlementObj.get("corporate_id").getAsInt());
						paymentHdrPs.setString(3, settlementObj.get("corporate_name").getAsString());
						paymentHdrPs.setDouble(4, settlementObj.get("payment_amount").getAsDouble());
						paymentHdrPs.setInt(5, settlementObj.get("paymentMode").getAsInt());
						paymentHdrPs.setString(6, settlementObj.get("reference_no").getAsString());
						if (!(settlementObj.get("reference_date").getAsString()).equals("")) {
							paymentHdrPs.setString(7, settlementObj.get("reference_date").getAsString());
						} else {
							paymentHdrPs.setString(7, null);
						}
						paymentHdrPs.setString(8, settlementObj.get("reference_name").getAsString());
						// paymentHdrPs.setInt(9,settlementObj.get("confirm_by").getAsInt());
						paymentHdrPs.setInt(9, 0);

						paymentHdrPs.executeUpdate();

						rsPaymentHdr = paymentHdrPs.getGeneratedKeys();

						if (rsPaymentHdr.next()) {
							long id = rsPaymentHdr.getLong(1);
							JsonArray invoiceArray = jobj.get("invoiceDtlsList").getAsJsonArray();
							for (int j = 0; j < invoiceArray.size(); j++) {
								JsonObject invoiceObject = invoiceArray.get(j).getAsJsonObject();
								if (invoiceObject.get("settle_amount").getAsDouble() > 0) {
									paymentDtlPs = connection.prepareStatement(queryPaymentDtl);
									paymentDtlPs.setLong(1, id);
									paymentDtlPs.setInt(2, 0);
									paymentDtlPs.setString(3, invoiceObject.get("invoice_no").getAsString());
									paymentDtlPs.setString(4, invoiceObject.get("invoice_date").getAsString());
									paymentDtlPs.setDouble(5, invoiceObject.get("settle_amount").getAsDouble());
									paymentDtlPs.setInt(6, 0);

									// paymentDtlPs.addBatch();
									paymentDtlPs.executeUpdate();
								}
							}

							// paymentDtlPs.executeBatch();
						}
					}
				}

				connection.commit();
				isSave = true;
			}

		} catch (Exception e) {
			isSave = false;
			e.printStackTrace();
			logger.error("Method : save " + Throwables.getStackTraceAsString(e));
		} finally {
			dbConnection.releaseResource(paymentHdrPs);
			dbConnection.releaseResource(paymentDtlPs);
			dbConnection.releaseResource(connection);
			
		}
		return isSave;
	}

	@Override
	public JsonArray getCorporates() throws SQLException {
		Connection con = null;
		Statement smt = null;
		ResultSet rs = null;
		JsonArray jarray = new JsonArray();
		try {
		 con = dbConnection.getConnection();
		 smt = con.createStatement();
		 rs = smt.executeQuery("SELECT id, name from corporate WHERE is_deleted = 0 ORDER BY NAME ASC");
		
		
		while(rs.next()) {
			JsonObject jobj = new JsonObject();
			jobj.addProperty("id", rs.getInt("id"));
			jobj.addProperty("name", rs.getString("name"));
			
			jarray.add(jobj);
		}
		}
		catch (Exception e) {
			logger.error("Method : getRoomRates()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
		} finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(smt);
			dbConnection.releaseResource(rs);
		}
		
		return jarray;
	}

	@Override
	public boolean saveNewPayment(JsonObject newPaymentObject, int currentUser) {
		boolean isSave = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Connection connection = dbConnection.getConnection();
		PreparedStatement paymentHdrPs = null;
		String queryPaymentHdr = "INSERT INTO payment_register_hdr(voucher_no, payment_date,"
				+ " corporate_id, corporate_name, payment_amount, payment_type,"
				+ " reference_no, reference_date, reference_name, remarks, confirm_by)" + " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			connection.setAutoCommit(false);
			
			paymentHdrPs = connection.prepareStatement(queryPaymentHdr);
			if (!newPaymentObject.equals(null)) {
				java.sql.Date paymentDate = new java.sql.Date(sdf.parse(newPaymentObject.get("date").getAsString()).getTime());	
				java.sql.Date referenceDate = new java.sql.Date(sdf.parse(newPaymentObject.get("refDate").getAsString()).getTime());
				int voucherNo = getVoucherNo();
				paymentHdrPs.setString(1, String.valueOf(voucherNo));
				paymentHdrPs.setDate(2, paymentDate);
				paymentHdrPs.setInt(3, newPaymentObject.get("corporateId").getAsInt());
				paymentHdrPs.setString(4, newPaymentObject.get("corporateName").getAsString());
				paymentHdrPs.setDouble(5, newPaymentObject.get("paymentAmount").getAsDouble());
				paymentHdrPs.setInt(6, newPaymentObject.get("paymentMode").getAsInt());
				paymentHdrPs.setString(7, newPaymentObject.get("refNo").getAsString());
				paymentHdrPs.setDate(8, referenceDate);
				paymentHdrPs.setString(9, newPaymentObject.get("refName").getAsString());
				paymentHdrPs.setString(10, newPaymentObject.get("remarks").getAsString());	
				paymentHdrPs.setInt(11, currentUser);
				
				paymentHdrPs.executeUpdate();
				connection.commit();
				isSave = true;
				
			}

		} catch (Exception e) {
			isSave = false;
			e.printStackTrace();
			logger.error("Method : save " + Throwables.getStackTraceAsString(e));
		} finally {
			dbConnection.releaseResource(paymentHdrPs);
			dbConnection.releaseResource(connection);
		}
		return isSave;
	}
	

	@Override
	public int getVoucherNo() throws SQLException {
		int voucherNo = 0;
		Connection connection = null;
		CallableStatement countFunct= null;
		connection = dbConnection.getConnection();
		ResultSet resultSet = null;
		String keyName = "voucher_no";
		String countFunction="{? = call counter(?)}";
		try {
		countFunct=connection.prepareCall(countFunction);
		countFunct.registerOutParameter(1, java.sql.Types.INTEGER);
		countFunct.setString(2, keyName);
		resultSet = countFunct.executeQuery();

		while(resultSet.next()){
			
			voucherNo = resultSet.getInt(1);
				 
		}
		}
		catch (Exception e) {
			logger.error("Method : getVoucherNo()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			
		} finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(countFunct);
			dbConnection.releaseResource(resultSet);
		}
		
		return voucherNo;
	}

	@Override
	public boolean saveAllocation(JsonObject settlementObject) {
		boolean isSave = false;
		int hdrId = 0;
		double totalSettlement = 0;
		PreparedStatement psmtTotalPaid = null;
		PreparedStatement hdrPs = null;
		double allocAmt = 0;
		ResultSet rs = null ;
		Connection con = dbConnection.getConnection();
		String sql = "INSERT INTO payment_register_dtl(payment_register_hdr_id,"
				+ " invoice_no, invoice_date, settle_date, settle_amount)" + 
				" VALUES(?,?,?,?,?)";
		String totalPaidSql = "SELECT SUM(settle_amount) AS total_settlement FROM payment_register_dtl WHERE payment_register_hdr_id = ?";
		String hdrSql = "UPDATE payment_register_hdr SET allocate_amount = ? WHERE id = ?";
		try {
			con.setAutoCommit(false);
			if(settlementObject != null) {
				JsonArray settlementArray = settlementObject.get("detail").getAsJsonArray();
				hdrId = settlementObject.get("hdrId").getAsInt();
				psmtTotalPaid = con.prepareStatement(totalPaidSql);
				psmtTotalPaid.setInt(1, hdrId);
				 rs = psmtTotalPaid.executeQuery();
				while(rs.next()) {
					totalSettlement = rs.getDouble("total_settlement");
				}
				for(Object obj : settlementArray) {
					JsonObject jobj = (JsonObject) obj;
					allocAmt += jobj.get("settle_amount").getAsDouble();
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setInt(1, hdrId);
					ps.setString(2, jobj.get("invoice_no").getAsString());
					ps.setString(3, jobj.get("invoice_date").getAsString());
					ps.setString(4, LocalDate.now().toString());
					ps.setDouble(5, jobj.get("settle_amount").getAsDouble());
					
					ps.executeUpdate();
					
				}
				
				 hdrPs = con.prepareStatement(hdrSql);
				hdrPs.setDouble(1, totalSettlement+allocAmt);
				hdrPs.setInt(2, hdrId);
				
				hdrPs.executeUpdate();
				
				con.commit();
				isSave = true;
			}

		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource( hdrPs);
			dbConnection.releaseResource(psmtTotalPaid);
			dbConnection.releaseResource( rs);
		}
		return isSave;
	}

	@Override
	public boolean updatePayment(JsonObject newPaymentObject, int currentUser) {
		boolean isSave = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Connection connection = dbConnection.getConnection();
		PreparedStatement paymentHdrPs = null;
		String queryPaymentHdr = "UPDATE payment_register_hdr SET voucher_no = ?, payment_date = ?," + 
								" corporate_id = ?, corporate_name = ?, payment_amount = ?, payment_type = ?," + 
								" reference_no = ?, reference_date = ?, reference_name = ?, remarks = ?, confirm_by=?" + 
								" WHERE id = ?";
		try {
			connection.setAutoCommit(false);
			
			paymentHdrPs = connection.prepareStatement(queryPaymentHdr);
			if (!newPaymentObject.equals(null)) {
				java.sql.Date paymentDate = new java.sql.Date(sdf.parse(newPaymentObject.get("date").getAsString()).getTime());	
				java.sql.Date referenceDate = new java.sql.Date(sdf.parse(newPaymentObject.get("refDate").getAsString()).getTime());
				
				paymentHdrPs.setString(1, newPaymentObject.get("voucherNo").getAsString());
				paymentHdrPs.setDate(2, paymentDate);
				paymentHdrPs.setInt(3, newPaymentObject.get("corporateId").getAsInt());
				paymentHdrPs.setString(4, newPaymentObject.get("corporateName").getAsString());
				paymentHdrPs.setDouble(5, newPaymentObject.get("paymentAmount").getAsDouble());
				paymentHdrPs.setInt(6, newPaymentObject.get("paymentMode").getAsInt());
				paymentHdrPs.setString(7, newPaymentObject.get("refNo").getAsString());
				paymentHdrPs.setDate(8, referenceDate);
				paymentHdrPs.setString(9, newPaymentObject.get("refName").getAsString());
				paymentHdrPs.setString(10, newPaymentObject.get("remarks").getAsString());		
				paymentHdrPs.setInt(11, currentUser);
				paymentHdrPs.setInt(12, newPaymentObject.get("id").getAsInt());
				
				paymentHdrPs.executeUpdate();
				connection.commit();
				isSave = true;
			}

		} catch (Exception e) {
			isSave = false;
			e.printStackTrace();
			logger.error("Method : save " + Throwables.getStackTraceAsString(e));
		} finally {
			dbConnection.releaseResource(paymentHdrPs);
			dbConnection.releaseResource(connection);
		}
		return isSave;
	}

	@Override
	public double getOutstanding(int corporate) {
		double outstanding = 0;
		ResultSet rs = null;
		Connection connection = dbConnection.getConnection();
		PreparedStatement ps = null;
		String query = "SELECT SUM(payment_amount) - SUM(allocate_amount) AS outstanding FROM payment_register_hdr WHERE corporate_id = ?";
		try {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(query);
			ps.setInt(1, corporate);
			rs = ps.executeQuery();
			while(rs.next()) {
				outstanding = rs.getDouble("outstanding");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(ps);
			dbConnection.releaseResource(rs);
		}
		return outstanding;
	}

	@Override
	public JsonObject getReceiptData(String voucherNo) {
		Connection con = dbConnection.getConnection();
		PreparedStatement smt = null;
		ResultSet rs = null;
		JsonObject jobj = new JsonObject();
		try {
			smt = con.prepareStatement("SELECT voucher_no, payment_date, corporate_id, corporate_name, payment_amount FROM payment_register_hdr WHERE voucher_no = ?");
			smt.setString(1, voucherNo);
			rs = smt.executeQuery();
			while(rs.next()) {
				jobj.addProperty("voucher_no", rs.getString("voucher_no"));
				jobj.addProperty("payment_date", rs.getString("payment_date"));
				jobj.addProperty("corporate_name", rs.getString("corporate_name"));
				jobj.addProperty("payment_amount", rs.getString("payment_amount"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(smt);
			dbConnection.releaseResource(rs);
		}
		
		return jobj;
	}

}