/**
 * @Project :  스마트톨링정보시스템 구축
 * @Class : AppContextRegister.java
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

package dymn.demo.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import dymn.demo.util.BeanUtil;

@Component
public class AppContextRegister implements ApplicationContextAware {

	/** To use BeanUtil as static, set applicationContext to BeanUtil **/
	@Override
	public void setApplicationContext(ApplicationContext applicationcontext) throws BeansException {
		BeanUtil.setAppContext(applicationcontext);
		
	}

}
