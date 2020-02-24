package com.example.sampleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import com.example.sampleService.utils.Utils;

@SpringBootApplication
@EnableCaching
public class SampleServiceApplication {

	@Autowired
	Utils utils;
	
	public static void main(String[] args) {
		SpringApplication.run(SampleServiceApplication.class, args);
	}
	
	@Bean
	public void fakebean() {
		utils.getStartTime();
	}
	
}
