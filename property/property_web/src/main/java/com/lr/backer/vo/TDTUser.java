package com.lr.backer.vo;

import java.util.List;

import com.lr.backer.util.PageUtil;

public class TDTUser {

	private Integer id;

	private String username;

	private String password;

	private String provinceCode;

	private String realName;

	private String email;

	private Integer provinceId;

	private String phone;

	private String telephone;

	private Integer status;

	private String provincename;

	private List<TDTRole> roleList;

	private PageUtil page;

	private String repassword;

	private String yspassword;

	private String roleid;

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getYspassword() {
		return yspassword;
	}

	public void setYspassword(String yspassword) {
		this.yspassword = yspassword;
	}

	// 验证码
	private String validcode;

	// 经销商（2） 还是 管理员（1）
	private String roletype;

	public String getRoletype() {
		return roletype;
	}

	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public String getValidcode() {
		return validcode;
	}

	public void setValidcode(String validcode) {
		this.validcode = validcode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone == null ? null : telephone.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<TDTRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<TDTRole> roleList) {
		this.roleList = roleList;
	}

	public PageUtil getPage() {
		return page;
	}

	public void setPage(PageUtil page) {
		this.page = page;
	}

	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
}