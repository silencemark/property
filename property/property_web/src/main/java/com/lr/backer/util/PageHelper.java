package com.lr.backer.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PageHelper {
	private String output;
	private int pageCount = 0;

	private int currentPage = 1;

	private int totalCount = 0;

	private int rowPage = 10;

	private HttpServletRequest request = null;

	private int begin = 0;
	private int end = 0;
	Map<String, Object> params = new HashMap<String, Object>();
	PagerVO page = new PagerVO();
	private String form = "pageForm";
	String linkUrl = "";
	String param = "";

	public PageHelper() {
	}

	public PageHelper(HttpServletRequest request) {
		setRequest(request);
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
		this.linkUrl = UrlUtil.getCurUri(request);
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		init();
	}
	
	public void setTotalCount4(int totalCount) {
		this.totalCount = totalCount;
		init4();
	}

	private void init() {
		this.linkUrl = UrlUtil.getCurUri(this.request);

		if (this.linkUrl.indexOf("?") == -1) {
			this.linkUrl += "?currentPage=";
			this.param = "1";
		} else {
			int pos = this.linkUrl.indexOf("currentPage");
			if (pos == -1) {
				this.linkUrl = this.linkUrl.substring(0,
						this.linkUrl.indexOf("?"));
				this.linkUrl += "?currentPage=";
				this.param = "1";
			} else {
				this.linkUrl = this.linkUrl.substring(0, pos + 12);
				this.param = this.request.getParameter("currentPage");
			}

		}

		this.pageCount = (this.totalCount % this.rowPage > 0 ? this.totalCount
				/ this.rowPage + 1 : this.totalCount / this.rowPage);

		if ((this.request.getParameter("currentPage") != null)
				&& (!"".equals(this.request.getParameter("currentPage")))
				&& (!"null".equals(this.request.getParameter("currentPage")))) {
			try {
				this.currentPage = Integer.parseInt(this.request
						.getParameter("currentPage"));
			} catch (Exception e) {
				this.currentPage = this.pageCount;
			}

		}

		if (this.currentPage > this.pageCount) {
			this.currentPage = this.pageCount;
		}

		if (this.currentPage <= 0) {
			this.currentPage = 1;
		}

		if (this.currentPage > 1) {
			this.begin = (this.rowPage * (this.currentPage - 1));
		}

		StringBuffer paramBuf = new StringBuffer();
		Enumeration enumeration = this.request.getParameterNames();
		paramBuf.append("<form name='" + this.form + "' action='"
				+ this.linkUrl + this.param + "' method='post'>");
		while (enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			String value = ParamUtil.getParameter(this.request, name);

			this.params.put(name, value);
			if (!name.equals("currentPage")) {
				paramBuf.append("<input id='" + name + "' type='hidden' name='"
						+ name + "' value='" + value + "'>\n");
			}
		}
		paramBuf.append("</form>");
		this.output = paramBuf.toString();
		this.page.setFormStr(this.output);
	}
	
	public void init4() {
		this.linkUrl = UrlUtil.getCurUri(this.request);

		if (this.linkUrl.indexOf("?") == -1) {
			this.linkUrl += "?currentPage=";
			this.param = "1";
		} else {
			int pos = this.linkUrl.indexOf("currentPage");
			if (pos == -1) {
				this.linkUrl = this.linkUrl.substring(0,
						this.linkUrl.indexOf("?"));
				this.linkUrl += "?currentPage=";
				this.param = "1";
			} else {
				this.linkUrl = this.linkUrl.substring(0, pos + 12);
				this.param = this.request.getParameter("currentPage");
			}

		}

		this.pageCount = (this.totalCount % this.rowPage > 0 ? this.totalCount
				/ this.rowPage + 1 : this.totalCount / this.rowPage);

		if ((this.request.getParameter("currentPage") != null)
				&& (!"".equals(this.request.getParameter("currentPage")))
				&& (!"null".equals(this.request.getParameter("currentPage")))) {
			try {
				this.currentPage = Integer.parseInt(this.request
						.getParameter("currentPage"));
			} catch (Exception e) {
				this.currentPage = this.pageCount;
			}

		}

		if (this.currentPage > this.pageCount) {
			this.currentPage = this.pageCount;
		}

		if (this.currentPage <= 0) {
			this.currentPage = 1;
		}

		if (this.currentPage > 1) {
			this.begin = (this.rowPage * (this.currentPage - 1));
		}

		StringBuffer paramBuf = new StringBuffer();
		Enumeration enumeration = this.request.getParameterNames();
		this.linkUrl.replaceFirst("/","");
		paramBuf.append("<form id='" + this.form + "' action='"
				+ this.linkUrl + this.param + "' method='get'>");
//		while (enumeration.hasMoreElements()) {
//			String name = (String) enumeration.nextElement();
//			String value = ParamUtil.getParameter(this.request, name);
//
//			this.params.put(name, value);
//			if (!name.equals("currentPage")) {
//				paramBuf.append("<input id='" + name + "' type='hidden' name='"
//						+ name + "' value='" + value + "'>\n");
//			}
//			paramBuf.append("<input id='currentPage' type='hidden' name='currentPage'>");
//		}
		paramBuf.append("<input id='currentPage' type='hidden' name='currentPage'>");
		paramBuf.append("</form>");
		this.output = paramBuf.toString();
		this.page.setFormStr(this.output);
	}

	public PagerVO paginate() {
		StringBuffer rt = new StringBuffer();

		rt.append("&nbsp;&nbsp;<SPAN class='current'>共有&nbsp;"
				+ this.totalCount + "&nbsp;条记录");
		if (this.pageCount > 0) {
			rt.append(",&nbsp;当前第&nbsp;" + this.currentPage + "/"
					+ this.pageCount + "&nbsp;页&nbsp;</span>");
			rt.append("&nbsp;");
			if ((this.currentPage > 1) && (this.pageCount > 1)) {
				rt.append("<a class='list_link' href=\"");
				rt.append("#");
				rt.append("\" onclick=\"javascript:document." + this.form
						+ ".action='" + this.linkUrl + "1';document."
						+ this.form + ".submit();return false;\">");
			}
			rt.append("&lt;&lt;首页");
			if ((this.currentPage > 1) && (this.pageCount > 1)) {
				rt.append("</a>");
			}
			rt.append("&nbsp;");
			if ((this.currentPage > 1) && (this.pageCount > 1)) {
				rt.append("<a class='list_link' href=\"");
				rt.append("#");
				rt.append("\" onclick=\"javascript:document." + this.form
						+ ".action='" + this.linkUrl + (this.currentPage - 1)
						+ "';document." + this.form
						+ ".submit();return false;\">");
			}
			rt.append("&lt;上一页");
			if ((this.currentPage > 1) && (this.pageCount > 1)) {
				rt.append("</a>");
			}
			rt.append("&nbsp;");
			if (this.currentPage < this.pageCount) {
				rt.append("<a class='list_link' href=\"");
				rt.append("#");
				rt.append("\" onclick=\"javascript:document." + this.form
						+ ".action='" + this.linkUrl + (this.currentPage + 1)
						+ "';document." + this.form
						+ ".submit();return false;\">");
			}
			rt.append("下一页&gt;");
			if (this.currentPage < this.pageCount) {
				rt.append("</a>");
			}
			rt.append("&nbsp;");
			if (this.currentPage < this.pageCount) {
				rt.append("<a class='list_link' href=\"");
				rt.append("#");
				rt.append("\" onclick=\"javascript:document." + this.form
						+ ".action='" + this.linkUrl + this.pageCount
						+ "';document." + this.form
						+ ".submit();return false;\">");
			}
			rt.append("尾页&gt;&gt;");
			if (this.currentPage < this.pageCount) {
				rt.append("</a>");
			}

			rt.append("&nbsp;");
			rt.append("转到<input id='pagerSelect' name='pagerSelect'  style=\"width:5;\" class=\"intext\" size=\"1\">页");
			rt.append("<input type='button' value='go'  style=\"width:30;\" ");
			rt.append("\" onclick=\"pagego();\"");
			rt.append(">");
			rt.append("<script type=\"text/javascript\">");
			rt.append("function pagego(){document." + this.form + ".action='"
					+ this.linkUrl
					+ "'+document.getElementById(\"pagerSelect\").value;"
					+ "document." + this.form + ".submit();" + "}");
			rt.append("</script>");
		}
		this.page.setPageStr(rt.toString());

		return this.page;
	}

	public PagerVO paginate1() {
		StringBuffer rt = new StringBuffer();

		rt.append("<div class=\"row area-table-bar\"><div class=\"area-pager\">");
		rt.append("&nbsp;&nbsp;<SPAN class='current'>共有&nbsp;"
				+ this.totalCount + "&nbsp;条记录&nbsp;&nbsp;");
		rt.append("<span>第" + this.currentPage + "/" + this.pageCount
				+ "页</span>");

		if (this.pageCount > 0) {
			rt.append(" <ul class=\"pagination pagination-sm\">");

			//首页
			rt.append("&nbsp;");
			if ((this.currentPage > 1) && (this.pageCount > 1)) {
				rt.append("<li><a href=\"");
				rt.append("#");
				rt.append("\" onclick=\"javascript:document." + this.form
						+ ".action='" + this.linkUrl + "1';document."
						+ this.form + ".submit();return false;\">");
			} else {
				rt.append("<li><a  class=\"disabled\">");
			}

			rt.append("首页");
			rt.append("</a></li>");
			rt.append("&nbsp;");
			
			// 上一页
			if ((this.currentPage > 1) && (this.pageCount > 1)) {
				rt.append("<li><a href=\"");
				rt.append("#");
				rt.append("\" onclick=\"javascript:document." + this.form
						+ ".action='" + this.linkUrl + (this.currentPage - 1)
						+ "';document." + this.form
						+ ".submit();return false;\">");
			} else {
				rt.append("<li><a  class=\"disabled\">");
			}

			rt.append("上一页");
			if ((this.currentPage > 1) && (this.pageCount > 1))
				rt.append("</a></li>");
			else {
				rt.append("</a></li>");
			}
			// 当前页
			rt.append(" <li class=\"active\"><a href=\"\">" + this.currentPage
					+ "</a></li>");

			// 下一页
			if (this.currentPage < this.pageCount) {
				rt.append("<li><a href=\"");
				rt.append("#");
				rt.append("\" onclick=\"javascript:document." + this.form
						+ ".action='" + this.linkUrl + (this.currentPage + 1)
						+ "';document." + this.form
						+ ".submit();return false;\">");
			} else {
				rt.append("<li><a  class=\"disabled\">");
			}
			rt.append("下一页");
			if (this.currentPage < this.pageCount)
				rt.append("</a></li>");
			else {
				rt.append("</a></li>");
			}
			//尾页
			rt.append("&nbsp;");
			if (this.currentPage < this.pageCount) {
				rt.append("<li><a  href=\"");
				rt.append("#");
				rt.append("\" onclick=\"javascript:document." + this.form
						+ ".action='" + this.linkUrl + this.pageCount
						+ "';document." + this.form
						+ ".submit();return false;\">");
			} else {
				rt.append("<li><a  class=\"disabled\">");
			}
			rt.append("尾页");
			rt.append("</a></li>");
			
			rt.append(" </ul>");
			rt.append("<div class=\"pagination-jump\">");
			// 跳转页面
			rt.append("<input id='pagerSelect' name='pagerSelect'  class=\"form-control\"/>");
			rt.append("<input type='button' value='跳转' class=\"btn\"");
			rt.append("\" onclick=\"pagego();\"");
			rt.append("/>");
			rt.append("<script type=\"text/javascript\">");
			rt.append("function pagego(){document." + this.form + ".action='"
					+ this.linkUrl
					+ "'+document.getElementById(\"pagerSelect\").value;"
					+ "document." + this.form + ".submit();" + "}");
			rt.append("</script>");

			rt.append("</div>");
			rt.append("</div>");
		}  
		this.page.setPageStr(rt.toString());

		return this.page;
	}

	/**
	 * 患者,医生PC端分页
	 * @return
	 */
	public PagerVO paginate2(){
		StringBuffer rt = new StringBuffer();
		
		if (this.pageCount > 0) {
		rt.append("<div class=\"page_box\">");
		rt.append("<div class=\"box\">");
		rt.append("共有&nbsp;" + this.totalCount + "&nbsp;条信息 当前第"+ this.currentPage + "/" + this.pageCount  +"页&nbsp;&nbsp;&nbsp;");
		//首页 
		if ((this.currentPage > 1) && (this.pageCount > 1)) {
			rt.append("<a href=\"");
			rt.append("#");
			rt.append("\" onclick=\"javascript:document." + this.form
					+ ".action='" + this.linkUrl + "1';document."
					+ this.form + ".submit();return false;\">");
		} else {
			rt.append("<a  class=\"none\">");
		} 
		rt.append("首页</a>&nbsp;"); 
		
		// 上一页
		if ((this.currentPage > 1) && (this.pageCount > 1)) {
			rt.append("<a href=\"");
			rt.append("#");
			rt.append("\" onclick=\"javascript:document." + this.form
					+ ".action='" + this.linkUrl + (this.currentPage - 1)
					+ "';document." + this.form
					+ ".submit();return false;\">");
		} else {
			rt.append("<a  class=\"none\">");
		}
		rt.append("&lt; 上一页</a>&nbsp;");
		
		List<Integer> list = paging(); 
		for(Integer o:list){
			// 当前页 this.currentPage
			if(this.currentPage==o){
				rt.append("<a href=\"javascript:void(0);\"  class=\"active\"");
			}else{
				rt.append("<a href=\"javascript:void(0);\"  class=\"none\"");
			}
			rt.append(" onclick=\"javascript:document." + this.form
					+ ".action='" + this.linkUrl + (o)
					+ "';document." + this.form
					+ ".submit();return false;\">");
			rt.append(o+"</a>&nbsp;");
		} 
		// 下一页
		if (this.currentPage < this.pageCount) {
			rt.append("<a href=\"");
			rt.append("#");
			rt.append("\" onclick=\"javascript:document." + this.form
					+ ".action='" + this.linkUrl + (this.currentPage + 1)
					+ "';document." + this.form
					+ ".submit();return false;\">");
		} else {
			rt.append("<a  class=\"none\">");
		} 
		rt.append("下一页 &gt;</a>&nbsp;");
		
		//尾页 
		if (this.currentPage < this.pageCount) {
			rt.append("<a  href=\"");
			rt.append("#");
			rt.append("\" onclick=\"javascript:document." + this.form
					+ ".action='" + this.linkUrl + this.pageCount
					+ "';document." + this.form
					+ ".submit();return false;\">");
		} else {
			rt.append("<a  class=\"none\">");
		}
		rt.append("尾页</a>&nbsp;");
		
		// 跳转页面
		rt.append("<span><input id='pagerSelect' name='pagerSelect' value=\""+ this.currentPage +"\" style=\" border:1px solid #ccc; background:#fff; margin-left:-1px;width: 30px; height: 30px; margin-bottom: 4px; text-align: center\"/></span>");
		//rt.append("<input id='pagerSelect' name='pagerSelect' value=\""+ this.currentPage +"\" style=\"width: 30px; height: 34px; margin-bottom: 4px; text-align: center\"/>&nbsp;");
		rt.append("<a href='javascript:void(0);'");
		rt.append("\" onclick=\"pagego();\"");
		rt.append("/>跳转</a>&nbsp;");
		
		rt.append("<script type=\"text/javascript\">");
		rt.append("function pagego(){document." + this.form + ".action='"
				+ this.linkUrl
				+ "'+document.getElementById(\"pagerSelect\").value;"
				+ "document." + this.form + ".submit();" + "}");
		rt.append("</script>");

		rt.append("</div>");
		rt.append("</div>");
		
		}
		
		this.page.setPageStr(rt.toString());

		return this.page;
	}
	
	/**
	 * pc前端
	 * @return
	 */
	public PagerVO paginate3(){
		StringBuffer rt = new StringBuffer();
		
		//上一页 当前页大于1时出现
		if ((this.currentPage > 1) && (this.pageCount > 1)) {
			rt.append("<a href=\"javascript:void(0)\" onclick=\"$('#currentPage').val("+(this.currentPage-1)+");$('#searchForm').submit();\">&lt; 上一页</a>");
		}
		
		//中间一共出现九条 
		int index = 9;
		int j = 6;
		if(this.currentPage<j){
			j = this.currentPage;
		}
		
		for(int i = j-1;i>0;i--){
			rt.append("<a href=\"javascript:void(0)\" onclick=\"$('#currentPage').val("+(this.currentPage-i)+");$('#searchForm').submit();\">"+(this.currentPage-i)+"</a>");
			index--;
		}
		
		if(this.getTotalCount()>0){
			//当前页
			rt.append("<a href=\"javascript:void(0)\" onclick=\"$('#currentPage').val("+this.currentPage+");$('#searchForm').submit();\"><span class=\"current\">"+this.currentPage+"</span></a>");
		}else{
			rt.append("暂无相关数据");
		}

		//后面
		for(int i = 1;i< index;i++){
			if(this.currentPage+i <= this.pageCount){
				rt.append("<a href=\"javascript:void(0)\" onclick=\"$('#currentPage').val("+(this.currentPage+i)+");$('#searchForm').submit();\">"+(this.currentPage+i)+"</a>");
			}
		}
		
		//下一页 当前页小于总页数时出现
		
		if ((this.currentPage < this.pageCount )) {
			rt.append("<a href=\"javascript:void(0)\" onclick=\"$('#currentPage').val("+(this.currentPage+1)+");$('#searchForm').submit();\">&gt; 下一页</a>");
		}
		
		this.page.setPageStr(rt.toString());

		return this.page;
	}
	
	public PagerVO paginate4(){
		StringBuffer rt = new StringBuffer();
		
		//上一页 当前页大于1时出现
		if ((this.currentPage > 1) && (this.pageCount > 1)) {
			rt.append("<a href=\"javascript:void(0)\" onclick=\"$('#currentPage').val("+(this.currentPage-1)+");$('#pageForm').submit();\">&lt; 上一页</a>");
		}
		
		//中间一共出现九条 
		int index = 9;
		int j = 6;
		if(this.currentPage<j){
			j = this.currentPage;
		}
		
		for(int i = j-1;i>0;i--){
			rt.append("<a href=\"javascript:void(0)\" onclick=\"$('#currentPage').val("+(this.currentPage-i)+");$('#pageForm').submit();\">"+(this.currentPage-i)+"</a>");
			index--;
		}
		
		if(this.getTotalCount()>0){
			//当前页
			rt.append("<a href=\"javascript:void(0)\" onclick=\"$('#currentPage').val("+this.currentPage+");$('#pageForm').submit();\"><span class=\"current\">"+this.currentPage+"</span></a>");
		}else{
			rt.append("暂无相关数据");
		}

		//后面
		for(int i = 1;i< index;i++){
			if(this.currentPage+i <= this.pageCount){
				rt.append("<a href=\"javascript:void(0)\" onclick=\"$('#currentPage').val("+(this.currentPage+i)+");$('#pageForm').submit();\">"+(this.currentPage+i)+"</a>");
			}
		}
		
		//下一页 当前页小于总页数时出现
		
		if ((this.currentPage < this.pageCount )) {
			rt.append("<a href=\"javascript:void(0)\" onclick=\"$('#currentPage').val("+(this.currentPage+1)+");$('#pageForm').submit();\">&gt; 下一页</a>");
		}
		
		this.page.setPageStr(rt.toString());

		return this.page;
	}
	
	
	public void setPages(int rowPage, HttpServletRequest request, String form) {
		this.rowPage = rowPage;
		this.request = request;
		this.form = form;
	}

	public int getBegin() {
		return this.begin;
	}

	public int getRowPage() {
		return this.rowPage;
	}

	public void setRowPage(int rowPage) {
		this.rowPage = rowPage;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public String getParameter(String par) {
		return this.request.getParameter(par);
	}

	public HttpSession getSession() {
		return this.request.getSession();
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getIndexSer() {
		return (getCurrentPage() - 1) * this.rowPage + 1;
	}

	public int getEnd() {
		this.end = (getRowPage() + getBegin());
		this.end = (this.end > getTotalCount() ? getTotalCount() : this.end);
		return this.end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public List<Integer> paging(){
		int no = currentPage;
		int total = pageCount;
		List<Integer> list = new ArrayList<Integer>();
		int q = 2;
		if(total-no<3){
			q=total-no;
		}
		if(no-1<3){
			q = 4-(no-1);
		}
		for(int i=no;i>=no-(4-q);i--){
			list.add(i);
		}
		for(int i=no;i<=no+(q);i++){
			list.add(i);
		}
		
		//去重  去除错误数据
		List<Integer> tempList= new ArrayList<Integer>();  
	    for(Integer i:list){  
	        if((i>=1&&i<=total)&&!tempList.contains(i)){  
	            tempList.add(i);  
	        }  
	    }
	    //排序
	    
	    Collections.sort(tempList);
		for(Integer o:tempList){
			System.out.print(o+"\t");
		}
		return tempList;
	}
	
	/**
	 * 处理分页参数
	 * 
	 * @param paramMap
	 * @param pageHelper
	 */
	public void initPage(Map<String, Object> paramMap) {
		if (null != paramMap) {
			rowPage = this.getRowPage(); // 每页显示的记录
			currentPage = paramMap.get("currentPage") == null
					|| paramMap.get("currentPage").equals("") ? 1 : Integer
					.parseInt(paramMap.get("currentPage").toString());//
			if (currentPage < 1) {
				currentPage = 1;
			}
			if (rowPage < 1)
				rowPage = 1;
			int startnum = (currentPage - 1) * rowPage; // 分页查询开始的序号
			paramMap.put("startnum", startnum);
			paramMap.put("rownum", rowPage);
		}
	}
	
	/**
	 * 处理分页参数
	 * 
	 * @param paramMap
	 * @param rowPage
	 */
	public void initPage(Map<String, Object> paramMap,Integer rowPage) {
		if (null != paramMap) { 
			if(rowPage==null){
				rowPage = 1;
			}
			paramMap.put("startnum", 0);
			paramMap.put("rownum", rowPage);
		}
	}

}
