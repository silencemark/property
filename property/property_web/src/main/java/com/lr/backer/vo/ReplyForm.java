package com.lr.backer.vo;

import java.util.List;

/**
 * 自定义回复表单
 * 
 * @author robin88
 *
 */
public class ReplyForm {

	private String ruleid;
	private String rulename;
	private String replytype;
	private String replycontent;

	/**
	 * 关键字的集合
	 */
	private List<KeyWord> keyWordList;

	public List<KeyWord> getKeyWordList() {
		return keyWordList;
	}

	public void setKeyWordList(List<KeyWord> keyWordList) {
		this.keyWordList = keyWordList;
	}

	public String getRuleid() {
		return ruleid;
	}

	public void setRuleid(String ruleid) {
		this.ruleid = ruleid;
	}

	public String getRulename() {
		return rulename;
	}

	public void setRulename(String rulename) {
		this.rulename = rulename;
	}

	public String getReplytype() {
		return replytype;
	}

	public void setReplytype(String replytype) {
		this.replytype = replytype;
	}

	public String getReplycontent() {
		return replycontent;
	}

	public void setReplycontent(String replycontent) {
		this.replycontent = replycontent;
	}

}
