package com.paladin.credit.model.template;

import com.paladin.framework.common.BaseModel;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class TemplateItem extends BaseModel {

	// 
	@Id
	@GeneratedValue(generator = "UUID")
	private String id;

	// 项目名称
	private String itemName;

	// 是否多选
	private Integer isMultiple;
	
	// 项目适用对象
	private Integer itemTargetType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getItemTargetType() {
		return itemTargetType;
	}

	public void setItemTargetType(Integer itemTargetType) {
		this.itemTargetType = itemTargetType;
	}

	public Integer getIsMultiple() {
		return isMultiple;
	}

	public void setIsMultiple(Integer isMultiple) {
		this.isMultiple = isMultiple;
	}
}