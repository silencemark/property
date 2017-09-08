package com.lr.backer.vo;

import com.lr.backer.util.PageUtil;

public class TDTRole {
  
    private Integer id;

    private String name;

    private Integer status;
    
    private PageUtil page;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public PageUtil getPage() {
		return page;
	}

	public void setPage(PageUtil page) {
		this.page = page;
	}
    
    
}