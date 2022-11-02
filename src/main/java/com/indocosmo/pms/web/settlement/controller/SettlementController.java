package com.indocosmo.pms.web.settlement.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.settlement.model.Settlement;
import com.indocosmo.pms.web.settlement.service.SettlementService;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;

@Controller
@RequestMapping(value = "/settlement")
public class SettlementController {

	public static final Logger logger = LoggerFactory.getLogger(SettlementController.class);

	@Autowired
	SettlementService settlementService;

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	private SysPermissions permissionObj;

	/**
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String listRedirect(Model model, HttpSession session) throws Exception {

		permissionObj = pageAccessPermissionService.getPermission(session, "SETTLEMENT");
		model.addAttribute("curPagePerObj", permissionObj);

		return ((permissionObj.isCanView() && permissionObj.isIs_view_applicable()) ? "settlement/settlement"
				: "access_denied/access_denied");
	}

	@RequestMapping(value = "settlementList", method = RequestMethod.POST)
	public @ResponseBody List<Settlement> getSettlementList(
			@RequestParam(value = "searchData") String searchData) throws Exception {

		JsonParser parser = new JsonParser();
		JsonObject obj = parser.parse(searchData).getAsJsonObject();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Integer corporate = obj.get("searchCorp").getAsInt();
		java.sql.Date startDate = new java.sql.Date(sdf.parse(obj.get("startDate").getAsString()).getTime());	
		java.sql.Date endDate = new java.sql.Date(sdf.parse(obj.get("endDate").getAsString()).getTime());	
		JsonObject paginationObj = obj.get("pagination").getAsJsonObject();
		
		List<Settlement> list = settlementService.getSettlementList(startDate, endDate, corporate, paginationObj);
		return list;
	}

	@RequestMapping(value = "settlementDetails", method = RequestMethod.POST)
	public @ResponseBody List<Settlement> getInvoiceDetails(@RequestParam(value="corporate") int corporate) throws Exception {

		List<Settlement> list = settlementService.getInvoiceDetails(corporate);

		return list;
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody boolean save(@RequestParam(value = "settlementDetails") Object settlementDetails)
			throws Exception {

		JsonParser parser = new JsonParser();
		JsonArray settlementArray = parser.parse(settlementDetails.toString()).getAsJsonArray();

		boolean isSave = settlementService.save(settlementArray);

		return isSave;
	}

	@RequestMapping(value = "getCorporates", method = RequestMethod.POST)
	public @ResponseBody String getCorporates() throws SQLException {
		JsonArray corporates = settlementService.getCorporates();

		return corporates.toString();
	}
	
	@RequestMapping(value = "printReceipt")
	public ModelAndView printReceipt(@RequestParam(value = "voucherNo") String voucherNo) {
		JsonObject obj = settlementService.getReceiptData(voucherNo);
		return new ModelAndView("pdfCreditorsReceiptReport", "receiptData", obj);
	}
	
	@RequestMapping(value="saveNew", method = RequestMethod.POST)
	public @ResponseBody boolean saveNewPayment(@RequestParam(value="saveData") String saveData, HttpSession session) {
		JsonParser parser = new JsonParser();
		JsonObject newPaymentObject = parser.parse(saveData).getAsJsonObject();
		int currentUser = ((User) session.getAttribute("userForm")).getId();
		boolean isSave = settlementService.saveNewPayment(newPaymentObject, currentUser);
		
		return isSave;
	}
	
	@RequestMapping(value = "updatePayment")
	public @ResponseBody boolean updatePayment(@RequestParam(value="saveData") String saveData, HttpSession session) {
		JsonParser parser = new JsonParser();
		JsonObject newPaymentObject = parser.parse(saveData).getAsJsonObject();
		int currentUser = ((User) session.getAttribute("userForm")).getId();
		boolean isSave = settlementService.updatePayment(newPaymentObject, currentUser);
		
		return isSave;
	}
	
	@RequestMapping(value="saveAllocation", method = RequestMethod.POST)
	public @ResponseBody boolean saveAllocation(@RequestParam(value="settlement") String settlement) {
		JsonParser parser = new JsonParser();
		JsonObject settlementObject = parser.parse(settlement).getAsJsonObject();
		
		return settlementService.saveAllocation(settlementObject);
	}
	
	@RequestMapping(value="getOutstanding", method = RequestMethod.GET)
	public @ResponseBody double getOutstanding(@RequestParam(value="corporate") int corporate) {
		return settlementService.getOutstanding(corporate);
	}
	
}
