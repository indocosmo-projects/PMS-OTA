package com.indocosmo.pms.web.setup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/setup")
public class SetupController {
	
	@RequestMapping(value="setup",method=RequestMethod.GET)
	public String departmentRedirect()throws Exception{ 
		//System.out.println("test");
		return "menu/setup_list";
	}
}
