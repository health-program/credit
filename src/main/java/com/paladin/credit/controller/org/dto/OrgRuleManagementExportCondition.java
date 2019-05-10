package com.paladin.credit.controller.org.dto;

import com.paladin.common.core.export.ExportCondition;
import com.paladin.credit.service.org.dto.OrgRuleManagementQuery;

public class OrgRuleManagementExportCondition extends ExportCondition {

	private OrgRuleManagementQuery query;

	public OrgRuleManagementQuery getQuery() {
		return query;
	}

	public void setQuery(OrgRuleManagementQuery query) {
		this.query = query;
	}

}