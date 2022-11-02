package com.indocosmo.pms.web.ota.dao.reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Repository;

import com.indocosmo.pms.web.ota.entity.reservation.OTACancelReservation;
import com.indocosmo.pms.web.ota.entity.reservation.OTARentalInfo;

@Repository
public class OTACancelReservationDaoImpl {
	
	
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
	
	public static int save(OTACancelReservation cancelReservation){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("insert into otacancelreservation(id,locationid,reservationid,status,canceldatetime,remark,voucherno) values(?,?,?,?,?,?,?)");  
	        ps.setInt(1,cancelReservation.getId());  
	        ps.setString(2,cancelReservation.getLocationid());  
	        ps.setInt(3,cancelReservation.getReservationid());  
	        ps.setString(4,cancelReservation.getStatus());  
	        ps.setString(5,cancelReservation.getCanceldatetime());  
	        ps.setString(6,cancelReservation.getRemark());  
	        ps.setString(7,cancelReservation.getVoucherno());  
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  
	
	public static int update(OTACancelReservation cancelReservation){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("update otacancelreservation set id=?,locationid=?,status=?,canceldatetime=?,remark=?,voucherno=? where reservationid=?");  
	        ps.setInt(1,cancelReservation.getId());  
	        ps.setString(2,cancelReservation.getLocationid());  
	        ps.setString(3,cancelReservation.getStatus());  
	        ps.setString(4,cancelReservation.getCanceldatetime());  
	        ps.setString(5,cancelReservation.getRemark());  
	        ps.setString(6,cancelReservation.getVoucherno());    
	        ps.setInt(7,cancelReservation.getReservationid());  
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  
	
	
	
	public static int deleteAll(Integer size){  
		 int status=0;  
		 size++ ;
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otacancelreservation where id<?");  
		        ps.setInt(1,size);
		        status=ps.executeUpdate(); 
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
	}  
	
	public static int deleteById(OTACancelReservation cancelReservation){  
		 int status=0;  
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otacancelreservation where reservationid=?");  
		        ps.setInt(1,cancelReservation.getReservationid());  
		        status=ps.executeUpdate();  
		    }catch(Exception e){System.out.println(e);}  
		  
		    return status;  
	}  
	
	
	public static List<OTACancelReservation> getAllRecords(){  
	    List<OTACancelReservation> OTACancelReservationList = new ArrayList<OTACancelReservation>();  
	      
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otacancelreservation");  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTACancelReservation otacancelreservation = new OTACancelReservation();  
	        	otacancelreservation.setId(rs.getInt("id"));  
	        	otacancelreservation.setLocationid(rs.getString("locationid")); 
	        	otacancelreservation.setReservationid(rs.getInt("reservationid"));  
	        	otacancelreservation.setStatus(rs.getString("status"));  
	        	otacancelreservation.setCanceldatetime(rs.getString("canceldatetime"));  
	        	otacancelreservation.setRemark(rs.getString("remark"));  
	        	otacancelreservation.setVoucherno(rs.getString("voucherno"));  
	        	
	        	OTACancelReservationList.add(otacancelreservation);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return OTACancelReservationList;  
	}  
	
	
	public static List<OTACancelReservation> getRecordById(int reservationid){  
		List<OTACancelReservation> OTACancelReservationList = new ArrayList<OTACancelReservation>();
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otacancelreservation where reservationid=?");  
	        ps.setInt(1,reservationid);  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTACancelReservation otacancelreservation = new OTACancelReservation();  
	        	otacancelreservation.setId(rs.getInt("id"));  
	        	otacancelreservation.setLocationid(rs.getString("locationid")); 
	        	otacancelreservation.setReservationid(rs.getInt("reservationid"));  
	        	otacancelreservation.setStatus(rs.getString("status"));  
	        	otacancelreservation.setCanceldatetime(rs.getString("canceldatetime"));  
	        	otacancelreservation.setRemark(rs.getString("remark"));  
	        	otacancelreservation.setVoucherno(rs.getString("voucherno"));  
	        	
	        	OTACancelReservationList.add(otacancelreservation);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return OTACancelReservationList;  
	}  
	
	
	
}
