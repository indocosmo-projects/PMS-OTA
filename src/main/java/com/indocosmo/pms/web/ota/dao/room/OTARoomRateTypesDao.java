package com.indocosmo.pms.web.ota.dao.room;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Repository;

import com.indocosmo.pms.web.ota.entity.reservation.OTATaxDeatil;
import com.indocosmo.pms.web.ota.entity.room.OTARoomRateTypes;

@Repository
public class OTARoomRateTypesDao {
	
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
	
	public static int save(OTARoomRateTypes otaroomratetypes){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("insert into otaroomratetypes(id,roomtypesid,roomtypesname) values(?,?,?)");  
	        ps.setInt(1,otaroomratetypes.getId());  
	        ps.setString(2,otaroomratetypes.getRoomtypesid());  
	        ps.setString(3,otaroomratetypes.getRoomtypesname());  
	        
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  
	
	public static int update(OTARoomRateTypes otaroomratetypes){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("update otaroomratetypes set id=?,roomtypesname=? where roomtypesid=?");  
	        ps.setInt(1,otaroomratetypes.getId());  
	        ps.setString(2,otaroomratetypes.getRoomtypesname()); 
	        ps.setString(3,otaroomratetypes.getRoomtypesid());  
	     
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  

	
	
	public static int deleteAll(Integer size){  
		 int status=0;  
		 size++ ;
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otaroomratetypes where id<?");  
		        ps.setInt(1,size);
		        
		        status=ps.executeUpdate();  ;  
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
	}  
	
	public static int deleteById(OTARoomRateTypes otaroomratetypes){  
		 int status=0;  
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otaroomratetypes where roomtypesid=?");  
		        ps.setString(1,otaroomratetypes.getRoomtypesid());  
		        
		        status=ps.executeUpdate();  
		    }catch(Exception e){System.out.println(e);}  
		  
		    return status;  
	}  
	
	
	public static List<OTARoomRateTypes> getAllRecords(){  
	    List<OTARoomRateTypes> otaroomratetypeslist =new ArrayList<OTARoomRateTypes>();  
	      
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otaroomratetypes");  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTARoomRateTypes otaroomratetypes = new OTARoomRateTypes();  
	        	otaroomratetypes.setId(rs.getInt("id"));  
	        	otaroomratetypes.setRoomtypesid(rs.getString("roomtypesid")); 
	        	otaroomratetypes.setRoomtypesname(rs.getString("roomtypesname"));  
	  
	        	otaroomratetypeslist.add(otaroomratetypes);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otaroomratetypeslist;  
	}  
	
	
	public static List<OTARoomRateTypes> getRecordById(int reservationid){  
		 List<OTARoomRateTypes> otaroomratetypeslist =new ArrayList<OTARoomRateTypes>();  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otaroomratetypes where roomtypesid=?");  
	        ps.setInt(1,reservationid);  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTARoomRateTypes otaroomratetypes = new OTARoomRateTypes();  
	        	otaroomratetypes.setId(rs.getInt("id"));  
	        	otaroomratetypes.setRoomtypesid(rs.getString("roomtypesid")); 
	        	otaroomratetypes.setRoomtypesname(rs.getString("roomtypesname"));  
	  
	        	otaroomratetypeslist.add(otaroomratetypes);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otaroomratetypeslist;  
	}  
	
	
	
}
