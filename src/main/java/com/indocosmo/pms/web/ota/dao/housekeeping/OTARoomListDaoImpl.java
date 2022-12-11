package com.indocosmo.pms.web.ota.dao.housekeeping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Repository;
import com.indocosmo.pms.web.ota.entity.housekeeping.OTARoomList;


@Repository
public class OTARoomListDaoImpl {

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
	
	public static int save(OTARoomList otaroomlist){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("insert into otaroomlist(id,hotel_code,roomid,unitid,roomname,roomtypeid,roomtypename,isblocked,hkstatus,hkremarks,roomstatus) values(?,?,?,?,?,?,?,?,?,?,?)");  
	        ps.setInt(1,otaroomlist.getId());  
	        ps.setString(2,otaroomlist.getHotel_code());  
	        ps.setString(3,otaroomlist.getRoomid());  
	        ps.setString(4,otaroomlist.getUnitid());  
	        ps.setString(5,otaroomlist.getRoomname());  
	        ps.setString(6,otaroomlist.getRoomtypeid());  
	        ps.setString(7,otaroomlist.getRoomtypename());  
	        ps.setString(8,otaroomlist.getIsblocked());  
	        ps.setString(9,otaroomlist.getHkstatus());  
	        ps.setString(10,otaroomlist.getHkremarks()); 
	        ps.setString(11,otaroomlist.getRoomstatus()); 
	        
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  

	
	public static int update(OTARoomList otaroomlist){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("update otaroomlist set id=?,hotel_code=?,unitid=?,roomname=?,roomtypeid=?,roomtypename=?,isblocked=?,hkstatus=?,hkremarks=?,roomstatus=? where roomid=?");  
	        ps.setInt(1,otaroomlist.getId());  
	        ps.setString(2,otaroomlist.getHotel_code());  
	        ps.setString(3,otaroomlist.getRoomid());  
	        ps.setString(4,otaroomlist.getUnitid());  
	        ps.setString(5,otaroomlist.getRoomname());  
	        ps.setString(6,otaroomlist.getRoomtypeid());  
	        ps.setString(7,otaroomlist.getRoomtypename());  
	        ps.setString(8,otaroomlist.getIsblocked());  
	        ps.setString(9,otaroomlist.getHkstatus());  
	        ps.setString(10,otaroomlist.getHkremarks()); 
	        ps.setString(11,otaroomlist.getRoomstatus()); 
	     
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  

	
	
	public static int deleteAll(Integer size){  
		 int status=0;  
		 size++ ;
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otaroomlist where id<?");  
		        ps.setInt(1,size);
		        status=ps.executeUpdate();  ;  
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
	}  
	
	public static int deleteById(OTARoomList otaroomlist){  
		 int status=0;  
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otaroomlist where roomid=?");  
		        ps.setString(1,otaroomlist.getRoomid());  
		        status=ps.executeUpdate();  
		    }catch(Exception e){System.out.println(e);}  
		  
		    return status;  
	}  
	
	
	public static List<OTARoomList> getAllRecords(){  
	    List<OTARoomList> otaroomlist =new ArrayList<OTARoomList>();  
	      
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otaroomlist");  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTARoomList otaroom = new OTARoomList();  
	        	otaroom.setId(rs.getInt("id"));  
	        	otaroom.setHotel_code(rs.getString("hotel_code")); 
	        	otaroom.setRoomid(rs.getString("roomid"));  
	        	otaroom.setUnitid(rs.getString("unitid"));  
	        	otaroom.setRoomname(rs.getString("roomname"));
	        	otaroom.setRoomtypeid(rs.getString("roomtypeid"));  
	        	otaroom.setRoomtypename(rs.getString("roomtypename")); 
	        	otaroom.setIsblocked(rs.getString("isblocked"));  
	        	otaroom.setHkstatus(rs.getString("hkstatus"));  
	        	otaroom.setHkremarks(rs.getString("hkremarks"));
	        	otaroom.setRoomstatus(rs.getString("roomstatus"));      
	 	  
	        	otaroomlist.add(otaroom);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otaroomlist;  
	}  
	
	
	public static List<OTARoomList> getRecordById(String roomid){  
		  List<OTARoomList> otaroomlist =new ArrayList<OTARoomList>(); 
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otataxdetail where roomid=?");  
	        ps.setString(1,roomid);  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	          	OTARoomList otaroom = new OTARoomList();  
	        	otaroom.setId(rs.getInt("id"));  
	        	otaroom.setHotel_code(rs.getString("hotel_code")); 
	        	otaroom.setRoomid(rs.getString("roomid"));  
	        	otaroom.setUnitid(rs.getString("unitid"));  
	        	otaroom.setRoomname(rs.getString("roomname"));
	        	otaroom.setRoomtypeid(rs.getString("roomtypeid"));  
	        	otaroom.setRoomtypename(rs.getString("roomtypename")); 
	        	otaroom.setIsblocked(rs.getString("isblocked"));  
	        	otaroom.setHkstatus(rs.getString("hkstatus"));  
	        	otaroom.setHkremarks(rs.getString("hkremarks"));
	        	otaroom.setRoomstatus(rs.getString("roomstatus"));      
	 	  
	        	otaroomlist.add(otaroom);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otaroomlist;  
	}  
	

	
}
