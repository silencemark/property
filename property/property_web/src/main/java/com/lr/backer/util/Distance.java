package com.lr.backer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

public class Distance {

	private static final double EARTH_RADIUS = 6378137;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
	 * 
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	public static double GetDistance(double lng1, double lat1, double lng2,
			double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	public JSONObject getJson(HttpServletRequest request) {
		String json = "";
		BufferedReader br;
		try {

			br = new BufferedReader(new InputStreamReader(
					(ServletInputStream) request.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer("");
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();
			json = sb.toString();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSONObject.fromObject(json);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成方法存根
		 double distance = GetDistance(121.455207, 31.126425, 121.566705,
		 31.193438);
		 System.out.println("Distance is:" + (int) distance);
//		String lon = "39.902641,116.282181";
//		System.out.println(getDis(lon));

	}

	public static String getDis(String location) {
		// String location="lat,lon";
		String json = loadJSON("http://api.map.baidu.com/geocoder/v2/?ak=wBYHoaC0rzxp8zqGCdx9WXxa"
				+ "&callback=renderReverse&location=" 
				+ location
				+ "&output=json&pois=1");
		System.out.println(json);

		String[] address = json.split("\\(");
		String ta = "";
		int index = 0;
		for (String a : address) {
			if (index != 0) {
				ta = ta + a;
			}
			index++;
		}
		String[] addrJson = (ta + "dd").split("\\)");

		System.out.println(":::11::" + ta);

		String tb = "";
		for (int k = 0; k < addrJson.length; k++) {
			if (k < addrJson.length - 1) {
				tb = tb + addrJson[k];
			}
		}
		System.out.println(":::22::" + tb);
		JSONObject jsonObject = JSONObject.fromObject(tb);

		JSONObject addressComponent = jsonObject.getJSONObject("result")
				.getJSONObject("addressComponent");

//		String city = addressComponent.getString("city");

		String district = addressComponent.getString("district");
	
		return district;
	}

	public static String loadJSON(String url) {
		StringBuilder json = new StringBuilder();
		try {
			URL oracle = new URL(url);
			URLConnection yc = oracle.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc
					.getInputStream()));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return json.toString();
	}

}
