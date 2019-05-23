package com.paladin.credit.service.department.dto;

import com.paladin.framework.excel.read.ReadProperty;

import java.util.Date;

/**
 * 功能描述 :<法人红榜上传模板>
 * @Date 13:13 2019/5/23
 **/
public class DepartmentCreditRedUploadDTO {
    // 统一社会信用代码（三证必须选一填写）
    @ReadProperty(cellIndex = 0,nullable = false)
    private String socialCreditCode;

    // 工商注册号
    @ReadProperty(cellIndex = 1)
    private String commercialRegistrationNumber;

    // 组织机构代码
    @ReadProperty(cellIndex = 2)
    private String organizationCode;

    // 荣誉名称
    @ReadProperty(cellIndex = 3,nullable = false)
    private String honorName;

    // 认定文号
    @ReadProperty(cellIndex = 4,nullable = false)
    private String affirmWrit;

    // 荣誉等级
    @ReadProperty(cellIndex = 5,enumType = "honor-level-type")
    private Integer honorLevel;

    // 机构名称
    @ReadProperty(cellIndex = 6,nullable = false)
    private String name;

    // 认定日期
    @ReadProperty(cellIndex = 7,nullable = false)
    private Date affirmTime;

    // 认定部门全称
    @ReadProperty(cellIndex = 8,nullable = false)
    private String affirmDepartmentName;

    // 荣誉内容
    @ReadProperty(cellIndex = 9,nullable = false)
    private String honorText;

    // 备注
    @ReadProperty(cellIndex = 10)
    private String remark;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
