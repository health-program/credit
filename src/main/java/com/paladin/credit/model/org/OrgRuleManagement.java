package com.paladin.credit.model.org;

import com.paladin.framework.common.BaseModel;
import javax.persistence.Id;

public class OrgRuleManagement extends BaseModel {

	public static final int  TYPE_AGENCY = 1;
	public static final int  TYPE_PEOPLE = 2;

	// 
	@Id
	private String id;

	// 
	private String name;

	// 分值
	private Integer score;

	// 备注
	private String remarks;

	// 
	private Integer type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}