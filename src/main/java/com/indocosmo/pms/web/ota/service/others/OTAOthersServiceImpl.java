package com.indocosmo.pms.web.ota.service.others;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
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
import com.google.gson.JsonParser;
import com.indocosmo.pms.web.ota.dao.others.OTACompanyDaoImpl;
import com.indocosmo.pms.web.ota.dao.others.OTAGuestStaticsDaoImpl;
import com.indocosmo.pms.web.ota.dao.others.OTATADaoImpl;
import com.indocosmo.pms.web.ota.dto.housekeeping.OTAHouseStatusDTO;
import com.indocosmo.pms.web.ota.entity.finance.OTAExtras;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.housekeeping.OTARoomList;
import com.indocosmo.pms.web.ota.entity.others.OTACompanies;
import com.indocosmo.pms.web.ota.entity.others.OTAGuestStatics;
import com.indocosmo.pms.web.ota.entity.reservation.OTABookingTrans;
import com.indocosmo.pms.web.ota.service.common.OnlineTravelAgentServiceImpl;

@Service
public class OTAOthersServiceImpl implements OTAOthersServiceInterface{

	@Autowired
	private OnlineTravelAgentServiceImpl onlineTravelAgentServiceImpl;
	
	@Autowired
	private OTAGuestStaticsDaoImpl otagueststaticsdaoimpl;
	
	@Autowired
	private OTACompanyDaoImpl otacompanydaoimpl;
	
	@Autowired
	private OTATADaoImpl otatravelagentdaoimpl;
	
	
	@Override
	public List<OTAGuestStatics> getRetrievegueststatics(HotelInfo hotel) {
		
		int o = 0;
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		List<OTAGuestStatics>  otaguestlist = new ArrayList<OTAGuestStatics>();
		
		List<OTAGuestStatics>  otaguestlistDB = otagueststaticsdaoimpl.getAllRecords();
		o = otagueststaticsdaoimpl.deleteAll(otaguestlistDB.size());
		
		JSONObject request = new JSONObject();
		request.put("hotel_code", hotelcode);
		request.put("authkey", hotelauthkey);
		
		String json = request.toString();
		String url = "https://live.ipms247.com/index.php/page/service.guestdatabase";
		
	    String result = onlineTravelAgentServiceImpl.Post_JSON_CSV(url, json, hotel);
	    JsonObject jsonObject = null;
	    int c = 0;
	    int d = 0;
	    try {
			 Scanner scanner = new Scanner(result);
			 while (scanner.hasNextLine()) {
			   c++;
			   String line = scanner.nextLine();
			   String arr[] = line.split(",");
			   if(c != 1) {
			   OTAGuestStatics  otaguest = new  OTAGuestStatics();
			   otaguest.setId(++d);
			   otaguest.setGuestname(onlineTravelAgentServiceImpl.removequotes(arr[0]));
			   otaguest.setGuestemail(onlineTravelAgentServiceImpl.removequotes(arr[1]));
			   otaguest.setTotalnumberofstays(onlineTravelAgentServiceImpl.removequotes(arr[2]));
			   otaguest.setFirststay(onlineTravelAgentServiceImpl.removequotes(arr[3]));
			   otaguest.setFirstreservationno(onlineTravelAgentServiceImpl.removequotes(arr[4]));
			   otaguest.setFirstfoliono(onlineTravelAgentServiceImpl.removequotes(arr[5]));
			   otaguest.setLaststay(onlineTravelAgentServiceImpl.removequotes(arr[6]));
			   otaguest.setLastreservationno(onlineTravelAgentServiceImpl.removequotes(arr[7]));
			   otaguest.setLastfoliono(onlineTravelAgentServiceImpl.removequotes(arr[8]));  
			   otaguest.setNextstay(onlineTravelAgentServiceImpl.removequotes(arr[9]));
			   otaguest.setNextreservationno(onlineTravelAgentServiceImpl.removequotes(arr[10]));
			   otaguest.setNextfoliono(onlineTravelAgentServiceImpl.removequotes(arr[11]));
			   otaguest.setLifetimespending(onlineTravelAgentServiceImpl.removequotes(arr[12]));
			   
			   otaguestlist.add(otaguest);
			   }
			 }
			 scanner.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    int k = 0;
		for(OTAGuestStatics otaguest : otaguestlist) {
			k++;
			otaguest.setId(k);
			o = otagueststaticsdaoimpl.save(otaguest);
		}
	    
		return otaguestlist;
	}
	
	
	@Override
	public List<OTACompanies> getretrievecompany(HotelInfo hotel,String[] ids,String[] names,String createdfromdate,
			String createdtodate,String updatedfromdate,String updatedtodate,String isactive) {
		
		int o = 0;
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		List<OTACompanies> otacompanylist = new ArrayList<OTACompanies>();
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject auth = new JSONObject();
		JSONObject created = new JSONObject();
		JSONObject update = new JSONObject();
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Request_Type", "CompanyList");
		payload.put("Authentication", auth);
		payload.put("Ids", ids);
		payload.put("Names", names);
		created.put("from_date", createdfromdate);
		created.put("to_date", createdtodate);
		update.put("from_date", updatedfromdate);
		update.put("to_date", updatedtodate);
		payload.put("Created", created);
		payload.put("Updated", update);
		payload.put("isActive", isactive);
		request.put("RES_Request", payload);
		
		String json = request.toString();
		String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
		
	    JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON_Header(url, json, hotel);
	    String jojbstr = jsonobject.toString();
	    String response = "";
	    
	    if(jojbstr.contains("Errors")) {
		    JsonObject jobjerror = jsonobject.get("Errors").getAsJsonObject();
		    String errormsg = jobjerror.get("ErrorMessage").getAsString();
		    String errorcode = jobjerror.get("ErrorCode").getAsString();
		    response = errormsg;
		    OTACompanies otacompany = new OTACompanies();
		    otacompany.setId(response);
			otacompanylist.add(otacompany);
	    }else {
		    try {
		    JsonArray jsonCompany = jsonobject.get("Companies").getAsJsonArray();
		    
		    if(jsonCompany.size() > 0) {
		    	for(int i = 0; i< jsonCompany.size();i++) {
		    		JsonObject jobjotacompany = jsonCompany.get(i).getAsJsonObject();
		    		OTACompanies otacompany = new OTACompanies();
		    		
		    		otacompany.setId(jobjotacompany.get("Id").getAsString());
		    		otacompany.setAccountname(jobjotacompany.get("AccountName").getAsString());
		    		otacompany.setAccountcode(jobjotacompany.get("AccountCode").getAsString());
		    		otacompany.setContact_person(jobjotacompany.get("Contact_person").getAsString());
		    		otacompany.setAddress(jobjotacompany.get("Address").getAsString());
		    		otacompany.setCity(jobjotacompany.get("City").getAsString());
		    		otacompany.setPostalcode(jobjotacompany.get("PostalCode").getAsString());
		    		otacompany.setState(jobjotacompany.get("State").getAsString());
		    		otacompany.setCountry(jobjotacompany.get("Country").getAsString());
		    		otacompany.setPhone(jobjotacompany.get("Phone").getAsString());
		    		otacompany.setMobile(jobjotacompany.get("Mobile").getAsString());
		    		otacompany.setFax(jobjotacompany.get("Fax").getAsString());
		    		otacompany.setEmail(jobjotacompany.get("Email").getAsString());
		    		otacompany.setTaxid(jobjotacompany.get("TaxId").getAsString());
		    		otacompany.setRegistrationno(jobjotacompany.get("RegistrationNo").getAsString());
		    		otacompany.setIsactive(jobjotacompany.get("IsActive").getAsString());
		    		
		    		otacompanylist.add(otacompany);
		    }
		    }
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	    
		for(OTACompanies otacomp : otacompanylist) {
			o = otacompanydaoimpl.save(otacomp);
		}
		
	    return otacompanylist;
		
	}

	@Override
	public List<OTAGuestStatics> getRetrievegueststaticsFromDB() {
		
		List<OTAGuestStatics> otaguestlist = otagueststaticsdaoimpl.getAllRecords();
		return otaguestlist;
		
	}

	@Override
	public List<OTACompanies> getotaretrievetravelagent(HotelInfo hotel,String[] id,String[] name,String createdfromdate,
			String createdtodate,String updatedfromdate,String updatedtodate,String isactive) {
		
		int o = 0;
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		List<OTACompanies> otatravellist = new ArrayList<OTACompanies>();
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject auth = new JSONObject();
		JSONObject created = new JSONObject();
		JSONObject update = new JSONObject();
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Request_Type", "TravelAgentList");
		payload.put("Authentication", auth);
		payload.put("Ids", id);
		payload.put("Names", name);
		created.put("from_date", createdfromdate);
		created.put("to_date", createdtodate);
		update.put("from_date", updatedfromdate);
		update.put("to_date", updatedtodate);
		payload.put("Created", created);
		payload.put("Updated", update);
		payload.put("isActive", isactive);
		request.put("RES_Request", payload);	
		
	    
		String json = request.toString();
		String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
		
	    JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON_Header(url, json, hotel);
	    String jojbstr = jsonobject.toString();
	    String response = "";
	    if(jojbstr.contains("Errors")) {
		    JsonObject jobjerror = jsonobject.get("Errors").getAsJsonObject();
		    String errormsg = jobjerror.get("ErrorMessage").getAsString();
		    String errorcode = jobjerror.get("ErrorCode").getAsString();
		    response = errormsg;
		    OTACompanies otacompany = new OTACompanies();
		    otacompany.setId(response);
		    otatravellist.add(otacompany);
	    }else {
		    try {
		    JsonArray jsontravelagent = jsonobject.get("TravelAgent").getAsJsonArray();
		    
		    if(jsontravelagent.size() > 0) {
		    	for(int i = 0; i< jsontravelagent.size();i++) {
		    		JsonObject jobjotatravel = jsontravelagent.get(i).getAsJsonObject();
		    		OTACompanies otatravel = new OTACompanies();
		    		
		    		otatravel.setId(jobjotatravel.get("Id").getAsString());
		    		otatravel.setAccountname(jobjotatravel.get("AccountName").getAsString());
		    		otatravel.setAccountcode(jobjotatravel.get("AccountCode").getAsString());
		    		otatravel.setContact_person(jobjotatravel.get("Contact_person").getAsString());
		    		otatravel.setAddress(jobjotatravel.get("Address").getAsString());
		    		otatravel.setCity(jobjotatravel.get("City").getAsString());
		    		otatravel.setPostalcode(jobjotatravel.get("PostalCode").getAsString());
		    		otatravel.setState(jobjotatravel.get("State").getAsString());
		    		otatravel.setCountry(jobjotatravel.get("Country").getAsString());
		    		otatravel.setPhone(jobjotatravel.get("Phone").getAsString());
		    		otatravel.setMobile(jobjotatravel.get("Mobile").getAsString());
		    		otatravel.setFax(jobjotatravel.get("Fax").getAsString());
		    		otatravel.setEmail(jobjotatravel.get("Email").getAsString());
		    		otatravel.setTaxid(jobjotatravel.get("TaxId").getAsString());
		    		otatravel.setRegistrationno(jobjotatravel.get("RegistrationNo").getAsString());
		    		otatravel.setRegistrationno(jobjotatravel.get("CommissionPlan").getAsString());
		    		otatravel.setRegistrationno(jobjotatravel.get("CommissionValue").getAsString());
		    		otatravel.setRegistrationno(jobjotatravel.get("Discount on the standard rate %").getAsString());
		    		otatravel.setIsactive(jobjotatravel.get("IsActive").getAsString());
		    		
		    		otatravellist.add(otatravel);
		    }
		    }
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	    
		for(OTACompanies otacomp : otatravellist) {
			o = otatravelagentdaoimpl.save(otacomp);
		}
		
	    return otatravellist;
	}


	@Override
	public String getCreatetravelagent(HotelInfo hotel,String user,String businessname,
			String country,String email,String percentdiscount) {
		
		String res = "";
		String result = "";
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
	    ResponseEntity<String> response = null;
		
		try {
			
		String url = "https://live.ipms247.com/booking/reservation_api/listing.php?APIKey="+hotelauthkey+"&request_type=InsertTravelAgent&name="+user+"&businessname="+businessname+"&salutation=MR&country="+country+"&email="+email+"&HotelCode="+hotelcode+"&percentdiscount="+percentdiscount+"&businesssource=true&isusercreated=true&ismailsend=true";
    
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
					result = "Successfully Created Travel Agent";
				}
	    
		return result;
	}
	
	
	
	@Override
	public List<OTACompanies> getGuestList(HotelInfo hotel) {
		
		int o = 0;
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		List<OTACompanies> otacompanyList = new ArrayList<OTACompanies>();
		List<OTAGuestStatics>  otaguestlistDB = otagueststaticsdaoimpl.getAllRecords();
		o = otagueststaticsdaoimpl.deleteAll(otaguestlistDB.size());
		
		JSONObject request = new JSONObject();
		JSONObject auth = new JSONObject();
		JSONObject payload = new JSONObject();
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Request_Type", "GuestList");
		payload.put("Authentication", auth);
		request.put("RES_Request",payload);
		
		String json = request.toString();
		String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
		
		 JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON_Header(url, json, hotel);
		 String jojbstr = jsonobject.toString();
		 String response = "";
		
		    if(jojbstr.contains("Errors")) {
			    JsonObject jobjerror = jsonobject.get("Errors").getAsJsonObject();
			    String errormsg = jobjerror.get("ErrorMessage").getAsString();
			    String errorcode = jobjerror.get("ErrorCode").getAsString();
			    response = errormsg;
			    OTACompanies otacompany = new OTACompanies();
			    otacompany.setId(response);
			    otacompanyList.add(otacompany);
		    }else {
			    try {
			    JsonArray jsonCompany = jsonobject.get("Guests").getAsJsonArray();
			    
			    if(jsonCompany.size() > 0) {
			    	for(int i = 0; i< jsonCompany.size();i++) {
			    		JsonObject jobjotacompany = jsonCompany.get(i).getAsJsonObject();
			    		OTACompanies otacompany = new OTACompanies();
			    		otacompany.setId(jobjotacompany.get("Id").getAsString());
			    		otacompany.setContact_person(jobjotacompany.get("Contact_person").getAsString());
			    		otacompany.setAddress(jobjotacompany.get("Type").getAsString());
			    		
			    		otacompanyList.add(otacompany);
			    }
			    }
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		    
		return otacompanyList;
	}

	
}
