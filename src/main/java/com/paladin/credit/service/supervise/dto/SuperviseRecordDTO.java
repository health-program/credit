package com.paladin.credit.service.supervise.dto;


import java.util.List;

public class SuperviseRecordDTO {

	private String itemId;

	// 目标类型
	private Integer targetType;

	// 机构ID
	private String[] agencyId;

	// 人员ID
	private String[] personnelId;

	private String[] selections;

	//相关人员
	private List<SuperviseRecordPersonnelDTO> personnels;

	// 监察结果名称
	private String explain;

	// 监察结果名称
	private String explainAttachment;


	public Integer getTargetType() {
		return targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getExplainAttachment() {
		return explainAttachment;
	}

	public void setExplainAttachment(String explainAttachment) {
		this.explainAttachment = explainAttachment;
	}

	public List<SuperviseRecordPersonnelDTO> getPersonnels() {
		return personnels;
	}

	public void setPersonnels(List<SuperviseRecordPersonnelDTO> personnels) {
		this.personnels = personnels;
	}

	public String[] getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String[] agencyId) {
		this.agencyId = agencyId;
	}

	public String[] getPersonnelId() {
		return personnelId;
	}

	public void setPersonnelId(String[] personnelId) {
		this.personnelId = personnelId;
	}

	public String[] getSelections() {
		return selections;
	}

	public void setSelections(String[] selections) {
		this.selections = selections;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
}