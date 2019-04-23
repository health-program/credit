package com.paladin.credit.service.template.dto;

import com.paladin.framework.common.OffsetPage;

public class TemplateItemAgencyQuery extends OffsetPage {

    private String agencyId;

    private Integer code;

    private String itemName;

    private Integer itemTargetType;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }
}