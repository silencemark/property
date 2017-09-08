package com.lr.backer.service.impl;

 
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.lr.backer.dao.EvaluationMapper;
import com.lr.backer.service.EvaluationService;
import com.lr.backer.util.StringUtil;
import com.lr.weixin.backer.dao.MemberMapper;
 

public class EvaluationServiceImpl implements EvaluationService {

	@Autowired  EvaluationMapper evaluationMapper;
	@Autowired  MemberMapper memberMapper;

	@Override
	public List<Map<String, Object>> getEvalList(Map<String, Object> map) {
		 
		return this.evaluationMapper.getEvalList(map);
	}

	@Override
	public int getEvalListNum(Map<String, Object> map) {
		 
		return this.evaluationMapper.getEvalListNum(map);
	}

	@Override
	public void updateEvaluateStatus(Map<String, Object> map) {
		
		this.evaluationMapper.updateEvaluateStatus(map);
		
	}

	@Override
	public List<Map<String, Object>> getEvaluations(Map<String, Object> map) {
		return this.evaluationMapper.getEevaluations(map);
	}

	@Override
	public Map<String, Object> getEvaStatistics(Map<String, Object> map) {
		return this.evaluationMapper.getEevaStatistics(map);
	}

	@Override
	public void insertEvaluate(Map<String, Object> map) {
		Timestamp tm = new Timestamp(System.currentTimeMillis());
		map.put("id", StringUtil.getUUID());
		map.put("status", "0");
		map.put("createtime", tm);
		this.evaluationMapper.insertEvaluate(map);
		//查询当前被评价人的平均分数值 然后写入用户扩展表
		Map<String,Object> dt = new HashMap<String, Object>();
		dt.put("userid", map.get("userid"));
		Map<String, Object>  avgScoreMap = this.evaluationMapper.getEevaStatistics(dt);
		dt.put("evaluationavg", avgScoreMap.get("avg"));
		if(map.containsKey("isban") && String.valueOf(map.get("isban")).equals("1")){
			//保存到封杀表
			Map<String, Object> killInfo=new HashMap<String, Object>();
			killInfo.put("killid",UUID.randomUUID().toString().replace("-", ""));
			killInfo.put("bekillerid", map.get("userid"));
			killInfo.put("killerid", map.get("evaluationerid"));
			killInfo.put("isban", map.get("isban"));
			killInfo.put("createtime", map.get("createtime"));
			this.evaluationMapper.insertKillInfo(killInfo);
		}
		this.memberMapper.updateExtend(dt);
	}

	@Override
	public int getEevaluationsNum(Map<String, Object> map) {
		
		return  this.evaluationMapper.getEevaluationsNum(map);
	}

 
	 
	
}
