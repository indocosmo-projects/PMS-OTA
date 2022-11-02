package com.indocosmo.pms.web.ota.service.common;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;

import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.web.ota.dao.login.OTALoginDaoImpl;
import com.indocosmo.pms.web.ota.dto.hotel.HotelInfoDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;

@Service
public class OnlineTravelAgentServiceImpl implements OnlineTravelAgentServiceInterface {
	
	  
	@Autowired
	private OTALoginDaoImpl onlineTravelAgentDaoImpl;
	
	  private String hotelCode = "";
	  private String hotelname = "";
	  private String hotelauthkey = "";
	
	  public  ResponseEntity<String> getUrlContents(String url, String xmldata){  
		  RestTemplate restTemplate =  new RestTemplate();
		    List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		    messageConverters.add(new StringHttpMessageConverter());
		    restTemplate.setMessageConverters(messageConverters);

		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_XML);
		    HttpEntity<String> request = new HttpEntity<String>(xmldata, headers);
		    
		    ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		    return response;
	  }  
	  
	  
		public JsonObject  Post_JSON( String query_url,String json) {
		JsonObject jsonObject = null;
		try {
			URL url = new URL(query_url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			OutputStream os = conn.getOutputStream();
			os.write(json.getBytes("UTF-8"));
			os.close();
			InputStream in = new BufferedInputStream(conn.getInputStream());
			String result = IOUtils.toString(in, "UTF-8");
			System.out.println("===>result after Reading JSON Response ==>" + result);

			JsonParser jsonParser = new JsonParser();
			jsonObject = jsonParser.parse(result).getAsJsonObject();
			
			in.close();
			conn.disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}
	      return jsonObject ;
	}


	

	@Override
	public ResponseEntity<String> getHotelList() {
		String xmldata = "{    \r\n" + 
				"            \"RES_Request\": {\r\n" + 
				"            \"Request_Type\": \"RoomInfo\",\r\n" + 
				"            \"NeedPhysicalRooms\":1,\r\n" + 
				"            \"Authentication\": {\r\n" + 
				"                \"HotelCode\": "+ hotelCode +",\r\n" + 
				"                \"AuthCode\": "+hotelauthkey+"\r\n" + 
				"            }\r\n" + 
				"    }\r\n" + 
				"} ";
		String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
		
		ResponseEntity<String> response = getUrlContents(url,xmldata);
	    System.out.println("getHotelList===>"+ response);
	    
	    String data = response.getBody();
		
	    return response;
	}

	@Override
	public ResponseEntity<String> getHotelDetails() {
		String xmldata = "{    \r\n" + 
				"            \"RES_Request\": {\r\n" + 
				"            \"Request_Type\": \"HotelList\",\r\n" + 
				"            \"NeedPhysicalRooms\":1,\r\n" + 
				"            \"Authentication\": {\r\n" + 
				"                \"HotelCode\": "+ hotelCode +",\r\n" + 
				"                \"AuthCode\": "+hotelauthkey+"\r\n" + 
				"            }\r\n" + 
				"    }\r\n" + 
				"} ";
		String url = "https://live.ipms247.com/booking/reservation_api/listing.php";
		
		ResponseEntity<String> response = getUrlContents(url,xmldata);
	    System.out.println("getHotelDetails===>"+ response);
	    
	    String data = response.getBody();
		
	    return response;
	}
	
	@Override
	public ResponseEntity<String> getUpdateRoomRates() {
		String xmldata = "{    \r\n" + 
				"		 \"RES_Request\": {\r\n" + 
				"		         \"Request_Type\": \"UpdateRoomRates\",\r\n" + 
				"		         \"Authentication\": {\r\n" + 
				"		             \"HotelCode\": "+hotelCode+",\r\n" + 
				"		             \"AuthCode\": "+hotelauthkey+"\r\n" + 
				"		         },\r\n" + 
				"		        \"Sources\": {\r\n" + 
				"		             \"ContactId\": [\r\n" + 
				"		                 \"112400000000000873\",\r\n" + 
				"		                 \"112400000000000087\"\r\n" + 
				"		             ]\r\n" + 
				"		         },\r\n" + 
				"		         \"RateType\": [\r\n" + 
				"		             {\r\n" + 
				"		                 \"RoomTypeID\": \"112400000000000003\",\r\n" + 
				"		                 \"RateTypeID\": \"112400000000000002\",\r\n" + 
				"		                 \"FromDate\": \"2019-06-20\",\r\n" + 
				"		                 \"ToDate\": \"2019-06-25\",\r\n" + 
				"		                 \"RoomRate\": {\r\n" + 
				"		                     \"Base\": \"159\"\r\n" + 
				"		                 }\r\n" + 
				"		             },\r\n" + 
				"		             {\r\n" + 
				"		                 \"RoomTypeID\": \"112400000000000003\",\r\n" + 
				"		                 \"RateTypeID\": \"112400000000000002\",\r\n" + 
				"		                 \"FromDate\": \"2019-06-02\",\r\n" + 
				"		                 \"ToDate\": \"2019-06-06\",\r\n" + 
				"		                 \"RoomRate\": {\r\n" + 
				"		                     \"Base\": \"159\",\r\n" + 
				"		                     \"ExtraAdult\": \"80\",\r\n" + 
				"		                     \"ExtraChild\": \"50\"\r\n" + 
				"		                 }\r\n" + 
				"		             }\r\n" + 
				"		         ]\r\n" + 
				"		     }\r\n" + 
				"		 }";
		
		String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
		
		ResponseEntity<String> response = getUrlContents(url,xmldata);
	    System.out.println("getUpdateRoomRates===>"+ response);
	    
	    String data = response.getBody();
		
	    return response;
	}
	  
	@Override
	public ResponseEntity<String> getInventory() {
	String xmldata = "<?xml version=\"1.0\" standalone=\"yes\"?><RES_Request>   \r\n" + 
			"	<Request_Type>Inventory</Request_Type>\r\n" + 
			"	   <Authentication>\r\n" + 
			"	       <HotelCode>"+hotelCode+"</HotelCode>\r\n" + 
			"	       <AuthCode>"+hotelauthkey+"</AuthCode>\r\n" + 
			"	   </Authentication>\r\n" + 
			"	   <FromDate>2020-03-05</FromDate>\r\n" + 
			"	   <ToDate>2020-03-18</ToDate>\r\n" + 
			"	</RES_Request>";

		String url = "https://live.ipms247.com/index.php/page/service.pos2pms";
		
		ResponseEntity<String> response = getUrlContents(url, xmldata);
		System.out.println("getInventory===>"+ response);
		 
		String data = response.getBody();
		   
		return response;
	}

	
	

	
}
