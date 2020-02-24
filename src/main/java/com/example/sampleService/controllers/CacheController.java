package com.example.sampleService.controllers;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.sampleService.utils.Utils;

@RestController
public class CacheController {

	@Autowired
	Utils utils;

	private static final Logger LOGGER = Logger.getLogger(CacheController.class.getName());
	
	@Autowired
    private CacheManager cacheManager; 
	
	@RequestMapping(value="/cache/clear",method=RequestMethod.GET)
	public ResponseEntity<String> CacheClear(HttpServletRequest request){
		
		for(String name:cacheManager.getCacheNames()){
            cacheManager.getCache(name).clear();            // clear cache by name
        }
		return new ResponseEntity<String>("CACHE Cleared",HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/cache/get/**/",method=RequestMethod.GET)
	public ResponseEntity<String> CachePut(HttpServletRequest request){
		String uri = request.getRequestURI();
		
		String returnStr = utils.putCacheEntry(uri);
		
		LOGGER.info("Returning for URI: "+uri);
		return new ResponseEntity<String>(returnStr,HttpStatus.OK);
		
	}
}
