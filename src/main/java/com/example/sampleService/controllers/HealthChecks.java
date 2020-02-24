package com.example.sampleService.controllers;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.sampleService.utils.Utils;

@RestController
@RequestMapping("/health")
public class HealthChecks {

	private static final Logger LOGGER = Logger.getLogger(HealthChecks.class.getName());
	
	//by default the liveliness probe will always return alive.  
	//set the livetime to number of milliseconds for the pod to stay alive. 
	@Value("${livetime:0}")
	private Long livetime;
	
	@Autowired
	Utils utils;
	
	@RequestMapping(value="/readiness",method=RequestMethod.GET)
	public ResponseEntity<String> Readiness(HttpServletRequest request){
		
		LOGGER.info("Returning for URI: "+request.getRequestURI());
		return new ResponseEntity<String>("good",HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value="/liveliness",method=RequestMethod.GET)
	public ResponseEntity<String> Liveliness(HttpServletRequest request){
		
		if(livetime > 0 && (livetime < (System.currentTimeMillis() - utils.getStartTime()))) {
			LOGGER.info("Returning for URI: "+request.getRequestURI());
			return new ResponseEntity<String>("kill pod",HttpStatus.EXPECTATION_FAILED);
		}
		
		LOGGER.info("Returning for URI: "+request.getRequestURI());
		return new ResponseEntity<String>("good",HttpStatus.OK);
		
	}
	
}
