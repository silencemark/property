package com.lr.backer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lr.backer.dao.TradeMapper;
import com.lr.backer.service.TradeService;

public class TradeServiceImpl implements TradeService{
	@Autowired TradeMapper TradeMapper;

	@Override
	public List<Map<String, Object>> gettradelist(Map<String, Object> data) {
	 
		return this.TradeMapper.gettradelist(data);
	}

	@Override
	public int gettradenum(Map<String, Object> data) {
	 
		return this.TradeMapper.gettradenum(data);
	}

	@Override
	public void insertInsurance(Map<String, Object> data) {
		 
		this.TradeMapper.insertInsurance(data);
	}

	
}
