package com.paladin.credit.service.template.vo;

import com.paladin.credit.model.template.TemplateItemSelection;

import java.util.List;

public class TemplateItemDetailVO {

	//
	private String id;

	// 项目名称
	private String itemName;

	// 项目适用对象
	private Integer itemTargetType;
	
	private Integer isMultiple;

	private  String agencyId;

	// 选项
	private List<TemplateItemSelection> selections;

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

	public List<TemplateItemSelection> getSelections() {
		return selections;
	}

	public void setSelections(List<TemplateItemSelection> selections) {
		this.selections = selections;
	}

	public Integer getIsMultiple() {
		return isMultiple;
	}

	public void setIsMultiple(Integer isMultiple) {
		this.isMultiple = isMultiple;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
}