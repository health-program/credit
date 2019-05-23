package com.paladin.credit.service.department.dto;

import com.paladin.framework.excel.read.ReadProperty;

import java.util.Date;

/**
 * <自然人红榜名单模板导入>
 *
 * @author Huangguochen
 * @create 2019/5/23 9:47
 */
public class DepartmentPersonRedUploadDTO {

    @ReadProperty(cellIndex = 0,nullable = false)
    private String name;

    @ReadProperty(cellIndex = 1,minLength = 1,maxLength = 18,nullable = false)
    private String identificationNo;

    @ReadProperty(cellIndex = 2,minLength = 1,maxLength = 25,nullable = false)
    private String honorName;

    @ReadProperty(cellIndex = 3,minLength = 1,maxLength = 20,nullable = false)
    private String affirmWrit;

    @ReadProperty(cellIndex = 4,enumType = "honor-level-type")
    private Integer honorLevel;

    @ReadProperty(cellIndex = 5,nullable = false)
    private Date affirmTime;

    @ReadProperty(cellIndex = 6,nullable = false)
    private String affirmDepartmentName;

    @ReadProperty(cellIndex = 7,nullable = false)
    private String honorText;

    @ReadProperty(cellIndex = 8)
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentificationNo() {
        return identificationNo;
    }

    public void setIdentificationNo(String identificationNo) {
        this.identificationNo = identificationNo;
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
