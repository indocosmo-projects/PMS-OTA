package com.indocosmo.pms.web.ota.service.login;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.indocosmo.pms.web.ota.dao.login.OTALoginDaoImpl;
import com.indocosmo.pms.web.ota.dto.hotel.HotelInfoDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.service.common.OnlineTravelAgentServiceImpl;
import com.sun.mail.iap.Response;

@Service
public class OTALoginServiceImpl implements OTALoginServiceInterface {

	  
	@Autowired
	private OTALoginDaoImpl onlineTravelAgentDaoImpl;
	
	@Autowired
	private OnlineTravelAgentServiceImpl onlineTravelAgentServiceImpl;
	
	
	@Override
	public HotelInfoDTO gethotelinfo(String name, String password) {
		
		String xmldata = "<?xml version=\"1.0\" standalone=\"yes\"?><request><auth>"+password+"</auth><oprn>gethotelinfo</oprn></request>";
		String url = "https://live.ipms247.com/index.php/page/service.pos2pms";
		
		JSONObject jobj = onlineTravelAgentServiceImpl.getUrlContents(url, xmldata);
		String data = jobj.toString();
	  
	    HotelInfoDTO hotelInfo = new HotelInfoDTO();
	    HotelInfo hoteldet = new HotelInfo();
      List<HotelInfo> hotellist = onlineTravelAgentDaoImpl.getRecordByAuthKey(password);
      List<HotelInfo> hotelAlllist = onlineTravelAgentDaoImpl.getAllRecords();
      HotelInfo lastdata = null;
      if(hotelAlllist != null && hotelAlllist.size() > 0) {
      	lastdata = hotelAlllist.get(hotelAlllist.size()-1);
      }else {
      	lastdata = new HotelInfo();
      }
      try {
    	  
	    if(data.contains("success") && data.contains("ok")) {
	    	
	    	JSONObject jobjresponse = (JSONObject) jobj.get("response");
	    	String HotelCode = jobjresponse.get("hotelcode").toString();
	    	String Hotelname = jobjresponse.get("hotelname").toString();
	    	
	 	    hotelInfo.setHotelcode(HotelCode);
	    	hotelInfo.setHotelname(Hotelname);
	    	hotelInfo.setSuccessmsg("success");
	    	hotelInfo.setStatus("200");
	    	hotelInfo.setPassword(password);
	    	hotelInfo.setUsername(name);
	   
	    	if(hotellist != null && hotellist.size() > 0) {
	    		hoteldet = hotellist.get(0);
	    	}else {
	    		hoteldet.setId(lastdata.getId()+1);
		    	hoteldet.setHotelcode(HotelCode);
		    	hoteldet.setHotelname(Hotelname);
		    	hoteldet.setUsername(name);
		    	hoteldet.setAuthkey(password);
		    	hoteldet.setIsdeleted(0);
		    	int j = onlineTravelAgentDaoImpl.save(hoteldet);
	    	}
	    }else {
	    	hotelInfo.setStatus("611");
	    	hotelInfo.setErrormsg("Unauthorized Access: Invalid Auth Code or Hotel Code. ErrorCode : 611");
	    }
	    
	  	} catch (Exception e) {
			e.printStackTrace();
		}
	    
		return hotelInfo;
	}


}
