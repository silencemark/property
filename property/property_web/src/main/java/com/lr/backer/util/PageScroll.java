package com.lr.backer.util;

import java.io.Serializable;
import java.util.Map;

/**
 * 分页JavaBean
 */
@SuppressWarnings("serial")
public class PageScroll implements Serializable{
	
	private int pageSize=10; //每页显示的条数
	private int pageNo = 1; //当前页码
	private int totalRecords = 0; //总记录数 
	private int totalPages=1;    //总页码
	public PageScroll() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 总记录数
	 * @param totalRecords
	 */
	public PageScroll(int totalRecords){
		this.totalRecords = totalRecords;
	}
	/**
	 * 设置每页显示记录的条数
	 */
	public void setPageSize(Object size){
		try {
			pageSize = new Integer(size.toString());
		} catch (Exception e) {
			pageSize = 10;
		} 
	}
	public void setPageSize(Map<String, Object> data){
		if(data.get("pageSize")!=null){
			 setPageSize(data.get("pageSize"));
		 }else if(data.get("pagesize")!=null){
			 setPageSize(data.get("pagesize"));
		 }else{
			 setPageSize(10);
		 }
	}
	
	/**
	 * 获取每页显示的记录的条数
	 */
	public int getPageSize(){
		return this.pageSize;
	}
	
	/**
	 * 设置当前页的页码
	 */
	public void setPageNo(Object no){
		try {
			this.pageNo = new Integer(no.toString());
		} catch (Exception e) {
			this.pageNo = 1;
		} 
	}
	public void setPageNo(Map<String, Object> data){
		if(data.get("pageno")!=null){
			setPageNo(data.get("pageno"));
		}else if(data.get("pageNo")!=null){
			setPageNo(data.get("pageNo"));
		}else if(data.get("currentPage")!=null){
			setPageNo(data.get("currentPage"));
		}else{
			setPageNo(1);
		}
	}
	
	/**
	 * 获取当前页的页码
	 */
	public int getPageNo(){
		if(getTotalPages()<pageNo){
			return this.pageNo = getTotalPages();
		}
		return this.pageNo;
	} 
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 设置总数据数
	 * @param totalPages
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public void setLastPageNo(int lastPageNo) {
		this.lastPageNo = lastPageNo;
	}

	public void setPreviousPageNo(int previousPageNo) {
		this.previousPageNo = previousPageNo;
	}

	public void setNextPageNo(int nextPageNo) {
		this.nextPageNo = nextPageNo;
	}

	/**
	 * 获取总页码
	 */
	public int getTotalPages(){
		if(this.totalPages<=0){
			return this.totalPages=1;
		}
		return this.totalPages;
		 
		 
	}
	
	/**
	 * 设置记录总数
	 */
	public void setTotalRecords(int totalRecords){
		this.totalRecords = totalRecords;
	}
	
	/**
	 * 获取记录总数
	 */
	public int getTotalRecords(){
		return this.totalRecords;
	}
 
	/**
	 * 获取首页页码
	 */
	public int getIndexPageNo(){
		return 1;
	}
	@SuppressWarnings("unused")
	private int lastPageNo;
	/**
	 * 获取尾页页码
	 */
	public int getLastPageNo(){
		return this.getTotalPages();
	}
	@SuppressWarnings("unused")
	private int previousPageNo;
	/**
	 * 获取上一页页码
	 */
	public int getPreviousPageNo(){
		if(this.pageNo <= 1){
			return 1;
		}else{
			return this.pageNo-1;
		}
	}
	@SuppressWarnings("unused")
	private int nextPageNo;
	/**
	 * 获取下一页页码
	 */
	public int getNextPageNo(){
		if(this.pageNo >= this.getTotalPages()){
			return this.getTotalPages();
		}else{
			return this.pageNo+1;
		}
	}
 
	/**
	 * 处理分页参数
	 * 
	 * @param paramMap
	 * @param pageHelper
	 */
	public void initPage(Map<String, Object> map) {
		if (null != map) {
			setPageSize(map);
			this.totalPages = this.totalRecords%this.pageSize==0?this.totalRecords/this.pageSize:(this.totalRecords/this.pageSize+1); 
			setPageNo(map);  
			int startnum = (getPageNo() - 1) * getPageSize(); // 分页查询开始的序号
			map.put("startnum", startnum);
			map.put("rownum", getPageSize());
		}
	}
}
