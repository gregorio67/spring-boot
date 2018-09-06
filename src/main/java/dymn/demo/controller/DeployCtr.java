/**
 * @Project :  스마트톨링정보시스템 구축
 * @Class : DeployCtr.java
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
 * 2018. 7. 6.        LGCNS             최초작성
 *-------------------------------------------------------------
 */

package dymn.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dymn.demo.base.BaseController;
import dymn.demo.base.CamelMap;
import dymn.demo.exception.BizException;
import dymn.demo.service.DeploySvc;
import dymn.demo.service.TcpClientSvc;
import dymn.demo.util.MessageUtil;
import dymn.demo.util.PropertiesUtil;
import dymn.demo.util.SessionUtil;

@Controller
public class DeployCtr extends BaseController {
	
	@Autowired
	private MessageSource messageSource;
	
	@Resource(name = "deployIService")
	private DeploySvc deployIService;
	
	@Resource(name = "tcpClientSvi")
	private TcpClientSvc tcpClientSvi;
	
//	@Autowired
//	@Qualifier("system")
//	private PropertiesFactoryBean system;


	@RequestMapping(value = "/deploy/mainpage.do")
	public String mainPageView() throws Exception {
		SessionUtil.setSession("curPage", "main");
		return "main";
	}

	
	@RequestMapping(value = "deploy/searchCodeList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> searchCodeList() throws Exception {
		Map<String, Object> codeList =  deployIService.selCodeList();
	
		String msg = MessageUtil.getMessage("sys.err.frame.001", new String[] {this.getClass().getName()}, Locale.getDefault());
		
//		LOGGER.debug("Message Test :: {}::{}", msg,system.getObject().get("jndi.datasource.name"));
		if (LOGGER.isDebugEnabled()) {			
			LOGGER.debug("Search Code :: {}", codeList);
		}
		
		return codeList;
	}
	
	@RequestMapping(value = "/deploy/searchDeployList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> searchDeployList(@RequestBody Map<String, Object> param) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> pageMap = new HashMap<String, Object>();
		
		int totalCount = deployIService.selDeployTargetListCnt(param);
		
		int currentPage = 0;
		if (param.get("currentPage") != null) {
			currentPage = Integer.parseInt(String.valueOf(param.get("currentPage")));
		}
		else {
			currentPage = 1;
		}
		
		pageMap = getPageInfo(currentPage, totalCount);
		/** Get Batch Job List **/
		int pageRowCount = PropertiesUtil.getInt("screen.page.rowcount");
		if (pageRowCount == 0) {
			throw new BizException("Page row count is null, check your properties");
		}
		param.put("pageRowCount", pageRowCount);
		param.put("curRowCount", (currentPage  - 1) * pageRowCount);
		
		List<Map<String, Object>> deployTarget = deployIService.selDeployTargetList(param);
		
		resultMap.put("page", pageMap);
		resultMap.put("deployTarget", deployTarget);
		
		return resultMap;		
	}

	
	@RequestMapping(value = "/deploy/deployTarget", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> deployTarget(@RequestBody Map<String, Object> param) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CamelMap deployItem = deployIService.selDeployTargetItem(param);
		if (deployItem == null) {
			throw new BizException("Can't find information to deploy");
		}
		
		String host = deployItem.get("ipAddr") != null ? String.valueOf(deployItem.get("ipAddr")) : "";
		int port = deployItem.get("listenPort") != null ? Integer.parseInt(String.valueOf(deployItem.get("listenPort"))) : 0;
		
		Map<String, Object> shellParam = new HashMap<String, Object>();
		
		shellParam.put("service", "shellExecService");
		shellParam.put("responseYn", "Y");
		Map<String, Object> commandMap = new HashMap<String, Object>(); 
		commandMap.put("shellCommand", deployItem.get("command"));
		commandMap.put("shellName", deployItem.get("shellName"));
		commandMap.put("shellParams", deployItem.get("shellParams"));
		
		shellParam.put("command", commandMap);
		
		
		Map<String, Object> result = tcpClientSvi.sendMessage(host, port, shellParam, true);
		return result;		
	}

}
