package com.paladin.credit.controller.template.dto;

import com.paladin.common.core.export.ExportCondition;
import com.paladin.credit.service.template.dto.TemplateItemQuery;

public class TemplateItemExportCondition extends ExportCondition {

	private TemplateItemQuery query;

	public TemplateItemQuery getQuery() {
		return query;
	}

	public void setQuery(TemplateItemQuery query) {
		this.query = query;
	}

}