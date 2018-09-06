/**
 * @Project :  스마트톨링정보시스템 구축
 * @Class : SchdulerSvc.java
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
 * 2018. 8. 7.        LGCNS             최초작성
 *-------------------------------------------------------------
 */

package dymn.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchdulerSvc{

	private static final Logger LOGGER = LoggerFactory.getLogger(SchdulerSvc.class);

	@Autowired
	private AsyncTask asyncTask;
	
//	@Scheduled(cron = "0/30 * * * * ?")
//	public void sampleTask() throws Exception {
//		LOGGER.info("SchdulerSvc is started");
//	}
//	
//	@Scheduled(fixedDelay = 2000)
//	public void scheduleTaskWithFixedDelay() {
//		asyncTask.scheduleTaskWithFixedDelay();
//	}

}
