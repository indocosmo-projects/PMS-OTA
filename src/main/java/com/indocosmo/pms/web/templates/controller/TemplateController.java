package com.indocosmo.pms.web.templates.controller;

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
import com.google.gson.JsonObject;
import com.indocosmo.pms.enumerator.CommunicationGroups;
import com.indocosmo.pms.enumerator.CommunicationPurposes;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.templates.model.EmailTemplate;
import com.indocosmo.pms.web.templates.model.InvoiceTemplate;
import com.indocosmo.pms.web.templates.model.SmsTemplate;
import com.indocosmo.pms.web.templates.service.TemplateService;

@Controller
@RequestMapping("/templates")
public class TemplateController {
	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "access_denied/access_denied";

	@Autowired
	TemplateService templateService;

//	@Autowired
//	private JavaMailSender mailSender;

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
		String pageUrl = "/templates/templates_edit";
		permissionObj = pageAccessPermissionService.getPermission(session, "MST_TEMPLATE");
		if (permissionObj.isCanView() && permissionObj.isIs_view_applicable()) {
			model.addAttribute("curPagePerObj", permissionObj);
			model.addAttribute("CommunicationPurposes", CommunicationPurposes.values());
			model.addAttribute("CommunicationGroups", CommunicationGroups.values());
		} else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}

	/**
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getTemplateDetails", method = RequestMethod.GET)
	public @ResponseBody String getTemplateDetails(HttpSession session) throws Exception {
		JsonObject template = new JsonObject();
		InvoiceTemplate invTempl = templateService.getInvoiceTemplateDtls();
		List<EmailTemplate> emailTemplList = templateService.getEmailTemplateDtls();
		List<SmsTemplate> smsTemplList = templateService.getSmsTemplateDtls();

		for (int i = 0; i < emailTemplList.size(); i++) {

			emailTemplList.get(i)
					.setPurposeCode(CommunicationPurposes.get(emailTemplList.get(i).getPurpose()).getName());
			emailTemplList.get(i).setGroupCode(CommunicationGroups.get(emailTemplList.get(i).getGroup()).getName());
		}

		for (int i = 0; i < smsTemplList.size(); i++) {

			smsTemplList.get(i).setPurposeCode(CommunicationPurposes.get(smsTemplList.get(i).getPurpose()).getName());
			smsTemplList.get(i).setGroupCode(CommunicationGroups.get(smsTemplList.get(i).getGroup()).getName());
		}

		template.addProperty("invoice", new Gson().toJson(invTempl));
		template.addProperty("email", new Gson().toJson(emailTemplList));
		template.addProperty("sms", new Gson().toJson(smsTemplList));
		return template.toString();
	}

	/**
	 * @param inv
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "saveInvoice", method = RequestMethod.POST)
	public @ResponseBody String saveInvoice(@RequestParam(value = "invoice") String inv) throws Exception {
	//	JsonParser jparser = new JsonParser();
		Gson gson = new Gson();
		InvoiceTemplate invoiceTmpl = gson.fromJson(inv, InvoiceTemplate.class);

		boolean isSave = templateService.saveInvoice(invoiceTmpl);
		String saveStatus = "success";
		if (isSave) {
			saveStatus = "status:" + saveStatus;
		} else {
			saveStatus = "status:error";
		}
		return gson.toJson(saveStatus).toString();
	}

	/**
	 * @param email
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "saveEmail", method = RequestMethod.POST)
	public @ResponseBody String saveEmail(@RequestParam(value = "email") String email, HttpSession session)
			throws Exception {
//		JsonParser jparser = new JsonParser();
		Gson gson = new Gson();
		EmailTemplate EmailTmpl = gson.fromJson(email, EmailTemplate.class);
		int userId = ((User) session.getAttribute("userForm")).getId();
		EmailTmpl.setUser_id(userId);
		EmailTmpl.setIs_deleted(false);
		boolean isSave = templateService.saveEmail(EmailTmpl);
		String saveStatus = "success";
		if (isSave) {
			saveStatus = "status:" + saveStatus;
		} else {
			saveStatus = "status:error";
		}
		return gson.toJson(saveStatus).toString();
	}

	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteEmail", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String deleteEmail(@RequestParam(value = "id") int id) {
		boolean isDelete = templateService.deleteEmailTempl(id);
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
	 * @param sms
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "saveSms", method = RequestMethod.POST)
	public @ResponseBody String saveSms(@RequestParam(value = "sms") String sms, HttpSession session) throws Exception {
//		JsonParser jparser = new JsonParser();
		Gson gson = new Gson();
		SmsTemplate smsTmpl = gson.fromJson(sms, SmsTemplate.class);
		int userId = ((User) session.getAttribute("userForm")).getId();
		smsTmpl.setUser_id(userId);
		smsTmpl.setIs_deleted(false);
		boolean isSave = templateService.saveSms(smsTmpl);
		String saveStatus = "success";
		if (isSave) {
			saveStatus = "status:" + saveStatus;
		} else {
			saveStatus = "status:error";
		}
		return gson.toJson(saveStatus).toString();
	}

	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteSms", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String deleteSms(@RequestParam(value = "id") int id) {
		boolean isDelete = templateService.deleteSmsTempl(id);
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
