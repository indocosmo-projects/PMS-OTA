package com.indocosmo.pms.web.agingAR.controller;

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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.web.agingAR.model.AgingAR;
import com.indocosmo.pms.web.agingAR.service.AgingARService;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.corporate.model.Corporate;
import com.indocosmo.pms.web.corporate.service.CorporateService;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;

@Controller
@RequestMapping(value = "/agingAR")
public class AgingARController {

	public static final Logger logger = LoggerFactory.getLogger(AgingARController.class);

	@Autowired
	AgingARService agingARService;

	@Autowired
	private CorporateService corporateService;

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

		permissionObj = pageAccessPermissionService.getPermission(session, "AGING_AR");
		model.addAttribute("curPagePerObj", permissionObj);

		return ((permissionObj.isCanView() && permissionObj.isIs_view_applicable()) ? "aging_ar/aging_ar"
				: "access_denied/access_denied");
	}

	@RequestMapping(value = "agingARList", method = RequestMethod.POST)
	public @ResponseBody List<AgingAR> getAgingArRList(Model model, HttpSession session) throws Exception {
		List<AgingAR> list = agingARService.getAgingARList();
		return list;
	}

	@RequestMapping(value = "/corporate", method = RequestMethod.POST)
	public @ResponseBody List<Corporate> corporateList() throws Exception {
		List<Corporate> corporateList = null;
		try {
			corporateList = corporateService.listOfCorporate(1, "");

		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		return corporateList;

	}

	@RequestMapping(value = "agingARDetails", method = RequestMethod.POST)
	public @ResponseBody List<AgingAR> getInvoiceDetails(@RequestParam(value = "corporate") String corporate)
			throws Exception {
		JsonParser parser = new JsonParser();
		JsonObject agingObj = parser.parse(corporate).getAsJsonObject();
		List<AgingAR> list = agingARService.getInvoiceDetails(agingObj);

		return list;
	}

	@RequestMapping(value = "/reportPrint")
	public ModelAndView AgingArRList() throws Exception {

		List<AgingAR> agingARList = agingARService.getAgingARList();
		System.out.println("inside reportprint"+agingARList.size());
		return new ModelAndView("pdfAgingReport", "summaryreportdata", agingARList);
		
	}

}
