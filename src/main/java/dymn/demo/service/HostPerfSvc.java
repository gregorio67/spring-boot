package dymn.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import dymn.demo.base.BaseService;

@Service("hostPerfSvi")
public class HostPerfSvc extends BaseService {

	@SuppressWarnings("unchecked")
	public Map<String, Object> savePerfData(Map<String, Object> param) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> memInfo = (Map<String, Object>) param.get("memInfo");
		Map<String, Object> cpuInfo = (Map<String, Object>) param.get("cpuInfo");
		
		LOGGER.info("CPU info :: {}", cpuInfo);
		LOGGER.info("Memory Info : {}", memInfo);
		
		int cnt = baseDao.insert("host.perf.insCpuInfo", cpuInfo);
		if (cnt <= 0) {
			resultMap.put("status", "F");
		}
		cnt = baseDao.insert("host.perf.insMemInfo", memInfo);
		if (cnt <= 0) {
			if (resultMap.get("status") != null) {
				resultMap.put("message", "CPU, and Memory information insert failed.");
			}
		}
		resultMap.put("status", "S");
		resultMap.put("message", "Successfully ended.");
		return resultMap;
	}

	public int selectCpuListCnt(Map<String, Object> param) throws Exception {
		
		return baseDao.select("host.perf.selCpuInfoListCnt", param);
	}

	public List<Map<String, Object>> selectCpuList(Map<String, Object> param) throws Exception {
		return baseDao.selectList("host.perf.selCpuInfoList", param);
	}

	public int selectMemListCnt(Map<String, Object> param) throws Exception {
		return baseDao.select("host.perf.selMemInfoListCnt", param);
	}

	public List<Map<String, Object>> selectMemList(Map<String, Object> param) throws Exception {
		return baseDao.selectList("host.perf.selMemInfoList", param);
	}

	public Map<String, Object> selectCpuChartList(Map<String, Object> param) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> hostList = baseDao.selectList("host.perf.selCpuHostList", param);
		resultMap.put("hostCnt", hostList.size());

		int idx = 0;
		for (String host : hostList) {
			param.remove("hostName");
			param.put("hostName", host);
			List<Map<String, Object>> cpuList = baseDao.selectList("host.perf.selCpuInfoChartList", param);
			String name = "cpuInfo_" + idx;
			resultMap.put(name, cpuList);
			idx++;
		}
		
		return resultMap;
	}

	public Map<String, Object> selectMemChartList(Map<String, Object> param) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> hostList = baseDao.selectList("host.perf.selMemHostList", param);
		int idx = 0;
		resultMap.put("hostCnt", hostList.size());
		for (String host : hostList) {
			param.remove("hostName");
			param.put("hostName", host);
			List<Map<String, Object>> memList = baseDao.selectList("host.perf.selMemInfoChartList", param);
			String name = "memInfo_" + idx;
			resultMap.put(name, memList);
			idx++;
		}
		
		return resultMap;
	}
}
