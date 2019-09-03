package com.paladin.credit.service.department.dto;

import com.paladin.framework.excel.read.ReadProperty;

import java.util.Date;

/**
 * <法人行政处罚模板导入>
 *
 * @author Huangguochen
 * @create 2019/6/4 10:48
 */
public class DepartmentAdministrativePunishmentOrgUploadDTO {
    // 行政相对人
    @ReadProperty(cellIndex = 0,nullable = false)
    private String name;

    // 统一社会信用代码（三证必须选一填写）
    @ReadProperty(cellIndex = 1)
    private String socialCreditCode;

    // 组织机构代码
    @ReadProperty(cellIndex = 2)
    private String organizationCode;

    // 工商注册号
    @ReadProperty(cellIndex = 3)
    private String commercialRegistrationNumber;

    // 税务登记号
    @ReadProperty(cellIndex = 4)
    private String taxRegistrationNumber;

    // 法定代表人或负责人
    @ReadProperty(cellIndex = 5,nullable = false)
    private String chargePerson;

    // 证件类型
    @ReadProperty(cellIndex = 6,enumType = "identification-type",nullable = false)
    private Integer identificationType;

    // 证件号码
    @ReadProperty(cellIndex = 7,nullable = false)
    private String identificationNo;

    // 处罚名称
    @ReadProperty(cellIndex = 8,nullable = false)
    private String punishmentName;

    // 处罚权利编码
    @ReadProperty(cellIndex = 9)
    private String punishmentPowerCode;

    // 文书号
    @ReadProperty(cellIndex = 10,nullable = false)
    private String punishmentDocumentNumber;

    // 处罚日期
    @ReadProperty(cellIndex = 11,nullable = false)
    private Date punishmentDecisionTime;

    // 公示日期（始）
    @ReadProperty(cellIndex = 12)
    private Date punishmentEffectivityStartTime;

    // 公示截止日期（终）
    @ReadProperty(cellIndex = 13)
    private Date punishmentEffectivityEndTime;

    // 处罚机构
    @ReadProperty(cellIndex = 14,nullable = false)
    private String punishmentOrganization;

    // 处罚类型1
    @ReadProperty(cellIndex = 15,nullable = false,enumType = "punishment-type")
    private Integer punishmentTypeOne;

    // 处罚类型2
    @ReadProperty(cellIndex = 16,enumType = "punishment-type")
    private Integer punishmentTypeTwo;

    // 处罚事由
    @ReadProperty(cellIndex = 17,nullable = false)
    private String punishmentCause;

    // 处罚依据
    @ReadProperty(cellIndex = 18,nullable = false)
    private String punishmentBasis;

    // 处罚结果
    @ReadProperty(cellIndex = 19,nullable = false)
    private String punishmentResult;

    // 备注
    @ReadProperty(cellIndex = 20)
    private String remark;

    // 信息使用范围：0.公示；1.仅政府部门内部共享；2.仅可授权查询；
    @ReadProperty(cellIndex = 21,enumType = "information-usage-scope")
    private Integer informationUsageScope;

    // 失信严重程度：0.未定；1.一般；2.较重；3.严重
    @ReadProperty(cellIndex = 22,enumType = "dishonesty-degree-type")
    private Integer dishonestyDegree;

    // 状态：0=正常；1=撤销；2=异议；3=其他（备注说明）
    @ReadProperty(cellIndex = 23,enumType = "administrative-status-type")
    private Integer punishmentStatus;

    // 事业单位证书号
    @ReadProperty(cellIndex = 24)
    private String institutionCertificateNumber;

    // 社会组织登记证号
    @ReadProperty(cellIndex = 25)
    private String socialOrganizationRegistrationNumber;

    // 违法行为类型
    @ReadProperty(cellIndex = 26)
    private String illegalActType;

    // 罚款金额（单位万元）
    @ReadProperty(cellIndex = 27,min = "0" ,max = "10000")
    private Double penalty;

    // 没收违法所得没收非法财物的金额（单位为万元）
    @ReadProperty(cellIndex = 28,min = "0" ,max = "10000")
    private Double illegalProceeds;

    // 暂扣或吊销证照名称及编号
    @ReadProperty(cellIndex = 29)
    private String involvedLicenses;

    // 处罚机构统一社会信用代码
    @ReadProperty(cellIndex = 30)
    private String punishmentSocialCreditCode;

    // 数据来源单位
    @ReadProperty(cellIndex = 31)
    private String dataSourceUnit;

    // 数据来源统一社会信用代码
    @ReadProperty(cellIndex = 32)
    private String sourceSocialCreditCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
