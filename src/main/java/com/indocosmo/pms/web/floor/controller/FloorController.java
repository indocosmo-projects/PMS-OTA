package com.indocosmo.pms.web.floor.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.floor.model.Floor;
import com.indocosmo.pms.web.floor.service.FloorService;
import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;

@Controller
@RequestMapping(value = "/floor") 
public class FloorController {
	public static final String FLOOR_EDIT_PAGE_URL = "floor/floor_edit";
	public static final Logger logger=LoggerFactory.getLogger(FloorController.class);

	@Autowired
	FloorService floorService;
	
	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;
	
	private SysPermissions permissionObj;

	/**
	 * Currency module save and update function
	 * @param currency currencyModel
	 * @return if record added successfully page redirect to floorList / not success it will redirect to edit page
	 */
	@RequestMapping(value = "/save")
	public String save(@ModelAttribute Floor floor, HttpSession session) throws Exception {
		String redirectUrl = FLOOR_EDIT_PAGE_URL;
		boolean isSave = floorService.save(floor);

		if(isSave) {
			Map<String, String> temp1 = new HashMap<String, String>();
			Map<String, String> temp2 = new HashMap<String, String>();
			int count = floorService.getCount(temp1, temp2);
			session.setAttribute("recordCount", count);
			redirectUrl = "redirect:floorList";
		}

		return redirectUrl;
	}

	
	/**
	 * FloorList function
	 * @param floor 
	 * @param currentPage
	 * @param rowLimit
	 * @param pagingStart
	 * @param advanceSearch
	 * @param sortVal      
	 * @param simpleSearch
	 * @param session
	 * @return  jsonData
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JqGridListWrapperDTO list(
			@ModelAttribute("floor") Floor floor,
			@RequestParam(value = "currentPage", required = false) String  currentPage,
			@RequestParam(value = "rowLimit", required = false) String rowLimit,
			@RequestParam(value = "pagingStart", required = false) String pagingStart,
			@RequestParam(value = "advanceSearch", required = false) String advanceSearch,
			@RequestParam(value = "sortVal", required = false) String sortVal,
			@RequestParam(value = "simpleSearh", required = false) String simpleSearch ,HttpSession session)throws Exception {
		Map<String, String> simpleSearchMap = new HashMap<String, String>();

		if (simpleSearch != null && !simpleSearch.equals("")) {
			simpleSearchMap.put("code", simpleSearch);
			simpleSearchMap.put("name", simpleSearch);
		}

		Map<String, String> advanceSearchMap = new HashMap<String, String>();

		if (advanceSearch != null && !advanceSearch.equals("null") && !advanceSearch.equals("")) {
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				advanceSearchMap = objectMapper.readValue(advanceSearch,new TypeReference<HashMap<String, String>>(){});
			} catch (JsonParseException ex) {
				ex.printStackTrace();
				logger.error("Method : getCount " + Throwables.getStackTraceAsString(ex));
				throw new CustomException();
			}
		}

		if (currentPage.equals("")) {
			currentPage = "1";
		}

		if (pagingStart.equals("NaN")) {
			pagingStart = "1";
		}

		JqGridListWrapperDTO jqGridListWrapperDTO = null;
		jqGridListWrapperDTO = floorService.list(currentPage, rowLimit, pagingStart, advanceSearchMap, sortVal, simpleSearchMap);
		session.removeAttribute("recordCount");

		return jqGridListWrapperDTO;
	}

	/**
	 * Floor delete function
	 * @param id
	 * @param session
	 * @return  true when deletion is success/ false when deletion is not success
	 * @throws Exception 
	 */
	/*@RequestMapping(value = "delete",  method = RequestMethod.POST)
	public @ResponseBody String delete(@RequestParam(value = "ids") String id, HttpSession session) throws Exception {
		int canDelete = floorService.getCountOfFloor(Integer.parseInt(id));
		String deleteStatus = "success";
		System.out.println("can delete status value :"+canDelete);
		boolean isDeleted = false;
		Gson g = new Gson();
		if (canDelete == 0) {
			isDeleted = floorService.delete(Integer.parseInt(id));
			session.removeAttribute("recordCount");
			Map<String, String> temp1 = new HashMap<String, String>();
			Map<String, String> temp2 = new HashMap<String, String>();
			int count = floorService.getCount(temp1, temp2);
			session.setAttribute("recordCount", count);
			System.out.println("isDeleted in if : "+isDeleted);
			if (isDeleted) {
				deleteStatus = "status:" + deleteStatus;
			} else {
				deleteStatus = "status:error";
			}
		}
		System.out.println("Isdeleted :: "+isDeleted);
		//return isDeleted;
		return deleteStatus;
	}
*/
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String delete(@RequestParam(value = "id") int id,
			HttpSession session) throws Exception {
		int canDelete = floorService.getCountOfFloor(id);
		String deleteStatus = "success";
		Gson g = new Gson();
		if(canDelete==0){
			boolean isDelete = floorService.delete(id);
			if (isDelete) {
				deleteStatus = "status:" + deleteStatus;
			} else {
				deleteStatus = "status:error";
			}
		}
		return g.toJson(deleteStatus).toString();

	}
	
	/**
	 * List redirect function
	 * @return redirect to floor list
	 */
	@RequestMapping(value = "floorList", method = RequestMethod.GET)
	public String listRedirect(Model model, HttpSession session) throws Exception { 
		
		permissionObj = pageAccessPermissionService.getPermission(session,"MST_FLR");	
		model.addAttribute("curPagePerObj",permissionObj);
		
		return ((permissionObj.isCanView() && permissionObj.isIs_view_applicable())?"floor/floor_list":"access_denied/access_denied");

		 
	}

	/**
	 * Edit redirect function
	 * @param currency
	 * @param model
	 * @return redirect to floor edit page
	 */  
	@RequestMapping(value = "floorRedirect", method = RequestMethod.GET)
	public String editRedirect(HttpSession session,@ModelAttribute Floor floor, Model model) throws Exception { 
		    model.addAttribute("floor", floor);
		    permissionObj = pageAccessPermissionService.getPermission(session,"MST_FLR");	
			model.addAttribute("curPagePerObj",permissionObj);
		return FLOOR_EDIT_PAGE_URL;
	}

	/**
	 * Currency module Single record access function
	 * @param currency CurrencyModel
	 * @param model 
	 * @param id primary key of table (encrypted)
	 * @return if the record is get the page redirect to edit page
	 */
	@RequestMapping(value = "getRecord", method = RequestMethod.GET)
	public String getRecord(HttpSession session,@ModelAttribute Floor floor, Model model,
			@RequestParam(value = "ids", required = true) String id) throws Exception {
		try {
			
			permissionObj = pageAccessPermissionService.getPermission(session,"MST_FLR");	
			model.addAttribute("curPagePerObj",permissionObj);
			Encryption encryption = new Encryption();
			int floorId = Integer.parseInt(encryption.decrypt(id));
			floor = floorService.getRecord(floorId);
			String value = encryption.encrypt(Integer.toString(floor.getId()));
			floor.setEncryption(value);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getRecord " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		model.addAttribute("floor", floor);

		return "floor/floor_edit";
	}

	/**
	 * code Exist checking function
	 * @param code
	 * @return true when code is exist/ false when  is not exist
	 */
	@RequestMapping(value = "codeExist", method = RequestMethod.GET)
	public @ResponseBody boolean codeExist(@RequestParam(value = "code") String code) throws Exception {
		boolean isExist = floorService.codeExist(code);

		return isExist;
	}

	/**
	 * function of get total count 
	 * @param simpleSearchData
	 * @param advanceSearch
	 * @return total count of records
	 */
	@RequestMapping(value = "getCount", method = RequestMethod.POST )
	public @ResponseBody String getCount(@RequestParam(value = "simpleSearch") String simpleSearchData,
			@RequestParam(value = "advanceSearch") String advanceSearch ) throws Exception {
		Map<String,String> simpleSearchMap = new HashMap<String, String>();

		if(simpleSearchData != null && !simpleSearchData.equals("")) {
			simpleSearchMap.put("code", simpleSearchData);
			simpleSearchMap.put("name", simpleSearchData);
		}

		Map<String, String> advanceSearchMap = new HashMap<String, String>();	

		if(advanceSearch != null && !advanceSearch.equals("null") && !advanceSearch.equals("")) {  	
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				advanceSearchMap = objectMapper.readValue(advanceSearch, new TypeReference<HashMap<String, String>>(){});
			} catch (JsonParseException ex) {
				ex.printStackTrace();
				logger.error("Method : getCount " + Throwables.getStackTraceAsString(ex));
				throw new CustomException();
			}
		}
		
		int count = floorService.getCount(advanceSearchMap, simpleSearchMap);	

		return Integer.toString(count);
	}
}
