package com.paladin.credit.service.template.dto;

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.QueryCondition;
import com.paladin.framework.common.QueryType;

public class TemplateItemQuery extends OffsetPage {

	private String itemName;

	private Integer itemTargetType;

	@QueryCondition(type = QueryType.LIKE)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@QueryCondition(type = QueryType.EQUAL)
	public Integer getItemTargetType() {
		return itemTargetType;
	}

	public void setItemTargetType(Integer itemTargetType) {
		this.itemTargetType = itemTargetType;
	}

}