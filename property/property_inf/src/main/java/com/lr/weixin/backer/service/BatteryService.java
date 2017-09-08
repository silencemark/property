package com.lr.weixin.backer.service;

import java.util.List;
import java.util.Map;

public interface BatteryService {
	
	//通过粉丝昵称获取粉丝的openid
	String getOpenidByNickname(Map<String, Object> map) ;

	
	//更新用户第一条访问兑换记录
	boolean updateMemberFirstInteChange(String openid);
	//更新用户来源
	void updateMemberSource(String openid,String targetsource);

	// 会员设置
	public List<Map<String, Object>> findMemberList(Map<String, Object> pvMap);

	public int findMemberListNum(Map<String, Object> pvMap);

	public void deleteMember(Map<String, Object> pvMap);

	public Map<String, Object> findMember(Map<String, Object> pvMap);

	public void updateMember(Map<String, Object> pvMap);

	public void cancalMember(Map<String, Object> pvMap);

	public void insertMember(Map<String, Object> pvMap);

	void updateNickName(Map<String, Object> pvMap);

	List<Map<String, Object>> getMemberOpenList();

	public void isValidCode(Map<String, Object> pvMap);

	public List<Map<String, Object>> getMlBannerList();

	public Map<String, Object> getMlBanner();

	public void updateMlBanner(Map<String, Object> mlMap);

	public Integer getMemberNum();

	public Integer getBattHistoryNum();

	public List<Map<String, Object>> getBattHistoryList(
			Map<String, Object> paramMap);

	public Integer getBattHistoryListNum(Map<String, Object> paramMap);

	public void insertBattHistory(Map<String, Object> pvMap);

	// 深夜的士
	public List<Map<String, Object>> getShenyeList(Map<String, Object> pvMap);

	public int getShenyeListNum(Map<String, Object> pvMap);

	public Map<String, Object> getShenye(Map<String, Object> pvMap);

	public void insertShenye(Map<String, Object> pvMap);

	public void insertXingzuo(Map<String, Object> pvMap);

	public void updateShenye(Map<String, Object> pvMap);

	// 大宁音乐节
	public List<Map<String, Object>> getDaningList(Map<String, Object> pvMap);

	public int getDaningListNum(Map<String, Object> pvMap);

	public Map<String, Object> getDaning(Map<String, Object> pvMap);

	public void insertDaning(Map<String, Object> pvMap);

	public int getDaningNumByDay(Map<String, Object> pvMap);

	// 大宁开关
	public boolean getDaningSwtich(Map<String, Object> pvMap);

	// 抢CD
	public List<Map<String, Object>> getCDList(Map<String, Object> pvMap);

	public int getCDListNum(Map<String, Object> pvMap);

	public Map<String, Object> getCD(Map<String, Object> pvMap);

	public void insertCD(Map<String, Object> pvMap);

	public int getCDNumByDay(Map<String, Object> pvMap);

	// CD开关
	public boolean getCDSwtich(Map<String, Object> pvMap);

	void insertTextKey(Map<String, Object> data);

	void updateTextKey(Map<String, Object> data);

	void deleteTextKey(Map<String, Object> data);

	//微信关键字，支持模糊匹配
	List<Map<String, Object>> getWeiXinKeyList(Map<String, Object> data);
	
	List<Map<String, Object>> getTextKeyList(Map<String, Object> data);

	int getTextKeyListNum(Map<String, Object> data);


	// 获取省市信息
	List<Map<String, Object>> getProvinceList();

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
	void updateMemberMark(String memberid,List<Map<String, Object>> markList);
	

	// 获得粉丝数量
	List<Map<String, Object>> getMemberFenSiList(Map<String, Object> pvjMap);
	// 获取粉丝数量
	int getMemberFenSiListNum(Map<String, Object> pvjMap);

	List<Map<String, Object>> getweixinAllKey(Map<String, Object> data);
	
	Map<String, Object> getAreaLike(Map<String, Object> data);
	
}
