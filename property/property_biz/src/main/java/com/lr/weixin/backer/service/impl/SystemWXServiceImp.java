package com.lr.weixin.backer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.lr.backer.util.Constants;
import com.lr.backer.util.FileDeal;
import com.lr.weixin.backer.dao.SystemWXMapper;
import com.lr.weixin.backer.service.SystemWXService;


public class SystemWXServiceImp implements SystemWXService {
	
	@Autowired
	private SystemWXMapper systemMapper;

	
	public void deleteDictData(Map<String, Object> data) {
		this.systemMapper.deleteDictData(data);
	}

	
	public void deleteDictType(Map<String, Object> data) {
		this.systemMapper.deleteDictType(data);
	}

	
	public Map<String, Object> getDictData(Map<String, Object> data) {
		return this.systemMapper.getDictData(data);
	}

	
	public List<Map<String, Object>> getDictDataList(Map<String, Object> data) {
		return this.systemMapper.getDictDataList(data);
	}

	
	public Map<String, Object> getDictType(Map<String, Object> data) {
		return this.systemMapper.getDictType(data);
	}

	
	public List<Map<String, Object>> getDictTypeList(Map<String, Object> data) {
		return this.systemMapper.getDictTypeList(data);
	}

	
	public void insertDictData(Map<String, Object> data) {
		this.systemMapper.insertDictData(data);
	}

	
	public void insertDictType(Map<String, Object> data) {
		this.systemMapper.insertDictType(data);
	}

	
	public void updateDictData(Map<String, Object> data) {
		this.systemMapper.updateDictData(data);
	}

	
	public void updateDictType(Map<String, Object> data) {
		this.systemMapper.updateDictType(data);
	}

	
	public int getDictDataListNum(Map<String, Object> data) {
		return this.systemMapper.getDictDataListNum(data);
	}

	
	public int getDictTypeListNum(Map<String, Object> data) {
		return this.systemMapper.getDictTypeListNum(data);
	}

	private String getMapValue(Map<String, Object> map,String key){
		if(null!=map && !map.isEmpty() && map.containsKey(key)){
			Object object = map.get(key) ;
			return null!=object && !"".equals(object) ? object.toString():"" ; 
		}
		return "" ;
	}
	
	
	public void deleteFunction(Map<String, Object> data) {
		//1. 删除关系表中functionid
		if(null!=data && data.containsKey("id")){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("funId", getMapValue(data, "id")) ;
			this.systemMapper.deleteRoleFunction(paramMap);
		}
		//2.删除function表中的数据
		this.systemMapper.deleteFunction(data);
	}

	
	public void deleteRole(Map<String, Object> data) {
		//	1. 删除关系表中roleId
		if(null!=data && data.containsKey("id")){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("roleId", getMapValue(data, "id")) ;
			this.systemMapper.deleteRoleFunction(paramMap);
			this.systemMapper.deleteUserRole(paramMap);
		}
		//2.删除role表中的数据
		this.systemMapper.deleteRole(data);
	}

	
	public void deleteUser(Map<String, Object> data) {
		if(null!=data && data.containsKey("id")){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", getMapValue(data, "id")) ;
			this.systemMapper.deleteUserRole(paramMap);
		}
		this.systemMapper.deleteUser(data);
	}

	
	public List<LinkedHashMap<String, Object>> getFunctionList(Map<String, Object> data) {
		List<LinkedHashMap<String, Object>> temp =  this.systemMapper.getFunctionList(data);
		List<LinkedHashMap<String, Object>> funcList = getFunctionChildRen(temp, "0") ;
		return funcList ;
	}
	
	private List<LinkedHashMap<String, Object>> getFunctionChildRen(List<LinkedHashMap<String, Object>> list,String parentid){
		List<LinkedHashMap<String, Object>> tempList = new ArrayList<LinkedHashMap<String,Object>>();
		if(null!=list && !list.isEmpty()){
			for (LinkedHashMap<String, Object> map : list) {
				if(null!=map && !map.isEmpty()){
					String tparentid = getMapValue(map, "parentid");
					if(null!=parentid && tparentid.equals(parentid)){
						//表示是顶级节点
						List<LinkedHashMap<String, Object>> dataList = getFunctionChildRen(list, getMapValue(map, "id")) ;
						if(null!=dataList){
							map.put("childRen", dataList);
							tempList.add(map);
						}
					}
				}
			}
		}
		return tempList;
	}
	
	
	
	public List<LinkedHashMap<String, Object>> getFunctionDataGridList() {
		List<LinkedHashMap<String, Object>> temp =  this.systemMapper.getFunctionList(null);
		List<LinkedHashMap<String, Object>> funcList = getFunctionChildRen(temp, "0") ;
		return funcList ;
	}

	
	public int getFunctionListNum(Map<String, Object> data) {
		return this.systemMapper.getFunctionListNum(data);
	}

	
	public List<Map<String, Object>> getRoleList(Map<String, Object> data) {
		return this.systemMapper.getRoleList(data);
	}

	
	public int getRoleListNum(Map<String, Object> data) {
		return this.systemMapper.getRoleListNum(data);
	}

	
	public List<Map<String, Object>> getUserList(Map<String, Object> data) {
		return this.systemMapper.getUserList(data);
	}

	
	public int getUserListNum(Map<String, Object> data) {
		return this.systemMapper.getUserListNum(data);
	}

	
	public void insertFunction(Map<String, Object> data) {
		this.systemMapper.insertFunction(data);
	}

	
	public void insertRole(Map<String, Object> data) {
		this.systemMapper.insertRole(data);
	}

	
	public void insertUser(Map<String, Object> data) {
		this.systemMapper.insertUser(data);
	}
	
	@SuppressWarnings("unchecked")
	private Set<String> getKeyList(Map<String, Object> data,String key,String itemKey){
		Set<String> keySet = new HashSet<String>();
 		if(null!=data && !data.isEmpty() && data.containsKey(key)){
 			List<Map<String, Object>> itemList = (List<Map<String, Object>>)data.get(key) ;
 			if(null!=itemList){
 				for (Map<String, Object> map : itemList) {
 					keySet.add(getMapValue(map, itemKey)) ;
				}
 			}
 		}
		return keySet ;
	}
	
	
	public void saveRoleFunction(Map<String, Object> data) {
		//删除需要的角色的关联
		if(null!=data && !data.isEmpty() ){
			Set<String> roleIdSet = getKeyList(data, "roleFunList", "roleId") ;
			Map<String, Object> paramMap = new HashMap<String, Object>();
			if(null!=roleIdSet){
				for (String string : roleIdSet) {
					paramMap.put("roleId", string);
					this.systemMapper.deleteRoleFunction(paramMap);
				}
			}
		}
		if(saveASCondition(data, "roleFunList", "roleId", "funId")){
			this.systemMapper.saveRoleFunction(data);
		}
	}

	
	public void saveUserRole(Map<String, Object> data) {
		if(null!=data && !data.isEmpty() ){
			Set<String> userIdSet = getKeyList(data, "userRoleList", "userId") ;
			Map<String, Object> paramMap = new HashMap<String, Object>();
			if(null!=userIdSet){
				for (String string : userIdSet) {
					paramMap.put("userId", string);
					this.systemMapper.deleteUserRole(paramMap);
				}
			}
		}
		if(saveASCondition(data, "userRoleList", "userId", "roleId")){
			this.systemMapper.saveUserRole(data);
		}
	}
	
	private boolean saveASCondition(Map<String, Object> data,String key,String key1,String key2){
		if(null!=data && !data.isEmpty()){
			List<Map<String, Object>> dataList = (List<Map<String,Object>>)data.get(key) ;
			if(null!=dataList && dataList.size()>=1){
				Map<String, Object> map = dataList.get(0) ;
				if(null!=map && map.containsKey(key1) && map.containsKey(key2)
						&& null!=map.get(key1) && null!=map.get(key2)
						&& !"".equals(map.get(key1).toString()) && !"".equals(map.get(key2).toString())){
					return true ;
				}
			}
		}
		return false ;
	}
	
	
	public void updateFunction(Map<String, Object> data) {
		this.systemMapper.updateFunction(data);
	}

	
	public void updateRole(Map<String, Object> data) {
		this.systemMapper.updateRole(data);
	}

	
	public void updateUser(Map<String, Object> data) {
		this.systemMapper.updateUser(data);
	}

	
	public void deleteMLBanner(Map<String, Object> data) {
		this.systemMapper.deleteMLBanner(data);
	}

	
	public Map<String, Object> getMLBanner(Map<String, Object> data) {
		return this.systemMapper.getMLBanner(data);
	}

	
	public List<Map<String, Object>> getMLBannerList(Map<String, Object> data) {
		return this.systemMapper.getMLBannerList(data);
	}

	
	public int getMLBannerListNum(Map<String, Object> data) {
		return this.systemMapper.getMLBannerListNum(data);
	}

	
	public void insertMLBanner(Map<String, Object> data) {
		this.systemMapper.insertMLBanner(data);
	}

	
	public void updateMLBanner(Map<String, Object> data) {
		this.systemMapper.updateMLBanner(data);
	}

	
	public Map<String, Object> getMLBannerByPlaceCode(String placecode) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("placecode", placecode);
		System.out.println(placecode);
		List<Map<String, Object>> list = this.systemMapper.getMLBannerList(data);
		if(null!=list && list.size()>0){
			Map<String, Object> map = list.get(0);
			String imgpath = "" ;
			if(null!=map && !map.isEmpty()){
				imgpath = null !=  map.get("img") ? map.get("img").toString() : "" ; 
			}
			if(null == imgpath || "".equals(imgpath)){
				imgpath = Constants.DEFAULT_PLACE_IMG;
			}
			map.put("img", imgpath);
			return map;
		}
		return null ;
	}

	
	public String getImgByPlaceCode(String placecode) {
		Map<String, Object> map = this.getMLBannerByPlaceCode(placecode);
		String imgpath = "" ;
		if(null!=map && !map.isEmpty()){
			imgpath = null != map.get("img") ? map.get("img").toString() : "" ;
		}
		if(null == imgpath || "".equals(imgpath)){
			imgpath = Constants.DEFAULT_PLACE_IMG;
		}
		return imgpath;
	}

	
	public void deleteDTMenu(Map<String, Object> data) {
		this.systemMapper.deleteDTMenu(data);
	}

	
	public Map<String, Object> getDTMenu(Map<String, Object> data) {
		return this.systemMapper.getDTMenu(data);
	}

	
	public List<LinkedHashMap<String, Object>> getDTMenuList(Map<String, Object> data) {
		System.out.println("*************");
		List<LinkedHashMap<String, Object>> temp =  this.systemMapper.getDTMenuList(data);
		System.out.println("查询到的菜单="+temp.toString());
		List<LinkedHashMap<String, Object>> menuList = FileDeal.getChildRen(temp, "0", "menuid", "parentid");
		System.out.println("*************");
		return menuList;
	}

	
	public List<Map<String, Object>> getTopDTMenuList(Map<String, Object> data) {
		return this.systemMapper.getTopDTMenuList(data);
	}

	
	public void insertDTMenu(Map<String, Object> data) {
		this.systemMapper.insertDTMenu(data);
	}

	
	public void updateDTMenu(Map<String, Object> data) {
		this.systemMapper.updateDTMenu(data);
	}

	
	public Map<String, Object> getInteValidtime() {
		List<Map<String, Object>> list = this.systemMapper.getInteValidtime();
		if(null!=list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	
	public void updateInteValidtime(Map<String, Object> data) {
		this.systemMapper.updateInteValidtime(data);
	}

	
	public String getInsertUser() {
		Map<String, Object> map = this.systemMapper.getInsertUser();
		if(null!=map && !map.isEmpty()){
			return null != map.get("userid") ? map.get("userid").toString() : "" ;
		}
		return "";
	}
	
	
	public List<Map<String, Object>> getMsgtypeList(String requestType) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("type", requestType);
		return this.systemMapper.getMsgtypeList(data);
	}
	
	public List<Map<String, Object>> getMsgtypeList(Map<String, Object> data) {
		return this.systemMapper.getMsgtypeList(data);
	}

}