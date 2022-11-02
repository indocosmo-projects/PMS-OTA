package com.indocosmo.pms.web.corporateTaRates.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import com.indocosmo.pms.enumerator.common.MealPlan;
import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.common.model.RateDist;
import com.indocosmo.pms.web.common.model.RateDtl;
import com.indocosmo.pms.web.common.model.RateHdr;
import com.indocosmo.pms.web.corporate.model.Corporate;
import com.indocosmo.pms.web.corporate.service.CorporateService;
import com.indocosmo.pms.web.corporateTaRates.service.CorporateTaRateService;
import com.indocosmo.pms.web.department.service.DepartmentService;
import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.roomType.model.RoomType;
import com.indocosmo.pms.web.roomType.service.RoomTypeService;
import com.indocosmo.pms.web.season.service.SeasonHdrService;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;

@Controller
@RequestMapping("/corporateTaRate")
public class CorporateTaRateController {

	@Autowired
	private CorporateTaRateService corporateTaRateService;

	@Autowired
	private RoomTypeService roomTypeService;

	@Autowired
	private SeasonHdrService seasonService;
	
	@Autowired
	private CorporateService corporateService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;
	
	private SysPermissions permissionObj;


	public static final String CORP_RATE_EDIT_PAGE_URL = "corporate_ta_rates/corporate_ta_rates_edit";
	public static final String CORP_RATE_LIST_PAGE_URL = "corporate_ta_rates/corporate_ta_rates_list";

	public static final Logger logger = LoggerFactory.getLogger(CorporateTaRateController.class);

	/**
	 * room_rate List function
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
			@ModelAttribute("rateHdr") RateHdr rateHdr,
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
		jqGridListWrapperDTO = corporateTaRateService.list(currentPage, rowLimit, pagingStart,advanceSearchMap, sortVal, simpleSearchMap);

		session.removeAttribute("recordCount");

		return jqGridListWrapperDTO;
	}

	/**
	 * List redirect function
	 * @return page redirect to roomRateList 
	 */
	@RequestMapping(value = "corporateRateList", method = RequestMethod.GET)
	public String listRedirect(Model model, HttpSession session) throws Exception { 
		
		permissionObj = pageAccessPermissionService.getPermission(session,"MST_CORP_RATE");	
		model.addAttribute("curPagePerObj",permissionObj);
		
		return ((permissionObj.isCanView() && permissionObj.isIs_view_applicable())?CORP_RATE_LIST_PAGE_URL:"access_denied/access_denied");
 
	}

	/**
	 * function of get total count 
	 * @param simpleSearchData
	 * @param advanceSearch
	 * @return total count of records
	 */
	@RequestMapping(value = "getCount", method = RequestMethod.POST )
	public @ResponseBody String totalCount(@RequestParam(value = "simpleSearch") String simpleSearchData,
			@RequestParam(value = "advanceSearch") String advanceSearch ,Model model,HttpSession session) throws Exception {
		Map<String,String> simpleSearchMap=new HashMap<String, String>();
		
		
		
		
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
		
		int count = corporateTaRateService.getCount(advanceSearchMap, simpleSearchMap);

		return Integer.toString(count);
	}

	/**
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "editRedirect", method = RequestMethod.GET)
	public String editRedirect(Model model, HttpSession session) throws Exception {
		RateHdr rateHdr = new RateHdr();
		rateHdr.setMealPlan(MealPlan.EP.getPlan());
		
		model.addAttribute("corporateRate", rateHdr);
		model.addAttribute("corporate", new Corporate());
		model.addAttribute("roomTypes", roomTypeService.getRoomTypeList());
		model.addAttribute("mealPlans", MealPlan.values());
		model.addAttribute("seasons", seasonService.getSeasonNamesWithId());
		model.addAttribute("departments", departmentService.getNameIdMap());
		model.addAttribute("dateFormat", session.getAttribute("dateFormat").toString());

		return CORP_RATE_EDIT_PAGE_URL;
	}
	
	/**
	 * Room Rate module Single record access function
	 * @param rateHdr RateHdrModel
	 * @param model 
	 * @param ids primary key of table (encrypted)
	 * @return  if the record is get the page redirect to edit page
	 */
	@RequestMapping(value = "getRecord", method = RequestMethod.GET)
	public String getRecord(@ModelAttribute RateHdr rateHdr, Model model,
			@RequestParam(value = "ids", required = true) String ids, HttpSession session) throws Exception {
		try {
			permissionObj = pageAccessPermissionService.getPermission(session,"MST_CORP_RATE");	
			model.addAttribute("curPagePerObj",permissionObj);
			Encryption encryption = new Encryption();
			int rateHdrId = Integer.parseInt(encryption.decrypt(ids));
			rateHdr = corporateTaRateService.getRecord(rateHdrId);
			
			Iterator<RateDtl> rtDtlItr = rateHdr.getRateDetails().iterator();
			RateDtl rateDtl;
			
			while(rtDtlItr.hasNext()) {
				rateDtl = rtDtlItr.next();
				
				if(rateDtl.getIsDeleted()) {
					rtDtlItr.remove();
				}
			}
			
			Iterator<RateDist> itr = rateHdr.getRateDistribution().iterator(); 
			RateDist rateDist;
			
			while(itr.hasNext()) {
				rateDist = itr.next();
				
				if(rateDist.getIsDeleted()) {
					itr.remove();
				}
			}
			
			String encryptedId = encryption.encrypt(Integer.toString(rateHdr.getId()));
			rateHdr.setEncryption(encryptedId);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getRecord " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		model.addAttribute("corporateRate", rateHdr);
		model.addAttribute("corporate", corporateService.getRecord(rateHdr.getCorporateId()));
		model.addAttribute("roomTypes", roomTypeService.getRoomTypeList());
		model.addAttribute("mealPlans", MealPlan.values());
		model.addAttribute("seasons", seasonService.getSeasonNamesWithId());
		model.addAttribute("departments", departmentService.getNameIdMap());
		model.addAttribute("dateFormat", session.getAttribute("dateFormat").toString());

		return CORP_RATE_EDIT_PAGE_URL;
	}	

	/**
	 * room rate module save and update function
	 * @param rateHdr RateHdrModel
	 * @return if record added successfully page redirect to roomRateList / not success it will redirect to edit page
	 * @throws Exception 
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@ModelAttribute RateHdr rateHdr, HttpSession session) throws Exception {
		String redirectUrl = CORP_RATE_EDIT_PAGE_URL;
		boolean isRateHdrSave = corporateTaRateService.save(rateHdr);

		if (isRateHdrSave) {
			/**
			 * get count method is using to get total record count from table.
			 * record count session is using in jqGrid
			 */
			Map<String, String> temp1 = new HashMap<String, String>();
			Map<String, String> temp2 = new HashMap<String, String>();
			int count = corporateTaRateService.getCount(temp1, temp2);
			session.setAttribute("recordCount", count);
			redirectUrl = "redirect:corporateRateList"; 
		}

		return redirectUrl;
	}

	/**
	 * Room Rate delete function
	 * @param rateHdrids
	 * @param session
	 * @return true when deletion is success / false when deletion is not success
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody boolean delete(
			@RequestParam(value = "ids") int rateHdrids, HttpSession session) throws Exception {
		boolean isDeleted = false;
		
		try {
			isDeleted = corporateTaRateService.delete(rateHdrids);
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
		int count = corporateTaRateService.getCount(temp1, temp2);
		session.setAttribute("recordCount", count);

		return isDeleted;
	}

	/**
     * code Exist checking function
     * @param code
     * @return true when code is exist / false when code is not exist
     */
    @RequestMapping(value = "codeExist" , method = RequestMethod.GET)
    public @ResponseBody boolean codeExist(@RequestParam(value = "code") String code) throws Exception {
    	boolean isExist = corporateTaRateService.codeExist(code);
    	
  	    return isExist;
    }
    
    /**
     * get corporate list as per the search key word
     * @param keyWord
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "searchCorporate", method = RequestMethod.POST)
    public @ResponseBody String getCorporates(@RequestParam(value = "keyWord") String keyWord) throws Exception {
    	List<Corporate> corporates = corporateService.getCorporates(keyWord);
    	ObjectMapper mapper = new ObjectMapper();
    	String jsonList = mapper.writeValueAsString(corporates);
    	
    	return jsonList;
    }
    
    /**
     * 
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "occStat", method = RequestMethod.POST)
    public @ResponseBody String getOccupancyStatus(@RequestParam(value = "id") String id) throws Exception {
    	RoomType roomType = roomTypeService.getRecord(Integer.parseInt(id));
    	ObjectMapper mapper = new ObjectMapper();
    	
    	return mapper.writeValueAsString(roomType);
    }
}