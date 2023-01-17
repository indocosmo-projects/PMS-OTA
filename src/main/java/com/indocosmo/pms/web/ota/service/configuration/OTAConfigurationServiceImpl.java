package com.indocosmo.pms.web.ota.service.configuration;

import java.util.ArrayList;
import java.util.List;

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
import com.indocosmo.pms.web.ota.dto.booking.OTAFolioDTO;
import com.indocosmo.pms.web.ota.dto.booking.OTARoomListDTO;
import com.indocosmo.pms.web.ota.dto.hotel.HotelInfoDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTACurrencyDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTAPaymentGatewayDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTAPaymethodsDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomTypeDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.room.OTARoomRoomTypes;
import com.indocosmo.pms.web.ota.service.common.OnlineTravelAgentServiceImpl;

@Service
public class OTAConfigurationServiceImpl implements OTAConfigurationServiceInterface{

	@Autowired
	private OnlineTravelAgentServiceImpl onlineTravelAgentServiceImpl;
	
	@Override
	public List<HotelInfo> getCheckhotelauthentication(String authkey) {

		List<HotelInfo> hotelList = new  ArrayList<HotelInfo>();
		String xmldata = "<?xml version=\"1.0\" standalone=\"yes\"?><request><auth>"+authkey+"</auth><oprn>gethotelinfo</oprn></request>";
		String url = "https://live.ipms247.com/index.php/page/service.pos2pms";
		
		JSONObject jobj = onlineTravelAgentServiceImpl.getUrlContents(url, xmldata);
		String response = jobj.toString();
		
		if(response.contains("success")) {
			HotelInfo hotelinfo = new HotelInfo();
			JSONObject jobjresponse = (JSONObject) jobj.get("response");
			hotelinfo.setId(1);
			hotelinfo.setHotelname(jobjresponse.get("hotelname").toString());
			hotelinfo.setHotelcode(jobjresponse.get("hotelcode").toString());
			hotelList.add(hotelinfo);
		}else {
			HotelInfo hotelinfo = new HotelInfo();
			JSONObject jobjresponse = (JSONObject) jobj.get("response");
			hotelinfo.setId(-1);
			hotelinfo.setHotelname(jobjresponse.get("msg").toString());
			hotelList.add(hotelinfo);
		}
		
		return hotelList;
	}

	@Override
	public List<HotelInfoDTO> getRetrievehotelinformation(String hotelcode, String authkey) {

		String res = "";
		String result = "";
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    ResponseEntity<String> response = null;
	    List<HotelInfoDTO> otahotelinfoList = new ArrayList<HotelInfoDTO>();
	    
		try {
			
		String url = 
				"https://live.ipms247.com/booking/reservation_api/listing.php?request_type=HotelList&HotelCode="+hotelcode+"&APIKey="+authkey+"&language=en";
			        RestTemplate template = new RestTemplate();
			        HttpHeaders header = new HttpHeaders();
			        HttpEntity requestEntity = new HttpEntity(header);
			        response = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
			        res = response.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
				
				if(res.contains("Error")) {
					HotelInfoDTO otahotelinfo = new HotelInfoDTO();
					  try {
					    	res = response.getBody();
					    	jsonarrdata = new JSONArray(res);
					    	jsondata =jsonarrdata.getJSONObject(0);
						} catch (Exception e) {
							e.printStackTrace();
						}
						JSONObject jobj = jsondata.getJSONObject("Error Details");
						String msg = jobj.get("Error_Message").toString();
						otahotelinfo.setId(-1);
						otahotelinfo.setHotelname(msg);
						otahotelinfoList.add(otahotelinfo);
						
				}else {
					 try {
					    	res = response.getBody();
					    	jsonarrdata = new JSONArray(res);
					    	for(int i=0; i < jsonarrdata.length() ;i++ ) {
						    	jsondata = jsonarrdata.getJSONObject(i);
						    	int c= i + 1;
						    	HotelInfoDTO hotelinfo = new HotelInfoDTO();
						    	hotelinfo.setId(c);
						    	hotelinfo.setHotelname(jsondata.get("Hotel_Name").toString());
						    	hotelinfo.setHotelcode(jsondata.get("Hotel_Code").toString());
						    	hotelinfo.setHotelunkid(jsondata.get("hotelunkid").toString());
						    	hotelinfo.setCountry(jsondata.get("Country").toString());
						    	hotelinfo.setPropertytype(jsondata.get("Property_Type").toString());
						    	hotelinfo.setBookingengineurl(jsondata.get("BookingEngineURL").toString());
						    	hotelinfo.setPhone(jsondata.get("Phone").toString());
						    	hotelinfo.setEmail(jsondata.get("Email").toString());
						    	hotelinfo.setBookingenginefoldername(jsondata.get("BookingEngineFolderName").toString());
						    	hotelinfo.setCurrencycode(jsondata.get("CurrencyCode").toString());
						 
						    	otahotelinfoList.add(hotelinfo);
					    	}
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
					
		return otahotelinfoList;
		
	}

	
	@Override
	public List<HotelInfoDTO> getRetrievehotelamenities(String hotelcode, String authkey) {

		String res = "";
		String result = "";
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    ResponseEntity<String> response = null;
	    List<HotelInfoDTO> otahotelinfoList = new ArrayList<HotelInfoDTO>();
	    
		try {
			
		String url = "https://live.ipms247.com/booking/reservation_api/listing.php?request_type=HotelAmenity&HotelCode="+hotelcode+"&APIKey="+authkey+"&language=en";
			        RestTemplate template = new RestTemplate();
			        HttpHeaders header = new HttpHeaders();
			        HttpEntity requestEntity = new HttpEntity(header);
			        response = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
			        res = response.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
				
				if(res.contains("Error")) {
					HotelInfoDTO otahotelinfo = new HotelInfoDTO();
					  try {
					    	res = response.getBody();
					    	jsonarrdata = new JSONArray(res);
					    	jsondata =jsonarrdata.getJSONObject(0);
						} catch (Exception e) {
							e.printStackTrace();
						}
						JSONObject jobj = jsondata.getJSONObject("Error Details");
						String msg = jobj.get("Error_Message").toString();
						otahotelinfo.setId(-1);
						otahotelinfo.setAmenity(msg);
						otahotelinfoList.add(otahotelinfo);
						
				}else {
					 try {
					    	res = response.getBody();
					    	jsonarrdata = new JSONArray(res);
					    	for(int i=0; i < jsonarrdata.length() ;i++ ) {
						    	jsondata = jsonarrdata.getJSONObject(i);
						    	int c= i + 1;
						    	HotelInfoDTO hotelinfo = new HotelInfoDTO();
						    	hotelinfo.setId(c);
						    	hotelinfo.setAmenity(jsondata.get("amenity").toString());
						 
						    	otahotelinfoList.add(hotelinfo);
					    	}
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
					
		return otahotelinfoList;	
	}

	
	@Override
	public List<OTARoomTypeDTO> getRetrieveroomtypes(String hotelcode, String authkey, String publishtoweb) {

		String res = "";
		String result = "";
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    ResponseEntity<String> response = null;
	    List<OTARoomTypeDTO> otaroomtypedtoList = new ArrayList<OTARoomTypeDTO>();
	    
		try {
			
		String url = "https://live.ipms247.com/booking/reservation_api/listing.php?request_type=RoomTypeList&HotelCode="+hotelcode+"&APIKey="+authkey+"&language=en&publishtoweb="+publishtoweb;
					RestTemplate template = new RestTemplate();
			        HttpHeaders header = new HttpHeaders();
			        HttpEntity requestEntity = new HttpEntity(header);
			        response = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
			        res = response.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
				
				if(res.contains("Error")) {
					OTARoomTypeDTO otaroomtype = new OTARoomTypeDTO();
					  try {
					    	res = response.getBody();
					    	jsonarrdata = new JSONArray(res);
					    	jsondata =jsonarrdata.getJSONObject(0);
						} catch (Exception e) {
							e.printStackTrace();
						}
						JSONObject jobj = jsondata.getJSONObject("Error Details");
						String msg = jobj.get("Error_Message").toString();
						otaroomtype.setId(-1);
						otaroomtype.setMsg(msg);
						otaroomtypedtoList.add(otaroomtype);
						
				}else {
					 try {
					    	res = response.getBody();
					    	jsonarrdata = new JSONArray(res);
					    	for(int i=0; i < jsonarrdata.length() ;i++ ) {
						    	jsondata = jsonarrdata.getJSONObject(i);
						    	int c= i + 1;
						    	OTARoomTypeDTO otaroomtype = new OTARoomTypeDTO();
						    	otaroomtype.setId(c);
						    	otaroomtype.setRoomtypeunkid(jsondata.get("roomtypeunkid").toString());
						    	otaroomtype.setRoomtype(jsondata.get("roomtype").toString());
						    	otaroomtype.setShortcode(jsondata.get("shortcode").toString());
						    	otaroomtype.setBase_adult_occupancy(jsondata.get("base_adult_occupancy").toString());
						    	otaroomtype.setBase_child_occupancy(jsondata.get("base_child_occupancy").toString());
						    	otaroomtype.setMax_adult_occupancy(jsondata.get("max_adult_occupancy").toString());
						    	otaroomtype.setMax_child_occupancy(jsondata.get("max_child_occupancy").toString());
						 
						    	otaroomtypedtoList.add(otaroomtype);
					    	}
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
					
		return otaroomtypedtoList;	
	}

	
	@Override
	public List<OTARoomTypeDTO> getRetrievesalutationsandcountry(String hotelcode, String authkey) {

		String res = "";
		String result = "";
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    ResponseEntity<String> response = null;
	    List<OTARoomTypeDTO> otaroomtypedtoList = new ArrayList<OTARoomTypeDTO>();
	    
		try {
			
		String url = 
				"https://live.ipms247.com/booking/reservation_api/listing.php?request_type=ConfiguredDetails&HotelCode="+hotelcode+"&APIKey="+authkey+"&language=en";
					RestTemplate template = new RestTemplate();
			        HttpHeaders header = new HttpHeaders();
			        HttpEntity requestEntity = new HttpEntity(header);
			        response = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
			        res = response.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
				
				if(res.contains("Error")) {
					OTARoomTypeDTO otaroomtype = new OTARoomTypeDTO();
					  try {
					    	res = response.getBody();
					    	jsonarrdata = new JSONArray(res);
					    	jsondata =jsonarrdata.getJSONObject(0);
						} catch (Exception e) {
							e.printStackTrace();
						}
						JSONObject jobj = jsondata.getJSONObject("Error Details");
						String msg = jobj.get("Error_Message").toString();
						otaroomtype.setId(-1);
						otaroomtype.setMsg(msg);
						otaroomtypedtoList.add(otaroomtype);
						
				}else {
					 try {
					    		res = response.getBody();
						    	jsondata = new JSONObject(res);
						    	int c=  1;
						    	List<String> salutation = new ArrayList<String>();
						    	List<String> country = new ArrayList<String>();
						    	
						    	OTARoomTypeDTO otaroomtype = new OTARoomTypeDTO();
						    	otaroomtype.setId(c);
						    	String jsonsal = jsondata.get("Salutation").toString();
						    	String[] arrsal = jsonsal.substring(1, jsonsal.length()-1).split(",");
						    	for(String str : arrsal) {
						    		salutation.add(str);
						    	}
						    	
						    	String jsoncoun = jsondata.get("CountryList").toString();
						    	String[] arrcoun = jsoncoun.substring(1, jsoncoun.length()-1).split(",");
						    	for(String str1 : arrcoun) {
						    		country.add(str1);
						    	}
						    	
						    	otaroomtype.setSalutation(salutation);
						    	otaroomtype.setCountry(country);
						 
						    	otaroomtypedtoList.add(otaroomtype);
					    	
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
					
		return otaroomtypedtoList;	
		
	}
	
	
	
	@Override
	public List<OTARoomTypeDTO> getRetrieveextrasratebasedonparameters(String hotelcode, String authkey, String checkindate,
			String checkoutdate, String extrachargeid, String extraitem) {

		String res = "";
		String result = "";
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    ResponseEntity<String> response = null;
	    List<OTARoomTypeDTO> otaroomtypedtoList = new ArrayList<OTARoomTypeDTO>();
	    
		try {
			
		String url = "https://live.ipms247.com/booking/reservation_api/listing.php?request_type=CalculateExtraCharge&HotelCode="+hotelcode+"&APIKey="+authkey+"&check_in_date="+checkindate+"&check_out_date="+checkoutdate+"&ExtraChargeId="+extrachargeid+"&Total_ExtraItem="+extraitem;
					RestTemplate template = new RestTemplate();
			        HttpHeaders header = new HttpHeaders();
			        HttpEntity requestEntity = new HttpEntity(header);
			        response = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
			        res = response.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
				
				if(res.contains("Error")) {
					OTARoomTypeDTO otaroomtype = new OTARoomTypeDTO();
					  try {
					    	res = response.getBody();
					    	jsonarrdata = new JSONArray(res);
					    	jsondata =jsonarrdata.getJSONObject(0);
						} catch (Exception e) {
							e.printStackTrace();
						}
						JSONObject jobj = jsondata.getJSONObject("Error Details");
						String msg = jobj.get("Error_Message").toString();
						otaroomtype.setId(-1);
						otaroomtype.setMsg(msg);
						otaroomtypedtoList.add(otaroomtype);
						
				}else {
					 try {
					    		res = response.getBody();
						    	jsondata = new JSONObject(res);
						    	int c =  1;
						    	List<String> individualcharge = new ArrayList<String>();
						    	List<String> country = new ArrayList<String>();
						    	
						    	OTARoomTypeDTO otaroomtype = new OTARoomTypeDTO();
						    	otaroomtype.setId(c);
						    	String jsonind = jsondata.get("IndividualCharge").toString();
						    	String[] arrsal = jsonind.substring(1, jsonind.length()-1).split(",");
						    	for(String str : arrsal) {
						    		individualcharge.add(str);
						    	}
						    	otaroomtype.setIndividualcharge(individualcharge);
						    	otaroomtype.setTotalcharge(jsondata.get("TotalCharge").toString());
						 
						    	otaroomtypedtoList.add(otaroomtype);
					    
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
					
		return otaroomtypedtoList;	
	}

	
	@Override
	public List<OTARoomTypeDTO> getVerifytravelagent(String hotelcode, String authkey, String username, String password,
			String groupcode) {

		String res = "";
		String result = "";
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    ResponseEntity<String> response = null;
	    List<OTARoomTypeDTO> otaroomtypedtoList = new ArrayList<OTARoomTypeDTO>();
	    
		try {
			
		String url = 
				"https://live.ipms247.com/booking/reservation_api/listing.php?APIKey="+authkey+"&request_type=VerifyUser&username="+username+"&password="+password+"&groupcode="+groupcode;
					RestTemplate template = new RestTemplate();
			        HttpHeaders header = new HttpHeaders();
			        HttpEntity requestEntity = new HttpEntity(header);
			        response = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
			        res = response.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
				
				if(res.contains("Error")) {
					OTARoomTypeDTO otaroomtype = new OTARoomTypeDTO();
					  try {
					    	res = response.getBody();
					    	jsonarrdata = new JSONArray(res);
					    	jsondata =jsonarrdata.getJSONObject(0);
						} catch (Exception e) {
							e.printStackTrace();
						}
						JSONObject jobj = jsondata.getJSONObject("Error Details");
						String msg = jobj.get("Error_Message").toString();
						otaroomtype.setId(-1);
						otaroomtype.setMsg(msg);
						otaroomtypedtoList.add(otaroomtype);
						
				}else {
					 try {
					    		res = response.getBody();
						    	jsondata = new JSONObject(res);
						    	int c =  1;
						    	List<String> individualcharge = new ArrayList<String>();
						    	
						    	OTARoomTypeDTO otaroomtype = new OTARoomTypeDTO();
						    	otaroomtype.setId(c);
						    	String jsoncontactdetail = jsondata.get("contact_detail").toString();
						    	
						    	otaroomtype.setContactname("contactname"); 
						    	otaroomtype.setBusinessname("businessname");
						    	otaroomtype.setEmail("email"); 
						 
						    	otaroomtypedtoList.add(otaroomtype);
					    
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
					
		return otaroomtypedtoList;	
	}

	
	
	
	@Override
	public List<OTAPaymentGatewayDTO> getPaymentgateways(String hotelcode, String authkey) {

		String res = "";
		String result = "";
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    ResponseEntity<String> response = null;
	    List<OTAPaymentGatewayDTO> otapaymentgatewayList = new ArrayList<OTAPaymentGatewayDTO>();
	    
		try {
			
		String url =  "https://live.ipms247.com/booking/reservation_api/listing.php?APIKey="+authkey+"&request_type=ConfiguredPGList&HotelCode="+hotelcode;
					RestTemplate template = new RestTemplate();
			        HttpHeaders header = new HttpHeaders();
			        HttpEntity requestEntity = new HttpEntity(header);
			        response = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
			        res = response.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
				
				if(res.contains("Error")) {
					OTAPaymentGatewayDTO otapaymentgateway = new OTAPaymentGatewayDTO();
					  try {
					    	res = response.getBody();
					    	jsonarrdata = new JSONArray(res);
					    	jsondata =jsonarrdata.getJSONObject(0);
						} catch (Exception e) {
							e.printStackTrace();
						}
						JSONObject jobj = jsondata.getJSONObject("Error Details");
						String msg = jobj.get("Error_Message").toString();
						otapaymentgateway.setId(-1);
						otapaymentgateway.setShortcode(msg);
						otapaymentgatewayList.add(otapaymentgateway);
						
				}else {
					 try {
						 res = response.getBody();
					    	jsonarrdata = new JSONArray(res);
					    	for(int i=0; i < jsonarrdata.length() ;i++ ) {
						    	jsondata = jsonarrdata.getJSONObject(i);
						    	int c= i + 1;
						    	OTAPaymentGatewayDTO otapaymentgateway = new OTAPaymentGatewayDTO();
						    	otapaymentgateway.setId(c);
						    	otapaymentgateway.setPaymenttypeunkid(jsondata.get("paymenttypeunkid").toString());
						    	otapaymentgateway.setHotel_code(jsondata.get("hotel_code").toString());;
						    	otapaymentgateway.setPaymenttype(jsondata.get("paymenttype").toString());;
						    	otapaymentgateway.setShortcode(jsondata.get("shortcode").toString());;
						    	
						    	otapaymentgatewayList.add(otapaymentgateway);
					    	}
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
				
		return otapaymentgatewayList;	
	}
	
	
	
	@Override
	public List<OTACurrencyDTO> getRetrievecurrency(String hotelcode, String authkey) {
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject auth = new JSONObject();
		List<OTACurrencyDTO> otacurrencydtolist = new ArrayList<OTACurrencyDTO>();
		
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", authkey);
		payload.put("Request_Type", "RetrieveCurrency");        
		payload.put("Authentication", auth);
		request.put("RES_Request", payload);
		String json =  request.toString();
		
		String url = "https://live.ipms247.com/index.php/page/service.kioskconnectivity";
		JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
		String res = jsonobject.toString();
		
		if(res.contains("Success")) {
			 try {
				    JsonObject jobjSuccess = jsonobject.get("Success").getAsJsonObject();
				    JsonArray jobjCurrency = jobjSuccess.get("CurrencyList").getAsJsonArray();
			    	for(int i=0; i < jobjCurrency.size() ;i++ ) {
			    		JsonObject jsondata = jobjCurrency.get(i).getAsJsonObject();
				    	int c= i + 1;
				    	OTACurrencyDTO otacurrencydto = new OTACurrencyDTO();
				    	otacurrencydto.setId(c);
				    	otacurrencydto.setCurrencyid(jsondata.get("CurrencyID").getAsString());
				    	otacurrencydto.setCountry(jsondata.get("Country").getAsString());
				    	otacurrencydto.setCurrency(jsondata.get("Currency").getAsString());
				    	otacurrencydto.setCurrencycode(jsondata.get("CurrencyCode").getAsString());
				    	otacurrencydto.setSign(jsondata.get("Sign").getAsString());
				    	otacurrencydto.setDigitsafterdecimal(jsondata.get("DigitsAfterDecimal").getAsString());
				    	otacurrencydto.setIsbasecurrency(jsondata.get("IsBaseCurrency").getAsString()); 
				    	otacurrencydto.setExchangerate(jsondata.get("ExchangeRate").getAsString()); 
				    	
				    	otacurrencydtolist.add(otacurrencydto);
			    	}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}else{
			  try {
				  JsonObject jobjerror = jsonobject.get("Error").getAsJsonObject();
					String msg = jobjerror.get("ErrorMessage").getAsString();
					OTACurrencyDTO otacurrencydto = new OTACurrencyDTO();
					otacurrencydto.setId(-1);
					otacurrencydto.setCountry(msg);
					otacurrencydtolist.add(otacurrencydto);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
		}
		
		
		return otacurrencydtolist;
	}

	
	
	
	@Override
	public List<OTAPaymethodsDTO> getRetrievepaymethods(String hotelcode, String authkey) {
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject auth = new JSONObject();
		List<OTAPaymethodsDTO> otapaymethodsDTOList = new ArrayList<OTAPaymethodsDTO>();
		
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", authkey);
		payload.put("Request_Type", "RetrievePayMethods");        
		payload.put("Authentication", auth);
		request.put("RES_Request", payload);
		String json =  request.toString();
		
		String url = "https://live.ipms247.com/index.php/page/service.kioskconnectivity";
		JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
		String res = jsonobject.toString();
		
		if(res.contains("Success")) {
			 try {
				    JsonObject jobjSuccess = jsonobject.get("Success").getAsJsonObject();
				    JsonArray jobjPaymethods = jobjSuccess.get("PayMethods").getAsJsonArray();
			    	for(int i=0; i < jobjPaymethods.size() ;i++ ) {
			    		JsonObject jsondata = jobjPaymethods.get(i).getAsJsonObject();
				    	int c= i + 1;
				    	OTAPaymethodsDTO otapaymethods = new OTAPaymethodsDTO();
				    	otapaymethods.setId(c);
				    	otapaymethods.setPaymethodid(jsondata.get("PayMethodID").getAsString());
				    	otapaymethods.setPaymentid(jsondata.get("PaymentID").getAsString());
				    	otapaymethods.setName(jsondata.get("Name").getAsString());
				    	otapaymethods.setShortcode(jsondata.get("ShortCode").getAsString());
				    	otapaymethods.setType(jsondata.get("Type").getAsString());
				    	otapaymethods.setCardprocessing(jsondata.get("CardProcessing").getAsString());
				    	otapaymethods.setSurchargeapplicable(jsondata.get("SurchargeApplicable").getAsString());
				    	otapaymethods.setSurchargetype(jsondata.get("SurchargeType").getAsString());
				    	otapaymethods.setSurchargevalue(jsondata.get("SurchargeValue").getAsString());
				    	otapaymethods.setSurchargeid(jsondata.get("SurchargeID").getAsString());
				    	otapaymethods.setSurchargename(jsondata.get("SurchargeName").getAsString());
				    	
				    	otapaymethodsDTOList.add(otapaymethods);
			    	}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}else{
			  try {
				  JsonObject jobjerror = jsonobject.get("Error").getAsJsonObject();
					String msg = jobjerror.get("ErrorMessage").getAsString();
					OTAPaymethodsDTO otapaymethods = new OTAPaymethodsDTO();
					otapaymethods.setId(-1);
					otapaymethods.setSurchargename(msg);
					otapaymethodsDTOList.add(otapaymethods);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
		}
	
		return otapaymethodsDTOList;
	}

	
	@Override
	public List<OTAPaymethodsDTO> getRetrieveidentitytype(String hotelcode, String authkey) {

		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject auth = new JSONObject();
		List<OTAPaymethodsDTO> otapaymethodsDTOList = new ArrayList<OTAPaymethodsDTO>();
		
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", authkey);
		payload.put("Request_Type", "RetrieveIdentityType");        
		payload.put("Authentication", auth);
		request.put("RES_Request", payload);
		String json =  request.toString();
		
		String url = "https://live.ipms247.com/index.php/page/service.kioskconnectivity";
		JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
		String res = jsonobject.toString();
		
		if(res.contains("Success")) {
			 try {
				    JsonObject jobjSuccess = jsonobject.get("Success").getAsJsonObject();
				    JsonArray jobjPaymethods = jobjSuccess.get("IdentityType").getAsJsonArray();
			    	for(int i=0; i < jobjPaymethods.size() ;i++ ) {
			    		JsonObject jsondata = jobjPaymethods.get(i).getAsJsonObject();
				    	int c = i + 1;
				    	OTAPaymethodsDTO otapaymethods = new OTAPaymethodsDTO();
				    	otapaymethods.setId(c);
				    	otapaymethods.setName(jsondata.get("Name").getAsString());
				    	otapaymethods.setIdentitytypeid(jsondata.get("IdentityTypeID").getAsString());
				    	
				    	otapaymethodsDTOList.add(otapaymethods);
			    	}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}else{
			  try {
				  JsonObject jobjerror = jsonobject.get("Error").getAsJsonObject();
					String msg = jobjerror.get("ErrorMessage").getAsString();
					OTAPaymethodsDTO otapaymethods = new OTAPaymethodsDTO();
					otapaymethods.setId(-1);
					otapaymethods.setSurchargename(msg);
					otapaymethodsDTOList.add(otapaymethods);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
		}
	
		return otapaymethodsDTOList;
		
	}
	
	@Override
	public List<OTARoomRoomTypes> getRetrieveroomlist(String hotelcode, String authkey,String fromdate, String todate) {
	
			JSONObject request = new JSONObject();
			JSONObject payload = new JSONObject();
			JSONObject auth = new JSONObject();
			JSONObject roomdata = new JSONObject();
			List<OTARoomRoomTypes> otaroomroomtypesList = new ArrayList<OTARoomRoomTypes>();
			
			auth.put("HotelCode", hotelcode);
			auth.put("AuthCode", authkey);
			roomdata.put("from_date", fromdate);
			roomdata.put("to_date", todate);
			payload.put("Request_Type", "RoomAvailability");        
			payload.put("Authentication", auth);
			payload.put("RoomData", roomdata);
			
			request.put("RES_Request", payload);
			String json =  request.toString();
			
			String url = "https://live.ipms247.com/index.php/page/service.kioskconnectivity";
			JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
			String res = jsonobject.toString();
			int c  = 0;
			if(res.contains("Success")) {
				 try {
					    JsonObject jobjSuccess = jsonobject.get("Success").getAsJsonObject();
					    JsonArray jobjRoomList = jobjSuccess.get("RoomList").getAsJsonArray();
				    	for(int i=0; i < jobjRoomList.size() ;i++ ) {
				    		JsonObject jsondata = jobjRoomList.get(i).getAsJsonObject();
				    		JsonArray jobjRoomdata = jsondata.get("RoomData").getAsJsonArray();
				    			for(int j=0; j < jobjRoomdata.size() ;j++ ) {
				    				JsonObject jsonroomdata = jobjRoomdata.get(j).getAsJsonObject();
							    	++c;
							    	OTARoomRoomTypes otaroomroomtypes = new OTARoomRoomTypes();
							    	otaroomroomtypes.setId(c);
							    	otaroomroomtypes.setRoomtypesid(jsondata.get("RoomtypeID").getAsString());
							    	otaroomroomtypes.setRoomtypename(jsondata.get("RoomtypeName").getAsString());
							    	otaroomroomtypes.setRoomid(jsonroomdata.get("RoomID").getAsString());
							    	otaroomroomtypes.setRoomname(jsonroomdata.get("RoomName").getAsString());
							    	
							    	otaroomroomtypesList.add(otaroomroomtypes);
				    		}
				    	}
					} catch (Exception e) {
						e.printStackTrace();
					}
			}else{
				  try {
					  JsonObject jobjerror = jsonobject.get("Error").getAsJsonObject();
						String msg = jobjerror.get("ErrorMessage").getAsString();
						OTARoomRoomTypes otaroomroomtypes = new OTARoomRoomTypes();
						otaroomroomtypes.setId(-1);
						otaroomroomtypes.setRoomname(msg);
						otaroomroomtypesList.add(otaroomroomtypes);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
			}

	return otaroomroomtypesList;
	
}


}
