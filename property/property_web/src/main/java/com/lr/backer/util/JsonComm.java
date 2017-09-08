package com.lr.backer.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsDateJsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

public class JsonComm {
	public static String getJsonString4JavaPOJO(Object obj) {
		JSONObject onejson = JSONObject.fromObject(obj);
		return onejson.toString();
	}

	public static String getJsonString4JavaPOJO(List<Object> objlist) {
		JSONObject onejson = null;
		String json = "[\n";
		for (Iterator localIterator = objlist.iterator(); localIterator
				.hasNext();) {
			Object obj = localIterator.next();
			onejson = JSONObject.fromObject(obj);
			json = json + onejson.toString() + ",\n";
		}
		return json.substring(0, json.lastIndexOf(",")) + "\n]";
	}

	public static String getJsonStringObj(Object obj, String dataft) {
		JsonConfig jsonConfig = configJson(dataft);
		JSONObject json = JSONObject.fromObject(obj, jsonConfig);
		return json.toString();
	}

	public static JsonConfig configJson(String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "" });
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsDateJsonValueProcessor());
		return jsonConfig;
	}

	public static String createJson(Object obj) {
		if ((obj instanceof List)) {
			List para = (List) obj;
			return createJsonList(para);
		} else if (obj instanceof Map) {
			Map para = (Map) obj;
			return createJsonMap(para);
		}

		return createJsonObj(obj);
	}

	public static String createJsonOBJ(Object obj) {
		if (obj instanceof List) {
			List para = (List) obj;
			return JsonComm.createJsonList(para);

		} else {
			return JsonComm.createJsonObj(obj);
		}
	}

	public static String createJsonObj(Object obj) {
		JSONObject json = JSONObject.fromObject(obj);

		String content = "[\n";
		content = content + json.toString();
		content = content + "\n]";
		return content;
	}

	public static String createJsonFromMap(Map<String, Object> datamap) {
		String json = "[";
		for (String key : datamap.keySet()) {
			String value = datamap.get(key) + "";
			json = json + "[" + key + "," + value + "],";
		}
		if (json.lastIndexOf(",") != -1) {
			json = json.substring(0, json.lastIndexOf(",")) + "]";
		} else {
			json = json + "]";
		}
		return json;
	}

	public static String createJsonList(List dataList) {
		JSONObject onejson = null;
		String json = "[\n";
		for (Iterator localIterator = dataList.iterator(); localIterator
				.hasNext();) {
			Object obj = localIterator.next();
			try {
				onejson = JSONObject.fromObject(obj);
				json = json + onejson.toString() + ",\n";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (json.lastIndexOf(",") != -1)
			json = json.substring(0, json.lastIndexOf(",")) + "\n]";
		else {
			json = json + "\n]";
		}
		return json;
	}

	public static String createJsonMap(Map<String, List> dataMap) {
		String json = "";
		List dataList = null;
		json = json + "{\n";
		for (Map.Entry<String, List> entry : dataMap.entrySet()) {
			dataList = entry.getValue();
			JSONObject onejson = null;
			json = json + "\"" + entry.getKey() + "\":[\n";
			int k = 0;
			for (Iterator localIterator = dataList.iterator(); localIterator
					.hasNext();) {
				Object obj = localIterator.next();
				try {
					k++;
					onejson = JSONObject.fromObject(obj);
					json = k == dataList.size() ? json + onejson.toString()
							: json + onejson.toString() + ",\n";
					;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			json = json + "\n],";
		}
		if (json.length() > 0) {
			if (json.substring(json.length() - 1).equals(",")) {
				json = json.substring(0, json.length() - 1);
			}
		}

		json = json + "\n}";
		return json;
	}
	public static String createkjtpJson(Object obj) {
		String result = "";
		try {
			if (obj instanceof List) {
				List para = (List) obj;
				result = JsonComm.createJsonList(para);

			} else {
				result = JsonComm.createJsonObj(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}
		return result;

	}
	
	
    public static String getJsonString(String urlPath) throws Exception {  
        URL url = new URL(urlPath);  
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
        connection.connect();  
        InputStream inputStream = connection.getInputStream();  
        //对应的字符编码转换  
        Reader reader = new InputStreamReader(inputStream, "UTF-8");  
        BufferedReader bufferedReader = new BufferedReader(reader);  
        String str = null;  
        StringBuffer sb = new StringBuffer();  
        while ((str = bufferedReader.readLine()) != null) {  
            sb.append(str);  
        }  
        reader.close();  
        connection.disconnect();  
        return sb.toString();  
    }  
}
