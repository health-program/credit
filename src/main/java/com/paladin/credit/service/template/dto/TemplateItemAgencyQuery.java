package com.paladin.credit.service.template.dto;

import com.paladin.framework.common.OffsetPage;

public class TemplateItemAgencyQuery extends OffsetPage {

    private String agencyId;

    private String itemName;

    private Integer itemTargetType;

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemTargetType() {
        return itemTargetType;
    }

    public void setItemTargetType(Integer itemTargetType) {
        this.itemTargetType = itemTargetType;
    }
}