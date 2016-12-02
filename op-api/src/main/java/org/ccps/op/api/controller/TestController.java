package org.ccps.op.api.controller;

import org.ccps.op.api.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@Autowired
	private TestService testService;
	
	
	@RequestMapping(value = "/getuser", method = RequestMethod.GET)
	public String getShop(@RequestParam("id") int id) {
		System.out.println("+++++++++++++++++++++++++");
	     testService.test();	    
	     return "111aaa";
	}
	
}
