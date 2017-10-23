package com.darkchitect.springboot.firstspringbootapp.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darkchitect.springboot.firstspringbootapp.configuration.BasicConfiguration;
import com.darkchitect.springboot.firstspringbootapp.service.WelcomeService;

@RestController
public class WelcomeController {

	//Auto wiring
	@Autowired
	private WelcomeService service;

	@Autowired
	private BasicConfiguration configuration;
	
	@GetMapping("/welcome")
	public String welcome() {
		return service.retrieveWelcomeMessage();
	}
	
	@GetMapping("dynamic-configuration")
	public Map dynamicConfiguration() {
		Map	map = new HashMap();
		map.put("message", configuration.getMessage());
		map.put("number", configuration.getNumber());
		map.put("value", configuration.isValue());
		
		return map;
	}
}