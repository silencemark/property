package com.lr.backer.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.lr.backer.dao.EmployerMapper;
import com.lr.backer.service.EmployerService;
import com.lr.weixin.backer.service.MemberService;


public class EmployerServiceImpl implements EmployerService {

	@Autowired EmployerMapper employerMapper;
	
	@Autowired MemberService memberService;

	@Override
	public String insertJob(Map<String, Object> data) {
		String jobid = UUID.randomUUID().toString().replace("-", "");
		//发布招工
		data.put("createtime", new Date());
		data.put("jobid", jobid);
		employerMapper.insertJob(data);
		//生成订单
		Map<String,Object> orderMap = new HashMap<String,Object>();
		//订单号(14位时间戳加4位随机数)
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		int ordernum=this.employerMapper.getOrderNo();
		String orderno=sdf.format(new Date())+String.format("%05d",(ordernum+1));
		
		orderMap.put("orderno", orderno);
		orderMap.put("jobid", jobid);
		orderMap.put("publisherid", data.get("createrid"));
		orderMap.put("failuretime", data.get("starttime"));
		orderMap.put("longitude", data.get("longitude"));
		orderMap.put("latitude", data.get("latitude"));
		employerMapper.insertOrder(orderMap);
		return jobid;
	}
	@Override
	public String insertProject(Map<String, Object> data) {
		String projectid = UUID.randomUUID().toString().replace("-", "");
		//发布项目
		data.put("createtime", new Date());
		data.put("projectid", projectid);
		employerMapper.insertProject(data);
		//生成订单
		Map<String,Object> orderMap = new HashMap<String,Object>();
		//订单号(14位时间戳加4位随机数)
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		int ordernum=this.employerMapper.getOrderNo();
		String orderno=sdf.format(new Date())+String.format("%05d",(ordernum+1));;
		orderMap.put("orderno", orderno);
		orderMap.put("projectid", projectid);
		orderMap.put("publisherid", data.get("createrid"));
		orderMap.put("failuretime", data.get("starttime"));
		orderMap.put("longitude", data.get("longitude"));
		orderMap.put("latitude", data.get("latitude"));
		employerMapper.insertOrder(orderMap);
		return projectid;
	}
	@Override
	public String insertImg(Map<String, Object> data) {
		// TODO Auto-generated method stub
		String imgid = UUID.randomUUID().toString().replace("-", "");
		data.put("imgid", imgid);
		this.employerMapper.insertImg(data);
		return imgid;
	}

	@Override
	public String insertProjectImg(Map<String, Object> data) {
		// TODO Auto-generated method stub
		String projectimgid = UUID.randomUUID().toString().replace("-", "");
		data.put("projectimgid", projectimgid);
		this.employerMapper.insertProjectImg(data);
		return projectimgid;
	}

	@Override
	public List<Map<String, Object>> getTopTenproject(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.employerMapper.getTopTenproject(data);
	}
	@Override
	public int insertprojectjob(Map<String, Object> data) {
		// TODO Auto-generated method stub
		Map<String, Object> projectjob=new HashMap<String, Object>();
		projectjob=this.employerMapper.getprojectjob(data);
		if(projectjob== null || projectjob.size()==0){
			data.put("jobprojectid",UUID.randomUUID().toString().replace("-", ""));
			data.put("createtime", new Date());
			this.employerMapper.insertprojectjob(data);
		}
		return 0;
	}
	@Override
	public Map<String, Object> getProjectInfo(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.employerMapper.getProjectInfo(data);
	}
	@Override
	public List<Map<String, Object>> getMemberListByinfo(
			Map<String, Object> data) {
		// TODO Auto-generated method stub
		String jobtype=data.get("jobtype")+"";
		Map<String, Object> dictmap=new HashMap<String, Object>();
		dictmap.put("dataid", jobtype);
		dictmap=this.employerMapper.getDictData(dictmap);
		if(dictmap!=null && dictmap.size()>0){
			data.put("parentid",dictmap.get("parentid")+"");
			data.remove("jobtype");
		}
		Map<String, Object> areaInfo=new HashMap<String, Object>();
		areaInfo.put("areaid", data.get("projectprovince"));
		areaInfo=this.memberService.getAreaInfo(areaInfo);
		if(areaInfo!= null && areaInfo.containsKey("cname") && (String.valueOf(areaInfo.get("cname")).equals("上海") || String.valueOf(areaInfo.get("cname")).equals("重庆") || String.valueOf(areaInfo.get("cname")).equals("天津") || String.valueOf(areaInfo.get("cname")).equals("北京") )){
			data.put("areaparentid",data.get("projectprovince"));
			data.remove("projectarea");
			data.remove("projectprovince");
		}
		return this.employerMapper.getMemberListByinfo(data);
	}
	@Override
	public int insertprojectMessage(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.employerMapper.insertprojectMessage(data);
	}
	@Override
	public List<Map<String, Object>> getApplyOrder(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.employerMapper.getApplyOrder(data);
	}
	@Override
	public int getnoticenum(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.employerMapper.getnoticenum(data);
	}
	@Override
	public Map<String, Object> getApplyProjectDetail(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.employerMapper.getApplyProjectDetail(data);
	}
	@Override
	public Map<String, Object> getApplyJobDetail(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.employerMapper.getApplyJobDetail(data);
	}
	@Override
	public List<Map<String, Object>> getOutTimeOrder() {
		// TODO Auto-generated method stub
		return this.employerMapper.getOutTimeOrder();
	}
	@Override
	public int updateorderInfo(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.employerMapper.updateorderInfo(data);
	}
	@Override
	public Map<String, Object> getOrderInfo(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.employerMapper.getOrderInfo(data);
	}
	@Override
	public void insertShareRecore(Map<String, Object> data){
		// TODO Auto-generated method stub
		data.put("createtime", new Date());
		data.put("sharerecordid", UUID.randomUUID().toString().replace("-", ""));
		this.employerMapper.insertShareRecore(data);
	}
	@Override
	public List<Map<String, Object>> getProjectPicture(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.employerMapper.getProjectPicture(data);
	}
	@Override
	public void updateApplyOrder(Map<String, Object> data) {
		// TODO Auto-generated method stub
		this.employerMapper.updateApplyOrder(data);
	}
	public static void main(String[] args) {
		  String str = String.format("%05d", 500);
		     System.out.println(str);

	}
	@Override
	public List<Map<String, Object>> getKillList(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.employerMapper.getKillList(data);
	}
	@Override
	public void updateApplyOrderinfo(Map<String, Object> data) {
		// TODO Auto-generated method stub
		this.employerMapper.updateApplyOrderinfo(data);
	}
}
