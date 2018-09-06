/**
 * @Project :  스마트톨링정보시스템 구축
 * @Class : RequestQueue.java
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

package dymn.demo.interceptor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import dymn.demo.vo.RequestVo;

@Component
public class RequestQueue {

	private static BlockingQueue<RequestVo> requestQueue = new ArrayBlockingQueue<RequestVo>(10);

	
	private static int MAX_REQUEST  = 10;
	private static int current_REQ = 0;
	
	
	public void addQueue(HttpServletRequest request, HttpServletResponse response, Object handler) {
		try {
			requestQueue.put(new RequestVo(request, response, handler));			
		}
		catch(Exception ex) {
			
		}
	}
}
