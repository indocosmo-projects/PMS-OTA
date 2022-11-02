package com.indocosmo.pms.web.user.controller;

import java.util.List;

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
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.user.service.UserService;

@Controller
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	private SysPermissions permissionObj;

	public static final String USER_URL = "users/user_edit";

	@RequestMapping(value = "list")
	public String userGroupList(Model model, HttpSession session) throws Exception {

		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_USR_LIST");

		model.addAttribute("curPagePerObj", permissionObj);
		return ((permissionObj.isCanView() && permissionObj.isIs_view_applicable()) ? USER_URL
				: "access_denied/access_denied");

	}

	/**
	 * User list function
	 * 
	 * @return noshowJson
	 */
	@RequestMapping(value = "getUserList")
	public @ResponseBody String getUserList() {
		List<User> userGroup = userService.getUserList();
		String json = new Gson().toJson(userGroup);
		return json;
	}

	@RequestMapping(value = "getUser")
	public @ResponseBody String getUser() {
		List<User> userGrop = userService.getUser();
		String json = new Gson().toJson(userGrop);
		return json;
	}

	/**
	 * 
	 * @param loginId
	 * @param password
	 * @param name
	 * @param group
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String save(@RequestParam(value = "uLoginId") String loginId,
			@RequestParam(value = "uName") String name, @RequestParam(value = "uGroup") int group,
			@RequestParam(value = "uEmail") String email, @RequestParam(value = "uPassword") String password,
			@RequestParam(value = "gStatus") boolean status, @RequestParam(value = "gisCashier") boolean isCashier,
			@ModelAttribute User user) {
		user.setId(0);
		user.setLoginId(loginId);
		user.setName(name);
		user.setUserGroupId(group);
		user.setEmail(email);
		user.setPassword(password);
		user.setIsActive(status);
		user.setIsCashier(isCashier);
		user.setIsSystem(0);
		// user.setIsDeleted(0);
		user.setDeleted(false);
		boolean isSave = userService.save(user);
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
	 * @param userId
	 * @param loginId
	 * @param password
	 * @param name
	 * @param group
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String edit(@RequestParam(value = "uId") int userId,
			@RequestParam(value = "uLoginId") String loginId, @RequestParam(value = "uName") String name,
			@RequestParam(value = "uGroup") int group, @RequestParam(value = "uEmail") String email,
			@RequestParam(value = "uPassword") String password, @RequestParam(value = "gStatus") boolean status,
			@RequestParam(value = "gisCashier") boolean isCashier, @ModelAttribute User user) {
		user.setId(userId);
		user.setLoginId(loginId);
		user.setName(name);
		user.setUserGroupId(group);
		user.setEmail(email);
		user.setPassword(password);
		user.setIsActive(status);
		user.setIsCashier(isCashier);
		boolean isSave = userService.save(user);
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
	public @ResponseBody String delete(@RequestParam(value = "uId") int id) {
		boolean isDelete = userService.delete(id);
		String deleteStatus = "success";
		Gson g = new Gson();
		if (isDelete) {
			deleteStatus = "status:" + deleteStatus;
		} else {
			deleteStatus = "status:error";
		}
		return g.toJson(deleteStatus).toString();
	}

	/**
	 * user Exist checking function
	 * 
	 * @param userid
	 * @return true when user id is exist/ false when is not exist
	 */
	@RequestMapping(value = "userExist")
	public @ResponseBody String userExist(@RequestParam(value = "userId") String userId) throws Exception {
		boolean isExist = userService.userExist(userId);
		String userStatus = "exists";
		Gson g = new Gson();
		if (isExist) {
			userStatus = "status:" + userStatus;
		} else {
			userStatus = "status:error";
		}
		return g.toJson(userStatus).toString();
	}

}