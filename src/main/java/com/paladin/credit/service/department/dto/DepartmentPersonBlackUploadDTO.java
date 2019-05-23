package com.paladin.credit.service.department.dto;

import com.paladin.framework.excel.read.ReadProperty;

import java.util.Date;

/**
 * <自然人黑榜名单模板导入>
 *
 * @author Huangguochen
 * @create 2019/5/23 11:12
 */
public class DepartmentPersonBlackUploadDTO {

    @ReadProperty(cellIndex = 0,minLength = 1,maxLength = 18,nullable = false)
    private String identificationNo;

    @ReadProperty(cellIndex = 1,nullable = false)
    private String chargePersonName;

    @ReadProperty(cellIndex = 2)
    private String affirmWrit;

    @ReadProperty(cellIndex = 3,nullable = false)
    private String affirmDepartmentName;


    @ReadProperty(cellIndex = 4,nullable = false)
    private Date losePromiseTime;

    @ReadProperty(cellIndex = 5,nullable = false)
    private String losePromiseTruth;

    @ReadProperty(cellIndex = 6)
    private String punishText;

    public String getIdentificationNo() {
        return identificationNo;
    }

    public void setIdentificationNo(String identificationNo) {
        this.identificationNo = identificationNo;
    }

    public String getChargePersonName() {
        return chargePersonName;
    }

    public void setChargePersonName(String chargePersonName) {
        this.chargePersonName = chargePersonName;
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
