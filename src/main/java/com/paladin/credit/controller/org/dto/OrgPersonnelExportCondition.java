package com.paladin.credit.controller.org.dto;

import com.paladin.common.core.export.ExportCondition;
import com.paladin.credit.service.org.dto.OrgPersonnelQuery;

public class OrgPersonnelExportCondition extends ExportCondition{
	
	private OrgPersonnelQuery query;

	public OrgPersonnelQuery getQuery() {
		return query;
	}

	public void setQuery(OrgPersonnelQuery query) {
		this.query = query;
	}
	
}
