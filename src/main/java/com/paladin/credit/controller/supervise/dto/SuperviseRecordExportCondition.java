package com.paladin.credit.controller.supervise.dto;

import com.paladin.common.core.export.ExportCondition;
import com.paladin.credit.service.supervise.dto.SuperviseRecordQuery;

public class SuperviseRecordExportCondition extends ExportCondition {

	private SuperviseRecordQuery query;

	public SuperviseRecordQuery getQuery() {
		return query;
	}

	public void setQuery(SuperviseRecordQuery query) {
		this.query = query;
	}

}