package com.indocosmo.pms.web.pettycash.controller;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/*
 * 
 *   @gana 08-09-2020
 */
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.pettycash.model.PettyCash;
import com.indocosmo.pms.web.pettycash.service.PettyCashService;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;
/*
 * @gana 08-09-20
 */
@Controller
@RequestMapping("/pettycash")
public class PettyCashController {
    
	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "access_denied/access_denied";
	private static String pageUrl="pettycash/petty_list";
	
	@Autowired
	PageAccessPermissionService pageAccessPermissionService;
	
	@Autowired
	SystemSettingsService systemSettingsService;
	
	@Autowired
	PettyCashService  pettyCashService;
	
	private SysPermissions systemPermissions;
	
	@RequestMapping("/list")
	public String pettyCash(Model model,HttpSession session,HttpServletResponse response) {
		systemPermissions=pageAccessPermissionService.getPermission(session, "PETTY_CASH");
		if(systemPermissions.isCanView() && systemPermissions.isIs_view_applicable()) {
			model.addAttribute("curPagePerObj",systemPermissions);
			model.addAttribute("userGrpId",session.getAttribute("userGrpId"));
			
			
		}else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}
	
	@RequestMapping("/headList")
	public String pettyHeadList(Model model,HttpSession session,HttpServletResponse response) {
		String pageListUrl="pettycashhead/pettycash_head_list";
		systemPermissions=pageAccessPermissionService.getPermission(session, "MST_PETTY_HEAD");
		if(systemPermissions.isCanView() && systemPermissions.isIs_view_applicable()) {
			model.addAttribute("curPagePerObj",systemPermissions);
			model.addAttribute("userGrpId",session.getAttribute("userGrpId"));
		}else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageListUrl;
	}
	
	@RequestMapping(value="/openingBalance",method = RequestMethod.POST)
	public @ResponseBody Double openingBalance() throws Exception {
		final SystemSettings systemSettings = systemSettingsService.getSystemSettings();
		DateFormat simpleDateFormatHotelDate = new SimpleDateFormat("yyyy-MM-dd");
		final String hotelDate = simpleDateFormatHotelDate.format(systemSettings.getHotelDate());
		Double floatingAmount=pettyCashService.openingBalance(hotelDate);
		return floatingAmount;
	}
	
	@RequestMapping(value="/saveCategory",method = RequestMethod.POST)
	public @ResponseBody String saveCategory(@RequestParam String saveJson,HttpServletResponse response) throws IOException {
		Gson gson = new Gson();
		String status=pettyCashService.saveCategory(saveJson);
		 //response.getWriter().print(status);
		 return  gson.toJson(status).toString();
	}
	
	@RequestMapping(value="/selCategory",method = RequestMethod.GET)
	public @ResponseBody String loadCategory() {
		Gson gson = new Gson();
		JsonArray catArray=pettyCashService.loadCategory();
		return gson.toJson(catArray);
	}
	
	@RequestMapping(value="/pettyHead",method = RequestMethod.GET)
	public @ResponseBody String loadHead() {
		Gson gson = new Gson();
		JsonArray catArray=pettyCashService.loadHead();
		return gson.toJson(catArray);
	}
	
	@RequestMapping(value="/saveExpense",method = RequestMethod.POST)
	public @ResponseBody String saveExpense(@RequestBody String saveExpense) throws Exception {
		Gson gson = new Gson();
		List<PettyCash> pettytList=pettyCashService.saveExpense(saveExpense);
		return gson.toJson(pettytList);
	}
	
	@RequestMapping(value="/expenseDetails",method = RequestMethod.POST)
	public @ResponseBody String expenseDetails() throws Exception {
		Gson gson = new Gson();
      List<PettyCash> expenseList=pettyCashService.loadExpense();
		return gson.toJson(expenseList);
	}
	
	@RequestMapping(value="/deletePetty",method = RequestMethod.POST)
	public @ResponseBody String deleteExpense(@RequestBody String deletePetty) throws Exception {
		Gson gson = new Gson();
		String status=pettyCashService.deleteExpense(deletePetty);
		return gson.toJson(status);
	}
	
	@RequestMapping(value="/pettyEdit",method = RequestMethod.POST)
	public @ResponseBody String pettyEdit(@RequestBody int id) throws Exception {
		Gson gson = new Gson();
		String status=pettyCashService.pettyEdit(id);
		return gson.toJson(status);
	}
	
	@RequestMapping(value="/pettyHeadList",method = RequestMethod.GET)
	public @ResponseBody String pettyHeadDetails() throws Exception {
		Gson gson = new Gson();
		JsonArray headArray=pettyCashService.pettyHeadDetails();
		return gson.toJson(headArray);
	}
	
	@RequestMapping(value="/loadHeadList",method = RequestMethod.GET)
	public @ResponseBody String loadHeadDetails(@RequestParam int id) throws Exception {
		Gson gson = new Gson();
		JsonArray headArray=pettyCashService.loadHeadList(id);
		return gson.toJson(headArray);
	}
	
	@RequestMapping(value="/deleteHead",method = RequestMethod.POST)
	public @ResponseBody String deletePettyHead(@RequestParam int id) throws Exception {
		Gson gson = new Gson();
		String status=pettyCashService.deletePettyHead(id);
		return gson.toJson(status);
	}
	
	@RequestMapping(value="/editExpense",method = RequestMethod.POST)
	public @ResponseBody String editExpense(@RequestParam String searchCritrea) throws Exception {
		Gson gson = new Gson();
		JsonArray searchArry=pettyCashService.editExpense(searchCritrea);
		return gson.toJson(searchArry);
	}
	
	@RequestMapping(value="/searchExpense",method = RequestMethod.POST)
	public @ResponseBody String searchExpense(@RequestParam String searchCritrea) throws Exception {
		Gson gson = new Gson();
		List<PettyCash>  searchArry=pettyCashService.searchExpense(searchCritrea);
		return gson.toJson(searchArry);
	}
	
	@RequestMapping(value="/searchCategory",method = RequestMethod.POST)
	public @ResponseBody String searchCategory(@RequestBody String searchCritrea) throws Exception {
		Gson gson = new Gson();
		JsonArray searchArry=pettyCashService.searchCategory(searchCritrea);
		return gson.toJson(searchArry);
	}
	
	@RequestMapping(value="/updateOpng",method = RequestMethod.POST)
	public @ResponseBody String updateOpng(@RequestBody double opening) throws Exception {
		Gson gson = new Gson();
		String status=pettyCashService.updateOpening(opening);
		return gson.toJson(status);
	}
	
	@RequestMapping(value="/deleteRow",method = RequestMethod.POST)
	public @ResponseBody String deleteExpenseRow(@RequestParam String id) throws Exception {
		Gson gson = new Gson();
		String status=pettyCashService.deleteExpenseRow(id);
		return gson.toJson(status);
	}
}
