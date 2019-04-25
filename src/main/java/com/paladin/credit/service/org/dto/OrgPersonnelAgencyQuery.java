package com.paladin.credit.service.org.dto;

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.QueryCondition;
import com.paladin.framework.common.QueryType;

public class OrgPersonnelAgencyQuery extends OffsetPage {
    private String name;

    @QueryCondition(type = QueryType.EQUAL)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}