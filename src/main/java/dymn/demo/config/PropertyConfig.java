/**
 * @Project :  스마트톨링정보시스템 구축
 * @Class : BeanConfiguration.java
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
 * 2018. 8. 6.        LGCNS             최초작성
 *-------------------------------------------------------------
 */

package dymn.demo.config;

import java.io.IOException;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class PropertyConfig {

	@Bean(name="app")
	public static PropertiesFactoryBean app() throws IOException {
		PropertiesFactoryBean pspc = new PropertiesFactoryBean();
		Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:/properties/app.properties");
		pspc.setLocations(resources);
		return pspc;
	}
	
	@Bean(name="system")
	public static PropertiesFactoryBean system() throws IOException {
		PropertiesFactoryBean pspc = new PropertiesFactoryBean();
		Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:/properties/system.properties");
		pspc.setLocations(resources);
		return pspc;
	}

}
