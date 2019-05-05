package com.paladin.credit.service.supervise.dto;

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.QueryCondition;
import com.paladin.framework.common.QueryType;

public class SuperviseRecordQuery extends OffsetPage {

    private String item;

    private Integer targetType;

    private String agencyId;

    private Integer resultGrade;

    private  String  agencyName;

    private String personnelName;

    private String relatedPersonnelName;

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
}