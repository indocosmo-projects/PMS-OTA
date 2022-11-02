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
import com.indocosmo.pms.web.ota.entity.reservation.OTATaxDeatil;

@Repository
public class OTATaxDeatilDaoImpl {

	
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
	
	public static int save(OTATaxDeatil otataxdeatil){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("insert into otataxdetail(id,taxcode,taxname,taxamount,reservationid) values(?,?,?,?,?)");  
	        ps.setInt(1,otataxdeatil.getId());  
	        ps.setString(2,otataxdeatil.getTaxcode());  
	        ps.setString(3,otataxdeatil.getTaxname());  
	        ps.setString(4,otataxdeatil.getTaxamount());  
	        ps.setInt(5,otataxdeatil.getReservationid());  
	        
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  
	
	public static int update(OTATaxDeatil otataxdeatil){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("update otataxdetail set id=?,taxcode=?,taxname=?,taxamount=? where reservationid=?");  
	        ps.setInt(1,otataxdeatil.getId());  
	        ps.setString(2,otataxdeatil.getTaxcode());  
	        ps.setString(3,otataxdeatil.getTaxname());  
	        ps.setString(4,otataxdeatil.getTaxamount());  
	        ps.setInt(5,otataxdeatil.getReservationid());  
	     
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  

	
	
	public static int deleteAll(Integer size){  
		 int status=0;  
		 size++ ;
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otataxdetail where id<?");  
		        ps.setInt(1,size);
		        status=ps.executeUpdate();  ;  
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
	}  
	
	public static int deleteById(OTATaxDeatil otataxdeatil){  
		 int status=0;  
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otataxdetail where reservationid=?");  
		        ps.setInt(1,otataxdeatil.getReservationid());  
		        status=ps.executeUpdate();  
		    }catch(Exception e){System.out.println(e);}  
		  
		    return status;  
	}  
	
	
	public static List<OTATaxDeatil> getAllRecords(){  
	    List<OTATaxDeatil> otataxdeatillist =new ArrayList<OTATaxDeatil>();  
	      
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otataxdetail");  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTATaxDeatil otataxdeatil = new OTATaxDeatil();  
	        	otataxdeatil.setId(rs.getInt("id"));  
	        	otataxdeatil.setTaxcode(rs.getString("taxcode")); 
	        	otataxdeatil.setTaxname(rs.getString("taxname"));  
	        	otataxdeatil.setTaxamount(rs.getString("taxamount"));  
	        	otataxdeatil.setReservationid(rs.getInt("reservationid"));  
	  
	        	otataxdeatillist.add(otataxdeatil);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otataxdeatillist;  
	}  
	
	
	public static List<OTATaxDeatil> getRecordById(int reservationid){  
		 List<OTATaxDeatil> otataxdeatillist =new ArrayList<OTATaxDeatil>();  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otataxdetail where reservationid=?");  
	        ps.setInt(1,reservationid);  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTATaxDeatil otataxdeatil = new OTATaxDeatil();  
	        	otataxdeatil.setId(rs.getInt("id"));  
	        	otataxdeatil.setTaxcode(rs.getString("taxcode")); 
	        	otataxdeatil.setTaxname(rs.getString("taxname"));  
	        	otataxdeatil.setTaxamount(rs.getString("taxamount"));  
	        	otataxdeatil.setReservationid(rs.getInt("reservationid"));  
	  
	        	otataxdeatillist.add(otataxdeatil);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otataxdeatillist;  
	}  
	
	
	
}
