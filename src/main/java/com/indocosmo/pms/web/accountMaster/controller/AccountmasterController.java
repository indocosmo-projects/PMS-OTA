package com.indocosmo.pms.web.accountMaster.controller;

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
import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.accountMaster.service.AccountmasterService;
import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.common.service.CommonService;
import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;
import com.indocosmo.pms.web.tax.service.TaxHdrService;

@Controller
@RequestMapping(value = "/accountMaster")
public class AccountmasterController {

	@Autowired
	private AccountmasterService accountMasterService;

	@Autowired
	private SystemSettingsService systemSettingsService;

	@Autowired
	private TaxHdrService taxHdrService;

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	@Autowired
	private CommonService commonService;

	private SysPermissions permissionObj;

	public static final String ACCOUNTMASTER_EDIT_PAGE_URL = "account_master/account_edit";
	public static final Logger logger = LoggerFactory.getLogger(AccountmasterController.class);

	/**
	 * code Exist checking function
	 * 
	 * @param code
	 * @return true when code is exist/ false when is not exist
	 */
	@RequestMapping(value = "codeExist", method = RequestMethod.GET)
	public @ResponseBody boolean codeExist(@RequestParam(value = "code") String code) throws Exception {
		boolean isExist = accountMasterService.codeExist(code);

		return isExist;
	}

	/**
	 * Account Master module returning dropdown list
	 * 
	 * @param accountmaster
	 * @return map
	 */
	@RequestMapping(value = "editRedirect", method = RequestMethod.GET)
	public String editRedirect(@ModelAttribute AccountMaster accountMaster, Model model) throws Exception {
		Map<Integer, String> taxList = null;

		taxList = taxHdrService.getTaxMap();
		model.addAttribute("taxList", taxList);
		model.addAttribute("accountMaster", accountMaster);

		Map<Integer, String> typesList = null;
		typesList = accountMasterService.getAccountType();
		model.addAttribute("typesList", typesList);
		model.addAttribute("accountMaster", accountMaster);

		return ACCOUNTMASTER_EDIT_PAGE_URL;
	}

	/**
	 * Account Master module Single record access function
	 * 
	 * @param AccountMaster
	 *            accountMaster
	 * @param model
	 * @param id
	 *            primary key of table (encrypted)
	 * @return if the record is get the page redirect to edit page
	 */
	@RequestMapping(value = "getRecord", method = RequestMethod.GET)
	public String getDeparment(@ModelAttribute AccountMaster accountMaster, Model model,
			@RequestParam(value = "ids", required = true) String ids) throws Exception {
		try {

			Encryption encryption = new Encryption();
			int accountmasterId = Integer.parseInt(encryption.decrypt(ids));
			accountMaster = accountMasterService.getRecord(accountmasterId);
			String value = encryption.encrypt(Integer.toString(accountMaster.getId()));
			accountMaster.setEncryption(value);

			Map<Integer, String> taxList = null;

			taxList = taxHdrService.getTaxMap();
			model.addAttribute("taxList", taxList);
			model.addAttribute("accountMaster", accountMaster);

			Map<Integer, String> typesList = null;
			typesList = accountMasterService.getAccountType();
			model.addAttribute("typesList", typesList);
			model.addAttribute("accountMaster", accountMaster);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getRecord " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		SystemSettings systemSettings = systemSettingsService.getSystemSettings();
		boolean is_serviceCharge_included = systemSettings.isServiceCharge();

		model.addAttribute("accountMaster", accountMaster);
		model.addAttribute("isServiceChargeIncluded", is_serviceCharge_included);

		return "account_master/account_edit";
	}

	/**
	 * Account master module save and update function
	 * 
	 * @param AccountMaster
	 *            account master
	 * @return if record added successfully page redirect to departmentList / not
	 *         success it will redirect to edit page
	 */
	@RequestMapping(value = "/save")
	public String addEdit(@ModelAttribute AccountMaster acountmaster, HttpSession session) throws Exception {
		String redirectUrl = ACCOUNTMASTER_EDIT_PAGE_URL;
		boolean isSave = accountMasterService.save(acountmaster);

		if (isSave) {
			Map<String, String> temp1 = new HashMap<String, String>();
			Map<String, String> temp2 = new HashMap<String, String>();
			int count = accountMasterService.getCount(temp1, temp2);
			session.setAttribute("recordCount", count);
			redirectUrl = "redirect:accountMasterList";
		}

		return redirectUrl;
	}

	/**
	 * List redirect function
	 * 
	 * @return redirect to account list
	 */
	@RequestMapping(value = "accountMasterList", method = RequestMethod.GET)
	public String accountMasterListRedirect(Model model, HttpSession session) throws Exception {

		permissionObj = pageAccessPermissionService.getPermission(session, "MST_ACOUNT_MSTER");
		model.addAttribute("curPagePerObj", permissionObj);

		return ((permissionObj.isCanView() && permissionObj.isIs_view_applicable()) ? "account_master/account_list"
				: "access_denied/access_denied");

	}

	/**
	 * AccountMaster delete function
	 * 
	 * @param id
	 * @param session
	 * @return true when deletion is success/ false when deletion is not success
	 */
	@RequestMapping(value = "delete")
	public @ResponseBody boolean departmentDelete(@RequestParam(value = "ids") String id, HttpSession session)
			throws Exception {
		boolean isDeleted = accountMasterService.delete(Integer.parseInt(id));
		session.removeAttribute("recordCount");

		Map<String, String> temp1 = new HashMap<String, String>();
		Map<String, String> temp2 = new HashMap<String, String>();
		int count = accountMasterService.getCount(temp1, temp2);
		session.setAttribute("recordCount", count);

		return isDeleted;
	}

	/**
	 * function of get total count
	 * 
	 * @param simpleSearchData
	 * @param advanceSearch
	 * @return total count of records
	 */
	@RequestMapping(value = "getCount", method = RequestMethod.POST)
	public @ResponseBody String totalCount(@RequestParam(value = "simpleSearch") String simpleSearchData,
			@RequestParam(value = "advanceSearch") String advanceSearch, HttpSession session, Model model)
			throws Exception {

		permissionObj = pageAccessPermissionService.getPermission(session, "MST_ACOUNT_MSTER");
		model.addAttribute("curPagePerObj", permissionObj);

		Map<String, String> simpleSearchMap = new HashMap<String, String>();

		if (simpleSearchData != null && !simpleSearchData.equals("")) {
			simpleSearchMap.put("code", simpleSearchData);
			simpleSearchMap.put("name", simpleSearchData);
		}

		Map<String, String> advanceSearchMap = new HashMap<String, String>();

		if (advanceSearch != null && !advanceSearch.equals("null") && !advanceSearch.equals("")) {
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				advanceSearchMap = objectMapper.readValue(advanceSearch, new TypeReference<HashMap<String, String>>() {
				});

			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("Method : getCount " + Throwables.getStackTraceAsString(ex));
				throw new CustomException();
			}
		}

		int count = accountMasterService.getCount(advanceSearchMap, simpleSearchMap);

		return Integer.toString(count);
	}

	/**
	 * AccountMasterList function
	 * 
	 * @param accountmaster
	 * @param currentPage
	 * @param rowLimit
	 * @param pagingStart
	 * @param advanceSearch
	 * @param sortVal
	 * @param simpleSearch
	 * @param session
	 * @return jsonData
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JqGridListWrapperDTO list(@ModelAttribute("accountmaster") AccountMaster accountmaster,
			@RequestParam(value = "currentPage", required = false) String currentPage,
			@RequestParam(value = "rowLimit", required = false) String rowLimit,
			@RequestParam(value = "pagingStart", required = false) String pagingStart,
			@RequestParam(value = "advanceSearch", required = false) String advanceSearch,
			@RequestParam(value = "sortVal", required = false) String sortVal,
			@RequestParam(value = "simpleSearh", required = false) String simpleSearch, HttpSession session)
			throws Exception {
		Map<String, String> simpleSearchMap = new HashMap<String, String>();

		if (simpleSearch != null && !simpleSearch.equals("")) {
			simpleSearchMap.put("code", simpleSearch);
			simpleSearchMap.put("name", simpleSearch);
		}

		Map<String, String> advanceSearchMap = new HashMap<String, String>();

		if (advanceSearch != null && !advanceSearch.equals("null") && !advanceSearch.equals("")) {
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				advanceSearchMap = objectMapper.readValue(advanceSearch, new TypeReference<HashMap<String, String>>() {
				});

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
		jqGridListWrapperDTO = accountMasterService.list(currentPage, rowLimit, pagingStart, advanceSearchMap, sortVal,
				simpleSearchMap);
		session.removeAttribute("recordCount");

		return jqGridListWrapperDTO;
	}

	/**
	 * 
	 * @param accmaster
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "accmasterRedirect", method = RequestMethod.GET)
	public String departmentRedirect(@ModelAttribute AccountMaster accmaster, Model model) throws Exception {
		model.addAttribute("accmaster", accmaster);

		return ACCOUNTMASTER_EDIT_PAGE_URL;
	}

	@RequestMapping(value = "getTaxStatus", method = RequestMethod.GET)
	public @ResponseBody Boolean getTaxStatus(@RequestParam(value = "taxTypeId", required = true) int taxTypeId) {

		String table = "sysdef_acc_type";
		String fieldName = "id";
		String getFieldName = "can_have_tax";

		Boolean taxStatus = commonService.getStatus(table, fieldName, taxTypeId, getFieldName);

		return taxStatus;
	}

	/**
	 * @param taxTypeId
	 * @param acmId
	 * @return
	 */
	@RequestMapping(value = "getInheritStatus", method = RequestMethod.GET)
	public @ResponseBody Boolean getInheritStatus(@RequestParam(value = "taxTypeId", required = true) int taxTypeId,
			@RequestParam(value = "acmId", required = true) int acmId) {
		String table = "sysdef_acc_type";
		String fieldName = "id";
		String getFieldName = "can_inherit_from";
		Boolean nheritStatus = commonService.getStatus(table, fieldName, taxTypeId, getFieldName);
		Boolean status = false;
		if (nheritStatus) {
			status = true;
		} else {
			table = "AccountMaster";
			fieldName = "sysdef_acc_type_id";
			int count = commonService.getCount(table, fieldName, taxTypeId, acmId);
			if (count == 0) {
				status = true;
			}
		}
		return status;
	}
}