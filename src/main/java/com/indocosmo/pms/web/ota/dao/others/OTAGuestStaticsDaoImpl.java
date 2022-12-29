package com.indocosmo.pms.web.ota.dao.others;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Repository;

import com.indocosmo.pms.web.ota.entity.others.OTAGuestStatics;
import com.indocosmo.pms.web.ota.entity.reservation.OTARentalInfo;
import com.indocosmo.pms.web.ota.entity.reservation.OTATaxDeatil;

@Repository
public class OTAGuestStaticsDaoImpl {

	
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
	
	public static int save(OTAGuestStatics otagueststatics){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("insert into otagueststatics(id,guestname,guestemail,totalnumberofstays,firststay,firstreservationno,firstfoliono,laststay,lastreservationno,lastfoliono,nextstay,nextreservationno,nextfoliono,lifetimespending) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");  
	        ps.setInt(1,otagueststatics.getId());  
	        ps.setString(2,otagueststatics.getGuestname());  
	        ps.setString(3,otagueststatics.getGuestemail());  
	        ps.setString(4,otagueststatics.getTotalnumberofstays());  
	        ps.setString(5,otagueststatics.getFirststay());  
	        ps.setString(6,otagueststatics.getFirstreservationno());  
	        ps.setString(7,otagueststatics.getFirstfoliono());  
	        ps.setString(8,otagueststatics.getLaststay());  
	        ps.setString(9,otagueststatics.getLastreservationno());
	        ps.setString(10,otagueststatics.getLastfoliono());
	        ps.setString(11,otagueststatics.getNextstay());
	        ps.setString(12,otagueststatics.getNextreservationno());
	        ps.setString(13,otagueststatics.getNextfoliono());
	        ps.setString(14,otagueststatics.getLifetimespending());

	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  
	
	public static int update(OTAGuestStatics otagueststatics){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("update otagueststatics set guestname=?,guestemail=?,totalnumberofstays=?,firststay=?,firstreservationno=?,firstfoliono=?,laststay=?,lastreservationno=?,lastfoliono=?,nextstay=?,nextreservationno=?,nextfoliono=?,lifetimespending? where id=?");  
	        ps.setString(1,otagueststatics.getGuestname());  
	        ps.setString(2,otagueststatics.getGuestemail());  
	        ps.setString(3,otagueststatics.getTotalnumberofstays());  
	        ps.setString(4,otagueststatics.getFirststay());  
	        ps.setString(5,otagueststatics.getFirstreservationno());  
	        ps.setString(6,otagueststatics.getFirstfoliono());  
	        ps.setString(7,otagueststatics.getLaststay());  
	        ps.setString(8,otagueststatics.getLastreservationno());
	        ps.setString(9,otagueststatics.getLastfoliono());
	        ps.setString(10,otagueststatics.getNextstay());
	        ps.setString(11,otagueststatics.getNextreservationno());
	        ps.setString(12,otagueststatics.getNextfoliono());
	        ps.setString(13,otagueststatics.getLifetimespending());
	        ps.setInt(14,otagueststatics.getId());  
	     
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  

	
	
	public static int deleteAll(Integer size){  
		 int status=0;  
		 size++ ;
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otagueststatics where id<?");  
		        ps.setInt(1,size);
		        status=ps.executeUpdate();  ;  
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
	}  
	
	public static int deleteById(OTAGuestStatics otagueststatics){  
		 int status=0;  
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otagueststatics where id=?");  
		        ps.setInt(1,otagueststatics.getId());  
		        status=ps.executeUpdate();  
		    }catch(Exception e){System.out.println(e);}  
		  
		    return status;  
	}  
	
	
	public static List<OTAGuestStatics> getAllRecords(){  
	    List<OTAGuestStatics> otagueststaticslist = new ArrayList<OTAGuestStatics>();  
	      
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otagueststatics");  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTAGuestStatics otagueststatics = new OTAGuestStatics();  
	        	otagueststatics.setId(rs.getInt("id"));  
	        	otagueststatics.setGuestname(rs.getString("guestname"));  
	        	otagueststatics.setGuestemail(rs.getString("guestemail"));  
	        	otagueststatics.setTotalnumberofstays(rs.getString("totalnumberofstays"));   
	        	otagueststatics.setFirststay(rs.getString("firststay"));  
	        	otagueststatics.setFirstreservationno(rs.getString("firstreservationno"));  
	        	otagueststatics.setFirstfoliono(rs.getString("firstfoliono"));  
	        	otagueststatics.setLaststay(rs.getString("laststay"));  
	        	otagueststatics.setLastreservationno(rs.getString("lastreservationno"));   
	        	otagueststatics.setLastfoliono(rs.getString("lastfoliono"));  
	        	otagueststatics.setNextstay(rs.getString("nextstay"));  
	        	otagueststatics.setNextreservationno(rs.getString("nextreservationno"));  
	        	otagueststatics.setNextfoliono(rs.getString("nextfoliono"));  
	        	otagueststatics.setLifetimespending(rs.getString("lifetimespending"));   
	  
	        	otagueststaticslist.add(otagueststatics);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otagueststaticslist;  
	}  
	
	
	public static List<OTAGuestStatics> getRecordById(int id){  
		  List<OTAGuestStatics> otagueststaticslist =new ArrayList<OTAGuestStatics>();   
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otagueststatics where id=?");  
	        ps.setInt(1,id);  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTAGuestStatics otagueststatics = new OTAGuestStatics();  
	        	otagueststatics.setId(rs.getInt("id"));  
	        	otagueststatics.setGuestname(rs.getString("guestname"));  
	        	otagueststatics.setGuestemail(rs.getString("guestemail"));  
	        	otagueststatics.setTotalnumberofstays(rs.getString("totalnumberofstays"));   
	        	otagueststatics.setFirststay(rs.getString("firststay"));  
	        	otagueststatics.setFirstreservationno(rs.getString("firstreservationno"));  
	        	otagueststatics.setFirstfoliono(rs.getString("firstfoliono"));  
	        	otagueststatics.setLaststay(rs.getString("laststay"));  
	        	otagueststatics.setLastreservationno(rs.getString("lastreservationno"));   
	        	otagueststatics.setLastfoliono(rs.getString("lastfoliono"));  
	        	otagueststatics.setNextstay(rs.getString("nextstay"));  
	        	otagueststatics.setNextreservationno(rs.getString("nextreservationno"));  
	        	otagueststatics.setNextfoliono(rs.getString("nextfoliono"));  
	        	otagueststatics.setLifetimespending(rs.getString("lifetimespending"));   
	  
	        	otagueststaticslist.add(otagueststatics);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otagueststaticslist;  
	}  
	
	
	
}
