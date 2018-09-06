/**
 * @Project :  스마트톨링정보시스템 구축
 * @Class : AsyncTask.java
 * @Description : 
 *
 * @Author : LGCNS
 * @Since : 2017. 4. 20.
 *
 * @Copyright (c) 2018 EX All rights reserved.
 *-------------------------------------------------------------
 *              Modification Information
 *-------------------------------------------------------------
 * 날짜            수정자             변경사유 
 *-------------------------------------------------------------
 * 2018. 8. 13.        LGCNS             최초작성
 *-------------------------------------------------------------
 */

package dymn.demo.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class AsyncTask {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncTask.class);
	
	@Async
	public void scheduleTaskWithFixedDelay() {
		
		LOGGER.debug("async Task is executed at {} ", new Date());
	    try {
	        TimeUnit.SECONDS.sleep(5);
	    } catch (InterruptedException ex) {
	        LOGGER.error("Ran into an error {}", ex);
	        throw new IllegalStateException(ex);
	    }
	}

}
