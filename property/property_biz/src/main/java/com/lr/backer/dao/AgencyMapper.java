package com.lr.backer.dao;

import java.util.List;
import java.util.Map;

public interface AgencyMapper {
    //查询代理商列表
	List<Map<String,Object>> getAgencyList(Map<String,Object> map);
	//查询代理商总记录数
	int getAgencyListNum(Map<String,Object> map);
	//新增代理商
	void insertAgency(Map<String,Object> map);
	//修改代理商
	void updateAgency(Map<String,Object> map);
	/**
	 * 得到所有的子节点
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> getChildrenAgency(Map<String,Object> map);
	
	/**
	 * 查询代理商资质图片 列表
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> getAgencyImgList(Map<String,Object> map);
	/**
	 * 删除代理商资质图片
	 * @param map
	 */
	void deleteAgencyImg(Map<String,Object> map);
	 /**
	  * 新增代理商资质图片
	  * @param map
	  */
	void insertAgencyImg(Map<String,Object> map);
	
	
}
