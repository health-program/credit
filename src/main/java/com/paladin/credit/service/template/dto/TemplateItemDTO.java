package com.paladin.credit.service.template.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TemplateItemDTO {

	//
	private String id;

	// 项目名称
	@NotEmpty(message = "项目名称不能为空")
	private String itemName;

	// 项目适用对象
	@NotNull(message = "项目目标类型不能为空")
	private Integer itemTargetType;

	@NotNull(message = "项目是否多选不能为空")
	private Integer isMultiple;
	
	// 选项
	private List<TemplateItemSelectionDTO> selections;

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

	public List<TemplateItemSelectionDTO> getSelections() {
		return selections;
	}

	public void setSelections(List<TemplateItemSelectionDTO> selections) {
		this.selections = selections;
	}

	public Integer getIsMultiple() {
		return isMultiple;
	}

	public void setIsMultiple(Integer isMultiple) {
		this.isMultiple = isMultiple;
	}

}