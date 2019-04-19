package com.paladin.credit.service.supervise.dto;

/**
 * <>
 *
 * @author Huangguochen
 * @create 2019/4/18 17:12
 */
public class SuperviseRecordPersonnelDTO {
    // 人员姓名
    private String personnelName;

    // 人员性别
    private Integer personnelSex;

    // 人员身份证
    private String personnelIdentification;

    // 人员地址
    private String personnelAddress;


    public String getPersonnelName() {
        return personnelName;
    }

    public void setPersonnelName(String personnelName) {
        this.personnelName = personnelName;
    }

    public Integer getPersonnelSex() {
        return personnelSex;
    }

    public void setPersonnelSex(Integer personnelSex) {
        this.personnelSex = personnelSex;
    }

    public String getPersonnelIdentification() {
        return personnelIdentification;
    }

    public void setPersonnelIdentification(String personnelIdentification) {
        this.personnelIdentification = personnelIdentification;
    }

    public String getPersonnelAddress() {
        return personnelAddress;
    }

    public void setPersonnelAddress(String personnelAddress) {
        this.personnelAddress = personnelAddress;
    }
}
