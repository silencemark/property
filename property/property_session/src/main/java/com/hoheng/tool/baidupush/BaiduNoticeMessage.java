package com.hoheng.tool.baidupush;

import net.sf.json.JSONObject;

/**
 * 百度android通知格式 { "title" : "hello" , "description": "hello world" //必选
 * "notification_builder_id": 0, //可选 "notification_basic_style": 7, //可选
 * "open_type":0, //可选 "url": "http://developer.baidu.com", //可选
 * "pkg_content":"", //可选 "custom_content":{"key":"value"}, }
 */
public class BaiduNoticeMessage {
	// 通知标题，可以为空；如果为空则设为appid对应的应用名;
	private String title = null;
	// 通知文本内容，不能为空;
	private String description = null;
	// 点击通知后的行为(1：打开Url; 2：自定义行为；);
	private Integer open_type = 0;
	// 需要打开的Url地址，open_type为1时才有效;
	private String url = null;
	/*
	 * open_type为2时才有效，Android端SDK会把pkg_content字符串转换成Android
	 * Intent,通过该Intent打开对应app组件，所以pkg_content字符串格式必须遵循Intent
	 * uri格式，最简单的方法可以通过Intent方法toURI()获取
	 */
	private String pkg_content = null;
	/*
	 * 自定义内容，键值对，Json对象形式(可选)；在android客户端，这些键值对将以Intent中的extra进行传递。
	 */
	private String custom_content = null;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public int getOpen_type() {
		return open_type;
	}

	public void setOpen_type(int open_type) {
		this.open_type = open_type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPkg_content() {
		return pkg_content;
	}

	public void setPkg_content(String pkg_content) {
		this.pkg_content = pkg_content;
	}

	public String getCustom_content() {
		return custom_content;
	}

	public void setCustom_content(String custom_content) {
		this.custom_content = custom_content;
	}

	@Override
	public String toString() {
		return "AndroidNoticeFormat [title=" + title + ", description="
				+ description + ",  open_type=" + open_type
				+ ", url=" + url + ", pkg_content=" + pkg_content
				+ ", custom_content=" + custom_content + "]";
	}

	/**
	 * 转换为JSON字符 串
	 * 
	 * @return
	 */
	public String toJsonString() {
		return convertToJson().toString();
	}

	/**
	 * 转换为json对象
	 * 
	 * @return
	 */
	public JSONObject convertToJson() {
		JSONObject jsonObj = JSONObject.fromObject(this);
		if (title == null) {
			jsonObj.remove("title");
		}
		if (description == null) {
			jsonObj.remove("description");
		}
		if (open_type == null) {
			jsonObj.remove("open_type");
		}
		if (url == null) {
			jsonObj.remove("url");
		}
		if (pkg_content == null) {
			jsonObj.remove("pkg_content");
		}
		if (custom_content == null) {
			jsonObj.remove("custom_content");
		}
		return jsonObj;
	}

}
