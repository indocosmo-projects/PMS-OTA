package com.indocosmo.pms.web.ota.service.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.ota.dto.booking.OTABookingDTO;
import com.indocosmo.pms.web.ota.dto.booking.OTARoomListDTO;
import com.indocosmo.pms.web.ota.dto.booking.OTARoomReportDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTAReservationDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInventoryDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.others.OTAGuestStatics;
import com.indocosmo.pms.web.ota.entity.reservation.OTABookingTrans;
import com.indocosmo.pms.web.ota.entity.reservation.OTACancelReservation;
import com.indocosmo.pms.web.ota.entity.reservation.OTARentalInfo;
import com.indocosmo.pms.web.ota.entity.reservation.OTAReservation;
import com.indocosmo.pms.web.ota.entity.reservation.OTATaxDeatil;
import com.indocosmo.pms.web.ota.service.common.OnlineTravelAgentServiceImpl;

@Service
public class OTABookingServiceImpl implements OTABookingServiceInterface{
	
	@Autowired
	private OnlineTravelAgentServiceImpl onlineTravelAgentServiceImpl;
	
	@Override
	public List<OTABookingDTO> getCheckAvailability(HotelInfo hotel,String checkindate,String checkoutdate,
			String nights,String adults,String child,String rooms,String roomtypeid) {

		String res = "";
		String result = "";
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    ResponseEntity<String> response = null;
	    List<OTABookingDTO> otabookingdtoList = new ArrayList<OTABookingDTO>();
	    
		try {
			
		String url = 
				"https://live.ipms247.com/booking/reservation_api/listing.php?request_type=RoomList&HotelCode="+hotelcode+"&APIKey="+hotelauthkey+"&check_in_date= "+checkindate+"&check_out_date="+checkoutdate+"&num_nights="+nights+"&number_adults="+adults+"&number_children="+child+"&num_rooms="+rooms+"&promotion_code=&property_configuration_info=0&showtax=0&show_only_available_rooms=0&language=en&roomtypeunkid="+roomtypeid;

			        RestTemplate template = new RestTemplate();
			        HttpHeaders header = new HttpHeaders();
			        HttpEntity requestEntity = new HttpEntity(header);
			        response = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
			        res = response.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
				
				if(res.contains("Error")) {
						OTABookingDTO otabookingdto = new OTABookingDTO();
					  try {
					    	res = response.getBody();
					    	jsonarrdata = new JSONArray(res);
					    	jsondata =jsonarrdata.getJSONObject(0);
						} catch (Exception e) {
							e.printStackTrace();
						}
						JSONObject jobj = jsondata.getJSONObject("Error Details");
						String msg = jobj.get("Error_Message").toString();
						result = msg;
						otabookingdto.setId(-1);
						otabookingdto.setMinavarooms(result);
						otabookingdtoList.add(otabookingdto);
						
				}else {
					 try {
					    	res = response.getBody();
					    	jsonarrdata = new JSONArray(res);
					    	for(int i=0; i < jsonarrdata.length() ;i++ ) {
						    	jsondata = jsonarrdata.getJSONObject(i);
						    	OTABookingDTO otabookingdto = new OTABookingDTO();
						    	otabookingdto.setRoomname(jsondata.get("Room_Name").toString());
						    	otabookingdto.setRoomtypeid(jsondata.get("roomtypeunkid").toString());
						    	otabookingdto.setRackrate(jsondata.get("rack_rate").toString());
						    	otabookingdto.setTotalpriceroomonly(jsondata.get("totalprice_room_only").toString());
						    	otabookingdto.setTotalpriceinclusiveall(jsondata.get("totalprice_inclusive_all").toString());
						    	otabookingdto.setAvgpernightafterdiscount(jsondata.get("avg_per_night_after_discount").toString());
						    	otabookingdto.setAvgpernightwithouttax(jsondata.get("avg_per_night_without_tax").toString());
						    	otabookingdto.setMinavarooms(jsondata.get("min_ava_rooms").toString());
								
						    	otabookingdtoList.add(otabookingdto);
					    	}
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
					
		return otabookingdtoList;
	}
	
	
	
	@Override
	public List<OTAReservation> getSingleBooking(HotelInfo hotel,String bookingid) {
	
	String hotelcode = hotel.getHotelcode();
	String hotelauthkey = hotel.getAuthkey();
	
	JSONObject request = new JSONObject();
	JSONObject payload = new JSONObject();
	JSONObject auth = new JSONObject();
	
	auth.put("HotelCode", hotelcode);
	auth.put("AuthCode", hotelauthkey);
	payload.put("Request_Type", "FetchSingleBooking");        
	payload.put("Authentication", auth);
	payload.put("BookingId", bookingid);
	request.put("RES_Request", payload);
	String json =  request.toString();
	
	String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
	
	JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
	
	List<OTAReservation> resrvationList = new ArrayList<OTAReservation>();
	
	JsonObject jobj = jsonobject.get("Reservations").getAsJsonObject();
	JsonArray jarr = jobj.get("Reservation").getAsJsonArray();
	
	int count = 0;
	for (int i = 0; i < jarr.size(); i++) { 
		JsonObject  obj = jarr.get(i).getAsJsonObject();
		
		try {
		
		count = i + 1;
		OTAReservation otareservation = new OTAReservation();
		
		otareservation.setId(count);
		otareservation.setLocationid(obj.get("LocationId").getAsString());
		otareservation.setUniquereservationid(obj.get("UniqueID").getAsInt());
		otareservation.setBookedby(obj.get("BookedBy").getAsString());
		otareservation.setSalutation(obj.get("Salutation").getAsString());
		otareservation.setFirstname(obj.get("FirstName").getAsString()+obj.get("LastName").getAsString());
		otareservation.setGender(obj.get("Address").getAsString());
		otareservation.setCity(obj.get("City").getAsString());
		otareservation.setState(obj.get("State").getAsString());
		otareservation.setCountry(obj.get("Country").getAsString());
		otareservation.setZipcode(obj.get("Zipcode").getAsString());
		otareservation.setPhone(obj.get("Phone").getAsString());
		otareservation.setMobile(obj.get("Mobile").getAsString());
		otareservation.setFax(obj.get("Fax").getAsString());
		otareservation.setEmail(obj.get("Email").getAsString());
		otareservation.setRegistrationno(obj.get("RegistrationNo").getAsString());
		otareservation.setSource(obj.get("Source").getAsString());
		otareservation.setIschannelbooking(obj.get("IsChannelBooking").getAsString());
		otareservation.setIsdeleted(0);
		
		resrvationList.add(otareservation);
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	return resrvationList;
	
	}
	
	
	
	@Override
	public List<OTABookingTrans> getRetrieveArrivalList(HotelInfo hotel,String bookingid,String fromdate, String todate) {
	
	String hotelcode = hotel.getHotelcode();
	String hotelauthkey = hotel.getAuthkey();
	int c = 0;
	
	List<OTABookingTrans> otabookingTransList = new ArrayList<OTABookingTrans>();
	
	JSONObject request = new JSONObject();
	JSONObject payload = new JSONObject();
	JSONObject auth = new JSONObject();
	JSONObject date = new JSONObject();
	
	date.put("from_date", fromdate);
	date.put("to_date", todate);
	auth.put("HotelCode", hotelcode);
	auth.put("AuthCode", hotelauthkey);
	payload.put("Request_Type", "ArrivalList");        
	payload.put("Authentication", auth);
	payload.put("BookingId", bookingid);
	payload.put("Date", date);
	request.put("RES_Request", payload);
	String json =  request.toString();
	
	String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
	
	JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
	List<OTAReservation> resrvationList = new ArrayList<OTAReservation>();
	
	String response = jsonobject.toString();
	
	if(response.contains("error")) {
		JsonObject jsonerror = jsonobject.get("error").getAsJsonObject();
		
		OTABookingTrans otabookingtrans = new OTABookingTrans();
		otabookingtrans.setId(-1);
		otabookingtrans.setComment(jsonerror.get("message").getAsString());
		
		otabookingTransList.add(otabookingtrans);
		
	}else {
	JsonObject jobj = jsonobject.get("Reservations").getAsJsonObject();
	JsonArray jarr = jobj.get("Reservation").getAsJsonArray();
	
	int count = resrvationList.size();
	for (int h = 0; h < jarr.size(); h++) { 
		JsonObject  obj = jarr.get(h).getAsJsonObject();
		
		String uniqueid = (obj.get("UniqueID").getAsString()); 
		int reseid = Integer.valueOf(uniqueid);
		JsonArray  BookingTransarr = obj.get("BookingTran").getAsJsonArray();
		for (int i = 0; i < BookingTransarr.size(); i++) { 

			try {
			JsonObject otabooking = BookingTransarr.get(i).getAsJsonObject();
			OTABookingTrans otabookingtrans = new OTABookingTrans();
			 c = i + 1;
			otabookingtrans.setId(c);
			otabookingtrans.setSubbookingid(otabooking.get("SubBookingId").getAsString());
			otabookingtrans.setReservationid(reseid);
			otabookingtrans.setTransactionid(otabooking.get("TransactionId").getAsString());
			otabookingtrans.setCreatedatetime(otabooking.get("Createdatetime").getAsString());
			otabookingtrans.setModifydatetime(otabooking.get("Modifydatetime").getAsString());
			otabookingtrans.setStatus(otabooking.get("Status").getAsString());
			otabookingtrans.setCurrentstatus(otabooking.get("CurrentStatus").getAsString());
			otabookingtrans.setVoucherno(otabooking.get("VoucherNo").getAsString());
			otabookingtrans.setPackagecode(otabooking.get("PackageCode").getAsString());
			otabookingtrans.setPackagename(otabooking.get("PackageName").getAsString());
			otabookingtrans.setRateplancode(otabooking.get("RateplanCode").getAsString());
			otabookingtrans.setRateplanname(otabooking.get("RateplanName").getAsString());
			otabookingtrans.setRoomtypecode(otabooking.get("RoomTypeCode").getAsString());
			otabookingtrans.setRoomtypename(otabooking.get("RoomTypeName").getAsString());
			otabookingtrans.setStart(otabooking.get("Start").getAsString());
			otabookingtrans.setEnd(otabooking.get("End").getAsString());
			otabookingtrans.setArrivaltime(otabooking.get("ArrivalTime").getAsString());
			otabookingtrans.setDeparturetime(otabooking.get("DepartureTime").getAsString());
			
			otabookingtrans.setCurrencycode(otabooking.get("CurrencyCode").getAsString());
			otabookingtrans.setTotalamountaftertax(otabooking.get("TotalAmountAfterTax").getAsString());
			otabookingtrans.setTotalamountbeforetax(otabooking.get("TotalAmountBeforeTax").getAsString());
			otabookingtrans.setTotaltax(otabooking.get("TotalTax").getAsString());
			otabookingtrans.setStatus(otabooking.get("Status").getAsString());
			otabookingtrans.setTotaldiscount(otabooking.get("TotalDiscount").getAsString());
			otabookingtrans.setTotalextracharge(otabooking.get("TotalExtraCharge").getAsString());
			otabookingtrans.setTotalpayment(otabooking.get("TotalPayment").getAsString());
			otabookingtrans.setTacommision(otabooking.get("TACommision").getAsString());
			otabookingtrans.setSalutation(otabooking.get("Salutation").getAsString());
			otabookingtrans.setFirstname(otabooking.get("FirstName").getAsString());
			otabookingtrans.setLastname(otabooking.get("LastName").getAsString());
			otabookingtrans.setGender(otabooking.get("Gender").getAsString());
			otabookingtrans.setDateofbirth(otabooking.get("DateOfBirth").getAsString());
			otabookingtrans.setSpousedateofbirth(otabooking.get("SpouseDateOfBirth").getAsString());
			otabookingtrans.setWeddinganniversary(otabooking.get("WeddingAnniversary").getAsString());
			otabookingtrans.setAddress(otabooking.get("Address").getAsString());
			
			otabookingtrans.setCity(otabooking.get("City").getAsString());
			otabookingtrans.setState(otabooking.get("State").getAsString());
			otabookingtrans.setCountry(otabooking.get("Country").getAsString());
			otabookingtrans.setNationality(otabooking.get("Nationality").getAsString());
			otabookingtrans.setZipcode(otabooking.get("Zipcode").getAsString());
			otabookingtrans.setPhone(otabooking.get("Phone").getAsString());
			otabookingtrans.setMobile(otabooking.get("Mobile").getAsString());
			otabookingtrans.setFax(otabooking.get("Fax").getAsString());
			otabookingtrans.setEmail(otabooking.get("Email").getAsString());
			otabookingtrans.setRegistrationno(otabooking.get("RegistrationNo").getAsString());
			otabookingtrans.setIdentiytype(otabooking.get("IdentiyType").getAsString());
			otabookingtrans.setIdentityno(otabooking.get("IdentityNo").getAsString());
			otabookingtrans.setExpirydate(otabooking.get("ExpiryDate").getAsString());
			otabookingtrans.setTransportationmode(otabooking.get("TransportationMode").getAsString());
			otabookingtrans.setVehicle(otabooking.get("Vehicle").getAsString());
			otabookingtrans.setPickupdate(otabooking.get("PickupDate").getAsString());
			otabookingtrans.setPickuptime(otabooking.get("PickupTime").getAsString());
			
			otabookingtrans.setSource(otabooking.get("Source").getAsString());
			otabookingtrans.setComment(otabooking.get("Comment").getAsString());
			otabookingtrans.setAffiliatename(otabooking.get("AffiliateName").getAsString());
			otabookingtrans.setAffiliatecode(otabooking.get("AffiliateCode").getAsString());
			otabookingtrans.setCclink(otabooking.get("CCLink").getAsString());
			otabookingtrans.setCcno(otabooking.get("CCNo").getAsString());
			otabookingtrans.setCctype(otabooking.get("CCType").getAsString());
			otabookingtrans.setCcexpirydate(otabooking.get("CCExpiryDate").getAsString());
			otabookingtrans.setCardholdersname(otabooking.get("CardHoldersName").getAsString());
		
			otabookingTransList.add(otabookingtrans);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
		}
	}
	return otabookingTransList;
	
	}
	
	
	
	@Override
	public List<OTABookingTrans> getRetrieveDepartureList(HotelInfo hotel,String bookingid,String fromdate, String todate) {
	
	String hotelcode = hotel.getHotelcode();
	String hotelauthkey = hotel.getAuthkey();
	int c = 0;
	List<OTABookingTrans> otabookingTransList = new ArrayList<OTABookingTrans>();
	
	JSONObject request = new JSONObject();
	JSONObject payload = new JSONObject();
	JSONObject auth = new JSONObject();
	JSONObject date = new JSONObject();
	
	date.put("from_date", fromdate);
	date.put("to_date", todate);
	auth.put("HotelCode", hotelcode);
	auth.put("AuthCode", hotelauthkey);
	payload.put("Request_Type", "DepartureList");        
	payload.put("Authentication", auth);
	payload.put("BookingId", bookingid);
	payload.put("Date", date);
	request.put("RES_Request", payload);
	String json =  request.toString();
	
	String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
	
	JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
	List<OTAReservation> resrvationList = new ArrayList<OTAReservation>();
	
	String response = jsonobject.toString();
	
	if(response.contains("error")) {
		JsonObject jsonerror = jsonobject.get("error").getAsJsonObject();
		
		OTABookingTrans otabookingtrans = new OTABookingTrans();
		otabookingtrans.setId(-1);
		otabookingtrans.setComment(jsonerror.get("message").getAsString());
		
		otabookingTransList.add(otabookingtrans);
		
	}else {
	JsonObject jobj = jsonobject.get("Reservations").getAsJsonObject();
	JsonArray jarr = jobj.get("Reservation").getAsJsonArray();
	
	int count = resrvationList.size();
	for (int h = 0; h < jarr.size(); h++) { 
		JsonObject  obj = jarr.get(h).getAsJsonObject();
		
		String uniqueid = (obj.get("UniqueID").getAsString()); 
		int reseid = Integer.valueOf(uniqueid);
		JsonArray  BookingTransarr = obj.get("BookingTran").getAsJsonArray();
		for (int i = 0; i < BookingTransarr.size(); i++) { 

			try {
			JsonObject otabooking = BookingTransarr.get(i).getAsJsonObject();
			OTABookingTrans otabookingtrans = new OTABookingTrans();
			c = i + 1;
			otabookingtrans.setId(c);
			otabookingtrans.setSubbookingid(otabooking.get("SubBookingId").getAsString());
			otabookingtrans.setReservationid(reseid);
			otabookingtrans.setTransactionid(otabooking.get("TransactionId").getAsString());
			otabookingtrans.setCreatedatetime(otabooking.get("Createdatetime").getAsString());
			otabookingtrans.setModifydatetime(otabooking.get("Modifydatetime").getAsString());
			otabookingtrans.setStatus(otabooking.get("Status").getAsString());
			otabookingtrans.setCurrentstatus(otabooking.get("CurrentStatus").getAsString());
			otabookingtrans.setVoucherno(otabooking.get("VoucherNo").getAsString());
			otabookingtrans.setPackagecode(otabooking.get("PackageCode").getAsString());
			otabookingtrans.setPackagename(otabooking.get("PackageName").getAsString());
			otabookingtrans.setRateplancode(otabooking.get("RateplanCode").getAsString());
			otabookingtrans.setRateplanname(otabooking.get("RateplanName").getAsString());
			otabookingtrans.setRoomtypecode(otabooking.get("RoomTypeCode").getAsString());
			otabookingtrans.setRoomtypename(otabooking.get("RoomTypeName").getAsString());
			otabookingtrans.setStart(otabooking.get("Start").getAsString());
			otabookingtrans.setEnd(otabooking.get("End").getAsString());
			otabookingtrans.setArrivaltime(otabooking.get("ArrivalTime").getAsString());
			otabookingtrans.setDeparturetime(otabooking.get("DepartureTime").getAsString());
			
			otabookingtrans.setCurrencycode(otabooking.get("CurrencyCode").getAsString());
			otabookingtrans.setTotalamountaftertax(otabooking.get("TotalAmountAfterTax").getAsString());
			otabookingtrans.setTotalamountbeforetax(otabooking.get("TotalAmountBeforeTax").getAsString());
			otabookingtrans.setTotaltax(otabooking.get("TotalTax").getAsString());
			otabookingtrans.setStatus(otabooking.get("Status").getAsString());
			otabookingtrans.setTotaldiscount(otabooking.get("TotalDiscount").getAsString());
			otabookingtrans.setTotalextracharge(otabooking.get("TotalExtraCharge").getAsString());
			otabookingtrans.setTotalpayment(otabooking.get("TotalPayment").getAsString());
			otabookingtrans.setTacommision(otabooking.get("TACommision").getAsString());
			otabookingtrans.setSalutation(otabooking.get("Salutation").getAsString());
			otabookingtrans.setFirstname(otabooking.get("FirstName").getAsString());
			otabookingtrans.setLastname(otabooking.get("LastName").getAsString());
			otabookingtrans.setGender(otabooking.get("Gender").getAsString());
			otabookingtrans.setDateofbirth(otabooking.get("DateOfBirth").getAsString());
			otabookingtrans.setSpousedateofbirth(otabooking.get("SpouseDateOfBirth").getAsString());
			otabookingtrans.setWeddinganniversary(otabooking.get("WeddingAnniversary").getAsString());
			otabookingtrans.setAddress(otabooking.get("Address").getAsString());
			
			otabookingtrans.setCity(otabooking.get("City").getAsString());
			otabookingtrans.setState(otabooking.get("State").getAsString());
			otabookingtrans.setCountry(otabooking.get("Country").getAsString());
			otabookingtrans.setNationality(otabooking.get("Nationality").getAsString());
			otabookingtrans.setZipcode(otabooking.get("Zipcode").getAsString());
			otabookingtrans.setPhone(otabooking.get("Phone").getAsString());
			otabookingtrans.setMobile(otabooking.get("Mobile").getAsString());
			otabookingtrans.setFax(otabooking.get("Fax").getAsString());
			otabookingtrans.setEmail(otabooking.get("Email").getAsString());
			otabookingtrans.setRegistrationno(otabooking.get("RegistrationNo").getAsString());
			otabookingtrans.setIdentiytype(otabooking.get("IdentiyType").getAsString());
			otabookingtrans.setIdentityno(otabooking.get("IdentityNo").getAsString());
			otabookingtrans.setExpirydate(otabooking.get("ExpiryDate").getAsString());
			otabookingtrans.setTransportationmode(otabooking.get("TransportationMode").getAsString());
			otabookingtrans.setVehicle(otabooking.get("Vehicle").getAsString());
			otabookingtrans.setPickupdate(otabooking.get("PickupDate").getAsString());
			otabookingtrans.setPickuptime(otabooking.get("PickupTime").getAsString());
			
			otabookingtrans.setSource(otabooking.get("Source").getAsString());
			otabookingtrans.setComment(otabooking.get("Comment").getAsString());
			otabookingtrans.setAffiliatename(otabooking.get("AffiliateName").getAsString());
			otabookingtrans.setAffiliatecode(otabooking.get("AffiliateCode").getAsString());
			otabookingtrans.setCclink(otabooking.get("CCLink").getAsString());
			otabookingtrans.setCcno(otabooking.get("CCNo").getAsString());
			otabookingtrans.setCctype(otabooking.get("CCType").getAsString());
			otabookingtrans.setCcexpirydate(otabooking.get("CCExpiryDate").getAsString());
			otabookingtrans.setCardholdersname(otabooking.get("CardHoldersName").getAsString());
		
			otabookingTransList.add(otabookingtrans);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
		}
	}
	return otabookingTransList;
	
	}

	
	@Override
	public String getChargePostList(HotelInfo hotel, String room, String folio,
			String table, String outlet, String charge, String postingdate, String trandate, String amount, String tax,
			String gross_amount, String voucherno, String posuser) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		String xmldata = "<request><auth>"+hotelauthkey+"</auth><oprn>chargepost</oprn><room>"+room+"</room><folio>"+folio+"</folio><table>"+table+"</table>"+
		"<outlet>"+outlet+"</outlet><charge>"+charge+"</charge><postingdate>"+postingdate+"</postingdate><trandate>"+trandate+"</trandate>"+
		"<amount>"+amount+"</amount><tax>"+tax+"</tax><gross_amount>"+gross_amount+"</gross_amount><voucherno>"+voucherno+"</voucherno><posuser>"+posuser+"</posuser></request>";
		
		String url = "https://live.ipms247.com/index.php/page/service.pos2pms";
		
		JSONObject jobj = onlineTravelAgentServiceImpl.getUrlContents(url, xmldata);
		String response = jobj.toString();
		String msg = "" ;
		
		if(response.contains("error")) {
			 JSONObject jobjres = (JSONObject) jobj.get("response");
			 msg = jobjres.get("msg").toString();
		}else {
			 JSONObject jobjres = (JSONObject) jobj.get("response");
			 msg = jobjres.get("msg").toString();
		}
		
		
		return msg;
	}
	
	
	@Override
	public String getChargeOnRoom(HotelInfo hotel, String requestid) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		String xmldata = "<request><auth>"+hotelauthkey+"</auth><oprn>voidcharge</oprn><requestid>"+requestid+"</requestid></request>";
		String url = "https://live.ipms247.com/index.php/page/service.pos2pms";
		
		JSONObject jobj = onlineTravelAgentServiceImpl.getUrlContents(url, xmldata);
		String response = jobj.toString();
		String msg = "" ;
		
		if(response.contains("error")) {
			 JSONObject jobjres = (JSONObject) jobj.get("response");
			 msg = jobjres.get("msg").toString();
		}else {
			 JSONObject jobjres = (JSONObject) jobj.get("response");
			 msg = jobjres.get("msg").toString();
		}
		
		return msg;
	}
	
	
	@Override
	public String getPosreceiptno(HotelInfo hotel, String requestid, String voucherno) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		String xmldata = "<request><auth>"+hotelauthkey+"</auth><oprn>updatevoucherno</oprn><voucherno>"+voucherno+"</voucherno><requestid>"+requestid+"</requestid></request>";
		String url = "https://live.ipms247.com/index.php/page/service.pos2pms";
		
		JSONObject jobj = onlineTravelAgentServiceImpl.getUrlContents(url, xmldata);
		String response = jobj.toString();
		String msg = "" ;
		
		if(response.contains("error")) {
			 JSONObject jobjres = (JSONObject) jobj.get("response");
			 msg = jobjres.get("msg").toString();
		}else {
			 JSONObject jobjres = (JSONObject) jobj.get("response");
			 msg = jobjres.get("msg").toString();
		}
		
		return msg;
	}
	
	
	@Override
	public List<OTARoomListDTO> getRetrieveposttoroominformation(HotelInfo hotel) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		List<OTARoomListDTO> otaroomlistdto = new ArrayList<OTARoomListDTO>();
		String xmldata = "<request><auth>"+hotelauthkey+"</auth><oprn>roomlist</oprn></request>";
		String url = "https://live.ipms247.com/index.php/page/service.pos2pms";
		
		JSONObject jobj = onlineTravelAgentServiceImpl.getUrlContents(url, xmldata);
		String response = jobj.toString();
		String msg = "" ;
		
		int c = 0;
		if(response.contains("error")) {
			 JSONObject jobjres = (JSONObject) jobj.get("response");
			 msg = jobjres.get("msg").toString();
			 OTARoomListDTO otaroom = new OTARoomListDTO();
			 otaroom.setId(-1);
			 otaroom.setRemarks(msg);
			 otaroomlistdto.add(otaroom);
		}else {
			 JSONObject jobjres = (JSONObject) jobj.get("response");
			 JSONObject jobjroomrows = (JSONObject) jobjres.get("roomrows");
			 JSONArray jobjrowsarr = (JSONArray) jobjroomrows.get("row");
			
			 for(int i=0; i< jobjrowsarr.length() ; i++) {
				 JSONObject jobjro = jobjrowsarr.getJSONObject(i) ;
				 c = i + 1;
				 OTARoomListDTO otaroom = new OTARoomListDTO();
				 otaroom.setId(c);
				 otaroom.setGuestname(jobjro.getString("guestname").toString());
				 otaroom.setArrival(jobjro.getString("arrival").toString());
				 otaroom.setDeparture(jobjro.getString("departure").toString()); 
				 otaroom.setMasterfolio(jobjro.getString("masterfolio").toString());
				 otaroom.setRoom(jobjro.getString("room").toString()); 
				 otaroom.setRoomtype(jobjro.getString("roomtype").toString());
				 otaroom.setRatetype(jobjro.getString("ratetype").toString());
				 otaroom.setRemarks(jobjro.getString("remarks").toString());
				 otaroom.setResno(jobjro.getString("resno").toString());
				 
				 otaroomlistdto.add(otaroom);
			 } 
		}
		
		return otaroomlistdto;
	}
	
	
	
	@Override
	public List<OTARoomListDTO> getRetrieveposttoroominformationSpecific(HotelInfo hotel,String room) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		List<OTARoomListDTO> otaroomlistdto = new ArrayList<OTARoomListDTO>();
		String xmldata = "<request><auth>"+hotelauthkey+"</auth><oprn>roomquery</oprn><room>"+room+"</room></request>";
		String url = "https://live.ipms247.com/index.php/page/service.pos2pms";
		
		JSONObject jobj = onlineTravelAgentServiceImpl.getUrlContents(url, xmldata);
		String response = jobj.toString();
		String msg = "" ;
		
		int c = 0;
		if(response.contains("error")) {
			 JSONObject jobjres = (JSONObject) jobj.get("response");
			 msg = jobjres.get("msg").toString();
			 OTARoomListDTO otaroom = new OTARoomListDTO();
			 otaroom.setId(-1);
			 otaroom.setRemarks(msg);
			 otaroomlistdto.add(otaroom);
		}else {
			 JSONObject jobjres = (JSONObject) jobj.get("response");
			 JSONObject jobjroomrows = (JSONObject) jobjres.get("roomrows");
			 JSONArray jobjrowsarr = (JSONArray) jobjroomrows.get("row");
			
			 for(int i=0; i< jobjrowsarr.length() ; i++) {
				 JSONObject jobjro = jobjrowsarr.getJSONObject(i) ;
				 c = i + 1;
				 OTARoomListDTO otaroom = new OTARoomListDTO();
				 otaroom.setId(c);
				 otaroom.setGuestname(jobjro.getString("guestname").toString());
				 otaroom.setArrival(jobjro.getString("arrival").toString());
				 otaroom.setDeparture(jobjro.getString("departure").toString()); 
				 otaroom.setMasterfolio(jobjro.getString("masterfolio").toString());
				 otaroom.setRoom(jobjro.getString("room").toString()); 
				 otaroom.setRoomtype(jobjro.getString("roomtype").toString());
				 otaroom.setRatetype(jobjro.getString("ratetype").toString());
				 otaroom.setResno(jobjro.getString("resno").toString());
				 
				 otaroomlistdto.add(otaroom);
			 } 
		}
		
		return otaroomlistdto;
	}
	
	
	
	@Override
	public List<OTARoomReportDTO> getRoomSalesData(HotelInfo hotel, ArrayList<String> roomid, String fromdate, String todate) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		List<OTARoomReportDTO> otaroomreportlist = new ArrayList<OTARoomReportDTO>();
		String url = "https://live.ipms247.com/channelbookings/vacation_rental.php";
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject jobjroom = new JSONObject();
		List<JSONObject> jobjroomList = new ArrayList<JSONObject>();
		
		jobjroom.put("room_id",roomid);
		jobjroom.put("from_date",fromdate);
		jobjroom.put("to_date",todate);
		jobjroomList.add(jobjroom);
		
		payload.put("hotel_id", hotelcode);
		payload.put("rooms", jobjroomList);
		request.put("request_type", "get_sales_report");
		request.put("body", payload);
		
		String json = request.toString();
		JsonObject jobj = onlineTravelAgentServiceImpl.Post_JSON_Header(url, json, hotel);
		String response = jobj.toString();
		String msg = "" ;
		String room1 = roomid.get(0);
		
		System.out.println("response==>"+response);
		int c = 0;
		if(response.contains("success")) {
			JsonObject jobjdata  =  jobj.get("data").getAsJsonObject();
			JsonArray jobjreport = jobjdata.get("report").getAsJsonArray();
			JsonObject jobjroomtype  =  jobjreport.get(0).getAsJsonObject();
			String str = jobjroomtype.toString();
			List<String> lines = new ArrayList<String>();
			Scanner scanner = new Scanner(str);
			while (scanner.hasNextLine()) {
			  String line = scanner.nextLine();
			  lines.add(line);
			}
			scanner.close();
		    System.out.println(lines);   
			int count = (lines.size()/6);
			try {
			for(Integer i=0; i< count ; i++) {
				 String k = i.toString();
				 JsonObject jobjro = jobjroomtype.get(k).getAsJsonObject() ;
				 System.out.println("==>");
				 if(jobjro != null) {
				 c = c + 1;
				 System.out.println("==>jobjro");
				 OTARoomReportDTO  otaroomreport = new OTARoomReportDTO();
				 otaroomreport.setId(c);
				 otaroomreport.setRoom_nights(jobjro.get("room_nights").getAsString());
				 otaroomreport.setRoom_sold(jobjro.get("room_sold").getAsString());
				 otaroomreport.setAdr(jobjro.get("adr").getAsString()); 
				 otaroomreport.setRoom_charges(jobjro.get("room_charges").getAsString());
				 
				 otaroomreportlist.add(otaroomreport);
				 }
			 } 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		return otaroomreportlist;
	}


	
	
	
	
	
	
	
	
	
	
}
