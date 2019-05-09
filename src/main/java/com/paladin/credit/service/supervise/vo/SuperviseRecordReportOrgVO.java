package com.paladin.credit.service.supervise.vo;

import java.util.Date;

/**
 * <医疗机构信誉评价表理前台展示>
 */
public class SuperviseRecordReportOrgVO {
    //信誉等级
    private String resultGrade;
    //单位名称
    private String agencyName;
    //社会信用代码
    private String socialCreditCode;
    //单位地址
    private String address;
    //法定代表人或负责人
    private String chargePerson;
    //联系电话
    private String contactWay;
    //机构类别
    private String agencyType;
    //成立日期
    private Date registerTime;
    //经营范围
    private String businessScope;

    private  String agencyCoordinate;

    public String getResultGrade() {
        return resultGrade;
    }

    public void setResultGrade(String resultGrade) {
        this.resultGrade = resultGrade;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getAgencyType() {
        return agencyType;
    }

    public void setAgencyType(String agencyType) {
        this.agencyType = agencyType;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getAgencyCoordinate() {
        return agencyCoordinate;
    }

    public void setAgencyCoordinate(String agencyCoordinate) {
        this.agencyCoordinate = agencyCoordinate;
    }
}
