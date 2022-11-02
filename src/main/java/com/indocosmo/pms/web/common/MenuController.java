package com.indocosmo.pms.web.common;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Scope("prototype")
public class MenuController {

	@RequestMapping(value = "/headerDetails", method = RequestMethod.GET)
	public String header(Model model) {
		return "home/header_details";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model) {
		return "rackrate/rackrate_edit";
	}
}