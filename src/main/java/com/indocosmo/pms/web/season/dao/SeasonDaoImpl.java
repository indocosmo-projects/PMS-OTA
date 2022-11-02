package com.indocosmo.pms.web.season.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.ReturningWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.season.model.Seasonhdr;

@Repository
public class SeasonDaoImpl implements SeasonDao {

	private static final Logger logger = LoggerFactory.getLogger(SeasonDaoImpl.class);

	DbConnection dbConnection = null;
	
	@Autowired
	private  SessionFactory sessionFactory; 

	/**
	 * Seasonhdr table access for currency list
	 * @param startLimit  
	 * @param endLimit
	 * @param advanceSearchMap
	 * @param sortVal
	 * @param simpleSearchMap
	 * @return list of records from currency table
	 */
	
	public SeasonDaoImpl(){
		dbConnection=new DbConnection();
	}
	
	@SuppressWarnings("unchecked")
	public List<Seasonhdr> list(int startLimit, int endLimit,Map<String,String> searchContent, String sortVal,Map<String, String>simpleSearchMap) {
		List<Seasonhdr> seasonList = null;

		try {
			Session session=sessionFactory.getCurrentSession();
			String criteria="";

			for(Map.Entry<String, String> searchC:searchContent.entrySet()){
				criteria+="and "+searchC.getKey()+" like '%"+searchC.getValue()+"%'";
			}

			/*
			 * for form simple search
			 */
			String simpleSearch="";
			for(Map.Entry<String,String> simpleSearchVal:simpleSearchMap.entrySet()){
				if( simpleSearch.equals("") || simpleSearch == null){
					simpleSearch+=" and ("+simpleSearchVal.getKey()+" like '%"+simpleSearchVal.getValue()+"%'"; 
				}else{
					simpleSearch+=" OR "+simpleSearchVal.getKey()+" like '%"+simpleSearchVal.getValue()+"%'";
				}
			}

			if( !simpleSearch.equals("")){
				simpleSearch+=")";
			}

			if(sortVal == null) {
				sortVal="id asc";
			}

			String query = "select * from  (select * from season_hdr where  is_deleted='" 
					+ false+ "' " + criteria + simpleSearch + "  limit " + startLimit + "," + endLimit + ") qry order by " + sortVal;
			Query qry = session.createSQLQuery(query).addEntity(Seasonhdr.class);
			seasonList = qry.list();
		}catch(Exception ex){
			logger.error("Method : list  , "+ Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
		}

		return seasonList;
	}

	/**
	 * Total count from season table
	 * @param searchContent
	 * @param simpleSearchMap
	 */
	public int totalRecord(Map<String, String> searchContent ,Map<String,String>simpleSearchMap) {
		int count=0;
		String searchCriteria="";

		if(!searchContent.isEmpty()){
			for(Map.Entry<String,String> mapValue :searchContent.entrySet()){
				searchCriteria+=" and "+mapValue.getKey()+" like '%"+mapValue.getValue()+"%'";
			}
		}

		String simpleSearch="";

		if(!simpleSearchMap.isEmpty()){
			for(Map.Entry<String,String> simpleSearchVal:simpleSearchMap.entrySet()){
				if( simpleSearch.equals("") || simpleSearch == null){
					simpleSearch+=" and "+simpleSearchVal.getKey()+" like '%"+simpleSearchVal.getValue()+"%'"; 
				}else{
					simpleSearch+=" or "+simpleSearchVal.getKey()+" like '%"+simpleSearchVal.getValue()+"%'";
				}
			}

		}

		try{
			Session session=sessionFactory.getCurrentSession();
			String hql="select count(*) from Seasonhdr where deleted='"+false+"'"+searchCriteria + simpleSearch;
			count = Integer.parseInt(session.createQuery(hql).list().get(0).toString());
		}catch(Exception ex){
			logger.error("Method : totalRecord  , "+ Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
		}

		return count;
	}

	public boolean newSeason(Seasonhdr ism) {
		return false;
	}

	/**
	 * Seasonhdr save
	 * @param seasonhdr model
	 */
	public boolean save(Seasonhdr seasonHdr) throws Exception {
		boolean isSave=true;
		Session session=null;

		try{
			session=sessionFactory.getCurrentSession();

			if(seasonHdr.getId()!=0){				
				Query query = session.createQuery("delete from Seasondtl where season_hdr_id= :hdrId");
				query.setParameter("hdrId", seasonHdr.getId());

				int result = query.executeUpdate();

				if (result > 0) {
					session.update(seasonHdr);
				}
			}else{
				session.save(seasonHdr);
			}		    
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("Method : seasonSave  , "+ Throwables.getStackTraceAsString(ex));
			isSave=false;
			return isSave;
		}

		return isSave;
	}

	/**
	 * Database access to get single record from season table
	 * @param seasonId 
	 */
	public Seasonhdr getRecord(int seasonId) throws Exception  {
		Session session=null;
		Seasonhdr seasonHdr=null;

		try{
			session=sessionFactory.getCurrentSession();
			seasonHdr=(Seasonhdr) session.get(Seasonhdr.class, seasonId);
		}catch(Exception ex){			
			ex.printStackTrace();
			logger.error("Method : getSeason  , "+ Throwables.getStackTraceAsString(ex));
		}

		return seasonHdr;
	}

	/**
	 * Delete record from season table (soft deletion)
	 * @param seasonIds
	 */
	public boolean delete(int seasonIds) throws Exception {
		Session session = null;
		Seasonhdr season=null;

		try {
			session = sessionFactory.getCurrentSession();
			season = (Seasonhdr) session.load(Seasonhdr.class,seasonIds);
			season.setDeleted(true);
			session.update(season);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : deleteSeason  , "+ Throwables.getStackTraceAsString(ex));
			return false;
		}

		return true;
	}

	/**
	 * Total count from season table
	 * @param advanceSearchMap
	 * @param simpleSearchMap
	 */
	public int getCount(Map<String, String> searchContent, Map<String, String> simpleSearchMap) {
		int count=0;
		String searchCriteria="";

		if(!searchContent.isEmpty()){
			for(Map.Entry<String,String> mapValue :searchContent.entrySet()){
				searchCriteria+=" and "+mapValue.getKey()+" like '%"+mapValue.getValue()+"%'";
			}
		}

		String simpleSearch="";

		for(Map.Entry<String,String> simpleSearchVal:simpleSearchMap.entrySet()){
			if( simpleSearch.equals("") || simpleSearch == null){
				simpleSearch+=" and ("+simpleSearchVal.getKey()+" like '%"+simpleSearchVal.getValue()+"%'"; 
			}else{
				simpleSearch+=" OR "+simpleSearchVal.getKey()+" like '%"+simpleSearchVal.getValue()+"%'";
			}
		}

		if( !simpleSearch.equals("")){
			simpleSearch+=")";
		}

		if( !simpleSearch.equals("")){
			simpleSearch+=")";
		}

		try{
			Session session=sessionFactory.getCurrentSession();
			String hql="select count(*) from Seasonhdr where deleted='"+false+"'"+searchCriteria + simpleSearch;
			count = Integer.parseInt(session.createQuery(hql).list().get(0).toString());

		}catch(Exception ex){
			logger.error("Method : getCount  , "+ Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
		}

		return count;
	}

	/**
	 * server validation for date overlapping
	 * @param startDate
	 * @param endDate
	 */
	public boolean getDates(final String startDate,  final String endDate, final int id) {
		Session session=null;
		session=sessionFactory.getCurrentSession();
		Boolean isValid = session.doReturningWork(new ReturningWork<Boolean>() {
			public Boolean execute(java.sql.Connection connection){
				CallableStatement call;
				Boolean isValid=false;

				try {
					call = connection.prepareCall("{ ? = call ValidateSeason(?,?,?) }");
					call.registerOutParameter( 1, Types.INTEGER); 			   
					call.setString(2, startDate);			   
					call.setString(3,endDate);
					call.setInt(4,id);
					call.execute();
					//System.out.println(call.toString());
					isValid =(call.getInt(1)==0);
				} catch (SQLException ex) {
					logger.error("Method : getDate  , "+ Throwables.getStackTraceAsString(ex));
					ex.printStackTrace();
				}

				return 	isValid;
			}
		});

		return isValid;		
	}	

	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<String>> getSeasonNamesWithId() {
		ArrayList<ArrayList<String>> seasons = new ArrayList<ArrayList<String>>(); 

		try {
			seasons = (ArrayList<ArrayList<String>>) sessionFactory.getCurrentSession()
					.createQuery("select id, name from Seasonhdr where deleted=0").list();
		} catch(Exception e) {
			e.printStackTrace();
		}

		return seasons;
	}

	/**
	 * Database access to get record list from season table 
	 * @return map containing season types' id and name
	 */
	public List<Seasonhdr> seasonList() throws Exception{
		List<Seasonhdr> seasonList = null;

		try {
			Session session = sessionFactory.getCurrentSession();
			String query = "select * from season_hdr where is_deleted='false'";
			Query qry = session.createSQLQuery(query).addEntity(Seasonhdr.class);
			seasonList = qry.list();
		} catch(Exception ex){
			logger.error("Method : codeExist " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		}

		return seasonList;
	}

	/**
	 * code Exist checking in Season table
	 * @param code
	 * @return true when code is exist / false when code is not exist
	 */
	public boolean codeExist(String code) {
		boolean isExist = false;

		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Seasonhdr.class);
			criteria.add(Restrictions.eq("code", code));
			criteria.add(Restrictions.eq("deleted",false));
			@SuppressWarnings("rawtypes")
			List listOfData=criteria.list();

			if(!listOfData.isEmpty()) {
				isExist=true;
			}
		} catch(Exception ex) {
			logger.error("Method : codeExist " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			throw new CustomException();
		}

		return isExist;
	}

	@SuppressWarnings("unchecked")
	public List<Seasonhdr> getSeasonDtl(String filter_query) {
		List<Seasonhdr>   seasonList = new ArrayList<Seasonhdr>();
		Connection        connection = dbConnection.getConnection();
		Statement         statement  = null;
		ResultSet         resultSet  = null;
		try {
			 
			
			
			String query = "select season_hdr.code,season_hdr.name, season_dtl.start_day ,season_dtl.end_day,season_dtl.cal_month,season_hdr.color_code FROM season_dtl JOIN season_hdr ON season_hdr.id= season_dtl.season_hdr_id WHERE season_hdr.is_deleted=0 "+filter_query;
 
 	     
 	    statement=connection.createStatement();
		resultSet=statement.executeQuery(query);
		
		
		
		while(resultSet.next()) {
			Seasonhdr seasonhdr= new Seasonhdr();
		//	Seasondtl seasondetails = new Seasondtl();
			 //dis.setCode(resultSet.getString("amount"));
			 
			seasonhdr.setColorCode(resultSet.getString("color_code"));
			seasonhdr.setStartDay(resultSet.getInt("start_day"));
			seasonhdr.setEndDay(resultSet.getInt("end_day"));
			seasonhdr.setStartMonth(resultSet.getInt("cal_month"));
			seasonhdr.setCode(resultSet.getString("code"));
			seasonhdr.setName(resultSet.getString("name"));
			
	 
			 seasonList.add(seasonhdr);
		}
		
		
			
		}catch(Exception ex){
			logger.error("Method : list  , "+ Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
		}

		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(statement);
		}
		return seasonList;
	}

	
	
}