package com.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/test")
public class TestController {

	
	@RequestMapping(value="/all", method=RequestMethod.GET)
	public ResponseEntity<String> getAllUsers()
	{	
		return new ResponseEntity<>("Hello from the other side!", HttpStatus.OK);
	}
}
