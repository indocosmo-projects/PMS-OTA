package com.indocosmo.pms.web.request.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.web.checkIn.model.CheckInRate;
import com.indocosmo.pms.web.checkIn.model.CheckInRequest;
import com.indocosmo.pms.web.checkIn.model.CheckInRequestStatus;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.request.service.RequestService;
import com.indocosmo.pms.web.shift.model.Shift;
import com.indocosmo.pms.web.shiftManagement.model.ShiftManagement;
import com.indocosmo.pms.web.shiftManagement.service.ShiftManagementService;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;

@Controller
@RequestMapping("/requestCheckin")
public class RequestController {
	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;
	
	@Autowired
	private ShiftManagementService shiftManagementService;

	@Autowired
	SystemSettingsService systemSettingsService; 
	@Autowired
	private RequestService requestService;

	private SysPermissions permissionObj;
	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "access_denied/access_denied";


	@RequestMapping(value="listaddon", method = RequestMethod.GET)
	public String dashbord2(HttpServletRequest request,Model model,HttpSession session)throws Exception{
		String pageUrl="requestCheckin/requestCheckin_list";
		permissionObj=pageAccessPermissionService.getPermission(session,"PMS_CHKIN_REQUEST");
		if(permissionObj.isCanView() && permissionObj.isIs_view_applicable()){
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
			model.addAttribute("curPagePerObj",permissionObj);
			model.addAttribute("hotelDate",formatter.format(commonSettings.getHotelDate()));
			String count=shiftManagementService.isshiftOPen(formatter.format(commonSettings.getHotelDate()));
			
			 model.addAttribute("count", count);
		}else{
			pageUrl= SYDEF_PERMISION_DENIED_PAGE_URL;
		}		
		return pageUrl;
	}


	@RequestMapping(value="/getRoomLists",method=RequestMethod.POST)
	public  @ResponseBody String getRoomLists(HttpServletRequest request,HttpSession session){
		JsonArray jarryRoom=new JsonArray();
		jarryRoom=requestService.getRoomLists();
		return jarryRoom.toString();
	}
	@RequestMapping(value="/getFacilities",method=RequestMethod.POST)
	public @ResponseBody String getFacilities(HttpServletRequest request,HttpSession session){
		JsonArray jarryRoom=new JsonArray();
		jarryRoom=requestService.getFacilities();
		return jarryRoom.toString();	
	}
	
	@RequestMapping(value="/isRequstExist", method = RequestMethod.POST)
	public @ResponseBody void openShiftsave(@RequestParam(value="isRequestExistJson")String isRequestExistJson,
			HttpSession session,HttpServletResponse response)throws Exception{
		Gson g=new Gson();
       final CheckInRequestStatus checkinrequststatus=g.fromJson(isRequestExistJson, CheckInRequestStatus.class);
		int userId = ((User) session.getAttribute("userForm")).getId();	
		JsonParser jsonParser=new JsonParser();
		JsonObject jobj=jsonParser.parse(isRequestExistJson).getAsJsonObject();
		int checkinnumber=jobj.get("checkInNo").getAsInt();
		int facilityid=jobj.get("facilityId").getAsInt();
		String requstDate=jobj.get("date").getAsString();
		String reqTime=jobj.get("reqTime").getAsString();
		
		String status = "success";
		if(requestService.isRequestExist(checkinnumber,facilityid,requstDate,reqTime).equals("0")){
			status="status_newRequest";
			response.getWriter().print(status);
		}
		else{
			status="status_requestExist";
			response.getWriter().print(status);
		}
		
	}
	
	
	@RequestMapping(value= "/saveNewRequest",method = RequestMethod.POST)
	public @ResponseBody String saveNew(HttpServletRequest request,HttpSession session,@RequestBody String addon){
		boolean isSave=false;
		Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		JsonParser parser = new JsonParser();
		mapper.setDateFormat(simpleDateformat);
		JsonObject jobj = parser.parse(addon).getAsJsonObject();
		try{
			List<CheckInRequest> chreqlist = mapper.readValue(jobj.get("data").toString(),TypeFactory.defaultInstance().constructCollectionType(List.class, CheckInRequest.class));	

			for (CheckInRequest reqObj: chreqlist){
				if(reqObj.getId()==0){
					reqObj.setReqCompleted(false);
					reqObj.setIsDeleted(false);
					reqObj.setReqTakenBy(((User) session.getAttribute("userForm")).getId());
					reqObj.setUserId(reqObj.getReqTakenBy());
					reqObj.setReqTakenDate(commonSettings.hotelDate);
					reqObj.setCanceled(false);
				}
			}
			isSave= requestService.saveAdd(chreqlist);
		}catch(Exception e){
			isSave=false;
			e.printStackTrace();
		}
		String status="success";
		if(!isSave)
			status="failure";

		return gson.toJson("status:"+status);
	}

	@RequestMapping(value="/delete", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String delete(@RequestParam(value="id")int id){

		boolean canceled=requestService.delete(id);
		String canceledStatus = "success";
		Gson g=new Gson();		
		if(canceled){
			canceledStatus="status:"+canceledStatus;
		}else{
			canceledStatus="status:error";
		}
		return g.toJson(canceledStatus).toString();
	}

	@RequestMapping(value="/update", method = RequestMethod.POST)
	public @ResponseBody String update(@RequestParam(value="updateJson")String updateJson,HttpSession session,HttpServletResponse response)throws Exception{
		Gson g=new Gson();
		final int userId = ((User) session.getAttribute("userForm")).getId();	
		final CheckInRequestStatus checkInRequestStatus = g.fromJson(updateJson, CheckInRequestStatus.class);
		JsonParser jparser = new JsonParser();
		JsonObject jobj=jparser.parse(updateJson).getAsJsonObject();
		checkInRequestStatus.setId(jobj.get("id").getAsInt());
		checkInRequestStatus.setProcessStatus(jobj.get("processStatus").getAsInt());
		checkInRequestStatus.setReqTime(jobj.get("reqTime").getAsString());
		checkInRequestStatus.setCheckinhdrremark(String.valueOf(jobj.get("checkinhdrremark")));
		checkInRequestStatus.setProvider(jobj.get("provider").getAsInt());
		checkInRequestStatus.setRemarks(String.valueOf(jobj.get("checkinhdrremark")));
		checkInRequestStatus.setCheckInReequestId(jobj.get("checkinrequestid").getAsInt());
		checkInRequestStatus.setDate(jobj.get("date").getAsString());
		checkInRequestStatus.setReqTime(jobj.get("reqTime").getAsString());
		checkInRequestStatus.setReqCompleted(jobj.get("reqCompleted").getAsBoolean());
		checkInRequestStatus.setUserId(userId);
		checkInRequestStatus.setCanceled(false);
		String updateStatus = "success";
		boolean isUpdate=requestService.update(checkInRequestStatus);
		if(isUpdate){
			updateStatus="status:"+updateStatus;
		}else{
			updateStatus="status:error";
		}
		return g.toJson(updateStatus).toString();

	}

	@RequestMapping(value="getSearchData", method = RequestMethod.POST)
	public @ResponseBody String getSearchData(HttpSession session,HttpServletRequest request,@RequestParam(value = "listParams", required = true) String listParams) throws Exception{
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonObject jobject = parser.parse(listParams).getAsJsonObject();
		JsonObject jobj = new JsonObject();
		JsonObject resultObj  =new JsonObject();
		Map<String, String> searchMap = new HashMap<String, String>();
		try{
			jobj.add("sort",jobject.get("sort").getAsJsonObject());
			JsonObject searchObj = jobject.get("search").getAsJsonObject();
			jobj.add("pagination",jobject.get("pagination").getAsJsonObject());
			jobj.add("comnSearchValue", searchObj.get("comnSearchValue"));

			if( searchObj.get("roomNo").getAsJsonObject().get("searchable").getAsBoolean()){
				searchMap.put("room_no", searchObj.get("roomNo").getAsJsonObject().get("searchValue").getAsString());	
			}
			if(searchObj.get("reqStatus").getAsJsonObject().get("searchable").getAsBoolean()){
				searchMap.put("req_Status", searchObj.get("reqStatus").getAsJsonObject().get("searchValue").getAsString());	
			}

			if(searchObj.get("reqDate").getAsJsonObject().get("searchable").getAsBoolean()){
				searchMap.put("req_Date", searchObj.get("reqDate").getAsJsonObject().get("searchValue").getAsString());	
			}
			if(searchObj.get("name").getAsJsonObject().get("searchable").getAsBoolean()){
				searchMap.put("name", searchObj.get("name").getAsJsonObject().get("searchValue").getAsString());	
			}
			if(searchObj.get("phone").getAsJsonObject().get("searchable").getAsBoolean()){
				searchMap.put("phone", searchObj.get("phone").getAsJsonObject().get("searchValue").getAsString());	
			}
			if(searchObj.get("facility").getAsJsonObject().get("searchable").getAsBoolean()){
				searchMap.put("facility", searchObj.get("facility").getAsJsonObject().get("searchValue").getAsString());	
			}
			
			if(searchObj.get("addonStatus").getAsJsonObject().get("searchable").getAsBoolean()){
				searchMap.put("addonStatus", searchObj.get("addonStatus").getAsJsonObject().get("searchValue").getAsString());	
			}

			String searchType="advance";
			if((!searchObj.get("comnSearch").getAsBoolean() && !searchObj.get("advSearch").getAsBoolean())){

				searchType="default";

			}else if(searchObj.get("comnSearch").getAsBoolean()){

				searchType="simple";
			}

			jobj.addProperty("searchType",searchType);		
			resultObj  = requestService.getSearchData(jobj,searchMap);
		}catch(Exception e){
			e.printStackTrace();
			throw new CustomException();
		}

		return gson.toJson(resultObj);
	}

}
