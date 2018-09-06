/**
 * @Project :  스마트톨링정보시스템 구축
 * @Class : RequestVo.java
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
 * 2018. 8. 31.        LGCNS             최초작성
 *-------------------------------------------------------------
 */

package dymn.demo.vo;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5029288010729255796L;

	private HttpServletRequest request;
	private HttpServletResponse response;
	private Object handler;
	
	public RequestVo(HttpServletRequest request,  HttpServletResponse response, Object handler) {
		this.request = request;
		this.response = response;
		this.handler = handler;
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public HttpServletResponse getResponse() {
		return response;
	}
	
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public Object getHandler() {
		return handler;
	}
	public void setHandler(Object handler) {
		this.handler = handler;
	}
	
	
}
