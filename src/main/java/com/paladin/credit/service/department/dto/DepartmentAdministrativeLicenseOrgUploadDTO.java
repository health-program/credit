package com.paladin.credit.service.department.dto;

import com.paladin.framework.excel.read.ReadProperty;

import java.util.Date;

/**
 * <法人行政许可上报模板>
 *
 * @author Huangguochen
 * @create 2019/6/3 15:11
 */
public class DepartmentAdministrativeLicenseOrgUploadDTO {
    // 许可相对人
    @ReadProperty(cellIndex = 0,nullable = false)
    private String name;

    // 统一社会信用代码（三证必须选一填写）
    @ReadProperty(cellIndex = 1,nullable = false)
    private String socialCreditCode;

    // 工商注册号
    @ReadProperty(cellIndex = 2)
    private String commercialRegistrationNumber;

    // 组织机构代码
    @ReadProperty(cellIndex = 3)
    private String organizationCode;

    // 税务登记号
    @ReadProperty(cellIndex = 4)
    private String taxRegistrationNumber;

    // 审批类别
    @ReadProperty(cellIndex = 5,nullable = false)
    private String examineType;

    // 法定代表人或负责人
    @ReadProperty(cellIndex = 6,nullable = false)
    private String chargePerson;

    // 证件类型
    @ReadProperty(cellIndex = 7,nullable = false,enumType = "identification-type")
    private Integer identificationType;

    // 证件号码
    @ReadProperty(cellIndex = 8)
    private String identificationNo;

    // 许可名称
    @ReadProperty(cellIndex = 9,nullable = false)
    private String licenseName;

    // 许可文书号
    @ReadProperty(cellIndex = 10,nullable = false)
    private String licenseDocumentNumber;

    // 许可权力编码
    @ReadProperty(cellIndex = 11)
    private String licenseAuthorityCode;

    // 许可决定日期
    @ReadProperty(cellIndex = 12,nullable = false)
    private Date licenseDecisionTime;

    // 许可有效期（始）
    @ReadProperty(cellIndex = 13)
    private Date licenseEffectivityStartTime;

    // 许可有效期（终）
    @ReadProperty(cellIndex = 14)
    private Date licenseEffectivityEndTime;

    // 许可机构
    @ReadProperty(cellIndex = 15,nullable = false)
    private String licenseOrganization;

    // 许可内容
    @ReadProperty(cellIndex = 16,nullable = false)
    private String licenseText;


    // 信息使用范围：0.公示；1.仅政府部门内部共享；2.仅可授权查询；
    @ReadProperty(cellIndex = 17,nullable = false,enumType = "information-usage-scope")
    private Integer informationUsageScope;

    // 许可状态：0=正常；1=撤销；2=异议；3=其他（备注说明）
    @ReadProperty(cellIndex = 18,nullable = false,enumType = "license-status-type")
    private Integer licenseStatus;

    // 备注
    @ReadProperty(cellIndex = 19)
    private String remark;

    // 事业单位证书号
    @ReadProperty(cellIndex = 20)
    private String institutionCertificateNumber;

    // 社会组织登记证号
    @ReadProperty(cellIndex = 21)
    private String socialOrganizationRegistrationNumber;

    // 许可证书名称
    @ReadProperty(cellIndex = 22)
    private String licenseCertificateName;

    // 许可证书编号
    @ReadProperty(cellIndex = 23)
    private String licenseCertificateNumber;

    // 许可文书名称
    @ReadProperty(cellIndex = 24)
    private String licenseDocumentName;

    // 许可机构统一社会信用代码
    @ReadProperty(cellIndex = 25)
    private String licenseSocialCreditCode;

    // 数据来源单位
    @ReadProperty(cellIndex = 26)
    private String dataSourceUnit;

    // 数据来源统一社会信用代码
    @ReadProperty(cellIndex = 27)
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

    public String getTaxRegistrationNumber() {
        return taxRegistrationNumber;
    }

    public void setTaxRegistrationNumber(String taxRegistrationNumber) {
        this.taxRegistrationNumber = taxRegistrationNumber;
    }

    public String getExamineType() {
        return examineType;
    }

    public void setExamineType(String examineType) {
        this.examineType = examineType;
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

    public String getLicenseOrganization() {
        return licenseOrganization;
    }

    public void setLicenseOrganization(String licenseOrganization) {
        this.licenseOrganization = licenseOrganization;
    }

    public String getLicenseText() {
        return licenseText;
    }

    public void setLicenseText(String licenseText) {
        this.licenseText = licenseText;
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
