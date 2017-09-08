package com.lr.backer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lr.backer.dao.SystemMapper;
import com.lr.backer.service.SystemService;

public class SystemServiceImpl implements SystemService {

	@Autowired SystemMapper systemMapper;
	
	

	public List<Map<String, Object>> getbannerListHome(Map<String, Object> data){
		return systemMapper.getbannerListHome(data);
	}
	
	public int getbannerListHomeNum(Map<String, Object> data){
		return systemMapper.getbannerListHomeNum(data);
	}
	
	public	List<Map<String, Object>> getbannerPositionListHome(Map<String, Object> data){
		return systemMapper.getbannerPositionListHome(data);
	}

	public	int getbannerPositionListHomeNum(Map<String, Object> data){
		return systemMapper.getbannerPositionListHomeNum(data);
	}

	public	int insertBanner(Map<String, Object> data){
		return systemMapper.insertBanner(data);
	}

	public	int updateBanner(Map<String, Object> data){
		return systemMapper.updateBanner(data);
	}

	public	int deleteBanner(Map<String, Object> data){
		return systemMapper.deleteBanner(data);
	}
		
	public int editUser(Map<String, Object> map) {
		int row = 0;
		//修改用户信息
		if(map.get("userid") != null && !map.get("userid").equals("")){
			row = systemMapper.updateUser(map);
		}
		//新增用户信息
		else{

			row = systemMapper.insertUser(map);
		}
		return row;
	}

	
	public List<Map<String, Object>> getUserList(Map<String, Object> map) {
		return systemMapper.getUserList(map);
	}

	
	public int getUserListNum(Map<String, Object> map) {
		return systemMapper.getUserListNum(map);
	}

	
	public Map<String, Object> getUserInfo(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return systemMapper.getUserInfo(data);
	}

	
	public List<Map<String, Object>> getMenuList(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return systemMapper.getMenuList(data);
	}

	
	public List<Map<String, Object>> getFunctionList(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.systemMapper.getFunctionList(data);
	}

	
	public int getFunctionListNum(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return  this.systemMapper.getFunctionListNum(data);
	}

	
	public void insertFunction(Map<String, Object> data) {
		// TODO Auto-generated method stub
		this.systemMapper.insertFunction(data);
	}

	
	public void updateFunction(Map<String, Object> data) {
		// TODO Auto-generated method stub
		this.systemMapper.updateFunction(data);
	}

	
	public void deleteFunction(Map<String, Object> data) {
		// TODO Auto-generated method stub
		this.systemMapper.deleteFunction(data);
	}

	
	public void insertRole(Map<String, Object> data) {
		this.systemMapper.insertRole(data);
	}

	
	public void updateRole(Map<String, Object> data) {
		this.systemMapper.updateRole(data);
	}

	
	public void deleteRole(Map<String, Object> data) {
		// 1. 删除关系表中roleId
		if (null != data && !data.isEmpty() && data.containsKey("id")) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("roleId", data.get("id"));
			this.systemMapper.deleteRoleFunction(paramMap);
			this.systemMapper.deleteUserRole(paramMap);
		}
		// 2.删除role表中的数据
		this.systemMapper.deleteRole(data);		
	}

	
	public List<Map<String, Object>> getRoleList(Map<String, Object> data) {
		return this.systemMapper.getRoleList(data);
	}

	
	public int getRoleListNum(Map<String, Object> data) {
		return this.systemMapper.getRoleListNum(data);
	}

	
	public List<Map<String, Object>> getRoleListAll() {
		return this.systemMapper.getRoleListAll();
	}

	
	public List<String> findFactFunByRole(Map<String, Object> data) {
		return this.systemMapper.findFactFunByRole(data);
	}

	
	public List<Map<String, Object>> getRoleFunctionList(
			Map<String, Object> data) {
		return this.systemMapper.getRoleFunctionList(data);
	}

	
	public int getRoleFunctionListNum(Map<String, Object> data) {
		return this.systemMapper.getRoleFunctionListNum(data);
	}

	
	public List<Map<String, Object>> getFunctionListAll() {
		return this.systemMapper.getFunctionListAll();
	}

	
	public void editRoleFunction(String roleid, String funids, String[] ids) {
		String[] jiufunids = funids.split(",");
		//List<String> deletelist = new ArrayList<String>();
		  if(roleid!= null && !roleid.equals("")){
			  Map<String, Object> paramMap = new HashMap<String, Object>();
	        	//删除当前角色所对应的所有菜单
			    paramMap.put("roleid", roleid);
	        	systemMapper.deleteRoleFunction(paramMap);
	        }
		/*if (funids != null && !funids.equals("")) {
			for (String i : jiufunids) {
				boolean ok = true;
				for (String j : ids) {
					if (i.equals(j)) {
						ok = false;
					}
				}
				if (ok) {
					deletelist.add(i);
				}
			}
			if (deletelist !=null && deletelist.size() > 0) {
				data.put("roleid", roleid);
				data.put("list", deletelist);
				systemMapper.deleteRoleFunction(data);
			}
		}*/
		  Map<String, Object> data = new HashMap<String, Object>();
		data.put("roleid", roleid);
		systemMapper.deleteRoleFunction(data);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (String i : ids) {
			boolean ok = true;
			for (String j : jiufunids) {
				if (i.equals(j)) {
					ok = false;
				}
			}
			if (ok) {
				Map<String, Object> dat = new HashMap<String, Object>();
				dat.put("roleid", roleid);
				dat.put("funid", i);
				list.add(dat);
			}
		}
		if (list.size() > 0) {
			data.put("roleFunList", list);
			systemMapper.saveRoleFunction(data);
		}
	}



	
	public List<Map<String, Object>> getRole() {
		// TODO Auto-generated method stub
		return this.systemMapper.getRole();
	}
	
	public void editUserRole(String userid, String roleids, String[] ids) {
		String[] jiuroleids = roleids.split(",");
		List<String> deletelist = new ArrayList<String>();

		Map<String, Object> data = new HashMap<String, Object>();
		if (roleids != null && !roleids.equals("")) {
			for (String i : jiuroleids) {
				boolean ok = true;
				for (String j : ids) {
					if (i.equals(j)) {
						ok = false;
					}
				}
				if (ok) {
					deletelist.add(i);
				}
			}
			if (deletelist !=null && deletelist.size() > 0) {
				data.put("userid", userid);
				data.put("list", deletelist);
				systemMapper.deleteUserRole(data);
			}
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (String i : ids) {
			boolean ok = true;
			for (String j : jiuroleids) {
				if (i.equals(j)) {
					ok = false;
				}
			}
			if (ok) {
				Map<String, Object> dat = new HashMap<String, Object>();
				dat.put("userid", userid);
				dat.put("roleid", i);
				list.add(dat);
			}
		}
		if (list.size() > 0) {
			data.put("userRoleList", list);
			systemMapper.saveUserRole(data);
		}
	}


	public void resetPwd(Map<String, Object> map) {
		systemMapper.resetPwd(map); 
	}
	
	
	public List<Map<String, Object>> getAreaList(Map<String, Object> data){
		return systemMapper.getAreaList(data);
	}

	public int getAreaCount(Map<String, Object> data) {
		return systemMapper.getAreaCount(data);
	}

	public int updateArea(Map<String, Object> data) {
		return systemMapper.updateArea(data);
	}

	public int insertArea(Map<String, Object> data) {
		return systemMapper.insertArea(data);
	}

	@Override
	public void saveUserRole(Map<String, Object> map) {
		 this.systemMapper.saveUserRole(map);
	}

	@Override
	public List<Map<String, Object>> getParams(Map<String, Object> paramMap) {
		return this.systemMapper.getParams(paramMap);
	}

	@Override
	public void updateUser(Map<String, Object> paramMap) {
		
		this.systemMapper.updateUser(paramMap);
		
	}

	@Override
	public List<Map<String, Object>> getCommissionList(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.systemMapper.getCommissionList(data);
	}

	@Override
	public int getCommissionListNum(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.systemMapper.getCommissionListNum(data);
	}

	@Override
	public String getAmountAll(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.systemMapper.getAmountAll(data);
	}

	@Override
	public List<Map<String, Object>> judgeUserRoles(Map<String, Object> paramMap) {
		
		return this.systemMapper.judgeUserRoles(paramMap);
	}

	@Override
	public List<Map<String, Object>> getUserListByRoleId(
			Map<String, Object> paramMap) {
		
		return this.systemMapper.getUserListByRoleId(paramMap);
	}


	@Override
	public List<Map<String, Object>> getActiveMembers(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.systemMapper.getActiveMembers(data);
	}

	@Override
	public int getActiveMounthcount(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.systemMapper.getActiveMounthcount(data);
	}

	@Override
	public List<Map<String, Object>> getActiveMembersbyYear(
			Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.systemMapper.getActiveMembersbyYear(data);
	}


	@Override
	public List<Map<String, Object>> getMemberList(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.systemMapper.getMemberList(data);
	}

	@Override
	public List<Map<String, Object>> getGrabsinglelist(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.systemMapper.getGrabsinglelist(data);
	}

	@Override
	public List<Map<String, Object>> getGrabsinglebyYear(
			Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.systemMapper.getGrabsinglebyYear(data);
	}

	@Override
	public int getGrabsinglecount(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.systemMapper.getGrabsinglecount(data);
	}

	@Override
	public List<Map<String, Object>> getpublishorderbyYear(
			Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.systemMapper.getpublishorderbyYear(data);
	}

	@Override
	public int getpublishordercount(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.systemMapper.getpublishordercount(data);
	}

	@Override
	public List<Map<String, Object>> getpublishorderlist(
			Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.systemMapper.getpublishorderlist(data);
	}

	@Override
	public List<Map<String, Object>> gettraderecordbyYear(
			Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.systemMapper.gettraderecordbyYear(data);
	}

	@Override
	public Map<String, Object> gettraderecordcount(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.systemMapper.gettraderecordcount(data);
	}

	@Override
	public List<Map<String, Object>> gettraderecordlist(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.systemMapper.gettraderecordlist(data);
	}

	@Override
	public Map<String, Object> getAgencyMap(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.systemMapper.getAgencyMap(data);
	}

	@Override
	public List<Map<String, Object>> getRoleListByUserid(
			Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.systemMapper.getRoleListByUserid(data);
	}
}