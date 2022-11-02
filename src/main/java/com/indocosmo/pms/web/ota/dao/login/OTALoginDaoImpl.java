package com.indocosmo.pms.web.ota.dao.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Repository;

import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;

@Repository
public class OTALoginDaoImpl {

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
	
	public static int save(HotelInfo hotel){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("insert into hoteldetails(id,hotelcode,hotelname,authkey,isdeleted,username) values(?,?,?,?,?,?)");  
	        ps.setInt(1,hotel.getId());  
	        ps.setString(2,hotel.getHotelcode());  
	        ps.setString(3,hotel.getHotelname());  
	        ps.setString(4,hotel.getAuthkey());  
	        ps.setInt(5,hotel.getIsdeleted()); 
	        ps.setString(6,hotel.getUsername()); 
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  
	
	public static int update(HotelInfo hotel){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("update hoteldetails set hotelcode=?,hotelname=?,authkey=?,isdeleted=?,username=? where id=?");  
	        ps.setString(1,hotel.getHotelcode());  
	        ps.setString(2,hotel.getHotelname());  
	        ps.setString(3,hotel.getAuthkey());  
	        ps.setInt(4,hotel.getIsdeleted());
	        ps.setString(5,hotel.getUsername()); 
	        ps.setInt(6,hotel.getId());  
	        status=ps.executeUpdate();  ;  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  
	
	public static int delete(HotelInfo hotel){  
		  int status=0;  
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("update hoteldetails set hotelcode=?,hotelname=?,authkey=?,isdeleted=?,username=? where id=?");  
		        ps.setString(1,hotel.getHotelcode());  
		        ps.setString(2,hotel.getHotelname());  
		        ps.setString(3,hotel.getAuthkey());  
		        ps.setInt(4,1);
		        ps.setString(5,hotel.getUsername()); 
		        ps.setInt(6,hotel.getId());  
		        status=ps.executeUpdate();  ;  
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
	}  
	
	
	public static List<HotelInfo> getAllRecords(){  
	    List<HotelInfo> list=new ArrayList<HotelInfo>();  
	      
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from hoteldetails");  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	HotelInfo hotel = new HotelInfo();  
	        	hotel.setId(rs.getInt("id"));  
	        	hotel.setHotelcode(rs.getString("hotelcode"));  
	        	hotel.setHotelname(rs.getString("hotelname"));  
	        	hotel.setAuthkey(rs.getString("authkey"));  
	        	hotel.setIsdeleted(rs.getInt("isdeleted"));   
	        	hotel.setUsername(rs.getString("username"));   
	            list.add(hotel);  
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return list;  
	}  
	
	
	public static HotelInfo getRecordById(int id){  
		HotelInfo hotel=null;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from hoteldetails where id=?");  
	        ps.setInt(1,id);  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	hotel=new HotelInfo();  
	        	hotel.setId(rs.getInt("id"));  
	        	hotel.setHotelcode(rs.getString("hotelcode"));  
	        	hotel.setHotelname(rs.getString("hotelname"));  
	        	hotel.setAuthkey(rs.getString("authkey"));  
	        	hotel.setIsdeleted(rs.getInt("isdeleted")); 
	        	hotel.setUsername(rs.getString("username"));  
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return hotel;  
	}  
	
	
	public static List<HotelInfo> getRecordByAuthKey(String authkey){  
		List<HotelInfo> hotellist= new ArrayList<HotelInfo>();  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from hoteldetails where authkey=? and isdeleted=0");  
	        ps.setString(1,authkey);  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	HotelInfo hotel=new HotelInfo();  
	        	hotel.setId(rs.getInt("id"));  
	        	hotel.setHotelcode(rs.getString("hotelcode"));  
	        	hotel.setHotelname(rs.getString("hotelname"));  
	        	hotel.setAuthkey(rs.getString("authkey"));  
	        	hotel.setIsdeleted(rs.getInt("isdeleted")); 
	        	hotel.setUsername(rs.getString("username"));  
	        	hotellist.add(hotel);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return hotellist;  
	}  

}
