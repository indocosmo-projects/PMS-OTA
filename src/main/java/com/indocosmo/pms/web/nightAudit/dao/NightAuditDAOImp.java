package com.indocosmo.pms.web.nightAudit.dao;

import org.springframework.stereotype.Repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.enumerator.ReservationStatus;
import com.indocosmo.pms.enumerator.TxnSource;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.checkIn.model.CheckInHdr;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.reservation_test.model.ResvHdr;
import com.indocosmo.pms.web.transaction.model.Transaction;
import com.thoughtworks.xstream.XStream;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NightAuditDAOImp implements NightAuditDAO {

	private DbConnection dbCconnection = null;
	public NightAuditDAOImp() {		
		dbCconnection =  new DbConnection();
	}

	//No Show List
	public JsonArray getArrivalList(){
		Connection connection=dbCconnection.getConnection();
		Statement statement=null;
		ResultSet rs=null;
		JsonObject jsonObject;
		JsonArray jsonArray = new JsonArray();
		String sqlArrivalList = "select rh.resv_no,rh.folio_bind_no,rh.min_arr_date,rh.resv_by_first_name as  firstName,rh.resv_by_last_name,rh.corporate_name,rh.resv_by_mail as email,rh.resv_by_phone as phone,rh.resv_date,rh.`status`,rd.resv_dtl_no,rd.room_type_id,rd.room_type_code,rd.rate_code,rd.occupancy,r.name as roomName,rm.resv_room_no,rm.room_number,rm.is_noshow,rm.noshow_date,rm.noshow_reason,rm.first_name,rm.last_name,rm.gender, rm.address,rm.email,rm.phone,rm.nationality from resv_hdr rh inner join resv_dtl rd on rh.resv_no=rd.resv_no inner join resv_room rm on rd.resv_dtl_no=rm.resv_dtl_no left join room r on  rm.room_number=r.number where rh.`status` in (0,1,3,4) and rm.room_status in(0,1) and rh.min_arr_date=(select system_setting.hotel_date from system_setting)";
		try {	
			statement=connection.createStatement();
			rs=statement.executeQuery(sqlArrivalList);
			while (rs.next() ) {
				jsonObject = new JsonObject();
				jsonObject.addProperty("resvNo", rs.getInt("resv_no"));
				jsonObject.addProperty("folioBindNo", rs.getInt("folio_bind_no"));
				jsonObject.addProperty("arrivalDate", rs.getString("min_arr_date"));
				jsonObject.addProperty("resvDate", rs.getString("resv_date"));
				jsonObject.addProperty("corpName", rs.getString("corporate_name"));
				//jsonObject.addProperty("firstName", rs.getString("resv_by_first_name"));
				//jsonObject.addProperty("resvByMail", rs.getString("resv_by_mail"));
				//jsonObject.addProperty("resvByPhone",rs.getString("resv_by_phone"));
				jsonObject.addProperty("status", rs.getInt("status"));
				jsonObject.addProperty("resvDtlNo",rs.getInt("resv_dtl_no"));
				jsonObject.addProperty("roomTypeId",rs.getInt("room_type_id"));
				jsonObject.addProperty("roomTypeCode", rs.getString("room_type_code"));
				jsonObject.addProperty("rateCode", rs.getString("rate_code"));
				jsonObject.addProperty("occupancy", rs.getInt("occupancy"));
				jsonObject.addProperty("roomName", rs.getString("roomName"));
				jsonObject.addProperty("resvRoomNo",rs.getString("resv_room_no"));
				jsonObject.addProperty("isNoShow", rs.getInt("is_noshow"));
				jsonObject.addProperty("noShowDate", rs.getString("noshow_date"));
				jsonObject.addProperty("noShowReason",rs.getString("noshow_reason"));
				jsonObject.addProperty("firstName",rs.getString("firstName"));
				jsonObject.addProperty("lastName",rs.getString("resv_by_last_name"));
				jsonObject.addProperty("gender", rs.getString("gender"));
				jsonObject.addProperty("address",rs.getString("address"));
				jsonObject.addProperty("email", rs.getString("email"));
				jsonObject.addProperty("phone",rs.getString("phone"));
				jsonObject.addProperty("nationality", rs.getString("nationality"));
				jsonArray.add(jsonObject);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbCconnection.releaseResource(connection);
			dbCconnection.releaseResource(statement);
			dbCconnection.releaseResource(rs);
		}
		return jsonArray;
	}


	//Expected Departure List
	public JsonArray getDepartList(){
		Connection connection=dbCconnection.getConnection();
		Statement statement=null;
		ResultSet rs=null;
		JsonObject jsonObject;
		JsonArray jsonArray = new JsonArray();
		String sqlDepartList = "select ch.checkin_no,ch.resv_no,ch.corporate_name,ch.room_type_code,ch.folio_bind_no,r.name as roomName,ch.arr_date,ch.exp_depart_date,ch.rate_code,"+
				"ch.occupancy,cd.first_name,cd.last_name,cd.address,cd.email,cd.gender,cd.phone,cd.remarks,fb.folio_balance  from checkin_hdr ch inner join checkin_dtl cd on ch.checkin_no=cd.checkin_no"+
				" inner join room r on ch.room_number=r.number inner join folio f on f.checkin_no=ch.checkin_no left join v_list_folio_balance fb on fb.folio_no=f.folio_no where cd.is_sharer=0 and ch.status=5 and (ch.exp_depart_date=(select system_setting.hotel_date from system_setting) or ch.is_fully_settled=1)";
		try {	
			statement=connection.createStatement();
			rs=statement.executeQuery(sqlDepartList);
			while (rs.next() ) {
				jsonObject = new JsonObject();
				jsonObject.addProperty("checkinNo", rs.getInt("checkin_no"));
				jsonObject.addProperty("resvNo", rs.getInt("resv_no"));
				jsonObject.addProperty("corpName", rs.getString("corporate_name"));
				jsonObject.addProperty("folioBindNo", rs.getInt("folio_bind_no"));
				jsonObject.addProperty("arrivalDate", rs.getString("arr_date"));
				jsonObject.addProperty("expDepartDate", rs.getString("exp_depart_date"));
				jsonObject.addProperty("roomTypeCode", rs.getString("room_type_code"));
				jsonObject.addProperty("rateCode", rs.getString("rate_code"));
				jsonObject.addProperty("occupancy", rs.getInt("occupancy"));
				jsonObject.addProperty("roomName", rs.getString("roomName"));
				jsonObject.addProperty("firstName",rs.getString("first_name"));
				jsonObject.addProperty("lastName",rs.getString("last_name"));
				jsonObject.addProperty("gender", rs.getString("gender"));
				jsonObject.addProperty("address",rs.getString("address"));
				jsonObject.addProperty("email", rs.getString("email"));
				jsonObject.addProperty("phone",rs.getString("phone"));
				jsonObject.addProperty("remarks", rs.getString("remarks"));
				jsonObject.addProperty("folioBal",rs.getString("folio_balance"));
				jsonArray.add(jsonObject);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbCconnection.releaseResource(connection);
			dbCconnection.releaseResource(statement);
			dbCconnection.releaseResource(rs);
		}
		return jsonArray;
	}

	//InHouse List
	public JsonArray getInHouseList(String hotelDate){
		Connection connection=dbCconnection.getConnection();
		Statement statement=null;
		ResultSet rs=null;
		JsonObject jsonObject;
		JsonArray jsonArray = new JsonArray();
		String sqlInHouseList = "select ch.checkin_no,ch.room_number,ch.room_type_code,ch.rate_code,ch.occupancy,cd.checkin_dtl_no,cd.first_name,cd.last_name,cd.phone,f.folio_no,f.folio_bind_no,cr.room_charge,cr.include_tax,cr.night from checkin_hdr ch inner join checkin_dtl cd on ch.checkin_no=cd.checkin_no inner join checkin_rate cr  on cr.checkin_dtl_no=cd.checkin_dtl_no and cr.night=(DATEDIFF('"+hotelDate+"',ch.arr_date)+1) inner join folio f on  f.checkin_no=ch.checkin_no where cd.is_sharer=0 and ch.status=5 and ch.exp_depart_date >"+hotelDate;				
		//System.out.println(sqlInHouseList);
		try {	
			statement=connection.createStatement();
			rs=statement.executeQuery(sqlInHouseList);
			while (rs.next() ) {
				jsonObject = new JsonObject();
				jsonObject.addProperty("checkinNo",rs.getString("checkin_no"));
				jsonObject.addProperty("roomNum",rs.getString("room_number"));
				jsonObject.addProperty("roomTypeCode",rs.getString("room_type_code"));
				jsonObject.addProperty("rateCode",rs.getString("rate_code"));
				jsonObject.addProperty("occupancy",rs.getString("occupancy"));
				jsonObject.addProperty("checkinDtlNo",rs.getString("checkin_dtl_no"));
				jsonObject.addProperty("firstName",rs.getString("first_name"));
				jsonObject.addProperty("lastName",rs.getString("last_name"));
				jsonObject.addProperty("phone",rs.getString("phone"));
				jsonObject.addProperty("folioNo", rs.getInt("folio_no"));
				jsonObject.addProperty("folioBindNo", rs.getInt("folio_bind_no"));
				jsonObject.addProperty("roomCharge",rs.getString("room_charge"));
				jsonObject.addProperty("inctax", rs.getInt("include_tax"));
				jsonArray.add(jsonObject);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbCconnection.releaseResource(connection);
			dbCconnection.releaseResource(statement);
			dbCconnection.releaseResource(rs);
		}
		return jsonArray;
	}

	//get Rate Details
	public JsonObject getRoomRateForCurrentDate(String hotelDate,int checkInNo){
		Connection connection=dbCconnection.getConnection();
		CallableStatement txnCountCs=null;
		PreparedStatement txnPs=null;
		JsonArray txnList= new JsonArray();
		String txnsSql ="select txn.txn_no,txn.acc_mst_id,txn.acc_mst_code,txn.amount,txn.base_amount,txn.service_charge,txn.nett_amount,(txn.tax1_amount+txn.tax2_amount+txn.tax3_amount+txn.tax4_amount)as tax from txn inner join folio on folio.folio_no = txn.folio_no where txn.txn_date = ? and folio.checkin_no=?";
		ResultSet rs=null;
		JsonObject jsonObject=new JsonObject();
		JsonObject expectedPostings=new JsonObject();
		try {		
			txnCountCs=connection.prepareCall("{call getRoomChargesWithinDate(?,?)}");
			txnCountCs.setString(1,hotelDate);
			txnCountCs.setInt(2,checkInNo);
			rs=txnCountCs.executeQuery();
			if(rs.next()){
				expectedPostings.addProperty("roomTypeId",rs.getInt("room_type_id"));
				expectedPostings.addProperty("roomTypeCode",rs.getString("room_type_code"));
				expectedPostings.addProperty("roomNum",rs.getString("room_number"));
				expectedPostings.addProperty("rateId",rs.getInt("rate_id"));
				expectedPostings.addProperty("rateCode",rs.getString("rate_code"));
				expectedPostings.addProperty("occupancy",rs.getInt("occupancy"));
				expectedPostings.addProperty("roomCharge",rs.getDouble("room_charge"));
				expectedPostings.addProperty("tax",rs.getDouble("tax"));
				expectedPostings.addProperty("tax1Amt",rs.getDouble("tax1_amount"));
				expectedPostings.addProperty("tax2Amt",rs.getDouble("tax2_amount"));
				expectedPostings.addProperty("tax3Amt",rs.getDouble("tax3_amount"));
				expectedPostings.addProperty("tax4Amt",rs.getDouble("tax4_amount"));
				expectedPostings.addProperty("serviceCharge",rs.getDouble("service_charge_amount"));
				expectedPostings.addProperty("tax1Pc",rs.getDouble("tax1_percentage"));
				expectedPostings.addProperty("tax2Pc",rs.getDouble("tax2_percentage"));
				expectedPostings.addProperty("tax3Pc",rs.getDouble("tax3_percentage"));
				expectedPostings.addProperty("tax4Pc",rs.getDouble("tax4_percentage"));
				expectedPostings.addProperty("serviceChargePc",rs.getDouble("service_charge_percentage"));
				expectedPostings.addProperty("finalRoomCharge",rs.getDouble("nett_room_charge"));
				expectedPostings.addProperty("isTaxInc",rs.getInt("is_tax_included"));
				expectedPostings.addProperty("discId",rs.getInt("disc_id"));
				expectedPostings.addProperty("discCode",rs.getString("disc_code"));
				expectedPostings.addProperty("disc",rs.getDouble("disc"));
				expectedPostings.addProperty("isDiscPc",rs.getInt("is_disc_percentage"));
				expectedPostings.addProperty("finalDiscAmt",rs.getDouble("final_disc_amt"));
				expectedPostings.addProperty("disc_tax", rs.getDouble("disc_tax"));
			}
			rs.close();
			txnPs= connection.prepareStatement(txnsSql);
			txnPs.setString(1,hotelDate);
			txnPs.setInt(2,checkInNo);
			rs=txnPs.executeQuery();
			while(rs.next()){
				JsonObject txn= new JsonObject();
				txn.addProperty("txn_no",rs.getInt("txn_no"));
				txn.addProperty("acc_mst_id",rs.getInt("acc_mst_id"));
				txn.addProperty("acc_mst_code",rs.getString("acc_mst_code"));
				txn.addProperty("amount",rs.getDouble("amount"));
				txn.addProperty("tax",rs.getDouble("tax"));
				txn.addProperty("base_amount",rs.getDouble("base_amount"));
				txn.addProperty("service_charge",rs.getBigDecimal("service_charge"));
				txn.addProperty("nett_amount",rs.getDouble("nett_amount"));
				txnList.add(txn);
			}
//			Gson gson = new Gson();
			jsonObject.add("expected",expectedPostings);
			jsonObject.add("txnToday",txnList);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbCconnection.releaseResource(connection);
			dbCconnection.releaseResource(txnCountCs);
			dbCconnection.releaseResource(rs);
		}
		return jsonObject;
	}

	public void setNightAuditStage(int stage){
		Connection connection = dbCconnection.getConnection();
		String updateSql = "update system_setting set night_audit_stage=?";
		PreparedStatement updatePs=null;
		try{
			updatePs=connection.prepareStatement(updateSql);
			updatePs.setInt(1,stage);
			updatePs.executeUpdate();			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbCconnection.releaseResource(connection);
			dbCconnection.releaseResource(updatePs);			
		}
	}

	public boolean changeHotelDate(String newHotDate){
		boolean isSave=false;
		Connection connection = dbCconnection.getConnection();
		String updateSql = "update system_setting set hotel_date=?";
		PreparedStatement updatePs=null;
		try{
			updatePs=connection.prepareStatement(updateSql);
			updatePs.setString(1,newHotDate);
			if(updatePs.executeUpdate()==1){
				isSave=true;	
			}		
		}catch(Exception e){
			e.printStackTrace();
			throw new CustomException();
		}finally{
			dbCconnection.releaseResource(connection);
			dbCconnection.releaseResource(updatePs);			
		}
		return isSave;
	}




	public String postNightAudit(Transaction txn){
		String status="error";
		if((txn.getAcc_mst_id()!=1 && txn.getTxn_source()!=1 || (getIsRcExist(txn.getTxn_date(),txn.getFolio_no()))==0)){
	//		TxnDaoImpl txnDao = new TxnDaoImpl();
			int isDisExist=0;
			int countDis=0;
			ResultSet result=null;
			PreparedStatement isExist=null;
			PreparedStatement checkDis=null;
			Connection connection=dbCconnection.getConnection();
			PreparedStatement nightAuditPs=null;
			PreparedStatement updateQuerySet=null;
			ResultSet rs=null;
			ResultSet rst=null;
			try{
				String countFunction="{? = call counter(?)}";
				CallableStatement countFunct=connection.prepareCall(countFunction);
				countFunct.registerOutParameter(1, java.sql.Types.INTEGER);
				countFunct.setString(2,"txn_no");
				rs = countFunct.executeQuery();
				while(rs.next()){
					txn.setTxn_no(rs.getInt(1));
				//	System.out.println(rs.getInt(1)+"  counter txn no");
				}
				rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}

			String sqlNightAudit="insert into txn(txn_no,folio_no,folio_bind_no,txn_source,txn_date,txn_time,acc_mst_id,"
					+ "acc_mst_code,tax_id,tax_code,include_tax,tax1_pc,tax2_pc,tax3_pc,tax4_pc,service_charge_pc,"
					+ "amount,base_amount,tax1_amount,tax2_amount,tax3_amount,tax4_amount,service_charge,nett_amount,txn_status,"
					+ "remarks,user_id,is_confirmed,confirm_by,shift_id)"+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			String existQuery="SELECT COUNT(txn_no) as count FROM txn WHERE folio_no='"+txn.getFolio_no()+"' AND txn_source=2 AND txn_date='"+txn.getTxn_date()+"'AND acc_mst_id="+txn.getAcc_mst_id();
			String updateQuery="UPDATE txn SET txn_no=?,folio_no=?,folio_bind_no=?,txn_source=?,txn_date=?,txn_time=?,acc_mst_id=?,"
					+ "acc_mst_code=?,tax_id=?,tax_code=?,include_tax=?,tax1_pc=?,tax2_pc=?,tax3_pc=?,tax4_pc=?,service_charge_pc=?,"
					+ "amount,base_amount,tax1_amount,tax2_amount,tax3_amount,tax4_amount,service_charge,nett_amount,txn_status,"
					+ "remarks=?,user_id=?,is_confirmed=?,confirm_by=?,shift_id=?";
			String checkTxnSource = "SELECT COUNT(txn_no) as count FROM txn WHERE folio_no='"+txn.getFolio_no()+"' AND acc_mst_id=1 AND txn_source='"+TxnSource.FRONTOFFICEPOSTING.getCode()+"' AND txn_date='"+txn.getTxn_date()+"'";
			try{
				
				checkDis=connection.prepareStatement(checkTxnSource);
				rst=checkDis.executeQuery();
				while(rst.next()){
					countDis=rst.getInt("count");
				}
			}catch(Exception e){
				e.printStackTrace();	
			}
			try{
				isExist=connection.prepareStatement(existQuery);
				result=isExist.executeQuery();
				while(result.next()){
					isDisExist=result.getInt("count");
				}
				if(isDisExist!=0){
					updateQuerySet=connection.prepareStatement(updateQuery);
					updateQuerySet.setInt(1,txn.getTxn_no());
					updateQuerySet.setInt(2,txn.getFolio_no());
					updateQuerySet.setInt(3,txn.getFolio_bind_no());
					updateQuerySet.setInt(4,txn.getTxn_source());
					updateQuerySet.setString(5, txn.getTxn_date());
					updateQuerySet.setString(6,txn.getTxn_time());
					updateQuerySet.setInt(7,txn.getAcc_mst_id());
					updateQuerySet.setString(8, txn.getAcc_mst_code());
					updateQuerySet.setInt(9,txn.getTax_id());
					updateQuerySet.setString(10,txn.getTax_code());
					updateQuerySet.setBoolean(11,txn.isInclude_tax());
					updateQuerySet.setDouble(12,txn.getTax1_pc());
					updateQuerySet.setDouble(13,txn.getTax2_pc());
					updateQuerySet.setDouble(14, txn.getTax3_pc());
					updateQuerySet.setDouble(15,txn.getTax4_pc());
					updateQuerySet.setBigDecimal(16,txn.getServiceChargePc());
					updateQuerySet.setDouble(17,txn.getAmount());
					updateQuerySet.setDouble(18,txn.getBase_amount());
					updateQuerySet.setDouble(19,txn.getTax1_amount());
					updateQuerySet.setDouble(20,txn.getTax2_amount());
					updateQuerySet.setDouble(21,txn.getTax3_amount());
					updateQuerySet.setDouble(22,txn.getTax4_amount());
					updateQuerySet.setBigDecimal(23,txn.getServiceCharge());
					updateQuerySet.setDouble(24,txn.getNett_amount());
					updateQuerySet.setInt(25,txn.getTxn_status());
					updateQuerySet.setString(26,txn.getRemarks());
					updateQuerySet.setInt(27,txn.getUser_id());
					updateQuerySet.setBoolean(28,txn.isIs_confirmed());
					updateQuerySet.setInt(29,txn.getUser_id());
					updateQuerySet.setInt(30,txn.getShift_id());
					updateQuerySet.executeUpdate();
					//txnDao.updateTxnLog(connection, txn.getTxn_no(), "UPDATE");
				}else{
					nightAuditPs=connection.prepareStatement(sqlNightAudit);
					nightAuditPs.setInt(1,txn.getTxn_no());
					nightAuditPs.setInt(2,txn.getFolio_no());
					nightAuditPs.setInt(3,txn.getFolio_bind_no());
					nightAuditPs.setInt(4,txn.getTxn_source());
					nightAuditPs.setString(5, txn.getTxn_date());
					nightAuditPs.setString(6,txn.getTxn_time());
					nightAuditPs.setInt(7,txn.getAcc_mst_id());
					nightAuditPs.setString(8, txn.getAcc_mst_code());
					nightAuditPs.setInt(9,txn.getTax_id());
					nightAuditPs.setString(10,txn.getTax_code());
					nightAuditPs.setBoolean(11,txn.isInclude_tax());
					nightAuditPs.setDouble(12,txn.getTax1_pc());
					nightAuditPs.setDouble(13,txn.getTax2_pc());
					nightAuditPs.setDouble(14, txn.getTax3_pc());
					nightAuditPs.setDouble(15,txn.getTax4_pc());
					nightAuditPs.setBigDecimal(16,txn.getServiceChargePc());
					nightAuditPs.setDouble(17,txn.getAmount());
					nightAuditPs.setDouble(18,txn.getBase_amount());
					nightAuditPs.setDouble(19,txn.getTax1_amount());
					nightAuditPs.setDouble(20,txn.getTax2_amount());
					nightAuditPs.setDouble(21,txn.getTax3_amount());
					nightAuditPs.setDouble(22,txn.getTax4_amount());
					nightAuditPs.setBigDecimal(23,txn.getServiceCharge());
					nightAuditPs.setDouble(24,txn.getNett_amount());
					nightAuditPs.setInt(25,txn.getTxn_status());
					nightAuditPs.setString(26,txn.getRemarks());
					nightAuditPs.setInt(27,txn.getUser_id());
					nightAuditPs.setBoolean(28,txn.isIs_confirmed());
					nightAuditPs.setInt(29,txn.getUser_id());
					nightAuditPs.setInt(30,txn.getShift_id());
					if(countDis==0){
						if(nightAuditPs.executeUpdate()==1){
							//txnDao.updateTxnLog(connection, txn.getTxn_no(), "INSERT");
							status="success";
						}
					}else{
						status="posting";
					}
				}
			}catch(Exception e){
				e.printStackTrace();	
			}finally{
				dbCconnection.releaseResource(connection);
				dbCconnection.releaseResource(rs);
				dbCconnection.releaseResource(nightAuditPs);
			}
		}else{
			status="success";
		}
		return status;
	}

	public String confirmTransactions(String hotelDate,int userId){
		Connection connection = dbCconnection.getConnection();
		String updateSql = "update txn set is_confirmed=1,confirm_by=? where txn_date=?";
		PreparedStatement updatetxnStatusPs=null;
		String status="error";
		try{
			updatetxnStatusPs=connection.prepareStatement(updateSql);
			updatetxnStatusPs.setInt(1,userId);
			updatetxnStatusPs.setString(2,hotelDate);			
			if(updatetxnStatusPs.executeUpdate()!=0){
				status="success";
			}
		}catch(Exception e){
			status="error";
			e.printStackTrace();
		}finally{
			dbCconnection.releaseResource(connection);
			dbCconnection.releaseResource(updatetxnStatusPs);			
		}
		return status;
	}

	public JsonArray getNightAuditTransactions(String hotelDate){
		Connection connection= new DbConnection().getConnection();
		Statement statement=null;
		ResultSet rs=null;
		JsonObject jsonObject;
		JsonArray jsonArray = new JsonArray();
		String sqlPaymentList = "select txn.txn_date,txn.txn_no,txn.folio_no,txn.folio_bind_no,txn.txn_source,txn.txn_date,txn.acc_mst_id,"+
				"txn.acc_mst_code,(txn.tax1_amount+txn.tax2_amount+txn.tax3_amount+txn.tax4_amount)as tax,txn.service_charge,txn.include_tax,txn.amount,txn.base_amount,txn.nett_amount,txn.remarks,txn.txn_status,r.name as room_name from txn inner join folio f on txn.folio_no=f.folio_no inner join checkin_hdr ch on ch.checkin_no=f.checkin_no inner join room r on r.number= ch.room_number where txn.txn_source=2 AND txn.acc_mst_id NOT IN (11) and txn.txn_date='"+hotelDate+"'";
		try {	
			statement=connection.createStatement();
			rs=statement.executeQuery(sqlPaymentList);
			while (rs.next() ) {
				jsonObject = new JsonObject();
				jsonObject.addProperty("id", rs.getInt("txn_no"));
				jsonObject.addProperty("txnDate", rs.getString("txn_date"));
				jsonObject.addProperty("folioNo", rs.getInt("folio_no"));
				jsonObject.addProperty("txnDate", rs.getString("txn_date"));
				jsonObject.addProperty("incTax", rs.getInt("include_tax"));
				jsonObject.addProperty("tax", rs.getDouble("tax"));
				jsonObject.addProperty("serviceCharge", rs.getDouble("service_charge"));
				jsonObject.addProperty("amount", rs.getDouble("amount"));
				jsonObject.addProperty("baseAmount", rs.getDouble("base_amount"));
				jsonObject.addProperty("nettAmount", rs.getDouble("nett_amount"));
				jsonObject.addProperty("accId",rs.getInt("acc_mst_id"));
				jsonObject.addProperty("accCode", rs.getString("acc_mst_code"));
				jsonObject.addProperty("txnSource",rs.getInt("txn_source"));
				jsonObject.addProperty("remarks", rs.getString("remarks"));
				jsonObject.addProperty("txnStatus",rs.getInt("txn_status"));
				jsonObject.addProperty("roomName",rs.getString("room_name"));
				jsonArray.add(jsonObject);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbCconnection.releaseResource(connection);
			dbCconnection.releaseResource(rs);	
			dbCconnection.releaseResource(statement);
		}
		return jsonArray;
	}






	//get Reception Count
	public JsonObject getReceptionCount(String hotelDate){
		Connection connection=dbCconnection.getConnection();
		CallableStatement rcptnCountCs=null;
		ResultSet rs=null;
		JsonObject jsonObject=new JsonObject();
		try {		
			rcptnCountCs=connection.prepareCall("{call GetReceptionCount(?)}");
			rcptnCountCs.setString(1,hotelDate);
			rs=rcptnCountCs.executeQuery();
			if(rs.next()){
				jsonObject.addProperty("expArrival",rs.getInt("exparrivals"));
				jsonObject.addProperty("expDepart",rs.getInt("expdeparts"));
				jsonObject.addProperty("noShow",rs.getInt("noshows"));
				jsonObject.addProperty("inHouse",rs.getInt("inhouse"));	
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbCconnection.releaseResource(connection);
			dbCconnection.releaseResource(rcptnCountCs);
			dbCconnection.releaseResource(rs);
		}
		return jsonObject;
	}


	public List<ResvHdr> getCutOffDateRecord(){
		Connection connection=dbCconnection.getConnection();
		Statement statement=null;
		ResultSet resultSet=null;
		ResvHdr resvHdr=null; 
		List<ResvHdr> resvHdrList=new ArrayList<ResvHdr>();
		try{
			String sql="select resv_hdr.resv_by_first_name,resv_hdr.resv_by_last_name,resv_hdr.resv_for,resv_hdr.resv_no,resv_hdr.resv_date,resv_hdr.min_arr_date,resv_hdr.cut_off_date,resv_hdr.max_depart_date,resv_hdr.sum_num_rooms,resv_hdr.num_adults,DATEDIFF(resv_hdr.max_depart_date,resv_hdr.min_arr_date) as num_nights,resv_hdr.resv_by_mail,resv_hdr.resv_by_phone from resv_hdr where resv_hdr.cut_off_date=(select system_setting.hotel_date from system_setting) and resv_hdr.`status` in(0,1,3)";
			statement=connection.createStatement(); //
			resultSet=statement.executeQuery(sql);

			while(resultSet.next()){
				resvHdr=new  ResvHdr(); 
				resvHdr.setResvByFirstName(resultSet.getString("resv_by_first_name"));
				resvHdr.setResvByLastName(resultSet.getString("resv_by_last_name"));
				resvHdr.setNumRooms(resultSet.getShort("sum_num_rooms"));
				resvHdr.setResvNo(resultSet.getInt("resv_no"));
				resvHdr.setMinArrDate(resultSet.getDate("min_arr_date"));
				resvHdr.setCutOffDate(resultSet.getDate("cut_off_date"));
				resvHdr.setMaxDepartDate(resultSet.getDate("max_depart_date"));
				resvHdr.setResvByMail(resultSet.getString("resv_by_mail"));
				resvHdr.setResvByPhone(resultSet.getString("resv_by_phone"));
				resvHdr.setNumNights(resultSet.getByte("num_nights"));
				resvHdr.setNumAdults(resultSet.getByte("num_adults"));
				resvHdr.setResvFor(resultSet.getString("resv_for"));
				resvHdr.setResvDate(resultSet.getDate("resv_date"));
				resvHdrList.add(resvHdr);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally {

			dbCconnection.releaseResource(resultSet);
			dbCconnection.releaseResource(connection);
		}
		return resvHdrList;
	}

	public List<ResvHdr> getRecordToCancel(){
		Connection connection=dbCconnection.getConnection();
		Statement statement=null;
		ResultSet resultSet=null;
		ResvHdr resvHdr=null; 
		List<ResvHdr> resvHdrList=new ArrayList<ResvHdr>();
		try{
			String sql="select resv_hdr.resv_by_first_name,resv_hdr.resv_by_last_name,resv_hdr.resv_for,resv_hdr.resv_no,resv_hdr.resv_date,resv_hdr.min_arr_date,resv_hdr.cut_off_date,resv_hdr.max_depart_date,resv_hdr.sum_num_rooms,resv_hdr.num_adults,DATEDIFF(resv_hdr.max_depart_date,resv_hdr.min_arr_date) as num_nights,resv_hdr.resv_by_mail,resv_hdr.resv_by_phone from resv_hdr where resv_hdr.cut_off_date<(select system_setting.hotel_date from system_setting) and resv_hdr.`status` =0";
			statement=connection.createStatement(); //
			resultSet=statement.executeQuery(sql);

			while(resultSet.next()){
				resvHdr=new  ResvHdr(); 
				resvHdr.setResvByFirstName(resultSet.getString("resv_by_first_name"));
				resvHdr.setResvByLastName(resultSet.getString("resv_by_last_name"));
				resvHdr.setNumRooms(resultSet.getShort("sum_num_rooms"));
				resvHdr.setResvNo(resultSet.getInt("resv_no"));
				resvHdr.setMinArrDate(resultSet.getDate("min_arr_date"));
				resvHdr.setCutOffDate(resultSet.getDate("cut_off_date"));
				resvHdr.setMaxDepartDate(resultSet.getDate("max_depart_date"));
				resvHdr.setResvByMail(resultSet.getString("resv_by_mail"));
				resvHdr.setResvByPhone(resultSet.getString("resv_by_phone"));
				resvHdr.setNumNights(resultSet.getByte("num_nights"));
				resvHdr.setNumAdults(resultSet.getByte("num_adults"));
				resvHdr.setResvFor(resultSet.getString("resv_for"));
				resvHdr.setResvDate(resultSet.getDate("resv_date"));
				resvHdrList.add(resvHdr);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally {

			dbCconnection.releaseResource(resultSet);
			dbCconnection.releaseResource(connection);
		}
		return resvHdrList;
	}

	public boolean cancelNonNoShowRooms(int userid) throws Exception{
		boolean isSave= false;
		String sqlPartcheckin="update resv_room inner join resv_dtl on resv_room.resv_dtl_no = resv_dtl.resv_dtl_no inner join resv_hdr on resv_dtl.resv_no = resv_hdr.resv_no set resv_room.room_status=?,resv_room.is_noshow =?,resv_room.noshow_date=?,resv_room.noshow_time=?,resv_room.noshow_by=?,resv_room.noshow_reason=? where resv_hdr.`status`=? and resv_hdr.min_arr_date<(select system_setting.hotel_date from system_setting) and resv_room.is_noshow = ?";
		String sqlconfirm="update resv_room inner join resv_dtl on resv_room.resv_dtl_no = resv_dtl.resv_dtl_no inner join resv_hdr on resv_dtl.resv_no = resv_hdr.resv_no set resv_room.room_status=?,resv_room.is_noshow =?,resv_room.noshow_date=?,resv_room.noshow_time=?,resv_room.noshow_by=?,resv_room.noshow_reason=?,resv_hdr.`status`=? where (resv_hdr.`status`=? or resv_hdr.`status`=?) and resv_room.is_noshow=0 and resv_hdr.min_arr_date<(select system_setting.hotel_date from system_setting)";
		Connection con = dbCconnection.getConnection();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PreparedStatement cancelPartCheckinPs = null;
		PreparedStatement cancelConfirmedPs =null;
		try{
			con.setAutoCommit(false);
			cancelPartCheckinPs = con.prepareStatement(sqlPartcheckin);
			cancelConfirmedPs = con.prepareStatement(sqlconfirm);
			cancelPartCheckinPs.setInt(1,ReservationStatus.NOSHOW.getCode());
			cancelPartCheckinPs.setBoolean(2,true);
			cancelPartCheckinPs.setDate(3,new Date(commonSettings.getHotelDate().getTime()));
			cancelPartCheckinPs.setString(4,dateFormat.format(new java.util.Date()));
			cancelPartCheckinPs.setInt(5,userid);
			cancelPartCheckinPs.setString(6,"night audit autochange");
			cancelPartCheckinPs.setInt(7,ReservationStatus.PARTCHECKIN.getCode());
			cancelPartCheckinPs.setBoolean(8,false);
			cancelPartCheckinPs.executeUpdate();

			cancelConfirmedPs.setInt(1,ReservationStatus.NOSHOW.getCode());
			cancelConfirmedPs.setBoolean(2,true);
			cancelConfirmedPs.setDate(3,new Date(commonSettings.getHotelDate().getTime()));
			cancelConfirmedPs.setString(4,dateFormat.format(new java.util.Date()));
			cancelConfirmedPs.setInt(5,userid);
			cancelConfirmedPs.setString(6,"night audit autochange");
			cancelConfirmedPs.setInt(7,ReservationStatus.NOSHOW.getCode());
			cancelConfirmedPs.setInt(8,ReservationStatus.CONFIRMED.getCode());
			cancelConfirmedPs.setInt(9,ReservationStatus.NOSHOW.getCode());
			cancelConfirmedPs.executeUpdate();
			con.commit();
		}catch(Exception e){
			e.printStackTrace();
			isSave = false;
			con.rollback();
		}
		finally {

			dbCconnection.releaseResource(cancelConfirmedPs);
			dbCconnection.releaseResource(con);
		}

		return isSave;
	}


	public int getlastShiftDetail(String prevHotelDate) {
		ResultSet rs=null;
		Connection connection=dbCconnection.getConnection();
		Statement statement = null;
		int lastShift=0;
		String sql="SELECT id,closing_time,shift_id FROM user_shift where  opening_date='"+prevHotelDate+"' ORDER BY closing_time DESC LIMIT 1";
		try{
			statement=connection.createStatement();
			rs=statement.executeQuery(sql);
			while(rs.next()){
				lastShift=rs.getInt("shift_id");

			}

		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally {

			dbCconnection.releaseResource(rs);
			dbCconnection.releaseResource(connection);
		}
		return lastShift;
	}


	public int getIsRcExist(String prevHotelDate,int foliono) {

		int isRcExist=0;
		ResultSet rs=null;
		PreparedStatement isRc=null;
		Connection connection=dbCconnection.getConnection();
		String query="select count(folio_no) as rcexist FROM txn where txn_date='"+prevHotelDate+"' and acc_mst_id=1 and folio_no='"+foliono+"' AND (txn_status=2 OR (txn_status IN (1,3) AND source_folio_no  is NULL ))";
		try{
			isRc=connection.prepareStatement(query);
			rs=isRc.executeQuery();
			while(rs.next()){
				isRcExist=rs.getInt("rcexist");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbCconnection.releaseResource(isRc);
			dbCconnection.releaseResource(connection);
			dbCconnection.releaseResource(rs);
		}
		return isRcExist;
	}

	public boolean extendStayOneNight(List<CheckInHdr> chkinHdrs) throws Exception
	{
		boolean isSave=true;
		Connection con=dbCconnection.getConnection();
		XStream xstr=new XStream();
		con.setAutoCommit(false);
		CallableStatement st = null;
		try {
			st = con.prepareCall("{call ExtendStay(?)}");
			xstr = new XStream();
			xstr.alias("checkinItem", CheckInHdr.class);
			st.setString(1,xstr.toXML(chkinHdrs).replaceAll("\\s*[\\r\\n]+\\s*", "").trim());
			st.executeUpdate();
			con.commit();
		}catch(Exception e){
			isSave=false;
			con.rollback();
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbCconnection.releaseResource(st);
			dbCconnection.releaseResource(con);
		}
		return isSave;
	}
}