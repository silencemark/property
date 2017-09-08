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

import net.sf.json.JSONObject;

 
/** 
* ��ȡ��γ��
* 
* @author jueyue ���ظ�ʽ��Map<String,Object> map map.put("status", 
* reader.nextString());//״̬ map.put("result", list);//��ѯ��� 
* list<map<String,String>> 
* ��Կ:f247cdb592eb43ebac6ccd27f796e2d2 
*/ 
public class baidujingweicity { 
     
    /** 
    * @param addr 
    * ��ѯ�ĵ�ַ 
    * @return 
    * @throws IOException 
    */ 
    public String getCoordinate(String lat,String lng) throws IOException { 
        //γ��lng
        //����lat
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
            httpsConn = (URLConnection) myURL.openConnection();// ��ʹ�ô��� 
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
        return addressComponent.get("city")+","+addressComponent.get("province")+","+addressComponent.get("district");
    }
	public static void main(String[] args) {
		baidujingweicity getLatAndLngByBaidu = new baidujingweicity();
        Object[] o;
		Map<String, Object> jingweiMap=new HashMap<String, Object>();
		String cityInfos[]=null;
		try {
			String cityInfo= getLatAndLngByBaidu.getCoordinate("31.254820","121.605957");
			cityInfos=cityInfo.split(",");
			System.out.println(cityInfos[0].substring(0,cityInfos[0].length()-1));
			System.out.println(cityInfos[1].substring(0,cityInfos[1].length()-1));
			System.out.println(cityInfos[2].substring(0,cityInfos[2].length()-1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getcity(String lat,String lng){
		baidujingweicity getLatAndLngByBaidu = new baidujingweicity();
        Object[] o;
		Map<String, Object> jingweiMap=new HashMap<String, Object>();
		String cityInfos[]=null;
		String cityname="";
		try {
			String cityInfo= getLatAndLngByBaidu.getCoordinate(lat,lng);
			cityInfos=cityInfo.split(",");
			if(cityInfos[0].equals(cityInfos[1])){
				cityname=cityInfos[2];
			}else{
				cityname=cityInfos[0].substring(0,cityInfos[0].length()-1);
			}
			System.out.println();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cityname;
	}
	public static String getProvince(String lat,String lng){
		baidujingweicity getLatAndLngByBaidu = new baidujingweicity();
		String cityInfos[]=null;
		try {
			String cityInfo= getLatAndLngByBaidu.getCoordinate(lat,lng);
			cityInfos=cityInfo.split(",");
			System.out.println(cityInfos[1].substring(0,cityInfos[1].length()-1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cityInfos[1].substring(0,cityInfos[1].length()-1);
	}
}