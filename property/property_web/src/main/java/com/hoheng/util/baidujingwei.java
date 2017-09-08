package com.hoheng.util;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

 
/** 
* 获取经纬度
* 
* @author jueyue 返回格式：Map<String,Object> map map.put("status", 
* reader.nextString());//状态 map.put("result", list);//查询结果 
* list<map<String,String>> 
* 密钥:f247cdb592eb43ebac6ccd27f796e2d2 
*/ 
public class baidujingwei { 
     
    /** 
    * @param addr 
    * 查询的地址 
    * @return 
    * @throws IOException 
    */ 
    public Object[] getCoordinate(String addr) throws IOException { 
        String lng = null;//经度
        String lat = null;//纬度
        String address = null; 
        try { 
            address = java.net.URLEncoder.encode(addr, "UTF-8"); 
        }catch (UnsupportedEncodingException e1) { 
            e1.printStackTrace(); 
        } 
        String key = "wBYHoaC0rzxp8zqGCdx9WXxa"; 
        String url = String .format("http://api.map.baidu.com/geocoder?address=%s&output=json&key=%s", address, key); 
        URL myURL = null; 
        URLConnection httpsConn = null; 
        try { 
            myURL = new URL(url); 
        } catch (MalformedURLException e) { 
            e.printStackTrace(); 
        } 
        InputStreamReader insr = null;
        BufferedReader br = null;
        try { 
            httpsConn = (URLConnection) myURL.openConnection();// 不使用代理 
            if (httpsConn != null) { 
                insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8"); 
                br = new BufferedReader(insr); 
                String data = null; 
                int count = 1;
                while((data= br.readLine())!=null){ 
                    if(count==5){
                        lng = (String)data.subSequence(data.indexOf(":")+1, data.indexOf(","));//经度
                        count++;
                    }else if(count==6){
                        lat = data.substring(data.indexOf(":")+1);//纬度
                        count++;
                    }else{
                        count++;
                    }
                } 
                 
            } 
        } catch (IOException e) { 
            e.printStackTrace(); 
            return null; 
        }catch(Exception ex){
        	ex.printStackTrace(); 
        	return null; 
        } finally {
            if(insr!=null){
                insr.close();
            }
            if(br!=null){
                br.close();
            }
        }
        return new Object[]{lng,lat}; 
    }
    public static Map<String, Object> getjingwei(String address){
    	baidujingwei getLatAndLngByBaidu = new baidujingwei();
        Object[] o;
		Map<String, Object> jingweiMap=new HashMap<String, Object>();
		try {
			o = getLatAndLngByBaidu.getCoordinate(address);
			if(o==null){
				return null;
			}

	        jingweiMap.put("longitude", o[0]);
	        jingweiMap.put("latitude", o[1]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
         return jingweiMap;
    }
	public static void main(String[] args) {
		baidujingwei getLatAndLngByBaidu = new baidujingwei();
        Object[] o;
		Map<String, Object> jingweiMap=new HashMap<String, Object>();
		try {
			o = getLatAndLngByBaidu.getCoordinate("上海市辖浦东新区新金桥路");
	        jingweiMap.put("longitude", o[0]);
	        jingweiMap.put("latitude", o[1]);
	        System.out.println(o[0]+"--------------"+o[1]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//分享参数
	/*	public static Map<String, Object> initWeixinShareParam(HttpServletRequest request,String... strings) {
			// 分享参数设置 微信分享参数
			System.out.println("微信分享参数");
			String url = getURL(request);
			String url2 = Constants.PROJECT_PATH + url;
			if (url.indexOf("/") == 0) {
				url = url.substring(1, url.length());
			}
			url = Constants.PROJECT_PATH + url;
			Map<String, Object> shareMap = new HashMap<String, Object>();
			try {
				String jsTicket = WeiXinCenterProxy.getJSApiTicket();
				String noncestr = UUID.randomUUID().toString();
				String timestamp = Long.toString(System.currentTimeMillis() / 1000);
				// url = URLEncoder.encode(url,"UTF-8");
				String signature = "jsapi_ticket=" + jsTicket + "&noncestr="
				+ noncestr + "&timestamp=" + timestamp + "&url=" + url;
				String signature2 = "jsapi_ticket=" + jsTicket + "&noncestr="
				+ noncestr + "&timestamp=" + timestamp + "&url=" + url2;
				System.out
						.println("--------------------分享参数--------------------------------");
				System.out.println("\nString1-----------------" + signature);
				signature = SignUtil.getSignature(signature);
				signature2 = SignUtil.getSignature(signature2);
				shareMap.put("appId",WeiXinConfigure.APPID);

				System.out.println("\nappId-----------------" + WeiXinConfigure.APPID);
				shareMap.put("timestamp", timestamp);
				System.out.println("\ntimestamp-----------------" + timestamp);
				shareMap.put("jsapi_ticket", jsTicket);
				System.out.println("\njsTicket-----------------" + jsTicket);
				shareMap.put("nonceStr", noncestr);
				System.out.println("\nnonceStr-----------------" + noncestr);
				shareMap.put("url", url);
				System.out.println("\nurl-----------------" + url);

				shareMap.put("signature", signature.toLowerCase());
				shareMap.put("signature2", signature2.toLowerCase());
				System.out.println("\nsignature-----------------" + signature);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return shareMap;
		}*/
		public static String getURL(HttpServletRequest request) {
			String str = request.getServletPath();
			String  url= str;
			if(request.getQueryString()!=null){
				url = str + "?" + request.getQueryString();
			} 
			return url;
		}
		
		public static String bSubstring(String s, int length) throws Exception{
			byte[] bytes = s.getBytes("Unicode");
			int n = 0; // 表示当前的字节数
			int i = 2; // 要截取的字节数，从第3个字节开始
			for (; i < bytes.length && n < length; i++)
			{
			// 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
			if (i % 2 == 1)
				{
					n++; // 在UCS2第二个字节时n加1
				}
			else
				{
				// 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
				if (bytes[i] != 0)
					{
						n++;
					}
				}
			}
			// 如果i为奇数时，处理成偶数
			if (i % 2 == 1)
			{
				// 该UCS2字符是汉字时，去掉这个截一半的汉字
				if (bytes[i - 1] != 0)
					i = i - 1;
				// 该UCS2字符是字母或数字，则保留该字符
				else
					i = i + 1;
			}
				return new String(bytes, 0, i, "Unicode");
			}
}
