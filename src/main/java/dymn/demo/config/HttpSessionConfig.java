/**
 * @Project :  스마트톨링정보시스템 구축
 * @Class : HttpSessionConfig.java
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

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpSessionConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpSessionConfig.class);

	private static int totalSession = 0;

	@Bean
	public HttpSessionListener httpSessionListener() {
		return new HttpSessionListener() {

			/** This method will be automatically called when session is created **/
			@Override
			public void sessionCreated(HttpSessionEvent httpsessionevent) {
				HttpSession session = httpsessionevent.getSession();

				session.setAttribute("createdDate", new Date());
				totalSession++;
				LOGGER.info("Sessoin is created, the total session is {}", totalSession);
			}
		
			/** This method will be automatically called when session is removed **/
			@Override
			public void sessionDestroyed(HttpSessionEvent httpsessionevent) {
				HttpSession session = httpsessionevent.getSession();
				LOGGER.info("Sessoin is destoryed which is created at {}", session.getAttribute("createdDate"));
				totalSession--;
				LOGGER.info("The total session is {}", totalSession);
			}
		};
	}

	@Bean // bean for http session attribute listener
	public HttpSessionAttributeListener httpSessionAttributeListener() {
		return new HttpSessionAttributeListener() {
			/** This method will be automatically called when session attribute added **/
			@Override
			public void attributeAdded(HttpSessionBindingEvent se) {
				LOGGER.info("Attribute Added following information");
				LOGGER.info("Attribute Name:" + se.getName());
				LOGGER.info("Attribute Value:" + se.getName());
			}

			/** This method will be automatically called when session attribute removed **/
			@Override
			public void attributeRemoved(HttpSessionBindingEvent se) { 
				LOGGER.info("attributeRemoved");
			}

			/** This method will be automatically called when session attribute replaced **/
			@Override
			public void attributeReplaced(HttpSessionBindingEvent se) { 
				LOGGER.info("Attribute Replaced following information");
				LOGGER.info("Attribute Name:" + se.getName());
				LOGGER.info("Attribute Old Value:" + se.getValue());
			}
		};
	}

}
