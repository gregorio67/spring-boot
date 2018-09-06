/**
 * @Project :  스마트톨링정보시스템 구축
 * @Class : WebConfig.java
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import dymn.demo.filter.ABCFilter;
import dymn.demo.interceptor.SessionInterceptor;
import dymn.demo.util.PropertiesUtil;

@Configuration
// @EnableWebMvc
// public class WebConfig extends WebMvcConfigurerAdapter {
// public class WebConfig extends WebMvcConfigurationSupport {
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private SessionInterceptor sessionInterceptor;

	@Autowired
	private ABCFilter abcFilter;

	/** 
	 * Add Index Page
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/index.jsp");
	}

	/** Register intercepter **/
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> skipUris = new ArrayList<String>();

		String[] uris = PropertiesUtil.getString("session.skip.uri").split(",");
		;
		for (String uri : uris) {
			skipUris.add(uri);
		}
		sessionInterceptor.setSkipUris(skipUris);
		registry.addInterceptor(sessionInterceptor);
		registry.addInterceptor(localeChangeInterceptor());
		// .addPathPatterns("/*");
		// .excludePathPatterns(skipUris);
	}

	/** Register Filter **/
	/** If you want to register other filer, Add Bean for other fileter **/
	@Bean
	public FilterRegistrationBean<ABCFilter> getFilterRegistrationBean() {
		FilterRegistrationBean<ABCFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(abcFilter);
		registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE - 1);
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}

	@Bean
	public MappingJackson2JsonView jsonView() {
		return new MappingJackson2JsonView();
	}

	// @Bean
	// public JsonExceptionResolver jsonExceptionResolver() {
	// return new JsonExceptionResolver();
	// }

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:message/message");
		messageSource.setCacheSeconds(10);
		messageSource.setDefaultEncoding("UTF-8");

		return messageSource;
	}

	@Bean(name = "localeResolver")
	public LocaleResolver sessionlocaleResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("en_US"));
		return localeResolver;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("locale");

		return localeChangeInterceptor;
	}
}
