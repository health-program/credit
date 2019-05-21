package com.paladin.credit.service.supervise.vo;

import com.google.common.base.Strings;
import com.paladin.common.core.ConstantsContainer;

public class SuperviseRecordOrgMapVO {

    private String agencyId;
    //机构名称
    private String agencyName;
    //结果名称
    private String item;
    //结果等级
    private Integer resultGrade;
    //机构坐标
    private String agencyCoordinate;
    //左坐标
    private Double lng;
    //右坐标
    private Double lat;

    private Integer grade;

    private String gradeName;

    private String resultGradeName;

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setOrgLng(String agencyCoordinate) {
        this.lng = Double.valueOf(agencyCoordinate.split(",")[0]);
    }

    public Double getLat() {
        return lat;
    }

    public void setOrgLat(String agencyCoordinate) {
        this.lat = Double.valueOf(agencyCoordinate.split(",")[1]);
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getAgencyCoordinate() {
        return agencyCoordinate;
    }

    public void setAgencyCoordinate(String agencyCoordinate) {
        this.agencyCoordinate = agencyCoordinate;
        if ( !Strings.isNullOrEmpty(agencyCoordinate)){
            setOrgLat(agencyCoordinate);
            setOrgLng(agencyCoordinate);
        }
    }

    public Integer getResultGrade() {
        return resultGrade;
    }

    public void setResultGrade(Integer resultGrade) {
        this.resultGrade = resultGrade;
        if (resultGrade != null) {
            setResultGradeName(ConstantsContainer.getTypeValue("selection-grade-type", String.valueOf(resultGrade)));
        }
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
        if (grade != null) {
            setGradeName(ConstantsContainer.getTypeValue("agency-credit-grade", String.valueOf(grade)));
        }
    }

    public String getResultGradeName() {
        return resultGradeName;
    }

    public void setResultGradeName(String resultGradeName) {
        this.resultGradeName = resultGradeName;
    }
}
