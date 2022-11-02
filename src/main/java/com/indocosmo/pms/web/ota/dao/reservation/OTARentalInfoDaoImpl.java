package com.indocosmo.pms.web.ota.dao.reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Repository;

import com.indocosmo.pms.web.ota.entity.reservation.OTARentalInfo;
import com.indocosmo.pms.web.ota.entity.reservation.OTAReservation;

@Repository
public class OTARentalInfoDaoImpl {
	
	
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
	
	public static int save(OTARentalInfo rentalInfo){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("insert into otarentalinfo(id,effectivedate,packagecode,packagename,roomtypecode,roomtypename,adult,child,rentpretax,rent,discount,reservationid) values(?,?,?,?,?,?,?,?,?,?,?,?)");  
	        ps.setInt(1,rentalInfo.getId());  
	        ps.setString(2,rentalInfo.getEffectivedate());  
	        ps.setString(3,rentalInfo.getPackagecode());  
	        ps.setString(4,rentalInfo.getPackagename());  
	        ps.setString(5,rentalInfo.getRoomtypecode());  
	        ps.setString(6,rentalInfo.getRoomtypename());  
	        ps.setString(7,rentalInfo.getAdult());  
	        ps.setString(8,rentalInfo.getChild());  
	        ps.setString(9,rentalInfo.getRentpretax());  
	        ps.setString(10,rentalInfo.getRent());  
	        ps.setString(11,rentalInfo.getDiscount());  
	        ps.setInt(12,rentalInfo.getReservationid());  
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  
	
	public static int update(OTARentalInfo rentalInfo){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("update otarentalinfo set id=?,effectivedate=?,packagecode=?,packagename=?,roomtypecode=?,roomtypename=?,adult=?,child=?,rentpretax=?,rent=?,discount=? where reservationid=?");  
	        ps.setInt(1,rentalInfo.getId());  
	        ps.setString(2,rentalInfo.getEffectivedate());  
	        ps.setString(3,rentalInfo.getPackagecode());  
	        ps.setString(4,rentalInfo.getPackagename());  
	        ps.setString(5,rentalInfo.getRoomtypecode());  
	        ps.setString(6,rentalInfo.getRoomtypename());  
	        ps.setString(7,rentalInfo.getAdult());  
	        ps.setString(8,rentalInfo.getChild());  
	        ps.setString(9,rentalInfo.getRentpretax());  
	        ps.setString(10,rentalInfo.getRent());  
	        ps.setString(11,rentalInfo.getDiscount());  
	        ps.setInt(12,rentalInfo.getReservationid());  
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  
	
	
	public static int deleteAll(Integer size){  
		 int status=0;  
		 size++ ;
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otarentalinfo where id<?");  
		        ps.setInt(1,size);
		        status=ps.executeUpdate();   
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
	}  
	
	public static int deleteById(OTARentalInfo rentalInfo){  
		 int status=0;  
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otarentalinfo where reservationid=?");  
		        ps.setInt(1,rentalInfo.getReservationid());  
		        status=ps.executeUpdate();  
		    }catch(Exception e){System.out.println(e);}  
		  
		    return status;  
	}  
	
	
	public static List<OTARentalInfo> getAllRecords(){  
	    List<OTARentalInfo> otarentalinfoList =new ArrayList<OTARentalInfo>();  
	      
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otarentalinfo");  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTARentalInfo otarentalinfo = new OTARentalInfo();  
	        	otarentalinfo.setId(rs.getInt("id"));  
	        	otarentalinfo.setEffectivedate(rs.getString("effectivedate")); 
	        	otarentalinfo.setPackagecode(rs.getString("packagecode"));  
	        	otarentalinfo.setPackagename(rs.getString("packagename"));  
	        	otarentalinfo.setRoomtypecode(rs.getString("roomtypecode"));  
	        	otarentalinfo.setRoomtypename(rs.getString("roomtypename"));  
	        	otarentalinfo.setAdult(rs.getString("adult"));  
	        	otarentalinfo.setChild(rs.getString("child"));  
	        	otarentalinfo.setRentpretax(rs.getString("rentpretax"));  
	        	otarentalinfo.setRent(rs.getString("rent"));  
	        	otarentalinfo.setDiscount(rs.getString("discount"));  
	        	otarentalinfo.setReservationid(rs.getInt("reservationid"));  
	        	
	        	otarentalinfoList.add(otarentalinfo);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otarentalinfoList;  
	}  
	
	
	public static List<OTARentalInfo> getRecordById(int reservationid){  
		List<OTARentalInfo> otarentalinfoList = new ArrayList<OTARentalInfo>();
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otarentalinfo where reservationid=?");  
	        ps.setInt(1,reservationid);  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTARentalInfo otarentalinfo = new OTARentalInfo();  
	        	otarentalinfo.setId(rs.getInt("id"));  
	        	otarentalinfo.setEffectivedate(rs.getString("effectivedate")); 
	        	otarentalinfo.setPackagecode(rs.getString("packagecode"));  
	        	otarentalinfo.setPackagename(rs.getString("packagename"));  
	        	otarentalinfo.setRoomtypecode(rs.getString("roomtypecode"));  
	        	otarentalinfo.setRoomtypename(rs.getString("roomtypename"));  
	        	otarentalinfo.setAdult(rs.getString("adult"));  
	        	otarentalinfo.setChild(rs.getString("child"));  
	        	otarentalinfo.setRentpretax(rs.getString("rentpretax"));  
	        	otarentalinfo.setRent(rs.getString("rent"));  
	        	otarentalinfo.setDiscount(rs.getString("discount"));  
	        	otarentalinfo.setReservationid(rs.getInt("reservationid"));  
	        	
	        	otarentalinfoList.add(otarentalinfo);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otarentalinfoList;  
	}  
	
	
	
}
