package com.indocosmo.pms.web.discount.controller;

import java.util.HashMap;
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
import com.indocosmo.pms.enumerator.common.yesOrNoOptions;
import com.indocosmo.pms.enumerator.discount.DiscountCalculationMethod;
import com.indocosmo.pms.enumerator.discount.DiscountFor;
import com.indocosmo.pms.enumerator.discount.DiscountType;
import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.discount.model.Discount;
import com.indocosmo.pms.web.discount.service.DiscountService;
import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.roomrate.service.RoomRateService;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;

@Controller
@RequestMapping(value = "/discount")
public class DiscountController {

	public static final String DISCOUNT_EDIT_PAGE_URL = "discount/discount_edit";
	public static final Logger logger = LoggerFactory.getLogger(DiscountController.class);

	@Autowired
	private DiscountService discountService;

	@Autowired
	private RoomRateService roomRateService;

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	private SysPermissions permissionObj;

	/**
	 * DiscountList function
	 * 
	 * @param discount
	 *            DiscountModel
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
	public @ResponseBody JqGridListWrapperDTO list(@ModelAttribute("Discount") Discount discount,
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
		jqGridListWrapperDTO = discountService.list(currentPage, rowLimit, pagingStart, advanceSearchMap, sortVal,
				simpleSearchMap);

		session.removeAttribute("recordCount");

		return jqGridListWrapperDTO;
	}

	/**
	 * Discount module save and update function
	 * 
	 * @param discount
	 *            DiscountModel
	 * @return if record added successfully page redirect to discountList / not
	 *         success it will redirect to edit page
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@ModelAttribute Discount discount, HttpSession session) throws Exception {
		String redirectUrl = DISCOUNT_EDIT_PAGE_URL;
		boolean isDiscountSave = discountService.save(discount);

		if (isDiscountSave) {
			/**
			 * get count method is using to get total record count from table. record count
			 * session is using in jqGrid
			 */
			Map<String, String> temp1 = new HashMap<String, String>();
			Map<String, String> temp2 = new HashMap<String, String>();
			int count = discountService.getCount(temp1, temp2);
			session.setAttribute("recordCount", count);
			redirectUrl = "redirect:discountList";
		}

		return redirectUrl;
	}

	/**
	 * Discount module Single record access function
	 * 
	 * @param discount
	 *            DiscountModel
	 * @param model
	 * @param ids
	 *            primary key of table (encrypted)
	 * @return if the record is get the page redirect to edit page
	 */
	@RequestMapping(value = "getRecord", method = RequestMethod.GET)
	public String getRecord(@ModelAttribute Discount discount, Model model,
			@RequestParam(value = "ids", required = true) String ids, HttpSession session) throws Exception {
		Map<Integer, String> rateHdrList = null;

		try {

			permissionObj = pageAccessPermissionService.getPermission(session, "MST_DEPTMNT");
			model.addAttribute("curPagePerObj", permissionObj);
			Encryption encryption = new Encryption();
			int discountId = Integer.parseInt(encryption.decrypt(ids));
			discount = discountService.getRecord(discountId);
			String encryptedId = encryption.encrypt(Integer.toString(discount.getId()));
			discount.setEncryption(encryptedId);
			discount.setDateFormat(session.getAttribute("dateFormat").toString());
			rateHdrList = roomRateService.list(); // get current room rates from rateHdr table
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getRecord " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		model.addAttribute("calculationType", DiscountCalculationMethod.getDiscountcalculationmap());
		model.addAttribute("discountType", DiscountType.getDiscounttypemap());
		model.addAttribute("openOptions", yesOrNoOptions.getYesornomap());
		model.addAttribute("discountFor", DiscountFor.getDiscountformap());
		model.addAttribute("rateHdrList", rateHdrList);
		model.addAttribute("discount", discount);

		return DISCOUNT_EDIT_PAGE_URL;
	}

	/**
	 * Discount delete function
	 * 
	 * @param id
	 * @param session
	 * @return true when deletion is success / false when deletion is not success
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody boolean delete(@RequestParam(value = "ids") String discountIds, HttpSession session)
			throws Exception {

		boolean isDeleted = false;

		try {
			isDeleted = discountService.delete(discountIds);
			session.removeAttribute("recordCount");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new CustomException();
		}

		/**
		 * get count method is using to get total record count from table. record count
		 * session is using in jqGrid
		 */
		Map<String, String> temp1 = new HashMap<String, String>();
		Map<String, String> temp2 = new HashMap<String, String>();
		int count = discountService.getCount(temp1, temp2);
		session.setAttribute("recordCount", count);

		return isDeleted;
	}

	/**
	 * Edit redirect function
	 * 
	 * @param discount
	 *            Discount module
	 * @param model
	 * @return page redirect to edit page
	 */
	@RequestMapping(value = "/editRedirect", method = RequestMethod.GET)
	public String editRedirect(@ModelAttribute Discount discount, Model model, HttpSession session) throws Exception {
		Map<Integer, String> rateHdrList = null;
		rateHdrList = roomRateService.list(); // get current room rates from rateHdr table

		model.addAttribute("calculationType", DiscountCalculationMethod.getDiscountcalculationmap());
		model.addAttribute("discountType", DiscountType.getDiscounttypemap());
		model.addAttribute("openOptions", yesOrNoOptions.getYesornomap());
		model.addAttribute("discountFor", DiscountFor.getDiscountformap());
		model.addAttribute("rateHdrList", rateHdrList);
		discount.setDateFormat(session.getAttribute("dateFormat").toString());
		model.addAttribute("discount", discount);

		return DISCOUNT_EDIT_PAGE_URL;
	}

	/**
	 * code Exist checking function
	 * 
	 * @param code
	 * @return true when code is exist / false when code is not exist
	 */
	@RequestMapping(value = "codeExist", method = RequestMethod.GET)
	public @ResponseBody boolean codeExist(@RequestParam(value = "code") String code) throws Exception {
		boolean isExist = discountService.codeExist(code);

		return isExist;
	}

	/**
	 * List redirect function
	 * 
	 * @return page redirect to discount list
	 */
	@RequestMapping(value = "discountList", method = RequestMethod.GET)
	public String listRedirect(Model model, HttpSession session) throws Exception {

		permissionObj = pageAccessPermissionService.getPermission(session, "MST_DEPTMNT");
		model.addAttribute("curPagePerObj", permissionObj);

		return ((permissionObj.isCanView() && permissionObj.isIs_view_applicable()) ? "discount/discount_list"
				: "access_denied/access_denied");

	}

	/**
	 * 
	 * @param rateId
	 * @return
	 */
	@RequestMapping(value = "/getDiscountsByRateId", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Discount> getDiscountsByRateId(
			@RequestParam(value = "rateId", required = true) String rateId,
			@RequestParam(value = "selectedDate", required = true) String selectedDate) {

		// System.out.println(rateId+"-"+selectedDate);
		List<Discount> discountList = null;
		java.sql.Date sqlSDate = new java.sql.Date(Long.parseLong(selectedDate));

		// System.out.println("---"+sqlSDate);
		try {

			discountList = discountService.getDiscountsByRateId(rateId, sqlSDate);
		} catch (Exception e) {

			e.printStackTrace();
			logger.error("Method : getPlanBasedDiscountCodes " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return discountList;
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

		int count = discountService.getCount(advanceSearchMap, simpleSearchMap);

		return Integer.toString(count);
	}
}