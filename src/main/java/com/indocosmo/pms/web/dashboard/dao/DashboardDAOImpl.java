package com.indocosmo.pms.web.dashboard.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.enumerator.ReservationStatus;
import com.indocosmo.pms.enumerator.RoomHkStatus;
import com.indocosmo.pms.enumerator.RoomInventoryStatus;
import com.indocosmo.pms.enumerator.RoomOccupancyStatus;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.dashboard.model.RoomDetails;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.facilities.dao.FacilityDAO;
import com.indocosmo.pms.web.reservation.model.ResvRoom;
import com.mysql.jdbc.PreparedStatement;

@Repository
public class DashboardDAOImpl implements DashboardDAO {
	DbConnection dbConnection = null;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	FacilityDAO facilityDAO;

	public DashboardDAOImpl() {
		dbConnection = new DbConnection();
	}

	public JsonObject getDataForDashBoard() throws Exception {
		Connection connection = dbConnection.getConnection();
		JsonObject jobject = new JsonObject();
		ResultSet rs = null;
		CallableStatement preparedStatement = null;
		String countOfcheckIn = "call getDashboardDataCounts()";
		try {
			preparedStatement = connection.prepareCall(countOfcheckIn);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				jobject.addProperty("cancel_this_month", rs.getInt("temp_cancel_this_month"));
				jobject.addProperty("cancel_prev_month", rs.getInt("temp_cancel_prev_month"));
				jobject.addProperty("cancel_bfr_prev_month", rs.getInt("temp_cancel_bfr_prev_month"));
				jobject.addProperty("cancel_next_month", rs.getInt("temp_cancel_next_month"));
				jobject.addProperty("cancel_aftr_next_month", rs.getInt("temp_cancel_aftr_next_month"));
				jobject.addProperty("confirm_this_month", rs.getInt("temp_confirm_this_month"));
				jobject.addProperty("confirm_prev_month", rs.getInt("temp_confirm_prev_month"));
				jobject.addProperty("confirm_bfr_prev_month", rs.getInt("temp_confirm_bfr_prev_month"));
				jobject.addProperty("confirm_next_month", rs.getInt("temp_confirm_next_month"));
				jobject.addProperty("confirm_aftr_next_month", rs.getInt("temp_confirm_aftr_next_month"));
				jobject.addProperty("cancel_this_month_room", rs.getInt("temp_cancel_this_month_room"));
				jobject.addProperty("cancel_prev_month_room", rs.getInt("temp_cancel_prev_month_room"));
				jobject.addProperty("cancel_bfr_prev_month_room", rs.getInt("temp_cancel_bfr_prev_month_room"));
				jobject.addProperty("cancel_next_month_room", rs.getInt("temp_cancel_next_month_room"));
				jobject.addProperty("cancel_aftr_next_month_room", rs.getInt("temp_cancel_aftr_next_month_room"));
				jobject.addProperty("checkin_this_month", rs.getInt("temp_checkin_this_month"));
				jobject.addProperty("checkin_prev_month", rs.getInt("temp_checkin_prev_month"));
				jobject.addProperty("checkin_bfr_prev_month", rs.getInt("temp_checkin_bfr_prev_month"));
				jobject.addProperty("checkin_next_month", rs.getInt("temp_checkin_next_month"));
				jobject.addProperty("checkin_aftr_next_month", rs.getInt("temp_checkin_aftr_next_month"));
				jobject.addProperty("chin_today", rs.getInt("temp_chin_today"));
				jobject.addProperty("chin_yesterday", rs.getInt("temp_chin_yesterday"));
				jobject.addProperty("chin_in_30_days", rs.getInt("temp_chin_in_30_days"));
				jobject.addProperty("chin_before_30_days", rs.getInt("temp_chin_before_30_days"));
				jobject.addProperty("inhouse_today", rs.getInt("temp_inhouse_today"));
				jobject.addProperty("chout_today", rs.getInt("temp_chout_today"));
				jobject.addProperty("chout_yesterday", rs.getInt("temp_chout_yesterday"));
				jobject.addProperty("req_not_processed_count", rs.getInt("temp_req_not_processed_count"));
				jobject.addProperty("resv_confirmation_pending", rs.getInt("temp_resv_confirmation_pending"));
				jobject.addProperty("checkin_pending", rs.getInt("temp_checkin_pending"));
				jobject.addProperty("checkout_pending", rs.getInt("temp_checkout_pending"));
				jobject.addProperty("dirty_cleaning", rs.getInt("temp_dirty_cleaning"));
				jobject.addProperty("outofinventory", rs.getInt("temp_outofinventory"));
				jobject.addProperty("total_rooms_in_inventory", rs.getInt("temp_total_rooms_in_inventory"));
				jobject.addProperty("occupied_rooms", rs.getInt("temp_occupied_rooms"));
				
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(preparedStatement);
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(rs);
		}

		return jobject;
	}

	public JsonArray getLastReservationData(String wherePart) throws Exception {
		JsonArray jsonArr = new JsonArray();
		JsonObject jobject = null;
		String sql = "";
		if (!wherePart.equals("")) {
			sql = "select vr.resv_no,vr.reserved_by,vr.arr_date,vr.depart_date,vr.folio_bind_no,vr.num_nights,vr.num_rooms,vr.resv_type_xlt,vr.resv_status_xlt,vr.resv_date,vr.cut_off_date from v_list_reservation vr "
					+ wherePart;
		}
		Statement statement = null;
		ResultSet rs = null;
		Connection connection = dbConnection.getConnection();
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				jobject = new JsonObject();
				jobject.addProperty("resv_no", rs.getInt("resv_no"));
				jobject.addProperty("reserved_by", rs.getString("reserved_by"));
				jobject.addProperty("arr_date", rs.getDate("arr_date").toString());
				jobject.addProperty("depart_date", rs.getDate("depart_date").toString());
				jobject.addProperty("folio_bind_no", rs.getInt("folio_bind_no"));
				jobject.addProperty("num_nights", rs.getInt("num_nights"));
				jobject.addProperty("num_rooms", rs.getInt("num_rooms"));
				jobject.addProperty("resv_type", rs.getString("resv_type_xlt"));
				jobject.addProperty("resv_status", rs.getString("resv_status_xlt"));
				jobject.addProperty("resv_date", rs.getDate("resv_date").toString());
				jobject.addProperty("cut_off_date", rs.getDate("cut_off_date").toString());
				jsonArr.add(jobject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
		}

		return jsonArr;
	}

	public List<RoomDetails> getRoomDetails(int roomType, int floor) throws Exception {
		Connection connection = dbConnection.getConnection();
		String roomTypeSql = "";
		String floorSql = "";
		if (roomType != 0) {
			roomTypeSql = " AND room.room_type_id = " + roomType;
		}
		if (floor != 0) {
			floorSql = " AND room.floor_id = " + floor;
		}
					
	/*	String sql = "SELECT room.id, room.floor_id, room.number, room.`name`, room.inv_status, room.hk_status, room.occ_status, room.room_type_id, "
				+ "room_type.`code` AS roomTypeCode, checkin.checkin_no, checkin.arr_date, checkin.exp_depart_date, checkin.act_depart_date,"
				+ " checkin.`status`, room.is_deleted,(select room_status from resv_room where resv_room.room_number = room.number ORDER BY room_status LIMIT 1) AS roomStatus FROM room INNER JOIN room_type ON room.room_type_id = room_type.id "
				+ "LEFT JOIN (SELECT checkin_hdr.checkin_no, checkin_hdr.arr_date, checkin_hdr.exp_depart_date, checkin_hdr.act_depart_date, "
				+ "checkin_hdr.room_number, checkin_hdr.`status` FROM checkin_hdr ORDER BY checkin_hdr.checkin_no DESC) "
				+ " checkin ON checkin.room_number = room.number WHERE room.is_deleted = 0 " + roomTypeSql + floorSql
				+ " GROUP BY room.number ORDER BY room.room_type_id, room.number";*/
		
		
		String sql = "SELECT room.id, room.floor_id, room.number, room.`name`, room.inv_status, room.hk_status, room.occ_status, room.room_type_id, "
				+ "room_type.`code` AS roomTypeCode, checkin.checkin_no, checkin.arr_date, checkin.exp_depart_date, checkin.act_depart_date,"
				+ " checkin.`status`, room.is_deleted,(select room_status from resv_room where resv_room.room_number = room.number ORDER BY room_status LIMIT 1) AS roomStatus FROM room INNER JOIN room_type ON room.room_type_id = room_type.id "
				+ "LEFT JOIN (SELECT T2.checkin_no,T2.arr_date,T2.exp_depart_date,T2.act_depart_date,T2.room_number,T2.`status` FROM(SELECT  max(checkin_hdr.checkin_no) AS checkin_no "
				+ "FROM checkin_hdr GROUP BY room_number)T1 INNER JOIN checkin_hdr T2 ON T1.checkin_no = T2.checkin_no )"
				+ " checkin ON checkin.room_number = room.number WHERE room.is_deleted = 0 " + roomTypeSql + floorSql
				+ " GROUP BY room.number ORDER BY room.room_type_id, room.number";
		
		
		
		

		List<RoomDetails> roomDtlList = new ArrayList<RoomDetails>();
		RoomDetails roomDtl = null;
		Statement statement = null;

		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
	
			while (rs.next()) {
				roomDtl = new RoomDetails();
				roomDtl.setActualDepartDate(rs.getDate("act_depart_date"));
				roomDtl.setArrivalDate(rs.getDate("arr_date"));
				roomDtl.setCheckinNo(rs.getInt("checkin_no"));
				roomDtl.setCheckinStatus(rs.getInt("status"));
				roomDtl.setExpDepartDate(rs.getDate("exp_depart_date"));
				roomDtl.setHkStatus(rs.getInt("hk_status"));
				roomDtl.setHkStatusName(RoomHkStatus.get(roomDtl.getHkStatus()).name());
				roomDtl.setInvStatus(rs.getInt("inv_status"));
				roomDtl.setInvStatusName(RoomInventoryStatus.get(roomDtl.getInvStatus()).getRoomInventoryStatus());
				roomDtl.setOccStatus(rs.getInt("occ_status"));
				roomDtl.setOccStatusName(RoomOccupancyStatus.get(roomDtl.getOccStatus()).name());
				roomDtl.setRoomName(rs.getString("name"));
				roomDtl.setRoomNumber(rs.getString("number"));
				roomDtl.setRoomid(rs.getInt("id"));
				roomDtl.setRoomTypeCode(rs.getString("roomTypeCode"));
				roomDtl.setRoomTypeId(rs.getInt("room_type_id"));
				roomDtl.setResvStatus(rs.getInt("roomStatus"));
				roomDtlList.add(roomDtl);
	
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		} 
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
		}

		return roomDtlList;
	}

	

}
