package com.lr.backer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.lr.backer.dao.OrderMapper;
import com.lr.backer.service.OrderService;

public class OrderServiceImpl implements OrderService {

	@Autowired OrderMapper orderMapper;

	@Override
	public List<Map<String, Object>> getReleaseOrderList(
			Map<String, Object> data) {
		return orderMapper.getReleaseOrderList(data);
	}

	@Override
	public int getReleaseOrderCount(Map<String, Object> data) {
		return orderMapper.getReleaseOrderCount(data);
	}

	@Override
	public List<Map<String, Object>> getRushOrderList(Map<String, Object> data) {
		return orderMapper.getRushOrderList(data);
	}

	@Override
	public int getRushOrderCount(Map<String, Object> data) {
		return orderMapper.getRushOrderCount(data);
	}


	@Override
	public Map<String, Object> getProjectDetailById(Map<String, Object> data) {
		 
		return this.orderMapper.getProjectDetailById(data);
	}
 


	@Override
	public Map<String, Object> getJobDetailById(Map<String, Object> data) {
		
		return this.orderMapper.getJobDetailById(data);
	}

	@Override
	public List<Map<String, Object>> getApplyOrderById(Map<String, Object> data) {
		List<Map<String, Object>> applylist=new ArrayList<Map<String,Object>>();
		applylist=this.orderMapper.getApplyOrderById(data);
		 if(data.containsKey("publishmemberid") && !data.get("publishmemberid").equals("")){
			 for(Map<String, Object> app:applylist){
				 Map<String, Object> attention=new HashMap<String, Object>();
				 attention.put("userid", app.get("createrid"));
				 attention.put("attentionerid", data.get("publishmemberid"));
				 attention=this.orderMapper.getAttention(attention);
				 if(attention!=null && attention.size()>0){
					 app.put("isattention", 1);
				 }
			 }
		 }
		return applylist;
	}

	@Override
	public int getApplyOrderByIdNum(Map<String, Object> data) {
		 
		return this.orderMapper.getApplyOrderByIdNum(data);
	}

	@Override
	public List<Map<String, Object>> getProjectImgs(Map<String, Object> data) {
		
		return this.orderMapper.getProjectImgs(data);
	}

	@Override
	public Map<String, Object> getApplyOrderCount(Map<String, Object> map) {
		return orderMapper.getApplyOrderCount(map);
	}

	@Override
	public Map<String, Object> getOrderCount(Map<String, Object> map) {
		return orderMapper.getOrderCount(map);
	}

	@Override
	public void updateApplyOrderStatus(Map<String, Object> data) {
		 this.orderMapper.updateApplyOrderStatus(data);
		
	}

	@Override
	public void updateOrderStatus(Map<String, Object> map) {
		this.orderMapper.updateOrderStatus(map);
	}

	@Override
	public int savePayRecord(Map<String, Object> data) {
		// TODO Auto-generated method stub
		if(data.containsKey("type") && data.get("type").equals("1")){
			//嘀嗒币
			//1.自己的嘀嗒币减
			Map<String, Object> userinfo=new HashMap<String, Object>();
			userinfo.put("userid", data.get("memberid"));
			userinfo.put("reducetickcoin", data.get("price"));
			this.orderMapper.updateuserextend(userinfo);
			//2.嘀嗒币记录表
			Map<String, Object> coinrecord=new HashMap<String, Object>();
			coinrecord.put("recordid", UUID.randomUUID().toString().replace("-", ""));
			coinrecord.put("title", "支付佣金");
			coinrecord.put("description", "支付佣金查看工人信息");
			coinrecord.put("amount", data.get("price"));
			coinrecord.put("pay_userid", data.get("memberid"));
			coinrecord.put("paytime", new Date());
			this.orderMapper.insertCoinRecord(coinrecord);
		}
		//3.交易记录
		Map<String, Object> trademap=new HashMap<String, Object>();
		trademap.put("tradeid",UUID.randomUUID().toString().replace("-", ""));
		trademap.put("incomepay",2);
		trademap.put("amount",data.get("price"));
		trademap.put("paymethod",data.get("type"));
		trademap.put("paypurposetype",2);
		trademap.put("pay_userid",data.get("memberid"));
		trademap.put("status",1);
		trademap.put("createtime",new Date());
		trademap.put("tradeorderid", data.get("applyorderid"));
		this.orderMapper.insertTradeRecord(trademap);
		
		return 0;
	}

	@Override
	public void insertTradeRecord(Map<String, Object> data) {
		this.orderMapper.insertTradeRecord(data);
		
	}

	@Override
	public void insertCoinRecord(Map<String, Object> data) {
		 this.orderMapper.insertCoinRecord(data);
	}

	@Override
	public void insertCredit(Map<String, Object> map) {
		this.orderMapper.insertCredit(map);
		
	}

	@Override
	public List<Map<String, Object>> getApplyPeopleList(Map<String, Object> data) {
	
		return this.orderMapper.getApplyPeopleList(data);
	}

	@Override
	public int getApplyPeopleListNum(Map<String, Object> data) {
		 
		return this.orderMapper.getApplyPeopleListNum(data);
	}

	@Override
	public int getTradeNumByNow() {
		// TODO Auto-generated method stub
		return this.orderMapper.getTradeNumByNow();
	}

 



	
}
