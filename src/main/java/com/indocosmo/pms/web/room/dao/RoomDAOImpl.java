package com.indocosmo.pms.web.room.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.floor.model.Floor;
import com.indocosmo.pms.web.room.model.Room;
import com.indocosmo.pms.web.room.model.RoomFeature;

@Repository
public class RoomDAOImpl implements RoomDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(RoomDAOImpl.class);

	/**
	 * Room save/update
	 * 
	 * @param room
	 *            Room model
	 * @param newRecord
	 *            boolean value to distinguish whether the record is new or old
	 * @return true when save/update is successful/false when saving is failed.
	 */
	// public boolean save(Room room, boolean newRecord) throws Exception {
	// boolean isSave = true;
	//
	// try {
	// Session session = sessionFactory.getCurrentSession();
	//
	// if(!newRecord) {
	// session.update(room);
	// } else {
	// session.save(room);
	// }
	// } catch(Exception e) {
	// isSave = false;
	// e.printStackTrace();
	// logger.error("Method : save ," + Throwables.getStackTraceAsString(e));
	// throw new CustomException();
	// }
	//
	// return isSave;
	// }

	public boolean save(Room room, boolean newRecord) throws Exception {
		
		boolean isSave = true;

		try {
			Session session = sessionFactory.getCurrentSession();

			if (room.getId() != 0) {
				session.update(room);
			} else{
				session.save(room);
			}
		} catch (Exception e) {
			isSave = false;
			e.printStackTrace();
			logger.error("Method : save ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return isSave;
	}

	/**
	 * Delete record form room table (soft deletion)
	 * 
	 * @param id
	 *            the id of record to be deleted(soft deletion)
	 * @return true when soft deletion is successful/otherwise false
	 */

	// m
	public boolean delete(int id) throws Exception {
		boolean isDeleted = true;
		int occStat = 0;
		String sql = "SELECT occ_status FROM room WHERE is_deleted=0 AND id = " + id;
		DbConnection dbConnection = new DbConnection();
		Connection connection = dbConnection.getConnection();
		ResultSet resultSet = null;
		PreparedStatement getOccuStatPs = null;
		try {
			getOccuStatPs = connection.prepareStatement(sql);
			resultSet = getOccuStatPs.executeQuery();
			if (resultSet.next()) {
				occStat = resultSet.getInt("occ_status");
			}
			if (occStat == 2) {
				Session session = sessionFactory.getCurrentSession();
				Room room = (Room) session.load(Room.class, id);
				room.setDeleted(true);
				session.update(room);
			} else {
				isDeleted = false;
			}
		} catch (Exception e) {
			isDeleted = false;
			e.printStackTrace();
			logger.error("Method : delete, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
		
		}

		return isDeleted;
	}

	// e
	// public boolean delete(int id) throws Exception {
	// boolean isDeleted = true;
	//
	// try {
	// Session session = sessionFactory.getCurrentSession();
	// Room room = (Room) session.load(Room.class, id);
	// room.setDeleted(true);
	// session.update(room);
	// } catch(Exception e) {
	// isDeleted = false;
	// e.printStackTrace();
	// logger.error("Method : delete, " + Throwables.getStackTraceAsString(e));
	// throw new CustomException();
	// }
	//
	// return isDeleted;
	// }

	/**
	 * Fetch record from room table corresponding to the id
	 * 
	 * @param id
	 * @return return the Room model corresponding to the id
	 */
	public Room getRecord(int id) throws Exception {
		Room room = null;

		try {
			/*
			 * Session session = sessionFactory.getCurrentSession(); room = (Room)
			 * session.get(Room.class, id);
			 */

			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("FROM Room WHERE is_deleted=0 AND number =" + id);

			List<Object> roomList = query.list();
			Iterator<Object> it = roomList.iterator();
			while (it.hasNext()) {
				room = (Room) it.next();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getRecord, " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return room;
	}

	/**
	 * department table access for department list
	 * 
	 * @param startLimit
	 * @param endLimit
	 * @param advanceSearchMap
	 * @param sortVal
	 * @param simpleSearchMap
	 * @return list of records from department table
	 */
	@SuppressWarnings("unchecked")
	public List<Room> list(int startLimit, int endLimit, Map<String, String> advanceSearchMap, String sortVal,
			Map<String, String> simpleSearchMap) throws Exception {
		List<Room> roomList = null;

		try {
			Session session = sessionFactory.getCurrentSession();
			String advanceSearc = "";
			String simpleSearch = "";

			for (Map.Entry<String, String> searchC : advanceSearchMap.entrySet()) {
				if (searchC.getKey().equals("room_type_id")) {
					advanceSearc += "and " + searchC.getKey() + "=" + searchC.getValue();
				} else {
					advanceSearc += "and " + searchC.getKey() + " like '%" + searchC.getValue() + "%'";
				}
			}

			/*
			 * for form simple search
			 */
			for (Map.Entry<String, String> simpleSearchVal : simpleSearchMap.entrySet()) {
				if (simpleSearch.equals("") || simpleSearch == null) {
					simpleSearch += " and (" + simpleSearchVal.getKey() + " like '%" + simpleSearchVal.getValue()
							+ "%'";
				} else {
					simpleSearch += " OR " + simpleSearchVal.getKey() + " like '%" + simpleSearchVal.getValue() + "%'";
				}
			}

			if (!simpleSearch.equals("")) {
				simpleSearch += ")";
			}

			if (sortVal == null) {
				sortVal = "number asc";
			}

			String query = "select * from  (select * from room where  is_deleted='" + false + "' " + advanceSearc
					+ simpleSearch + " limit " + startLimit + "," + endLimit + ") qry order by " + sortVal;

			Query qry = session.createSQLQuery(query).addEntity(Room.class);
			roomList = qry.list();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list, " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return roomList;
	}

	/**
	 * Total count from room table
	 * 
	 * @param searchContent
	 * @param simpleSearchMap
	 * @return total count from room table
	 */
	public int getCount(Map<String, String> searchContent, Map<String, String> simpleSearchMap) throws Exception {
		int count = 0;
		String searchCriteria = "";
		String simpleSearch = "";

		if (!searchContent.isEmpty()) {
			for (Map.Entry<String, String> mapValue : searchContent.entrySet()) {
				searchCriteria += " and " + mapValue.getKey() + " like '%" + mapValue.getValue() + "%'";
			}
		}

		for (Map.Entry<String, String> simpleSearchVal : simpleSearchMap.entrySet()) {
			if (simpleSearch.equals("") || simpleSearch == null) {
				simpleSearch += " and (" + simpleSearchVal.getKey() + " like '%" + simpleSearchVal.getValue() + "%'";
			} else {
				simpleSearch += " OR " + simpleSearchVal.getKey() + " like '%" + simpleSearchVal.getValue() + "%'";
			}
		}

		if (!simpleSearch.equals("")) {
			simpleSearch += ")";
		}

		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "select count(*) from Room where deleted='" + false + "'" + searchCriteria + simpleSearch;
			count = Integer.parseInt(session.createQuery(hql).list().get(0).toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getCount, " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return count;
	}

	/**
	 * Check whether the room number is existing or not.
	 * 
	 * @param roomNo
	 * @return false if the room number not exist/else true.
	 */
	public boolean roomNumExist(String roomNo) throws Exception {
		boolean numberExist = false;

		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Room.class);
			criteria.add(Restrictions.eq("number", roomNo));
			criteria.add(Restrictions.eq("deleted", false));

			@SuppressWarnings("rawtypes")
			List listOfData = criteria.list();

			if (!listOfData.isEmpty()) {
				numberExist = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : roomNoExist, " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return numberExist;
	}

	/**
	 * Get all record from room_features table
	 * 
	 * @return List containing RoomFeatures
	 */
	@SuppressWarnings("unchecked")
	public List<RoomFeature> getRoomFeatures() throws Exception {
		List<RoomFeature> roomFeatures = null;

		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from RoomFeature where deleted=0");
			roomFeatures = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getRoomFeatures, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return roomFeatures;
	}

	public List<RoomFeature> getRoomFeatures(String features) throws Exception {
		List<RoomFeature> roomFeatures = null;

		try {
			Query query = sessionFactory.getCurrentSession()
					.createQuery("from RoomFeature where deleted=0 and id in (" + features + ")");
			roomFeatures = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getRoomFeatures, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return roomFeatures;
	}

	/**
	 * Check whether the room feature already exist or not
	 * 
	 * @param feature
	 * @return true if the feature exist. Else false
	 */
	public boolean featureExist(RoomFeature roomFeature) throws Exception {
		boolean featureExist = false;

		try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RoomFeature.class);
			criteria.add(Restrictions.eq("feature", roomFeature.getFeature()));
			criteria.add(Restrictions.eq("deleted", false));

			@SuppressWarnings("rawtypes")
			List listOfData = criteria.list();

			if (!listOfData.isEmpty()) {
				RoomFeature rmFeature = (RoomFeature) listOfData.get(0);

				if (roomFeature.getId() != 0 && rmFeature.getId() == roomFeature.getId()) {
					featureExist = false;
				} else {
					featureExist = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : featureExist, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return featureExist;
	}

	/**
	 * Save new Room Feature to room_feature table
	 * 
	 * @param roomFeature
	 * @return true if saved successfully.Else false.
	 */
	public boolean addFeature(RoomFeature roomFeature) throws Exception {
		boolean saved = true;

		try {
			if (roomFeature.getId() == 0) {
				sessionFactory.getCurrentSession().save(roomFeature);
			} else {
				sessionFactory.getCurrentSession().update(roomFeature);
			}
		} catch (Exception e) {
			saved = false;
			logger.error("Method : addFeature, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return saved;
	}

	public boolean deleteFeature(String id) throws Exception {

		try {
			String hql = "update RoomFeature set deleted=1 where id=" + id;
			sessionFactory.getCurrentSession().createQuery(hql).executeUpdate();
		} catch (Exception e) {
			logger.error("Method : deleteFeature, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Floor> getFloor() throws Exception {
		List<Floor> floor = null;
		try {
			floor = sessionFactory.getCurrentSession().createQuery("from Floor where is_deleted=0").list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return floor;

	}

	@Override
	public Room getRecord(String id) throws Exception {

		Room room = null;

		try {
			/*
			 * Session session = sessionFactory.getCurrentSession(); room = (Room)
			 * session.get(Room.class, id);
			 */

			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("FROM Room WHERE is_deleted=0 AND number ='"+id+"'");

			List<Object> roomList = query.list();
			Iterator<Object> it = roomList.iterator();
			while (it.hasNext()) {
				room = (Room) it.next();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getRecord, " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return room;
		}

}