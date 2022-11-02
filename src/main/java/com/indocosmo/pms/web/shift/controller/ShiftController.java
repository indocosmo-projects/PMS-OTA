package com.indocosmo.pms.web.shift.controller;

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
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.shift.model.Shift;
import com.indocosmo.pms.web.shift.service.ShiftService;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;

@Controller
@RequestMapping("/shift")

public class ShiftController {
	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "access_denied/access_denied";

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	@Autowired
	private ShiftService shiftService;

	private SysPermissions permissionObj;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, HttpSession session, HttpServletResponse response) throws Exception {
		String pageUrl = "/shift/shift_edit";
		permissionObj = pageAccessPermissionService.getPermission(session, "MST_SHIFT");
		if (permissionObj.isCanView() && permissionObj.isIs_view_applicable()) {
			model.addAttribute("curPagePerObj", permissionObj);
		} else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}

	@RequestMapping(value = "/shiftDetails", method = RequestMethod.POST)
	public @ResponseBody String getProviders() {

		List<Shift> shiftList = shiftService.getShiftList();
		String json = new Gson().toJson(shiftList);
		return json;

	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody String save(@RequestParam(value = "shiftJson") String shiftJson, HttpSession session)
			throws Exception {
	//	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	//	SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Gson g = new Gson();
		Shift shift = new Shift();
		shift = g.fromJson(shiftJson, Shift.class);
		shift.setSystem(false);
	//	int userId = ((User) session.getAttribute("userForm")).getId();
		boolean isSave = shiftService.save(shift);
		String saveStatus = "success";

		if (isSave) {
			saveStatus = "status:" + saveStatus;
		} else {
			saveStatus = "status:error";
		}
		return g.toJson(saveStatus).toString();
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(@RequestParam(value = "shiftJson") String shiftJson, HttpSession session)
			throws Exception {
		Gson g = new Gson();
		Shift shift = new Shift();
		shift = g.fromJson(shiftJson, Shift.class);
		shift.setSystem(false);

	//	int userId = ((User) session.getAttribute("userForm")).getId();
		boolean isSave = shiftService.save(shift);
		String saveStatus = "success";

		if (isSave) {
			saveStatus = "status:" + saveStatus;
		} else {
			saveStatus = "status:error";
		}
		return g.toJson(saveStatus).toString();

	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String delete(@RequestParam(value = "id") int id) {

		boolean isDelete = shiftService.delete(id);
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
