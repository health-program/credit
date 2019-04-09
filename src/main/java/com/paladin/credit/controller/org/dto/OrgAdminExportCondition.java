package com.paladin.credit.controller.org.dto;

import com.paladin.common.core.export.ExportCondition;
import com.paladin.credit.service.org.dto.OrgAdminQuery;

public class OrgAdminExportCondition extends ExportCondition {

	private OrgAdminQuery query;

	public OrgAdminQuery getQuery() {
		return query;
	}

	public void setQuery(OrgAdminQuery query) {
		this.query = query;
	}

}