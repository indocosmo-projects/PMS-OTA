package com.indocosmo.pms.web.ota.service.finance;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
import com.indocosmo.pms.web.ota.dao.finance.OTAExtrasDaoImpl;
import com.indocosmo.pms.web.ota.dto.finance.OTABillsDTO;
import com.indocosmo.pms.web.ota.dto.finance.OTADetailDTO;
import com.indocosmo.pms.web.ota.dto.finance.OTAFinancialAccountsDTO;
import com.indocosmo.pms.web.ota.dto.finance.OTAHotelExpensesDTO;
import com.indocosmo.pms.web.ota.dto.finance.OTARevenuesDTO;
import com.indocosmo.pms.web.ota.dto.finance.OTAoutinwDTO;
import com.indocosmo.pms.web.ota.entity.finance.OTAExtras;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.others.OTAGuestStatics;
import com.indocosmo.pms.web.ota.entity.reservation.OTAReservation;
import com.indocosmo.pms.web.ota.service.common.OnlineTravelAgentServiceImpl;

@Service
public class OTAFinanceServiceImpl implements OTAFinanceServiceInterface{
	
	@Autowired
	private OnlineTravelAgentServiceImpl onlineTravelAgentServiceImpl;
	
	
	@Autowired
	private OTAExtrasDaoImpl otaextrasdaoimpl;
	
	@Override
	public List<OTAExtras> getRetrieveExtras(HotelInfo hotel) {
		
		int o ;
		String res = "";
		String result = "";
		JSONArray jsonarrdata = null;
	    JSONObject jsondata = null;
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
	    ResponseEntity<String> response = null;
	    List<OTAExtras> extraslist = new ArrayList<OTAExtras>();   
	    
		try {
		   String url = "https://live.ipms247.com/booking/reservation_api/listing.php?request_type=ExtraCharges&APIKey="
				   +hotelauthkey+ "&HotelCode= "+hotelcode;
		    
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
			    	jsondata = jsonarrdata.getJSONObject(0) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
				JSONObject jobj = jsondata.getJSONObject("Error Details");
				String msg = jobj.get("Error_Message").toString();
				result = msg;
				OTAExtras extras = new OTAExtras();
				extras.setId(-1); 
				extras.setDescription(msg);
				extraslist.add(extras);
		}else {
				List<OTAExtras> otaextdb= otaextrasdaoimpl.getAllRecords();
				o = otaextrasdaoimpl.deleteAll(otaextdb.size());
				res = response.getBody();
		    	jsonarrdata = new JSONArray(res);
		    	for(int i=0; i< jsonarrdata.length(); i++) {
		    		jsondata = jsonarrdata.getJSONObject(i) ;
		    		OTAExtras extras = new OTAExtras();
		    		extras.setId(++i);
		    		extras.setExtrachargeid(jsondata.get("ExtraChargeId").toString());
		    		extras.setShortcode(jsondata.get("ShortCode").toString());
		    		extras.setCharge(jsondata.get("charge").toString());
		    		extras.setDescription(jsondata.get("description").toString());
		    		extras.setRate(jsondata.get("Rate").toString());
		    		extras.setChargerule(jsondata.get("ChargeRule").toString());
		    		extras.setPostingrule(jsondata.get("PostingRule").toString());
		    		extras.setValidfrom(jsondata.get("ValidFrom").toString());
		    		extras.setValidto(jsondata.get("ValidTo").toString());
		    		extras.setIschargealways(jsondata.get("ischargealways").toString());
		    		extras.setApplyon_rateplan(jsondata.get("applyon_rateplan").toString());
		    		extras.setApplyon_special(jsondata.get("applyon_special").toString());
		    		
		    		extraslist.add(extras);
		    	}
		    	
		    	int c = 0;
				for(OTAExtras otaextras : extraslist) {
					c++;
					otaextras.setId(c);
					o = otaextrasdaoimpl.save(otaextras);
				}
		}
	    
		return extraslist;
	
	}


	@Override
	public List<OTAExtras> getRetrieveExtrasFromDB() {
		
		List<OTAExtras> otaextraslistdb = otaextrasdaoimpl.getAllRecords();
		return otaextraslistdb;
		
	}

	
	@Override
	public List<OTAHotelExpensesDTO> getRetrievehotelexpenses(HotelInfo hotel, String fromdate, String todate) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		List<OTAHotelExpensesDTO>  otahotelexpensesdto = new ArrayList<OTAHotelExpensesDTO>();
		List<String>  str = new ArrayList<String>();
		
		JSONObject request = new JSONObject();
		request.put("authcode", hotelauthkey);
		request.put("hotel_code", hotelcode);
		request.put("fromdate", fromdate);
		request.put("todate", todate);
		
		String json = request.toString();
		
		String url = "https://live.ipms247.com/index.php/page/service.voucher";
	    String response = onlineTravelAgentServiceImpl.Post_JSON_CSV(url, json, hotel);
	    
		  int c = 0;
		  int d = 0;
		   try {
				 Scanner scanner = new Scanner(response);
				 while (scanner.hasNextLine()) {
				   c++;
				   String line = scanner.nextLine();
				   String arr[] = line.split(",");
				   str.add(line);
				   if(c != 1) {
				   OTAHotelExpensesDTO  otahotelexpenses = new OTAHotelExpensesDTO();
				   otahotelexpenses.setId(++d);
				   otahotelexpenses.setHotelname(onlineTravelAgentServiceImpl.removequotes(arr[0]));
				   otahotelexpenses.setHotelcode(onlineTravelAgentServiceImpl.removequotes(arr[1]));
				   otahotelexpenses.setVoucherno(onlineTravelAgentServiceImpl.removequotes(arr[2]));
				   otahotelexpenses.setContactinfo(onlineTravelAgentServiceImpl.removequotes(arr[3]));
				   otahotelexpenses.setExpensemadeto(onlineTravelAgentServiceImpl.removequotes(arr[4]));
				   otahotelexpenses.setPaidout(onlineTravelAgentServiceImpl.removequotes(arr[5]));
				   otahotelexpenses.setPaidoutcharges(onlineTravelAgentServiceImpl.removequotes(arr[6]));
				   otahotelexpenses.setPaidoutcurrency(onlineTravelAgentServiceImpl.removequotes(arr[7]));
				   otahotelexpenses.setPaymentmode(onlineTravelAgentServiceImpl.removequotes(arr[8]));  
				   otahotelexpenses.setPaymentcharges(onlineTravelAgentServiceImpl.removequotes(arr[9]));
				   otahotelexpenses.setPaymentcurrency(onlineTravelAgentServiceImpl.removequotes(arr[10]));
				   otahotelexpenses.setErrormsg("");
				   
				   otahotelexpensesdto.add(otahotelexpenses);
				   }
				 }
			} catch (Exception e) {
				e.printStackTrace();
			} 
		    if(str.size() == 1) {
		    	 OTAHotelExpensesDTO  otahotelexpenses = new OTAHotelExpensesDTO();
		    	 otahotelexpenses.setId(-1);
		    	 otahotelexpenses.setErrormsg(str.get(0));
		    	 otahotelexpensesdto.add(otahotelexpenses);
		    }
		    
		    return otahotelexpensesdto;
		}
	
	
	
	@Override
	public List<OTABillsDTO> getRetrievebills(HotelInfo hotel, String fromdate, String todate) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		List<OTABillsDTO>  otabillsList = new ArrayList<OTABillsDTO>();
		List<String>  str = new ArrayList<String>();
		
		JSONObject request = new JSONObject();
		request.put("authcode", hotelauthkey);
		request.put("hotel_code", hotelcode);
		request.put("fromdate", fromdate);
		request.put("todate", todate);
		String json = request.toString();
		
		String url = "https://live.ipms247.com/index.php/page/service.posting";
	    String response = onlineTravelAgentServiceImpl.Post_JSON_CSV(url, json, hotel);
		  int c = 0;
		  int d = 0;
		   try {
				 Scanner scanner = new Scanner(response);
				 while (scanner.hasNextLine()) {
				   c++;
				   String line = scanner.nextLine();
				   String arr[] = line.split(",");
				   str.add(line);
				   if(c != 1) {
				   OTABillsDTO  otabillsdto = new OTABillsDTO();
				   otabillsdto.setId(++d);
				   otabillsdto.setUniqueid(onlineTravelAgentServiceImpl.removequotes(arr[0]));
				   otabillsdto.setHotelname(onlineTravelAgentServiceImpl.removequotes(arr[1]));
				   otabillsdto.setHotelcode(onlineTravelAgentServiceImpl.removequotes(arr[2]));
				   otabillsdto.setFoliono(onlineTravelAgentServiceImpl.removequotes(arr[3]));
				   otabillsdto.setDate(onlineTravelAgentServiceImpl.removequotes(arr[4]));
				   otabillsdto.setVoucherno(onlineTravelAgentServiceImpl.removequotes(arr[5]));
				   otabillsdto.setInvoicenumber(onlineTravelAgentServiceImpl.removequotes(arr[6]));
				   otabillsdto.setGuestname(onlineTravelAgentServiceImpl.removequotes(arr[7]));
				   otabillsdto.setBilltoname(onlineTravelAgentServiceImpl.removequotes(arr[8]));  
				   otabillsdto.setGuestgstnumber(onlineTravelAgentServiceImpl.removequotes(arr[9]));
				   otabillsdto.setState(onlineTravelAgentServiceImpl.removequotes(arr[10]));
				   otabillsdto.setPhonenumber(onlineTravelAgentServiceImpl.removequotes(arr[11]));
				   otabillsdto.setMobilenumber(onlineTravelAgentServiceImpl.removequotes(arr[12]));
				   otabillsdto.setType(onlineTravelAgentServiceImpl.removequotes(arr[13]));
				   otabillsdto.setParticular(onlineTravelAgentServiceImpl.removequotes(arr[14]));
				   otabillsdto.setQty(onlineTravelAgentServiceImpl.removequotes(arr[15]));
				   otabillsdto.setCurrency(onlineTravelAgentServiceImpl.removequotes(arr[16]));
				   otabillsdto.setAmount(onlineTravelAgentServiceImpl.removequotes(arr[17]));  
				   otabillsdto.setGstrate(onlineTravelAgentServiceImpl.removequotes(arr[18]));  
				   otabillsdto.setCgsttaxamount(onlineTravelAgentServiceImpl.removequotes(arr[19]));
				   otabillsdto.setSgsttaxamount(onlineTravelAgentServiceImpl.removequotes(arr[20]));
				   otabillsdto.setIgsttaxamount(onlineTravelAgentServiceImpl.removequotes(arr[21]));
				   otabillsdto.setServicetax(onlineTravelAgentServiceImpl.removequotes(arr[22]));
				   otabillsdto.setLuxurytax(onlineTravelAgentServiceImpl.removequotes(arr[23]));
				   otabillsdto.setDiscount(onlineTravelAgentServiceImpl.removequotes(arr[24]));
				   otabillsdto.setAdjustment(onlineTravelAgentServiceImpl.removequotes(arr[25]));  
				   otabillsdto.setTotal(onlineTravelAgentServiceImpl.removequotes(arr[26]));
				   otabillsdto.setIsadvancedeposit(onlineTravelAgentServiceImpl.removequotes(arr[27]));
				   otabillsdto.setIsinclusion(onlineTravelAgentServiceImpl.removequotes(arr[28]));
				   otabillsdto.setPostedby(onlineTravelAgentServiceImpl.removequotes(arr[29]));
				   
				   otabillsList.add(otabillsdto);
				   }
				 }
			} catch (Exception e) {
				e.printStackTrace();
			} 
		    if(str.size() == 1) {
		    	 OTABillsDTO  otabillsdto = new OTABillsDTO();
		    	 otabillsdto.setId(-1);
		    	 otabillsdto.setErrormsg(str.get(0));
		    	 otabillsList.add(otabillsdto);
		    }
		    
		    return otabillsList;
		}

	
	@Override
	public List<OTAFinancialAccountsDTO> getRetrievefinancialaccounts(HotelInfo hotel) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		List<OTAFinancialAccountsDTO> otafinanceaccountsList = new ArrayList<OTAFinancialAccountsDTO>();
		List<String>  str = new ArrayList<String>();
		
		try {
		JSONObject request = new JSONObject();
		request.put("auth_code", hotelauthkey);
		request.put("hotel_code", hotelcode);
		request.put("requestfor", "XERO_GET_CONFIG_DATA");
		String json = request.toString();
		
		String url = "https://live.ipms247.com/index.php/page/service.PMSAccountAPI";
		JsonArray jsonarr = onlineTravelAgentServiceImpl.Post_JSONARR_Header(url, json, hotel);
		String res = jsonarr.toString();
		
		if(jsonarr == null) {
			otafinanceaccountsList = new ArrayList<OTAFinancialAccountsDTO>();
		}else {
			for(int i=0; i< jsonarr.size(); i++) {
				JsonObject jsonobj = jsonarr.get(i).getAsJsonObject();
				OTAFinancialAccountsDTO otafinancialaccountsdto = new OTAFinancialAccountsDTO();
				otafinancialaccountsdto.setId(i+1);
				otafinancialaccountsdto.setDescriptionunkid(jsonobj.get("descriptionunkid").getAsString());
				otafinancialaccountsdto.setDescription(jsonobj.get("description").getAsString());
				otafinancialaccountsdto.setDescriptiontypeunkid(jsonobj.get("descriptiontypeunkid").getAsString());
				otafinancialaccountsdto.setDescriptiontype(jsonobj.get("descriptiontype").getAsString());
				otafinancialaccountsdto.setHeaderid(jsonobj.get("headerid").getAsString()); 
				otafinancialaccountsdto.setHeader(jsonobj.get("header").getAsString()); 
				
				otafinanceaccountsList.add(otafinancialaccountsdto);
			}
		}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return otafinanceaccountsList ;
	}
	
	
	@Override
	public List<OTARevenuesDTO> getRetrieverevenues(HotelInfo hotel, String fromdate, String todate) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		String response = "";
		JsonObject jsonobj = null;
		JsonArray jsonarr = null;
		
		List<OTARevenuesDTO> otarevenuesdtoList = new ArrayList<OTARevenuesDTO>();
		List<OTADetailDTO> otadetaildtolist = new ArrayList<OTADetailDTO>();
		List<String>  str = new ArrayList<String>();
		
		try {
		JSONObject request = new JSONObject();
		request.put("auth_code", hotelauthkey);
		request.put("hotel_code", hotelcode);
		request.put("fromdate", fromdate);
		request.put("todate", todate);
		request.put("ischeckout", "true");
		request.put("requestfor", "XERO_GET_TRANSACTION_DATA");
		String json = request.toString();
		
		String url = "https://live.ipms247.com/index.php/page/service.PMSAccountAPI ";
		try {
			jsonobj = onlineTravelAgentServiceImpl.Post_JSON_Header(url, json, hotel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		try {
			jsonarr = onlineTravelAgentServiceImpl.Post_JSONARR_Header(url, json, hotel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		String res = jsonobj.toString();
		
		if(res.contains("Success")) {
			response = jsonobj.get("msg").getAsString();
			OTARevenuesDTO otarevenuesdto = new OTARevenuesDTO();
			otarevenuesdto.setId(-1);
			otarevenuesdto.setReference15(response);
			otarevenuesdtoList.add(otarevenuesdto);
		}else {
			try {
			for(int i=0; i< jsonarr.size(); i++) {
				JsonObject jsonobject = jsonarr.get(i).getAsJsonObject();
				OTARevenuesDTO otarevenuesdto = new OTARevenuesDTO();
				otarevenuesdto.setId(i+1);
				otarevenuesdto.setRecord_id(jsonobject.get("record_id").getAsString());
				otarevenuesdto.setRecord_date(jsonobject.get("record_date").getAsString());
				otarevenuesdto.setReference1(jsonobject.get("reference1").getAsString());
				otarevenuesdto.setReference2(jsonobject.get("reference2").getAsString());
				otarevenuesdto.setReference3(jsonobject.get("reference3").getAsString()); 
				otarevenuesdto.setReference4(jsonobject.get("reference4").getAsString()); 
				otarevenuesdto.setReference5(jsonobject.get("reference5").getAsString());
				otarevenuesdto.setReference6(jsonobject.get("reference6").getAsString());
				otarevenuesdto.setReference7(jsonobject.get("reference7").getAsString());
				otarevenuesdto.setReference8(jsonobject.get("reference8").getAsString()); 
				otarevenuesdto.setReference9(jsonobject.get("reference9").getAsString()); 
				otarevenuesdto.setReference10(jsonobject.get("reference10").getAsString());
				otarevenuesdto.setReference11(jsonobject.get("reference11").getAsString());
				otarevenuesdto.setReference12(jsonobject.get("reference12").getAsString());
				otarevenuesdto.setReference13(jsonobject.get("reference13").getAsString()); 
				otarevenuesdto.setReference14(jsonobject.get("reference14").getAsString()); 
				otarevenuesdto.setReference15(jsonobject.get("reference15").getAsString());
				otarevenuesdto.setReference16(jsonobject.get("reference16").getAsString());
				otarevenuesdto.setReference17(jsonobject.get("reference17").getAsString());
				otarevenuesdto.setReference18(jsonobject.get("reference18").getAsString()); 
				otarevenuesdto.setReference19(jsonobject.get("reference19").getAsString()); 
				otarevenuesdto.setReference20(jsonobject.get("reference20").getAsString());
				otarevenuesdto.setReference21(jsonobject.get("reference21").getAsString());
				otarevenuesdto.setGross_amount(jsonobject.get("gross_amount").getAsString());
				otarevenuesdto.setFlat_discount(jsonobject.get("flat_discount").getAsString()); 
				otarevenuesdto.setAdjustment_amount(jsonobject.get("adjustment_amount").getAsString()); 
				otarevenuesdto.setAdd_less_amount(jsonobject.get("add_less_amount").getAsString());
				otarevenuesdto.setTotal_amount(jsonobject.get("total_amount").getAsString());
				otarevenuesdto.setAmount_paid(jsonobject.get("amount_paid").getAsString());
				otarevenuesdto.setBalance(jsonobject.get("balance").getAsString()); 
				
				JsonArray detailjsonarr = jsonobject.get("detail").getAsJsonArray();
				for(int j=0; j< detailjsonarr.size(); j++) {
					OTADetailDTO otadetailDTO = new OTADetailDTO();
					otadetailDTO.setId(i+1);
					otadetailDTO.setDetail_record_id(jsonobject.get("detail_record_id").getAsString());
					otadetailDTO.setDetail_record_date(jsonobject.get("detail_record_date").getAsString());
					otadetailDTO.setReference_id(jsonobject.get("reference_id").getAsString());
					otadetailDTO.setReference_name(jsonobject.get("reference_name").getAsString());
					otadetailDTO.setSub_ref1_id(jsonobject.get("sub_ref1_id").getAsString());
					otadetailDTO.setSub_ref1_value(jsonobject.get("sub_ref1_value").getAsString());
					otadetailDTO.setSub_ref2_id(jsonobject.get("sub_ref2_id").getAsString());
					otadetailDTO.setSub_ref2_value(jsonobject.get("sub_ref2_value").getAsString());
					otadetailDTO.setSub_ref3_id(jsonobject.get("sub_ref3_id").getAsString());	
					otadetailDTO.setSub_ref3_value(jsonobject.get("sub_ref3_value").getAsString());
					otadetailDTO.setSub_ref4_id(jsonobject.get("sub_ref4_id").getAsString());
					otadetailDTO.setSub_ref4_value(jsonobject.get("sub_ref4_value").getAsString());
					otadetailDTO.setSub_ref5_id(jsonobject.get("sub_ref5_id").getAsString());
					otadetailDTO.setSub_ref5_value(jsonobject.get("sub_ref5_value").getAsString());
					otadetailDTO.setSub_ref6_id(jsonobject.get("sub_ref6_id").getAsString());
					otadetailDTO.setSub_ref6_value(jsonobject.get("sub_ref6_value").getAsString());
					otadetailDTO.setSub_ref7_id(jsonobject.get("sub_ref7_id").getAsString());
					otadetailDTO.setSub_ref7_value(jsonobject.get("sub_ref7_value").getAsString());
					otadetailDTO.setSub_ref8_id(jsonobject.get("sub_ref8_id").getAsString());
					otadetailDTO.setSub_ref8_value(jsonobject.get("sub_ref8_value").getAsString());
					otadetailDTO.setAmount(jsonobject.get("amount").getAsString());
					otadetailDTO.setTaxper(jsonobject.get("taxper").getAsString());
					otadetailDTO.setSlabtaxunkid(jsonobject.get("slabtaxunkid").getAsString());
					otadetailDTO.setSlabtax(jsonobject.get("slabtax").getAsString());
					otadetailDTO.setSlab(jsonobject.get("slab").getAsString());
					otadetailDTO.setCharge_name(jsonobject.get("charge_name").getAsString());
					otadetailDTO.setMasterunkid(jsonobject.get("masterunkid").getAsString());
					otadetailDTO.setParentmasterunkid(jsonobject.get("parentmasterunkid").getAsString());
					otadetailDTO.setDescription(jsonobject.get("description").getAsString());
					otadetailDTO.setTaxtype(jsonobject.get("Taxtype").getAsString());
					otadetailDTO.setPosdata(jsonobject.get("posdata").getAsString());
					otadetailDTO.setPostaxname(jsonobject.get("POSTaxName").getAsString());
					otadetailDTO.setPostaxpercent(jsonobject.get("POSTaxPercent").getAsString());
					
					otadetaildtolist.add(otadetailDTO);
				}
				otarevenuesdto.setDetail(otadetaildtolist); 
				otarevenuesdtoList.add(otarevenuesdto);
				}
				} catch (Exception e) {
					e.printStackTrace();
				}
			
		}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return otarevenuesdtoList;
	}
	
	

	@Override
	public List<OTAoutinwDTO> getRetrieveoutwardspayments(HotelInfo hotel, String fromdate, String todate) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		JsonArray jsonarr = null;
		JsonObject jsonobj = null;
		
		List<OTAoutinwDTO> otaoutinwdtoList = new ArrayList<OTAoutinwDTO>();
		
		try {
		JSONObject request = new JSONObject();
		request.put("auth_code", hotelauthkey);
		request.put("hotel_code", hotelcode);
		request.put("fromdate", fromdate);
		request.put("todate", todate);
		request.put("ischeckout", "true");
		request.put("requestfor", "XERO_GET_PAYMENT_DATA");
		String json = request.toString();
		
		String url = "https://live.ipms247.com/index.php/page/service.PMSAccountAPI ";

		try {
			jsonobj = onlineTravelAgentServiceImpl.Post_JSON_Header(url, json, hotel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String res = jsonobj.toString();
		
	    jsonarr = jsonobj.get("data").getAsJsonArray();
		
		if(res.contains("Success") == true) {
			for(int i=0; i< jsonarr.size(); i++) {
				JsonObject jsontype = jsonarr.get(i).getAsJsonObject();
				OTAoutinwDTO otaoutinwdto = new OTAoutinwDTO();
				otaoutinwdto.setId(i+1);
				otaoutinwdto.setType(jsontype.get("type").getAsString());
				otaoutinwdto.setTransid("");
				otaoutinwdto.setTran_datetime("");
				otaoutinwdto.setName("");
				otaoutinwdto.setEmail(""); 
				otaoutinwdto.setLocation("");
				otaoutinwdto.setGrossamount(""); 
				otaoutinwdto.setRemark(""); 
				try {
						JsonArray jsonarrdata =  jsonobj.get("data").getAsJsonArray();
						if(jsonarrdata != null && jsonarrdata.size() > 0) {
						JsonObject jsondatatype = jsonarrdata.get(0).getAsJsonObject();
						otaoutinwdto.setTransid(jsondatatype.get("tranId").getAsString());
						otaoutinwdto.setTran_datetime(jsondatatype.get("tran_datetime").getAsString());
						otaoutinwdto.setName(jsondatatype.get("reference7").getAsString());
						otaoutinwdto.setEmail(jsondatatype.get("reference19").getAsString()); 
						otaoutinwdto.setLocation(jsondatatype.get("reference20").getAsString());
						otaoutinwdto.setGrossamount(jsondatatype.get("gross_amount").getAsString()); 
						otaoutinwdto.setRemark(jsondatatype.get("remark").getAsString()); 
						}
				} catch (Exception e) {
					e.printStackTrace();
				}
				otaoutinwdtoList.add(otaoutinwdto);
			}
		} 
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return otaoutinwdtoList ;
	}
	
	
	@Override
	public List<OTAoutinwDTO> getRetrieveinwardspayments(HotelInfo hotel,String fromdate, String todate) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		JsonArray jsonarr = null;
		JsonObject jsonobj = null;
		
		List<OTAoutinwDTO> otaoutinwdtoList = new ArrayList<OTAoutinwDTO>();
		
		try {
		JSONObject request = new JSONObject();
		request.put("auth_code", hotelauthkey);
		request.put("hotel_code", hotelcode);
		request.put("fromdate", fromdate);
		request.put("todate", todate);
		request.put("requestfor", "XERO_GET_RECEIPT_DATA");

		String json = request.toString();
		
		String url = "https://live.ipms247.com/index.php/page/service.PMSAccountAPI";
		jsonobj = onlineTravelAgentServiceImpl.Post_JSON_Header(url, json, hotel);
		String res = jsonobj.toString();
		
	    jsonarr = jsonobj.get("data").getAsJsonArray();
		if(res.contains("Success") == true) {
			for(int i=0; i< jsonarr.size(); i++) {
				JsonObject jsontype = jsonarr.get(i).getAsJsonObject();
				OTAoutinwDTO otaoutinwdto = new OTAoutinwDTO();
				otaoutinwdto.setId(i+1);
				otaoutinwdto.setType(jsontype.get("type").getAsString());
				otaoutinwdto.setTransid("");
				otaoutinwdto.setTran_datetime("");
				otaoutinwdto.setName("");
				otaoutinwdto.setEmail(""); 
				otaoutinwdto.setLocation("");
				otaoutinwdto.setGrossamount(""); 
				otaoutinwdto.setRemark(""); 
				try {
						JsonArray jsonarrdata =  jsonobj.get("data").getAsJsonArray();
						if(jsonarrdata != null && jsonarrdata.size() > 0) {
						JsonObject jsondatatype = jsonarrdata.get(0).getAsJsonObject();
						otaoutinwdto.setTransid(jsondatatype.get("tranId").getAsString());
						otaoutinwdto.setTran_datetime(jsondatatype.get("tran_datetime").getAsString());
						otaoutinwdto.setName(jsondatatype.get("reference7").getAsString());
						otaoutinwdto.setEmail(jsondatatype.get("reference19").getAsString()); 
						otaoutinwdto.setLocation(jsondatatype.get("reference20").getAsString());
						otaoutinwdto.setGrossamount(jsondatatype.get("gross_amount").getAsString()); 
						otaoutinwdto.setRemark(jsondatatype.get("remark").getAsString()); 
						}
				} catch (Exception e) {
					e.printStackTrace();
				}
				otaoutinwdtoList.add(otaoutinwdto);
			}
		}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return otaoutinwdtoList ;
	}
	
	
	
	
	@Override
	public List<OTAoutinwDTO> getRetrievejournals(HotelInfo hotel,String fromdate, String todate) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		JsonArray jsonarr = null;
		JsonObject jsonobj = null;
		
		List<OTAoutinwDTO> otaoutinwdtoList = new ArrayList<OTAoutinwDTO>();
		
		try {
		JSONObject request = new JSONObject();
		request.put("auth_code", hotelauthkey);
		request.put("hotel_code", hotelcode);
		request.put("fromdate", fromdate);
		request.put("todate", todate);
		request.put("requestfor", "XERO_GENERAL_JOURNAL_INFO");

		String json = request.toString();
		
		String url = "https://live.ipms247.com/index.php/page/service.PMSAccountAPI";
		jsonobj = onlineTravelAgentServiceImpl.Post_JSON_Header(url, json, hotel);
		String res = jsonobj.toString();
		
	    jsonarr = jsonobj.get("data").getAsJsonArray();
		if(res.contains("Success") == true) {
			for(int i=0; i< jsonarr.size(); i++) {
				JsonObject jsontype = jsonarr.get(i).getAsJsonObject();
				OTAoutinwDTO otaoutinwdto = new OTAoutinwDTO();
				otaoutinwdto.setId(i+1);
				otaoutinwdto.setType(jsontype.get("type").getAsString());
				otaoutinwdto.setTransid("");
				otaoutinwdto.setTran_datetime("");
				otaoutinwdto.setName("");
				otaoutinwdto.setEmail(""); 
				otaoutinwdto.setLocation("");
				otaoutinwdto.setGrossamount(""); 
				otaoutinwdto.setRemark(""); 
				try {
						JsonArray jsonarrdata =  jsonobj.get("data").getAsJsonArray();
						if(jsonarrdata != null && jsonarrdata.size() > 0) {
						JsonObject jsondatatype = jsonarrdata.get(0).getAsJsonObject();
						otaoutinwdto.setTransid(jsondatatype.get("tranId").getAsString());
						otaoutinwdto.setTran_datetime(jsondatatype.get("tran_datetime").getAsString());
						otaoutinwdto.setName(jsondatatype.get("reference7").getAsString());
						otaoutinwdto.setEmail(jsondatatype.get("reference19").getAsString()); 
						otaoutinwdto.setLocation(jsondatatype.get("reference20").getAsString());
						otaoutinwdto.setGrossamount(jsondatatype.get("gross_amount").getAsString()); 
						otaoutinwdto.setRemark(jsondatatype.get("remark").getAsString()); 
						}
				} catch (Exception e) {
					e.printStackTrace();
				}
				otaoutinwdtoList.add(otaoutinwdto);
			}
		}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return otaoutinwdtoList ;
	}
	
	
	
	@Override
	public List<OTAoutinwDTO> getRetrieveincidentalinvoices(HotelInfo hotel,String fromdate, String todate) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		JsonArray jsonarr = null;
		JsonObject jsonobj = null;
		
		List<OTAoutinwDTO> otaoutinwdtoList = new ArrayList<OTAoutinwDTO>();
		
		try {
		JSONObject request = new JSONObject();
		request.put("auth_code", hotelauthkey);
		request.put("hotel_code", hotelcode);
		request.put("fromdate", fromdate);
		request.put("todate", todate);
		request.put("requestfor", "XERO_INCIDENTAL_INVOICE");

		String json = request.toString();
		
		String url = "https://live.ipms247.com/index.php/page/service.PMSAccountAPI";
		jsonobj = onlineTravelAgentServiceImpl.Post_JSON_Header(url, json, hotel);
		String res = jsonobj.toString();
		
	    jsonarr = jsonobj.get("data").getAsJsonArray();
		if(res.contains("Success") == true) {
			for(int i=0; i< jsonarr.size(); i++) {
				JsonObject jsontype = jsonarr.get(i).getAsJsonObject();
				OTAoutinwDTO otaoutinwdto = new OTAoutinwDTO();
				otaoutinwdto.setId(i+1);
				otaoutinwdto.setType(jsontype.get("type").getAsString());
				otaoutinwdto.setTransid("");
				otaoutinwdto.setTran_datetime("");
				otaoutinwdto.setName("");
				otaoutinwdto.setEmail(""); 
				otaoutinwdto.setLocation("");
				otaoutinwdto.setGrossamount(""); 
				otaoutinwdto.setRemark(""); 
				try {
						JsonArray jsonarrdata =  jsonobj.get("data").getAsJsonArray();
						if(jsonarrdata != null && jsonarrdata.size() > 0) {
						JsonObject jsondatatype = jsonarrdata.get(0).getAsJsonObject();
						otaoutinwdto.setTransid(jsondatatype.get("tranId").getAsString());
						otaoutinwdto.setTran_datetime(jsondatatype.get("tran_datetime").getAsString());
						otaoutinwdto.setName(jsondatatype.get("reference7").getAsString());
						otaoutinwdto.setEmail(jsondatatype.get("reference19").getAsString()); 
						otaoutinwdto.setLocation(jsondatatype.get("reference20").getAsString());
						otaoutinwdto.setGrossamount(jsondatatype.get("gross_amount").getAsString()); 
						otaoutinwdto.setRemark(jsondatatype.get("remark").getAsString()); 
						}
				} catch (Exception e) {
					e.printStackTrace();
				}
				otaoutinwdtoList.add(otaoutinwdto);
			}
		}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return otaoutinwdtoList ;
	}
	
	
	
	
	
	
}
