package com.paladin.credit.model.org;

import com.paladin.framework.common.BaseModel;

import javax.persistence.Id;

public class OrgPunishmentRulesManage extends BaseModel {

	public static final String COLUMN_FIELD_SERIAL_NUMBER = "serialNumber";

	// 
	@Id
	private String id;

	private Integer serialNumber;

	// 案由
	private String punishmentCase;

	// 违反条款
	private String punishmentReason;

	// 处罚条款
	private String punishmentBasis;

	// 处罚内容
	private String punishmentText;

	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPunishmentCase() {
		return punishmentCase;
	}

	public void setPunishmentCase(String punishmentCase) {
		this.punishmentCase = punishmentCase;
	}

	public String getPunishmentReason() {
		return punishmentReason;
	}

	public void setPunishmentReason(String punishmentReason) {
		this.punishmentReason = punishmentReason;
	}

	public String getPunishmentBasis() {
		return punishmentBasis;
	}

	public void setPunishmentBasis(String punishmentBasis) {
		this.punishmentBasis = punishmentBasis;
	}

	public String getPunishmentText() {
		return punishmentText;
	}

	public void setPunishmentText(String punishmentText) {
		this.punishmentText = punishmentText;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}