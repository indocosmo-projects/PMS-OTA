package com.indocosmo.pms.web.ota.service.ota_rms;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.indocosmo.pms.web.ota.dao.room.OTARoomRatePlansDao;
import com.indocosmo.pms.web.ota.dao.room.OTARoomRateTypesDao;
import com.indocosmo.pms.web.ota.dao.room.OTARoomRoomTypesDao;
import com.indocosmo.pms.web.ota.dto.reservation.OTAPushReservationDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInfoDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInventoryDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.reservation.OTABookingTrans;
import com.indocosmo.pms.web.ota.entity.room.OTARoomRatePlans;
import com.indocosmo.pms.web.ota.entity.room.OTARoomRateTypes;
import com.indocosmo.pms.web.ota.entity.room.OTARoomRoomTypes;
import com.indocosmo.pms.web.ota.service.common.OnlineTravelAgentServiceImpl;
import com.indocosmo.pms.web.ota.service.reservation.OTAReservationServiceImpl;

@Service
public class OTAOTA_RmsServiceImpl implements OTAOTA_RmsServiceInterface {
	
	@Autowired
	private OnlineTravelAgentServiceImpl onlineTravelAgentServiceImpl;
	
	@Autowired
	private OTARoomRatePlansDao otaroomrateplansdao;
	
	@Autowired
	private OTARoomRateTypesDao otaroomratetypesDao;
	
	@Autowired
	private OTARoomRoomTypesDao otaroomroomtypesdao;
	
	@Autowired
	private	OTAReservationServiceImpl  OTAReservationServiceImpl;
	
	@Override
	public OTARoomInfoDTO getRequestroominformation(HotelInfo hotel) {
		
		int o = 0;
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject auth = new JSONObject();
		
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Request_Type", "RoomInfo");
		payload.put("Authentication", auth);
		request.put("RES_Request", payload); 
		
		String json = request.toString();
		String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
		
	    JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
	    
	    JsonObject jobj = jsonobject.get("RoomInfo").getAsJsonObject();
	    JsonObject jobjroomtypes = jobj.get("RoomTypes").getAsJsonObject();
	    JsonObject jobjratetypes = jobj.get("RateTypes").getAsJsonObject();
	    JsonObject jobjrateplans = jobj.get("RatePlans").getAsJsonObject();
	    
	    List<OTARoomRoomTypes> otaroomtypes = OTAReservationServiceImpl.toListRoomTypes(jobjroomtypes);
	    List<OTARoomRateTypes> otaratetypes = OTAReservationServiceImpl.toListRateTypes(jobjratetypes);
	    List<OTARoomRatePlans> otarateplans = OTAReservationServiceImpl.toListRatePlans(jobjrateplans);
	  
	    
	    try {
	
		int c = 0;
		for (OTARoomRoomTypes roomtype : otaroomtypes) {
			c++;
			roomtype.setId(c);
		}

		c = 0;
		for (OTARoomRateTypes otaratetype : otaratetypes) {
			c++;
			otaratetype.setId(c);
		}

		c = 0;
		for (OTARoomRatePlans otarateplan : otarateplans) {
			c++;
			otarateplan.setId(c);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	    
	    OTARoomInfoDTO otaroominfodto = new OTARoomInfoDTO();
	    otaroominfodto.setOtaroomroomtypes(otaroomtypes);
	    otaroominfodto.setOtaroomratetypes(otaratetypes);
	    otaroominfodto.setOtaroomrateplans(otarateplans);
	    
	    return otaroominfodto;
	    
	}

	
	
	
	@Override
	public String getPushinventory(HotelInfo hotel,OTARoomInventoryDTO otaroominv) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		String response = "";
		
		String roomtypeId = otaroominv.getRoomtypeid();
		String fromdate = otaroominv.getFromdate();
		String todate = otaroominv.getTodate();
		String count = otaroominv.getBase();
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject auth = new JSONObject();
		JSONObject roomtype = new JSONObject();
		List<JSONObject> roomtypeList = new ArrayList<JSONObject>();
		
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Request_Type", "UpdateAvailability");
		payload.put("Authentication", auth);
		roomtype.put("RoomTypeID", roomtypeId);
		roomtype.put("FromDate", fromdate);
		roomtype.put("ToDate", todate);
		roomtype.put("Availability", count);
		roomtypeList.add(roomtype);
		payload.put("RoomType", roomtypeList);
		request.put("RES_Request", payload);
		String json =  request.toString();

		String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
		
	    JsonObject jobj = onlineTravelAgentServiceImpl.Post_JSON(url, json);
	
		String jobjresponse = jobj.toString();
		
		 try {
		    	if(jobjresponse.contains("Success")) {
		    		JsonObject jobjsuccess = jobj.get("Success").getAsJsonObject();
				    String successmsg = jobjsuccess.get("SuccessMsg").getAsString();
				    response =  successmsg;
				    JsonObject jobjerror = jobj.get("Errors").getAsJsonObject();
				    String errormsg = jobjerror.get("ErrorMessage").getAsString();
				    String errorcode = jobjerror.get("ErrorCode").getAsString();
				    if(errorcode.equals("0") == false) {
				    	response = response +" : "+ errormsg ;
				    }
		    	}else {
		    		JsonObject jobjerror = jobj.get("Errors").getAsJsonObject();
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
	public String getPushlinearrates(HotelInfo hotel, OTARoomInventoryDTO otaroominv) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		String response = "";
		
		String roomtypeId = otaroominv.getRoomtypeid();
		String ratetypeId = otaroominv.getRatetypeid();
		String fromdate = otaroominv.getFromdate();
		String todate = otaroominv.getTodate();
		String base = otaroominv.getBase();
		String extraadult = otaroominv.getExtraadult();
		String extrachild = otaroominv.getExtrachild();
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject auth = new JSONObject();
		JSONObject ratetype = new JSONObject();
		JSONObject roomtype = new JSONObject();
		List<JSONObject> ratetypeList = new ArrayList<JSONObject>();
		
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Request_Type", "UpdateRoomRates");
		payload.put("Authentication", auth);
		ratetype.put("RoomTypeID", roomtypeId);
		ratetype.put("RateTypeID", ratetypeId);
		ratetype.put("FromDate", fromdate);
		ratetype.put("ToDate", todate);
		roomtype.put("Base", base);
		roomtype.put("ExtraAdult", extraadult);
		roomtype.put("ExtraChild", extrachild);
		ratetype.put("RoomRate", roomtype);
		ratetypeList.add(ratetype);
		payload.put("RateType", ratetypeList);
		request.put("RES_Request", payload);
		String json =  request.toString();

		String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
	    JsonObject jobj = onlineTravelAgentServiceImpl.Post_JSON(url, json);
		String jobjresponse = jobj.toString();
		
		 try {
		    	if(jobjresponse.contains("Success")) {
		    		JsonObject jobjsuccess = jobj.get("Success").getAsJsonObject();
				    String successmsg = jobjsuccess.get("SuccessMsg").getAsString();
				    response =  successmsg;
				    JsonObject jobjerror = jobj.get("Errors").getAsJsonObject();
				    String errormsg = jobjerror.get("ErrorMessage").getAsString();
				    String errorcode = jobjerror.get("ErrorCode").getAsString();
				    if(errorcode.equals("0") == false) {
				    	response = response + errormsg ;
				    }
		    	}else {
		    		JsonObject jobjerror = jobj.get("Errors").getAsJsonObject();
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
	public String getPushNonlinearrates(HotelInfo hotel, OTARoomInventoryDTO otaroominv) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		String response = "";
		
		String roomtypeId = otaroominv.getRoomtypeid();
		String ratetypeId = otaroominv.getRatetypeid();
		String fromdate = otaroominv.getFromdate();
		String todate = otaroominv.getTodate();
		String extraadult = otaroominv.getExtraadult();
		String extrachild = otaroominv.getExtrachild();
		
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject auth = new JSONObject();
		JSONObject ratetype = new JSONObject();
		JSONObject roomtype = new JSONObject();
		List<JSONObject> ratetypeList = new ArrayList<JSONObject>();
		
		String[] adultarray = extraadult.split(",");
		String[] childarray = extrachild.split(",");
		
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
		
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Request_Type", "UpdateRoomRatesNL");
		payload.put("Authentication", auth);
		ratetype.put("RoomTypeID", roomtypeId);
		ratetype.put("RateTypeID", ratetypeId);
		ratetype.put("FromDate", fromdate);
		ratetype.put("ToDate", todate);
		
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
	    JsonObject jobj = onlineTravelAgentServiceImpl.Post_JSON(url, json);
		String jobjresponse = jobj.toString();
		
		 try {
		    	if(jobjresponse.contains("Success")) {
		    		JsonObject jobjsuccess = jobj.get("Success").getAsJsonObject();
				    String successmsg = jobjsuccess.get("SuccessMsg").getAsString();
				    response =  successmsg;
				    JsonObject jobjerror = jobj.get("Errors").getAsJsonObject();
				    String errormsg = jobjerror.get("ErrorMessage").getAsString();
				    String errorcode = jobjerror.get("ErrorCode").getAsString();
				    if(errorcode.equals("0") == false) {
				    	response = response +" : "+ errormsg ;
				    }
		    	}else {
		    		JsonObject jobjerror = jobj.get("Errors").getAsJsonObject();
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
	public String getPushminimumnights(HotelInfo hotel, OTARoomInventoryDTO otaroominv) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		String response = "";
		
		String roomtypeId = otaroominv.getRoomtypeid();
		String ratetypeId = otaroominv.getRatetypeid();
		String rateplanId = otaroominv.getRateplanid();
		String fromdate = otaroominv.getFromdate();
		String todate = otaroominv.getTodate();
		String base = otaroominv.getBase();
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject auth = new JSONObject();
		JSONObject rateplan = new JSONObject();
		JSONObject rateroomdet = new JSONObject();
		List<JSONObject> rateroomdetList = new ArrayList<JSONObject>();
		
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Request_Type", "UpdateMinNights");
		payload.put("Authentication", auth);
		rateroomdet.put("RoomTypeID", roomtypeId);
		rateroomdet.put("RateTypeID", ratetypeId);
		rateroomdet.put("RatePlanID", rateplanId);
		rateroomdet.put("FromDate", fromdate);
		rateroomdet.put("ToDate", todate);
		rateroomdet.put("MinNight", base);
		rateroomdetList.add(rateroomdet);
		payload.put("RatePlan", rateroomdetList);
		request.put("RES_Request", payload);
		String json =  request.toString();
		
		String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
	    JsonObject jobj = onlineTravelAgentServiceImpl.Post_JSON(url, json);
		String jobjresponse = jobj.toString();
		
		 try {
		    	if(jobjresponse.contains("Success")) {
		    		JsonObject jobjsuccess = jobj.get("Success").getAsJsonObject();
				    String successmsg = jobjsuccess.get("SuccessMsg").getAsString();
				    response =  successmsg;
				    JsonObject jobjerror = jobj.get("Errors").getAsJsonObject();
				    String errormsg = jobjerror.get("ErrorMessage").getAsString();
				    String errorcode = jobjerror.get("ErrorCode").getAsString();
				    if(errorcode.equals("0") == false) {
				    	response = response +" : "+ errormsg ;
				    }
		    	}else {
		    		JsonObject jobjerror = jobj.get("Errors").getAsJsonObject();
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
	public String getPushstopsell(HotelInfo hotel, OTARoomInventoryDTO otaroominv) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		String response = "";
		
		String roomtypeId = otaroominv.getRoomtypeid();
		String ratetypeId = otaroominv.getRatetypeid();
		String rateplanId = otaroominv.getRateplanid();
		String fromdate = otaroominv.getFromdate();
		String todate = otaroominv.getTodate();
		String stopcell = otaroominv.getBase();
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject auth = new JSONObject();
		JSONObject rateplan = new JSONObject();
		JSONObject rateroomdet = new JSONObject();
		List<JSONObject> rateroomdetList = new ArrayList<JSONObject>();
		
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Request_Type", "UpdateStopSell");
		payload.put("Authentication", auth);
		rateroomdet.put("RoomTypeID", roomtypeId);
		rateroomdet.put("RateTypeID", ratetypeId);
		rateroomdet.put("RatePlanID", rateplanId);
		rateroomdet.put("FromDate", fromdate);
		rateroomdet.put("ToDate", todate);
		rateroomdet.put("StopSell", stopcell);
		rateroomdetList.add(rateroomdet);
		payload.put("RatePlan", rateroomdetList);
		request.put("RES_Request", payload);
		String json =  request.toString();
		
		String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
	    JsonObject jobj = onlineTravelAgentServiceImpl.Post_JSON(url, json);
		String jobjresponse = jobj.toString();
		
		 try {
		    	if(jobjresponse.contains("Success")) {
		    		JsonObject jobjsuccess = jobj.get("Success").getAsJsonObject();
				    String successmsg = jobjsuccess.get("SuccessMsg").getAsString();
				    response =  successmsg;
				    JsonObject jobjerror = jobj.get("Errors").getAsJsonObject();
				    String errormsg = jobjerror.get("ErrorMessage").getAsString();
				    String errorcode = jobjerror.get("ErrorCode").getAsString();
				    if(errorcode.equals("0") == false) {
				    	response = response +" : "+ errormsg ;
				    }
		    	}else {
		    		JsonObject jobjerror = jobj.get("Errors").getAsJsonObject();
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
	public String getPushcloseonarrival(HotelInfo hotel, OTARoomInventoryDTO otaroominv) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		String response = "";
		
		String roomtypeId = otaroominv.getRoomtypeid();
		String ratetypeId = otaroominv.getRatetypeid();
		String rateplanId = otaroominv.getRateplanid();
		String fromdate = otaroominv.getFromdate();
		String todate = otaroominv.getTodate();
		String coa = otaroominv.getBase();
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject auth = new JSONObject();
		JSONObject rateplan = new JSONObject();
		JSONObject rateroomdet = new JSONObject();
		List<JSONObject> rateroomdetList = new ArrayList<JSONObject>();
		
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Request_Type", "UpdateCOA");
		payload.put("Authentication", auth);
		rateroomdet.put("RoomTypeID", roomtypeId);
		rateroomdet.put("RateTypeID", ratetypeId);
		rateroomdet.put("RatePlanID", rateplanId);
		rateroomdet.put("FromDate", fromdate);
		rateroomdet.put("ToDate", todate);
		rateroomdet.put("COA", coa);
		rateroomdetList.add(rateroomdet);
		payload.put("RatePlan", rateroomdetList);
		request.put("RES_Request", payload);
		String json =  request.toString();
		
		String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
	    JsonObject jobj = onlineTravelAgentServiceImpl.Post_JSON(url, json);
		String jobjresponse = jobj.toString();
		
		 try {
		    	if(jobjresponse.contains("Success")) {
		    		JsonObject jobjsuccess = jobj.get("Success").getAsJsonObject();
				    String successmsg = jobjsuccess.get("SuccessMsg").getAsString();
				    response =  successmsg;
				    JsonObject jobjerror = jobj.get("Errors").getAsJsonObject();
				    String errormsg = jobjerror.get("ErrorMessage").getAsString();
				    String errorcode = jobjerror.get("ErrorCode").getAsString();
				    if(errorcode.equals("0") == false) {
				    	response = response +" : "+ errormsg ;
				    }
		    	}else {
		    		JsonObject jobjerror = jobj.get("Errors").getAsJsonObject();
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
	public String getPushcloseondeparture(HotelInfo hotel, OTARoomInventoryDTO otaroominv) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		String response = "";
		
		String roomtypeId = otaroominv.getRoomtypeid();
		String ratetypeId = otaroominv.getRatetypeid();
		String rateplanId = otaroominv.getRateplanid();
		String fromdate = otaroominv.getFromdate();
		String todate = otaroominv.getTodate();
		String cod = otaroominv.getBase();
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject auth = new JSONObject();
		JSONObject rateplan = new JSONObject();
		JSONObject rateroomdet = new JSONObject();
		List<JSONObject> rateroomdetList = new ArrayList<JSONObject>();
		
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Request_Type", "UpdateCOD");
		payload.put("Authentication", auth);
		rateroomdet.put("RoomTypeID", roomtypeId);
		rateroomdet.put("RateTypeID", ratetypeId);
		rateroomdet.put("RatePlanID", rateplanId);
		rateroomdet.put("FromDate", fromdate);
		rateroomdet.put("ToDate", todate);
		rateroomdet.put("COD", cod);
		rateroomdetList.add(rateroomdet);
		payload.put("RatePlan", rateroomdetList);
		request.put("RES_Request", payload);
		String json =  request.toString();
		
		String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
	    JsonObject jobj = onlineTravelAgentServiceImpl.Post_JSON(url, json);
		String jobjresponse = jobj.toString();
		
		 try {
		    	if(jobjresponse.contains("Success")) {
		    		JsonObject jobjsuccess = jobj.get("Success").getAsJsonObject();
				    String successmsg = jobjsuccess.get("SuccessMsg").getAsString();
				    response =  successmsg;
				    JsonObject jobjerror = jobj.get("Errors").getAsJsonObject();
				    String errormsg = jobjerror.get("ErrorMessage").getAsString();
				    String errorcode = jobjerror.get("ErrorCode").getAsString();
				    if(errorcode.equals("0") == false) {
				    	response = response +" : "+ errormsg ;
				    }
		    	}else {
		    		JsonObject jobjerror = jobj.get("Errors").getAsJsonObject();
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
	public String getBookingstoezee(HotelInfo hotel, OTAPushReservationDTO otapushreservationdto) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		String response = "";
		
		String subbookingid = otapushreservationdto.getSubbookingid();
		String ratetypeid = otapushreservationdto.getRatetypeid();
		String ratetype = otapushreservationdto.getRatetype();
		String roomtypecode = otapushreservationdto.getRoomtypecode();
		String roomtypename = otapushreservationdto.getRoomtypename();
		String start = otapushreservationdto.getStart();
		String end = otapushreservationdto.getEnd();
		String totalrate = otapushreservationdto.getTotalrate();
		String totaldiscount = otapushreservationdto.getTotaldiscount();
		String totalextracharge = otapushreservationdto.getTotalextracharge();
		String totaltax = otapushreservationdto.getTotaltax();
		String totalpayment = otapushreservationdto.getTotalpayment();
		String salutation = otapushreservationdto.getSalutation();
		String firstname = otapushreservationdto.getFirstname();
		String lastname = otapushreservationdto.getLastname();
		String gender = otapushreservationdto.getGender();
		String address = otapushreservationdto.getAddress();
		String city = otapushreservationdto.getCity();
		String state = otapushreservationdto.getState();
		String country = otapushreservationdto.getCountry();
		String zipcode = otapushreservationdto.getZipcode();
		String phone = otapushreservationdto.getPhone();
		String mobile = otapushreservationdto.getMobile();
		String fax = otapushreservationdto.getFax();
		String email = otapushreservationdto.getEmail();
		String transportationmode = otapushreservationdto.getTransportationmode();
		String vehicle = otapushreservationdto.getVehicle();
		String pickupdate = otapushreservationdto.getPickupdate();
		String pickuptime = otapushreservationdto.getPickuptime();
		String comment = otapushreservationdto.getComment();
		
		String effectivedate = otapushreservationdto.getEffectivedate();
		String adult = otapushreservationdto.getAdult();
		String child = otapushreservationdto.getChild();
		String rent = otapushreservationdto.getRent();
		String extracharge = otapushreservationdto.getExtracharge();
		String tax = otapushreservationdto.getTax();
		String discount = otapushreservationdto.getDiscount();
		
		String bookingid = otapushreservationdto.getBookingid();
		String status = otapushreservationdto.getStatus();
		String source = otapushreservationdto.getSource();
		String code = otapushreservationdto.getCode();
		String ccno = otapushreservationdto.getCcno();
		String cctype = otapushreservationdto.getCctype();
		String ccexpirydate = otapushreservationdto.getCcexpirydate();
		String cardholdersname = otapushreservationdto.getCardholdersname();
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		
		JSONObject bookingtrans = new JSONObject();
		JSONObject rentalinfo = new JSONObject();
		JSONObject reservation = new JSONObject();
		JSONObject reservationsList = new JSONObject();
		
		List<JSONObject> rateroomdetList = new ArrayList<JSONObject>();
		List<JSONObject> rentalinfoList = new ArrayList<JSONObject>();
		
		bookingtrans.put("SubBookingId", subbookingid);
		bookingtrans.put("RateTypeID", ratetypeid);
		bookingtrans.put("RateType", ratetype);
		bookingtrans.put("RoomTypeCode", roomtypecode);
		bookingtrans.put("RoomTypeName", roomtypename);
		bookingtrans.put("Start", start);
		bookingtrans.put("End", end);
		bookingtrans.put("TotalRate", totalrate);
		bookingtrans.put("TotalDiscount", totaldiscount);
		bookingtrans.put("TotalExtraCharge", totalextracharge);
		bookingtrans.put("TotalTax", totaltax);
		bookingtrans.put("TotalPayment", totalpayment);
		bookingtrans.put("Salutation", salutation);
		bookingtrans.put("FirstName", firstname);
		bookingtrans.put("LastName", lastname);
		bookingtrans.put("Gender", gender);
		bookingtrans.put("Address", address);
		bookingtrans.put("City", city);
		bookingtrans.put("State", state);
		bookingtrans.put("Country", country);
		bookingtrans.put("Zipcode", zipcode);
		bookingtrans.put("Phone", phone);
		bookingtrans.put("Mobile", mobile);
		bookingtrans.put("Fax", fax);
		bookingtrans.put("Email", email);
		bookingtrans.put("TransportationMode", transportationmode);
		bookingtrans.put("Vehicle", vehicle);
		bookingtrans.put("PickupDate", pickupdate);
		bookingtrans.put("PickupTime", pickuptime);
		bookingtrans.put("Comment", comment);
	
		rentalinfo.put("EffectiveDate", effectivedate);
		rentalinfo.put("Adult", adult);
		rentalinfo.put("Child", child);
		rentalinfo.put("Rent", rent);
		rentalinfo.put("ExtraCharge", extracharge);
		rentalinfo.put("Tax", tax);
		rentalinfo.put("Discount", discount);
		rentalinfoList.add(rentalinfo);
		bookingtrans.put("RentalInfo", rentalinfoList);
		
		reservation.put("HotelCode", hotelcode);
		reservation.put("BookingID", bookingid);
		reservation.put("Status", status);
		reservation.put("Source", source);
		reservation.put("Code", code);
		reservation.put("CCNo", ccno);
		reservation.put("CCType", cctype);
		reservation.put("CCExpiryDate", ccexpirydate);
		reservation.put("CardHoldersName", cardholdersname);
		reservation.put("BookingTran", bookingtrans);
		
		payload.put("Reservations",reservation);
		payload.put("Request_Type","UpdateCOD");
		request.put("RES_Request", payload);
		String json =  request.toString();
		
		String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
	    JsonObject jobj = onlineTravelAgentServiceImpl.Post_JSON(url, json);
		String jobjresponse = jobj.toString();
		
		 try {
		    	if(jobjresponse.contains("Success")) {
		    		JsonObject jobjsuccess = jobj.get("Success").getAsJsonObject();
				    String successmsg = jobjsuccess.get("SuccessMsg").getAsString();
				    response =  successmsg;
				    JsonObject jobjerror = jobj.get("Errors").getAsJsonObject();
				    String errormsg = jobjerror.get("ErrorMessage").getAsString();
				    String errorcode = jobjerror.get("ErrorCode").getAsString();
				    if(errorcode.equals("0") == false) {
				    	response = response +" : "+ errormsg ;
				    }
		    	}else {
		    		JsonObject jobjerror = jobj.get("Errors").getAsJsonObject();
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
