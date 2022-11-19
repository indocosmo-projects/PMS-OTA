package com.indocosmo.pms.web.ota.dao.room;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Repository;

import com.indocosmo.pms.web.ota.entity.room.OTARoomRatePlans;

@Repository
public class OTARoomRatePlansDao {
	

	public static Connection getConnection(){  
	    Connection con=null;  
		ResourceBundle dataBaseProperty = ResourceBundle.getBundle("database");
		String url = dataBaseProperty.getString("jdbc.url");
		String userName = dataBaseProperty.getString("jdbc.username");
		String passWord = dataBaseProperty.getString("jdbc.password");
		String driverClass = dataBaseProperty.getString("jdbc.driverClassName");
	    try{  
	        Class.forName(driverClass);  
	        con=DriverManager.getConnection(url,userName,passWord);  
	    }catch(Exception e){System.out.println(e);}  
	    return con;  
	}  
	
	public static int save(OTARoomRatePlans otaroomrateplans){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("insert into otaroomrateplans(id,rateplanid,roomname,roomtypeid,roomtype,ratetypeid,ratetype) values(?,?,?,?,?,?,?)");  
	        ps.setInt(1,otaroomrateplans.getId());  
	        ps.setString(2,otaroomrateplans.getRateplanid());  
	        ps.setString(3,otaroomrateplans.getRoomname());  
	        ps.setString(4,otaroomrateplans.getRoomtypeid());  
	        ps.setString(5,otaroomrateplans.getRoomtype());
	        ps.setString(6,otaroomrateplans.getRatetypeid());
	        ps.setString(7,otaroomrateplans.getRatetype());
	        
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  
	
	public static int update(OTARoomRatePlans otaroomrateplans){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("update otaroomrateplans set id=?,roomname=?,roomtypeid=?,roomtype=?,ratetypeid=?,ratetype=? where rateplanid=?");  
	        ps.setInt(1,otaroomrateplans.getId());  
	        ps.setString(2,otaroomrateplans.getRoomname());  
	        ps.setString(3,otaroomrateplans.getRoomtypeid());  
	        ps.setString(4,otaroomrateplans.getRoomtype());
	        ps.setString(5,otaroomrateplans.getRatetypeid());
	        ps.setString(6,otaroomrateplans.getRatetype());
	        ps.setString(7,otaroomrateplans.getRateplanid());  
	        
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  

	
	
	public static int deleteAll(Integer size){  
		 int status=0;  
		 size++ ;
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otaroomrateplans where id<?");  
		        ps.setInt(1,size);
		        status=ps.executeUpdate();  ;  
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
	}  
	
	public static int deleteById(OTARoomRatePlans otaroomrateplans){  
		 int status=0;  
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otaroomrateplans where rateplanid=?");  
		        ps.setString(1,otaroomrateplans.getRateplanid());  
		        status=ps.executeUpdate();  
		    }catch(Exception e){System.out.println(e);}  
		  
		    return status;  
	}  
	
	
	public static List<OTARoomRatePlans> getAllRecords(){  
	    List<OTARoomRatePlans> otaroomrateplanslist =new ArrayList<OTARoomRatePlans>();  
	      
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otaroomrateplans");  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTARoomRatePlans otaroomrateplans = new OTARoomRatePlans();  
	        	otaroomrateplans.setId(rs.getInt("id"));  
	        	otaroomrateplans.setRateplanid(rs.getString("rateplanid")); 
	        	otaroomrateplans.setRoomname(rs.getString("roomname")); 
	        	otaroomrateplans.setRoomtypeid(rs.getString("roomtypeid"));  
	        	otaroomrateplans.setRoomtype(rs.getString("roomtype"));  
	        	otaroomrateplans.setRatetypeid(rs.getString("ratetypeid"));  
	        	otaroomrateplans.setRatetype(rs.getString("ratetype"));  
	  
	        	otaroomrateplanslist.add(otaroomrateplans);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otaroomrateplanslist;  
	}  
	
	
	public static List<OTARoomRatePlans> getRecordById(int reservationid){  
		 List<OTARoomRatePlans> otaroomrateplanslist =new ArrayList<OTARoomRatePlans>();   
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otaroomrateplans where rateplanid=?");  
	        ps.setInt(1,reservationid);  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTARoomRatePlans otaroomrateplans = new OTARoomRatePlans();  
	        	otaroomrateplans.setId(rs.getInt("id"));  
	        	otaroomrateplans.setRateplanid(rs.getString("rateplanid")); 
	        	otaroomrateplans.setRoomname(rs.getString("roomname")); 
	        	otaroomrateplans.setRoomtypeid(rs.getString("roomtypeid"));  
	        	otaroomrateplans.setRoomtype(rs.getString("roomtype"));  
	        	otaroomrateplans.setRatetypeid(rs.getString("ratetypeid"));  
	        	otaroomrateplans.setRatetype(rs.getString("ratetype"));  
	  
	        	otaroomrateplanslist.add(otaroomrateplans);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otaroomrateplanslist;  
	}  
	
	
}
