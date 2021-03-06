package com.paladin.credit.service.supervise.dto;

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.QueryCondition;
import com.paladin.framework.common.QueryType;

import java.util.Date;

public class SuperviseRecordQuery extends OffsetPage {

    private String item;

    private Integer targetType;

    private Integer infoEntryType;

    private Integer isWjs;

    private String agencyId;

    private Integer resultGrade;

    private  String  agencyName;

    private String personnelName;

    private String relatedPersonnelName;

    private Date bgTime;

    private Date endTime;

    private Integer status;

    private Integer code;

    @QueryCondition(type = QueryType.LIKE)
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @QueryCondition(type = QueryType.EQUAL)
    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    @QueryCondition(type = QueryType.EQUAL)
    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    @QueryCondition(type = QueryType.EQUAL)
    public Integer getResultGrade() {
        return resultGrade;
    }

    public void setResultGrade(Integer resultGrade) {
        this.resultGrade = resultGrade;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getPersonnelName() {
        return personnelName;
    }

    public void setPersonnelName(String personnelName) {
        this.personnelName = personnelName;
    }

    public String getRelatedPersonnelName() {
        return relatedPersonnelName;
    }

    public void setRelatedPersonnelName(String relatedPersonnelName) {
        this.relatedPersonnelName = relatedPersonnelName;
    }

    public Date getBgTime() {
        return bgTime;
    }

    public void setBgTime(Date bgTime) {
        this.bgTime = bgTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsWjs() {
        return isWjs;
    }

    public void setIsWjs(Integer isWjs) {
        this.isWjs = isWjs;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getInfoEntryType() {
        return infoEntryType;
    }

    public void setInfoEntryType(Integer infoEntryType) {
        this.infoEntryType = infoEntryType;
    }
}