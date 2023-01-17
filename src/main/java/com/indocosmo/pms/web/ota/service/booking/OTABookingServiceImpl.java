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
import com.indocosmo.pms.web.ota.dto.booking.OTACalendarDTO;
import com.indocosmo.pms.web.ota.dto.booking.OTAFolioDTO;
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
	
	try {
		
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
	
	} catch (Exception e) {
		e.printStackTrace();
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
			try {
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
					} catch (Exception e) {
						e.printStackTrace();
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
			try {
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return otaroomlistdto;
	}
	
	
	
	@Override
	public String getRoomSalesData(HotelInfo hotel, ArrayList<String> roomid, String fromdate, String todate) {
		
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
		String result = "";
		
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
			if(lines.size() > 0) {
				result = "Rooms Available";
			}
		}
	
		return result;
	}
	
	
	@Override
	public List<OTACalendarDTO> getReservedroomscalendar(HotelInfo hotel, ArrayList<String> roomid, ArrayList<String> roomcode,String fromdate, String todate) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		List<OTACalendarDTO> otacalendarList = new ArrayList<OTACalendarDTO>();
		String url = "https://live.ipms247.com/channelbookings/vacation_rental.php";
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject jobjroom = new JSONObject();
		List<JSONObject> jobjroomList = new ArrayList<JSONObject>();
		JsonArray jarrroominfo = null;
		
		jobjroom.put("room_id",roomid);
		jobjroom.put("room_code",roomcode);
		jobjroom.put("from_date",fromdate);
		jobjroom.put("to_date",todate);
		jobjroomList.add(jobjroom);
		
		payload.put("hotel_id", hotelcode);
		payload.put("rooms", jobjroomList);
		request.put("request_type", "get_calendar");
		request.put("body", payload);
		
		String json = request.toString();
		JsonObject jobj = onlineTravelAgentServiceImpl.Post_JSON_Header(url, json, hotel);
		String response = jobj.toString();
		
		int c = 0;
		String strroomid = roomid.get(0);
		if(response.contains("success")) {
			try {
			JsonObject jobjdata  =  jobj.get("data").getAsJsonObject();
			JsonObject jobjreport =  jobjdata.get(strroomid).getAsJsonObject();
			OTACalendarDTO otacalendardto = new OTACalendarDTO();
			
			try {
			String res = jobjreport.get("room_info").getAsString();
			OTACalendarDTO otacalendardto1 = new OTACalendarDTO();
			otacalendardto1.setId(-1);
			otacalendardto1.setStatus(res);
			otacalendarList.add(otacalendardto1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				jarrroominfo = jobjreport.get("room_info").getAsJsonArray();
				
				for(int i=0; i< jarrroominfo.size(); i++) {
					otacalendardto.setRoom_name(jobjreport.get("room_name").getAsString());
					c = i + 1;
					JsonObject JobjObjroominfo = jarrroominfo.get(i).getAsJsonObject();
					otacalendardto.setId(c);
					otacalendardto.setRoom_code(JobjObjroominfo.get("room_code").getAsString());
					otacalendardto.setDate(JobjObjroominfo.get("date").getAsString());
					otacalendardto.setStatus(JobjObjroominfo.get("status").getAsString());
					
					JsonObject JobjObjreservationinfo = JobjObjroominfo.get("reservation_info").getAsJsonObject();
					otacalendardto.setReservation_id(JobjObjreservationinfo.get("reservation_id").getAsString());
					otacalendardto.setGuest_name(JobjObjreservationinfo.get("guest_name").getAsString());
					otacalendardto.setCheck_in(JobjObjreservationinfo.get("check_in").getAsString());
					otacalendardto.setCheck_out(JobjObjreservationinfo.get("check_out").getAsString());
					otacalendardto.setBooking_status(JobjObjreservationinfo.get("booking_status").getAsString());
					otacalendardto.setTotal_amount(JobjObjreservationinfo.get("total_amount").getAsString());
					otacalendardto.setPayment_type(JobjObjreservationinfo.get("payment_type").getAsString());
					otacalendardto.setCreation_date(JobjObjreservationinfo.get("creation_date").getAsString());
			
				otacalendarList.add(otacalendardto);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			String res = jobj.get("error_message").getAsString();
			OTACalendarDTO otacalendardto = new OTACalendarDTO();
			otacalendardto.setId(-1);
			otacalendardto.setStatus(res);
			otacalendarList.add(otacalendardto);
		}
		
		return otacalendarList;
	}


	@Override
	public List<OTACalendarDTO> getRetrievephysicalrooms(HotelInfo hotel) {

		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		List<OTACalendarDTO> otacalendarList = new ArrayList<OTACalendarDTO>();
		String url = "https://live.ipms247.com/channelbookings/vacation_rental.php";
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
	
		payload.put("hotel_id", hotelcode);
		request.put("request_type", "get_rooms");
		request.put("body", payload);
		
		String json = request.toString();
		JsonObject jobj = onlineTravelAgentServiceImpl.Post_JSON_Header(url, json, hotel);
		String response = jobj.toString();
		
		int c = 0 ;
		try {
		if(response.contains("error")) {
			String msg = jobj.get("error_message").getAsString();
			OTACalendarDTO otacalendardto = new OTACalendarDTO();
			otacalendardto.setId(-1);
			otacalendardto.setStatus(msg);
			otacalendarList.add(otacalendardto);
		}else {
			JsonObject jobjdata = jobj.get("data").getAsJsonObject();
			JsonArray jobjrooms = jobjdata.get("rooms").getAsJsonArray();
			for(int i=0; i< jobjrooms.size(); i++) {
				JsonObject jobjeachroom = jobjrooms.get(i).getAsJsonObject();
				OTACalendarDTO otacalendardto = new OTACalendarDTO();
				c = i + 1;
				otacalendardto.setId(c);
				otacalendardto.setRoomid(jobjeachroom.get("room_id").getAsString());
				otacalendardto.setRoom_code(jobjeachroom.get("room_code").getAsString());
				otacalendardto.setRooms(jobjeachroom.get("rooms").getAsString());
				otacalendardto.setRoom_name(jobjeachroom.get("room_name").getAsString());
				otacalendarList.add(otacalendardto);
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return otacalendarList;
	}

	
	
	@Override
	public List<OTACalendarDTO> getCheckincheckout(HotelInfo hotel) {

		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		List<OTACalendarDTO> otacalendarList = new ArrayList<OTACalendarDTO>();
		String url = "https://live.ipms247.com/channelbookings/vacation_rental.php";
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
	
		payload.put("hotel_id", hotelcode);
		payload.put("report_type", "both");
		request.put("request_type", "get_calendar");
		request.put("body", payload);
		
		String json = request.toString();
		JsonObject jobj = onlineTravelAgentServiceImpl.Post_JSON_Header(url, json, hotel);
		String response = jobj.toString();
		
		int c = 0 ;
		try {
		if(response.contains("error")) {
			String msg = jobj.get("error_message").getAsString();
			OTACalendarDTO otacalendardto = new OTACalendarDTO();
			otacalendardto.setId(-1);
			otacalendardto.setStatus(msg);
			otacalendarList.add(otacalendardto);
		}else {
			JsonObject jobjdata = jobj.get("data").getAsJsonObject();
			JsonObject jobjhotelcode = jobjdata.get(hotelcode).getAsJsonObject();
			JsonArray jobjdeparture = jobjhotelcode.get("departures").getAsJsonArray();
			
			for(int i=0; i< jobjdeparture.size(); i++) {
				JsonObject jobjeachroom = jobjdeparture.get(i).getAsJsonObject();
				OTACalendarDTO otacalendardto = new OTACalendarDTO();
				c = i + 1;
				otacalendardto.setId(c);
				otacalendardto.setReservation_id(jobjeachroom.get("reservation_id").getAsString());
				otacalendardto.setRoom_code(jobjeachroom.get("room_code").getAsString());
				otacalendardto.setDeparturedate(jobjeachroom.get("departure_date_time").getAsString());
				otacalendarList.add(otacalendardto);
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return otacalendarList;
	}
	
	
	
	
	@Override
	public List<OTACalendarDTO> getReservationdetailsofaroom(HotelInfo hotel, String reservation_id) {

		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		List<OTACalendarDTO> otacalendarList = new ArrayList<OTACalendarDTO>();
		String url = "https://live.ipms247.com/channelbookings/vacation_rental.php";
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
	
		payload.put("hotel_id", hotelcode);
		payload.put("reservation_id", reservation_id);
		request.put("request_type", "get_reservation");
		request.put("body", payload);
		
		String json = request.toString();
		JsonObject jobj = onlineTravelAgentServiceImpl.Post_JSON_Header(url, json, hotel);
		String response = jobj.toString();
		
		int c = 0 ;
		try {
		if(response.contains("error")) {
			String msg = jobj.get("error_message").getAsString();
			OTACalendarDTO otacalendardto = new OTACalendarDTO();
			otacalendardto.setId(-1);
			otacalendardto.setStatus(msg);
			otacalendarList.add(otacalendardto);
		}else {
			JsonArray jarrdata = jobj.get("data").getAsJsonArray();
			
			for(int i=0; i< jarrdata.size(); i++) {
				JsonObject jobjeachroom = jarrdata.get(i).getAsJsonObject();
				OTACalendarDTO otacalendardto = new OTACalendarDTO();
				c = i + 1;
				otacalendardto.setId(c);
			
				otacalendardto.setRoomid(jobjeachroom.get("room_id").getAsString());
				otacalendardto.setRoom_name(jobjeachroom.get("room_name").getAsString());
				otacalendardto.setReservation_id(jobjeachroom.get("reservation_id").getAsString());
				otacalendardto.setBooking_status(jobjeachroom.get("booking_status").getAsString());
				otacalendardto.setGuest_name(jobjeachroom.get("guest_name").getAsString());
				otacalendardto.setCheck_in(jobjeachroom.get("check_in").getAsString());
				otacalendardto.setCheck_out(jobjeachroom.get("check_out").getAsString());
				otacalendardto.setTotal_amount(jobjeachroom.get("total_amount").getAsString());
				otacalendardto.setChannel(jobjeachroom.get("channel").getAsString());
				otacalendardto.setPayment_type(jobjeachroom.get("payment_type").getAsString());
				
				otacalendarList.add(otacalendardto);
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return otacalendarList;
	}
	
	
	
	@Override
	public List<OTAReservation> getPullhistoricalbookings(HotelInfo hotel, String fromdate, String todate) {

		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		List<OTAReservation> otareservationlist = new ArrayList<OTAReservation>();

		String url = "https://live.ipms247.com/pmsinterface/getdataAPI.php";
		String xmldata = "<RES_Request><Request_Type>Booking</Request_Type><Authentication><HotelCode>"+hotelcode+"</HotelCode><AuthCode>"+hotelauthkey+"</AuthCode></Authentication><FromDate>"+fromdate+"</FromDate><ToDate>"+todate+"</ToDate></RES_Request>";
		    
		JSONObject jobj = onlineTravelAgentServiceImpl.getUrlContents(url, xmldata);
		String response = jobj.toString();
		String msg = "" ;
			
		int c = 0;
		if(response.contains("Success")) {
			
				try {
					JSONObject jobjres = (JSONObject) jobj.get("RES_Response");
					JSONObject jobjreservation = (JSONObject) jobjres.get("Reservations");
					JSONArray jobjrowsarr = (JSONArray) jobjreservation.get("Reservation");
						
					for(int i=0; i< jobjrowsarr.length() ; i++) {
						JSONObject jobjro = jobjrowsarr.getJSONObject(i) ;
						JSONObject jobjbookbyinfo = (JSONObject) jobjro.get("BookByInfo");
						c = i + 1;
						OTAReservation otareservation = new OTAReservation();
						otareservation.setId(c);
						otareservation.setLocationid(jobjbookbyinfo.get("LocationId").toString());
						String unid = jobjbookbyinfo.get("UniqueID").toString();
						int id = Integer.parseInt(unid) ;
						otareservation.setUniquereservationid(id);
						otareservation.setBookedby(jobjbookbyinfo.get("BookedBy").toString());
						otareservation.setSalutation(jobjbookbyinfo.get("Salutation").toString());
						otareservation.setFirstname(jobjbookbyinfo.get("FirstName").toString()+jobjbookbyinfo.get("LastName").toString());
						otareservation.setGender(jobjbookbyinfo.get("Address").toString());
						otareservation.setCity(jobjbookbyinfo.get("City").toString());
						otareservation.setState(jobjbookbyinfo.get("State").toString());
						otareservation.setCountry(jobjbookbyinfo.get("Country").toString());
						otareservation.setZipcode(jobjbookbyinfo.get("Zipcode").toString());
						otareservation.setPhone(jobjbookbyinfo.get("Phone").toString());
						otareservation.setMobile(jobjbookbyinfo.get("Mobile").toString());
						otareservation.setFax(jobjbookbyinfo.get("Fax").toString());
						otareservation.setEmail(jobjbookbyinfo.get("Email").toString());
						otareservation.setRegistrationno(jobjbookbyinfo.get("BusinessSource").toString());
						otareservation.setSource(jobjbookbyinfo.get("Source").toString());
						otareservation.setIschannelbooking(jobjbookbyinfo.get("IsChannelBooking").toString());
									 
					    otareservationlist.add(otareservation);
					} 
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}else {
					JSONObject jobjres = (JSONObject) jobj.get("RES_Response");
					JSONObject jobjerror = (JSONObject) jobjres.get("Errors");
					msg = jobjerror.get("ErrorMessage").toString();
					OTAReservation otareservation = new OTAReservation();
					otareservation.setId(-1);
					otareservation.setSource(msg);
					otareservationlist.add(otareservation);
			}
			
		return otareservationlist;
	}


	@Override
	public String getCreatebookings(HotelInfo hotel,String rateplanid,
			String ratetypeid, String roomtypeid ,String baserate,
			String extradultrate ,String extrachildrate,String numberadults ,String numberchildren,
			String extrachildage, String title,String firstname ,String lastname ,String gender,
			String specialrequest, String checkindate,String checkoutdate ,String bookingpaymentmode,
			String emailaddress, String sourceid , String mobileno ,String address,String state ,String country ,
			String city,String zipcode,String fax,String device,String languagekey,String paymenttypeunkid) {

		String res = "";
		String result = "";
		String responsestring = "Created Successfully";
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    ResponseEntity<String> response = null;
	    
	    JSONObject JsonBookingData = new JSONObject();
	    JSONObject RoomDetails = new JSONObject();
	    JSONObject Room1 = new JSONObject();
	    
	    Room1.put("Rateplan_Id",rateplanid);
	    Room1.put("Ratetype_Id",ratetypeid);
	    Room1.put("Roomtype_Id",roomtypeid);
	    Room1.put("baserate",baserate);
	    Room1.put("extradultrate",extradultrate);
	    Room1.put("extrachildrate",extrachildrate);
	    Room1.put("number_adults",numberadults);
	    Room1.put("number_children",numberchildren);
	    Room1.put("ExtraChild_Age",extrachildage);
	    Room1.put("Title",title);
	    Room1.put("First_Name",firstname);
	    Room1.put("Last_Name",lastname);
	    Room1.put("Gender",gender);
	    Room1.put("SpecialRequest",specialrequest);
	  
	    RoomDetails.put("Room_1",Room1);
	    JsonBookingData.put("Room_Details",RoomDetails);
	    JsonBookingData.put("check_in_date",checkindate);
	    JsonBookingData.put("check_out_date",checkoutdate);
	    JsonBookingData.put("Booking_Payment_Mode",bookingpaymentmode);
	    JsonBookingData.put("Email_Address",emailaddress);
	    JsonBookingData.put("Source_Id",sourceid);
	    JsonBookingData.put("MobileNo",mobileno);
	    JsonBookingData.put("Address",address);
	    JsonBookingData.put("State",state);
	    JsonBookingData.put("Country",country);
	    JsonBookingData.put("City",state);
	    JsonBookingData.put("Zipcode",zipcode);
	    JsonBookingData.put("Fax",fax);
	    JsonBookingData.put("Device",device);
	    JsonBookingData.put("Languagekey",languagekey);
	    JsonBookingData.put("paymenttypeunkid",paymenttypeunkid);
	    
	    String strbooking = JsonBookingData.toString();
	    
		try {
		String url = "https://live.ipms247.com/booking/reservation_api/listing.php?request_type=InsertBooking&HotelCode="+hotelcode+"&APIKey="+hotelauthkey+"&BookingData="+strbooking;
		
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
							responsestring = msg;
						
				}else {
					 try {
					    	res = response.getBody();
					    	JSONObject jsonobj = new JSONObject(res);
					    	responsestring = jsonobj.get("ReservationNo").toString();
					    	
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
					
		return responsestring;
		
	}


	@Override
	public String getpostcreatebookingsactions(HotelInfo hotel) {
		String res = "";
		String result = "";
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    ResponseEntity<String> response = null;
	   
		try {
			String url = "https://live.ipms247.com/booking/reservation_api/listing.php?request_type=ProcessBooking&HotelCode="+hotelcode+"&APIKey="+hotelauthkey ; 
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
						
				}else {
					 try {
					    	res = response.getBody();
					    	JSONObject jsonobj = new JSONObject(res);
					    	result = jsonobj.get("message").toString();
					    	
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
					
		return result;
	}
	
	
	
	
	@Override
	public List<OTABookingTrans> getRetrieveabookingbasedonparameters(HotelInfo hotel, String fromdate,  String todate, String email) {
		String res = "";
		String result = "";
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		int c = 0;
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    ResponseEntity<String> response = null;
	    List<OTABookingTrans> otabookingtransList = new  ArrayList<OTABookingTrans>();
	   
		try {
			String url = "https://live.ipms247.com/booking/reservation_api/listing.php?request_type=BookingList&HotelCode="+hotelcode+"&APIKey="+hotelauthkey+"&arrival_from="+fromdate+"&arrival_to="+todate+"&EmailId="+email;
						RestTemplate template = new RestTemplate();
				        HttpHeaders header = new HttpHeaders();
				        HttpEntity requestEntity = new HttpEntity(header);
				        response = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
				        res = response.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
		
				if(res.contains("Error")) {
						OTABookingTrans otabookingtrans = new OTABookingTrans();
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
							otabookingtrans.setId(-1);
							otabookingtrans.setComment(result); 
							otabookingtransList.add(otabookingtrans);
						
				}else {
					 try {
					    	res = response.getBody();
					    	JSONObject jsonobj = new JSONObject(res);
					    	JSONObject jsonobjroomlist = (JSONObject) jsonobj.get("RoomList");
					    	OTABookingTrans otabookingroom = new OTABookingTrans();
					    	otabookingroom.setId(0);
					    	otabookingroom.setTotalactiveroominhotel(jsonobjroomlist.get("TotalActiveRoomInHotel").toString()); 
					    	otabookingroom.setTotalblockrooms(jsonobjroomlist.get("TotalBlockRooms").toString()); 
					    	otabookingroom.setTotaloccupiedrooms(jsonobjroomlist.get("TotalOccupiedRooms").toString()); 
				    		otabookingtransList.add(otabookingroom);
				    		
					    	JSONArray jsonarr = (JSONArray) jsonobj.get("BookingList");
					    	if(jsonarr.length() > 0) {
					    	for(int i = 0; i < jsonarr.length() ; i++ ) {
					    		JSONObject jsonbooking =  (JSONObject) jsonarr.get(i);
					    		OTABookingTrans otabookingtrans = new OTABookingTrans();
					    		c = i + 1;
					    		String reseid = jsonbooking.get("ReservationNo").toString();
					    		int reseint = Integer.valueOf(reseid);
					    		otabookingtrans.setId(c);
					    		otabookingtrans.setReservationid(reseint);
					    		otabookingtrans.setFirstname(jsonbooking.get("GuestName").toString());
					    		otabookingtrans.setRateplanname(jsonbooking.get("RatePlan").toString()); 
					    		otabookingtrans.setStart(jsonbooking.get("ArrivalDate").toString());
					    		otabookingtrans.setEnd(jsonbooking.get("DepartureDate").toString());
					    		otabookingtrans.setArrivaltime(jsonbooking.get("ArrivalTime").toString());
					    		otabookingtrans.setDeparturetime(jsonbooking.get("DepartureTime").toString());
					    		otabookingtrans.setEmail(jsonbooking.get("Email").toString());
					    		otabookingtrans.setCurrentstatus(jsonbooking.get("Status").toString());
					    		otabookingtrans.setReservationdate(jsonbooking.get("ReservationDate").toString());
					    		otabookingtrans.setNoofguest(jsonbooking.get("NoOfGuest").toString());
					    		otabookingtrans.setNoofnights(jsonbooking.get("NoOfNights").toString());
						    	
						    	otabookingtransList.add(otabookingtrans);
					    	}
					    	}
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
					
		return otabookingtransList;
	}


	@Override
	public List<OTABookingTrans> getReadabooking(HotelInfo hotel, String reservationnumber) {

		String res = "";
		String result = "";
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		int c = 0;
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    ResponseEntity<String> response = null;
	    List<OTABookingTrans> otabookingtransList = new  ArrayList<OTABookingTrans>();
	   
		try {
			String url =  "https://live.ipms247.com/booking/reservation_api/listing.php?request_type=ReadBooking&HotelCode="+hotelcode+"&APIKey="+hotelauthkey+"&language=en&ResNo="+ reservationnumber ; 
						RestTemplate template = new RestTemplate();
				        HttpHeaders header = new HttpHeaders();
				        HttpEntity requestEntity = new HttpEntity(header);
				        response = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
				        res = response.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
		
				if(res.contains("Error")) {
						OTABookingTrans otabookingtrans = new OTABookingTrans();
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
							otabookingtrans.setId(-1);
							otabookingtrans.setComment(result); 
							otabookingtransList.add(otabookingtrans);
						
				}else {
					 try {
					    	res = response.getBody();
					    	JSONArray jsonobjarr = new JSONArray(res);
				    		
					    	if(jsonobjarr.length() > 0) {
					    	for(int i = 0; i < jsonobjarr.length() ; i++ ) {
					    		JSONObject jsonbooking =  (JSONObject) jsonobjarr.get(i);
					    		OTABookingTrans otabookingtrans = new OTABookingTrans();
					    		c = i + 1;
					    		String reseid = jsonbooking.get("Reservation_No").toString();
					    		int reseint = Integer.valueOf(reseid);
					    		otabookingtrans.setId(c);
					    		otabookingtrans.setReservationid(reseint);
					    		otabookingtrans.setFirstname(jsonbooking.get("First_Name").toString());
					    		otabookingtrans.setLastname(jsonbooking.get("Last_Name").toString());
					    		otabookingtrans.setRateplanname(jsonbooking.get("Rate_Type").toString()); 
					    		otabookingtrans.setStart(jsonbooking.get("ArrivalDate").toString());
					    		otabookingtrans.setEnd(jsonbooking.get("DepartureDate").toString());
					    		otabookingtrans.setEmail(jsonbooking.get("Email").toString());
					    		otabookingtrans.setMobile(jsonbooking.get("Mobile").toString());
					    		otabookingtrans.setCurrentstatus(jsonbooking.get("StatusUnkId").toString());
					    		otabookingtrans.setNoofnights(jsonbooking.get("No_of_Nights").toString());
					    		otabookingtrans.setAffiliatecode(jsonbooking.get("Guestunkid").toString());
					    		String country = jsonbooking.get("City").toString() +","+ jsonbooking.get("State").toString() + ","+jsonbooking.get("Country").toString()+","+ jsonbooking.get("Zipcode").toString();    
					    		otabookingtrans.setCountry(country);
					    		
						    	otabookingtransList.add(otabookingtrans);
					    	}
					    	}
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
					
		return otabookingtransList;
		
	}
	
	
	
	@Override
	public String getCancelabooking(HotelInfo hotel, String reservationnumber) {

		String res = "";
		String result = "";
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		int c = 0;
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    ResponseEntity<String> response = null;
	    List<OTABookingTrans> otabookingtransList = new  ArrayList<OTABookingTrans>();
	   
		try {
				String url =  "https://live.ipms247.com/booking/reservation_api/listing.php?request_type=CancelBooking&HotelCode="+hotelcode+"&APIKey="+hotelauthkey+"&language=en&ResNo="+reservationnumber;
			
						RestTemplate template = new RestTemplate();
				        HttpHeaders header = new HttpHeaders();
				        HttpEntity requestEntity = new HttpEntity(header);
				        response = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
				        res = response.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
		
				if(res.contains("Error")) {
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
						
				}else {
					 try {
					    	res = response.getBody();
					    	JSONObject jobj = new JSONObject(res);
					    	String msg = jobj.get("status").toString();
					    	result = msg;
					    
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
					
		return result;
	}
	
	
	
	
	@Override
	public String getAutosync(HotelInfo hotel, String reservationnumber) {

		String res = "";
		String result = "";
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		int c = 0;
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    ResponseEntity<String> response = null;
	    List<OTABookingTrans> otabookingtransList = new  ArrayList<OTABookingTrans>();
	   
		try {
				String url =  "https://live.ipms247.com/booking/reservation_api/listing.php?request_type=CancelBooking&HotelCode="+hotelcode+"&APIKey="+hotelauthkey+"&language=en&ResNo="+reservationnumber;
			
						RestTemplate template = new RestTemplate();
				        HttpHeaders header = new HttpHeaders();
				        HttpEntity requestEntity = new HttpEntity(header);
				        response = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
				        res = response.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
		
				if(res.contains("Error")) {
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
						
				}else {
					 try {
					    	res = response.getBody();
					    	JSONObject jobj = new JSONObject(res);
					    	String msg = jobj.get("status").toString();
					    	result = msg;
					    
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
					
		return result;
	}



	@Override
	public String getGuestdataupdate(HotelInfo hotel, String reservationid, String firstname, String lastname, String email) {
		
		String res = "";
		String result = "";
		String response = "";
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    
	    JSONObject request = new JSONObject();
	    JSONObject payload = new JSONObject();
	    JSONObject auth = new JSONObject();
	    JSONObject reservation = new JSONObject();
	    List<JSONObject> reservationlist = new ArrayList<JSONObject>();
	    JSONObject guestdetails = new JSONObject();
	    List<JSONObject> guestdetailsList = new ArrayList<JSONObject>();
	    
	    guestdetails.put("FirstName", firstname);
	    guestdetails.put("LastName", lastname);
	    guestdetails.put("Email", email);
	    guestdetailsList.add(guestdetails);
	    
	    reservation.put("BookingId", reservationid);
	    reservation.put("GuestDetails", guestdetailsList);
	    reservationlist.add(reservation);
	    
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
	
		payload.put("Request_Type", "UploadDocument");        
		payload.put("Authentication", auth);
		payload.put("Reservation", reservationlist);
		request.put("RES_Request", payload);
		String json =  request.toString();
		
		String url = "https://live.ipms247.com/index.php/page/service.kioskconnectivity";
		
		JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
		List<OTAReservation> resrvationList = new ArrayList<OTAReservation>();
		
		String resp = jsonobject.toString();
		
		if(resp.contains("Success")) {
			JsonObject jobjsuccess = jsonobject.get("Success").getAsJsonObject();
			response = jobjsuccess.get("SuccessMsg").getAsString();
		}else {
			JsonArray jsonarr = jsonobject.get("Errors").getAsJsonArray();
			JsonObject jobjerror = jsonarr.get(0).getAsJsonObject();
			response = jobjerror.get("ErrorMessage").getAsString();
		}
		
		return response;	
	}
	
	
	
	@Override
	public String getAddPayment(HotelInfo hotel, String reservationid, String paymentid, String currencyid, String payment) {
		
		String res = "";
		String result = "";
		String response = "";
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    
	    JSONObject request = new JSONObject();
	    JSONObject payload = new JSONObject();
	    JSONObject auth = new JSONObject();
	    JSONObject reservation = new JSONObject();
	    List<JSONObject> reservationlist = new ArrayList<JSONObject>();
	    
	    
	    reservation.put("BookingId", reservationid);
	    reservation.put("PaymentId", paymentid);
	    reservation.put("CurrencyId", currencyid);
	    reservation.put("Payment", payment);
	    reservationlist.add(reservation);
	    
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
	
		payload.put("Request_Type", "AddPayment");        
		payload.put("Authentication", auth);
		payload.put("Reservation", reservationlist);
		request.put("RES_Request", payload);
		String json =  request.toString();
		
		String url = "https://live.ipms247.com/index.php/page/service.kioskconnectivity";
		
		JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
		List<OTAReservation> resrvationList = new ArrayList<OTAReservation>();
		
		String resp = jsonobject.toString();
		
		if(resp.contains("Success")) {
			JsonObject jobjsuccess = jsonobject.get("Success").getAsJsonObject();
			response = jobjsuccess.get("SuccessMsg").getAsString();
		}else {
			JsonArray jsonarr = jsonobject.get("Errors").getAsJsonArray();
			JsonObject jobjerror = jsonarr.get(0).getAsJsonObject();
			response = jobjerror.get("ErrorMessage").getAsString();
		}
		
		return response;	
	}
	
	
	
	@Override
	public String getGuestProfile(HotelInfo hotel, String reservationid, String firstname, String lastname, String gender, String type) {
		
		String res = "";
		String result = "";
		String response = "";
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    
	    JSONObject request = new JSONObject();
	    JSONObject payload = new JSONObject();
	    JSONObject auth = new JSONObject();
	    JSONObject sharer = new JSONObject();
	    List<JSONObject> sharerlist = new ArrayList<JSONObject>();
	    
	    
	    sharer.put("BookingId", reservationid);
	    sharer.put("FirstName", firstname);
	    sharer.put("LastName", lastname);
	    sharer.put("Gender", gender);
	    sharer.put("Type", type);
	    sharerlist.add(sharer);
	    
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
	
		payload.put("Request_Type", "AddSharer");        
		payload.put("Authentication", auth);
		payload.put("Sharers", sharerlist);
		request.put("RES_Request", payload);
		String json =  request.toString();
		
		String url = "https://live.ipms247.com/index.php/page/service.kioskconnectivity";
		
		JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
		List<OTAReservation> resrvationList = new ArrayList<OTAReservation>();
		
		String resp = jsonobject.toString();
		
		if(resp.contains("Success")) {
			JsonObject jobjsuccess = jsonobject.get("Success").getAsJsonObject();
			response = jobjsuccess.get("SuccessMsg").getAsString();
		}else {
			JsonArray jsonarr = jsonobject.get("Errors").getAsJsonArray();
			JsonObject jobjerror = jsonarr.get(0).getAsJsonObject();
			response = jobjerror.get("ErrorMessage").getAsString();
		}
		
		return response;	
	}
	
	
	
	
	
	@Override
	public String getGuestCheckIn(HotelInfo hotel, String reservationid, String guestname, String email, String address, String phone,String mobile) {
		
		String res = "";
		String result = "";
		String response = "No Data Found";
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    
	    JSONObject request = new JSONObject();
	    JSONObject payload = new JSONObject();
	    JSONObject auth = new JSONObject();
	    JSONObject reservation = new JSONObject();
	    List<JSONObject> reservationList = new ArrayList<JSONObject>();
	    
	    
	    reservation.put("BookingId", reservationid);
	    reservation.put("GuestName", guestname);
	    reservation.put("Email", email);
	    reservation.put("Address", address);
	    reservation.put("Phone", phone);
	    reservation.put("Mobile", mobile);
	    reservationList.add(reservation);
	    
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
	
		payload.put("Request_Type", "GuestCheckIn");        
		payload.put("Authentication", auth);
		payload.put("Reservation", reservationList);
		request.put("RES_Request", payload);
		String json =  request.toString();
		
		String url = "https://live.ipms247.com/index.php/page/service.kioskconnectivity";
		
		try {
		JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
		List<OTAReservation> resrvationList = new ArrayList<OTAReservation>();
		
		String resp = jsonobject.toString();

		if(resp.contains("Success")) {
			JsonObject jobjsuccess = jsonobject.get("Success").getAsJsonObject();
			response = jobjsuccess.get("SuccessMsg").getAsString();
		}else {
			JsonArray jsonarr = jsonobject.get("Error").getAsJsonArray();
			JsonObject jobjerror = jsonarr.get(0).getAsJsonObject();
			response = jobjerror.get("ErrorMessage").getAsString();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;	
	}
	
	
	
	@Override
	public String getRoomAssignment(HotelInfo hotel, String reservationid, String roomtypeid, String roomid) {
		
		String res = "";
		String result = "";
		String response = "";
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    
	    JSONObject request = new JSONObject();
	    JSONObject payload = new JSONObject();
	    JSONObject auth = new JSONObject();
	    JSONObject roomassign = new JSONObject();
	    List<JSONObject> roomassignList = new ArrayList<JSONObject>();
	    
	    
	    roomassign.put("BookingId", reservationid);
	    roomassign.put("RoomTypeID", roomtypeid);
	    roomassign.put("RoomID", roomid);
	    roomassignList.add(roomassign);
	    
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
	
		payload.put("Request_Type", "AssignRoom");        
		payload.put("Authentication", auth);
		payload.put("Reservation", roomassignList);
		request.put("RES_Request", payload);
		String json =  request.toString();
		
		String url = "https://live.ipms247.com/index.php/page/service.kioskconnectivity";
		
		try {
		JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
		List<OTAReservation> resrvationList = new ArrayList<OTAReservation>();
		
		String resp = jsonobject.toString();
		System.out.println("resp==>"+resp);
		
		if(resp.contains("Success")) {
			JsonObject jobjsuccess = jsonobject.get("Success").getAsJsonObject();
			response = jobjsuccess.get("SuccessMsg").getAsString();
		}else {
			JsonArray jsonarr = jsonobject.get("Error").getAsJsonArray();
			JsonObject jobjerror = jsonarr.get(0).getAsJsonObject();
			response = jobjerror.get("ErrorMessage").getAsString();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;	
	}


	@Override
	public String guestCheckOut(HotelInfo hotel, String reservationid) {

		String res = "";
		String result = "";
		String response = "";
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    
	    JSONObject request = new JSONObject();
	    JSONObject payload = new JSONObject();
	    JSONObject auth = new JSONObject();
	    JSONObject reservation = new JSONObject();
	    List<JSONObject> reservationList = new ArrayList<JSONObject>();
	    
	    reservation.put("BookingId", reservationid);
	    reservationList.add(reservation);
	    
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
	
		payload.put("Request_Type", "GuestCheckOut");        
		payload.put("Authentication", auth);
		payload.put("Reservation", reservationList);
		request.put("RES_Request", payload);
		String json =  request.toString();
		
		String url = "https://live.ipms247.com/index.php/page/service.kioskconnectivity";
		
		try {
		JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
		List<OTAReservation> resrvationList = new ArrayList<OTAReservation>();
		
		String resp = jsonobject.toString();
		
		if(resp.contains("Success")) {
			JsonObject jobjsuccess = jsonobject.get("Success").getAsJsonObject();
			response = jobjsuccess.get("SuccessMsg").getAsString();
		}else {
			JsonArray jsonarr = jsonobject.get("Error").getAsJsonArray();
			JsonObject jobjerror = jsonarr.get(0).getAsJsonObject();
			response = jobjerror.get("ErrorMessage").getAsString();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;	
	}
	
	
	
	@Override
	public  List<OTAFolioDTO> retrievelistofbills(HotelInfo hotel, String reservationid) {

		String res = "";
		String result = "";
		String response = "";
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    List<OTAFolioDTO> otafoliodtoList = new ArrayList<OTAFolioDTO>();
	    
	    JSONObject request = new JSONObject();
	    JSONObject payload = new JSONObject();
	    JSONObject auth = new JSONObject();
	    JSONObject reservation = new JSONObject();
	    List<JSONObject> reservationList = new ArrayList<JSONObject>();
	    reservationList.add(reservation);
	    
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		auth.put("BookingId", reservationid);
	
		payload.put("Request_Type", "RetrieveListofBills");        
		payload.put("Authentication", auth);
		request.put("RES_Request", payload);
		String json =  request.toString();
		
		String url = "https://live.ipms247.com/index.php/page/service.kioskconnectivity";
		
		try {
		JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
		List<OTAReservation> resrvationList = new ArrayList<OTAReservation>();
		
		String resp = jsonobject.toString();
		
		if(resp.contains("Success")) {
			JsonObject jobjsuccess = jsonobject.get("Success").getAsJsonObject();
			JsonArray jobjFoliolist = jobjsuccess.get("FolioList").getAsJsonArray();
			for(int i=0;i< jobjFoliolist.size();i++) {
				JsonObject jobjfolio = jobjFoliolist.get(i).getAsJsonObject();
				OTAFolioDTO otafoliodto = new OTAFolioDTO();
				int c = i+1;
				otafoliodto.setId(c); 
				otafoliodto.setFoliono(jobjfolio.get("foliono").getAsString());
				otafoliodto.setBilltocontact(jobjfolio.get("BillToContact").getAsString());
				otafoliodto.setGuestname(jobjfolio.get("GuestName").getAsString());
				otafoliodto.setCurrencycode(jobjfolio.get("CurrencyCode").getAsString());
				otafoliodto.setTotalcharges(jobjfolio.get("TotalCharges").getAsString());
				otafoliodto.setPaidamount(jobjfolio.get("PaidAmount").getAsString());
				otafoliodto.setDueamount(jobjfolio.get("DueAmount").getAsString());
				otafoliodtoList.add(otafoliodto);
			}
			
		}else {
			JsonObject jobjerror = jsonobject.get("Errors").getAsJsonObject();
			response = jobjerror.get("ErrorMessage").getAsString();
			OTAFolioDTO otafoliodto = new OTAFolioDTO();
			otafoliodto.setId(-1);
			otafoliodto.setGuestname(response); 
			otafoliodtoList.add(otafoliodto);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return otafoliodtoList;	
	}
	
	

	@Override
	public List<OTABookingTrans> retrievertransactiondetails(HotelInfo hotel, String tranunkid) {

		String res = "";
		String result = "";
		String response = "";
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
		List<OTABookingTrans> otabookingtransList = new ArrayList<OTABookingTrans>();
	    
	    JSONObject request = new JSONObject();
	    JSONObject payload = new JSONObject();
	    JSONObject auth = new JSONObject();
	    JSONObject reservation = new JSONObject();
	    List<JSONObject> reservationList = new ArrayList<JSONObject>();
	    reservationList.add(reservation);
	    
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
	
		payload.put("Request_Type", "GetTransactionDetails");     
		payload.put("TranunkId", tranunkid);
		payload.put("Authentication", auth);
		request.put("RES_Request", payload);
		String json =  request.toString();
		
		String url = "https://live.ipms247.com/index.php/page/service.kioskconnectivity";
		
		JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
		List<OTAReservation> resrvationList = new ArrayList<OTAReservation>();
		
		String resp = jsonobject.toString();
		System.out.println("resp==>"+resp);
		
		if(resp.contains("Error")) {
			JsonArray jobjerror = jsonobject.get("Error").getAsJsonArray();
			JsonObject jobjerr = jobjerror.get(0).getAsJsonObject();
			response = jobjerr.get("ErrorMessage").getAsString();
			
			OTABookingTrans otabookingtrans = new OTABookingTrans();
			otabookingtrans.setId(-1);
			otabookingtrans.setComment(response);
			
			otabookingtransList.add(otabookingtrans);
		} else {
			JsonObject jobjsuccess = jsonobject.get("Reservations").getAsJsonObject();
			JsonArray  jobjReservationarr = jobjsuccess.get("Reservation").getAsJsonArray();
		
			for (int i = 0; i < jobjReservationarr.size(); i++) { 
				JsonObject jobjbookingtrans = jobjReservationarr.get(i).getAsJsonObject();
				JsonArray  jobjbookingtransarr = jobjbookingtrans.get("BookingTran").getAsJsonArray();
				
				for (int j = 0; j < jobjbookingtransarr.size(); j++) { 
				try {
				JsonObject otabooking = jobjbookingtransarr.get(j).getAsJsonObject();
				OTABookingTrans otabookingtrans = new OTABookingTrans();
				int c = i + 1;
				otabookingtrans.setId(c);
				otabookingtrans.setSubbookingid(otabooking.get("SubBookingId").getAsString());
				String uniqueid = (otabooking.get("UniqueID").getAsString()); 
				int reseid = Integer.valueOf(uniqueid);
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
			
				otabookingtransList.add(otabookingtrans);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				}
			}
			}
		
		return otabookingtransList;	
	}
	
	
}