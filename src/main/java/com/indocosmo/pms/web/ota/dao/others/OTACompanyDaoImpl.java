package com.indocosmo.pms.web.ota.dao.others;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Repository;

import com.indocosmo.pms.web.ota.entity.others.OTACompanies;
import com.indocosmo.pms.web.ota.entity.others.OTAGuestStatics;

@Repository
public class OTACompanyDaoImpl {
	
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
	
	public static int save(OTACompanies otacompanies){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("insert into otacompanies(sid,id,accountname,accountcode,contact_person,address,city,postalcode,state,country,phone,mobile,fax,email,taxid,registrationno,isactive) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");  
	        ps.setInt(1,otacompanies.getSid());  
	        ps.setString(2,otacompanies.getId());  
	        ps.setString(3,otacompanies.getAccountname());  
	        ps.setString(4,otacompanies.getAccountcode());  
	        ps.setString(5,otacompanies.getContact_person());  
	        ps.setString(6,otacompanies.getAddress());  
	        ps.setString(7,otacompanies.getCity());  
	        ps.setString(8,otacompanies.getPostalcode());  
	        ps.setString(9,otacompanies.getState());
	        ps.setString(10,otacompanies.getCountry());
	        ps.setString(11,otacompanies.getPhone());
	        ps.setString(12,otacompanies.getMobile());
	        ps.setString(13,otacompanies.getFax());
	        ps.setString(14,otacompanies.getEmail());
	        ps.setString(15,otacompanies.getTaxid());
	        ps.setString(16,otacompanies.getRegistrationno());
	        ps.setString(17,otacompanies.getIsactive());

	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  
	
	
	

	
	public static int update(OTACompanies otacompanies){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("update otacompanies set id=?,accountname=?,accountcode=?,contact_person=?,address=?,city=?,postalcode=?,state=?,country=?,phone=?,mobile=?,fax=?,email=?,taxid=?,registrationno=?,isactive=? where sid=?");  
	        ps.setString(1,otacompanies.getId());  
	        ps.setString(2,otacompanies.getAccountname());  
	        ps.setString(3,otacompanies.getAccountcode());  
	        ps.setString(4,otacompanies.getContact_person());  
	        ps.setString(5,otacompanies.getAddress());  
	        ps.setString(6,otacompanies.getCity());  
	        ps.setString(7,otacompanies.getPostalcode());  
	        ps.setString(8,otacompanies.getState());
	        ps.setString(9,otacompanies.getCountry());
	        ps.setString(10,otacompanies.getPhone());
	        ps.setString(11,otacompanies.getMobile());
	        ps.setString(12,otacompanies.getFax());
	        ps.setString(13,otacompanies.getEmail());
	        ps.setString(14,otacompanies.getTaxid());
	        ps.setString(15,otacompanies.getRegistrationno());
	        ps.setString(16,otacompanies.getIsactive());
	        ps.setInt(17,otacompanies.getSid());  
	     
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  

	
	
	public static int deleteAll(Integer size){  
		 int status=0;  
		 size++ ;
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otacompanies where sid<?");  
		        ps.setInt(1,size);
		        status=ps.executeUpdate();   
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
	}  
	
	public static int deleteById(OTACompanies otacompanies){  
		 int status=0;  
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otacompanies where id=?");  
		        ps.setString(1,otacompanies.getId());  
		        status=ps.executeUpdate();  
		    }catch(Exception e){System.out.println(e);}  
		  
		    return status;  
	}  
	
	
	public static List<OTACompanies> getAllRecords(){  
	    List<OTACompanies> otacompaniesList = new ArrayList<OTACompanies>();  
	      
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otacompanies");  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTACompanies otacompanies = new OTACompanies();  
	        	otacompanies.setSid(rs.getInt("sid"));  
	        	otacompanies.setId(rs.getString("id"));  
	        	otacompanies.setAccountname(rs.getString("accountname"));  
	        	otacompanies.setAccountcode(rs.getString("accountcode"));  
	        	otacompanies.setContact_person(rs.getString("contact_person"));   
	        	otacompanies.setAddress(rs.getString("address"));  
	        	otacompanies.setCity(rs.getString("city"));  
	        	otacompanies.setPostalcode(rs.getString("postalcode"));  
	        	otacompanies.setState(rs.getString("state"));  
	        	otacompanies.setCountry(rs.getString("country"));   
	        	otacompanies.setPhone(rs.getString("phone"));  
	        	otacompanies.setMobile(rs.getString("mobile"));  
	        	otacompanies.setFax(rs.getString("fax"));  
	        	otacompanies.setEmail(rs.getString("email"));  
	        	otacompanies.setTaxid(rs.getString("taxid"));   
	        	otacompanies.setRegistrationno(rs.getString("registrationno"));   
	        	otacompanies.setIsactive(rs.getString("isactive"));   
	        	
	        	otacompaniesList.add(otacompanies);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otacompaniesList;  
	}  
	
	
	public static List<OTACompanies> getRecordById(int id){  
		   List<OTACompanies> otacompaniesList = new ArrayList<OTACompanies>();  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otacompanies where id=?");  
	        ps.setInt(1,id);  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTACompanies otacompanies = new OTACompanies();  
	        	otacompanies.setSid(rs.getInt("sid"));  
	        	otacompanies.setId(rs.getString("id"));  
	        	otacompanies.setAccountname(rs.getString("accountname"));  
	        	otacompanies.setAccountcode(rs.getString("accountcode"));  
	        	otacompanies.setContact_person(rs.getString("contact_person"));   
	        	otacompanies.setAddress(rs.getString("address"));  
	        	otacompanies.setCity(rs.getString("city"));  
	        	otacompanies.setPostalcode(rs.getString("postalcode"));  
	        	otacompanies.setState(rs.getString("state"));  
	        	otacompanies.setCountry(rs.getString("country"));   
	        	otacompanies.setPhone(rs.getString("phone"));  
	        	otacompanies.setMobile(rs.getString("mobile"));  
	        	otacompanies.setFax(rs.getString("fax"));  
	        	otacompanies.setEmail(rs.getString("email"));  
	        	otacompanies.setTaxid(rs.getString("taxid"));   
	        	otacompanies.setRegistrationno(rs.getString("registrationno"));   
	        	otacompanies.setIsactive(rs.getString("isactive"));   
	        	
	        	otacompaniesList.add(otacompanies);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otacompaniesList;  
	}  
	
	
	
}
