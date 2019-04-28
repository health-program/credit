package com.paladin.credit.controller.org.dto;

import com.paladin.common.core.export.ExportCondition;
import com.paladin.credit.service.org.dto.OrgPersonnelAgencyQuery;

public class OrgPersonnelAgencyExportCondition extends ExportCondition {

	private OrgPersonnelAgencyQuery query;

	public OrgPersonnelAgencyQuery getQuery() {
		return query;
	}

	public void setQuery(OrgPersonnelAgencyQuery query) {
		this.query = query;
	}

}