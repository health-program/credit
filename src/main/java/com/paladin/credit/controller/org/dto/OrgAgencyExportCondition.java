package com.paladin.credit.controller.org.dto;

import com.paladin.common.core.export.ExportCondition;
import com.paladin.credit.service.org.dto.OrgAgencyQuery;

public class OrgAgencyExportCondition extends ExportCondition {

	private OrgAgencyQuery query;

	public OrgAgencyQuery getQuery() {
		return query;
	}

	public void setQuery(OrgAgencyQuery query) {
		this.query = query;
	}

}