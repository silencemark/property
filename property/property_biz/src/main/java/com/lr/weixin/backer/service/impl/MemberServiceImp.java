package com.lr.weixin.backer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lr.backer.dao.SystemMapper;
import com.lr.backer.util.Constants;
import com.lr.weixin.backer.dao.MemberMapper;
import com.lr.weixin.backer.service.MemberService;


public class MemberServiceImp implements MemberService {
	
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private SystemMapper systemMapper;


	// 会员设置
	
	public List<Map<String, Object>> findMemberList(Map<String, Object> pvjMap) {

		List<Map<String, Object>> listMap = this.memberMapper
				.getMemberList(pvjMap);
		return listMap;
	}

	public int findMemberListNum(Map<String, Object> pvjMap) {
		int num = this.memberMapper.getMemberListNum(pvjMap);
		return num;
	}

	
	public void deleteMember(Map<String, Object> pvjMap) {
		memberMapper.deleteMember(pvjMap);

	}

	
	public Map<String, Object> findMember(Map<String, Object> pvMap) {
		Map<String, Object> map = this.memberMapper.getMember(pvMap);
		return map;
	}

	
	public void updateMember(Map<String, Object> pvMap) {
		this.memberMapper.updateMember(pvMap);
	}

	
	public String insertMember(Map<String, Object> pvMap) {
		
		//添加到用户表
		String memberid= UUID.randomUUID().toString().replace("-", "");
		pvMap.put("memberid",memberid);
		pvMap.put("createtime", new Date());
		pvMap.put("lasttime", new Date());
		this.memberMapper.insertMember(pvMap);
		//添加用户扩展表
		Map<String, Object> userkuo=new HashMap<String, Object>();
		userkuo.put("extendid", UUID.randomUUID().toString().replace("-", ""));
		userkuo.put("userid", pvMap.get("memberid"));
		userkuo.put("source", pvMap.get("source"));
		userkuo.put("sourcetype", pvMap.get("sourcetype"));
		userkuo.put("cityid", pvMap.get("cityid"));
		userkuo.put("provinceid", pvMap.get("provinceid"));
		userkuo.put("createtime", new Date());
		userkuo.put("sourcepid", pvMap.get("sourcepid"));
		userkuo.put("origin", "0");
		this.memberMapper.insertExtend(userkuo);
		String source = (String) pvMap.get("source");
		if(StringUtils.isNotBlank(source)){
			this.saverecommendcoin(source);
		}
		this.saveattentioncoin(memberid);
		return null;
	}


	private void saveattentioncoin(String source) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("pcategory", Constants.PARAMCATEGORY_ATTENTION);
		paramMap.put("pkey", Constants.PARAMKEY_ATTENTIONCOIN);
		String tickcoin = "";
		String pdesc = "";
		List<Map<String, Object>> params = this.systemMapper.getParams(paramMap);
		if (params != null && params.size() > 0) {
			 tickcoin =  (String) params.get(0).get("pvalue");
			 pdesc = (String) params.get(0).get("pdesc");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userid", source);
		map.put("tickcoin", tickcoin);
		this.updateExtend(map);
		map.put("title", pdesc);
		map.put("description", pdesc);
		map.put("income_userid", source);
		map.put("amount", tickcoin);
		this.insertCoinRecord(map);
	}
	
	private void saverecommendcoin(String source) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("pcategory", Constants.PARAMCATEGORY_RECOMMEND);
		paramMap.put("pkey", Constants.PARAMKEY_RECOMMENDCOIN);
		String tickcoin = "";
		String pdesc = "";
		List<Map<String, Object>> params = this.systemMapper.getParams(paramMap);
		if (params != null && params.size() > 0) {
			 tickcoin =  (String) params.get(0).get("pvalue");
			 pdesc = (String) params.get(0).get("pdesc");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userid", source);
		map.put("tickcoin", tickcoin);
		this.updateExtend(map);
		map.put("title", pdesc);
		map.put("description", pdesc);
		map.put("income_userid", source);
		map.put("amount", tickcoin);
		this.insertCoinRecord(map);
		
	}

	public void isValidCode(Map<String, Object> pvMap) {
		// TODO Auto-generated method stub

	}

	
	public Map<String, Object> getMemberInfo(Map<String, Object> map) {
		return this.memberMapper.getMemberInfo(map);
	}

	
	public Integer getMemberid(String openId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("openid", openId);
		Map<String, Object> map = memberMapper.getMemberid(param);
		return null == map ? 0 : Integer.parseInt(map.get("memberid") + "");
	}

	
	public Integer getIfRegisted(String openId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("openid", openId);
		return memberMapper.getIfRegisted(param);
	}

	
	public Map<String, Object> getMemberByOpenid(String openId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("openid", openId);
		return memberMapper.getMemberByOpenid(param);
	}

	
	public Integer getPoint(Map<String, Object> map) {
		return this.memberMapper.getPoint(map);
	}

	
	public int deductPoint(Map<String, Object> map) {
		return this.memberMapper.deductPoint(map);
	}

	
	public void cancelAttention(Map<String, Object> map) {
		memberMapper.cancelAttention(map);
	}

	
	public void updateMemberAllInfo(Map<String, Object> map) {
		memberMapper.updateMemberAllInfo(map);
	}

	
	public Map<String, Object> getAllMemberByOpenid(String openId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("openid", openId);
		return memberMapper.getAllMemberByOpenid(param);
	}

	
	public void gainPoint(Map<String, Object> param) {
		memberMapper.gainPoint(param);
	}

	
	public void saveMember(Map<String, Object> pvMap) {
		if (null == pvMap) {
			return;
		}
		String openid = null != pvMap.get("openid") ? pvMap.get("openid")
				.toString() : "";
		if (null == openid || "".equals(openid)) {
			return;
		}
		Map<String, Object> map = this.memberMapper.getMember(pvMap);
		if (null == map || map.isEmpty()) {
			// 不存在该粉丝
			this.memberMapper.insertMember(pvMap);
		} else {
			// 存在该粉丝
			this.memberMapper.updateMemberV2(pvMap);
		}
	}

	
	public List<Map<String, Object>> getInteSourceList(String INTE_WAY_REGISTER) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void insertInteGain(Map<String, Object> param) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public List<Map<String, Object>> getMemberList(Map<String, Object> map) {
		
		return this.memberMapper.getMemberList(map);
	}

	@Override
	public int getMemberListNum(Map<String, Object> map) {
		 
		return this.memberMapper.getMemberListNum(map);
	}

	@Override
	public void insertExtend(Map<String, Object> map) {
		if(!map.containsKey("origin") || "".equals(map.get("origin"))){
			map.put("origin","5");
		}
		this.memberMapper.insertExtend(map);
	}

	@Override
	public void updateExtend(Map<String, Object> map) {
		 
		this.memberMapper.updateExtend(map);
	}

	@Override
	public void updateExtendPrise(Map<String, Object> map) {
		
		this.memberMapper.updateExtendPrise(map);
		
	}

	@Override
	public void insertCoinRecord(Map<String, Object> map) {
		map.put("recordid", UUID.randomUUID().toString().replace("-", ""));
		map.put("paytime",new Date());
		this.memberMapper.insertCoinRecord(map);
	}

	@Override
	public void givesharecoin(Map<String, Object> map) {
		
		//查询是否分享三次（today）
		int count=this.memberMapper.getShareCount(map);
		if(count<3){
			Map<String, Object> paramMap;
			String amount = saveCoinRecord(map);
			paramMap = new HashMap<String,Object>();
			paramMap.put("tickcoin", amount);
			paramMap.put("userid", map.get("memberid"));
			this.updateExtend(paramMap);
		}
	}

	private String saveCoinRecord(Map<String, Object> map) {
		map.put("title", Constants.SHARE_TITLE);
		map.put("description", Constants.SHARE_DESCRIPTION);
		map.put("income_userid", map.get("memberid"));
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("pcategory", Constants.PARAMCATEGORY_ATTENTION);
		paramMap.put("pkey", Constants.PARAMKEY_ATTENTIONCOIN);
		String amount = "";
		List<Map<String, Object>> params = this.systemMapper.getParams(paramMap);
		if (params != null && params.size() > 0) {
			amount =  (String) params.get(0).get("pvalue");
		}
		
		map.put("amount", amount);
		this.insertCoinRecord(map);
		return amount;
	}
	
	@Override
	public Map<String, Object> getAreaInfo(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.memberMapper.getAreaInfo(data);
	}

	@Override
	public void insertMemberImg(Map<String, Object> map) {
		 
		this.memberMapper.insertMemberImg(map);
	}

	@Override
	public void updateMemberImg(Map<String, Object> map) {
		 this.memberMapper.updateMemberImg(map);
	}

	@Override
	public List<Map<String, Object>> getMemberImgList(Map<String, Object> pvjMap) {
		 
		return this.memberMapper.getMemberImgList(pvjMap);
	}

	@Override
	public void insertimg(Map<String, Object> pvjMap) {
		this.memberMapper.insertimg(pvjMap);
		
	}

	@Override
	public void addMember(Map<String, Object> data) {
		this.memberMapper.insertMember(data);
		
	}

	@Override
	public List<Map<String, Object>> getInsuranceList(Map<String, Object> map) {
		
		return this.memberMapper.getInsuranceList(map);
	}

	@Override
	public int getInsuranceListNum(Map<String, Object> map) {
		
		return this.memberMapper.getInsuranceListNum(map);
	}

	@Override
	public void updateInsurance(Map<String, Object> map) {
		 
		this.memberMapper.updateInsurance(map);
		
	}

	@Override
	public List<Map<String, Object>> getMemberCount(Map<String, Object> map) {
		
		return this.memberMapper.getMemberCount(map);
	}

	@Override
	public int getMemberCountByWeek(
			Map<String, Object> map) {
		
		return this.memberMapper.getMemberCountByWeek(map);
	}

	@Override
	public List<Map<String, Object>> getMemberCountByYear(
			Map<String, Object> map) {
		 
		return this.memberMapper.getMemberCountByYear(map);
	}

	@Override
	public void deleteMemberImg(Map<String, Object> map) {
		
		this.memberMapper.deleteMemberImg(map);
		
	}

	@Override
	public List<Map<String, Object>> geMemberOfComplete(Map<String, Object> order) {
		return memberMapper.geMemberOfComplete(order);
	}

	@Override
	public Map<String, Object> getAgencyInfo(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.memberMapper.getAgencyInfo(data);
	}

	@Override
	public Map<String, Object> getMemberByExtendid(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.memberMapper.getMemberByExtendid(data);
	}
}
