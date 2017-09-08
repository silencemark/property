package com.hoheng.util;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

 
/** 
* 获取经纬度
* 
* @author jueyue 返回格式：Map<String,Object> map map.put("status", 
* reader.nextString());//状态 map.put("result", list);//查询结果 
* list<map<String,String>> 
* 密钥:f247cdb592eb43ebac6ccd27f796e2d2 
*/ 
public class baidujingweicity { 
     
    /** 
    * @param addr 
    * 查询的地址 
    * @return 
    * @throws IOException 
    */ 
    public String getCoordinate(String lat,String lng) throws IOException { 
        //纬度lng
        //经度lat
        String key = "wBYHoaC0rzxp8zqGCdx9WXxa"; 
        String url = "http://api.map.baidu.com/geocoder/v2/?ak="+key+"&location="+lat+","+lng+"&output=json&pois=1"; 
        URL myURL = null; 
        URLConnection httpsConn = null; 
        try { 
            myURL = new URL(url); 
        } catch (MalformedURLException e) { 
            e.printStackTrace(); 
        }
        InputStreamReader insr = null;
        BufferedReader br = null;
        JSONObject jsonObject=new JSONObject();
        try { 
            httpsConn = (URLConnection) myURL.openConnection();// 不使用代理 
            if (httpsConn != null) { 
                insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8"); 
                br = new BufferedReader(insr); 
                String data = null; 
                while((data= br.readLine())!=null){ 
                	jsonObject=JSONObject.fromObject(data);
                	System.out.println(jsonObject);
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
        Map<String, Object> cityMap=jsonObject;
        Map<String, Object> result=(Map<String, Object>) cityMap.get("result");
        Map<String, Object> addressComponent=(Map<String, Object>) result.get("addressComponent");
        return addressComponent.get("city")+""; 
    }
	public static void main(String[] args) {
		baidujingweicity getLatAndLngByBaidu = new baidujingweicity();
        Object[] o;
		Map<String, Object> jingweiMap=new HashMap<String, Object>();
		try {
			String cityname= getLatAndLngByBaidu.getCoordinate("26.286083","107.523648");
			System.out.println(cityname.substring(0,cityname.length()-1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getcity(String lat,String lng){
		baidujingweicity getLatAndLngByBaidu = new baidujingweicity();
        Object[] o;
		Map<String, Object> jingweiMap=new HashMap<String, Object>();
		String cityname="";
		try {
			cityname= getLatAndLngByBaidu.getCoordinate(lat,lng);
			System.out.println(cityname.substring(0,cityname.length()-1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cityname.substring(0,cityname.length()-1);
	}
}