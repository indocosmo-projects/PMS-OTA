package com.indocosmo.pms.web.tax.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.tax.model.TaxHdr;
import com.indocosmo.pms.web.tax.service.TaxHdrService;

@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/TaxHdr")
public class TaxHdrController {

	public static final String TAX_EDIT_PAGE_URL = "tax/tax_edit";
	public static final Logger logger = LoggerFactory.getLogger(TaxHdrController.class);

	@Autowired
	private TaxHdrService taxHdrService;

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	private SysPermissions permissionObj;

	/**
	 * List redirect function
	 * 
	 * @return redirect to list page
	 */
	@RequestMapping(value = "/taxList", method = RequestMethod.GET)
	public String listRedirect(HttpServletRequest request, Model model, HttpSession session) throws Exception {

		permissionObj = pageAccessPermissionService.getPermission(session, "MST_TAX");
		model.addAttribute("curPagePerObj", permissionObj);

		return ((permissionObj.isCanView() && permissionObj.isIs_view_applicable()) ? "tax/tax_list"
				: "access_denied/access_denied");

	}

	/**
	 * TaxList function
	 * 
	 * @param taxHdr
	 *            model
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
	public @ResponseBody JqGridListWrapperDTO list(@ModelAttribute("taxHdr") TaxHdr taxHdr,
			@RequestParam(value = "currentPage", required = false) String currentPage/* pageNo */,
			@RequestParam(value = "rowLimit", required = false) String rowLimit,
			@RequestParam(value = "pagingStart", required = false) String pagingStart/* currentPage */,
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
		jqGridListWrapperDTO = taxHdrService.taxHdrList(currentPage, rowLimit, pagingStart, advanceSearchMap, sortVal,
				simpleSearchMap);

		session.removeAttribute("recordCount");

		return jqGridListWrapperDTO;
	}

	/**
	 * Edit redirect function
	 * 
	 * @param tax
	 * @param model
	 * @return redirect to edit page
	 */
	@RequestMapping(value = "/editRedirect", method = RequestMethod.GET)
	public String editRedirect(Model model, @ModelAttribute("tax") TaxHdr tax) throws Exception {
		int countTaxDetail = 0;
		countTaxDetail = tax.getTaxDetails().size();
		model.addAttribute("countTaxDetail", countTaxDetail);
		model.addAttribute("tax", tax);

		return "tax/tax_edit";
	}

	/**
	 * Tax module Single record access function
	 * 
	 * @param tax
	 *            TaxHdrModel
	 * @param model
	 * @param id
	 *            primary key of table (encrypted)
	 * @return if the record is get the page redirect to edit page
	 */
	@RequestMapping(value = "/getRecord", method = RequestMethod.GET)
	public String getRecord(@RequestParam(value = "ids", required = true) String ids, Model model,
			@ModelAttribute("tax") TaxHdr tax, HttpSession session) throws Exception {
		try {
			permissionObj = pageAccessPermissionService.getPermission(session, "MST_TAX");
			model.addAttribute("curPagePerObj", permissionObj);

			int countTaxDetail = 0;
			Encryption encryption = new Encryption();
			int taxHdrId = Integer.parseInt(encryption.decrypt(ids));
			tax = taxHdrService.getEditRecord(taxHdrId);
			String encryptedId = encryption.encrypt(Integer.toString(tax.getId()));
			tax.setEncryption(encryptedId);
			countTaxDetail = tax.getTaxDetails().size();
			model.addAttribute("countTaxDetail", countTaxDetail);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getRecord " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		model.addAttribute("tax", tax);

		return "tax/tax_edit";
	}

	/**
	 * Tax module save and update function
	 * 
	 * @param taxHdrForm
	 *            TaxHdrModel
	 * @return if record added successfully page redirect to currencyList / not
	 *         success it will redirect to edit page
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute TaxHdr tax, HttpSession session) throws Exception {
		String redirectUrl = TAX_EDIT_PAGE_URL;
		boolean isTaxSave = taxHdrService.save(tax);

		if (isTaxSave) {
			/**
			 * get count method is using to get total record count from table. record count
			 * session is using in jqGrid
			 */
			Map<String, String> temp1 = new HashMap<String, String>();
			Map<String, String> temp2 = new HashMap<String, String>();
			int count = taxHdrService.getCount(temp1, temp2);
			session.setAttribute("recordCount", count);

			redirectUrl = "redirect:taxList";
		}

		return redirectUrl;
	}

	/**
	 * not completed review Tax module save and update function
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody String delete(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "table") String table, HttpSession session) throws Exception {
		try {

			taxHdrService.delete(Integer.parseInt(id), table);
			/**
			 * get count method is using to get total record count from table. record count
			 * session is using in jqGrid
			 */
			Map<String, String> temp1 = new HashMap<String, String>();
			Map<String, String> temp2 = new HashMap<String, String>();
			int count = taxHdrService.getCount(temp1, temp2);
			session.setAttribute("recordCount", count);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new CustomException();
		}

		return table;
	}

	/**
	 * SystemSetting tax function
	 * 
	 * @param
	 * @return system setting tax
	 */
	@RequestMapping(value = "/systemSettingsData", method = RequestMethod.GET)
	public @ResponseBody List<SystemSettings> getRoomTypeData() throws Exception {
		List<SystemSettings> systemSettings = null;

		try {
			systemSettings = taxHdrService.getSystemSettingData();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new CustomException();
		}

		return systemSettings;
	}

	/**
	 * code Exist checking function
	 * 
	 * @param code
	 * @return true when code is exist / false when code is not exist
	 */
	@RequestMapping(value = "/checkCodeForDuplicate", method = RequestMethod.POST)
	public @ResponseBody boolean isCodeExist(@ModelAttribute(value = "code") String code) throws Exception {
		return taxHdrService.isCodeExists(code);
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
			@RequestParam(value = "advanceSearch") String advanceSearch) throws Exception {
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
			} catch (JsonParseException ex) {
				ex.printStackTrace();
				logger.error("Method : getCount " + Throwables.getStackTraceAsString(ex));
				throw new CustomException();
			}
		}

		int count = taxHdrService.getCount(advanceSearchMap, simpleSearchMap);

		return Integer.toString(count);
	}
}