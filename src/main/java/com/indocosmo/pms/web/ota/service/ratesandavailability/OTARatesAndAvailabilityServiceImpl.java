package com.indocosmo.pms.web.ota.service.ratesandavailability;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInfoDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInventoryDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.room.OTARoomRatePlans;
import com.indocosmo.pms.web.ota.entity.room.OTARoomRateTypes;
import com.indocosmo.pms.web.ota.entity.room.OTARoomRoomTypes;
import com.indocosmo.pms.web.ota.service.common.OnlineTravelAgentServiceImpl;

@Service
public class OTARatesAndAvailabilityServiceImpl implements OTARatesAndAvailabilityServiceInterface{
	
	@Autowired
	private OnlineTravelAgentServiceImpl onlineTravelAgentServiceImpl;
	
	
	@Override
	public String getUpdateRoomInventory(HotelInfo hotel,OTARoomInventoryDTO otaroominv) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		String response = "";
	
		JSONObject payload = new JSONObject();
		payload.put("Request_Type", "UpdateAvailability");
		
		JSONObject auth = new JSONObject();
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Authentication", auth);
		
		List<JSONObject> roomtypeList = new ArrayList<JSONObject>();
		JSONObject roomtype = new JSONObject();
		roomtype.put("RoomTypeID", otaroominv.getRoomtypeid());
		roomtype.put("FromDate", otaroominv.getFromdate());
		roomtype.put("ToDate", otaroominv.getTodate());
		roomtype.put("Availability", otaroominv.getBase());
		roomtypeList.add(roomtype);

		payload.put("RoomType", roomtypeList);
		JSONObject request = new JSONObject();
		request.put("RES_Request", payload); 	
		
		String json = request.toString();
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
	
	

	
	@Override
	public String getUpdateNonLinearRate(HotelInfo hotel,OTARoomInventoryDTO otaroominv) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		String response = "";
	
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		payload.put("Request_Type", "UpdateRoomRatesNL");
		
		JSONObject auth = new JSONObject();
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Authentication", auth);
		
		JSONObject contact = new JSONObject();
		contact.put("ContactId", otaroominv.getContactids());
		payload.put("Sources", contact);
		
		String[] adultarray = otaroominv.getAdultrates().split(",");
		String[] childarray = otaroominv.getChildrates().split(",");
		
		String adult1 = adultarray[0];
		String adult2 = adultarray[1];
		String adult3 = adultarray[2];
		String adult4 = adultarray[3];
		String adult5 = adultarray[4];
		String adult6 = adultarray[5];
		String adult7 = adultarray[6];
		
		String child1 = childarray[0];
		String child2 = childarray[1];
		String child3 = childarray[2];
		String child4 = childarray[3];
		String child5 = childarray[4];
		String child6 = childarray[5];
		String child7 = childarray[6];
		
		List<JSONObject> ratetypeList = new ArrayList<JSONObject>();
		JSONObject ratetype = new JSONObject();
		JSONObject roomtype = new JSONObject();
		
		payload.put("Request_Type", "UpdateRoomRatesNL");
		payload.put("Authentication", auth);
		ratetype.put("RoomTypeID", otaroominv.getRoomtypeid());
		ratetype.put("RateTypeID", otaroominv.getRatetypeid());
		ratetype.put("FromDate", otaroominv.getFromdate());
		ratetype.put("ToDate", otaroominv.getTodate());
		
		roomtype.put("Adult1", adult1);
		roomtype.put("Adult2", adult2);
		roomtype.put("Adult3", adult3);
		roomtype.put("Adult4", adult4);
		roomtype.put("Adult5", adult5);
		roomtype.put("Adult6", adult6);
		roomtype.put("Adult7", adult7);
		
		roomtype.put("Child1", child1);
		roomtype.put("Child2", child2);
		roomtype.put("Child3", child3);
		roomtype.put("Child4", child4);
		roomtype.put("Child5", child5);
		roomtype.put("Child6", child6);
		roomtype.put("Child7", child7);
		
		ratetype.put("RoomRate", roomtype);
		ratetypeList.add(ratetype);
		
		payload.put("RateType", ratetypeList);
		request.put("RES_Request", payload);
		
		String json =  request.toString();
			
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
	
	
	@Override
	public OTARoomInfoDTO getRetrieveroomratessourcedetails(HotelInfo hotel) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		String response = "";
		int c = 0;
		
		OTARoomInfoDTO otaroominfodto = new OTARoomInfoDTO();
		List<OTARoomRatePlans> otaroomrateplansList = new ArrayList<OTARoomRatePlans>();
		List<OTARoomRateTypes> otaroomratetypesList = new ArrayList<OTARoomRateTypes>();
		List<OTARoomRoomTypes> otaroomroomtypesList = new ArrayList<OTARoomRoomTypes>();
		List<OTARoomRateTypes> separatechannelsourceList = new ArrayList<OTARoomRateTypes>();
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject auth = new JSONObject();
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Request_Type", "Separatesourcemapping");
		payload.put("Authentication", auth);
		request.put("RES_Request", payload);
		String json =  request.toString();
			
		String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
	    JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
	    String jobjresponse = jsonobject.toString();
		
		if(jobjresponse.contains("Success")) {
			
		JsonObject jobjRoominfo = jsonobject.get("RoomInfo").getAsJsonObject();
		JsonObject jobjRoomTypes = jobjRoominfo.get("RoomTypes").getAsJsonObject();
		JsonObject jobjRateTypes = jobjRoominfo.get("RateTypes").getAsJsonObject();
		JsonObject jobjRatePlans = jobjRoominfo.get("RatePlans").getAsJsonObject();
		JsonObject jobjSaparatechannelsources = jobjRoominfo.get("Saparatechannelsources").getAsJsonObject();
		
		try {
		JsonArray  jsonarrroomtype = jobjRoomTypes.get("RoomType").getAsJsonArray();
		for(int i=0; i<jsonarrroomtype.size(); i++ ) {
			JsonObject jobjroomtype = jsonarrroomtype.get(i).getAsJsonObject();
			OTARoomRoomTypes otaroomroomtypes = new OTARoomRoomTypes();
			c = i + 1;
			otaroomroomtypes.setId(c);
			otaroomroomtypes.setRoomid(jobjroomtype.get("ID").getAsString());
			otaroomroomtypes.setRoomname(jobjroomtype.get("Name").getAsString());
			
			otaroomroomtypesList.add(otaroomroomtypes);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
		JsonArray  jsonarrratetype = jobjRateTypes.get("RateType").getAsJsonArray();
		for(int j=0; j<jsonarrratetype.size(); j++ ) {
			JsonObject jobjratetype = jsonarrratetype.get(j).getAsJsonObject();
			OTARoomRateTypes otaroomratetypes = new OTARoomRateTypes();
			c = j + 1;
			otaroomratetypes.setId(c);
			otaroomratetypes.setRoomtypesid(jobjratetype.get("ID").getAsString());
			otaroomratetypes.setRoomtypesname(jobjratetype.get("Name").getAsString());
			
			otaroomratetypesList.add(otaroomratetypes);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
		JsonArray  jsonarrrateplans = jobjRatePlans.get("RatePlan").getAsJsonArray();
		for(int k=0; k<jsonarrrateplans.size(); k++ ) {
			JsonObject jobjrateplans = jsonarrrateplans.get(k).getAsJsonObject();
			OTARoomRatePlans otaroomrateplans = new OTARoomRatePlans();
			c = k + 1;
			otaroomrateplans.setId(c);
			otaroomrateplans.setRateplanid(jobjrateplans.get("RatePlanID").getAsString());
			otaroomrateplans.setRoomname(jobjrateplans.get("Name").getAsString());
			otaroomrateplans.setRoomtypeid(jobjrateplans.get("RoomTypeID").getAsString());
			otaroomrateplans.setRoomtype(jobjrateplans.get("RoomType").getAsString());
			otaroomrateplans.setRatetypeid(jobjrateplans.get("RateTypeID").getAsString());
			otaroomrateplans.setRatetype(jobjrateplans.get("RateType").getAsString());
			
			otaroomrateplansList.add(otaroomrateplans);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
		JsonArray  jsonarrseparatechannel = jobjSaparatechannelsources.get("Saparatechannelsource").getAsJsonArray();
		for(int l=0; l<jsonarrseparatechannel.size(); l++ ) {
			JsonObject jobjseparatechannel = jsonarrseparatechannel.get(l).getAsJsonObject();
			OTARoomRateTypes separatechannelsource = new OTARoomRateTypes();
			c = l + 1;
			separatechannelsource.setId(c); 
			separatechannelsource.setRoomtypesid(jobjseparatechannel.get("ChannelID").getAsString());
			separatechannelsource.setRoomtypesname(jobjseparatechannel.get("Channel_name").getAsString());
			
			separatechannelsourceList.add(separatechannelsource);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		otaroominfodto.setOtaroomrateplans(otaroomrateplansList);
		otaroominfodto.setOtaroomratetypes(otaroomratetypesList);
		otaroominfodto.setOtaroomroomtypes(otaroomroomtypesList);
		otaroominfodto.setSeparatechannels(separatechannelsourceList); 
		
		}

		return otaroominfodto;
	}
	
	
	@Override
	public String getupdatemaxnights(HotelInfo hotel, String rateplanid, String fromdate, String todate,String maxnight) {

		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		String response = "";
	
		JSONObject payload = new JSONObject();
		payload.put("Request_Type", "UpdateMaxNights");
		
		JSONObject auth = new JSONObject();
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Authentication", auth);
		
		List<JSONObject> rateplanList = new ArrayList<JSONObject>();
		
		JSONObject ratedetails = new JSONObject();
		ratedetails.put("RatePlanID", rateplanid);
		ratedetails.put("FromDate", fromdate);
		ratedetails.put("ToDate", todate);
		ratedetails.put("MaxNight", maxnight);
		
		rateplanList.add(ratedetails);
		payload.put("RatePlan", rateplanList);
		
		JSONObject request = new JSONObject();
		request.put("RES_Request", payload); 	
		
		String json = request.toString();
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
	
	
	@Override
	public String getupdateminnights(HotelInfo hotel, String rateplanid, String fromdate, String todate,String minnight) {
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		String response = "";
	
		JSONObject payload = new JSONObject();
		payload.put("Request_Type", "UpdateMinNights");
		
		JSONObject auth = new JSONObject();
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Authentication", auth);
		
		List<JSONObject> rateplanList = new ArrayList<JSONObject>();
		
		JSONObject ratedetails = new JSONObject();
		ratedetails.put("RatePlanID", rateplanid);
		ratedetails.put("FromDate", fromdate);
		ratedetails.put("ToDate", todate);
		ratedetails.put("MinNight", minnight);
		
		rateplanList.add(ratedetails);
		payload.put("RatePlan", rateplanList);
		
		JSONObject request = new JSONObject();
		request.put("RES_Request", payload); 	
		
		String json = request.toString();
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
	
	
	
	@Override
	public String getupdatestopsell(HotelInfo hotel, String rateplanid, String fromdate, String todate,String stopsell) {
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		String response = "";
	
		JSONObject payload = new JSONObject();
		payload.put("Request_Type", "UpdateStopSell");
		
		JSONObject auth = new JSONObject();
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Authentication", auth);
		
		List<JSONObject> rateplanList = new ArrayList<JSONObject>();
		
		JSONObject ratedetails = new JSONObject();
		ratedetails.put("RatePlanID", rateplanid);
		ratedetails.put("FromDate", fromdate);
		ratedetails.put("ToDate", todate);
		ratedetails.put("StopSell", stopsell);
		
		rateplanList.add(ratedetails);
		payload.put("RatePlan", rateplanList);
		
		JSONObject request = new JSONObject();
		request.put("RES_Request", payload); 	
		
		String json = request.toString();
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
	
	
	
	@Override
	public String getupdatecoa(HotelInfo hotel, String rateplanid, String fromdate, String todate,String coa) {
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		String response = "";
	
		JSONObject payload = new JSONObject();
		payload.put("Request_Type", "UpdateCOA");
		
		JSONObject auth = new JSONObject();
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Authentication", auth);
		
		List<JSONObject> rateplanList = new ArrayList<JSONObject>();
		
		JSONObject ratedetails = new JSONObject();
		ratedetails.put("RatePlanID", rateplanid);
		ratedetails.put("FromDate", fromdate);
		ratedetails.put("ToDate", todate);
		ratedetails.put("COA", coa);
		
		rateplanList.add(ratedetails);
		payload.put("RatePlan", rateplanList);
		
		JSONObject request = new JSONObject();
		request.put("RES_Request", payload); 	
		
		String json = request.toString();
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
	
	
	
	@Override
	public String getupdatecod(HotelInfo hotel, String rateplanid, String fromdate, String todate,String cod) {
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		String response = "";
	
		JSONObject payload = new JSONObject();
		payload.put("Request_Type", "UpdateCOD");
		
		JSONObject auth = new JSONObject();
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Authentication", auth);
		
		List<JSONObject> rateplanList = new ArrayList<JSONObject>();
		
		JSONObject ratedetails = new JSONObject();
		ratedetails.put("RatePlanID", rateplanid);
		ratedetails.put("FromDate", fromdate);
		ratedetails.put("ToDate", todate);
		ratedetails.put("COD", cod);
		
		rateplanList.add(ratedetails);
		payload.put("RatePlan", rateplanList);
		
		JSONObject request = new JSONObject();
		request.put("RES_Request", payload); 	
		
		String json = request.toString();
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
	public List<OTARoomInventoryDTO> getRetrieveroomrates(HotelInfo hotel, String fromdate, String todate) {
		
		List<OTARoomInventoryDTO> otaroominvList  = new ArrayList<OTARoomInventoryDTO>();
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		String xmldata = "<?xml version=\"1.0\" standalone=\"yes\"?><RES_Request><Request_Type>Rate</Request_Type><Authentication><HotelCode>"+hotelcode+"</HotelCode><AuthCode>"+"hotelauthkey"+"</AuthCode></Authentication><FromDate>"+fromdate+"</FromDate><ToDate>"+todate+"</ToDate></RES_Request>";
		String url = "https://live.ipms247.com/pmsinterface/getdataAPI.php";
		
		JSONObject jobj = onlineTravelAgentServiceImpl.getUrlContents(url, xmldata);
		String response = jobj.toString();
		
		if(response.contains("RES_Response")) {
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
				otaroominv.setRatetypeid(jobjeachroom.get("RateTypeID").toString()); 
				otaroominv.setFromdate(jobjeachroom.get("FromDate").toString());
				otaroominv.setTodate(jobjeachroom.get("ToDate").toString());
				
				JSONObject jobjroomrate = (JSONObject) jobjeachroom.get("RoomRate"); 
				otaroominv.setBase(jobjeachroom.get("Base").toString());
				otaroominv.setExtraadult(jobjeachroom.get("ExtraAdult").toString());
				otaroominv.setExtrachild(jobjeachroom.get("ExtraChild").toString());
				
				otaroominvList.add(otaroominv);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		
		return otaroominvList;
		
	}



	



	


	
	
	
	
}
