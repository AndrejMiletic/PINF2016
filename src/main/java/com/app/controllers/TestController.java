package com.app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/test")
public class TestController {

	
	@RequestMapping(value="/all", method=RequestMethod.GET)
	public String getAllUsers()
	{	
		return "Hello world!";
	}
}
