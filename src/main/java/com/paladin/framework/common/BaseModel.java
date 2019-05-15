package com.paladin.framework.common;

import javax.persistence.OrderBy;
import java.util.Date;

public abstract class BaseModel {
	
	public final static String COLUMN_FIELD_CREATE_TIME = "createTime";
	
	public final static int BOOLEAN_YES = 1;
	public final static int BOOLEAN_NO = 2;


	@OrderBy("DESC")
	private Date createTime;

    private String createUserId;

    private Date updateTime;
    
    private String updateUserId;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
    
}
