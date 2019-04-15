package com.paladin.credit.controller.publicity.dto;

import com.paladin.common.core.export.ExportCondition;
import com.paladin.credit.service.publicity.dto.PublicityMessageQuery;

public class PublicityMessageExportCondition extends ExportCondition {

	private PublicityMessageQuery query;

	public PublicityMessageQuery getQuery() {
		return query;
	}

	public void setQuery(PublicityMessageQuery query) {
		this.query = query;
	}

}