package com.indocosmo.pms.web.transaction.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.checkIn.model.CheckInHdr;
import com.indocosmo.pms.web.common.setttings.Rounding;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.reservation.Folio;
import com.indocosmo.pms.web.tax.model.TaxHdr;
import com.indocosmo.pms.web.transaction.model.Transaction;

@Repository
public class TxnDaoImpl implements TxnDao{

	
	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(TxnDaoImpl.class);
	DbConnection dbConnection=null;	

	public TxnDaoImpl(){
		dbConnection=new DbConnection();
	}


	/*
	 * Method for deleting record
	 */
	public String deleteRecord(int txnNo) {
		String status="error";    

		Connection connection=dbConnection.getConnection();
		String sql="UPDATE txn SET txn_status=? WHERE txn_no=?";
		PreparedStatement prepareStatement= null;

		try {
			prepareStatement=connection.prepareStatement(sql);
			prepareStatement.setInt(1,0);
			prepareStatement.setInt(2,txnNo);
			if(prepareStatement.executeUpdate()==1){
				updateTxnLog(connection, txnNo, "UPDATE");

				status="success";
			};
		} catch (SQLException e) {
			e.printStackTrace();

		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(prepareStatement);
			
		}
		return status;
	}
	
	public void updateTxnLog(Connection connection, int txnNo, String update) {
		String txnLogSql="";
		if(update == "UPDATE") {
			txnLogSql="INSERT INTO txn_log SELECT txn.*, 1 FROM txn WHERE txn.txn_no = ?";
		}else {
			txnLogSql="INSERT INTO txn_log SELECT txn.*, 0 FROM txn WHERE txn.txn_no = ?";
		}
		try {
			PreparedStatement txnLogPs = connection.prepareStatement(txnLogSql);
			txnLogPs.setInt(1,txnNo);
			txnLogPs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public TaxHdr getTaxCode(double roomCharge,boolean isBase){
		Connection connection=dbConnection.getConnection();
		CallableStatement preparedStatement = null;
		TaxHdr taxHdr=new TaxHdr();
		String procedure="{CALL getRateTaxDetails(?,?,?,?)}";
		
		try{
			preparedStatement = connection.prepareCall(procedure);
			preparedStatement.setDouble(1, roomCharge);
			preparedStatement.setBoolean(2,isBase);
			preparedStatement.registerOutParameter(3,java.sql.Types.INTEGER);
			preparedStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
			preparedStatement.executeUpdate();
			taxHdr.setId(preparedStatement.getInt(3));
			taxHdr.setCode(preparedStatement.getString(4));
		}catch(Exception ex){
		ex.printStackTrace();
		logger.error("Method: getRateTaxDetail, " + Throwables.getStackTraceAsString(ex));
		throw new CustomException();
	}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(preparedStatement);
			
		}

		
		return taxHdr;
		
	}
	

	public TaxHdr getTaxCode(int taxId) {
		TaxHdr taxHdr=null;
		Connection connection=dbConnection.getConnection();
		Statement statement=null;
		ResultSet resultSet=null;
		String sql="SELECT code from tax_hdr where id="+taxId;

		try {
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);

			while(resultSet.next()){
				taxHdr=new TaxHdr();
				taxHdr.setCode(resultSet.getString("code"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			
		}

		return taxHdr;
	}

	/*
	 * Method to get code and taxId from accMaster table
	 */
	public AccountMaster getAccMstVal(int accMstId) {
		AccountMaster accMaster=null;
		Connection connection=dbConnection.getConnection();
		Statement statement=null;
		ResultSet resultSet=null;
		String sql="SELECT code,tax_id from acc_mst where id="+accMstId;

		try {
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);

			while(resultSet.next()){
				accMaster=new AccountMaster();
				accMaster.setCode(resultSet.getString("code"));
				accMaster.setTax_id(resultSet.getInt("tax_id"));

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


		return accMaster;
	}



	public Transaction getCharges(Date hotelDate,double amount,int accMst,boolean isBase, int chkInNo){
		Connection connection=dbConnection.getConnection();
		CallableStatement preparedStatement = null;
		//ResultSet resultSet=null;
		Transaction txn=new Transaction();
		

		String procedure="{CALL GetTaxAmounts(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

		try{
			preparedStatement = connection.prepareCall(procedure);
			preparedStatement.setInt(1, chkInNo);
			preparedStatement.setDate(2,hotelDate);
			preparedStatement.setDouble(3,1);
			preparedStatement.setDouble(4, amount);
			preparedStatement.setBoolean(5,isBase);
			preparedStatement.setInt(6,accMst);
			preparedStatement.setBoolean(7,commonSettings.isServiceChargeApplicable);
			preparedStatement.registerOutParameter(8,java.sql.Types.DECIMAL);
			preparedStatement.registerOutParameter(9,java.sql.Types.DECIMAL);
			preparedStatement.registerOutParameter(10,java.sql.Types.DECIMAL);
			preparedStatement.registerOutParameter(11,java.sql.Types.DECIMAL);
			preparedStatement.registerOutParameter(12,java.sql.Types.DECIMAL);
			preparedStatement.registerOutParameter(13,java.sql.Types.DECIMAL);
			preparedStatement.registerOutParameter(14,java.sql.Types.DECIMAL);
			preparedStatement.registerOutParameter(15,java.sql.Types.DECIMAL);
			preparedStatement.registerOutParameter(16,java.sql.Types.DECIMAL);
			preparedStatement.registerOutParameter(17,java.sql.Types.DECIMAL);
			preparedStatement.registerOutParameter(18,java.sql.Types.DECIMAL);
			preparedStatement.registerOutParameter(19,java.sql.Types.DECIMAL);
			preparedStatement.registerOutParameter(20,java.sql.Types.DECIMAL);
			preparedStatement.registerOutParameter(21,java.sql.Types.INTEGER);
			preparedStatement.registerOutParameter(22,java.sql.Types.VARCHAR);
			preparedStatement.executeUpdate();
								
			
			txn.setBase_amount(Rounding.roundOff(new BigDecimal(preparedStatement.getDouble(8)), 2, true)); 	
			txn.setTax1_amount(Rounding.roundOff(new BigDecimal(preparedStatement.getDouble(10)), 2, true));
			txn.setTax2_amount(Rounding.roundOff(new BigDecimal(preparedStatement.getDouble(11)), 2, true));
			txn.setTax3_amount(Rounding.roundOff(new BigDecimal(preparedStatement.getDouble(12)), 2, true));
			txn.setTax4_amount(Rounding.roundOff(new BigDecimal(preparedStatement.getDouble(13)), 2, true));
			txn.setTax1_pc(Rounding.roundOff(new BigDecimal(preparedStatement.getDouble(14)), 2, true));
			txn.setTax2_pc(Rounding.roundOff(new BigDecimal(preparedStatement.getDouble(15)), 2, true));
			txn.setTax3_pc(Rounding.roundOff(new BigDecimal(preparedStatement.getDouble(16)), 2, true));
			txn.setTax4_pc(Rounding.roundOff(new BigDecimal(preparedStatement.getDouble(17)), 2, true));
			txn.setNett_amount(Rounding.roundOff(new BigDecimal(preparedStatement.getDouble(19)), 2, true));
			
			txn.setServiceChargePc(preparedStatement.getBigDecimal(18));
			txn.setServiceCharge(preparedStatement.getBigDecimal(9));
			txn.setTax_id(preparedStatement.getInt(21));
			txn.setTax_code(preparedStatement.getString(22));


		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("Method: getCharges, " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(preparedStatement);
			
			
		}

		return txn;
	}
	
	public Folio getFolioDetails(int checkinNo) {
		Folio folio=null;
		Connection connection=dbConnection.getConnection();
		Statement statement=null;
		ResultSet resultSet=null;
		String sql="select * from folio where checkin_no="+checkinNo;

		try{
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);

			while(resultSet.next()) {
				folio = new Folio();
				folio.setFolio_no(resultSet.getInt("folio_no"));
				folio.setResv_no(resultSet.getInt("resv_no"));
				folio.setFolio_bind_no(resultSet.getInt("folio_bind_no"));
			}
		}catch(SQLException ex){
			ex.printStackTrace();
			logger.error("Method: getFolioDetails, " + Throwables.getStackTraceAsString(ex));
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			
		}

		return folio;
	}

	public CheckInHdr getcheckinDetails(int checkinNo) {
		CheckInHdr checkin=null;
		Connection connection=dbConnection.getConnection();
		Statement statement=null;
		ResultSet resultSet=null;
		String sql="select * from v_reception_in_house_list where checkin_no="+checkinNo;

		try{
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);

			while(resultSet.next()) {
				checkin = new CheckInHdr();
				checkin.setCheckInNo(resultSet.getInt("checkin_no"));
				checkin.setName(resultSet.getString("customer_name"));
				checkin.setFullySettled(resultSet.getBoolean("is_fully_settled"));
				checkin.setRoomNumber(resultSet.getString("room_number"));
				checkin.setFolioBalance(resultSet.getString("folio_balance"));
				checkin.setPhone(resultSet.getString("phone"));
				checkin.setEmail(resultSet.getString("email"));
				checkin.setAddress(resultSet.getString("address"));
				checkin.setRoomTypeCode(resultSet.getString("room_type_code"));
				checkin.setRoomNumber(resultSet.getString("room_number"));
				checkin.setArrDate(resultSet.getDate("arr_date"));
				checkin.setExpDepartDate(resultSet.getDate("depart_date"));
				checkin.setCorporateId(resultSet.getInt("corporate_id"));


			}
		}catch(SQLException ex){
			ex.printStackTrace();
			logger.error("Method: getcheckinDetails, " + Throwables.getStackTraceAsString(ex));
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			
		}

		return checkin;
	}
	public List<AccountMaster> getAccountMasterDetails(){
		AccountMaster accMst = null;
		List<AccountMaster> accountmaster=new ArrayList<AccountMaster>();
		Connection con = new DbConnection().getConnection();
		Statement st= null;
		ResultSet rs=null;
		String acSql="select id,code,sysdef_acc_type_id,is_tax_included from acc_mst where acc_mst.sysdef_acc_type_id not in(4,6,8,9,10,11) and is_deleted=0";
		try {
			st=con.createStatement();
			rs=st.executeQuery(acSql);
			while(rs.next()){
				accMst=new AccountMaster();
				accMst.setId(rs.getInt("id"));
				accMst.setCode(rs.getString("code"));
				accMst.setSysdef_acc_type_id(rs.getInt("sysdef_acc_type_id"));
				accMst.setIs_tax_included(rs.getBoolean("is_tax_included"));
				accountmaster.add(accMst);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Method: getAccountMasterDetails, " + Throwables.getStackTraceAsString(e));
		}
		finally {
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(st);
			dbConnection.releaseResource(con);
			
		}
		return accountmaster;
	}

	public List<AccountMaster> getAccountMasterDetailsSystem(int folioNo){
		AccountMaster accMst = null;
		List<AccountMaster> accountmaster=new ArrayList<AccountMaster>();
		Connection con = new DbConnection().getConnection();
		Statement st= null;
		ResultSet rs=null;
		//String acSql="select id,code,name from sysdef_acc_type where sysdef_acc_type.id not in(4,5,6,8,9,10,11,12) and in() ";
		String acSql=" SELECT  id,code,name from sysdef_acc_type st INNER JOIN "
		+" (SELECT DISTINCT sysdef_acc_type_id  FROM v_txn_details WHERE folio_no = "+folioNo + " AND sysdef_acc_type_id NOT IN(4,5,6,8,9,10,11,12)) AS tab "
		+" ON st.id = tab.sysdef_acc_type_id";
		
		try {
			st=con.createStatement();
			rs=st.executeQuery(acSql);
			while(rs.next()){
				accMst=new AccountMaster();
				accMst.setId(rs.getInt("id"));
				accMst.setCode(rs.getString("code"));
				accMst.setName(rs.getString("name"));
				accountmaster.add(accMst);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Method: getAccountMasterDetails, " + Throwables.getStackTraceAsString(e));
		}
		finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(st);
			dbConnection.releaseResource(rs);
		}	
		return accountmaster;
	}

	public String save(Transaction txn){
		String status="error";
		Connection connection=new DbConnection().getConnection();
		PreparedStatement newPostingPs=null;
		ResultSet rs=null;
		int txnNo=0;
		String sqlInsert="insert into txn(txn_no,folio_no,folio_bind_no,txn_source,txn_date,txn_time,acc_mst_id,"
				+ "acc_mst_code,tax_id,tax_code,include_tax,tax1_pc,tax2_pc,tax3_pc,tax4_pc,service_charge_pc,"
				+ "amount,base_amount,tax1_amount,tax2_amount,tax3_amount,tax4_amount,service_charge,nett_amount,payment_mode,txn_status,remarks,user_id,is_adjust,shift_id)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try{
			connection.setAutoCommit(false);
			String countFunction="{? = call counter(?)}";
			CallableStatement countFunct=connection.prepareCall(countFunction);
			countFunct.registerOutParameter(1, java.sql.Types.INTEGER);
			countFunct.setString(2,"txn_no");
			rs = countFunct.executeQuery();
			while(rs.next()){
				txnNo=rs.getInt(1);
			}
			newPostingPs=connection.prepareStatement(sqlInsert);

			newPostingPs.setInt(1,txnNo);
			newPostingPs.setInt(2,txn.getFolio_no());
			newPostingPs.setInt(3,txn.getFolio_bind_no());
			newPostingPs.setInt(4,txn.getTxn_source());
			newPostingPs.setString(5,txn.getTxn_date());
			newPostingPs.setString(6,txn.getTxn_time());
			newPostingPs.setInt(7,txn.getAcc_mst_id());//first line completes

			newPostingPs.setString(8, txn.getAcc_mst_code());
			newPostingPs.setInt(9,txn.getTax_id());
			newPostingPs.setString(10,txn.getTax_code());
			newPostingPs.setBoolean(11,txn.isInclude_tax());
			newPostingPs.setDouble(12,txn.getTax1_pc());
			newPostingPs.setDouble(13,txn.getTax2_pc());
			newPostingPs.setDouble(14, txn.getTax3_pc());
			newPostingPs.setDouble(15,txn.getTax4_pc());
			newPostingPs.setBigDecimal(16,txn.getServiceChargePc());
			//second line ends

			newPostingPs.setDouble(17,txn.getAmount());
			newPostingPs.setDouble(18,txn.getBase_amount());
			newPostingPs.setDouble(19,txn.getTax1_amount());
			newPostingPs.setDouble(20,txn.getTax2_amount());
			newPostingPs.setDouble(21,txn.getTax3_amount());
			newPostingPs.setDouble(22,txn.getTax4_amount());
			newPostingPs.setBigDecimal(23,txn.getServiceCharge());
			newPostingPs.setDouble(24,txn.getNett_amount());
			newPostingPs.setInt(25, txn.getPayment_mode());
			newPostingPs.setInt(26,txn.getTxn_status());
			newPostingPs.setString(27,txn.getRemarks());
			newPostingPs.setInt(28,txn.getUser_id());
			newPostingPs.setBoolean(29,txn.getIs_adjust());
			newPostingPs.setInt(30, txn.getShift_id());
			if(newPostingPs.executeUpdate()==1){
				updateTxnLog(connection, txnNo, "INSERT");
				status="success";
			}
			connection.commit();
		}catch(Exception e){
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("Method: save, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(newPostingPs);
			
		}
		return status;
	}


	public String update(Transaction txn){
		String status="error";
		Connection connection=new DbConnection().getConnection();
		PreparedStatement updatePostingPs=null;
		String sqlUpdate="update txn set acc_mst_id=?,acc_mst_code=?,tax_id=?,tax_code=?,include_tax=?,tax1_pc=?,tax2_pc=?,tax3_pc=?,tax4_pc=?,service_charge_pc=?,"
				+ "amount=?,base_amount=?,tax1_amount=?,tax2_amount=?,tax3_amount=?,tax4_amount=?,service_charge=?,nett_amount=?,remarks=?,user_id=?,is_adjust=?,shift_id=?, txn_date=? where txn_no=?";
		try{
			connection.setAutoCommit(false);
			updatePostingPs=connection.prepareStatement(sqlUpdate);
			updatePostingPs.setInt(1,txn.getAcc_mst_id());
			updatePostingPs.setString(2, txn.getAcc_mst_code());
			updatePostingPs.setInt(3,txn.getTax_id());
			updatePostingPs.setString(4,txn.getTax_code());
			updatePostingPs.setBoolean(5,txn.isInclude_tax());
			updatePostingPs.setDouble(6,txn.getTax1_pc());
			updatePostingPs.setDouble(7,txn.getTax2_pc());
			updatePostingPs.setDouble(8, txn.getTax3_pc());
			updatePostingPs.setDouble(9,txn.getTax4_pc());
			updatePostingPs.setBigDecimal(10,txn.getServiceChargePc());
			//second line ends

			updatePostingPs.setDouble(11,txn.getAmount());
			updatePostingPs.setDouble(12,txn.getBase_amount());
			updatePostingPs.setDouble(13,txn.getTax1_amount());
			updatePostingPs.setDouble(14,txn.getTax2_amount());
			updatePostingPs.setDouble(15,txn.getTax3_amount());
			updatePostingPs.setDouble(16,txn.getTax4_amount());
			updatePostingPs.setBigDecimal(17,txn.getServiceCharge());
			updatePostingPs.setDouble(18,txn.getNett_amount());
			updatePostingPs.setString(19,txn.getRemarks());
			updatePostingPs.setInt(20,txn.getUser_id());
			updatePostingPs.setBoolean(21,txn.getIs_adjust());
			updatePostingPs.setInt(22, txn.getShift_id());
			updatePostingPs.setString(23,txn.getTxn_date());
			updatePostingPs.setInt(24,txn.getTxn_no());
			if(updatePostingPs.executeUpdate()==1){
				updateTxnLog(connection, txn.getTxn_no(), "UPDATE");
				status="success";
			}
			connection.commit();
		}catch(Exception e){
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("Method: update, " + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(updatePostingPs);
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(updatePostingPs);
			
		}
		return status;
	}

	//Transaction Transfer
	public JsonArray getRoomList(){
		Connection connection = new DbConnection().getConnection();
		Statement statement=null;
		ResultSet resultSet=null;
		JsonObject jsonObject;
		JsonArray jarray=new JsonArray();
		//String sqlRoom="select r.number as number,r.name as name,f.folio_no as fNo,f.folio_bind_no from room r inner join checkin_hdr ch on ch.room_number = r.number inner join folio f on f.checkin_no=ch.checkin_no  left join txn on txn.folio_no=f.folio_no  where ch.`status`=5  and f.folio_no not in(select txn.folio_no from txn where txn.acc_mst_id=8) group by r.number";
		String sqlRoom = "SELECT * FROM (\r\n" + 
				"select r.number as number,r.name as name,f.folio_no as fNo,f.folio_bind_no ,max(IFNULL(txn.is_disc_applied,0)) AS is_disc_applied\r\n" + 
				"from room r inner JOIN \r\n" + 
				"checkin_hdr ch on ch.room_number = r.number \r\n" + 
				"inner join folio f on f.checkin_no=ch.checkin_no  \r\n" + 
				"left join txn on txn.folio_no=f.folio_no  \r\n" + 
				"where ch.`status`=5  and f.folio_no not in(select txn.folio_no \r\n" + 
				"from txn where txn.acc_mst_id=8) group by r.number)T1 WHERE is_disc_applied!=1";
		try {	
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sqlRoom);	
			while (resultSet.next() ){
				jsonObject = new JsonObject();
				jsonObject.addProperty("roomNo", resultSet.getString("number"));
				jsonObject.addProperty("roomName", resultSet.getString("name"));
				jsonObject.addProperty("folioNO", resultSet.getString("fNo"));
				jsonObject.addProperty("folioBindNo",resultSet.getInt("folio_bind_no"));
				jarray.add(jsonObject);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Method: getRoomList, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(resultSet);
			
		}
		return jarray;
	}
	//Payment List
	public JsonArray getTransactionDetails(int folioNo){
		Connection connection= new DbConnection().getConnection();
		Statement statement=null;
		ResultSet rs=null;
		JsonObject jsonObject;
		JsonArray jsonArray = new JsonArray();
		
		String sqlPaymentList = "select IFNULL(txn.is_disc_applied,0)AS is_disc_applied,txn.is_disc_applied,txn.txn_no,txn.folio_no,txn.folio_bind_no,txn.txn_source,txn.txn_date,txn.acc_mst_id,txn.acc_mst_code,"
				+ "CASE WHEN txn.acc_mst_code = 'POS' THEN IFNULL(txn.edit_acc_mst_name,acc.name) ELSE acc.name END AS acc_mst_name,txn.amount,txn.base_amount,txn.nett_amount,txn.payment_mode,txn.remarks,txn.txn_status,r.name as room_name,"+
				"txn.include_tax,txn.tax1_pc,txn.tax2_pc,txn.tax3_pc,txn.tax4_pc,txn.service_charge_pc,txn.tax1_amount,txn.tax2_amount,txn.tax3_amount,txn.tax4_amount,txn.service_charge,IFNULL(txn.is_adjust,0)as is_adjust from txn left join folio f on txn.dest_folio_no=f.folio_no left join checkin_hdr ch on ch.checkin_no=f.checkin_no left join room r on r.number= ch.room_number left join acc_mst acc on txn.acc_mst_id= acc.id  where txn.folio_no="+folioNo+" ORDER BY txn.txn_date DESC, txn.acc_mst_id";
		/*String sqlPaymentList= "select txn.is_disc_applied,txn.txn_no,txn.folio_no,txn.folio_bind_no,txn.txn_source,txn.txn_date,txn.acc_mst_id,\r\n" + 
				"txn.acc_mst_code,(case when (`txn`.`is_disc_applied` = 1) then `txn`.`new_amount` else `txn`.`amount` end) AS `amount`,\r\n" + 
				"(case when (`txn`.`is_disc_applied` = 1) then `txn`.`new_base_amount` else `txn`.`base_amount` end) AS `base_amount`,\r\n" + 
				"(case when (`txn`.`is_disc_applied` = 1) then `txn`.`new_nett_amount` else `txn`.`new_nett_amount` end) AS `nett_amount`,\r\n" + 
				"txn.payment_mode,txn.remarks,txn.txn_status,r.name as room_name,txn.include_tax,txn.tax1_pc,txn.tax2_pc,txn.tax3_pc,txn.tax4_pc,txn.service_charge_pc,\r\n" + 
				"(case when (`txn`.`is_disc_applied` = 1) then `txn`.`new_tax1_amount` else `txn`.`tax1_amount` end) AS `tax1_amount`,\r\n" + 
				"(case when (`txn`.`is_disc_applied` = 1) then `txn`.`new_tax2_amount` else `txn`.`tax2_amount` end) AS `tax2_amount`,\r\n" + 
				"(case when (`txn`.`is_disc_applied` = 1) then `txn`.`new_tax3_amount` else `txn`.`tax3_amount` end) AS `tax3_amount`,\r\n" + 
				" txn.tax4_amount,txn.service_charge,\r\n" + 
				"IFNULL(txn.is_adjust,0)as is_adjust from txn \r\n" + 
				"left join folio f on txn.dest_folio_no=f.folio_no left join checkin_hdr ch on ch.checkin_no=f.checkin_no \r\n" + 
				"left join room r on r.number= ch.room_number  \r\n" + 
				"where txn.folio_no="+folioNo+"  ORDER BY txn.txn_date DESC, txn.acc_mst_id";
		*/
		try {	
			statement=connection.createStatement();
			rs=statement.executeQuery(sqlPaymentList);
			while (rs.next() ) {
				jsonObject = new JsonObject();
				jsonObject.addProperty("id", rs.getInt("txn_no"));
				jsonObject.addProperty("txnDate", rs.getString("txn_date"));
				jsonObject.addProperty("amount", rs.getDouble("amount"));
				jsonObject.addProperty("baseAmount", rs.getDouble("base_amount"));
				jsonObject.addProperty("nettAmount", rs.getDouble("nett_amount"));
				jsonObject.addProperty("accId",rs.getInt("acc_mst_id"));
				jsonObject.addProperty("accCode", rs.getString("acc_mst_code"));
				jsonObject.addProperty("txnSource",rs.getInt("txn_source"));
				jsonObject.addProperty("paymentMode",rs.getInt("payment_mode"));
				jsonObject.addProperty("remarks", rs.getString("remarks"));
				jsonObject.addProperty("txnStatus",rs.getInt("txn_status"));
				jsonObject.addProperty("roomName",rs.getString("room_name"));
				//jsonObject.addProperty("accName", getAccountMasterName(rs.getInt("acc_mst_id")));
				jsonObject.addProperty("accName", rs.getString("acc_mst_name"));

				jsonObject.addProperty("inclTax",rs.getBoolean("include_tax"));
				jsonObject.addProperty("tax1_pc",rs.getDouble("tax1_pc"));
				jsonObject.addProperty("tax2_pc",rs.getDouble("tax2_pc"));
				jsonObject.addProperty("tax3_pc",rs.getDouble("tax3_pc"));
				jsonObject.addProperty("tax4_pc",rs.getDouble("tax4_pc"));
				jsonObject.addProperty("tax1_amount",rs.getDouble("tax1_amount"));
				jsonObject.addProperty("tax2_amount",rs.getDouble("tax2_amount"));
				jsonObject.addProperty("tax3_amount",rs.getDouble("tax3_amount"));
				jsonObject.addProperty("tax4_amount",rs.getDouble("tax4_amount"));
				jsonObject.addProperty("service_charge_pc",rs.getBigDecimal("service_charge_pc"));
				jsonObject.addProperty("service_charge",rs.getBigDecimal("service_charge"));
				jsonObject.addProperty("is_adjust",rs.getBoolean("is_adjust"));
				jsonObject.addProperty("is_disc_applied",rs.getBoolean("is_disc_applied"));
				

				jsonArray.add(jsonObject);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Method: getTransactionDetails, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(rs);
			
		}
		return jsonArray;
	}

	public Transaction getTxnDetailsById(int txnNo){
		Transaction txn=null;
		String sql="select * from txn where txn_no="+txnNo;
		Statement selectStatement=null;
		ResultSet rs=null;
		Connection con=new DbConnection().getConnection();
		try {
			selectStatement=con.createStatement();
			rs=selectStatement.executeQuery(sql);
			if(rs.next()){
				txn=new Transaction();
				txn.setTxn_source(rs.getInt("txn_source"));
				txn.setAcc_mst_id(rs.getInt("acc_mst_id"));
				txn.setAcc_mst_code(rs.getString("acc_mst_code"));
				txn.setTax_id(rs.getInt("tax_id"));
				txn.setTax_code(rs.getString("tax_code"));
				txn.setInclude_tax(rs.getBoolean("include_tax"));
				txn.setTax1_pc(rs.getDouble("tax1_pc"));
				txn.setTax2_pc(rs.getDouble("tax2_pc"));
				txn.setTax3_pc(rs.getDouble("tax3_pc"));
				txn.setTax4_pc(rs.getDouble("tax4_pc"));
				txn.setServiceChargePc(rs.getBigDecimal("service_charge_pc"));
				txn.setAmount(rs.getDouble("amount"));
				txn.setBase_amount(rs.getDouble("base_amount"));
				txn.setTax1_amount(rs.getDouble("tax1_amount"));
				txn.setTax2_amount(rs.getDouble("tax2_amount"));
				txn.setTax3_amount(rs.getDouble("tax3_amount"));
				txn.setTax4_amount(rs.getDouble("tax4_amount"));
				txn.setServiceCharge(rs.getBigDecimal("service_charge"));
				txn.setNett_amount(rs.getDouble("nett_amount"));
				txn.setPayment_mode(rs.getInt("payment_mode"));
				txn.setTxn_status(rs.getInt(1));
				txn.setRemarks(rs.getString("remarks"));
				txn.setUser_id(rs.getInt("user_id"));
				txn.setReceived_from(rs.getString("received_from"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Method: getTxnDetailsById, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		finally {
			dbConnection.releaseResource(selectStatement);
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(rs);
			
		}
		return txn;
	}

	public String newTransafer(int sourseFolioNO,Transaction txnDest,JSONObject selectedobj){
		String status="error";
		Connection connection=new DbConnection().getConnection();
		PreparedStatement newTransferPs=null;
		PreparedStatement setStatusPs=null;
		PreparedStatement updateTxnPs=null;
		ResultSet rs=null;
		int txnNo=0;

		String sql="update txn set dest_folio_no=? where txn_no=?";
		String sqlInsert="insert into txn(txn_no,folio_no,folio_bind_no,txn_source,txn_date,txn_time,acc_mst_id,"
				+ "acc_mst_code,tax_id,tax_code,include_tax,tax1_pc,tax2_pc,tax3_pc,tax4_pc,service_charge_pc,"
				+ "amount,base_amount,tax1_amount,tax2_amount,tax3_amount,tax4_amount,service_charge,nett_amount,payment_mode,txn_status,remarks,user_id,received_from,source_folio_no,shift_id,source_txn_no)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sqlstatus="update txn set txn_status=2 where txn_no=?";
		try {
			connection.setAutoCommit(false);
			for(Iterator iterator = selectedobj.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				if(selectedobj.get(key).toString()=="true"){
					newTransferPs=connection.prepareStatement(sql);
					newTransferPs.setInt(1,txnDest.getFolio_no());
					newTransferPs.setInt(2,Integer.parseInt(key));
					if(newTransferPs.executeUpdate()==1){
						updateTxnLog(connection, Integer.parseInt(key), "UPDATE");
						setStatusPs=connection.prepareStatement(sqlstatus);
						setStatusPs.setInt(1,Integer.parseInt(key));
						if(setStatusPs.executeUpdate()==1){
							updateTxnLog(connection, Integer.parseInt(key), "UPDATE");
							String countFunction="{? = call counter(?)}";
							CallableStatement countFunct=connection.prepareCall(countFunction);
							countFunct.registerOutParameter(1, java.sql.Types.INTEGER);
							countFunct.setString(2,"txn_no");
							rs = countFunct.executeQuery();
							while(rs.next()){
								txnNo=rs.getInt(1);
							}
							updateTxnPs=connection.prepareStatement(sqlInsert);
							Transaction txn=getTxnDetailsById(Integer.parseInt(key));
							updateTxnPs.setInt(1,txnNo);
							updateTxnPs.setInt(2,txnDest.getFolio_no());
							updateTxnPs.setInt(3,txnDest.getFolio_bind_no());
							updateTxnPs.setInt(4,txn.getTxn_source());
							updateTxnPs.setString(5,txnDest.getTxn_date());
							updateTxnPs.setString(6,txnDest.getTxn_time());
							updateTxnPs.setInt(7,txn.getAcc_mst_id());//first line completes

							updateTxnPs.setString(8, txn.getAcc_mst_code());
							updateTxnPs.setInt(9,txn.getTax_id());
							updateTxnPs.setString(10,txn.getTax_code());
							updateTxnPs.setBoolean(11,txn.isInclude_tax());
							updateTxnPs.setDouble(12,txn.getTax1_pc());
							updateTxnPs.setDouble(13,txn.getTax2_pc());
							updateTxnPs.setDouble(14, txn.getTax3_pc());
							updateTxnPs.setDouble(15,txn.getTax4_pc());
							updateTxnPs.setBigDecimal(16,txn.getServiceChargePc());//second line ends

							updateTxnPs.setDouble(17,txn.getAmount());
							//updateTxnPs.setDouble(18,txn.getAmount());
							updateTxnPs.setDouble(18,txn.getBase_amount());
							updateTxnPs.setDouble(19,txn.getTax1_amount());
							updateTxnPs.setDouble(20,txn.getTax2_amount());
							updateTxnPs.setDouble(21,txn.getTax3_amount());
							updateTxnPs.setDouble(22,txn.getTax4_amount());
							updateTxnPs.setBigDecimal(23,txn.getServiceCharge());
							updateTxnPs.setDouble(24,txn.getNett_amount());
							updateTxnPs.setInt(25, txn.getPayment_mode());
							updateTxnPs.setInt(26,1);
							updateTxnPs.setString(27,txn.getRemarks());
							updateTxnPs.setInt(28,txn.getUser_id());
							
							updateTxnPs.setString(29,txn.getReceived_from());
							updateTxnPs.setInt(30,sourseFolioNO);
							updateTxnPs.setInt(31,txnDest.getShift_id());

							updateTxnPs.setInt(32,Integer.parseInt(key));
							if(updateTxnPs.executeUpdate()==1){
								updateTxnLog(connection, txnNo, "INSERT");
								status="success";
							}
						}
					}else{
						break;
					}
				}
			}
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("Method: newTransafer, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(newTransferPs);
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(rs);
			
		}
		return status;
	}


	@Override
	public Object getOrderDetails(Integer txnNo) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		Object resultObj = null;
		List<Object> refundList = new ArrayList<>();
		String sql="";
		String refundSql = "";
		String sourceTxnNo = "";
		String recvdFrom = "";
		String sourceQuery = "SELECT source_txn_no, received_from FROM txn WHERE txn_no="+txnNo;
		Statement selectStatement=null;
		Statement getSourceStatement = null;
		Statement refundStatement = null;
		ResultSet rs=null;
		ResultSet sourceRs=null;
		ResultSet refundRs=null;
		Connection con=new DbConnection().getConnection();
		try {
			getSourceStatement = con.createStatement();
			sourceRs = getSourceStatement.executeQuery(sourceQuery);
			if(sourceRs.next()) {
				sourceTxnNo = sourceRs.getString("source_txn_no");
				recvdFrom = sourceRs.getString("received_from");
			}
			
			sql = "SELECT txn_dtl.bill_details FROM  txn " + 
					"JOIN txn_dtl ON txn.txn_no = txn_dtl.txn_no " + 
					"WHERE  txn.received_from='"+recvdFrom+ "' AND   txn.source_txn_no='"+sourceTxnNo+"'  AND   txn.acc_mst_code='POS'";
			refundSql = "SELECT txn_dtl.bill_details FROM  txn " + 
					"JOIN txn_dtl ON txn.txn_no = txn_dtl.txn_no " + 
					"WHERE  txn.received_from='"+recvdFrom+ "' AND   txn.source_txn_no='"+sourceTxnNo+"'  AND   txn.acc_mst_code='POS-REFUND'";
			selectStatement = con.createStatement();
			rs=selectStatement.executeQuery(sql);
			if(rs.next()){
				resultObj = rs.getObject("bill_details");
				resultMap.put("bill_details", resultObj);
			}
			refundStatement = con.createStatement();
			refundRs = refundStatement.executeQuery(refundSql);
			
			while (refundRs.next()) {
				refundList.add(refundRs.getObject("bill_details"));
			}
			resultMap.put("refund_details", refundList);
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(refundStatement);
			dbConnection.releaseResource(rs);
			
		}
		
		return resultMap;
	}


	@Override
	public List<Object> getAccountTypeName() throws Exception {
		
		List<Object> accMstList = new ArrayList<>();
		String sql = "SELECT id, code, name FROM acc_mst WHERE is_deleted = 0";
		Statement selectStatement=null;
		ResultSet rs=null;
		Connection con=new DbConnection().getConnection();
		try {
			selectStatement = con.createStatement();
			rs = selectStatement.executeQuery(sql);
			while(rs.next()) {
				AccountMaster accObj = new AccountMaster();
				accObj.setId(rs.getInt("id"));
				accObj.setCode(rs.getString("code"));
				accObj.setName(rs.getString("name"));
				
				accMstList.add(accObj);
			}
				
			}catch (Exception e) {
			//	System.out.println(e);
			}
		finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(selectStatement);
			dbConnection.releaseResource(rs);
			
		}
			
		return accMstList;
	}

	public String getAccountMasterName(int id) {
		Session session = sessionFactory.getCurrentSession();
		AccountMaster accountMaster = (AccountMaster) session.get(AccountMaster.class, id);
		return accountMaster.getName();
	}


	@Override
	public List<AccountMaster> getSystemAccMstList() {
		AccountMaster accMst = null;
		List<AccountMaster> accountmaster=new ArrayList<AccountMaster>();
		Connection con = new DbConnection().getConnection();
		Statement st= null;
		ResultSet rs=null;
		String acSql="select id,code,name,description from sysdef_acc_type where sysdef_acc_type.id in(1,2,3)";
				
		try {
			st=con.createStatement();
			rs=st.executeQuery(acSql);
			while(rs.next()){
				accMst=new AccountMaster();
				accMst.setId(rs.getInt("id"));
				accMst.setCode(rs.getString("code"));
				accMst.setName(rs.getString("name"));
				accMst.setDescription(rs.getString("description"));
				accountmaster.add(accMst);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Method: getAccountMasterDetails, " + Throwables.getStackTraceAsString(e));
		}
		finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(st);
			dbConnection.releaseResource(rs);
		}	
		return accountmaster;
	}


	@Override
	public List<AccountMaster> getAccMstList() {
		AccountMaster accMst = null;
		List<AccountMaster> accountmaster=new ArrayList<AccountMaster>();
		Connection con = new DbConnection().getConnection();
		Statement st= null;
		ResultSet rs=null;
		String acSql=" SELECT  id,code,name from acc_mst where sysdef_acc_type_id = 3 and id!=13";
		
		try {
			st=con.createStatement();
			rs=st.executeQuery(acSql);
			while(rs.next()){
				accMst=new AccountMaster();
				accMst.setId(rs.getInt("id"));
				accMst.setCode(rs.getString("code"));
				accMst.setName(rs.getString("name"));
				accountmaster.add(accMst);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Method: getAccountMasterDetails, " + Throwables.getStackTraceAsString(e));
		}
		finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(st);
			dbConnection.releaseResource(rs);
		}	
		return accountmaster;
	}


	@Override
	public String editAcessValue(String code) throws Exception {
		// TODO Auto-generated method stub
		Connection con = new DbConnection().getConnection();
		Statement st= null;
		String value = null;
		ResultSet rs=null;
		String acSql=" SELECT  code,value from editaccess where code ='"+code+"'";
		
		try {
			st=con.createStatement();
			rs=st.executeQuery(acSql);
			while(rs.next()){
				value = rs.getString("value");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Method: editAcessValue, " + Throwables.getStackTraceAsString(e));
		}
		finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(st);
			dbConnection.releaseResource(rs);
		}	
		return value;
	}


	@Override
	public List<AccountMaster> getPosAccountMasterDetails() throws Exception {
		// TODO Auto-generated method stub
		AccountMaster accMst = null;
		List<AccountMaster> accountmaster=new ArrayList<AccountMaster>();
		Connection con = new DbConnection().getConnection();
		Statement st= null;
		ResultSet rs=null;
		String acSql="select id,name from acc_mst where acc_mst.sysdef_acc_type_id = 3 and id NOT IN (12,13) and is_deleted=0";
		try {
			st=con.createStatement();
			rs=st.executeQuery(acSql);
			while(rs.next()){
				accMst=new AccountMaster();
				accMst.setId(rs.getInt("id"));
				accMst.setName(rs.getString("name"));
				
				accountmaster.add(accMst);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Method: getPosAccountMasterDetails, " + Throwables.getStackTraceAsString(e));
		}
		finally {
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(st);
			dbConnection.releaseResource(con);
			
		}
		return accountmaster;
	}


	@Override
	public String updatePOSAccName(int txnId, String posAccMStName) throws Exception {
		// TODO Auto-generated method stub
		String status="error";
		Connection connection=new DbConnection().getConnection();
		PreparedStatement updatePostingPs=null;
		String sqlUpdate="update txn set edit_acc_mst_name=? where txn_no=?";
		try{
			connection.setAutoCommit(false);
			updatePostingPs=connection.prepareStatement(sqlUpdate);
			updatePostingPs.setString(1,posAccMStName);
			updatePostingPs.setInt(2, txnId);
			
			if(updatePostingPs.executeUpdate()==1){
				updateTxnLog(connection, txnId, "UPDATE");
				status="success";
				
			}
			connection.commit();
		}catch(Exception e){
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("Method: updatePOSAccName, " + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(updatePostingPs);
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(updatePostingPs);
			
		}
		return status;
	}
		
}