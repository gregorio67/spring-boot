package dymn.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import dymn.demo.base.BaseService;
import dymn.demo.base.CamelMap;

@Service("deployIService")
public class DeploySvc extends BaseService{

	public Map<String, Object> selCodeList() throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> systemNameList = baseDao.selectList("deploy.target.selSystemNameList");
		
		List<Map<String, Object>> subSystemList = baseDao.selectList("deploy.target.selSubSystemList");
		
		resultMap.put("systemNameList", systemNameList);
		resultMap.put("subSystemList", subSystemList);
		return resultMap;
	}

	public List<Map<String, Object>> selDeployTargetList(Map<String, Object> param) throws Exception {
		
		return baseDao.selectList("deploy.target.selDeployTargetList", param);
	}

	public int selDeployTargetListCnt(Map<String, Object> param) throws Exception {
		
		return baseDao.select("deploy.target.selDeployTargetCnt", param);
	}

	public CamelMap selDeployTargetItem(Map<String, Object> param) throws Exception {
		
		return baseDao.select("deploy.target.selDeployTargetItem", param);
	}

}
