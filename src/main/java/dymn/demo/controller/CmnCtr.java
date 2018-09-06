/**
 * @Project :  스마트톨링정보시스템 구축
 * @Class : CmnCtr.java
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

package dymn.demo.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;

import dymn.demo.base.BaseController;

@Controller
public class CmnCtr extends BaseController {

	@RequestMapping(value = "/cmn/localeChange.do")
	public String localChange(HttpServletRequest request, @RequestParam("locale") String locale) throws Exception {
		Locale tempLocale = RequestContextUtils.getLocale(request);
		String referer = request.getHeader("referer");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Current Locale :: {}:{}", tempLocale, referer);
		}
		HttpSession session = request.getSession();
		String curPage = session.getAttribute("curPage") != null ? String.valueOf(session.getAttribute("curPage")) : null;
		/** If current page is null, return main page **/
		/** The current page is set when page is called **/
		return curPage != null ? curPage : "main";
	}

}
