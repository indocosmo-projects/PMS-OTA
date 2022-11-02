package com.indocosmo.pms.web.facilities.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.indocosmo.pms.enumerator.common.FacilityTypes;
import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.facilities.model.Facility;
import com.indocosmo.pms.web.facilities.service.FacilityService;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.transaction.service.TxnService;

@Controller
@RequestMapping("/facilities")
public class FacilityController {

	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "access_denied/access_denied";

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	@Autowired
	private FacilityService facilityService;

	@Autowired
	private TxnService transactionService;

	private SysPermissions permissionObj;

	/**
	 * @param model
	 * @param session
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, HttpSession session, HttpServletResponse response) throws Exception {
		String pageUrl = "/facilities/facilities_edit";
		permissionObj = pageAccessPermissionService.getPermission(session, "MST_FLTY");
		if (permissionObj.isCanView() && permissionObj.isIs_view_applicable()) {
			model.addAttribute("curPagePerObj", permissionObj);
			model.addAttribute("facilityTypes", FacilityTypes.values());
		} else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}

	@RequestMapping(value = "/facilityDetails", method = RequestMethod.POST)
	public @ResponseBody String getProviders() {
		List<Facility> facilityList = facilityService.getFacilityList();
		String json = new Gson().toJson(facilityList);
		return json;
	}

	@RequestMapping(value = "/getAccountMasterDetails", method = RequestMethod.POST)
	public @ResponseBody String getAccountMasterDetails() {
		List<AccountMaster> accList = transactionService.getAccountMasterDetails();
		String json = new Gson().toJson(accList);
		return json;
	}

	/**
	 * @param facilityJson
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody String save(@RequestParam(value = "facilityJson") String facilityJson, HttpSession session)
			throws Exception {
		Gson g = new Gson();
		Facility facility = new Facility();
		facility = g.fromJson(facilityJson, Facility.class);
		if (!facility.isPayable()) {
			facility.setAmount(null);
		}
		int userId = ((User) session.getAttribute("userForm")).getId();
		facility.setUserId(userId);
		facility.setSystem(false);
		boolean isSave = facilityService.save(facility);
		String saveStatus = "success";

		if (isSave) {
			saveStatus = "status:" + saveStatus;
		} else {
			saveStatus = "status:error";
		}
		return g.toJson(saveStatus).toString();
	}

	/**
	 * @param facilityJson
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(@RequestParam(value = "facilityJson") String facilityJson, HttpSession session)
			throws Exception {
		Gson g = new Gson();
		Facility facility = new Facility();
		facility = g.fromJson(facilityJson, Facility.class);
		int userId = ((User) session.getAttribute("userForm")).getId();
		facility.setUserId(userId);
		if (!facility.isPayable()) {
			facility.setAmount(null);
		}
		boolean isSave = facilityService.save(facility);
		String saveStatus = "success";

		if (isSave) {
			saveStatus = "status:" + saveStatus;
		} else {
			saveStatus = "status:error";
		}
		return g.toJson(saveStatus).toString();
	}

	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String delete(@RequestParam(value = "id") int id) {
		boolean isDelete = facilityService.delete(id);
		String deleteStatus = "success";
		Gson g = new Gson();
		if (isDelete) {
			deleteStatus = "status:" + deleteStatus;
		} else {
			deleteStatus = "status:error";
		}
		return g.toJson(deleteStatus).toString();
	}
}
