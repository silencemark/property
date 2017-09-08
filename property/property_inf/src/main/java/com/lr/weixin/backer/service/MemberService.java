package com.lr.weixin.backer.service;

import java.util.List;
import java.util.Map;

public interface MemberService {
	Map<String, Object> getMemberByExtendid(Map<String, Object> data);

	List<Map<String,Object>> getMemberList(Map<String,Object> map);
	
	int  getMemberListNum(Map<String,Object> map);
	
    void insertExtend(Map<String,Object> map);
	
	void updateExtend(Map<String,Object> map);
	
	void updateExtendPrise(Map<String,Object> map);
	
	void insertCoinRecord(Map<String,Object> map);
	
	void givesharecoin(Map<String,Object> map);
	
	
	
	// 会员设置
	public List<Map<String, Object>> findMemberList(Map<String, Object> pvjMap);

	public int findMemberListNum(Map<String, Object> pvjMap);

	public void deleteMember(Map<String, Object> pvjMap);

	public Map<String, Object> findMember(Map<String, Object> pvMap);

	public void updateMember(Map<String, Object> pvMap);

	public String insertMember(Map<String, Object> pvMap);

	public void isValidCode(Map<String, Object> pvMap);
	
	Map<String, Object> getMemberInfo(Map<String, Object> map);
	
	public Integer getMemberid(String openId);
	public Integer getIfRegisted(String openId);
	
	Map<String, Object> getMemberByOpenid(String openId);
	Map<String, Object> getAllMemberByOpenid(String openId);
	
	Integer getPoint(Map<String, Object> map);
	
	int deductPoint(Map<String, Object> map);

    void cancelAttention(Map<String, Object> map);
    void updateMemberAllInfo(Map<String, Object> map);

	public List<Map<String, Object>> getInteSourceList(String INTE_WAY_REGISTER);

	public void insertInteGain(Map<String, Object> param);

	public void gainPoint(Map<String, Object> param);
	
	void saveMember(Map<String, Object> pvMap);
	
	/**
	 * 新增用户图片（身份证照和个人照片） 中间表
	 */
	void insertMemberImg(Map<String, Object> map);
	/**
	 * 删除用户图片
	 */
	void updateMemberImg(Map<String, Object> map);
	/**
	 *  查询用户图片（身份证照和个人照） 
	 * @param pvjMap
	 * @return
	 */
	List<Map<String, Object>> getMemberImgList(Map<String, Object> pvjMap);
	/**
	 * 插入图片
	 * @param pvjMap
	 */
	void insertimg(Map<String, Object> pvjMap);

	Map<String, Object> getAreaInfo(Map<String, Object> data);
	
	/**
	 * 手机验证码登陆 新增用户
	 * @param data
	 */
	void addMember(Map<String, Object> data);
	
	/**
	 *  查询用户订单保险 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getInsuranceList(Map<String, Object> map);
	/**
	 * 查询用户订单保险总记录数
	 * @param map
	 * @return
	 */
	int getInsuranceListNum(Map<String, Object> map);
	
	/**
	 * 修改用户保险状态 
	 * @param map
	 */
	void updateInsurance(Map<String, Object> map);
	
	/**
	 * 新增用户统计
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getMemberCount(Map<String, Object> map);
	
	/**
	 * 新增用户统计按周
	 * @param map
	 * @return
	 */
	int getMemberCountByWeek(Map<String, Object> map);
	
	/**
	 * 新增用户统计按年
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getMemberCountByYear(Map<String, Object> map);
	
	/**
	 * 删除 用户图片
	 * @param map
	 */
	void deleteMemberImg(Map<String, Object> map);

	/**
	 * 获取订单完成后所有抢单成功的工人信息
	 * @param order
	 */
	List<Map<String, Object>> geMemberOfComplete(Map<String, Object> order);
	
	Map<String, Object> getAgencyInfo(Map<String, Object> data);
}
