package com.indocosmo.pms.web.ota.service.housekeeping;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.ota.dao.housekeeping.OTACheckinguestListDaoImpl;
import com.indocosmo.pms.web.ota.dao.housekeeping.OTARoomListDaoImpl;
import com.indocosmo.pms.web.ota.dto.housekeeping.OTAHouseStatusDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomDetailsDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInventoryDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.housekeeping.OTACheckinguestlist;
import com.indocosmo.pms.web.ota.entity.housekeeping.OTARoomList;
import com.indocosmo.pms.web.ota.entity.reservation.OTABookingTrans;
import com.indocosmo.pms.web.ota.entity.reservation.OTAReservation;
import com.indocosmo.pms.web.ota.service.common.OnlineTravelAgentServiceImpl;

@Service
public class OTAHouseKeepingServiceImpl implements OTAHouseKeepingServiceInterface {
	
	@Autowired
	private OnlineTravelAgentServiceImpl onlineTravelAgentServiceImpl;
	
	@Autowired
	private	OTACheckinguestListDaoImpl otacheckinguestlistdaoImpl;
	
	@Autowired
	private	OTARoomListDaoImpl otaroomListdaoimpl;
	
	@Override
	public OTAHouseStatusDTO getRetrieveinhouseroomstatus(HotelInfo hotel) {

		int o = 0;
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		OTAHouseStatusDTO otahousestatuslist =  new OTAHouseStatusDTO();
		List<OTACheckinguestlist> otacheckinguestList = new ArrayList<OTACheckinguestlist>();
		List<OTARoomList> otaroomlist = new ArrayList<OTARoomList>();
		
		List<OTACheckinguestlist> checkinguestList = otacheckinguestlistdaoImpl.getAllRecords();
		List<OTARoomList> roomlist = otaroomListdaoimpl.getAllRecords();
		
		o = otacheckinguestlistdaoImpl.deleteAll(checkinguestList.size());
		o = otaroomListdaoimpl.deleteAll(roomlist.size());
		
		JSONObject request = new JSONObject();
		request.put("authcode", hotelauthkey);
		request.put("hotel_code", hotelcode);
		
		String json = request.toString();
		String url = "https://live.ipms247.com/index.php/page/service.hkinfoforkaterina";
		
	    JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON_Header(url, json, hotel);
	    
	    String jojbstr = jsonobject.toString();
	    
	    try {
		
	    JsonArray jarrroomlist = jsonobject.get("roomlist").getAsJsonArray();
	    
	    if(jarrroomlist.size() > 0) {
	    	for(int i = 0; i< jarrroomlist.size();i++) {
	    		JsonObject jobjotaroom = jarrroomlist.get(i).getAsJsonObject();
	    		OTARoomList otaroom = new OTARoomList();
	    		
	    		otaroom.setId(i+1);
	    		otaroom.setHotel_code(jobjotaroom.get("hotel_code").getAsString());
	    		otaroom.setRoomid(jobjotaroom.get("roomid").getAsString());
	    		otaroom.setUnitid(jobjotaroom.get("unitid").getAsString());
	    		otaroom.setRoomname(jobjotaroom.get("roomname").getAsString());
	    		otaroom.setRoomtypeid(jobjotaroom.get("roomtypeid").getAsString());
	    		otaroom.setRoomtypename(jobjotaroom.get("roomtypename").getAsString());
	    		otaroom.setIsblocked(jobjotaroom.get("isblocked").getAsString());
	    		otaroom.setHkstatus(jobjotaroom.get("hkstatus").getAsString());
	    		otaroom.setHkremarks(jobjotaroom.get("hkremarks").getAsString());
	    		otaroom.setRoomstatus(jobjotaroom.get("roomstatus").getAsString());
	    		
	    		otaroomlist.add(otaroom);
	    }
	    }
		} catch (Exception e) {
			e.printStackTrace();
		}

	    try {
			
		    JsonArray jarrcheckinguestlist = jsonobject.get("checkinguestlist").getAsJsonArray();
		    	
		    if(jarrcheckinguestlist.size() > 0) {
		    	for(int i = 0; i< jarrcheckinguestlist.size();i++) {
		    		JsonObject jobjcheckinguest = jarrcheckinguestlist.get(i).getAsJsonObject();
		    		OTACheckinguestlist otacheckinguest = new OTACheckinguestlist();
		    		
		    		otacheckinguest.setId(i+1);
		    		otacheckinguest.setHotel_code(jobjcheckinguest.get("hotel_code").getAsString());  
		    		otacheckinguest.setReservationno(jobjcheckinguest.get("reservationno").getAsString());  
		    		otacheckinguest.setGuestname(jobjcheckinguest.get("guestname").getAsString()); 
		    		otacheckinguest.setEmail(jobjcheckinguest.get("email").getAsString()); 
		    		otacheckinguest.setAddress(jobjcheckinguest.get("address").getAsString()); 
		    		otacheckinguest.setRoom(jobjcheckinguest.get("room").getAsString());  
		    		otacheckinguest.setRoomtype(jobjcheckinguest.get("roomtype").getAsString());  
		    		otacheckinguest.setRatetype(jobjcheckinguest.get("ratetype").getAsString());  
		        	otacheckinguest.setBookingdate(jobjcheckinguest.get("bookingdate").getAsString());  
		        	otacheckinguest.setCheckindate(jobjcheckinguest.get("checkindate").getAsString()); 
		        	otacheckinguest.setCheckoutdate(jobjcheckinguest.get("checkoutdate").getAsString()); 
		        	otacheckinguest.setBusinesssource(jobjcheckinguest.get("businesssource").getAsString());  
		        	otacheckinguest.setMarket(jobjcheckinguest.get("market").getAsString());  
		        	otacheckinguest.setTravelagent(jobjcheckinguest.get("travelagent").getAsString());  
		        	otacheckinguest.setCompany(jobjcheckinguest.get("company").getAsString());  
		        	otacheckinguest.setTavoucherno(jobjcheckinguest.get("tavoucherno").getAsString()); 
		        	otacheckinguest.setAdult(jobjcheckinguest.get("Adult").getAsString()); 
		 	       	otacheckinguest.setChild(jobjcheckinguest.get("Child").getAsString());  
		 	       	otacheckinguest.setHousekeepingremarks(jobjcheckinguest.get("housekeepingremarks").getAsString()); 
		 	       	otacheckinguest.setBookingstatus(jobjcheckinguest.get("bookingstatus").getAsString()); 
		    		 	
		 	       otacheckinguestList.add(otacheckinguest);
		    }
		    }
			} catch (Exception e) {
				e.printStackTrace();
			}
	    
	    try {
			
	    int c = 0;
		for(OTARoomList otaroom : otaroomlist) {
			c++;
			otaroom.setId(c);
			o = otaroomListdaoimpl.save(otaroom);
		}
		
		c = 0;
		for(OTACheckinguestlist otacheckinguest : otacheckinguestList) {
			c = c + 1;
			otacheckinguest.setId(c);
			o = otacheckinguestlistdaoImpl.save(otacheckinguest);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    otahousestatuslist.setCheckinguestList(otacheckinguestList);
	    otahousestatuslist.setRoomlist(otaroomlist);
	    
		return otahousestatuslist;
	}
	
	
	
	@Override
	public OTAHouseStatusDTO getRetrieveinhouseroomstatusDB() {
		
		OTAHouseStatusDTO otahousestatusdto = new OTAHouseStatusDTO();
		List<OTARoomList> roomlist = otaroomListdaoimpl.getAllRecords();
		List<OTACheckinguestlist> checkinguestList = otacheckinguestlistdaoImpl.getAllRecords();
		
		otahousestatusdto.setRoomlist(roomlist);
		otahousestatusdto.setCheckinguestList(checkinguestList); 
		
		return otahousestatusdto;
	}
	
	
	@Override
	public String getUpdateroomstatus(HotelInfo hotel,String roomid,String unitid,String hkstatus,String hkremarks) {
		// TODO Auto-generated method stub
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		String response = "";
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		List<JSONObject> payloadList = new ArrayList<JSONObject>();
		
		request.put("authcode", hotelauthkey);
		request.put("hotel_code", hotelcode);
		payload.put("hotel_code", hotelcode);
		payload.put("roomid", roomid);
		payload.put("unitid", unitid);
		payload.put("hkstatus", hkstatus);
		payload.put("hkremarks", hkremarks);
		payloadList.add(payload);
		request.put("updatehkdata", payloadList);
		String json =  request.toString();
		
		String url = "https://live.ipms247.com/index.php/page/service.hkupdatestatus";
		
	    JsonObject jobj = onlineTravelAgentServiceImpl.Post_JSON_Header(url, json, hotel);
	    
		String jobjresponse = jobj.toString();
		
		 try {
		    	if(jobjresponse.contains("Success")) {
				    String successmsg = jobj.get("message").getAsString();
				    response =  successmsg;
		    	}else {
				    String errormsg = jobj.get("warning").getAsString();
				    response = errormsg;
		    	}
			} catch (Exception e) {
				e.printStackTrace();
			}
		 
		 return response;
		
	}
	
	
	
	@Override
	public String getBlockroom(HotelInfo hotel,String roomid,String roomtypeid,String fromdate,String todate,String reason) {
		// TODO Auto-generated method stub
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		String response = "";
			
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject auth = new JSONObject();
		JSONObject rooms = new JSONObject();
		List<JSONObject> roomsList = new ArrayList<JSONObject>();
		
	
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		rooms.put("RoomID", roomid);
		rooms.put("RoomtypeID", roomtypeid);
		rooms.put("FromDate", fromdate);
		rooms.put("ToDate", todate);
		rooms.put("Reason", reason);
		roomsList.add(rooms);
		payload.put("Request_Type","SetoutofOrder");
		payload.put("Authentication",auth);
		payload.put("Rooms", roomsList);
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
	public String getUnblockroom(HotelInfo hotel,String roomid,String roomtypeid,String fromdate,String todate) {
		// TODO Auto-generated method stub
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		String response = "";
			
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject auth = new JSONObject();
		JSONObject rooms = new JSONObject();
		List<JSONObject> roomsList = new ArrayList<JSONObject>();
		
	
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		rooms.put("RoomID", roomid);
		rooms.put("RoomtypeID", roomtypeid);
		rooms.put("FromDate", fromdate);
		rooms.put("ToDate", todate);
		roomsList.add(rooms);
		payload.put("Request_Type","UnblockRoom");
		payload.put("Authentication",auth);
		payload.put("Rooms", roomsList);
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
	public List<OTARoomList> getRoomidDB() {
		
		List<OTARoomList> otaroom = otaroomListdaoimpl.getAllRecords();
		return otaroom;
	}
	

	
}
