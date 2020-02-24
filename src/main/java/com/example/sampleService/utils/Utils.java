package com.example.sampleService.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.stereotype.Component;

@Component
public class Utils {

	@Value("${podBase_filepath}")
	private String podBasePath;
	
	public static Long startTime;

	private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());
	
	private String convert(InputStream inputStream) throws IOException {
		 
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()))) {	
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}
		}
	 
		return stringBuilder.toString();
	}

	public String getFile(String uri) throws IOException{
		String returnStr = null;
		
		
		try {
			returnStr = convert(this.getClass().getResourceAsStream("/json"+uri+".json"));
			return returnStr;
		} catch (Throwable e) {
			LOGGER.severe("Error pulling file from deployed service. " + e.getMessage());
		}
		
		
			
			Path path = Paths.get(podBasePath+uri+".json");
			 
		    returnStr = readLineByLine(path.toString());
		
		    if(StringUtils.isBlank(returnStr)) {
		    	returnStr = convert(this.getClass().getResourceAsStream("/404.json"));
		    	throw new FileNotFoundException(returnStr);
		    }
		    
		    return returnStr;
		
		
		
	}
	
	@Cacheable("sampleCache")
	public String putCacheEntry(String path) {
		
		try {
		    Thread.sleep(1500);
		} catch (InterruptedException ie) {
		    Thread.currentThread().interrupt();
		}
		LOGGER.info("Response Not in Cache");
		
		return path;
		
		
	}
	
	private static String readLineByLine(String filePath) 
	{
	    StringBuilder contentBuilder = new StringBuilder();
	    try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8)) 
	    {
	        stream.forEach(s -> contentBuilder.append(s).append("\n"));
	    }
	    catch (IOException e) 
	    {
	        LOGGER.severe("Error during file read: " + e.getMessage());
	    }
	    return contentBuilder.toString();
	}
	
	public Long getStartTime() {
		if(startTime == null) {
			startTime = System.currentTimeMillis();
			LOGGER.info("Start Time: NOW");
		}
		return startTime;
	}
}
