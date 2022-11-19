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
import org.json.XML;
import org.json.JSONObject;

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
	
	  public  JSONObject getUrlContents(String url, String xmldata){  
		  RestTemplate restTemplate =  new RestTemplate();
		    List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		    messageConverters.add(new StringHttpMessageConverter());
		    restTemplate.setMessageConverters(messageConverters);

		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_XML);
		    HttpEntity<String> request = new HttpEntity<String>(xmldata, headers);
		    
		    ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		    JSONObject jsondata = null;
		    try {
		    	jsondata = XML.toJSONObject(response.getBody());
			} catch (Exception e) {
				e.printStackTrace();
			}
		    
		    return jsondata;
	  }  
	  
	  
		public JsonObject  Post_JSON(String query_url,String json) {
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

			JsonParser jsonParser = new JsonParser();
			jsonObject = jsonParser.parse(result).getAsJsonObject();
			
			in.close();
			conn.disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}
	      return jsonObject ;
		}
		
		
		public JsonObject  Post_JSON_Header(String query_url,String json, HotelInfo hotel) {
			JsonObject jsonObject = null;
			try {
				URL url = new URL(query_url);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("AUTH_CODE", hotel.getAuthkey());
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setRequestMethod("POST");
				OutputStream os = conn.getOutputStream();
				os.write(json.getBytes("UTF-8"));
				os.close();
				InputStream in = new BufferedInputStream(conn.getInputStream());
				String result = IOUtils.toString(in, "UTF-8");
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
	public ResponseEntity<String> getUpdateRoomRates() {
		String xmldata = "{    \r\n" + 
				"		 \"RES_Request\": {\r\n" + 
				"		         \"Request_Type\": \"UpdateRoomRates\",\r\n" + 
				"		         \"Authentication\": {\r\n" + 
				"		             \"HotelCode\": "+"hotelCode"+",\r\n" + 
				"		             \"AuthCode\": "+"hotelauthkey"+"\r\n" + 
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
		
		JSONObject response = getUrlContents(url,xmldata);
	    System.out.println("getUpdateRoomRates===>"+ response);
	    
	    
		
	    return null;
	}
	

	
}
