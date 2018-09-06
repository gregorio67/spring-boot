/**
 * @Project :  스마트톨링정보시스템 구축
 * @Class : ContextClosedHandler.java
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
 * 2018. 8. 8.        LGCNS             최초작성
 *-------------------------------------------------------------
 */

package dymn.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class ContextClosedHandler implements ApplicationListener<ContextClosedEvent> {

	private  static final Logger LOGGER = LoggerFactory.getLogger(ContextClosedHandler.class);
	
	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;
	
	
	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		if (taskScheduler != null) {
			taskScheduler.destroy();
			taskScheduler.shutdown();			
			LOGGER.info("ThreadPoolTaskScheduler is shutdowned");
		}
	}

}
