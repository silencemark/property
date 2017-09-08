package com.lr.backer.util;

public class PUtil {
	private int pagesize; // 每页记录
	private int totalcount; // 总记录数
	private int totalpage; // 总页数
	private int currpage; // 当前页
	private int firstpage; // 起始页
	private int lastpage;// 尾页
	private int prepage;// 上一页
	private int nextpage;// 下一页
	private static PUtil instance = new PUtil();

	private PUtil() {
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public int getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}

	public int getCurrpage() {
		return currpage;
	}

	public void setCurrpage(int currpage) {
		this.currpage = currpage;
	}

	public int getFirstpage() {
		return firstpage;
	}

	public void setFirstpage(int firstpage) {
		this.firstpage = firstpage;
	}

	public int getLastpage() {
		return lastpage;
	}

	public void setLastpage(int lastpage) {
		this.lastpage = lastpage;
	}

	public int getPrepage() {
		return prepage;
	}

	public void setPrepage(int prepage) {
		this.prepage = prepage;
	}

	public int getNextpage() {
		return nextpage;
	}

	public void setNextpage(int nextpage) {
		this.nextpage = nextpage;
	}

	public static PUtil getInstance() {
		return instance;
	}

	public static PUtil getParam(int pagesize, int totalsize, int currpage) {
		PUtil page = PUtil.getInstance();
		page.setTotalcount(totalsize);
		int totalpage = totalsize > 0 ? (totalsize - 1) / pagesize + 1 : 0;
		page.setTotalpage(totalpage);
		page.setCurrpage(currpage);
		page.setFirstpage(totalsize > 0 ? 1 : 0);
		page.setLastpage(totalpage);
		page.setPrepage(currpage > 1 ? (currpage - 1) : 1);
		page.setNextpage(currpage < page.getLastpage() ? (currpage + 1) : page
				.getLastpage());
		return page;
	}

}