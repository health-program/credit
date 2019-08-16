package com.paladin.credit.controller.org.dto;

import com.paladin.common.core.export.ExportCondition;
import com.paladin.credit.service.org.dto.OrgPunishmentDiscretionQuery;

public class OrgPunishmentDiscretionExportCondition extends ExportCondition {

	private OrgPunishmentDiscretionQuery query;

	public OrgPunishmentDiscretionQuery getQuery() {
		return query;
	}

	public void setQuery(OrgPunishmentDiscretionQuery query) {
		this.query = query;
	}

}