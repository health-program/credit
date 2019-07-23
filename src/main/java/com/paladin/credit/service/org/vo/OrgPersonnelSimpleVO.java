package com.paladin.credit.service.org.vo;

/**
 * <>
 *
 * @author Huangguochen
 * @create 2019/6/27 15:04
 */
public class OrgPersonnelSimpleVO {
    private String id;

    private String uniqueCode;

    private  String agencyName;

    // 证件号码
    private String identificationNo;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getIdentificationNo() {
        return identificationNo;
    }

    public void setIdentificationNo(String identificationNo) {
        this.identificationNo = identificationNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
