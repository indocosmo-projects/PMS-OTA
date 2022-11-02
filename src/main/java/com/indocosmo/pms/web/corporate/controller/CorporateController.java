package com.indocosmo.pms.web.corporate.controller;

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
import com.indocosmo.pms.enumerator.corporate.CorporateClass;
import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.corporate.model.Corporate;
import com.indocosmo.pms.web.corporate.service.CorporateService;
import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;

@Controller
@RequestMapping(value = "/corporate")
public class CorporateController {
	
	public static final String CORPORATE_EDIT_PAGE_URL = "corporate/corporate_edit";
	public static final String CORPORATE_LIST_PAGE_URL = "corporate/corporate_list";
	public static final Logger logger = LoggerFactory.getLogger(CorporateController.class);
	
	@Autowired
	private CorporateService corporateService;
	
	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;
	
	private SysPermissions permissionObj;
	
	/**
	 * corporate List function
	 * @param rateHdr RateHdrModel
	 * @param currentPage
	 * @param rowLimit
	 * @param pagingStart
	 * @param advanceSearch
	 * @param sortVal      
	 * @param simpleSearch
	 * @param session
	 * @return jsonData
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public @ResponseBody JqGridListWrapperDTO list(
			@ModelAttribute("corporate") Corporate corporate,
			@RequestParam(value = "currentPage", required = false) String  currentPage,
			@RequestParam(value = "rowLimit", required = false) String rowLimit,
			@RequestParam(value = "pagingStart", required = false) String pagingStart,
			@RequestParam(value = "advanceSearch", required = false) String advanceSearch,
			@RequestParam(value = "sortVal", required = false) String sortVal,
			@RequestParam(value = "simpleSearh", required = false) String simpleSearch ,HttpSession session) throws Exception {
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
			
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("Method : list " + Throwables.getStackTraceAsString(ex));
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
		jqGridListWrapperDTO = corporateService.list(currentPage, rowLimit, pagingStart,advanceSearchMap, sortVal, simpleSearchMap);

		session.removeAttribute("recordCount");

		return jqGridListWrapperDTO;
	}
	
	
	/**
	 * List redirect function
	 * @return page redirect to corporateList 
	 */
	@RequestMapping(value = "corporateList", method = RequestMethod.GET)
	public String listRedirect(Model model, HttpSession session) throws Exception { 
		
		permissionObj = pageAccessPermissionService.getPermission(session,"MST_CORP");	
		model.addAttribute("curPagePerObj",permissionObj);
		
		return ((permissionObj.isCanView() && permissionObj.isIs_view_applicable())?CORPORATE_LIST_PAGE_URL:"access_denied/access_denied");

		 
		  
	}


	/**
	 * function of get total count 
	 * @param simpleSearchData
	 * @param advanceSearch
	 * @return total count of records
	 */
	@RequestMapping(value = "/getCount", method = RequestMethod.POST )
	public @ResponseBody String totalCount(@RequestParam(value = "simpleSearch") String simpleSearchData,
			@RequestParam(value = "advanceSearch") String advanceSearch ) throws Exception {
		Map<String, String> simpleSearchMap = new HashMap<String, String>();
		
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
		
		int count = corporateService.getCount(advanceSearchMap, simpleSearchMap);

		return Integer.toString(count);
	}

	/**
	 * Edit redirect function
	 * @param corporate
	 * @param model
	 * @return page redirect to edit page
	 */	
	@RequestMapping(value = "/editRedirect", method = RequestMethod.GET)
	public String editRedirect(@ModelAttribute Corporate corporate,Model model) throws Exception {
		model.addAttribute("corporateClass", CorporateClass.values());
		model.addAttribute("corporate", corporate);

		return CORPORATE_EDIT_PAGE_URL;
	}	
	
	/**
	 * Corporate module Single record access function
	 * @param corporate CorporateModel
	 * @param model 
	 * @param ids primary key of table (encrypted)
	 * @return  if the record is get the page redirect to edit page
	 */
	@RequestMapping(value = "getRecord", method = RequestMethod.GET)
	public String getRecord(@ModelAttribute Corporate corporate, Model model,
			@RequestParam(value = "ids", required = true) String ids,HttpSession session) throws Exception {
		try {
			permissionObj = pageAccessPermissionService.getPermission(session,"MST_CORP");	
			model.addAttribute("curPagePerObj",permissionObj);
			Encryption encryption = new Encryption();
			int corporateId = Integer.parseInt(encryption.decrypt(ids));

			corporate = corporateService.getRecord(corporateId);
			String encryptedId = encryption.encrypt(Integer.toString(corporate.getId()));
			corporate.setEncryption(encryptedId);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getRecord " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}
		
        model.addAttribute("corporateClass", CorporateClass.values());
		model.addAttribute("corporate", corporate);

		return CORPORATE_EDIT_PAGE_URL;
	}	

	/**
	 * Corporate module save and update function
	 * @param corporate CorporateModel
	 * @return if record added successfully page redirect to corporateList / not success it will redirect to edit page
	 * @throws Exception 
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@ModelAttribute Corporate corporate,HttpSession session) throws Exception {
		String redirectUrl=CORPORATE_EDIT_PAGE_URL;
	//	System.out.println("corporate"+new Gson().toJson(corporate));
		boolean isRateHdrSave = corporateService.save(corporate);

		if (isRateHdrSave) {
			/**
			 * get count method is using to get total record count from table.
			 * record count session is using in jqGrid
			 */
			Map<String, String> temp1=new HashMap<String, String>();
			Map<String, String> temp2=new HashMap<String, String>();
			int count=corporateService.getCount(temp1, temp2);
			session.setAttribute("recordCount", count);
			redirectUrl="redirect:corporateList"; 
		}

		return redirectUrl;
	}	
	
	/**
	 * Corporate delete function
	 * @param corporateids
	 * @param session
	 * @return true when deletion is success / false when deletion is not success
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody boolean delete(
			@RequestParam(value = "ids") int corporateIds,HttpSession session) throws Exception {
		boolean isDeleted = false;
		
		try {
			isDeleted = corporateService.delete(corporateIds);
			session.removeAttribute("recordCount");
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new CustomException();
		}
		
		/**
		 * get count method is using to get total record count from table.
		 * record count session is using in jqGrid
		 */
		Map<String, String> temp1 = new HashMap<String, String>();
		Map<String, String> temp2 = new HashMap<String, String>();
		int count = corporateService.getCount(temp1, temp2);
		session.setAttribute("recordCount", count);

		return isDeleted;
	}
	
	/**
     * code Exist checking function
     * @param code
     * @return true when code is exist / false when code is not exist
     */
    @RequestMapping(value = "codeExist", method = RequestMethod.GET)
    public @ResponseBody boolean codeExist(@RequestParam(value = "code") String code)throws Exception {
    	boolean isExist = corporateService.codeExist(code);
    	
  	    return isExist;
    }
}