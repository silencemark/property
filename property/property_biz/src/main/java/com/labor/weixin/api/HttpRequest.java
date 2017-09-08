package com.labor.weixin.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.labor.weixin.util.WeiXinConfigure;
import com.labor.weixin.util.Weixin509TrustManager;

public class HttpRequest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(HttpRequest.class);

	public static final String GET = "GET";
	public static final String POST = "POST";

	public static final String JPEG = "image/jpeg";
	public static final String MPEG = "video/mpeg";

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new Weixin509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			LOGGER.error("Weixin server connection timed out.");
		} catch (Exception e) {
			LOGGER.error("https request error:{}", e);
		}
		return jsonObject;
	}

	/**
	 * @function:提交Form表单,用于上传媒体文件
	 * @datetime:2015-1-19 下午08:28:04
	 * @Author: robin
	 * @param: @param requestUrl
	 * @param: @param requestMethod
	 * @param: @param outputStr
	 * @return JSONObject
	 */
	public static JSONObject httpRequestForm(String requestUrl, File file,
			JSONObject fileJson) {
		// String
		// requestUrl="http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="
		// + access_token + "&type=" + fileType;
		String result = "";
		String end = "\r\n";
		String twoHyphens = "--"; // 用于拼接

		URL submit = null;
		JSONObject json = null;
		try {
			submit = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) submit
					.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);

			conn.setRequestMethod(POST);
			conn.setRequestProperty("Connection", "Keep-Alive");
			String boundary = "*****"; // 用于拼接 可自定义
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			// 获取输出流对象，准备上传文件
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + end);
			String filename = "";
			String filePath = "";
			String contentType = "";
			if (isNotEmpty(fileJson, "filename")) {
				filename = fileJson.getString("filename");
			} else {
				throw new Exception("上传的 filename 不能为空！");
			}
			if (isNotEmpty(fileJson, "filePath")) {
				filePath = fileJson.getString("filePath");
			} else {
				throw new Exception("上传的 filePath 不能为空！");
			}
			if (isNotEmpty(fileJson, "contentType")) {
				contentType = fileJson.getString("contentType");
			} else {
				throw new Exception("上传的 contentType 不能为空！");
			}

			dos.writeBytes("Content-Disposition: form-data; name=\"" + file
					+ "\";filename=\"" + filename + ";filelength=\""
					+ file.length() + ";Content-Type=\"" + contentType + end);
			dos.writeBytes(end);
			// 对文件进行传输
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[8192]; // 8k
			int count = 0;
			while ((count = fis.read(buffer)) != -1) {
				dos.write(buffer, 0, count);
			}
			fis.close(); // 关闭文件流

			dos.writeBytes(end);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
			dos.flush();

			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			result = br.readLine();
			dos.close();
			is.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("与服务器连接发生异常错误:" + e.toString());
			System.out.println("连接地址是:" + requestUrl);
		}
		json = JSONObject.fromObject(result); // 获取到返回Json请自行根据返回码获取相应的结果
		return json;

	}

	private static boolean isNotEmpty(JSONObject fileJson, String key) {
		if (null != fileJson && fileJson.containsKey(key)) {
			String value = fileJson.getString(key);
			return null != value && !"".equals(value);
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static String httpsGetDownload(String requestUrl, String filePath,String fileName) {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
					null, new TrustStrategy() {
						@Override
						public boolean isTrusted(X509Certificate[] chain,
								String authType) throws CertificateException {
							return true;
						}
					}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslContext);
			CloseableHttpClient httpClient = HttpClients.custom()
					.setSSLSocketFactory(sslsf).build();
			HttpGet get = new HttpGet();
			get.setURI(new URI(requestUrl));
			CloseableHttpResponse response = httpClient.execute(get);
			File dirFile = new File(filePath);
			// 如果文件夹不存在则创建
			if (!dirFile.exists()&&!dirFile.isDirectory()) {
				dirFile.mkdir();
			}
			File storeFile  = new File(filePath+"/"+fileName);
			FileOutputStream output = new FileOutputStream(storeFile);
			HttpEntity entrty = response.getEntity();
			// 得到网络资源的字节数组,并写入文件
			if (entrty != null) {
				InputStream instream = entrty.getContent();
				byte b[] = new byte[1024];
				int j = 0;
				while ((j = instream.read(b)) != -1) {
					output.write(b, 0, j);
				}
			}
			output.flush();
			output.close();
		} catch (Exception e) {
			LOGGER.error("https request error:{}", e);
		}
		return (filePath+"/"+fileName);
	}
}
