package com.indocosmo.pms.web.ota.dao.reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.poi.hslf.record.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.reservation.OTAReservation;

@Repository
public class OTAReservationDaoImpl {
	 
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
	
	public static int save(OTAReservation reservation){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("insert into otareservation(id,locationid,bookedby,salutation,firstname,gender,address,city,state,zipcode,phone,mobile,fax,email,registrationno,source,ischannelbooking,isdeleted,uniquereservationid,country) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");  
	        ps.setInt(1,reservation.getId());  
	        ps.setString(2,reservation.getLocationid());  
	        ps.setString(3,reservation.getBookedby());  
	        ps.setString(4,reservation.getSalutation());  
	        ps.setString(5,reservation.getFirstname());  
	        ps.setString(6,reservation.getGender());  
	        ps.setString(7,reservation.getAddress());  
	        ps.setString(8,reservation.getCity());  
	        ps.setString(9,reservation.getState());  
	        ps.setString(10,reservation.getZipcode());  
	        ps.setString(11,reservation.getPhone());  
	        ps.setString(12,reservation.getMobile());  
	        ps.setString(13,reservation.getFax());  
	        ps.setString(14,reservation.getEmail());  
	        ps.setString(15,reservation.getRegistrationno());  
	        ps.setString(16,reservation.getSource());  
	        ps.setString(17,reservation.getIschannelbooking());  
	        ps.setInt(18,reservation.getIsdeleted());  
	        ps.setInt(19,reservation.getUniquereservationid());
	        ps.setString(20,reservation.getCountry());
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  
	
	public static int update(OTAReservation reservation){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("update otareservation set id=?,locationid=?,bookedby=?,salutation=?,firstname=?,gender=?,address=?,city=?,state=?,zipcode=?,phone=?,mobile=?,fax=?,email=?,registrationno=?,source=?,ischannelbooking=?,isdeleted=?,country=? where uniquereservationid=?");  
	        ps.setInt(1,reservation.getId());
	        ps.setString(2,reservation.getLocationid());  
	        ps.setString(3,reservation.getBookedby());  
	        ps.setString(4,reservation.getSalutation());  
	        ps.setString(5,reservation.getFirstname());  
	        ps.setString(6,reservation.getGender());  
	        ps.setString(7,reservation.getAddress());  
	        ps.setString(8,reservation.getCity());  
	        ps.setString(9,reservation.getState());  
	        ps.setString(10,reservation.getZipcode());  
	        ps.setString(11,reservation.getPhone());  
	        ps.setString(12,reservation.getMobile());  
	        ps.setString(13,reservation.getFax());  
	        ps.setString(14,reservation.getEmail());  
	        ps.setString(15,reservation.getRegistrationno());  
	        ps.setString(16,reservation.getSource());  
	        ps.setString(17,reservation.getIschannelbooking());  
	        ps.setInt(18,reservation.getIsdeleted());  
	        ps.setString(19,reservation.getCountry());
	        ps.setInt(20,reservation.getUniquereservationid());
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  
	
	public static int delete(OTAReservation reservation){  
		 int status=0;  
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("update otareservation set id=?,locationid=?,bookedby=?,salutation=?,firstname=?,gender=?,address=?,city=?,state=?,zipcode=?,phone=?,mobile=?,fax=?,email=?,registrationno=?,source=?,ischannelbooking=?,isdeleted=?,country=? where uniquereservationid=?");  
		        ps.setInt(1,reservation.getId());
		        ps.setString(2,reservation.getLocationid());  
		        ps.setString(3,reservation.getBookedby());  
		        ps.setString(4,reservation.getSalutation());  
		        ps.setString(5,reservation.getFirstname());  
		        ps.setString(6,reservation.getGender());  
		        ps.setString(7,reservation.getAddress());  
		        ps.setString(8,reservation.getCity());  
		        ps.setString(9,reservation.getState());  
		        ps.setString(10,reservation.getZipcode());  
		        ps.setString(11,reservation.getPhone());  
		        ps.setString(12,reservation.getMobile());  
		        ps.setString(13,reservation.getFax());  
		        ps.setString(14,reservation.getEmail());  
		        ps.setString(15,reservation.getRegistrationno());  
		        ps.setString(16,reservation.getSource());  
		        ps.setString(17,reservation.getIschannelbooking());  
		        ps.setInt(18,1);  
		        ps.setString(19,reservation.getCountry());  
		        ps.setInt(20,reservation.getUniquereservationid());
		        status=ps.executeUpdate();  
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
	}  
	
	
	public static int deleteAll(Integer size){  
		 int status=0;  
		 size++ ;
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otareservation where id<?");  
		        ps.setInt(1,size);
		        status=ps.executeUpdate();    
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
	}  
	
	public static int deleteById(OTAReservation reservation){  
		 int status=0;  
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otareservation where uniquereservationid=?");  
		        ps.setInt(1,reservation.getUniquereservationid());  
		        status=ps.executeUpdate();  
		    }catch(Exception e){System.out.println(e);}  
		  
		    return status;  
	}  
	
	
	public static List<OTAReservation> getAllRecords(){  
	    List<OTAReservation> otareservationList=new ArrayList<OTAReservation>();  
	      
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otareservation");  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTAReservation otareservation = new OTAReservation();  
	        	otareservation.setId(rs.getInt("id"));  
	        	otareservation.setLocationid(rs.getString("locationid")); 
	        	otareservation.setBookedby(rs.getString("bookedby"));  
	        	otareservation.setSalutation(rs.getString("salutation"));  
	        	otareservation.setFirstname(rs.getString("firstname"));  
	        	otareservation.setGender(rs.getString("gender"));  
	        	otareservation.setAddress(rs.getString("address"));  
	        	otareservation.setCity(rs.getString("city"));  
	        	otareservation.setState(rs.getString("state"));  
	        	otareservation.setCountry(rs.getString("country"));  
	        	otareservation.setZipcode(rs.getString("zipcode"));  
	        	otareservation.setPhone(rs.getString("phone"));  
	        	otareservation.setMobile(rs.getString("mobile"));  
	        	otareservation.setFax(rs.getString("fax"));  
	        	otareservation.setEmail(rs.getString("email"));  
	        	otareservation.setRegistrationno(rs.getString("registrationno"));  
	        	otareservation.setSource(rs.getString("source"));  
	        	otareservation.setIschannelbooking(rs.getString("ischannelbooking"));  
	        	otareservation.setIsdeleted(rs.getInt("isdeleted"));  
	        	otareservation.setUniquereservationid(rs.getInt("uniquereservationid"));
	        	
	        	otareservationList.add(otareservation);  
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otareservationList;  
	}  
	
	
	public static List<OTAReservation> getRecordById(int uniquereservationid){  
		List<OTAReservation> otareservationList = new ArrayList<OTAReservation>();
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otareservation where uniquereservationid=?");  
	        ps.setInt(1,uniquereservationid);  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTAReservation otareservation = new OTAReservation();  
	        	otareservation.setId(rs.getInt("id"));  
	        	otareservation.setLocationid(rs.getString("locationid")); 
	        	otareservation.setBookedby(rs.getString("bookedby"));  
	        	otareservation.setSalutation(rs.getString("salutation"));  
	        	otareservation.setFirstname(rs.getString("firstname"));  
	        	otareservation.setGender(rs.getString("gender"));  
	        	otareservation.setAddress(rs.getString("address"));  
	        	otareservation.setCity(rs.getString("city"));  
	        	otareservation.setState(rs.getString("state"));  
	        	otareservation.setCountry(rs.getString("country"));  
	        	otareservation.setZipcode(rs.getString("zipcode"));  
	        	otareservation.setPhone(rs.getString("phone"));  
	        	otareservation.setMobile(rs.getString("mobile"));  
	        	otareservation.setFax(rs.getString("fax"));  
	        	otareservation.setEmail(rs.getString("email"));  
	        	otareservation.setRegistrationno(rs.getString("registrationno"));  
	        	otareservation.setSource(rs.getString("source"));  
	        	otareservation.setIschannelbooking(rs.getString("ischannelbooking"));  
	        	otareservation.setIsdeleted(rs.getInt("isdeleted"));  
	        	otareservation.setUniquereservationid(rs.getInt("uniquereservationid"));
	        	
	        	otareservationList.add(otareservation);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otareservationList;  
	}  
	

}
