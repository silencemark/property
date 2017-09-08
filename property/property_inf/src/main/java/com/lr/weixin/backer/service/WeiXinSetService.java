package com.lr.weixin.backer.service;

import java.util.List;
import java.util.Map;

public interface WeiXinSetService {

	// 素材图片 
	void deleteDTImg(Map<String, Object> data) ;
	void insertDTImg(Map<String, Object> data) ;
	void updateDTImg(Map<String, Object> data) ;
	Map<String, Object> getDTImg(Map<String, Object> data) ;
	List<Map<String, Object>> getDTImgList(Map<String, Object> data) ;
	int getDTImgListNum(Map<String,Object> data);
	
	// 图文素材 
	void deleteDTImgText(Map<String, Object> data) ;
	void insertDTImgText(Map<String, Object> data) ;
	void updateDTImgText(Map<String, Object> data) ;
	Map<String, Object> getDTImgText(Map<String, Object> data) ;
	/**
	 * @function:获取图文（包含单图文和多图文）
	 * @datetime:2014-12-29 下午06:29:45
	 * @Author: robin
	 * @param: @param data
	 * @return List<Map<String,Object>>
	 */
	List<Map<String, Object>> getDTImgTextList(Map<String, Object> data) ;
	int getDTImgTextListNum(Map<String,Object> data);
	boolean saveGraphicSingle(Map<String, Object> data);
	boolean saveDTImgTextMulti(String multiGraphic);
	List<Map<String, Object>> getDTImgTextItemList(Map<String, Object> data);
	void deleteDTImgTextItem(Map<String, Object> data) ;
	void updateDTImgTextItem(Map<String, Object> data);
	
	//微信粉丝
	List<Map<String, Object>> getAgencyMemberList(Map<String, Object> data);
	void deleteMember(Map<String, Object> data);
	Map<String, Object> getMember(Map<String, Object> data);
	Map<String,Object> getMemberView(Map<String, Object> data);
	Integer getMemberActivityNum(Map<String, Object> data);
	void insertMember(Map<String, Object> data);
	void updateNickName(Map<String, Object> data);
	List<Map<String, Object>> getMemberOpenList(Map<String, Object> data);
	void attentionMember(Map<String, Object> data);
	void updateMember(Map<String, Object> data);
	void cancalMember(Map<String, Object> data);
	List<Map<String, Object>> getMemberList(Map<String, Object> data);
	Integer getMemberListNum(Map<String, Object> data);
	void updateDefaultGroup(Map<String, Object> data);
	//批量分组
	void updategtygroup(Map<String, Object> data);
	
	// 获取省市信息
	List<Map<String, Object>> getProvinceList();

	List<Map<String, Object>> getCityListByProvince(Map<String, Object> data);
	
	//固特异分组
	void deleteGTYGroup(Map<String, Object> data);
	void insertGTYGroup(Map<String, Object> data);
	void updateGTYGroup(Map<String, Object> data);
	List<Map<String, Object>> getGTYGroupList(Map<String, Object> data);
	List<Map<String, Object>> getGTYGroupTJList(Map<String, Object> data);
	//其他分组
	void deleteDTGroup(Map<String, Object> data);
	void insertDTGroup(Map<String, Object> data);
	void updateDTGroup(Map<String, Object> data);
	List<Map<String, Object>> getDTGroupList(Map<String, Object> data);
	boolean initWeiXinGroup();
	boolean initWeiXinMember();
	boolean initWeiXinMemberGroup();
	List<Map<String, Object>> getDTGroupAgencyTJList(Map<String, Object> data);
	
	//信息群发数量设置
	void deleteSYNoticeSet(Map<String, Object> data);
	void insertSYNoticeSet(Map<String, Object> data);
	void updateSYNoticeSet(Map<String, Object> data);
	Map<String, Object> getSYNoticeSet(Map<String, Object> data);
	//信息群发
	void deleteSYNoticeSend(Map<String, Object> data);
	void insertSYNoticeSend(Map<String, Object> data);
	void updateSYNoticeSend(Map<String, Object> data);
	void updateSYNoticeSendByWX(Map<String, Object> data);
	List<Map<String, Object>> getSYNoticeSendList(Map<String, Object> data);
	Integer getSYNoticeSendListNum(Map<String,Object> data);
	Integer getCanSendNum(Map<String, Object> data);
	List<Map<String, Object>> getSYNoticeSendAuditList(Map<String, Object> data);
	Integer getSYNoticeSendAuditListNum(Map<String,Object> data);
	List<Map<String, Object>> getSYNoticeSendAuditSearchList(Map<String, Object> data);
	Integer getSYNoticeSendAuditSearchListNum(Map<String,Object> data);
	
	
	//关注的回复
	void deleteDTTemplate(Map<String, Object> data);
	void insertDTTemplate(Map<String, Object> data);
	void updateDTTemplate(Map<String, Object> data);
	Map<String, Object> getDTTemplate(Map<String, Object> data);
	List<Map<String, Object>> getDTTemplateList(Map<String, Object> data);
	
	//关键字
	void insertDTTextKeyBatch(Map<String, Object> data);
	void deleteDTTextKey(Map<String, Object> data);
	//获取规则对应的关键字的集合
	List<Map<String, Object>> getDTRuleTextKey(Map<String, Object> data);
	
	//自动回复规则
	void deleteDTKeyRule(Map<String, Object> data);
	void insertDTKeyRule(Map<String, Object> data);
	void updateDTKeyRule(Map<String, Object> data);
	void updateDTKeyRuleUseCount(Map<String, Object> data);
	void changeReplyKeyStatus(Map<String, Object> data);
	Map<String, Object> getDTKeyRuleDetail(Map<String, Object> data);
	List<Map<String, Object>> getDTKeyRuleList(Map<String, Object> data);
	Integer getDTKeyRuleListNum(Map<String, Object> data);
	
	//素材语音
	void deleteDTAudio(Map<String, Object> data);
	void insertDTAudio(Map<String, Object> data);
	void updateDTAudio(Map<String, Object> data);
	Map<String, Object> getDTAudio(Map<String, Object> data);
	List<Map<String, Object>> getDTAudioList(Map<String, Object> data);
	int getDTAudioListNum(Map<String, Object> data);
	
	//系统消息
	void deleteOSMessage(Map<String, Object> data);
	void insertOSMessage(Map<String, Object> data);
	void updateOSMessage(Map<String, Object> data);
	Map<String, Object> getOSMessage(Map<String, Object> data);
	List<Map<String, Object>> getOSMessageList(Map<String, Object> data);
	int getOSMessageListNum(Map<String, Object> data);
	/**
	 * @function:获取当前用户的未读消息的数量
	 * @datetime:2015-1-26 上午11:01:26
	 * @Author: robin
	 * @return int
	 */
	int getCurrentMessageNum();
	
	//粉丝车辆
	void deleteMemberCar(Map<String, Object> data);
	void insertMemberCar(Map<String, Object> data);
	void updateMemberCar(Map<String, Object> data);
	Map<String, Object> getMemberCar(Map<String, Object> data);
	List<Map<String, Object>> getMemberCarList(Map<String, Object> data);
	int getMemberCarListNum(Map<String, Object> data);
	
	//微信回复匹配出的内容
	Map<String, Object> getMsgByKey(Map<String, Object> data);
	
	Map<String, Object> getMaxSYNoticeSend();
	
	//特色服务
	List<Map<String, Object>> findServiceList(Map<String, Object> data);
	int findServiceListNum();
	void updateService(Map<String, Object> paramCondition);
	void insertService(Map<String, Object> paramCondition);
	
	
	Integer getAgencyCanSendNum(Map<String, Object> data);
	
	List<Map<String, Object>> getMemberListV2(Map<String, Object> data);
	Integer getMemberListNumV2(Map<String, Object> data);
	
	List<Map<String, Object>> getImgTextList(Map<String, Object> data);
	
	Map<String, Object> getImgMap(Map<String, Object> data);
}