package com.paladin.credit.service.org.dto;

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.QueryCondition;
import com.paladin.framework.common.QueryType;

import java.util.Date;

public class OrgRuleManagementQuery extends OffsetPage {
    private String name;

    private Integer score;

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

    @QueryCondition(type = QueryType.EQUAL)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @QueryCondition(type = QueryType.EQUAL)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @QueryCondition( name = "createTime",type = QueryType.GREAT_EQUAL)
    public Date getBgTime() {
        return bgTime;
    }

    public void setBgTime(Date bgTime) {
        this.bgTime = bgTime;
    }

    @QueryCondition( name = "createTime",type = QueryType.LESS_THAN)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}