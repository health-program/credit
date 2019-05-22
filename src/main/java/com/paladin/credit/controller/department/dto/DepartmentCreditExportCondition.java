package com.paladin.credit.controller.department.dto;

import com.paladin.common.core.export.ExportCondition;
import com.paladin.credit.service.department.dto.DepartmentCreditQuery;

public class DepartmentCreditExportCondition extends ExportCondition {

	private DepartmentCreditQuery query;

	public DepartmentCreditQuery getQuery() {
		return query;
	}

	public void setQuery(DepartmentCreditQuery query) {
		this.query = query;
	}

}