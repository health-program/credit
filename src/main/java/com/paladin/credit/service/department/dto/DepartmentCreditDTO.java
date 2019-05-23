package com.paladin.credit.service.department.dto;

import java.util.Date;

public class DepartmentCreditDTO {

	// 主键
	private String id;

	// 机构名称
	private String name;

	// 类型
	private Integer type;

	// 统一社会信用代码（三证必须选一填写）
	private String socialCreditCode;

	// 工商注册号
	private String commercialRegistrationNumber;

	// 组织机构代码
	private String organizationCode;

	// 荣誉名称
	private String honorName;

	// 认定文号
	private String affirmWrit;

	// 荣誉等级
	private Integer honorLevel;

	// 评定结果
	private Integer assessmentResult;

	// 评定名称
	private String assessmentResultName;

	// 认定日期
	private Date affirmTime;

	// 认定部门全称
	private String affirmDepartmentName;

	// 荣誉内容
	private String honorText;

	// 备注
	private String remark;

	// 地址
	private String registeredAddress;

	// 法定代表人或负责人
	private String chargePerson;

	// 确认严重失信时间
	private Date breakPromiseTime;

	// 主要失信事实
	private String breakPromiseTruth;

	// 行政处理处罚或法院判决决定的主要内容
	private String punishText;

	public String getAssessmentResultName() {
		return assessmentResultName;
	}

	public void setAssessmentResultName(String assessmentResultName) {
		this.assessmentResultName = assessmentResultName;
	}
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

	public String getSocialCreditCode() {
		return socialCreditCode;
	}

	public void setSocialCreditCode(String socialCreditCode) {
		this.socialCreditCode = socialCreditCode;
	}

	public String getCommercialRegistrationNumber() {
		return commercialRegistrationNumber;
	}

	public void setCommercialRegistrationNumber(String commercialRegistrationNumber) {
		this.commercialRegistrationNumber = commercialRegistrationNumber;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
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

	public Integer getAssessmentResult() {
		return assessmentResult;
	}

	public void setAssessmentResult(Integer assessmentResult) {
		this.assessmentResult = assessmentResult;
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

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getChargePerson() {
		return chargePerson;
	}

	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
	}

	public Date getBreakPromiseTime() {
		return breakPromiseTime;
	}

	public void setBreakPromiseTime(Date breakPromiseTime) {
		this.breakPromiseTime = breakPromiseTime;
	}

	public String getBreakPromiseTruth() {
		return breakPromiseTruth;
	}

	public void setBreakPromiseTruth(String breakPromiseTruth) {
		this.breakPromiseTruth = breakPromiseTruth;
	}

	public String getPunishText() {
		return punishText;
	}

	public void setPunishText(String punishText) {
		this.punishText = punishText;
	}

}