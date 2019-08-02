package com.paladin.framework.common;

import javax.persistence.OrderBy;
import java.util.Date;

public abstract class BaseModel {
	
	public final static String COLUMN_FIELD_CREATE_TIME = "createTime";
	
	public final static int BOOLEAN_YES = 1;
	public final static int BOOLEAN_NO = 0;

	/** 自然人 */
	public final static int TARGET_TYPE_PEOPELE = 1;

	/** 法人 */
	public final static int TARGET_TYPE_ORG = 2;

	/** 红榜 */
	public final static int CREDIT_TYPE_RED = 1;

	/** 黑榜 */
	public final static int CREDIT_TYPE_BLACK = 2;

	/** 行业评定 */
	public final static int CREDIT_TYPE_HYPD = 3;

	public final static String REPLY_TIME_RANGE = " AND  CREATE_TIME <= DATEADD(mi, -10, DATEDIFF(DAY, 0,GETDATE() + 1 )) AND  CREATE_TIME >=  DATEADD(hh, 8, DATEDIFF(DAY, 0,GETDATE()))  ";


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
