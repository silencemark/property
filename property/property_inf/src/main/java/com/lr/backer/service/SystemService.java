package com.lr.backer.service;

import java.util.List;
import java.util.Map;

public interface SystemService {
	//代理商
	Map<String, Object> getAgencyMap(Map<String, Object> data);
	//发布订单
	List<Map<String,Object>> getpublishorderbyYear(Map<String, Object> data);
	int getpublishordercount(Map<String, Object> data);
	List<Map<String, Object>> getpublishorderlist(Map<String, Object> data);
	//交易金额
	List<Map<String,Object>> gettraderecordbyYear(Map<String, Object> data);
	Map<String, Object> gettraderecordcount(Map<String, Object> data);
	List<Map<String, Object>> gettraderecordlist(Map<String, Object> data);
	
	//抢单年次
	List<Map<String,Object>> getGrabsinglebyYear(Map<String, Object> data);
	//抢单月次
	int getGrabsinglecount(Map<String, Object> data);
	//抢单数
	List<Map<String, Object>> getGrabsinglelist(Map<String, Object> data);
	//查询用户
	List<Map<String, Object>> getMemberList(Map<String, Object> data);
	//月次数
	int getActiveMounthcount(Map<String, Object> data);
	//年次数
	List<Map<String, Object>> getActiveMembersbyYear(Map<String, Object> data);
	//活跃会员
	List<Map<String, Object>> getActiveMembers(Map<String, Object> data);
	
	String getAmountAll(Map<String, Object> data);
	//代理商列表
	List<Map<String, Object>> getCommissionList(Map<String, Object> data);
	int getCommissionListNum(Map<String, Object> data);
	//baner位置列表
	List<Map<String, Object>> getbannerListHome(Map<String, Object> data);
	//baner位置列表数量
	int getbannerListHomeNum(Map<String, Object> data);
	//baner详情列表
	List<Map<String, Object>> getbannerPositionListHome(Map<String, Object> data);
	//baner详情列表数量
	int getbannerPositionListHomeNum(Map<String, Object> data);
	//新增banner
	int insertBanner(Map<String, Object> data);
	//修改banner
	int updateBanner(Map<String, Object> data);
	//删除banner
	int deleteBanner(Map<String, Object> data);
	//用户登录
	public Map<String, Object> getUserInfo(Map<String, Object> data);
	//用户具有菜单权限
	public List<Map<String, Object>> getMenuList(Map<String, Object> data);
	//菜单管理
	List<Map<String, Object>> getFunctionList(Map<String, Object> data);
	int getFunctionListNum(Map<String, Object> data);
	//添加菜单
	void insertFunction(Map<String, Object> data);
	//修改菜单
	void updateFunction(Map<String, Object> data);
	//删除菜单
	void deleteFunction(Map<String, Object> data);
	//所有角色
	List<Map<String, Object>> getRole();
	//编辑用户角色
	void editUserRole(String userid,String roleids,String[] ids);
	//新增用户角色
	void saveUserRole (Map<String,Object> map);
	//编辑用户
	public int editUser(Map<String,Object> map);
	//重置用户密码
	public void resetPwd(Map<String,Object> map);
	//查询用户列表
	public List<Map<String,Object>> getUserList(Map<String,Object> map);
	//查询总用户数量
	public int getUserListNum(Map<String,Object> map);	
	//新增角色
	void insertRole(Map<String, Object> data);
	//修改角色
	void updateRole(Map<String, Object> data);
	//删除角色
	void deleteRole(Map<String, Object> data);
	//得到角色列表
	List<Map<String, Object>> getRoleList(Map<String, Object> data);
	//得到角色列表数量
	int getRoleListNum(Map<String, Object> data);
	//得到所有权限列表
	List<Map<String, Object>> getRoleListAll();
	//查询用户权限
	List<String> findFactFunByRole(Map<String, Object> data);
	//角色菜单
	List<Map<String, Object>> getRoleFunctionList(Map<String, Object> data);
	//角色菜单数量 
	int getRoleFunctionListNum(Map<String, Object> data);
	//得到所有菜单列表
	List<Map<String, Object>> getFunctionListAll();
	//编辑权限拥有的菜单
	void editRoleFunction(String roleid,String funids,String[] ids);
	//查询地区
	List<Map<String, Object>> getAreaList(Map<String, Object> data);
	//查询地区数量
	int getAreaCount(Map<String, Object> data);
	//新增地区
	int insertArea(Map<String, Object> data);
	//修改地区
    int updateArea(Map<String, Object> data);
    /**
     * 查询参数配置信息
     * @param paramMap
     * @return
     */
	List<Map<String, Object>> getParams(Map<String, Object> paramMap);
    
	/**
	 * 修改用户信息
	 * @param paramMap
	 */
	void updateUser(Map<String, Object> paramMap);
    
	/**
	 * 判断当前用户角色
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> judgeUserRoles(Map<String, Object> paramMap);
	
	/**
	 * 根据角色id查询系统用户
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getUserListByRoleId(Map<String, Object> paramMap);
	
	//角色
	List<Map<String, Object>> getRoleListByUserid(Map<String, Object> data);
}
