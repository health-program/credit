package com.paladin.credit.service.department.dto;

import com.paladin.credit.model.department.DepartmentPersonCredit;
import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.QueryCondition;
import com.paladin.framework.common.QueryType;

import java.util.Date;

public class DepartmentCreditQuery extends OffsetPage {
    //机构名称
    private String name;
    //统一社会信用代码
    private String socialCreditCode;

    private Integer type;

    private Date bgTime;

    private Date endTime;

    @QueryCondition(type = QueryType.LIKE)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @QueryCondition(type = QueryType.LIKE)
    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }
    @QueryCondition(type = QueryType.EQUAL)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    @QueryCondition(name = DepartmentPersonCredit.COLUMN_FIELD_CREATE_TIME,type = QueryType.GREAT_EQUAL)
    public Date getBgTime() {
        return bgTime;
    }

    public void setBgTime(Date bgTime) {
        this.bgTime = bgTime;
    }
    @QueryCondition(name = DepartmentPersonCredit.COLUMN_FIELD_CREATE_TIME,type = QueryType.LESS_THAN)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}