package com.lr.backer.util;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.lr.labor.weixin.util.SignUtil;
import com.lr.labor.weixin.util.WeiXinCenterProxy;
import com.lr.labor.weixin.util.WeiXinConfigure;

/** 
* ��ȡ��γ��
* 
* @author jueyue ���ظ�ʽ��Map<String,Object> map map.put("status", 
* reader.nextString());//״̬ map.put("result", list);//��ѯ��� 
* list<map<String,String>> 
* ��Կ:f247cdb592eb43ebac6ccd27f796e2d2 
*/ 
public class baidujingwei { 
     
    /** 
    * @param addr 
    * ��ѯ�ĵ�ַ 
    * @return 
    * @throws IOException 
    */ 
    public Object[] getCoordinate(String addr) throws IOException { 
        String lng = null;//����
        String lat = null;//γ��
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
            httpsConn = (URLConnection) myURL.openConnection();// ��ʹ�ô��� 
            if (httpsConn != null) { 
                insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8"); 
                br = new BufferedReader(insr); 
                String data = null; 
                int count = 1;
                while((data= br.readLine())!=null){ 
                    if(count==5){
                        lng = (String)data.subSequence(data.indexOf(":")+1, data.indexOf(","));//����
                        count++;
                    }else if(count==6){
                        lat = data.substring(data.indexOf(":")+1);//γ��
                        count++;
                    }else{
                        count++;
                    }
                } 
            } 
        } catch (IOException e) { 
            e.printStackTrace(); 
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
			o = getLatAndLngByBaidu.getCoordinate("浙江省杭州市");

	        jingweiMap.put("longitude", o[0]);
	        jingweiMap.put("latitude", o[1]);
	        System.out.println(o[0]+"--------------"+o[1]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//�������
		public static Map<String, Object> initWeixinShareParam(HttpServletRequest request,String... strings) {
			// ����������� ΢�ŷ������
			System.out.println("΢�ŷ������");
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
						.println("--------------------�������--------------------------------");
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
		}
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
			int n = 0; // ��ʾ��ǰ���ֽ���
			int i = 2; // Ҫ��ȡ���ֽ���ӵ�3���ֽڿ�ʼ
			for (; i < bytes.length && n < length; i++)
			{
			// ����λ�ã���3��5��7�ȣ�ΪUCS2�����������ֽڵĵڶ����ֽ�
			if (i % 2 == 1)
				{
					n++; // ��UCS2�ڶ����ֽ�ʱn��1
				}
			else
				{
				// ��UCS2����ĵ�һ���ֽڲ�����0ʱ����UCS2�ַ�Ϊ���֣�һ�������������ֽ�
				if (bytes[i] != 0)
					{
						n++;
					}
				}
			}
			// ���iΪ����ʱ�������ż��
			if (i % 2 == 1)
			{
				// ��UCS2�ַ��Ǻ���ʱ��ȥ�������һ��ĺ���
				if (bytes[i - 1] != 0)
					i = i - 1;
				// ��UCS2�ַ�����ĸ�����֣��������ַ�
				else
					i = i + 1;
			}
				return new String(bytes, 0, i, "Unicode");
			}
}
