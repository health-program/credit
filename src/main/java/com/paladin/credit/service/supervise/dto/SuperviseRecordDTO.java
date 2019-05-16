package com.paladin.credit.service.supervise.dto;


import java.util.Date;
import java.util.List;

public class SuperviseRecordDTO {

	private String itemId;

	// 目标类型
	private Integer targetType;

	// 机构ID
	private String agencyId;

	// 人员ID
	private String[] personnelId;

	private String[] selections;

	//相关人员
	private List<SuperviseRecordPersonnelDTO> personnels;

	// 监察结果名称
	private String explain;

	// 监察结果名称
	private String explainAttachment;

	private String item;

	private Integer score;

	private String scoreNo;

	private String scoreAttachment;

	private Date scoreTime;

	private String punishNo;

	private String punishAttachment;

	private Date punishTime;

	private Integer code;


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

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getScoreNo() {
		return scoreNo;
	}

	public void setScoreNo(String scoreNo) {
		this.scoreNo = scoreNo;
	}

	public String getScoreAttachment() {
		return scoreAttachment;
	}

	public void setScoreAttachment(String scoreAttachment) {
		this.scoreAttachment = scoreAttachment;
	}

	public Date getScoreTime() {
		return scoreTime;
	}

	public void setScoreTime(Date scoreTime) {
		this.scoreTime = scoreTime;
	}

	public String getPunishNo() {
		return punishNo;
	}

	public void setPunishNo(String punishNo) {
		this.punishNo = punishNo;
	}

	public String getPunishAttachment() {
		return punishAttachment;
	}

	public void setPunishAttachment(String punishAttachment) {
		this.punishAttachment = punishAttachment;
	}

	public Date getPunishTime() {
		return punishTime;
	}

	public void setPunishTime(Date punishTime) {
		this.punishTime = punishTime;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}