package com.cktech.web;

 
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cktech.services.ExternalServiceOne;
import com.netflix.config.DynamicLongProperty;
import com.netflix.config.DynamicPropertyFactory;

@RestController
public class ExternalServiceOneController {
	
	private static Logger LOG = LoggerFactory.getLogger(ExternalServiceOne.class);

    private static DynamicLongProperty timeToWait = DynamicPropertyFactory
            .getInstance().getLongProperty("hystrixdemo.sleep", 2000);


    public static synchronized List<String> getData(int id)
            throws InterruptedException {
         if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException();
        }
         long t = timeToWait.get();
        LOG.info("waiting {} ms", t);
         if (t > 0) {
             Thread.sleep(t);
       }
        
        List<String> list=new ArrayList<String>();
        list.add("Mumbai");
        list.add("Delhi");
        list.add("Chennai");
        list.add("Uttar Pradesh");
        list.add("Bangalore");
        return list;
    }
	
	@RequestMapping(path = "serviceOne/states", method = RequestMethod.GET)	
    public List<String> getExternalOneList(){
		try {
			
			return getData(1);
		} catch (InterruptedException e) {
 			e.printStackTrace();
		}
		return null;
		
	}
	
}
