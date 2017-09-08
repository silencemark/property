package com.lr.backer.util;

public class PageUtil {
	private int showCount = 15; // 每页显示记录数
	private int totalPage; // 总页数
	private int totalResult; // 总记录数
	private int currentPage; // 当前页
	private int currentResult; // 当前记录起始索引
	private boolean entityOrField=true; // true:需要分页的地方，传入的参数就是Page实体；false:需要分页的地方，传入的参数所代表的实体拥有Page属性
	private String pageStr; // 最终页面显示的底部翻页导航，详细见：getPageStr();

	public int getTotalPage() {
		if (totalResult % showCount == 0)
			totalPage = totalResult / showCount;
		else
			totalPage = totalResult / showCount + 1;
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}

	public int getCurrentPage() {
		if (currentPage <= 0)
			currentPage = 1;
		if (currentPage > getTotalPage())
			currentPage = getTotalPage();
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getPageStr() {
		StringBuffer sb = new StringBuffer();
		if (totalResult > 0) {
			sb.append(" <ul>\n");
			if (currentPage == 1) {
				sb.append(" <td class=\"center\">首页&nbsp;&nbsp;&nbsp;&nbsp;上页");
			} else {
				sb.append(" <td class=\"center\"><a href=\"#@\" onclick=\"nextPage(1)\">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;");
				sb.append(" <a href=\"#@\" onclick=\"nextPage("
						+ (currentPage - 1) + ")\">上页</a>\n");
			}
			int showTag = 10; // 分页标签显示数量
			int startTag = 1;
			if (currentPage > showTag) {
				startTag = currentPage - 1;
			}
			int endTag = startTag + showTag - 1;
			for (int i = startTag; i <= totalPage && i <= endTag; i++) {
				if (currentPage == i)
					sb.append("&nbsp;&nbsp;" + i + "&nbsp;&nbsp;");
				else
					sb.append("&nbsp;&nbsp;<a href=\"#@\" onclick=\"nextPage("
							+ i + ")\">" + i + "</a>&nbsp;&nbsp;");
			}
			if (currentPage == totalPage) {
				sb.append("&nbsp;&nbsp;下页&nbsp;&nbsp;");
				sb.append("&nbsp;&nbsp;末页&nbsp;&nbsp;");
			} else {
				sb.append("&nbsp;&nbsp;<a href=\"#@\" onclick=\"nextPage("
						+ (currentPage + 1) + ")\">下页</a>&nbsp;&nbsp;");
				sb.append("&nbsp;&nbsp;<a href=\"#@\" onclick=\"nextPage("
						+ totalPage + ")\">末页</a>&nbsp;&nbsp;");
			}
			sb.append(" &nbsp;&nbsp;第" + currentPage + "页&nbsp;&nbsp;");
			sb.append(" &nbsp;&nbsp;共" + totalPage + "页&nbsp;&nbsp;</td>");
			sb.append("</ul>\n");
			sb.append("<script type=\"text/javascript\">\n");
			sb.append(" function nextPage(pageUtil){\n");
			sb.append("var tmpHPage = document.URL.split(\"/\");");
			sb.append("var url = tmpHPage[ tmpHPage.length-1 ];");
			sb.append("		if(url.indexOf('?')>-1){\n");

			sb.append("			if(url.indexOf('currentPage')>-1){\n");
			sb.append("				var reg = /cm.page.currentPage=\\d*/g;\n");
			sb.append("				url = url.replace(reg,'cm.page.currentPage=');\n");
			sb.append("			}else{\n");
			sb.append("				url += \"&"
					+ (entityOrField ? "cm.page.currentPage"
							: "cm.page.currentPage") + "=\";\n");
			sb.append("			}\n");
			sb.append("		}else{url += \"?"
					+ (entityOrField ? "cm.page.currentPage"
							: "cm.page.currentPage") + "=\";}\n");
			sb.append("		document.location = url + pageUtil;\n");

			sb.append("}\n");
			sb.append("</script>");
		}
		pageStr = sb.toString();
		return pageStr;
	}

	public void setPageStr(String pageStr) {
		this.pageStr = pageStr;
	}

	public int getShowCount() {
		return showCount;
	}

	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}

	public int getCurrentResult() {
		currentResult = (getCurrentPage() - 1) * getShowCount();
		if (currentResult < 0)
			currentResult = 0;
		return currentResult;
	}

	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}

	public boolean isEntityOrField() {
		return entityOrField;
	}

	public void setEntityOrField(boolean entityOrField) {
		this.entityOrField = entityOrField;
	}
}