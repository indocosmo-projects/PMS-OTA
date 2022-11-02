package com.indocosmo.pms.web.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.indocosmo.pms.web.common.service.CommonService;

@Controller
@RequestMapping(value = "/common")
public class CommonController {
	@Autowired
	CommonService commonService;

	@RequestMapping(value = "getState", method = RequestMethod.POST)
	public @ResponseBody String getState(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "data", required = true) String addon) throws Exception {

		JsonArray jArray = commonService.getStateList(addon);
		return jArray.toString();
	}

}
