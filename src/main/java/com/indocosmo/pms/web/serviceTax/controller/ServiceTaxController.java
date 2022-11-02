package com.indocosmo.pms.web.serviceTax.controller;


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
import com.indocosmo.pms.web.serviceTax.model.serviceTax;
import com.indocosmo.pms.web.serviceTax.service.ServiceTaxService;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;

@Controller
@RequestMapping("/serviceTax")
public class ServiceTaxController {

public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "access_denied/access_denied";
	
	@Autowired
	private ServiceTaxService serviceTaxService; 
	
	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	private SysPermissions permissionObj;
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String list(Model model, HttpSession session,HttpServletResponse response ) throws Exception {
		String pageUrl="/service_tax/service_tax_edit";
		permissionObj = pageAccessPermissionService.getPermission(session,"MST_SRV_TAX");
		if(permissionObj.isCanView() && permissionObj.isIs_view_applicable() ){
			model.addAttribute("curPagePerObj",permissionObj);
		}else{
			pageUrl= SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;
	}
	
	@RequestMapping(value="/getServiceTaxDetails", method = RequestMethod.POST)
	public @ResponseBody String getServiceTaxDetails() throws Exception{
		serviceTax servTax =serviceTaxService.getServiceTaxDetails();
		String json = new Gson().toJson(servTax);
		return json;
	}
	
	@RequestMapping(value="save", method= RequestMethod.POST)
	public @ResponseBody String saveInvoice(@RequestParam (value="servicetax")String srvTax) throws Exception{		
	//	JsonParser jparser = new JsonParser();		
		Gson gson = new Gson();
		serviceTax  serviceTax = gson.fromJson(srvTax, serviceTax.class);
		
		boolean isSave=serviceTaxService.saveInvoice(serviceTax);
		String saveStatus = "success";	
		if(isSave){
			saveStatus="status:"+saveStatus;
		}else{
			saveStatus="status:error";
		}
		return gson.toJson(saveStatus).toString();
	}
	
}
