package com.paladin.credit.model.template;

import com.paladin.framework.common.BaseModel;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class TemplateItemSelection extends BaseModel {

	public static final String COLUMN_FIELD_ITEM_ID= "itemId";
	
	// 
	@Id
	@GeneratedValue(generator = "UUID")
	private String id;

	// 项目ID
	private String itemId;

	// 选项名称
	private String selectionName;

	// 选项标号（ABCDE）
	private Integer selectionGrade;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getSelectionName() {
		return selectionName;
	}

	public void setSelectionName(String selectionName) {
		this.selectionName = selectionName;
	}

	public Integer getSelectionGrade() {
		return selectionGrade;
	}

	public void setSelectionGrade(Integer selectionGrade) {
		this.selectionGrade = selectionGrade;
	}

}