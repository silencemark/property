package com.lr.backer.util;

import java.net.URL;
import java.util.Properties;


public class Constants {
	
	//项目路径
	//public static String PROJECT_PATH = "http://localhost/";
	public static String PROJECT_PATH = "http://www.ddjiaoren.com/";
	//public static String PROJECT_PATH = "http://zllknrenod.proxy.qqbrowser.cc/";
	
	
	
	//public static String COOK_DOMAIN = null;					//cookeis缓存域名  
	public static String COOK_DOMAIN ="www.ddjiaoren.com";
	
	public static  String BLAN_OPENID = "1";    //微信端正式数值(1)   本地测试雇主(2)   本地测试工人(3)  
	
	public static  String EMPLOYER_OPENID = "oYoUptzSmEjhF7m87e3A-IVuJ3Vc"; 	//silence	
	//public static  String EMPLOYER_OPENID = "oYoUpt0okxiH_2OF3wmo25I-D6Ro"; 
	//public static  String WORKER_OPENID = "oU9llt6ySTg_fddRfzodljhRWNrc"; //伊礼
	//public static  String EMPLOYER_OPENID = "oYoUptzSmEjhF7m87e3A-IVuJ3Vc"; 	//本地测试雇主openid	
		
	public static  String WORKER_OPENID = "oYoUpt0I-oD2XEhmE4KdNmUkVi78";//明哥
	
	//微信开放平台
	public static  String PC_APPID = "";
	public static  String PC_APPSECRET = "";

	// 商户资料
	public static  String APPID="wxe61fa1b721d619b9";
	public static  String APPSECRET="75011e1554940f14232719641a9913e2";
	public static  String PARTNER="1318817701";
	public static  String PARTNERKEY="95e4ab543797f7bd93788dd64205b9db";
	
	/**
	 * 头像默认图片
	 */
	public static  String DEFAULT_IMAGE=PROJECT_PATH+"appcssjs/images/page/pic_bg.png";
	
	
	/**
	 * 用户角色  管理员
	 */
	public static  String USER_MAMAGER_ROLE="1";
	/**
	 * 用户角色 代理商
	 */
	public static  String USER_AGENCY_ROLE="1f407e27dba011e594e100163e003d5d";
	/**
	 *用户角色 销售人员 
	 */
	public static  String USER_SELL_ROLE="e3a761c1cbd411e594e100163e003d5d";
	/**
	 *用户角色 客服人员 
	 */
	public static  String USER_CUSTOM_ROLE="a7df7a17d78f4090861ce92f10c00670";
	
	/**
	 *用户角色 招商人员
	 */
	public static  String USER_INVESTMENT="5660df6407c411e6811a02004c4f4f50";
	
	
	//字典表----工种
	public final static String OCCUPATION = "2";
	//字典表----所属行业
	public final static String INDUSTRY = "2";
	//(需求统一了 工种和行业)
	
	//字典表----结算方式
	public final static String CLEARINGFORM = "3";
	//字典表----消息类别
	public final static String MESSAGEKIND = "3faa4f1368f64faab60f81c9d759e062";
	//字典表----时间筛选
	public final static String TIMETYPE = "4";
	//字典表----薪资筛选
	public final static String SALARYTYPE = "5";
	//字典配置通知随即人数范围id
	public final static String INFONUMID = "24";
	
	
	// 以下字段来源于字典表中的数据
		// 当数据发生变更后，这里需要修改
		// 获取积分的途径= （问卷调查）
		public static  String INTE_WAY_QUESTION = "2";
		// 获取积分的途径= （乘坐出租）
		public static  String INTE_WAY_BUS = "1";
		// 获取积分的途径= （注册）
		public static  String INTE_WAY_REGISTER = "1";
		// 获取积分的途径= （加入帮派）
		public static  String INTE_WAY_JOINANGECY = "2";
		// 获取积分的途径= （参与活动）
		public static  String INTE_WAY_JOINACTIVITY = "3";
		// 获取积分的途径= （分享）
		public static  String INTE_WAY_SHARE = "4";
		// 获取积分的途径= （使用预约）
		public static  String INTE_WAY_USEAPPOINT = "5";
		// 获取积分的途径= （帮派评价）
		public static  String INTE_WAY_AGENCYAPPRAISE = "6";
		// 获取积分的途径= （服务评价）
		public static  String INTE_WAY_SERVICEAPPRAISE = "7";
		// 取消预约
		public static  String INTE_WAY_CANCELAPPOINT = "9";


		// 获取积分的途径(数据字典主表)
		public static  String DATATYPE_INTE_WAY = "10";
		// 积分类型(数据字典主表)
		public static  String DATATYPE_INTE_TYPE = "11";
		// 积分有效期设定
		public static  String DATATYPE_INTE_VALIDATIME = "3";

		// 积分类型 = （普通类型）
		public static  String INTE_TYPE_ORDINARY = "17";
		// 积分类型 = （特殊类型）
		public static  String INTE_TYPE_SPECIAL = "18";
		// 积分类型（typeid）
		public static  String INTE_TYPE = "11";

		// 积分二维码不正确
		public static  String INTE_SCAN_FAIL = "0";
		// 二维码已过期
		public static  String INTE_SCAN_TIMEOUT = "1";
		// 成功
		public static  String INTE_SCAN_SUCCESS = "2";
		// 未关注过触动传媒
		public static  String INTE_SCAN_NOFOCUS = "3";

		// 积分兑换部分结果参数
		// 没有该优惠券
		public static  String BUY_RESULT_NOCOUPONID = "1";
		// 优惠券已售完
		public static  String BUY_RESULT_EMPTY = "2";
		// 优惠券数量不足
		public static  String BUY_RESULT_LESSNUM = "3";
		// 用户积分不足
		public static  String BUY_RESULT_INTELESS = "4";
		// 满足兑换优惠券的所有条件
		public static  String BUY_CONDITION = "5";
		// 购买成功
		public static  String BUY_SUCCESS = "6";
		// 购买失败
		public static  String BUY_FAIL = "7";
		// 不满足购买条件爱你
		public static  String BUY_NOT_CONDITION = "8";

		// 微信常量end
		public static  String scope = "snsapi_base";
		private Properties properties = new Properties();
		public static  String LOGINADMIN = "administrator";
		public static  URL URL_ROOT = Constants.class.getResource("/");
		public static  String PATH_CLASS_ROOT = URL_ROOT.getPath();
		public static  String PATH_TEMPLATE = PATH_CLASS_ROOT
				+ "com/dzhqs/web/template/";
		public static  String FTP_DIR = "/wap";
		public static  String FilePath = PATH_CLASS_ROOT.substring(1,
				PATH_CLASS_ROOT.length() - 21)
				+ "/esp/chart/";
		public static  String FilePath_OLD = PATH_CLASS_ROOT.substring(0,
				PATH_CLASS_ROOT.length() - 21)
				+ "/web/wml/";
		public static  String File_Path = PATH_CLASS_ROOT.substring(0,
				PATH_CLASS_ROOT.length() - 16);

		public static  String DEFAULT_PASSWORD = "123456";

		// 问卷调查的背景图片的placecode
		public static  String QUESTIONBACKGROUND = "questionbackground";
		// 积分商城的背景图片的placecode
		public static  String INTE_BACK_GROUND = "intebackground";
		// 会员信息的背景图片的placecode
		public static  String MEMBER_BACK_GROUND = "memberbackground";
		// 积分扫描页
		public static  String POINT_SCAN = "PointScan";
		// 充电扫描页
		public static  String CHARGING_SCAN = "chargingScan";

		// 激活
		public static  String IFACTIVE_TRUE = "1";
		// 未激活
		public static  String IFACTIVE_FALSE = "0";

		// 已被购买
		public static  String IFBUY_TRUE = "1";
		// 未被购买
		public static  String IFBUY_FALSE = "0";

		// 已使用
		public static  String IFUSE_TRUE = "1";
		// 未使用
		public static  String IFUSE_FALSE = "0";

		// 已超期
		public static  String IFOVERDUE_TRUE = "1";
		// 未超期
		public static  String IFOVERDUE_FALSE = "0";


		// 粉丝的来源 = 充电
		public static  String MEMBER_SOURCE_BATT = "0";
		// 粉丝的来源 = 积分
		public static  String MEMBER_SOURCE_INTE = "1";

		// 微信关键字 = 模糊匹配
		public static  String WEIXIN_KEY_LIKE = "1";
		// 微信关键字 = 完全匹配
		public static  String WEIXIN_KEY_ALL = "0";

		// 返回文本
		public static  String WEIXIN_RESP_TEXT = "8";
		// 返回图文
		public static  String WEIXIN_RESP_TEXTIMG = "9";

		public static  String LOGGER_EXCEPTION = "exception";

		public static  String EMPTY = "";


		public  static Constants instance = new Constants();

		public static Constants getInstance() {
			return instance;
		}

		public Properties getProperties() {
			return properties;
		}

		public void setProperties(Properties properties) {
			this.properties = properties;
		}

		// 固特异官方
		public static  String ROLE_TYPE_ADMIN = "1";
		// 经销商
		public static  String ROLE_TYPE_CUSTOR = "3";

		// 粉丝分组
		public static  String GROUP_MEMBER = "1";
		// 经销商分组
		public static  String GROUP_AGENCY = "2";

		// 默认值
		public static  String DEFAULT_GROUP = "0";

		// 经销商 默认状态
		public static  String DEFAULT_AGENCY_STATUS = "0";
		// 管理员 默认状态
		public static  String DEFAULT_ADMIN_STATUS = "0";

		// 关注后自动回复
		public static  String FOCUS_AFTER_KEY_REPLAY = "2";
		// 未匹配关键字自动回复
		public static  String MATCH_NO_KEY_REPLAY = "4";

		// 经销商
		public static  String AGENCY_STATUS_AGENCY = "2";
		// 门店
		public static  String AGENCY_STATUS_STORE = "1";


		// 微信请求指令类型
		public static  String REQUEST_MSG = "0";
		// 微信响应指令类型
		public static  String RESPONSE_MSG = "1";
		
		

		// 微信菜单的文本回复
		public static  String WEIXIN_MENU_TXT = "txt";
		// 微信菜单的图文回复
		public static  String WEIXIN_MENU_ARTICLE = "article";
		// 微信菜单的图片回复
		public static  String WEIXIN_MENU_IMG = "img";
		// 微信菜单的链接地址
		public static  String WEIXIN_MENU_LINK = "link";
		// 微信菜单的语音
		public static  String WEIXIN_MENU_MUSIC = "audio";


		// 微信菜单的点击
		public static  String WEIXIN_MENU_CLICK = "click";
		// 微信菜单的view
		public static  String WEIXIN_MENU_VIEW = "view";

		/**
		 * @Fields DEFAULT_PLACE_IMG: 图片素材中的默认图片
		 */
		public static  String DEFAULT_PLACE_IMG = "/activity/shenye/images/nopic.jpg";
		/**
		 * 参数类别 雇主
		 */
		public static final String PARAMCATEGORY_EMPLOYER = "tick_employer";
		/**
		 * 参数key 佣金
		 */
		public static final String PARAMKEY_BROKERAGE = "brokerage";
		
		/**
		 * 参数类别 关注
		 */
		public static final String PARAMCATEGORY_ATTENTION = "attention";
		
		/**
		 * 参数类别 分享
		 */
		public static final String PARAMCATEGORY_SHARE = "share";
		/**
		 * 参数key 关注送嘀嗒币
		 */
		public static final String PARAMKEY_ATTENTIONCOIN = "attentioncoin";
		
		/**
		 * 参数key 分享送嘀嗒币
		 */
		public static final String PARAMKEY_SHARECOIN = "sharecoin";
		
		/**
		 * 参数key 推荐送嘀嗒币
		 */
		public static final String PARAMKEY_RECOMMENDCOIN = "recommendcoin";
		

		/**
		 * 参数类别 推荐
		 */
		public static final String PARAMCATEGORY_RECOMMEND = "recommend";

		public static final String SHARE_TITLE = "分享赠送嘀嗒币";
		public static final String SHARE_DESCRIPTION = "分享赠送嘀嗒币";
		
		public static final String DISTANCE_KEY="distance";
		
		public static final String PUBLISHTEXT_KEY_JOB="publishtextjob";
		
		public static final String PUBLISHTEXT_KEY_PROJECT="publishtextproject";
		
		/**
		 * 设置用户信息redis存储时间， 单位 ：天
		 */
		public static final String REDIS_TIME="7";
		/**
		 * 保存用户到session
		 */
		public static final String USER_SESSION = "USER_SESSION";
		
}
