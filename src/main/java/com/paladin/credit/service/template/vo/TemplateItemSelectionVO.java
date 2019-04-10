package com.paladin.credit.service.template.vo;


public class TemplateItemSelectionVO {

	// 
	private String id;

	// 选项名称
	private String selectionName;

	// 选项标号（ABCDE）
	private String selectionGrade;

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

	public String getSelectionGrade() {
		return selectionGrade;
	}

	public void setSelectionGrade(String selectionGrade) {
		this.selectionGrade = selectionGrade;
	}

}