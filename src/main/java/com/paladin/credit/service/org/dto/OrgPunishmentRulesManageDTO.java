package com.paladin.credit.service.org.dto;


import com.paladin.credit.model.org.OrgPunishmentDiscretion;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class OrgPunishmentRulesManageDTO {

	// 
	private String id;

	@NotNull(message = "序号不能为空")
	private Integer serialNumber;

	// 案由
	@NotEmpty(message = "案由不能为空")
	private String punishmentCase;

	// 违反条款
	private String punishmentReason;

	// 处罚条款
	@NotEmpty(message = "处罚条款不能为空")
	private String punishmentBasis;

	// 处罚内容
	@NotEmpty(message = "处罚内容不能为空")
	private String punishmentText;

	private String remark;

	private List<OrgPunishmentDiscretion> selections;

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

	public List<OrgPunishmentDiscretion> getSelections() {
		return selections;
	}

	public void setSelections(List<OrgPunishmentDiscretion> selections) {
		this.selections = selections;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}