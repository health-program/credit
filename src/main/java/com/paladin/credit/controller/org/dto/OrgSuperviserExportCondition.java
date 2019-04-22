package com.paladin.credit.controller.org.dto;

import com.paladin.common.core.export.ExportCondition;
import com.paladin.credit.service.org.dto.OrgSuperviserQuery;

public class OrgSuperviserExportCondition extends ExportCondition {

	private OrgSuperviserQuery query;

	public OrgSuperviserQuery getQuery() {
		return query;
	}

	public void setQuery(OrgSuperviserQuery query) {
		this.query = query;
	}

}