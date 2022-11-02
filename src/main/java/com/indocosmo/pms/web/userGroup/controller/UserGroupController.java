package com.indocosmo.pms.web.userGroup.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.login.model.UserSession;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.userGroup.model.UserGroup;
import com.indocosmo.pms.web.userGroup.service.UserGroupService;

@Controller
@RequestMapping(value = "/userGroup")
public class UserGroupController {

	@Autowired
	UserGroupService userGroupService;

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	private SysPermissions permissionObj;

	public static final String USER_GROUP_URL = "user_group/user_group";

	@RequestMapping(value = "list")
	public String userGroupList(Model model, HttpSession session) throws Exception {

		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_USR_GRP");
		model.addAttribute("curPagePerObj", permissionObj);

		UserSession userSessionData = (UserSession) session.getAttribute("userSession");
		if (userSessionData.isAdmin()) {
			permissionObj.setCanAdd(true);
			permissionObj.setCanEdit(true);
			permissionObj.setCanView(true);
			permissionObj.setIs_view_applicable(true);
			permissionObj.setIs_edit_applicable(true);
		}

		return ((permissionObj.isCanView() && permissionObj.isIs_view_applicable()) ? USER_GROUP_URL
				: "access_denied/access_denied");
	}

	/**
	 * User Group list function
	 * 
	 * @return noshowJson
	 */
	@RequestMapping(value = "getUserGroupList")
	public @ResponseBody String getUserGroupList() {
		List<UserGroup> userGroup = userGroupService.getUserGroupList();
		String json = new Gson().toJson(userGroup);
		return json;
	}

	/**
	 * 
	 * @param code
	 * @param name
	 * @param description
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String save(@RequestParam(value = "groupCode") String code,
			@RequestParam(value = "groupName") String name, @RequestParam(value = "groupDesc") String description,
			@ModelAttribute UserGroup userGroup) {
		userGroup.setId(0);
		userGroup.setCode(code);
		userGroup.setName(name);
		userGroup.setDescription(description);
		userGroup.setIsSystem(true);
		userGroup.setDeleted(false);
		boolean isSave = userGroupService.save(userGroup);
		String saveStatus = "success";
		Gson g = new Gson();
		if (isSave) {
			saveStatus = "status:" + saveStatus;
		} else {
			saveStatus = "status:error";
		}
		return g.toJson(saveStatus).toString();
	}

	/**
	 * @param id
	 * @param code
	 * @param name
	 * @param description
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String edit(@RequestParam(value = "groupId") int id,
			@RequestParam(value = "groupCode") String code, @RequestParam(value = "groupName") String name,
			@RequestParam(value = "groupDesc") String description, @ModelAttribute UserGroup userGroup) {
		userGroup.setId(id);
		userGroup.setCode(code);
		userGroup.setName(name);
		userGroup.setDescription(description);
		userGroup.setIsSystem(true);
		userGroup.setDeleted(false);
		boolean isSave = userGroupService.save(userGroup);
		String saveStatus = "success";
		Gson g = new Gson();
		if (isSave) {
			saveStatus = "status:" + saveStatus;
		} else {
			saveStatus = "status:error";
		}
		return g.toJson(saveStatus).toString();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String delete(@RequestParam(value = "groupId") int id, HttpServletResponse response) {
		int isCount = userGroupService.canDelete(id);
		String deleteStatus = "success";
		Gson g = new Gson();
		if (isCount == 0) {
			boolean isDelete = userGroupService.delete(id);
			if (isDelete) {
				deleteStatus = "status:" + deleteStatus;
			} else {
				deleteStatus = "status:error";
			}
		} else {
			deleteStatus = "status_error_delete";
		}
		return g.toJson(deleteStatus).toString();
	}

	/**
	 * code Exist checking function
	 * 
	 * @param code
	 * @return true when code is exist/ false when is not exist
	 */
	@RequestMapping(value = "codeExist")
	public @ResponseBody String userExist(@RequestParam(value = "code") String code) throws Exception {
		boolean isExist = userGroupService.codeExist(code);
		String codeStatus = "exists";
		Gson g = new Gson();
		if (isExist) {
			codeStatus = "status:" + codeStatus;
		} else {
			codeStatus = "status:error";
		}
		return g.toJson(codeStatus).toString();
	}
}
