package com.paladin.credit.service.template.dto;


public class TemplateItemSelectionDTO {

	// 
	private String id;

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