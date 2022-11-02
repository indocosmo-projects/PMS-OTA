package com.indocosmo.pms.web.payment.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.serviceTax.model.serviceTax;
import com.indocosmo.pms.web.transaction.dao.TxnDaoImpl;
import com.indocosmo.pms.web.transaction.model.Transaction;

@Repository
public class PaymentDAOImp implements PaymentDAO {
	private DbConnection dbCconnection = null;
	public PaymentDAOImp() {

		dbCconnection =  new DbConnection();
	}

	public static final Logger logger = LoggerFactory.getLogger(PaymentDAOImp.class);

	public serviceTax getserviceTax(int id, double amount){
		Connection connection=dbCconnection.getConnection();
		serviceTax sTax= new serviceTax();
		ResultSet rs=null;
		CallableStatement preparedStatement=null;
		String servTaxSql="CALL GetServiceTaxAmounts(?,?)";
		try{
			preparedStatement = connection.prepareCall(servTaxSql);
			preparedStatement.setInt(1,id);
			preparedStatement.setBigDecimal(2,BigDecimal.valueOf(amount));			
			rs=preparedStatement.executeQuery();
			if(rs.next()){
				sTax.setTotalServiceTax(rs.getBigDecimal("tp_total_service_tax").doubleValue());
				sTax.setServiceTax_amt(rs.getBigDecimal("tp_service_tax_amount").doubleValue());
				sTax.setServiceTax(rs.getBigDecimal("tp_service_tax_percentage").doubleValue());
				sTax.setAbatement(rs.getBigDecimal("tp_abatement_percentage").doubleValue());
				sTax.setCess1_amt(rs.getBigDecimal("tp_cess1_amount").doubleValue());
				sTax.setCess2_amt(rs.getBigDecimal("tp_cess2_amount").doubleValue());
				sTax.setCess3_amt(rs.getBigDecimal("tp_cess3_amount").doubleValue());
				sTax.setCess4_amt(rs.getBigDecimal("tp_cess4_amount").doubleValue());
				sTax.setCess5_amt(rs.getBigDecimal("tp_cess5_amount").doubleValue());
				sTax.setCess1_pc(rs.getBigDecimal("tp_cess1_percentage").doubleValue());
				sTax.setCess2_pc(rs.getBigDecimal("tp_cess2_percentage").doubleValue());
				sTax.setCess3_pc(rs.getBigDecimal("tp_cess3_percentage").doubleValue());
				sTax.setCess4_pc(rs.getBigDecimal("tp_cess4_percentage").doubleValue());
				sTax.setCess5_pc(rs.getBigDecimal("tp_cess5_percentage").doubleValue());
				sTax.setCess1Name(rs.getString("tp_cess1_name"));
				sTax.setCess2Name(rs.getString("tp_cess2_name"));
				sTax.setCess3Name(rs.getString("tp_cess3_name"));
				sTax.setCess4Name(rs.getString("tp_cess4_name"));
				sTax.setCess5Name(rs.getString("tp_cess5_name"));
			}
		}catch(Exception e){
			e.printStackTrace();	
			logger.error("Method : getserviceTax " + Throwables.getStackTraceAsString(e));
		}finally{
			dbCconnection.releaseResource(connection);
			dbCconnection.releaseResource(preparedStatement);
		}
		return sTax;
	}



	//Add New Payment
	public String save(Transaction txn){
		String status="error";
		Connection connection=dbCconnection.getConnection();
		PreparedStatement newPaymentPs=null;
		ResultSet rs=null;
		try{
			connection.setAutoCommit(false);
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
			String sqlNewPayment="insert into txn(txn_no,folio_no,folio_bind_no,txn_source,txn_date,txn_time,acc_mst_id,"
					+ "acc_mst_code,service_tax_id,tax_id,tax_code,include_tax,tax1_pc,tax2_pc,tax3_pc,tax4_pc,service_charge_pc,"
					+ "amount,base_amount,tax1_amount,tax2_amount,tax3_amount,tax4_amount,service_charge,nett_amount,payment_mode,corporate_id,corporate_name,payment_option,txn_status,"
					+ "remarks,user_id,received_from,shift_id)"+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			newPaymentPs=connection.prepareStatement(sqlNewPayment);
			newPaymentPs.setInt(1,txn.getTxn_no());
			newPaymentPs.setInt(2,txn.getFolio_no());
			newPaymentPs.setInt(3,txn.getFolio_bind_no());
			newPaymentPs.setInt(4,txn.getTxn_source());//source of transaction(0=FO,1=NA,2=POS)
			newPaymentPs.setString(5, txn.getTxn_date());
			newPaymentPs.setString(6,txn.getTxn_time());
			newPaymentPs.setInt(7,txn.getAcc_mst_id());//first line completes

			newPaymentPs.setString(8, txn.getAcc_mst_code());
			newPaymentPs.setInt(9,txn.getServiceTaxId());
			newPaymentPs.setInt(10,txn.getTax_id());
			newPaymentPs.setString(11,txn.getTax_code());
			newPaymentPs.setBoolean(12,txn.isInclude_tax());
			newPaymentPs.setDouble(13,txn.getTax1_pc());
			newPaymentPs.setDouble(14,txn.getTax2_pc());
			newPaymentPs.setDouble(15, txn.getTax3_pc());
			newPaymentPs.setDouble(16,txn.getTax4_pc());
			newPaymentPs.setBigDecimal(17,txn.getServiceChargePc());//second line ends

			newPaymentPs.setDouble(18,txn.getAmount());
			newPaymentPs.setDouble(19,txn.getAmount());
			newPaymentPs.setDouble(20,txn.getTax1_amount());
			newPaymentPs.setDouble(21,txn.getTax2_amount());
			newPaymentPs.setDouble(22,txn.getTax3_amount());
			newPaymentPs.setDouble(23,txn.getTax4_amount());
			newPaymentPs.setBigDecimal(24,txn.getServiceCharge());
			newPaymentPs.setDouble(25,txn.getNett_amount());
			newPaymentPs.setInt(26,txn.getPayment_mode());
			newPaymentPs.setInt(27,txn.getCorporate_id());
			newPaymentPs.setString(28,txn.getCorporate_name());
			newPaymentPs.setString(29,txn.getPayment_option());
			newPaymentPs.setInt(30,txn.getTxn_status());
			newPaymentPs.setString(31,txn.getRemarks());
			newPaymentPs.setInt(32,txn.getUser_id());
			newPaymentPs.setString(33,txn.getReceived_from());
			newPaymentPs.setInt(34,txn.getShift_id());
			if(newPaymentPs.executeUpdate()==1){
				TxnDaoImpl txnDao = new TxnDaoImpl();
				txnDao.updateTxnLog(connection, txn.getTxn_no(), "INSERT");
				status="success";
			}
			connection.commit();
		}catch(Exception e){
			e.printStackTrace();	
			logger.error("Method : save " + Throwables.getStackTraceAsString(e));
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			dbCconnection.releaseResource(connection);
			dbCconnection.releaseResource(rs);
			dbCconnection.releaseResource(newPaymentPs);
		}
		return status;
	}
	public String setFinalSettleMent(int folioNum) throws Exception{
		String status="error";
		Connection connection=dbCconnection.getConnection();
		PreparedStatement updateHdrPs=null;
		Statement statement=null;
		ResultSet rs = null;
		String updateHdr = "update checkin_hdr set is_fully_settled=? where checkin_no=?";
		String checkinsql = "select checkin_no from folio where folio_no="+	folioNum;
		int checkiNo=0;
		try{
			statement = connection.createStatement();
			rs=statement.executeQuery(checkinsql);
			while(rs.next()){
				checkiNo = rs.getInt(1);
			}
			updateHdrPs = connection.prepareStatement(updateHdr);
			updateHdrPs.setBoolean(1,true);
			updateHdrPs.setInt(2,checkiNo);
			if(updateHdrPs.executeUpdate()==1){
				status="success";
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbCconnection.releaseResource(connection);
			dbCconnection.releaseResource(updateHdrPs);
		}
		return status;
	}
	public JsonArray bankCardTypes()throws Exception{
		Connection connection=dbCconnection.getConnection();
		PreparedStatement bankCardTypes=null;
		ResultSet rs=null;
		JsonArray jArray=new JsonArray();
		JsonObject jObj=null;
		String query="SELECT  bank_card_types.card_id,bank_card_types.card_type FROM bank_card_types";
		try{
			bankCardTypes=connection.prepareStatement(query);
			rs=bankCardTypes.executeQuery();
			while(rs.next()){
				jObj=new JsonObject();
				jObj.addProperty("id", rs.getInt("card_id"));
				jObj.addProperty("code", rs.getString("card_type"));
				jArray.add(jObj);
			}
			
		
		}catch(Exception e){
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbCconnection.releaseResource(connection);
			dbCconnection.releaseResource(rs);
			dbCconnection.releaseResource(bankCardTypes);
		}
		return jArray;
		
	}
	public JsonArray getCompanyDetails()throws Exception{
		Connection connection=dbCconnection.getConnection();
		PreparedStatement companyName=null;
		ResultSet rs=null;
		JsonArray jArray=new JsonArray();
		JsonObject jObj=null;
		String query="SELECT txn.payment_mode,txn.payment_option FROM txn where payment_mode=5";
		try{
			companyName=connection.prepareStatement(query);
			rs=companyName.executeQuery();
			while(rs.next()){
				jObj=new JsonObject();
				jObj.addProperty("paymentmode", rs.getString("payment_mode"));
				jObj.addProperty("paymentoption", rs.getString("payment_option"));
				jArray.add(jObj);
			}
			
		
		}catch(Exception e){
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbCconnection.releaseResource(connection);
			dbCconnection.releaseResource(rs);
			dbCconnection.releaseResource(companyName);
		}
		return jArray;
		
	}
	public JsonArray getBankDetails()throws Exception{
		Connection connection=dbCconnection.getConnection();
		PreparedStatement bankName=null;
		ResultSet rs=null;
		JsonArray jArray=new JsonArray();
		JsonObject jObj=null;
		String query="SELECT txn.payment_mode,txn.payment_option FROM txn where payment_mode=3";
		try{
			bankName=connection.prepareStatement(query);
			rs=bankName.executeQuery();
			while(rs.next()){
				jObj=new JsonObject();
				jObj.addProperty("paymentmode", rs.getString("payment_mode"));
				jObj.addProperty("paymentoption", rs.getString("payment_option"));
				jArray.add(jObj);
			}
			
		
		}catch(Exception e){
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbCconnection.releaseResource(connection);
			dbCconnection.releaseResource(rs);
			dbCconnection.releaseResource(bankName);
		}
		return jArray;
		
		
	}



	@Override
	public String getCorporateName(int corporateId) {
		String corporateName = "";
		Connection connection=dbCconnection.getConnection();
		PreparedStatement smt=null;
		ResultSet rs=null;
//		JsonArray jArray=new JsonArray();
		//JsonObject jObj=null;
		String query="SELECT name FROM corporate WHERE id = ?";
		try{
			smt=connection.prepareStatement(query);
			smt.setInt(1, corporateId);
			rs=smt.executeQuery();
			if(rs.next()){
				corporateName = rs.getString("name");
			}
			
		
		}catch(Exception e){
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbCconnection.releaseResource(connection);
			dbCconnection.releaseResource(rs);
			dbCconnection.releaseResource(smt);
		}
		return corporateName;
	}



	@Override
	public JsonArray getDiscountDetails(int checkinNo,int folioNo) throws Exception {
		Connection connection=dbCconnection.getConnection();
		PreparedStatement bankCardTypes=null;
		ResultSet rs=null;
		JsonArray jArray=new JsonArray();
		JsonObject jObj=null;
		
		
		/*String Query = "SELECT T1.id,case when T1.discount_for = 1 then \"ROOM\" ELSE \"FOOD\" END AS sysdef_acc_type_id ,T1.discount_for,T1.disc_is_pc AS is_pc,T1.disc_pc AS disc_pc, IFNULL(T2.total_amount,0) AS total_amount,\r\n" + 
				"		case when T1.disc_is_pc = 1 then ROUND((IFNULL(T2.total_amount,0) * T1.disc_pc/100),2)  ELSE T1.disc_amount END AS disc_amount \r\n" + 
				"		FROM(\r\n" + 
				" 		select dis.id,dis.disc_type,dis.disc_id,dis.disc_code,dis.disc_is_pc,dis.disc_amount,dis.disc_pc,dis.is_open,\r\n" + 
				"      dis.discount_for,dis.txn_id\r\n" + 
				"      FROM checkin_discounts dis WHERE dis.checkin_no = ?)T1\r\n" + 
				"      LEFT JOIN \r\n" + 
				"      (\r\n" + 
				"       SELECT case when mst.sysdef_acc_type_id = 1 then 1 ELSE 2 END AS discount_for,txn.acc_mst_id,txn.acc_mst_code,ABS(sum(IFNULL(txn.nett_amount,0))) AS total_amount\r\n" + 
				" 		 FROM txn \r\n" + 
				"		 INNER JOIN checkin_hdr as hdr  ON txn.folio_bind_no = hdr.folio_bind_no \r\n" + 
				" 		 INNER JOIN acc_mst AS mst ON txn.acc_mst_id = mst.id\r\n" + 
				"		 WHERE hdr.checkin_no = ? AND txn.folio_no = ? AND mst.sysdef_acc_type_id IN(1,3)\r\n" + 
				"		 GROUP BY mst.sysdef_acc_type_id	)T2\r\n" + 
				"		 ON T1.discount_for = T2.discount_for";*/
		
			String Query = "SELECT txn_no,txn_date,acc_mst_code,disc_id,disc_code,is_pc,disc_pc,base_amount,discAmount AS disc_amount,new_base_amount,\r\n" + 
					" 			 new_tax1_amount,new_tax2_amount,new_tax3_amount,(new_base_amount+new_tax1_amount+new_tax2_amount+new_tax3_amount) AS new_nett_amount,disc_remarks\r\n" + 
					" 			 FROM(\r\n" + 
					" 	SELECT disc_remarks,txn_no,txn_date,acc_mst_id,acc_mst_code,disc_id,disc_code,is_pc,disc_pc,disc_amount,base_amount,discAmount,ROUND((base_amount-discAmount),2)AS new_base_amount,\r\n" + 
					" 			 ROUND((base_amount-discAmount)*tax1_pc/100,2) AS new_tax1_amount, ROUND((base_amount-discAmount)*tax2_pc/100,2) AS new_tax2_amount, ROUND((base_amount-discAmount)*tax3_pc/100,2) AS new_tax3_amount\r\n" + 
					" 			FROM(	\r\n" + 
					"				 SELECT T2.disc_remarks,T2.txn_no,T2.txn_date,T2.acc_mst_id,T2.acc_mst_code,T1.disc_id,T1.disc_code,T1.disc_is_pc AS is_pc,T1.disc_pc,T1.disc_amount,T2.base_amount,\r\n" + 
					"	   				case when T1.disc_is_pc = 1 then ROUND((IFNULL(T2.base_amount,0) * T1.disc_pc/100),2)  ELSE T1.disc_amount END AS discAmount,\r\n" + 
					"						T2.tax1_pc,T2.tax2_pc,T2.tax3_pc \r\n" + 
					"	   				FROM(\r\n" + 
					" 								SELECT dis.disc_type,dis.disc_id,dis.disc_code,dis.disc_is_pc,dis.disc_amount,dis.disc_pc,dis.is_open,\r\n" + 
					"      								 dis.discount_for\r\n" + 
					"      								FROM checkin_discounts dis WHERE dis.checkin_no = ?)T1\r\n" + 
					"      								INNER JOIN\r\n" + 
					" 										(SELECT  IFNULL(txn.is_disc_applied,0) AS  is_disc_applied,txn.disc_remarks,txn.txn_no AS txn_no,txn.txn_date,case when mst.sysdef_acc_type_id = 1 then 1 ELSE 2 END AS discount_for,\r\n" + 
					"		  		  								   txn.acc_mst_id,txn.acc_mst_code,txn.base_amount AS base_amount,txn.tax1_pc,txn.tax2_pc,txn.tax3_pc\r\n" + 
					" 		       									FROM txn \r\n" + 
					"		       									INNER JOIN checkin_hdr as hdr  ON txn.folio_bind_no = hdr.folio_bind_no \r\n" + 
					" 		        									INNER JOIN acc_mst AS mst ON txn.acc_mst_id = mst.id\r\n" + 
					"		       									WHERE hdr.checkin_no = ? AND txn.folio_no = ? AND mst.sysdef_acc_type_id IN(1,3))T2\r\n" + 
					"		       									ON T1.discount_for = T2.discount_for AND T2.is_disc_applied != 1)T3)T4 ORDER BY txn_date,acc_mst_id";
		
		try{
			bankCardTypes=connection.prepareStatement(Query);
			bankCardTypes.setInt(1,checkinNo);
			bankCardTypes.setInt(2,checkinNo);
			bankCardTypes.setInt(3,folioNo);
			rs=bankCardTypes.executeQuery();
			while(rs.next()){
				jObj=new JsonObject();
			/*	jObj.addProperty("pmtType", 6);
				jObj.addProperty("pmtMode","1");
				jObj.addProperty("remarks", rs.getString("sysdef_acc_type_id"));
				jObj.addProperty("acc_mst_code", "DISCOUNT");
				jObj.addProperty("discountFor", rs.getString("sysdef_acc_type_id"));
				jObj.addProperty("pmtType", 6);
				jObj.addProperty("checkindis_id", rs.getString("id"));
				jObj.addProperty("sysdef_acc_type_id", rs.getString("sysdef_acc_type_id"));
				jObj.addProperty("discount_for", rs.getString("discount_for"));
				jObj.addProperty("isPcDiscount", rs.getBoolean("is_pc"));
				jObj.addProperty("discPC", rs.getBigDecimal("disc_pc"));
				jObj.addProperty("TotalAmount", rs.getBigDecimal("total_amount"));
				jObj.addProperty("DiscountAmount", rs.getBigDecimal("disc_amount"));*/
				
				jObj.addProperty("txn_no", rs.getInt("txn_no"));
				jObj.addProperty("txn_date", rs.getString("txn_date"));
				jObj.addProperty("acc_mst_code", rs.getString("acc_mst_code"));
				jObj.addProperty("disc_id", rs.getInt("disc_id"));
				jObj.addProperty("isDiscApplied", 1);
				jObj.addProperty("disc_code", rs.getString("disc_code"));
				jObj.addProperty("isPcDiscount", rs.getBoolean("is_pc"));
				jObj.addProperty("discPC", rs.getBigDecimal("disc_pc"));
				jObj.addProperty("baseAmount", rs.getBigDecimal("base_amount"));
				jObj.addProperty("discAmount", rs.getBigDecimal("disc_amount"));
				jObj.addProperty("newBaseAmount", rs.getBigDecimal("new_base_amount"));
				jObj.addProperty("newTax1Amount", rs.getBigDecimal("new_tax1_amount"));
				jObj.addProperty("newTax2Amount", rs.getBigDecimal("new_tax2_amount"));
				jObj.addProperty("newTax3Amount", rs.getBigDecimal("new_tax3_amount"));
				jObj.addProperty("newNettAmount", rs.getBigDecimal("new_nett_amount"));
				jObj.addProperty("discRemarks", rs.getString("disc_remarks"));
				
				
				jArray.add(jObj);
			}
			
		
		}catch(Exception e){
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbCconnection.releaseResource(connection);
			dbCconnection.releaseResource(rs);
			dbCconnection.releaseResource(bankCardTypes);
		}
		return jArray;
		
	}



	@Override
	public int getDiscount(int folio_no) throws Exception {
		// TODO Auto-generated method stub
		
		Connection connection=null;
		PreparedStatement bankCardTypes=null;
		ResultSet rs=null;
		JsonArray jArray=new JsonArray();
		JsonObject jObj=null;
		int discountCnt = 0;
		String query="select COUNT(*) as cnt from txn where is_disc_Applied =1  AND folio_no = ? ";
		try {
			 connection=dbCconnection.getConnection();
			 bankCardTypes=connection.prepareStatement(query);
				bankCardTypes.setInt(1,folio_no);
				rs=bankCardTypes.executeQuery();
				while(rs.next()){
					discountCnt = rs.getInt("cnt");
				}
		
	}catch(Exception e){
		e.printStackTrace();
		throw new CustomException();
	}
	finally {
		dbCconnection.releaseResource(connection);
		dbCconnection.releaseResource(rs);
		dbCconnection.releaseResource(bankCardTypes);
	}
		return discountCnt;
	}



	@Override
	public String upDateDiscounts(JsonArray discountDetails) throws Exception {
		// TODO Auto-generated method stub
		String status = "";
		TxnDaoImpl txnDaoImpl = new TxnDaoImpl();
		
		Connection connection=new DbConnection().getConnection();
		PreparedStatement updatePostingPs=null;
		String sqlUpdate="update txn set is_disc_applied=?,disc_id=?,disc_amount=?,new_base_amount=?,new_tax1_amount=?,new_tax2_amount=?,new_tax3_amount=?,new_amount=?,new_nett_amount=?,disc_remarks=? "
				+ " where txn_no=?";
		try {
			connection.setAutoCommit(false);
		for (int i = 0; i < discountDetails.size(); i++) {
			JsonObject json = (JsonObject) discountDetails.get(i);
			updatePostingPs=connection.prepareStatement(sqlUpdate);
			updatePostingPs.setInt(1,json.get("isDiscApplied").getAsInt());
			updatePostingPs.setInt(2, json.get("disc_id").getAsInt());
			updatePostingPs.setBigDecimal(3,json.get("discAmount").getAsBigDecimal());
			updatePostingPs.setBigDecimal(4,json.get("newBaseAmount").getAsBigDecimal());
			updatePostingPs.setBigDecimal(5,json.get("newTax1Amount").getAsBigDecimal());
			updatePostingPs.setBigDecimal(6,json.get("newTax2Amount").getAsBigDecimal());
			updatePostingPs.setBigDecimal(7,json.get("newTax3Amount").getAsBigDecimal());
			updatePostingPs.setBigDecimal(8,json.get("newNettAmount").getAsBigDecimal());
			updatePostingPs.setDouble(9,-1*(json.get("newNettAmount").getAsDouble()));
			updatePostingPs.setString(10,json.get("discRemarks").getAsString());
			updatePostingPs.setInt(11,json.get("txn_no").getAsInt());
			//second line ends

			if(updatePostingPs.executeUpdate()==1){
				txnDaoImpl.updateTxnLog(connection, json.get("txn_no").getAsInt(), "UPDATE");
				status="success";
			}
			else {
				status = "error";
				
			
			}
		}
		connection.commit();
		}
		catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("Method: upDateDiscounts, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		finally {
			dbCconnection.releaseResource(updatePostingPs);
			dbCconnection.releaseResource(connection);
			
			
		}
		return status;
	}

}
