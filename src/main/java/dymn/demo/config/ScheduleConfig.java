/**
 * @Project :  스마트톨링정보시스템 구축
 * @Class : ScheduleConfig.java
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

package dymn.demo.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer, DisposableBean {

	
	private final int POOL_SIZE = 10;

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegister) {
		ThreadPoolTaskScheduler taskScheduler = threadPoolTaskScheduler();
		
		taskRegister.setTaskScheduler(taskScheduler);
	}
	
	
//	@Bean 
//	public TaskScheduler taskScheduler() { 
//		return new ConcurrentTaskScheduler(); 
//	} 
//	
	@Bean 
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() { 
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler(); 
		taskScheduler.setThreadNamePrefix("schduler-task-pool-");
		taskScheduler.initialize();
		taskScheduler.setPoolSize(POOL_SIZE);
		return taskScheduler; 
	}


	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
