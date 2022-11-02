package com.indocosmo.pms.web.checkIn.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.enumerator.ReservationStatus;
import com.indocosmo.pms.enumerator.RoomHkStatus;
import com.indocosmo.pms.enumerator.RoomInventoryStatus;
import com.indocosmo.pms.enumerator.RoomOccupancyStatus;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.checkIn.model.CheckInDtl;
import com.indocosmo.pms.web.checkIn.model.CheckInHdr;
import com.indocosmo.pms.web.checkIn.model.CheckInRate;
import com.indocosmo.pms.web.checkIn.model.CheckinType;
import com.indocosmo.pms.web.checkIn.model.PaymentType;
import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.common.setttings.Rounding;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.exception.CustomException;

@Repository
public class CheckInDAOImp implements CheckInDAO {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public static final Logger logger = LoggerFactory.getLogger(CheckInDAOImp.class);
	DbConnection dbConnection = null;
	boolean result = true;

	public CheckInDAOImp() {
		dbConnection = new DbConnection();
	}

	public int getTotalCheckInRooms(int checkInId) throws Exception {
		Connection connection = dbConnection.getConnection();
		ResultSet resultSet = null;
		int total = 0;
		PreparedStatement checkInPs = null;
		String selectQuery = "select count(checkin_no) as totalCheckIn from checkin_hdr where resv_no=?";

		try {
			checkInPs = connection.prepareStatement(selectQuery);
			checkInPs.setInt(1, checkInId);
			resultSet = checkInPs.executeQuery();
			if (resultSet.next()) {
				total = resultSet.getInt("totalCheckIn");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getTotalCheckInRooms " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(checkInPs);
		}

		return total;
	}

	@Override
	public void changeRoomStatus(int roomId) {
		try {

			final String query = "UPDATE room SET hk_status=3 WHERE id=?";
			jdbcTemplate.update(query, roomId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}

	}
	
	public void updateGroupCheckin(int roomId, String roomnumber) throws Exception {
		
		Connection connection = null;
		String sql = "";
		boolean isSave = true;
		PreparedStatement resvRoomPs = null;

		sql = "UPDATE resv_room SET room_number=? WHERE resv_room_no=? ";

		try {
			connection = dbConnection.getConnection();
			connection.setAutoCommit(false);
			resvRoomPs = connection.prepareStatement(sql);
			resvRoomPs.setString(1,roomnumber);
			resvRoomPs.setInt(2,roomId);
			
		    resvRoomPs.executeUpdate();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			isSave = false;
			connection.rollback();
			logger.error("Method : updateResvRoom()" + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(connection);
		}
	}
	public String save(CheckInHdr checkInHdr, CheckInDtl checkInDtl, List<CheckInRate> CheckInRateArray)
			throws Exception {
		ResultSet resultSet = null;
		int totalReservedRooms = 0;
		int totalCkeckedInRooms = 0;
		String resultStauts = "error";
		Byte resvStatus = ReservationStatus.CHECKIN.getCode();
		String coutnterCheckinNo = "checkin_no";
		String coutntercheckinDtlNo = "checkin_dtl_no";
		String coutnterFolioNo = "folio_no";
		String sqlCountRoomsPerReservation = "select COUNT(resv_room.resv_room_no) as sum_num_rooms, resv_hdr.status from resv_hdr INNER JOIN resv_dtl on resv_dtl.resv_no=resv_hdr.resv_no inner join resv_room on resv_room.resv_dtl_no=resv_dtl.resv_dtl_no where resv_room.is_noshow=0 and resv_hdr.resv_no=?";
		String sqlCheckRoomStatus = "SELECT occ_status, hk_status FROM room WHERE inv_status !=? and number=?";
		String sqlRoomStatusUpdate = "update room set occ_status=? where number=?";
		String sqlInsertCheckinHdr = "insert into checkin_hdr(checkin_no,resv_no,folio_bind_no,status,type,corporate_id,corporate_name,corporate_address,room_type_id,room_type_code,room_number,arr_date,arr_time,exp_depart_date,exp_depart_time,rate_type,rate_id,rate_code,rate_description,occupancy,disc_id,disc_code,disc_is_pc,disc_amount,disc_pc,billing_instruction,checkin_by,num_adults,num_children,num_infants,special_requests,num_nights,is_fully_settled)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sqlInsertCheckinDtl = "insert into checkin_dtl(checkin_dtl_no,checkin_no,guest_id,is_sharer,first_name,last_name,guest_name,gender,address,email,phone,nationality,state,passport_no,passport_expiry_on,gstno,remarks,customer_image,customer_id_proof,salutation) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sqlUpdateResvRoom = "update resv_room set room_status=? where resv_room_no=? ";
		String sqlInsertCheckinRate = "insert into checkin_rate(checkin_dtl_no,night_date, night, room_charge, include_tax, tax1_pc, tax2_pc, tax3_pc, tax4_pc,service_charge_pc, tax1, tax2, tax3, tax4,service_charge,rate_id,disc_id,disc_amount,disc_pc,room_type,occupancy) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sqlUpdateFolio = "update folio set checkin_no=? where folio_no=?";
		String sqlInsertFolio = "insert into folio(folio_no, folio_bind_no ,checkin_no) values(?,?,?)";
		String sqlUpdateResvHdr = "update resv_hdr set status=? where resv_no=?";
		String sqlUpdateCheckInRequest = "update checkin_request set checkin_no=? where resv_room_no=?";
		String sqlCountCkeckedInRooms = "select count(folio_bind_no) as checkedInRooms from checkin_hdr where folio_bind_no=?";

		String checkinDisUpdate = "UPDATE checkin_discounts t1,( SELECT DISTINCT id "
				+ " FROM checkin_discounts WHERE ISNULL(checkin_no ) AND resv_no= ? GROUP BY disc_id ) t2 SET t1.checkin_no = ? " + 
				" WHERE t1.id = t2.id";
		
		
		
		String countFunction = "{? = call counter(?)}";

		Connection connection = dbConnection.getConnection();
		
		PreparedStatement pscheckinDisUpdate = null;

		PreparedStatement prsCountRoomsPerReservation = null;
		PreparedStatement prsCheckRoomStatus = null;
		PreparedStatement prsRoomStatusUpdate = null;
		PreparedStatement prsInsertCheckinHdr = null;
		PreparedStatement prsInsertCheckinDtl = null;
		PreparedStatement prsUpdateResvRoom = null;
		PreparedStatement prsInsertCheckinRate = null;
		PreparedStatement prsUpdateFolio = null;
		PreparedStatement prsInsertFolio = null;
		PreparedStatement prsUpdateResvHdr = null;
		PreparedStatement prsCountCkeckedInRooms = null;
		PreparedStatement prsUpdateCheckInRequest = null;

		CallableStatement countFunct = null;

		int occStatus = RoomOccupancyStatus.OCCUPIED.getCode();
		int hkStatus = RoomHkStatus.DIRTY.getCode();

		try {
			connection = dbConnection.getConnection();
			connection.setAutoCommit(false);
			prsCountRoomsPerReservation = connection.prepareStatement(sqlCountRoomsPerReservation);
			System.out.println(prsCountRoomsPerReservation);
			//checkin_discounts update
			pscheckinDisUpdate = connection.prepareStatement(checkinDisUpdate);
			
			prsCheckRoomStatus = connection.prepareStatement(sqlCheckRoomStatus);
			prsRoomStatusUpdate = connection.prepareStatement(sqlRoomStatusUpdate);
			prsInsertCheckinHdr = connection.prepareStatement(sqlInsertCheckinHdr);
			prsInsertCheckinDtl = connection.prepareStatement(sqlInsertCheckinDtl);
			prsUpdateResvRoom = connection.prepareStatement(sqlUpdateResvRoom);
			prsInsertCheckinRate = connection.prepareStatement(sqlInsertCheckinRate);
			prsUpdateFolio = connection.prepareStatement(sqlUpdateFolio);
			prsInsertFolio = connection.prepareStatement(sqlInsertFolio);
			prsUpdateResvHdr = connection.prepareStatement(sqlUpdateResvHdr);
			prsCountCkeckedInRooms = connection.prepareStatement(sqlCountCkeckedInRooms);
			prsUpdateCheckInRequest = connection.prepareStatement(sqlUpdateCheckInRequest);

			countFunct = connection.prepareCall(countFunction);

			/** start- check status of room */
			prsCheckRoomStatus.setInt(1, RoomInventoryStatus.OUTOFINVENTORY.getCode());
			prsCheckRoomStatus.setString(2, checkInHdr.getRoomNumber());
			resultSet = prsCheckRoomStatus.executeQuery();
			/** end- check status of room */

			while (resultSet.next()) {
				occStatus = resultSet.getInt("occ_status");
				hkStatus = resultSet.getInt("hk_status");
			}

			if (occStatus == RoomOccupancyStatus.VACCANT.getCode() && hkStatus != RoomHkStatus.DIRTY.getCode()) {
				// Update occ_status of room in room table
				prsRoomStatusUpdate.setInt(1, RoomOccupancyStatus.OCCUPIED.getCode());
				prsRoomStatusUpdate.setString(2, checkInHdr.getRoomNumber());
				prsRoomStatusUpdate.executeUpdate();
				// End Update occ_status of room in room table
				// start counter for check in no
				countFunct.registerOutParameter(1, java.sql.Types.INTEGER);
				countFunct.setString(2, coutnterCheckinNo);
				resultSet = null;
				resultSet = countFunct.executeQuery();

				while (resultSet.next()) {
					checkInHdr.setCheckInNo(resultSet.getInt(1));
				}

				// end counter for check in no
				// start insert check in hdr table
				prsInsertCheckinHdr.setInt(1, checkInHdr.getCheckInNo());
				prsInsertCheckinHdr.setInt(2, checkInHdr.getResvNo());
				prsInsertCheckinHdr.setInt(3, checkInHdr.getFolioBindNo());
				prsInsertCheckinHdr.setByte(4, checkInHdr.getStatus());
				prsInsertCheckinHdr.setByte(5, checkInHdr.getResvType());
				prsInsertCheckinHdr.setInt(6, checkInHdr.getCorporateId());
				prsInsertCheckinHdr.setString(7, checkInHdr.getCorporateName());
				prsInsertCheckinHdr.setString(8, checkInHdr.getCorporateAddress());
				prsInsertCheckinHdr.setInt(9, checkInHdr.getRoomTypeId());
				prsInsertCheckinHdr.setString(10, checkInHdr.getRoomTypeCode());
				prsInsertCheckinHdr.setString(11, checkInHdr.getRoomNumber());
				prsInsertCheckinHdr.setDate(12, new java.sql.Date(checkInHdr.getArrDate().getTime()));
				// prsInsertCheckinHdr.setTime(13,new Time(checkInHdr.getArrTime().getTime()));
				java.sql.Timestamp sqlTime = new java.sql.Timestamp(new java.util.Date().getTime());
				prsInsertCheckinHdr.setTimestamp(13, sqlTime);
				prsInsertCheckinHdr.setDate(14, new java.sql.Date(checkInHdr.getExpDepartDate().getTime()));

				java.sql.Time sqlTime1 = new java.sql.Time(new java.util.Date().getTime());

				prsInsertCheckinHdr.setTime(15, sqlTime1);
				prsInsertCheckinHdr.setByte(16, checkInHdr.getRateType());
				prsInsertCheckinHdr.setInt(17, checkInHdr.getRateId());
				prsInsertCheckinHdr.setString(18, checkInHdr.getRateCode());
				prsInsertCheckinHdr.setString(19, checkInHdr.getRateDescription());
				prsInsertCheckinHdr.setByte(20, checkInHdr.getOccupancy());
				prsInsertCheckinHdr.setInt(21, checkInHdr.getDiscId());
				prsInsertCheckinHdr.setString(22, checkInHdr.getDiscCode());
				prsInsertCheckinHdr.setBoolean(23, checkInHdr.getDiscIsPc());
				prsInsertCheckinHdr.setBigDecimal(24, checkInHdr.getDiscAmount());
				prsInsertCheckinHdr.setBigDecimal(25, checkInHdr.getDiscPc());
				prsInsertCheckinHdr.setString(26, checkInHdr.getBillingInstruction());
				prsInsertCheckinHdr.setInt(27, checkInHdr.getCheckInBy());
				prsInsertCheckinHdr.setInt(28, checkInHdr.getNumAdults());
				prsInsertCheckinHdr.setInt(29, checkInHdr.getNumChildren());
				prsInsertCheckinHdr.setInt(30, checkInHdr.getNumInfants());
				prsInsertCheckinHdr.setString(31, checkInHdr.getSpecialRequests());
				prsInsertCheckinHdr.setInt(32, checkInHdr.getNumNights());
				prsInsertCheckinHdr.setBoolean(33, checkInHdr.isFullySettled());

				prsInsertCheckinHdr.executeUpdate();
				// end insert checkin hdr table

				prsUpdateCheckInRequest.setInt(1, checkInHdr.getCheckInNo());
				prsUpdateCheckInRequest.setInt(2, checkInDtl.getResvRoomNo());
				prsUpdateCheckInRequest.executeUpdate();

				// start counter for check in_dtl no
				countFunct.setString(2, coutntercheckinDtlNo);
				resultSet = null;
				resultSet = countFunct.executeQuery();

				while (resultSet.next()) {
					checkInDtl.setCheckinDtlNo(resultSet.getInt(1));
				}

				// end counter for check in_dtl no
				// start insert checkin dtl table
				prsInsertCheckinDtl.setInt(1, checkInDtl.getCheckinDtlNo());
				prsInsertCheckinDtl.setInt(2, checkInHdr.getCheckInNo());
				prsInsertCheckinDtl.setInt(3, checkInHdr.getGuestId());
				prsInsertCheckinDtl.setBoolean(4, checkInDtl.getIsSharer());// is_sharer
				prsInsertCheckinDtl.setString(5, checkInDtl.getFirstName());
				prsInsertCheckinDtl.setString(6, checkInDtl.getLastName());
				prsInsertCheckinDtl.setString(7, checkInDtl.getGuestName());
				prsInsertCheckinDtl.setString(8, checkInDtl.getGender());
				prsInsertCheckinDtl.setString(9, checkInDtl.getAddress());
				prsInsertCheckinDtl.setString(10, checkInDtl.getEmail());
				prsInsertCheckinDtl.setString(11, checkInDtl.getPhone());
				prsInsertCheckinDtl.setString(12, checkInDtl.getNationality());
				prsInsertCheckinDtl.setString(13, checkInDtl.getState());
				prsInsertCheckinDtl.setString(14, checkInDtl.getPassportNo());
				Date PassportExpiry = null;

				if (checkInDtl.getPassportExpiryOn() != null) {
					PassportExpiry = new java.sql.Date(checkInDtl.getPassportExpiryOn().getTime());
				}

				prsInsertCheckinDtl.setDate(15, PassportExpiry);
				prsInsertCheckinDtl.setString(16, checkInDtl.getGstno());
				prsInsertCheckinDtl.setString(17, checkInDtl.getRemarks());
				prsInsertCheckinDtl.setString(18, checkInDtl.getCustomerImage());
				prsInsertCheckinDtl.setString(19, checkInDtl.getCustomerIdProof());
				prsInsertCheckinDtl.setString(20, checkInDtl.getSalutation());

				prsInsertCheckinDtl.executeUpdate();
				// end insert checkin dtl table

				// Start the update related record in resv_room table
				prsUpdateResvRoom.setByte(1, ReservationStatus.CHECKIN.getCode());
				prsUpdateResvRoom.setInt(2, checkInDtl.getResvRoomNo());
				prsUpdateResvRoom.executeUpdate();
				// End the update related record in resv_room table

				for (CheckInRate checkInRate : CheckInRateArray) {
					java.sql.Date tDate = new java.sql.Date(
							checkInHdr.getArrDate().getTime() + ((checkInRate.getNight() - 1)) * 86400000);
					prsInsertCheckinRate.setInt(1, checkInDtl.getCheckinDtlNo());
					prsInsertCheckinRate.setDate(2, tDate);
					prsInsertCheckinRate.setByte(3, checkInRate.getNight());
					prsInsertCheckinRate.setBigDecimal(4, checkInRate.getRoomCharge());
					prsInsertCheckinRate.setBoolean(5, checkInRate.getIncludeTax());
					prsInsertCheckinRate.setBigDecimal(6, checkInRate.getTax1Pc());
					prsInsertCheckinRate.setBigDecimal(7, checkInRate.getTax2Pc());
					prsInsertCheckinRate.setBigDecimal(8, checkInRate.getTax3Pc());
					prsInsertCheckinRate.setBigDecimal(9, checkInRate.getTax4Pc());
					prsInsertCheckinRate.setBigDecimal(10, checkInRate.getServiceChargePc());
					prsInsertCheckinRate.setBigDecimal(11, checkInRate.getTax1());
					prsInsertCheckinRate.setBigDecimal(12, checkInRate.getTax2());
					prsInsertCheckinRate.setBigDecimal(13, checkInRate.getTax3());
					prsInsertCheckinRate.setBigDecimal(14, checkInRate.getTax4());
					prsInsertCheckinRate.setBigDecimal(15, checkInRate.getServiceCharge());
					prsInsertCheckinRate.setInt(16, checkInHdr.getRateId());
					prsInsertCheckinRate.setInt(17, checkInHdr.getDiscId());
					prsInsertCheckinRate.setBigDecimal(18, checkInHdr.getDiscAmount());
					prsInsertCheckinRate.setBigDecimal(19, checkInHdr.getDiscPc());
					prsInsertCheckinRate.setInt(20, checkInHdr.getRoomTypeId());
					prsInsertCheckinRate.setInt(21, checkInHdr.getOccupancy());
					prsInsertCheckinRate.addBatch();
				}
				prsInsertCheckinRate.executeBatch();

				// start rooms count per of reservation
				prsCountRoomsPerReservation.setInt(1, checkInHdr.getResvNo());
				resultSet = null;
				resultSet = prsCountRoomsPerReservation.executeQuery();

				while (resultSet.next()) {
					totalReservedRooms = resultSet.getInt("sum_num_rooms");
					resvStatus = resultSet.getByte("status");
				}

				// end rooms count of reservation
				// start count of checked rooms
				prsCountCkeckedInRooms.setInt(1, checkInHdr.getFolioBindNo());
				resultSet = null;
				ResultSet rsFolio=null;
				rsFolio = prsCountCkeckedInRooms.executeQuery();

				while (rsFolio.next()) {
					totalCkeckedInRooms = rsFolio.getInt("checkedInRooms");
				}

				// end count of rooms per this reservation
				// Start folio section

				if (totalCkeckedInRooms == 1) {
					prsUpdateFolio.setInt(1, checkInHdr.getCheckInNo());
					prsUpdateFolio.setInt(2, checkInHdr.getFolioNo());
					prsUpdateFolio.executeUpdate();
				} else {
					countFunct.setString(2, coutnterFolioNo);
					resultSet = null;
					resultSet = countFunct.executeQuery();

					while (resultSet.next()) {
						checkInHdr.setFolioNo(resultSet.getInt(1));
					}

					prsInsertFolio.setInt(1, checkInHdr.getFolioNo());
					prsInsertFolio.setInt(2, checkInHdr.getFolioBindNo());
					// checkInPs.setInt(3, checkInHdr.getResvNo());
					prsInsertFolio.setInt(3, checkInHdr.getCheckInNo());
					prsInsertFolio.executeUpdate();
				}
				// End folio section

				// Start reservation status update section
				if ((totalReservedRooms == 1) || (totalReservedRooms == totalCkeckedInRooms
						&& resvStatus == ReservationStatus.PARTCHECKIN.getCode())) {
					prsUpdateResvHdr.setInt(1, ReservationStatus.CHECKIN.getCode());
					prsUpdateResvHdr.setInt(2, checkInHdr.getResvNo());
					prsUpdateResvHdr.executeUpdate();
				} else if (resvStatus == ReservationStatus.CONFIRMED.getCode()
						|| resvStatus == ReservationStatus.UNCONFIRMED.getCode()
						|| resvStatus == ReservationStatus.NOSHOW.getCode()) {
					prsUpdateResvHdr.setInt(1, ReservationStatus.PARTCHECKIN.getCode());
					prsUpdateResvHdr.setInt(2, checkInHdr.getResvNo());
					prsUpdateResvHdr.executeUpdate();
				}

				resultStauts = "success";
			} else {
				resultStauts = "alreadyUpdated";
			}
		
			pscheckinDisUpdate.setInt(1, checkInHdr.getResvNo());
			pscheckinDisUpdate.setInt(2, checkInHdr.getCheckInNo());
			pscheckinDisUpdate.executeUpdate();
			
			connection.commit();
		} catch (Exception e) {
			resultStauts = "error";
			e.printStackTrace();
			logger.error("Method : save " + Throwables.getStackTraceAsString(e));
			connection.rollback();
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(prsCheckRoomStatus);
			dbConnection.releaseResource(prsCountCkeckedInRooms);
			dbConnection.releaseResource(prsCountRoomsPerReservation);
			dbConnection.releaseResource(prsInsertCheckinDtl);
			dbConnection.releaseResource(prsInsertCheckinHdr);
			dbConnection.releaseResource(prsInsertCheckinRate);
			dbConnection.releaseResource(prsInsertFolio);
			dbConnection.releaseResource(prsRoomStatusUpdate);
			dbConnection.releaseResource(prsUpdateFolio);
			dbConnection.releaseResource(prsUpdateResvHdr);
			dbConnection.releaseResource(prsUpdateResvRoom);
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(countFunct);
			dbConnection.releaseResource(pscheckinDisUpdate);
			
		}

		return resultStauts;
	}

	public JsonArray getCheckInData(int resvNo) throws Exception {
		JsonArray jArray = new JsonArray();
		JsonObject jObj = null;
		ResultSet rs = null;
		PreparedStatement checkInPs = null;
		Encryption encryption = new Encryption();
		Connection connection = dbConnection.getConnection();
		String selectQuery = "select vInfo.room_number,vInfo.resv_room_no,vInfo.room_type_code,vInfo.room_type_id,vInfo.rate_id,vInfo.rate_code,vInfo.occupancy,vInfo.first_name,vInfo.last_name,vInfo.guest_name,vInfo.`room_status`,vInfo.resv_no,vInfo.email,vInfo.address,vInfo.phone,vInfo.nationality,vInfo.state,vInfo.gender,vInfo.passport_no,vInfo.passport_expiry_on,vInfo.remarks,vInfo.min_arr_date,vInfo.max_depart_date,vInfo.gstno  from v_resevation_info vInfo where vInfo.resv_no=? and vInfo.is_noshow=? and vInfo.room_status!=?";
		try {
			checkInPs = connection.prepareStatement(selectQuery);
			checkInPs.setInt(1, resvNo);
			checkInPs.setBoolean(2, false);
			checkInPs.setInt(3, ReservationStatus.NOSHOW.getCode());
			rs = checkInPs.executeQuery();
			while (rs.next()) {
				jObj = new JsonObject();
				jObj.addProperty("resv_no", rs.getInt("resv_no"));
				jObj.addProperty("resv_room_no", rs.getInt("resv_room_no"));
				jObj.addProperty("room_number", rs.getString("room_number"));
				jObj.addProperty("room_type_id", rs.getInt("room_type_id"));
				jObj.addProperty("room_type_code", rs.getString("room_type_code"));
				jObj.addProperty("occupancy", rs.getInt("occupancy"));
				jObj.addProperty("rate_id", encryption.encrypt(Integer.toString(rs.getInt("rate_id"))));
				jObj.addProperty("rate_code", rs.getString("rate_code"));
				jObj.addProperty("first_name", rs.getString("first_name"));
				jObj.addProperty("last_name", rs.getString("last_name"));
				jObj.addProperty("guest_name", rs.getString("guest_name"));
				jObj.addProperty("email", rs.getString("email"));
				jObj.addProperty("phone", rs.getString("phone"));
				jObj.addProperty("address", rs.getString("address"));
				jObj.addProperty("nationality", rs.getString("nationality"));
				jObj.addProperty("state", rs.getString("nationality"));
				jObj.addProperty("gender", rs.getString("gender"));
				jObj.addProperty("passport_no", rs.getString("passport_no"));
				jObj.addProperty("passport_expiry_on", rs.getString("passport_expiry_on"));
				jObj.addProperty("gstno", rs.getString("gstno"));
				jObj.addProperty("remarks", rs.getString("remarks"));
				jObj.addProperty("min_arr_date", rs.getString("min_arr_date"));
				jObj.addProperty("max_depart_date", rs.getString("max_depart_date"));
				jObj.addProperty("status", rs.getInt("room_status"));
				jArray.add(jObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(checkInPs);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(connection);
		}
		return jArray;
	}

	public JsonObject getReservationRateDetails(int resvRoomNo) throws Exception {
		Connection connection = dbConnection.getConnection();
		CallableStatement preparedStatement;
		CallableStatement preparedStatementStax;
		ResultSet rs = null, rsSt = null;
		String procedureRoomRate = "{call GetRoomRateDetailsForOneReservedRoom(?)}";
		String procedureservTax = "CALL GetServiceTaxAmounts(?,?)";
		// SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy-MM-dd");
		JsonObject jsonContentOfRateDetails = null;
		JsonArray jsonContentPerOccupncy = new JsonArray();
		double totalRate = 0.0, totalSTax = 0.0, totalTax = 0.0, totalDisc = 0.0, totalSCharge = 0.0;
		int roomTypeId = 0, numNights = 0;
		boolean taxIncl = false;
		String roomType = "";
		JsonObject jObject = new JsonObject();
		try {
			preparedStatement = connection.prepareCall(procedureRoomRate);
			preparedStatement.setInt(1, resvRoomNo);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				jsonContentOfRateDetails = new JsonObject();
				jsonContentOfRateDetails.addProperty("resvStatus", 0);
				jsonContentOfRateDetails.addProperty("total", rs.getString("nett_room_charge"));
				jsonContentOfRateDetails.addProperty("tax1_pc", rs.getBigDecimal("tax1_percentage"));
				jsonContentOfRateDetails.addProperty("tax2_pc", rs.getBigDecimal("tax2_percentage"));
				jsonContentOfRateDetails.addProperty("tax3_pc", rs.getBigDecimal("tax3_percentage"));
				jsonContentOfRateDetails.addProperty("tax4_pc", rs.getBigDecimal("tax4_percentage"));
				jsonContentOfRateDetails.addProperty("tax1_amount", rs.getBigDecimal("tax1_amount"));
				jsonContentOfRateDetails.addProperty("tax2_amount", rs.getBigDecimal("tax2_amount"));
				jsonContentOfRateDetails.addProperty("tax3_amount", rs.getBigDecimal("tax3_amount"));
				jsonContentOfRateDetails.addProperty("tax4_amount", rs.getBigDecimal("tax4_amount"));
				jsonContentOfRateDetails.addProperty("service_charge_pc",
						rs.getBigDecimal("service_charge_percentage"));
				jsonContentOfRateDetails.addProperty("service_charge_amount",
						rs.getBigDecimal("service_charge_amount"));
				jsonContentOfRateDetails.addProperty("tax", rs.getBigDecimal("tax"));
				jsonContentOfRateDetails.addProperty("night", rs.getInt("night"));
				jsonContentOfRateDetails.addProperty("date", rs.getString("date"));
				jsonContentOfRateDetails.addProperty("room_type_id", rs.getInt("room_type_id"));
				jsonContentOfRateDetails.addProperty("room_type_code", rs.getString("room_type_code"));
				jsonContentOfRateDetails.addProperty("rate_id", rs.getInt("rate_id"));
				jsonContentOfRateDetails.addProperty("rate_code", rs.getString("rate_code"));
				jsonContentOfRateDetails.addProperty("occupancy", rs.getInt("occupancy"));
				jsonContentOfRateDetails.addProperty("charges", rs.getBigDecimal("room_charge"));
				jsonContentOfRateDetails.addProperty("is_tax_included", rs.getBoolean("is_tax_included"));
				jsonContentOfRateDetails.addProperty("disc_amount", rs.getBigDecimal("final_disc_amt"));
				BigDecimal disc = BigDecimal.valueOf(0);
				if (rs.getBigDecimal("final_disc_amt") != null) {
					disc = rs.getBigDecimal("final_disc_amt");
				}
				roomTypeId = rs.getInt("room_type_id");
				roomType = rs.getString("room_type_code");
				totalDisc += disc.doubleValue();
				totalRate += rs.getDouble("nett_room_charge");
				totalTax += rs.getDouble("tax");
				totalSCharge += rs.getDouble("service_charge_amount");
				taxIncl = rs.getBoolean("is_tax_included");
				numNights += 1;
				if (commonSettings.isServiceTaxIncluded) {
					BigDecimal total = rs.getBigDecimal("nett_room_charge").subtract(rs.getBigDecimal("tax"))
							.subtract(disc);
					preparedStatementStax = connection.prepareCall(procedureservTax);
					preparedStatementStax.setInt(1, commonSettings.serviceTaxId);
					preparedStatementStax.setBigDecimal(2, total);
					rsSt = preparedStatementStax.executeQuery();
					if (rsSt.next()) {
						jsonContentOfRateDetails.addProperty("service_tax", rsSt.getBigDecimal("tp_total_service_tax"));
					}
					totalSTax += rsSt.getDouble("tp_total_service_tax");
				} else {
					jsonContentOfRateDetails.addProperty("service_tax", 0);
				}
				jsonContentOfRateDetails.addProperty("service_tax_incl", commonSettings.isServiceTaxIncluded);
				jsonContentPerOccupncy.add(jsonContentOfRateDetails);
			}
			jObject.addProperty("roomTypeId", roomTypeId);
			jObject.addProperty("roomTypeCode", roomType);
			jObject.addProperty("numNights", numNights);
			jObject.addProperty("totalRate", Rounding.nRound(totalRate));
			jObject.addProperty("totalTax", Rounding.nRound(totalTax));
			jObject.addProperty("isTaxIncl", taxIncl);
			jObject.addProperty("totalSTax", Rounding.nRound(totalSTax));
			jObject.addProperty("totalDisc", Rounding.nRound(totalDisc));
			jObject.addProperty("totalSCharge", Rounding.nRound(totalSCharge));
			jObject.add("ratePerOcc", jsonContentPerOccupncy);
		} catch (Exception e) {
			logger.error("Method : getRoomRateDetails()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(connection);
		}
		return jObject;
	}

	@Override
	public List<CheckInRate> getRoomCharge(int checkinNo) {
		Connection con = null;
		ResultSet resultSet = null;
		List<CheckInRate> checkinRatePlanList = new ArrayList<CheckInRate>();
		CheckInRate checkinRates = new CheckInRate();
		PreparedStatement checkinRatePs = null;
		String selectQuery = "select * from checkin_rate where checkin_dtl_no=" + checkinNo;
		;
		try {
			con = dbConnection.getConnection();
			checkinRatePs = con.prepareStatement(selectQuery);
			resultSet = checkinRatePs.executeQuery();
			if (resultSet.next()) {
				checkinRates = new CheckInRate();
				checkinRates.setCheckinDtlNo(resultSet.getInt("checkin_dtl_no"));
				checkinRates.setRoomCharge(resultSet.getBigDecimal("room_charge"));
				/*
				 * checkinRates.setResvRoomNo(resultSet.getInt("resvRoomNo"));
				 * checkinRates.setRoomNumber(resultSet.getString("roomNumber"));
				 */
				checkinRatePlanList.add(checkinRates);
			}
		} catch (Exception e) {
			logger.error("Method : getCheckinRates()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
		} finally {
			dbConnection.releaseResource(checkinRatePs);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(con);

		}

		return checkinRatePlanList;

	}

	@Override
	public List<CheckinType> getCheckinTypes() throws Exception {
		Connection con = null;
		ResultSet resultSet = null;
		List<CheckinType> checkinTypeList = new ArrayList<CheckinType>();
		CheckinType checkinType;
		PreparedStatement checkinRatePs = null;
		String selectQuery = "select * from checkin_types where is_active=1;";
		try {
			con = dbConnection.getConnection();
			checkinRatePs = con.prepareStatement(selectQuery);
			resultSet = checkinRatePs.executeQuery();
			while (resultSet.next()) {
				checkinType = new CheckinType();
				checkinType.setId(resultSet.getInt("id"));
				checkinType.setName(resultSet.getString("name"));
				/*
				 * checkinRates.setResvRoomNo(resultSet.getInt("resvRoomNo"));
				 * checkinRates.setRoomNumber(resultSet.getString("roomNumber"));
				 */
				checkinTypeList.add(checkinType);
			}
		} catch (Exception e) {
			logger.error("Method : getCheckinRates()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
		} finally {
			dbConnection.releaseResource(checkinRatePs);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(con);

		}

		return checkinTypeList;
	}

	@Override
	public List<PaymentType> getPaymentTypes() {

		Connection con = null;
		ResultSet resultSet = null;
		List<PaymentType> paymentTypes = new ArrayList<PaymentType>();
		// CheckinType checkinType;
		PreparedStatement preparedStatements = null;
		String selectQuery = "select * from payment_source where is_active=1;";
		try {
			con = dbConnection.getConnection();
			preparedStatements = con.prepareStatement(selectQuery);
			resultSet = preparedStatements.executeQuery();
			while (resultSet.next()) {
				PaymentType paymentType = new PaymentType();
				paymentType.setId(resultSet.getInt("id"));
				paymentType.setName(resultSet.getString("name"));
				paymentTypes.add(paymentType);
			}
		} catch (Exception e) {
			logger.error("Method : getCheckinRates()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
		} finally {
			dbConnection.releaseResource(preparedStatements);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(con);

		}

		return paymentTypes;
	}

	@Override
	public JsonArray getCustomers() {
		JsonArray jArray = new JsonArray();
		JsonObject jObj = null;
		ResultSet rs = null;
		PreparedStatement pS = null;
		Connection connection = dbConnection.getConnection();
		String query = "select  max(checkin_hdr.checkin_no) as checkin_no, checkin_dtl.first_name as first_name "
				+ "from checkin_hdr inner join checkin_dtl on checkin_hdr.checkin_no=checkin_dtl.checkin_no where checkin_dtl.is_sharer=0 group by checkin_dtl.first_name";

		try {
			pS = connection.prepareStatement(query);
			rs = pS.executeQuery();
			while (rs.next()) {
				jObj = new JsonObject();
				jObj.addProperty("id", rs.getInt("checkin_no"));
				jObj.addProperty("name", rs.getString("first_name"));

				jArray.add(jObj);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(pS);
		}
		return jArray;
	}

	@Override
	public JsonArray loadData(String customer) {
		JsonArray jArray = new JsonArray();
		JsonObject jObj = null;
		ResultSet rs = null;
		PreparedStatement pS = null;
		Connection connection = dbConnection.getConnection();
		/*
		 * String
		 * query="select checkin_dtl.salutation,checkin_dtl.last_name,checkin_dtl.address,email,gender,phone,nationality,state,passport_no,passport_expiry_on,gstno "
		 * +
		 * "from checkin_hdr inner join checkin_dtl on checkin_hdr.checkin_no=checkin_dtl.checkin_no where checkin_dtl.is_sharer=0 and checkin_hdr.checkin_no="
		 * +checkinNo; String phone_no=null;
		 */
		String query = "SELECT count(*) AS count, max(checkin_hdr.arr_date) as arr_date, checkin_dtl.salutation,"
				+ " checkin_dtl.first_name,checkin_dtl.last_name,checkin_dtl.gender,checkin_dtl.address,"
				+ " checkin_dtl.email,checkin_dtl.phone,checkin_dtl.nationality,checkin_dtl.state,checkin_dtl.passport_no,"
				+ " max((CASE WHEN checkin_dtl.passport_expiry_on != '0000-00-00' THEN checkin_dtl.passport_expiry_on ELSE '1900-01-01' END)) "
				+ " as passport_expiry_on,checkin_dtl.gstno,checkin_dtl.remarks FROM checkin_dtl INNER JOIN"
				+ " checkin_hdr ON checkin_hdr.checkin_no = checkin_dtl.checkin_no WHERE checkin_dtl.first_name LIKE  '"+ customer +"%'"
				+ " GROUP BY checkin_dtl.salutation,"
				+ " checkin_dtl.first_name,checkin_dtl.last_name,checkin_dtl.gender,checkin_dtl.address,checkin_dtl.email,"
				+ " checkin_dtl.phone,checkin_dtl.nationality,checkin_dtl.state,checkin_dtl.passport_no,"
				+ " checkin_dtl.gstno";

		try {
			pS = connection.prepareStatement(query);
			rs = pS.executeQuery();
			while (rs.next()) {
				jObj = new JsonObject();
				jObj.addProperty("salutation", rs.getString("salutation"));
				jObj.addProperty("first_name", rs.getString("first_name"));
				jObj.addProperty("last_name", rs.getString("last_name"));
				jObj.addProperty("address", rs.getString("address"));
				jObj.addProperty("phone", rs.getString("phone"));
				jObj.addProperty("mail", rs.getString("email"));
				jObj.addProperty("gender", rs.getString("gender"));
				jObj.addProperty("nationality", rs.getString("nationality"));
				jObj.addProperty("state", rs.getString("state"));
				jObj.addProperty("passport_no", rs.getString("passport_no"));
				jObj.addProperty("passport_expiry_on", rs.getString("passport_expiry_on"));
				jObj.addProperty("gstno", rs.getString("gstno"));
				jObj.addProperty("no_visit", rs.getString("count"));
				jObj.addProperty("last_visit", rs.getString("arr_date"));
				jArray.add(jObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(pS);
		}
		return jArray;
	}

	@Override
	public JsonObject loadDataByMail(String mail) {

		JsonObject jObj = new JsonObject();
		ResultSet rs = null;
		ResultSet rs1 = null;
		PreparedStatement pS = null;
		PreparedStatement pS1 = null;
		Connection connection = dbConnection.getConnection();
		String query = "select salutation,first_name, last_name,gender, address,email,phone,nationality,state,passport_no,passport_expiry_on,gstno "
				+ " from checkin_dtl where is_sharer=0 and email= '" + mail + "'";
		String phone_no = null;
		try {
			pS = connection.prepareStatement(query);
			rs = pS.executeQuery();
			while (rs.next()) {
				jObj.addProperty("salutation", rs.getString("salutation"));
				jObj.addProperty("first_name", rs.getString("first_name"));
				jObj.addProperty("last_name", rs.getString("last_name"));
				jObj.addProperty("address", rs.getString("address"));
				jObj.addProperty("phone", rs.getString("phone"));
				jObj.addProperty("gender", rs.getString("gender"));
				phone_no = rs.getString("phone");
				jObj.addProperty("nationality", rs.getString("nationality"));
				jObj.addProperty("state", rs.getString("state"));
				jObj.addProperty("passport_no", rs.getString("passport_no"));
				jObj.addProperty("passport_expiry_on", rs.getString("passport_expiry_on"));
				jObj.addProperty("gstno", rs.getString("gstno"));

			}
			if (phone_no != null) {
				connection = dbConnection.getConnection();
				String query1 = "SELECT count(checkin_hdr.arr_date) AS count, max(checkin_hdr.arr_date) as arr_date FROM checkin_hdr"
						+ " INNER JOIN checkin_dtl ON checkin_hdr.checkin_no = checkin_dtl.checkin_no"
						+ " WHERE checkin_dtl.phone = '" + phone_no + "'";
				pS1 = connection.prepareStatement(query1);
				rs1 = pS1.executeQuery();
				while (rs1.next()) {
					jObj.addProperty("no_visit", rs1.getString("count"));
					jObj.addProperty("last_visit", rs1.getString("arr_date"));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(rs1);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(pS1);
			dbConnection.releaseResource(pS);
		}
		return jObj;
	}

}