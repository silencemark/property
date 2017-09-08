package com.lr.backer.util;
/**
 * @author 韦守勤 E-mail:wsq_xw@163.com
 * 创建时间：Apr 24, 2009 3:26:53 PM
 */
public class PageQuery
{ 
	public static final int page_size = 25;//每页显示的记录数
	private int	curpage=1;//当前页
	private int allpage=0;//总页数
	private int allcount=0;//总纪录数
	
	//新闻列表每页显示多少条新闻
	public static final int newspage_size = 20;//每页显示的记录数
	public static final int indexpage_size = 10;//直播页面显示条数
	private int newscurpage=1;//当前页
	private int newsallpage=0;//总页数
	private int newsallcount=0;//总记录数
	/**
	 * 构造函数用来初始化当前分页信息
	 * @param allcount 总记录数
	 * @param curpage  当前页
	 */
	public PageQuery(int allcount,int curpage){
		 this.allpage=(allcount+page_size-1)/page_size;
	     if(curpage>allpage&&allpage>0)
	     {
	        this.curpage=allpage;
	     }
	     else if(curpage<1)
	     {
	           curpage=1;
	     }
	     else
	     {
	           this.curpage=curpage;
	     }
	     this.allcount=allcount;
	}
	
	public PageQuery(int allcount,int curpage,String strsiza){
		 this.allpage=(allcount+newspage_size-1)/newspage_size;
	     if(curpage>allpage&&allpage>0)
	     {
	        this.curpage=allpage;
	     }
	     else if(curpage<1)
	     {
	           curpage=1;
	     }
	     else
	     {
	           this.curpage=curpage;
	     }
	     this.allcount=allcount;
	}
	
	public PageQuery(int newsallcount,String newscurpages){
		 this.newscurpage=Integer.parseInt(newscurpages);
		 this.newsallpage=(newsallcount+newspage_size-1)/newspage_size;
	     if(newscurpage>newsallpage&&newsallpage>0)
	     {
	        this.curpage=newsallpage;
	     }
	     else if(curpage<1)
	     {
	           curpage=1;
	     }
	     else
	     {
	           this.newscurpage=Integer.parseInt(newscurpages);
	     }
	     this.newsallcount=newsallcount;
	}
	
	public PageQuery(int allcount, int curpage, int pageSize) {
		this.allpage = (allcount + pageSize - 1) / pageSize;
		if (curpage > allpage && allpage > 0) {
			this.curpage = allpage;
		} else if (curpage < 1) {
			curpage = 1;
		} else {
			this.curpage = curpage;
		}
		this.allcount = allcount;
	}
	
	public PageQuery(){}
	
	public int getCurpage() {
		return curpage;
	}

	public void setCurpage(int curpage) {
		this.curpage = curpage;
	}

	public int getAllpage() {
		return allpage;
	}

	public void setAllpage(int allpage) {
		this.allpage = allpage;
	}

	public int getAllcount() {
		return allcount;
	}

	public void setAllcount(int allcount) {
		this.allcount = allcount;
	}
	
	/**
	 * 该方法返回分页信息
	 * @author 韦鹏
	 * @param mothodName 调用方法的名称
	 * Apr 28, 2009
	 * @return String
	 */
	public String htmlPage(String mothodName,String parame)
	{
		StringBuffer html=new StringBuffer();
    	html.append("总计："+allcount+" 条记录&nbsp;");
    	html.append("总："+allpage+" 页&nbsp;");
    	html.append(" 当前第"+curpage+"页&nbsp; ");
    	html.append(" &nbsp;<a href=?method="+mothodName+parame+"&pages=1>首页</a> ");
    	if(curpage>1)
    	html.append(" <a href=?method="+mothodName+parame+"&pages="+(curpage-1)+">上一页</a> ");
    	if(curpage<allpage)
    	html.append(" <a href=?method="+mothodName+parame+"&pages="+(curpage+1)+">下一页</a> ");
    	html.append(" <a href=?method="+mothodName+parame+"&pages="+allpage+">尾页</a> ");
    	return html.toString();
	}

	/**
	 * 个股咨询分页
	 * @param mothodName
	 * @param currentPage
	 * @return
	 */
	public String stockHtmlPage(String mothodName,String currentPage,Integer all_Page)
	{
		StringBuffer html=new StringBuffer();
    	html.append("总计："+allcount+" 条记录&nbsp;");
    	html.append("总："+all_Page+" 页&nbsp;");
    	html.append(" 当前第"+currentPage+"页&nbsp; ");
    	html.append(" &nbsp;<a href=?method="+mothodName+"&pages=1>首页</a> ");
    	if(!currentPage.equals("1")){
    		html.append(" <a href=?method="+mothodName+"&pages="+(Integer.valueOf(currentPage)-1)+">上一页</a> ");
    	}
    	if(!currentPage.equals(all_Page.toString())){
    		html.append(" <a href=?method="+mothodName+"&pages="+(Integer.valueOf(currentPage)+1)+">下一页</a> ");
    	}
    	html.append(" <a href=?method="+mothodName+"&pages="+all_Page+">尾页</a> ");
    	return html.toString();
	}
	
	public String pageNewsList(int curnumber,int allpages,String pagename){
		StringBuffer html=new StringBuffer();
		html.append("<a href=\""+pagename+1+".wml\">首页</a>&nbsp;");
		if(curnumber!=1)
		html.append("<a href=\""+pagename+(curnumber-1)+".wml\">上一页</a>&nbsp;");
		if(curnumber!=allpages){
		html.append("<a href=\""+pagename+(curnumber+1)+".wml\">下一页</a>&nbsp;");
		}
		html.append("<a href=\""+pagename+allpages+".wml\">尾页</a>&nbsp;");
		return html.toString();
	}
	
	public String pageNewsList2(String pagename,int allpage){
		StringBuffer html=new StringBuffer();
    	html.append("总:"+allpage+"页 ");
    	html.append("  当前第"+newscurpage+"页 <br/>");
		html.append("<a href=\""+1+".wml\">首页</a>");
		if(newscurpage!=1)
		html.append("<a href=\""+(newscurpage-1)+".wml\">上一页</a>");
		if(newscurpage!=newsallpage){
		html.append("<a href=\""+(newscurpage+1)+".wml\">下一页</a>");
		}
		html.append("<a href=\""+newsallpage+".wml\">尾页</a> ");
		return html.toString();
	}
	
	public String pageNewsListhtml(String pagename,int allpage){
		StringBuffer html=new StringBuffer();
    	html.append("共<span class=\"total-page\">"+allpage+"页 ");
    	html.append("</span>页 当前第<span class=\"current-page\">"+newscurpage+"页 <br/>");
		html.append("<a href=\""+1+".html\">首页</a>");
		if(newscurpage!=1)
		html.append("<a href=\""+(newscurpage-1)+".html\">上一页</a>");
		if(newscurpage!=newsallpage){
		html.append("<a href=\""+(newscurpage+1)+".html\">下一页</a>");
		}
		html.append("<a href=\""+newsallpage+".html\">尾页</a> ");
		return html.toString();
	}
	
	public String pageNewsList4(String pagename,int allpage){
		StringBuffer html=new StringBuffer();
    	html.append("总:"+allpage+"页 ");
    	html.append("  当前第"+newscurpage+"页 <br/>");
		html.append("<a href=\""+1+".wml\">首页</a><br/>");
		if(newscurpage!=1)
		html.append("<a href=\""+(newscurpage-1)+".wml\">上一页</a><br/>");
		if(newscurpage!=newsallpage){
		html.append("<a href=\""+(newscurpage+1)+".wml\">下一页</a><br/>");
		}
		html.append("<a href=\""+newsallpage+".wml\">尾页</a> ");
		return html.toString();
	}
	
	public String pageNewsList3(String pagename,int allpage,String filename){
		StringBuffer html=new StringBuffer();
    	html.append("总:"+allpage+"页 ");
    	html.append("  当前第"+newscurpage+"页 <br/>");
		html.append("<a href=\""+1+filename+".wml\">首页</a>");
		if(newscurpage!=1)
		html.append("<a href=\""+(newscurpage-1)+filename+".wml\">上一页</a>");
		if(newscurpage!=newsallpage){
		html.append("<a href=\""+(newscurpage+1)+filename+".wml\">下一页</a>");
		}
		html.append("<a href=\""+newsallpage+filename+".wml\">尾页</a> ");
		return html.toString();
	}
	//个股公司新闻分页信息
	public String pageNewsInfo(String pagename,int allpage){
		StringBuffer html=new StringBuffer();
    	html.append("总:"+allpage+"页 ");
    	html.append("  当前第"+newscurpage+"页 <br/>");
		html.append("<a href=\"index."+pagename+"\">首页</a>");
		if(newscurpage!=1&&newscurpage!=2){
			html.append("<a href=\""+(newscurpage-1)+"."+pagename+"\">上一页</a>");
		}else if(newscurpage==2){
			html.append("<a href=\"index."+pagename+"\">上一页</a>");
		}
		if(newscurpage!=newsallpage){
		html.append("<a href=\""+(newscurpage+1)+"."+pagename+"\">下一页</a>");
		}
		if(newsallpage!=1){
			html.append("<a href=\""+newsallpage+"."+pagename+"\">尾页</a> ");
		}else{
			html.append("<a href=\"index."+pagename+"\">尾页</a> ");
		}
		return html.toString();
	}
	//个股公告新闻分页信息
	public String pageNewsInfogg(int cupage,int allpage,String path){
		StringBuffer html=new StringBuffer();
    	html.append("总:"+allpage+"页 ");
    	html.append("  当前第"+cupage+"页 <br/>");
		html.append("<a href=\""+path+"tide.wml\">首页</a>");
		if(cupage!=1&&cupage!=2){
			html.append("<a href=\""+path+(cupage-1)+"gd.wml\">上一页</a>");
		}else if(cupage==2){
			html.append("<a href=\""+path+"tide.wml\">上一页</a>");
		}
		if(cupage!=allpage){
		html.append("<a href=\""+path+(cupage+1)+"gd.wml\">下一页</a>");
		}
		if(cupage<allpage){
			html.append("<a href=\""+path+allpage+"gd.wml\">尾页</a> ");
		}else if(allpage==1){
			html.append("<a href=\""+path+"tide.wml\">尾页</a> ");
		}
		return html.toString();
	}
	//iphone版分页
	public String pageNewsInfogg_html(int cupage,int allpage){
		StringBuffer html=new StringBuffer();
    	html.append("总:"+allpage+"页 ");
    	html.append("  当前第"+cupage+"页 <br/>");
		html.append("<a href=\"tide.html\">首页</a>&nbsp;");
		if(cupage!=1&&cupage!=2){
			html.append("<a href=\""+(cupage-1)+"gd.html\">上一页</a>&nbsp;");
		}else if(cupage==2){
			html.append("<a href=\"tide.html\">上一页</a>&nbsp;");
		}
		if(cupage!=allpage){
		html.append("<a href=\""+(cupage+1)+"gd.html\">下一页</a>&nbsp;");
		}
		if(cupage<allpage){
			html.append("<a href=\""+allpage+"gd.html\">尾页</a> ");
		}else if(allpage==1){
			html.append("<a href=\"tide.html\">尾页</a> ");
		}
		return html.toString();
	}
	//ipad分页
	public String getPageIpadHtml() {
		StringBuffer html = new StringBuffer();
		html.append("总:" + allpage + "页 ");
		html.append("  当前第" + curpage + "页 &nbsp;");
		html.append("<a href=\"" + 1 + ".html\">首页</a>");
		if (curpage != 1) {
			html.append("<a href=\"" + (curpage - 1)+ ".html\">上一页</a>");
		}
		if (curpage != allpage) {
			html.append("<a href=\"" + (curpage + 1) + ".html\">下一页</a>");
		}
		html.append("<a href=\"" + allpage + ".html\">尾页</a> ");
		return html.toString();
	}

	public int getNewscurpage() {
		return newscurpage;
	}

	public void setNewscurpage(int newscurpage) {
		this.newscurpage = newscurpage;
	}

	public int getNewsallpage() {
		return newsallpage;
	}

	public void setNewsallpage(int newsallpage) {
		this.newsallpage = newsallpage;
	}

	public int getNewsallcount() {
		return newsallcount;
	}

	public void setNewsallcount(int newsallcount) {
		this.newsallcount = newsallcount;
	}

	public static int getPage_size() {
		return page_size;
	}

	public static int getNewspage_size() {
		return newspage_size;
	}
	
	public String getStkMsgPage(){
		StringBuffer html = new StringBuffer();
		html.append("总:" + allpage + "页   当前第" + curpage + "页<br/>");
		html.append("<a href=\"msgcenter.html\">首页</a>");
		if (curpage != 1) {
			if (curpage == 2) {
				html.append("<a href=\"msgcenter.html\">上一页</a>");
			} else {
				html.append("<a href=\"msgcenter" + (curpage - 1)+ ".html\">上一页</a>");
			}
		}
		if (curpage != allpage) {
			html.append("<a href=\"msgcenter" + (curpage + 1) + ".html\">下一页</a>");
		}
		html.append("<a href=\"msgcenter" + allpage + ".html\">尾页</a></span>");
		return html.toString();
	}
}

