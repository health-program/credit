package com.paladin.credit.service.department.dto;

import com.paladin.framework.excel.read.ReadProperty;

import java.util.Date;

/**
 * 功能描述 :<法人黑榜上传模板>
 * @Date 13:12 2019/5/23
 **/
public class DepartmentCreditBlackUploadDTO {

    // 统一社会信用代码（三证必须选一填写）
    @ReadProperty(cellIndex = 0,nullable = false)
    private String socialCreditCode;

    // 工商注册号
    @ReadProperty(cellIndex = 1)
    private String commercialRegistrationNumber;

    // 组织机构代码
    @ReadProperty(cellIndex = 2)
    private String organizationCode;

    // 认定文号
    @ReadProperty(cellIndex = 3,nullable = false)
    private String affirmWrit;

    // 认定部门全称
    @ReadProperty(cellIndex = 4,nullable = false)
    private String affirmDepartmentName;

    // 机构名称
    @ReadProperty(cellIndex = 5,nullable = false)
    private String name;

    // 地址
    @ReadProperty(cellIndex = 6)
    private String registeredAddress;

    // 法定代表人或负责人
    @ReadProperty(cellIndex = 7)
    private String chargePerson;

    // 确认严重失信时间
    @ReadProperty(cellIndex = 8,nullable = false)
    private Date breakPromiseTime;

    // 主要失信事实
    @ReadProperty(cellIndex = 9,nullable = false)
    private String breakPromiseTruth;

    // 行政处理处罚或法院判决决定的主要内容
    @ReadProperty(cellIndex = 10)
    private String punishText;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAffirmWrit() {
        return affirmWrit;
    }

    public void setAffirmWrit(String affirmWrit) {
        this.affirmWrit = affirmWrit;
    }

    public String getAffirmDepartmentName() {
        return affirmDepartmentName;
    }

    public void setAffirmDepartmentName(String affirmDepartmentName) {
        this.affirmDepartmentName = affirmDepartmentName;
    }
}
