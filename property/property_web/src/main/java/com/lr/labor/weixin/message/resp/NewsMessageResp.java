package com.lr.labor.weixin.message.resp;

import java.util.List;

import com.lr.labor.weixin.message.BaseMessageResp;

public class NewsMessageResp extends BaseMessageResp {
	private int ArticleCount;
	private List<Article> Articles;
	private Integer FuncFlag;
	public Integer getFuncFlag() {
		return FuncFlag;
	}
	public void setFuncFlag(Integer funcFlag) {
		FuncFlag = funcFlag;
	}
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<Article> getArticles() {
		return Articles;
	}
	public void setArticles(List<Article> articles) {
		Articles = articles;
	}	
}
