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
import com.indocosmo.pms.web.ota.entity.room.OTARoomRoomTypes;

@Repository
public class OTARoomRoomTypesDao {
	
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
	
	public static int save(OTARoomRoomTypes otaroomroomtypes){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("insert into otaroomroomtypes(id,roomtypesid,roomtypename,roomid,roomname) values(?,?,?,?,?)");  
	        ps.setInt(1,otaroomroomtypes.getId());  
	        ps.setString(2,otaroomroomtypes.getRoomtypesid());  
	        ps.setString(3,otaroomroomtypes.getRoomtypename());  
	        ps.setString(4,otaroomroomtypes.getRoomid());  
	        ps.setString(5,otaroomroomtypes.getRoomname());  
	        
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  
	
	public static int update(OTARoomRoomTypes otaroomroomtypes){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("update otaroomroomtypes set id=?,roomtypename=?,roomid=?,roomname=? where roomtypesid=?");  
	        ps.setInt(1,otaroomroomtypes.getId());  
	        ps.setString(2,otaroomroomtypes.getRoomtypename());  
	        ps.setString(3,otaroomroomtypes.getRoomid());  
	        ps.setString(4,otaroomroomtypes.getRoomname());  
	        ps.setString(5,otaroomroomtypes.getRoomtypesid());  
	     
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  

	
	
	public static int deleteAll(Integer size){  
		 int status=0;  
		 size++ ;
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otaroomroomtypes where id<?");  
		        ps.setInt(1,size);
		        status=ps.executeUpdate();  ;  
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
	}  
	
	public static int deleteById(OTARoomRoomTypes otaroomroomtypes){  
		 int status=0;  
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otaroomroomtypes where roomtypesid=?");  
		        ps.setString(1,otaroomroomtypes.getRoomtypesid());  
		        status=ps.executeUpdate();  
		    }catch(Exception e){System.out.println(e);}  
		  
		    return status;  
	}  
	
	
	public static List<OTARoomRoomTypes> getAllRecords(){  
	    List<OTARoomRoomTypes> otaroomroomtypeslist =new ArrayList<OTARoomRoomTypes>();  
	      
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otaroomroomtypes");  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTARoomRoomTypes otaroomroomtypes = new OTARoomRoomTypes();  
	        	otaroomroomtypes.setId(rs.getInt("id"));  
	        	otaroomroomtypes.setRoomtypesid(rs.getString("roomtypesid")); 
	        	otaroomroomtypes.setRoomtypename(rs.getString("roomtypename"));  
	        	otaroomroomtypes.setRoomid(rs.getString("roomid"));  
	        	otaroomroomtypes.setRoomname(rs.getString("roomname"));  
	  
	        	otaroomroomtypeslist.add(otaroomroomtypes);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otaroomroomtypeslist;  
	}  
	
	
	public static List<OTARoomRoomTypes> getRecordById(int roomtypesid){  
		 List<OTARoomRoomTypes> otaroomroomtypeslist =new ArrayList<OTARoomRoomTypes>();  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otaroomroomtypes where roomtypesid=?");  
	        ps.setInt(1,roomtypesid);  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTARoomRoomTypes otaroomroomtypes = new OTARoomRoomTypes();  
	        	otaroomroomtypes.setId(rs.getInt("id"));  
	        	otaroomroomtypes.setRoomtypesid(rs.getString("roomtypesid")); 
	        	otaroomroomtypes.setRoomtypename(rs.getString("roomtypename"));  
	        	otaroomroomtypes.setRoomid(rs.getString("roomid"));  
	        	otaroomroomtypes.setRoomname(rs.getString("roomname"));  
	  
	        	otaroomroomtypeslist.add(otaroomroomtypes);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otaroomroomtypeslist;  
	}  
	
	
}
