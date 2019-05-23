package com.paladin.credit.service.department.dto;

import com.paladin.framework.excel.read.ReadProperty;

import java.util.Date;

/**
 * 功能描述 :<行业上传模板>
 * @Date 13:13 2019/5/23
 **/
public class DepartmentEnterpriseUploadDTO {
    // 统一社会信用代码（三证必须选一填写）
    @ReadProperty(cellIndex = 0,nullable = false)
    private String socialCreditCode;

    // 工商注册号
    @ReadProperty(cellIndex = 1)
    private String commercialRegistrationNumber;

    // 组织机构代码
    @ReadProperty(cellIndex = 2)
    private String organizationCode;

    // 机构名称
    @ReadProperty(cellIndex = 3,nullable = false)
    private String name;

    // 评定结果
    @ReadProperty(cellIndex = 4,enumType = "assessment-result-type")
    private Integer assessmentResult;

    // 认定日期
    @ReadProperty(cellIndex = 5,nullable = false)
    private Date affirmTime;

    // 评定名称
    @ReadProperty(cellIndex = 6,nullable = false)
    private String assessmentResultName;

    // 认定部门全称
    @ReadProperty(cellIndex = 7,nullable = false)
    private String affirmDepartmentName;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAssessmentResultName() {
        return assessmentResultName;
    }

    public void setAssessmentResultName(String assessmentResultName) {
        this.assessmentResultName = assessmentResultName;
    }

    public String getAffirmDepartmentName() {
        return affirmDepartmentName;
    }

    public void setAffirmDepartmentName(String affirmDepartmentName) {
        this.affirmDepartmentName = affirmDepartmentName;
    }
}
