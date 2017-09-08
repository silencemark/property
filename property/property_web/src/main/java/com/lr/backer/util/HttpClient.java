package com.lr.backer.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpProtocolParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http工具类
 * 
 * @author xufeng
 */
public class HttpClient {
	private static Logger LOG = LoggerFactory.getLogger(HttpClient.class);
	private static final String CHARSET = "UTF-8";
	private static CookieStore _cookieStore = null;

	/**
	 * 调用HTTP GET请求
	 * 
	 * @param url
	 * @param param
	 * @return String
	 * @throws Exception
	 */
	public static String get(String url) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		if (_cookieStore != null) {
			httpclient.setCookieStore(_cookieStore);
		}
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = null;
		try {
			responseBody = httpclient.execute(httpget, responseHandler);
		} catch (Exception e) {
			LOG.error("ERROR in get url: {}", url);
		}
		_cookieStore = httpclient.getCookieStore();
		httpclient.getConnectionManager().shutdown();
		return responseBody;
	}

	/**
	 * 调用HTTP POST请求
	 * 
	 * @param url
	 * @param param
	 * @return String
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> param) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(
				HttpProtocolParams.HTTP_CONTENT_CHARSET, CHARSET);
		HttpPost httppost = new HttpPost(url);
		if (param != null && !param.isEmpty()) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : param.entrySet()) {
				nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
			try {
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
						CHARSET));
			} catch (UnsupportedEncodingException e) {
			}
		}
		if (_cookieStore != null) {
			httpclient.setCookieStore(_cookieStore);
		}
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = null;
		try {
			responseBody = httpclient.execute(httppost, responseHandler);
		} catch (Exception e) {
			LOG.error("ERROR in post to url {} with {}", new Object[] { url,
					param });
		}
		_cookieStore = httpclient.getCookieStore();
		httpclient.getConnectionManager().shutdown();
		return responseBody;
	}

	public String encode(String value) {
		String result = "";
		if (value != null) {
			try {
				result = URLEncoder.encode(value, CHARSET);
			} catch (Exception e) {
			}
		}
		return result;
	}


}
