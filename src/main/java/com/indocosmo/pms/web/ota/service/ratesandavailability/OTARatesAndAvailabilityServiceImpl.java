package com.indocosmo.pms.web.ota.service.ratesandavailability;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInventoryDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.service.common.OnlineTravelAgentServiceImpl;

@Service
public class OTARatesAndAvailabilityServiceImpl implements OTARatesAndAvailabilityServiceInterface{
	
	@Autowired
	private OnlineTravelAgentServiceImpl onlineTravelAgentServiceImpl;
	
	
	@Override
	public List<OTARoomInventoryDTO> getRetrieveRoomInventory(HotelInfo hotel, String fdate, String tdate) {
		
		List<OTARoomInventoryDTO> otaroominvList  = new ArrayList<OTARoomInventoryDTO>();
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		String xmldata = "<?xml version=\"1.0\" standalone=\"yes\"?><RES_Request><Request_Type>Inventory</Request_Type><Authentication><HotelCode>"+hotelcode+"</HotelCode><AuthCode>"+hotelauthkey+"</AuthCode></Authentication><FromDate>"+fdate+"</FromDate><ToDate>"+tdate+"</ToDate></RES_Request>";
		String url = "https://live.ipms247.com/pmsinterface/getdataAPI.php";
		
		JSONObject jobj = onlineTravelAgentServiceImpl.getUrlContents(url, xmldata);
		String data = jobj.toString();
		
		try {
			
			JSONObject jobjresresponse = (JSONObject) jobj.get("RES_Response");
			JSONObject jobjroominfo = (JSONObject) jobjresresponse.get("RoomInfo");
			JSONArray jobjsource = (JSONArray) jobjroominfo.get("Source");
			for(int k = 0; k < jobjsource.length() ; k++ ) {
			JSONObject jobjroomtypesobj = (JSONObject) jobjsource.get(k);
			JSONObject jobjroomtypes = (JSONObject) jobjroomtypesobj.get("RoomTypes");
			JSONArray jobjrooms =  (JSONArray) jobjroomtypes.get("RoomType");
			int c = 0;
			for(int i=0; i< jobjrooms.length() ;i++) {
				JSONObject jobjeachroom = (JSONObject) jobjrooms.get(i);
				
				OTARoomInventoryDTO otaroominv  = new OTARoomInventoryDTO();
				c++;
				otaroominv.setId(c);
				otaroominv.setRoomtypeid(jobjeachroom.get("RoomTypeID").toString()); 
				otaroominv.setAvailability(jobjeachroom.get("Availability").toString()); 
				otaroominv.setFromdate(jobjeachroom.get("FromDate").toString());
				otaroominv.setTodate(jobjeachroom.get("ToDate").toString());
				
				otaroominvList.add(otaroominv);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
		return otaroominvList;
	}
	
	
	
	@Override
	public String updateLinearRateinventory(HotelInfo hotel,OTARoomInventoryDTO otaroominv) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		String response = "";
	
		JSONObject payload = new JSONObject();
		payload.put("Request_Type", "UpdateRoomRates");
		
		JSONObject auth = new JSONObject();
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Authentication", auth);
		
		JSONObject contact = new JSONObject();
		contact.put("ContactId", otaroominv.getContactids());
		payload.put("Sources", contact);
		
		List<JSONObject> ratetypeList = new ArrayList<JSONObject>();
		
		JSONObject roomrate = new JSONObject();
		roomrate.put("Base", otaroominv.getBase());
		JSONObject ratetype = new JSONObject();
		ratetype.put("RoomTypeID", otaroominv.getRoomtypeid());
		ratetype.put("RateTypeID", otaroominv.getRatetypeid());
		ratetype.put("FromDate", otaroominv.getFromdate());
		ratetype.put("ToDate", otaroominv.getTodate());
		ratetype.put("RoomRate", roomrate);
		ratetypeList.add(ratetype);
		
		roomrate = new JSONObject();
		roomrate.put("Base", otaroominv.getBase());
		roomrate.put("ExtraAdult", otaroominv.getExtraadult());
		roomrate.put("ExtraChild", otaroominv.getExtrachild());
		ratetype = new JSONObject();
		ratetype.put("RoomTypeID", otaroominv.getRoomtypeid());
		ratetype.put("RateTypeID", otaroominv.getRatetypeid());
		ratetype.put("FromDate", otaroominv.getFromdate());
		ratetype.put("ToDate", otaroominv.getTodate());
		ratetype.put("RoomRate", roomrate);
		ratetypeList.add(ratetype);
		
		payload.put("RateType", ratetypeList);
		
		JSONObject requestlinear = new JSONObject();
		requestlinear.put("RES_Request", payload); 	
		
		String json = requestlinear.toString();
		String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
		
	    JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
		
	    String jobjresponse = jsonobject.toString();
	    try {
	    	if(jobjresponse.contains("Success")) {
			    JsonObject jobjsuccess = jsonobject.get("Success").getAsJsonObject();
			    String successmsg = jobjsuccess.get("SuccessMsg").getAsString();
			    response =  successmsg;
			    JsonObject jobjerror = jsonobject.get("Errors").getAsJsonObject();
			    String errormsg = jobjerror.get("ErrorMessage").getAsString();
			    String errorcode = jobjerror.get("ErrorCode").getAsString();
			    if(errorcode.equals("0") == false) {
			    	response = response +" : "+ errormsg ;
			    }
	    	}else {
			    JsonObject jobjerror = jsonobject.get("Errors").getAsJsonObject();
			    String errormsg = jobjerror.get("ErrorMessage").getAsString();
			    String errorcode = jobjerror.get("ErrorCode").getAsString();
			    response = errormsg;
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
		return response;
	}


	
}
