package dymn.demo.base;

import org.apache.commons.collections.map.LinkedMap;

import dymn.demo.util.CamelUtil;

public class CamelMap extends LinkedMap {

	private static final long serialVersionUID = -8644338222761233955L;
	
	public Object put(Object key, Object value) {
		return super.put(CamelUtil.convert2CamelCase((String)key), value);
	}
}
