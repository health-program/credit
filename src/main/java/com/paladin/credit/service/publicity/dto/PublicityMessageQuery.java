package com.paladin.credit.service.publicity.dto;

import com.paladin.credit.model.publicity.PublicityMessage;
import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.QueryCondition;
import com.paladin.framework.common.QueryType;

import java.util.Date;

public class PublicityMessageQuery extends OffsetPage {

    private String title;

    private Integer status;

    private Date startTime;

    private Date endTime;

    @QueryCondition(type = QueryType.LIKE)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @QueryCondition(type = QueryType.EQUAL)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @QueryCondition(name = PublicityMessage.COLUMN_FIELD_PUBLISH_TIME,type = QueryType.GREAT_EQUAL)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @QueryCondition(name = PublicityMessage.COLUMN_FIELD_PUBLISH_TIME,type = QueryType.LESS_EQUAL)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}