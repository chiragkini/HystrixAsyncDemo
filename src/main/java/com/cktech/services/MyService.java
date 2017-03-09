package com.cktech.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

@Service
public class MyService {

	@Autowired
	ExternalServiceOne externalServiceOne;

	@Autowired
	ExternalServiceTwo externalServiceTwo;

	public List<String> getStatesList() throws InterruptedException, ExecutionException {

		List<String> states=new ArrayList<String>();
		 Observable.merge(externalServiceOne.observe().subscribeOn(Schedulers.io()),
				externalServiceTwo.observe().subscribeOn(Schedulers.io())).toBlocking().subscribe(new Action1<List<String>>() {
					
					@Override
					public void call(List<String> arg0) {
						 states.addAll(arg0);
						
					}
				});
		 return states;
		
	}
}
