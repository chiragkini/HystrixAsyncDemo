package com.cktech.web;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cktech.services.MyService;
import com.netflix.hystrix.HystrixCommandProperties;

@RestController
public class AppController {

	@Autowired
	MyService myService;
	
	@RequestMapping(path="getStatesList", method=RequestMethod.GET)
	public List<String> getStatesList() throws InterruptedException, ExecutionException{
		
		long startTime = System.currentTimeMillis();
	 	System.out.println("Time out configured as :"+ HystrixCommandProperties.Setter().getExecutionTimeoutInMilliseconds());
		List<String> states=myService.getStatesList();
 	  
		 long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("Time Elasped:"+ totalTime/1000);
		 System.out.println("Here i come..");
		 return states;
	}
}
