package com.indocosmo.pms.web.ota.dao.housekeeping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Repository;

import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.housekeeping.OTACheckinguestlist;
import com.indocosmo.pms.web.ota.entity.reservation.OTATaxDeatil;

@Repository
public class OTACheckinguestListDaoImpl {
	
	
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
	
	public static int save(OTACheckinguestlist otacheckinguestlist){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("insert into otacheckinguestlist(id,hotel_code,reservationno,guestname,email,address,room,roomtype,ratetype,bookingdate,checkindate,checkoutdate,businesssource,market,travelagent,company,tavoucherno,adult,child,housekeepingremarks,bookingstatus) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");  
	        ps.setInt(1,otacheckinguestlist.getId());  
	        ps.setString(2,otacheckinguestlist.getHotel_code());  
	        ps.setString(3,otacheckinguestlist.getReservationno());  
	        ps.setString(4,otacheckinguestlist.getGuestname());  
	        ps.setString(5,otacheckinguestlist.getEmail()); 
	        ps.setString(6,otacheckinguestlist.getAddress()); 
	        ps.setString(7,otacheckinguestlist.getRoom());  
	        ps.setString(8,otacheckinguestlist.getRoomtype());  
	        ps.setString(9,otacheckinguestlist.getRatetype());  
	        ps.setString(10,otacheckinguestlist.getBookingdate());  
	        ps.setString(11,otacheckinguestlist.getCheckindate()); 
	        ps.setString(12,otacheckinguestlist.getCheckoutdate()); 
	        ps.setString(13,otacheckinguestlist.getBusinesssource());  
	        ps.setString(14,otacheckinguestlist.getMarket());  
	        ps.setString(15,otacheckinguestlist.getTravelagent());  
	        ps.setString(16,otacheckinguestlist.getCompany());  
	        ps.setString(17,otacheckinguestlist.getTavoucherno()); 
	        ps.setString(18,otacheckinguestlist.getAdult()); 
	        ps.setString(19,otacheckinguestlist.getChild());  
	        ps.setString(20,otacheckinguestlist.getHousekeepingremarks()); 
	        ps.setString(21,otacheckinguestlist.getBookingstatus()); 
	        
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  
	
	
	public static int update(OTACheckinguestlist otacheckinguestlist){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("update otacheckinguestlist set id=?,hotel_code=?,guestname=?,email=?,address=?,room=?,roomtype=?,ratetype=?,bookingdate=?,checkindate=?,checkoutdate=?,businesssource=?,market=?,travelagent=?,company=?,tavoucherno=?,adult=?,child=?,housekeepingremarks=?,bookingstatus=? where reservationno=?");  
	        ps.setInt(1,otacheckinguestlist.getId());  
	        ps.setString(2,otacheckinguestlist.getHotel_code());  
	        ps.setString(3,otacheckinguestlist.getGuestname());  
	        ps.setString(4,otacheckinguestlist.getEmail()); 
	        ps.setString(5,otacheckinguestlist.getAddress()); 
	        ps.setString(6,otacheckinguestlist.getRoom());  
	        ps.setString(7,otacheckinguestlist.getRoomtype());  
	        ps.setString(8,otacheckinguestlist.getRatetype());  
	        ps.setString(9,otacheckinguestlist.getBookingdate());  
	        ps.setString(10,otacheckinguestlist.getCheckindate()); 
	        ps.setString(11,otacheckinguestlist.getCheckoutdate()); 
	        ps.setString(12,otacheckinguestlist.getBusinesssource());  
	        ps.setString(13,otacheckinguestlist.getMarket());  
	        ps.setString(14,otacheckinguestlist.getTravelagent());  
	        ps.setString(15,otacheckinguestlist.getCompany());  
	        ps.setString(16,otacheckinguestlist.getTavoucherno()); 
	        ps.setString(17,otacheckinguestlist.getAdult()); 
	        ps.setString(18,otacheckinguestlist.getChild());  
	        ps.setString(19,otacheckinguestlist.getHousekeepingremarks()); 
	        ps.setString(20,otacheckinguestlist.getBookingstatus()); 
	        ps.setString(21,otacheckinguestlist.getReservationno());  
	        
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  
	
	public static int delete(OTACheckinguestlist otacheckinguestlist){  
		 int status=0;  
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("update otacheckinguestlist set id=?,hotel_code=?,guestname=?,email=?,address=?,room=?,roomtype=?,ratetype=?,bookingdate=?,checkindate=?,checkoutdate=?,businesssource=?,market=?,travelagent=?,company=?,tavoucherno=?,adult=?,child=?,housekeepingremarks=?,bookingstatus=? where reservationno=?");  
		        ps.setInt(1,otacheckinguestlist.getId());  
		        ps.setString(2,otacheckinguestlist.getHotel_code());  
		        ps.setString(3,otacheckinguestlist.getGuestname());  
		        ps.setString(4,otacheckinguestlist.getEmail()); 
		        ps.setString(5,otacheckinguestlist.getAddress()); 
		        ps.setString(6,otacheckinguestlist.getRoom());  
		        ps.setString(7,otacheckinguestlist.getRoomtype());  
		        ps.setString(8,otacheckinguestlist.getRatetype());  
		        ps.setString(9,otacheckinguestlist.getBookingdate());  
		        ps.setString(10,otacheckinguestlist.getCheckindate()); 
		        ps.setString(11,otacheckinguestlist.getCheckoutdate()); 
		        ps.setString(12,otacheckinguestlist.getBusinesssource());  
		        ps.setString(13,otacheckinguestlist.getMarket());  
		        ps.setString(14,otacheckinguestlist.getTravelagent());  
		        ps.setString(15,otacheckinguestlist.getCompany());  
		        ps.setString(16,otacheckinguestlist.getTavoucherno()); 
		        ps.setString(17,otacheckinguestlist.getAdult()); 
		        ps.setString(18,otacheckinguestlist.getChild());  
		        ps.setString(19,otacheckinguestlist.getHousekeepingremarks()); 
		        ps.setString(20,otacheckinguestlist.getBookingstatus()); 
		        ps.setString(21,otacheckinguestlist.getReservationno());  
		        
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
	}  
	
	public static int deleteAll(Integer size){  
		 int status=0;  
		 size++ ;
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otacheckinguestlist where id<?");  
		        ps.setInt(1,size);
		        status=ps.executeUpdate();  ;  
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
	}  
	
	public static int deleteById(OTACheckinguestlist otacheckinguestlist){  
		 int status=0;  
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otacheckinguestlist where reservationno=?");  
		        ps.setString(1,otacheckinguestlist.getReservationno());  
		        status=ps.executeUpdate();  
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
	}  
	
	
	public static List<OTACheckinguestlist> getAllRecords(){  
	    List<OTACheckinguestlist> otacheckinguestlist =new ArrayList<OTACheckinguestlist>();  
	      
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otacheckinguestlist");  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTACheckinguestlist otacheckinguest = new OTACheckinguestlist();  
	        	otacheckinguest.setId(rs.getInt("id"));  
	        	otacheckinguest.setHotel_code(rs.getString("hotel_code"));  
	        	otacheckinguest.setReservationno(rs.getString("reservationno"));  
	        	otacheckinguest.setGuestname(rs.getString("guestname")); 
	        	otacheckinguest.setEmail(rs.getString("email")); 
	        	otacheckinguest.setAddress(rs.getString("address")); 
	        	otacheckinguest.setRoom(rs.getString("room"));  
	        	otacheckinguest.setRoomtype(rs.getString("roomtype"));  
	        	otacheckinguest.setRatetype(rs.getString("ratetype"));  
	        	otacheckinguest.setBookingdate(rs.getString("bookingdate"));  
	        	otacheckinguest.setCheckindate(rs.getString("checkindate")); 
	        	otacheckinguest.setCheckoutdate(rs.getString("checkoutdate")); 
	 	        otacheckinguest.setBusinesssource(rs.getString("businesssource"));  
	 	        otacheckinguest.setMarket(rs.getString("market"));  
	 	        otacheckinguest.setTravelagent(rs.getString("travelagent"));  
	 	        otacheckinguest.setCompany(rs.getString("company"));  
	 	        otacheckinguest.setTavoucherno(rs.getString("tavoucherno")); 
	 	        otacheckinguest.setAdult(rs.getString("adult")); 
	 	        otacheckinguest.setChild(rs.getString("child"));  
	 	        otacheckinguest.setHousekeepingremarks(rs.getString("housekeepingremarks")); 
	 	        otacheckinguest.setBookingstatus(rs.getString("bookingstatus")); 
	  
	        	otacheckinguestlist.add(otacheckinguest);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otacheckinguestlist;  
	}  
	
	
	public static List<OTACheckinguestlist> getRecordById(int reservationid){  
		 List<OTACheckinguestlist> otacheckinguestlist =new ArrayList<OTACheckinguestlist>();  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otataxdetail where reservationid=?");  
	        ps.setInt(1,reservationid);  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTACheckinguestlist otacheckinguest = new OTACheckinguestlist();  
	        	otacheckinguest.setId(rs.getInt("id"));  
	        	otacheckinguest.setHotel_code(rs.getString("hotel_code"));  
	        	otacheckinguest.setReservationno(rs.getString("reservationno"));  
	        	otacheckinguest.setGuestname(rs.getString("guestname")); 
	        	otacheckinguest.setEmail(rs.getString("email")); 
	        	otacheckinguest.setAddress(rs.getString("address")); 
	        	otacheckinguest.setRoom(rs.getString("room"));  
	        	otacheckinguest.setRoomtype(rs.getString("roomtype"));  
	        	otacheckinguest.setRatetype(rs.getString("ratetype"));  
	        	otacheckinguest.setBookingdate(rs.getString("bookingdate"));  
	        	otacheckinguest.setCheckindate(rs.getString("checkindate")); 
	        	otacheckinguest.setCheckoutdate(rs.getString("checkoutdate")); 
	 	        otacheckinguest.setBusinesssource(rs.getString("businesssource"));  
	 	        otacheckinguest.setMarket(rs.getString("market"));  
	 	        otacheckinguest.setTravelagent(rs.getString("travelagent"));  
	 	        otacheckinguest.setCompany(rs.getString("company"));  
	 	        otacheckinguest.setTavoucherno(rs.getString("tavoucherno")); 
	 	        otacheckinguest.setAdult(rs.getString("adult")); 
	 	        otacheckinguest.setChild(rs.getString("child"));  
	 	        otacheckinguest.setHousekeepingremarks(rs.getString("housekeepingremarks")); 
	 	        otacheckinguest.setBookingstatus(rs.getString("bookingstatus")); 
	  
	        	otacheckinguestlist.add(otacheckinguest);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otacheckinguestlist;  
	}  
	
	
	
	
}
