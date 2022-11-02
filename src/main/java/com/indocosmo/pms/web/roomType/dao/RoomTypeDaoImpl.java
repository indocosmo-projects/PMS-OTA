package com.indocosmo.pms.web.roomType.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.google.gson.JsonObject;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.roomType.model.RoomType;

@Repository
public class RoomTypeDaoImpl implements RoomTypeDao {
	DbConnection dbConnection = null;

	public RoomTypeDaoImpl() {
		dbConnection = new DbConnection();
	}

	@Autowired
	private SessionFactory sessionfactory;

	private static final Logger logger = LoggerFactory.getLogger(RoomTypeDaoImpl.class);

	@SuppressWarnings("unchecked")
	public List<RoomType> getRoomTypeLists() {
		List<RoomType> roomTypeList = null;
		try {
			roomTypeList = sessionfactory.getCurrentSession().createQuery("from RoomType where is_deleted=0").list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return roomTypeList;
	}

	public boolean save(RoomType rType) throws Exception {
		boolean isSave = false;
		try {
			Session session = sessionfactory.getCurrentSession();
			if (rType.getId() != 0) {
				session.update(rType);
			} else {
				session.save(rType);
			}
			isSave = true;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method:save," + Throwables.getStackTraceAsString(e));
			isSave = false;
			throw new CustomException();

		}
		return isSave;
	}

	public boolean delete(int id) {
		boolean isDelete = false;
		try {
			Session session = sessionfactory.getCurrentSession();
			RoomType rType = (RoomType) session.load(RoomType.class, id);
			rType.setDeleted(true);
			session.update(rType);
			isDelete = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : delete," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return isDelete;
	}

	public boolean isCodeExists(String code) {
		boolean isExist = false;

		try {
			Query query = sessionfactory.getCurrentSession()
					.createQuery("select id from RoomType where code='" + code + "' and deleted=0");

			if (query.list().size() > 0) {
				isExist = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : isCodeExists, " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return isExist;
	}

	public RoomType getAvailRooms(int id) {
		RoomType roomtype = null;

		try {
			roomtype = (RoomType) sessionfactory.getCurrentSession().get(RoomType.class, id);

			if (roomtype != null) {
				Query query = sessionfactory.getCurrentSession().createQuery(
						"select count(number) from Room where roomType=" + Integer.toString(id) + " and deleted=0");

				@SuppressWarnings("unchecked")
				List<Long> noList = query.list();

				if (noList.size() > 0) {
					roomtype.setNoOfRooms(noList.get(0));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getRecord, " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return roomtype;
	}

	public JsonObject getRoomTypeImages(int id) {
		ResultSet rs = null;
		JsonObject jobj = new JsonObject();
		PreparedStatement resvRoomPs = null;
		Connection connection = dbConnection.getConnection();
		String selectQuery = "select image1,image2,image3,image4 from room_type where id=?";
		try {
			resvRoomPs = connection.prepareStatement(selectQuery);
			resvRoomPs.setInt(1, id);
			rs = resvRoomPs.executeQuery();
			while (rs.next()) {

				jobj.addProperty("image1", rs.getString("image1"));
				jobj.addProperty("image2", rs.getString("image2"));
				jobj.addProperty("image3", rs.getString("image3"));
				jobj.addProperty("image4", rs.getString("image4"));

			}
		} catch (Exception e) {
			logger.error("Method : getRoomData()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(resvRoomPs);
			dbConnection.releaseResource(rs);
		}
		return jobj;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getRoomTypeList() {
		List<Object[]> roomTypes = null;
		Map<Integer, String> roomTypeMap = new LinkedHashMap<Integer, String>();

		try {
			String hql = "select id,code from RoomType where deleted=0";
			Query query = sessionfactory.getCurrentSession().createQuery(hql);
			roomTypes = query.list();

			for (Object[] roomType : roomTypes) {
				roomTypeMap.put((Integer) roomType[0], (String) roomType[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getRoomTypeList, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return roomTypeMap;
	}

	/**
	 * Database access to get single record from room_type table
	 * 
	 * @param id
	 * @return single record from room_type
	 */
	public RoomType getRecord(int id) throws Exception {
		RoomType roomtype = null;

		try {
			roomtype = (RoomType) sessionfactory.getCurrentSession().get(RoomType.class, id);

			if (roomtype != null) {
				Query query = sessionfactory.getCurrentSession().createQuery(
						"select count(number) from Room where roomType=" + Integer.toString(id) + " and deleted=0");

				@SuppressWarnings("unchecked")
				List<Long> noList = query.list();

				if (noList.size() > 0) {
					roomtype.setNoOfRooms(noList.get(0));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getRecord, " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return roomtype;
	}

	@Override
	public int getCount(int id) {
		int count = 0;
		DbConnection dbConnection = null;
		ResultSet rs = null;
		dbConnection = new DbConnection();
		PreparedStatement countQuery = null;
		Connection connection = dbConnection.getConnection();
		String sql = "Select COUNT(room.room_type_id) AS count from room WHERE room.is_deleted=0 AND room.room_type_id="
				+ id;
		try {
			countQuery = connection.prepareStatement(sql);
			rs = countQuery.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(countQuery);
			dbConnection.releaseResource(rs);
		}
		return count;
	}
}