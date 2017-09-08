package com.lr.backer.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 供应商管理
 * @author 唐杰 
 * 
 */
public interface SupplierService {
	//供销商的图片
	List<Map<String, Object>> getsupplierimage(Map<String, Object> data);
	
	List<Map<String, Object>> getsupperlist(Map<String, Object> data);
	int getsuppernum(Map<String, Object> data);
	List<Map<String, Object>> getsupperimg(Map<String, Object> data);
	
	void insertsupper (Map<String, Object> data);
	void insertsupperimg (Map<String, Object> data);
	void insertimg(Map<String, Object> data);
	
	void updatesupper(Map<String, Object> data);
	void updatesupplierimg(Map<String, Object> data);
	void updateimg(Map<String, Object> data);
}
