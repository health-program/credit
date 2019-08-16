package com.paladin.credit.controller.org.dto;

import com.paladin.common.core.export.ExportCondition;
import com.paladin.credit.service.org.dto.OrgPunishmentRulesManageQuery;

public class OrgPunishmentRulesManageExportCondition extends ExportCondition {

	private OrgPunishmentRulesManageQuery query;

	public OrgPunishmentRulesManageQuery getQuery() {
		return query;
	}

	public void setQuery(OrgPunishmentRulesManageQuery query) {
		this.query = query;
	}

}