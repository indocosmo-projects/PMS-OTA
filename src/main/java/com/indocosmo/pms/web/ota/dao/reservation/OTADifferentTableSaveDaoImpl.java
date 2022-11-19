package com.indocosmo.pms.web.ota.dao.reservation;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.indocosmo.pms.enumerator.ReservationStatus;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.discount.model.Discount;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.ota.dto.reservation.OTAReservationDTO;
import com.indocosmo.pms.web.ota.entity.reservation.OTAReservation;
import com.indocosmo.pms.web.ota.entity.reservation.OTATaxDeatil;
import com.indocosmo.pms.web.reservation_test.dao.ReservationDaoTest;
import com.indocosmo.pms.web.reservation_test.model.ResvDtl;
import com.indocosmo.pms.web.reservation_test.model.ResvHdr;
import com.indocosmo.pms.web.reservation_test.model.RoomRateDetailsCheck;

@Repository
public class OTADifferentTableSaveDaoImpl {
	
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
	

public static int saveResvHdr(OTAReservation reservation){  
	String sqlResvHdrInsert = "insert into resv_hdr(resv_no,folio_bind_no,status,min_arr_time,min_arr_date,max_depart_time,max_depart_date,sum_num_rooms,resv_type,payment_source,corporate_id,corporate_name,corporate_address,disc_type,guest_id,salutation,resv_by_first_name,resv_by_last_name,resv_by_address,resv_by_mail,resv_by_phone,num_adults,num_children,remarks,billing_instruction,resv_date,resv_taken_by,resv_for,cut_off_date,pickup_needed,special_requests,num_infants,company,designation,country,state,arriving_from,proceeding_to,meal_plan,dob,gender,guest_name)values"+			
	"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    int status=0;  
    try{  
        Connection con=getConnection();  
        PreparedStatement ps=con.prepareStatement(sqlResvHdrInsert);  
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

	
}
