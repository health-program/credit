package com.paladin.credit.service.department.dto;

import java.util.Date;

public class DepartmentPersonCreditDTO {

	// 
	private String id;

	// 
	private String name;

	// 类型
	private Integer type;

	// 身份号码
	private String identificationNo;

	// 荣誉名称
	private String honorName;

	// 认定文号
	private String affirmWrit;

	// 荣誉等级
	private Integer honorLevel;

	// 认定日期
	private Date affirmTime;

	// 认定部门全称
	private String affirmDepartmentName;

	// 荣誉内容
	private String honorText;

	// 备注
	private String remark;

	// 负责人名字
	private String chargePersonName;

	// 确认严重失信时间
	private Date losePromiseTime;

	// 主要失信事实
	private String losePromiseTruth;

	// 行政处理处罚或法院判决决定的主要内容
	private String punishText;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIdentificationNo() {
		return identificationNo;
	}

	public void setIdentificationNo(String identificationNo) {
		this.identificationNo = identificationNo;
	}

	public String getHonorName() {
		return honorName;
	}

	public void setHonorName(String honorName) {
		this.honorName = honorName;
	}

	public String getAffirmWrit() {
		return affirmWrit;
	}

	public void setAffirmWrit(String affirmWrit) {
		this.affirmWrit = affirmWrit;
	}

	public Integer getHonorLevel() {
		return honorLevel;
	}

	public void setHonorLevel(Integer honorLevel) {
		this.honorLevel = honorLevel;
	}

	public Date getAffirmTime() {
		return affirmTime;
	}

	public void setAffirmTime(Date affirmTime) {
		this.affirmTime = affirmTime;
	}

	public String getAffirmDepartmentName() {
		return affirmDepartmentName;
	}

	public void setAffirmDepartmentName(String affirmDepartmentName) {
		this.affirmDepartmentName = affirmDepartmentName;
	}

	public String getHonorText() {
		return honorText;
	}

	public void setHonorText(String honorText) {
		this.honorText = honorText;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getChargePersonName() {
		return chargePersonName;
	}

	public void setChargePersonName(String chargePersonName) {
		this.chargePersonName = chargePersonName;
	}

	public Date getLosePromiseTime() {
		return losePromiseTime;
	}

	public void setLosePromiseTime(Date losePromiseTime) {
		this.losePromiseTime = losePromiseTime;
	}

	public String getLosePromiseTruth() {
		return losePromiseTruth;
	}

	public void setLosePromiseTruth(String losePromiseTruth) {
		this.losePromiseTruth = losePromiseTruth;
	}

	public String getPunishText() {
		return punishText;
	}

	public void setPunishText(String punishText) {
		this.punishText = punishText;
	}

}