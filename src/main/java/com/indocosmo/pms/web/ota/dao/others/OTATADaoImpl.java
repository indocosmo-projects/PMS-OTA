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

@Repository
public class OTATADaoImpl {
	
	
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
	
	public static int save(OTACompanies otatravelagent){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("insert into otatravelagent(sid,id,accountname,accountcode,contact_person,address,city,postalcode,state,country,phone,mobile,fax,email,taxid,registrationno,commissionplan,commissionvalue,discount,isactive) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");  
	        ps.setInt(1,otatravelagent.getSid());  
	        ps.setString(2,otatravelagent.getId());  
	        ps.setString(3,otatravelagent.getAccountname());  
	        ps.setString(4,otatravelagent.getAccountcode());  
	        ps.setString(5,otatravelagent.getContact_person());  
	        ps.setString(6,otatravelagent.getAddress());  
	        ps.setString(7,otatravelagent.getCity());  
	        ps.setString(8,otatravelagent.getPostalcode());  
	        ps.setString(9,otatravelagent.getState());
	        ps.setString(10,otatravelagent.getCountry());
	        ps.setString(11,otatravelagent.getPhone());
	        ps.setString(12,otatravelagent.getMobile());
	        ps.setString(13,otatravelagent.getFax());
	        ps.setString(14,otatravelagent.getEmail());
	        ps.setString(15,otatravelagent.getTaxid());
	        ps.setString(16,otatravelagent.getRegistrationno());
	        ps.setString(17,otatravelagent.getCommissionplan());
	        ps.setString(18,otatravelagent.getCommissionvalue());
	        ps.setString(19,otatravelagent.getDiscount());
	        ps.setString(20,otatravelagent.getIsactive());

	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  
	
	
	

	
	public static int update(OTACompanies otatravelagent){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("update otatravelagent set id=?,accountname=?,accountcode=?,contact_person=?,address=?,city=?,postalcode=?,state=?,country=?,phone=?,mobile=?,fax=?,email=?,taxid=?,registrationno=?,commissionplan=?,commissionvalue=?,discount=?,isactive=? where sid=?");  
	        ps.setString(1,otatravelagent.getId());  
	        ps.setString(2,otatravelagent.getAccountname());  
	        ps.setString(3,otatravelagent.getAccountcode());  
	        ps.setString(4,otatravelagent.getContact_person());  
	        ps.setString(5,otatravelagent.getAddress());  
	        ps.setString(6,otatravelagent.getCity());  
	        ps.setString(7,otatravelagent.getPostalcode());  
	        ps.setString(8,otatravelagent.getState());
	        ps.setString(9,otatravelagent.getCountry());
	        ps.setString(10,otatravelagent.getPhone());
	        ps.setString(11,otatravelagent.getMobile());
	        ps.setString(12,otatravelagent.getFax());
	        ps.setString(13,otatravelagent.getEmail());
	        ps.setString(14,otatravelagent.getTaxid());
	        ps.setString(15,otatravelagent.getRegistrationno());
	        ps.setString(16,otatravelagent.getCommissionplan());
	        ps.setString(17,otatravelagent.getCommissionvalue());
	        ps.setString(18,otatravelagent.getDiscount());
	        ps.setString(19,otatravelagent.getIsactive());
	        ps.setInt(20,otatravelagent.getSid());  
	     
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  

	
	
	public static int deleteAll(Integer size){  
		 int status=0;  
		 size++ ;
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otatravelagent where sid<?");  
		        ps.setInt(1,size);
		        status=ps.executeUpdate();   
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
	}  
	
	public static int deleteById(OTACompanies otatravelagent){  
		 int status=0;  
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otatravelagent where id=?");  
		        ps.setString(1,otatravelagent.getId());  
		        status=ps.executeUpdate();  
		    }catch(Exception e){System.out.println(e);}  
		  
		    return status;  
	}  
	
	
	public static List<OTACompanies> getAllRecords(){  
	    List<OTACompanies> otatravelagentList = new ArrayList<OTACompanies>();  
	      
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otatravelagent");  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTACompanies otatravelagent = new OTACompanies();  
	        	otatravelagent.setSid(rs.getInt("sid"));  
	        	otatravelagent.setId(rs.getString("id"));  
	        	otatravelagent.setAccountname(rs.getString("accountname"));  
	        	otatravelagent.setAccountcode(rs.getString("accountcode"));  
	        	otatravelagent.setContact_person(rs.getString("contact_person"));   
	        	otatravelagent.setAddress(rs.getString("address"));  
	        	otatravelagent.setCity(rs.getString("city"));  
	        	otatravelagent.setPostalcode(rs.getString("postalcode"));  
	        	otatravelagent.setState(rs.getString("state"));  
	        	otatravelagent.setCountry(rs.getString("country"));   
	        	otatravelagent.setPhone(rs.getString("phone"));  
	        	otatravelagent.setMobile(rs.getString("mobile"));  
	        	otatravelagent.setFax(rs.getString("fax"));  
	        	otatravelagent.setEmail(rs.getString("email"));  
	        	otatravelagent.setTaxid(rs.getString("taxid"));   
	        	otatravelagent.setRegistrationno(rs.getString("registrationno"));   
	        	otatravelagent.setCommissionplan(rs.getString("commissionplan"));   
	        	otatravelagent.setCommissionvalue(rs.getString("commissionvalue"));   
	        	otatravelagent.setDiscount(rs.getString("discount"));   
	        	otatravelagent.setIsactive(rs.getString("isactive"));   
	        	
	        	otatravelagentList.add(otatravelagent);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otatravelagentList;  
	}  
	
	
	public static List<OTACompanies> getRecordById(int id){  
		   List<OTACompanies> otatravelagentList = new ArrayList<OTACompanies>();  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otatravelagent where id=?");  
	        ps.setInt(1,id);  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTACompanies otatravelagent = new OTACompanies();  
	        	otatravelagent.setSid(rs.getInt("sid"));  
	        	otatravelagent.setId(rs.getString("id"));  
	        	otatravelagent.setAccountname(rs.getString("accountname"));  
	        	otatravelagent.setAccountcode(rs.getString("accountcode"));  
	        	otatravelagent.setContact_person(rs.getString("contact_person"));   
	        	otatravelagent.setAddress(rs.getString("address"));  
	        	otatravelagent.setCity(rs.getString("city"));  
	        	otatravelagent.setPostalcode(rs.getString("postalcode"));  
	        	otatravelagent.setState(rs.getString("state"));  
	        	otatravelagent.setCountry(rs.getString("country"));   
	        	otatravelagent.setPhone(rs.getString("phone"));  
	        	otatravelagent.setMobile(rs.getString("mobile"));  
	        	otatravelagent.setFax(rs.getString("fax"));  
	        	otatravelagent.setEmail(rs.getString("email"));  
	        	otatravelagent.setTaxid(rs.getString("taxid"));   
	        	otatravelagent.setRegistrationno(rs.getString("registrationno"));   
	        	otatravelagent.setCommissionplan(rs.getString("commissionplan"));   
	        	otatravelagent.setCommissionvalue(rs.getString("commissionvalue"));   
	        	otatravelagent.setDiscount(rs.getString("discount"));   
	        	otatravelagent.setIsactive(rs.getString("isactive"));   
	        	
	        	otatravelagentList.add(otatravelagent);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otatravelagentList;  
	}  
	
	
	
}
