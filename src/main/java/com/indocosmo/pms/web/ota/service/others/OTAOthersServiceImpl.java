package com.indocosmo.pms.web.ota.service.others;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.web.ota.dto.housekeeping.OTAHouseStatusDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.housekeeping.OTARoomList;
import com.indocosmo.pms.web.ota.entity.others.OTACompanies;
import com.indocosmo.pms.web.ota.entity.others.OTAGuestStatics;
import com.indocosmo.pms.web.ota.service.common.OnlineTravelAgentServiceImpl;

@Service
public class OTAOthersServiceImpl implements OTAOthersServiceInterface{

	@Autowired
	private OnlineTravelAgentServiceImpl onlineTravelAgentServiceImpl;
	
	@Override
	public List<OTAGuestStatics> getRetrievegueststatics(HotelInfo hotel) {
		
		int o = 0;
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		List<OTAGuestStatics>  otaguestlist = new ArrayList<OTAGuestStatics>();
		
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
		System.out.println("==>json"+json); 
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
		
	    return otacompanylist;
	    /*
	    	{   
			"RES_Request": {
			        "Request_Type": "CompanyList",
			        "Authentication": {
			           "HotelCode": "18727",
			           "AuthCode": "d44f6590f2c3@9148790807c57666de-bb8c-11ea-a"
			        },
			       "Ids": ["abc4578lk","yzx7426kj"],
			       "Names": ["33Comp"],
			        "Created": {
			           "from_date": "2019-12-05",
			           "to_date": "2019-12-10"
			       },
			       "Updated": {
			           "from_date": "2019-12-05",
			           "to_date": "2019-12-10"
			       },
			       "isActive":"1"
			       }
			}
		*/
		
	}


	public List<OTAGuestStatics> getRetrievegueststaticsFromDB() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
