package com.indocosmo.pms.web.deposit.dao;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.enumerator.AccMst;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.payment.service.PaymentSeviceImp;
import com.indocosmo.pms.web.reservation.Folio;
import com.indocosmo.pms.web.transaction.dao.TxnDaoImpl;
import com.indocosmo.pms.web.transaction.model.Transaction;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DepositDAOImp implements DepositDAO {
	public static final Logger logger = LoggerFactory.getLogger(PaymentSeviceImp.class);
	DbConnection dbConnection = null;

	public DepositDAOImp() {
		dbConnection = new DbConnection();
	}

	public JsonArray listOfTransaction(int id, Map<String, String> advanceSearchMap, String dateFormat,
			Map<String, String> simpleSearchMap, int limit, String sort, String sortValue) {
		DateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		DateFormat destDf = new SimpleDateFormat("yyyy-MM-dd");
		String advanceSearchCriteria = "";

		if (!advanceSearchMap.isEmpty()) {
			advanceSearchCriteria = " and ";
		}

		for (Map.Entry<String, String> searchContent : advanceSearchMap.entrySet()) {
			if (searchContent.getKey().equals("txn_date")) {
				String temps = "";

				try {
					java.util.Date temp = simpleDateFormat.parse(searchContent.getValue());
					temps = destDf.format(temp);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				advanceSearchCriteria += "txn." + searchContent.getKey() + " = '" + temps + "'";
			} else {
				advanceSearchCriteria += "txn." + searchContent.getKey() + " like '%" + searchContent.getValue() + "%'";
			}
		}

		String simpleSearch = "";

		for (Map.Entry<String, String> simpleSearchVal : simpleSearchMap.entrySet()) {
			if (simpleSearch.equals("") || simpleSearch == null) {
				simpleSearch += " and " + simpleSearchVal.getKey() + " like '%" + simpleSearchVal.getValue() + "%'";
			} else {
				simpleSearch += " OR " + simpleSearchVal.getKey() + " like '%" + simpleSearchVal.getValue() + "%'";
			}
		}

		JsonObject jsonObject = null;
		JsonArray jsonArray = new JsonArray();
		PreparedStatement preparedStatement = null;
		Connection connection = (Connection) dbConnection.getConnection();
		ResultSet resultSet = null;

		try {
			String sql = "select acc_mst.name, txn.nett_amount,txn.txn_date from acc_mst inner join txn on acc_mst.id=txn.acc_mst_id where acc_mst_id=3  and folio_bind_no="
					+ id + advanceSearchCriteria + simpleSearch + " order by txn." + sort + " " + sortValue;

			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				jsonObject = new JsonObject();
				jsonObject.addProperty("date", resultSet.getDate("txn.txn_date").toString());
				jsonObject.addProperty("transaction", resultSet.getString("acc_mst.name"));
				jsonObject.addProperty("amount", resultSet.getBigDecimal("txn.nett_amount").toString());

				jsonArray.add(jsonObject);
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

		return jsonArray;
	}

	public List<Transaction> getDepositList(String folioBindNo, String sortCol, String sortStatus) {
		List<Transaction> depositList = null;
		depositList = new ArrayList<Transaction>();
		Connection connection = dbConnection.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		
		
	       

		AccMst i = AccMst.DEPOSIT;
		int accId = i.getSysdef_acc_type_id();
		String sortString = "";

		if (sortCol != null && !sortCol.equals("")) {
			sortString = " order by " + sortCol + " " + sortStatus;
		}
		String sql2 = "";
		 String arr[] = folioBindNo.split("_");
		int folio_bind_no = Integer.parseInt(arr[1]);
		 if(arr[0].equals("folioNo")) {
	        	
	        	 sql2 = "select receipt_gen.id,folio.folio_no,folio.folio_bind_no,folio.resv_no,txn.txn_no,txn.nett_amount,txn.amount,"
	    				+ "txn.acc_mst_code, txn.txn_date ,txn.txn_time, txn.remarks,txn.received_from "

	    				+ "from folio inner  join txn on (folio.folio_no=txn.folio_no) "
	    				+ "left join receipt_gen on receipt_gen.txn_no= txn.txn_no where  txn.txn_status =1 AND acc_mst_id = 4 AND txn.folio_no="
	    				+ folio_bind_no + sortString;
	        }
	        else {
	        	 sql2 = "select receipt_gen.id,folio.folio_no,folio.folio_bind_no,folio.resv_no,txn.txn_no,txn.nett_amount,txn.amount,"
	    				+ "txn.acc_mst_code, txn.txn_date ,txn.txn_time, txn.remarks,txn.received_from "

	    				+ "from folio inner  join txn on (folio.folio_no=txn.folio_no) "
	    				+ "left join receipt_gen on receipt_gen.txn_no= txn.txn_no where  txn.txn_status =1 AND acc_mst_id = 4 AND txn.folio_bind_no="
	    				+ folio_bind_no + sortString;
	        	
	        }
		
		

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql2);

			while (resultSet.next()) {
				Transaction txn = new Transaction();
				txn.setTxn_no(resultSet.getInt("txn_no"));
				txn.setReceipt_no(resultSet.getInt("id"));
				txn.setAmount(resultSet.getDouble("amount"));
				txn.setNett_amount(resultSet.getDouble("txn.nett_amount"));
				txn.setTxn_date(resultSet.getString("txn_date"));
				txn.setTxn_time(resultSet.getString("txn_time"));
				txn.setRemarks(resultSet.getString("remarks"));
				txn.setReceivedForm(resultSet.getString("received_from"));
				depositList.add(txn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}

		return depositList;
	}

	public Folio getFolioObj(Transaction txnForDeposit) {
		Connection connection = dbConnection.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		Folio foliodetails = null;

		String sql = "";

		if (txnForDeposit.getDepositFrom() == 1) {

			sql = "select * from folio where resv_no=" + txnForDeposit.getResvNo();
		} else {

			sql = "select * from folio where checkin_no=" + txnForDeposit.getCheckInNo();
		}

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				foliodetails = new Folio();
				foliodetails.setFolio_no(resultSet.getInt("folio_no"));
				foliodetails.setFolio_bind_no(resultSet.getInt("folio_bind_no"));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}
		
		return foliodetails;
	}

	public int gettxnNo() {

		Connection connection = dbConnection.getConnection();
		ResultSet resultSet = null;

		int txnNo = 0;

		String keyName = "txn_no";
		String countFunction = "{? = call counter(?)}";

		try {
			CallableStatement countFunct = connection.prepareCall(countFunction);
			countFunct.registerOutParameter(1, java.sql.Types.INTEGER);
			countFunct.setString(2, keyName);
			resultSet = countFunct.executeQuery();

			while (resultSet.next()) {

				txnNo = resultSet.getInt(1);

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}

		return txnNo;

	}

	public AccountMaster getAccMst(int accId) {
		Connection connection = dbConnection.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		AccountMaster am = null;
		String sql = "select * from acc_mst where sysdef_acc_type_id=" + accId;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				am = new AccountMaster();
				am.setId(resultSet.getInt("id"));
				am.setCode(resultSet.getString("code"));
				am.setTax_id(resultSet.getInt("tax_id"));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}
		

		return am;
	}

	public int getSaveDeposit(Transaction saveDeposit) {
		boolean isSave = false;
		boolean isSaveReceipt = false;
		TxnDaoImpl txndao = new TxnDaoImpl();
		PreparedStatement prepareStatement = null;
		Connection connection = dbConnection.getConnection();

		String sql = "insert into txn(txn_no,folio_no,folio_bind_no,txn_source,txn_date,txn_time,acc_mst_id,"// 7
				+ "acc_mst_code,tax_id,tax_code,include_tax,tax1_pc,tax2_pc,tax3_pc,tax4_pc,"// 8
				+ "amount,base_amount,tax1_amount,tax2_amount,tax3_amount,tax4_amount,nett_amount,payment_mode,txn_status,"// 8
				+ "remarks,user_id,received_from,service_charge_pc,service_charge,shift_id)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";// 2
		try {
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setInt(1, saveDeposit.getTxn_no());
			prepareStatement.setInt(2, saveDeposit.getFolio_no());
			prepareStatement.setInt(3, saveDeposit.getFolio_bind_no());
			prepareStatement.setInt(4, 1);// source of transaction(0=FO,1=NA,2=POS)
			prepareStatement.setString(5, saveDeposit.getTxn_date());
			prepareStatement.setString(6, saveDeposit.getTxn_time());
			prepareStatement.setInt(7, saveDeposit.getAcc_mst_id());// first line completes

			prepareStatement.setString(8, saveDeposit.getAcc_mst_code());
			prepareStatement.setInt(9, saveDeposit.getTax_id());
			prepareStatement.setString(10, saveDeposit.getTax_code());
			prepareStatement.setBoolean(11, saveDeposit.isInclude_tax());
			prepareStatement.setDouble(12, saveDeposit.getTax1_pc());
			prepareStatement.setDouble(13, saveDeposit.getTax2_pc());
			prepareStatement.setDouble(14, saveDeposit.getTax3_pc());
			prepareStatement.setDouble(15, saveDeposit.getTax4_pc());// second line ends

			prepareStatement.setDouble(16, saveDeposit.getAmount());
			prepareStatement.setDouble(17, saveDeposit.getAmount());
			prepareStatement.setDouble(18, saveDeposit.getTax1_amount());
			prepareStatement.setDouble(19, saveDeposit.getTax2_amount());
			prepareStatement.setDouble(20, saveDeposit.getTax3_amount());
			prepareStatement.setDouble(21, saveDeposit.getTax4_amount());
			prepareStatement.setDouble(22, saveDeposit.getNett_amount());
			prepareStatement.setInt(23, saveDeposit.getPayment_mode());
			prepareStatement.setInt(24, saveDeposit.getTxn_status());
			prepareStatement.setString(25, saveDeposit.getRemarks());
			prepareStatement.setInt(26, saveDeposit.getUser_id());
			prepareStatement.setString(27, saveDeposit.getReceivedForm());
			prepareStatement.setDouble(28, 0);
			prepareStatement.setDouble(29, 0);
			prepareStatement.setInt(30, saveDeposit.getShift_id());
			prepareStatement.executeUpdate();
			txndao.updateTxnLog(connection, saveDeposit.getTxn_no(), "INSERT");

			isSave = true;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Method : getSaveDeposit " + Throwables.getStackTraceAsString(e));
		}
		
		String sqlReceipt = "insert into receipt_gen(txn_no) values(?)";
		try {
			prepareStatement = connection.prepareStatement(sqlReceipt);
			prepareStatement.setInt(1, saveDeposit.getTxn_no());
			prepareStatement.executeUpdate();
			isSaveReceipt = true;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Method : getSaveDeposit " + Throwables.getStackTraceAsString(e));
		}
		finally {
			dbConnection.releaseResource(prepareStatement);
			dbConnection.releaseResource(connection);
		}
		if (isSave && isSaveReceipt) {
			return 1;
		} else {
			return 0;
		}
	}

	public double getTotalNetDeposit(String resvNo, String chkinNo) {
		Connection connection = dbConnection.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		double totalNetDeposit = 0.0;
		String sql = "";
		if (chkinNo != null) {
			sql = "SELECT sum(amount) AS total_new_deposit FROM txn WHERE folio_no = (SELECT folio_no FROM folio WHERE checkin_no ="
					+ chkinNo + ") AND acc_mst_id = 4  AND txn.txn_status =1 ";
		} else {
			sql = "SELECT" + "	sum(amount) AS total_new_deposit" + " FROM" + "	txn"
					+ " JOIN resv_hdr hdr ON txn.folio_bind_no = hdr.folio_bind_no" + " WHERE" + " hdr.resv_no = "
					+ resvNo + " AND txn.acc_mst_id = 4 AND txn.txn_status =1";
		}

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				totalNetDeposit = resultSet.getDouble("total_new_deposit");
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}
		
		return totalNetDeposit;
	}

	public double getTotalPayable(int id) {
		Connection connection = dbConnection.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		double totalPay = 0.0;
		String sql = "select resv_hdr.resv_no,resv_hdr.folio_bind_no,count(resv_rate.night) as total_nights ,sum(resv_rate.room_charge - if((resv_rate.include_tax = 1),0,(resv_rate.tax1 + resv_rate.tax2 + resv_rate.tax3 + resv_rate.tax4))) as total_payable from resv_hdr inner join resv_dtl on (resv_hdr.resv_no=resv_dtl.resv_no) inner join resv_room on (resv_dtl.resv_dtl_no=resv_room.resv_dtl_no) inner join resv_rate on (resv_room.resv_room_no=resv_rate.resv_room_no) where resv_hdr.resv_no="
				+ id;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				totalPay = resultSet.getDouble("total_payable");
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}
		
		return totalPay;
	}

	@Override
	public  List<Transaction>  getDetails(Transaction deposit) {
		int txn_no = deposit.getTxn_no();
		Connection con = null;
		CallableStatement st = null;
		List<Transaction> txnList=new ArrayList<Transaction>();
		ResultSet rs = null;
		String storedProcedure = null;
		storedProcedure = "{call getDepostDetails(?)}";
		try {
			con = dbConnection.getConnection();
			st = con.prepareCall(storedProcedure);
			st.setInt(1, txn_no);
			st.execute();
			rs = st.getResultSet();
			if (rs.next()) {
				deposit.setFirstName(rs.getString("first_name"));
				deposit.setLastName(rs.getString("last_name"));
				deposit.setGuestName(rs.getString("guest_name"));
				deposit.setRoomNumber(rs.getString("room_no"));
				deposit.setRoom_type(rs.getString("room_type"));
				deposit.setPayment_mode(rs.getInt("payment_mode"));
				txnList.add(deposit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(st);
		}
		
		return txnList;
	}

	public JsonArray getCorpName() {
		String corpName = "";
		JsonObject jsonObject = null;
		JsonArray jsonArray = new JsonArray();
		PreparedStatement preparedStatement = null;
		Connection connection = (Connection) dbConnection.getConnection();
		ResultSet resultSet = null;

		try {
			String sql1 = "select name from corporate";

			preparedStatement = connection.prepareStatement(sql1);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				jsonObject = new JsonObject();
				jsonObject.addProperty("corpName", resultSet.getString("name"));
				jsonArray.add(jsonObject);
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

		return jsonArray;
	}

	@Override
	public Integer findFolio(int folioBindNo) throws Exception {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		 connection = (Connection) dbConnection.getConnection();
		Integer folio = 0;

		try {
			String sql1 = "select folio_no from folio where checkin_no = '"+folioBindNo+"'";

			preparedStatement = connection.prepareStatement(sql1);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				folio =  resultSet.getInt("folio_no");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(preparedStatement);
			dbConnection.releaseResource(connection);
		}
		
		return folio;
	}

}