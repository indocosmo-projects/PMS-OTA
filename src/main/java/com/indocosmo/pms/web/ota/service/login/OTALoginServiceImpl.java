package com.indocosmo.pms.web.ota.service.login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.indocosmo.pms.web.ota.dao.login.OTALoginDaoImpl;
import com.indocosmo.pms.web.ota.dto.hotel.HotelInfoDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.service.common.OnlineTravelAgentServiceImpl;

@Service
public class OTALoginServiceImpl implements OTALoginServiceInterface {

	  
	@Autowired
	private OTALoginDaoImpl onlineTravelAgentDaoImpl;
	
	@Autowired
	private OnlineTravelAgentServiceImpl onlineTravelAgentServiceImpl;
	
	private String hotelCode = "";
	private String hotelname = "";
	private String hotelauthkey = "";
	
	@Override
	public HotelInfoDTO gethotelinfo(String name, String password) {
		
		String xmldata = "<?xml version=\"1.0\" standalone=\"yes\"?><request><auth>"+password+"</auth><oprn>gethotelinfo</oprn></request>";
		String url = "https://live.ipms247.com/index.php/page/service.pos2pms";
		
		ResponseEntity<String> response = onlineTravelAgentServiceImpl.getUrlContents(url, xmldata);
		String data = response.getBody();
	    System.out.println(response.getStatusCode()+ "--" + response);
	  
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
      
	    if(response.getStatusCode().toString().equals("200")) {
	    	String Hotelname = data.substring(data.lastIndexOf("<hotelname>")+11, data.indexOf("</hotelname>"));
	 	    String HotelCode = data.substring(data.lastIndexOf("<hotelcode>")+11, data.indexOf("</hotelcode>"));
	    	
	 	    hotelInfo.setHotelcode(HotelCode);
	    	hotelInfo.setHotelname(Hotelname);
	    	hotelInfo.setSuccessmsg("success");
	    	hotelInfo.setStatus(response.getStatusCode().toString());
	    	hotelInfo.setPassword(password);
	    	hotelInfo.setUsername(name);
	    	
	    	if(hotellist != null && hotellist.size() > 0) {
	    		hoteldet = hotellist.get(0);
	//    		int i = onlineTravelAgentDaoImpl.update(hoteldet);
	    	}else {
	    		hoteldet.setId(lastdata.getId()+1);
		    	hoteldet.setHotelcode(HotelCode);
		    	hoteldet.setHotelname(Hotelname);
		    	hoteldet.setUsername(name);
		    	hoteldet.setAuthkey(password);
		    	hoteldet.setIsdeleted(0);
		    	int j = onlineTravelAgentDaoImpl.save(hoteldet);
	    	}
	    	
	    	hotelCode = name;
	    	hotelname = Hotelname;
	    	hotelauthkey = password;
	    	
	    	
	    }else {
	    	hotelInfo.setStatus(response.getStatusCode().toString());
	    	hotelInfo.setErrormsg("Unauthorized Access: Invalid Auth Code or Hotel Code. ErrorCode : 611");
	    	hotelCode = "";
	    	hotelname = "";
	    	hotelauthkey = "";
	    }
	    
		return hotelInfo;
	}


}
