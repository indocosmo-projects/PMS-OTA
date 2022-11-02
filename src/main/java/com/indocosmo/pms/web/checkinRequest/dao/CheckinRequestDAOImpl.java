package com.indocosmo.pms.web.checkinRequest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.checkIn.model.CheckInRequest;
import com.indocosmo.pms.web.checkIn.model.CheckInRequestStatus;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.facilities.dao.FacilityDAO;

@Repository
public class CheckinRequestDAOImpl implements CheckinRequestDAO {
	DbConnection dbConnection = null;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	FacilityDAO facilityDAO;

	public CheckinRequestDAOImpl() {
		dbConnection = new DbConnection();
	}

	public JsonArray getListRoomRequest() throws Exception {
		JsonArray jarray = new JsonArray();
		ResultSet rs = null;
		PreparedStatement psaddon = null;
		Connection connection = dbConnection.getConnection();
		String sql = "SELECT cr.id,cr.is_one_time_request,cr.req_date,cr.req_time,cr.req_remarks,cr.facility_id,f.`code`,f.`name`,f.is_payable,f.acc_mst_id,f.amount,f.facility_type,ch.room_number,tbl.process_date,tbl.process_status,tbl.remarks,cr.is_req_completed,cr.is_canceled,ch.folio_bind_no,ch.checkin_no FROM checkin_request cr INNER JOIN facilities f ON f.id = cr.facility_id INNER JOIN checkin_hdr ch ON ch.checkin_no = cr.checkin_no LEFT JOIN (SELECT checkin_request_id,max(date) AS process_date,process_status,remarks FROM checkin_request_status WHERE checkin_request_status.date <= '2018-01-07' GROUP BY checkin_request_id ) AS tbl ON tbl.checkin_request_id = cr.id WHERE ch.`status` = 5";
		try {
			psaddon = connection.prepareStatement(sql);
			rs = psaddon.executeQuery();
			while (rs.next()) {
				JsonObject jobject = new JsonObject();
				jobject.addProperty("chreq_id", rs.getInt("id"));
				jobject.addProperty("chreq_is_one_time_request", rs.getBoolean("is_one_time_request"));
				jobject.addProperty("chreq_req_date", rs.getDate("req_date").toString());
				jobject.addProperty("chreq_req_time", rs.getString("req_time"));
				jobject.addProperty("chreq_req_remarks", rs.getString("req_remarks"));
				jobject.addProperty("chreq_facility_id", rs.getInt("facility_id"));
				jobject.addProperty("facility_code", rs.getString("code"));
				jobject.addProperty("facility_name", rs.getString("name"));
				jobject.addProperty("facility_is_payable", rs.getBoolean("is_payable"));
				jobject.addProperty("facility_amount", rs.getDouble("amount"));
				jobject.addProperty("facility_facility_type", rs.getInt("facility_type"));
				jobject.addProperty("room_number", rs.getString("room_number"));
				if (rs.getDate("process_date") != null) {
					jobject.addProperty("process_date", rs.getDate("process_date").toString());
				} else {
					jobject.addProperty("process_date", "");
				}
				jobject.addProperty("remarks", rs.getString("remarks"));
				jobject.addProperty("process_status", rs.getBoolean("process_status"));
				jobject.addProperty("is_req_completed", rs.getBoolean("is_req_completed"));
				jobject.addProperty("is_canceled", rs.getBoolean("is_canceled"));
				jobject.addProperty("acc_mst_id", rs.getInt("acc_mst_id"));
				jobject.addProperty("folio_bind_no", rs.getInt("folio_bind_no"));
				jobject.addProperty("checkin_no", rs.getInt("checkin_no"));
				jarray.add(jobject);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(psaddon);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(connection);
		}
		return null;
	}

	public JsonArray getAddOnDetails(int id, int checkin_no) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String hotelDate = formatter.format(commonSettings.getHotelDate());
		JsonArray jarray = new JsonArray();
		ResultSet rs = null;
		PreparedStatement psaddon = null;
		Connection connection = dbConnection.getConnection();

		String sql = "select cr.id,cr.is_one_time_request,cr.req_date,cr.req_time,cr.req_remarks,cr.facility_id,f.`code`,f.`name`,f.is_payable,f.acc_mst_id,f.amount,f.facility_type,ch.room_number,tbl.process_date,tbl.process_status,tbl.remarks,cr.is_req_completed,cr.is_canceled,ch.folio_bind_no,ch.checkin_no from checkin_request cr inner join facilities f on f.id = cr.facility_id inner join checkin_hdr ch on ch.checkin_no = cr.checkin_no "
				+ "LEFT JOIN (select checkin_request_id,max(date) as process_date,process_status,remarks from checkin_request_status where checkin_request_status.date<=? GROUP BY checkin_request_id) as tbl on tbl.checkin_request_id = cr.id "
				+ "where ch.`status` = 5";
		if (checkin_no != 0) {
			sql = sql + " and cr.checkin_no=?";
		} else if (id == 0) {
			sql = sql
					+ " and (CASE WHEN cr.is_one_time_request=1 THEN (cr.req_date BETWEEN ? and (select ? + INTERVAL 1 DAY)) ELSE 1=1 END) and (CASE WHEN (DATE(tbl.process_date) != ? and cr.is_req_completed=1) THEN cr.is_req_completed=0 ELSE 1=1 END) and cr.is_canceled=0 ";
		}
		if (id != 0) {
			sql = sql + " and cr.id=?";
		}
		try {
			psaddon = connection.prepareStatement(sql);
			psaddon.setString(1, hotelDate);
			if (checkin_no != 0) {
				psaddon.setInt(2, checkin_no);
				if (id != 0) {
					psaddon.setInt(3, id);
				}
			} else if (id == 0) {
				psaddon.setString(2, hotelDate);
				psaddon.setString(3, hotelDate);
				psaddon.setString(4, hotelDate);
			}
			if (id != 0) {
				psaddon.setInt(2, id);
			}
			rs = psaddon.executeQuery();
			while (rs.next()) {
				JsonObject jobject = new JsonObject();
				jobject.addProperty("chreq_id", rs.getInt("id"));
				jobject.addProperty("chreq_is_one_time_request", rs.getBoolean("is_one_time_request"));
				jobject.addProperty("chreq_req_date", rs.getDate("req_date").toString());
				jobject.addProperty("chreq_req_time", rs.getString("req_time"));
				jobject.addProperty("chreq_req_remarks", rs.getString("req_remarks"));
				jobject.addProperty("chreq_facility_id", rs.getInt("facility_id"));
				jobject.addProperty("facility_code", rs.getString("code"));
				jobject.addProperty("facility_name", rs.getString("name"));
				jobject.addProperty("facility_is_payable", rs.getBoolean("is_payable"));
				jobject.addProperty("facility_amount", rs.getDouble("amount"));
				jobject.addProperty("facility_facility_type", rs.getInt("facility_type"));
				jobject.addProperty("room_number", rs.getString("room_number"));
				if (rs.getDate("process_date") != null) {
					jobject.addProperty("process_date", rs.getDate("process_date").toString());
				} else {
					jobject.addProperty("process_date", "");
				}
				jobject.addProperty("remarks", rs.getString("remarks"));
				jobject.addProperty("process_status", rs.getBoolean("process_status"));
				jobject.addProperty("is_req_completed", rs.getBoolean("is_req_completed"));
				jobject.addProperty("is_canceled", rs.getBoolean("is_canceled"));
				jobject.addProperty("acc_mst_id", rs.getInt("acc_mst_id"));
				jobject.addProperty("folio_bind_no", rs.getInt("folio_bind_no"));
				jobject.addProperty("checkin_no", rs.getInt("checkin_no"));
				jarray.add(jobject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(psaddon);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(connection);
		}
		return jarray;
	}

	public boolean saveAddOns(List<CheckInRequest> requestList) throws Exception {
		boolean isSave = true;
		try {
			Session session = sessionFactory.getCurrentSession();

			for (CheckInRequest chr : requestList) {
				if (chr.getIsDeleted() != true) {
					if (chr.getId() != 0) {
						session.update(chr);
					} else {
						session.save(chr);
					}
				} else {
					isSave = deleteAddOns(chr.getId());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			isSave = false;
			throw new CustomException();
		}
		return isSave;
	}

	public boolean deleteAddOns(int id) throws Exception {
		boolean isDeleted = true;
		try {
			Session session = sessionFactory.getCurrentSession();
			CheckInRequest chreq = (CheckInRequest) session.load(CheckInRequest.class, id);
			session.delete(chreq);
		} catch (Exception ex) {
			ex.printStackTrace();
			isDeleted = false;
			throw new CustomException();
		}
		return isDeleted;
	}

	public boolean cancelAddOns(int id) throws Exception {
		boolean isCanceled = true;
		try {
			Session session = sessionFactory.getCurrentSession();
			CheckInRequest chreq = (CheckInRequest) session.load(CheckInRequest.class, id);
			chreq.setCanceled(true);
			session.update(chreq);
		} catch (Exception ex) {
			ex.printStackTrace();
			isCanceled = false;
			throw new CustomException();
		}
		return isCanceled;
	}

	public List<CheckInRequest> getCheckInRequestList(int checkno, String source) {
		String qry = "from CheckInRequest where resv_room_no=" + checkno;
		if (source.equals("checkin")) {
			qry = "from CheckInRequest where checkin_no=" + checkno;
		}
		List<CheckInRequest> checkInRequestList = null;
		try {
			checkInRequestList = sessionFactory.getCurrentSession().createQuery(qry).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		return checkInRequestList;

	}

	public CheckInRequest getCheckInRequest(int id) {
		CheckInRequest checkInRequest = null;
		try {
			checkInRequest = (CheckInRequest) sessionFactory.getCurrentSession().get(CheckInRequest.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		return checkInRequest;

	}

	public boolean processAddon(CheckInRequestStatus reqStatus) throws Exception {
		boolean isSave = true;
		try {
			Session session = sessionFactory.getCurrentSession();
			if (reqStatus.getId() != 0) {
				session.update(reqStatus);
			} else {
				session.save(reqStatus);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			isSave = false;
			throw new CustomException();
		}
		return isSave;
	}

	public boolean deleteAddOnStatus(int id) throws Exception {
		boolean isDeleted = true;
		try {
			Session session = sessionFactory.getCurrentSession();
			CheckInRequestStatus chreq = (CheckInRequestStatus) session.load(CheckInRequestStatus.class, id);
			session.delete(chreq);
		} catch (Exception ex) {
			ex.printStackTrace();
			isDeleted = false;
			throw new CustomException();
		}
		return isDeleted;
	}

	public List<CheckInRequestStatus> getCheckInRequestStatusList(int requestId) throws Exception {
		List<CheckInRequestStatus> checkInRequestStatusList = null;
		try {
			checkInRequestStatusList = sessionFactory.getCurrentSession()
					.createQuery("from CheckInRequestStatus where checkInReequestId=" + requestId + " order by date")
					.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		return checkInRequestStatusList;
	}

	public JsonArray getRoomList() throws Exception {
		ResultSet rs = null;
		Statement statement  = null;
		Connection connection = dbConnection.getConnection();
		JsonArray jarray = new JsonArray();
		try {
		String sql = "select ch.checkin_no,ch.room_number,cd.first_name,cd.phone,ch.exp_depart_date from checkin_hdr ch inner join checkin_dtl cd on cd.checkin_no = ch.checkin_no where ch.`status` = 5 and cd.is_sharer=0";
		statement = connection.createStatement();
		rs = statement.executeQuery(sql);
		
		while (rs.next()) {
			JsonObject jobject = new JsonObject();
			jobject.addProperty("checkin_no", rs.getInt("checkin_no"));
			jobject.addProperty("room_number", rs.getString("room_number"));
			jobject.addProperty("first_name", rs.getString("first_name"));
			jobject.addProperty("phone", rs.getString("phone"));
			jobject.addProperty("exp_depart_date", rs.getDate("exp_depart_date").toString());
			jarray.add(jobject);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(connection);
		}
		return jarray;
	}

}
