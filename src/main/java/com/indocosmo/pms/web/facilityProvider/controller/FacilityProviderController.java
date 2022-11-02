package com.indocosmo.pms.web.facilityProvider.controller;

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
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.facilityProvider.model.FacilityProvider;
import com.indocosmo.pms.web.facilityProvider.service.FacilityProviderService;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;

@Controller
@RequestMapping("/facilityProvider")
public class FacilityProviderController {
	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "access_denied/access_denied";

	@Autowired
	FacilityProviderService facilityProviderService;

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

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
		String pageUrl = "/facility_provider/facility_provider_edit";
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_RECPN_FLTY_PVDR");
		if (permissionObj.isCanView() && permissionObj.isIs_view_applicable()) {
			model.addAttribute("curPagePerObj", permissionObj);
			model.addAttribute("facilityTypes", FacilityTypes.values());
		} else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}

	@RequestMapping(value = "/facilityProviderDetails", method = RequestMethod.POST)
	public @ResponseBody String getProviders() {
		List<FacilityProvider> providerList = facilityProviderService.getFacilityProviderList();
		String json = new Gson().toJson(providerList);
		return json;
	}

	/**
	 * @param providerJson
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody String save(@RequestParam(value = "providerJson") String providerJson, HttpSession session)
			throws Exception {
		Gson g = new Gson();
		FacilityProvider fprov = new FacilityProvider();
		fprov = g.fromJson(providerJson, FacilityProvider.class);
		int userId = ((User) session.getAttribute("userForm")).getId();
		fprov.setUserId(userId);
		boolean isSave = facilityProviderService.save(fprov);
		String saveStatus = "success";

		if (isSave) {
			saveStatus = "status:" + saveStatus;
		} else {
			saveStatus = "status:error";
		}
		return g.toJson(saveStatus).toString();
	}

	/**
	 * @param providerJson
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(@RequestParam(value = "providerJson") String providerJson, HttpSession session)
			throws Exception {
		Gson g = new Gson();
		FacilityProvider fprov = new FacilityProvider();
		fprov = g.fromJson(providerJson, FacilityProvider.class);
		int userId = ((User) session.getAttribute("userForm")).getId();
		fprov.setUserId(userId);
		boolean isSave = facilityProviderService.save(fprov);
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
		boolean isDelete = facilityProviderService.delete(id);
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
