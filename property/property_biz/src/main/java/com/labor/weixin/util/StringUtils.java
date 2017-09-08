package com.labor.weixin.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class StringUtils {
	

	public static List stockList = new ArrayList(); // 存储股票数据
	private static String DOUHAO = ",";
	private static String MULTI_CHRA_SPLIT = "[m]";

	private static Map<String, String> stock_Map = null;


	/**
	 * ȥ���ַ��е�һЩ��Ƿ�ţ���HTML��׼ת������ " &quot; | < &lt; | > &gt; | �C &ndash;
	 * | add by zzm 20120227
	 * 
	 * @param str
	 * @return
	 */
	public static String tranHtmlSign(String str) {
		String strTmp = str;
		strTmp = strTmp.replace("\"", "&quot;");
		strTmp = strTmp.replace("<", "&lt;");
		strTmp = strTmp.replace(">", "&gt;");
		strTmp = strTmp.replace("�C", "&ndash;");
		return strTmp;
	}

	/**
	 * �滻�ַ��� add by zzm 20120113
	 * 
	 * @param str
	 * @return String
	 */
	private static String filterHtml(String str, String old, String newstr) {
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

	/**
	 * ȥ���ı��� �� < Ϊ��ͷ�� >Ϊ��β�ı�ǩ add by zzm 20120409
	 * 
	 * @param strTxt
	 * @return
	 */
	public static String getZY(String strTxt) {
		String regxpForHtmlall = "<([^>]*)>"; // ����������<��ͷ��>��β�ı�ǩ
		String zy = "";
		zy = strTxt;
		zy = filterHtml(zy, regxpForHtmlall, ""); // ȥ������HTML��ǩ

		return zy;
	}

	/**
	 * @author frank
	 * @date 2012-04-12
	 * @doc ��ʽ���ַ���ʾ
	 */
	public static String strFormatShow(String formatstr) {
		String opstrFormat = null;
		if (formatstr != null && !"".equals(formatstr.trim())) {
			if (formatstr.contains("\r")) {
				formatstr = formatstr.replace("\r", "<br/>   ");
			} else if (formatstr.contains("\n")) {
				formatstr = formatstr.replace("\n", "<br/>   ");
			} else if (formatstr.contains("\r\n")) {
				formatstr = formatstr.replace("\r\n", "<br/>   ");
			} else if (formatstr.contains("\n\r")) {
				formatstr = formatstr.replace("\n\r", "<br/>   ");
			} else {
				formatstr = formatstr;
			}
			opstrFormat = formatstr;
		}
		return opstrFormat;
	}

	public static String MD5(String s) {
		try {
			byte[] btInput = s.getBytes("utf-8");
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < md.length; i++) {
				int val = ((int) md[i]) & 0xff;
				if (val < 16)
					sb.append("0");
				sb.append(Integer.toHexString(val));
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @author 韦守勤
	 * @doc 获取股票代码 以及股票名称对应 关系 allStockInfo.json
	 */
	public static Map<String, String> AllStockInfoMap() {
		Map<String, String> stockMap = null;
		String url_path = "http://mnews.hk.com.cn/wap/data/ipad/stock/allStockOfAbHkCode.json";
		URL url;
		try {
			url = new URL(url_path);
			URLConnection uc = url.openConnection();
			InputStream inputstream = uc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputstream, "utf-8"));
			String repStr = null;
			if ((repStr = reader.readLine()) != null) {
				stockMap = new HashMap<String, String>();
				JSONObject jObj = JSONObject.fromObject(repStr);
				JSONArray jary = jObj.getJSONArray("data");
				JSONObject obj = null;
				for (int i = 0; i < jary.size(); i++) {
					obj = jary.getJSONObject(i);
					stockMap.put(obj.getString("dm"), obj.getString("mc"));
				}
			}
		} catch (Exception e) {
			return null;
		}
		return stockMap;
	}

	/**
	 * @author 韦守勤
	 * @version 1.0 2013-03-08 添加
	 * @doc 处理股票代码和 股票名称对应关系
	 * @return Map<Strirng,String> stockMap 出现异常返回null
	 * @param i
	 *            int 1:获取AB股 2：获取港股 3：获取基金 4：获取板块 0：获取全部
	 */
	public static Map<String, String> StockInfoMap(int i) {
		Map<String, String> rsMap = new HashMap<String, String>();
		if (stock_Map == null || stock_Map.isEmpty()) {
			stock_Map = AllStockInfoMap();
		}
		String qzcode = null;
		if (i == 1) {// AB股
			for (String key : stock_Map.keySet()) {
				qzcode = key.substring(0, 2);
				if (qzcode.equals("SH") || qzcode.equals("SZ")) {
					rsMap.put(key, stock_Map.get(key));
				}
			}
		} else if (i == 2) {// 港股
			for (String key : stock_Map.keySet()) {
				qzcode = key.substring(0, 2);
				if (qzcode.equals("HK")) {
					rsMap.put(key, stock_Map.get(key));
				}
			}
		} else if (i == 3) {// 基金
			for (String key : stock_Map.keySet()) {
				qzcode = key.substring(0, 2);
				if (qzcode.equals("OF")) {
					rsMap.put(key, stock_Map.get(key));
				}
			}
		} else if (i == 4) {// 板块
			for (String key : stock_Map.keySet()) {
				qzcode = key.substring(0, 2);
				if (qzcode.equals("BI")) {
					rsMap.put(key, stock_Map.get(key));
				}
			}
		} else {// 全部
			rsMap = stock_Map;
		}
		return rsMap;
	}

	public static boolean isNullOrEmpty(String value){
		return null==value || "".equals(value) ;
	}
	
    /**
    * 计算采用utf-8编码方式时字符串所占字节数
    *
    * @param content
    * @return
    */
    public static int getByteSizeForUtf8(String content) {
            int size = 0;
            if (null != content) {
                    try {
                            // 汉字采用utf-8编码时占3个字节
                            size = content.getBytes("utf-8").length;
                    } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                    }
            }
            return size;
    }
}
