package com.example.sampleService.controllers;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.sampleService.utils.Utils;


@RestController
//@RequestMapping("/cache")
public class MainController {
	
	@Autowired
	Utils utils;
	
	@Value("${backendURL:localhost}")
	String backUrl;
	

	private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());
	
	@RequestMapping(value="/**/",method=RequestMethod.GET)
	public ResponseEntity<String> MainResponse(HttpServletRequest request){
	    String uri = request.getRequestURI();
		
		String returnStr = null;
		try {
			returnStr = utils.getFile(uri);
		} catch (IOException e) {
			LOGGER.severe("Error finding file for URI: " + uri + " ErrorMessage: " + e.getMessage());
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
		
		LOGGER.info("Returning for URI: "+uri);
		return new ResponseEntity<String>(returnStr,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/backend",method=RequestMethod.GET)
	public String getbackend() {
		RestTemplate template = new RestTemplate();
		System.out.println(("NEW ENDPOINT"));
		
		URI uri = URI.create("http://" + backUrl );
		return (template.getForEntity(uri, String.class)).getBody();
		
	}
	
	@RequestMapping(value="/lunch",method=RequestMethod.GET)
	public String lunchNLearn() {
		
		return "Lunch-n-Learn";
		
	}
}
