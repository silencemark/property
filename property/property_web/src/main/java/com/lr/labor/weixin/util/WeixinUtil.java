package com.lr.labor.weixin.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lr.backer.util.FileUtil;
import com.lr.backer.util.HttpClient;
import com.lr.labor.weixin.pojo.Button;
import com.lr.labor.weixin.pojo.Menu;

/**
 * 公众平台通用接口工具类
 * 
 */
public class WeixinUtil {
	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(Menu menu, String accessToken) {
		int result = 0;

		// 拼装创建菜单的url
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		JsonConfig config = new JsonConfig();
		// 忽略掉getDishDate属性
		config.setJsonPropertyFilter(new PropertyFilter() {
			@Override
			public boolean apply(Object arg0, String arg1, Object arg2) {
				if (arg1.equals("sub_button") || arg1.equals("url")) {
					if (null == arg2 || "".equals(arg2)) {
						System.out.println(null == arg2 || "".equals(arg2));
						return true;
					}
				}
				return false;
			}
		});

		String jsonMenu = JSONObject.fromObject(menu, config).toString();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setIgnoreDefaultExcludes(true);
		log.info(jsonMenu);
		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			log.info(jsonObject.toString());
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject
						.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return result;
	}

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
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;
	}

	public String GetCodeRequest = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

	public String urlEnodeUTF8(String str) {
		String result = str;
		try {
			result = URLEncoder.encode(str, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String GetTokenRequest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	public String GetTokenRequest(String code) {
		String result = null;
		GetTokenRequest = GetTokenRequest.replace("APPID",
				urlEnodeUTF8(WeiXinConfigure.APPID));
		GetTokenRequest = GetTokenRequest
				.replace("SECRET", WeiXinConfigure.APPSECRET);
		// System.out.println("\nGetTokenRequest = "+GetTokenRequest+"\n");
		// System.out.println("\n code = "+code+"\n");

		GetTokenRequest = GetTokenRequest.replace("CODE", code);
		result = GetTokenRequest;
		return result;
	}

	public String getInfo(String code) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		
		HttpSession session = request.getSession();
		if (null == code || "".equals(code)) {
			code = null != session.getAttribute("code") ? session.getAttribute("code").toString()
					: "";
		} else {
			session.setAttribute("code", code);
		}
		String result = HttpClient.get(GetTokenRequest(code));
		System.out.println("result.........................................."
				+ result);

		return result;
	}

	public static List<Map<String, Object>> importOpenId(
			List<Map<String, Object>> datalist) {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();

		// 调用接口获取access_token
		String tokenid = WeiXinCenterProxy.getAccessToken();
		// 获取该公众号下的所有的openid
		String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token="
				+ tokenid;
		// 获取该openid的详细信息
		String detailurl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
				+ tokenid + "&lang=zh_CN&openid=";
		String result = get(url);
		JSONObject jsonobj = JSONObject.fromObject(result);// 将字符串转化成json对象
		Map<String, Object> map = null;
		Map<String, Object> vMap = new HashMap<String, Object>();
		if (jsonobj.has("data")) {
			String data = jsonobj.getString("data");// 获取字符串。
			JSONObject jsonopenid = JSONObject.fromObject(data);
			if (jsonobj.has("data")) {
				JSONArray openidArray = jsonopenid.getJSONArray("openid");
				// for (int i = 0; i < openidArray.size(); i++) {
				// map = new HashMap<String, Object>();
				// String rt = get(detailurl + openidArray.getString(i));
				// map = FileUtil.parseJSON2Map(rt);
				// listMap.add(map);
				// }

				for (int i = 0; i < datalist.size(); i++) {
					map = new HashMap<String, Object>();
					Map<String, Object> openmap = datalist.get(i);
					String rt = get(detailurl + openmap.get("openid"));
					map = FileUtil.parseJSON2Map(rt);
					listMap.add(map);
					System.out.println(i + ":" + rt);

				}

			}

		}
		return listMap;

	}

	public static Map<String, Object> getUserInfo(String openid) {
		String tokenid = WeiXinCenterProxy.getAccessToken();

		// 获取该openid的详细信息
		String detailurl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
				+ tokenid + "&lang=zh_CN&openid=" + openid;
		Map<String, Object> map = null;
		System.out.println(detailurl);
		String rt = get(detailurl);
		map = FileUtil.parseJSON2Map(rt);
		System.out.println(map.get("openid") + "::::" + map.get("nickname"));
		return map;
	}

	private static final String CHARSET = "UTF-8";
	private static CookieStore _cookieStore = null;

	public static String getContentCharSet(final HttpEntity entity)
			throws ParseException {

		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}
		String charset = null;
		if (entity.getContentType() != null) {
			HeaderElement values[] = entity.getContentType().getElements();
			if (values.length > 0) {
				NameValuePair param = values[0].getParameterByName("charset");
				if (param != null) {
					charset = param.getValue();
				}
			}
		}

		if (org.apache.commons.lang.StringUtils.isEmpty(charset)) {
			charset = "UTF-8";
		}
		return charset;
	}

	public static String get(String url) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		if (_cookieStore != null) {
			httpclient.setCookieStore(_cookieStore);
		}
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = null;
		String charset = "utf-8";
		try {
			// responseBody = httpclient.execute(httpget, responseHandler);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// charset = getContentCharSet(entity);
				// 使用EntityUtils的toString方法，传递编码，默认编码是ISO-8859-1
				responseBody = EntityUtils.toString(entity, charset);
			}

		} catch (Exception e) {

		}
		_cookieStore = httpclient.getCookieStore();
		httpclient.getConnectionManager().shutdown();
		return responseBody;
	}

	public static void main(String[] args) {

		Button button = new Button();
		button.setName("1111");
		Button button3 = new Button();
		button3.setName("");
		Menu menu = new Menu();
		Button[] buttonArrayButtons = new Button[2];
		buttonArrayButtons[0] = button;
		buttonArrayButtons[1] = button3;
		menu.setButton(buttonArrayButtons);

		String jsonMenu = JSONObject.fromObject(menu).toString();
		System.out.println(jsonMenu);
		JsonConfig config = new JsonConfig();
		// 忽略掉getDishDate属性
		config.setJsonPropertyFilter(new PropertyFilter() {
			@Override
			public boolean apply(Object arg0, String arg1, Object arg2) {
				if (arg1.equals("name")) {
					System.out.println(null == arg2 || "".equals(arg2));
					if (null == arg2 || "".equals(arg2)) {
						return true;
					}
				}
				return false;
			}

		});
		jsonMenu = JSONObject.fromObject(menu, config).toString();
		System.out.println(jsonMenu);

		// getUserInfo("oALjgjoF2z8GDy_FF9wGLfCCku5Q");
		//		
		//
		//		
		// getUserInfo("oYINut33zb6aChxRIg1VihPoItRU");
		//		
		//
		// getUserInfo("oALjgjgynyaH53Dya7H500cZRKw4");
		//		
		//
		//
		// getUserInfo("oALjgjhbKRtNX4_pckKhRDu8UGj0");
		//		
		//
		//
		// getUserInfo("oALjgjhphj4nePlFRSmgjUHcpe8c");
		//		
		//
		// getUserInfo("oALjgji1Fh_AaTC2fI0BlCw2b1Aw");
		//		
		//
		//
		// getUserInfo("oALjgjiigL8gFWBG7gccVGsHuaW4");
		//		
		//
		//
		//
		// getUserInfo("oALjgjuqFqK2D6BNtRpcKvPoHIqw");
		//		
		//
		// getUserInfo("oALjgjuhDK8F9rYvNq_3zIX67V7M");
		//		
		//
		// getUserInfo("oALjgjutijczgkrw7-LbJbzzYRg4");
		//		
		//
		// getUserInfo("oALjgjuLEdk2WA36ARlxLv50j74w");
	}
	public static int sendMessage(Map<String, Object> dataMap, String accessToken){
		int result=0;
		JSONObject jsonNews=new JSONObject();
		String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken;
		jsonNews.put("touser",dataMap.get("openid"));
		jsonNews.put("template_id","vrJcdnw4sc7yzdOZiADx0NoGz3Ssrw38Rq27j2fI-9I");
		jsonNews.put("url", dataMap.get("calbackurl"));
		jsonNews.put("topcolor", "#FF0000");
		JSONObject data=new JSONObject();
			JSONObject first=new JSONObject();
			first.put("value",dataMap.get("title"));
			first.put("color", "#173177");
			JSONObject keyword1=new JSONObject();
			keyword1.put("value",dataMap.get("fromname"));
			keyword1.put("color", "#173177");
			JSONObject keyword2=new JSONObject();
			keyword2.put("value",dataMap.get("content"));
			keyword2.put("color", "#173177");
			JSONObject remark=new JSONObject();
			remark.put("value",dataMap.get("remark") );
			remark.put("color", "#173177");
			data.put("first", first);
			data.put("keyword1", keyword1);
			data.put("keyword2", keyword2);
			data.put("remark", remark);
		jsonNews.put("data", data);
		System.out.println("666666666666666666666666666:"+jsonNews.toString());
		JSONObject jsonObject = httpRequest(url, "POST", jsonNews.toString());
		System.out.println(jsonObject);
		return result;
	}	

}
