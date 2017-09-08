package com.lr.backer.util;

public class PagerVO {
	String pageStr = "";
	  String formStr = "";

	  public PagerVO()
	  {
	  }

	  public PagerVO(String pageStr, String formStr) {
	    this.pageStr = pageStr;
	    this.formStr = formStr;
	  }
	  public String getFormStr() {
	    return this.formStr;
	  }
	  public String getPageStr() {
	    return this.pageStr;
	  }
	  public void setFormStr(String formStr) {
	    this.formStr = formStr;
	  }
	  public void setPageStr(String pageStr) {
	    this.pageStr = pageStr;
	  }

	  public String toString()
	  {
	    return this.pageStr + this.formStr;
	  }
}
