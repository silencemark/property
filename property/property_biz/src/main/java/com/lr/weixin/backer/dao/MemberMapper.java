package com.lr.weixin.backer.dao;

import java.util.List;
import java.util.Map;

public interface MemberMapper {
	Map<String, Object> getMemberByExtendid(Map<String, Object> data);
	
	int getShareCount(Map<String, Object> data);
	
    void insertExtend(Map<String,Object> map);
	
	void updateExtend(Map<String,Object> map);
	
	void updateExtendPrise(Map<String,Object> map);
	
	
	
	// 会员相关
	List<Map<String, Object>> getMemberList(Map<String, Object> pvjMap);

	int getMemberListNum(Map<String, Object> pvjMap);

	void deleteMember(Map<String, Object> pvjMap);

	Map<String, Object> getMember(Map<String, Object> pvMap);

	void updateMember(Map<String, Object> pvMap);

	void insertMember(Map<String, Object> pvMap);
	
	int updateMemberV2(Map<String, Object> pvjMap);

	Map<String, Object> getMemberid(Map<String, Object> data);

	Integer getIfRegisted(Map<String, Object> param);

	Map<String, Object> getMemberInfo(Map<String, Object> map);

	Map<String, Object> getAllMemberByOpenid(Map<String, Object> map);

	Map<String, Object> getMemberByOpenid(Map<String, Object> map);

	Integer getPoint(Map<String, Object> map);

	int deductPoint(Map<String, Object> map);

	void cancelAttention(Map<String, Object> map);

	void updateMemberAllInfo(Map<String, Object> map);

	void gainPoint(Map<String, Object> param);
	
	void insertOwner(Map<String, Object> data);

	void insertCoinRecord(Map<String, Object> map);
	
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
	 * 新增用户统计按天
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
	 * 基本订单ID查询订单完成抢单成功的会员信息
	 * @param order
	 * @return
	 */
	List<Map<String, Object>> geMemberOfComplete(Map<String, Object> order);
	
	Map<String, Object> getAgencyInfo(Map<String, Object> data);
	
}
