package com.paladin.credit.service.department.dto;

import com.paladin.framework.excel.read.ReadProperty;

import java.util.Date;

/**
 * <自然人行政许可模板>
 *
 * @author Huangguochen
 * @create 2019/6/3 14:03
 */
public class DepartmentAdministrativeLicensePeopleUploadDTO {

    // 许可相对人
    @ReadProperty(cellIndex = 0,nullable = false)
    private String name;

    // 证件类型
    @ReadProperty(cellIndex = 1,nullable = false,enumType = "identification-type")
    private Integer identificationType;

    // 证件号码
    @ReadProperty(cellIndex = 2,nullable = false)
    private String identificationNo;

    // 审批类别
    @ReadProperty(cellIndex = 3,nullable = false)
    private String examineType;

    // 许可名称
    @ReadProperty(cellIndex = 4,nullable = false)
    private String licenseName;

    // 许可文书号
    @ReadProperty(cellIndex =5,nullable = false)
    private String licenseDocumentNumber;

    // 许可权力编码
    @ReadProperty(cellIndex = 6)
    private String licenseAuthorityCode;

    // 许可内容
    @ReadProperty(cellIndex = 7,nullable = false)
    private String licenseText;

    // 许可机构
    @ReadProperty(cellIndex = 8,nullable = false)
    private String licenseOrganization;

    // 许可决定日期
    @ReadProperty(cellIndex = 9,nullable = false)
    private Date licenseDecisionTime;

    // 许可有效期（始）
    @ReadProperty(cellIndex = 10)
    private Date licenseEffectivityStartTime;

    // 许可有效期（终）
    @ReadProperty(cellIndex = 11)
    private Date licenseEffectivityEndTime;

    // 信息使用范围：0.公示；1.仅政府部门内部共享；2.仅可授权查询；
    @ReadProperty(cellIndex = 12,nullable = false,enumType = "information-usage-scope")
    private Integer informationUsageScope;

    // 许可状态：0=正常；1=撤销；2=异议；3=其他（备注说明）
    @ReadProperty(cellIndex = 13,nullable = false,enumType = "license-status-type")
    private Integer licenseStatus;

    // 备注
    @ReadProperty(cellIndex = 14)
    private String remark;

    // 许可证书名称
    @ReadProperty(cellIndex = 15)
    private String licenseCertificateName;

    // 许可证书编号
    @ReadProperty(cellIndex = 16)
    private String licenseCertificateNumber;

    // 许可文书名称
    @ReadProperty(cellIndex = 17)
    private String licenseDocumentName;

    // 许可机构统一社会信用代码
    @ReadProperty(cellIndex = 18)
    private String licenseSocialCreditCode;

    // 数据来源单位
    @ReadProperty(cellIndex = 19)
    private String dataSourceUnit;

    // 数据来源统一社会信用代码
    @ReadProperty(cellIndex = 20)
    private String sourceSocialCreditCode;

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

    public String getExamineType() {
        return examineType;
    }

    public void setExamineType(String examineType) {
        this.examineType = examineType;
    }

    public String getLicenseName() {
        return licenseName;
    }

    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    public String getLicenseDocumentNumber() {
        return licenseDocumentNumber;
    }

    public void setLicenseDocumentNumber(String licenseDocumentNumber) {
        this.licenseDocumentNumber = licenseDocumentNumber;
    }

    public String getLicenseAuthorityCode() {
        return licenseAuthorityCode;
    }

    public void setLicenseAuthorityCode(String licenseAuthorityCode) {
        this.licenseAuthorityCode = licenseAuthorityCode;
    }

    public String getLicenseText() {
        return licenseText;
    }

    public void setLicenseText(String licenseText) {
        this.licenseText = licenseText;
    }

    public String getLicenseOrganization() {
        return licenseOrganization;
    }

    public void setLicenseOrganization(String licenseOrganization) {
        this.licenseOrganization = licenseOrganization;
    }

    public Date getLicenseDecisionTime() {
        return licenseDecisionTime;
    }

    public void setLicenseDecisionTime(Date licenseDecisionTime) {
        this.licenseDecisionTime = licenseDecisionTime;
    }

    public Date getLicenseEffectivityStartTime() {
        return licenseEffectivityStartTime;
    }

    public void setLicenseEffectivityStartTime(Date licenseEffectivityStartTime) {
        this.licenseEffectivityStartTime = licenseEffectivityStartTime;
    }

    public Date getLicenseEffectivityEndTime() {
        return licenseEffectivityEndTime;
    }

    public void setLicenseEffectivityEndTime(Date licenseEffectivityEndTime) {
        this.licenseEffectivityEndTime = licenseEffectivityEndTime;
    }

    public Integer getInformationUsageScope() {
        return informationUsageScope;
    }

    public void setInformationUsageScope(Integer informationUsageScope) {
        this.informationUsageScope = informationUsageScope;
    }

    public Integer getLicenseStatus() {
        return licenseStatus;
    }

    public void setLicenseStatus(Integer licenseStatus) {
        this.licenseStatus = licenseStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLicenseCertificateName() {
        return licenseCertificateName;
    }

    public void setLicenseCertificateName(String licenseCertificateName) {
        this.licenseCertificateName = licenseCertificateName;
    }

    public String getLicenseCertificateNumber() {
        return licenseCertificateNumber;
    }

    public void setLicenseCertificateNumber(String licenseCertificateNumber) {
        this.licenseCertificateNumber = licenseCertificateNumber;
    }

    public String getLicenseDocumentName() {
        return licenseDocumentName;
    }

    public void setLicenseDocumentName(String licenseDocumentName) {
        this.licenseDocumentName = licenseDocumentName;
    }

    public String getLicenseSocialCreditCode() {
        return licenseSocialCreditCode;
    }

    public void setLicenseSocialCreditCode(String licenseSocialCreditCode) {
        this.licenseSocialCreditCode = licenseSocialCreditCode;
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
