package com.lr.backer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lr.backer.dao.SupplierMapper;
import com.lr.backer.service.SupplierService;

public class SupplierServiceImpl implements SupplierService{
	@Autowired SupplierMapper SupplierMapper;

	public List<Map<String, Object>> getsupperlist(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.SupplierMapper.getsupperlist(data);
	}

	public List<Map<String, Object>> getsupperimg(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.SupplierMapper.getsupperimg(data);
	}

	public void insertsupper(Map<String, Object> data) {
		// TODO Auto-generated method stub
		this.SupplierMapper.insertsupper(data);
	}

	public void insertsupperimg(Map<String, Object> data) {
		// TODO Auto-generated method stub
		this.SupplierMapper.insertsupperimg(data);
	}

	public void insertimg(Map<String, Object> data) {
		// TODO Auto-generated method stub
		this.SupplierMapper.insertimg(data);
	}

	public void updatesupper(Map<String, Object> data) {
		// TODO Auto-generated method stub
		this.SupplierMapper.updatesupper(data);
	}

	public void updatesupplierimg(Map<String, Object> data) {
		// TODO Auto-generated method stub
		this.SupplierMapper.updatesupplierimg(data);
	}

	public void updateimg(Map<String, Object> data) {
		// TODO Auto-generated method stub
		this.SupplierMapper.updateimg(data);
	}

	@Override
	public int getsuppernum(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.SupplierMapper.getsuppernum(data);
	}

	@Override
	public List<Map<String, Object>> getsupplierimage(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.SupplierMapper.getsupplierimage(data);
	}
	
}
