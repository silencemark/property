package com.lr.weixin.backer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.labor.weixin.api.WeiXinGroupAPI;
import com.lr.backer.util.Constants;
import com.lr.backer.util.FileDeal;
import com.lr.backer.util.MapUtils;
import com.lr.backer.util.UserSession;
import com.lr.backer.util.WeiXinSetUtil;
import com.lr.weixin.backer.dao.WeiXinSetMapper;
import com.lr.weixin.backer.service.WeiXinSetService;
import com.lr.weixin.util.WeiXinUser;


public class WeiXinSetServiceImp implements WeiXinSetService {
	
	@Autowired
	private WeiXinSetMapper weiXinSetMapper;

	
	public void deleteDTImg(Map<String, Object> data) {
		this.weiXinSetMapper.deleteDTImg(data);
	}

	
	public int getDTImgListNum(Map<String, Object> data) {
		return this.weiXinSetMapper.getDTImgListNum(data);
	}

	
	public Map<String, Object> getDTImg(Map<String, Object> data) {
		return this.weiXinSetMapper.getDTImg(data);
	}

	
	public List<Map<String, Object>> getDTImgList(Map<String, Object> data) {
		return this.weiXinSetMapper.getDTImgList(data);
	}

	
	public void insertDTImg(Map<String, Object> data) {
		this.weiXinSetMapper.insertDTImg(data);
	}

	
	public void updateDTImg(Map<String, Object> data) {
		this.weiXinSetMapper.updateDTImg(data);
	}

	
	public void deleteDTImgText(Map<String, Object> data) {
		this.weiXinSetMapper.deleteDTImgTextItem(data);
		this.weiXinSetMapper.deleteDTImgText(data);
	}

	
	public Map<String, Object> getDTImgText(Map<String, Object> data) {
		return this.weiXinSetMapper.getDTImgTextItem(data);
	}

	/**
	 * @function:获取图文（包含单图文和多图文）
	 * @datetime:2014-12-29 下午06:29:45
	 * @Author: robin
	 * @param: @param data
	 * @return List<Map<String,Object>>
	 */
	
	public List<Map<String, Object>> getDTImgTextList(Map<String, Object> data) {
		List<Map<String, Object>> sourceList = this.weiXinSetMapper.getDTImgTextList(data);
		//1. 获取主表的所有id
		List<String> idList = new ArrayList<String>();
		if(null!=sourceList){
			for (Map<String, Object> map : sourceList) {
				idList.add(MapUtils.getString(map, "imgtextid"));
			}
		}
		//2. 查询子表并组装数据
		if(null!=idList && idList.size()>0){
			data = new HashMap<String, Object>();
			data.put("parentList", idList);
			List<Map<String, Object>> childList = this.weiXinSetMapper.getDTImgTextChildList(data);
			for (Map<String, Object> map : sourceList) {
				map.put("childList", getChildList(childList, MapUtils.getString(map, "imgtextid")));
			}
		}
		return sourceList;
	}
	
	private List<Map<String, Object>> getChildList(List<Map<String, Object>> dataList,String imgtextid){
		List<Map<String, Object>> childList = new ArrayList<Map<String,Object>>();
		if(null!=dataList && dataList.size()>0){
			for (Map<String, Object> map : dataList) {
				String t1 = MapUtils.getString(map, "imgtextid");
				if(null!=t1 && null!=imgtextid && t1.equals(imgtextid)){
					String imgtextlistid = MapUtils.getString(map, "imgtextlistid");
					map.put("imgtextlistid", WeiXinSetUtil.getSecretID(imgtextlistid));
					childList.add(map);
				}
			}
		}
		return childList;
	}
	
	
	public int getDTImgTextListNum(Map<String, Object> data) {
		return this.weiXinSetMapper.getDTImgTextListNum(data);
	}

	
	public void insertDTImgText(Map<String, Object> data) {
		this.weiXinSetMapper.insertDTImgText(data);
		String imgtextid = this.weiXinSetMapper.getMaxDTImgTextID(data);
		data.put("imgtextid", imgtextid);
		this.weiXinSetMapper.insertDTImgTextItem(data);
	}

	
	public void updateDTImgText(Map<String, Object> data) {
		//这里不需要更改主表的信息
		this.weiXinSetMapper.updateDTImgText(data);
	}
	
	//获取多图文的集合
	private List<Map<String, Object>> getMultiJsonList(String json){
		//多图文标志
		String imgtexttype = "2";
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		//"{'cover':{'title':'bbbb','author':'abadfasdf','coverimg':'/theme/grey/images/0.jpeg','show':true,'summary':'abadfasdf','article':'<p>ffaa<br/></p>','link':'ffff'},'sub':[{'title':'1111','author':'11111','coverimg':'http://localhost/upload/images/8278102221095109370.png','show':true,'summary':'','article':'<p>1111111</p>','link':'111111111'},{'title':'1111','author':'11111','coverimg':'http://localhost/upload/images/8278102221095109370.png','show':true,'summary':'','article':'<p>1111111</p>','link':'111111111'}]}";
		JSONObject jsonObject = JSONObject.fromObject(json);
		
		//1. 获取主图文
		Map<String, Object> map1 = (Map<String, Object>)JSONObject.toBean(jsonObject.getJSONObject("cover"),Map.class);
		map1.put("imgtexttype", imgtexttype);
		list.add(map1);
		
		//2. 获取子图文
		JSONArray jsonArray = jsonObject.getJSONArray("sub");
		if(null!=jsonArray && jsonArray.size()>0){
			for (int i=0,len=jsonArray.size();i<len;i++){
				JSONObject json2 = jsonArray.getJSONObject(i);
				Map<String, Object> map2 = (Map<String, Object>)JSONObject.toBean(json2,Map.class);
				map2.put("imgtexttype", imgtexttype);
				list.add(map2);
			}
		}
		return list ;
	}
	
	
	public boolean saveDTImgTextMulti(String multiGraphic) {
		//多图文保存时，应先删除所有图文，否则，已经删除的图文会继续存在
		boolean flag = false ;
		List<Map<String, Object>> dataList = this.getMultiJsonList(multiGraphic);
		if(null!=dataList && dataList.size()>0){
			int i = 0 ;
			String imgtextid = "";
			//1. 判断主表的数据是否存在
			Map<String, Object> t1 = dataList.get(0);
			imgtextid = MapUtils.getString(t1, "imgtextid");
			if(null==imgtextid || "".equals(imgtextid)){
//				t1.put("roletype", UserSession.getInstance().getCurrentUser().getRoletype());
//				t1.put("primaryid", UserSession.getInstance().getCurrentUser().getId());
				t1.put("createtime", new Date());
				this.weiXinSetMapper.insertDTImgText(t1);
				imgtextid = this.weiXinSetMapper.getMaxDTImgTextID(t1);
			}else {
				//清空所有图文
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("imgtextid", imgtextid);
				this.weiXinSetMapper.deleteDTImgTextItem(temp);
			}
			
			//2. 插入所有图文
			for (Map<String, Object> map : dataList) {
				map.put("imgtextid", imgtextid);
				this.weiXinSetMapper.insertDTImgTextItem(map);
				i++;
			}
			flag = i==dataList.size() ? true : false ;
		}
		// 保存多图文
		return flag;
	}

	
	public boolean saveGraphicSingle(Map<String, Object> data) {
		// 保存单图文
		boolean flag = false ;
		if(null!=data){
			//判断主表
			String imgtextid = MapUtils.getString(data, "imgtextid");
			if(null==imgtextid || "".equals(imgtextid)){ 
//				data.put("roletype", UserSession.getInstance().getCurrentUser().getRoletype());
//				data.put("primaryid", UserSession.getInstance().getCurrentUser().getId());
				data.put("createtime", new Date());
				this.weiXinSetMapper.insertDTImgText(data);
				imgtextid = this.weiXinSetMapper.getMaxDTImgTextID(data);
				data.put("imgtextid", imgtextid);
			}
			
			//判断明细表
			String imgtextlistid = MapUtils.getString(data, "imgtextlistid");
			if(null==imgtextlistid || "".equals(imgtextlistid)){
				this.weiXinSetMapper.insertDTImgTextItem(data);
			}else {
				this.weiXinSetMapper.updateDTImgTextItem(data);
			}
			flag = true ;
		}
		return flag;
	}

	
	public List<Map<String, Object>> getDTImgTextItemList(
			Map<String, Object> data) {
		return this.weiXinSetMapper.getDTImgTextItemList(data);
	}

	
	public void deleteDTImgTextItem(Map<String, Object> data) {
		this.weiXinSetMapper.deleteDTImgTextItem(data);
	}

	
	public void attentionMember(Map<String, Object> data) {
		this.weiXinSetMapper.attentionMember(data);
	}

	
	public void cancalMember(Map<String, Object> data) {
		this.weiXinSetMapper.cancalMember(data);
	}

	
	public void deleteMember(Map<String, Object> data) {
		this.weiXinSetMapper.deleteMember(data);
	}

	
	public Map<String, Object> getMember(Map<String, Object> data) {
		return this.weiXinSetMapper.getMember(data);
	}

	
	public List<Map<String, Object>> getMemberList(Map<String, Object> data) {
		return this.weiXinSetMapper.getMemberList(data);
	}

	
	public Integer getMemberListNum(Map<String, Object> data) {
		return this.weiXinSetMapper.getMemberListNum(data);
	}

	
	public List<Map<String, Object>> getMemberOpenList(Map<String, Object> data) {
		return this.weiXinSetMapper.getMemberOpenList(data);
	}

	
	public void insertMember(Map<String, Object> data) {
		this.weiXinSetMapper.insertMember(data);
	}

	
	public void updateMember(Map<String, Object> data) {
		this.weiXinSetMapper.updateMember(data);
	}

	
	public void updateNickName(Map<String, Object> data) {
		this.weiXinSetMapper.updateNickName(data);
	}

	
	public List<Map<String, Object>> getCityListByProvince(
			Map<String, Object> data) {
		return this.weiXinSetMapper.getCityListByProvince(data);
	}

	
	public List<Map<String, Object>> getProvinceList() {
		return this.weiXinSetMapper.getProvinceList();
	}

	
	public void deleteDTGroup(Map<String, Object> data) {
		this.weiXinSetMapper.deleteDTGroup(data);
	}

	
	public void deleteGTYGroup(Map<String, Object> data) {
		this.weiXinSetMapper.deleteGTYGroup(data);
	}

	
	public List<Map<String, Object>> getDTGroupList(Map<String, Object> data) {
		return this.weiXinSetMapper.getDTGroupList(data);
	}

	
	public List<Map<String, Object>> getGTYGroupList(Map<String, Object> data) {
		return this.weiXinSetMapper.getGTYGroupList(data);
	}
	
	
	public List<Map<String, Object>> getGTYGroupTJList(Map<String, Object> data) {
		return this.weiXinSetMapper.getGTYGroupTJList(data);
	}

	
	public void insertDTGroup(Map<String, Object> data) {
		this.weiXinSetMapper.insertDTGroup(data);
	}

	
	public void insertGTYGroup(Map<String, Object> data) {
		this.weiXinSetMapper.insertGTYGroup(data);
	}

	
	public void updateDTGroup(Map<String, Object> data) {
		this.weiXinSetMapper.updateDTGroup(data);
	}

	
	public void updateGTYGroup(Map<String, Object> data) {
		this.weiXinSetMapper.updateGTYGroup(data);
	}

	
	public boolean initWeiXinGroup() {
		//初始化所有分组和粉丝
		JSONObject groupJson = WeiXinGroupAPI.initAllGroup();
		if(groupJson.containsKey("groups")){
			JSONArray groupList = groupJson.getJSONArray("groups");
			int oknum = 0;
			if(null!=groupList && groupList.size()>0){
				for(int i=0,len=groupList.size();i<len;i++){
					JSONObject t1 = groupList.getJSONObject(i);
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("groupid", t1.get("id"));
					Integer count = this.weiXinSetMapper.getGTYGroupCount(data);
					data.put("groupname", t1.get("name"));
					data.put("grouptype", Constants.GROUP_MEMBER);
					if(null==count || count<=0){
						//需要插入
						this.weiXinSetMapper.insertGTYGroup(data);
						oknum++;
					}else {
						this.weiXinSetMapper.updateGTYGroup(data);
						oknum++;
					}
					//判断当前分组是否存在于当前系统中
				}
			return groupList.size()==oknum ;
			}
		}
		return false;
	}

	
	public boolean initWeiXinMember() {
		boolean flag = false ;
		this.weiXinSetMapper.deleteAllMember();
		JSONObject jsonObject = WeiXinUser.initAllUser(null);
		if(null!=jsonObject && jsonObject.containsKey("data")){
			JSONObject dataJson = jsonObject.getJSONObject("data");
			if(dataJson.containsKey("openid")){
				JSONArray jsonArray = dataJson.getJSONArray("openid");
				for(int i=0,len=jsonArray.size();i<len;i++){
					String openid = jsonArray.get(i).toString();
					JSONObject userJson = WeiXinUser.getUser(openid);
					System.out.println(userJson);
					Map<String,Object> member = new HashMap<String, Object>();
					member.put("openid", openid);
					if(null!=userJson && userJson.containsKey("subscribe")){
						String subscribe = userJson.getString("subscribe");
						if("1".equals(subscribe)){
							//关注
							member.put("ifattention", "1");
							member.put("nickname", userJson.get("nickname"));
							member.put("headimg", userJson.get("headimg"));
							member.put("sex", userJson.get("sex"));
							member.put("province", userJson.get("province"));
							member.put("city", userJson.get("city"));
							member.put("gaintime", WeiXinUser.getSubscribeTime(userJson.get("subscribe_time")));
						}else {
							//未关注
							member.put("ifattention", "0");
						}
						this.weiXinSetMapper.insertMember(member);
					}
				}
				flag = true ;
			}
		}
		return flag;
	}

	
	public boolean initWeiXinMemberGroup() {
		List<Map<String, Object>> memberList  = this.weiXinSetMapper.getMemberList(null);
		if(null!=memberList && !memberList.isEmpty()){
			for (Map<String, Object> map : memberList) {
				String gtygroup = WeiXinGroupAPI.getMemberGroup(MapUtils.getString(map, "openid"));
				if(null!=gtygroup && !"".equals(gtygroup)){
					map.put("gtygroup", gtygroup);
					this.weiXinSetMapper.updateMember(map);
				}
			}
		}
		return true;
	}

	
	public List<Map<String, Object>> getDTGroupAgencyTJList(Map<String, Object> data) {
		return this.weiXinSetMapper.getDTGroupAgencyTJList(data);
	}

	
	public void deleteSYNoticeSend(Map<String, Object> data) {
		this.weiXinSetMapper.deleteSYNoticeSend(data);
	}

	
	public void deleteSYNoticeSet(Map<String, Object> data) {
		this.weiXinSetMapper.deleteSYNoticeSet(data);
	}

	
	public List<Map<String, Object>> getSYNoticeSendList(
			Map<String, Object> data) {
		List<Map<String, Object>> dataList = this.weiXinSetMapper.getSYNoticeSendList(data);
		if(null!=dataList){
			for (Map<String, Object> map : dataList) {
				if(null!=map && !map.isEmpty()){
					String otherreason = MapUtils.getString(map, "otherreason");
					map.put("otherreasonshort", FileDeal.subStringLengthMore(otherreason, 20));
				}
			}
		}
		return dataList ;
	}

	
	public Integer getSYNoticeSendListNum(Map<String, Object> data) {
		return this.weiXinSetMapper.getSYNoticeSendListNum(data);
	}

	
	public Map<String, Object> getSYNoticeSet(Map<String, Object> data) {
		return this.weiXinSetMapper.getSYNoticeSet(data);
	}

	
	public void insertSYNoticeSend(Map<String, Object> data) {
		this.weiXinSetMapper.insertSYNoticeSend(data);
	}

	
	public void insertSYNoticeSet(Map<String, Object> data) {
		this.weiXinSetMapper.insertSYNoticeSet(data);
	}

	
	public void updateSYNoticeSend(Map<String, Object> data) {
		this.weiXinSetMapper.updateSYNoticeSend(data);
	}

	
	public void updateSYNoticeSet(Map<String, Object> data) {
		this.weiXinSetMapper.updateSYNoticeSet(data);
	}

	
	public Integer getCanSendNum(Map<String, Object> data) {
		return this.weiXinSetMapper.getCanSendNum(data);
	}

	
	public void updateDefaultGroup(Map<String, Object> data) {
		this.weiXinSetMapper.updateDefaultGroup(data);
	}

	
	public Map<String, Object> getMemberView(Map<String, Object> data) {
		return this.weiXinSetMapper.getMemberView(data);
	}

	
	public Integer getMemberActivityNum(Map<String, Object> data) {
		return this.weiXinSetMapper.getMemberActivityNum(data);
	}

	
	public void deleteDTTemplate(Map<String, Object> data) {
		this.weiXinSetMapper.deleteDTTemplate(data);
	}

	
	public Map<String, Object> getDTTemplate(Map<String, Object> data) {
		return this.weiXinSetMapper.getDTTemplate(data);
	}

	
	public List<Map<String, Object>> getDTTemplateList(Map<String, Object> data) {
		return this.weiXinSetMapper.getDTTemplateList(data);
	}

	
	public void insertDTTemplate(Map<String, Object> data) {
		this.weiXinSetMapper.insertDTTemplate(data);
	}

	
	public void updateDTTemplate(Map<String, Object> data) {
		this.weiXinSetMapper.updateDTTemplate(data);
	}

	
	public void deleteDTKeyRule(Map<String, Object> data) {
		//删除关键字表
		this.weiXinSetMapper.deleteDTTextKey(data);
		//删除规则表
		this.weiXinSetMapper.deleteDTKeyRule(data);
	}

	
	public void deleteDTTextKey(Map<String, Object> data) {
		this.weiXinSetMapper.deleteDTTextKey(data);
	}

	
	public List<Map<String, Object>> getDTKeyRuleList(Map<String, Object> data) {
		return this.weiXinSetMapper.getDTKeyRuleList(data);
	}

	
	public Integer getDTKeyRuleListNum(Map<String, Object> data) {
		return this.weiXinSetMapper.getDTKeyRuleListNum(data);
	}

	
	public List<Map<String, Object>> getDTRuleTextKey(Map<String, Object> data) {
		return this.weiXinSetMapper.getDTRuleTextKey(data);
	}

	
	public void insertDTKeyRule(Map<String, Object> data) {
		data.put("createtime", new Date());
		//插入规则表
		this.weiXinSetMapper.insertDTKeyRule(data);
		//获取ID
		Integer ruleid = this.weiXinSetMapper.getDTKeyRuleMaxID();
		if(null!=data && data.containsKey("textKeyList")){
			//遍历将id存储到关键字表
			List<Map<String, Object>> textKeyList = (List<Map<String,Object>>)data.get("textKeyList");
			if(null!=textKeyList && textKeyList.size()>0){
				for (Map<String, Object> map : textKeyList) {
					map.put("ruleid", ruleid);
				}
				this.weiXinSetMapper.insertDTTextKeyBatch(data);
			}
		}
	}

	
	public void insertDTTextKeyBatch(Map<String, Object> data) {
		this.weiXinSetMapper.insertDTTextKeyBatch(data);
	}
	
	
	public void updateDTKeyRuleUseCount(Map<String, Object> data) {
		this.weiXinSetMapper.updateDTKeyRule(data);
	}

	
	public void updateDTKeyRule(Map<String, Object> data) {
		//更新规则表
		this.weiXinSetMapper.updateDTKeyRule(data);
		//删除关键字表
		this.weiXinSetMapper.deleteDTTextKey(data);
		//批量插入关键字
		String ruleid = MapUtils.getString(data, "ruleid");
		if(null!=ruleid && !"".equals(ruleid)){
			List<Map<String, Object>> textKeyList = (List<Map<String,Object>>)data.get("textKeyList");
			if(null!=textKeyList && textKeyList.size()>0){
				for (Map<String, Object> map : textKeyList) {
					map.put("ruleid", ruleid);
				}
				data.put("textKeyList", textKeyList);
			}
		}
		this.weiXinSetMapper.insertDTTextKeyBatch(data);
	}
	
	
	public void changeReplyKeyStatus(Map<String, Object> data) {
		this.weiXinSetMapper.updateDTKeyRule(data);
	}

	
	public Map<String, Object> getDTKeyRuleDetail(Map<String, Object> data) {
		Map<String, Object> temp = this.weiXinSetMapper.getDTKeyRuleDetail(data);
		if(null!=temp && !temp.isEmpty()){
			//查询该规则的关键字列表
			List<Map<String, Object>> textKeyList = this.weiXinSetMapper.getDTRuleTextKey(data);
			temp.put("textKeyList", textKeyList);
		}
		return temp;
	}

	
	public List<Map<String, Object>> getSYNoticeSendAuditList(
			Map<String, Object> data) {
		List<Map<String, Object>> dataList = this.weiXinSetMapper.getSYNoticeSendAuditList(data);
		if(null!=dataList){
			for (Map<String, Object> map : dataList) {
				if(null!=map && !map.isEmpty()){
					String msgtypekey = MapUtils.getString(map, "msgtypekey");
					if(null!=msgtypekey && "article".equals(msgtypekey)){
						//图文
						String articleids = MapUtils.getString(map, "articleids");
						List<String> articleList = new ArrayList<String>();
						if(null!=articleids && !"".equals(articleids)){
							String[] articleidArray = articleids.split(",") ;
							if(null!=articleidArray && articleidArray.length>0){
								for (String articleid : articleidArray) {
									articleid = WeiXinSetUtil.getSecretID(articleid) ;
									articleList.add(articleid);
								}
							}
						}
						//============
						map.put("articleList", articleList);
						
					}
				}
			}
		}
		return dataList ;
	}
	
	
	public List<Map<String, Object>> getSYNoticeSendAuditSearchList(
			Map<String, Object> data) {
		List<Map<String, Object>> dataList = this.weiXinSetMapper.getSYNoticeSendAuditSearchList(data);
		if(null!=dataList){
			for (Map<String, Object> map : dataList) {
				if(null!=map && !map.isEmpty()){
					String content = MapUtils.getString(map, "content");
					String otherreason = MapUtils.getString(map, "otherreason");
					map.put("contentshort", FileDeal.subStringLengthMore(content, 20));
					map.put("otherreasonshort", FileDeal.subStringLengthMore(otherreason, 20));
					
					String msgtypekey = MapUtils.getString(map, "msgtypekey");
					if(null!=msgtypekey && "article".equals(msgtypekey)){
						//图文
						String articleids = MapUtils.getString(map, "articleids");
						List<String> articleList = new ArrayList<String>();
						if(null!=articleids && !"".equals(articleids)){
							String[] articleidArray = articleids.split(",") ;
							if(null!=articleidArray && articleidArray.length>0){
								for (String articleid : articleidArray) {
									articleid = WeiXinSetUtil.getSecretID(articleid) ;
									articleList.add(articleid);
								}
							}
						}
						//============
						map.put("articleList", articleList);
						
					}
				}
			}
		}
		return dataList ;
	}

	
	public Integer getSYNoticeSendAuditSearchListNum(Map<String, Object> data) {
		return this.weiXinSetMapper.getSYNoticeSendAuditSearchListNum(data);
	}

	
	
	public Integer getSYNoticeSendAuditListNum(Map<String, Object> data) {
		return this.weiXinSetMapper.getSYNoticeSendAuditListNum(data);
	}

	
	public void updateDTImgTextItem(Map<String, Object> data) {
		this.weiXinSetMapper.updateDTImgTextItem(data);
	}

	
	public void deleteDTAudio(Map<String, Object> data) {
		this.weiXinSetMapper.deleteDTAudio(data);
	}

	
	public Map<String, Object> getDTAudio(Map<String, Object> data) {
		return this.weiXinSetMapper.getDTAudio(data);
	}

	
	public List<Map<String, Object>> getDTAudioList(Map<String, Object> data) {
		return this.weiXinSetMapper.getDTAudioList(data);
	}

	
	public int getDTAudioListNum(Map<String, Object> data) {
		return this.weiXinSetMapper.getDTAudioListNum(data);
	}

	
	public void insertDTAudio(Map<String, Object> data) {
		this.weiXinSetMapper.insertDTAudio(data);
	}

	
	public void updateDTAudio(Map<String, Object> data) {
		this.weiXinSetMapper.updateDTAudio(data);
	}

	
	public List<Map<String, Object>> getAgencyMemberList(
			Map<String, Object> data) {
		return this.weiXinSetMapper.getAgencyMemberList(data);
	}

	
	public void deleteOSMessage(Map<String, Object> data) {
		this.weiXinSetMapper.deleteOSMessage(data);
	}

	
	public Map<String, Object> getOSMessage(Map<String, Object> data) {
		return this.weiXinSetMapper.getOSMessage(data);
	}

	
	public List<Map<String, Object>> getOSMessageList(Map<String, Object> data) {
		return this.weiXinSetMapper.getOSMessageList(data);
	}

	
	public int getOSMessageListNum(Map<String, Object> data) {
		return this.weiXinSetMapper.getOSMessageListNum(data);
	}

	
	public void insertOSMessage(Map<String, Object> data) {
		this.weiXinSetMapper.insertOSMessage(data);
	}

	
	public void updateOSMessage(Map<String, Object> data) {
		this.weiXinSetMapper.updateOSMessage(data);
	}

	
	public int getCurrentMessageNum() {
		Map<String, Object> data = new HashMap<String, Object>();
		if(null == UserSession.getInstance().getCurrentUser()){
			return 0 ;
		}
		data.put("receiveroletype", UserSession.getInstance().getCurrentUser().getRoletype()) ;
		data.put("receiveprimaryid", UserSession.getInstance().getCurrentUser().getId());
		data.put("readstatus", "0");
		return this.weiXinSetMapper.getOSMessageListNum(data);
	}

	
	public void updateSYNoticeSendByWX(Map<String, Object> data) {
		this.weiXinSetMapper.updateSYNoticeSendByWX(data);
	}

	
	public void deleteMemberCar(Map<String, Object> data) {
		this.weiXinSetMapper.deleteMemberCar(data);
	}

	
	public Map<String, Object> getMemberCar(Map<String, Object> data) {
		return this.weiXinSetMapper.getMemberCar(data);
	}

	
	public List<Map<String, Object>> getMemberCarList(Map<String, Object> data) {
		return this.weiXinSetMapper.getMemberCarList(data);
	}

	
	public void insertMemberCar(Map<String, Object> data) {
		this.weiXinSetMapper.insertMemberCar(data);
	}

	
	public void updateMemberCar(Map<String, Object> data) {
		this.weiXinSetMapper.updateMemberCar(data);
	}

	
	public int getMemberCarListNum(Map<String, Object> data) {
		return this.weiXinSetMapper.getMemberCarListNum(data);
	}

	
	public Map<String, Object> getMsgByKey(Map<String, Object> data) {
		return this.weiXinSetMapper.getMsgByKey(data);
	}

	
	public Map<String, Object> getMaxSYNoticeSend() {
		return this.weiXinSetMapper.getMaxSYNoticeSend();
	} 

	
	public List<Map<String, Object>> findServiceList(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.weiXinSetMapper.findServiceList(data);
	}

	
	public int findServiceListNum() {
		// TODO Auto-generated method stub
		return this.weiXinSetMapper.findServiceListNum();
	}

	
	public void insertService(Map<String, Object> paramCondition) {

		this.weiXinSetMapper.insertService(paramCondition);
		
	}

	
	public void updateService(Map<String, Object> paramCondition) {
		this.weiXinSetMapper.updateService(paramCondition);
		
	}

	
	public void updategtygroup(Map<String, Object> data) {
		this.weiXinSetMapper.updategtygroup(data);
		
	}

	
	public Integer getAgencyCanSendNum(Map<String, Object> data) {
		return this.weiXinSetMapper.getAgencyCanSendNum(data);
	}

	
	public Integer getMemberListNumV2(Map<String, Object> data) {
		return this.weiXinSetMapper.getMemberListNumV2(data);
	}

	
	public List<Map<String, Object>> getMemberListV2(Map<String, Object> data) {
		return this.weiXinSetMapper.getMemberListV2(data);
	}


	@Override
	public List<Map<String, Object>> getImgTextList(Map<String, Object> data) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> sourceList= this.weiXinSetMapper.getImgTextList(data);
		if(sourceList!= null && sourceList.size()==1){
			String imgtextlistid =sourceList.get(0).get("imgtextlistid")+"";
			sourceList.get(0).put("imgtextlistid", WeiXinSetUtil.getSecretID(imgtextlistid));
		}
		return sourceList;
	}


	@Override
	public Map<String, Object> getImgMap(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.weiXinSetMapper.getImgMap(data);
	} 
	
}