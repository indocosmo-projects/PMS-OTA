package com.indocosmo.pms.web.ota.dao.reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Repository;

import com.indocosmo.pms.web.ota.entity.reservation.OTABookingTrans;
import com.indocosmo.pms.web.ota.entity.reservation.OTARentalInfo;

@Repository
public class OTABookingTransDaoImpl {
	
	
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
	
	public static int save(OTABookingTrans otabookingtrans){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("insert into otabookingtrans(id,subbookingid,reservationid,transactionid,createdatetime,modifydatetime,status,isconfirmed,currentstatus,voucherno,packagecode,packagename,rateplancode,rateplanname,roomtypecode,roomtypename,start,end,arrivaltime,departuretime,currencycode,totalamountaftertax,totalamountbeforetax,totaltax,totaldiscount,totalextracharge,totalpayment,tacommision,salutation,firstname,lastname,gender,dateofbirth,spousedateofbirth,weddinganniversary,address,city,state,country,nationality,zipcode,phone,mobile,fax,email,registrationno,identiytype,identityno,expirydate,transportationmode,vehicle,pickupdate,pickuptime,source,comment,affiliatename,affiliatecode,cclink,ccno,cctype,ccexpirydate,cardholdersname) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");  
	        ps.setInt(1,otabookingtrans.getId());  
	        ps.setString(2,otabookingtrans.getSubbookingid());  
	        ps.setInt(3,otabookingtrans.getReservationid());  
	        ps.setString(4,otabookingtrans.getTransactionid());  
	        ps.setString(5,otabookingtrans.getCreatedatetime());  
	        ps.setString(6,otabookingtrans.getModifydatetime());  
	        ps.setString(7,otabookingtrans.getStatus());  
	        ps.setString(8,otabookingtrans.getIsconfirmed());  
	        ps.setString(9,otabookingtrans.getCurrentstatus());  
	        ps.setString(10,otabookingtrans.getVoucherno());  
	        ps.setString(11,otabookingtrans.getPackagecode());  
	        ps.setString(12,otabookingtrans.getPackagename());  
	        ps.setString(13,otabookingtrans.getRateplancode());  
	        ps.setString(14,otabookingtrans.getRateplanname());  
	        ps.setString(15,otabookingtrans.getRoomtypecode());  
	        ps.setString(16,otabookingtrans.getRoomtypename());  
	        ps.setString(17,otabookingtrans.getStart());  
	        ps.setString(18,otabookingtrans.getEnd());  
	        ps.setString(19,otabookingtrans.getArrivaltime());  
	        ps.setString(20,otabookingtrans.getDeparturetime());  
	        ps.setString(21,otabookingtrans.getCurrencycode());  
	        ps.setString(22,otabookingtrans.getTotalamountaftertax());  
	        ps.setString(23,otabookingtrans.getTotalamountbeforetax());  
	        ps.setString(24,otabookingtrans.getTotaltax());  
	        ps.setString(25,otabookingtrans.getTotaldiscount());  
	        ps.setString(26,otabookingtrans.getTotalextracharge());  
	        ps.setString(27,otabookingtrans.getTotalpayment());  
	        ps.setString(28,otabookingtrans.getTacommision());  
	        ps.setString(29,otabookingtrans.getSalutation());  
	        ps.setString(30,otabookingtrans.getFirstname());  
	        ps.setString(31,otabookingtrans.getLastname());  
	        ps.setString(32,otabookingtrans.getGender());  
	        ps.setString(33,otabookingtrans.getDateofbirth());  
	        ps.setString(34,otabookingtrans.getSpousedateofbirth());  
	        ps.setString(35,otabookingtrans.getWeddinganniversary());  
	        ps.setString(36,otabookingtrans.getAddress());  
	        ps.setString(37,otabookingtrans.getCity());  
	        ps.setString(38,otabookingtrans.getState());  
	        ps.setString(39,otabookingtrans.getCountry());  
	        ps.setString(40,otabookingtrans.getNationality());  
	        ps.setString(41,otabookingtrans.getZipcode());  
	        ps.setString(42,otabookingtrans.getPhone());  
	        ps.setString(43,otabookingtrans.getMobile());  
	        ps.setString(44,otabookingtrans.getFax());  
	        ps.setString(45,otabookingtrans.getEmail());  
	        ps.setString(46,otabookingtrans.getRegistrationno());  
	        ps.setString(47,otabookingtrans.getIdentiytype());  
	        ps.setString(48,otabookingtrans.getIdentityno());  
	        ps.setString(49,otabookingtrans.getExpirydate());  
	        ps.setString(50,otabookingtrans.getTransportationmode());  
	        ps.setString(51,otabookingtrans.getVehicle());  
	        ps.setString(52,otabookingtrans.getPickupdate());  
	        ps.setString(53,otabookingtrans.getPickuptime());  
	        ps.setString(54,otabookingtrans.getSource());  
	        ps.setString(55,otabookingtrans.getComment());  
	        ps.setString(56,otabookingtrans.getAffiliatename());  
	        ps.setString(57,otabookingtrans.getAffiliatecode());  
	        ps.setString(58,otabookingtrans.getCclink());  
	        ps.setString(59,otabookingtrans.getCcno());  
	        ps.setString(60,otabookingtrans.getCctype());  
	        ps.setString(61,otabookingtrans.getCcexpirydate());  
	        ps.setString(62,otabookingtrans.getCardholdersname());  
	       
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  
	
	public static int update(OTABookingTrans otabookingtrans){  
	    int status=0;  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("update otabookingtrans set id=?,subbookingid=?,transactionid=?,createdatetime=?,modifydatetime=?,status=?,isconfirmed=?,currentstatus=?,voucherno=?,packagecode=?,packagename=?,rateplancode=?,rateplanname=?,roomtypecode=?,roomtypename=?,start=?,end=?,arrivaltime=?,departuretime=?,currencycode=?,totalamountaftertax=?,totalamountbeforetax=?,totaltax=?,totaldiscount=?,totalextracharge=?,totalpayment=?,tacommision=?,salutation=?,firstname=?,lastname=?,gender=?,dateofbirth=?,spousedateofbirth=?,weddinganniversary=?,address=?,city=?,state=?,country=?,nationality=?,zipcode=?,phone=?,mobile=?,fax=?,email=?,registrationno=?,identiytype=?,identityno=?,expirydate=?,transportationmode=?,vehicle=?,pickupdate=?,pickuptime=?,source=?,comment=?,affiliatename=?,affiliatecode=?,cclink=?,ccno=?,cctype=?,ccexpirydate=?,cardholdersname=? where reservationid=?");  
	        ps.setInt(1,otabookingtrans.getId());  
	        ps.setString(2,otabookingtrans.getSubbookingid());  
	        ps.setString(3,otabookingtrans.getTransactionid());  
	        ps.setString(4,otabookingtrans.getCreatedatetime());  
	        ps.setString(5,otabookingtrans.getModifydatetime());  
	        ps.setString(6,otabookingtrans.getStatus());  
	        ps.setString(7,otabookingtrans.getIsconfirmed());  
	        ps.setString(8,otabookingtrans.getCurrentstatus());  
	        ps.setString(9,otabookingtrans.getVoucherno());  
	        ps.setString(10,otabookingtrans.getPackagecode());  
	        ps.setString(11,otabookingtrans.getPackagename());  
	        ps.setString(12,otabookingtrans.getRateplancode());  
	        ps.setString(13,otabookingtrans.getRateplanname());  
	        ps.setString(14,otabookingtrans.getRoomtypecode());  
	        ps.setString(15,otabookingtrans.getRoomtypename());  
	        ps.setString(16,otabookingtrans.getStart());  
	        ps.setString(17,otabookingtrans.getEnd());  
	        ps.setString(18,otabookingtrans.getArrivaltime());  
	        ps.setString(19,otabookingtrans.getDeparturetime());  
	        ps.setString(20,otabookingtrans.getCurrencycode());  
	        ps.setString(21,otabookingtrans.getTotalamountaftertax());  
	        ps.setString(22,otabookingtrans.getTotalamountbeforetax());  
	        ps.setString(23,otabookingtrans.getTotaltax());  
	        ps.setString(24,otabookingtrans.getTotaldiscount());  
	        ps.setString(25,otabookingtrans.getTotalextracharge());  
	        ps.setString(26,otabookingtrans.getTotalpayment());  
	        ps.setString(27,otabookingtrans.getTacommision());  
	        ps.setString(28,otabookingtrans.getSalutation());  
	        ps.setString(29,otabookingtrans.getFirstname());  
	        ps.setString(30,otabookingtrans.getLastname());  
	        ps.setString(31,otabookingtrans.getGender());  
	        ps.setString(32,otabookingtrans.getDateofbirth());  
	        ps.setString(33,otabookingtrans.getSpousedateofbirth());  
	        ps.setString(34,otabookingtrans.getWeddinganniversary());  
	        ps.setString(35,otabookingtrans.getAddress());  
	        ps.setString(36,otabookingtrans.getCity());  
	        ps.setString(37,otabookingtrans.getState());  
	        ps.setString(38,otabookingtrans.getCountry());  
	        ps.setString(39,otabookingtrans.getNationality());  
	        ps.setString(40,otabookingtrans.getZipcode());  
	        ps.setString(41,otabookingtrans.getPhone());  
	        ps.setString(42,otabookingtrans.getMobile());  
	        ps.setString(43,otabookingtrans.getFax());  
	        ps.setString(44,otabookingtrans.getEmail());  
	        ps.setString(45,otabookingtrans.getRegistrationno());  
	        ps.setString(46,otabookingtrans.getIdentiytype());  
	        ps.setString(47,otabookingtrans.getIdentityno());  
	        ps.setString(48,otabookingtrans.getExpirydate());  
	        ps.setString(49,otabookingtrans.getTransportationmode());  
	        ps.setString(50,otabookingtrans.getVehicle());  
	        ps.setString(51,otabookingtrans.getPickupdate());  
	        ps.setString(52,otabookingtrans.getPickuptime());  
	        ps.setString(53,otabookingtrans.getSource());  
	        ps.setString(54,otabookingtrans.getComment());  
	        ps.setString(55,otabookingtrans.getAffiliatename());  
	        ps.setString(56,otabookingtrans.getAffiliatecode());  
	        ps.setString(57,otabookingtrans.getCclink());  
	        ps.setString(58,otabookingtrans.getCcno());  
	        ps.setString(59,otabookingtrans.getCctype());  
	        ps.setString(60,otabookingtrans.getCcexpirydate());  
	        ps.setString(61,otabookingtrans.getCardholdersname());  
	        ps.setInt(62,otabookingtrans.getReservationid()); 
	        
	        status=ps.executeUpdate();  
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  
	
	
	public static int deleteAll(Integer size){  
		 int status=0;  
		 size++ ;
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otabookingtrans where id<?");  
		        ps.setInt(1,size);
		        status=ps.executeUpdate();  ;  
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
	}  
	
	public static int deleteById(OTABookingTrans otabookingtrans){  
		 int status=0;  
		    try{  
		        Connection con=getConnection();  
		        PreparedStatement ps=con.prepareStatement("delete from otabookingtrans where reservationid=?");  
		        ps.setInt(1,otabookingtrans.getReservationid());  
		        status=ps.executeUpdate();  
		    }catch(Exception e){System.out.println(e);}  
		  
		    return status;  
	}  
	
	
	public static List<OTABookingTrans> getAllRecords(){  
	    List<OTABookingTrans> otabookingtranslist =new ArrayList<OTABookingTrans>();  
	      
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otabookingtrans");  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTABookingTrans otabookingtrans = new OTABookingTrans();  
	        	otabookingtrans.setId(rs.getInt("id"));  
	        	otabookingtrans.setSubbookingid(rs.getString("subbookingid")); 
	        	otabookingtrans.setReservationid(rs.getInt("reservationid"));  
	        	otabookingtrans.setTransactionid(rs.getString("transactionid"));  
	        	otabookingtrans.setCreatedatetime(rs.getString("createdatetime"));  
	        	otabookingtrans.setModifydatetime(rs.getString("modifydatetime"));  
	        	otabookingtrans.setStatus(rs.getString("status"));  
	        	otabookingtrans.setIsconfirmed(rs.getString("isconfirmed"));  
	        	otabookingtrans.setCurrentstatus(rs.getString("currentstatus"));  
	        	otabookingtrans.setVoucherno(rs.getString("voucherno"));  
	        	otabookingtrans.setPackagecode(rs.getString("packagecode"));  
	        	otabookingtrans.setPackagename(rs.getString("packagename"));  
	        	
	        	otabookingtrans.setRateplancode(rs.getString("rateplancode")); 
	        	otabookingtrans.setRateplanname(rs.getString("rateplanname"));  
	        	otabookingtrans.setRoomtypecode(rs.getString("roomtypecode"));  
	        	otabookingtrans.setRoomtypename(rs.getString("roomtypename"));  
	        	otabookingtrans.setStart(rs.getString("start"));  
	        	otabookingtrans.setEnd(rs.getString("end"));  
	        	otabookingtrans.setArrivaltime(rs.getString("arrivaltime"));  
	        	otabookingtrans.setDeparturetime(rs.getString("departuretime"));  
	        	otabookingtrans.setCurrencycode(rs.getString("currencycode"));  
	        	otabookingtrans.setTotalamountaftertax(rs.getString("totalamountaftertax"));  
	        	otabookingtrans.setTotalamountbeforetax(rs.getString("totalamountbeforetax"));  
	        	
	        	otabookingtrans.setTotaltax(rs.getString("totaltax")); 
	        	otabookingtrans.setTotaldiscount(rs.getString("totaldiscount"));  
	        	otabookingtrans.setTotalextracharge(rs.getString("totalextracharge"));  
	        	otabookingtrans.setTotalpayment(rs.getString("totalpayment"));  
	        	otabookingtrans.setTacommision(rs.getString("tacommision"));  
	        	otabookingtrans.setSalutation(rs.getString("salutation"));  
	        	otabookingtrans.setFirstname(rs.getString("firstname"));  
	        	otabookingtrans.setLastname(rs.getString("lastname"));  
	        	otabookingtrans.setGender(rs.getString("gender"));  
	        	otabookingtrans.setDateofbirth(rs.getString("dateofbirth"));  
	        	otabookingtrans.setSpousedateofbirth(rs.getString("spousedateofbirth"));  
	        	
	        	otabookingtrans.setWeddinganniversary(rs.getString("weddinganniversary")); 
	        	otabookingtrans.setAddress(rs.getString("address"));  
	        	otabookingtrans.setCity(rs.getString("city"));  
	        	otabookingtrans.setState(rs.getString("state"));  
	        	otabookingtrans.setCountry(rs.getString("country"));  
	        	otabookingtrans.setNationality(rs.getString("nationality"));  
	        	otabookingtrans.setZipcode(rs.getString("zipcode"));  
	        	otabookingtrans.setPhone(rs.getString("phone"));  
	        	otabookingtrans.setMobile(rs.getString("mobile"));  
	        	otabookingtrans.setFax(rs.getString("fax"));  
	        	otabookingtrans.setEmail(rs.getString("email"));  
	        	
	        	otabookingtrans.setRegistrationno(rs.getString("registrationno")); 
	        	otabookingtrans.setIdentiytype(rs.getString("identiytype"));  
	        	otabookingtrans.setIdentityno(rs.getString("identityno"));  
	        	otabookingtrans.setExpirydate(rs.getString("expirydate"));  
	        	otabookingtrans.setTransportationmode(rs.getString("transportationmode"));  
	        	otabookingtrans.setVehicle(rs.getString("vehicle"));  
	        	otabookingtrans.setPickupdate(rs.getString("pickupdate"));  
	        	otabookingtrans.setPickuptime(rs.getString("pickuptime"));  
	        	otabookingtrans.setSource(rs.getString("source"));  
	        	otabookingtrans.setComment(rs.getString("comment"));  
	        	otabookingtrans.setAffiliatename(rs.getString("affiliatename"));  
	        	
	        	otabookingtrans.setAffiliatecode(rs.getString("affiliatecode")); 
	        	otabookingtrans.setCclink(rs.getString("cclink"));  
	        	otabookingtrans.setCcno(rs.getString("ccno"));  
	        	otabookingtrans.setCctype(rs.getString("cctype"));  
	        	otabookingtrans.setCcexpirydate(rs.getString("ccexpirydate"));  
	        	otabookingtrans.setCardholdersname(rs.getString("cardholdersname"));  
	        	
	        	otabookingtranslist.add(otabookingtrans);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otabookingtranslist;  
	}  
	
	
	public static List<OTABookingTrans> getRecordById(int reservationid){  
		 List<OTABookingTrans> otabookingtranslist =new ArrayList<OTABookingTrans>();  
	    try{  
	        Connection con=getConnection();  
	        PreparedStatement ps=con.prepareStatement("select * from otabookingtrans where reservationid=?");  
	        ps.setInt(1,reservationid);  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	        	OTABookingTrans otabookingtrans = new OTABookingTrans();  
	        	otabookingtrans.setId(rs.getInt("id"));  
	        	otabookingtrans.setSubbookingid(rs.getString("subbookingid")); 
	        	otabookingtrans.setReservationid(rs.getInt("reservationid"));  
	        	otabookingtrans.setTransactionid(rs.getString("transactionid"));  
	        	otabookingtrans.setCreatedatetime(rs.getString("createdatetime"));  
	        	otabookingtrans.setModifydatetime(rs.getString("modifydatetime"));  
	        	otabookingtrans.setStatus(rs.getString("status"));  
	        	otabookingtrans.setIsconfirmed(rs.getString("isconfirmed"));  
	        	otabookingtrans.setCurrentstatus(rs.getString("currentstatus"));  
	        	otabookingtrans.setVoucherno(rs.getString("voucherno"));  
	        	otabookingtrans.setPackagecode(rs.getString("packagecode"));  
	        	otabookingtrans.setPackagename(rs.getString("packagename"));  
	        	
	        	otabookingtrans.setRateplancode(rs.getString("rateplancode")); 
	        	otabookingtrans.setRateplanname(rs.getString("rateplanname"));  
	        	otabookingtrans.setRoomtypecode(rs.getString("roomtypecode"));  
	        	otabookingtrans.setRoomtypename(rs.getString("roomtypename"));  
	        	otabookingtrans.setStart(rs.getString("start"));  
	        	otabookingtrans.setEnd(rs.getString("end"));  
	        	otabookingtrans.setArrivaltime(rs.getString("arrivaltime"));  
	        	otabookingtrans.setDeparturetime(rs.getString("departuretime"));  
	        	otabookingtrans.setCurrencycode(rs.getString("currencycode"));  
	        	otabookingtrans.setTotalamountaftertax(rs.getString("totalamountaftertax"));  
	        	otabookingtrans.setTotalamountbeforetax(rs.getString("totalamountbeforetax"));  
	        	
	        	otabookingtrans.setTotaltax(rs.getString("totaltax")); 
	        	otabookingtrans.setTotaldiscount(rs.getString("totaldiscount"));  
	        	otabookingtrans.setTotalextracharge(rs.getString("totalextracharge"));  
	        	otabookingtrans.setTotalpayment(rs.getString("totalpayment"));  
	        	otabookingtrans.setTacommision(rs.getString("tacommision"));  
	        	otabookingtrans.setSalutation(rs.getString("salutation"));  
	        	otabookingtrans.setFirstname(rs.getString("firstname"));  
	        	otabookingtrans.setLastname(rs.getString("lastname"));  
	        	otabookingtrans.setGender(rs.getString("gender"));  
	        	otabookingtrans.setDateofbirth(rs.getString("dateofbirth"));  
	        	otabookingtrans.setSpousedateofbirth(rs.getString("spousedateofbirth"));  
	        	
	        	otabookingtrans.setWeddinganniversary(rs.getString("weddinganniversary")); 
	        	otabookingtrans.setAddress(rs.getString("address"));  
	        	otabookingtrans.setCity(rs.getString("city"));  
	        	otabookingtrans.setState(rs.getString("state"));  
	        	otabookingtrans.setCountry(rs.getString("country"));  
	        	otabookingtrans.setNationality(rs.getString("nationality"));  
	        	otabookingtrans.setZipcode(rs.getString("zipcode"));  
	        	otabookingtrans.setPhone(rs.getString("phone"));  
	        	otabookingtrans.setMobile(rs.getString("mobile"));  
	        	otabookingtrans.setFax(rs.getString("fax"));  
	        	otabookingtrans.setEmail(rs.getString("email"));  
	        	
	        	otabookingtrans.setRegistrationno(rs.getString("registrationno")); 
	        	otabookingtrans.setIdentiytype(rs.getString("identiytype"));  
	        	otabookingtrans.setIdentityno(rs.getString("identityno"));  
	        	otabookingtrans.setExpirydate(rs.getString("expirydate"));  
	        	otabookingtrans.setTransportationmode(rs.getString("transportationmode"));  
	        	otabookingtrans.setVehicle(rs.getString("vehicle"));  
	        	otabookingtrans.setPickupdate(rs.getString("pickupdate"));  
	        	otabookingtrans.setPickuptime(rs.getString("pickuptime"));  
	        	otabookingtrans.setSource(rs.getString("source"));  
	        	otabookingtrans.setComment(rs.getString("comment"));  
	        	otabookingtrans.setAffiliatename(rs.getString("affiliatename"));  
	        	
	        	otabookingtrans.setAffiliatecode(rs.getString("affiliatecode")); 
	        	otabookingtrans.setCclink(rs.getString("cclink"));  
	        	otabookingtrans.setCcno(rs.getString("ccno"));  
	        	otabookingtrans.setCctype(rs.getString("cctype"));  
	        	otabookingtrans.setCcexpirydate(rs.getString("ccexpirydate"));  
	        	otabookingtrans.setCardholdersname(rs.getString("cardholdersname"));  
	        	
	        	otabookingtranslist.add(otabookingtrans);
	        }  
	    }catch(Exception e){System.out.println(e);}  
	    return otabookingtranslist;  
	}  
	
	
	
}
