package com.lr.labor.weixin.util;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @ClassName:WeiXinConfigure.java
 * @ClassPath:com.hk.reserve.util
 * @Desciption:微信配置信息
 * @Author: robin
 * @Date: 2015-1-15 下午08:23:08
 * 
 */
public class WeiXinConfigure extends PropertyPlaceholderConfigurer {

	public static String APPID;
	public static String APPSECRET;
	public static String PROJECT_PATH;
	public static String  QRCODE_URL= "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";
	public static String SHOWQRCODE = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";
	public WeiXinConfigure() {
		super();
	}

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		// 加载微信配置的信息
		APPID = props.getProperty("weixin.appid");
		APPSECRET = props.getProperty("weixin.secret");
		PROJECT_PATH = props.getProperty("weixin.project_path");
	}
}
