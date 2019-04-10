package com.paladin.credit.controller.template.dto;

import com.paladin.common.core.export.ExportCondition;
import com.paladin.credit.service.template.dto.TemplateItemSelectionQuery;

public class TemplateItemSelectionExportCondition extends ExportCondition {

	private TemplateItemSelectionQuery query;

	public TemplateItemSelectionQuery getQuery() {
		return query;
	}

	public void setQuery(TemplateItemSelectionQuery query) {
		this.query = query;
	}

}