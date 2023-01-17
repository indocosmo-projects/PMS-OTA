package com.indocosmo.pms.web.ota.dao.finance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Repository;

import com.indocosmo.pms.web.ota.entity.finance.OTAExtras;
import com.indocosmo.pms.web.ota.entity.reservation.OTATaxDeatil;

@Repository
public class OTAExtrasDaoImpl {
	

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
	
	public static int save(OTAExtras otaextras){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("insert into otaextras(id,extrachargeid,shortcode,charge,description,rate,chargerule,postingrule,validfrom,validto,ischargealways,applyon_rateplan,applyon_special) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");  
	        ps.setInt(1,otaextras.getId());  
	        ps.setString(2,otaextras.getExtrachargeid());  
	        ps.setString(3,otaextras.getShortcode());  
	        ps.setString(4,otaextras.getCharge());  
	        ps.setString(5,otaextras.getDescription());  
	        ps.setString(6,otaextras.getRate());  
	        ps.setString(7,otaextras.getChargerule());  
	        ps.setString(8,otaextras.getPostingrule());  
	        ps.setString(9,otaextras.getValidfrom()); 
	        ps.setString(10,otaextras.getValidto()); 
	        ps.setString(11,otaextras.getIschargealways()); 
	        ps.setString(12,otaextras.getApplyon_rateplan()); 
	        ps.setString(13,otaextras.getApplyon_special()); 
	        
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  
	
	public static int update(OTAExtras otaextras){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("update otaextras set id,shortcode,charge,description,rate,chargerule,postingrule,validfrom,validto,ischargealways,applyon_rateplan,applyon_special where extrachargeid=?");  
	        ps.setInt(1,otaextras.getId());  
	        ps.setString(2,otaextras.getShortcode());  
	        ps.setString(3,otaextras.getCharge());  
	        ps.setString(4,otaextras.getDescription());  
	        ps.setString(5,otaextras.getRate());  
	        ps.setString(6,otaextras.getChargerule());  
	        ps.setString(7,otaextras.getPostingrule());  
	        ps.setString(8,otaextras.getValidfrom()); 
	        ps.setString(9,otaextras.getValidto()); 
	        ps.setString(10,otaextras.getIschargealways()); 
	        ps.setString(11,otaextras.getApplyon_rateplan()); 
	        ps.setString(12,otaextras.getApplyon_special()); 
	        ps.setString(13,otaextras.getExtrachargeid());  
	     
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  

	
	
	public static int deleteAll(Integer size){  
		 int status=0;  
		 size++ ;
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otaextras where id<?");  
		        ps.setInt(1,size);
		        status=ps.executeUpdate();  ;  
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
	}  
	
	public static int deleteById(OTAExtras otaextras){  
		 int status=0;  
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otaextras where extrachargeid=?");  
		        ps.setString(1,otaextras.getExtrachargeid());  
		        status=ps.executeUpdate();  
		    }catch(Exception e){System.out.println(e);}  
		  
		    return status;  
	}  
	
	
	public static List<OTAExtras> getAllRecords(){  
	    List<OTAExtras> otaextrasList = new ArrayList<OTAExtras>();  
	      
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otaextras");  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTAExtras otaextras = new OTAExtras();  
	        	otaextras.setId(rs.getInt("id"));  
	        	otaextras.setExtrachargeid(rs.getString("extrachargeid"));  
	   	        otaextras.setShortcode(rs.getString("shortcode"));  
	   	        otaextras.setCharge(rs.getString("charge"));  
	   	        otaextras.setDescription(rs.getString("description"));  
	   	        otaextras.setRate(rs.getString("rate"));  
	   	        otaextras.setChargerule(rs.getString("chargerule"));  
	   	        otaextras.setPostingrule(rs.getString("postingrule"));  
	   	        otaextras.setValidfrom(rs.getString("validfrom")); 
	   	        otaextras.setValidto(rs.getString("validto")); 
	   	        otaextras.setIschargealways(rs.getString("ischargealways")); 
	   	        otaextras.setApplyon_rateplan(rs.getString("applyon_rateplan")); 
	   	        otaextras.setApplyon_special(rs.getString("applyon_special")); 
	   	      
	   	        otaextrasList.add(otaextras);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otaextrasList;  
	}  
	
	
	public static List<OTAExtras> getRecordById(int id){  
		List<OTAExtras> otaextrasList = new ArrayList<OTAExtras>();  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otaextras where extrachargeid=?");  
	        ps.setInt(1,id);  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTAExtras otaextras = new OTAExtras();  
	        	otaextras.setId(rs.getInt("id"));  
	        	otaextras.setExtrachargeid(rs.getString("extrachargeid"));  
	   	        otaextras.setShortcode(rs.getString("shortcode"));  
	   	        otaextras.setCharge(rs.getString("charge"));  
	   	        otaextras.setDescription(rs.getString("description"));  
	   	        otaextras.setRate(rs.getString("rate"));  
	   	        otaextras.setChargerule(rs.getString("chargerule"));  
	   	        otaextras.setPostingrule(rs.getString("postingrule"));  
	   	        otaextras.setValidfrom(rs.getString("validfrom")); 
	   	        otaextras.setValidto(rs.getString("validto")); 
	   	        otaextras.setIschargealways(rs.getString("ischargealways")); 
	   	        otaextras.setApplyon_rateplan(rs.getString("applyon_rateplan")); 
	   	        otaextras.setApplyon_special(rs.getString("applyon_special")); 
	   	      
	   	        otaextrasList.add(otaextras);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otaextrasList;  
	}  
	
	
	
}


