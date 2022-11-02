package com.indocosmo.pms.web.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.enumerator.CommunicationGroups;
import com.indocosmo.pms.enumerator.CommunicationPurposes;
import com.indocosmo.pms.enumerator.CommunicationType;
import com.indocosmo.pms.web.checkIn.controller.CheckInController;
import com.indocosmo.pms.web.common.model.CommunicationDetails;
import com.indocosmo.pms.web.common.service.CommonService;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;

@Controller
@RequestMapping(value = "/communication")
public class CommunicationController {

	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "access_denied/access_denied";

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	@Autowired
	private CommonService commonService;

	private SysPermissions permissionObj;

	public static final Logger logger = LoggerFactory.getLogger(CheckInController.class);

	/**
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String CommunicationList(HttpSession session, Model model) throws Exception {
		session.removeAttribute("CommunicationGroup");
		session.removeAttribute("CommunicationCheckInNo");
		session.removeAttribute("CommunicationResvnNo");
		String pageUrl = "/communication/communication_list";
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_COMMTN");
		model.addAttribute("curPagePerObj", permissionObj);
		if (permissionObj.isCanView() && permissionObj.isIs_view_applicable()) {
			model.addAttribute("CommunicationGroups", CommunicationGroups.values());
		} else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}

	/**
	 * @param commtn
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getData", method = RequestMethod.POST)
	public @ResponseBody String getCommunicationData(@RequestParam(value = "communication") String commtn)
			throws Exception {
		JsonParser jParse = new JsonParser();
		JsonObject jObj = (JsonObject) jParse.parse(commtn);
		JsonArray jsonArray = commonService.getCommunicationGuestDetails(jObj);
		String json = new Gson().toJson(jsonArray);
		return json;
	}

	/**
	 * @param session
	 * @param model
	 * @param group
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCommunicationDetail")
	public @ResponseBody String getCommunicationDetails(HttpSession session, Model model,
			@RequestParam(value = "group") String group, @RequestParam(value = "id") String id) throws Exception {
		Gson g = new Gson();
		String status = "success";
		int checkInNo = 0, resvnNo = 0;
		try {
			if (group.equals("checkIn")) {
				checkInNo = Integer.parseInt(id);
			} else if (group.equals("resvn")) {
				resvnNo = Integer.parseInt(id);
			}
			session.setAttribute("CommunicationGroup", group);
			session.setAttribute("CommunicationCheckInNo", checkInNo);
			session.setAttribute("CommunicationResvnNo", resvnNo);
		} catch (Exception e) {
			e.printStackTrace();
			status = "error";
			throw new CustomException();
		}
		status = "status:" + status;
		return g.toJson(status).toString();
	}

	/**
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCommunicationDetailPage")
	public String getCommunicationDetailPage(HttpSession session, Model model) throws Exception {
		String group = session.getAttribute("CommunicationGroup").toString();
		int checkInNo = 0, resvnNo = 0;
		String pageUrl = "communication/communication_edit";
		permissionObj = pageAccessPermissionService.getPermission(session, "PMS_COMMTN");
		try {
			model.addAttribute("curPagePerObj", permissionObj);
			if (permissionObj.isCanView() && permissionObj.isIs_view_applicable()) {
				if (group.equals("checkIn")) {
					checkInNo = Integer.parseInt(session.getAttribute("CommunicationCheckInNo").toString());
					JsonObject chInobj = commonService.getCheckInguestData(checkInNo);
					model.addAttribute("arrivalDate", chInobj.get("arr_date").getAsString());
					model.addAttribute("departDate", chInobj.get("exp_depart_date").getAsString());
					model.addAttribute("name", chInobj.get("name").getAsString());
					model.addAttribute("phone", chInobj.get("phone").getAsString());
					model.addAttribute("email", chInobj.get("email").getAsString());
					model.addAttribute("address", chInobj.get("address").getAsString());
					model.addAttribute("roomNo", chInobj.get("room_number").getAsString());
					model.addAttribute("roomType", chInobj.get("room_type_code").getAsString());
					model.addAttribute("ratePlan", chInobj.get("rate_code").getAsString());

				} else if (group.equals("resvn")) {
					resvnNo = Integer.parseInt(session.getAttribute("CommunicationResvnNo").toString());
					JsonObject chInobj = commonService.getReservationGuestData(resvnNo);
					model.addAttribute("reserved_by", chInobj.get("reserved_by").getAsString());
					model.addAttribute("resv_by_phone", chInobj.get("resv_by_phone").getAsString());
					model.addAttribute("resv_by_mail", chInobj.get("resv_by_mail").getAsString());
					model.addAttribute("resv_by_address", chInobj.get("resv_by_address").getAsString());
					model.addAttribute("resv_date", chInobj.get("resv_date").getAsString());
					model.addAttribute("resv_for", chInobj.get("resv_for").getAsString());
					model.addAttribute("cut_off_date", chInobj.get("cut_off_date").getAsString());
					model.addAttribute("arr_date", chInobj.get("arr_date").getAsString());
					model.addAttribute("depart_date", chInobj.get("depart_date").getAsString());
					model.addAttribute("num_nights", chInobj.get("num_nights").getAsString());
					model.addAttribute("num_rooms", chInobj.get("num_rooms").getAsString());
					model.addAttribute("resv_status", chInobj.get("resv_status").getAsString());
				}
				model.addAttribute("group", group);
				model.addAttribute("CommunicationPurposes", CommunicationPurposes.values());
			} else {
				pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageUrl;
	}

	/**
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCommunicationData")
	public @ResponseBody String getCommunicationData(HttpSession session) throws Exception {
		String group = session.getAttribute("CommunicationGroup").toString();
		List<CommunicationDetails> communicationDataList = new ArrayList<CommunicationDetails>();
		if (group.equals("checkIn")) {
			int checkInNum = Integer.parseInt(session.getAttribute("CommunicationCheckInNo").toString());
			communicationDataList = commonService.getCommunicationData(checkInNum, group);
		} else if (group.equals("resvn")) {
			int resvnNum = Integer.parseInt(session.getAttribute("CommunicationResvnNo").toString());
			communicationDataList = commonService.getCommunicationData(resvnNum, group);
		}
		Gson g = new Gson();
		return g.toJson(communicationDataList);
	}

	/**
	 * @param commtnJson
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveCommunication", method = RequestMethod.POST)
	public @ResponseBody String saveCommunication(@RequestParam(value = "newCommunication") String commtnJson,
			HttpSession session) throws Exception {
		Gson g = new Gson();
		boolean isSave;
		String saveStatus = "success";
		JsonParser jParse = new JsonParser();
		try {
			JsonObject jsonObj = (JsonObject) jParse.parse(commtnJson);
			CommunicationDetails commDtl = new CommunicationDetails();
			commDtl.setCommunicationType((byte) CommunicationType.TELEPHONE.getCode());
			commDtl.setPurpose(jsonObj.get("purpose").getAsByte());
			commDtl.setDescription(jsonObj.get("description").getAsString());
			commDtl.setStatus(jsonObj.get("status").getAsBoolean());
			commDtl.setPhone(jsonObj.get("phone").getAsString());
			commDtl.setSubject(jsonObj.get("subject").getAsString());
			String group = session.getAttribute("CommunicationGroup").toString();
			if (group.equals("checkIn")) {
				commDtl.setChkInNum(Integer.parseInt(session.getAttribute("CommunicationCheckInNo").toString()));
			} else if (group.equals("resvn")) {
				commDtl.setResvNum(Integer.parseInt(session.getAttribute("CommunicationResvnNo").toString()));
			}
			isSave = commonService.saveCommunicationDetails(commDtl);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		if (isSave) {
			saveStatus = "status:" + saveStatus;
		} else {
			saveStatus = "status:error";
		}
		return g.toJson(saveStatus).toString();
	}

	@RequestMapping(value = "/saveCommunictnMail", method = RequestMethod.POST)
	public @ResponseBody boolean saveCommunictnMail(
			@RequestParam(value = "newCommunicationMail") String newCommunicationMail, HttpSession session)
			throws Exception {
	//	Gson g = new Gson();
		boolean mailStatus;
	//	String saveStatus = "success";
		JsonParser jParse = new JsonParser();
		CommunicationDetails commDtlMail = new CommunicationDetails();
		String group = session.getAttribute("CommunicationGroup").toString();
		try {
			JsonObject jsonObj = (JsonObject) jParse.parse(newCommunicationMail);
			commDtlMail.setCommunicationType((byte) CommunicationType.EMAIL.getCode());
			commDtlMail.setPurpose(jsonObj.get("selectedPurpose").getAsByte());
			commDtlMail.setContent(jsonObj.get("content").getAsString());
			commDtlMail.setEmailto(jsonObj.get("email").getAsString());
			commDtlMail.setEmailcc(jsonObj.get("cc").getAsString());
			commDtlMail.setPhone(jsonObj.get("phone").getAsString());
			commDtlMail.setDescription(jsonObj.get("description").getAsString());
			commDtlMail.setSubject(jsonObj.get("mailSubject").getAsString());

			commDtlMail.setStatus(true);
			if (group.equals("checkIn")) {
				commDtlMail.setChkInNum(Integer.parseInt(session.getAttribute("CommunicationCheckInNo").toString()));
			} else if (group.equals("resvn")) {
				commDtlMail.setResvNum(Integer.parseInt(session.getAttribute("CommunicationResvnNo").toString()));
			}
			mailStatus = commonService.saveCommunicationDetailsMail(commDtlMail);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		
		return mailStatus;
	}

	@RequestMapping(value = "/ResendCommunictnMail", method = RequestMethod.POST)
	public @ResponseBody String ResendCommunictnMail(
			@RequestParam(value = "resendCommunicationMail") String resendCommunicationMail, HttpSession session)
			throws Exception {
		Gson g = new Gson();
		boolean isSave;
		String saveStatus = "success";
		JsonParser jParse = new JsonParser();
		CommunicationDetails commDtlMail = new CommunicationDetails();
		String group = session.getAttribute("CommunicationGroup").toString();
		try {
			JsonObject jsonObj = (JsonObject) jParse.parse(resendCommunicationMail);
			commDtlMail.setCommunicationType((byte) CommunicationType.EMAIL.getCode());
			commDtlMail.setPurpose(jsonObj.get("selectedPurpose").getAsByte());
			commDtlMail.setContent(jsonObj.get("content").getAsString());
			commDtlMail.setEmailto(jsonObj.get("email").getAsString());
			commDtlMail.setEmailcc(jsonObj.get("cc").getAsString());
			commDtlMail.setPhone(jsonObj.get("phone").getAsString());
			commDtlMail.setDescription(jsonObj.get("description").getAsString());
			commDtlMail.setSubject(jsonObj.get("mailSubject").getAsString());
			commDtlMail.setId(jsonObj.get("id").getAsInt());

			commDtlMail.setStatus(true);
			if (group.equals("checkIn")) {
				commDtlMail.setChkInNum(Integer.parseInt(session.getAttribute("CommunicationCheckInNo").toString()));
			} else if (group.equals("resvn")) {
				commDtlMail.setResvNum(Integer.parseInt(session.getAttribute("CommunicationResvnNo").toString()));
			}
			isSave = commonService.saveCommunicationDetailsMail(commDtlMail);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		if (!isSave) {
			saveStatus = "status:error";
		} /*
			 * else{ saveStatus="status:error"; }
			 */
		return g.toJson(saveStatus).toString();
	}

	@RequestMapping(value = "/editCommunication", method = RequestMethod.POST)
	public @ResponseBody String editCommunication(@RequestParam(value = "editCommunication") String editCommunication,
			HttpSession session) throws Exception {

		Gson g = new Gson();
		boolean isSave;
		String saveStatus = "success";
		JsonParser jParse = new JsonParser();
		try {
			JsonObject jsonObj = (JsonObject) jParse.parse(editCommunication);
			CommunicationDetails commDtl = new CommunicationDetails();
			commDtl.setCommunicationType((byte) CommunicationType.TELEPHONE.getCode());
			commDtl.setPurpose(jsonObj.get("purpose").getAsByte());
			commDtl.setDescription(jsonObj.get("description").getAsString());
			commDtl.setStatus(jsonObj.get("status").getAsBoolean());
			commDtl.setPhone(jsonObj.get("phone").getAsString());
			commDtl.setSubject(jsonObj.get("subject").getAsString());
			commDtl.setId(jsonObj.get("id").getAsInt());
			String group = session.getAttribute("CommunicationGroup").toString();
			if (group.equals("checkIn")) {
				commDtl.setChkInNum(Integer.parseInt(session.getAttribute("CommunicationCheckInNo").toString()));
			} else if (group.equals("resvn")) {
				commDtl.setResvNum(Integer.parseInt(session.getAttribute("CommunicationResvnNo").toString()));
			}
			isSave = commonService.saveCommunicationDetails(commDtl);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		if (isSave) {
			saveStatus = "status:" + saveStatus;
		} else {
			saveStatus = "status:error";
		}
		return g.toJson(saveStatus).toString();

	}
}
