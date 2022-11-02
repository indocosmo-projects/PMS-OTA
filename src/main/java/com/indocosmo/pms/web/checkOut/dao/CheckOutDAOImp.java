package com.indocosmo.pms.web.checkOut.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.apache.taglibs.standard.tei.ForEachTEI;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.transform.impl.AddPropertyTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.enumerator.ReservationStatus;
import com.indocosmo.pms.enumerator.RoomHkStatus;
import com.indocosmo.pms.enumerator.RoomOccupancyStatus;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.checkIn.model.CheckInHdr;
import com.indocosmo.pms.web.checkOut.controller.CheckOutController;
import com.indocosmo.pms.web.checkOut.model.CheckOutDetails;
import com.indocosmo.pms.web.checkOut.model.Invoice;
import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.transaction.model.Transaction;

@Repository
public class CheckOutDAOImp implements CheckOutDAO{

	public static final Logger logger=LoggerFactory.getLogger(CheckOutDAOImp.class);

	private DbConnection dbConnection = null;


	private static final String ROOM_NUMBER="BH";
	private static final String ROOM_TYPE_CODE="BAN";

	public CheckOutDAOImp() {
		dbConnection = new DbConnection();
	}

	public List<CheckOutDetails> getCheckInDetailsByFolioBindNO(int folioBindNO) throws Exception {
		Encryption encryption=new Encryption();
		ResultSet resultSet=null;
		CheckOutDetails checkOutDetails=null;
		List<CheckOutDetails> CheckOutDetailsList=new ArrayList<CheckOutDetails>();
		String queryString="select (select COUNT(*) as cnt from txn where is_disc_Applied =1 and txn.folio_no = folio.folio_no AND txn.folio_bind_no = checkin_hdr.folio_bind_no ) AS disAplied,(select COUNT(id) from checkin_discounts where checkin_no = checkin_hdr.checkin_no ) AS numDis,checkin_hdr.checkin_no,checkin_hdr.folio_bind_no,checkin_hdr.resv_no,folio.folio_no,status,if((status = 7),0,1) AS can_checkout,room_number,first_name,last_name,corporate_name,folio_balance,exp_depart_date from checkin_hdr inner join folio on (checkin_hdr.checkin_no=folio.checkin_no) inner join checkin_dtl on(checkin_hdr.checkin_no=checkin_dtl.checkin_no) left join v_list_folio_balance on(folio.folio_no=v_list_folio_balance.folio_no) where checkin_dtl.is_sharer=0 and checkin_hdr.folio_bind_no=?";
		Connection con=null;
		PreparedStatement prs=null;

		try {
			con = dbConnection.getConnection();
			prs=con.prepareStatement(queryString);
			prs.setInt(1, folioBindNO);
			resultSet=prs.executeQuery();

			while(resultSet.next()){
				checkOutDetails=new CheckOutDetails();
				checkOutDetails.setCheckinNo(resultSet.getInt("checkin_no"));
				checkOutDetails.setEncryCheckinNo(encryption.encrypt(Integer.toString(resultSet.getInt("checkin_no"))));
				checkOutDetails.setFolioBindNo(resultSet.getInt("folio_bind_no"));
				checkOutDetails.setEncryFolioBindNo(encryption.encrypt(Integer.toString(resultSet.getInt("folio_bind_no"))));
				checkOutDetails.setFolioNo(resultSet.getInt("folio_no"));
				checkOutDetails.setEncryFolioNo(encryption.encrypt(Integer.toString(resultSet.getInt("folio_no"))));
				checkOutDetails.setResvNo(resultSet.getInt("resv_no"));
				checkOutDetails.setEncryResvNo(encryption.encrypt(Integer.toString(resultSet.getInt("resv_no"))));
				checkOutDetails.setStatus(resultSet.getByte("status"));
				checkOutDetails.setRoomNumber(resultSet.getString("room_number"));
				checkOutDetails.setEncryRoomNo(encryption.encrypt(resultSet.getString("room_number")));
				checkOutDetails.setFirstName(resultSet.getString("first_name"));
				checkOutDetails.setLastName(resultSet.getString("last_name"));
				checkOutDetails.setCorporateName(resultSet.getString("corporate_name"));
				checkOutDetails.setFolioBalance(resultSet.getBigDecimal("folio_balance"));
				checkOutDetails.setExpDepartDate(resultSet.getDate("exp_depart_date"));
				if(checkRoomChargePosting(resultSet.getInt("folio_no"))){
					checkOutDetails.setRcPostStatus(true);
				}else{
					checkOutDetails.setRcPostStatus(false);
				}
				if(resultSet.getInt("numDis")>0) {
					checkOutDetails.setDiscount(true);
				}
				else {
					checkOutDetails.setDiscount(false);
				}
				if(resultSet.getInt("disAplied")>0) {
					checkOutDetails.setRcDiscntStatus(true);
				}else {
					checkOutDetails.setRcDiscntStatus(false);
				}
				
				CheckOutDetailsList.add(checkOutDetails);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getCheckInDetailsByFolioBindNO " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}finally{
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(prs);
			dbConnection.releaseResource(resultSet);
		}

		return CheckOutDetailsList;
	}

	public String doCheckOut(List<CheckOutDetails> checkOutDetailsList, JsonObject billAddressObj) throws Exception {
		int resutl=0;
		ResultSet resultSet=null;
		ResultSet rs=null;
		ResultSet result=null;
		ResultSet result1=null;
		ResultSet result2=null;
		String resultStatus="error";
		int resvNo=0;
		int countTocheckIn=0;
		int countConfirmed=0;
		int countCheckOut=0;
		int resrvationNum=0;

		String queryCheckInUpdate = "UPDATE checkin_hdr SET status=?,act_depart_date=?,act_depart_time=?,billing_instruction=?,checkout_by=?,checkout_at=? WHERE checkin_no=?";
		String queryCheckInStatus="SELECT checkin_hdr.status as status, v_list_folio_balance.folio_balance as folio_balance,room.floor_id as floor_id,room_type.code AS room_type_code FROM checkin_hdr inner join folio on (checkin_hdr.checkin_no=folio.checkin_no) left join v_list_folio_balance on(folio.folio_no=v_list_folio_balance.folio_no) INNER JOIN room ON checkin_hdr.room_number=room.number INNER JOIN room_type ON room.room_type_id = room_type.id WHERE checkin_hdr.checkin_no=?";
		String queryRoomUpdate="UPDATE room set occ_status=?,hk_status=? where number=?";
		String queryResvRoomUpdate="UPDATE resv_room set room_status=? where room_number=?";
		String queryResvHdrUpdate="UPDATE resv_hdr set status=? where resv_no=?";
		String queryCountCheckInStatus = "SELECT count(status) as count, resv_no FROM resv_hdr WHERE folio_bind_no=? and status=?";
		String queryGetResvNo="SELECT resv_no FROM checkin_hdr WHERE folio_bind_no=? and status=?";
		String queryCountConfirmed="SELECT COUNT(resv_room_no) as countConfirm from ((SELECT * FROM (SELECT * FROM resv_room) AS newResvRoom WHERE resv_dtl_no IN (SELECT resv_dtl_no FROM resv_dtl WHERE resv_no = ? )) as resvDtlRoom )WHERE room_status=?";
		String queryCountCheckOut="SELECT COUNT(checkin_no) as checkOutCount from checkin_hdr where resv_no=? AND status=?";
		String queryInvoicegenInsert = "SELECT getInvoiceNo(?,?)";
		String querybillingAdrsInsert = "INSERT INTO billing_address(checkin_no, folio_bind_no, salutation, first_name, last_name, guest_name, address,"
				+ " email, phone, nationality, state, gstno) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		String querynontransferData = "SELECT COUNT(txn.txn_no) AS non_transfer_data FROM checkin_hdr inner join folio on (checkin_hdr.checkin_no=folio.checkin_no) inner join txn " + 
										" ON txn.folio_no = folio.folio_no WHERE  txn_status =1 AND checkin_hdr.checkin_no=? ";
		Connection con = null;
		PreparedStatement prsCheckInUpdate=null;
		PreparedStatement prsRoomUpdate=null;
		PreparedStatement prsResvRoomUpdate=null;
		PreparedStatement prsResvHdrUpdate=null;
		PreparedStatement prsCheckInStatus=null;
		PreparedStatement prsCountCheckInStatus=null;
		PreparedStatement prsInvoicegenInsert=null;
		PreparedStatement prsCountConfirmed=null;
		PreparedStatement prsCheckOutCount=null;
		PreparedStatement prsqueryGetResvNo=null;
		PreparedStatement prsQueryBillingAdrsInsert = null;
		PreparedStatement prsnonTransferData = null;
		
		

		try{ 
			con = dbConnection.getConnection();
			con.setAutoCommit(false);
			prsCheckInUpdate=con.prepareStatement(queryCheckInUpdate);
			prsRoomUpdate=con.prepareStatement(queryRoomUpdate);
			prsResvRoomUpdate=con.prepareStatement(queryResvRoomUpdate);
			prsResvHdrUpdate=con.prepareStatement(queryResvHdrUpdate);
			prsCheckInStatus=con.prepareStatement(queryCheckInStatus);
			prsCountCheckInStatus=con.prepareStatement(queryCountCheckInStatus);
			prsInvoicegenInsert=con.prepareStatement(queryInvoicegenInsert);
			prsCountConfirmed=con.prepareStatement(queryCountConfirmed);
			prsCheckOutCount=con.prepareStatement(queryCountCheckOut);
			prsqueryGetResvNo=con.prepareStatement(queryGetResvNo);
			prsnonTransferData=con.prepareStatement(querynontransferData);
			
			prsQueryBillingAdrsInsert = con.prepareStatement(querybillingAdrsInsert);
			for (CheckOutDetails checkOutDetails : checkOutDetailsList) {

				/**Query checkin_hdr table*/
				prsCountCheckInStatus.setInt(1,checkOutDetails.getFolioBindNo());
				prsCountCheckInStatus.setByte(2, ReservationStatus.PARTCHECKIN.getCode());
				resultSet=null;
				resultSet=prsCountCheckInStatus.executeQuery();
				resultSet.next();
				resvNo=resultSet.getInt("resv_no");
				countTocheckIn=resultSet.getInt("count");


				/**query insert invoice_gen*/
				prsInvoicegenInsert.setInt(1,checkOutDetails.getCheckinNo());




				/**update check in table*/
				prsCheckInUpdate.setByte(1, checkOutDetails.getStatus());
				prsCheckInUpdate.setDate(2, new java.sql.Date(checkOutDetails.getActDepartDate().getTime()));
				prsCheckInUpdate.setTime(3, new Time(checkOutDetails.getActDepartTime().getTime()));
				prsCheckInUpdate.setString(4, checkOutDetails.getBillingInstruction());
				prsCheckInUpdate.setInt(5, checkOutDetails.getCheckOutBy());
				prsCheckInUpdate.setTimestamp(6,  new java.sql.Timestamp(checkOutDetails.getCheckOutAt().getTime()));
				prsCheckInUpdate.setInt(7,checkOutDetails.getCheckinNo());
				prsCheckInStatus.setInt(1,checkOutDetails.getCheckinNo());
				resultSet=prsCheckInStatus.executeQuery();
		
				if(resultSet.next()) {
					//	prsCheckInStatus.setInt(2,checkOutDetails.getFloorId());
					checkOutDetails.setFloorId(resultSet.getInt("floor_id"));
					checkOutDetails.setRoomTypeCode(resultSet.getString("room_type_code"));
					
					
				}
				if(checkOutDetails.getRoomTypeCode().equals(ROOM_TYPE_CODE)) {
					prsInvoicegenInsert.setString(2,ROOM_NUMBER);
				}else {
					prsInvoicegenInsert.setString(2,"");
				}
				
				
				//exclude invoice generation for transfer rooms
				prsnonTransferData.setInt(1,checkOutDetails.getCheckinNo());
				result2=prsnonTransferData.executeQuery();
				int countNonTranferData = 0;
				if(result2.next()) {
					//	prsCheckInStatus.setInt(2,checkOutDetails.getFloorId());
					countNonTranferData = result2.getInt("non_transfer_data");
					
				}
				if(countNonTranferData > 0) {
					prsInvoicegenInsert.executeQuery();
				}
				
				//resultSet.next();

				if(resultSet.getByte("status")==ReservationStatus.CHECKIN.getCode() && resultSet.getByte("folio_balance")>=0){
					resutl=prsCheckInUpdate.executeUpdate();

					/**update room table*/
					prsRoomUpdate.setInt(1,RoomOccupancyStatus.VACCANT.getCode());
					prsRoomUpdate.setInt(2,RoomHkStatus.DIRTY.getCode());
					prsRoomUpdate.setString(3, checkOutDetails.getRoomNumber());
					resutl=prsRoomUpdate.executeUpdate();


					prsqueryGetResvNo.setInt(1,checkOutDetails.getFolioBindNo());
					prsqueryGetResvNo.setInt(2, ReservationStatus.CHECKOUT.getCode());
					result1=prsqueryGetResvNo.executeQuery();
					result1.next();
					resrvationNum=result1.getInt("resv_no");

					prsCheckOutCount.setInt(1, resrvationNum);
					prsCheckOutCount.setInt(2, ReservationStatus.CHECKIN.getCode());
					result=prsCheckOutCount.executeQuery();
					result.next();
					countCheckOut=result.getInt("checkOutCount");

					if(resrvationNum!=0){
						prsResvRoomUpdate.setByte(1, checkOutDetails.getStatus());
						prsResvRoomUpdate.setString(2, checkOutDetails.getRoomNumber());
						resutl=prsResvRoomUpdate.executeUpdate();
						if(countCheckOut==0){
							prsResvHdrUpdate.setInt(1,ReservationStatus.CHECKOUT.getCode());
							prsResvHdrUpdate.setInt(2,resrvationNum);
							resutl=prsResvHdrUpdate.executeUpdate();
						}
					}

					if(resvNo!=0){
						prsCountConfirmed.setInt(1, resvNo);
						prsCountConfirmed.setInt(2, ReservationStatus.CONFIRMED.getCode());
						rs=prsCountConfirmed.executeQuery();
						rs.next();
						countConfirmed=rs.getInt("countConfirm");

						if(countConfirmed==0){
							prsResvHdrUpdate.setInt(1,ReservationStatus.CHECKOUT.getCode());
							prsResvHdrUpdate.setInt(2,resvNo);
							resutl=prsResvHdrUpdate.executeUpdate();
						}
						else{
							prsResvHdrUpdate.setInt(1,ReservationStatus.PARTCHECKIN.getCode());
							prsResvHdrUpdate.setInt(2,resvNo);
							resutl=prsResvHdrUpdate.executeUpdate();
						}
					}

					resultStatus="success";
				}else{
					resultStatus="notAllow";
					break;
				}


				/**Insert into billing_address table*/ 

				prsQueryBillingAdrsInsert.setInt(1, checkOutDetails.getCheckinNo());
				prsQueryBillingAdrsInsert.setInt(2, checkOutDetails.getFolioBindNo());
				prsQueryBillingAdrsInsert.setString(3, billAddressObj.get("salutation").getAsString());
				prsQueryBillingAdrsInsert.setString(4, billAddressObj.get("firstName").getAsString());
				prsQueryBillingAdrsInsert.setString(5, billAddressObj.get("lastName").getAsString());
				if(!billAddressObj.get("guestName").isJsonNull())
					prsQueryBillingAdrsInsert.setString(6, billAddressObj.get("guestName").getAsString());
				prsQueryBillingAdrsInsert.setString(7, billAddressObj.get("address").getAsString());
				if(!billAddressObj.get("email").isJsonNull())
					prsQueryBillingAdrsInsert.setString(8, billAddressObj.get("email").getAsString());
				if(!billAddressObj.get("phone").isJsonNull())
					prsQueryBillingAdrsInsert.setString(9, billAddressObj.get("phone").getAsString());
				prsQueryBillingAdrsInsert.setString(10, billAddressObj.get("nationality").getAsString());
				prsQueryBillingAdrsInsert.setString(11, billAddressObj.get("state").getAsString());
				if(!billAddressObj.get("gstno").isJsonNull()) {
					prsQueryBillingAdrsInsert.setString(12, billAddressObj.get("gstno").getAsString());
				}else {
					prsQueryBillingAdrsInsert.setString(12, "");
				}
				prsQueryBillingAdrsInsert.executeUpdate();
			}

			con.commit();
		}catch (Exception e) {
			con.rollback();
			resultStatus="error";			
			e.printStackTrace();
			logger.error("Method : doCheckOut " + Throwables.getStackTraceAsString(e));
			if(e.getMessage().equals("Column 'fin_id' cannot be null")){
				throw new CustomException("Financial Year not added");
			}else{

				throw new CustomException();
			}
		}finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(prsCheckInUpdate);
			dbConnection.releaseResource(prsRoomUpdate);
			dbConnection.releaseResource(prsResvRoomUpdate);
			dbConnection.releaseResource(prsResvHdrUpdate);
			dbConnection.releaseResource(prsCheckInStatus);
			dbConnection.releaseResource(prsCountCheckInStatus);
			dbConnection.releaseResource(prsInvoicegenInsert);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(prsnonTransferData);
			dbConnection.releaseResource(result2);
			
		}

		return resultStatus;
	}

	public boolean checkRoomChargePosting(int folioNo){
		Boolean status=false;
		Statement statement = null;
		ResultSet rs = null;
		Connection connection = dbConnection.getConnection();
		String sql = "select txn.txn_no from txn INNER JOIN acc_mst ON txn.acc_mst_id=acc_mst.id where acc_mst.sysdef_acc_type_id=1 and txn.folio_no="+folioNo; 
		try{
			statement=connection.createStatement();
			rs=statement.executeQuery(sql);
			if(rs.next()){
				status=true;
			}
		}catch (Exception e) {

		}finally{
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
		}
		return status;
	}

	public List<Transaction> getTxnDetails(int folioNo){
		ResultSet rs=null;
		Transaction txn=null;
		List<Transaction> txnList=new ArrayList<Transaction>();
		String queryString="SELECT * FROM v_txn_details WHERE folio_no = ? ORDER BY txn_date, txn_time, txn_no";
		//String queryString="Select * from v_txn_details where folio_no =? ORDER BY acc_mst_code";
		//String queryString="select txn.txn_no,txn.txn_date,txn.acc_mst_id,txn.acc_mst_code,txn.service_tax_id,txn.tax_id,txn.tax_code,txn.include_tax,txn.tax1_amount,txn.tax2_amount,txn.tax3_amount,txn.tax4_amount,txn.service_charge,txn.amount,txn.base_amount,txn.nett_amount,tax_hdr.tax_indicator from txn inner join tax_hdr on tax_hdr.id=txn.tax_id where txn.txn_status=1 AND  txn.acc_mst_id NOT IN(11) and txn.folio_no=?";
		Connection con=null;
		PreparedStatement prs=null;
		try {
			con = dbConnection.getConnection();
			prs=con.prepareStatement(queryString);
			prs.setInt(1, folioNo);
			rs=prs.executeQuery();
			while(rs.next()){
				txn=new Transaction();
				txn.setTxn_no(rs.getInt("txn_no"));
				txn.setTxn_date(rs.getString("txn_date"));
				txn.setAcc_mst_id(rs.getInt("acc_mst_id"));
				txn.setAcc_mst_code(rs.getString("acc_mst_code"));
				txn.setServiceTaxId(rs.getInt("service_tax_id"));
				txn.setTax_id(rs.getInt("tax_id"));
				txn.setTax_code(rs.getString("tax_code"));
				txn.setTax_indicator(rs.getString("tax_indicator"));
				txn.setInclude_tax(rs.getBoolean("include_tax"));
				txn.setTax1_amount(rs.getDouble("tax1_amount"));
				txn.setTax2_amount(rs.getDouble("tax2_amount"));
				txn.setTax3_amount(rs.getDouble("tax3_amount"));
				txn.setTax4_amount(rs.getDouble("tax4_amount"));
				txn.setTax1_pc(rs.getDouble("tax1_pc"));
				txn.setTax2_pc(rs.getDouble("tax2_pc"));
				txn.setTax3_pc(rs.getDouble("tax3_pc"));
				txn.setTax4_pc(rs.getDouble("tax4_pc"));
				txn.setServiceCharge(rs.getBigDecimal("service_charge"));
				txn.setAmount(rs.getDouble("amount"));
				txn.setBase_amount(rs.getDouble("base_amount"));
				txn.setNett_amount(rs.getDouble("nett_amount"));
				txn.setSourceRoom(rs.getString("source_room_number"));
				txn.setReceived_from(rs.getString("received_from"));
				txnList.add(txn);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : doCheckOut " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(prs);
			dbConnection.releaseResource(rs);
		}			
		return txnList;
	}

	
	public List<Transaction> getTxnDetailsSeparate(int folioNo,int systemAccType){
		ResultSet rs=null;
		Transaction txn=null;
		List<Transaction> txnList=new ArrayList<Transaction>();
		String queryString="SELECT * FROM v_txn_details WHERE folio_no = ? and sysdef_acc_type_id = ? ORDER BY txn_date, txn_time, txn_no";
	
	
		Connection con=null;
		PreparedStatement prs=null;
		try {
			con = dbConnection.getConnection();
			prs=con.prepareStatement(queryString);
			prs.setInt(1, folioNo);
			prs.setInt(2, systemAccType);
			rs=prs.executeQuery();
			while(rs.next()){
				txn=new Transaction();
				txn.setTxn_no(rs.getInt("txn_no"));
				txn.setTxn_date(rs.getString("txn_date"));
				txn.setAcc_mst_id(rs.getInt("acc_mst_id"));
				txn.setAcc_mst_code(rs.getString("acc_mst_code"));
				txn.setServiceTaxId(rs.getInt("service_tax_id"));
				txn.setTax_id(rs.getInt("tax_id"));
				txn.setTax_code(rs.getString("tax_code"));
				txn.setTax_indicator(rs.getString("tax_indicator"));
				txn.setInclude_tax(rs.getBoolean("include_tax"));
				txn.setTax1_amount(rs.getDouble("tax1_amount"));
				txn.setTax2_amount(rs.getDouble("tax2_amount"));
				txn.setTax3_amount(rs.getDouble("tax3_amount"));
				txn.setTax4_amount(rs.getDouble("tax4_amount"));
				txn.setTax1_pc(rs.getDouble("tax1_pc"));
				txn.setTax2_pc(rs.getDouble("tax2_pc"));
				txn.setTax3_pc(rs.getDouble("tax3_pc"));
				txn.setTax4_pc(rs.getDouble("tax4_pc"));
				txn.setServiceCharge(rs.getBigDecimal("service_charge"));
				txn.setAmount(rs.getDouble("amount"));
				txn.setBase_amount(rs.getDouble("base_amount"));
				txn.setNett_amount(rs.getDouble("nett_amount"));
				txn.setSourceRoom(rs.getString("source_room_number"));
				txn.setReceived_from(rs.getString("received_from"));
				txnList.add(txn);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : doCheckOut " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(prs);
			dbConnection.releaseResource(rs);
		}			
		return txnList;
	}

	public List<Transaction> getTxnDetailsSeparateGrop(int folioNo,int systemAccType){
		ResultSet rs=null;
		Transaction txn=null;
		List<Transaction> txnListInvoice=new ArrayList<Transaction>();
		//String queryString="SELECT COUNT(v_txn_details.acc_mst_id) AS qty,	v_txn_details.acc_mst_id,	v_txn_details.acc_mst_code,	v_txn_details.service_tax_id,	v_txn_details.tax_id,	v_txn_details.tax_code,	v_txn_details.include_tax,	v_txn_details.rate_description,	v_txn_details.hsn_code,SUM(v_txn_details.tax1_amount) AS tax1_amount,	SUM(v_txn_details.tax2_amount) AS tax2_amount,	SUM(v_txn_details.tax3_amount) AS tax3_amount,	SUM(v_txn_details.tax4_amount) AS tax4_amount,	SUM(		v_txn_details.service_charge	) AS service_charge,	SUM(v_txn_details.amount) AS invoiceAmount,	v_txn_details.base_amount AS invoiceBaseAmount,	SUM(v_txn_details.nett_amount) AS invoiceNett_amount,	v_txn_details.tax_indicator,	v_txn_details.dest_folio_no,	v_txn_details.source_txn_no,	v_txn_details.folio_no,	v_txn_details.checkin_no,	v_txn_details.source_folio_no,	v_txn_details.room_number,	v_txn_details.source_room_number,	v_txn_details.resv_no,v_txn_details.acc_mst_name FROM	v_txn_details WHERE	folio_no = ? GROUP BY	source_room_number,	acc_mst_code,	base_amount";
		String queryString="SELECT" + 
				"	v_txn_details.txn_date,v_txn_details.txn_time," + 
				"	v_txn_details.acc_mst_id," + 
				"	v_txn_details.acc_mst_code," + 
				"	v_txn_details.service_tax_id," + 
				"	v_txn_details.tax_id," + 
				"	v_txn_details.tax_code," + 
				"	v_txn_details.include_tax," + 
				"	v_txn_details.rate_description," + 
				"	v_txn_details.hsn_code," + 
				"	v_txn_details.tax1_amount," + 
				"	v_txn_details.tax2_amount," + 
				"	v_txn_details.tax3_amount," + 
				"	v_txn_details.tax4_amount," + 
				"	v_txn_details.tax1_pc," + 
				"	v_txn_details.tax2_pc," + 
				"	v_txn_details.tax3_pc," + 
				"	v_txn_details.tax4_pc,"+
				"	v_txn_details.service_charge," + 
				"	v_txn_details.amount AS invoiceAmount," + 
				"	v_txn_details.base_amount AS invoiceBaseAmount," + 
				"	v_txn_details.nett_amount AS invoiceNett_amount," + 
				"	v_txn_details.tax_indicator," + 
				"	v_txn_details.dest_folio_no," + 
				"	v_txn_details.source_txn_no," + 
				"	v_txn_details.folio_no," + 
				"	v_txn_details.checkin_no," + 
				"	v_txn_details.source_folio_no," + 
				"	v_txn_details.room_number," + 
				"	v_txn_details.source_room_number," + 
				"	v_txn_details.resv_no," + 
				"	(" + 
				"		CASE" + 
				"		WHEN v_txn_details.txn_source = 3 THEN" + 
				"			v_txn_details.received_from" + 
				"		ELSE" + 
				"			v_txn_details.acc_mst_name" + 
				"		END" + 
				"	) AS acc_mst_name_prev ," + 
				"	v_txn_details.acc_mst_name AS acc_mst_name" + 
				" FROM" + 
				"	v_txn_details" + 
				" WHERE" + 
				"	folio_no = ? and sysdef_acc_type_id = ? " + 
				" ORDER BY" + 
				"	v_txn_details.txn_date,	" + 
				"	v_txn_details.acc_mst_id";

		Connection con=null;
		PreparedStatement prs=null;
		try {
			con = dbConnection.getConnection();
			prs=con.prepareStatement(queryString);
			prs.setInt(1, folioNo);
			prs.setInt(2, systemAccType);
			rs=prs.executeQuery();
			while(rs.next()){
				txn=new Transaction();
				//txn.setQty(rs.getInt("qty"));
				txn.setTxn_date(rs.getString("txn_date"));
				txn.setTxn_time(rs.getString("txn_time"));
				txn.setAcc_mst_id(rs.getInt("acc_mst_id"));
				txn.setAcc_mst_code(rs.getString("acc_mst_code"));
				txn.setSourceRoom(rs.getString("source_room_number"));
				txn.setInvoiceAmount(rs.getDouble("invoiceAmount"));
				txn.setInvoiceBaseAmount(rs.getDouble("invoiceBaseAmount"));
				txn.setInvoiceNett_amount(rs.getDouble("invoiceNett_amount"));
				txn.setRate_description(rs.getString("rate_description"));
				txn.setHsnCode(rs.getInt("hsn_code"));
				txn.setAccMstName(rs.getString("acc_mst_name"));
				txn.setTax1_amount(rs.getDouble("tax1_amount"));
				txn.setTax2_amount(rs.getDouble("tax2_amount"));
				txn.setTax3_amount(rs.getDouble("tax3_amount"));
				txn.setTax4_amount(rs.getDouble("tax4_amount"));
				txn.setTax1_pc(rs.getDouble("tax1_pc"));
				txn.setTax2_pc(rs.getDouble("tax2_pc"));
				txn.setTax3_pc(rs.getDouble("tax3_pc"));
				txn.setTax4_pc(rs.getDouble("tax4_pc"));
				txnListInvoice.add(txn);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : doCheckOut " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(prs);
			dbConnection.releaseResource(rs);
		}			
		return txnListInvoice;
	}
	
	
	public List<Transaction> getInvoiceGroupDetails(int folioNo){
		ResultSet rs=null;
		Transaction txn=null;
		List<Transaction> txnListInvoice=new ArrayList<Transaction>();
		//String queryString="SELECT COUNT(v_txn_details.acc_mst_id) AS qty,	v_txn_details.acc_mst_id,	v_txn_details.acc_mst_code,	v_txn_details.service_tax_id,	v_txn_details.tax_id,	v_txn_details.tax_code,	v_txn_details.include_tax,	v_txn_details.rate_description,	v_txn_details.hsn_code,SUM(v_txn_details.tax1_amount) AS tax1_amount,	SUM(v_txn_details.tax2_amount) AS tax2_amount,	SUM(v_txn_details.tax3_amount) AS tax3_amount,	SUM(v_txn_details.tax4_amount) AS tax4_amount,	SUM(		v_txn_details.service_charge	) AS service_charge,	SUM(v_txn_details.amount) AS invoiceAmount,	v_txn_details.base_amount AS invoiceBaseAmount,	SUM(v_txn_details.nett_amount) AS invoiceNett_amount,	v_txn_details.tax_indicator,	v_txn_details.dest_folio_no,	v_txn_details.source_txn_no,	v_txn_details.folio_no,	v_txn_details.checkin_no,	v_txn_details.source_folio_no,	v_txn_details.room_number,	v_txn_details.source_room_number,	v_txn_details.resv_no,v_txn_details.acc_mst_name FROM	v_txn_details WHERE	folio_no = ? GROUP BY	source_room_number,	acc_mst_code,	base_amount";
		String queryString="SELECT" + 
				"	v_txn_details.txn_date," + 
				"	v_txn_details.acc_mst_id," + 
				"	v_txn_details.acc_mst_code," + 
				"	v_txn_details.service_tax_id," + 
				"	v_txn_details.tax_id," + 
				"	v_txn_details.tax_code," + 
				"	v_txn_details.include_tax," + 
				"	v_txn_details.rate_description," + 
				"	v_txn_details.hsn_code," + 
				"	v_txn_details.tax1_amount," + 
				"	v_txn_details.tax2_amount," + 
				"	v_txn_details.tax3_amount," + 
				"	v_txn_details.tax4_amount," + 
				"	v_txn_details.tax1_pc," + 
				"	v_txn_details.tax2_pc," + 
				"	v_txn_details.tax3_pc," + 
				"	v_txn_details.tax4_pc,"+
				"	v_txn_details.service_charge," + 
				"	v_txn_details.amount AS invoiceAmount," + 
				"	v_txn_details.base_amount AS invoiceBaseAmount," + 
				"	v_txn_details.nett_amount AS invoiceNett_amount," + 
				"	v_txn_details.tax_indicator," + 
				"	v_txn_details.dest_folio_no," + 
				"	v_txn_details.source_txn_no," + 
				"	v_txn_details.folio_no," + 
				"	v_txn_details.checkin_no," + 
				"	v_txn_details.source_folio_no," + 
				"	v_txn_details.room_number," + 
				"	v_txn_details.source_room_number," + 
				"	v_txn_details.resv_no," + 
				"	(" + 
				"		CASE" + 
				"		WHEN v_txn_details.txn_source = 3 THEN" + 
				"			v_txn_details.received_from" + 
				"		ELSE" + 
				"			v_txn_details.acc_mst_name" + 
				"		END" + 
				"	) AS acc_mst_name" + 
				" FROM" + 
				"	v_txn_details" + 
				" WHERE" + 
				"	folio_no = ?" + 
				" ORDER BY" + 
				"	v_txn_details.txn_date,	" + 
				"	v_txn_details.acc_mst_id";

		Connection con=null;
		PreparedStatement prs=null;
		try {
			con = dbConnection.getConnection();
			prs=con.prepareStatement(queryString);
			prs.setInt(1, folioNo);
			//prs.setInt(2, folioNo);
			rs=prs.executeQuery();
			while(rs.next()){
				txn=new Transaction();
				//txn.setQty(rs.getInt("qty"));
				txn.setTxn_date(rs.getString("txn_date"));
				txn.setAcc_mst_id(rs.getInt("acc_mst_id"));
				txn.setAcc_mst_code(rs.getString("acc_mst_code"));
				txn.setSourceRoom(rs.getString("source_room_number"));
				txn.setInvoiceAmount(rs.getDouble("invoiceAmount"));
				txn.setInvoiceBaseAmount(rs.getDouble("invoiceBaseAmount"));
				txn.setInvoiceNett_amount(rs.getDouble("invoiceNett_amount"));
				txn.setRate_description(rs.getString("rate_description"));
				txn.setHsnCode(rs.getInt("hsn_code"));
				txn.setAccMstName(rs.getString("acc_mst_name"));
				txn.setTax1_amount(rs.getDouble("tax1_amount"));
				txn.setTax2_amount(rs.getDouble("tax2_amount"));
				txn.setTax3_amount(rs.getDouble("tax3_amount"));
				txn.setTax4_amount(rs.getDouble("tax4_amount"));
				txn.setTax1_pc(rs.getDouble("tax1_pc"));
				txn.setTax2_pc(rs.getDouble("tax2_pc"));
				txn.setTax3_pc(rs.getDouble("tax3_pc"));
				txn.setTax4_pc(rs.getDouble("tax4_pc"));
				txnListInvoice.add(txn);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : doCheckOut " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(prs);
			dbConnection.releaseResource(rs);
		}			
		return txnListInvoice;
	}
	
	
	public List<Transaction> getInvoiceGroupDetailsCombined(int folioNo){
		ResultSet rs=null;
		Transaction txn=null;
		List<Transaction> txnListInvoice=new ArrayList<Transaction>();
		//String queryString="SELECT COUNT(v_txn_details.acc_mst_id) AS qty,	v_txn_details.acc_mst_id,	v_txn_details.acc_mst_code,	v_txn_details.service_tax_id,	v_txn_details.tax_id,	v_txn_details.tax_code,	v_txn_details.include_tax,	v_txn_details.rate_description,	v_txn_details.hsn_code,SUM(v_txn_details.tax1_amount) AS tax1_amount,	SUM(v_txn_details.tax2_amount) AS tax2_amount,	SUM(v_txn_details.tax3_amount) AS tax3_amount,	SUM(v_txn_details.tax4_amount) AS tax4_amount,	SUM(		v_txn_details.service_charge	) AS service_charge,	SUM(v_txn_details.amount) AS invoiceAmount,	v_txn_details.base_amount AS invoiceBaseAmount,	SUM(v_txn_details.nett_amount) AS invoiceNett_amount,	v_txn_details.tax_indicator,	v_txn_details.dest_folio_no,	v_txn_details.source_txn_no,	v_txn_details.folio_no,	v_txn_details.checkin_no,	v_txn_details.source_folio_no,	v_txn_details.room_number,	v_txn_details.source_room_number,	v_txn_details.resv_no,v_txn_details.acc_mst_name FROM	v_txn_details WHERE	folio_no = ? GROUP BY	source_room_number,	acc_mst_code,	base_amount";
		
		/*
		
		String queryString=" SELECT	v_txn_details.txn_date,v_txn_details.txn_time, 	v_txn_details.acc_mst_id,\r\n" + 
				"      	v_txn_details.acc_mst_code,	v_txn_details.service_tax_id,	v_txn_details.tax_id,\r\n" + 
				"			v_txn_details.tax_code,	v_txn_details.include_tax,	v_txn_details.rate_description,\r\n" + 
				"			v_txn_details.hsn_code,	SUM(v_txn_details.tax1_amount) AS tax1_amount,	SUM(v_txn_details.tax2_amount) AS tax2_amount,\r\n" + 
				"			SUM(v_txn_details.tax3_amount) AS tax3_amount,	SUM(v_txn_details.tax4_amount) AS tax4_amount,v_txn_details.tax1_pc,\r\n" + 
				"			v_txn_details.tax2_pc,	v_txn_details.tax3_pc,	v_txn_details.tax4_pc,\r\n" + 
				"			SUM(v_txn_details.service_charge) AS service_charge,SUM(v_txn_details.amount) AS invoiceAmount,\r\n" + 
				"			SUM(v_txn_details.base_amount) AS invoiceBaseAmount,	SUM(v_txn_details.nett_amount) AS invoiceNett_amount,\r\n" + 
				"			v_txn_details.tax_indicator,	v_txn_details.dest_folio_no,	v_txn_details.source_txn_no,\r\n" + 
				"			v_txn_details.folio_no,	v_txn_details.checkin_no,	v_txn_details.source_folio_no,\r\n" + 
				"			v_txn_details.room_number,	v_txn_details.source_room_number,	v_txn_details.resv_no,\r\n" + 
				"			(CASE	WHEN v_txn_details.txn_source = 3 THEN	v_txn_details.received_from ELSE	v_txn_details.acc_mst_name	END) AS acc_mst_name_prev ,\r\n" + 
				"			v_txn_details.acc_mst_name as  acc_mst_name  \r\n" + 
				"			FROM\r\n" + 
				" 		   v_txn_details WHERE	folio_no = ? \r\n" + 
				"	      GROUP BY txn_date,acc_mst_id,hsn_code\r\n" + 
				"	      ORDER BY	v_txn_details.txn_date,		v_txn_details.acc_mst_id";*/
		
		String queryString = " SELECT * FROM (SELECT	v_txn_details.discount_code,v_txn_details.txn_date,v_txn_details.txn_time, 	v_txn_details.acc_mst_id,	v_txn_details.acc_mst_code,	v_txn_details.service_tax_id,	v_txn_details.tax_id,	v_txn_details.tax_code,v_txn_details.is_disc_applied,v_txn_details.disc_amount,v_txn_details.actual_base_amount,v_txn_details.include_tax,	v_txn_details.rate_description,	v_txn_details.hsn_code,	v_txn_details.tax1_amount,	v_txn_details.tax2_amount,	v_txn_details.tax3_amount,	v_txn_details.tax4_amount,	v_txn_details.tax1_pc,	v_txn_details.tax2_pc,	v_txn_details.tax3_pc,	v_txn_details.tax4_pc,	v_txn_details.service_charge,	v_txn_details.amount AS invoiceAmount,	v_txn_details.base_amount AS invoiceBaseAmount,	v_txn_details.nett_amount AS invoiceNett_amount,	v_txn_details.tax_indicator,	v_txn_details.dest_folio_no,	v_txn_details.source_txn_no,	v_txn_details.folio_no,	v_txn_details.checkin_no,	v_txn_details.source_folio_no,	v_txn_details.room_number,	v_txn_details.source_room_number,	v_txn_details.resv_no,	(		CASE		WHEN v_txn_details.txn_source = 3 THEN			v_txn_details.received_from		ELSE			v_txn_details.acc_mst_name		END	) AS acc_mst_name_prev ,v_txn_details.acc_mst_name as  acc_mst_name  \r\n" + 
				"FROM	v_txn_details WHERE	folio_no = ? AND  acc_mst_id <> '12'\r\n" + 
				"\r\n" + 
				"UNION ALL \r\n" + 
				"\r\n" + 
				"SELECT	v_txn_details.discount_code,v_txn_details.txn_date,v_txn_details.txn_time, 	v_txn_details.acc_mst_id,\r\n" + 
				"      	v_txn_details.acc_mst_code,	v_txn_details.service_tax_id,	v_txn_details.tax_id,\r\n" + 
				"			v_txn_details.tax_code,v_txn_details.is_disc_applied,v_txn_details.disc_amount,v_txn_details.actual_base_amount,v_txn_details.include_tax,v_txn_details.rate_description,\r\n" + 
				"			v_txn_details.hsn_code,	SUM(v_txn_details.tax1_amount) AS tax1_amount,	SUM(v_txn_details.tax2_amount) AS tax2_amount,\r\n" + 
				"			SUM(v_txn_details.tax3_amount) AS tax3_amount,	SUM(v_txn_details.tax4_amount) AS tax4_amount,v_txn_details.tax1_pc,\r\n" + 
				"			v_txn_details.tax2_pc,	v_txn_details.tax3_pc,	v_txn_details.tax4_pc,\r\n" + 
				"			SUM(v_txn_details.service_charge) AS service_charge,SUM(v_txn_details.amount) AS invoiceAmount,\r\n" + 
				"			SUM(v_txn_details.base_amount) AS invoiceBaseAmount,	SUM(v_txn_details.nett_amount) AS invoiceNett_amount,\r\n" + 
				"			v_txn_details.tax_indicator,	v_txn_details.dest_folio_no,	v_txn_details.source_txn_no,\r\n" + 
				"			v_txn_details.folio_no,	v_txn_details.checkin_no,	ifnull(v_txn_details.source_folio_no,0) as source_folio_no,\r\n" + 
				"			v_txn_details.room_number,	v_txn_details.source_room_number,	v_txn_details.resv_no,\r\n" + 
				"			(CASE	WHEN v_txn_details.txn_source = 3 THEN	v_txn_details.received_from ELSE	v_txn_details.acc_mst_name	END) AS acc_mst_name_prev ,\r\n" + 
				"			v_txn_details.acc_mst_name as  acc_mst_name  \r\n" + 
				"			FROM\r\n" + 
				" 		   v_txn_details WHERE	folio_no = ? and acc_mst_id = '12' \r\n" + 
				"	      GROUP BY txn_date,acc_mst_id,acc_mst_name,hsn_code\r\n" + 
				"\r\n" + 
				"\r\n" + 
				") AS tab ORDER BY	tab.txn_date,	tab.acc_mst_id";
		Connection con=null;
		PreparedStatement prs=null;
		try {
			con = dbConnection.getConnection();
			prs=con.prepareStatement(queryString);
			prs.setInt(1, folioNo);
			prs.setInt(2, folioNo);
			rs=prs.executeQuery();
			while(rs.next()){
				txn=new Transaction();
				//txn.setQty(rs.getInt("qty"));
				txn.setTxn_date(rs.getString("txn_date"));
				txn.setTxn_time(rs.getString("txn_time"));
				txn.setAcc_mst_id(rs.getInt("acc_mst_id"));
				txn.setAcc_mst_code(rs.getString("acc_mst_code"));
				txn.setSource_folio_no(rs.getInt("source_folio_no"));
				txn.setSourceRoom(rs.getString("source_room_number"));
				txn.setInvoiceAmount(rs.getDouble("invoiceAmount"));
				txn.setInvoiceBaseAmount(rs.getDouble("invoiceBaseAmount"));
				txn.setInvoiceNett_amount(rs.getDouble("invoiceNett_amount"));
				txn.setRate_description(rs.getString("rate_description"));
				txn.setHsnCode(rs.getInt("hsn_code"));
				txn.setAccMstName(rs.getString("acc_mst_name"));
				txn.setTax1_amount(rs.getDouble("tax1_amount"));
				txn.setTax2_amount(rs.getDouble("tax2_amount"));
				txn.setTax3_amount(rs.getDouble("tax3_amount"));
				txn.setTax4_amount(rs.getDouble("tax4_amount"));
				txn.setTax1_pc(rs.getDouble("tax1_pc"));
				txn.setTax2_pc(rs.getDouble("tax2_pc"));
				txn.setTax3_pc(rs.getDouble("tax3_pc"));
				txn.setTax4_pc(rs.getDouble("tax4_pc"));
				txn.setActualBaseAmount(rs.getDouble("actual_base_amount"));
				txn.setDisc_amount(rs.getDouble("disc_amount"));
				txn.setDiscApplied(rs.getBoolean("is_disc_applied"));
				txn.setDiscount(rs.getString("discount_code"));
				txn.setDisc_amount(rs.getDouble("disc_amount"));
				txnListInvoice.add(txn);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : doCheckOut " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(prs);
			dbConnection.releaseResource(rs);
		}			
		return txnListInvoice;
	}


	public List<Transaction> getInvoiceDetails(int folioNo){
		ResultSet rs=null;
		Transaction txn=null;
		List<Transaction> txnListInvoice=new ArrayList<Transaction>();
		//String queryString="SELECT COUNT(v_txn_details.acc_mst_id) AS qty,	v_txn_details.acc_mst_id,	v_txn_details.acc_mst_code,	v_txn_details.service_tax_id,	v_txn_details.tax_id,	v_txn_details.tax_code,	v_txn_details.include_tax,	v_txn_details.rate_description,	v_txn_details.hsn_code,SUM(v_txn_details.tax1_amount) AS tax1_amount,	SUM(v_txn_details.tax2_amount) AS tax2_amount,	SUM(v_txn_details.tax3_amount) AS tax3_amount,	SUM(v_txn_details.tax4_amount) AS tax4_amount,	SUM(		v_txn_details.service_charge	) AS service_charge,	SUM(v_txn_details.amount) AS invoiceAmount,	v_txn_details.base_amount AS invoiceBaseAmount,	SUM(v_txn_details.nett_amount) AS invoiceNett_amount,	v_txn_details.tax_indicator,	v_txn_details.dest_folio_no,	v_txn_details.source_txn_no,	v_txn_details.folio_no,	v_txn_details.checkin_no,	v_txn_details.source_folio_no,	v_txn_details.room_number,	v_txn_details.source_room_number,	v_txn_details.resv_no,v_txn_details.acc_mst_name FROM	v_txn_details WHERE	folio_no = ? GROUP BY	source_room_number,	acc_mst_code,	base_amount";
		String queryString="SELECT COUNT(v_txn_details.acc_mst_id) AS qty,"
				+ " v_txn_details.acc_mst_id,"
				+ " v_txn_details.acc_mst_code,"
				+ " v_txn_details.service_tax_id,"
				+ " v_txn_details.tax_id,"
				+ " v_txn_details.tax_code,"
				+ " v_txn_details.include_tax,v_txn_details.is_disc_applied,"
				+ " v_txn_details.disc_amount,v_txn_details.actual_base_amount,"
				+ " v_txn_details.rate_description,"
				+ " v_txn_details.hsn_code,"
				+ " SUM(v_txn_details.tax1_amount) AS tax1_amount,"
				+ " SUM(v_txn_details.tax2_amount) AS tax2_amount,"
				+ " SUM(v_txn_details.tax3_amount) AS tax3_amount,"
				+ " SUM(v_txn_details.tax4_amount) AS tax4_amount,"
				+ " SUM("
				+ "	v_txn_details.service_charge"
				+ " ) AS service_charge,"
				+ " SUM(v_txn_details.amount) AS invoiceAmount,"
				+ " v_txn_details.base_amount AS invoiceBaseAmount,"
				+ " SUM(v_txn_details.nett_amount) AS invoiceNett_amount,"
				+ " v_txn_details.tax_indicator,"
				+ " v_txn_details.dest_folio_no,"
				+ " v_txn_details.source_txn_no,"
				+ " v_txn_details.folio_no,"
				+ " v_txn_details.checkin_no,"
				+ " v_txn_details.source_folio_no, "
				+ " v_txn_details.room_number,"
				+ " v_txn_details.source_room_number,"
				+ " v_txn_details.resv_no,"
				+ "( CASE WHEN v_txn_details.txn_source = 3"
				+ " THEN v_txn_details.received_from"
				+ " ELSE v_txn_details.acc_mst_name END ) AS acc_mst_name_prev, "
				+ " v_txn_details.acc_mst_name AS acc_mst_name"
				+ " FROM"
				+ "		v_txn_details"
				+ "	WHERE"
				+ "	folio_no = ?"
				+ " GROUP BY"
				+ "	source_room_number,"
				+ "	acc_mst_code,"
				+ "	base_amount"
				+ " ORDER BY	v_txn_details.acc_mst_id";
		/*+ " UNION"
	+ " SELECT"
	+ " 1 AS qty,"
	+ "		v_txn_details.acc_mst_id,"
	+ " v_txn_details.acc_mst_code,"
	+ " v_txn_details.service_tax_id,"
	+ " v_txn_details.tax_id,"
	+ " v_txn_details.tax_code,"
	+ " v_txn_details.include_tax,"
	+ " v_txn_details.rate_description,"
	+ " v_txn_details.hsn_code,"
	+ " v_txn_details.tax1_amount AS tax1_amount,"
	+ " v_txn_details.tax2_amount AS tax2_amount,"
	+ " v_txn_details.tax3_amount AS tax3_amount,"
	+ " v_txn_details.tax4_amount AS tax4_amount,"		
	+ " v_txn_details.service_charge"
	+ " AS service_charge,"
	+ " v_txn_details.amount AS invoiceAmount,"
	+ " v_txn_details.base_amount AS invoiceBaseAmount,"
	+ " v_txn_details.nett_amount AS invoiceNett_amount,"
	+ " v_txn_details.tax_indicator,"
	+ " v_txn_details.dest_folio_no,"
	+ " v_txn_details.source_txn_no,"
	+ " v_txn_details.folio_no,"
	+ " v_txn_details.checkin_no,"
	+ " v_txn_details.source_folio_no,"
	+ " v_txn_details.room_number,"
	+ " v_txn_details.source_room_number,"
	+ " v_txn_details.resv_no,"
	+ " v_txn_details.acc_mst_name"
	+ " FROM"
	+ " v_txn_details"
	+ " WHERE"
	+ " folio_no = ? AND txn_source = 3";*/

		Connection con=null;
		PreparedStatement prs=null;
		try {
			con = dbConnection.getConnection();
			prs=con.prepareStatement(queryString);
			prs.setInt(1, folioNo);
			//prs.setInt(2, folioNo);
			rs=prs.executeQuery();
			while(rs.next()){
				txn=new Transaction();
				txn.setQty(rs.getInt("qty"));
				txn.setAcc_mst_id(rs.getInt("acc_mst_id"));
				txn.setAcc_mst_code(rs.getString("acc_mst_code"));
				txn.setSourceRoom(rs.getString("source_room_number"));
				txn.setInvoiceAmount(rs.getDouble("invoiceAmount"));
				txn.setInvoiceBaseAmount(rs.getDouble("invoiceBaseAmount"));
				txn.setInvoiceNett_amount(rs.getDouble("invoiceNett_amount"));
				txn.setRate_description(rs.getString("rate_description"));
				txn.setHsnCode(rs.getInt("hsn_code"));
				txn.setAccMstName(rs.getString("acc_mst_name"));
				txnListInvoice.add(txn);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : doCheckOut " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(prs);
			dbConnection.releaseResource(rs);
		}			
		return txnListInvoice;
	}

	public JsonObject getCustomerDetails(int folioNo){
		Connection connection = dbConnection.getConnection();
		Statement statement=null;
		ResultSet rs=null;
		JsonObject jsonObject=null;
		String sqlPaymentList = "SELECT " + 
				"(CASE WHEN b.salutation IS NULL THEN cd.salutation ELSE b.salutation  END) AS salutation, " + 
				"(CASE WHEN b.first_name IS NULL THEN cd.first_name ELSE b.first_name END) AS first_name, " + 
				"(CASE WHEN b.last_name IS NULL THEN cd.last_name ELSE b.last_name END) AS last_name, " + 
				//"(CASE WHEN b.guest_name IS NULL THEN cd.guest_name ELSE b.guest_name  END) AS guest_name, " + 
//changed by anju				
				"cd.guest_name AS guest_name, " +
				"(CASE WHEN b.phone IS NULL THEN cd.phone ELSE b.phone  END) AS phone, " + 
				"(CASE WHEN b.address IS NULL THEN cd.address ELSE b.address  END) AS address, " + 
				"(CASE WHEN b.state IS NULL THEN cd.state ELSE b.state  END) AS state, " + 
				"(CASE WHEN b.nationality IS NULL THEN cd.nationality ELSE b.nationality  END) AS nationality,  " + 
				"(CASE WHEN b.gstno IS NULL THEN cd.gstno ELSE b.gstno  END) AS gstno, " + 
				"(CASE WHEN b.email IS NULL THEN cd.email ELSE b.email  END) AS email, " + 
				"	ch.room_number, " + 
				"	ch.arr_date, " + 
				"	ch.act_depart_date, " + 
				"	ch.invoice_no, " + 
				"	ch.checkin_no, " + 
				"	ch.num_nights, " + 
				"	ch.arr_time, " + 
				"	ch.act_depart_time, " + 
				"	ch.rate_description " + 
				"FROM " + 
				"	checkin_dtl cd " + 
				"INNER JOIN checkin_hdr ch ON ch.checkin_no = cd.checkin_no " + 
				"INNER JOIN folio f ON f.checkin_no = ch.checkin_no " + 
				"LEFT JOIN billing_address b ON b.checkin_no = ch.checkin_no " + 
				"WHERE " + 
				"	cd.is_sharer = 0 " + 
				"AND f.folio_no="+folioNo;
		try {	
			statement=connection.createStatement();
			rs=statement.executeQuery(sqlPaymentList);
			while (rs.next() ) {
				jsonObject = new JsonObject();
				jsonObject.addProperty("name", rs.getString("salutation")+" "+rs.getString("first_name")+" "+rs.getString("last_name"));
				jsonObject.addProperty("guest_name", rs.getString("guest_name"));
				jsonObject.addProperty("email", rs.getString("email"));
				jsonObject.addProperty("phone", rs.getString("phone"));
				jsonObject.addProperty("roomNumber", rs.getString("room_number"));
				jsonObject.addProperty("address", rs.getString("address"));
				jsonObject.addProperty("state", rs.getString("state"));
				jsonObject.addProperty("nationality", rs.getString("nationality"));
				jsonObject.addProperty("arrDate",rs.getString("arr_date"));
				jsonObject.addProperty("depDate",rs.getString("act_depart_date"));
				jsonObject.addProperty("invoiceNo",rs.getString("invoice_no"));
				jsonObject.addProperty("checkinnum", rs.getInt("checkin_no"));
				jsonObject.addProperty("numnights", rs.getInt("num_nights"));
				jsonObject.addProperty("gstno", rs.getString("gstno"));
				jsonObject.addProperty("arrTime", rs.getString("arr_time"));
				jsonObject.addProperty("act_depart_time", rs.getString("act_depart_time"));
				jsonObject.addProperty("rateDescription", rs.getString("rate_description"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
		}
		return jsonObject;
	}

	public JsonArray getCheckInCheckOutData(String wherePart) throws Exception{
		JsonArray  jsonArray =new JsonArray();
		Statement statement = null;
		Connection connection =dbConnection.getConnection();
		ResultSet rs=null;
		JsonObject jsonObject=null;
		String sql="select ch.checkin_no,ch.folio_bind_no,ch.room_type_code,ch.room_number, cd.first_name,cd.last_name,cd.phone,cd.email,cd.address,ch.rate_code,ch.arr_date,ch.act_depart_date,ch.exp_depart_date from  checkin_dtl cd inner join checkin_hdr ch on ch.checkin_no=cd.checkin_no  where "+wherePart;
		try {	
			statement=connection.createStatement();
			rs=statement.executeQuery(sql);
			while (rs.next() ) {
				jsonObject = new JsonObject();
				jsonObject.addProperty("checkin_no", rs.getInt("checkin_no"));
				jsonObject.addProperty("folio_bind_no", rs.getInt("folio_bind_no"));
				jsonObject.addProperty("name", rs.getString("first_name")+" "+rs.getString("last_name"));
				jsonObject.addProperty("phone", rs.getString("phone"));
				jsonObject.addProperty("email", rs.getString("email"));
				jsonObject.addProperty("address", rs.getString("address"));	
				jsonObject.addProperty("rate_code", rs.getString("rate_code"));			
				jsonObject.addProperty("room_type_code", rs.getString("room_type_code"));
				jsonObject.addProperty("room_number", rs.getString("room_number"));
				jsonObject.addProperty("arr_date",rs.getString("arr_date"));
				jsonObject.addProperty("act_depart_date",rs.getString("act_depart_date"));
				jsonObject.addProperty("exp_depart_date",rs.getString("exp_depart_date"));
				jsonArray.add(jsonObject);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
		}

		return jsonArray;
	}

	@Override
	public List<Transaction> getPaymentList(int folioNo) {

		Transaction txn = new Transaction();
		//8 =PAID IN,14=COMPLIMENTARY
		String sql = "SELECT payment_mode, amount FROM txn WHERE folio_no = ?  AND acc_mst_id IN (8,14) GROUP BY payment_mode";
		Connection con=null;
		PreparedStatement prs=null;
		ResultSet rs = null;
		List<Transaction> txnListInvoice = new ArrayList<>();
		try {
			con = dbConnection.getConnection();
			prs=con.prepareStatement(sql);
			prs.setInt(1, folioNo);
			rs=prs.executeQuery();
			while(rs.next()){
				txn=new Transaction();
				txn.setPayment_mode(rs.getInt("payment_mode"));
				txn.setAmount(rs.getDouble("amount"));
				txnListInvoice.add(txn);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : doCheckOut " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(prs);
			dbConnection.releaseResource(rs);
		}			
		return txnListInvoice;
	}

	@Override
	public JsonObject loadAddressWithCheckinNo(int checkinNo) {
		JsonObject jObj=new JsonObject();
		ResultSet rs=null;
		ResultSet rs1=null;
		PreparedStatement pS=null;
		PreparedStatement pS1=null;
		Connection connection=dbConnection.getConnection();
		String query="select salutation, first_name, last_name, guest_name, address, email, phone, nationality, state, gstno "
				+ " from checkin_dtl where is_sharer=0 and checkin_no = '"+checkinNo+ "'";
		try{
			pS=connection.prepareStatement(query);
			rs=pS.executeQuery();
			while(rs.next()){
				jObj.addProperty("salutation", rs.getString("salutation"));
				jObj.addProperty("first_name", rs.getString("first_name"));
				jObj.addProperty("last_name", rs.getString("last_name"));
				jObj.addProperty("guest_name", rs.getString("guest_name"));
				jObj.addProperty("email", rs.getString("email"));
				jObj.addProperty("address", rs.getString("address"));
				jObj.addProperty("phone", rs.getString("phone"));
				jObj.addProperty("nationality", rs.getString("nationality"));
				jObj.addProperty("state", rs.getString("state"));
				jObj.addProperty("gstno", rs.getString("gstno"));
			}

		}catch(Exception e){
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(pS);
			dbConnection.releaseResource(rs);
		}	
		return jObj;
	}



	public String getReportName(int sysAccType){
		ResultSet rs=null;
	
		String queryString="SELECT name FROM sysdef_acc_type WHERE id = ? ";
		String rptName = "";
		Connection con=null;
		PreparedStatement prs=null;
		try {
			con = dbConnection.getConnection();
			prs=con.prepareStatement(queryString);
			prs.setInt(1, sysAccType);
			rs=prs.executeQuery();
			while(rs.next()){
			rptName = rs.getString("name");
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : doCheckOut " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(prs);
			dbConnection.releaseResource(rs);
		}			
		return rptName;
	}





	public List<Transaction> getTxnSummary(int folioNo){
		ResultSet rs=null;
		Transaction txn=null;
		List<Transaction> txnListSummary = new ArrayList<Transaction>();
		String queryString="SELECT * FROM v_invoice_tax WHERE folio_no = ? ";

		Connection con=null;
		PreparedStatement prs=null;
		try {
			con = dbConnection.getConnection();
			prs=con.prepareStatement(queryString);
			prs.setInt(1, folioNo);
			rs=prs.executeQuery();
			while(rs.next()){
				txn=new Transaction();
				txn.setTax1_amount(rs.getDouble("tax1_amount"));
				txn.setTax2_amount(rs.getDouble("tax2_amount"));
				txn.setTax3_amount(rs.getDouble("tax3_amount"));
				txn.setTax1_pc(rs.getDouble("tax1_pc"));
				txn.setTax2_pc(rs.getDouble("tax2_pc"));
				txn.setTax3_pc(rs.getDouble("tax3_pc"));
				txn.setTax_total(rs.getDouble("tax_total"));
				txn.setBase_amount(rs.getDouble("base_amount"));
				txn.setHsnCode(rs.getInt("hsn_code"));
				txnListSummary.add(txn);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : doCheckOut " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(prs);
			dbConnection.releaseResource(rs);
		}			
		return txnListSummary;
	}
	public List<Transaction> getTxnSummarySeparate(int folioNo,int sysAccType){
		ResultSet rs=null;
		Transaction txn=null;
		List<Transaction> txnListSummary = new ArrayList<Transaction>();
		String queryString="SELECT * FROM v_invoice_tax WHERE folio_no = ?  and sysdef_acc_type_id = ?";

		Connection con=null;
		PreparedStatement prs=null;
		try {
			con = dbConnection.getConnection();
			prs=con.prepareStatement(queryString);
			prs.setInt(1, folioNo);
			prs.setInt(2, sysAccType);
			rs=prs.executeQuery();
			while(rs.next()){
				txn=new Transaction();
				txn.setTax1_amount(rs.getDouble("tax1_amount"));
				txn.setTax2_amount(rs.getDouble("tax2_amount"));
				txn.setTax3_amount(rs.getDouble("tax3_amount"));
				txn.setTax1_pc(rs.getDouble("tax1_pc"));
				txn.setTax2_pc(rs.getDouble("tax2_pc"));
				txn.setTax3_pc(rs.getDouble("tax3_pc"));
				txn.setTax_total(rs.getDouble("tax_total"));
				txn.setBase_amount(rs.getDouble("base_amount"));
				txn.setHsnCode(rs.getInt("hsn_code"));
				txnListSummary.add(txn);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : doCheckOut " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(prs);
			dbConnection.releaseResource(rs);
		}			
		return txnListSummary;
	}











}