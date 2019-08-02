package com.paladin.credit.service.department.vo;

import java.util.Date;

public class DepartmentAdministrativePunishmentVO {

	// 
	private String id;

	// 处罚对象类型
	private Integer type;

	// 行政相对人
	private String name;

	private Integer status;

	// 证件类型
	private Integer identificationType;

	// 证件号码
	private String identificationNo;

	// 处罚名称
	private String punishmentName;

	// 统一社会信用代码（三证必须选一填写）
	private String socialCreditCode;

	// 组织机构代码
	private String organizationCode;

	// 工商注册号
	private String commercialRegistrationNumber;

	// 税务登记号
	private String taxRegistrationNumber;

	// 法定代表人或负责人
	private String chargePerson;

	// 处罚权利编码
	private String punishmentPowerCode;

	// 文书号
	private String punishmentDocumentNumber;

	// 处罚日期
	private Date punishmentDecisionTime;

	// 公示日期（始）
	private Date punishmentEffectivityStartTime;

	// 公示截止日期（终）
	private Date punishmentEffectivityEndTime;

	// 处罚机构
	private String punishmentOrganization;

	// 处罚类型1
	private Integer punishmentTypeOne;

	// 处罚类型2
	private Integer punishmentTypeTwo;

	// 处罚事由
	private String punishmentCause;

	// 处罚依据
	private String punishmentBasis;

	// 处罚结果
	private String punishmentResult;

	// 备注
	private String remark;

	// 信息使用范围：0.公示；1.仅政府部门内部共享；2.仅可授权查询；
	private Integer informationUsageScope;

	// 失信严重程度：0.未定；1.一般；2.较重；3.严重
	private Integer dishonestyDegree;

	// 状态：0=正常；1=撤销；2=异议；3=其他（备注说明）
	private Integer punishmentStatus;

	// 违法行为类型
	private String illegalActType;

	// 罚款金额（单位万元）
	private Double penalty;

	// 没收违法所得没收非法财物的金额（单位为万元）
	private Double illegalProceeds;

	// 事业单位证书号
	private String institutionCertificateNumber;

	// 社会组织登记证号
	private String socialOrganizationRegistrationNumber;

	// 暂扣或吊销证照名称及编号
	private String involvedLicenses;

	// 处罚机构统一社会信用代码
	private String punishmentSocialCreditCode;

	// 数据来源单位
	private String dataSourceUnit;

	// 数据来源统一社会信用代码
	private String sourceSocialCreditCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(Integer identificationType) {
		this.identificationType = identificationType;
	}

	public String getIdentificationNo() {
		return identificationNo;
	}

	public void setIdentificationNo(String identificationNo) {
		this.identificationNo = identificationNo;
	}

	public String getPunishmentName() {
		return punishmentName;
	}

	public void setPunishmentName(String punishmentName) {
		this.punishmentName = punishmentName;
	}

	public String getSocialCreditCode() {
		return socialCreditCode;
	}

	public void setSocialCreditCode(String socialCreditCode) {
		this.socialCreditCode = socialCreditCode;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getCommercialRegistrationNumber() {
		return commercialRegistrationNumber;
	}

	public void setCommercialRegistrationNumber(String commercialRegistrationNumber) {
		this.commercialRegistrationNumber = commercialRegistrationNumber;
	}

	public String getTaxRegistrationNumber() {
		return taxRegistrationNumber;
	}

	public void setTaxRegistrationNumber(String taxRegistrationNumber) {
		this.taxRegistrationNumber = taxRegistrationNumber;
	}

	public String getChargePerson() {
		return chargePerson;
	}

	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
	}

	public String getPunishmentPowerCode() {
		return punishmentPowerCode;
	}

	public void setPunishmentPowerCode(String punishmentPowerCode) {
		this.punishmentPowerCode = punishmentPowerCode;
	}

	public String getPunishmentDocumentNumber() {
		return punishmentDocumentNumber;
	}

	public void setPunishmentDocumentNumber(String punishmentDocumentNumber) {
		this.punishmentDocumentNumber = punishmentDocumentNumber;
	}

	public Date getPunishmentDecisionTime() {
		return punishmentDecisionTime;
	}

	public void setPunishmentDecisionTime(Date punishmentDecisionTime) {
		this.punishmentDecisionTime = punishmentDecisionTime;
	}

	public Date getPunishmentEffectivityStartTime() {
		return punishmentEffectivityStartTime;
	}

	public void setPunishmentEffectivityStartTime(Date punishmentEffectivityStartTime) {
		this.punishmentEffectivityStartTime = punishmentEffectivityStartTime;
	}

	public Date getPunishmentEffectivityEndTime() {
		return punishmentEffectivityEndTime;
	}

	public void setPunishmentEffectivityEndTime(Date punishmentEffectivityEndTime) {
		this.punishmentEffectivityEndTime = punishmentEffectivityEndTime;
	}

	public String getPunishmentOrganization() {
		return punishmentOrganization;
	}

	public void setPunishmentOrganization(String punishmentOrganization) {
		this.punishmentOrganization = punishmentOrganization;
	}

	public Integer getPunishmentTypeOne() {
		return punishmentTypeOne;
	}

	public void setPunishmentTypeOne(Integer punishmentTypeOne) {
		this.punishmentTypeOne = punishmentTypeOne;
	}

	public Integer getPunishmentTypeTwo() {
		return punishmentTypeTwo;
	}

	public void setPunishmentTypeTwo(Integer punishmentTypeTwo) {
		this.punishmentTypeTwo = punishmentTypeTwo;
	}

	public String getPunishmentCause() {
		return punishmentCause;
	}

	public void setPunishmentCause(String punishmentCause) {
		this.punishmentCause = punishmentCause;
	}

	public String getPunishmentBasis() {
		return punishmentBasis;
	}

	public void setPunishmentBasis(String punishmentBasis) {
		this.punishmentBasis = punishmentBasis;
	}

	public String getPunishmentResult() {
		return punishmentResult;
	}

	public void setPunishmentResult(String punishmentResult) {
		this.punishmentResult = punishmentResult;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getInformationUsageScope() {
		return informationUsageScope;
	}

	public void setInformationUsageScope(Integer informationUsageScope) {
		this.informationUsageScope = informationUsageScope;
	}

	public Integer getDishonestyDegree() {
		return dishonestyDegree;
	}

	public void setDishonestyDegree(Integer dishonestyDegree) {
		this.dishonestyDegree = dishonestyDegree;
	}

	public Integer getPunishmentStatus() {
		return punishmentStatus;
	}

	public void setPunishmentStatus(Integer punishmentStatus) {
		this.punishmentStatus = punishmentStatus;
	}

	public String getIllegalActType() {
		return illegalActType;
	}

	public void setIllegalActType(String illegalActType) {
		this.illegalActType = illegalActType;
	}

	public Double getPenalty() {
		return penalty;
	}

	public void setPenalty(Double penalty) {
		this.penalty = penalty;
	}

	public Double getIllegalProceeds() {
		return illegalProceeds;
	}

	public void setIllegalProceeds(Double illegalProceeds) {
		this.illegalProceeds = illegalProceeds;
	}

	public String getInstitutionCertificateNumber() {
		return institutionCertificateNumber;
	}

	public void setInstitutionCertificateNumber(String institutionCertificateNumber) {
		this.institutionCertificateNumber = institutionCertificateNumber;
	}

	public String getSocialOrganizationRegistrationNumber() {
		return socialOrganizationRegistrationNumber;
	}

	public void setSocialOrganizationRegistrationNumber(String socialOrganizationRegistrationNumber) {
		this.socialOrganizationRegistrationNumber = socialOrganizationRegistrationNumber;
	}

	public String getInvolvedLicenses() {
		return involvedLicenses;
	}

	public void setInvolvedLicenses(String involvedLicenses) {
		this.involvedLicenses = involvedLicenses;
	}

	public String getPunishmentSocialCreditCode() {
		return punishmentSocialCreditCode;
	}

	public void setPunishmentSocialCreditCode(String punishmentSocialCreditCode) {
		this.punishmentSocialCreditCode = punishmentSocialCreditCode;
	}

	public String getDataSourceUnit() {
		return dataSourceUnit;
	}

	public void setDataSourceUnit(String dataSourceUnit) {
		this.dataSourceUnit = dataSourceUnit;
	}

	public String getSourceSocialCreditCode() {
		return sourceSocialCreditCode;
	}

	public void setSourceSocialCreditCode(String sourceSocialCreditCode) {
		this.sourceSocialCreditCode = sourceSocialCreditCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}