package com.lr.weixin.backer.dao;

import java.util.List;
import java.util.Map;

public interface BatteryMapper {
	
	//通过粉丝昵称获取粉丝的openid
	String getOpenidByNickname(Map<String, Object> map) ;
	
	
	//更新用户第一条访问兑换记录
	void updateMemberFirstInteChange(Map<String, Object> data);
	//重置粉丝来源
	void resetMemberSource(Map<String, Object> data);
	//更新用户来源
	void updateMemberSource(Map<String, Object> data);
	Map<String, Object> checkSourceBattHistory(Map<String, Object> data);
	Map<String, Object> checkSourceInteGain(Map<String, Object> data);
	
	// 获得粉丝数量
	List<Map<String, Object>> getMemberList(Map<String, Object> pvjMap);

	// 获取粉丝数量
	int getMemberListNum(Map<String, Object> pvjMap);

	// 删除粉丝
	void deleteMember(Map<String, Object> pvjMap);

	// 获得粉丝
	Map<String, Object> getMember(Map<String, Object> pvMap);

	// 更新粉丝
	int updateMember(Map<String, Object> pvMap);

	// 关注
	void attentionMember(Map<String, Object> pvMap);

	// 取消关注
	void cancalMember(Map<String, Object> pvMap);

	// 粉丝插入
	void insertMember(Map<String, Object> pvMap);
	void updateNickName(Map<String, Object> pvMap);
	List<Map<String, Object>> getMemberOpenList();
	// 获得首页Banner列表
	List<Map<String, Object>> getMlBannerList();

	// 获得首页Banner数据
	Map<String, Object> getMlBanner();

	// 更新首页Banner数据
	void updateMlBanner(Map<String, Object> mlMap);

	// 插入首页Banner数据
	void insertMlBanner(Map<String, Object> mlMap);

	// 根据openid来判断
	Map<String, Object> getMemberByOpenid(Map<String, Object> pvMap);

	// 首页上的粉丝数量统计
	Integer getMemberNum();

	// 充电历史的数量
	Integer getBattHistoryNum();

	// 充电历史列表
	List<Map<String, Object>> getBattHistoryList(Map<String, Object> condition);

	// 插入充电记录
	public int insertBattHistory(Map<String, Object> paramMap);

	// 获得充电历史分页
	Integer getBattHistoryListNum(Map<String, Object> pvMap);

	// 深夜的士列表
	List<Map<String, Object>> getShenyeList(Map<String, Object> pvMap);

	// 深夜的士数量
	int getShenyeListNum(Map<String, Object> pvMap);

	// 获得单个深夜的士
	Map<String, Object> getShenye(Map<String, Object> pvMap);

	// 插入深夜数据
	int insertShenye(Map<String, Object> pvMap);

	// 更新深夜数据
	int updateShenye(Map<String, Object> pvMap);

	// 大宁音乐节列表
	List<Map<String, Object>> getDaningList(Map<String, Object> pvMap);

	// 大宁音乐节数量
	int getDaningListNum(Map<String, Object> pvMap);

	// 获得单个大宁音乐节
	Map<String, Object> getDaning(Map<String, Object> pvMap);

	// 插入大宁音乐节数据
	int insertDaning(Map<String, Object> pvMap);

	// 根据当天每个场次的数量
	List<Map<String, Object>> getDaningNumByDay(Map<String, Object> pvMap);

	// 大宁活动开关
	int getACSwitch(Map<String, Object> pvMap);// 大宁音乐节列表

	List<Map<String, Object>> getCDList(Map<String, Object> pvMap);

	// 大宁音乐节数量
	int getCDListNum(Map<String, Object> pvMap);

	// 获得单个大宁音乐节
	Map<String, Object> getCD(Map<String, Object> pvMap);

	// 插入大宁音乐节数据
	int insertCD(Map<String, Object> pvMap);

	// 根据当天每个场次的数量
	List<Map<String, Object>> getCDNumByDay(Map<String, Object> pvMap);
	//微信关键字，支持模糊匹配
	List<Map<String, Object>> getWeiXinKeyList(Map<String, Object> data);
	
	void insertTextKey(Map<String, Object> data) ;
	void updateTextKey(Map<String, Object> data) ;
	void deleteTextKey(Map<String, Object> data);
	List<Map<String, Object>> getTextKeyList(Map<String, Object> data) ;
	int getTextKeyListNum(Map<String,Object> data);
	int insertXingzuo(Map<String,Object> data);
	
	
	//	获取省市信息
	List<Map<String, Object>> getProvinceList();
	List<Map<String, Object>> getCityList();
	List<Map<String, Object>> getCityListByProvince(Map<String, Object> data);
	
	
	//Hello Message
	void insertHelloMessage(Map<String, Object> data);
	void deleteHelloMessage(Map<String, Object> data);
	void updateHelloMessage(Map<String, Object> data);
	Map<String, Object> getHelloMessage(Map<String, Object> data);
	
	// Message
	void insertMessage(Map<String, Object> data);
	void deleteMessage(Map<String, Object> data);
	void updateMessage(Map<String, Object> data);
	List<Map<String, Object>> getMessageList(Map<String, Object> data);
	Integer getMessageListNum(Map<String, Object> data);
	
	
	
	//粉丝标签管理
	void deleteMark(Map<String, Object> data);
	void insertMark(Map<String, Object> data);
	void updateMark(Map<String, Object> data);
	Map<String, Object> getMark(Map<String, Object> data);
	List<Map<String, Object>> getMarkList(Map<String, Object> data);
	Integer getMarkListNum(Map<String, Object> data);
	void deleteMemberMark(Map<String, Object> data);
	void insertMemberMark(Map<String, Object> data);

	
	// 获得粉丝数量
	List<Map<String, Object>> getMemberFenSiList(Map<String, Object> pvjMap);
	// 获取粉丝数量
	int getMemberFenSiListNum(Map<String, Object> pvjMap);
	
	List<Map<String, Object>> getweixinAllKey(Map<String, Object> data);

	Map<String, Object> getAreaLike(Map<String, Object> data);
	
}