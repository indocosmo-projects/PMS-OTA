package com.indocosmo.pms.web.currency.controller;

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
import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.currency.model.Currency;
import com.indocosmo.pms.web.currency.service.CurrencyService;
import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;

@Controller
@RequestMapping(value = "/currency")

public class CurrencyController {

	public static final String CURRENCY_EDIT_PAGE_URL = "currency/currency_edit";
	public static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

	@Autowired
	CurrencyService currencyService;

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	private SysPermissions permissionObj;

	/**
	 * Currency module save and update function
	 * 
	 * @param currency
	 *            currencyModel
	 * @return if record added successfully page redirect to currencyList / not
	 *         success it will redirect to edit page
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String save(@ModelAttribute Currency currency, HttpSession session) throws Exception {
		String redirectUrl = CURRENCY_EDIT_PAGE_URL;
		currency.setLastUpdates(currency.getUpdatedDate()); // lastUpdate field is not in jsp page .its hard coding
															// using updated date
		boolean isCurrencySave = currencyService.save(currency);

		if (isCurrencySave) {
			/**
			 * get count method is using to get total record count from table. record count
			 * session is using in jqGrid
			 */
			Map<String, String> advanceSearchMap = new HashMap<String, String>();
			Map<String, String> simpleSearchMap = new HashMap<String, String>();
			int count = currencyService.getCount(advanceSearchMap, simpleSearchMap);
			session.setAttribute("recordCount", count);

			redirectUrl = "redirect:currencyList";
		}

		return redirectUrl;
	}

	/**
	 * Currency module Single record access function
	 * 
	 * @param currency
	 *            CurrencyModel
	 * @param model
	 * @param id
	 *            primary key of table (encrypted)
	 * @return if the record is get the page redirect to edit page
	 */
	@RequestMapping(value = "getRecord", method = RequestMethod.GET)
	public String getRecord(@ModelAttribute Currency currency, Model model,
			@RequestParam(value = "ids", required = true) String id, HttpSession session) throws Exception {
		permissionObj = pageAccessPermissionService.getPermission(session, "MST_CURENCY");
		model.addAttribute("curPagePerObj", permissionObj);

		try {
			Encryption encryption = new Encryption();
			int currencyId = Integer.parseInt(encryption.decrypt(id));
			currency = currencyService.getRecord(currencyId);
			String encryptedId = encryption.encrypt(Integer.toString(currency.getId()));
			currency.setEncryption(encryptedId);
			currency.setDateFormat(session.getAttribute("dateFormat").toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getRecord " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		model.addAttribute("currency", currency);

		return CURRENCY_EDIT_PAGE_URL;
	}

	/**
	 * CurrecyList function
	 * 
	 * @param currency
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
	public @ResponseBody JqGridListWrapperDTO list(@ModelAttribute("currency") Currency currency,
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
		jqGridListWrapperDTO = currencyService.list(currentPage, rowLimit, pagingStart, advanceSearchMap, sortVal,
				simpleSearchMap);

		session.removeAttribute("recordCount");

		return jqGridListWrapperDTO;
	}

	/**
	 * Currency delete function
	 * 
	 * @param id
	 * @param session
	 * @return true when deletion is success / false when deletion is not success
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody boolean delete(@RequestParam(value = "ids") int id, HttpSession session) throws Exception {

		boolean isDeleted = false;

		try {
			isDeleted = currencyService.delete(id);
			session.removeAttribute("recordCount");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new CustomException();
		}

		/**
		 * get count method is using to get total record count from table. record count
		 * session is using in jqGrid
		 */
		Map<String, String> advanceSearchMap = new HashMap<String, String>();
		Map<String, String> simpleSearchMap = new HashMap<String, String>();
		int count = currencyService.getCount(advanceSearchMap, simpleSearchMap);
		session.setAttribute("recordCount", count);

		return isDeleted;
	}

	/**
	 * List redirect function
	 * 
	 * @return page redirect to currency list
	 */
	@RequestMapping(value = "currencyList", method = RequestMethod.GET)
	public String listRedirect(Model model, HttpSession session) throws Exception {

		permissionObj = pageAccessPermissionService.getPermission(session, "MST_CURENCY");
		model.addAttribute("curPagePerObj", permissionObj);

		return ((permissionObj.isCanView() && permissionObj.isIs_view_applicable()) ? "currency/currency_list"
				: "access_denied/access_denied");

	}

	/**
	 * Edit redirect function
	 * 
	 * @param currency
	 * @param model
	 * @return page redirect to edit page
	 */
	@RequestMapping(value = "editRedirect", method = RequestMethod.GET)
	public String editRedirect(@ModelAttribute Currency currency, Model model, HttpSession session) throws Exception {
		currency.setDateFormat(session.getAttribute("dateFormat").toString());
		model.addAttribute("currency", currency);

		return CURRENCY_EDIT_PAGE_URL;
	}

	/**
	 * function of get total count
	 * 
	 * @param simpleSearchData
	 * @param advanceSearch
	 * @return total count of records
	 */
	@RequestMapping(value = "getCount", method = RequestMethod.POST)
	public @ResponseBody String getCount(@RequestParam(value = "simpleSearch") String simpleSearchData,
			@RequestParam(value = "advanceSearch") String advanceSearchData) throws Exception {
		@SuppressWarnings("unused")
		String simpleSearch = "";
		Map<String, String> simpleSearchMap = new HashMap<String, String>();

		if (simpleSearchData != null && !simpleSearchData.equals("")) {
			simpleSearchMap.put("code", simpleSearchData);
			simpleSearchMap.put("name", simpleSearchData);
			simpleSearch = "code = " + simpleSearchData + "and name =" + simpleSearchData;
		}

		@SuppressWarnings("unused")
		String advanceSearch = advanceSearchData;
		Map<String, String> advanceSearchMap = new HashMap<String, String>();

		if (advanceSearchData != null && !advanceSearchData.equals("null") && !advanceSearchData.equals("")) {
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				advanceSearchMap = objectMapper.readValue(advanceSearchData,
						new TypeReference<HashMap<String, String>>() {
						});
			} catch (JsonParseException ex) {
				ex.printStackTrace();
				logger.error("Method : getCount " + Throwables.getStackTraceAsString(ex));
				throw new CustomException();
			}
		}

		int count = currencyService.getCount(advanceSearchMap, simpleSearchMap);

		return Integer.toString(count);
	}

	/**
	 * code Exist checking function
	 * 
	 * @param code
	 * @return true when code is exist / false when code is not exist
	 */
	@RequestMapping(value = "codeExist", method = RequestMethod.GET)
	public @ResponseBody boolean codeExist(@RequestParam(value = "code") String code) throws Exception {
		boolean isExist = currencyService.codeExist(code);

		return isExist;
	}

	/**
	 * get currency symbol from currency table
	 * 
	 */
	@RequestMapping(value = "getCurrencySymbol", method = RequestMethod.GET)
	public @ResponseBody String getCurrencySymbol() throws Exception {
		String symbol = currencyService.getCurrencySymbol();

		return symbol;
	}
}