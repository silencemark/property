package com.lr.backer.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileDeal {
	public static int String_length(String value) {
		int valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		for (int i = 0; i < value.length(); i++) {
			String temp = value.substring(i, i + 1);
			if (temp.matches(chinese)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	}

	public static String splitAndFilterString(String input) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		// 去掉所有html元素,
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
				"<[^>]*>", "");
		str = str.replaceAll("[(>)<]", "");
		return str.replace("$", "<br/>").replace("<br/><br/>", "<br/>");
	}

	public static String filterDecode(String url) {
		url = url.replaceAll("&amp;", "&").replaceAll("&lt;", "<").replaceAll(
				"&gt;", ">").replaceAll("&apos;", "\'").replaceAll("&quot;",
				"\"").replaceAll("&nbsp;", " ").replaceAll("&copy;", "@")
				.replaceAll("&reg;", "?").replace("", "");
		return url;
	}

	public static String filterFCK(String url) {
		url = url.replaceAll("&nbsp;", " ").replaceAll("&amp;", "&")
				.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll(
						"&rsquo;", "‘").replaceAll("&quot;", "“").replaceAll(
						"&lsquo;", "‘").replaceAll("&rsquo;", "’").replaceAll(
						"&ldquo;", "“").replaceAll("&rdquo;", "”").replaceAll(
						"&#39;", "\\");
		return url;

	}

	public static String filterHtml(String str, String old, String newstr) {
		Pattern pattern = Pattern.compile(old);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, newstr);
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static String procHtml4Txt(String s) {
		String regxpForHtmlp = "<[Pp]([^>]*)>"; // 过滤所有以<P开头以>结尾的标签
		String regxpForHtmlbr = "<[bB][Rr]([^>]*)>"; // 过滤所有以<br开头以>结尾的标签

		String regxpForHtmleP = "</[Pp]>"; // 过滤所有以<br开头以>结尾的标签
		String regxpForHtmlebr = "</[bB][Rr]>"; // 过滤所有以<br开头以>结尾的标签

		String regxpForHtmlImg = "<[Ii][Mm][Gg][ ][Ss][Rr][Cc]="; // 所有<img
		// src= 开头的
		String regxpForHtmlImgE = "\">"; // ">结尾的
		String zzmImg = "Img|Img_zzm";
		String zzmImgE = "END|Img|zzm";

		s = s.replace("\r\n", "<br />");
		s = s.replace("\r", "<br />");
		s = s.replace("\n", "<br />");

		String regxpForHtmlall = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签
		String zzmP = "P|P_zzm";
		String zzmbr = "br|br_zzm";

		s = filterHtml(s, regxpForHtmlp, zzmP); // 替换p
		s = filterHtml(s, regxpForHtmlbr, zzmbr); // 替换br

		s = filterHtml(s, regxpForHtmleP, "END|P|zzm"); // P结尾
		s = filterHtml(s, regxpForHtmlebr, "END|BR|zzm"); // br结尾

		s = filterHtml(s, regxpForHtmlImg, zzmImg); // 替换img
		s = filterHtml(s, regxpForHtmlImgE, zzmImgE); // 替换img结尾

		s = filterHtml(s, regxpForHtmlall, ""); // 去掉所有HTML标签

		// 恢复回来
		s = s.replace("END|P|zzm", "<br />");
		s = s.replace("END|BR|zzm", "<br />");
		s = s.replace(zzmP, "<br />");
		s = s.replace(zzmbr, "<br />");

		s = s.replace(zzmImg, "<img src=");
		s = s.replace(zzmImgE, "\">");
		// 过滤重复br和空白
		s = s.replaceAll("(   )+", "　　");
		s = s.replaceAll("(　　)+", "　　");
		s = s.replaceAll("(<br />([\\s|　]*<br />)*)+", "<br /><br />");
		// s =s.replaceAll("<br />", "<br /><br />");
		if (s.indexOf("<br /><br />") == 0) {
			s = s.substring(12);
		}
		s = splitAndFilterString(s);
		s = filterFCK(s);
		s = filterDecode(s);

		return s;
	}

	public static int StringTNUM(String value, int tnum) {
		int num = 0;
		int valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";

		for (int i = 0; i < value.length(); i++) {
			if (valueLength >= tnum) {
				break;
			}
			String temp = value.substring(i, i + 1);
			if (temp.matches(chinese)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
			num++;
		}

		return num;
	}

	public static void mapArrayToStr(Map<String, Object> dataMap) {
		if (null != dataMap) {
			for (String st : dataMap.keySet()) {

				if (dataMap.get(st) instanceof String[]) {
					String[] array = (String[]) dataMap.get(st);
					if ("null".equals(array[0])) {
						dataMap.put(st, null);
					} else {
						dataMap.put(st, array[0]);
					}

				} else if (dataMap.get(st) instanceof String) {
					dataMap.put(st, dataMap.get(st));
				}
				if (dataMap.get(st) != null
						&& dataMap.get(st) instanceof String) {
					String value = String.valueOf(dataMap.get(st)).trim();
					dataMap.put(st, value);
				}
			}
		} else {
			dataMap = new HashMap<String, Object>();
		}
	}

	public static void parseSimpleForm(Map<String, Object> sourceMap) {
		// 转换Strust2Map表单
		if (null != sourceMap) {
			Iterator<Map.Entry<String, Object>> iterator = sourceMap.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = iterator.next();
				if (entry.getValue() instanceof String[]) {
					String[] strArray = (String[]) entry.getValue();
					if (strArray.length >= 1) {
						sourceMap.put(entry.getKey(), strArray[0]);
					} else {
						sourceMap.put(entry.getKey(), entry.getValue());
					}
				}
			}
		}
	}

	public static List<Map<String, Object>> parseFormList(
			Map<String, Object> sourceMap) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		// 转换Strust2Map表单
		if (null != sourceMap) {
			Iterator<Map.Entry<String, Object>> iterator = sourceMap.entrySet()
					.iterator();
			while (iterator.hasNext()) {

				Map.Entry<String, Object> entry = iterator.next();
				if (entry.getValue() instanceof String[]) {
					String[] strArray = (String[]) entry.getValue();
					for (int i = 0, len = strArray.length; i < len; i++) {
						int dataLen = null != dataList ? dataList.size() : 0;
						Map<String, Object> dataMap = null;
						boolean flag = dataLen > i;
						if (flag) {
							dataMap = dataList.get(i);
						} else {
							// 集合中还没有存储相应的数据
							dataMap = new HashMap<String, Object>();
						}
						dataMap.put(entry.getKey(), strArray[i]);
						if (!flag) {
							dataList.add(i, dataMap);
						}
					}
				}
			}
		}
		return dataList;
	}

	public static Map<String, Object> copyNew(Map<String, Object> sourceMap) {
		Map<String, Object> targetMap = new HashMap<String, Object>();
		if (null != sourceMap) {
			Iterator<Map.Entry<String, Object>> iterator = sourceMap.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = iterator.next();
				targetMap.put(entry.getKey(), entry.getValue());
			}
		}
		return targetMap;
	}

	public static void isMapValue(Map<String, Object> dataMap) {
		if (null != dataMap) {
			for (String st : dataMap.keySet()) {
				System.out.println(dataMap.get(st) + ":::" + st);
				if (dataMap.get(st) == null)
					dataMap.put(st, "");
			}
		}
	}

	public static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}

	private  static String getMapValue(Map<String, Object> map,String key){
		if(null!=map && !map.isEmpty() && map.containsKey(key)){
			Object object = map.get(key) ;
			return null!=object && !"".equals(object) ? object.toString():"" ; 
		}
		return "" ;
	}
	
	
	/**
	 * @function: 将集合中子集合并到父集中（key=“childRen”）<br/>
	 * getChildRen(list,parentid,"id","parentid");
	 * @datetime:2014-11-5 下午01:12:48
	 * @Author: robin
	 * @param: @param list 需要处理的集合
	 * @param: @param parentid 隶属的上级id 
	 * @param: @param mainKey 主键 的 key = “id” 
	 * @param: @param parentKey 引用上级的key = “parentid ”
	 * @param: @return
	 * @return List<LinkedHashMap<String,Object>>
	 */
	public static List<LinkedHashMap<String, Object>> getChildRen(List<LinkedHashMap<String, Object>> list,String parentid,String mainKey,String parentKey){
		List<LinkedHashMap<String, Object>> tempList = new ArrayList<LinkedHashMap<String,Object>>();
		if(null!=list && !list.isEmpty()){
			for (LinkedHashMap<String, Object> map : list) {
				if(null!=map && !map.isEmpty()){
					String tparentid = getMapValue(map, parentKey);
					if(null!=parentid && tparentid.equals(parentid)){
						//表示是顶级节点
						List<LinkedHashMap<String, Object>> dataList = getChildRen(list, getMapValue(map, mainKey),mainKey,parentKey) ;
						if(null!=dataList){
							map.put("childRen", dataList);
							tempList.add(map);
						}
					}
				}
			}
		}
		return tempList;
	}

	
	

	public static String formatTimeStamp(long timestamp){
		Date date = new Date(timestamp*1000l);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	/**
	 * @function:获取集合的第一项
	 * @datetime:2015-1-23 下午08:15:26
	 * @Author: robin
	 * @param: @param dataList
	 * @return Map<String,Object>
	 */
	public static Map<String, Object> getListFirst(List<Map<String, Object>> dataList){
		if(null != dataList && !dataList.isEmpty() && dataList.size()>=1){
			return dataList.get(0) ;
		}
		return null ;
	}
	
	public static void main(String[] args) {
		  System.out.print(FileDeal.String_length("瑶瑶🚀🚀🚀"));
	}
	
	/**
	 * 截取指定长度的字符串
	 * 
	 * @datetime:2015-4-14 下午07:38:36
	 * @Author: robin
	 * @param: @param value 要截取的字符串
	 * @param: @param index 要截取的长度（英文字符的长度）
	 * @return String
	 */
	public static String subStringLengthMore(String value, int index) {
		if (null != value) {
			// 获取包含字符串的长度
			// 中文转成2个长度
			int num = String_length(value);
			// 字符串的长度大于要截取的长度，进行截取
			if (num > index) {
				return value.substring(0, StringTNUM(value, index)) + "...";
			}
		}
		return value;
	}
	
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
}
