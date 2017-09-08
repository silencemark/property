package com.lr.labor.weixin.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.lr.backer.util.Constants;
import com.lr.labor.weixin.pojo.Button;
import com.lr.labor.weixin.pojo.CommonButton;
import com.lr.labor.weixin.pojo.ComplexButton;
import com.lr.labor.weixin.pojo.Menu;
import com.lr.labor.weixin.pojo.ViewButton;

public class MenuManager {

	private static final Logger log = Logger.getLogger(MenuManager.class);

	public void importOpenId() {
		// 调用接口获取access_token
		String tokenid = WeiXinCenterProxy.getAccessToken();
		System.out.println(tokenid);
		String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token="
				+ tokenid;
		String result = get(url);
		JSONObject jsonobj = JSONObject.fromObject(result);// 将字符串转化成json对象
		if (jsonobj.has("data")) {
			String data = jsonobj.getString("data");// 获取字符串。
			JSONObject jsonopenid = JSONObject.fromObject(data);
			if (jsonobj.has("data")) {
				JSONArray openidArray = jsonopenid.getJSONArray("openid");
				for (int i = 0; i < openidArray.size(); i++) {
					System.out
							.println("openid:" + openidArray.getString(i) + " ");

				}
			}

		}

	}

	private static final String CHARSET = "UTF-8";
	private static CookieStore _cookieStore = null;

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

		}
		_cookieStore = httpclient.getCookieStore();
		httpclient.getConnectionManager().shutdown();
		return responseBody;
	}

	
	public boolean createMenu(Menu menu) {

		// 调用接口获取access_token
		String tokenid = WeiXinCenterProxy.getAccessToken();
		// 调用接口创建菜单
		int result = WeixinUtil.createMenu(menu, tokenid);

		// 判断菜单创建结果
		if (0 == result){
			log.info("菜单创建成功！");
			return true ;
		}else{
			log.info("菜单创建失败，错误码：" + result);
		}
		
		return false ;
	}
	
	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	public static Menu getMenu(List<LinkedHashMap<String, Object>> menuList) {
		/**
		 * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项
		 * 
		 * 
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？
		 * 
		 * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：
		 * 
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		Button[] menuButton ;
		
		if(null!=menuList && menuList.size()>0){
			menuButton = new Button[menuList.size()] ;
			for (int i=0, len=menuList.size();i<len;i++) {
				
				Map<String, Object> menuMap = menuList.get(i);
				List<LinkedHashMap<String, Object>> childRen = (List<LinkedHashMap<String, Object>>) menuMap.get("childRen");
				
				if(null!=childRen && childRen.size()>0){
					//存在子菜单的情况
					Button[] subButton ;
					ComplexButton bottomButton = new ComplexButton();
					bottomButton.setName(MapUtils.getString(menuMap, "menuname"));
					subButton = new Button[childRen.size()] ;
					//生成子菜单
					for (int j=0,len2=childRen.size();j<len2;j++) {
						LinkedHashMap<String, Object> item = childRen.get(j);
						Button button = getButton(item) ;
						if(null!=button){
							subButton[j] =button;
						}
					}
					//subButton : 是生成的子菜单的数组
					bottomButton.setSub_button(subButton);
					menuButton[i] = bottomButton ;
					
				}else {
					//不存在子菜单的情况
					//不存在子菜单
					log.info("空菜单");
					Button button = getButton(menuMap);
					if(null!=button){
						menuButton[i] = button;
					}
					
				}
				
				//menuButton.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
			}
			menu.setButton(menuButton);
		}
		return menu ;
	}
	
	
	private static Button getButton(Map<String, Object> item){
		if(null!=item && !item.isEmpty()){
			//这里生成每一个子菜单
			// 如果key = click ，则获取linkurl（存储的是关键字）
			//如果key= view ，则获取linkurl（存储的是连接地址）
			String msgKey = MapUtils.getString(item, "msgKey");
			//微信菜单可以是文本回复、图文消息、图片回复、链接跳转
			if(null!=msgKey && (
					Constants.WEIXIN_MENU_TXT.equals(msgKey) ||
					Constants.WEIXIN_MENU_ARTICLE.equals(msgKey) ||
					Constants.WEIXIN_MENU_IMG.equals(msgKey) ||
					Constants.WEIXIN_MENU_MUSIC.equals(msgKey) )){
				//微信菜单的文本回复
				//微信菜单的图文回复
				//微信菜单的图片回复
				//微信菜单的语音回复
				
				CommonButton itemButton = new CommonButton();
				itemButton.setName(MapUtils.getString(item, "menuname"));
				itemButton.setType(Constants.WEIXIN_MENU_CLICK);
				//文本 、 图文、图片、语音都是执行click
				itemButton.setKey(MapUtils.getString(item, "menuid"));
				return itemButton ;
				
			}else if(Constants.WEIXIN_MENU_LINK.equals(msgKey)){
				//微信菜单的链接地址
				ViewButton itemButton = new ViewButton();
				itemButton.setName(MapUtils.getString(item, "menuname"));
//				itemButton.setType(msgKey);
				itemButton.setType("view");
				itemButton.setUrl(MapUtils.getString(item, "linkurl"));
				return itemButton ;
			}else {
				log.info("微信菜单异常！");
			}
			//=============
		}
		return null;
	}
	
	
}
