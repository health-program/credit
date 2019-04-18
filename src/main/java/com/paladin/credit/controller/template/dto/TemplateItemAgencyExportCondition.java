package com.paladin.credit.controller.template.dto;

import com.paladin.common.core.export.ExportCondition;
import com.paladin.credit.service.template.dto.TemplateItemAgencyQuery;

public class TemplateItemAgencyExportCondition extends ExportCondition {

	private TemplateItemAgencyQuery query;

	public TemplateItemAgencyQuery getQuery() {
		return query;
	}

	public void setQuery(TemplateItemAgencyQuery query) {
		this.query = query;
	}

}