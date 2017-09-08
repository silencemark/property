package com.lr.backer.util;

import java.util.Map;

public class MapUtils {

	public static String getString(Map<String, Object> map, String key) {
		if (null == map || map.isEmpty()) {
			return "";
		}
		if (null == key || "".equals(key)) {
			return "";
		}
		return null != map.get(key) ? map.get(key).toString() : "";
	}

}
