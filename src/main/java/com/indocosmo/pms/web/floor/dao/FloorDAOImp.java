package com.indocosmo.pms.web.floor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
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
import com.indocosmo.pms.web.floor.dao.FloorDAO;
import com.indocosmo.pms.web.floor.model.Floor;

@Repository 
public class FloorDAOImp implements FloorDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(FloorDAOImp.class);

	/**
	 * Floor save
	 * @param Floor model
	 * @return true when saving is success/ false when saving is not success
	 */
	public boolean save(Floor floor) throws Exception {
		boolean isSave = true;
		
		try {
			Session session = sessionFactory.getCurrentSession();
			
			if(floor.getId() != 0) {
				session.update(floor);
			} else {
				session.save(floor);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			logger.error("Method : save  , " + Throwables.getStackTraceAsString(ex));
			isSave = false;
			throw new CustomException();
		}

		return isSave;
	}

	/**
	 * Delete record from currency table (soft deletion)
	 * @param id
	 * @return true when saving is success/ false when saving is not success
	 */
	public boolean delete(int id) throws Exception {
		boolean isDeleted = true;
		
		try {
			Session session = sessionFactory.getCurrentSession();
			Floor floor = (Floor) session.load(Floor.class, id);
			floor.setDeleted(true);
			session.update(floor);
		} catch(Exception ex) {
			ex.printStackTrace();
			logger.error("Method : delete  , " + Throwables.getStackTraceAsString(ex));
			isDeleted = false;
			throw new CustomException();
		}

		return isDeleted;
	}

	/**
	 * Database access to get single record from floor table
	 * @param Id 
	 * @return floor model
	 */
	public Floor getRecord(int id) throws Exception {
		Floor floor = null;
		
		try {
			Session session = sessionFactory.getCurrentSession();
			floor = (Floor) session.get(Floor.class, id);
		} catch(Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getRecord  , " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return floor;
	}

	/**
	 * floor table access for floor list
	 * @param startLimit  
	 * @param endLimit
	 * @param advanceSearchMap
	 * @param sortVal
	 * @param simpleSearchMap
	 * @return list of records from floor table
	 */
	@SuppressWarnings("unchecked")
	public List<Floor> list(int startLimit, int endLimit, Map<String, String> advanceSearchMap,
			String sortVal, Map<String, String> simpleSearchMap) throws Exception {	
		List<Floor> floorList = null;
		
		try {
			Session session = sessionFactory.getCurrentSession();
			String advanceSearc = "";

			for(Map.Entry<String, String> searchC : advanceSearchMap.entrySet()) {
				advanceSearc += "and " + searchC.getKey() + " like '%" + searchC.getValue() + "%'";
			}

			/*
			 * for form simple search
			 */
			String simpleSearch = "";
			
			for(Map.Entry<String, String> simpleSearchVal : simpleSearchMap.entrySet()) {
				if(simpleSearch.equals("") || simpleSearch == null) {
					simpleSearch += " and (" + simpleSearchVal.getKey() + " like '%" + simpleSearchVal.getValue() + "%'"; 
				} else {
					simpleSearch += " OR " + simpleSearchVal.getKey() + " like '%" + simpleSearchVal.getValue() + "%'";
				}
			}

			if(!simpleSearch.equals("")) {
				simpleSearch += ")";
			}

			if(sortVal == null) {
				sortVal = "id asc";
			}

			String query = "select * from  (select * from floor where  is_deleted='"
					+ false + "' " + advanceSearc + simpleSearch + "  limit "
					+ startLimit + "," + endLimit + ") qry order by " + sortVal;
			
			Query qry = session.createSQLQuery(query).addEntity(Floor.class);
			floorList = qry.list();
		} catch(Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list  , " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return floorList;
	}

	/**
	 * Total count from floor table
	 * @param advanceSearchMap
	 * @param simpleSearchMap
	 * @return total count records from floor table
	 */
	public int getCount(Map<String, String> advanceSearchMap, Map<String, String> simpleSearchMap) throws Exception {
		int count = 0;
		String advanceSearch = "";
		
		if(!advanceSearchMap.isEmpty()) {
			for(Map.Entry<String,String> mapValue : advanceSearchMap.entrySet()) {
				advanceSearch += " and " + mapValue.getKey() + " like '%" + mapValue.getValue() + "%'";
			}
		}

		String simpleSearch = "";
		
		for(Map.Entry<String, String> simpleSearchVal:simpleSearchMap.entrySet()) {
			if( simpleSearch.equals("") || simpleSearch == null) {
				simpleSearch += " and (" + simpleSearchVal.getKey() + " like '%" + simpleSearchVal.getValue() + "%'"; 
			} else {
				simpleSearch += " OR " + simpleSearchVal.getKey() + " like '%" + simpleSearchVal.getValue() + "%'";
			}
		}

		if(!simpleSearch.equals("")) {
			simpleSearch += ")";
		}

		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "select count(*) from Floor where isDeleted='" + false + "'" + advanceSearch + simpleSearch;
			count = Integer.parseInt(session.createQuery(hql).list().get(0).toString());
		} catch(Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getCount  , " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		return count;
	}

	/**
	 * code Exist checking in Floor table
	 * @param code
	 * @return true when code is exist / false when code is not exist
	 */
	public boolean codeExist(String code) throws Exception {
		boolean isCodeExist = false;
		
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Floor.class);
			criteria.add(Restrictions.eq("code", code));
			criteria.add(Restrictions.eq("isDeleted", false));
			
			@SuppressWarnings("rawtypes")
			List listOfData = criteria.list();
			
			if(!listOfData.isEmpty()) {
				isCodeExist = true;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			logger.error("Method : codeExist  , " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}
		
		return isCodeExist;
	}

	/**
	 * returns a map contains <id, name>
	 */
	public Map<Integer, String> getNameIdMap() throws Exception {
		LinkedHashMap<Integer, String> nameIdMap = new LinkedHashMap<Integer, String>();

		try {
			@SuppressWarnings("unchecked")
			List<Floor> floors = sessionFactory.getCurrentSession()
				.createQuery("from Floor where isDeleted=0").list();

			for (Floor floor : floors) {
				nameIdMap.put(floor.getId(), floor.getName());
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("Method : getNameIdMap, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return nameIdMap;
	}

	@Override
	public Floor canDelete(int id) {
		Floor floor = null;
		try {
			floor = (Floor) sessionFactory.getCurrentSession().get(Floor.class, id);
					if (floor != null) {
					//	System.out.println("In if candelete");
						Query query = sessionFactory.getCurrentSession().createQuery
								("SELECT COUNT(*) from Room where floorId =" + Integer.toString(id) + " and deleted=0\"");
						@SuppressWarnings("unchecked")
						List<Long> noList = query.list();
						if(noList.size() > 0) {
								floor.setNoOfRooms(noList.get(0));
							//	System.out.println("Count : "+floor.getNoOfRooms());
						}

					}
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getRecord, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return floor;
	}

	@Override
	public int getCountOfFloor(int id) {
		int count=0;
		DbConnection dbConnection=null;
		ResultSet rs=null;
		dbConnection=new DbConnection();
		PreparedStatement countQuery=null;
		Connection connection=dbConnection.getConnection();
		//String sql1 = "Select COUNT(room.room_type_id) AS count from room WHERE room.is_deleted=0 AND room.room_type_id="+id;
		String sql = "SELECT COUNT(room.floor_id) as COUNT FROM room WHERE room.is_deleted = 0 AND floor_id = "+id;
		try {
			countQuery=connection.prepareStatement(sql);
			rs=countQuery.executeQuery();
			while(rs.next()){
				count=rs.getInt("count");
			}
			//System.out.println("Count :: "+count);
		}catch(Exception e){
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(dbConnection);
			dbConnection.releaseResource(countQuery);
			dbConnection.releaseResource(rs);
		}
		return count;	
	}
}