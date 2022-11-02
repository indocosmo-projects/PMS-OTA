package com.indocosmo.pms.web.cform.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.cform.model.CForm;
import com.indocosmo.pms.web.cform.service.CFormService;
import com.indocosmo.pms.web.templates.model.InvoiceTemplate;
import com.indocosmo.pms.web.templates.service.TemplateService;

@Controller
@RequestMapping(value = "/cform")
public class CFormController {
	public static final Logger logger = LoggerFactory.getLogger(CFormController.class);

	@Autowired
	CFormService cformService;

	@Autowired
	TemplateService templateService;

	@RequestMapping(value = "/cformList", method = RequestMethod.GET)
	public ModelAndView PrintCform(@RequestParam(value = "folioNo") int folioNo, HttpServletRequest request,
			HttpSession session) throws Exception {

		String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(),
				request.getServerPort());
		CForm cform = new CForm();
		JsonObject jobj = cformService.getDetailsCustomer(folioNo);
		cform.setName(String.valueOf(jobj.get("name").getAsString()));
		cform.setEmail(String.valueOf(jobj.get("email").getAsString()));
		cform.setPhone(String.valueOf(jobj.get("phone").getAsString()));
		cform.setAddress(String.valueOf(jobj.get("address").getAsString()));
		cform.setArr_date(String.valueOf(jobj.get("arrDate").getAsString()));
		cform.setArr_time(String.valueOf(jobj.get("arr_time").getAsString()));
		cform.setAct_depart_date(String.valueOf(jobj.get("depDate").getAsString()));
		cform.setGender(String.valueOf(jobj.get("gender").getAsString()));
		cform.setNationality(jobj.get("nationality").getAsString());
		cform.setPassportNo(String.valueOf(jobj.get("passport_no").getAsString()));
		cform.setPassport_expiry_on(String.valueOf(jobj.get("passport_expiry_on").getAsString()));
		cform.setNum_nights(String.valueOf(jobj.get("num_nights").getAsString()));
		cform.setRemarks(String.valueOf(jobj.get("remarks").getAsString()));
		InvoiceTemplate invTmpl = new InvoiceTemplate();
		invTmpl = templateService.getInvoiceTemplateDtls();
		String  companyN = (String) session.getAttribute("companyN");
		invTmpl.setHdrLogoUrl(baseUrl+(String) session.getAttribute("rootPath") + "/resources/common/images/logos_"+companyN+"/invoicelogo.png");
		cform.setHeaderFooter(invTmpl);
		return new ModelAndView("pdfViewCform", "listCform", cform);
	}
}
