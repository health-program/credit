package com.paladin.credit.service.org.dto;

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.QueryCondition;
import com.paladin.framework.common.QueryType;

public class OrgPunishmentRulesManageQuery extends OffsetPage {

    private Integer serialNumber;

    // 案由
    private String punishmentCase;

    @QueryCondition(type = QueryType.EQUAL)
    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    @QueryCondition(type = QueryType.LIKE)
    public String getPunishmentCase() {
        return punishmentCase;
    }

    public void setPunishmentCase(String punishmentCase) {
        this.punishmentCase = punishmentCase;
    }
}