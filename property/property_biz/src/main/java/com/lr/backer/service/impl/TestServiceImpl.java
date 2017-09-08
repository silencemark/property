package com.lr.backer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lr.backer.dao.TestMapper;
import com.lr.backer.service.TestService;

public class TestServiceImpl implements TestService {

	@Autowired TestMapper testMapper;

	public List<Map<String, Object>> getTestList(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return testMapper.getTestList(data);
	}
	
}
